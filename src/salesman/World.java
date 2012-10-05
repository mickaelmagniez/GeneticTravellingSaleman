package salesman;

import java.awt.*;
import java.util.*;

public class World extends Canvas {

	private static final int Coordinate_LIMIT = 50;
	private static final int MARGINS = 10;
	private static final int CROSS_SIZE = 1;
	private Random moRandom = new Random();
	private Coordinate[] moTowns;
	private Genotype moGenes;
	private int miTownCount;
	private Traveller moSolution;

	World(int _townCount) {
		miTownCount = _townCount;
		moTowns = new Coordinate[miTownCount];
		moGenes = new Genotype();
		for (int i = 0; i < miTownCount; i++) {
			moTowns[i] = new Coordinate(moRandom.nextDouble() * Coordinate_LIMIT
					* 2 - Coordinate_LIMIT, moRandom.nextDouble()
					* Coordinate_LIMIT * 2 - Coordinate_LIMIT);
			moGenes.addGene(new TownGene(i));
		}
	}

	public void newCountry(int _townCount) {
		miTownCount = _townCount;
		moTowns = new Coordinate[miTownCount];
		moGenes = new Genotype();
		moSolution = null;
		for (int i = 0; i < miTownCount; i++) {
			moTowns[i] = new Coordinate(moRandom.nextDouble() * Coordinate_LIMIT
					* 2 - Coordinate_LIMIT, moRandom.nextDouble()
					* Coordinate_LIMIT * 2 - Coordinate_LIMIT);
			moGenes.addGene(new TownGene(i));
		}
		paint(getGraphics());
	}

	public Genotype genesBase() {
		return moGenes.cloneMe();
	}

	int townCount() {
		return miTownCount;
	}

	public void paint(Graphics g) {

		if (moSolution != null) {
			drawCurves(g, moSolution.genes());
		} else {
			g.clearRect(0, 0, size().width, size().height);
		}
		drawBase(g);
	}

	private void drawCurves(Graphics g, Genotype _genes) {
		int townS, townD;
		int xTempVS, yTempVS, xTempVD, yTempVD;

		g.clearRect(0, 0, size().width, size().height);
		for (int i = 0; i < moSolution.getGenotypeSize(); i++) {
			townS = ((TownGene) _genes.geneAt(i)).getTownNumber();
			townD = ((TownGene) _genes.geneAt((i + 1) % moSolution.getGenotypeSize()))
					.getTownNumber();
			xTempVS = moTowns[townS].convertX(this.size().width - MARGINS * 2,
					Coordinate_LIMIT) + MARGINS;
			yTempVS = moTowns[townS].convertY(this.size().height - MARGINS * 2,
					Coordinate_LIMIT) + MARGINS;
			xTempVD = moTowns[townD].convertX(this.size().width - MARGINS * 2,
					Coordinate_LIMIT) + MARGINS;
			yTempVD = moTowns[townD].convertY(this.size().height - MARGINS * 2,
					Coordinate_LIMIT) + MARGINS;
			g.drawLine(xTempVS, yTempVS, xTempVD, yTempVD);
		}
	}

	private void drawBase(Graphics g) {
		int xTemp, yTemp;

		g.setFont(new Font("Arial", Font.BOLD, 14));
		FontMetrics fM = g.getFontMetrics();
		g.setColor(Color.red);
		for (int i = 0; i < miTownCount; i++) {
			xTemp = moTowns[i].convertX(this.size().width - MARGINS * 2,
					Coordinate_LIMIT) + MARGINS;
			yTemp = moTowns[i].convertY(this.size().height - MARGINS * 2,
					Coordinate_LIMIT) + MARGINS;

			g.drawLine(xTemp - CROSS_SIZE, yTemp, xTemp + CROSS_SIZE, yTemp);
			g.drawLine(xTemp, yTemp - CROSS_SIZE, xTemp, yTemp + CROSS_SIZE);
		}

		g.setColor(Color.black);
	}

	void newSolution(Traveller _solution) {
		Graphics leg = getGraphics();
		moSolution = _solution;
		paint(leg);
	}

	public Traveller currentSolution() {
		return moSolution;
	}

	public double townsDistance(int i, int j) {
		return moTowns[i].distance(moTowns[j]);
	}
}
