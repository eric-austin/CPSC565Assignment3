import java.util.Arrays;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) {
		//set up parameters
		int nRobots = 100;
		double mutationRate = 0.05;
		int nGens = 500;
		double rouletteFactor = 0.8;
		double[] selectionProbs = new double[nRobots];
		for (int i = 0; i < nRobots; i++) {
			selectionProbs[i] = Math.pow(rouletteFactor, i);
		}
		Comparator<Tilly> tillyComparator = new Comparator<Tilly>() {
			@Override
			public int compare(Tilly a, Tilly b) {
				if (b.avgFitness > a.avgFitness) {
					return 1;
				} else if (b.avgFitness < a.avgFitness) {
					return -1;
				} else {
					return 0;
				}
			}
		};
		
		//initial setup
		Tilly best = null;
		
		//create random population of Tillys
		Tilly[] population = new Tilly[nRobots];
		for (int i = 0; i < nRobots; i++) {
			population[i] = new Tilly();
		}
		
		
		//iterate through generations
		for (int g = 0; g < nGens; g++) {
			//evaluate fitness of population
			for (Tilly t : population) {
				int totalProfit = 0;
				//create 100 random fountains
				Fountain[] fountains = new Fountain[100];
				for (int i = 0; i < 100; i++) {
					fountains[i] = new Fountain();
				}
				for (Fountain f : fountains) {
					t.updateState(f);
					t.fixFountain(f);
					totalProfit += t.profit;
					t.profit = 0;
				}
				t.avgFitness = ((double) totalProfit) / 100.0;
			}
			
			//sort by fitness
			Arrays.sort(population, tillyComparator);
			
			//create array of new tillys
			Tilly[] children = new Tilly[nRobots];
			
			//create children through crossover/mutation
			for (int i = 0; i < (nRobots/2); i++) {
				int parentIndex = Operators.selectTillyIndex(population, selectionProbs);
				children[i] = Operators.mutate(population[parentIndex], mutationRate);
			}
			for (int i = (nRobots/2); i < nRobots; i++) {
				int[] parentIndices = Operators.selectTwoTillyIndex(population, selectionProbs);
				children[i] = Operators.crossover(population[parentIndices[0]], population[parentIndices[1]]);
			}
			
			//evaluate fitness of children
			for (Tilly t : children) {
				int totalProfit = 0;
				//create 100 random fountains
				Fountain[] fountains = new Fountain[100];
				for (int i = 0; i < 100; i++) {
					fountains[i] = new Fountain();
				}
				for (Fountain f : fountains) {
					t.updateState(f);
					t.fixFountain(f);
					totalProfit += t.profit;
					t.profit = 0;
				}
				t.avgFitness = ((double) totalProfit) / 100.0;
			}
			
			//combine arrays of parents and children and sort by fitness
			Tilly[] allTillys = Arrays.copyOf(population, nRobots*2);
			for (int i = nRobots; i < nRobots*2; i++) {
				allTillys[i] = children[i - nRobots];
			}
			Arrays.sort(allTillys, tillyComparator);
			
			//population for next generation are best nRobots tillys
			population = Arrays.copyOf(allTillys, nRobots);
			
			System.out.println(population[0].toString());
			if (best == null) {
				best = population[0];
			} else {
				if (population[0].avgFitness > best.avgFitness) {
					best = new Tilly(population[0]);
				}
			}
		}
		System.out.println("Best:");
		System.out.println(best.toString());
	}

}
