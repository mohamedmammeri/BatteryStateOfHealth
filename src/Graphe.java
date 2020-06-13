import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Graphe extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x,y,ax,by;
	static ArrayList<GrapheModele> gModele;
	public Graphe(ArrayList<GrapheModele> gmodele) {
		gModele=new ArrayList<>();
		Graphe.gModele=gmodele;
		setTitle("test Graphe");
		setSize(1300,600);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		x=70;
		y=500;
		
		
	}
	public void paint(Graphics g) {
		super.paint(g); 
		if(gModele.get(gModele.size()-1).getDays()<=2000) {
	/// axe des x
		g.drawLine(70, 70, 70, 500);	
		g.drawString("SOH(%)", 60, 60);
		g.drawString("100 -", 45,100 );
		g.drawString("75 -", 45,200 );
		g.drawString("50 -", 45,300 );
		g.drawString("25 -", 45,400 );
		g.drawString("0 -", 45,500 );
	/// axe des y
		
		g.drawLine(70, 500, 1260, 500);
		g.drawString("temps(Jours)", 630, 550);
		g.drawString("250", 157, 520);
		g.drawString("500", 315, 520);
		g.drawString("750", 472, 520);
		g.drawString("1000", 630, 520);
		g.drawString("1500", 945, 520);
		g.drawString("2000", 1260, 520);
		
		
		
		g.setColor(Color.red);
		
		for(int i=0;i<gModele.size();i++) {
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		g.fillOval((int) (70+(gModele.get(i).getDays()*0.63)),(int) (500-(gModele.get(i).getSOH()*100*4)), 3, 3);
		}
		
		}else if(gModele.get(gModele.size()-1).getDays()<=4000) {
			
				/// axe des x
					g.drawLine(70, 70, 70, 500);	
					g.drawString("SOH(%)", 60, 60);
					g.drawString("100 -", 45,100 );
					g.drawString("75 -", 45,200 );
					g.drawString("50 -", 45,300 );
					g.drawString("25 -", 45,400 );
					g.drawString("0 -", 45,500 );
				/// axe des y
					
					g.drawLine(70, 500, 1260, 500);
					g.drawString("temps(Jours)", 630, 550);
					g.drawString("500", 157, 520);
					g.drawString("1000", 315, 520);
					g.drawString("1500", 472, 520);
					g.drawString("2000", 630, 520);
					g.drawString("3000", 945, 520);
					g.drawString("4000", 1260, 520);
					
					g.setColor(Color.red);
					
					for(int i=0;i<gModele.size();i++) {
						try {
							Thread.sleep(3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					g.fillOval((int) (70+(gModele.get(i).getDays()*0.31)),(int) (500-(gModele.get(i).getSOH()*100*4)), 3, 3);
					}
					
			}else {
				/// axe des x
				g.drawLine(70, 70, 70, 500);	
				g.drawString("SOH(%)", 60, 60);
				g.drawString("100 -", 45,100 );
				g.drawString("75 -", 45,200 );
				g.drawString("50 -", 45,300 );
				g.drawString("25 -", 45,400 );
				g.drawString("0 -", 45,500 );
			/// axe des y
				
				g.drawLine(70, 500, 1260, 500);
				g.drawString("temps(Jours)", 630, 550);
				g.drawString("1000", 210, 520);
				g.drawString("2000", 420, 520);
				g.drawString("3000", 630, 520);
				g.drawString("4000", 840, 520);
				g.drawString("5000", 1050, 520);
				g.drawString("6000", 1260, 520);
				
				g.setColor(Color.red);
				
				for(int i=0;i<gModele.size();i++) {
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				g.fillOval((int) (70+(gModele.get(i).getDays()*0.21)),(int) (500-(gModele.get(i).getSOH()*100*4)), 3, 3);
				}
			}
		float stat =(gModele.get(gModele.size()-1).getDays()/365);
		int ans= (int) stat;
		float mois=(stat-ans)*12;
		JOptionPane.showMessageDialog(null, 
			    "La duréee de vie estimer est : "+ans+" ans et "+mois+" Mois");
			}
		
	public static void main(String[] arg) {
		new Graphe(gModele);
	}
	

}
