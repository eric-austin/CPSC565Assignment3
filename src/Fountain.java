import java.util.Random;

public class Fountain {
	protected int[][] tiles;
	
	public Fountain() {
		tiles = new int[12][12];
		Random rng = new Random();
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (i == 0 || i == 11 || j == 0 || j == 11) {
					this.tiles[i][j] = 2;
				} else {
					this.tiles[i][j] = rng.nextInt(2);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				str += this.tiles[i][j] + " ";
			}
			str += "\n";
		}
		return str;
	}

}
