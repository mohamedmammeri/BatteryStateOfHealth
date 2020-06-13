import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JProgressBar;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;


public class SOH_SIMUL {
	private static final String IMG_PATH = "img/img.png";
	private static final String FC_PATH = "img/logo.jpg";
	private static final String CDER_PATH = "img/logo_cder.png";
static String mUrl,place;
ArrayList<InfoModele> Infomodele;
ArrayList<GrapheModele> gModele;
JEditorPane editorPane_1;
JLabel lblNewLabel_2;
boolean TfixeFlag=false,HebdominaireFlag=false;
int T,Tref=25,Id,Capacite,Nc,days=1,ans=0,x,y,weekDays=-1;
Double Vs=2.4,Voc=2.3,Vod=1.9,Vex=1.8,SOHo=1.05,ntFix,nwz1,Vn=2.0;
ArrayList<Double> Vss,Vocc,Vodd,Vexx,Vnn,Vcell,SOH,Cm,nt;
Date starDate,endDate;
Image image;
JProgressBar progressBar;
JPanel panel;


	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SOH_SIMUL window = new SOH_SIMUL();
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the application.
	 */
	public SOH_SIMUL() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//lists
		
		Vss=new ArrayList<Double>();
		Vocc=new ArrayList<Double>();
		Vodd=new ArrayList<Double>();
		Vexx=new ArrayList<Double>();
		Vnn=new ArrayList<Double>();
		Vcell=new ArrayList<Double>();
		nt=new ArrayList<Double>();
		SOH=new ArrayList<Double>();
		Cm=new ArrayList<Double>();
		Infomodele=new ArrayList<>();
		gModele=new ArrayList<>();
		
		
		// UI Frame
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.getContentPane().setBackground(new Color(100, 149, 237));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
	
		
	 // World Weather URL
	 mUrl="http://api.worldweatheronline.com/premium/v1/past-weather.ashx?key=d73ff5a0288e4c3883c201502200304&q=";
		
		
	// UI Tabed with content
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(28, 57, 946, 459);
		frame.getContentPane().add(tabbedPane);
		
	// First Tabe
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Batterie", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblProcdure = new JLabel("Proc\u00E9dure");
		lblProcdure.setForeground(new Color(178, 34, 34));
		lblProcdure.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProcdure.setBounds(21, 11, 70, 27);
		panel_1.add(lblProcdure);
		
		JLabel lblBatterie = new JLabel("Batterie  :               -D\u00E9finissez les caracteristiques de batterie.  ");
		lblBatterie.setForeground(new Color(178, 34, 34));
		lblBatterie.setBounds(26, 36, 459, 14);
		panel_1.add(lblBatterie);
		
		JLabel lblNewLabel_3 = new JLabel("Utilisation :            -D\u00E9finissez le temps de travaille de batterie pendant l'ann\u00E9e.");
		lblNewLabel_3.setForeground(new Color(178, 34, 34));
		lblNewLabel_3.setBounds(26, 49, 459, 20);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblSitedfinissez = new JLabel("Site :                       -D\u00E9finissez le site g\u00E9ographique ou le batterie est stock\u00E9e.");
		lblSitedfinissez.setForeground(new Color(178, 34, 34));
		lblSitedfinissez.setBounds(26, 67, 459, 19);
		panel_1.add(lblSitedfinissez);
		
		JLabel lblGraphestracer = new JLabel("Graphes :              -Tracer les graphes est d\u00E9finer la dur\u00E9e de vie estim\u00E9 de batterie.");
		lblGraphestracer.setForeground(new Color(178, 34, 34));
		lblGraphestracer.setBounds(26, 80, 459, 20);
		panel_1.add(lblGraphestracer);
			
		JLabel lblDureDeVie = new JLabel("Dur\u00E9e de vie de batterie :");
		lblDureDeVie.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDureDeVie.setBounds(26, 238, 168, 14);
		panel_1.add(lblDureDeVie);
		
		JEditorPane editorPane_2 = new JEditorPane();
		editorPane_2.setBounds(194, 238, 57, 20);
		editorPane_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.add(editorPane_2);
		
		JLabel lblAm = new JLabel("an(s)");
		lblAm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAm.setBounds(261, 238, 46, 20);
		panel_1.add(lblAm);
		
		JLabel lblCapacitDeBatterie = new JLabel("Capacit\u00E9 C10 :");
		lblCapacitDeBatterie.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCapacitDeBatterie.setBounds(26, 290, 168, 14);
		panel_1.add(lblCapacitDeBatterie);
		
		JEditorPane editorPane_3 = new JEditorPane();
		editorPane_3.setBounds(194, 290, 57, 20);
		editorPane_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.add(editorPane_3);
		
		JLabel lblAh = new JLabel("Ah");
		lblAh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAh.setBounds(261, 291, 46, 19);
		panel_1.add(lblAh);
		
		JLabel lblTensionNominal = new JLabel("Nombre de cellules :");
		lblTensionNominal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTensionNominal.setBounds(26, 341, 148, 23);
		panel_1.add(lblTensionNominal);
		
