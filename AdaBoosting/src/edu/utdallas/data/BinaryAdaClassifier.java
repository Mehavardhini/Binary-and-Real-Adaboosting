package edu.utdallas.data;

import java.util.ArrayList;

import edu.utdallas.utilities.PrintUtilities;

public class BinaryAdaClassifier {
	Hypothesis hypothesis;
	double weight;
	double probPreNorm[];
	double zNorm;
	double boostedClassifierF[];
	double boostedClassifierError=0;
	double bound;
	double probNorm[];
	ArrayList<Sample> sampleDataList;
	String boostedClassifier;
	
	public BinaryAdaClassifier(Hypothesis hypothesis,
			ArrayList<Sample> sampleDataList) {
		super();
		this.hypothesis = hypothesis;
		this.sampleDataList = sampleDataList;
	}

	//double z[];
	//double probNorm[];
	
	public void formattedClassifierData(){
		
		System.out.println("Classifier h = I(x " + this.hypothesis.getOperator()+ " "+this.hypothesis.getThreshold() + ")");
		System.out.println("Error = "+RealAdaClassifier.df.format(this.hypothesis.getEpsilon()));
		System.out.println("Alpha = "+RealAdaClassifier.df.format(this.weight));
		System.out.println("Normalization Factor Z = "+RealAdaClassifier.df.format(this.zNorm));
		System.out.print("Pi after normalization = ");
		PrintUtilities.printArray(this.probNorm);		
		System.out.println("Boosted Classifier f(x) = "+boostedClassifier);
		System.out.println("Boosted Classifier Error = "+RealAdaClassifier.df.format(this.boostedClassifierError));
		System.out.println("Bound on Error = "+RealAdaClassifier.df.format(this.bound));
	}
	
	public void printClassiferData(){
		
		System.out.println("The selected weak classifier: h(x" + this.hypothesis.getOperator()+ this.hypothesis.getThreshold() + ")");
		System.out.println("The error of ht(Epsilon) : "+this.hypothesis.getEpsilon());
		System.out.println("The weight of ht(alpha) : "+this.weight);
		/*System.out.print("The probabilities  Pre-normalized(pi): ");
		PrintUtil.printArray(this.probPreNorm);
		*/
		System.out.println("The probabilities normalization factor(Zt) : "+this.zNorm);
		System.out.print("The probabilities after normalization(pi) : ");
		PrintUtilities.printArray(this.probNorm);
		System.out.println("The boosted classifier(ft) : "+boostedClassifier);
		System.out.print("The boosted classifier(ft) values : ");
		PrintUtilities.printArray(this.boostedClassifierF);
		
		
		System.out.println("The error of the boosted classifier(Et) : "+this.boostedClassifierError);
		System.out.println("The bound on Et(Z) : "+this.bound);
		
	}
	
	public void computeClassifierData(ArrayList<BinaryAdaClassifier> prevBinaryAdaClassifierDataList){
		computeWeight();
		computePreNormalizedProb();
		computeZNorm();
		computeNormalizedProb();
		computeBoostedClassiferF(prevBinaryAdaClassifierDataList);
		computeBoostedClassifierError();
		computeBound(prevBinaryAdaClassifierDataList);	
	}

	public void computeBoostedClassiferF(
			ArrayList<BinaryAdaClassifier> prevBinaryAdaClassifierDataList) {
		this.boostedClassifierF = new double[sampleDataList.size()];
		double[] prevBoostedClassifierF = null;
		String prevBoostedClassifier=null;

		// Get the values for the previous hypothesis
		if (prevBinaryAdaClassifierDataList != null
				&& prevBinaryAdaClassifierDataList.size() > 0) {
			BinaryAdaClassifier prevBinaryAdaClassifierData = prevBinaryAdaClassifierDataList
					.get(prevBinaryAdaClassifierDataList.size() - 1);

			prevBoostedClassifierF = prevBinaryAdaClassifierData
					.getBoostedClassifierF();
			prevBoostedClassifier=prevBinaryAdaClassifierData.getBoostedClassifier();
		}

		for (int i = 0; i < sampleDataList.size(); i++) {
			Sample sampleData = sampleDataList.get(i);
			double prevClassiferF=(prevBoostedClassifierF != null)? prevBoostedClassifierF[i]: 0.0;
			// computing for current hypothesis
			this.boostedClassifierF[i] = prevClassiferF+ this.getWeight() * hypothesis.hypothesisPrediction(sampleData);
			this.boostedClassifier=(prevBoostedClassifier!=null)?(prevBoostedClassifier+" + "):"";
			this.boostedClassifier+=RealAdaClassifier.df.format(this.getWeight())+" * I(x "+hypothesis.getOperator()+" "+hypothesis.threshold+")";
				
		}
		
	}
	
