package cwj.interest;

import java.util.ArrayList;

public interface Interest {
	public int getRandom(int begin,int end);
	public void output(int carId,int road,ArrayList<Integer> around);
	public ArrayList<Integer> getAround(int road);
	public void control();
	public final String PATH="D:\\interest\\";
}
