package Strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import BaseChess.Point;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Point[] chesslistB = new Point[18*18];
		Point[] chesslistW;
		Point NextStep;
		
		int stepx,stepy;
		// TODO Auto-generated method stub
		ChessStrategy f=new ChessStrategy();//���������  
	    f.setVisible(true);//��ʾ�����  
	
	    
	    //System.in�����׼���룬���Ǽ�������
       // Scanner sc = new Scanner(System.in);sc.hasNext()
       /**
        *  
        */
        
       
        while(true){
          /**
           * ���Ⱥ���������  
           */
        	NextStep=f.BlackNextStep(chesslistB);
        	stepx=NextStep.getX();
        	stepy=NextStep.getY();
        	
	     chesslistB=f.getChessBoard().BlackAddChess(stepx,stepy);
	     
	     /**
	      * ������
	      */
	     NextStep=f.WhiteNextStep(chesslistB);
	     	stepx=NextStep.getX();
	     	stepy=NextStep.getY();
	     chesslistW=f.getChessBoard().WhiteAddChess(stepx,stepy);
	    
	    }

	}



}
