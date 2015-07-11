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
 * @description �����ֹ�����Ҫ�Ǵ��������յ��·����Ϣ������DFS�����ص����·��
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
	 * @description ����������� ������
	 * @return
	 */
	public ArrayList<Integer> search()
	{
		ArrayList<Integer> onePath=new ArrayList<Integer>();
		System.out.println("ǰ���"+this.begin+",�յ㣺"+this.end);
		DFS(onePath,begin,0);
		System.out.println(allPath);
		//����������·�������ҳ����ŵ�·��
		TreeMap <Integer,Integer> distance=new TreeMap<Integer,Integer>();
		for(int i=0;i<allPath.size();i++)
		{
			distance.put(calculate(allPath.get(i)), i);
		}
		if(distance.isEmpty())
			return null;
		return allPath.get(distance.get(distance.firstKey()));//ʵ��Ҫ����ʵ��Ȩֵ��С��·��
	}
	
	
	/**
	 * 
	 * @param path
	 * @return ����ĳһ��·�̵�ȫ������
	 */
	private int calculate(ArrayList<Integer> path) {
		// TODO Auto-generated method stub
		int count=0;
		for(int i=0;i<path.size()-1;i++)
		{
			count=count+db.getDis(path.get(i), path.get(i+1));
		}
		System.out.println("���·������"+count);
		return count;
		
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
			try{
				onepath.remove(onepath.size()-1);
				onepath.remove(onepath.size()-1);
			}
			catch(java.lang.ArrayIndexOutOfBoundsException e)
			{
				System.out.println("�߽�����");
				return;
			}
			
			return;
		}
		if(deep>3)//������ȴﵽ10 ���ص��ô�
		{
			//System.out.println("��Ŷ��û�ҵ���"+onepath);
			//onepath=null;
			try{
				onepath.remove(onepath.size()-1);
			}
			catch(java.lang.ArrayIndexOutOfBoundsException e)
			{
				System.out.println("�߽�����");
				return;
			}
			
			//deep--;
			return;
		}
		//onepath.add(now);
		deep=deep+1;
		//���Ǵ˴���Ԫ�ؼ��뼯��
		ArrayList<Integer> neighbour=db.findRoad(now);
		
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



}
