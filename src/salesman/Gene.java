package salesman;

import java.util.*;

public abstract class Gene {
	private static Random moRandom = new Random();

	abstract public void initRandom();

	abstract public void mutation();

	abstract public Gene cloneMe();

}
