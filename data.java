import java.io.*; 
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;




public class data{

	public static void main(String[] args)  throws InterruptedException, FileNotFoundException
	  { 
	  // We need to provide file path as the parameter: 
	  // double backquote is to avoid compiler interpret words 
	  // like \test as \t (ie. as a escape sequence) 
		  
		  //defining arrays for storing data
		  int[] roll = new int[100];
		 String[] name = new String[100];
		 String[] email = new String[100];
		 int[] marks = new int[100];
		  String[] last = new String[100];
		  int i=0,j=0,n;
	

	//accessing file
	 File file = new File("/home/cse/workspace/data/src/studInfo.txt"); 
	 
	 Scanner br = new Scanner(file); 
	 
	 	String st;

	 	//saving in the arrays
		  while (br.hasNextLine()) 
		  {  
			  st = br.nextLine();
			 System.out.println("  " + st);
			if(j%5 == 0)
			{  String word = st;
			  roll[i] = Integer.parseInt(word);
			 
			}
			  
			 if(j%5 == 1)
			 {
				 name[i] = st;
			 }
			  
			if(j%5 == 2)
			  email[i] = st;
			  
			if(j%5 == 3)
			{
				String word = st;
				marks[i] = Integer.parseInt(word);
			}
			  
			 if(j%5 == 4)
			 {
				 last[i] = st;
				 i++;
			 }
			  
			  j++;
    
		  }
		  
		  final int l=i;
		 n=0;
		 
		  
		  int c=1;
		  
		  int level=1;
		  String[] teach = new String[20];
		  int[] rl = new int[20];
		  int[] f = new int[20];
		  int[] m = new int[20];
		  String ch;
		  Scanner in = new Scanner(System.in); 
		  int flag = 1;
		  
		  while(flag == 1){
			  

			  while(c == 1)
			  {
					  System.out.println("Mentioning : \n 1. Record-level \n 2. File-level ");
						
					  level= in.nextInt();
					
					  System.out.println("Enter Teacher’s Name: ");
					  ch = in.nextLine();
					  teach[n] = in.nextLine();
					  
					  System.out.println("Enter Student Roll number: ");
					  
					  rl[n] = in.nextInt();
					  
					  System.out.println("Update Marks : \n 1. Increse \n 2. Decrease ");
					  
					  f[n] = in.nextInt();
					  if(f[n] == 1)
					  System.out.println("Marks to add : ");
					  else
					  {
						  System.out.println("Marks to subtract : ");
					  }
					  
					  m[n] = in.nextInt();
					  
					  
					  
					  System.out.println("Do you want to add another task \n1: Yes  \n2: No");
					  
					  c = in.nextInt();
					  n++;
					  
			  }
			  
			  
			  System.out.println("Choose one : \n 1. Without Synchronization \n 2. With Synchronization "); 
			  
			  int synch = in.nextInt();
			  
			  

			  //defining locks for all records
			  Lock[] record_locks = new ReentrantLock[l];
			  
			  for(i=0;i<l;i++)
				{
				
				
					record_locks[i] = new ReentrantLock();
					
					
				}
			  
			  
			  //making concurrent threads for each task
			  i=0;
	
					  Thread mythread = new Thread(new hello("person: ",level, rl[i], teach[i], f[i], m[i], synch,roll, name,email,marks,last, l , record_locks));
					  mythread.start();
					  i=1; 
			  while(i<l)
			  {
				 
				  mythread = new Thread(new hello("person: ", level,rl[i], teach[i], f[i], m[i], synch,roll, name,email,marks,last,l, record_locks));
				  mythread.start();
				  i++;
				  
			  }
			  
			  
			  
			  
			  //System.out.println("continue : \n 1. yes \n 2. no ");
				
			  flag = 2;// = in.nextInt();
			  
			  System.out.println("Write to file: \n 1. yes \n 2. no ");
			  
			
			  
			//writing back to the file
			 		  
			  File file2 = new File("Sorted_Name.txt");
		        FileWriter fr = null;
		        BufferedWriter br2 = null;
		     
		        try{
		            fr = new FileWriter(file2);
		            br2 = new BufferedWriter(fr);
		            for(i = 0; i>=0; i++){
		            
		            	
		                br2.write(roll[i]);
		                br2.write(name[i]);
		                br2.write(email[i]);
		                br2.write(marks[i]);
		                br2.write(last[i]);
		            }
		            
		        } catch (IOException e) {
		            e.printStackTrace();
		        }finally{
		            try {
		                br.close();
		                fr.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
			  
			  
			  //threads for each task
		
	  }
		  
	
		  
	  } 
	 
	
}


class hello implements Runnable {
	
	
	int[] roll; 
	  String[] name ;
	  String[] email;
	  int[] marks;
	  String[] last ;
	int level;
	String nam;
	int rl,f,m,synch,i,l;
	String teach;
	
	
	
	
	Lock[] record_locks;
	 
	
	
	  //constructor
	public hello(String n, int lev, int r, String teac,  int fh, int mh, int synchh,int[] rol, String[] na, String[] em, int[] mar, String[] te, int n1, Lock[] loc)
	{
			nam = n;
			rl = r;
			teach = teac;
			f = fh;
			m = mh;
			synch = synchh;
			l = n1;
			level = lev;
			
				roll = rol;
				name = na;
				email = em;
				marks = mar;
				last = te;
				
				record_locks = loc;
				
				
			
			
			
			
	}
	
	
	 public void run()
	  {
		 //record level
		 if(level == 1)
		 {
			 
		 
				 int i=0;
				 for(i=0;i<l;i++)
				  {
					  if(roll[i] == rl)
						  break;
				  }
				 if(i >= l)
					 return;
		
					System.out.print("INDEX" +synch);
					
				 //without synchronization

				 if(synch == 1)
				 {
					
					 
					 	
					 if(last[i] == "CC" && teach == "CC")
					  {
						//NORMAL
						
							  if(f == 1)
							  {
								  marks[i] += m;
							  }
							  else{
								  marks[i] -= m;
							  }
							 
							  
					  }
					 else if(last[i] != "CC"){
						 
						 if(f == 1)
						  {
							  marks[i] += m;
						  }
						  else{
							  marks[i] -= m;
						  }
						  
						  last[i] = teach;
						 
						 
					 }
					 
						System.out.println("roll no. :" +roll[i] +" marks" + marks[i]);
			
				 }
				 //with synchronization
				 else{
					 
					 //getting lock for the record
				 boolean ans =  record_locks[i].tryLock();
					 System.out.println(" "+ans);
					
		
		       	 while(!ans)
		       	  {
		       		 try {
						Thread.sleep(500);
						  ans= record_locks[i].tryLock(); 
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		       		
		       		 
		       	  }  
					 
		        	try{
					 System.out.println("  " + last[i] +" "+teach);
					 
							 if((last[i] == "CC" && teach == "CC") || !last[i].equals( "CC"))
							{
									//NORMAL updating
								
										  if(f == 1)
										  {
											  marks[i] += m;
										  }
										  else{
											  marks[i] -= m;
										  }
										  last[i] = teach;
										  
								  }
								
							 
							 System.out.println("roll no. :" +roll[i] +" marks" + marks[i]);
		        	  }
		        	 
				     	  finally
				              { 
				                //Inner lock release 
				                System.out.println("task name - "  + 
				                           " releasing inner lock"+i); 
				      
				                record_locks[i].unlock(); 
				              } 
				 
		        	 
		        		
		        	  
				 
				 }
		 
				 
			  }
	  
	 
	 	//for file level
		 else{
		 	//synchronizing such that only 1 task accesses the file
				 synchronized(this)
				 
				 {
					 int i=0;
					 for(i=0;i<l;i++)
					  {
						  if(roll[i] == rl)
							  break;
					  }
					 if(i >= l)
						 return;
			
						System.out.print("INDEX" +synch);
						
					 
					 if(synch == 1)
					 {
						
						 
						 	
						 if(last[i] == "CC" && teach == "CC")
						  {
							//NORMAL
							
								  if(f == 1)
								  {
									  marks[i] += m;
								  }
								  else{
									  marks[i] -= m;
								  }
								 
								  
						  }
						 else if(last[i] != "CC"){
							 
							 if(f == 1)
							  {
								  marks[i] += m;
							  }
							  else{
								  marks[i] -= m;
							  }
							  
							  last[i] = teach;
							 
							 
						 }
						 
							System.out.println("roll no. :" +roll[i] +" marks" + marks[i]);
				
					 }
					 else{
						 
						 
					 boolean ans =  record_locks[i].tryLock();
						 System.out.println(" "+ans);
						
			
			       	 while(!ans)
			       	  {
			       		 try {
							Thread.sleep(500);
							  ans= record_locks[i].tryLock(); 
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			       		
			       		 
			       	  }  
						 
			        	try{
						 System.out.println("  " + last[i] +" "+teach);
						 
								 if((last[i] == "CC" && teach == "CC") || !last[i].equals( "CC"))
								{
										//NORMAL
									
											  if(f == 1)
											  {
												  marks[i] += m;
											  }
											  else{
												  marks[i] -= m;
											  }
											  last[i] = teach;
											  
									  }
									
								 
								 System.out.println("roll no. :" +roll[i] +" marks" + marks[i]);
			        	  }
			        	 
					     	  finally
					              { 
					                //Inner lock release 
					                System.out.println("task name - "  + 
					                           " releasing inner lock"+i); 
					      
					                record_locks[i].unlock(); 
					              } 
					 
			        	 
			        		
			        	  
					 
					 }
			 
					 
					 
					 
					 
				 }
		 }
	
	  }
}



/*
input:
1
CC
174101055
1
3
1
1
TA2
174101055
2
5
1
1
TA2
174101012
2
5
2
2
*/


