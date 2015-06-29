import java.util.HashMap;


public class aaaaa {

	public static void main(String[] args){
		HashMap<Integer, Float> road=new HashMap<Integer,Float>();
		aaaaa m=new aaaaa();
		m.test(road);
		System.out.println(road);
	}
	private void test(HashMap<Integer, Float> road1)
	{
		road1.put(123, (float) 11.22);
	}
}
