package hyk;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

class J_JPanel extends JPanel
{  
  private static final long serialVersionUID = 1L;
  Image m_image;

  public J_JPanel()throws IOException
  {
      File f = new File("D:\\tupian\\target\\test.jpg");
      m_image = ImageIO.read(f);
  }

  public void paintComponent(Graphics g)
  {      
    g.drawImage(m_image, 0, 0, 270, 270, this);
  } 
}



class Puzzle extends JFrame 
{

  private static final long serialVersionUID = 1L;
  int i,j;
  
  int gx[] = {0,1,2};
  int gy[] = {0,1,2};
  GridBagConstraints gc = new GridBagConstraints();
  
  Container c = getContentPane();
  JButton b[] = new JButton[10];
  ImageIcon ic[]= new ImageIcon[9];
  boolean isRunning = false;

    int location[][] = {{17,13},{107,13},{197,13},{17,103},{107,103},{197,103},{17,193},{107,193}
    ,{197,193}};

  public Puzzle() throws IOException
  {
        
    super("������Ӧ��");
    String pic_name[] = new String[15];
     for(j = 0;j < 9;j ++)
      {
          //G:\game\target\map_0_14
       pic_name[j] = String.valueOf("D:\\tupian\\target\\one\\map_"+i+"_"+j+".jpg");
       ic[j] = new ImageIcon(pic_name[j]);
      }
 
    JMenuBar mBar = new JMenuBar();
    setJMenuBar(mBar);
      
    JMenu[]m = {new JMenu("ȫͼ��ʾ(H)"),new JMenu("�Զ�(AUTO)")};
    char mC[][] = {{'H','A'},{'E'},{'A'}};
    JMenuItem mItem[] = {new JMenuItem("�鿴��ͼ(E)"),new JMenuItem("�Զ�ƴͼ(A)")};
    for(i = 0;i < 2;i ++)
     {
      mBar.add(m[i]);
      m[i].setMnemonic(mC[0][i]);
      for(j = 0;j <1;j ++)
      { 
        m[i].add(mItem[i]);
        mItem[i].setMnemonic(mC[i+1][0]);
          mItem[i].setAccelerator(KeyStroke.getKeyStroke("ctrl" + mC[i+1][0]));
          mItem[i].addActionListener(new ActionListener() {

              public void actionPerformed(ActionEvent e) {
                  JMenuItem mItem = (JMenuItem) e.getSource();
                  if (mItem.getText().equalsIgnoreCase("�鿴��ͼ(E)")) {

                      JFrame jj = new JFrame("��ͼ");
                      jj.setSize(270, 270);
                      jj.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 3 - 270,
                              Toolkit.getDefaultToolkit().getScreenSize().height / 4);
                      jj.setVisible(true);
                      Container c1 = jj.getContentPane();

                      try {
                          c1.add(new J_JPanel(), BorderLayout.CENTER);
                      } catch (IOException e1) {
                          //
                          e1.printStackTrace();
                      }
                  }else if (mItem.getText().equals("�Զ�ƴͼ(A)")) {
                          if(isRunning){
                              return;
                          }
                          //����,�������߳̽�����
                          isRunning = true;
                          //��ȡ��ʼ��״̬
                          int[] rd_number = randomnumber();
                         // int[] rd_number = new int []{7,9,1,5,8,4,6,3,2};

                          for (int i = 0; i < rd_number.length; i++) {
                        	    if(rd_number[i]==9){
                            	    int location_x  = location[i][0];
                                    int location_y  = location[i][1];
                                    b[rd_number[i]].setIcon(null);
                                    b[rd_number[i]].setLocation(location_x, location_y);       
                                    continue;
                        	    }
                        	    int location_x  = location[i][0];
                                int location_y  = location[i][1];
                             //   b[rd_number[i]].setIcon(ic[rd_number[i]-1]);
                                b[rd_number[i]].setLocation(location_x, location_y);                              
                            }
                          //����һ���߳�ȥ����
                          new new_thread(rd_number).start();
                  }
              }

          });
      }}
        
      
      GridBagLayout gr = new GridBagLayout();
      c.setLayout(gr);

      for(i = 0;i < 3;i ++)
       {
        for(j = 1;j < 4;j ++)
         {
        	 if(i*3+j==9){
        		 gc.gridx = gx[j-1];
                 gc.gridy = gy[i];
                 gc.fill = GridBagConstraints.NONE;
                 b[i*3+j]=new JButton();
                 b[i*3+j].setPreferredSize(new Dimension(90,90));
                 gr.setConstraints(b[i*3+j], gc);
                 c.add(b[i*3+j]);     
                 continue;
     	    }
          gc.gridx = gx[j-1];
          gc.gridy = gy[i];
          gc.fill = GridBagConstraints.NONE;
          b[i*3+j]=new JButton(ic[i*3+j-1]);
         b[i*3+j].setPreferredSize(new Dimension(90,90));
         gr.setConstraints(b[i*3+j], gc);
          c.add(b[i*3+j]);
         }

       }
    

  }

  
  
    public class new_thread extends Thread{
        int [] rd_number;
        public new_thread(int [] rd_number) {
            this.rd_number = rd_number;
        }
        @Override
        public void run() {
            try{
            	
                //����,�������߳̽�����
                isRunning = true;
                Aarithmetic a = new Aarithmetic();
                //����
                a.paixu(rd_number);
                //��ȡ�����Ĳ���
                Stack<Aarithmetic.JieDian> jieDians = Aarithmetic.myStack;
                if(null != jieDians && jieDians.isEmpty()){
                    return;
                }
                if(null != Aarithmetic.OpenBiao && Aarithmetic.OpenBiao.isEmpty()){
                    return;
                }

                int aa = 0;
                int [] [] old = new int [][]{};
                while (null != jieDians && !jieDians.empty()) {
                    aa++;
                    System.out.println("-----����"+aa+"����ʼ------");
                    //ֻ����һ��
                    if(aa == 1){
                        old = jieDians.pop().State;                     //��ά����M�洢����0-8��Щ����
                        continue;
                    }
                    int[][] M = jieDians.pop().State;   //�ڶ������Ǹı��
                    java.util.List<Integer> flags = new ArrayList<Integer>();
                    for (int i = 0; i < M.length; i++) {
                        for (int j = 0; j < M[i].length; j++) {
                            int p = M[i][j];
                            System.out.print(p+",");

                            if(old[i][j] != M[i][j]){
                                flags.add(old[i][j]);
                            }
                        }
                    }
                    Point p1 = b[flags.get(0)].getLocation();
                    b[flags.get(0)].setLocation(b[flags.get(1)].getLocation());
                    b[flags.get(1)].setLocation(p1);
                    //ÿ��������ɶ���ֵ���old
                    old = M;
                    System.out.println("-----����"+aa+"������------");
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //������˳�
                isRunning = false;
            }

        }
    }
    
    
/*���������*/
  public int[] randomnumber()
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


  
  public static void main(String args[]) throws IOException
  {
    Puzzle app = new Puzzle();
    app.setDefaultCloseOperation(EXIT_ON_CLOSE);
    app.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/3, Toolkit.getDefaultToolkit().getScreenSize().height/4);
    app.setSize(310,350);
    app.setVisible(true);
    app.setResizable(false); 
  }
}
