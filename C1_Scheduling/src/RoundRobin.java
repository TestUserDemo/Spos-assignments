import java.util.Arrays;
import java.util.Scanner;

public class RoundRobin {
	public static void main(String args[]) {
		
		Scanner	sc = new Scanner(System.in);

			//input
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
			Arrays.sort(process,new SortByArrival()); //sort according to arrival time

			System.out.println("Enter Quantum Time: ");
			int quantum=sc.nextInt();
			
			double avgWT=0,avgTAT=0;
			int time=0;
			System.out.println("\n\nPRNo\tBT\tAT\tCT\tTAT\tWT\tPR");
			System.out.println("============================================================================================");
			while(true)
			{
				boolean done=true;
				for(int i=0;i<numProcess;i++)
				{
					if(process[i].remBT>0 && process[i].AT<=time)
					{
						done=false;
						
						if(process[i].remBT>quantum) //Ajun time remaining aahe :v
						{
							time=time+quantum;
							process[i].remBT=process[i].remBT-quantum;
							System.out.println((i+1)+" TIME "+time);
						}
						else //process will finish execution
						{	
							time+=process[i].remBT;
							System.out.println((i+1)+" TIME "+time);
							System.out.println();

							process[i].remBT=0;
							process[i].CT=time;
							process[i].TAT=process[i].CT-process[i].AT;
							process[i].WT=process[i].TAT-process[i].BT;
							avgWT+=process[i].WT;
							avgTAT+=process[i].TAT;
							process[i].display();
						}
					}
				}
				if(done==true) //done executing all processes
				{
					break;
				}
			}
		}
}
/*
4
0 10 1 4 2 5 3 3
3

5
0 300 0 120 0 400 0 150 0 100
50
*/
