package salesman;

import java.util.*;

abstract public class Population {

	protected static Random moRand = new Random();
	IndividualVector moPopulation;
	protected int miIndMax;
	protected double mdMortality;
	protected double mdMutationProbability;
	protected int miStagnationMax;
	protected int miGenerationMax;
	protected double mdAcceptableValue;
	protected int miMaxSameGeneration = 0;
	protected double mdBestAdaptation = 0;
	protected int miGenerationNum = 0;

	public Individual bestInd() {
		return moPopulation.firstInd();
	}

	public Individual worstInd() {
		return moPopulation.lastInd();
	}

	public double bestAdaptation() {
		return bestInd().getAdaptation();
	}

	public double avgAdaptation() {
		int i;
		double somme = 0;

		for (i = 0; i < moPopulation.getSize(); i++) {
			somme += moPopulation.indAt(i).getAdaptation();
		}
		return somme / moPopulation.getSize();
	}

	public int populationSize() {
		return moPopulation.getSize();
	}

	public int getGenerationNum() {
		return miGenerationNum;
	}

	public boolean testEndCompute() {
		return testEndStagnation() || testAcceptable()
				|| testLastGeneration();

	}

	public boolean testEndStagnation() {
		if (bestAdaptation() <= mdBestAdaptation) {
			miMaxSameGeneration++;
			if (miMaxSameGeneration >= miStagnationMax) {
				return true;
			} else {
				return false;
			}
		} else {
			miMaxSameGeneration = 0;
			mdBestAdaptation = bestAdaptation();
			return false;
		}
	}

	public boolean testAcceptable() {
		return (bestAdaptation() >= mdAcceptableValue);
	}

	public boolean testLastGeneration() {
		return (miGenerationNum >= miGenerationMax);
	}

	public int getIndToRemplace() {
		return (int) Math.floor(mdMortality * miIndMax);
	}

	public int getIndToKeep() {
		return miIndMax - getIndToRemplace();
	}

	abstract public void selection();

	abstract public void cross();

	abstract public void mutation();

	protected void nextGeneration() {
		miGenerationNum++;
	}

	abstract public void evolution();
}
