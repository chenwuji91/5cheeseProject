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
	 * @description ��������Ҫ��������λ����Ϣ��������ɺ󱣴����ڵ�·����Ϣ
	 * @description ����Ŀ�����
	 */
	public boolean definePosition(Node n)
	{
		TreeMap<Float,Integer> val=getNearby(n);//�õ����뵱ǰ����ĵ�
		int min,mid;
		try{
			min=val.get(val.firstKey());
//			val.pollFirstEntry();
//			mid=val.get(val.firstKey());
		}
		catch(java.util.NoSuchElementException e)//����û���ҵ������
		{
			
			TreeMap<Float,Integer> val1=getNearby1(n);//�õ����뵱ǰ����ĵ�
			try{
				System.out.println("û�ҵ��ھ�");
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
		
		ArrayList<Integer> neighbour=findRoad(min, n);//�õ�ÿ��·����Χ���ĸ��ڽڵ�,�Ѿ�����ȥnull�������ص�����ʵ���ھ���
		//System.out.println("��С�㣺"+min+"���м�㣺"+mid+",���ڵ㣺"+neighbour);
		TreeMap<Double,Integer> neighbourK=calculateAngle(neighbour,min, n);//���ؼ���ļ����н���n�Ĳ�ֵ��������С�������� pullfirst�ҳ���С��
		int min2=neighbourK.get(neighbourK.firstKey());
		//System.out.println("������ļн�б��"+neighbourK+",��С�㣺"+neighbourK.get(neighbourK.firstKey()));
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
		//���ݼн���ѡ���н����������·
//		TreeMap<Double,Integer> angle=
	}
	/**
	 * @description ����ڵ�������Ϣ�����ؾ���ýڵ������·��
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
	 * @return �ڽڵ��뵱ǰ���б��
	 */
	private TreeMap<Double,Integer> calculateAngle(ArrayList<Integer> neighbour,int min,Node n)
	{		
		TreeMap<Double,Integer> angle=new TreeMap<Double,Integer>();
		CalculateAngle cal=new CalculateAngle();
		for(int i=0;i<neighbour.size();i++)
		{
			int temp=90-n.getAngle();//�Ƕ���n.getAngle()
			double value1=Math.abs(cal.calculateAngle(neighbour.get(i),min));
			double value2=Math.abs(Math.tan((temp)*(Math.PI/180)));
			//System.out.println("value2: "+value2);
			//System.out.println("value1:"+value1);
			double value3=Math.abs(Math.atan(value1));
			double value4=Math.abs(Math.atan(value2));
			angle.put(Math.abs(value3-value4),neighbour.get(i));//��������нǶȵ�б�ʲ�ֵ
		}
		return angle;
	}
	private boolean judgePosi(int angle,int n1,int n2)
	{
		CalculateAngle cal=new CalculateAngle();
		float temp1=(float) cal.calculatePosi(n1, n2);//������ֵ��ʾ������������ʻ  ����ƫ�����ĵ�n1�ķ�����ʻ
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
	 * �˺������쳣����ʹ�ã���������û���ҵ���������Ŵ�Χ
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
