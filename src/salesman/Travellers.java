package salesman;

import java.util.Random;
import java.util.Vector;

public class Travellers extends Population {
	double mdProbablilityBestWin;

	String msCrossType;
	String msSelectionType;
	int miUncross;
	public boolean mbUncross = false;
	public boolean mbMutation = false;

	Travellers(int _iMaxInd, double _mortality,
			double _mutationIndProb, double _mutationGebeProb,
			int _MaxStagnation, int _iMaxGeneration, World _world,
			String _cross, String _selection, int _uncross

	) {
		miIndMax = _iMaxInd;
		mdMortality = (_mortality > 0 || _mortality < 1) ? _mortality : 0.5;
		mdMutationProbability = (_mutationIndProb > 0 || _mutationIndProb < 1) ? _mutationIndProb
				: 0.5;
		miGenerationNum = 0;
		miStagnationMax = _MaxStagnation;
		miGenerationMax = _iMaxGeneration;
		mdAcceptableValue = Double.POSITIVE_INFINITY;

		msCrossType = _cross;
		msSelectionType = _selection;
		miUncross = _uncross;

		moPopulation = new IndividualVector();
		Traveller trav;
		for (int i = 0; i < _iMaxInd; i++) {
			trav = new Traveller(0, _mutationGebeProb, _world);
			if (miUncross > 0)
				trav.uncross();
			moPopulation.insertIndSort(trav);

		}
	}

	public void selection() {
		IndividualVector newpop = new IndividualVector();
		int nbElite = 0;
		int nbRoulette = 0;
		if (msSelectionType == "Mix")
			nbElite = nbRoulette = getIndToKeep() / 2;
		if (msSelectionType == "Elitism")
			nbElite = getIndToKeep();
		if (msSelectionType == "Rank Roulette")
			nbRoulette = getIndToKeep();

		for (int i = 0; i < nbElite; i++) {
			newpop.insertIndSort((Individual) moPopulation.elementAt(i));
		}

		if (nbRoulette > 0) {

			int prem = 0;
			if (nbElite > 0) {
				prem = nbElite;
			}

			Vector roulette = new Vector();
			int nb = Math.min((int) Math.floor(nbRoulette * 1.5),
					moPopulation.getSize());

			for (int i = prem; i < prem + nb; i++) {
				for (int j = 0; j < moPopulation.getSize() - (i - prem); j++) {
					roulette.add(new Integer(i));

				}
			}

			Random rand = new Random();

			for (int i = 0; i < nbRoulette && roulette.size() > 0; i++) {
				int taken = ((Integer) (roulette.elementAt(rand
						.nextInt(roulette.size())))).intValue();

				int j = 0;
				while (j < roulette.size())
					if (((Integer) roulette.elementAt(j)).intValue() == taken)
						roulette.remove(j);
					else
						j++;

				newpop.insertIndSort((Individual) moPopulation.elementAt(taken));
			}
		}

		moPopulation.clear();

		this.moPopulation = newpop;

		if (mbUncross)
			uncross();
		if (mbMutation)
			mute();

	}

	public void setMute() {
		mbMutation = true;
	}

	public void mute() {
		for (int i = 0; i < moPopulation.getSize(); i++)
			((Traveller) moPopulation.indAt(i)).diversify();
		mbMutation = false;
	}

	public void setUncross() {
		mbUncross = true;
	}

	public void uncross() {
		for (int i = 0; i < moPopulation.getSize(); i++)
			((Traveller) moPopulation.indAt(i)).uncross();
		mbUncross = false;
	}

	private IndividualVector selectParent() {
		IndividualVector couple = new IndividualVector();
		couple.insertIndSort(moPopulation.indAt((int) Math.floor(moRand
				.nextDouble() * this.moPopulation.getSize())));
		couple.insertIndSort(moPopulation.indAt((int) Math.floor(moRand
				.nextDouble() * this.moPopulation.getSize())));
		return couple;
	}

	public void cross() {
		crossLimitAndMutation(miIndMax);
	}
	private void crossLimitAndMutation(int leNombreIndividusFinal) {
		IndividualVector couple;
		IndividualVector children = new IndividualVector();

		while (populationSize() < leNombreIndividusFinal) {
			couple = selectParent();

			if (msCrossType.equals("Crossing-Over (1point)"))
				children = ((Traveller) couple.indAt(0)).OnePointCrossOver(
						couple.indAt(1), getGenerationNum());
			if (msCrossType.equals("Crossing-Over (2point)"))
				children = ((Traveller) couple.indAt(0)).TwoPointCrossOver(
						couple.indAt(1), getGenerationNum());
			if (msCrossType.equals("Greedy"))
				children = ((Traveller) couple.indAt(0)).GreedyCrossOver(
						couple.indAt(1), getGenerationNum());
			if (msCrossType.equals("Super Greedy"))
				children = ((Traveller) couple.indAt(0)).greedy(couple.indAt(1),
						getGenerationNum());

			if (miUncross == 2)
				for (int i = 0; i < children.size(); i++)
					((Traveller) children.indAt(i)).uncross();
			for (int i = 0; i < children.getSize(); i++) {
				if (moRand.nextDouble() < mdMutationProbability) {
					(children.indAt(i)).mutation();
				}
			}
			this.moPopulation.insertManyIndAt(children);
		}
	}

	public void mutation() {
	}

	public void evolution() {
		nextGeneration();
		selection();
		cross();
	}
}