		JEditorPane editorPane_4 = new JEditorPane();
		editorPane_4.setBounds(194, 341, 57, 23);
		editorPane_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.add(editorPane_4);
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(10, 11, 475, 113);
		panel_1.add(panel_4);
		
		JLabel lblDfinissezLesCaracteristique = new JLabel("D\u00E9finissez les caracteristiques de batterie");
		lblDfinissezLesCaracteristique.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDfinissezLesCaracteristique.setBounds(26, 151, 268, 14);
		panel_1.add(lblDfinissezLesCaracteristique);
		
		JLabel lblTempratureDeRfrence = new JLabel("Temp\u00E9rature de r\u00E9f\u00E9rence :");
		lblTempratureDeRfrence.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTempratureDeRfrence.setBounds(26, 192, 148, 14);
		panel_1.add(lblTempratureDeRfrence);
		
		JEditorPane editorPane_5 = new JEditorPane();
		editorPane_5.setBounds(194, 192, 57, 20);
		editorPane_5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.add(editorPane_5);
		
		JLabel lblC = new JLabel("C (25 par défault)");
		lblC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblC.setBounds(261, 192, 168, 20);
		panel_1.add(lblC);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBounds(10, 147, 475, 231);
		panel_1.add(panel_5);
		
		JPanel panel_6 = new JPanel() {
	             BufferedImage bufferedImg;
	             {
	                 try {
	                	 bufferedImg =  ImageIO.read(this.getClass().getResource("img.PNG"));
	                 } catch (IOException ex) {
	                 }
	             }
	             @Override
	             protected void paintComponent(Graphics g) {
	                 super.paintComponent(g);
	                 g.drawImage(bufferedImg, 0, 0, getWidth(), getHeight(), this);
	             }
	             @Override
	             public Dimension getPreferredSize() {
	                 return new Dimension(320, 200);
	             }
	         };
	         panel_6.setBounds(524, 21, 381, 201);
			panel_1.add(panel_6);
	      
		JLabel lblFigPerte = new JLabel("fig : Perte de la capacit\u00E9 \u00E9nerg\u00E9tique \u00E0 diff\u00E9rentes temp\u00E9ratures.");
		lblFigPerte.setBounds(534, 239, 360, 14);
		panel_1.add(lblFigPerte);
		
		JLabel lblNewLabel_4 = new JLabel("Instruction :");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setForeground(new Color(255, 165, 0));
		lblNewLabel_4.setBounds(524, 277, 104, 14);
		panel_1.add(lblNewLabel_4);
		
		String s="&nbsp &nbsp -Il est recommandé de fixer la température de batterie , car selon \n"
				+ " la loi de PUCKER chaque 10 C la durée de vie de batterie \n"
				+ " diminue de moitié comme le montre la figure au dessus.";
		
		JLabel lblNewLabel_5 = new JLabel("<html>"+s+"</html>");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_5.setForeground(new Color(255, 165, 0));
		lblNewLabel_5.setBounds(524, 290, 381, 73);
		panel_1.add(lblNewLabel_5);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_7.setBackground(new Color(176, 224, 230));
		panel_7.setBounds(504, 264, 427, 114);
		panel_1.add(panel_7);
		
