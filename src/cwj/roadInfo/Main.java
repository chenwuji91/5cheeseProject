package cwj.roadInfo;

/**
 * 
 * @author chenwuji
 * 程序的主要目的是为了计算邻接矩阵  从而求出相邻节点之间具体的距离
 * 使用MVC标准的模式来实现
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DbPool;
import defineRoad.Calculate;
public class Main {
	private static ArrayList<Data> dataStore=new ArrayList<Data>();
	private static ArrayList<Result> result=new ArrayList<Result>();
//	Main()
//	{
//		dataStore=new ArrayList<Data>();
//		result=new ArrayList<Result>();
//	}
	
	public static void main(String args[])
	{
		DbPool.init();	
		DB database=new DB();
		System.out.println("数据集起始长度："+dataStore.size()+"，Main Hash值是"+dataStore.hashCode());
		dataStore=database.getMap(dataStore);//获得所有的数据库数据集
		System.out.println("原始矩阵的个数是:"+dataStore.size());
		for(int i=0;i<dataStore.size();i++)//依次返回存在的结果集，保存到结果集中
		{
			int one=dataStore.get(i).getOne();
			int two=dataStore.get(i).getTwo();
			int three=dataStore.get(i).getThree();
			int four=dataStore.get(i).getFour();
			int five=dataStore.get(i).getFive();
			if(one!=0)
			{
				if(two!=0)
					result.add(new Result(one,two));
				if(three!=0)
					result.add(new Result(one,three));
				if(four!=0)
					result.add(new Result(one,four));
				if(five!=0)
					result.add(new Result(one,five));
			}
		}
		dataStore=null;
		System.out.println("结果集，即有效路段的个数是:"+result.size());
		for(int i=0;i<result.size();i++)//查看结果集中的元素，并查询其他元素
		{
			int id1=result.get(i).getId1();
			int id2=result.get(i).getId2();
			Point point1=database.queryInfo(new Point(id1));
			Point point2=database.queryInfo(new Point(id2));
			result.set(i, result.get(i).setDetails(point1.getLati(), point2.getLati(), point1.getLongi(), 
					point2.getLongi(),(float) Calculate.getDistance(point1.getLati(),point1.getLongi(), point2.getLati(), point2.getLongi())));	
		}
		//结果集插入回数据库
		System.out.println("计算全部完成，进入结果保存阶段");
		//此处添加生产者消费者模式   异步并发执行
		for(int i=0;i<result.size();i++)
		{
			database.insertData(result.get(i));
		}
		
	}

}
class Data{
	private int one,two,three,four,five;
	Data(int one,int two,int three,int four,int five){
		this.one=one;
		this.two=two;
		this.three=three;
		this.four=four;
		this.five=five;
		}
		public int getOne()
		{
			return this.one;
		}
		public int getTwo()
		{
			return this.two;
		}
		public int getThree()
		{
			return this.three;
		}
		public int getFour()
		{
			return this.four;
		}
		public int getFive()
		{
			return this.five;
		}
	
}
class Result{
	private int id1,id2;
	private float lati1,lati2,longi1,longi2,dis;
	Result(int id1,int id2){
		this.id1=id1;
		this.id2=id2;
	}
	public int getId1()
	{
		return this.id1;
	}
	public int getId2()
	{
		return this.id2;
	}
	public Result setDetails(float lati1,float lati2,float longi1,float longi2,float dis)
	{
		this.lati1=lati1;
		this.longi1=longi1;
		this.lati2=lati2;
		this.longi2=longi2;
		this.dis=dis;
		return this;
	}
	public float getLati1()
	{
		return lati1;
	}
	public float getLati2()
	{
		return lati2;
	}
	public float getLongi1()
	{
		return longi1;
	}
	public float getLongi2()
	{
		return longi2;
	}
	public float getDis()
	{
		return dis;
	}
}

class DB{
	public ArrayList<Data> getMap(ArrayList<Data> dataStore){
		Connection conn;
		try {
			conn = DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from suzhou.road2");
			while(rs.next())
			{
				int one=rs.getInt(1);
				int two=rs.getInt(2);
				int three=rs.getInt(3);
				int four=rs.getInt(4);
				int five=rs.getInt(5);
				Data data=new Data(one, two, three, four, five);
				System.out.println("数据集起始长度："+dataStore.size()+"，Hash值是"+dataStore.hashCode());
				dataStore.add(data);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataStore;	
	}
	public Point queryInfo(Point point)
	{
		Connection conn;
		try {
			conn = DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select id1,latitude,longitude from suzhou.road1 where id1="+point.getId());
			while(rs.next())
			{
				int id=rs.getInt(1);
				point.setPoint(rs.getFloat(2), rs.getFloat(3));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return point;
	}
	public void insertData(Result result)
	{
		Connection conn;
		try {
			conn = DbPool.ds2.getConnection();
			PreparedStatement prst=conn.prepareStatement("insert into road3(id1,id2,lati1,longi1,lati2,longi2,dis) values(?,?,?,?,?,?,?)");
			prst.setInt(1, result.getId1());
			prst.setInt(2, result.getId2());
			prst.setFloat(3, result.getLati1());
			prst.setFloat(4, result.getLongi1());
			prst.setFloat(5, result.getLati2());
			prst.setFloat(6, result.getLongi2());
			prst.setFloat(7, result.getDis());
			
			prst.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return;
	}
}
class Point{
	private int id;
	private float lati;
	private float longi;
	Point(int id){
		this.id=id;
	}
	public void setPoint(float lati,float longi)
	{
		this.lati=lati;
		this.longi=longi;
	}
	public int getId(){
		return id;
	}
	public float getLati(){
		return lati;
	}
	public float getLongi(){
		return longi;
	}
}
