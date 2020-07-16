

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Graphe extends JFrame {

    private static final long serialVersionUID = 1L;
    int x, y, ax, by;
    static ArrayList<GrapheModele> gModele;
    static String mod;

    public Graphe(ArrayList<GrapheModele> gmodele, String mode) {
        gModele = new ArrayList<>();
        this.mod = mode;
        Graphe.gModele = gmodele;
        setTitle("Graphe");
        setSize(1300, 600);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        x = 70;
        y = 500;


    }

    public void paint(Graphics g) {
        super.paint(g);
        if (gModele.get(gModele.size() - 1).getDays() <= 2000) {
        	
        	smallFrame(g);
        	
            for (int i = 0; i < gModele.size(); i++) {
                if (mod.equals("capacity")) {
                    g.fillOval((int) (70 + (gModele.get(i).getDays() * 0.63)), (int) (500 - ((gModele.get(i).getCm() * 100) / (gModele.get(0).getCm()) * 1.6)), 3, 3);
                } else if (mod.equals("lifeTime")) {

                    g.fillOval((int) (70 + (gModele.get(i).getDays() * 0.63)), (int) (500 - (gModele.get(i).getSOH() * 100 * 4)), 3, 3);
                }
            
            }

        } else if (gModele.get(gModele.size() - 1).getDays() <= 4000) {
        	
        	largeFrame(g);

            for (int i = 0; i < gModele.size(); i++) {

                if (mod.equals("capacity")) {
                    g.fillOval((int) (70 + (gModele.get(i).getDays() * 0.31)), (int) (500 - ((gModele.get(i).getCm() * 100) / (gModele.get(0).getCm()) * 1.6)), 3, 3);
                } else if (mod.equals("lifeTime")) {

                    g.fillOval((int) (70 + (gModele.get(i).getDays() * 0.31)), (int) (500 - (gModele.get(i).getSOH() * 100 * 4)), 3, 3);
                }


            }

        } else {
        	
        	hugeFrame(g);

            for (int i = 0; i < gModele.size(); i++) {
                if (mod.equals("capacity")) {
                    g.fillOval((int) (70 + (gModele.get(i).getDays() * 0.21)), (int) (500 - ((gModele.get(i).getCm() * 100) / (gModele.get(0).getCm()) * 1.6)), 3, 3);

                } else if (mod.equals("lifeTime")) {
                    g.fillOval((int) (70 + (gModele.get(i).getDays() * 0.21)), (int) (500 - (gModele.get(i).getSOH() * 100 * 4)), 3, 3);
                }


            }

        }

        if (mod.equals("lifeTime")) {
            float stat = (float) ((float) (gModele.get(gModele.size() - 1).getDays()) / (float) 365);
            int ans = (int) stat;
            float mois = (stat - ans) * 12;
            String f = String.format("%.00f", mois);
            JOptionPane.showMessageDialog(null,
                    "La durée de vie estimée est : " + ans + " ans et " + f + " Mois");
        }

    }
    

/// Small frame methode
    public void smallFrame(Graphics g) {
    	/// axe des y

        g.drawLine(70, 500, 1260, 500);
        g.drawString("Time(Days)", 630, 550);
        g.drawString("250", 157, 520);
        g.drawString("500", 315, 520);
        g.drawString("750", 472, 520);
        g.drawString("1000", 630, 520);
        g.drawString("1500", 945, 520);
        g.drawString("2000", 1260, 520);

/// axe des x durée de vie
        if (mod.equals("lifeTime")) {
            g.drawLine(70, 70, 70, 500);
            g.drawString("SOH(%)", 60, 60);
            g.drawString("100 -", 45, 100);
            g.drawString("75 -", 45, 200);
            g.drawString("50 -", 45, 300);
            g.drawString("25 -", 45, 400);
            g.drawString("0 -", 45, 500);
            g.setColor(Color.red);
        } else if (mod.equals("capacity")) {
/// axe des x capacité
            g.drawLine(70, 70, 70, 500);
            g.drawString("Cm", 60, 60);
            g.drawString("250 -", 45, 100);
            g.drawString("150 -", 45, 260);
            g.drawString("100 -", 45, 340);
            g.drawString("50 -", 45, 420);
            g.drawString("0 -", 45, 500);
            g.setColor(Color.blue);
        }
    
    }
    
/// Large frame methode
    public void largeFrame(Graphics g) {
    	/// axe des y

        g.drawLine(70, 500, 1260, 500);
        g.drawString("Time(Days)", 630, 550);
        g.drawString("500", 157, 520);
        g.drawString("1000", 315, 520);
        g.drawString("1500", 472, 520);
        g.drawString("2000", 630, 520);
        g.drawString("3000", 945, 520);
        g.drawString("4000", 1260, 520);

        if (mod.equals("lifeTime")) {
/// axe des x for life time
            g.drawLine(70, 70, 70, 500);
            g.drawString("SOH(%)", 60, 60);
            g.drawString("100 -", 45, 100);
            g.drawString("75 -", 45, 200);
            g.drawString("50 -", 45, 300);
            g.drawString("25 -", 45, 400);
            g.drawString("0 -", 45, 500);
            g.setColor(Color.red);
        } else if (mod.equals("capacity")) {
/// axe des x for capacity
            g.drawLine(70, 70, 70, 500);
            g.drawString("Cm)", 60, 60);
            g.drawString("250 -", 45, 100);
            g.drawString("150 -", 45, 260);
            g.drawString("100 -", 45, 340);
            g.drawString("50 -", 45, 420);
            g.drawString("0 -", 45, 500);
            g.setColor(Color.blue);

    	
    }
    }

// Huge frame methode
        public void hugeFrame(Graphics g) {
/// axe des y

            g.drawLine(70, 500, 1260, 500);
            g.drawString("Time(Days)", 630, 550);
            g.drawString("1000", 210, 520);
            g.drawString("2000", 420, 520);
            g.drawString("3000", 630, 520);
            g.drawString("4000", 840, 520);
            g.drawString("5000", 1050, 520);
            g.drawString("6000", 1260, 520);
            if (mod.equals("lifeTime")) {
/// axe des x
                g.drawLine(70, 70, 70, 500);
                g.drawString("SOH(%)", 60, 60);
                g.drawString("100 -", 45, 100);
                g.drawString("75 -", 45, 200);
                g.drawString("50 -", 45, 300);
                g.drawString("25 -", 45, 400);
                g.drawString("0 -", 45, 500);

                g.setColor(Color.red);
            } else if (mod.equals("capacity")) {
/// axe des x
                g.drawLine(70, 70, 70, 500);
                g.drawString("Cm", 60, 60);
                g.drawString("250 -", 45, 100);
                g.drawString("150 -", 45, 260);
                g.drawString("100 -", 45, 340);
                g.drawString("50 -", 45, 420);
                g.drawString("0 -", 45, 500);

                g.setColor(Color.blue);

        }
    }
    public static void main(String[] arg) {
        new Graphe(gModele, mod);
    }


}
