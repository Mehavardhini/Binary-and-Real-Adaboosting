package edu.utdallas.classification;

import java.util.ArrayList;

import edu.utdallas.data.BinaryAdaClassifier;
import edu.utdallas.data.Hypothesis;
import edu.utdallas.data.InputConfig;
import edu.utdallas.data.RealAdaClassifier;
import edu.utdallas.data.RealAdaHypothesis;
import edu.utdallas.data.Sample;
import edu.utdallas.utilities.DebuggingClass;

public class RealAdaBoosting extends AdaBoosting {

	private ArrayList<RealAdaClassifier> realAdaClassifierDataList = new ArrayList<RealAdaClassifier>();
	
	public RealAdaBoosting(int noOfIterations,
			ArrayList<Sample> sampleDataList,double epsilon) {
		super(noOfIterations, sampleDataList,epsilon);
	}

	
	public int runClassifier() {
		
		System.out.println("\n\n\t\tReal ADA Boosting classifier");
		System.out.println("=============================================================================================");
		
		
		for (int i = 0; i < iterationCount; i++) {
			//System.out.println("****************************************************************************************");
			System.out.println("Iteration "+(i+1));
			RealAdaHypothesis bestHypothesis = chooseBestHypothesis();
			
			RealAdaClassifier realAdaClassifierData = new RealAdaClassifier(bestHypothesis, sampleData,epsilon);
			realAdaClassifierData.setHypothesis(bestHypothesis);
			realAdaClassifierData.computeClassifierData(realAdaClassifierDataList);
			//realAdaClassifierData.printClassiferData();
			realAdaClassifierData.formattedClassiferData();
			realAdaClassifierDataList.add(realAdaClassifierData);
			System.out.println();
			
		}
		return 0;
	}

	public RealAdaHypothesis chooseBestHypothesis() {
		RealAdaHypothesis bestHypothesis = null;
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

			RealAdaHypothesis hypothesis = evaluateHypothesis(threshold);

			if (bestHypothesis == null) {
				bestHypothesis = hypothesis;
			} else if (bestHypothesis.getGt() > hypothesis.getGt()) {
				bestHypothesis = hypothesis;
			}

			// Debugger.println("****************************************************************************");

		}

		DebuggingClass.println("-----------------Selected Hypothesis---------------------");
		DebuggingClass.println(bestHypothesis.toString());

		return bestHypothesis;

	}


	RealAdaHypothesis evaluateHypothesis(double threshold) {

		RealAdaHypothesis leftPosHypothesis = new RealAdaHypothesis(threshold, '<');
		RealAdaHypothesis leftNegHypothesis = new RealAdaHypothesis(threshold, '>');


		for (int i = 0; i < sampleData.size(); i++) {
			Sample sample = sampleData.get(i);

			// Left Positive Hypothesis h(x<v)
			leftPosHypothesis.probabilityComputing(sample);
			leftPosHypothesis.computeGt();
			
			leftNegHypothesis.probabilityComputing(sample);
			leftNegHypothesis.computeGt();
			
		}


		DebuggingClass.println(leftPosHypothesis.toString());

		DebuggingClass.println(leftNegHypothesis.toString());

		if (leftPosHypothesis.getGt() <= leftNegHypothesis.getGt()) {
			return leftPosHypothesis;
		} else {
			return leftNegHypothesis;
		}

	}

	
}
