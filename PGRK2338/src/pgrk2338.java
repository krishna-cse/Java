/* DUBAGUNTA SAI KRISHNA cs610 PrP 2338 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class pgrk2338 {

	/**
	 * @param args - iterations initalValueOption(1, 0, -1, -2) filename
	 */
	static DecimalFormat dFormat = new DecimalFormat("#.0000000");
	static double d = 0.85;
	static int no_of_iterations;
	static Integer size;
	static BufferedReader bReader;
	static Boolean errorate = false;
	static boolean largeGraph = false;
	
	public static void main(String[] args) throws Exception {
		ArrayList<Node2338> Node2338s;
		no_of_iterations = Integer.parseInt(args[0]);
		size = Integer.parseInt(args[1]);
		String filename = args[2];
		bReader = new BufferedReader(new FileReader(filename));
		String line = bReader.readLine();
		size = Integer.parseInt(line.split(" ")[0]);
		Node2338s = new ArrayList<Node2338>(size);
		double initVal = getInitialValue2338(Integer.parseInt(args[1]));
		if (no_of_iterations<0) {
			errorate = true;
		}
		else if(no_of_iterations==0){
			errorate = true;
			no_of_iterations = -5;
		}
		Node2338s = intializeNode2338s2338(initVal,Node2338s);
		Node2338s=buildGraph2338(Node2338s);
		printAllIters2338("Base",Node2338s,0);
		update2338PageRanks2338(Node2338s);
	}
	
	public static double getInitialValue2338(int init){
		double initialVal = 0.0;
		if(size > 10){
			initialVal = 1/((double)size);
			no_of_iterations = 0;
			largeGraph = true;
		}
		else{
			switch(init){
			case 0:
				initialVal = 0.0;
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
		return initialVal;
	}

	public static ArrayList<Node2338> intializeNode2338s2338(double initVal,ArrayList<Node2338> Node2338s){
		Node2338s = new ArrayList<Node2338>(size);
		for(int i =0;i<size;i++){
			Node2338s.add(new Node2338(initVal));
		}
		return Node2338s;
	}
	
	public static ArrayList<Node2338> buildGraph2338(ArrayList<Node2338> Node2338s) throws Exception{
		String line = "";
		while((line = bReader.readLine())!=null){
			Integer one,two;
			one = Integer.parseInt(line.split(" ")[0]);
			two = Integer.parseInt(line.split(" ")[1]);
			Node2338s.get(two).putNode2338Inc(one);
			Node2338s.get(one).putNode2338Out(two);
		}
		return Node2338s;
	}
	
	public static void update2338PageRanks2338(ArrayList<Node2338> Node2338s){
		if(errorate){
			update2338PGRKerrorRate2338(Node2338s);
		}
		else{
			update2338PGRKIters2338(Node2338s);
		}
	}
	
	public static void update2338PGRKerrorRate2338(ArrayList<Node2338> Node2338s){
		double sum=0.0;
		int i = 0;
		double restCals = (1-d)/(double)size;
		ArrayList<Node2338> prev_Node2338s = new ArrayList<Node2338>(size);
		double errVal = Math.pow(10.0,no_of_iterations);
		int precision = (int) (1/errVal);
		String format = (""+precision).substring(1);
		DecimalFormat dF = new DecimalFormat("#."+format);
		do{
			prev_Node2338s = getValuesFrom2338(Node2338s);
			Node2338s = update2338(sum,restCals,Node2338s);
			if(!largeGraph)
				printAllIters2338("Iter", Node2338s, i+1);
			i++;
		}while(!checkConvergence2338(prev_Node2338s,errVal,Node2338s,dF));
		if(largeGraph){
			printAllIters2338("Iter",Node2338s,i+1);
		}
	}
	
	public static ArrayList<Node2338> getValuesFrom2338(ArrayList<Node2338> Node2338s){
		ArrayList<Node2338> x = new ArrayList<Node2338>(size);
		for(int i = 0 ; i<Node2338s.size();i++){
			x.add(new Node2338(Node2338s.get(i).getPgrk2338()));
		}
		return x;
	}
	
	public static Boolean checkConvergence2338(ArrayList<Node2338> prev, double errVal,ArrayList<Node2338> Node2338s, DecimalFormat dF){
		boolean check = true;
		boolean[] diff = new boolean[size];
		for(int i =0;i<size;i++){		
			diff[i] = Math.abs(Node2338s.get(i).getPgrk2338() - prev.get(i).getPgrk2338()) < errVal ? true : false;
		}
		
		for( boolean b: diff){
			check = check && b;
		}
		return check;
	}
	
	public static void update2338PGRKIters2338(ArrayList<Node2338> Node2338s){
		double sum=0.0;
		double restCals = (1-d)/(double)size;
		int i =0;
		for(i=0;i<no_of_iterations;i++){
			Node2338s = update2338(sum,restCals,Node2338s);
			printAllIters2338("Iter",Node2338s,i+1);	
		}
	}
	
	public static ArrayList<Node2338> update2338(double sum, double restCals, ArrayList<Node2338> Node2338s){
		ArrayList<Node2338> t1 = new ArrayList<Node2338>(size);
		for(Node2338 Node2338 : Node2338s){
			sum = 0.0;
			for( Integer inc : Node2338.incoming){
				sum += ((Node2338s.get(inc).getPgrk2338()) / (double)(Node2338s.get(inc).outgoing.size()));
			}
			double fin_Cal = restCals + (d*sum);
			t1.add(new Node2338(fin_Cal));
		}
		for(Node2338 n : t1){
			Node2338s.get(t1.indexOf(n)).setNode2338(n.getPgrk2338()); 
		}
		return Node2338s;
	}
	
	public static void printAllIters2338(String cased, ArrayList<Node2338> p,int iter){
		System.out.print(cased + " : " + iter);
		if((cased == "Base") || !largeGraph){
			for(Node2338 Node2338 : p){
				System.out.printf(" P["+p.indexOf(Node2338)+"] = %.7f",+(Node2338.getPgrk2338()));
			}
		}
		if(largeGraph && !(cased == "Base")){
			for(Node2338 Node2338 : p){
				System.out.printf(" P["+p.indexOf(Node2338)+"] = %.7f",+(Node2338.getPgrk2338()));
				System.out.println("");
			}
		}
		System.out.println("");
	}
	
}

class Node2338 {
	ArrayList<Integer> incoming;
	ArrayList<Integer> outgoing;
	double pgrk=0.0;
	
	public Node2338(double initialValue){
		incoming = new ArrayList<Integer>(1);
		outgoing = new ArrayList<Integer>(1);
		pgrk = initialValue;
	}
	
	public void setNode2338(double initialValue){
		pgrk = initialValue;
	}
	
	public void putNode2338Inc(Integer p){
		if(!incoming.contains(p)){
			incoming.add(p);
		}
	}
	public void putNode2338Out(Integer p){
		if(!outgoing.contains(p)){
			outgoing.add(p);
		}
	}
	
	public double getPgrk2338(){
		return pgrk;
	}
	
}
