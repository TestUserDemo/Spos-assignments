import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class MacroPass1 {

	public static void main(String[] args) throws IOException {
		
		String line;
		String macroName=null;
		int flag=0,pp=0,kp=0,mdtp=1,kpdtp=1;
		int paramNo=1;
		
		BufferedReader br = new BufferedReader(new FileReader("macro_input.asm"));
		
		FileWriter mnt = new FileWriter("mnt.txt");
		FileWriter mdt = new FileWriter("mdt.txt");
		FileWriter kpdt = new FileWriter("kpdt.txt");
		FileWriter pnt = new FileWriter("pnt.txt");
		FileWriter ic = new FileWriter("intermediate.txt");
		
		LinkedHashMap<String,Integer> pntab= new LinkedHashMap<>(); //To give number to the parameters of macro
		
		while((line=br.readLine())!=null) {
			
			String parts[] = line.split("\\s+");	//splits elements by space
			
			if(parts[0].equalsIgnoreCase("MACRO"))	//processing parameters and updating pntab,mnt,kpdt
			{
				flag=1;						//to process inside macro
				line = br.readLine();		//read next line
				parts=line.split("\\s+");	//	M1 &X, &Y, &A=AREG, &B=
				macroName=parts[0];			//	M1
				
				if(parts.length<=1)	//if macro doesn't have parameters
				{
					mnt.write(parts[0]+"\t"+pp+"\t"+kp+"\t"+mdtp+"\t"+kpdtp+"\n");
					continue;
				}
				
				for(int i=1;i<parts.length;i++)	//processing of parameters
				{
					parts[i]=parts[i].replaceAll("[&,]", "");	//M1 X Y A=AREG B=
					if(parts[i].contains("="))	//check for keyword param
					{
						++kp; //%%%%%%
						
						String keywordParam[]=parts[i].split("="); //A AREG  %   B
						
						pntab.put(keywordParam[0], paramNo++);		//A 3	% B 4
						
						if(keywordParam.length==2)
						{
							kpdt.write(keywordParam[0]+"\t"+keywordParam[1]+"\n");	//A AREG
						}
						else
						{
							kpdt.write(keywordParam[0]+"\t-\n");	//B -
						}
					}
					else	//check for positional param
					{
						pntab.put(parts[i], paramNo++);	//X 1	% Y 2
						pp++;
					}
				}
				mnt.write(parts[0]+"\t"+pp+"\t"+kp+"\t"+mdtp+"\t"+kpdtp+"\n");
				kpdtp=kpdtp+kp;
			}
			
			else if(parts[0].equalsIgnoreCase("MEND"))	//updating pnt
			{
				mdt.write(line+"\n");
				flag=pp=kp=0;	//reset after macro end
				mdtp++;
				paramNo=1;
				
				pnt.write(macroName+":\t");
				
				Iterator<String> itr = pntab.keySet().iterator();
				
				while(itr.hasNext())
				{
					pnt.write(itr.next()+"\t");
				}
				pnt.write("\n");
				pntab.clear();
			}
			
			else if(flag==1)	//inside macro definition, update mdt
			{
				for(int i=0;i<parts.length;i++)
				{
					if(parts[i].contains("&"))
					{
						parts[i]=parts[i].replaceAll("[&,]", "");	//A X    %     A ='1'
						mdt.write("(P"+pntab.get(parts[i])+")\t");
					}
					else	//instructions or literals
					{
						mdt.write(parts[i]+"\t");
					}
				}
				mdt.write("\n");
				mdtp++;
			}
			
			else	//else write in intermediate code
			{
				ic.write(line+"\n");
			}
		}
		
		br.close();
		mnt.close();
		mdt.close();
		ic.close();
		kpdt.close();
		pnt.close();
		
		System.out.println("Macro pass 1 processing done");
	}

}
