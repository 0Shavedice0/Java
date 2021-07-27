import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class PaintPanel extends JPanel{
	
	public static final int DRAW_LINE=0;
	public static final int DRAW_RECT=1;
	public static final int DRAW_OVAL=2;
	int sx,sy,ex,ey;
	boolean temp=false;
	
	int type=PaintPanel.DRAW_LINE;
	
	public void setDrawType(int type) {
		this.type=type;
	}
	
	public PaintPanel() {
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				ex=e.getX();
				ey=e.getY();
				temp=true;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				sx=e.getX();
				sy=e.getY();
				ex=e.getX();
				ey=e.getY();
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
	}

	@Override
	protected void paintComponent(Graphics e) {
		// TODO Auto-generated method stub
		super.paintComponent(e);
		if(temp==true) {
			switch(type) {
			case DRAW_LINE:
				
				e.drawLine(sx, sy, ex, ey);
			break;
			case DRAW_RECT:
				if(ex>sx&&ey<sy)e.drawRect(sx, ey, ex-sx, sy-ey);
				else if(ex<sx&&ey<sy)e.drawRect(ex, ey, sx-ex, sy-ey);	
				else if(ex<sx&&ey>sy)e.drawRect(ex, sy, sx-ex, ey-sy);
				else if(ex>sx&&ey>sy)e.drawRect(sx, sy, ex-sx, ey-sy);
			break;
			case DRAW_OVAL:
				if(ex>sx&&ey<sy)e.drawOval(sx, ey, ex-sx, sy-ey);
				else if(ex<sx&&ey<sy)e.drawOval(ex, ey, sx-ex, sy-ey);	
				else if(ex<sx&&ey>sy)e.drawOval(ex, sy, sx-ex, ey-sy);
				else if(ex>sx&&ey>sy)e.drawOval(sx, sy, ex-sx, ey-sy);
			break;
			}
		}
		repaint();
	}
	
}
