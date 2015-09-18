package edu.utdallas.data;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.utdallas.utilities.PrintUtilities;

public class RealAdaClassifier {

	RealAdaHypothesis hypothesis;
	double weightCtPositive;
	double weightCtNegative;
	double probPreNorm[];
	double probNorm[];
	double zNormalization;
	double ft[];
	double epsilon;
	double errorBoostedClassifier;
	double bound;
	ArrayList<Sample> listOfSampleData;
	double gt[];
	double boostedClassifierF[];
	public static DecimalFormat df = new DecimalFormat("#.###");
	
	

	public RealAdaClassifier(RealAdaHypothesis hypothesis,
			ArrayList<Sample> sampleDataList,double epsilon) {
		super();
		this.hypothesis = hypothesis;
		this.listOfSampleData = sampleDataList;
		this.epsilon=epsilon;
	}
	
	
	public void formattedClassiferData(){
		
		
		System.out.println("Classifier h = I(x " + hypothesis.getOperator()+ " "+ hypothesis.getThreshold() + ")");
		System.out.println("G error = "+df.format(this.hypothesis.getGt()));
		System.out.println("C_Plus = "+df.format(this.weightCtPositive)+ ", C_Minus = "+df.format(this.weightCtNegative));
		System.out.println("Normalization Factor Z = "+df.format(this.zNormalization));
		
		System.out.print("Pi after normalization = ");
		PrintUtilities.printArray(this.probNorm);
		
		System.out.print("f(x) = ");
		PrintUtilities.printArray(this.boostedClassifierF);
		
		System.out.println("Boosted Classifier Error = "+df.format(this.errorBoostedClassifier));
		System.out.println("Bound on Error = "+df.format(this.bound));
		
	}

	


	public void printClassiferData(){
		System.out.println("The selected weak classifier : h(x" + hypothesis.getOperator()+ hypothesis.getThreshold() + ")");
		System.out.println("The G error value of ht : "+this.hypothesis.getGt());
		System.out.println("The weight of Ct+ : "+this.weightCtPositive);
		System.out.println("The weight of Ct- : "+this.weightCtNegative);
		
		/*System.out.print("The probabilities  Pre-normalized(pi): ");
		PrintUtil.printArray(this.probPreNorm);
		System.out.println();
		*/
		
		System.out.println("The probabilities normalization factor(Zt) : "+this.zNormalization);
		
		System.out.print("The probabilities after normalization(pi) : ");
		PrintUtilities.printArray(this.probNorm);
		System.out.println();
		
		System.out.print("The values ft(xi) for each one of the examples : ");
		PrintUtilities.printArray(this.boostedClassifierF);
		System.out.println();
		
		System.out.println("The error of the boosted classifier(Et) : "+this.errorBoostedClassifier);
		System.out.println("The bound on Et(Z) : "+this.bound);
		
	}

	
	public void computeClassifierData(ArrayList<RealAdaClassifier> prevRealAdaClassifierDataList){
		computeCtPosAndCtNeg();
		computePreNormalizedProb();
		computeZNorm();
		computeNormalizedProb();
		computeBoostedClassiferF(prevRealAdaClassifierDataList);
		computeBoostedClassifierError();
		computeBound(prevRealAdaClassifierDataList);	
	}
	
	public void computeBoostedClassifierError(){
		this.errorBoostedClassifier=0;
		for (int i = 0; i < listOfSampleData.size(); i++) {
			Sample sampleData = listOfSampleData.get(i);
			if(sampleData.getY()==-1 && boostedClassifierF[i] > 0){
				this.errorBoostedClassifier++;
			}
			else if(sampleData.getY()==1 && boostedClassifierF[i] <= 0){
				this.errorBoostedClassifier++;
				
			}		
		}
		this.errorBoostedClassifier/=listOfSampleData.size();
	}
	
	public void computeBoostedClassiferF(
			ArrayList<RealAdaClassifier> prevRealAdaClassifierDataList) {
		this.boostedClassifierF = new double[listOfSampleData.size()];
		double[] prevBoostedClassifierF = null;

		// Get the values for the previous hypothesis
		if (prevRealAdaClassifierDataList != null
				&& prevRealAdaClassifierDataList.size() > 0) {
			RealAdaClassifier prevRealAdaClassifierData = prevRealAdaClassifierDataList
					.get(prevRealAdaClassifierDataList.size() - 1);
			prevBoostedClassifierF = prevRealAdaClassifierData
					.getBoostedClassifierF();
		}
		
		for (int i = 0; i < listOfSampleData.size(); i++) {
			double prevClassiferF=(prevBoostedClassifierF != null)? prevBoostedClassifierF[i]: 0.0;
			// computing for current hypothesis			
			this.boostedClassifierF[i] = prevClassiferF+ gt[i];
			
		}
		
	}
	
	
	
