import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Pass1 {
	public static void main(String[] args) {
		File file=new File("program.asm");
		
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
		
		OPTAB optab=new OPTAB();
		HashMap<String,Integer> symbol_address=new HashMap<>();
		int lc=0;
		int littab_ptr=0,pooltab_ptr=0;
		int sym_no=0;
		
		POOLTAB.add(pooltab_ptr);
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			BufferedWriter bw=new BufferedWriter(new FileWriter("IC.txt"));
			String s;
			while((s=br.readLine())!=null)
			{
				String[] arr=s.split("\t",-1);
				if(!arr[0].equals(""))
				{
					Symbol obj;
					boolean flag=false;
					if(!symbol_address.containsKey(arr[0]))
					{
						obj=new Symbol();
						obj.name=arr[0];
						sym_no++;	
					}
					else
					{
						symbol_address.remove(arr[0]);
						obj=new Symbol();
						obj.name=arr[0];
						flag=true;	
					}
					int address;
					if(arr[1].equals("EQU"))
					{
						if(arr[2].contains("+"))
						{
							String[] addr=arr[2].split("\\+");
							obj.address=symbol_address.get(addr[0])+Integer.valueOf(addr[1]);
						}
						else if(arr[2].contains("-"))
						{
							
							String[] addr=arr[2].split("-");
							obj.address=symbol_address.get(addr[0])-Integer.valueOf(addr[1]);
						}
						else
						{
							obj.address=symbol_address.get(arr[2]);
						}
					}
					else
					{
						obj.address=lc;
					}
					if(flag==false)
					SYMTAB.add(obj);
					else
					{
						for(Symbol sym:SYMTAB)
						{
							if(sym.name.equals(arr[0]))
							{
								sym.address=obj.address;
								break;
							}
						}
					}
					
					symbol_address.put(obj.name, obj.address);
				}
				
				if(arr[1].contains("START"))
				{
					lc=Integer.valueOf(arr[2]);
				}
				else if(arr[1].equals("ORIGIN"))
				{
					if(arr[2].contains("+"))
					{
						String[] addr=arr[2].split("\\+");
						lc=symbol_address.get(addr[0])+Integer.valueOf(addr[1]);
					}
					else if(arr[2].contains("-"))
					{
						String[] addr=arr[2].split("-");
						lc=symbol_address.get(addr[0])-Integer.valueOf(addr[1]);
					}
					else
					{
						lc=symbol_address.get(arr[2]);
					}
				}
				else if(arr[1].equals("LTORG"))
				{
					System.out.println("\t(AD,05)");
					bw.write("\t(AD,05)\t");
					bw.write("\n");
					
					String intermediate;
					for(int i=POOLTAB.get(pooltab_ptr);i<LITTAB.size();i++)
					{
						s=br.readLine();
						LITTAB.get(i).address=lc;
					
						String name=LITTAB.get(i).name.replace("=","");
						name=name.replace("'", "");
						intermediate=Integer.toString(lc)+"\t(DL,01)\t(C,"+name+")";
						System.out.println(intermediate);
						bw.write(intermediate);
						bw.write("\n");
						lc++;
					}
					pooltab_ptr++;
					POOLTAB.add(littab_ptr);
				}
				
				if(!arr[1].equals(""))
				{
					IntermediateCode ic=optab.optab.get(arr[1]);

					String intermediate="";
					if(!ic.class_name.equals("AD"))
					{
						intermediate+=Integer.toString(lc)+"\t";
					}
					else
					{
						intermediate+="\t";
					}
					intermediate+=ic.toString()+"\t";
			
					if(ic.class_name.equals("AD"))
					{
						if(ic.code.equals("01"))
						{
							intermediate+="(C,"+arr[2]+")";
						}
						else if(ic.code.equals("02"))
						{
							System.out.println("\t(AD,02)");
							bw.write("\t(AD,02)\t");
							bw.write("\n");
							for(int i=POOLTAB.get(pooltab_ptr);i<LITTAB.size();i++)
							{
								s=br.readLine();
								LITTAB.get(i).address=lc;
								
								String name=LITTAB.get(i).name.replace("=","");
								name=name.replace("'", "");
								intermediate=Integer.toString(lc)+"\t(DL,01)\t(C,"+name+")";
								System.out.println(intermediate);
								bw.write(intermediate);
								bw.write("\n");
								lc++;
							}
							intermediate="";
							
						}
						else if(!ic.code.equals("05"))
						{
							intermediate+="(S,0";
							int sym=1;
							String sym_name="";
							for(Symbol obj:SYMTAB)
							{
								if(arr[2].contains(obj.name))
								{
									sym_name=obj.name;
									break;
								}
								sym++;
							}
							if(arr[2].contains("+"))
							{
								String[] addr=arr[2].split("\\+");
								
								intermediate+=Integer.toString(sym)+")+"+addr[1];
							}
							else if(arr[2].contains("-"))
							{
								String[] addr=arr[2].split("-");
								intermediate+=Integer.toString(sym)+")-"+addr[1];
							}
							else
							{
								intermediate+=Integer.toString(sym)+")";
							}
						}
						else
						{
							intermediate="";
						}
					}
					else if(ic.class_name.equals("DL"))
					{
						//System.out.println(ic.toString());
						intermediate+="(C,"+arr[2]+")";
						if(intermediate.contains("02"))
						{
							for(Symbol str:SYMTAB)
							{
								if(str.name.equals(arr[0]))
								{
									str.length=Integer.valueOf(arr[2]);
									lc+=str.length;
									break;
								}
							}
						}
						else
							lc++;
						
					}
					else if(ic.class_name.equals("IS"))
					{
						String[] param=arr[2].split(",",-1);
						String operand="";
						int sym=1;
						String sym_name="";
						if(!ic.code.equals("00") && !ic.code.equals("10"))
						{
							for(Symbol obj:SYMTAB)
							{
								if(param[1].contains(obj.name))
								{
									sym_name=obj.name;
									break;
								}
								sym++;
							}
						}
						else
						{
							for(Symbol obj:SYMTAB)
							{
								if(arr[2].contains(obj.name))
								{
									sym_name=obj.name;
									break;
								}
								sym++;
							}
						}
						operand="(S,0"+sym+")";
						
						if(ic.code.equals("07"))
						{
							intermediate+="("+condition.get(param[0])+") ";
							if(!symbol_address.containsKey(param[1]))
							{
								Symbol obj=new Symbol();
								obj.name=param[1];
								//obj.address=lc;
								symbol_address.put(obj.name, obj.address);
								SYMTAB.add(obj);
								sym_no++;
								operand="(S,0"+Integer.toString(sym_no)+")";
								intermediate+=operand;
							}

						}
						else if(ic.code.equals("10"))
						{
							intermediate+="(S,0";
							intermediate+=Integer.toString(sym)+")";
							
						}
						else if(!ic.code.equals("00"))
						{
							if(arr[2].contains("="))
							{
								String[] arg=arr[2].split(",");
								Literal lit=new Literal(arg[1]);
								LITTAB.add(lit);
								littab_ptr++;
								operand="(L,0"+littab_ptr+")";

								intermediate+="("+registers.get(param[0])+")"+" "+operand;
							}
							else
							{
								if(!symbol_address.containsKey(param[1]))
								{
									Symbol obj=new Symbol();
									obj.name=param[1];
									symbol_address.put(obj.name, obj.address);
									SYMTAB.add(obj);
									sym_no++;
								}
								intermediate+="("+registers.get(param[0])+")"+" "+operand;
							}
						}	
						
						lc++;
					}
					System.out.println(intermediate);
					bw.write(intermediate);
					bw.write("\n");
				}
			}
			
			Pass1.display(SYMTAB, LITTAB, POOLTAB);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void display(ArrayList<Symbol> SYMTAB,ArrayList<Literal>LITTAB,ArrayList<Integer>POOLTAB) throws IOException
	{
		BufferedWriter bw_symtab=new BufferedWriter(new FileWriter("SYMTAB.txt"));
		BufferedWriter bw_littab=new BufferedWriter(new FileWriter("LITTAB.txt"));
		BufferedWriter bw_pooltab=new BufferedWriter(new FileWriter("POOLTAB.txt"));
		
		System.out.println("*****************SYMTAB****************");
		for(Symbol s:SYMTAB)
		{
			System.out.println(s.name+"\t"+s.address+"\t"+s.length);
			bw_symtab.write(s.name+"\t"+s.address+"\t"+s.length);
			bw_symtab.write("\n");
		}
		
		System.out.println("******************LITTAB*************");
		for(Literal lit:LITTAB)
		{
			System.out.println(lit.name+"\t"+lit.address);
			bw_littab.write(lit.name+"\t"+lit.address);
			bw_littab.write("\n");
		}
		System.out.println("***************POOLTAB************");
		for(Integer i:POOLTAB)
		{
			System.out.println(i);
			bw_pooltab.write(Integer.toString(i));
			bw_pooltab.write("\n");
		}
		bw_symtab.close();
		bw_pooltab.close();
		bw_littab.close();
		
	}
}
