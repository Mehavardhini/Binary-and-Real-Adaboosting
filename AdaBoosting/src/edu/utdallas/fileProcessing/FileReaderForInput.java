package edu.utdallas.fileProcessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import edu.utdallas.data.InputConfig;
import edu.utdallas.data.Sample;
import edu.utdallas.utilities.DebuggingClass;

public class FileReaderForInput {
	
	public int fileReader(String fileName,InputConfig inputConfig){
		
		Scanner scanInputFile = null;

		try {
			scanInputFile = new Scanner(new BufferedReader(new FileReader(fileName)));
			
			inputConfig.setNoOfIteration(scanInputFile.nextInt());
			
			int noOfSamples=scanInputFile.nextInt();
			inputConfig.setNoOfSamples(noOfSamples);
			
			inputConfig.setEpsilon(scanInputFile.nextDouble());
			
			ArrayList<Sample> sampleDataList= new ArrayList<Sample>();
			
			for(int i=0;i<noOfSamples;i++){
				Sample sampleData = new Sample();
				sampleData.setX(scanInputFile.nextDouble());
				sampleDataList.add(sampleData);				
			}
			
			for(Sample sampleData:sampleDataList){
				sampleData.setY(scanInputFile.nextInt());
			}
			
			for(Sample sampleData:sampleDataList){
				sampleData.setProb(scanInputFile.nextDouble());
			}
			
			inputConfig.setSampleDataList(sampleDataList);
			DebuggingClass.println(inputConfig.toString());
		}
		catch(Exception e){
			
			System.err.println("Input File Reader Failed");
			e.printStackTrace();
			return -1;
			
		}
		finally{
			scanInputFile.close();
		}

		
		
		return 0;
		
	}

}
