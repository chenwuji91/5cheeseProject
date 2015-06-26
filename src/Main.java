import java.io.File;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file=new File("D:\\suzhou");
		String[] list=file.list();
		File[] filename=file.listFiles();
		for(int i=0;i<list.length;i++)
		System.out.println(filename[i]);
		

		
	}

}
