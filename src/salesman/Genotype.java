package salesman;

import java.util.*;

class Genotype extends Vector {

	int taille() {
		return this.size();

	}

	Gene geneAt(int _i) {
		return (Gene) this.elementAt(_i);
	}

	Gene firstGene() {
		return (Gene) this.firstElement();
	}

	Gene lastGene() {
		return (Gene) this.lastElement();
	}

	void addGene(Gene _gene) {
		this.addElement(_gene);
	}

	void removeGeneAt(int _i) {
		this.removeElementAt(_i);
	}

	void removeGene(Gene _gene) {
		this.removeElement(_gene);
	}

	void insererGeneAt(Gene _gene, int _i) {
		this.insertElementAt(_gene, _i);
	}

	int inGene(Gene _gene) {
		return this.indexOf(_gene);
	}

	int indGenePerNum(int num) {

		int i, ind = -1;
		for (i = 0; i < this.size(); i++)
			if (((TownGene) this.elementAt(i)).getTownNumber() == num) {
				ind = i;
				break;
			}

		return ind;
	}

	TownGene genePerNum(int num) {
		TownGene temp = new TownGene(-1);
		int i;
		for (i = 0; i < this.size(); i++)
			if (((TownGene) this.elementAt(i)).getTownNumber() == num) {
				temp = (TownGene) this.elementAt(i);
				break;
			}

		return temp;
	}

	TownGene geneNextTown(int num) {
		return (TownGene) this
				.elementAt((indGenePerNum(num) + 1) % this.size());
	}

	Genotype cloneMe() {
		return (Genotype) this.clone();
	}

	boolean in(int num) {

		int i;
		boolean b = false;
		for (i = 0; i < this.size(); i++)
			if (((TownGene) this.elementAt(i)).getTownNumber() == num) {
				b = true;
				break;
			}

		return b;
	}

	boolean in(TownGene g) {

		int i;
		boolean b = false;
		for (i = 0; i < this.size(); i++) {
			if (((TownGene) this.elementAt(i)).getTownNumber() == g
					.getTownNumber())
				b = true;
		}

		return b;
	}
}
