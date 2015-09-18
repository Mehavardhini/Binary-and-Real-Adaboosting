package edu.utdallas.data;

import java.util.ArrayList;

public class InputConfig {

private int iterationCount;
private int sampleCount;
private double epsilon;
private ArrayList<Sample> sampleDataList;


@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str="T :"+iterationCount+"\n";
		str+="n :"+sampleCount+"\n";
		str+="epsilon :"+epsilon+"\n";
		for(Sample sampleData:sampleDataList){
			str+=sampleData.toString();
		}
		return str;
	}

public int getNoOfIteration() {
	return iterationCount;
}
public void setNoOfIteration(int noOfIteration) {
	this.iterationCount = noOfIteration;
}
public int getNoOfSamples() {
	return sampleCount;
}
public void setNoOfSamples(int noOfSamples) {
	this.sampleCount = noOfSamples;
}
public double getEpsilon() {
	return epsilon;
}
public void setEpsilon(double epsilon) {
	this.epsilon = epsilon;
}
public ArrayList<Sample> getSampleDataList() {
	return sampleDataList;
}
public void setSampleDataList(ArrayList<Sample> sampleDataList) {
	this.sampleDataList = sampleDataList;
}
}
