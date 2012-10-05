package salesman;

public class TownGene extends Gene {

	int miTownNumber;

	TownGene(int _iTownNumber) {
		miTownNumber = _iTownNumber;
	}

	int getTownNumber() {
		return miTownNumber;
	}

	public void initRandom() {
	}

	public void mutation() {
	}

	public Gene cloneMe() {
		return new TownGene(getTownNumber());
	}

}
