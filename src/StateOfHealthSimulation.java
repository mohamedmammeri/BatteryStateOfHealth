
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JProgressBar;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;


public class StateOfHealthSimulation {
    String mUrl, place, StorageCondition, RegulatorMode;
    ArrayList<InfoModele> Infomodele;
    ArrayList<GrapheModele> gModele;
    JEditorPane editorPane_1;
    JLabel editCommentaire;
    boolean TfixeFlag = false, HebdominaireFlag = false;
    int T, Tref = 25, Id, Capacite, CapacityNominal, CapacityInitial, TensionNominal, days = 1, ans = 0, x, y, weekDays = -1;
    Double Vs = 2.4, Voc = 2.3, Vod = 1.9, Vex = 1.8, SOHo = (double) 1, ntFix, nwz1, Vn = 2.0;
    ArrayList<Double> Vss, Vocc, Vodd, Vexx, Vnn, Vcell, SOH, Cm, nt;
    Date starDate, endDate;
    Image image;
    JProgressBar progressBar;


    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StateOfHealthSimulation window = new StateOfHealthSimulation();

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
    public StateOfHealthSimulation() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        //lists

        Vss = new ArrayList<Double>();
        Vocc = new ArrayList<Double>();
        Vodd = new ArrayList<Double>();
        Vexx = new ArrayList<Double>();
        Vnn = new ArrayList<Double>();
        Vcell = new ArrayList<Double>();
        nt = new ArrayList<Double>();
        SOH = new ArrayList<Double>();
        Cm = new ArrayList<Double>();
        Infomodele = new ArrayList<>();
        gModele = new ArrayList<>();


