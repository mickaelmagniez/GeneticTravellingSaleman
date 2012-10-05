package salesman;

import java.util.*;

class IndividualVector extends Vector {
	int getSize() {
		return this.size();
	}

	Individual indAt(int _i) {
		return (Individual) this.elementAt(_i);
	}

	Individual firstInd() {
		return (Individual) this.firstElement();
	}

	Individual lastInd() {
		return (Individual) this.lastElement();
	}

	void removeIndAt(int _i) {
		this.removeElementAt(_i);
	}

	IndividualVector cloneMe() {
		return (IndividualVector) this.clone();
	}

	void insertIndSort(Individual _ind) {
		int inf = 0;
		int sup;

		sup = this.getSize() - 1;
		if (sup == -1) {
			this.addElement(_ind);
		} else if (_ind.getAdaptation() <= (this.lastInd())
				.getAdaptation()) {
			this.addElement(_ind);
		} else if (_ind.getAdaptation() >= (this.firstInd())
				.getAdaptation()) {
			this.insertElementAt(_ind, 0);
		} else {
			while ((sup - inf) > 1) {
				if ((this.indAt((int) Math.floor((sup + inf) / 2)))
						.getAdaptation() < _ind.getAdaptation())
					sup = (int) Math.floor((sup + inf) / 2);
				else
					inf = (int) Math.floor((sup + inf) / 2);
			}
			this.insertElementAt(_ind, inf + 1);
		}
	}

	void reclassIndAt(int leIndice) {
		Individual temp;
		temp = this.indAt(leIndice);
		this.removeIndAt(leIndice);
		this.insertIndSort(temp);
	}

	void insertManyIndAt(IndividualVector source) {
		for (int i = 0; i < source.getSize(); i++) {
			insertIndSort(source.indAt(i));
		}
	}

}
