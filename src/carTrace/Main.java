package carTrace;

import java.util.ArrayList;

public class Main {

	public static ArrayList<Node> node=new ArrayList<Node>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFile data=new ReadFile();
		node=data.getData(node);//一个节点，保存了当前目录文件下的所有的节点信息
		
		
		

	}

}