	public void computeBoostedClassifierError(){	
		for (int i = 0; i < sampleDataList.size(); i++) {
			Sample sampleData = sampleDataList.get(i);
			if(sampleData.getY()==-1 && boostedClassifierF[i] > 0){
				this.boostedClassifierError++;
			}
			else if(sampleData.getY()==1 && boostedClassifierF[i] < 0){
				this.boostedClassifierError++;
			}			
		}
		this.boostedClassifierError/=sampleDataList.size();
	}

	public void computeBound(ArrayList<BinaryAdaClassifier> prevBinaryAdaClassifierDataList) {
		this.bound = zNorm;
		for (BinaryAdaClassifier binaryAdaClassifierData : prevBinaryAdaClassifierDataList) {
			this.bound *= binaryAdaClassifierData.getzNorm();
		}
	}

	public void computeNormalizedProb() {
		this.probNorm= new double[sampleDataList.size()];
		for (int i = 0; i < sampleDataList.size(); i++) {
			Sample sampleData = sampleDataList.get(i);
			double prob = probPreNorm[i]/ this.zNorm;
			sampleData.setProb(prob);
			probNorm[i]=prob;
		}

	}

	public void computePreNormalizedProb() {
		probPreNorm = new double[sampleDataList.size()];
		for (int i = 0; i < sampleDataList.size(); i++) {
			Sample sampleData = sampleDataList.get(i);
			if (this.hypothesis.isValidData(sampleData)) {
				this.probPreNorm[i] = Math.exp(-this.weight)*sampleData.getProb();
			} else {
				this.probPreNorm[i] = Math.exp(this.weight)*sampleData.getProb();
			}

		}
	}

	public void computeZNorm() {
		this.zNorm = 0.0;
		for (double prob : probPreNorm) {
			this.zNorm += prob;
		}
	}

	public void computeWeight() {
		double epsilon = hypothesis.getEpsilon();
		this.weight = 0.5 * Math.log((1 - epsilon) / epsilon);
	}

	public Hypothesis getHypothesis() {
		return hypothesis;
	}

	public void setHypothesis(Hypothesis hypothesis) {
		this.hypothesis = hypothesis;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

/*	public double[] getZ() {
		return z;
	}

	public void setZ(double[] z) {
		this.z = z;
	}
*/
	public double getzNorm() {
		return zNorm;
	}

	public void setzNorm(double zNorm) {
		this.zNorm = zNorm;
	}

	/*
	 * public double[] getProbNorm() { return probNorm; }
	 * 
	 * public void setProbNorm(double[] probNorm) { this.probNorm = probNorm; }
	 */
	public double getBound() {
		return bound;
	}

	public void setBound(double bound) {
		this.bound = bound;
	}

	public double[] getProbPreNorm() {
		return probPreNorm;
	}

	public void setProbPreNorm(double[] probPreNorm) {
		this.probPreNorm = probPreNorm;
	}

	public ArrayList<Sample> getSampleDataList() {
		return sampleDataList;
	}

	public void setSampleDataList(ArrayList<Sample> sampleDataList) {
		this.sampleDataList = sampleDataList;
	}

	public double[] getBoostedClassifierF() {
		return boostedClassifierF;
	}

	public void setBoostedClassifierF(double[] boostedClassifierF) {
		this.boostedClassifierF = boostedClassifierF;
	}

	public double getBoostedClassifierError() {
		return boostedClassifierError;
	}

	public void setBoostedClassifierError(double boostedClassifierError) {
		this.boostedClassifierError = boostedClassifierError;
	}

	public double[] getProbNorm() {
		return probNorm;
	}

	public void setProbNorm(double[] probNorm) {
		this.probNorm = probNorm;
	}

	public String getBoostedClassifier() {
		return boostedClassifier;
	}

	public void setBoostedClassifier(String boostedClassifier) {
		this.boostedClassifier = boostedClassifier;
	}

}
