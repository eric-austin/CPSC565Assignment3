import java.util.Random;

public class Operators {
	
	static public Tilly crossover(Tilly parentA, Tilly parentB) {
		Random rng = new Random();
		Tilly newTilly = new Tilly();
		int breakpoint = rng.nextInt(127);
		for (int i = 0; i <= breakpoint; i++) {
			newTilly.genome[i] = parentA.genome[i];
		}
		for (int i = breakpoint + 1; i < 128; i++) {
			newTilly.genome[i] = parentB.genome[i];
		}
		return newTilly;
	}
	
	static public Tilly mutate(Tilly parent, double prob) {
		Random rng = new Random();
		Tilly newTilly = new Tilly(parent);
		for (int i = 0; i < 128; i++) {
			if (rng.nextDouble() < prob) {
				newTilly.genome[i] = (newTilly.genome[i] + rng.nextInt(6) + 1) % 7;
			}
		}
		return newTilly;
	}
	
	static public int selectTillyIndex(Tilly[] tillys, double[] probs) {
		double totalWeight = 0.0;
		Random rng = new Random();
		for (int i = 0; i < probs.length; i++) {
			totalWeight += probs[i];
		}
		int index = 0;
		double random = rng.nextDouble() * totalWeight;
		for (int i = 0; i < probs.length; i++) {
			random -= probs[i];
			if (random <= 0.0) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	static public int[] selectTwoTillyIndex(Tilly[] tillys, double[] probs) {
		int index1 = selectTillyIndex(tillys, probs);
		int index2 = selectTillyIndex(tillys, probs);
		if (index1 == index2) {
			if (index1 == (tillys.length - 1)) {
				index2--;
			} else {
				index2++;
			}
		}
		int[] indices = {index1, index2};
		return indices;
	}

}
