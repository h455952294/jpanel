package org.hyk.demo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


//------------------<ʹ��A*�㷨�ҳ�����ͼĿ��״̬��ʵ��·��>--------------------
public class Aarithmetic {

	 public static void main(String[] args) {

         int[][] endMap = { { 1, 2, 3 }, { 4, 5, 6 },{ 7, 8, 9 } };//����ͼ��[Ŀ��״̬]
    
        //Scanner scan = new Scanner(System.in);
         System.out.println("���������ͼ�ĳ�ʼ״̬��0-8��:");
         int[][] beginMap = new int[3][3];
         /*for(int i=0; i<beginMap.length; i++)
             for(int j=0; j<beginMap[0].length; j++)
                 beginMap[i][j] = scan.nextInt();*/
         
         /*System.out.println("���������ͼ��Ŀ��״̬:");
         int[][] endMap = new int[3][3];
         for(int i=0; i<endMap.length; i++)
             for(int j=0; j<endMap[0].length; j++)
                 endMap[i][j] = scan.nextInt();*/
         //scan.close();

         int [] rd_number = randomnumber();
         int a = 0;
         for(int i=0; i<beginMap.length; i++) {
             for (int j = 0; j < beginMap[0].length; j++){
                 beginMap[i][j] = rd_number[a];
                 System.out.println(rd_number[a]);
                 a++;
             }
         }

         int m,n;
         Aarithmetic t = new Aarithmetic();
         m= t.Xulie(beginMap)%2;
         n=t.Xulie(endMap)%2;
         
         if(t.reviewSame(beginMap, endMap)){
             System.out.println("����İ���ͼ��ʼ״̬��Ŀ��״̬��ͬ�������ƶ�����");
         }
         else if(m==n){
             t.Search(beginMap, endMap);
         }
         else{
         	System.out.println("��״̬û�н�");
         }    
     }
    public void paixu(int[] rd_number) {

        int[][] endMap = { { 1, 2, 3 }, { 4, 5, 6 },{ 7, 8, 9 } };//����ͼ��[Ŀ��״̬]

        //Scanner scan = new Scanner(System.in);
        System.out.println("���������ͼ�ĳ�ʼ״̬��1-9��:");
        int[][] beginMap = new int[3][3];
         /*for(int i=0; i<beginMap.length; i++)
             for(int j=0; j<beginMap[0].length; j++)
                 beginMap[i][j] = scan.nextInt();*/

         /*System.out.println("���������ͼ��Ŀ��״̬:");
         int[][] endMap = new int[3][3];
         for(int i=0; i<endMap.length; i++)
             for(int j=0; j<endMap[0].length; j++)
                 endMap[i][j] = scan.nextInt();*/
        //scan.close();

        //int [] rd_number = randomnumber();
        int a = 0;
        for(int i=0; i<beginMap.length; i++) {
            for (int j = 0; j < beginMap[0].length; j++){
                beginMap[i][j] = rd_number[a];
                System.out.println(rd_number[a]);
                a++;
            }
        }

        int m,n;
        Aarithmetic t = new Aarithmetic();
        m= t.Xulie(beginMap)%2;
        n=t.Xulie(endMap)%2;

        if(t.reviewSame(beginMap, endMap)){
            System.out.println("����İ���ͼ��ʼ״̬��Ŀ��״̬��ͬ�������ƶ�����");
        }
        else if(m==n){
            t.Search(beginMap, endMap);
        }
        else{
            System.out.println("��״̬û�н�");
        }
    }
	 //����������ʵ�ֹ���
    public static JieDian FirstJiedian;
    public static JieDian LastJiedian;
    
    public static ArrayList<JieDian> OpenBiao;
    public static ArrayList<JieDian> CloseBiao; 
    
    public static Stack<JieDian> stack;
    public static Stack<JieDian> myStack = new Stack<>();
    public static int  Pointernum= 0;
    
    class JieDian{
        int[][] State;    //����ͼ��״̬
        int x;          //0�ĺ�����
        int y;            //0��������}
        int fatherPointer;    //��ָ��
        int sonPointer;        //�����ָ��
        int g;             //gֵ���Ѿ��ƶ��Ĳ���
        int h;          //hֵ����Ŀ��״̬�����ֲ�ͬ�ĸ�����������0��
        int f;          //fֵ(f = g+h);
    }
    /*���������*/
    public static int[] randomnumber()
    {
        Random rd = new Random();
        int n[] = new int[9];
        for(int i = 0 ;i < 9;i ++)
        {
            int temp = rd.nextInt(9)+1;
            n[i] = temp;
            for(int j = 0;j < i;j ++)
                if(n[j]==temp)
                {
                    i--;
                    break;
                }
        }
        return n;
    }
   