        // UI Frame

        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 600);
        frame.getContentPane().setBackground(new Color(100, 149, 237));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);


        // World Weather URL
        mUrl = "http://api.worldweatheronline.com/premium/v1/past-weather.ashx?key=2052464ad950481a83d160216201706&q=";


        // UI Tabed with content
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(28, 57, 946, 459);
        frame.getContentPane().add(tabbedPane);

        editCommentaire = new JLabel();
        editCommentaire.setBounds(149, 517, 806, 33);
        editCommentaire.setFont(new Font("Tahoma", Font.PLAIN, 14));
        editCommentaire.setForeground(Color.YELLOW);
        frame.getContentPane().add(editCommentaire);

        tabbedPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent arg0) {
                editCommentaire.setText(null);

            }

        });
        ////////// Strings 

        String s = "&nbsp &nbsp -Il est recommandé de fixer la température de la batterie sur la valeur de la température ambiante , car selon \n"
                + " la loi de Peukert chaque 10 C la durée de vie de la batterie \n"
                + " diminue de moitié comme le montre la figure ci-dessus.";

        String s3 = "(Vous pouvez entrer US Zipcode, UK Postcode, Canada Code postal, IP address, Latitude/Longitude (degré décimal) ou Nom de Ville)";

        //===================================================================================================================================//
        //															Battery tab 
        //====================================================================================================================================//	
        JPanel panel_Battery = new JPanel();
        tabbedPane.addTab("Batterie", null, panel_Battery, null);
        panel_Battery.setLayout(null);

        JLabel lblProcdure = new JLabel("Proc\u00E9dure");
        lblProcdure.setForeground(new Color(178, 34, 34));
        lblProcdure.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblProcdure.setBounds(21, 11, 70, 27);
        panel_Battery.add(lblProcdure);

        JLabel lblBatterie = new JLabel("Batterie  :               -D\u00E9finissez les caracteristiques de la batterie.  ");
        lblBatterie.setForeground(new Color(178, 34, 34));
        lblBatterie.setBounds(26, 36, 459, 14);
        panel_Battery.add(lblBatterie);

        JLabel lblNewLabel_3 = new JLabel("Utilisation :            -D\u00E9finissez le temps de travaille de la batterie pendant l'ann\u00E9e.");
        lblNewLabel_3.setForeground(new Color(178, 34, 34));
        lblNewLabel_3.setBounds(26, 49, 459, 20);
        panel_Battery.add(lblNewLabel_3);

        JLabel lblSitedfinissez = new JLabel("Site :                       -D\u00E9finissez le site g\u00E9ographique o\u00F9 la batterie est stock\u00E9e.");
        lblSitedfinissez.setForeground(new Color(178, 34, 34));
        lblSitedfinissez.setBounds(26, 67, 459, 19);
        panel_Battery.add(lblSitedfinissez);

        JLabel lblGraphestracer = new JLabel("Graphes :              -Tracer les graphes est d\u00E9finer la dur\u00E9e de vie/Capacit\u00E9/Temp\u00E9rature ");
        lblGraphestracer.setForeground(new Color(178, 34, 34));
        lblGraphestracer.setBounds(26, 80, 459, 20);
        panel_Battery.add(lblGraphestracer);

        JLabel lblDureDeVie = new JLabel("Dur\u00E9e de vie de la batterie :");
        lblDureDeVie.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDureDeVie.setBounds(21, 308, 229, 14);
        panel_Battery.add(lblDureDeVie);

        JEditorPane editorLifeTime = new JEditorPane();
        editorLifeTime.setBounds(288, 308, 57, 20);
        editorLifeTime.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_Battery.add(editorLifeTime);

        JLabel lblAm = new JLabel("an(s)");
        lblAm.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAm.setBounds(370, 304, 46, 20);
        panel_Battery.add(lblAm);

        JLabel lblCapacitDeBatterie = new JLabel("Capacit\u00E9 C10 :");
        lblCapacitDeBatterie.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCapacitDeBatterie.setBounds(21, 176, 168, 14);
        panel_Battery.add(lblCapacitDeBatterie);

        JEditorPane editorCapacityNominal = new JEditorPane();
        editorCapacityNominal.setBounds(288, 176, 57, 20);
        editorCapacityNominal.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_Battery.add(editorCapacityNominal);

        JLabel lblAh = new JLabel("Ah");
        lblAh.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAh.setBounds(370, 177, 46, 19);
        panel_Battery.add(lblAh);

        JLabel lblTensionNominal = new JLabel("Tension nominal :");
        lblTensionNominal.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTensionNominal.setBounds(21, 239, 148, 23);
        panel_Battery.add(lblTensionNominal);

        JEditorPane editorTension = new JEditorPane();
        editorTension.setBounds(288, 239, 57, 23);
        editorTension.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_Battery.add(editorTension);


        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_4.setBounds(10, 11, 475, 113);
        panel_Battery.add(panel_4);

        JLabel lblDfinissezLesCaracteristique = new JLabel("D\u00E9finissez les caracteristiques de la batterie");
        lblDfinissezLesCaracteristique.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDfinissezLesCaracteristique.setBounds(26, 151, 268, 14);
        panel_Battery.add(lblDfinissezLesCaracteristique);

        JLabel lblTempratureDeRfrence = new JLabel("Temp\u00E9rature de r\u00E9f\u00E9rence :");
        lblTempratureDeRfrence.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTempratureDeRfrence.setBounds(21, 279, 229, 14);
        panel_Battery.add(lblTempratureDeRfrence);

        JEditorPane editorTemps = new JEditorPane();
        editorTemps.setBounds(288, 273, 57, 20);
        editorTemps.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_Battery.add(editorTemps);

        JLabel lblC = new JLabel("C (25 par défault)");
        lblC.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblC.setBounds(355, 275, 139, 20);
        panel_Battery.add(lblC);

        JLabel lblCapacitInitiale = new JLabel("Capacit\u00E9 initiale :");
        lblCapacitInitiale.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCapacitInitiale.setBounds(22, 213, 187, 14);
        panel_Battery.add(lblCapacitInitiale);

        JEditorPane editorCapacityInitial = new JEditorPane();
        editorCapacityInitial.setBounds(288, 207, 57, 20);
        editorCapacityInitial.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_Battery.add(editorCapacityInitial);

        JLabel lblAhgaleCPar = new JLabel("Ah");
        lblAhgaleCPar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAhgaleCPar.setBounds(355, 210, 22, 19);
        panel_Battery.add(lblAhgaleCPar);

        JLabel lblV_1 = new JLabel("V");
        lblV_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblV_1.setBounds(370, 244, 35, 19);
        panel_Battery.add(lblV_1);

        /// mode of regulator group
        ButtonGroup gRegulateur = new ButtonGroup();

        JRadioButton rdbtnNewRadioButton = new JRadioButton("Couplage direct");
        rdbtnNewRadioButton.setBackground(new Color(208, 255, 208));
        rdbtnNewRadioButton.setBounds(690, 240, 168, 23);
        rdbtnNewRadioButton.setEnabled(false);
        rdbtnNewRadioButton.setSelected(true);
        panel_Battery.add(rdbtnNewRadioButton);

        JRadioButton rdbtnConvertiseurMppt = new JRadioButton("Convertiseur MPPT");
        rdbtnConvertiseurMppt.setBackground(new Color(208, 255, 208));
        rdbtnConvertiseurMppt.setBounds(690, 266, 168, 23);
        rdbtnConvertiseurMppt.setEnabled(false);
        panel_Battery.add(rdbtnConvertiseurMppt);

        JRadioButton rdbtnConvertisseurDcdc = new JRadioButton("Convertisseur DCDC");
        rdbtnConvertisseurDcdc.setBackground(new Color(208, 255, 208));
        rdbtnConvertisseurDcdc.setBounds(690, 292, 153, 23);
        rdbtnConvertisseurDcdc.setEnabled(false);
        panel_Battery.add(rdbtnConvertisseurDcdc);
        gRegulateur.add(rdbtnNewRadioButton);
        gRegulateur.add(rdbtnConvertiseurMppt);
        gRegulateur.add(rdbtnConvertisseurDcdc);

        /// regulateur spinner

        JSpinner regulateur_spinner = new JSpinner();
        regulateur_spinner.setModel(new SpinnerListModel(new String[]{"   D\u00E9sactiver", "   Activer"}));
        regulateur_spinner.setBounds(690, 187, 129, 20);
        panel_Battery.add(regulateur_spinner);
        regulateur_spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent arg0) {
                if (regulateur_spinner.getValue().equals("   Activer")) {
                    rdbtnNewRadioButton.setEnabled(true);
                    rdbtnConvertiseurMppt.setEnabled(true);
                    rdbtnConvertisseurDcdc.setEnabled(true);
                } else {
                    rdbtnNewRadioButton.setEnabled(false);
                    rdbtnConvertiseurMppt.setEnabled(false);
                    rdbtnConvertisseurDcdc.setEnabled(false);
                }

            }

        });


        JLabel lblConditionsDeStockages = new JLabel("Conditions de stockages :");
        lblConditionsDeStockages.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblConditionsDeStockages.setBounds(535, 14, 250, 20);
        panel_Battery.add(lblConditionsDeStockages);

        /// Condition of stockage group
        ButtonGroup gCondition = new ButtonGroup();
        JRadioButton rdbtnNormal = new JRadioButton("Condition Normale");
        rdbtnNormal.setBounds(518, 40, 187, 23);
        rdbtnNormal.setSelected(true);
        panel_Battery.add(rdbtnNormal);

        JRadioButton rdbtnOversize = new JRadioButton("Condition Surdimensioner");
        rdbtnOversize.setBounds(518, 66, 203, 21);
        panel_Battery.add(rdbtnOversize);

        JRadioButton rdbtnLesssize = new JRadioButton("Condition SousDimensioner");
        rdbtnLesssize.setBounds(518, 90, 255, 23);
        panel_Battery.add(rdbtnLesssize);
        gCondition.add(rdbtnNormal);
        gCondition.add(rdbtnOversize);
        gCondition.add(rdbtnLesssize);


        JLabel label_3 = new JLabel("<html>Les param\u00E8tres de fonctionnement de r\u00E8gulateur seront automatiquement ajust\u00E9 selon les propriet\u00E9s de systeme.</html>");
        label_3.setForeground(new Color(165, 42, 42));
        label_3.setBounds(535, 332, 369, 46);
        panel_Battery.add(label_3);

        JLabel label_4 = new JLabel("Regulateur universel :");
        label_4.setBounds(535, 190, 129, 14);
        panel_Battery.add(label_4);

        JLabel lblModeDopration = new JLabel("Mode d'op\u00E9ration :");
        lblModeDopration.setBounds(535, 244, 129, 14);
        panel_Battery.add(lblModeDopration);

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(530, 239, 357, 83);
        panel.setBackground(new Color(208, 255, 208));
        panel_Battery.add(panel);

        JPanel panel_6 = new JPanel();
        panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_6.setBounds(497, 135, 434, 243);
        panel_6.setBackground(new Color(208, 255, 208));
        panel_Battery.add(panel_6);

        JLabel lblChoixDeRgulateur = new JLabel("Choix de R\u00E9gulateur :");
        panel_6.add(lblChoixDeRgulateur);
        lblChoixDeRgulateur.setForeground(new Color(0, 128, 0));
        lblChoixDeRgulateur.setFont(new Font("Tahoma", Font.BOLD, 12));

        JPanel panel_12 = new JPanel();
        panel_12.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_12.setBounds(496, 11, 435, 113);
        panel_Battery.add(panel_12);

        JLabel lblNewLabel = new JLabel("(Ci=C10 par d\u00E9fault)");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblNewLabel.setBounds(375, 214, 105, 14);
        panel_Battery.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_1.setBounds(10, 135, 475, 243);
        panel_Battery.add(panel_1);

