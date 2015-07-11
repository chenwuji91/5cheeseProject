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
 * @description �����ֹ�����Ҫ�Ǵ��������յ��·����Ϣ������DFS�����ص����·��
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
		System.out.println("ǰ���"+this.begin+",�յ㣺"+this.end);
		DFS(onePath,begin,0);
		System.out.println(allPath);
		return allPath.get(0);//ʵ��Ҫ����ʵ��Ȩֵ��С��·��
	}
	
	
	private void DFS(ArrayList<Integer> onepath,int now,int deep)
	{
		
		//System.out.println("��ǰ���"+deep);
		if(now==this.end)//��ʾ�ҵ�·����������һ��
		{
			
			onepath.add(now);//��ǰ�ڵ���뵽��󵥸�·���������
			//System.out.println("�ҵ���"+onepath);
			//System.out.println("all path"+allPath);
			ArrayList<Integer> temp=(ArrayList<Integer>) onepath.clone();
			allPath.add(temp);//�ɻ������Ѿ��ɻ��ķŵ�����ȥ	
			onepath.remove(onepath.size()-1);
			onepath.remove(onepath.size()-1);
			
			return;
		}
		if(deep>5)//������ȴﵽ10 ���ص��ô�
		{
			//System.out.println("��Ŷ��û�ҵ���"+onepath);
			//onepath=null;
			onepath.remove(onepath.size()-1);
			//deep--;
			return;
		}
		//onepath.add(now);
		deep=deep+1;
		//���Ǵ˴���Ԫ�ؼ��뼯��
		ArrayList<Integer> neighbour=findRoad(now);
		
		for(int i=0;i<neighbour.size();i++)
		{
			onepath.add(now);
			//System.out.println("�����ң�"+onepath);
			//������ȼ�1���ڴ���֮ǰִ��
			
			DFS(onepath,neighbour.get(i),deep);
		}
		//System.out.println("all path"+allPath);
		try{
			onepath.remove(onepath.size()-1);
			deep--;
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e)
		{
			System.out.println("�߽�����");
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
