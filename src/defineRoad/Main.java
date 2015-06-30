package defineRoad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


//https://github.com/chenwuji91/chenwuji.git
import database.DbPool;

public class Main implements Runnable{

	private ArrayList<carData> car;
	private static int jj=0;
	Main()
	{
		car=new ArrayList<carData>();
	}
	private class carData{
		private int carId;
		private float latitude;
		private float longitude;
		private int angle;
		
		carData(){}
		carData(int carId,float latitude,float longitude,int angle)
		{
			this.carId=carId;
			this.latitude=latitude;
			this.longitude=longitude;
			this.angle=angle;
		}
		public int getId()
		{
			return this.carId;
		}
		public float getLatitude()
		{
			return this.latitude;
		}
		public float getLongitude()
		{
			return this.longitude;
		}
		public int getAngle(){
			return this.angle;
		}
	}
	public void run()
	{
		Main m=new Main();
		int temp=999999;;
		synchronized (this) {
			for(;jj<48372;jj++)
			temp=jj;
			
		}
		
		{
			m.getData(temp*1000,(temp+1)*1000);
			System.out.println(m.car.size());
			
			for(int i=0;i<m.car.size();i++)
			{
				//m.getNearby(m.car.get(i));
				HashMap<Integer, Float> road=new HashMap<Integer, Float>();
				int minPoint=m.getNearby(m.car.get(i),road);
				String result=m.findRoad(minPoint,m.car.get(i).getAngle());
				m.writeDb(m.car.get(i).getId(), result);
			}
			
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbPool.init();	
		Main m=new Main();
		new Thread(m,"Thread 1").start();
		new Thread(m,"Thread 2").start();
		new Thread(m,"Thread 3").start();
		new Thread(m,"Thread 4").start();
		new Thread(m,"Thread 5").start();
		new Thread(m,"Thread 6").start();
		new Thread(m,"Thread 7").start();
		new Thread(m,"Thread 8").start();
		new Thread(m,"Thread 9").start();
		new Thread(m,"Thread 10").start();
		
		

	}
	private void getData(int beginIndex,int endIndex){
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select carId,latitude,longitude,angle from suzhou.car where index1>"+beginIndex+" and index1<"+endIndex);
			while(rs.next())
			{
				int carId=rs.getInt(1);
				float latitude=rs.getFloat(2);
				float longitude=rs.getFloat(3);
				int angle=rs.getInt(4);
				car.add(new carData(carId,latitude,longitude,angle));
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private int getNearby(carData onecar,HashMap<Integer, Float> road){
		System.out.println(onecar.getId());
		int min=9999;
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM suzhou.road1 where"
					+ " road1.latitude>"+(onecar.getLatitude()-0.005)+" and "
					+ "road1.latitude<"+(onecar.getLatitude()+0.005)+" and "
					+ "road1.longitude>"+(onecar.getLongitude()-0.005)+" and "
					+ "road1.longitude<"+(onecar.getLongitude()+0.005)+";");
			float temp1=99999,temp2=99999;
			while(rs.next())
			{
				int resultId=rs.getInt(1);
				float latitude=rs.getFloat(3);
				float longitude=rs.getFloat(4);
				System.out.println("ID"+resultId+" "+latitude+" "+longitude);
				road.put(resultId,temp1=(float) Calculate.getDistance(latitude, longitude, onecar.getLatitude(),onecar.getLongitude()));
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
	
	private String findRoad(int minPoint,int angle)
	{
		int n1,n2,n3,n4;
		n1=n2=n3=n4=-1;
		try {
			Connection conn=DbPool.ds2.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM suzhou.road2 where id1="+minPoint);	
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
		System.out.println("最终的返回结果是："+this.cAngle(angle,minPoint, n1, n2, n3, n4));
		return this.cAngle(angle,minPoint, n1, n2, n3, n4);
		 
	}
	
	private String cAngle(int angle,int minPoint,int n1,int n2,int n3,int n4)
    {
    	if(angle>180)
    		angle=angle-180;
    	System.out.println("计算角度的时候，传入进来的临近点的ID是："+" "+n1+" "+n2+" "+n3+" "+n4+" 最后的行车的角度是"+angle+"当前点"+minPoint);
    	int angle1=getPosi(minPoint, n1);
    	int angle2=getPosi(minPoint, n2);
    	int angle3=getPosi(minPoint, n3);
    	int angle4=getPosi(minPoint, n4);
    	System.out.println("计算得到的四个相邻路口的角度是"+angle1+" "+angle2+" "+angle3+" "+angle4+" "+angle);
    	int min=999;int min2=999;
    	if(Math.abs(angle-angle1)<min)
    		{min=Math.abs(angle-angle1);min2=n1;}
    	if(Math.abs(angle-angle2)<min)
    		{min=Math.abs(angle-angle2);min2=n2;}
    	if(Math.abs(angle-angle3)<min)
    		{Math.abs(angle-angle3);min2=n3;}
    	if(Math.abs(angle-angle4)<min)
    		{Math.abs(angle-angle4);min2=n4;}
    	return minPoint+"+"+min2;
    }
	private int getPosi(int minPoint,int n)
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
		System.out.println("最后返回的结果是"+angle);
		return angle;
	}
	
	private void writeDb(int carId,String result)
	{
		Connection conn;
		try {
			conn = DbPool.ds2.getConnection();
			PreparedStatement prestmt=conn.prepareStatement("insert into suzhou.position(idposition,position) values(?,?)");
			prestmt.setInt(1, carId);
			prestmt.setString(2, result);
			prestmt.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    			
		
	}
	

}
