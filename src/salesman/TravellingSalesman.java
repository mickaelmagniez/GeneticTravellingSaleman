package salesman;

import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

import javax.swing.*;

public class TravellingSalesman extends Applet {

	boolean isStandalone = false;
	BorderLayout borderLayout1 = new BorderLayout();
	JTabbedPane Onglets = new JTabbedPane();
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel PanelVisu = new JPanel();
	JPanel PanelConfig = new JPanel();
	JPanel PanelCanvasPays = new JPanel();
	JPanel PanelInfos = new JPanel();
	JPanel PanelCanvasCourbes = new JPanel();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JTextField TexteNbIndividus = new JTextField();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel7 = new JLabel();
	JLabel jLabel8 = new JLabel();
	JTextField TexteNbStagMax = new JTextField();
	JTextField TexteNbGenerationsMax = new JTextField();
	JTextField TexteNbVilles = new JTextField();
	JLabel jLabel3 = new JLabel();
	JTextField TexteProbaMutationInd = new JTextField();
	JTextField TexteTauxReproduction = new JTextField();
	JLabel jLabel4 = new JLabel();
	JTextField TexteProbaMutationGene = new JTextField();
	JLabel jLabel5 = new JLabel();
	JToggleButton BoutonDepart = new JToggleButton();
	JToggleButton BoutonDecroise = new JToggleButton();
	JToggleButton BoutonMute = new JToggleButton();
	JToggleButton BoutonArret = new JToggleButton();
	JToggleButton BoutonRandom = new JToggleButton();
	JLabel jLabel9 = new JLabel();
	JLabel jLabel10 = new JLabel();
	JLabel jLabel11 = new JLabel();
	JLabel jLabelAlgoSelection = new JLabel();
	JComboBox ComboAlgoCroisement = new JComboBox();
	JComboBox ComboAlgoSelection = new JComboBox();
	JLabel jLabel12 = new JLabel();
	JCheckBox CheckAlgoRepro = new JCheckBox();
	JComboBox ComboAlgoRepro = new JComboBox();
	JCheckBox CheckAlgoDecroise = new JCheckBox();
	JComboBox ComboAlgoDecroisement = new JComboBox();
	JTextField TexteAdaptationMax = new JTextField();
	JLabel jLabel13 = new JLabel();
	JLabel label = new JLabel();
	JLabel jLabel15 = new JLabel();
	JLabel jLabel16 = new JLabel();
	JTextField TexteNbGenerations = new JTextField();
	JTextField TexteDistance = new JTextField();
	JTextField TexteNbIndividusTestes = new JTextField();
	BorderLayout borderLayout3 = new BorderLayout();

	public World pays;

	public Curves courbes;

	public Travellers population;

	public Main main;

	public Thread leThread = null;
	BorderLayout borderLayout4 = new BorderLayout();

	public String getParameter(String key, String def) {
		return isStandalone ? System.getProperty(key, def)
				: (getParameter(key) != null ? getParameter(key) : def);
	}

	public TravellingSalesman() {
	}

