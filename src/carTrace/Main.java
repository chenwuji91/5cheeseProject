package carTrace;

import java.util.ArrayList;

import database.DbPool;

public class Main {

	public static ArrayList<Node> node=new ArrayList<Node>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DbPool.init();
		ReadFile data=new ReadFile();
		node=data.getData(node);//һ���ڵ㣬�����˵�ǰĿ¼�ļ��µ����еĽڵ���Ϣ
		//for(int i=0;i<node.size();i++)//���ڿ�ʼ����ѡȡ����յ���бȽ�
		{
			Node n1,n2;
//			try{
//				n1=node.get(i);
//				n2=node.get(i+1);
				n1=node.get(1);
				n2=node.get(2);
//			}
//			catch(java.lang.IndexOutOfBoundsException e)
//			{
//				continue;
//			}
			
			Position position=new Position();//��ȡ������Ҫ��������Ϣ�����浽traceDao����
			position.definePosition(n1);//��λ��ȡ��һ�����·�Σ���ʻ���򣬷���ֵtraceDao��nullҲ���ԣ�	
			position.definePosition(n2);//��λ��õڶ������·�Σ���ʻ���򣬷���ֵtraceDao��null��
			//�����·�ھ����ĸ�λ�õ��ж�
			TraceDAO traceDao=new TraceDAO(n1,n2);
			Path path=new Path(traceDao);//
			path.search();
			//��һ��·�ε��м�Ϊ��㣬������ʻ�����ҵ���һ��·�ڣ�������һ��·�ڣ����յ����һ��·�ڣ��ҳ��м��·��,����ֵArrayList�����������м侭��������·�Σ���Ȩͼ������ʻ·���Ļ�ԭ��
			//������ʻ��·�Σ��Լ���Ӧ·�εĳ��ȣ����������м�ʱ������ÿ��λ��ʱ�̡�����ֵ���м�·�ε�Arraylist��traceDao���󣬷���ֵArrayList������󷵻صĽ����
			//������������ݿ�
			
		}
		
		
		

	}

}