// import battery data

        JButton btnImporta_battery = new JButton("Importer");
        btnImporta_battery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Id = 0;
                Capacite = 0;
                CapacityNominal = 0;
                CapacityInitial = 0;
                TensionNominal = 0;
                try {
                    // input life time
                    String sId = editorLifeTime.getText().toString();
                    Id = Integer.parseInt(sId);
                    // input C Nominal
                    String sCN = editorCapacityNominal.getText().toString();
                    CapacityNominal = Integer.parseInt(sCN);
                    // input C initial
                    String sCI = editorCapacityInitial.getText().toString();
                    CapacityInitial = Integer.parseInt(sCI);

                    if (CapacityInitial == 0) {
                        Capacite = CapacityNominal;
                    } else {
                        Capacite = CapacityInitial;
                    }

                    // input V nominal
                    String sNc = editorTension.getText().toString();
                    TensionNominal = Integer.parseInt(sNc);
                    //input T reference
                    String sTref = editorTemps.getText().toString();
                    Tref = Integer.parseInt(sTref);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Il y'a des erreurs véréfie les valeurs entré");
                }

                // input consomation mode
                if (rdbtnNormal.isSelected()) {
                    StorageCondition = "mode1";
                } else if (rdbtnOversize.isSelected()) {
                    StorageCondition = "mode2";
                } else {
                    StorageCondition = "mode3";
                }

                // input regulator mode
                if (regulateur_spinner.getValue().equals("   Activer")) {
                    if (rdbtnNewRadioButton.isSelected()) {
                        RegulatorMode = "Normal";
                    } else if (rdbtnConvertiseurMppt.isSelected()) {
                        RegulatorMode = "MPPT";
                    } else {
                        RegulatorMode = "DCDC";
                    }
                }


                if (Id != 0 && CapacityNominal != 0 && TensionNominal != 0) {
                    editCommentaire.setText("Opération Réussit");
                    editCommentaire.setForeground(Color.YELLOW);
                } else {
                    editCommentaire.setText("Opération Échec ,Vérifie que vous avez remplir tous les cases.");
                    editCommentaire.setForeground(new Color(255, 0, 0));
                }
            }
        });
        btnImporta_battery.setBounds(842, 397, 89, 23);
        panel_Battery.add(btnImporta_battery);
        //=========================================================================================================================//
        // 														 site Tabe 
        //=========================================================================================================================//

        JPanel panel_site = new JPanel();
        tabbedPane.addTab("Site", null, panel_site, null);
        panel_site.setLayout(null);

        JPanel panel_graph_photo = new JPanel() {
            BufferedImage bufferedImg;

            {
                try {
                    bufferedImg = ImageIO.read(this.getClass().getResource("img.PNG"));
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
        panel_graph_photo.setBounds(524, 21, 381, 201);
        panel_site.add(panel_graph_photo);

        JLabel lblNewLabel_4 = new JLabel("Instruction :");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_4.setForeground(new Color(255, 165, 0));
        lblNewLabel_4.setBounds(524, 277, 104, 14);
        panel_site.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("<html>" + s + "</html>");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblNewLabel_5.setForeground(new Color(255, 165, 0));
        lblNewLabel_5.setBounds(524, 290, 381, 73);
        panel_site.add(lblNewLabel_5);

        JPanel panel_7 = new JPanel();
        panel_7.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_7.setBackground(new Color(176, 224, 230));
        panel_7.setBounds(504, 264, 427, 114);
        panel_site.add(panel_7);

        JLabel lblFigPerte = new JLabel("fig : Perte de la capacit\u00E9 \u00E9nerg\u00E9tique \u00E0 diff\u00E9rentes temp\u00E9ratures.");
        lblFigPerte.setBounds(534, 239, 360, 14);
        panel_site.add(lblFigPerte);

        JLabel lblSiteGographique = new JLabel("Nom de Site :");
        lblSiteGographique.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblSiteGographique.setBounds(36, 43, 165, 30);
        panel_site.add(lblSiteGographique);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setBounds(129, 43, 211, 30);
        editorPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_site.add(editorPane);

        JLabel lblFixLaTemprature = new JLabel("Fixer la temp\u00E9rature ?");
        lblFixLaTemprature.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFixLaTemprature.setBounds(57, 207, 144, 38);
        panel_site.add(lblFixLaTemprature);

        JSpinner spinner = new JSpinner();
        spinner.setBackground(new Color(255, 255, 255));
        spinner.setModel(new SpinnerListModel(new String[]{"\tNon", "\tOui"}));
        spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
        spinner.setOpaque(true);
        spinner.setBounds(190, 212, 211, 27);
        panel_site.add(spinner);

        JLabel lblT = new JLabel("T =");
        lblT.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblT.setBounds(88, 256, 33, 30);
        panel_site.add(lblT);

        editorPane_1 = new JEditorPane();
        editorPane_1.setContentType("Integer/Double\r\n");
        editorPane_1.setBounds(131, 256, 55, 30);
        editorPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        editorPane_1.setEditable(false);
        panel_site.add(editorPane_1);

        JLabel lblLieu = new JLabel("Lieu");
        lblLieu.setForeground(new Color(0, 0, 255));
        lblLieu.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblLieu.setBounds(36, 11, 46, 14);
        panel_site.add(lblLieu);
        JLabel lblpassUsZipcode = new JLabel("<html>" + s3 + "</html>");
        lblpassUsZipcode.setBounds(36, 84, 447, 40);
        panel_site.add(lblpassUsZipcode);

        JPanel panel_10 = new JPanel();
        panel_10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_10.setBounds(27, 11, 456, 133);
        panel_site.add(panel_10);

        JLabel lblC_1 = new JLabel("C");
        lblC_1.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblC_1.setBounds(200, 256, 46, 30);
        panel_site.add(lblC_1);

        JLabel lblTempratureDeBatterie = new JLabel("Temp\u00E9rature de la batterie on op\u00E9ration");
        lblTempratureDeBatterie.setForeground(new Color(165, 42, 42));
        lblTempratureDeBatterie.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTempratureDeBatterie.setBounds(36, 170, 292, 26);
        panel_site.add(lblTempratureDeBatterie);
        JLabel lblCommeOnA = new JLabel("<html> la temp\u00E9rature est importante pour la dur\u00E9e de vie de la batterie, Si vous ne pouvez pas fix\u00E9 la temperature de la batterie laissez le choix sur (Non) et le syst\u00E8me va prendre la valeur de la temp\u00E9rature de lieu. </html>");
        lblCommeOnA.setForeground(new Color(165, 42, 42));
        lblCommeOnA.setBounds(36, 297, 433, 104);
        panel_site.add(lblCommeOnA);

        JPanel panel_11 = new JPanel();
        panel_11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_11.setBounds(27, 155, 456, 234);
        panel_site.add(panel_11);

        progressBar = new JProgressBar();
        progressBar.setBounds(27, 397, 456, 14);
        panel_site.add(progressBar);

        String s5 = "les paramètres de fonctionnement de règulateur seront automatiquement ajusté selon les proprietés de systeme.";

        JLabel lblVeuillezPatienter = new JLabel("Veuillez patienter..........");
        lblVeuillezPatienter.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblVeuillezPatienter.setBounds(525, 397, 236, 18);
        lblVeuillezPatienter.setVisible(false);
        panel_site.add(lblVeuillezPatienter);


        // Spinner listener
        spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                if (String.valueOf(spinner.getValue()).equals("	Oui")) {
                    editorPane_1.setEditable(true);
                    TfixeFlag = true;


                } else {
                    editorPane_1.setEditable(false);
                    TfixeFlag = false;

                }
            }
        });

        // button import site data
        JButton btnImporeterSite = new JButton("Importer");
        btnImporeterSite.setBounds(842, 397, 89, 23);
        panel_site.add(btnImporeterSite);

        btnImporeterSite.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                lblVeuillezPatienter.setVisible(true);
                Thread newThread = new Thread(() -> {
                    //clear data 
                    SOH.clear();
                    Cm.clear();
                    nt.clear();
                    gModele.clear();
                    Infomodele.clear();
                    days = 1;
                    ans = 0;

                    //////// fetching data from server

                    try {
                        place = editorPane.getText().toString();
                        Infomodele = new FetchData().startFetching(mUrl, place, progressBar);

                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                    if (Infomodele.size() > 360) {
                        editCommentaire.setText("Importation des données a été réussit ,Votre site est : " + Infomodele.get(0).getLocation());
                        editCommentaire.setForeground(Color.YELLOW);
                        lblVeuillezPatienter.setVisible(false);


                    } else {
                        editCommentaire.setText("Importation des données a été échoué ");
                        editCommentaire.setForeground(new Color(255, 0, 0));
                    }
                    if (TfixeFlag) {
                        String t = editorPane_1.getText().toString();
                        T = Integer.parseInt(t);
                    }
                });
                newThread.start();
            }
        });

        String s6 = "&nbsp &nbsp &nbsp Le systeme dépend des données météorologiques, et comme il n'y a pas de données gratuites disponibles par heure,"
                + " la moyenne quotidienne est utilisée pour l'arrondi, il n'y a donc pas d'option pour spécifier les heures d'utilisation par jour";

        String s7 = "Nous nous appuierons sur des données d'extrapolation pour les dernières années afin de collecter des données météorologiques.";

        //==============================================================================================================================//	
        // 														 Utilisation Tabe
        //=============================================================================================================================//	

        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("Utilisation", null, panel_3, null);
        panel_3.setLayout(null);

        JLabel lblDateDeDbut = new JLabel("Date de d\u00E9but :");
        lblDateDeDbut.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDateDeDbut.setBounds(64, 263, 101, 20);
        panel_3.add(lblDateDeDbut);

        JLabel lblDfinitionDeLa = new JLabel("S\u00E9lectioner le mode de consommation");
        lblDfinitionDeLa.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDfinitionDeLa.setForeground(new Color(0, 0, 0));
        lblDfinitionDeLa.setBounds(27, 26, 352, 14);
        panel_3.add(lblDfinitionDeLa);

        /// Consomation mode groupe
        ButtonGroup gConsomation = new ButtonGroup();

        JRadioButton rdbtnAnnuel = new JRadioButton("Annuelle");
        rdbtnAnnuel.setBounds(27, 47, 109, 23);
        rdbtnAnnuel.setSelected(true);
        panel_3.add(rdbtnAnnuel);

        JRadioButton rdbtnSaisonialle = new JRadioButton("Saisonnier");
        rdbtnSaisonialle.setBounds(483, 47, 109, 23);
        panel_3.add(rdbtnSaisonialle);

        JRadioButton rdbtnPeriodique = new JRadioButton("P\u00E9riodique");
        rdbtnPeriodique.setBounds(27, 206, 109, 20);
        panel_3.add(rdbtnPeriodique);

        JRadioButton rdbtnHebdomadaire = new JRadioButton("Hebdomadaire");
        rdbtnHebdomadaire.setBounds(483, 205, 142, 23);
        panel_3.add(rdbtnHebdomadaire);
        gConsomation.add(rdbtnAnnuel);
        gConsomation.add(rdbtnPeriodique);
        gConsomation.add(rdbtnHebdomadaire);
        gConsomation.add(rdbtnSaisonialle);

        /// weekly buttons
        JRadioButton rdbtnSamedi = new JRadioButton("Samedi");
        rdbtnSamedi.setBounds(509, 277, 89, 23);
        rdbtnSamedi.setEnabled(false);
        panel_3.add(rdbtnSamedi);

        JRadioButton rdbtnDimenche = new JRadioButton("Dimanche");
        rdbtnDimenche.setBounds(600, 277, 109, 23);
        rdbtnDimenche.setEnabled(false);
        panel_3.add(rdbtnDimenche);

        JRadioButton rdbtnLundi = new JRadioButton("Lundi");
        rdbtnLundi.setBounds(711, 277, 109, 23);
        rdbtnLundi.setEnabled(false);
        panel_3.add(rdbtnLundi);

        JRadioButton rdbtnMardi = new JRadioButton("Mardi");
        rdbtnMardi.setBounds(822, 277, 101, 23);
        rdbtnMardi.setEnabled(false);
        panel_3.add(rdbtnMardi);

        JRadioButton rdbtnMercrudi = new JRadioButton("Mercredi");
        rdbtnMercrudi.setBounds(509, 303, 89, 23);
        rdbtnMercrudi.setEnabled(false);
        panel_3.add(rdbtnMercrudi);

        JRadioButton rdbtnJudi = new JRadioButton("Jeudi");
        rdbtnJudi.setBounds(600, 303, 109, 23);
        rdbtnJudi.setEnabled(false);
        panel_3.add(rdbtnJudi);

        JRadioButton rdbtnVendredi = new JRadioButton("Vendredi");
        rdbtnVendredi.setBounds(711, 303, 109, 23);
        rdbtnVendredi.setEnabled(false);
        panel_3.add(rdbtnVendredi);
        rdbtnHebdomadaire.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent arg0) {
                if (!rdbtnHebdomadaire.isSelected()) {
                    rdbtnSamedi.setEnabled(false);
                    rdbtnDimenche.setEnabled(false);
                    rdbtnLundi.setEnabled(false);
                    rdbtnMardi.setEnabled(false);
                    rdbtnMercrudi.setEnabled(false);
                    rdbtnJudi.setEnabled(false);
                    rdbtnVendredi.setEnabled(false);
                } else {
                    rdbtnSamedi.setEnabled(true);
                    rdbtnDimenche.setEnabled(true);
                    rdbtnLundi.setEnabled(true);
                    rdbtnMardi.setEnabled(true);
                    rdbtnMercrudi.setEnabled(true);
                    rdbtnJudi.setEnabled(true);
                    rdbtnVendredi.setEnabled(true);
                }

            }

        });

        ////////// periodique buttons

        JSpinner spinner_2 = new JSpinner();
        spinner_2.setModel(new SpinnerDateModel(new Date(1577833200000L), new Date(1577833200000L), new Date(1606777200000L), Calendar.DAY_OF_MONTH));
        spinner_2.setEditor(new JSpinner.DefaultEditor(spinner_2));
        spinner_2.setBounds(181, 264, 156, 20);
        spinner_2.setEnabled(false);
        panel_3.add(spinner_2);

        JSpinner spinner_3 = new JSpinner();
        spinner_3.setModel(new SpinnerDateModel(new Date(1577833200000L), new Date(1577833200000L), new Date(1606777200000L), Calendar.DAY_OF_MONTH));
        spinner_3.setEditor(new JSpinner.DefaultEditor(spinner_3));
        spinner_3.setBounds(181, 300, 156, 21);
        spinner_3.setEnabled(false);
        panel_3.add(spinner_3);

        rdbtnPeriodique.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent event) {
                if (!rdbtnPeriodique.isSelected()) {
                    spinner_2.setEnabled(false);
                    spinner_3.setEnabled(false);
                } else {
                    spinner_2.setEnabled(true);
                    spinner_3.setEnabled(true);
                }
            }
        });


        JLabel lblannuelleUtilisation = new JLabel("-Annuelle : utilisation des batteries pendant toute l'ann\u00E9e.");
        lblannuelleUtilisation.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblannuelleUtilisation.setForeground(new Color(139, 0, 0));
        lblannuelleUtilisation.setBounds(10, 94, 380, 19);
        panel_3.add(lblannuelleUtilisation);

        JLabel lblMonsuelleUtilisation = new JLabel("-Monsuelle : utilisation des batteries pendant un intervalle de temps.");
        lblMonsuelleUtilisation.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblMonsuelleUtilisation.setForeground(new Color(128, 0, 0));
        lblMonsuelleUtilisation.setBounds(10, 118, 402, 20);
        panel_3.add(lblMonsuelleUtilisation);

        JLabel lblhebdomadaireUtilisation = new JLabel("-Hebdomadaire : utilisation des batteries dans quelques jours pendant l'ann\u00E9e.");
        lblhebdomadaireUtilisation.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblhebdomadaireUtilisation.setForeground(new Color(128, 0, 0));
        lblhebdomadaireUtilisation.setBounds(10, 137, 402, 25);
        panel_3.add(lblhebdomadaireUtilisation);


        JLabel lblDateDeFin = new JLabel("Date de fin :");
        lblDateDeFin.setBounds(64, 306, 69, 15);
        panel_3.add(lblDateDeFin);
        lblDateDeFin.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JPanel panel_5 = new JPanel();
        panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_5.setBounds(10, 229, 424, 123);
        panel_3.add(panel_5);

        JLabel lblNewLabel_1 = new JLabel("P\u00E9riodique");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        panel_5.add(lblNewLabel_1);

        JPanel panel_13 = new JPanel();
        panel_13.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_13.setBounds(459, 229, 472, 123);
        panel_3.add(panel_13);

        JLabel lblHbdomadiare = new JLabel("H\u00E9bdomadiare");
        lblHbdomadiare.setFont(new Font("Tahoma", Font.BOLD, 11));
        panel_13.add(lblHbdomadiare);

        //// seasonal buttons

        ButtonGroup seasonal = new ButtonGroup();

        JRadioButton rdbtnSummer = new JRadioButton("\u00C9t\u00E9");
        rdbtnSummer.setBounds(495, 92, 109, 23);
        rdbtnSummer.setEnabled(false);
        panel_3.add(rdbtnSummer);

        JRadioButton rdbtnWinter = new JRadioButton("Hiver");
        rdbtnWinter.setBounds(495, 117, 109, 23);
        rdbtnWinter.setEnabled(false);
        panel_3.add(rdbtnWinter);

        JRadioButton rdbtnFall = new JRadioButton("Automne");
        rdbtnFall.setBounds(495, 143, 109, 23);
        rdbtnFall.setEnabled(false);
        panel_3.add(rdbtnFall);

        JRadioButton rdbtnSpring = new JRadioButton("Printemps");
        rdbtnSpring.setBounds(495, 169, 109, 23);
        rdbtnSpring.setEnabled(false);
        panel_3.add(rdbtnSpring);
        seasonal.add(rdbtnSummer);
        seasonal.add(rdbtnWinter);
        seasonal.add(rdbtnFall);
        seasonal.add(rdbtnSpring);
        rdbtnSaisonialle.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent arg0) {
                if (!rdbtnSaisonialle.isSelected()) {
                    rdbtnWinter.setEnabled(false);
                    rdbtnFall.setEnabled(false);
                    rdbtnSpring.setEnabled(false);
                    rdbtnSummer.setEnabled(false);
                } else {
                    rdbtnWinter.setEnabled(true);
                    rdbtnFall.setEnabled(true);
                    rdbtnSpring.setEnabled(true);
                    rdbtnSummer.setEnabled(true);
                }
            }

        });

        JPanel panel_14 = new JPanel();
        panel_14.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_14.setBounds(459, 74, 472, 124);
        panel_3.add(panel_14);

        JLabel lblSaisonnier = new JLabel("Saisonnier");
        panel_14.add(lblSaisonnier);
        lblSaisonnier.setFont(new Font("Tahoma", Font.BOLD, 11));

        //import utilisation data
        JButton btnImporterUtilisation = new JButton("Importer");
        btnImporterUtilisation.setBounds(842, 397, 89, 23);
        panel_3.add(btnImporterUtilisation);
        btnImporterUtilisation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (rdbtnSaisonialle.isSelected()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    if (rdbtnSummer.isSelected()) {
                        try {
                            starDate = dateFormat.parse("2018-06-21");
                            endDate = dateFormat.parse("2018-09-21");
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (rdbtnWinter.isSelected()) {
                        try {
                            starDate = dateFormat.parse("2018-12-23");
                            endDate = dateFormat.parse("2018-03-21");
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (rdbtnFall.isSelected()) {
                        try {
                            starDate = dateFormat.parse("2018-09-21");
                            endDate = dateFormat.parse("2018-12-23");
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (rdbtnSpring.isSelected()) {
                        try {
                            starDate = dateFormat.parse("2018-03-21");
                            endDate = dateFormat.parse("2018-06-21");
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }


                }

                if (rdbtnPeriodique.isSelected()) {
                    starDate = (Date) spinner_2.getValue();
                    endDate = (Date) spinner_3.getValue();
                    Calendar c = Calendar.getInstance();
                    c.setTime(starDate);
                    c.add(Calendar.YEAR, -2);
                    starDate = c.getTime();
                    c.setTime(endDate);
                    c.add(Calendar.YEAR, -2);
                    endDate = c.getTime();
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        starDate = dateFormat.parse("2018-01-01");
                        endDate = dateFormat.parse("2018-12-31");
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                editCommentaire.setText("la Date a été définie. ");
                editCommentaire.setForeground(Color.YELLOW);

                if (rdbtnHebdomadaire.isSelected()) {
                    weekDays = -1;
                    HebdominaireFlag = true;

                    if (rdbtnSamedi.isSelected()) {
                        weekDays++;
                    }
                    ;
                    if (rdbtnDimenche.isSelected()) {
                        weekDays++;
                    }
                    ;
                    if (rdbtnLundi.isSelected()) {
                        weekDays++;
                    }
                    ;
                    if (rdbtnMardi.isSelected()) {
                        weekDays++;
                    }
                    ;
                    if (rdbtnMercrudi.isSelected()) {
                        weekDays++;
                    }
                    ;
                    if (rdbtnSamedi.isSelected()) {
                        weekDays++;
                    }
                    ;
                    if (rdbtnVendredi.isSelected()) {
                        weekDays++;
                    }
                    ;

                }
            }
        });
        ////////

        //=================================================================================================================================//	
        // 														 graphe Tabe
        //================================================================================================================================//	

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Graphes", null, panel_2, null);
        panel_2.setLayout(null);

        JButton btnDesign = new JButton("Tracer");
        btnDesign.setBounds(165, 150, 89, 23);
        panel_2.add(btnDesign);
        String s8 = "Dessiner le graphe de la durée de vie de la batterie en fonction de nombre des jours estimer.";
        JLabel lblNewLabel_9 = new JLabel("<html>Variation de l'état de santé de la batterie en fonction du temps (jours).</html>");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_9.setBounds(83, 32, 299, 88);
        panel_2.add(lblNewLabel_9);

        JPanel panel_17 = new JPanel();
        panel_17.setBackground(new Color(192, 192, 192));
        panel_17.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_17.setBounds(10, 11, 437, 187);
        panel_2.add(panel_17);
        String s9 = "Dessiner la variation de la capacité estimer par le modele en fonction temps.";
        JLabel lblDessinerLaVariation = new JLabel("<html>Variation de la capacit\u00E9 estim\u00E9e par le modèle en fonction du temps (jours).</html>");
        lblDessinerLaVariation.setBackground(new Color(255, 255, 255));
        lblDessinerLaVariation.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDessinerLaVariation.setBounds(83, 249, 338, 88);
        panel_2.add(lblDessinerLaVariation);

        JButton btnDessiner = new JButton("Tracer");
        btnDessiner.setBounds(177, 348, 89, 23);
        panel_2.add(btnDessiner);
        btnDessiner.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (!rdbtnHebdomadaire.isSelected()) {
                    if (String.valueOf(regulateur_spinner.getValue()).equals("Activer")) {
                        avecMpptCalcul();
                    } else {
                        NoMpptCalcul();
                    }
                } else if (rdbtnHebdomadaire.isSelected()) {
                    if (String.valueOf(regulateur_spinner.getValue()).equals("Activer")) {
                        HebdominaireAvecMpptCalcul();
                    } else {
                        HebdominaireNoMpptCalcul();
                    }
                }
                new Graphe(gModele, "capacity");

            }
        });


        JPanel panel_18 = new JPanel();
        panel_18.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_18.setBackground(new Color(192, 192, 192));
        panel_18.setBounds(10, 209, 437, 200);
        panel_2.add(panel_18);

        String s10 = "Dessinez le graphique de la courbe de température à l'emplacement défini au cours de l'année.";

        JLabel lblDissenerLaVariation = new JLabel("<html>Variation de la temp\u00E9rature \u00E0 l'emplacement d\u00E9fini au cours de l'ann\u00E9e.</html>");
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
        String s11 = "Dessiner la courbe de la durée de jour à l'emplacement défini pendant l'année ";
        JLabel lblDessinerLaCourbe = new JLabel("<html>" + s11 + "</html>");
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
                    bufferedImg = ImageIO.read(this.getClass().getResource("logo.jpg"));
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

        JPanel CDERLogo = new JPanel() {
            BufferedImage bufferedImg;

            {
                try {
                    bufferedImg = ImageIO.read(this.getClass().getResource("logo_cder.png"));
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

        String s1 = "-Il est n\u00E9cessaire d'appuyer sur le bouton (Importer) apr\u00E8s avoir rempli les cases.";
        JLabel lblIlEstNeccaire = new JLabel("<html>" + s1 + "</html>");
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

        String s2 = "Donn\u00E9es m\u00E9t\u00E9orologiques extraites du serveur de site www.worldweatheronline.com ";
        JLabel lblLeServeur = new JLabel("<html>" + s2 + "</html>");
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
                /// save data in local storage 
                String fileName = "PFE" + starDate.getTime();
				/*
				for(int i=0 ; i<gModele.size();i++) {
					FileOutputStream fileOutputStream;
					String outputText="Nombre de jours : "+ i+gModele.get(i).getSOH()+";"+gModele.get(i).getCm()+";";
					try {
					fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
					fileOutputStream.write(outputText.getBytes());
					fileOutputStream.close();
					} catch (Exception e) {
					e.printStackTrace();
					}
				}*/
                if (!rdbtnHebdomadaire.isSelected()) {
                    if (String.valueOf(regulateur_spinner.getValue()).equals("Activer")) {
                        avecMpptCalcul();
                    } else {
                        NoMpptCalcul();
                    }
                } else if (rdbtnHebdomadaire.isSelected()) {
                    if (String.valueOf(regulateur_spinner.getValue()).equals("Activer")) {
                        HebdominaireAvecMpptCalcul();
                    } else {
                        HebdominaireNoMpptCalcul();
                    }
                }
                new Graphe(gModele, "lifeTime");

            }
        });

    }


    public void avecMpptCalcul() {
        Boolean stopFlag = false;
        cteCalcul();

        ///////////////////////////////// Annuel et mensuel \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// avec regulateur et fixer la temerature		
        if (TfixeFlag) {
            int i = 0;
            while (!stopFlag) {
                if (i == nt.size()) {
                    i = 0;
                    ans++;
                    days = 0;
                }

                if (Infomodele.get(i).getDate().after(starDate) && Infomodele.get(i).getDate().before(endDate)) {

                    SOHo = SOHo - (ntFix * nwz1 * 86400);
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));

                } else {
                    /// srorage condition
				/*if(StorageCondition.equals("safe")) {
					SOHo=SOHo-(ntFix*nwz1*86400);
					gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				}else{
					SOHo=SOHo-(ntFix*10*nwz1*86400);;
					gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
				}*/

                }
                if (SOHo < 0.005) {
                    stopFlag = true;
                }
                days++;
                i++;
            }
        }
