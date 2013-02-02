package ca.quadrilateral.btester;

public abstract class SuperClass {
	private String a;
	private String c;
	
	public String getA() {
		return a;
	}
	
	public void setA(final String a) {
		this.a = a;
	}
	
	protected String getC() {
		return c;		
	}
	
	protected void setC(final String c) {
		this.c = c;
	}
}
