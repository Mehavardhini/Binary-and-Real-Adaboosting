package edu.utdallas.start;


import java.util.ArrayList;
import java.util.Collections;

import edu.utdallas.classification.BinaryAdaBoosting;
import edu.utdallas.classification.RealAdaBoosting;
import edu.utdallas.data.InputConfig;
import edu.utdallas.data.Sample;
import edu.utdallas.fileProcessing.FileUtilities;
import edu.utdallas.fileProcessing.FileReaderForInput;
import edu.utdallas.utilities.DebuggingClass;

public class ProcessStart {

	public static void main(String[] args) {
		
		
		if (args.length < 0) {
			System.err.println("Insufficent no of arguments: "
					+ args.length);
			System.err.println("1 arguments required");
			System.err.println("1.inputFile");
			System.err.println("eg.java -jar AdaBoostingClassifier.jar \"G:\\input\\input1.txt\" ");
			
			
			for (String arg : args) {
				System.out.println("Argument:" + arg);
			}
			return;
		}
		
		DebuggingClass.setEnabled(false);
		
		String inputFile=args[0];
		//String inputFile="H:/input files/adaboost-5.dat";
		
		InputConfig inputConfigData= new InputConfig();
		FileReaderForInput  inputFileReader = new FileReaderForInput();
		
		if(!FileUtilities.isFileExisting(inputFile)){
			return;
		}
		
		System.out.println("\n");
		inputFileReader.fileReader(inputFile, inputConfigData);
		

		BinaryAdaBoosting binaryAdaBoosting= new BinaryAdaBoosting(inputConfigData.getNoOfIteration(),inputConfigData.getSampleDataList(),inputConfigData.getEpsilon());
		binaryAdaBoosting.runClassifier();
		
		inputFileReader = new FileReaderForInput();
		inputFileReader.fileReader(inputFile, inputConfigData);

		RealAdaBoosting realAdaBoosting = new RealAdaBoosting(inputConfigData.getNoOfIteration(),inputConfigData.getSampleDataList(),inputConfigData.getEpsilon());
		realAdaBoosting.runClassifier();
		
	}
}