// avec regulateur et temperature variente		
        else {
            int i = 0;
            while (!stopFlag) {
                if (i == nt.size()) {
                    i = 0;
                    ans++;
                    days = 0;
                }
                if (Infomodele.get(i).getDate().after(starDate) && Infomodele.get(i).getDate().before(endDate)) {

                    SOHo = SOHo - (nt.get(i) * nwz1 * 86400);
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));
                } else {
                    // storage condition
					/*if(StorageCondition.equals("safe")) {
						SOHo=SOHo-(nt.get(i)*nwz1*86400);
						gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
					}else{
						SOHo=SOHo-(ntFix*10*nwz1*86400);;
						gModele.add((new GrapheModele(SOHo,SOHo*Capacite,(365*ans)+days)));
					}*/
                }
                if (SOHo < 0.005) {
                    stopFlag = true;
                }
                days++;
                i++;

            }
        }


    }


    public void NoMpptCalcul() {
        Boolean stopFlag = false;
        cteCalcul();
        Double nwz = 0.0;
//sans regulateur avec temperature fixe

        if (TfixeFlag) {
            int i = 0;
            while (!stopFlag) {
                if (i == nt.size()) {
                    i = 0;
                    ans++;
                    days = 0;
                }
                /**if(Vcell.get(i)>Vodd.get(i) && Vcell.get(i)<Vocc.get(i)) {
                 nwz=nwz1;
                 }else if((Vocc.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vss.get(i)) || (Vexx.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vodd.get(i)) ) {
                 nwz=10*nwz1;
                 }else {
                 nwz=100*nwz1;
                 }*/
                if (Infomodele.get(i).getDate().after(starDate) && Infomodele.get(i).getDate().before(endDate)) {
                    if (T > 25) {
                        nwz = 10 * nwz1;
                    } else if (T > 38) {
                        nwz = 100 * nwz1;
                    } else {
                        nwz = nwz1;
                    }

                    SOHo = SOHo - (ntFix * nwz * 86400);
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));
                } else {
                    if (T > 25) {
                        nwz = 10 * nwz1;
                    } else if (T > 38) {
                        nwz = 100 * nwz1;
                    } else {
                        nwz = nwz1;
                    }

                    SOHo = SOHo - 0.00001;
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));
                }
                if (SOHo < 0.005) {
                    stopFlag = true;
                }
                i++;
                days++;

            }
        }
