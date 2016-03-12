import java.util.Vector;

public class sorting extends AllAlgorithms implements Cloneable{
	/**
	 * 
	 */

	public static void main(String[] args) throws Exception{
		Vector<Integer> array_unsorted = scramble();
		
		Thread SSThread = new Thread(new ShellSortThread(array_unsorted));
		Thread ISThread = new Thread(new InsertionSortThread(array_unsorted));
		Thread QSThread = new Thread(new QuickSortThread(array_unsorted));
		SSThread.start();
		ISThread.start();
		QSThread.start();
	}
}

@SuppressWarnings("unchecked")
class ShellSortThread extends AllAlgorithms implements Runnable{

	Vector<Integer> array_unsorted;
	public ShellSortThread(Vector<Integer> array){
		array_unsorted = array;
	}
	
	@Override
	public void run() {
		try {
			singletonClass.object.setSSInstance((Vector<Integer>) array_unsorted.clone());
			shellsort();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@SuppressWarnings("unchecked")
class QuickSortThread extends AllAlgorithms implements Runnable{
	Vector<Integer> array_unsorted;
	public QuickSortThread(Vector<Integer> array){
		array_unsorted = array;
	}
	@Override
	public void run() {
		try {
			singletonClass.object.setMSInstance((Vector<Integer>) array_unsorted.clone());
			quicksort();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@SuppressWarnings("unchecked")
class InsertionSortThread extends AllAlgorithms implements Runnable{

	Vector<Integer> array_unsorted;
	public InsertionSortThread(Vector<Integer> array){
		array_unsorted = array;
	}
	
	@Override
	public void run() {
		try {
			singletonClass.object.setISInstance((Vector<Integer>) array_unsorted.clone());
			insertionSort();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}