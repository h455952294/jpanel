package org.apdplat.extractor.html.demo;

import java.util.ArrayList;
//import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;


//------------------<使用A*算法找出八码图目标状态的实现路径>--------------------
public class Aarithmetic {

	 public static void main(String[] args) {

         int[][] endMap = { { 1, 2, 3 }, { 4, 5, 6 },{ 7, 8, 0 } };//八码图的[目标状态]
    
        Scanner scan = new Scanner(System.in);
         System.out.println("请输入八码图的初始状态（0-8）:");
         int[][] beginMap = new int[3][3];
         for(int i=0; i<beginMap.length; i++)
             for(int j=0; j<beginMap[0].length; j++)
                 beginMap[i][j] = scan.nextInt();
         
         /*System.out.println("请输入八码图的目标状态:");
         int[][] endMap = new int[3][3];
         for(int i=0; i<endMap.length; i++)
             for(int j=0; j<endMap[0].length; j++)
                 endMap[i][j] = scan.nextInt();*/
         scan.close();
/*
         int [] rd_number = randomnumber();
         int a = 0;
         for(int i=0; i<beginMap.length; i++) {
             for (int j = 0; j < beginMap[0].length; j++){
                 beginMap[i][j] = rd_number[a];
                 System.out.println(rd_number[a]);
                 a++;
             }
         }*/

         int m,n;
         Aarithmetic t = new Aarithmetic();
         m= t.Xulie(beginMap)%2;
         n=t.Xulie(endMap)%2;
         
         if(t.reviewSame(beginMap, endMap)){
             System.out.println("输入的八码图初始状态和目标状态相同，无须移动！！");
         }
         else if(m==n){
             t.Search(beginMap, endMap);
         }
         else{
         	System.out.println("此状态没有解");
         }    
     }
    public void paixu(int[] rd_number) {

        int[][] endMap = { { 1, 2, 3 }, { 4, 5, 6 },{ 7, 8, 0 } };//八码图的[目标状态]

        //Scanner scan = new Scanner(System.in);
        System.out.println("请输入八码图的初始状态（0-8）:");
        int[][] beginMap = new int[3][3];

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
            System.out.println("输入的八码图初始状态和目标状态相同，无须移动！！");
        }
        else if(m==n){
            t.Search(beginMap, endMap);
        }
        else{
            System.out.println("此状态没有解");
        }
    }
	 //，，，具体实现过程
    public static JieDian FirstJiedian;
    public static JieDian LastJiedian;
    
    public static ArrayList<JieDian> OpenBiao;
    public static ArrayList<JieDian> CloseBiao; 
    
    public static Stack<JieDian> stack;
    public static Stack<JieDian> myStack = new Stack<>();
    public static int  Pointernum= 0;
    
    class JieDian{
        int[][] State;    //八码图的状态
        int x;          //0的横坐标
        int y;            //0的纵坐标}
        int fatherPointer;    //父指针
        int sonPointer;        //自身的指针
        int g;             //g值，已经移动的步数
        int h;          //h值，与目标状态的数字不同的个数（不包括0）
        int f;          //f值(f = g+h);
    }
    /*产生随机数*/
    
    public static int[] randomnumber()
    {
        Random rd = new Random();
        int n[] = new int[9];
        for(int i = 0 ;i < 9;i ++)
        {
            int temp = rd.nextInt(9)+0;
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
   
    //查看两种状态是否一致，返回布尔值
    public boolean reviewSame(int[][] A, int[][] B){
        for(int i=0; i<A.length; i++)
            for(int j=0; j<A[0].length; j++)
                if(A[i][j] != B[i][j])
                    return false;
        return true;
    }
    
    //找到0的起始位置坐标(x)
    public int ZuobiaoX(int[][] M){
        for(int i=0; i<M.length; i++)
            for(int j=0; j<M[0].length; j++)
                if(M[i][j] == 0)
                    return i;
        return -1;
    }
    
    //找到0的起始位置坐标(y)
    public int ZuobiaoY(int[][] M){
        for(int i=0; i<M.length; i++)
            for(int j=0; j<M[0].length; j++)
                if(M[i][j] == 0)
                    return j;
        return -1;
    }
    

  //计算H值
    public int counth (int[][] A, int[][] B){
        int hnum = 0;
        for(int i=0; i<A.length; i++){
            for(int j=0; j<A[0].length; j++){
                if(A[i][j] != B[i][j] && A[i][j] != 0){
                    hnum++;
                }
            }
        }    
        return hnum;
    }
    
    //检查节点是否已在{open表}中
    public int reviewopen(int[][] checkMap){
        for(int i=0; i<OpenBiao.size(); i++){
            if(reviewSame(OpenBiao.get(i).State, checkMap))
                return i;
        }
        return -1;            
    }
    
    //检查节点是否已在{close表}中
    public int reviewclose(int[][] checkMap){
        for(int i=0; i<CloseBiao.size(); i++){
            if(reviewSame(CloseBiao.get(i).State, checkMap))
                return i;
        }
        return -1;            
    }
    
    
    //加入到表中，并根据f值由小到大排序;
    public void AddtoList(ArrayList<JieDian> eList, JieDian e){
        int eIndex=0;
        for(; eIndex<eList.size() && eList.get(eIndex).f<=e.f ; eIndex++)
            ;
        eList.add(eIndex, e);
    }  
    
   // 打印出状态图;
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
     
    
    //返回相应指针的值；
    public int reviewPointer(ArrayList<JieDian> checkList, int checkID){
        for(int i=0; i<checkList.size(); i++){
            if(checkList.get(i).sonPointer == checkID)
                return i;
        }
        return -1;    
    }
    

    
    //结点的扩展}，即“0”可能的4种移动；
    public void expendPointN(JieDian par){
        if(par.y-1 >= 0){//“0”往[上]移动
            JieDian son = new JieDian();
            son.State = new int[3][3];
            for(int i=0; i<par.State.length; i++)
                for(int j=0; j<par.State[0].length; j++)
                    son.State[i][j] = par.State[i][j];
            son.State[par.x][par.y] = son.State[par.x][par.y-1];
            son.State[par.x][par.y-1] = 0;
            son.x = par.x;
            son.y = par.y-1;
            son.fatherPointer = par.sonPointer;
            son.g = par.g + 1;
            son.h = counth(son.State, LastJiedian.State);
            son.f = son.g + son.h;
            if(par.fatherPointer == -1){//如果是根结点，将自身的指针加入open表
                son.sonPointer = Pointernum++;
                AddtoList(OpenBiao, son);
            }
            else {
                boolean flag = true;
              //判断子节点跟CLOSE表中的已扩展结点是否一样<一样则无须进行下一步的if语句判断>；
                    if(reviewSame(son.State, CloseBiao.get(reviewPointer(CloseBiao, par.fatherPointer)).State))
                       flag = false;
                if(flag){
                    int oIndex = reviewopen(son.State);
                    int cIndex = reviewclose(son.State);
                    if( oIndex > -1){//如果在open表中，根据g值（因为已根据f值将open表中的结点排好序）
                    	                    // 更新OPEN表中的结点； 
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
                    else{//{如果是全新的节点，将子节点加入OPEN表，并建立从子节点到父节点n的指针；
                        son.sonPointer = Pointernum++;
                        AddtoList(OpenBiao, son);        
                    }            
                }
            }
        }
        if(par.x-1 >= 0){//"0"往[左]移动，方法同往[上]移动
            JieDian son = new JieDian();
            son.State = new int[3][3];
            for(int i=0; i<par.State.length; i++)
                for(int j=0; j<par.State[0].length; j++)
                    son.State[i][j] = par.State[i][j];
            son.State[par.x][par.y] = son.State[par.x-1][par.y];
            son.State[par.x-1][par.y] = 0;
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
        if(par.y+1 < 3){//"0"往[下]移动，方法同往[上]移动
            JieDian son = new JieDian();
            son.State = new int[3][3];
            for(int i=0; i<par.State.length; i++)
                for(int j=0; j<par.State[0].length; j++)
                    son.State[i][j] = par.State[i][j];
            son.State[par.x][par.y] = son.State[par.x][par.y+1];
            son.State[par.x][par.y+1] = 0;
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
        if(par.x+1 < 3){//"0"往[右]移动，方法同往[上]移动
            JieDian son = new JieDian();
            son.State = new int[3][3];
            for(int i=0; i<par.State.length; i++)
                for(int j=0; j<par.State[0].length; j++)
                    son.State[i][j] = par.State[i][j];
            son.State[par.x][par.y] = son.State[par.x+1][par.y];
            son.State[par.x+1][par.y] = 0;
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
    
    //路径的搜索过程，
	//包括结点的扩展，open和close表的建立，堆栈及最后的出栈过程；
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
        System.out.println("正在进行路径搜索...");
        while (!OpenBiao.isEmpty()) {
            bestNode = OpenBiao.remove(0);
            CloseBiao.add(bestNode);           
            //直到跟目标一样
            if(reviewSame(bestNode.State, LastJiedian.State)){
                //将目标入栈  
            	
                stack.push(bestNode);
                break;
            }    
            else
                expendPointN(bestNode);
        }      
        if (OpenBiao.isEmpty()) {
        	//open表为空则无解
            System.out.println("查找失败");
        } else {
            int tempParID = stack.peek().fatherPointer;
            int tempParIndex;
            JieDian tempNode;
            //根节点的id=-1
            while(tempParID != -1){
            	//回溯的过程是直接在close表里搜索相应的父节点，直到根节点为止
                tempParIndex = reviewPointer(CloseBiao, tempParID);
                tempNode = CloseBiao.get(tempParIndex);
                stack.push(tempNode);
                tempParID = tempNode.fatherPointer;
            }
            System.out.println("实现目标状态的移动过程为：");/*+new Date().getTime());*/
            int i=0;
            //出栈之后就没有值了,所以先把它放在其他地方
            if(!stack.isEmpty()){
                myStack = (Stack<JieDian>) stack.clone();
            }
            while(!stack.empty()){
                System.out.println("第"+(i++)+"步:");
                printState(stack.pop().State);
                System.out.println();
            }
            
        }
    }
    
     //奇偶序列的判断；
    public int Xulie(int[][] map){
    	int k=0;	
    for(int a=map.length-1;a>=0;a--){
      for(int b=map.length-1;b>=0;b--){   	
        for(int i=0;i<=a;i++){    
        	for(int j =0;j<=b;j++){ 	
        		if(a==i&&b==j)continue;
        		 if(map[i][j]!=0&&map[a][b]!=0&&map[a][b]<map[i][j]){
        			k++;      		
        	}
         }
        }
       }
     }
      return k;  
    }
     
  }
    
    
    