//sans regulateur avec temperature variente
        else {
            int i = 0;
            while (!stopFlag) {
                if (i == nt.size()) {
                    i = 0;
                    ans++;
                    days = 0;
                }

                /**if(Vcell.get(i)>Vodd.get(i) && Vcell.get(i)<Vocc.get(i)) {
                 nwz=nwz1;
                 }else if((Vocc.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vss.get(i)) || (Vexx.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vodd.get(i)) ) {
                 nwz=10*nwz1;
                 }else {
                 nwz=100*nwz1;
                 }*/
                if (Infomodele.get(i).getDate().after(starDate) && Infomodele.get(i).getDate().before(endDate)) {
                    if (Infomodele.get(i).getTemperature() > 25 && Infomodele.get(i).getTemperature() < 38) {
                        nwz = 10 * nwz1;
                    } else if (Infomodele.get(i).getTemperature() > 38) {
                        nwz = 100 * nwz1;
                    } else {
                        nwz = nwz1;
                    }
                    SOHo = SOHo - (nt.get(i) * nwz * 86400);
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));
                } else {

                    SOHo = SOHo - 0.00001;
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));
                }
                if (SOHo < 0.005) {
                    stopFlag = true;
                }
                i++;
                days++;
            }

        }

    }
////////////////////////////////////////////////////////// Hebdominaire \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    public void HebdominaireAvecMpptCalcul() {
        Boolean stopFlag = false;
        cteCalcul();

