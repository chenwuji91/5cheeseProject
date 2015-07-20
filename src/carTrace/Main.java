package carTrace;

import java.util.ArrayList;

import database.DbPool;

public class Main {

	public static ArrayList<Node> node=new ArrayList<Node>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetList filelist=new GetList();
		ArrayList<String> list=filelist.getList("D:\\suzhou\\20120301");
		DbPool.init();
		for(int kk=1;kk<list.size();kk++)
		{
		try{
		ReadFile data=new ReadFile();
		node=data.getData(node,list.get(kk));//一个节点，保存了当前目录文件下的所有的节点信息
		for(int i=0;i<node.size();i++)//现在开始逐行选取起点终点进行比较
		{
			Node n1,n2;
			try{
//				n1=node.get(i);
//				n2=node.get(i+1);
				n1=node.get(i);
				n2=node.get(i+1);
			}
			catch(java.lang.IndexOutOfBoundsException e)
			{
				continue;
			}
			
			Position position=new Position();//获取现在需要的所有信息，保存到traceDao类中
			position.definePosition(n1);//定位获取第一个点的路段，行驶方向，返回值traceDao（null也可以）	
			position.definePosition(n2);
//			if(position.definePosition(n2))//定位获得第二个点的路段，行驶方向，返回值traceDao（null）
//				continue;//主要处理找不到元素的情况 直接忽略
			//完成了路在具体哪个位置的判断
			TraceDAO traceDao=new TraceDAO(n1,n2);
			Path path=new Path(traceDao);//
			ArrayList<Integer> shortest=path.search();//返回最短路径
			System.out.println("最短路径是"+shortest);
			//第一个路段的中间为起点，基于行驶方向找到下一个路口；基于下一个路口，和终点的上一个路口，找出中间的路段,返回值ArrayList容器，保存中间经过的所有路段（带权图车辆行驶路径的还原）
			//根据行驶的路段，以及相应路段的长度，分析计算中间时间间隔的每个位置时刻。传入值，中间路段的Arraylist、traceDao对象，返回值ArrayList保存最后返回的结果集
			//结果集保存数据库
			ResultToFile result=new ResultToFile(shortest,n1,n2);
			result.outPut(list.get(kk));
		}
	
		}
			catch(Exception e2)
			{
				e2.printStackTrace();
				continue;
			}
		}
		
		
		}
		
		
		

	}


