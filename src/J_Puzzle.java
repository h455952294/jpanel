package org.apdplat.extractor.html.demo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

class J_JPanel extends JPanel
{  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  Image m_image;
  int showpicture=0;
  @SuppressWarnings("static-access")
  public J_JPanel()throws IOException
  {      J_Puzzle a = new J_Puzzle();
    if(a.changetime==-1)
      showpicture = 1;
      File f = new File("D:\\tupian\\target\\test.jpg");
      m_image = ImageIO.read(f);
  }
  public void paintComponent(Graphics g)
  {      
    g.drawImage(m_image, 0, 0, 270, 270, this);
  } 
}

class J_Puzzle extends JFrame implements ActionListener
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  int i,j;
  static int changetime = 0;
  Container c = getContentPane();
  JButton b[] = new JButton[16];
  ImageIcon ic[][] = new ImageIcon[2][15];
  boolean isRunning = false;

    int location[][] = {{17,13},{17,103},{17,193},{107,13},{107,103},{107,193}
            ,{197,13},{197,103},{197,193}};

    //按钮的布局
    int gx[] = {0,1,2};
    int gy[] = {0,1,2};
    int k1;
    Dimension d = new Dimension(90,90);
    String s_number;
    GridBagConstraints gc = new GridBagConstraints();

  public J_Puzzle() throws IOException
  {
        
    super("拼图游戏(八数码应用)");
      
    String pic_name[] = new String[15];
    for(i = 0;i < 2;i ++)
     for(j = 0;j < 15;j ++)
      {
          //G:\game\target\map_0_14
       pic_name[j] = String.valueOf("D:\\tupian\\target\\one\\map_"+i+"_"+j+".jpg");
       ic[i][j] = new ImageIcon(pic_name[j]);
      }
 
    JMenuBar mBar = new JMenuBar();
    setJMenuBar(mBar);
      
    int k = 0;
     
    JMenu[]m = {new JMenu("菜单(M)"),new JMenu("帮助(H)"),new JMenu("自动(AUTO)")};
    char mC[][] = {{'M','H','A'},{'S','X','C','Z'},{'E','T'},{'A'}};
    JMenuItem mItem[][] = {{new JMenuItem("开始(S)"),new JMenuItem("重置(X)"),new JMenuItem("背景更换(C)"),new JMenuItem("退出(Z)")},{new JMenuItem("查看样图(E)")},{new JMenuItem("自动拼图(A)")}};
    for(i = 0;i < 3;i ++)
     {
      mBar.add(m[i]);
      m[i].setMnemonic(mC[0][i]);
      if(i==0)k = 0;
      else if(i==2)k=1;
      else k = 2;
      for(j = 0;j < 4-i-k;j ++)
      { 
        m[i].add(mItem[i][j]);
        mItem[i][j].setMnemonic(mC[i + 1][j]);
          mItem[i][j].setAccelerator(KeyStroke.getKeyStroke("ctrl" + mC[i + 1][j]));
          mItem[i][j].addActionListener(new ActionListener() {

              public void actionPerformed(ActionEvent e) {
                  JMenuItem mItem = (JMenuItem) e.getSource();
                  if (mItem.getText().equalsIgnoreCase("重置(X)") || mItem.getText().equalsIgnoreCase("开始(S)")) {

                      int rd_number[] = new int[9];
                      
                      rd_number = randomnumber();
                      for (i = 1; i < 9; i++)
                          b[i].setLocation(location[rd_number[i - 1]][0], location[rd_number[i - 1]][1]);
                  } else if (mItem.getText().equalsIgnoreCase("背景更换(C)")) {

                      changetime++;
                      for (i = 0; i < 15; i++) {
                          b[i + 1].setIcon(null);
                          b[i + 1].setIcon(ic[changetime][i]);
                      }
                      if (changetime == 1)
                          changetime = -1;
                  } else if (mItem.getText().equalsIgnoreCase("退出(Z)")) {
                      int a = JOptionPane.showConfirmDialog(null, "您确定退出游戏？");
                      if (a == 0)
                          System.exit(0);
                  } else if (mItem.getText().equalsIgnoreCase("查看样图(E)")) {

                      JFrame jj = new JFrame("样图");
                      jj.setSize(270, 270);
                      jj.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 3-270 ,
                              Toolkit.getDefaultToolkit().getScreenSize().height / 4);
                      jj.setVisible(true);
                      Container c1 = jj.getContentPane();
                     

                      try {
                          c1.add(new J_JPanel(), BorderLayout.CENTER);
                      } catch (IOException e1) {
                          //
                          e1.printStackTrace();
                      }
                  }else if (mItem.getText().equals("自动拼图(A)")) {
                          if(isRunning){
                              return;
                          }
                          //开启,让其他线程进不来
                          isRunning = true;
                          //获取初始化状态
                          int[] rd_number = new int[9];
                          rd_number= randomnumber();

//===============================================================
                          for (int i = 0; i < rd_number.length; i++) {
                            if (rd_number[i] == 0) { //一开姐加载界面的时候,0位置是null,所以要初始化
                                  b[rd_number[i] + 1] = new JButton(rd_number[i]+"",ic[0][rd_number[i]]);
                                  int location_x  = location[i] [0];
                                  int location_y  = location[i] [1];
                                  b[rd_number[i] + 1].setLocation(location_x, location_y);
                                  continue;
                              }
                              int location_x  = location[i] [0];
                              int location_y  = location[i] [1];
                              b[rd_number[i] + 1].setLocation(location_x, location_y);
                              /*b[rd_number[i] + 1].setIcon(ic[0][rd_number[i]]);*/
                          }

                          //启动一条线程去排序
                          new new_thread(rd_number).start();

                  }
                 
              }

          });
      }
        
    }
    m[0].insertSeparator(1);m[1].insertSeparator(1);

    GridBagLayout gr = new GridBagLayout();
    c.setLayout(gr);

     //一开始生成的时候0是无效的
     int k2=0;
     for(i = 3;i > 0;i --)
     {
      if(i==1){k2=1;}
      else {k2=0;}
      for(j = 3;j > k2;j --)
       {
        gc.gridx = gx[j-1];
        gc.gridy = gy[i-1];
        gc.fill = GridBagConstraints.NONE;
        s_number = String.valueOf(j + (i - 1) * 3);
        b[j+(i-1)*3] = new JButton(s_number,ic[0][j+(i-1)*3-1]);
        b[j+(i-1)*3].setPreferredSize(d);
        b[j+(i-1)*3].setFont(new Font("宋体", Font.PLAIN, 0));
        gr.setConstraints(b[j + (i - 1) * 3], gc);
        c.add(b[j + (i - 1) * 3]);

       }
     }
     for(i = 9;i > 1;i --)
      b[i].addActionListener(this);
  }

    public void actionPerformed(ActionEvent e)
   {
      int j;
      JButton b = (JButton)e.getSource();
      Point p = b.getLocation();
       Point p1 = null;
        for(j = -1;j < 2;j ++)
        {           
          if(p.y+j*90>193||p.y+j*90<11)
            continue;
          else
          { 
            Component a = c.getComponentAt(p.x, p.y+j*90);
            if(a.getHeight()!=90)
              p1 = new Point(p.x,p.y+j*90);
          }   
        }
        for(j = -1;j < 2;j ++)
        {   
          if(p.x+j*90>197||p.x+j*90<17)
            continue;
                    
          else
          {
            Component a = c.getComponentAt(p.x+j*90, p.y);
            if(a.getHeight()!=90)        
               p1 = new Point(p.x+j*90,p.y);
          }        
        }
        if(p1!=null)
        b.setLocation(p1.x, p1.y);
       if(check()==true)
         JOptionPane.showMessageDialog(null, "恭喜您成功了");
   }

    public class new_thread extends Thread{
        int [] rd_number;
        public new_thread(int [] rd_number) {
            this.rd_number = rd_number;
        }
        @Override
        public void run() {
            try{
                Aarithmetic a = new Aarithmetic();
                //排序
                a.paixu(rd_number);
                //获取排序后的步骤
                Stack<Aarithmetic.JieDian> jieDians = Aarithmetic.myStack;
                if(null != jieDians && jieDians.isEmpty()){
                    return;
                }
                if(null != Aarithmetic.OpenBiao && Aarithmetic.OpenBiao.isEmpty()){
                    return;
                }
                //按步骤显示
            /*while (null != jieDians && !jieDians.empty()) {
                int[][] M = jieDians.pop().State;//二维数组M存储的是0-8这些数字
                for (int i = 0; i < M.length; i++) {
                    for (int j = 0; j < M[i].length; j++) {
                        //设置位置
                        b[M[i][j]+1].setLocation(location[M[i][j]][0], location[M[i][j]][1]);
                        //设置图标
                        b[rd_number[i] + 1].setDisabledIcon(ic[0][M[i][j]]);

                    }
                }
            }*/
                int aa = 0;
                int [] [] old = new int [][]{};
                while (null != jieDians && !jieDians.empty()) {
                    aa++;
                    System.out.println("-----排序"+aa+"步开始------");
                    //只排序一步
                    if(aa == 1){
                        old = jieDians.pop().State;                     //二维数组M存储的是0-8这些数字
                        continue;
                    }
                    int[][] M = jieDians.pop().State;   //第二步才是改变的
                    java.util.List<Integer> flags = new ArrayList<Integer>();
                    for (int i = 0; i < M.length; i++) {
                        for (int j = 0; j < M[i].length; j++) {
                            int p = M[i][j];
                            System.out.print(p+",");
                        /*//设置位置
                        b[p + 1].setLocation(location[p][0], location[p][1]);
                        //设置图标
                        b[p + 1].setDisabledIcon(ic[0][p]);*/
                            //只是设置位置变化的按钮
                            //7,0,1,5,8,4,6,3,2 变化到 7,1,0,5,8,4,6,3,2 那些位置变化了,就只是变化他们的位置
                            if(old[i][j] != M[i][j]){
                                flags.add(old[i][j]);
                            }
                        }
                    }
                    Point p1 = b[flags.get(0)+1].getLocation();
                    b[flags.get(0) +1].setLocation(b[flags.get(1)+1].getLocation());
                    b[flags.get(1)+1].setLocation(p1);
                    //每次自行完成都把值变成old
                    old = M;
                    System.out.println("-----排序"+aa+"步结束------");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //最后再退出
                isRunning = false;
            }

        }
    }
/*产生随机数*/
  public int[] randomnumber()
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

  /*判断是否排序成功*/
  public boolean check()
  {
     Point location[] = new Point[9];
     boolean bo = false;
     int count=0;
     for(int i = 0;i < 3;i ++)
       for(int j = 0;j < 3;j ++)
        location[i*3+j] = new Point(17+j*90, 13+i*90);
      
     for(int i = 0;i < 8;i ++)
     {
       if(b[i+1].getLocation().x==location[i].x&&b[i+1].getLocation().y==location[i].y)
         count++;
       if(count==9)
         bo=true;
     }    
    return bo; 
  }
    
  public static void main(String args[]) throws IOException
  {
    J_Puzzle app = new J_Puzzle();
    app.setDefaultCloseOperation(EXIT_ON_CLOSE);
    app.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/3, Toolkit.getDefaultToolkit().getScreenSize().height/4);
    app.setSize(310,350);
    app.setVisible(true);
    app.setResizable(false); 
  }
}
