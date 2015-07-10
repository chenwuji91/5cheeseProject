package cwj.roadInfo;

/**
 * 
 * @author chenwuji
 * @E_mail 53996386@qq.com
 * @description �������ҪĿ����Ϊ�˼����ڽӾ���  �Ӷ�������ڽڵ�֮�����ľ���
 * ʹ��MVC��׼��ģʽ��ʵ��
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
	Main()
	{
		dataStore=new ArrayList<Data>();
		result=new ArrayList<Result>();
	}
	
	public static void main(String args[])
	{
		DbPool.init();	
		DB database=new DB();
		System.out.println("���ݼ���ʼ���ȣ�"+dataStore.size()+"��Main Hashֵ��"+dataStore.hashCode());
		dataStore=database.getMap(dataStore);//������е����ݿ����ݼ�
		System.out.println("ԭʼ����ĸ�����:"+dataStore.size());
		for(int i=0;i<dataStore.size();i++)//���η��ش��ڵĽ���������浽�������
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
		System.out.println("�����������Ч·�εĸ�����:"+result.size());
		for(int i=0;i<result.size();i++)//�鿴������е�Ԫ�أ�����ѯ����Ԫ��
		{
			int id1=result.get(i).getId1();
			int id2=result.get(i).getId2();
			Point point1=database.queryInfo(new Point(id1));
			Point point2=database.queryInfo(new Point(id2));
			result.set(i, result.get(i).setDetails(point1.getLati(), point2.getLati(), point1.getLongi(), 
					point2.getLongi(),(double) Calculate.getDistance(point1.getLati(),point1.getLongi(), point2.getLati(), point2.getLongi())));	
		}
		//�������������ݿ�
		System.out.println("����ȫ����ɣ�����������׶�");
		//�˴����������������ģʽ   �첽����ִ��
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
	private double lati1,lati2,longi1,longi2,dis;
	Result(int id1,int id2){
		this.id1=id1;
		this.id2=id2;
	}
	
	public Result(int id1, int id2, double lati1, double lati2, double longi1,
			double longi2, double dis) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.lati1 = lati1;
		this.lati2 = lati2;
		this.longi1 = longi1;
		this.longi2 = longi2;
		this.dis = dis;
	}

	public int getId1()
	{
		return this.id1;
	}
	/**
	 * @param id1 the id1 to set
	 */
	public void setId1(int id1) {
		this.id1 = id1;
	}

	/**
	 * @param id2 the id2 to set
	 */
	public void setId2(int id2) {
		this.id2 = id2;
	}

	/**
	 * @param lati1 the lati1 to set
	 */
	public void setLati1(double lati1) {
		this.lati1 = lati1;
	}

	/**
	 * @param lati2 the lati2 to set
	 */
	public void setLati2(double lati2) {
		this.lati2 = lati2;
	}

	/**
	 * @param longi1 the longi1 to set
	 */
	public void setLongi1(double longi1) {
		this.longi1 = longi1;
	}

	/**
	 * @param longi2 the longi2 to set
	 */
	public void setLongi2(double longi2) {
		this.longi2 = longi2;
	}

	/**
	 * @param dis the dis to set
	 */
	public void setDis(double dis) {
		this.dis = dis;
	}

	public int getId2()
	{
		return this.id2;
	}
	public Result setDetails(double lati1,double lati2,double longi1,double longi2,double dis)
	{
		this.lati1=lati1;
		this.longi1=longi1;
		this.lati2=lati2;
		this.longi2=longi2;
		this.dis=dis;
		return this;
	}
	public double getLati1()
	{
		return lati1;
	}
	public double getLati2()
	{
		return lati2;
	}
	public double getLongi1()
	{
		return longi1;
	}
	public double getLongi2()
	{
		return longi2;
	}
	public double getDis()
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
				System.out.println("���ݼ���ʼ���ȣ�"+dataStore.size()+"��Hashֵ��"+dataStore.hashCode());
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
			int clean0=point.getId();//����Ĳ����൱�ڴ򲹶�
			if(clean0==-1)
				clean0=0;
			ResultSet rs=stmt.executeQuery("select id1,latitude,longitude from suzhou.road1 where id1="+clean0);
			while(rs.next())
			{
				int id=rs.getInt(1);
				point.setPoint(rs.getDouble(2), rs.getDouble(3));
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
			prst.setDouble(3, result.getLati1());
			prst.setDouble(4, result.getLongi1());
			prst.setDouble(5, result.getLati2());
			prst.setDouble(6, result.getLongi2());
			prst.setDouble(7, result.getDis());
			
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
	private double lati;
	private double longi;
	Point(int id){
		this.id=id;
	}
	public void setPoint(double lati,double longi)
	{
		this.lati=lati;
		this.longi=longi;
	}
	public int getId(){
		return id;
	}
	public double getLati(){
		return lati;
	}
	public double getLongi(){
		return longi;
	}
}
