package salesman;

import java.lang.Math.*;
import java.util.*;

public abstract class Individual {

	protected static Random moRand = new Random();
	protected Genotype moGenotype = new Genotype();
	protected double mdMutationProbability;
	protected int miGeneration;
	protected double mdAdapation;

	public double getAdaptation() {
		return mdAdapation;
	}

	public int getGeneration() {
		return miGeneration;
	}

	public int getGenotypeSize() {
		return moGenotype.taille();
	}

	public Genotype genes() {
		return moGenotype;
	}

	abstract public void mutation();

	abstract public IndividualVector cross(Individual partner,
			int _iGen);

	abstract public void createGenotype();

	abstract public void computeAdaptation();

	abstract public void diversify();
}