    //�鿴����״̬�Ƿ�һ�£����ز���ֵ
    public boolean reviewSame(int[][] A, int[][] B){
        for(int i=0; i<A.length; i++)
            for(int j=0; j<A[0].length; j++)
                if(A[i][j] != B[i][j])
                    return false;
        return true;
    }
    
    //�ҵ�0����ʼλ������(x)
    public int ZuobiaoX(int[][] M){
        for(int i=0; i<M.length; i++)
            for(int j=0; j<M[0].length; j++)
                if(M[i][j] == 9)
                    return i;
        return -1;
    }
    
    //�ҵ�0����ʼλ������(y)
    public int ZuobiaoY(int[][] M){
        for(int i=0; i<M.length; i++)
            for(int j=0; j<M[0].length; j++)
                if(M[i][j] == 9)
                    return j;
        return -1;
    }
    

  //����Hֵ
    public int counth (int[][] A, int[][] B){
        int hnum = 0;
        for(int i=0; i<A.length; i++){
            for(int j=0; j<A[0].length; j++){
                if(A[i][j] != B[i][j] && A[i][j] != 9){
                    hnum++;
                }
            }
        }    
        return hnum;
    }
    
    //���ڵ��Ƿ�����{open��}��
    public int reviewopen(int[][] checkMap){
        for(int i=0; i<OpenBiao.size(); i++){
            if(reviewSame(OpenBiao.get(i).State, checkMap))
                return i;
        }
        return -1;            
    }
    
    //���ڵ��Ƿ�����{close��}��
    public int reviewclose(int[][] checkMap){
        for(int i=0; i<CloseBiao.size(); i++){
            if(reviewSame(CloseBiao.get(i).State, checkMap))
                return i;
        }
        return -1;            
    }
    
    
    //���뵽���У�������fֵ��С��������;
    public void AddtoList(ArrayList<JieDian> eList, JieDian e){
        int eIndex=0;
        for(; eIndex<eList.size() && eList.get(eIndex).f<=e.f ; eIndex++)
            ;
        eList.add(eIndex, e);
    }  
    
