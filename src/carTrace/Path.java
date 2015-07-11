package carTrace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

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
	private PathDB db;
	private ArrayList<ArrayList<Integer>> allPath;
	public Path(TraceDAO trace) {
		// TODO Auto-generated constructor stub
		this.trace=trace;
		begin=trace.getNextPoint1();
		end=trace.getLastPoint2();
		allPath=new ArrayList<ArrayList<Integer>>();
		db=new PathDB();
	}
	/**
	 * @description 该类的主方法 控制器
	 * @return
	 */
	public ArrayList<Integer> search()
	{
		ArrayList<Integer> onePath=new ArrayList<Integer>();
		System.out.println("前起点"+this.begin+",终点："+this.end);
		DFS(onePath,begin,0);
		System.out.println(allPath);
		//在搜索到的路径里面找出最优的路径
		TreeMap <Integer,Integer> distance=new TreeMap<Integer,Integer>();
		for(int i=0;i<allPath.size();i++)
		{
			distance.put(calculate(allPath.get(i)), i);
		}
		if(distance.isEmpty())
			return null;
		return allPath.get(distance.get(distance.firstKey()));//实际要搜索实际权值最小的路径
	}
	
	
	/**
	 * 
	 * @param path
	 * @return 返回某一段路程的全部距离
	 */
	private int calculate(ArrayList<Integer> path) {
		// TODO Auto-generated method stub
		int count=0;
		for(int i=0;i<path.size()-1;i++)
		{
			count=count+db.getDis(path.get(i), path.get(i+1));
		}
		System.out.println("这段路距离是"+count);
		return count;
		
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
			try{
				onepath.remove(onepath.size()-1);
				onepath.remove(onepath.size()-1);
			}
			catch(java.lang.ArrayIndexOutOfBoundsException e)
			{
				System.out.println("边界问题");
				return;
			}
			
			return;
		}
		if(deep>3)//遍历深度达到10 返回调用处
		{
			//System.out.println("啊哦，没找到："+onepath);
			//onepath=null;
			try{
				onepath.remove(onepath.size()-1);
			}
			catch(java.lang.ArrayIndexOutOfBoundsException e)
			{
				System.out.println("边界问题");
				return;
			}
			
			//deep--;
			return;
		}
		//onepath.add(now);
		deep=deep+1;
		//考虑此处将元素加入集合
		ArrayList<Integer> neighbour=db.findRoad(now);
		
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



}
