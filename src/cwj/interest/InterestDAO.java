package cwj.interest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import carTrace.PathDB;
import database.DbPool;

public class InterestDAO {
	public double[] pointPosition(int n)
	{
		double position[]=new double[2];
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where id1="+n);		
			while(rs.next())
			{
				
				position[0]=rs.getDouble(1);
				position[1]=rs.getDouble(2);
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return position;
	}
	public ArrayList<Integer> areaAround(double[] position)
	{
		ArrayList<Integer> around=new ArrayList<Integer>();
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM suzhou.road1 where"
					+ " road1.latitude>"+(position[0]-0.03)+" and "
					+ "road1.latitude<"+(position[0]+0.03)+" and "
					+ "road1.longitude>"+(position[1]-0.03)+" and "
					+ "road1.longitude<"+(position[1]+0.03)+";");
			while(rs.next())
			{
				around.add(rs.getInt(1));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return around;
	}
	public ArrayList<Integer> getRoad(int begin) {
		// TODO Auto-generated method stub
		ArrayList<Integer> as=new ArrayList<Integer>();
		as.add(begin);
		PathDB db2=new PathDB();
		ArrayList<Integer> near=db2.findRoad(begin);
		double time=System.currentTimeMillis();
		while(as.size()<12)
		{
			if(System.currentTimeMillis()-time>333){
				System.out.println("timeout");
				return null;
				//超过10秒没找到不找了
			}
			for(int j=0;j<near.size();j++)
			{
				if(as.contains(near.get(j)))
				{
					
				}
				else{//如果没有重复 添加该点 寻找下一些邻节点
					as.add(near.get(j));
					near=db2.findRoad(near.get(j));
					break;
				}
			}
			
		}
		return as;
	}
	
	
	
	
	
//	public ArrayList<Integer> roadAround(double[] position)
//	{
//		ArrayList<Integer> around=new ArrayList<Integer>();
//		try {
//			Connection conn=DbPool.ds2.getConnection();
//			Statement stmt=conn.createStatement();
//			ResultSet rs=stmt.executeQuery("SELECT * FROM suzhou.road1 where"
//					+ " road1.latitude<"+(position[0]-0.01)+" and "
//					+ "road1.latitude>"+(position[0]-0.03)+" and "
//					+ "road1.latitude>"+(position[0]+0.01)+" and "
//					+ "road1.latitude<"+(position[0]+0.03)+" and "
//					+ "road1.longitude<"+(position[1]-0.01)+" and "
//					+ "road1.longitude>"+(position[1]-0.03)+" and "
//					+ "road1.longitude>"+(position[1]+0.01)+" and "
//					+ "road1.longitude<"+(position[1]+0.03)+";");
//			while(rs.next())
//			{
//				around.add(rs.getInt(1));
//			}
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return around;
//	}

}
