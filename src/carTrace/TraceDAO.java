package carTrace;

import java.sql.Timestamp;

public class TraceDAO {

	private int carId1;
	private int carId2;
	private double latitude1;
	private double latitude2;
	private double longitude1;
	private double longitude2;
	private int angle1;
	private int angle2;
	private int speed1;
	private int speed2;
	private Timestamp timestamp1;
	private Timestamp timestamp2;
	private int nextPoint1;
	private int nextPoint2;
	private int lastPoint1;
	private int lastPoint2;
	
	public TraceDAO(Node n1, Node n2) {
		// TODO Auto-generated constructor stub
		this.carId2=n1.getCarID();
		this.carId2=n2.getCarID();
		this.latitude1=n1.getLati();
		this.latitude2=n2.getLati();
		this.longitude1=n1.getLongi();
		this.longitude2=n2.getLongi();
		this.angle1=n1.getAngle();
		this.angle2=n2.getAngle();
		this.timestamp1=n1.getTimeStamp();
		this.timestamp2=n2.getTimeStamp();
		this.nextPoint1=n1.getNextPoint();
		this.nextPoint2=n2.getNextPoint();
		this.lastPoint1=n1.getLastPoint();
		this.lastPoint2=n2.getLastPoint();
		
	}
	public TraceDAO(int begin, int end) {
		// TODO Auto-generated constructor stub
		
		this.nextPoint1=begin;
		this.lastPoint2=end;
		
	}

	/**
	 * @return the nextPoint1
	 */
	public int getNextPoint1() {
		return nextPoint1;
	}

	/**
	 * @param nextPoint1 the nextPoint1 to set
	 */
	public void setNextPoint1(int nextPoint1) {
		this.nextPoint1 = nextPoint1;
	}

	/**
	 * @return the nextPoint2
	 */
	public int getNextPoint2() {
		return nextPoint2;
	}

	/**
	 * @param nextPoint2 the nextPoint2 to set
	 */
	public void setNextPoint2(int nextPoint2) {
		this.nextPoint2 = nextPoint2;
	}

	/**
	 * @return the lastPoint1
	 */
	public int getLastPoint1() {
		return lastPoint1;
	}

	/**
	 * @param lastPoint1 the lastPoint1 to set
	 */
	public void setLastPoint1(int lastPoint1) {
		this.lastPoint1 = lastPoint1;
	}

	/**
	 * @return the lastPoint2
	 */
	public int getLastPoint2() {
		return lastPoint2;
	}

	/**
	 * @param lastPoint2 the lastPoint2 to set
	 */
	public void setLastPoint2(int lastPoint2) {
		this.lastPoint2 = lastPoint2;
	}

	/**
	 * @param longitude1 the longitude1 to set
	 */
	public void setLongitude1(double longitude1) {
		this.longitude1 = longitude1;
	}

	/**
	 * @return the carId1
	 */
	public int getCarId1() {
		return carId1;
	}

	/**
	 * @param carId1 the carId1 to set
	 */
	public void setCarId1(int carId1) {
		this.carId1 = carId1;
	}

	/**
	 * @return the carId2
	 */
	public int getCarId2() {
		return carId2;
	}

	/**
	 * @param carId2 the carId2 to set
	 */
	public void setCarId2(int carId2) {
		this.carId2 = carId2;
	}

	/**
	 * @return the latitude1
	 */
	public double getLatitude1() {
		return latitude1;
	}

	/**
	 * @param latitude1 the latitude1 to set
	 */
	public void setLatitude1(double latitude1) {
		this.latitude1 = latitude1;
	}

	/**
	 * @return the latitude2
	 */
	public double getLatitude2() {
		return latitude2;
	}

	/**
	 * @param latitude2 the latitude2 to set
	 */
	public void setLatitude2(double latitude2) {
		this.latitude2 = latitude2;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude1() {
		return longitude1;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude1) {
		this.longitude1 = longitude1;
	}

	/**
	 * @return the longitude2
	 */
	public double getLongitude2() {
		return longitude2;
	}

	/**
	 * @param longitude2 the longitude2 to set
	 */
	public void setLongitude2(double longitude2) {
		this.longitude2 = longitude2;
	}

	/**
	 * @return the angle1
	 */
	public int getAngle1() {
		return angle1;
	}

	/**
	 * @param angle1 the angle1 to set
	 */
	public void setAngle1(int angle1) {
		this.angle1 = angle1;
	}

	/**
	 * @return the angle2
	 */
	public int getAngle2() {
		return angle2;
	}

	/**
	 * @param angle2 the angle2 to set
	 */
	public void setAngle2(int angle2) {
		this.angle2 = angle2;
	}

	/**
	 * @return the speed1
	 */
	public int getSpeed1() {
		return speed1;
	}

	/**
	 * @param speed1 the speed1 to set
	 */
	public void setSpeed1(int speed1) {
		this.speed1 = speed1;
	}

	/**
	 * @return the speed2
	 */
	public int getSpeed2() {
		return speed2;
	}

	/**
	 * @param speed2 the speed2 to set
	 */
	public void setSpeed2(int speed2) {
		this.speed2 = speed2;
	}

	/**
	 * @return the timestamp1
	 */
	public Timestamp getTimestamp1() {
		return timestamp1;
	}

	/**
	 * @param timestamp1 the timestamp1 to set
	 */
	public void setTimestamp1(Timestamp timestamp1) {
		this.timestamp1 = timestamp1;
	}

	/**
	 * @return the timestamp2
	 */
	public Timestamp getTimestamp2() {
		return timestamp2;
	}

	/**
	 * @param timestamp2 the timestamp2 to set
	 */
	public void setTimestamp2(Timestamp timestamp2) {
		this.timestamp2 = timestamp2;
	}

	

}
