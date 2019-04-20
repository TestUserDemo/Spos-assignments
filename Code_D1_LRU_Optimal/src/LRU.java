import java.util.ArrayList;
import java.util.Scanner;

public class LRU {

	public static void main(String[] args) {
		
		int capacity, n;
		int page_fault=0,count=0;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter capacity of buffer");
		capacity = sc.nextInt();
		ArrayList<Integer> buffer = new ArrayList<Integer>(capacity);
		
		System.out.println("Enter number of pages");
		n = sc.nextInt();
		ArrayList<Integer> pages = new ArrayList<Integer>(n);
		
		System.out.println("Enter page numbers");
		for(int i=0;i<n;i++) {
			System.out.print("Page "+(i+1)+": ");
			pages.add(sc.nextInt());
		}
		System.out.println();
		
		for(int i=0;i<n;i++) {
			 
			if(!buffer.contains(pages.get(i))) //if page isn't present in the buffer
			{
				if(buffer.size()==capacity) {
					
					buffer.remove(0);
					buffer.add(pages.get(i));
					page_fault++;
				}
				
				else {
					buffer.add(count, pages.get(i));
					page_fault++;
					count++;
				}
				
			}
			
			else //if page is present in the buffer 
			{
				buffer.remove(pages.get(i));
				buffer.add(pages.get(i));
			}
			
			System.out.println("Buffer contents");
			for(int p:buffer) {
				System.out.println(p);
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
