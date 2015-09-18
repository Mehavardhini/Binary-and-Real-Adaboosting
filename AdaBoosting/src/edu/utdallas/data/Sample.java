package edu.utdallas.data;

public class Sample implements Cloneable{
	
	double x;
	int y;
	double prob;
	
	
	public Sample() {
		super();
	}

	public Sample(double x, int y, double prob) {
		super();
		this.x = x;
		this.y = y;
		this.prob = prob;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		String str="x :"+x+" y :"+y+" Prob :"+prob+"\n";
		return str;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}
	
	
	
	

}
