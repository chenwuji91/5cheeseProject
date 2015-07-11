package carTrace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DbPool;

public class PathDB {

	/**
	 * 
	 * @param 某个点
	 * @return 这个点的四个邻居
	 */
	public ArrayList<Integer> findRoad(int min)
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
	/**
	 * 
	 * @param id1
	 * @param id2
	 * @return 某一路段两个之间的距离
	 */
	public int getDis(int id1,int id2)
	{
		int dis=99999;
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT dis FROM suzhou.road3 where id1="+id1+" and id2="+id2);	
			if(rs.next())
			{
				dis=rs.getInt(1);	
			}
			conn.close();
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dis;
	}
	public int getDis(int index1)
	{
		int dis=99999;
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT dis FROM suzhou.road3 where index1="+index1);	
			if(rs.next())
			{
				dis=rs.getInt(1);	
			}
			conn.close();
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dis;
	}
}
