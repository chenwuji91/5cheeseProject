package carTrace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DbPool;

public class ResultToFile {
	ArrayList<Integer> path;
	ArrayList<Integer> pathLength=new ArrayList<Integer>();
	Node n1;
	Node n2;
	ArrayList<Node> result=new ArrayList<Node>();
	PathDB db=new PathDB();

	public ResultToFile(ArrayList<Integer> path, Node n1, Node n2) {
		super();
		this.path = path;
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public void outPut(String outpath){
		int time=(int) (n2.getTimeStamp().getTime()-n1.getTimeStamp().getTime())/1000;
		System.out.println("ʱ����"+time);
		if(path==null)
		{
			dealEmpty(time);
			ToFile(outpath);
			return;
		}

		int distance=calculateDis();
	//	float speed=distance/time;
		this.ToNode(distance,time);
		System.out.println(result.size());
		ToFile(outpath);
		
		
	}
	private void dealEmpty(int time) {
		// TODO Auto-generated method stub
		{
			for(int j=0;j<time;j++)
			{
				double latitude1=n1.getLati();
				double latitude2=n2.getLati();
				double longitude1=n1.getLongi();
				double longitude2=n2.getLongi();
				double latitudeI=(latitude2-latitude1)/time;
				double longitudeI=(longitude2-longitude1)/time;
				{
				result.add(new Node(n1.getCarID(),latitude1+latitudeI*(j+1),longitude1+longitudeI*(j+1),
						n1.getSpeed(),n1.getAngle(),n1.getTimeStamp().toString(),n1.getOccupy1(),n1.getOccupy2(),n1.getDis()));
				}
			}
			
		}
	}

	private int calculateDis()
	{
		int distance=0;
		
		pathLength.add((db.getDis(n1.getRoadIndex())/2));
		distance=distance+pathLength.get(0);
		for(int i=0;i<path.size()-1;i++)
		{
			pathLength.add(db.getDis(path.get(i),path.get(i+1)));
			distance=distance+pathLength.get(i+1);
			
		}
		//�˴�����������,�յ����
		
		pathLength.add((db.getDis(n2.getRoadIndex())/2));
		distance=distance+pathLength.get(pathLength.size()-1);
		
		return distance;
		
	}
	private void ToNode(int distance,int time)
	{
		//д���һ�ε�����
		
		
				{
					double latitude1=n1.getLati();
					double longitude1=n1.getLongi();
					try {
						Connection conn=DbPool.ds2.getConnection();
						Statement stmt=conn.createStatement();
						ResultSet rs=stmt.executeQuery("SELECT lati1,lati2,longi1,longi2 FROM suzhou.road3 where index1="+n1.getRoadIndex());	
						if(rs.next())
						{
							latitude1=(rs.getDouble(1)+rs.getDouble(2))/2;	
							longitude1=(rs.getDouble(3)+rs.getDouble(4))/2;	
						}
						conn.close();
					} catch (SQLException e) { 
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double latitude2=n1.getLati();;
					double longitude2=n1.getLongi();
					try {
						Connection conn=DbPool.ds2.getConnection();
						Statement stmt=conn.createStatement();
						ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where id1="+path.get(0));	
						if(rs.next())
						{
							latitude2=rs.getDouble(1);	
							longitude2=rs.getDouble(2);	
						}
						conn.close();
					} catch (SQLException e) { 
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(distance);
					int time1=(int) ((double)time*((double)pathLength.get(0)/(double)distance));
					double latitudeI=(latitude2-latitude1)/time1;
					double longitudeI=(longitude2-longitude1)/time1;
					System.out.println(time1);
					for(int j=0;j<time1;j++)//��Node�����Ԫ��
					{
						//System.out.println(latitude1+latitudeI*(j+1));
						int temp=n1.getTimeStamp().getSeconds();
						n1.getTimeStamp().setSeconds(temp+1);
						result.add(new Node(n1.getCarID(),latitude1+latitudeI*(j+1),longitude1+longitudeI*(j+1),
								n1.getSpeed(),n1.getAngle(),n1.getTimeStamp().toString(),n1.getOccupy1(),n1.getOccupy2(),n1.getDis()));
					}
				}
				//���
				for(int i=0;i<path.size()-1;i++)//�ܹ��м���·������·���δ���
				{
					//int time1=time/distance*pathLength.get(i);//��ǰ·�ε�ʱ����  ����ǰ·�ε�����
					int time1=(int) ((double)time*((double)pathLength.get(i)/(double)distance));
					double latitude1=n1.getLati();
					double longitude1=n1.getLongi();
					try {
						Connection conn=DbPool.ds2.getConnection();
						Statement stmt=conn.createStatement();
						ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where id1="+path.get(i));	
						if(rs.next())
						{
							latitude1=rs.getDouble(1);	
							longitude1=rs.getDouble(2);	
						}
						conn.close();
					} catch (SQLException e) { 
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double latitude2=n1.getLati();;
					double longitude2=n1.getLongi();
					try {
						Connection conn=DbPool.ds2.getConnection();
						Statement stmt=conn.createStatement();
						ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where id1="+path.get(i+1));	
						if(rs.next())
						{
							latitude2=rs.getDouble(1);	
							longitude2=rs.getDouble(2);	
						}
						conn.close();
					} catch (SQLException e) { 
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					double latitudeI=(latitude2-latitude1)/time1;
					double longitudeI=(longitude2-longitude1)/time1;
					for(int j=0;j<time1;j++)//��Node�����Ԫ��
					{
						int temp=n1.getTimeStamp().getSeconds();
						n1.getTimeStamp().setSeconds(temp+1);
						result.add(new Node(n1.getCarID(),latitude1+latitudeI*(j+1),longitude1+longitudeI*(j+1),
								n1.getSpeed(),n1.getAngle(),n1.getTimeStamp().toString(),n1.getOccupy1(),n1.getOccupy2(),n1.getDis()));
					}
					
					
				}
				//���һ������
				{
					double latitude2=n2.getLati();
					double longitude2=n2.getLongi();
					try {
						Connection conn=DbPool.ds2.getConnection();
						Statement stmt=conn.createStatement();
						ResultSet rs=stmt.executeQuery("SELECT lati1,lati2,longi1,longi2 FROM suzhou.road3 where index1="+n2.getRoadIndex());	
						if(rs.next())
						{
							latitude2=(rs.getDouble(1)+rs.getDouble(2))/2;	
							longitude2=(rs.getDouble(3)+rs.getDouble(4))/2;	
						}
						conn.close();
					} catch (SQLException e) { 
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double latitude1=n2.getLati();;
					double longitude1=n2.getLongi();
					try {
						Connection conn=DbPool.ds2.getConnection();
						Statement stmt=conn.createStatement();
						ResultSet rs=stmt.executeQuery("SELECT latitude,longitude FROM suzhou.road1 where id1="+path.get(path.size()-1));	
						if(rs.next())
						{
							latitude1=rs.getDouble(1);	
							longitude1=rs.getDouble(2);	
						}
						conn.close();
					} catch (SQLException e) { 
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(distance);
					int time1=(int) ((double)time*((double)pathLength.get(pathLength.size()-1)/(double)distance));
					double latitudeI=(latitude2-latitude1)/time1;
					double longitudeI=(longitude2-longitude1)/time1;
					System.out.println(time1);
					for(int j=0;j<time1;j++)//��Node�����Ԫ��
					{
						int temp=n1.getTimeStamp().getSeconds();
						n1.getTimeStamp().setSeconds(temp+1);
						//System.out.println(latitude1+latitudeI*(j+1));
						result.add(new Node(n1.getCarID(),latitude1+latitudeI*(j+1),longitude1+longitudeI*(j+1),
								n1.getSpeed(),n1.getAngle(),n1.getTimeStamp().toString(),n1.getOccupy1(),n1.getOccupy2(),n1.getDis()));
					}
				}
				//����
	}
	
	private void ToFile(String outpath)
	{
		FileWriter writer;
		try {
			writer = new FileWriter(outpath+"change",true);
			BufferedWriter buffer = new BufferedWriter(writer); 
			StringBuilder sb = new StringBuilder(); 
	 
			for(int i=0;i<result.size();i++)
			{
				sb.append(result.get(i).getCarID()+","+result.get(i).getLati()+","+result.get(i).getLongi()+","+
						result.get(i).getSpeed()+","+result.get(i).getAngle()+","+result.get(i).getTimeStamp().toString()+","+
						result.get(i).getOccupy1()+","+result.get(i).getOccupy2()+"\r\n"); //������׷��һ�εļ�¼
			}	 
			buffer.write(sb.toString()); 		
			buffer.flush(); 
			buffer.close(); 
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		
		} 
	}
	


