import java.util.ArrayList;
import java.util.Scanner;

public class Bankers {

	public static void main(String[] args) {
		
		int r,p;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter number of resources");
		r=sc.nextInt();
		
		System.out.println("Enter number of processes");
		p=sc.nextInt();
		
		int[][] allocated = new int[p][r];
		int[][] max = new int[p][r];
		int[][] need = new int[p][r];
		
		int[] available = new int[r];
		int[] resources = new int[r];
		
		System.out.println("Enter the resource values");	//10 5 7
		for(int i=0;i<r;i++) {
			System.out.print("Resource R"+(i+1)+": ");
			resources[i]=sc.nextInt();
			
		}
		
		System.out.println("Enter the resources allocated to each process");	//allocated
		for(int i=0;i<p;i++) {
			System.out.println("**Process P**"+(i+1)+": ");
			for(int j=0;j<r;j++) {
				System.out.print("Resource R"+(j+1)+": ");
				allocated[i][j] = sc.nextInt();
				
			}
		}
		
		System.out.println("Enter max resources allocated to each process");	//max
		for(int i=0;i<p;i++) {
			System.out.println("**Process P**"+(i+1)+": ");
			for(int j=0;j<r;j++) {
				System.out.print("Resource R"+(j+1)+": ");
				max[i][j] = sc.nextInt();
				
			}
		}
		System.out.println();
		
		//available resource calculation
		for(int i=0;i<r;i++) {
			int sum=0;
			for(int j=0;j<p;j++) {
				sum+= allocated[j][i];
			}
			available[i] = resources[0] - sum;
		}
		
		//calculate need matrix
		for(int i=0;i<p;i++) {
			for(int j=0;j<r;j++) {
				need[i][j]=max[i][j]-allocated[i][j];
			}
		}
		
		ArrayList<Boolean> done = new ArrayList<Boolean>();
		for(int i=0;i<p;i++) {
			done.add(false);
		}
		
		ArrayList<Integer> safe = new ArrayList<Integer>();
		
		Bankers.safe(r,p,available,allocated,max,need, done,safe);
	}
	
	public static Boolean is_possible(int i, int r, int available[],int allocated[][],int max[][],int need[][]) {
		
		Boolean flag=true;
		
		for(int k=0;k<r;k++) {
			if(need[i][k]>available[k]) {
				flag=false;
			}
		}
		return flag;
	}
	
	public static void safe(int r,int p, int available[],int allocated[][],int max[][],int need[][],ArrayList<Boolean> done,ArrayList<Integer> safe) 
	{
		
		for(int i=0;i<p;i++) {
			
			if(done.get(i)==false && is_possible(i,r,available,allocated,max,need)==true) {
				
				for(int j=0;j<r;j++) {
					available[j]+=allocated[i][j];
				}
				
				done.set(i, true);
				safe.add(i);
				
				Bankers.safe(r, p, available, allocated, max, need, done, safe);
				
				safe.remove(safe.size()-1);
				done.set(i, false);
				
				for(int j=0;j<r;j++) {
					
					available[j]-=allocated[i][j];
				}
			}	
		}
		
		//if safe sequence is found, display it
			
		if(safe.size()==p) {
			for(int i=0;i<p;i++) {
				
				System.out.print("P="+(safe.get(i)+1));
				if(i!=(p-1)) {
					System.out.print("--> ");
				}
				else
					System.out.println();
			}
		}
	}
	
		public static void print(int r,int p,int available[],int need[][]) {
			
			System.out.println("Available");
			for(int i=0;i<r;i++) {
				System.out.println(available[i]+"\t");
			}
			
			System.out.println("Need");
			for(int i=0;i<p;i++) {
				for(int j=0;j<r;j++) {
					System.out.println(need[i][j]+"\t");
				}
			}
		}
}

/*
 3
 4
 10 5 7
 0 1 0 2 0 0 3 0 2 2 1 1
 7 5 3 3 2 2 9 0 2 2 2 2
 */
