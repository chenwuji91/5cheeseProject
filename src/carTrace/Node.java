package carTrace;

import java.sql.Timestamp;
public class Node {
	private int carID;
	private double lati;
	private double longi;
	private int speed;
	private int angle;
	private Timestamp timeStamp;
	private int occupy1;
	private int occupy2;
	private double dis;
	Node(int carID,double lati,double longi,int speed,int angle,String time,int occupy1,int occupy2,double dis)
	{
		this.carID=carID;
		this.lati=lati;
		this.longi=longi;
		this.speed=speed;
		this.angle=angle;
		this.timeStamp=Timestamp.valueOf(time);
		this.occupy1=occupy1;
		this.occupy2=occupy2;
		this.dis=dis;
	}
	

}