		JButton btnTermin_1 = new JButton("Importer");	
		btnTermin_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Id=0;Capacite=0;Nc=0;
				try {
				String sId=editorPane_2.getText().toString();
				Id=Integer.parseInt(sId);
				String sC=editorPane_3.getText().toString();
				Capacite=Integer.parseInt(sC);
				String sNc=editorPane_4.getText().toString();
				Nc=Integer.parseInt(sNc);
				String sTref=editorPane_5.getText().toString();
				Tref=Integer.parseInt(sTref);
				}catch(Exception e) {
					
				}
				if(Id!=0 && Capacite!=0 && Nc!=0 ) {
					lblNewLabel_2.setText("Opération Réussit");
					lblNewLabel_2.setForeground(Color.YELLOW);
				}else {
					lblNewLabel_2.setText("Opération Échec ,Vérifie que vous avez remplir tous les cases.");
					lblNewLabel_2.setForeground(new Color(255, 0, 0));
				}
			}
		});
		btnTermin_1.setBounds(842, 397, 89, 23);
		panel_1.add(btnTermin_1);
	   
		
		 lblNewLabel_2 = new JLabel();
		 lblNewLabel_2.setBounds(149, 517, 806, 33);
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_2.setForeground(Color.YELLOW);
			frame.getContentPane().add(lblNewLabel_2);
			
		String s3="(Vous pouvez pass US Zipcode, UK Postcode, Canada Code postal, IP address, Latitude/Longitude (degré décimal) ou Nom de Ville)";
		
		String s4="Comme nous l'avons vu dans la fenetre (Batterie ) la temp\u00E9rature est important pour la dur\u00E9e de vie de batteri, Si vous nous pouvez pas fix\u00E9 la temperature de batterie laisser le chois sur (Non) et le systeme va prendre la valeur de la temp\u00E9rature de lieu. ";
		
			
			
			
		// third Tabe
			
			
			panel = new JPanel();
			tabbedPane.addTab("Site", null, panel, null);
			panel.setLayout(null);
			
			JLabel lblSiteGographique = new JLabel("Nom de Site :");
			lblSiteGographique.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblSiteGographique.setBounds(36, 43, 165, 30);
			panel.add(lblSiteGographique);
			
			JEditorPane editorPane = new JEditorPane();
			editorPane.setBounds(129, 43, 211, 30);
			editorPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel.add(editorPane);
			
			JLabel lblFixLaTemprature = new JLabel("Fix\u00E9e la temp\u00E9rature ?");
			lblFixLaTemprature.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblFixLaTemprature.setBounds(57, 207, 144, 38);
			panel.add(lblFixLaTemprature);
			
			JSpinner spinner = new JSpinner();
			spinner.setBackground(new Color(255, 255, 255));
			spinner.setModel(new SpinnerListModel(new String[] {"\tNon", "\tOui"}));
			spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
			spinner.setOpaque(true);
			spinner.setBounds(190, 212, 211, 27);
			panel.add(spinner);
			
		JLabel lblT = new JLabel("T =");
		lblT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblT.setBounds(88, 256, 33, 30);
		panel.add(lblT);
		
		editorPane_1 = new JEditorPane();
		editorPane_1.setContentType("Integer/Double\r\n");
		editorPane_1.setBounds(131, 256, 55, 30);
		editorPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		editorPane_1.setEditable(false);
		panel.add(editorPane_1);
		
		JButton btnTermin = new JButton("Importer");
		btnTermin.setBounds(842, 397, 89, 23);
		panel.add(btnTermin);
		
		
		JLabel lblLieu = new JLabel("Lieu");
		lblLieu.setForeground(new Color(0, 0, 255));
		lblLieu.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLieu.setBounds(36, 11, 46, 14);
		panel.add(lblLieu);
		JLabel lblpassUsZipcode = new JLabel("<html>"+s3+"</html>");
		lblpassUsZipcode.setBounds(36, 84, 447, 40);
		panel.add(lblpassUsZipcode);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_10.setBounds(27, 11, 456, 133);
		panel.add(panel_10);
		
		JLabel lblC_1 = new JLabel("C");
		lblC_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblC_1.setBounds(200, 256, 46, 30);
		panel.add(lblC_1);
		
		JLabel lblTempratureDeBatterie = new JLabel("Temp\u00E9rature de batterie on op\u00E9ration");
		lblTempratureDeBatterie.setForeground(new Color(165, 42, 42));
		lblTempratureDeBatterie.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTempratureDeBatterie.setBounds(36, 170, 292, 26);
		panel.add(lblTempratureDeBatterie);
		JLabel lblCommeOnA = new JLabel("<html>"+s4+"</html>");
		lblCommeOnA.setForeground(new Color(165, 42, 42));
		lblCommeOnA.setBounds(36, 297, 433, 104);
		panel.add(lblCommeOnA);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_11.setBounds(27, 155, 456, 234);
		panel.add(panel_11);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(27, 397, 456, 14);
		panel.add(progressBar);
		
		JLabel lblChoisissezLeRegulateur = new JLabel("Choisissez le regulateur");
		lblChoisissezLeRegulateur.setForeground(new Color(0, 128, 0));
		lblChoisissezLeRegulateur.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblChoisissezLeRegulateur.setBounds(509, 13, 165, 23);
		panel.add(lblChoisissezLeRegulateur);
		
		String s5="les paramètres de fonctionnement de règulateur seront automatiquement ajusté selon les proprietés de systeme.";
		JLabel lblNewLabel = new JLabel("<html>"+s5+"</html>");
		lblNewLabel.setForeground(new Color(165, 42, 42));
		lblNewLabel.setBounds(498, 36, 433, 61);
		panel.add(lblNewLabel);
		
		JLabel lblRegulateurUniversal = new JLabel("Regulateur universel :");
		lblRegulateurUniversal.setBounds(508, 93, 134, 26);
		panel.add(lblRegulateurUniversal);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerListModel(new String[] {"D\u00E9sactiver", "Activer"}));
		spinner_1.setBounds(653, 96, 179, 20);
		panel.add(spinner_1);
		
		JLabel lblNewLabel_1 = new JLabel("Courant max :  25 A");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(509, 130, 179, 23);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_6 = new JLabel("Courant de charge/d\u00E9charge :   5 A");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_6.setBounds(509, 155, 179, 14);
		panel.add(lblNewLabel_6);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_12.setBackground(new Color(208, 255, 208));
		panel_12.setBounds(493, 11, 438, 375);
		panel.add(panel_12);
		
	
		// Spinner listener
	    spinner.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	        	
	          if (String.valueOf(spinner.getValue()).equals("	Oui")) {
	        	  editorPane_1.setEditable(true);
	        	  TfixeFlag=true;
	        	  
	        	  
	          }else {
	        	  editorPane_1.setEditable(false);
	        	  TfixeFlag=false;
	        	  
	          }
	        }
	    });
	    
		
		 // fetching data from server
		btnTermin.addMouseListener(new MouseAdapter() {
			@Override
		     public void mouseClicked(MouseEvent mouseEvent) {
				Thread newThread = new Thread(() -> {
					//clear data 
					Vss.clear();Vocc.clear();Vodd.clear();Vexx.clear();Vnn.clear();Vcell.clear();SOH.clear();Cm.clear();nt.clear();
					gModele.clear();
					days=1;ans=0;
					//////
					
				    try {
				    	place=editorPane.getText().toString();
						Infomodele=new FetchData().startFetching(mUrl,place,progressBar);
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				    
				    if(Infomodele.size()>360) {
				    	lblNewLabel_2.setText("Importation des données a été réussit ,Votre site est : "+Infomodele.get(0).getLocation());
				    	lblNewLabel_2.setForeground(Color.YELLOW);
				 
				    	
				    }else {
				    	lblNewLabel_2.setText("Importation des données a été échoué ");
				    	lblNewLabel_2.setForeground(new Color(255, 0, 0));
				    }
				    if(TfixeFlag) {
				    	String t=editorPane_1.getText().toString();
				    	T=Integer.parseInt(t);	
				    }
				});newThread.start();
		     }
		});
		
		ButtonGroup g1=new ButtonGroup();
		
		String s6="&nbsp &nbsp &nbsp Le systeme dépend des données météorologiques, et comme il n'y a pas de données gratuites disponibles par heure,"
				+ " la moyenne quotidienne est utilisée pour l'arrondi, il n'y a donc pas d'option pour spécifier les heures d'utilisation par jour";
		
		String s7="Nous nous appuierons sur des données d'extrapolation pour les dernières années afin de collecter des données météorologiques.";
		
		
	// Second Tabe
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Utilisation", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblDateDeDbut = new JLabel("Date de d\u00E9but :");
		lblDateDeDbut.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDateDeDbut.setBounds(433, 91, 101, 20);
		panel_3.add(lblDateDeDbut);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerDateModel(new Date(1577833200000L), new Date(1577833200000L), new Date(1606777200000L), Calendar.DAY_OF_MONTH));
		spinner_2.setEditor(new JSpinner.DefaultEditor(spinner_2));
		spinner_2.setBounds(554, 92, 156, 20);
		panel_3.add(spinner_2);
		
		JLabel lblDateDeFin = new JLabel("Date de fin :");
		lblDateDeFin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDateDeFin.setBounds(433, 122, 101, 25);
		panel_3.add(lblDateDeFin);
		
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerDateModel(new Date(1577833200000L), new Date(1577833200000L), new Date(1606777200000L), Calendar.DAY_OF_MONTH));
		spinner_3.setEditor(new JSpinner.DefaultEditor(spinner_3));
		spinner_3.setBounds(554, 123, 156, 21);
		panel_3.add(spinner_3);
		
		JButton btnTermin_2 = new JButton("Importer");
		btnTermin_2.setBounds(842, 397, 89, 23);
		panel_3.add(btnTermin_2);
		
		JLabel lblDfinitionDeLa = new JLabel("D\u00E9finition de la consommation par");
		lblDfinitionDeLa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDfinitionDeLa.setForeground(new Color(0, 0, 0));
		lblDfinitionDeLa.setBounds(27, 26, 280, 14);
		panel_3.add(lblDfinitionDeLa);
		
		JRadioButton rdbtnAnnuel = new JRadioButton("Ann\u00E9e");
		rdbtnAnnuel.setBounds(27, 62, 109, 23);
		rdbtnAnnuel.setSelected(true);
		panel_3.add(rdbtnAnnuel);
		
		JRadioButton rdbtnMenseul = new JRadioButton("Mois");
		rdbtnMenseul.setBounds(27, 86, 109, 23);
		panel_3.add(rdbtnMenseul);
		
		JRadioButton rdbtnHebdomadaire = new JRadioButton("Hebdomadaire");
		rdbtnHebdomadaire.setBounds(27, 111, 142, 23);
		panel_3.add(rdbtnHebdomadaire);
		g1.add(rdbtnAnnuel);
		g1.add(rdbtnMenseul);
		g1.add(rdbtnHebdomadaire);
		
		JLabel lblMensuel = new JLabel("Mensuel");
		lblMensuel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMensuel.setBounds(433, 66, 120, 14);
		panel_3.add(lblMensuel);
		
		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_13.setBounds(413, 62, 504, 123);
		panel_3.add(panel_13);
		
		JLabel lblHebdomadaire = new JLabel("Hebdomadaire");
		lblHebdomadaire.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHebdomadaire.setBounds(433, 217, 156, 14);
		panel_3.add(lblHebdomadaire);
		
		JRadioButton rdbtnSamedi = new JRadioButton("Samedi");
		rdbtnSamedi.setBounds(425, 238, 89, 23);
		panel_3.add(rdbtnSamedi);
		
		JRadioButton rdbtnDimenche = new JRadioButton("Dimenche");
		rdbtnDimenche.setBounds(516, 238, 109, 23);
		panel_3.add(rdbtnDimenche);
		
		JRadioButton rdbtnLundi = new JRadioButton("Lundi");
		rdbtnLundi.setBounds(627, 238, 109, 23);
		panel_3.add(rdbtnLundi);
		
		JRadioButton rdbtnMardi = new JRadioButton("Mardi");
		rdbtnMardi.setBounds(738, 238, 109, 23);
		panel_3.add(rdbtnMardi);
		
		JRadioButton rdbtnMercrudi = new JRadioButton("Mercredi");
		rdbtnMercrudi.setBounds(425, 264, 89, 23);
		panel_3.add(rdbtnMercrudi);
		
		JRadioButton rdbtnJudi = new JRadioButton("Judi");
		rdbtnJudi.setBounds(516, 264, 109, 23);
		panel_3.add(rdbtnJudi);
		
		JRadioButton rdbtnVendredi = new JRadioButton("Vendredi");
		rdbtnVendredi.setBounds(627, 264, 109, 23);
		panel_3.add(rdbtnVendredi);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_14.setBounds(414, 205, 504, 147);
		panel_3.add(panel_14);
		JLabel lblNewLabel_7 = new JLabel("<html>"+s6+"</html>");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_7.setBounds(27, 238, 366, 88);
		
		lblNewLabel_7.setForeground(new Color(255,165,0));
		panel_3.add(lblNewLabel_7);
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_15.setBounds(20, 205, 384, 147);
		panel_15.setBackground(new Color(176, 224, 230));
		panel_3.add(panel_15);
		
		JLabel lblInstruction = new JLabel("Instruction");
		lblInstruction.setForeground(new Color(255, 140, 0));
		lblInstruction.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_15.add(lblInstruction);
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_16.setBounds(10, 11, 921, 363);
		panel_3.add(panel_16);
		JLabel lblNewLabel_8 = new JLabel("<html>"+s7+"</html>");
		lblNewLabel_8.setForeground(new Color(139, 0, 0));
		lblNewLabel_8.setBounds(57, 385, 689, 35);
		panel_3.add(lblNewLabel_8);
		
		btnTermin_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnMenseul.isSelected()) {
				starDate=(Date) spinner_2.getValue();
				endDate=(Date) spinner_3.getValue();
				Calendar c=Calendar.getInstance();
				c.setTime(starDate);
				c.add(Calendar.YEAR, -2);
				starDate=c.getTime();
				c.setTime(endDate);
				c.add(Calendar.YEAR, -2);
				endDate=c.getTime();
				}else {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						starDate=dateFormat.parse("2018-01-01");
						endDate=dateFormat.parse("2018-12-31");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
					lblNewLabel_2.setText("la Date a été définie. ");
			    	lblNewLabel_2.setForeground(Color.YELLOW);
			    	
				if(rdbtnHebdomadaire.isSelected()) {
					weekDays=-1;
					HebdominaireFlag=true;
				
				if(rdbtnSamedi.isSelected()) {weekDays++;};if(rdbtnDimenche.isSelected()) {weekDays++;};
				if(rdbtnLundi.isSelected()) {weekDays++;};if(rdbtnMardi.isSelected()) {weekDays++;};
				if(rdbtnMercrudi.isSelected()) {weekDays++;};if(rdbtnSamedi.isSelected()) {weekDays++;};if(rdbtnVendredi.isSelected()) {weekDays++;};
				
				}
			}});
	////////
		
		
	// fourth Tabe
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Graphes", null, panel_2, null);
		panel_2.setLayout(null);
		
		JButton btnDesign = new JButton("Tracer");
		btnDesign.setBounds(165, 150, 89, 23);
		panel_2.add(btnDesign);
		String s8="Dessiner le graphe de la durée de vie de la batterie en fonction de nombre des jours estimer.";
		JLabel lblNewLabel_9 = new JLabel("<html>"+s8+"</html>");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_9.setBounds(83, 32, 299, 88);
		panel_2.add(lblNewLabel_9);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_17.setBounds(10, 11, 437, 187);
		panel_2.add(panel_17);
		String s9="Dessiner la variation de la capacité estimer par le modele en fonction temps.";
		JLabel lblDessinerLaVariation = new JLabel("<html>"+s9+"</html>");
		lblDessinerLaVariation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDessinerLaVariation.setBounds(83, 249, 338, 88);
		panel_2.add(lblDessinerLaVariation);
		
		JButton btnDessiner = new JButton("Tracer");
		btnDessiner.setBounds(177, 348, 89, 23);
		panel_2.add(btnDessiner);
		
		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_18.setBackground(new Color(238, 130, 238));
		panel_18.setBounds(10, 209, 437, 200);
		panel_2.add(panel_18);
		
		JLabel lblComingSoon = new JLabel("Coming soon");
		lblComingSoon.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblComingSoon.setForeground(new Color(255, 250, 205));
		panel_18.add(lblComingSoon);
		String s10="Dessinez le graphique de la courbe de température à l'emplacement défini au cours de l'année.";
		JLabel lblDissenerLaVariation = new JLabel("<html>"+s10+"</html>");
		lblDissenerLaVariation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDissenerLaVariation.setBounds(536, 39, 318, 103);
		panel_2.add(lblDissenerLaVariation);
		
		JButton btnDessiner_1 = new JButton("Tracer");
		btnDessiner_1.setBounds(638, 150, 89, 23);
		panel_2.add(btnDessiner_1);
		
		JPanel panel_19 = new JPanel();
		panel_19.setBackground(new Color(238, 130, 238));
		panel_19.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_19.setBounds(474, 11, 437, 187);
		panel_2.add(panel_19);
		
		JLabel lblComingSoon_1 = new JLabel("Coming soon");
		lblComingSoon_1.setForeground(new Color(255, 255, 255));
		lblComingSoon_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_19.add(lblComingSoon_1);
		String s11="Dessiner la courbe de la durée de jour à l'emplacement défini pendant l'année ";
		JLabel lblDessinerLaCourbe = new JLabel("<html>"+s11+"</html>");
		lblDessinerLaCourbe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDessinerLaCourbe.setBounds(535, 249, 380, 104);
		panel_2.add(lblDessinerLaCourbe);
		
		JButton btnTracer = new JButton("Tracer");
		btnTracer.setBounds(638, 348, 89, 23);
		panel_2.add(btnTracer);
		
		JPanel panel_20 = new JPanel();
		panel_20.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_20.setBackground(new Color(238, 130, 238));
		panel_20.setBounds(474, 209, 437, 200);
		panel_2.add(panel_20);
		
		JLabel lblComingSoon_2 = new JLabel("Coming soon");
		lblComingSoon_2.setForeground(new Color(255, 255, 255));
		lblComingSoon_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_20.add(lblComingSoon_2);
		
		JLabel lblCommentaire = new JLabel("Commentaire :");
		lblCommentaire.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCommentaire.setBounds(28, 517, 111, 33);
		frame.getContentPane().add(lblCommentaire);
		
		JPanel FCLogo = new JPanel() {
            BufferedImage bufferedImg;
            {
                try {
               	 bufferedImg =  ImageIO.read(this.getClass().getResource("logo.jpg"));
                } catch (IOException ex) {
                }
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImg, 0, 0, getWidth(), getHeight(), this);
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(320, 200);
            }
        };
		FCLogo.setBackground(new Color(100, 149, 237));
		FCLogo.setBounds(916, 11, 78, 54);
		frame.getContentPane().add(FCLogo);
		
		JPanel CDERLogo =  new JPanel() {
            BufferedImage bufferedImg;
            {
                try {
               	 bufferedImg =  ImageIO.read(this.getClass().getResource("logo_cder.png"));
                } catch (IOException ex) {
                }
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImg, 0, 0, getWidth(), getHeight(), this);
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(320, 200);
            }
        };
		CDERLogo.setBackground(new Color(100, 149, 237));
		CDERLogo.setBounds(839, 11, 78, 54);
		frame.getContentPane().add(CDERLogo);
		
		JLabel lblImportant = new JLabel("Important :");
		lblImportant.setForeground(new Color(0, 0, 255));
		lblImportant.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblImportant.setBounds(61, 11, 78, 35);
		frame.getContentPane().add(lblImportant);
		
		String s1="-Il est n\u00E9cessaire d'appuyer sur le bouton (Importer) apr\u00E8s avoir rempli les cases.";
		JLabel lblIlEstNeccaire = new JLabel("<html>"+s1+"</html>");
		lblIlEstNeccaire.setForeground(new Color(0, 0, 255));
		lblIlEstNeccaire.setBounds(146, 15, 324, 31);
		frame.getContentPane().add(lblIlEstNeccaire);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(255, 192, 203));
		panel_8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_8.setBounds(27, 11, 443, 42);
		frame.getContentPane().add(panel_8);
		
		JLabel lblV = new JLabel("V 1.0");
		lblV.setBounds(958, 527, 36, 14);
		frame.getContentPane().add(lblV);
		
		String s2="Donn\u00E9es m\u00E9t\u00E9orologiques extraites du serveur de site www.worldweatheronline.com ";
		JLabel lblLeServeur = new JLabel("<html>"+s2+"</html>");
		lblLeServeur.setForeground(new Color(0, 0, 255));
		lblLeServeur.setBounds(529, 11, 300, 54);
		frame.getContentPane().add(lblLeServeur);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_9.setBackground(new Color(255, 192, 203));
		panel_9.setBounds(509, 11, 320, 54);
		frame.getContentPane().add(panel_9);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmQuite = new JMenuItem("quite");
		mnMenu.add(mntmQuite);
		
		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		
		JMenuItem mntmBatterie = new JMenuItem("Batterie");
		mnAide.add(mntmBatterie);
		
		JMenuItem mntmUtilisateur = new JMenuItem("Utilisateur");
		mnAide.add(mntmUtilisateur);
		
		JMenuItem mntmSite = new JMenuItem("Site");
		mnAide.add(mntmSite);
		
		JMenu mnGraphes = new JMenu("Graphes");
		mnAide.add(mnGraphes);
		
		JMenuItem mntmSoh = new JMenuItem("SOH");
		mnGraphes.add(mntmSoh);
		
		JMenuItem mntmCapacit = new JMenuItem("Capacit\u00E9");
		mnGraphes.add(mntmCapacit);
		
		JMenuItem mntmTemprature = new JMenuItem("Temp\u00E9rature");
		mnGraphes.add(mntmTemprature);
		
		JMenuItem mntmDureDeJour = new JMenuItem("Dur\u00E9e de jour");
		mnGraphes.add(mntmDureDeJour);
		
		JMenu mnAPropos = new JMenu("A propos");
		menuBar.add(mnAPropos);
		
		JMenuItem mntmModele = new JMenuItem("Modele");
		mnAPropos.add(mntmModele);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Projet");
		mnAPropos.add(mntmNewMenuItem);
		
		JMenuItem mntmDonnesMetho = new JMenuItem("Donn\u00E9es meth\u00E9o");
		mnAPropos.add(mntmDonnesMetho);
		btnDesign.addMouseListener(new MouseAdapter() {
			@Override
		     public void mouseClicked(MouseEvent mouseEvent) {
				if(!rdbtnHebdomadaire.isSelected()) {
				if(String.valueOf(spinner_1.getValue()).equals("Activer")) {
				avecMpptCalcul();
				}else {
					NoMpptCalcul();
				}
				}else if(rdbtnHebdomadaire.isSelected()) {
					if(String.valueOf(spinner_1.getValue()).equals("Activer")) {
						HebdominaireAvecMpptCalcul();
						}else {
							HebdominaireNoMpptCalcul();
						}
				}
				new Graphe(gModele);
			
			} });
		
	}
	
	
	public void avecMpptCalcul(){
		Boolean stopFlag=false;
		cteCalcul();
		
		///////////////////////////////// Annuel et mensuel \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// avec regulateur et fixer la temerature		
		if(TfixeFlag) {
			int i=0;
			while(!stopFlag) {
				if(i==nt.size()) {
					i=0;
					ans++;
					days=0;
				}
				
				if(Infomodele.get(i).getDate().after(starDate) && Infomodele.get(i).getDate().before(endDate)) {
				
				SOHo=SOHo-(ntFix*nwz1*86400);
				gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				
			}else {
					SOHo=SOHo-0.0001;
					gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
					
			}
				if(SOHo<0.005) {
					stopFlag=true;
				}
				days++;
				i++;
				}
		}
// avec regulateur et temperature variente		
		else {
			int i=0;
			while(!stopFlag) {
				if(i==nt.size()) {
					i=0;
					ans++;
					days=0;
				}
				if(Infomodele.get(i).getDate().after(starDate) && Infomodele.get(i).getDate().before(endDate)) {
			
			SOHo=SOHo-(nt.get(i)*nwz1*86400);
			gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				}else {
					
					SOHo=SOHo-0.0001;
					gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				}
				if(SOHo<0.005) {
					stopFlag=true;
				}
			days++;
			i++;
			
			}
		}
		
		
	}
	
	
	public void NoMpptCalcul() {
		Boolean stopFlag=false;
		cteCalcul();
		Double nwz=0.0;
//sans regulateur avec temperature fixe

		if(TfixeFlag) {
			int i=0;
			while(!stopFlag) {
				if(i==nt.size()) {
					i=0;
					ans++;
					days=0;
				}
				/**if(Vcell.get(i)>Vodd.get(i) && Vcell.get(i)<Vocc.get(i)) {
					nwz=nwz1;
				}else if((Vocc.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vss.get(i)) || (Vexx.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vodd.get(i)) ) {
					nwz=10*nwz1;
				}else {
					nwz=100*nwz1;
				}*/
				if(Infomodele.get(i).getDate().after(starDate) && Infomodele.get(i).getDate().before(endDate)) {
				if(T>25) {nwz=10*nwz1;}else if(T>38) {nwz=100*nwz1;}else {nwz=nwz1;}
				
				SOHo=SOHo-(ntFix*nwz*86400);
				gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				}else {
					if(T>25) {nwz=10*nwz1;}else if(T>38) {nwz=100*nwz1;}else {nwz=nwz1;}
					
					SOHo=SOHo-0.00001;
					gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				}
				if(SOHo<0.005) {
					stopFlag=true;
				}
				i++;
				days++;
	
			}	
		}
//sans regulateur avec temperature variente
		else {
			int i=0;
			while(!stopFlag) {
				if(i==nt.size()) {
					i=0;
					ans++;
					days=0;
				}
			
			/**if(Vcell.get(i)>Vodd.get(i) && Vcell.get(i)<Vocc.get(i)) {
				nwz=nwz1;
			}else if((Vocc.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vss.get(i)) || (Vexx.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vodd.get(i)) ) {
				nwz=10*nwz1;
			}else {
				nwz=100*nwz1;
			}*/
			if(Infomodele.get(i).getDate().after(starDate) && Infomodele.get(i).getDate().before(endDate)) {
			if(Infomodele.get(i).getTemperature()>25 && Infomodele.get(i).getTemperature()<38) {nwz=10*nwz1;}else if(Infomodele.get(i).getTemperature()>38) {nwz=100*nwz1;}else {nwz=nwz1;}
			SOHo=SOHo-(nt.get(i)*nwz*86400);
			gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
			}else {
				
				SOHo=SOHo-0.00001;
				gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
			}
			if(SOHo<0.005) {
				stopFlag=true;
			}
			i++;
			days++;
			}
			
		}
		
	}