   // ��ӡ��״̬ͼ;
    public void printState(int[][] M){
        for(int i=0; i<M.length; i++){
            for(int j=0; j<M[0].length; j++){
                /*if(i==0 && j==0){
                    System.out.print("1-1.jpg"+" ");
                }
                if(i==0 && j==1){
                    System.out.print("1-2.jpg"+" ");
                }
                if(i==0 && j==2){
                    System.out.print("1-3.jpg"+" ");
                }
                if(i==0 && j==3){
                    System.out.print("1-4.jpg"+" ");
                }*/
                //System.out.print(i+1+"-"+j+1+".jpg ");
                System.out.print(M[i][j]+" ");
            }
            System.out.println();
        }
    }
     
    
    //������Ӧָ���ֵ��
    public int reviewPointer(ArrayList<JieDian> checkList, int checkID){
        for(int i=0; i<checkList.size(); i++){
            if(checkList.get(i).sonPointer == checkID)
                return i;
        }
        return -1;    
    }
    

    
    //������չ}������0�����ܵ�4���ƶ���
    public void expendPointN(JieDian par){
        if(par.y-1 >= 0){//��0����[��]�ƶ�
            JieDian son = new JieDian();
            son.State = new int[3][3];
            for(int i=0; i<par.State.length; i++)
                for(int j=0; j<par.State[0].length; j++)
                    son.State[i][j] = par.State[i][j];
            son.State[par.x][par.y] = son.State[par.x][par.y-1];
            son.State[par.x][par.y-1] = 9;
            son.x = par.x;
            son.y = par.y-1;
            son.fatherPointer = par.sonPointer;
            son.g = par.g + 1;
            son.h = counth(son.State, LastJiedian.State);
            son.f = son.g + son.h;
            if(par.fatherPointer == -1){//����Ǹ���㣬�������ָ�����open��
                son.sonPointer = Pointernum++;
                AddtoList(OpenBiao, son);
            }
            else {
                boolean flag = true;
              //�ж��ӽڵ��CLOSE���е�����չ����Ƿ�һ��<һ�������������һ����if����ж�>��
                    if(reviewSame(son.State, CloseBiao.get(reviewPointer(CloseBiao, par.fatherPointer)).State))
                       flag = false;
                if(flag){
                    int oIndex = reviewopen(son.State);
                    int cIndex = reviewclose(son.State);
                    if( oIndex > -1){//�����open���У�����gֵ����Ϊ�Ѹ���fֵ��open���еĽ���ź���
                    	                    // ����OPEN���еĽ�㣻 
                        if(son.g < OpenBiao.get(oIndex).g){
                            son.sonPointer = OpenBiao.get(oIndex).sonPointer;
                            OpenBiao.remove(oIndex);
                            AddtoList(OpenBiao, son);
                        }
                    }
                  
                    else if(cIndex > -1){
                        /*if(son.g < CloseBiao.get(cIndex).g){
                            son.sonPointer = CloseBiao.get(cIndex).sonPointer;
                            CloseBiao.remove(cIndex);
                            AddtoList(OpenBiao, son);                      
                        }*/
                    }
                    else{//{�����ȫ�µĽڵ㣬���ӽڵ����OPEN�����������ӽڵ㵽���ڵ�n��ָ�룻
                        son.sonPointer = Pointernum++;
                        AddtoList(OpenBiao, son);        
                    }            
                }
            }
        }
        if(par.x-1 >= 0){//"0"��[��]�ƶ�������ͬ��[��]�ƶ�
            JieDian son = new JieDian();
            son.State = new int[3][3];
            for(int i=0; i<par.State.length; i++)
                for(int j=0; j<par.State[0].length; j++)
                    son.State[i][j] = par.State[i][j];
            son.State[par.x][par.y] = son.State[par.x-1][par.y];
            son.State[par.x-1][par.y] = 9;
            son.x = par.x-1;
            son.y = par.y;
            son.fatherPointer = par.sonPointer;
            son.g = par.g + 1;
            son.h = counth(son.State, LastJiedian.State);
            son.f = son.g + son.h;
            if(par.fatherPointer == -1){
                son.sonPointer = Pointernum++;
                AddtoList(OpenBiao, son);
            }
            else {
                boolean flag = true;
        
                    if(reviewSame(son.State, CloseBiao.get(reviewPointer(CloseBiao, par.fatherPointer)).State))
                        flag = false;
                if(flag){
                    int oIndex = reviewopen(son.State);
                    int cIndex = reviewclose(son.State);
                    if( oIndex > -1){
                        if(son.g < OpenBiao.get(oIndex).g){
                            son.sonPointer = OpenBiao.get(oIndex).sonPointer;
                            OpenBiao.remove(oIndex);
                            AddtoList(OpenBiao, son);
                        }
                    }
                    else if(cIndex > -1){//in CloseBiao
                       /* if(son.g < CloseBiao.get(cIndex).g){
                            son.sonPointer = CloseBiao.get(cIndex).sonPointer;
                            CloseBiao.remove(cIndex);
                            AddtoList(OpenBiao, son);
                        }*/
                    }
                    else{
                        son.sonPointer = Pointernum++;
                        AddtoList(OpenBiao, son);        
                    }            
                }
            }            
        }
        if(par.y+1 < 3){//"0"��[��]�ƶ�������ͬ��[��]�ƶ�
            JieDian son = new JieDian();
            son.State = new int[3][3];
            for(int i=0; i<par.State.length; i++)
                for(int j=0; j<par.State[0].length; j++)
                    son.State[i][j] = par.State[i][j];
            son.State[par.x][par.y] = son.State[par.x][par.y+1];
            son.State[par.x][par.y+1] = 9;
            son.x = par.x;
            son.y = par.y+1;
            son.fatherPointer = par.sonPointer;
            son.g = par.g + 1;
            son.h = counth(son.State, LastJiedian.State);
            son.f = son.g + son.h;
            if(par.fatherPointer == -1){
                son.sonPointer = Pointernum++;
                AddtoList(OpenBiao, son);
            }
            else {
                boolean flag = true;
                    if(reviewSame(son.State, CloseBiao.get(reviewPointer(CloseBiao, par.fatherPointer)).State))
                        flag = false;
                if(flag){
                    int oIndex = reviewopen(son.State);
                    int cIndex = reviewclose(son.State);
                    if( oIndex > -1){//in OpenBiao
                        if(son.g < OpenBiao.get(oIndex).g){
                            son.sonPointer = OpenBiao.get(oIndex).sonPointer;
                            OpenBiao.remove(oIndex);
                            AddtoList(OpenBiao, son);
                        }
                    }
                    else if(cIndex > -1){//in CloseBiao
                       /* if(son.g < CloseBiao.get(cIndex).g){
                            son.sonPointer = CloseBiao.get(cIndex).sonPointer;
                            CloseBiao.remove(cIndex);
                            AddtoList(OpenBiao, son);
                        }*/
                    }
                    else{
                        son.sonPointer = Pointernum++;
                        AddtoList(OpenBiao, son);        
                    }            
                }
            }
        }
        if(par.x+1 < 3){//"0"��[��]�ƶ�������ͬ��[��]�ƶ�
            JieDian son = new JieDian();
            son.State = new int[3][3];
            for(int i=0; i<par.State.length; i++)
                for(int j=0; j<par.State[0].length; j++)
                    son.State[i][j] = par.State[i][j];
            son.State[par.x][par.y] = son.State[par.x+1][par.y];
            son.State[par.x+1][par.y] = 9;
            son.x = par.x+1;
            son.y = par.y;
            son.fatherPointer = par.sonPointer;
            son.g = par.g + 1;
            son.h = counth(son.State, LastJiedian.State);
            son.f = son.g + son.h;
         
            if(par.fatherPointer == -1){
                son.sonPointer = Pointernum++;
                AddtoList(OpenBiao, son);
            }
            else {
                boolean flag = true;
                    if(reviewSame(son.State, CloseBiao.get(reviewPointer(CloseBiao, par.fatherPointer)).State))
                        flag = false;
                if(flag){
                    int oIndex = reviewopen(son.State);
                    int cIndex = reviewclose(son.State);
                    if( oIndex > -1){
                        if(son.g < OpenBiao.get(oIndex).g){
                            son.sonPointer = OpenBiao.get(oIndex).sonPointer;
                            OpenBiao.remove(oIndex);
                            AddtoList(OpenBiao, son);
                        }
                    }
                    else if(cIndex > -1){//in CloseBiao
                       /* if(son.g < CloseBiao.get(cIndex).g){
                            son.sonPointer = CloseBiao.get(cIndex).sonPointer;
                            CloseBiao.remove(cIndex);
                            AddtoList(OpenBiao, son);
                        }*/
                    }
                    else{
                        son.sonPointer = Pointernum++;
                        AddtoList(OpenBiao, son);        
                    }            
                }
            }
        }
    }
    
