/* DUBAGUNTA SAI KRISHNA cs610 PrP 2338 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class hits2338 {
	/**
	 * @param args - iterations initalValueOption(1, 0, -1, -2) filename
	 */
	
	private static BufferedReader bReader;
	private static double[] hubValues;
	private static double[] authValues;
	private static int no_of_iterations;
	private static int[][] aT,adjMat;
	private static Integer size;
	private static DecimalFormat dFormat;
	private static boolean errorRate;
	private static boolean largeGraph = false; 

	public static void main(String[] args ) throws Exception {
		dFormat = new DecimalFormat("#.0000000");
		String filename = args[2];
		int init_Value = Integer.parseInt(args[1]);
		no_of_iterations = Integer.parseInt(args[0]);
		bReader = new BufferedReader(new FileReader(filename));
		String line = bReader.readLine();
		size = Integer.parseInt(line.split(" ")[0]);
		adjMat = new int[size][size];
		aT=new int[size][size];
		hubValues = new double[size];
		authValues = new double[size];
		initializeAdjMatAndValues2338(init_Value);
		if(no_of_iterations < 0)
			setErrorRate2338(true);
		else if(no_of_iterations == 0){
			setErrorRate2338(true);
			no_of_iterations = -5;
		}
		buildMat2338(init_Value);
		aT = transpose2338();
		printAllIters2338("Base",0,authValues,hubValues);
		updateAHon2338(no_of_iterations);
	}


	public static void initializeAdjMatAndValues2338(int initVal){
		double initialVal = 0.0;
		if(size > 10){
			initialVal = 1/((double)size);
			no_of_iterations = 0;
			largeGraph = true;
		}
		else{
			switch(initVal){
			case 0:
				initialVal = 1/((double)size);
				break;
			case 1:
				initialVal = 1.0;
				break;
			case -1:
				double value = 1/((double)size);
				initialVal = value;
				break;
			case -2:
				initialVal = 1 / Math.sqrt((double)size);
				break;
			}
		}
		System.out.println("Vectors are initialized to "+initialVal);
		for(Integer i = 0; i< size; i++){
			hubValues[i] = initialVal;
			authValues[i] = initialVal;
		}
	}


	public static void buildMat2338(int initVal) throws IOException{

		String line = "";
		while((line = bReader.readLine())!=null){
			Integer one,two;
			one = Integer.parseInt(line.split(" ")[0]);
			two = Integer.parseInt(line.split(" ")[1]);
			adjMat[one][two] = 1;
		}
	}

	public static void updateAHon2338(int iter){
		if(isErrorRate2338()){
			updateWithErrorRate2338();
		}
		else{
			updateWithIters2338(iter);
		}
	}

	public static void updateWithErrorRate2338(){
		double[] pA,pH,a=authValues,h = hubValues;
		double errVal = Math.pow(10, no_of_iterations);
		int i =0;
		do{
			pA = a;
			pH = h;
			a = matMul2338(h, aT);
			h = matMul2338(a,adjMat);
			a = normalize2338(a);
			h = normalize2338(h);
			if(!largeGraph)
				printAllIters2338("Iter", ++i, a, h);
		}while(!checkConvergence2338(a, h, pA, pH,errVal));
		if(largeGraph){
			printAllIters2338("Iter", ++i, a, h);
		}
	}
	
	public static boolean checkConvergence2338(double[] a, double[] h, double[] pA, double[] pH, double err){
		boolean check = true;
		boolean diff[] = new boolean[size];
		for(Integer i=0; i< size;i++){
			diff[i] = Math.abs(a[i] - pA[i]) < err && Math.abs(h[i] - pH[i]) < err ? true : false;
		}
		
		for(boolean b : diff){
			check = b && check;
		}
		
		return check;
	}
	
	public static void updateWithIters2338(int iter){
		double[] a,h=hubValues;
		int i=0;
		while(i<iter){
			a = matMul2338(h,aT);
			h = matMul2338(a,adjMat);
			a = normalize2338(a);
			h = normalize2338(h);
			i++;
			printAllIters2338("Iter", i, a, h);
		}
	}

	public static double[] normalize2338(double[] v){
		double[] res = new double[size];
		double sum = 0;
		for(Integer i =0;i<size;i++){
			sum+=Math.pow(v[i], 2.0);
		}
		for(Integer i =0; i<size;i++){
			res[i] = v[i] / Math.sqrt(sum); 
		}
		return res;
	}

	public static double[] matMul2338(double[] v, int[][] a){
		double[] res = new double[size];
		for(Integer i =0; i< size;i++){
			for(Integer j=0;j<size;j++){
				res[i] = res[i] + (a[i][j] * v[j]);
			}
		}
		return res;
	}

	public static int[][] transpose2338(){
		int[][] gTrans = new int[size][size];
		for(Integer i = 0; i < size; i++)
		{
			for(Integer j = 0; j < size; j++)
			{
				gTrans[i][j] = adjMat[j][i];
			}
		}
		return gTrans;
	}

	public static void printAllIters2338(String cased, int i,double[] a, double[] b){
		System.out.print(cased+" : " + (i) + " ");
		if((cased == "Base") || !largeGraph){
			for(Integer j = 0 ; j< size;j++){
				System.out.print("A/H["+j+"] "+dFormat.format(a[j])+"/"+dFormat.format(b[j])+" ");
			}
		}
		if(largeGraph && !(cased == "Base")){
			for(Integer j = 0 ; j< size;j++){
				System.out.println("A/H["+j+"] "+dFormat.format(a[j])+"/"+dFormat.format(b[j])+" ");
			}
		}
		System.out.println("");
	}


	public static boolean isErrorRate2338() {
		return errorRate;
	}


	public static void setErrorRate2338(boolean errorRate) {
		hits2338.errorRate = errorRate;
	}
}