import java.util.ArrayList;
import java.util.Scanner;

public class Optimal {

	public static void main(String[] args) {
		
		int capacity,n;
		int page_fault=0,count=0;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter capacity of buffer");
		capacity=sc.nextInt();
		ArrayList<Integer> buffer = new ArrayList<Integer>(capacity);
		
		System.out.println("Enter number of pages");
		n=sc.nextInt();
		ArrayList<Integer> pages = new ArrayList<Integer>(n);
		
		System.out.println("Enter pages numbers");
		for(int i=0;i<n;i++) {
			System.out.print("Page "+(i+1)+": ");
			pages.add(sc.nextInt());
		}
		
		for(int i=0;i<n;i++) {
			
			if(!buffer.contains(pages.get(i))) //if buffer doesn't contain the page
			{								   //Note: else part isn't written because if buffer contains the page,do not change anything
				if(buffer.size()==capacity)    //if buffer is full
				{
					Boolean flag=false;
					for(int j=0;j<capacity;j++) 
					{
						for(int k=i+1;k<n;k++) 
						{
							if(buffer.get(j)==pages.get(k)) //if any page in buffer is present in future pages
							{
								flag=true;
								break;
							}
						}
						if(flag==false) //if page in buffer isn't present in future pages, insert new page at 
						{				//jth location in buffer
							buffer.set(j,pages.get(i));	//insertion
							break;
						}
					}
					
					if(flag==true) {
						
						ArrayList<Integer> temp = new ArrayList<Integer>(capacity);
						for(int c : buffer) //copy all contents of buffer into temp
						{
							temp.add(c);
						}
						
						//doubt
						for(int j=i+1;j<n;j++) {
							if(temp.contains(pages.get(j)) && temp.size()>1) 
							{
								temp.remove(pages.get(j)); //remove the pages which occur in future until you are left with min 1 page
							}
						}
						
						for(int j=0;j<capacity;j++) 
						{
							if(temp.get(0)==buffer.get(j)) 
							{
								buffer.set(j, pages.get(i));	//insert new page in place of the page which doesn't occur or comes late in future pages
								temp.remove(0);
								break;
							}
						}
						//doubt
					}
					
					page_fault++;
				}
				else						   //if buffer isn't full
				{
					buffer.add(count,pages.get(i));
					page_fault++;
					count++;
				}
			}
			
			System.out.println("Buffer contents");
			for(int p:buffer) {
				System.out.println(p+"\t");
			}
		}
		
		System.out.println("Page faults = "+page_fault);
		double ratio=(double)page_fault/n;
		System.out.println("Page fault ratio = "+ratio);
	}

}

/*
3
12
2 3 2 1 5 2 4 5 3 2 5 2
*/
