import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class sock implements Runnable {
	
	
	String name; 
	
	
	

	int count=0,i;
	int[] arr = new int[100];
	
	
	  Lock[] sock_locks = new ReentrantLock[100];
	
	  
	  int[] pair_of_sock = new int[5];
		
		 Lock[] color_locks = new ReentrantLock[5];

	  
	public sock(String n, int[] a,Lock[] sock_lock, int[] pair_of_soc, Lock[] color_lock)
	{
		//initializing threads

			name = n;
		
			for(i=0;i<100;i++)
			{
				
				arr[i] = a[i];
				
				sock_locks[i] =sock_lock[i];
			
			}
	
			
			
		
			for( i=0;i<4;i++)
			{
				pair_of_sock[i] = pair_of_soc[i];
				color_locks[i] = color_lock[i];
			}
	}
	
	
	
	
	
	
	 public void run()
	  {
		 
			while(true)
		       {
		    	 
		          
		    		int i1 = 0, i2=0, i3=0, i4=0,k,t=0,prev=0;
		        	  Random random = new Random();
		      		//random integer index for each threads
		      
		        	 i =  random.ints(0,100).findFirst().getAsInt(); 
		        	 
		        	 
		        	 //putting lock for a position in array
		        	 
		        	  boolean ans = sock_locks[i].tryLock(); 
		        	  
		        	  while(!ans)
		        	  {
		        		  i =  random.ints(0,100).findFirst().getAsInt(); 
		        		  ans = sock_locks[i].tryLock(); 
		        		  
		        	  }
		        	  
		        	  try 
		        	  {
		        		
		                  System.out.println("task name - outer lock acquired at " + " Doing outer work" +i); 
		                 Thread.sleep(100); 
		          
				      		 k = arr[i];
				      		
				      		 
				      		 //getting lock for matcher

				      		 boolean ans2 = color_locks[k-1].tryLock();
				      		  //color_locks[k-1].lock(); 
				      		 System.out.println("task name - inner lock acquired at " + " inner outer work" + k + "  " + ans2); 
				      		 
				      		 
				        	  while(!ans2)
				        	  {
				        		  Thread.sleep(1500);
				        		  ans2= sock_locks[i].tryLock(); 
				        		  
				        	  }
				      		  
				      		 try
				      		  {
				      			// System.out.println("task name - inner lock acquired at "
				      	                 
		                           //  + " inner outer work" + k ); 
				      		 
		      		  
						      		 if(pair_of_sock[k-1] != 0)
						      		 {
						      			 count++;
						      			 pair_of_sock[k-1] = 0;
						      		 }
						      		 else{
						      			 pair_of_sock[k-1] = 1;
						      		 }
						      		
						      		 Thread.sleep(500);
						      		//  color_locks[k-1].unlock(); 
						         
				      		 	System.out.println("Count is:" + count); 
				      		  }
				      		catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	  finally
				              { 
				                //Inner lock release 
				                System.out.println("task name - "  + 
				                           " releasing inner lock"+k); 
				      
				                //sock_locks[i].unlock(); 
				              } 
				      		
				      		  
		        	  } catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	  finally
		              { 
		                //Inner lock release 
		                System.out.println("task name - "  + 
		                           " releasing outer lock"+i); 
		      
		                //sock_locks[i].unlock(); 
		              } 
		       
		    
		      // System.out.println("Main thread run is over" );
		      
		       }
		   
		   
	    }

}






class RunnableExample
{

    private static int count;
    
    public static void main(String args[]) throws InterruptedException
    {
  
    	
    	count = 0;
		int i;
		int[] arr = new int[100];
    	
    	//defining locks
    	  Lock[] sock_locks = new ReentrantLock[100];
    	
    	  
    	  int[] pair_of_sock = new int[4];
    		
    		 Lock[] color_locks = new ReentrantLock[4];
    		 
    		 

    		 //defining random array for socks
 			for(i=0;i<100;i++)
 			{
 				Random random = new Random();
 				arr[i] =  random.ints(1,5).findFirst().getAsInt(); 
 				
 				sock_locks[i] = new ReentrantLock();
 				
 				  System.out.print(" " + arr[i]); 
 				
 			}
 			System.out.println("") ;
 			
 			
 			// initializing the arrays
 			for( i=0;i<4;i++)
 			{
 				pair_of_sock[i] = 0;
 				color_locks[i] = new ReentrantLock();
 			}

    	    
 			//calling threads concurrently ---- 4 robotic arms

        	Thread mythread1 = new Thread(new sock("arm1: ", arr, sock_locks, pair_of_sock, color_locks));
        	Thread mythread2 = new Thread(new sock("arm2: ", arr, sock_locks, pair_of_sock, color_locks));
        	Thread mythread3 = new Thread(new sock("arm3: ", arr, sock_locks, pair_of_sock, color_locks));
        	Thread mythread4 = new Thread(new sock("arm4: ", arr, sock_locks, pair_of_sock, color_locks));
       
        	mythread1.start();
       	  mythread2.start();
       	   mythread3.start();
       	   mythread4.start();
       	   
    
    }
	

	
}



