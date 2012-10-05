package salesman;

import java.util.Random;
import java.util.Vector;

class Traveller extends Individual {
	public Traveller() {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private World moWorld;

	Traveller(int _iGenerationNum, double _dMutationProbability, World _world,
			Genotype _legacy) {
		miGeneration = _iGenerationNum;
		mdMutationProbability = _dMutationProbability;
		moWorld = _world;
		moGenotype = _legacy;
		computeAdaptation();
	}

	Traveller(int _iGenerationNum, double _dMutationProbability, World _world) {
		miGeneration = _iGenerationNum;
		mdMutationProbability = _dMutationProbability;
		moWorld = _world;
		createGenotype();
		computeAdaptation();
	}

	private double min(double d1, double d2) {
		if (d1 < d2) {
			return d1;
		} else {
			return d2;
		}
	}

	private double min(double d1, double d2, double d3, double d4) {
		return min(d1, min(d2, min(d3, d4)));
	}

	public void uncross() {

		boolean crossed = true;
		int townCount = getGenotypeSize();
		double d[][] = new double[townCount][townCount];
		int t[], lgmax = 0;

		t = new int[townCount];

		for (int v = 0; v < townCount; v++) {
			t[v] = ((TownGene) this.genes().geneAt(v)).getTownNumber();
			for (int vv = 0; vv < townCount; vv++) {
				d[v][vv] = moWorld.townsDistance(v, vv);
				if (d[v][vv] > lgmax)
					lgmax = (int) Math.floor(d[v][vv]);
			}
		}

		while (crossed) {
			int a = townCount / 4;
			crossed = false;
			for (int i = 0; i <= townCount - 4; i++) {
				for (int j = 0; j <= townCount - i - 3; j++) {
					int j0 = i + 2 + ((j + a) % (townCount - i - 2));
					int j1 = (j0 + 1) % townCount;

					if ((d[t[i]][t[i + 1]] > 0.3 * lgmax)
							&& (d[t[i]][t[i + 1]] + d[t[j0]][t[j1]] > d[t[i]][t[j0]]
									+ d[t[i + 1]][t[j1]] + 1E-8)) {
						crossed = true;
						for (int k = i + 1; k <= (j0 + i) / 2; k++) {
							int val = t[k];
							t[k] = t[j0 + i + 1 - k];
							t[j0 + i + 1 - k] = val;
						}
					}
				}
			}
		}

		crossed = true;
		while (crossed) {
			int a = townCount / 4;
			crossed = false;
			for (int i = 0; i <= townCount - 4; i++) {
				for (int j = 0; j <= townCount - i - 3; j++) {
					int j0 = i + 2 + ((j + a) % (townCount - i - 2));
					int j1 = (j0 + 1) % (townCount);
					if (d[t[i]][t[i + 1]] + d[t[j0]][t[j1]] > d[t[i]][t[j0]]
							+ d[t[i + 1]][t[j1]] + 1E-8) {
						crossed = true;
						for (int k = i + 1; k <= (j0 + i) / 2; k++) {
							int val = t[k];
							t[k] = t[j0 + i + 1 - k];
							t[j0 + i + 1 - k] = val;
						}
					}
				}

			}
		}

		this.moGenotype.clear();
		for (int v = 0; v < townCount; v++) {
			this.moGenotype.add(new TownGene(t[v]));

		}
	}

	public IndividualVector cross(Individual _partner, int _iGenerationNum) {

		IndividualVector vec = GreedyCrossOver(_partner, _iGenerationNum);
		Traveller trav = (Traveller) vec.elementAt(0);

		return vec;

	}

	public IndividualVector greedy(Individual _partner, int _iGenerationNum) {
		IndividualVector child = new IndividualVector();
		Genotype childGenes = new Genotype();
		int first;
		int indGeneFatherBefore, indGeneMotherBefore;
		int indGeneFatherAfter, indGeneMotherAfter;
		double distance1, distance2, distance3, distance4, distanceMin;

		first = (int) Math.floor(moRand.nextDouble() * getGenotypeSize());
		childGenes.addGene(this.moGenotype.geneAt(first));

		for (int i = 0; i < (getGenotypeSize() - 1); i++) {
			indGeneFatherBefore = (getGenotypeSize()
					+ this.moGenotype.inGene(childGenes.lastGene()) - 1)
					% getGenotypeSize();
			indGeneFatherAfter = (this.moGenotype
					.inGene(childGenes.lastGene()) + 1) % getGenotypeSize();
			indGeneMotherBefore = (getGenotypeSize()
					+ _partner.moGenotype.inGene(childGenes.lastGene()) - 1)
					% getGenotypeSize();
			indGeneMotherAfter = (_partner.moGenotype.inGene(childGenes
					.lastGene()) + 1) % getGenotypeSize();

			while (childGenes.in((TownGene) this.moGenotype
					.geneAt(indGeneFatherBefore))) {
				indGeneFatherBefore = (getGenotypeSize()
						+ indGeneFatherBefore - 1)
						% getGenotypeSize();
			}
			while (childGenes.in((TownGene) this.moGenotype
					.geneAt(indGeneFatherAfter))) {
				indGeneFatherAfter = (indGeneFatherAfter + 1)
						% getGenotypeSize();
			}
			while (childGenes.in((TownGene) _partner.moGenotype
					.geneAt(indGeneMotherBefore))) {
				indGeneMotherBefore = (getGenotypeSize()
						+ indGeneMotherBefore - 1)
						% getGenotypeSize();
			}
			while (childGenes.in((TownGene) _partner.moGenotype
					.geneAt(indGeneMotherAfter))) {
				indGeneMotherAfter = (indGeneMotherAfter + 1)
						% getGenotypeSize();
			}
			distance1 = moWorld.townsDistance(((TownGene) childGenes.lastGene())
					.getTownNumber(), ((TownGene) this.moGenotype
					.geneAt(indGeneFatherBefore)).getTownNumber());
			distance2 = moWorld.townsDistance(((TownGene) childGenes.lastGene())
					.getTownNumber(), ((TownGene) this.moGenotype
					.geneAt(indGeneFatherAfter)).getTownNumber());
			distance3 = moWorld.townsDistance(((TownGene) childGenes.lastGene())
					.getTownNumber(), ((TownGene) _partner.moGenotype
					.geneAt(indGeneMotherBefore)).getTownNumber());
			distance4 = moWorld.townsDistance(((TownGene) childGenes.lastGene())
					.getTownNumber(), ((TownGene) _partner.moGenotype
					.geneAt(indGeneMotherAfter)).getTownNumber());
			distanceMin = min(distance1, distance2, distance3, distance4);

			if (distance1 == distanceMin) {
				childGenes.addGene(this.moGenotype.geneAt(indGeneFatherBefore));
			} else if (distance2 == distanceMin) {
				childGenes.addGene(this.moGenotype.geneAt(indGeneFatherAfter));
			} else if (distance3 == distanceMin) {
				childGenes.addGene(_partner.moGenotype
						.geneAt(indGeneMotherBefore));
			} else {
				childGenes.addGene(_partner.moGenotype
						.geneAt(indGeneMotherAfter));
			}
		}
		child.insertIndSort(new Traveller(_iGenerationNum,
				this.mdMutationProbability, this.moWorld, childGenes));

		return child;

	}

	public IndividualVector OnePointCrossOver(Individual _partner,
			int _iGenerationNum) {
		IndividualVector child = new IndividualVector();
		Genotype childGenes = new Genotype();
		int point;
		Gene gene;

		point = (int) Math.floor(moRand.nextDouble() * getGenotypeSize());

		childGenes.addAll(this.moGenotype.subList(0, point));

		for (int i = 0; i < (getGenotypeSize()); i++) {
			gene = _partner.moGenotype.geneAt(i);
			if (!childGenes.in((TownGene) gene))
				childGenes.addGene(gene);

		}

		child.insertIndSort(new Traveller(_iGenerationNum,
				this.mdMutationProbability, this.moWorld, childGenes));

		childGenes.clear();

		point = (int) Math.floor(moRand.nextDouble() * getGenotypeSize());

		childGenes.addAll(_partner.moGenotype.subList(0, point));

		for (int i = 0; i < (getGenotypeSize()); i++) {
			gene = this.moGenotype.geneAt(i);
			if (!childGenes.in((TownGene) gene))
				childGenes.addGene(gene);

		}

		child.insertIndSort(new Traveller(_iGenerationNum,
				this.mdMutationProbability, this.moWorld, childGenes));

		return child;
	}

	public IndividualVector TwoPointCrossOver(Individual partner,
			int _iGenerationNum) {
		IndividualVector child = new IndividualVector();
		Genotype childGenes = new Genotype(), genesFils2 = new Genotype();
		int point, point2;
		Gene gene;

		point = (int) Math.floor(moRand.nextDouble() * getGenotypeSize()) / 2;
		point2 = (int) point
				+ (int) Math.floor(moRand.nextDouble() * getGenotypeSize()) / 2;

		childGenes.addAll(this.moGenotype.subList(0, point));
		genesFils2.addAll(this.moGenotype
				.subList(point2, getGenotypeSize() - 1));

		for (int i = 0; i < getGenotypeSize(); i++) {
			gene = partner.moGenotype.geneAt(i);
			if (!childGenes.in((TownGene) gene)
					&& !genesFils2.in((TownGene) gene))
				childGenes.addGene(gene);
		}
		childGenes
				.addAll(this.moGenotype.subList(point2, getGenotypeSize() - 1));

		child.insertIndSort(new Traveller(_iGenerationNum,
				this.mdMutationProbability, this.moWorld, childGenes));

		childGenes.clear();
		genesFils2.clear();
		point = (int) Math.floor(moRand.nextDouble() * getGenotypeSize()) / 2;
		point2 = (int) point
				+ (int) Math.floor(moRand.nextDouble() * getGenotypeSize()) / 2;

		childGenes.addAll(partner.moGenotype.subList(0, point));
		genesFils2.addAll(partner.moGenotype.subList(point2,
				getGenotypeSize() - 1));

		for (int i = 0; i < getGenotypeSize(); i++) {
			gene = this.moGenotype.geneAt(i);
			if (!childGenes.in((TownGene) gene)
					&& !genesFils2.in((TownGene) gene))
				childGenes.addGene(gene);
		}
		childGenes.addAll(partner.moGenotype.subList(point2,
				getGenotypeSize() - 1));

		child.insertIndSort(new Traveller(_iGenerationNum,
				this.mdMutationProbability, this.moWorld, childGenes));

		return child;
	}

	public IndividualVector GreedyCrossOver(Individual _partner,
			int _iGenerationNum) {

		IndividualVector child = new IndividualVector();
		Genotype _childGenes = new Genotype();
		int i, currentTown, ind = 0, j;
		double fatherDistance, motherDistance;
		TownGene nextFatherGene, nextMotherGene, gene;
		gene = new TownGene(0);
		TownGene temp;

		Vector townsRemaining = new Vector();

		for (i = 0; i < getGenotypeSize(); i++) {
			gene = new TownGene(i);
			townsRemaining.add(gene);
		}

		currentTown = (int) Math.floor(moRand.nextDouble()
				* getGenotypeSize());

		_childGenes.addGene(this.moGenotype.genePerNum(currentTown));

		for (i = 1; i < getGenotypeSize(); i++) {
			// System.out.println(villeCourante + " : ");
			for (j = 0; j < townsRemaining.size(); j++) {
				if (((TownGene) townsRemaining.elementAt(j)).getTownNumber() == currentTown) {
					ind = j;
					break;
				}
			}

			townsRemaining.remove(ind);

			nextFatherGene = this.moGenotype.geneNextTown(currentTown);
			nextMotherGene = _partner.moGenotype
					.geneNextTown(currentTown);

			if (_childGenes.in(nextFatherGene)) {
				if (_childGenes.in(nextMotherGene)) {
					currentTown = (int) Math
							.floor((moRand.nextDouble() * townsRemaining
									.size()) % townsRemaining.size());

					temp = (TownGene) townsRemaining.elementAt(currentTown);
					currentTown = temp.getTownNumber();
					gene = this.moGenotype.genePerNum(currentTown);
				} else {
					gene = nextMotherGene;
				}
			} else {
				if (_childGenes.in(nextMotherGene)) {
					gene = nextFatherGene;
				} else {
					fatherDistance = moWorld.townsDistance(
							((TownGene) _childGenes.lastGene()).getTownNumber(),
							(((TownGene) nextFatherGene).getTownNumber()));

					motherDistance = moWorld.townsDistance(
							((TownGene) _childGenes.lastGene()).getTownNumber(),
							(((TownGene) nextMotherGene).getTownNumber()));

					if (fatherDistance < motherDistance)
						gene = nextFatherGene;
					else
						gene = nextMotherGene;
				}
			}
			_childGenes.addGene(gene);
			currentTown = gene.getTownNumber();
		}

		child.insertIndSort(new Traveller(_iGenerationNum,
				this.mdMutationProbability, this.moWorld, _childGenes));
		return child;

	}

	public void createGenotype() {
		moGenotype = moWorld.genesBase();

		for (int i = 0; i < getGenotypeSize(); i++) {
			int index = (int) Math.floor(moRand.nextDouble()
					* (getGenotypeSize() - i));
			moGenotype.addGene(moGenotype.geneAt(index));
			moGenotype.removeGeneAt(index);
		}
	}

	public void mutation() {
		TownGene tempon1;
		TownGene tempon2;

		for (int i = 0; i < getGenotypeSize(); i++) {
			if (moRand.nextDouble() < mdMutationProbability) {
				int index = (int) Math.floor(moRand.nextDouble()
						* getGenotypeSize());
				tempon1 = (TownGene) moGenotype.geneAt(i);
				tempon2 = (TownGene) moGenotype.geneAt(index);
				moGenotype.removeGeneAt(index);
				moGenotype.insererGeneAt(tempon1, index);
				moGenotype.removeGeneAt(i);
				moGenotype.insererGeneAt(tempon2, i);
			}
		}
		computeAdaptation();
	}

	public void computeAdaptation() {
		double distance;

		distance = 0;
		for (int i = 0; i < getGenotypeSize(); i++) {
			distance += moWorld.townsDistance(((TownGene) moGenotype.geneAt(i))
					.getTownNumber(), ((TownGene) moGenotype.geneAt((i + 1)
					% getGenotypeSize())).getTownNumber());
		}
		this.mdAdapation = (getGenotypeSize() * 100) / (double) distance;
	}

	public void diversify() {
		Random r = new Random();
		for (int i = 0; i < r.nextInt(moGenotype.size() / 2); i++) {
			Gene g = moGenotype.geneAt(i);
			moGenotype.removeGeneAt(i);
			moGenotype.addGene(g);
		}

	}
}
