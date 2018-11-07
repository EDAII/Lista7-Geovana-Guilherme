import java.util.Random;
public class ElementController {
	private int[] weights;
	private int[] values;
	
	public ElementController() {
		// TODO Auto-generated constructor stub
	}

	public int[] getWeights() {
		return weights;
	}

	public void setWeights(int[] weights) {
		this.weights = weights;
	}

	public int[] getValues() {
		return values;
	}

	public void setValues(int[] values) {
		this.values = values;
	}
	

	public int getRandomInt(int max){
		Random rnd = new Random();
		return rnd.nextInt(Math.abs(max));
	}
	
	public void generateRandomElements() {
	
		int num_elements = getRandomInt(5) + 3;
		int max_weight = getRandomInt(10) + 1;
		weights = new int[num_elements];
		values = new int[num_elements];
		
		for(int i = 0; i < num_elements; i++) {
			values[i] = getRandomInt(30) + 1;
			weights[i] = getRandomInt(max_weight) + 3;
		}
		
	}
	
	
	public int[][] generateResultsMatrix(int max_weight){
		
		int[][] matrix = new int[weights.length + 2][max_weight + 2];
		
		
		//fill line 0 with all weights from 0 to max_wegiht
		for(int j=0; j<max_weight + 2; j++) {
			if(j<2) {
				matrix[0][j] = 0; 
			} else {
				matrix[0][j] = j-1;
			}
			
		}
		
		//fill line 1 with zeros
		for(int j=1; j<max_weight + 2; j++) {
				matrix[1][j] = 0; 
		}
		
		//fill column 0 with all elements from 0 to n elements
		for(int i=0; i<weights.length + 2; i++) {
			if(i<2) {
				matrix[i][0] = 0; 
			} else {
				matrix[i][0] = i-1;
			}
			
		}
		
		//fill column 1 with zeros
		for(int i=1; i<weights.length + 2; i++) {
				matrix[i][1] = 0; 
		}
		
		
		//calculate rest of the cells
		for(int i=2; i<weights.length + 2; i++) {
			for(int j=2; j<max_weight + 2; j++) {
				
				int not_take = matrix[i-1][j];
				int take;
				if(j-weights[i-2] > 0) {
					take = matrix[i-1][j-weights[i-2]] + values[i-2];
				} else {
					take = -1;
				}
				
				if(take > not_take) {
					matrix[i][j] = take;
				} else {
					matrix[i][j] = not_take;
				}
				
			}
		}
		
		return matrix;
	}
	
	
	
	

	
}