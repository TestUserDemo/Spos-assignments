
public class IntermediateCode {
	String class_name;
	String code;
	public IntermediateCode(String class_name, String code) {
		this.class_name = class_name;
		this.code = code;
	}
	@Override
	public String toString() {
		return "("+ class_name + "," + code + ")";
	}
	
	
}