	public void init() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(borderLayout2);
		PanelVisu.setLayout(null);
		PanelCanvasPays.setBackground(Color.white);
		PanelCanvasPays.setBounds(new Rectangle(294, 71, 401, 400));
		PanelCanvasPays.setLayout(borderLayout3);
		PanelCanvasCourbes.setBackground(Color.white);
		PanelCanvasCourbes.setBounds(new Rectangle(0, 242, 290, 230));
		PanelCanvasCourbes.setLayout(borderLayout4);
		jLabel1.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel1.setText("Individuals count :");
		jLabel1.setBounds(new Rectangle(2, 25, 140, 20));
		jLabel2.setFont(new java.awt.Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		jLabel2.setText("Configuration :");
		jLabel2.setBounds(new Rectangle(12, 3, 192, 15));
		TexteNbIndividus.setText("100");
		TexteNbIndividus.setBounds(new Rectangle(200, 25, 50, 17));
		jLabel6.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel6.setText("Stagnations max :");
		jLabel6.setBounds(new Rectangle(2, 50, 160, 20));
		jLabel7.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel7.setText("Generation max : ");
		jLabel7.setBounds(new Rectangle(2, 77, 160, 20));
		jLabel8.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel8.setText("Number of towns :");
		jLabel8.setBounds(new Rectangle(1, 104, 160, 20));
		TexteNbStagMax.setText("500");
		TexteNbStagMax.setBounds(new Rectangle(200, 50, 50, 17));
		TexteNbGenerationsMax.setText("8000");
		TexteNbGenerationsMax.setBounds(new Rectangle(200, 77, 50, 17));
		TexteNbVilles.setText("200");
		TexteNbVilles.setBounds(new Rectangle(199, 104, 50, 17));
		PanelConfig.setLayout(null);
		jLabel3.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel3.setText("Reproductive rate :");
		jLabel3.setBounds(new Rectangle(50, 50, 150, 20));
		TexteProbaMutationInd.setText("0.05");
		TexteProbaMutationInd.setBounds(new Rectangle(230, 207, 32, 20));
		TexteTauxReproduction.setText("0.5");
		TexteTauxReproduction.setBounds(new Rectangle(215, 49, 33, 20));
		jLabel4.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel4.setText("Gene mutation probability :");
		jLabel4.setBounds(new Rectangle(50, 182, 138, 13));
		TexteProbaMutationGene.setText("0.05");
		TexteProbaMutationGene.setBounds(new Rectangle(230, 182, 33, 20));
		jLabel5.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel5.setText("Individual mutation probability :");
		jLabel5.setBounds(new Rectangle(50, 207, 152, 13));
		BoutonDepart.setText("Start");
		BoutonDepart.setBounds(new Rectangle(21, 148, 86, 22));
		BoutonDepart
				.addMouseListener(new VoyageurDeCommerce_BoutonDepart_mouseAdapter(
						this));
		BoutonArret.setText("Stop");
		BoutonArret.setBounds(new Rectangle(128, 149, 79, 22));
		BoutonArret
				.addMouseListener(new VoyageurDeCommerce_BoutonArret_mouseAdapter(
						this));
		BoutonRandom.setText("Random");
		BoutonRandom.setBounds(new Rectangle(53, 180, 124, 24));
		BoutonRandom
				.addMouseListener(new VoyageurDeCommerce_BoutonRandom_mouseAdapter(
						this));
		BoutonDecroise.setText("Uncross");
		BoutonDecroise.setBounds(new Rectangle(21, 210, 84, 24));
		BoutonDecroise
				.addMouseListener(new VoyageurDeCommerce_BoutonDecroise_mouseAdapter(
						this));
		BoutonMute.setText("Diversify");
		BoutonMute.setBounds(new Rectangle(128, 210, 84, 24));
		BoutonMute
				.addMouseListener(new VoyageurDeCommerce_BoutonMute_mouseAdapter(
						this));
		jLabel9.setFont(new java.awt.Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		jLabel9.setText("Reproduction");
		jLabel9.setBounds(new Rectangle(35, 22, 84, 15));
		jLabel10.setFont(new java.awt.Font("Tahoma", Font.BOLD | Font.ITALIC,
				12));
		jLabel10.setToolTipText("");
		jLabel10.setText("Mutations");
		jLabel10.setBounds(new Rectangle(35, 148, 68, 15));
		jLabel11.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel11.setText("Crossing Algo :");
		jLabel11.setBounds(new Rectangle(50, 80, 150, 20));
		jLabelAlgoSelection
				.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabelAlgoSelection.setText("Selection algo:");
		jLabelAlgoSelection.setBounds(new Rectangle(50, 110, 150, 20));
		ComboAlgoCroisement.setEditor(null);
		ComboAlgoCroisement.setBounds(new Rectangle(213, 78, 152, 23));
		ComboAlgoSelection.setEditor(null);
		ComboAlgoSelection.setBounds(new Rectangle(213, 108, 152, 23));
		jLabel12.setFont(new java.awt.Font("Tahoma", Font.BOLD | Font.ITALIC,
				12));
		jLabel12.setText("Heuristiques");
		jLabel12.setBounds(new Rectangle(34, 261, 76, 15));
		CheckAlgoRepro.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		CheckAlgoRepro.setText("Reproduction Heuristic");
		CheckAlgoRepro.setBounds(new Rectangle(37, 291, 164, 21));
		CheckAlgoRepro.doClick();
		CheckAlgoDecroise.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		CheckAlgoDecroise.setText("Graph uncross Heuristic");
		CheckAlgoDecroise.setBounds(new Rectangle(37, 340, 202, 21));
		TexteAdaptationMax.setText("0");
		TexteAdaptationMax.setBounds(new Rectangle(134, 0, 40, 18));
		jLabel13.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel13.setText("Number of generation :");
		jLabel13.setBounds(new Rectangle(0, 30, 150, 15));
		PanelInfos.setLayout(null);
		label.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		label.setText("Distance :");
		label.setBounds(new Rectangle(200, 0, 150, 15));
		jLabel15.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel15.setText("Tested individuals :");
		jLabel15.setBounds(new Rectangle(200, 34, 150, 15));
		jLabel16.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
		jLabel16.setText("Max Adaptation");
		jLabel16.setBounds(new Rectangle(0, 0, 150, 15));
		TexteNbGenerations.setText("0");
		TexteNbGenerations.setBounds(new Rectangle(134, 30, 40, 18));
		TexteDistance.setText("0");
		TexteDistance.setBounds(new Rectangle(350, 0, 40, 18));
		TexteNbIndividusTestes.setText("0");
		TexteNbIndividusTestes.setBounds(new Rectangle(350, 30, 40, 18));
		ComboAlgoDecroisement.setBounds(new Rectangle(266, 338, 121, 23));
		ComboAlgoRepro.setBounds(new Rectangle(268, 287, 115, 23));
		PanelInfos.setBounds(new Rectangle(293, 0, 403, 71));

		ComboAlgoCroisement.addItem("Crossing-Over (2point)");
		ComboAlgoCroisement.addItem("Crossing-Over (1point)");

		ComboAlgoSelection.addItem("Mix");
		ComboAlgoSelection.addItem("Rank Roulette");
		ComboAlgoSelection.addItem("Elitism");

		ComboAlgoRepro.addItem("Greedy");
		ComboAlgoRepro.addItem("Super Greedy");

		ComboAlgoDecroisement.addItem("Starting Population");
		ComboAlgoDecroisement.addItem("Everybody");

		pays = new World(Integer.parseInt(TexteNbVilles.getText()));
		this.PanelCanvasPays.add(pays);

		String[] legendeCourbes = { "Adaptation", "" };

		courbes = new Curves(-1, 1, 1, "", "", legendeCourbes);
		this.PanelCanvasCourbes.add(courbes);

		this.add(Onglets, java.awt.BorderLayout.CENTER);
		PanelConfig.add(jLabel9, null);
		PanelConfig.add(jLabel3, null);
		PanelConfig.add(TexteTauxReproduction, null);
		PanelConfig.add(jLabel10, null);
		PanelConfig.add(jLabel4, null);
		PanelConfig.add(TexteProbaMutationGene, null);
		PanelConfig.add(jLabel5, null);
		PanelConfig.add(jLabel12, null);
		PanelConfig.add(CheckAlgoRepro, null);
		PanelConfig.add(CheckAlgoDecroise, null);
		PanelConfig.add(ComboAlgoRepro, null);
		PanelConfig.add(jLabel11, null);
		PanelConfig.add(jLabelAlgoSelection, null);
		PanelConfig.add(TexteProbaMutationInd, null);
		PanelConfig.add(ComboAlgoCroisement, null);
		PanelConfig.add(ComboAlgoSelection, null);
		PanelConfig.add(ComboAlgoDecroisement, null);
		Onglets.add(PanelVisu, "Résultat");

		PanelInfos.add(jLabel13, null);
		PanelInfos.add(jLabel16, null);
		PanelInfos.add(label, null);
		PanelInfos.add(jLabel15, null);
		PanelInfos.add(TexteAdaptationMax, null);
		PanelInfos.add(TexteNbGenerations, null);
		PanelInfos.add(TexteDistance, null);
		PanelInfos.add(TexteNbIndividusTestes, null);
		PanelVisu.add(PanelCanvasCourbes, null);
		PanelVisu.add(jLabel1, null);
		PanelVisu.add(TexteNbIndividus, null);
		PanelVisu.add(TexteNbStagMax, null);
		PanelVisu.add(jLabel6, null);
		PanelVisu.add(jLabel7, null);
		PanelVisu.add(TexteNbGenerationsMax, null);
		PanelVisu.add(jLabel8, null);
		PanelVisu.add(TexteNbVilles, null);
		PanelVisu.add(BoutonRandom, null);
		PanelVisu.add(BoutonDepart, null);
		PanelVisu.add(BoutonDecroise, null);
		PanelVisu.add(BoutonMute, null);
		PanelVisu.add(jLabel2, null);
		PanelVisu.add(BoutonArret, null);
		PanelVisu.add(PanelCanvasPays, null);
		PanelVisu.add(PanelInfos, null);
		Onglets.add(PanelConfig, "Configuration");

	}

	public void start() {

		String plaf = "";

		plaf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(plaf);
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}
		courbes.initialisation();
		pays.paint(pays.getGraphics());
	}

