import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class test implements Runnable{
	private static List<String> sss=Collections.synchronizedList(new ArrayList<String>());
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for(int i=0;i<10000;i++)
		sss.add(String.valueOf(i));
		test t=new test();
		new Thread(t,"ss1").start(); 
		new Thread(t,"ss2").start(); 
		new Thread(t,"ss3").start(); 
		new Thread(t,"ss4").start(); 
		new Thread(t,"ss5").start(); 
		new Thread(t,"ss6").start(); 
		new Thread(t,"ss7").start(); 
		new Thread(t,"ss8").start(); 
		new Thread(t,"ss9").start(); 
		new Thread(t,"s0s").start(); 
		
	}
	  public void run()
	  {
		  
		  Iterator it=sss.iterator();
		  while(it.hasNext())
		  System.out.println(it.next());
		  
	  }

}