// avec regulateur et fixer la temerature		
        if (TfixeFlag) {
            int i = 0;
            while (!stopFlag) {
                if (i == nt.size()) {
                    i = 0;
                    ans++;
                    days = 0;
                }

                if (Infomodele.get(i).getDate().getDay() < weekDays) {


                    SOHo = SOHo - (ntFix * nwz1 * 86400);
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));
                } else {


                    SOHo = SOHo - 0.0001;
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));

                }
                if (SOHo < 0.005) {
                    stopFlag = true;
                }
                days++;
                i++;
            }
        }
// avec regulateur et temperature variente		
        else {
            int i = 0;
            while (!stopFlag) {
                if (i == nt.size()) {
                    i = 0;
                    ans++;
                    days = 0;
                }
                if (Infomodele.get(i).getDate().getDay() < weekDays) {


                    SOHo = SOHo - (nt.get(i) * nwz1 * 86400);
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));

                } else {

                    SOHo = SOHo - 0.0001;
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));

                }
                if (SOHo < 0.005) {
                    stopFlag = true;
                }
                days++;
                i++;

            }
        }


    }


    public void HebdominaireNoMpptCalcul() {
        Boolean stopFlag = false;
        cteCalcul();
        Double nwz = 0.0;
//sans regulateur avec temperature fixe

        if (TfixeFlag) {
            int i = 0;
            while (!stopFlag) {
                if (i == nt.size()) {
                    i = 0;
                    ans++;
                    days = 0;
                }
                /**if(Vcell.get(i)>Vodd.get(i) && Vcell.get(i)<Vocc.get(i)) {
                 nwz=nwz1;
                 }else if((Vocc.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vss.get(i)) || (Vexx.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vodd.get(i)) ) {
                 nwz=10*nwz1;
                 }else {
                 nwz=100*nwz1;
                 }*/
                if (Infomodele.get(i).getDate().getDay() < weekDays) {
                    if (Infomodele.get(i).getTemperature() > 25) {
                        nwz = 10 * nwz1;
                    } else if (Infomodele.get(i).getTemperature() > 30) {
                        nwz = 100 * nwz1;
                    } else {
                        nwz = nwz1;
                    }
                    SOHo = SOHo - (ntFix * nwz * 86400);
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));

                } else {
                    if (Infomodele.get(i).getTemperature() > 25) {
                        nwz = 10 * nwz1;
                    } else if (Infomodele.get(i).getTemperature() > 30) {
                        nwz = 100 * nwz1;
                    } else {
                        nwz = nwz1;
                    }
                    SOHo = SOHo - 0.00001;
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));

                }
                if (SOHo < 0.005) {
                    stopFlag = true;
                }
                i++;
                days++;

            }
        }
