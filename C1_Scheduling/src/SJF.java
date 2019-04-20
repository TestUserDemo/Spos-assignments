import java.util.Scanner;

public class SJF {
	
		public static void main(String args[])
		{
			Scanner sc = new Scanner(System.in);

			System.out.println("Enter Number of Processes:");
			int numProcess=sc.nextInt();
			Process []process=new Process[numProcess];

			for(int i=0;i<numProcess;i++)
			{
				System.out.println("P("+(i+1)+"):Enter Arrival time & Burst time");
				int at=sc.nextInt();
				int bt=sc.nextInt();

				process[i]=new Process("P"+(i+1), bt, at);
			}

			int min=Integer.MAX_VALUE;
			int count=0,shortest=0;
			int time=0;
			int sum=0;
			double avgWT=0,avgTAT=0;
			boolean check=false;
			System.out.println("\n\nPRNo\tBT\tAT\tCT\tTAT\tWT");
			System.out.println("============================================================================================");
			while(count<numProcess)
			{
			    //	check=false;//remove this if given wrong i=output
				//find shortest process till time
				for(int i=0;i<numProcess;i++)
				{
					
					if(process[i].AT<=time &&(process[i].remBT<min && process[i].remBT>0))
					{
						shortest=i;
						min=process[i].remBT;
						check=true;
					}
				}
					if(check==false) //No process is present currently
					{
						time++;
						continue;
					}
					process[shortest].remBT--;
					min=process[shortest].remBT;
					
					if(min==0) //process completes its execution
					{
						min=Integer.MAX_VALUE;
						count++;
						sum=time+1;
						process[shortest].CT=sum;
						process[shortest].TAT=process[shortest].CT-process[shortest].AT;
						process[shortest].WT=process[shortest].TAT-process[shortest].BT;

						avgWT=avgWT+process[shortest].WT;
						avgTAT=avgTAT+process[shortest].TAT;

						process[shortest].display();
					}
					time++;	
			}
			
			avgTAT=(double)avgTAT/numProcess;
			avgWT=(double)avgWT/numProcess;
			System.out.println("Average Waiting Time"+avgWT);
			System.out.println("Average TAT="+avgTAT);
		}
}
/*
 6
 0 300 0 120 0 400 0 150 0 100 150 50
 */