    //·�����������̣�
	//����������չ��open��close��Ľ�������ջ�����ĳ�ջ���̣�
    @SuppressWarnings("unchecked")
	public void Search(int[][] beginM, int[][] endM){
    	    
        OpenBiao = new ArrayList<JieDian>();
        CloseBiao = new ArrayList<JieDian>();
        stack = new Stack<JieDian>();
     
        FirstJiedian = new JieDian();
        FirstJiedian.State = beginM;
        FirstJiedian.x = ZuobiaoX(beginM);
        FirstJiedian.y = ZuobiaoY(beginM);
        FirstJiedian.fatherPointer = -1;
        FirstJiedian.sonPointer = Pointernum++;
        FirstJiedian.g = 0;
        FirstJiedian.h = counth(beginM, endM);
        FirstJiedian.f = FirstJiedian.g + FirstJiedian.h;
    
        LastJiedian = new JieDian();
        LastJiedian.State = endM;
        LastJiedian.x = ZuobiaoX(endM);
        LastJiedian.y = ZuobiaoY(endM);
        LastJiedian.fatherPointer = -1;
        
        AddtoList(OpenBiao, FirstJiedian);
        JieDian bestNode;
        System.out.println("���ڽ���·������...");
        while (!OpenBiao.isEmpty()) {
            bestNode = OpenBiao.remove(0);
            CloseBiao.add(bestNode);           
            //ֱ����Ŀ��һ��
            if(reviewSame(bestNode.State, LastJiedian.State)){
                //��Ŀ����ջ  
            	
                stack.push(bestNode);
                break;
            }    
            else
                expendPointN(bestNode);
        }      
        if (OpenBiao.isEmpty()) {
        	//open��Ϊ�����޽�
            System.out.println("����ʧ��");
        } else {
            int tempParID = stack.peek().fatherPointer;
            int tempParIndex;
            JieDian tempNode;
            //���ڵ��id=-1
            while(tempParID != -1){
            	//���ݵĹ�����ֱ����close����������Ӧ�ĸ��ڵ㣬ֱ�����ڵ�Ϊֹ
                tempParIndex = reviewPointer(CloseBiao, tempParID);
                tempNode = CloseBiao.get(tempParIndex);
                stack.push(tempNode);
                tempParID = tempNode.fatherPointer;
            }

            int i=0;
            //��ջ֮���û��ֵ��,�����Ȱ������������ط�
            if(!stack.isEmpty()){
                myStack = (Stack<JieDian>) stack.clone();
            }
            while(!stack.empty()){
                System.out.println("��"+(i++)+"��:");
                printState(stack.pop().State);
                System.out.println();
            }
            
        }
    }
    
     //��ż���е��жϣ�
    public int Xulie(int[][] map) {
		int k = 0;
		for (int m = map.length - 1; m >= 0; m--) {
			for (int n = map.length - 1; n >= 0; n--) {
				for (int i = 0; i < map.length; i++) {
					if (i <= m) {
						for (int j = 0; j < map.length; j++) {
							if (m == i && n == j)
								break;
							if (map[i][j] != 9 && map[m][n] != 9 && map[m][n] < map[i][j]) {
								k++;
							}
						}
					}
				}
			}
		}
		return k;
	}
     
  }
    
    
    