	public void computeBound(ArrayList<RealAdaClassifier> prevRealAdaClassifierDataList) {
		this.bound = zNormalization;
		for (RealAdaClassifier realAdaClassifierData : prevRealAdaClassifierDataList) {
			this.bound *= realAdaClassifierData.getzNorm();
		}
	}
	
	public void computeNormalizedProb() {
		this.probNorm= new double[listOfSampleData.size()];
		for (int i = 0; i < listOfSampleData.size(); i++) {
			Sample sampleData = listOfSampleData.get(i);
			double prob = probPreNorm[i]/ this.zNormalization;
			sampleData.setProb(prob);
			probNorm[i]=prob;
		}

	}
	
	public void computePreNormalizedProb() {
		probPreNorm = new double[listOfSampleData.size()];
		gt=new double[listOfSampleData.size()];
		
		for (int i = 0; i < listOfSampleData.size(); i++) {
			Sample sampleData = listOfSampleData.get(i);
			
			if (hypothesis.hypothesisPrediction(sampleData)==1) {
				gt[i]=weightCtPositive;				
			} else {
				gt[i]=weightCtNegative;
			}
			this.probPreNorm[i] = Math.exp(-sampleData.getY()*gt[i])*sampleData.getProb();
		}
	}
	
	
	public void computeZNorm() {
		this.zNormalization = 0.0;
		for (double prob : probPreNorm) {
			this.zNormalization += prob;
		}
	}

	
	public void computeCtPosAndCtNeg(){
		
		weightCtPositive= 0.5* Math.log( (hypothesis.getProbRpos() + epsilon) / (hypothesis.getProbWneg()+epsilon));
		weightCtNegative= 0.5* Math.log( (hypothesis.getProbWpos() + epsilon) / (hypothesis.getProbRneg()+epsilon));
		//System.out.println(hypothesis.getProbRpos()+" "+hypothesis.getProbRneg()+" "+hypothesis.getProbWpos()+" "+hypothesis.getProbWneg());
		
	}



	public RealAdaHypothesis getHypothesis() {
		return hypothesis;
	}



	public void setHypothesis(RealAdaHypothesis hypothesis) {
		this.hypothesis = hypothesis;
	}



	public double getWeightCtPos() {
		return weightCtPositive;
	}



	public void setWeightCtPos(double weightCtPos) {
		this.weightCtPositive = weightCtPos;
	}



	public double getWeightCtNeg() {
		return weightCtNegative;
	}



	public void setWeightCtNeg(double weightCtNeg) {
		this.weightCtNegative = weightCtNeg;
	}



	public double[] getProbPreNorm() {
		return probPreNorm;
	}



	public void setProbPreNorm(double[] probPreNorm) {
		this.probPreNorm = probPreNorm;
	}



	public double[] getProbNorm() {
		return probNorm;
	}



	public void setProbNorm(double[] probNorm) {
		this.probNorm = probNorm;
	}



	public double getzNorm() {
		return zNormalization;
	}



	public void setzNorm(double zNorm) {
		this.zNormalization = zNorm;
	}



	public double[] getFt() {
		return ft;
	}



	public void setFt(double[] ft) {
		this.ft = ft;
	}



	public double getEpsilon() {
		return epsilon;
	}



	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}



	public double getBoostedClassifierError() {
		return errorBoostedClassifier;
	}



	public void setBoostedClassifierError(double boostedClassifierError) {
		this.errorBoostedClassifier = boostedClassifierError;
	}



	public double getBound() {
		return bound;
	}



	public void setBound(double bound) {
		this.bound = bound;
	}



	public ArrayList<Sample> getSampleDataList() {
		return listOfSampleData;
	}



	public void setSampleDataList(ArrayList<Sample> sampleDataList) {
		this.listOfSampleData = sampleDataList;
	}



	public double[] getGt() {
		return gt;
	}



	public void setGt(double[] gt) {
		this.gt = gt;
	}



	public double[] getBoostedClassifierF() {
		return boostedClassifierF;
	}



	public void setBoostedClassifierF(double[] boostedClassifierF) {
		this.boostedClassifierF = boostedClassifierF;
	}
	
	
	
	
}
