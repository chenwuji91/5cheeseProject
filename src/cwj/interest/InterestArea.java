package cwj.interest;


import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;


public class InterestArea implements Interest{

	@Override
	public int getRandom(int begin,int end) {
		// TODO Auto-generated method stub
		 return begin+(int)((double)(end-begin)*(Math.random()));
	
	}

	@Override
	public void output(int carId, int road, ArrayList<Integer> around) {
		// TODO Auto-generated method stub
		File file =new File(Interest.PATH+carId);    
		//如果文件夹不存在则创建    
		if(!file.exists()&& !file.isDirectory())      
		{        
		    file.mkdir();    
		}
		InterestToFile tofile=new InterestToFile();
		tofile.toFile(Interest.PATH+carId+"\\"+carId+".txt",road+around.toString());
		
		
		
	}


	@Override
	public ArrayList<Integer> getAround(int roadId) {
		// TODO Auto-generated method stub
		InterestDAO db=new InterestDAO();
		double[] point=db.pointPosition(roadId);
		ArrayList<Integer> around=db.areaAround(point);
		return around;
	}

	@Override
	public void control() {
		// TODO Auto-generated method stub
		System.out.println(getRandom(1,3998));
		for(int i=0;i<5;i++){
			int carId=getRandom(1,3997);
			TreeMap<Integer,ArrayList<Integer>> oneRecord=new TreeMap<Integer,ArrayList<Integer>>();
			System.out.println("Hi");
			while(oneRecord.size()<500)//控制生成
			{
				int roadId=getRandom(1,1402);
				ArrayList<Integer> around=getAround(roadId);
				oneRecord.put(roadId, around);
				System.out.println(oneRecord.size());
			}
			while(!oneRecord.isEmpty()){//控制输出
				Entry<Integer, ArrayList<Integer>> entry=oneRecord.pollFirstEntry();
				output(carId,entry.getKey(),entry.getValue());
				System.out.println(entry.getValue());
			}
			
		}
	}


}
