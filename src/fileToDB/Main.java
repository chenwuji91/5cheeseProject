package fileToDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



public class Main implements Runnable{
	private static List<String> filepath=Collections.synchronizedList(new ArrayList<String>());
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbPool.init();
		Main m=new Main();
		m.getList("D:\\suzhou");
		new Thread(m,"Thread 1:").start();
		
			
	}
	
	public void run(){
		Iterator<String> it=filepath.iterator();
		while(it.hasNext())
		{
			try {
				InputStreamReader reader=new InputStreamReader(new FileInputStream(it.next()),"UTF-8");
				BufferedReader br=new BufferedReader(reader);
				String LineText=null;
				while((LineText=br.readLine())!=null)
				{
					String Latitude=null;
	            	String Longitude=null;
	            	String Altitude=null;
	            	String TimePass=null;
	            	String Date=null;
	            	String Time=null;   
	            	Latitude=lineTxt.split(",")[0];
	            	Longitude=lineTxt.split(",")[1];
	            	Altitude=lineTxt.split(",")[3];
		         	TimePass=lineTxt.split(",")[4];
		          	Date=lineTxt.split(",")[5];
		         	Time=lineTxt.split(",")[6];
		             
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	@author(author = "chenwuji", function = "显示所有文件列表")
	private void getList(String path)
	{	
		File file=new File(path);
		File[] filename=file.listFiles();
		for(int i=0;i<filename.length;i++)
		{
			if(filename[i].isFile())
			{
				System.out.println(filename[i]);
				Main.filepath.add(filename[i].toString());
			}	
			else if(filename[i].isDirectory())
			{
				this.getList(filename[i].toString());
			}
		}	
	}

}
