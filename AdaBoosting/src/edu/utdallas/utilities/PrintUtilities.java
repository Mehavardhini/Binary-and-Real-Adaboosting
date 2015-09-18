package edu.utdallas.utilities;

import edu.utdallas.data.RealAdaClassifier;

public class PrintUtilities {

	public static void printArray(double array[]) {

		for (int i = 0; i < array.length; i++) {
			if (i < array.length - 1) {
				System.out.print(RealAdaClassifier.df.format(array[i]) + ", ");
			} else {
				System.out.println(RealAdaClassifier.df.format(array[i]));
			}
		}
	}

}
