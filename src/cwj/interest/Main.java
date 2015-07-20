package cwj.interest;

import database.DbPool;

public class Main {
	public static void main(String args[])
	{
		DbPool.init();
//		InterestArea area=new InterestArea();
//		area.control();
		InterestRoad road=new InterestRoad();
		road.control();
	}

}