	public void stop() {
	}

	public void destroy() {
	}

	public String getAppletInfo() {
		return "Information d'Applet";
	}

	public String[][] getParameterInfo() {
		return null;
	}

	public int nbIndividus() {
		return Integer.parseInt(TexteNbIndividus.getText());
	}

	public int nbIndividusTestes() {
		return Integer.parseInt(TexteNbIndividusTestes.getText());
	}

	public double tauxReproduction() {
		return Double.parseDouble(this.TexteTauxReproduction.getText());
	}

	public double probaMutationIndividu() {
		return Double.parseDouble(this.TexteProbaMutationInd.getText());
	}

	public double probaMutationGene() {
		return Double.parseDouble(this.TexteProbaMutationGene.getText());
	}

	public int nbGeneStag() {
		return Integer.parseInt(this.TexteNbStagMax.getText());
	}

	public int nbGeneMax() {
		return Integer.parseInt(this.TexteNbGenerationsMax.getText());
	}

	public String croisement() {
		String s = new String();
		if (this.CheckAlgoRepro.isSelected())
			s = (String) this.ComboAlgoRepro.getSelectedItem();
		else
			s = (String) this.ComboAlgoCroisement.getSelectedItem();
		return s;
	}

	public String selection() {
		String s = new String();

		s = (String) this.ComboAlgoSelection.getSelectedItem();

		return s;
	}

