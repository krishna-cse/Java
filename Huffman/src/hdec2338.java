import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class hdec2338 {

	@SuppressWarnings({ "resource", "unchecked"})
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String filename = args[0];
		String fileOutName = filename.substring(0, filename.indexOf("."));
		FileInputStream fIS = new FileInputStream(fileOutName + ".ser");
		ObjectInputStream oOS = new ObjectInputStream(fIS);
		ArrayList<Node> huffmanTable = null;
		huffmanTable = (ArrayList<Node>)oOS.readObject();
		fIS = new FileInputStream(filename);
		DataInputStream dIS = new DataInputStream(fIS);
		FileOutputStream fOS = new FileOutputStream(fileOutName+"1."+huffmanTable.get(0).getExtension());
		Node curr = huffmanTable.get(0);
		while(dIS.available()>0){
			int value = dIS.read();
			System.out.println(value);
			if(value==1){
				if(curr.getRight()!=null){
					curr = curr.getRight();
				}
				else{
					fOS.write(curr.getCh());
					curr = huffmanTable.get(0);
				}
			}
			if(value==0){
				if(curr.getLeft()!=null){
					curr = curr.getLeft();
				}
				else{
					fOS.write(curr.getCh());
					curr = huffmanTable.get(0);
				}
			}
		}
	}

}
