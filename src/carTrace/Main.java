package carTrace;

import java.util.ArrayList;

import database.DbPool;

public class Main {

	public static ArrayList<Node> node=new ArrayList<Node>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetList filelist=new GetList();
		ArrayList<String> list=filelist.getList("D:\\suzhou\\20120301");
		DbPool.init();
		for(int kk=1;kk<list.size();kk++)
		{
		try{
		ReadFile data=new ReadFile();
		node=data.getData(node,list.get(kk));//һ���ڵ㣬�����˵�ǰĿ¼�ļ��µ����еĽڵ���Ϣ
		for(int i=0;i<node.size();i++)//���ڿ�ʼ����ѡȡ����յ���бȽ�
		{
			Node n1,n2;
			try{
//				n1=node.get(i);
//				n2=node.get(i+1);
				n1=node.get(i);
				n2=node.get(i+1);
			}
			catch(java.lang.IndexOutOfBoundsException e)
			{
				continue;
			}
			
			Position position=new Position();//��ȡ������Ҫ��������Ϣ�����浽traceDao����
			position.definePosition(n1);//��λ��ȡ��һ�����·�Σ���ʻ���򣬷���ֵtraceDao��nullҲ���ԣ�	
			position.definePosition(n2);
//			if(position.definePosition(n2))//��λ��õڶ������·�Σ���ʻ���򣬷���ֵtraceDao��null��
//				continue;//��Ҫ�����Ҳ���Ԫ�ص���� ֱ�Ӻ���
			//�����·�ھ����ĸ�λ�õ��ж�
			TraceDAO traceDao=new TraceDAO(n1,n2);
			Path path=new Path(traceDao);//
			ArrayList<Integer> shortest=path.search();//�������·��
			System.out.println("���·����"+shortest);
			//��һ��·�ε��м�Ϊ��㣬������ʻ�����ҵ���һ��·�ڣ�������һ��·�ڣ����յ����һ��·�ڣ��ҳ��м��·��,����ֵArrayList�����������м侭��������·�Σ���Ȩͼ������ʻ·���Ļ�ԭ��
			//������ʻ��·�Σ��Լ���Ӧ·�εĳ��ȣ����������м�ʱ������ÿ��λ��ʱ�̡�����ֵ���м�·�ε�Arraylist��traceDao���󣬷���ֵArrayList������󷵻صĽ����
			//������������ݿ�
			ResultToFile result=new ResultToFile(shortest,n1,n2);
			result.outPut(list.get(kk));
		}
	
		}
			catch(Exception e2)
			{
				e2.printStackTrace();
				continue;
			}
		}
		
		
		}
		
		
		

	}


