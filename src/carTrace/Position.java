package carTrace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import database.DbPool;
import defineRoad.Calculate;


public class Position {

	private Node node;
	/**
	 * 
	 * @param traceDao
	 * @description 本方法主要传入两个位置信息，处理完成后保存所在的路段信息
	 * @description 本类的控制器
	 */
	public boolean definePosition(Node n)
	{
		TreeMap<Float,Integer> val=getNearby(n);//得到距离当前最近的点
		int min,mid;
		try{
			min=val.get(val.firstKey());
//			val.pollFirstEntry();
//			mid=val.get(val.firstKey());
		}
		catch(java.util.NoSuchElementException e)//处理没有找到的情况
		{
			
			TreeMap<Float,Integer> val1=getNearby1(n);//得到距离当前最近的点
			try{
				System.out.println("没找到邻居");
				System.out.println(n.getLati()+" "+n.getLongi());
				min=val1.get(val1.firstKey());
			}
			catch(java.util.NoSuchElementException e2)
			{
				return false;
			}
			val1.pollFirstEntry();
			//mid=val1.get(val1.firstKey());
			//return;
		}
		
		ArrayList<Integer> neighbour=findRoad(min, n);//得到每个路口周围的四个邻节点,已经进行去null处理，返回的是真实的邻居数
		//System.out.println("最小点："+min+"，中间点："+mid+",相邻点："+neighbour);
		TreeMap<Double,Integer> neighbourK=calculateAngle(neighbour,min, n);//返回计算的几个夹角与n的差值，并按由小到大排序， pullfirst找出最小的
		int min2=neighbourK.get(neighbourK.firstKey());
		//System.out.println("几个点的夹角斜率"+neighbourK+",最小点："+neighbourK.get(neighbourK.firstKey()));
		boolean position=judgePosi(n.getAngle(), min, min2);
		if(position)
		{
			n.setRoadIndex(selectRoad(min, min2));
			n.setNextPoint(min2);
			n.setLastPoint(min);
		}
		else
		{
			n.setRoadIndex(selectRoad(min2, min));
			n.setNextPoint(min);
			n.setLastPoint(min2);
		}
		return true;	
		//根据夹角来选出夹角最近的两个路
//		TreeMap<Double,Integer> angle=
	}
	/**
	 * @description 输入节点的相关信息，返回距离该节点最近的路口
	 * @param n
	 * @return min
	 */
	private TreeMap<Float,Integer> getNearby(Node n){
		//System.out.println(n.getCarID());
		
		TreeMap<Float,Integer> val=new TreeMap<Float,Integer>();
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM suzhou.road1 where"
					+ " road1.latitude>"+(n.getLati()-0.005)+" and "
					+ "road1.latitude<"+(n.getLati()+0.005)+" and "
					+ "road1.longitude>"+(n.getLongi()-0.005)+" and "
					+ "road1.longitude<"+(n.getLongi()+0.005)+";");
			float temp1=99999,temp2=99999;
			
			while(rs.next())
			{
				int resultId=rs.getInt(1);
				float latitude=rs.getFloat(3);
				float longitude=rs.getFloat(4);
				//System.out.println("ID"+resultId+" "+latitude+" "+longitude);
				temp1=(float) Calculate.getDistance(latitude, longitude, n.getLati(),n.getLongi());	
				//System.out.println(temp1);
				val.put(temp1,resultId);
//				if(temp1<temp2)
//					min=resultId;
			}
//			System.out.println(val);
//			System.out.println("pull first"+val.pollFirst());
//			System.out.println("pull last"+val.pollLast());
//			System.out.println(val);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return val;
	}
	/**
	 * 
	 * @param min
	 * @param n
	 * @return 当前邻节点的集合
	 */
	private ArrayList<Integer> findRoad(int min,Node n)
	{
		int n1,n2,n3,n4;
		n1=n2=n3=n4=-1;
		
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
	
	/**
	 * 
	 * @param neighbour
	 * @return 邻节点与当前点的斜率
	 */
	private TreeMap<Double,Integer> calculateAngle(ArrayList<Integer> neighbour,int min,Node n)
	{		
		TreeMap<Double,Integer> angle=new TreeMap<Double,Integer>();
		CalculateAngle cal=new CalculateAngle();
		for(int i=0;i<neighbour.size();i++)
		{
			int temp=90-n.getAngle();//角度是n.getAngle()
			double value1=Math.abs(cal.calculateAngle(neighbour.get(i),min));
			double value2=Math.abs(Math.tan((temp)*(Math.PI/180)));
			//System.out.println("value2: "+value2);
			//System.out.println("value1:"+value1);
			double value3=Math.abs(Math.atan(value1));
			double value4=Math.abs(Math.atan(value2));
			angle.put(Math.abs(value3-value4),neighbour.get(i));//计算出所有角度的斜率差值
		}
		return angle;
	}
	private boolean judgePosi(int angle,int n1,int n2)
	{
		CalculateAngle cal=new CalculateAngle();
		float temp1=(float) cal.calculatePosi(n1, n2);//返回正值表示是向正方向行驶  即往偏离中心点n1的方向行驶
		float temp2=angle<180?1:-1;
		if(temp1*temp2>0)
			return true;
		else 
			return false;
	}
	
	private int selectRoad(int id1,int id2)
	{
		int index = 0;
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT index1 FROM suzhou.road3 where id1="+id1+" and id2="+id2);	
			
			if(rs.next())
			{
				index=rs.getInt(1);	
			}
			conn.close();
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index;
		
	}
	
	
	/**
	 * 此函数做异常处理使用，用来处理没有找到的情况，放大范围
	 * @param n
	 * @return
	 */
	private TreeMap<Float,Integer> getNearby1(Node n){
		//System.out.println(n.getCarID());
		
		TreeMap<Float,Integer> val=new TreeMap<Float,Integer>();
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM suzhou.road1 where"
					+ " road1.latitude>"+(n.getLati()-0.05)+" and "
					+ "road1.latitude<"+(n.getLati()+0.05)+" and "
					+ "road1.longitude>"+(n.getLongi()-0.05)+" and "
					+ "road1.longitude<"+(n.getLongi()+0.05)+";");
			float temp1=99999,temp2=99999;
			
			while(rs.next())
			{
				int resultId=rs.getInt(1);
				float latitude=rs.getFloat(3);
				float longitude=rs.getFloat(4);
				//System.out.println("ID"+resultId+" "+latitude+" "+longitude);
				temp1=(float) Calculate.getDistance(latitude, longitude, n.getLati(),n.getLongi());	
				//System.out.println(temp1);
				val.put(temp1,resultId);
//				if(temp1<temp2)
//					min=resultId;
			}
//			System.out.println(val);
//			System.out.println("pull first"+val.pollFirst());
//			System.out.println("pull last"+val.pollLast());
//			System.out.println(val);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return val;
	}
	

}
