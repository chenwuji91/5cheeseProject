package fileToDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import database.DbPool;



public class Main implements Runnable{
	private static List<String> filepath=Collections.synchronizedList(new ArrayList<String>());
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbPool.init();
		Main m=new Main();
		m.getList("D:\\suzhou\\20120317");
		new Thread(m,"Thread 1:").start();
		new Thread(m,"Thread 2:").start();
		new Thread(m,"Thread 3:").start();
		new Thread(m,"Thread 4:").start();
		new Thread(m,"Thread 5:").start();
		
			
	}
	
	public void run(){
		Iterator<String> it=filepath.iterator();
		while(it.hasNext())
		{
			try {
				InputStreamReader reader=new InputStreamReader(new FileInputStream(it.next()),"UTF-8");
				BufferedReader br=new BufferedReader(reader);
				String lineText=null;
				while((lineText=br.readLine())!=null)
				{
					Connection conn=DbPool.ds2.getConnection();
					try{
					
					PreparedStatement prst=conn.prepareStatement("insert into car(carId,latitude,longitude,speed,angle,"
							+ "dateTime1,occupy1,occupy2,index1) values(?,?,?,?,?,?,?,?,null)");
					prst.setInt(1, Integer.valueOf(lineText.split(",")[0]));
					prst.setDouble(2, Double.valueOf(lineText.split(",")[1]));
					prst.setDouble(3, Double.valueOf(lineText.split(",")[2]));
					prst.setInt(4, Integer.valueOf(lineText.split(",")[3]));
					prst.setInt(5, Integer.valueOf(lineText.split(",")[4]));
					prst.setTimestamp(6, Timestamp.valueOf(lineText.split(",")[5]));
					prst.setInt(7, Integer.valueOf(lineText.split(",")[6]));
					prst.setInt(8, Integer.valueOf(lineText.split(",")[7]));
					prst.executeUpdate();
					conn.close();
					System.out.println("doing");
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						conn.close();
						continue;
					}
					 catch (java.lang.NumberFormatException e) {
						conn.close();
						continue;
					 }
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
			} catch (SQLException e) {
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
