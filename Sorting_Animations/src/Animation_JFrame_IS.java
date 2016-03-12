import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JComponent;

public class Animation_JFrame_IS extends JComponent {
	
	private static final long serialVersionUID = 1L;

	public void init(){
		
	}
	
	public void paint(Graphics g){
		{
			Vector<Integer> arrayForDraw = singletonClass.object.getISInstance();
			int head = 0;
			while(head<arrayForDraw.size()){
				g.setColor(Color.gray);
				g.drawLine(0, 3*head+1, arrayForDraw.get(head)*3, 3*head+1);
				head++;
			}
			g.drawString(""+singletonClass.object.getTimeIS()/1000+" secs", 3*head, 3*head+10);
		}
	}
}
