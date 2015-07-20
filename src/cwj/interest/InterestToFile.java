package cwj.interest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InterestToFile {
	public void toFile(String outpath,String data)
	{
		
		FileWriter writer;
		try {
			writer = new FileWriter(outpath,true);
			BufferedWriter buffer = new BufferedWriter(writer); 
			StringBuilder sb = new StringBuilder(); 	 
			sb.append(data+"\r\n"); //在这里追加一次的记录 
			buffer.write(sb.toString()); 		
			buffer.flush(); 
			buffer.close(); 
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		
		} 
	}


