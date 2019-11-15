public class Rule {
	
	public String symptom_name;
	
	public String bodypart_name;
	
	public String value;
	
	public String equality_type;
	
	public Rule(String name, String name2, String value, String type) {
		this.symptom_name = name;
		this.bodypart_name = name2;
		this.value = value;
		this.equality_type = type;
	}
}