	public boolean decroise() {
		return this.CheckAlgoDecroise.isSelected();
	}

	public void BoutonDepart_mouseExited(MouseEvent e) {

	}

	public void BoutonDepart_mousePressed(MouseEvent e) {

	}

	public void BoutonDepart_mouseClicked(MouseEvent e) {
		if (leThread != null) {
			leThread.stop();
		}

		main = new Main(this);
		leThread = new Thread(main);

		leThread.start();

	}

	public void BoutonArret_mouseClicked(MouseEvent e) {
		if ((leThread != null)) {

			leThread.stop();
			leThread = null;

			/*
			 * depart.enable(); nouveauProbleme.enable(); fin.disable();
			 * tableau.enable();
			 */
		}
	}

	public void BoutonRandom_mouseClicked(MouseEvent e) {
		pays.newCountry(Integer.parseInt(this.TexteNbVilles.getText()));
	}

	public void BoutonDecroise_mouseClicked(MouseEvent e) {
		population.setUncross();
	}

	public void BoutonMute_mouseClicked(MouseEvent e) {
		population.setMute();
	}

}

class VoyageurDeCommerce_BoutonArret_mouseAdapter extends MouseAdapter {
	private TravellingSalesman adaptee;

	VoyageurDeCommerce_BoutonArret_mouseAdapter(TravellingSalesman adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.BoutonArret_mouseClicked(e);
	}
}

class VoyageurDeCommerce_BoutonRandom_mouseAdapter extends MouseAdapter {
	private TravellingSalesman adaptee;

	VoyageurDeCommerce_BoutonRandom_mouseAdapter(TravellingSalesman adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.BoutonRandom_mouseClicked(e);
	}
}

class VoyageurDeCommerce_BoutonMute_mouseAdapter extends MouseAdapter {
	private TravellingSalesman adaptee;

	VoyageurDeCommerce_BoutonMute_mouseAdapter(TravellingSalesman adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.BoutonMute_mouseClicked(e);
	}
}

class VoyageurDeCommerce_BoutonDecroise_mouseAdapter extends MouseAdapter {
	private TravellingSalesman adaptee;

	VoyageurDeCommerce_BoutonDecroise_mouseAdapter(TravellingSalesman adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.BoutonDecroise_mouseClicked(e);
	}
}

class VoyageurDeCommerce_BoutonDepart_mouseAdapter extends MouseAdapter {
	private TravellingSalesman adaptee;

	VoyageurDeCommerce_BoutonDepart_mouseAdapter(TravellingSalesman adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseExited(MouseEvent e) {
		adaptee.BoutonDepart_mouseExited(e);
	}

	public void mousePressed(MouseEvent e) {
		adaptee.BoutonDepart_mousePressed(e);
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.BoutonDepart_mouseClicked(e);
	}
}
