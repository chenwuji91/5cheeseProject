package carTrace;

import java.io.*;
import java.util.ArrayList;

public class ReadFile {
		
        public ArrayList<Node> getData(ArrayList<Node> node,String path) {//"D:\\suzhou\\20120301\\1033.txt"
                File file=new File(path);  //�ҵ��ļ���C����
                //String content=
                String text=readToString(file);
                String line[]=text.split("\n");
//                System.out.println(line.length);
                for(int i=0;i<line.length;i=i+2)
                {
                	//System.out.println("0"+Integer.valueOf(line[i].split(",")[0]));
                	//System.out.println(Integer.valueOf(line[i].split(",")[5]));
                	//System.out.println(line[i].split(",")[7]);
                	try
                	{
                		node.add(new Node(Integer.valueOf(line[i].split(",")[0]),Double.valueOf(line[i].split(",")[1]),Double.valueOf(line[i].split(",")[2]),
                			Integer.valueOf(line[i].split(",")[3]),Integer.valueOf(line[i].split(",")[4]),line[i].split(",")[5],
                			Integer.valueOf(line[i].split(",")[6]),
                			Integer.valueOf(line[i].split(",")[7].split("\r")[0]),
                			Double.valueOf(line[i+1])));
                	}
                	catch(java.lang.ArrayIndexOutOfBoundsException e)
                	{
                		e.printStackTrace();
                		continue;
                	}
                }
                System.out.println(node.size());
                return node;
        }
        
        public static String readToString(File file) {
                Long filelength = file.length();     //��ȡ�ļ�����
                byte[] filecontent = new byte[filelength.intValue()];
                try {
                    FileInputStream in = new FileInputStream(file);
                    in.read(filecontent);
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new String(filecontent);//�����ļ�����,Ĭ�ϱ���
        }


}

