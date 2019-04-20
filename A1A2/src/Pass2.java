import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Pass2 {

	public static void main(String[] args) throws IOException {
		ArrayList<Symbol> SYMTAB=new ArrayList<>();
		ArrayList<Literal> LITTAB=new ArrayList<>();
		ArrayList<Integer> POOLTAB=new ArrayList<>();
		
		HashMap<String,String>registers=new HashMap<>();
		registers.put("AREG","1");
		registers.put("BREG","2");
		registers.put("CREG","3");
		registers.put("DREG","4");
		
		HashMap<String,String> condition=new HashMap<>();
		condition.put("LT","1");
		condition.put("LE","2");
		condition.put("EQ","3");
		condition.put("GT","4");
		condition.put("GE","5");
		condition.put("ANY","6");
		
		BufferedReader br=new BufferedReader(new FileReader("IC.txt"));
		BufferedReader br_symtab=new BufferedReader(new FileReader("SYMTAB.txt"));
		BufferedReader br_pooltab=new BufferedReader(new FileReader("POOLTAB.txt"));
		BufferedReader br_littab=new BufferedReader(new FileReader("LITTAB.txt"));
		BufferedWriter bw=new BufferedWriter(new FileWriter("Target.txt"));
		String s;
		
		while((s=br_symtab.readLine())!=null)
		{
			Symbol obj=new Symbol();
			String[] symbol=s.split("\t",-1);
			obj.name=symbol[0];
			obj.address=Integer.valueOf(symbol[1]);
			obj.length=Integer.valueOf(symbol[2]);
			SYMTAB.add(obj);
			//System.out.println(s);
		}
		
		while((s=br_littab.readLine())!=null)
		{
			String[] lit=s.split("\t",-1);

			Literal l=new Literal(lit[0]);
			l.address=Integer.valueOf(lit[1]);

			LITTAB.add(l);
			//System.out.println(s);
		}

		while((s=br_pooltab.readLine())!=null)
		{
			POOLTAB.add(Integer.valueOf(s));
			//System.out.println(Integer.valueOf(s));
		}


		
		while((s=br.readLine())!=null)
		{
			String[] arr=s.split("\t", -1);
			
			String out="";
			out+=arr[0]+"\t";
			if(!s.equals(""))
			{	
				if(arr[1].contains("IS"))
				{
					arr[1]=arr[1].replace("(", "");
					arr[2]=arr[2].replace("(", "");
	
					arr[1]=arr[1].replace(")", "");
					arr[2]=arr[2].replace(")", "");
					
					String[] ic=arr[1].split(",", -1);
						
					out+=ic[1]+"\t";
					String[] op=arr[2].split(" ",-1);
					
					
					if(!arr[1].contains("00") && !arr[1].contains("10"))
					{
						out+=op[0];
						String[] code=op[1].split(",", -1);
						if(op[1].contains("S"))
						{
							Symbol sym=SYMTAB.get(Integer.valueOf(code[1])-1);
							out+="\t"+sym.address;
							
						}
						else if(op[1].contains("L"))
						{
							Literal lit=LITTAB.get(Integer.valueOf(code[1])-1);
							out+="\t"+lit.address;

						}
					}
					else if(arr[1].contains("10"))
					{
						out+="0";
						String[] code=op[0].split(",", -1);
						//System.out.println(code[1]);
						Symbol sym=SYMTAB.get(Integer.valueOf(code[1])-1);
						out+="\t"+sym.address;
						
						
					}
					else
					{
						out+="0\t000";
					}
					
					
					
					
					//System.out.println(out);
					
				}
				else if(arr[1].contains("AD"))
				{
					
				}
				else if(arr[1].contains("DL"))
				{
					arr[1]=arr[1].replace("(", "");
					arr[2]=arr[2].replace("(", "");
	
					arr[1]=arr[1].replace(")", "");
					arr[2]=arr[2].replace(")", "");
					
					String[] ic=arr[1].split(",", -1);
					String[] cons=arr[2].split(",", -1);	
					out+="00\t0\t";

						out+=String.format("%03d", Integer.valueOf(cons[1]));
					
				}
		
			}
			System.out.println(out);
			bw.write(out);
			bw.write("\n");

		}	
		bw.close();
	}
}