//sans regulateur avec temperature variente
        else {
            int i = 0;
            while (!stopFlag) {
                if (i == nt.size()) {
                    i = 0;
                    ans++;
                    days = 0;
                }
                /**if(Vcell.get(i)>Vodd.get(i) && Vcell.get(i)<Vocc.get(i)) {
                 nwz=nwz1;
                 }else if((Vocc.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vss.get(i)) || (Vexx.get(i)<=Vcell.get(i) && Vcell.get(i)<=Vodd.get(i)) ) {
                 nwz=10*nwz1;
                 }else {
                 nwz=100*nwz1;
                 }*/
                if (Infomodele.get(i).getDate().getDay() < weekDays) {
                    if (Infomodele.get(i).getTemperature() > 25) {
                        nwz = 10 * nwz1;
                    } else if (Infomodele.get(i).getTemperature() > 30) {
                        nwz = 100 * nwz1;
                    } else {
                        nwz = nwz1;
                    }
                    SOHo = SOHo - (nt.get(i) * nwz * 86400);
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));

                } else {
                    SOHo = SOHo - 0.00001;
                    gModele.add((new GrapheModele(SOHo, SOHo * Capacite, (365 * ans) + days)));

                }
                if (SOHo < 0.005) {
                    stopFlag = true;
                }
                i++;
                days++;
            }

        }

    }

    public void cteCalcul() {
        nwz1 = (3.17 * Math.pow(10, -8)) / Id;

        ntFix = Math.pow(2, (T - 25) / 10);

        for (int i = 0; i < Infomodele.size(); i++) {
            nt.add(Math.pow(2, (Infomodele.get(i).getTemperature() - 25) / 10));
            Vss.add(Vs - (0.005 * (Infomodele.get(i).getTemperature() - 25)));
            Vocc.add(Voc - (0.005 * (Infomodele.get(i).getTemperature() - 25)));
            Vodd.add(Vod - (0.005 * (Infomodele.get(i).getTemperature() - 25)));
            Vexx.add(Vex - (0.005 * (Infomodele.get(i).getTemperature() - 25)));
            Vnn.add(Vn - (0.005 * (Infomodele.get(i).getTemperature() - 25)));

        }
    }
}
