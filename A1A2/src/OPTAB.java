import java.util.HashMap;

public class OPTAB {
	HashMap<String,IntermediateCode> optab;
	public OPTAB() {
		
		optab=new HashMap<>();
		optab.put("STOP", new IntermediateCode("IS", "00"));
		optab.put("ADD", new IntermediateCode("IS", "01"));
		optab.put("SUB", new IntermediateCode("IS", "02"));
		optab.put("MULT", new IntermediateCode("IS", "03"));
		optab.put("MOVER", new IntermediateCode("IS", "04"));
		optab.put("MOVEM", new IntermediateCode("IS", "05"));
		optab.put("COMP", new IntermediateCode("IS", "06"));
		optab.put("BC", new IntermediateCode("IS", "07"));
		optab.put("DIV", new IntermediateCode("IS", "08"));
		optab.put("READ", new IntermediateCode("IS", "09"));
		optab.put("PRINT", new IntermediateCode("IS", "10"));
		
		optab.put("START", new IntermediateCode("AD", "01"));
		optab.put("END", new IntermediateCode("AD", "02"));
		optab.put("ORIGIN", new IntermediateCode("AD", "03"));
		optab.put("EQU", new IntermediateCode("AD", "04"));
		optab.put("LTORG", new IntermediateCode("AD", "05"));
		
		optab.put("DC", new IntermediateCode("DL", "01"));
		optab.put("DS", new IntermediateCode("DL", "02"));
	}
	
}
