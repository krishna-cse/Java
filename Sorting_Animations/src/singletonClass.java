import java.util.Vector;

public class singletonClass {
	public static singletonClass object = new singletonClass();
	private Vector<Integer> ISinstance;
	private long timeIS;
	private Vector<Integer> SSinstance;
	private long timeSS;
	private Vector<Integer> MSinstance;
	private long timeQS;
	
	public void setISInstance(Vector<Integer> array){
		ISinstance = array;
	}
	
	public Vector<Integer> getISInstance(){
		return ISinstance;
	}
	
	public void setMSInstance(Vector<Integer> array){
		MSinstance = array;
	}
	
	public Vector<Integer> getMSInstance(){
		return MSinstance;
	}
	
	public void setSSInstance(Vector<Integer> array){
		SSinstance = array;
	}
	
	public Vector<Integer> getSSInstance(){
		return SSinstance;
	}

	public long getTimeIS() {
		return timeIS;
	}

	public void setTimeIS(long timeIS) {
		this.timeIS = timeIS;
	}

	public long getTimeSS() {
		return timeSS;
	}

	public void setTimeSS(long timeSS) {
		this.timeSS = timeSS;
	}

	public long getTimeQS() {
		return timeQS;
	}

	public void setTimeQS(long timeQS) {
		this.timeQS = timeQS;
	}
}