////////////////////////////////////////////////////////// Hebdominaire \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	
	public void HebdominaireAvecMpptCalcul(){
		Boolean stopFlag=false;
		cteCalcul();
		
// avec regulateur et fixer la temerature		
		if(TfixeFlag) {
			int i=0;
			while(!stopFlag) {
				if(i==nt.size()) {
					i=0;
					ans++;
					days=0;
				}
				
				if(Infomodele.get(i).getDate().getDay()<weekDays) {
				
				
				SOHo=SOHo-(ntFix*nwz1*86400);
				gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
			}else {
					
					
					SOHo=SOHo-0.0001;
					gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
					
			}
				if(SOHo<0.005) {
					stopFlag=true;
				}
				days++;
				i++;
				}
		}
// avec regulateur et temperature variente		
		else {
			int i=0;
			while(!stopFlag) {
				if(i==nt.size()) {
					i=0;
					ans++;
					days=0;
				}
				if(Infomodele.get(i).getDate().getDay()<weekDays) {
				
			
			SOHo=SOHo-(nt.get(i)*nwz1*86400);
			gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
			
				}else {
					
					SOHo=SOHo-0.0001;
					gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				
				}
				if(SOHo<0.005) {
					stopFlag=true;
				}
			days++;
			i++;
			
			}
		}
		
		
	}
	
	
	public void HebdominaireNoMpptCalcul() {
		Boolean stopFlag=false;
		cteCalcul();
		Double nwz=0.0;
//sans regulateur avec temperature fixe

		if(TfixeFlag) {
			int i=0;
			while(!stopFlag) {
				if(i==nt.size()) {
					i=0;
					ans++;
					days=0;
				}
				/**if(Vcell.get(i)>Vodd.get(i) && Vcell.get(i)<Vocc.get(i)) {
					nwz=nwz1;
				}else if((Vocc.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vss.get(i)) || (Vexx.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vodd.get(i)) ) {
					nwz=10*nwz1;
				}else {
					nwz=100*nwz1;
				}*/
				if(Infomodele.get(i).getDate().getDay()<weekDays) {
				if(Infomodele.get(i).getTemperature()>25) {nwz=10*nwz1;}else if(Infomodele.get(i).getTemperature()>30) {nwz=100*nwz1;}else {nwz=nwz1;}
				SOHo=SOHo-(ntFix*nwz*86400);
				gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				
				}else {
					if(Infomodele.get(i).getTemperature()>25) {nwz=10*nwz1;}else if(Infomodele.get(i).getTemperature()>30) {nwz=100*nwz1;}else {nwz=nwz1;}
					SOHo=SOHo-0.00001;
					gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				
				}
				if(SOHo<0.005) {
					stopFlag=true;
				}
				i++;
				days++;
	
			}	
		}
//sans regulateur avec temperature variente
		else {
			int i=0;
			while(!stopFlag) {
				if(i==nt.size()) {
					i=0;
					ans++;
					days=0;
				}
			/**if(Vcell.get(i)>Vodd.get(i) && Vcell.get(i)<Vocc.get(i)) {
				nwz=nwz1;
			}else if((Vocc.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vss.get(i)) || (Vexx.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vodd.get(i)) ) {
				nwz=10*nwz1;
			}else {
				nwz=100*nwz1;
			}*/
			if(Infomodele.get(i).getDate().getDay()<weekDays) {
			if(Infomodele.get(i).getTemperature()>25) {nwz=10*nwz1;}else if(Infomodele.get(i).getTemperature()>30) {nwz=100*nwz1;}else {nwz=nwz1;}
			SOHo=SOHo-(nt.get(i)*nwz*86400);
			gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
			
			}else {
				SOHo=SOHo-0.00001;
				gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				
			}
			if(SOHo<0.005) {
				stopFlag=true;
			}
			i++;
			days++;
			}
			
		}
		
	}
	public void cteCalcul() {
		nwz1=(3.17*Math.pow(10,-8))/Id;
	
			ntFix=Math.pow(2, (T-25)/10);
		
			for(int i=0;i<Infomodele.size();i++) {
			nt.add(Math.pow(2, (Infomodele.get(i).getTemperature()-25)/10));
			Vss.add(Vs-(0.005*(Infomodele.get(i).getTemperature()-25)));
			Vocc.add(Voc-(0.005*(Infomodele.get(i).getTemperature()-25)));
			Vodd.add(Vod-(0.005*(Infomodele.get(i).getTemperature()-25)));
			Vexx.add(Vex-(0.005*(Infomodele.get(i).getTemperature()-25)));
			Vnn.add(Vn-(0.005*(Infomodele.get(i).getTemperature()-25)));
			
		}
	}

}
