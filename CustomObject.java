package schoolFile;

public class CustomObject {
	int valueNode,valueUpper,g,f;
	String Charter;
	String Upper;
	
	public CustomObject() {
		
	}
	public CustomObject(int f, String charter,int g) {
		super();
		this.f = f;
		this.g = g;
		Charter = charter;
	}

	public CustomObject(int h,String TTK,int k,String TT) {
		// TODO Auto-generated constructor stub
		this.valueNode=h;
		this.Charter = TTK;
		this.valueUpper=k;
		this.Upper = TT;
	}
	
}
