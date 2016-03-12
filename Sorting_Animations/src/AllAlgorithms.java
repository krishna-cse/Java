import java.awt.Label;
import java.util.Vector;
import javax.swing.JFrame;

public class AllAlgorithms  {
	static JFrame SSWindow, ISWindow, QSWindow;
	
	public static Vector<Integer> scramble(){
		Vector<Integer> array = new Vector<Integer>(50,1);
		
		for(int i = 50; i>0;){
			int temp = (int)(100*Math.random());
			array.add(temp);
			--i;
		}
		
		return array;
	}
	
	public static void quicksort() throws Exception{
		QSWindow = setJframe(QSWindow, "Quick Sort", 600, 30);
		QSWindow.add(new Animation_JFrame_QS());
		QSWindow.setVisible(true);
		quickSort(0,singletonClass.object.getMSInstance().size()-1,QSWindow);
	}
	
	private static void quickSort(int left, int right, JFrame QS) throws InterruptedException{
		long start = System.currentTimeMillis();
		if(left >= right)
			return;
	
		int pivot = singletonClass.object.getMSInstance().get(right);
		int partition = partition(left, right, pivot,QS);

		quickSort(0, partition-1,QS);
		Thread.sleep(5);
		QS.repaint();
		quickSort(partition+1, right,QS);
		Thread.sleep(5);
		QS.repaint();
		long stop = System.currentTimeMillis();
		singletonClass.object.setTimeQS(stop-start);
	}
	
	private static int partition(int left,int right,int pivot, JFrame QS) throws InterruptedException{
		Vector<Integer> a = singletonClass.object.getMSInstance();
		int leftCursor = left-1;
		int rightCursor = right;
		while(leftCursor < rightCursor){
                while(a.get(++leftCursor)< pivot);
                while(rightCursor > 0 && a.get(--rightCursor) > pivot);
			if(leftCursor >= rightCursor){
				break;
			}else{
				swap(leftCursor, rightCursor,QS);
				Thread.sleep(5);
				QS.repaint();
			}
		}
		swap(leftCursor, right,QS);
		Thread.sleep(5);
		QS.repaint();
		return leftCursor;
	}
	
	public static void swap(int left,int right, JFrame QS) throws InterruptedException{
		Vector<Integer> a = singletonClass.object.getMSInstance();
		int temp = a.get(left);
		a.add(left, a.get(right));
		a.remove(left+1);
		a.add(right,temp);
		a.remove(right+1);
		Thread.sleep(5);
		QS.repaint();
	}
	
	public static void shellsort() throws Exception {
		long start = System.currentTimeMillis();
		SSWindow=setJframe(SSWindow,"Shell Sort",330,330);
		SSWindow.getContentPane().add(new Animation_JFrame_SS());
		SSWindow.setVisible(true);
	    int h=1;
	    Vector<Integer> arraySS=singletonClass.object.getSSInstance();
	    while(h<= arraySS.size()/3){
	        h = 3*h + 1;   
	    }
	    while(h>0){ 
	       for(int i=0; i<arraySS.size(); i++){
	    	   
	            int temp = arraySS.get(i); 
	            int j;              

	            for(j=i; j>h-1 && arraySS.get(j-h) >= temp; j=j-h){
	            	arraySS.add(j, arraySS.get(j-h));
	            	arraySS.remove(j+1);
	            }
	            arraySS.add(j, temp);
	            arraySS.remove(j+1);
	            Thread.sleep(10);
				SSWindow.repaint();
	        }
	        h = (h-1)/3;
	        Thread.sleep(10);
			SSWindow.repaint();
	    }
	    long stop = System.currentTimeMillis();
	    singletonClass.object.setTimeSS(stop-start);
	}

	public static  JFrame setJframe(JFrame window, String name, int x, int y) throws Exception, Exception{
		window = new JFrame(name);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(x,y,500,500);
		return window;
	}	

	
	public static void insertionSort() throws Exception{
		long start = System.currentTimeMillis();
		ISWindow = setJframe(ISWindow,"Insertion Sort", 30,30);
		ISWindow.getContentPane().add(new Animation_JFrame_IS());
		ISWindow.setVisible(true);
		Vector<Integer> arrayIS = singletonClass.object.getISInstance();
		for(int i=0;i< arrayIS.size();i++){
			for(int j=i+1;j<arrayIS.size();j++){
				if(arrayIS.get(i)>arrayIS.get(j)){
					int temp = arrayIS.get(i);
					arrayIS.add(i,arrayIS.get(j));
					arrayIS.remove(i+1);
					arrayIS.add(j,temp);
					arrayIS.remove(j+1);
				}
				Thread.sleep(10);
				ISWindow.repaint();
			}
			Thread.sleep(10);
			ISWindow.repaint();
		}
		long stop = System.currentTimeMillis();
		singletonClass.object.setTimeIS(stop-start);
	}
}
