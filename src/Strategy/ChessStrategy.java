package Strategy;
import java.awt.event.*;  
import java.awt.*;  
  
import javax.swing.*;  

import BaseChess.ChessBoard;
import BaseChess.Point;


public class ChessStrategy extends JFrame {

	/**
	 * @param args
	 */
	  private ChessBoard chessBoard;  
	
	private JPanel toolbar;  
	  private JButton startButton,backButton,exitButton;  
	    
	  private JMenuBar menuBar;  
	  private JMenu sysMenu;  
	  private JMenuItem startMenuItem,exitMenuItem,backMenuItem;  
	  //���¿�ʼ���˳����ͻ���˵���  
	 public ChessStrategy(){  
	      setTitle("������������");//���ñ���  
	      chessBoard=new ChessBoard();  
	      
	        
	      Container contentPane=getContentPane();  
	      contentPane.add(chessBoard);  
	      chessBoard.setOpaque(true);  
	        
	        
	      //��������Ӳ˵�  
	      menuBar =new JMenuBar();//��ʼ���˵���  
	      sysMenu=new JMenu("ϵͳ");//��ʼ���˵�  
	      //��ʼ���˵���  
	      startMenuItem=new JMenuItem("���¿�ʼ");  
	      exitMenuItem =new JMenuItem("�˳�");  
	      backMenuItem =new JMenuItem("����");  
	      //�������˵�����ӵ��˵���  
	      sysMenu.add(startMenuItem);  
	      sysMenu.add(exitMenuItem);  
	      sysMenu.add(backMenuItem);  
	      //��ʼ����ť�¼��������ڲ���  
	      MyItemListener lis=new MyItemListener();  
	      //�������˵�ע�ᵽ�¼���������  
	      this.startMenuItem.addActionListener(lis);  
	      backMenuItem.addActionListener(lis);  
	      exitMenuItem.addActionListener(lis);  
	      menuBar.add(sysMenu);//��ϵͳ�˵���ӵ��˵�����  
	      setJMenuBar(menuBar);//��menuBar����Ϊ�˵���  
	        
	      toolbar=new JPanel();//�������ʵ����  
	      //������ť��ʼ��  
	      startButton=new JButton("���¿�ʼ");  
	      exitButton=new JButton("�˳�");  
	      backButton=new JButton("����");  
	      //��������尴ť��FlowLayout����  
	      toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));  
	      //��������ť��ӵ��������  
	      toolbar.add(startButton);  
	      toolbar.add(exitButton);  
	      toolbar.add(backButton);  
	      //��������ťע������¼�  
	      startButton.addActionListener(lis);  
	      exitButton.addActionListener(lis);  
	      backButton.addActionListener(lis);  
	      //��������岼�ֵ����桱�Ϸ���Ҳ�����·�  
	      add(toolbar,BorderLayout.SOUTH);  
	      add(chessBoard);//����������ӵ�������  
	      //���ý���ر��¼�  
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	      //setSize(800,800);  
	      pack();//����Ӧ��С  
	        
	  }  
	  private class MyItemListener implements ActionListener{  
	      public void actionPerformed(ActionEvent e){  
	          Object obj=e.getSource();//����¼�Դ  
	          if(obj==ChessStrategy.this.startMenuItem||obj==startButton){  
	              //���¿�ʼ  
	              //JFiveFrame.this�ڲ��������ⲿ��  
	              System.out.println("���¿�ʼ");  
	              chessBoard.restartGame();  
	          }  
	          else if (obj==exitMenuItem||obj==exitButton)  
	              System.exit(0);  
	          else if (obj==backMenuItem||obj==backButton){  
	              System.out.println("����...");  
	              chessBoard.goback();  
	          }  
	      }  
	  }  
	    
	  public ChessBoard getChessBoard() {
			return chessBoard;
		}
		public Point WhiteNextStep(Point[] chessList){
		/**
		 * ���Ӳ���
		 */
			return new Point(9,9,Color.BLACK);
			//return null;//�޸ķ�����һ�������
		}

		public Point BlackNextStep(Point[] chesslistB) {//���ڵ�ǰ�����������  Ѱ����һ���ߵķ���  ����һ��Point��
			// TODO Auto-generated method stub
			/**
			 * ���Ӳ���
			 */
			//return new Point(8,8,Color.BLACK);
			return null;//�޸ķ�����һ�������
		}	
}

class ComputerStrategy{
	private static int chessNow[][] = new int[18][18];
	/**
	 * ������ת��ˢ��Ϊ���ڼ�������ʽ
	 */
	public void refreshChess(Point[] chesslist)
	{
		for(int i=0;i<18;i++)
		{
			for(int j=0;j<18;j++)
			{
				chessNow[i][j] = 0;//��ʾ��ǰλ��Ϊ��
			}
		}	
		for(Point p:chesslist)
		{
			if(p.getColor()==Color.black)
			{
				chessNow[p.getX()][p.getY()] = 1;//��ǰλ���к���
			}
			else if(p.getColor()==Color.white)
			{
				chessNow[p.getX()][p.getY()] =  2;//��ǰλ���а���
			}
		}
	}
	
	/**
	 * 
	 * @param chesslist
	 * @return ���п������ӵ�λ��
	 * ����ĳһ��ȵ�һЩ��λ�������Χһ���ķ�Χ�������ӣ���Ϊ���λ����һ�����е�λ��
	 */
	public Point[] gen(Point[] chesslist, int deep){
		
		Point[] availablePoint = new Point[18*18];
		int countAvailable = 0;
		refreshChess(chesslist);//ˢ�µ�ǰ��������״̬
		for(Point p:chesslist)
		{
			if(p.getColor()!=Color.black&&p.getColor()!=Color.white)
			{
				for(int i=-deep;i<=deep;i++)
					for(int j=-deep;j<=deep;j++)
					{
						if((i+j>-deep)&&(i+j<deep))
						{
							if(chessNow[i+j][i+j]==0)
							{
								availablePoint[countAvailable++] = new Point(i+j,i+j,Color.blue);
							}
						}
					}
			}
		}	
		return chesslist;
	}
	
}
