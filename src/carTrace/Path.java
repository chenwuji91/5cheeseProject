package carTrace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DbPool;

/**
 * 
 * @author chenwuji
 * @description 本部分功能主要是传入起点和终点的路口信息，根据DFS求得相关的最短路径
 */
public class Path {


	private TraceDAO trace;
	private int begin;
	private int end;
	private ArrayList<ArrayList<Integer>> allPath;
	public Path(TraceDAO trace) {
		// TODO Auto-generated constructor stub
		this.trace=trace;
		begin=trace.getNextPoint1();
		end=trace.getLastPoint2();
		allPath=new ArrayList<ArrayList<Integer>>();
	}
	
	public ArrayList<Integer> search()
	{
		ArrayList<Integer> onePath=new ArrayList<Integer>();
		System.out.println("前起点"+this.begin+",终点："+this.end);
		DFS(onePath,begin,0);
		System.out.println(allPath);
		return allPath.get(0);//实际要搜索实际权值最小的路径
	}
	
	
	private void DFS(ArrayList<Integer> onepath,int now,int deep)
	{
		
		//System.out.println("当前深度"+deep);
		if(now==this.end)//表示找到路径，返回上一层
		{
			
			onepath.add(now);//当前节点加入到最后单个路径结果集中
			//System.out.println("找到了"+onepath);
			//System.out.println("all path"+allPath);
			ArrayList<Integer> temp=(ArrayList<Integer>) onepath.clone();
			allPath.add(temp);//成环，把已经成环的放到里面去	
			onepath.remove(onepath.size()-1);
			onepath.remove(onepath.size()-1);
			
			return;
		}
		if(deep>5)//遍历深度达到10 返回调用处
		{
			//System.out.println("啊哦，没找到："+onepath);
			//onepath=null;
			onepath.remove(onepath.size()-1);
			//deep--;
			return;
		}
		//onepath.add(now);
		deep=deep+1;
		//考虑此处将元素加入集合
		ArrayList<Integer> neighbour=findRoad(now);
		
		for(int i=0;i<neighbour.size();i++)
		{
			onepath.add(now);
			//System.out.println("正在找："+onepath);
			//考虑深度加1放在传参之前执行
			
			DFS(onepath,neighbour.get(i),deep);
		}
		//System.out.println("all path"+allPath);
		try{
			onepath.remove(onepath.size()-1);
			deep--;
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e)
		{
			System.out.println("边界问题");
			return;
		}
		
	}

	private ArrayList<Integer> findRoad(int min)
	{
		int n1,n2,n3,n4;
		n1=n2=n3=n4=-2;
		
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM suzhou.road2 where id1="+min);	
			if(rs.next())
			{
				n1=rs.getInt(2);
				n2=rs.getInt(3);
				n3=rs.getInt(4);
				n4=rs.getInt(5);	
			}
			conn.close();
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Integer> neighbour=new ArrayList<Integer>();
		if(n1!=0)neighbour.add(n1);
		if(n2!=0)neighbour.add(n2);
		if(n3!=0)neighbour.add(n3);
		if(n4!=0)neighbour.add(n4);
		return neighbour;
	}
	

}
