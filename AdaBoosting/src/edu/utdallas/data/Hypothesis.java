package edu.utdallas.data;

public class Hypothesis {
	double threshold;
	char operator;
	double epsilon;
	int errorCount;

	public Hypothesis(double threshold, char operator, double epsilon,
			int noOfErrors) {
		super();
		this.threshold = threshold;
		this.operator = operator;
		this.epsilon = epsilon;
		this.errorCount = noOfErrors;
	}

	public Hypothesis() {

	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public char getOperator() {
		return operator;
	}

	public void setOperator(char operator) {
		this.operator = operator;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public int getNoOfErrors() {
		return errorCount;
	}

	public void setNoOfErrors(int noOfErrors) {
		this.errorCount = noOfErrors;
	}
	
public int hypothesisPrediction(Sample sampleData){
		
		if (this.operator == '<') {
			if (sampleData.getX() < threshold) {
				return 1;
			} else{
				return -1;
			}

		} else { // operator '>'
			if (sampleData.getX() < threshold ) {
				return -1;
			} else{
				return 1;
			}
		}		
	}
	
	public boolean isValidData(Sample sampleData) {
		if(hypothesisPrediction(sampleData)==sampleData.getY()){
			return true;
		}
		else{
			return false;
		}

	}


/*	public boolean isValidData(SampleData sampleData) {

		if (this.operator == '<') {

			if (sampleData.getX() < threshold && sampleData.getY() != 1) {
				return false;
			} else if (sampleData.getX() > threshold && sampleData.getY() != -1) {
				return false;
			}

		} else { // operator '>'
			if (sampleData.getX() < threshold && sampleData.getY() != -1) {
				return false;
			} else if (sampleData.getX() > threshold && sampleData.getY() != 1) {
				return false;
			}
		}
		return true;

	}
*/
}
