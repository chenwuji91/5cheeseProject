package carTrace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DbPool;
import defineRoad.Calculate;


public class Position {

	private Node node;
	/**
	 * 
	 * @param traceDao
	 * @description ��������Ҫ��������λ����Ϣ��������ɺ󱣴����ڵ�·����Ϣ
	 * @description ����Ŀ�����
	 */
	public void definePosition(Node n)
	{
		int min=getNearby(n);//�õ����뵱ǰ����ĵ�
		ArrayList<Integer> neighbour=findRoad(min, n);//�õ�ÿ��·����Χ���ĸ��ڽڵ�,�Ѿ�����ȥnull�������ص�����ʵ���ھ���
		System.out.println("��С�㣺"+min+"�����ڵ㣺"+neighbour);
		ArrayList<Integer> neighbourAngle=calculateAngle(neighbour,min, n);//���ؼ���ļ����н�
		
	}
	/**
	 * @description ����ڵ�������Ϣ�����ؾ���ýڵ������·��
	 * @param n
	 * @return min
	 */
	private int getNearby(Node n){
		//System.out.println(n.getCarID());
		int min=9999;
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
				if(temp1<temp2)
					min=resultId;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return min;
	}
	/**
	 * 
	 * @param min
	 * @param n
	 * @return ��ǰ�ڽڵ�ļ���
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
	 * @return �ڽڵ��뵱ǰ��ļн�
	 */
	private ArrayList<Integer> calculateAngle(ArrayList<Integer> neighbour,int min,Node n)
	{		
		ArrayList<Integer> angle=new ArrayList<Integer>();
		CalculateAngle cal=new CalculateAngle();
		for(int i=0;i<neighbour.size();i++)
		{
			angle.add(cal.calculateAngle(neighbour.get(i),min));
		}
		
		return angle;
	}
	

}
