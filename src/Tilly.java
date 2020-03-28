import java.util.Arrays;
import java.util.Random;

public class Tilly {
	//instance variables
	protected int xcoor;
	protected int ycoor;
	protected int[] state = null;
	protected int[] genome;
	protected int profit;
	protected double avgFitness;
	
	//methods
	public Tilly() {
		this.xcoor = 1;
		this.ycoor = 1;
		this.state = new int[5];
		this.genome = new int[65];
		Random rng = new Random();
		for (int i = 0; i < 65; i++) {
			this.genome[i] = rng.nextInt(7);
		}
		this.profit = 0;
	}
	
	public Tilly(Tilly parent) {
		this.xcoor = parent.xcoor;
		this.ycoor = parent.ycoor;
		this.state = Arrays.copyOf(parent.state, 5);
		this.genome = Arrays.copyOf(parent.genome, 65);
		this.profit = parent.profit;
		this.avgFitness = parent.avgFitness;
	}

	public void updateState(Fountain fount) {
		state[0] = fount.tiles[this.xcoor - 1][this.ycoor];
		state[1] = fount.tiles[this.xcoor][this.ycoor - 1];
		state[2] = fount.tiles[this.xcoor + 1][this.ycoor];
		state[3] = fount.tiles[this.xcoor][this.ycoor + 1];
		state[4] = fount.tiles[this.xcoor][this.ycoor];
	}
	
	public int count2s() {
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (this.state[i] == 2) {
				count++;
			}
		}
		return count;
	}
	
	public int stateToIndex() {
		int index = 0;
		int twos = this.count2s();
		if (this.state[4] == 0) {
			index = 64;
		} else if (twos == 0) {
			for (int i = 0; i < 4; i++) {
				index += Math.pow(2, i) * this.state[i];
			}
		} else if (twos == 1) {
			int[] tempState = new int[3];
			int temp = 0;
			for (int i = 0; i < 4; i++) {
				if (this.state[i] != 2) {
					tempState[temp] = this.state[i];
					temp++;
				}
			}
			for (int i = 0; i < 3; i++) {
				index += Math.pow(2, i) * tempState[i];
			}
			if (this.state[0] == 2) {
				index += 16;
			} else if (this.state[1] == 2) {
				index += 24;
			} else if (this.state[2] == 2) {
				index += 32;
			} else {
				index += 40;
			}
		} else {
			int[] tempState = new int[2];
			int temp = 0;
			for (int i = 0; i < 4; i++) {
				if (this.state[i] != 2) {
					tempState[temp] = this.state[i];
					temp++;
				}
			}
			for (int i = 0; i < 2; i++) {
				index += Math.pow(2, i) * tempState[i];
			}
			if (this.state[0] == 2 && this.state[1] == 2) {
				index += 48;
			} else if (this.state[1] == 2 && this.state[2] == 2) {
				index += 52;
			} else if (this.state[2] == 2 && this.state[3] == 2) {
				index += 56;
			} else {
				index += 60;
			}
		}
		return index;
	}
	
	public void doNothing() {
		//do nothing
	}
	
	public void moveNorth(Fountain fount) {
		if (fount.tiles[this.xcoor - 1][this.ycoor] == 2) {
			this.profit -= 5;
		} else {
			this.xcoor = this.xcoor - 1;
		}
	}
	
	public void moveWest(Fountain fount) {
		if (fount.tiles[this.xcoor][this.ycoor - 1] == 2) {
			this.profit -= 5;
		} else {
			this.ycoor = this.ycoor - 1;
		}
	}
	
	public void moveSouth(Fountain fount) {
		if (fount.tiles[this.xcoor + 1][this.ycoor] == 2) {
			this.profit -= 5;
		} else {
			this.xcoor = this.xcoor + 1;
		}
	}
	
	public void moveEast(Fountain fount) {
		if (fount.tiles[this.xcoor][this.ycoor + 1] == 2) {
			this.profit -= 5;
		} else {
			this.ycoor = this.ycoor + 1;
		}
	}
	
	public void moveRandom(Fountain fount) {
		Random rng = new Random();
		int move = rng.nextInt(4);
		switch(move) {
		case 0 : this.moveNorth(fount);
				 break;
		case 1 : this.moveWest(fount);
				 break;
		case 2 : this.moveSouth(fount);
				 break;
		case 3 : this.moveEast(fount);
				 break;
		}
		
	}
	
	public void replaceTile(Fountain fount) {
		if (fount.tiles[this.xcoor][this.ycoor] == 0) {
			fount.tiles[this.xcoor][this.ycoor] = 1;
			this.profit += 10;
		} else {
			this.profit -= 1;
		}
	}
	
	public void fixFountain(Fountain fount) {
		int action;
		int index;
		for (int i = 0; i < 200; i++) {
			index = this.stateToIndex();
			action = this.genome[index];
			switch (action) {
			case 0 : this.doNothing();
					 break;
			case 1 : this.moveNorth(fount);
					 break;
			case 2 : this.moveWest(fount);
					 break;
			case 3 : this.moveSouth(fount);
					 break;
			case 4 : this.moveEast(fount);
					 break;
			case 5 : this.moveRandom(fount);
					 break;
			case 6 : this.replaceTile(fount);
					 break;
			}
			this.updateState(fount);
		}
	}
	
	@Override
	public String toString() {
		String str = "Genome: " + Arrays.toString(this.genome);
		str = str + "\nAverage Fitness: " + this.avgFitness;
		return str;
	}

}
