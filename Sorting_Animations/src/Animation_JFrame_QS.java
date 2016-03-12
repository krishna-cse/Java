import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Animation_JFrame_QS extends JComponent {
	public void paint(Graphics g){
		{
			Vector<Integer> arrayForDraw = singletonClass.object.getMSInstance();
			int head = 0;
			while(head<arrayForDraw.size()){
				g.setColor(Color.gray);
				g.drawLine(0, 3*head+1, arrayForDraw.get(head)*3, 3*head+1);	
				head++;
			}
			g.drawString(""+singletonClass.object.getTimeQS()/1000+" secs", 3*head, 3*head+10);
		}
	}
}
