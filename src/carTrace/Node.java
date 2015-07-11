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
	private int roadIndex;
	private int nextPoint;
	private int lastPoint;
	
	private int beginPoint;

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
	
	
	/**
	 * @return the occupy1
	 */
	public int getOccupy1() {
		return occupy1;
	}


	/**
	 * @param occupy1 the occupy1 to set
	 */
	public void setOccupy1(int occupy1) {
		this.occupy1 = occupy1;
	}


	/**
	 * @return the occupy2
	 */
	public int getOccupy2() {
		return occupy2;
	}


	/**
	 * @param occupy2 the occupy2 to set
	 */
	public void setOccupy2(int occupy2) {
		this.occupy2 = occupy2;
	}


	public int getCarID() {
		return carID;
	}
	public void setCarID(int carID) {
		this.carID = carID;
	}
	public double getLati() {
		return lati;
	}
	public void setLati(double lati) {
		this.lati = lati;
	}
	public double getLongi() {
		return longi;
	}
	public void setLongi(double longi) {
		this.longi = longi;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	public double getDis() {
		return dis;
	}
	public void setDis(double dis) {
		this.dis = dis;
	}
	
	/**
	 * @return the roadIndex
	 */
	public int getRoadIndex() {
		return roadIndex;
	}
	/**
	 * @param roadIndex the roadIndex to set
	 */
	public void setRoadIndex(int roadIndex) {
		this.roadIndex = roadIndex;
	}
	/**
	 * @return the nextPoint
	 */
	public int getNextPoint() {
		return nextPoint;
	}
	/**
	 * @param nextPoint the nextPoint to set
	 */
	public void setNextPoint(int nextPoint) {
		this.nextPoint = nextPoint;
	}
	/**
	 * @return the lastPoint
	 */
	public int getLastPoint() {
		return lastPoint;
	}
	/**
	 * @param lastPoint the lastPoint to set
	 */
	public void setLastPoint(int lastPoint) {
		this.lastPoint = lastPoint;
	}
	
	

}
