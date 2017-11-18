/* DUBAGUNTA SAI KRISHNA cs610 PrP 2338 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Vector;

public class henc2338 {

	static Vector<dictionary> mappingTable = new Vector<dictionary>();

	public static void main(String[] args) throws Exception {
		String filename = args[0];
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		ArrayList<Integer> freq = new ArrayList<Integer>();
		FileInputStream fIS = new FileInputStream(filename);
		DataInputStream dIS = new DataInputStream(fIS);
		String fileOutName = filename.substring(0, filename.indexOf("."));
		String extension = filename.substring(filename.indexOf(".")+1,filename.length());
		FileOutputStream fOS = new FileOutputStream(fileOutName+".huf");
		ArrayList<Node> huffmanTable = null;
		huffmanTable = buildListWithFile2338(dIS, bytes,freq,huffmanTable);
		huffmanTable = buildTree2338(huffmanTable);
//		System.out.println(huffmanTable.get(0).getFreq());
		buildPrefixCodes2338(huffmanTable);
		huffmanTable.get(0).setExtension(extension);
		dIS = new DataInputStream(new FileInputStream(filename));
		writeToFile(fOS,mappingTable,dIS);
		fOS = new FileOutputStream(fileOutName+".ser");
		ObjectOutputStream oOS = new ObjectOutputStream(fOS);
		oOS.writeObject(huffmanTable);
		oOS.close();
		fIS.close();
		dIS.close();
		fOS.close();
	}

	public static ArrayList<Node> buildListWithFile2338(DataInputStream d, ArrayList<Byte> bytes, ArrayList<Integer> freq, ArrayList<Node> hT) throws IOException{
		hT = new ArrayList<Node>();
		Byte b;
		while(d.available()>0){
			b = d.readByte();
//			System.out.println(""+(char)b.byteValue());
			if(bytes.contains(b.byteValue())){
				Integer index = bytes.indexOf(b.byteValue());
				Integer freqAtIndex =freq.get(index);
				freq.set(index, freqAtIndex+1);
			}
			else{
				bytes.add(b.byteValue());
				freq.add(1);
			}
		}
		for(Integer i =0;i<bytes.size();i++){
			Node n = new Node(bytes.get(i),freq.get(i));
			hT.add(n);
		}
		return hT;
	}

	public static ArrayList<Node> buildTree2338(ArrayList<Node> hT){
		while(hT.size()>1){
			hT=heapify2338(hT);
			Node one = hT.get(0);
			hT.remove(0);
			hT = heapify2338(hT);
			Node two = hT.get(0);
			hT.remove(0);
			Node res = makeParent(one,two);
			hT.add(res);
		}
		return hT;
	}

	public static ArrayList<Node> heapify2338(ArrayList<Node> hT){
		for(int i=0;i< (hT.size() - 1)/2;i++){
				if((hT.get(i).getFreq()>hT.get(2*i+1).getFreq())){
					hT = swapNodes2338(i,2*i+1,hT);
				}
				if(hT.get(i).getFreq()>hT.get(2*i+2).getFreq()){
					hT = swapNodes2338(i,2*i+2,hT);
				}
			else
				break;
		}
		return hT;
	}

	public static ArrayList<Node> swapNodes2338(int one,int two, ArrayList<Node> hT){
		Node oneN = hT.get(one);
		Node twoN = hT.get(two);
		hT.remove(one);
		hT.remove(two-1);
		hT.add(one, twoN);
		hT.add(two, oneN);
		return hT;
	}

	public static Node makeParent(Node one, Node two){
		Node p = new Node();
		p.setFreq((one.getFreq() + two.getFreq()));
		p.setLeft(one);
		p.setRight(two);
		return p;
	}

	public static void buildPrefixCodes2338(ArrayList<Node> nodes){
		Node head = nodes.get(0);
		String s= "";
		setCodes(head.getLeft(),s,1);
		setCodes(head.getRight(),s,1);
	}


	public static void setCodes(Node curr,String s, int index){
		if(curr.getLeft()==null){
			BitSet b = new BitSet();
			for(int i=0;i<s.length();i++){
				if(s.charAt(i)=='0')
					b.clear(i);
				else
					b.set(i);
			}
			curr.setBits(b);
			dictionary d = new dictionary();
			BitSet bs = new BitSet();
			d.setCh(curr.getCh());
			for(int j =0;j<b.length();j++){
//				System.out.println(bs.get(j));
				bs.set(j,b.get(j));
			}
//			System.out.println();
			d.setB(bs);
			mappingTable.add(d);
		}
		else{
			s=s+"0";
			setCodes(curr.getLeft(),s,index+1);
		}
		if(curr.getRight()==null){
			BitSet b = new BitSet();
			for(int i=0;i<s.length();i++){
				if(s.charAt(i)=='0')
					b.clear(i);
				else
					b.set(i);
			}
			curr.setBits(b);
			curr.setBits(b);
			dictionary d = new dictionary();
			d.setCh(curr.getCh());
			BitSet bs = new BitSet();
			for(int j =0;j<b.length();j++){
//				System.out.println(bs.get(j));
				bs=b;
			}
			d.setB(bs);
			mappingTable.add(d);
		}
		else{
			s=s+"1";
			setCodes(curr.getRight(),s,index+1);
		}
	}

	public static void writeToFile(FileOutputStream fO, Vector<dictionary> dict, DataInputStream fI) throws Exception{
		byte b;
		DataOutputStream dO = new DataOutputStream(fO);
		while(fI.available()>0){
			b = fI.readByte();
			System.out.println(b);
			for(dictionary d : dict){
				if(d.getCh() == b){
					BitSet bs = d.getB();
					for(int i = 0; i<bs.length();i++){
						if(bs.get(i)){
							dO.writeByte(0);
						}
						else{
							dO.writeByte(1);
						}
					}
					break;
				}
			}
		}
		dO.flush();
		dO.close();
	}
}

class dictionary{
	private byte ch;
	private BitSet b;

	public byte getCh() {
		return ch;
	}
	public void setCh(byte ch) {
		this.ch = ch;
	}

	public BitSet getB() {
		return b;
	}
	public void setB(BitSet b) {
		this.b = b;
	}

}

class Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Node right = null;
	private Node left = null;
	private byte ch;
	private Integer freq=0;
	private BitSet bits;
	private String extension;

	public Node(){
		bits = new BitSet();
	}

	public Node(Byte ch, Integer freq){
		bits = new BitSet();
		this.ch = ch;
		this.freq = freq;
	}

	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public byte getCh() {
		return ch;
	}
	public void setCh(byte ch) {
		this.ch = ch;
	}
	public Integer getFreq() {
		return freq;
	}
	public void setFreq(Integer freq) {
		this.freq = freq;
	}
//	public Boolean getPrefixBit() {
//		return prefixBit;
//	}
//	public void setPrefixBit(Boolean prefixBit) {
//		this.prefixBit = prefixBit;
//	}
	public BitSet getBits() {
		return bits;
	}
	public void setBits(BitSet bits) {
		this.bits = bits;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}	
