package carTrace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DbPool;
/*
 * 给出两点求夹角
 */
public class CalculateAngle {
	public double calculateAngle(int n,int minPoint)
	{
		//System.out.println("Begin:"+minPoint+" "+n);
		
		float latitude1=0,latitude2=0;
		float longitude1=0,longitude2=0;
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where road1.id1="+minPoint);
			if(rs.next())
			{
				latitude1=rs.getFloat(1);
				longitude1=rs.getFloat(2);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where road1.id1="+n);
			if(rs.next())
			{
				latitude2=rs.getFloat(1);
				longitude2=rs.getFloat(2);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(latitude2==0||longitude2==0||latitude1==latitude2)
			return 9999;
		//System.out.println("la1:"+latitude1+"la2:"+latitude2+"lg1:"+longitude1+"lg2:"+longitude2+"mm:"+(latitude1-latitude2)/(longitude1-longitude2)+"__"+Math.toDegrees(Math.atan(((latitude1-latitude2)/(longitude1-longitude2)))));
		//int angle=(int) Math.toDegrees(Math.atan(((latitude1-latitude2)/(longitude1-longitude2))));
//		angle=angle>0?angle:(180+angle);
//		angle=angle<90?angle:(Math.abs((angle-180)));
		double angle=(longitude1-longitude2)/(latitude1-latitude2);
		//System.out.println("最后返回斜率是"+angle);
		return angle;
	}
	
	/*
	 * @old
	 */
	public int getPosi(int minPoint,int n)
	{
		//System.out.println("Begin:"+minPoint+" "+n);
		if(n==0)
			return 90999;
		float latitude1=0,latitude2=0;
		float longitude1=0,longitude2=0;
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where road1.id1="+minPoint);
			if(rs.next())
			{
				latitude1=rs.getFloat(1);
				longitude1=rs.getFloat(2);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where road1.id1="+n);
			if(rs.next())
			{
				latitude2=rs.getFloat(1);
				longitude2=rs.getFloat(2);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(latitude2==0||longitude2==0)
			return 9999;
		System.out.println("la1:"+latitude1+"la2:"+latitude2+"lg1:"+longitude1+"lg2:"+longitude2+"mm:"+(latitude1-latitude2)/(longitude1-longitude2)+"__"+Math.toDegrees(Math.atan(((latitude1-latitude2)/(longitude1-longitude2)))));
		int angle=(int) Math.toDegrees(Math.atan(((latitude1-latitude2)/(longitude1-longitude2))));
		angle=angle>0?angle:(180+angle);
		angle=angle<90?angle:(Math.abs((angle-180)));
		//System.out.println("最后返回的结果是"+angle);
		return angle;
	}

	
	public double calculatePosi(int n1,int n2)
	{
		//System.out.println("Begin:"+minPoint+" "+n);
		
		float latitude1=0,latitude2=0;
		//float longitude1=0,longitude2=0;
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where road1.id1="+n2);
			if(rs.next())
			{
				latitude1=rs.getFloat(1);
				//longitude1=rs.getFloat(2);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where road1.id1="+n1);
			if(rs.next())
			{
				latitude2=rs.getFloat(1);
				//longitude2=rs.getFloat(2);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//if(latitude2==0||longitude2==0||latitude1==latitude2)
		//	return 9999;
		//System.out.println("la1:"+latitude1+"la2:"+latitude2+"lg1:"+longitude1+"lg2:"+longitude2+"mm:"+(latitude1-latitude2)/(longitude1-longitude2)+"__"+Math.toDegrees(Math.atan(((latitude1-latitude2)/(longitude1-longitude2)))));
		//int angle=(int) Math.toDegrees(Math.atan(((latitude1-latitude2)/(longitude1-longitude2))));
//		angle=angle>0?angle:(180+angle);
//		angle=angle<90?angle:(Math.abs((angle-180)));
//		double angle=(longitude1-longitude2)/(latitude1-latitude2);
//		System.out.println("最后返回斜率是"+angle);
		return latitude1-latitude2;
	}


}
