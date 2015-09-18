package edu.utdallas.utilities;

public class DebuggingClass {
	
	static boolean isEnabled=true;
	
	public static boolean isEnabled() {
		return isEnabled;
	}

	public static void setEnabled(boolean isEnabled) {
		DebuggingClass.isEnabled = isEnabled;
	}

	public static void print(String printStr)
	{
		if(isEnabled)
		System.out.print(printStr);
	}
	
	public static void println(String printStr)
	{
		if(isEnabled)
		System.out.println(printStr);
	}

}
