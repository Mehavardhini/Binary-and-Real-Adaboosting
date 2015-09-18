package edu.utdallas.classification;

import java.util.ArrayList;

import edu.utdallas.data.Hypothesis;
import edu.utdallas.data.Sample;
import edu.utdallas.utilities.DebuggingClass;

public abstract class AdaBoosting {

	protected int iterationCount;
	protected ArrayList<Sample> sampleData;
	protected double epsilon;

	public AdaBoosting(int iterationCount,
			ArrayList<Sample> sampleDataList, double epsilon) {
		super();
		this.iterationCount = iterationCount;
		this.sampleData = sampleDataList;
		this.epsilon = epsilon;
	}

	public int getNoOfIterations() {
		return iterationCount;
	}

	public void setNoOfIterations(int noOfIterations) {
		this.iterationCount = noOfIterations;
	}

	public ArrayList<Sample> getSampleDataList() {
		return sampleData;
	}

	public void setSampleDataList(ArrayList<Sample> sampleDataList) {
		this.sampleData = sampleDataList;
	}



	public double getEpsilon() {
		return epsilon;
	}



	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

}