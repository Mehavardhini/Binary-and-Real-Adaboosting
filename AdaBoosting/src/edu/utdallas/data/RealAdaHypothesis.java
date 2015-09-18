package edu.utdallas.data;

public class RealAdaHypothesis {

	double thresholdValue;
	char operator;
	double probRpositive;
	double probRnegative;
	double probWpositive;
	double probWnegative;
	double Gt;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "Hypothesis: h(X" + operator + thresholdValue + ")"
				+ " probRpos: " + probRpositive + " probRneg: " + probRnegative
				+ " probWpos: " + probWpositive + " probWneg: " + probWnegative + " Gt: "
				+ Gt + "\n";
		return str;
	}

	public RealAdaHypothesis(double threshold, char operator) {
		super();
		this.thresholdValue = threshold;
		this.operator = operator;
	}

	public RealAdaHypothesis(double threshold, char operator, double probRpositive,
			double probRnegative, double probWpositive, double probWnegative, double gt) {
		super();
		this.thresholdValue = threshold;
		this.operator = operator;
		this.probRpositive = probRpositive;
		this.probRnegative = probRnegative;
		this.probWpositive = probWpositive;
		this.probWnegative = probWnegative;
		Gt = gt;
	}

	public void probabilityComputing(Sample sampleData) {
		if (operator == '<') {

			if (sampleData.getX() < thresholdValue && sampleData.getY() == 1) {
				probRpositive += sampleData.getProb();
			} else if (sampleData.getX() > thresholdValue && sampleData.getY() == -1) {
				probRnegative += sampleData.getProb();
			} else if (sampleData.getX() < thresholdValue && sampleData.getY() == -1) {
				probWnegative += sampleData.getProb();
			} else if (sampleData.getX() > thresholdValue && sampleData.getY() == 1) {
				probWpositive += sampleData.getProb();
			}

		} else { // operator '>'
			if (sampleData.getX() > thresholdValue && sampleData.getY() == 1) {
				probRpositive += sampleData.getProb();
			} else if (sampleData.getX() < thresholdValue && sampleData.getY() == -1) {
				probRnegative += sampleData.getProb();
			} else if (sampleData.getX() > thresholdValue && sampleData.getY() == -1) {
				probWnegative += sampleData.getProb();
			} else if (sampleData.getX() < thresholdValue && sampleData.getY() == 1) {
				probWpositive += sampleData.getProb();
			}
		}

	}

	public void computeGt() {
		Gt = Math.sqrt(probRpositive * probWnegative) + Math.sqrt(probWpositive * probRnegative);
	}

	public int hypothesisPrediction(Sample sampleData) {

		if (this.operator == '<') {
			if (sampleData.getX() < thresholdValue) {
				return 1;
			} else {
				return -1;
			}

		} else { // operator '>'
			if (sampleData.getX() < thresholdValue) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	public boolean isValidData(Sample sampleData) {
		if (hypothesisPrediction(sampleData) == sampleData.getY()) {
			return true;
		} else {
			return false;
		}

	}

	public double getThreshold() {
		return thresholdValue;
	}

	public void setThreshold(double threshold) {
		this.thresholdValue = threshold;
	}

	public char getOperator() {
		return operator;
	}

	public void setOperator(char operator) {
		this.operator = operator;
	}

	
	public double getProbRpos() {
		return probRpositive;
	}

	public void setProbRpos(double probRpos) {
		this.probRpositive = probRpos;
	}

	public double getProbRneg() {
		return probRnegative;
	}

	public void setProbRneg(double probRneg) {
		this.probRnegative = probRneg;
	}

	public double getProbWpos() {
		return probWpositive;
	}

	public void setProbWpos(double probWpos) {
		this.probWpositive = probWpos;
	}

	public double getProbWneg() {
		return probWnegative;
	}

	public void setProbWneg(double probWneg) {
		this.probWnegative = probWneg;
	}

	public double getGt() {
		return Gt;
	}

	public void setGt(double gt) {
		Gt = gt;
	}

}
