package ca.quadrilateral.btester;

public class SubSubClass extends SubClass {
	private String b;
	
	@Override
	public String getB() {
		return this.b + this.b;
	}
	
	@Override
	public void setB(final String b) {
		this.b = b;
	}

}
