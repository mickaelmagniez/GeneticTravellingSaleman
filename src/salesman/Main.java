package salesman;

public class Main implements Runnable {
	public Main() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private Travellers population;
	private TravellingSalesman interfaceVDC;
	Main(TravellingSalesman laInterface) {
		interfaceVDC = laInterface;
	}

	public void run() {
		double[] moyenneEtMeilleur = new double[2];
		int decroise;

		if (!interfaceVDC.decroise())
			decroise = 0;
		else if ((String) interfaceVDC.ComboAlgoDecroisement.getSelectedItem() == "Tout le monde")
			decroise = 2;
		else
			decroise = 1;
		population = new Travellers(interfaceVDC.nbIndividus(),
				interfaceVDC.tauxReproduction(),
				interfaceVDC.probaMutationIndividu(),
				interfaceVDC.probaMutationGene(), interfaceVDC.nbGeneStag(),
				interfaceVDC.nbGeneMax(), interfaceVDC.pays,
				interfaceVDC.croisement(), interfaceVDC.selection(), decroise

		);
		interfaceVDC.pays
				.newSolution((Traveller) population.bestInd());
		interfaceVDC.population = population;
		interfaceVDC.courbes.initialisation();
		moyenneEtMeilleur[0] = population.bestAdaptation();
		interfaceVDC.courbes.nouveauPoint(moyenneEtMeilleur);

		while (!population.testEndCompute()) {
			population.evolution();
			moyenneEtMeilleur[0] = population.bestAdaptation();
			interfaceVDC.courbes.nouveauPoint(moyenneEtMeilleur);
			if (population.bestInd().getAdaptation() != interfaceVDC.pays
					.currentSolution().getAdaptation()) {
				interfaceVDC.pays.newSolution((Traveller) population
						.bestInd());
			}

			interfaceVDC.TexteAdaptationMax.setText(String.valueOf(population
					.bestAdaptation()));
			interfaceVDC.TexteDistance.setText(String.valueOf((100 * population
					.bestInd().getGenotypeSize())
					/ population.bestAdaptation()));
			interfaceVDC.TexteNbGenerations.setText(String.valueOf(population
					.getGenerationNum()));
			interfaceVDC.TexteNbIndividusTestes.setText(String
					.valueOf((int) Math.floor(population.getGenerationNum()
							* interfaceVDC.nbIndividus()
							* interfaceVDC.tauxReproduction()
							+ interfaceVDC.nbIndividus())));
		}
		interfaceVDC.leThread = null;
	}

	private void jbInit() throws Exception {
	}

}
