package edu.utdallas.classification;

import java.util.ArrayList;

import edu.utdallas.data.BinaryAdaClassifier;
import edu.utdallas.data.Hypothesis;
import edu.utdallas.data.Sample;
import edu.utdallas.utilities.DebuggingClass;

public class BinaryAdaBoosting extends AdaBoosting{

	private ArrayList<BinaryAdaClassifier> binaryClassifierDataList = new ArrayList<BinaryAdaClassifier>();

	public BinaryAdaBoosting(int noOfIterations,
			ArrayList<Sample> sampleDataList,double epsilon) {
		super(noOfIterations, sampleDataList,epsilon);
	}

	public int runClassifier() {
		System.out.println("\t\tBinary ADA Boosting classifier");
		System.out.println("=============================================================================================");
		for (int i = 0; i < iterationCount; i++) {
			//System.out.println("****************************************************************************************");
			System.out.println("Iteration "+(i+1));
			Hypothesis bestHypothesis = chooseBestHypothesis();
			BinaryAdaClassifier binaryAdaClassifierData = new BinaryAdaClassifier(bestHypothesis, sampleData);
			binaryAdaClassifierData.setHypothesis(bestHypothesis);
			binaryAdaClassifierData.computeClassifierData(binaryClassifierDataList);
			//binaryAdaClassifierData.printClassiferData();
			binaryAdaClassifierData.formattedClassifierData();
			binaryClassifierDataList.add(binaryAdaClassifierData);
			System.out.println();
			
		}
		return 0;
	}
	
	public Hypothesis chooseBestHypothesis() {
		Hypothesis bestHypothesis = null;
		int noOfHypothesis = sampleData.size() + 1;

		double threshold;

		for (int i = 0; i < noOfHypothesis; i++) {

			if (i == 0) {
				threshold = sampleData.get(i).getX() - 0.5;
			} else if (i == noOfHypothesis - 1) {
				threshold = sampleData.get(i - 1).getX() + 0.5;
			} else {
				threshold = (sampleData.get(i - 1).getX() + sampleData
						.get(i).getX()) / 2;
			}

			DebuggingClass.println("****************Hypothesis:Threshold["
					+ threshold + "]****************");
			// Hypothesis hypothesis = new Hypothesis(threshold, ' ', 0.0, 0);

			Hypothesis hypothesis = evaluateHypothesis(threshold);

			if (bestHypothesis == null) {
				bestHypothesis = hypothesis;
			} else if (bestHypothesis.getEpsilon() > hypothesis.getEpsilon()) {
				bestHypothesis = hypothesis;
			}

			// Debugger.println("****************************************************************************");

		}

		DebuggingClass.println("-----------------Selected Hypothesis---------------------");
		DebuggingClass.println("Hypothesis: h(x" + bestHypothesis.getOperator()
				+ bestHypothesis.getThreshold() + ")");
		DebuggingClass.println("Epsilon:" + bestHypothesis.getEpsilon());
		DebuggingClass.println("No Of Errors:" + bestHypothesis.getNoOfErrors());

		return bestHypothesis;

	}


	Hypothesis evaluateHypothesis(double threshold) {

		Hypothesis leftPosHypothesis = new Hypothesis(threshold, '<', 0, 0);
		Hypothesis leftNegHypothesis = new Hypothesis(threshold, '>', 0, 0);

		double leftNegEpsilon = 0.0;
		double leftPosEpsilon = 0.0;

		int noOfLeftPosErrors = 0;
		int noOfLeftNegErrors = 0;

		for (int i = 0; i < sampleData.size(); i++) {
			Sample sample = sampleData.get(i);

			// Left Positive Hypothesis h(x<v)
			if (!leftPosHypothesis.isValidData(sample)) {
				leftPosEpsilon = leftPosEpsilon + sample.getProb();
				noOfLeftPosErrors++;
			}

			// Left Negative Hypothesis h(x>v)
			if (!leftNegHypothesis.isValidData(sample)) {
				leftNegEpsilon = leftNegEpsilon + sample.getProb();
				noOfLeftNegErrors++;

			}
		}

		leftPosHypothesis.setEpsilon(leftPosEpsilon);
		leftPosHypothesis.setNoOfErrors(noOfLeftPosErrors);

		leftNegHypothesis.setEpsilon(leftNegEpsilon);
		leftNegHypothesis.setNoOfErrors(noOfLeftNegErrors);

		DebuggingClass.println("Hypothesis: h(X < "
				+ leftPosHypothesis.getThreshold() + ")");
		DebuggingClass.println("No of Errors:" + leftPosHypothesis.getNoOfErrors());
		DebuggingClass.println("Epsilon:" + leftPosHypothesis.getEpsilon());

		DebuggingClass.println("Hypothesis: h(X > "
				+ leftNegHypothesis.getThreshold() + ")");
		DebuggingClass.println("No of Errors:" + leftNegHypothesis.getNoOfErrors());
		DebuggingClass.println("Epsilon:" + leftNegHypothesis.getEpsilon());

		if (leftPosHypothesis.getEpsilon() < leftNegHypothesis.getEpsilon()) {
			return leftPosHypothesis;
		} else {
			return leftNegHypothesis;
		}

	}
	
	public ArrayList<BinaryAdaClassifier> getBinaryClassifierDataList() {
		return binaryClassifierDataList;
	}

	public void setBinaryClassifierDataList(
			ArrayList<BinaryAdaClassifier> binaryClassifierDataList) {
		this.binaryClassifierDataList = binaryClassifierDataList;
	}

}