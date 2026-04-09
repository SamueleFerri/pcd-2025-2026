package pcd.sketch01;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class ViewFrame extends JFrame {
    
    private VisualiserPanel panel;
    private ViewModel model;
    private RenderSynch sync;
    
    public ViewFrame(ViewModel model, int w, int h){
    	this.model = model;
    	this.sync = new RenderSynch();
    	setTitle("Sketch 03");
        setSize(w,h + 25);
        setResizable(false);
        panel = new VisualiserPanel(w,h);
        getContentPane().add(panel);
        addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
    }
     
    public void render(){
		long nf = sync.nextFrameToRender();
        panel.repaint();
		try {
			sync.waitForFrameRendered(nf);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
    }
        
    public class VisualiserPanel extends JPanel {
        private int ox;
        private int oy;
        private int delta;
        
        public VisualiserPanel(int w, int h){
            setSize(w,h + 25);
            ox = w/2;
            oy = h/2;
            delta = Math.min(ox, oy);
        }

        public void paint(Graphics g){
    		Graphics2D g2 = (Graphics2D) g;
    		
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    		          RenderingHints.VALUE_ANTIALIAS_ON);
    		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
    		          RenderingHints.VALUE_RENDER_QUALITY);
    		g2.clearRect(0,0,this.getWidth(),this.getHeight());
            
    		g2.setColor(Color.LIGHT_GRAY);
		    g2.setStroke(new BasicStroke(1));
    		g2.drawLine(ox,0,ox,oy*2);
    		g2.drawLine(0,oy,ox*2,oy);
    		g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1));
			for (var b: model.getBalls()) {
				var p = b.pos();
				int x0 = (int)(ox + p.x()*delta);
				int y0 = (int)(oy - p.y()*delta);
				int radiusX = (int)(b.radius()*delta);
				int radiusY = (int)(b.radius()*delta);
				g2.drawOval(x0 - radiusX,y0 - radiusY,radiusX*2,radiusY*2);
			}

			g2.setStroke(new BasicStroke(3));
			var pb = model.getPlayerBall();
			if (pb != null) {
				var p1 = pb.pos();
				int x0 = (int)(ox + p1.x()*delta);
				int y0 = (int)(oy - p1.y()*delta);
				int radiusX = (int)(pb.radius()*delta);
				int radiusY = (int)(pb.radius()*delta);
				g2.drawOval(x0 - radiusX,y0 - radiusY,radiusX*2,radiusY*2);
			}

			g2.setColor(Color.DARK_GRAY);
			if (model.getHoles() != null) {
				for (var h: model.getHoles()) {
					var p = h.pos();
					int x0 = (int)(ox + p.x()*delta);
					int y0 = (int)(oy - p.y()*delta);
					int radiusX = (int)(h.radius()*delta);
					int radiusY = (int)(h.radius()*delta);
					g2.fillOval(x0 - radiusX, y0 - radiusY, radiusX*2, radiusY*2);
				}
			}

			var bb = model.getBotBall();
			if (bb != null) {
				g2.setColor(Color.RED);
				g2.setStroke(new BasicStroke(3));
				var p1 = bb.pos();
				int x0 = (int)(ox + p1.x()*delta);
				int y0 = (int)(oy - p1.y()*delta);
				int radiusX = (int)(bb.radius()*delta);
				int radiusY = (int)(bb.radius()*delta);
				g2.drawOval(x0 - radiusX, y0 - radiusY, radiusX*2, radiusY*2);
			}

			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1));
			int panelWidth = this.getWidth();
			int panelHeight = this.getHeight();
			g2.drawString("Num small balls: " + model.getBalls().size(), panelWidth - 150, panelHeight - 40);
			g2.drawString("Frame per sec: " + model.getFramePerSec(), panelWidth - 150, panelHeight - 20);
			g2.drawString("Score: " + model.getScore(), panelWidth - 150, panelHeight - 60);

			GameState currentState = model.getState();
			if (currentState != GameState.PLAYING) {
				g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
				String msg = "";

				if (currentState == GameState.PLAYER_WON) {
					g2.setColor(Color.GREEN);
					msg = "PLAYER WON!";
				} else if (currentState == GameState.BOT_WON) {
					g2.setColor(Color.RED);
					msg = "BOT WON!";
				} else if (currentState == GameState.TIE) {
					g2.setColor(Color.BLUE);
					msg = "TIE!";
				}
				//center test
				java.awt.FontMetrics fm = g2.getFontMetrics();
				int textWidth = fm.stringWidth(msg);
				int textHeight = fm.getAscent();
				g2.drawString(msg, (panelWidth - textWidth) / 2, (panelHeight + textHeight) / 2);
			}

			sync.notifyFrameRendered();
        }
        
    }
}
