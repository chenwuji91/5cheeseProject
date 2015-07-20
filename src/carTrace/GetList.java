package carTrace;

import java.io.File;
import java.util.ArrayList;


public class GetList {
	public ArrayList<String> getList(String folder)
	{	
		ArrayList<String> list=new ArrayList<String>();
		File file=new File(folder);
		File[] filename=file.listFiles();
		for(int i=0;i<filename.length;i++)
		{
			if(filename[i].isFile())
			{
				//System.out.println(filename[i]);
				list.add(filename[i].toString());
			}	
			else if(filename[i].isDirectory())
			{
				this.getList(filename[i].toString());
			}
		}
		return list;
	}

}
