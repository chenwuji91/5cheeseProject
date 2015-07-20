package cwj.interest;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import carTrace.Path;
import carTrace.TraceDAO;

public class InterestRoad implements Interest 
{


	@Override
	public void control() {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i++){
			int carId=getRandom(1,3997);
			TreeMap<Integer,ArrayList<Integer>> oneRecord=new TreeMap<Integer,ArrayList<Integer>>();
			System.out.println("Hi");
			while(oneRecord.size()<500)//��������
			{
				int roadId=getRandom(1,1402);
				//System.out.println(roadId);
				ArrayList<Integer> around=getAround(roadId);
				System.out.println("��ǰ·��"+around);
				if(around!=null)
				oneRecord.put(roadId, around);
				System.out.println(oneRecord.size());
			}
			while(!oneRecord.isEmpty()){//�������
				Entry<Integer, ArrayList<Integer>> entry=oneRecord.pollFirstEntry();
				output(carId,entry.getKey(),entry.getValue());
				System.out.println(entry.getValue());
			}
			
		}
	}

	@Override
	public int getRandom(int begin, int end) {
		// TODO Auto-generated method stub
		return begin+(int)((double)(end-begin)*(Math.random()));
	}

	@Override
	public void output(int carId, int road, ArrayList<Integer> around) {
		// TODO Auto-generated method stub
		File file =new File(Interest.PATH+carId);    
		//����ļ��в������򴴽�    
		if(!file.exists()&& !file.isDirectory())      
		{        
		    file.mkdir();    
		}
		InterestToFile tofile=new InterestToFile();
		tofile.toFile(Interest.PATH+carId+"\\"+carId+".txt",road+around.toString());
		
		
	}

	@Override
	public ArrayList<Integer> getAround(int road) {
		// TODO Auto-generated method stub
		InterestDAO db=new InterestDAO();
		//System.out.println("road ID:"+road);
		double[] point=db.pointPosition(road);
		//System.out.println("point:"+point);
		ArrayList<Integer> around=db.areaAround(point);
		int begin=road;
//		int end=around.get(getRandom(0,around.size()));
//		TraceDAO traceDao=new TraceDAO(begin,end);
//		Path path=new Path(traceDao);//
//		ArrayList<Integer> shortest=path.search();//�������·
//		if(shortest!=null)
//		System.out.println("���·��"+shortest);
//		return shortest;
		return db.getRoad(begin);
		
	}
	
	

}
