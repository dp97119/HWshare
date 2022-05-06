package tw.optest.tutor;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Puzzle extends JFrame{
	private JMenuBar menubar;
	private JMenu files, quit;
	private JMenuItem lookall, restart;
	private JLabel lookImg;
	private JButton[] buttons;
	private JButton cell1, cell2;  //空按鈕，暫存用
	private BufferedImage img;
	private BufferedImage[][] imgs;
	private JPanel all;
	private int imgW, imgH;
	private int rows = 3, cols = 3;
	private int chunks = rows*cols;
	private int chunksW,chunksH;
	private int num;
	private int x0,y0,x1,y1;  //空xy軸，暫存用
	private int k=0;
	private int result = 0 ;
	private LinkedList<Integer> list;
	
	
	public Puzzle(){
		super("拼圖");
		all = new JPanel();
		all.setLayout(null);
		setContentPane(all);
		//新增MenuBar並設定
		menubar = new JMenuBar();
		files = new JMenu("檔案"); quit = new JMenu("離開");
		lookall = new JMenuItem("查看完整圖"); restart = new JMenuItem("重新開始");
		menubar.add(files); menubar.add(quit);
		files.add(lookall); files.add(restart);
		
		//各Menu執行動作
		lookall.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				look();				
			}
		});
		
		restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rmd();
				
			}
		});
		
		quit.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				System.exit(0);
				
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				
			}
		});
		
		//抓取圖片以及設定版面
		pic_count();
		setVisible(true);
		setSize(imgW+15, imgH+61);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setJMenuBar(menubar);
		
		//呼叫裁切圖片
		cut();
		buttons = new JButton[chunks];  //新增所有按鈕
		
		//按鈕逐一放圖片
		num = 0 ;
		for (int i = 0;i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				buttons[num] = new JButton();
				buttons[num].setBounds(chunksW*i, chunksH*j, chunksW, chunksH);
				all.add(buttons[num]);
				buttons[num].setIcon(new ImageIcon(imgs[i][j]));
				num++;
			}
		}

		rmd();  //打亂拼圖
		
		Listener listener = new Listener();   //增加點擊按鈕動作
		for (int i = 0 ;i<chunks; i++) {
			buttons[i].addMouseListener(listener);
		}
		
		
	}
	//更換拼圖
	//1.新增兩個假按鈕
	//2.各自儲存xy軸
	//3.再交換xy軸
	public class Listener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			for (int j = 0 ;j<chunks;j++) {
				if((JButton)e.getSource() == buttons[j]) {
					if (k==0) {
						cell1 = new JButton();
						cell1 = buttons[j];
						x0 = cell1.getX();
						y0 = cell1.getY();
						k=1;
						System.out.println(x0+" "+y0 +" "+j);  //測試點到按鈕位置及編號
					}else {
						cell2 = new JButton();
						cell2 = buttons[j];
						x1 = cell2.getX();
						y1 = cell2.getY();
						cell1.setLocation(x1,y1);
						cell2.setLocation(x0, y0);
						k=0;
						System.out.println(x1+" "+y1+" "+j);  //測試點到按鈕位置及編號
					}
					
				}
				
			}
			//判斷贏了之後須顯示的視窗
			if (iswin()) {
				JFrame msg = new JFrame();   //新增視窗元件
				Object[] options = {"是","離開"};  //設定視窗選項物件
				result = JOptionPane.showOptionDialog(msg,"恭喜你贏了！！！是否重新開始？","訊息", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
				if (result == 0) {
					rmd();    //如果點選是，重新開始
				}else {
					System.exit(0);    //點選離開則關閉程式
				}
			}
			

		}
	}

	//載入圖片
	//抓取圖片寬高
	//抓取拼圖大小
	public void pic_count() {
		try {
			img = ImageIO.read(new File("dir1/20190525_101754.JPG"));
		} catch (IOException e1) {
			System.out.println(e1.toString());
		}
		
		imgW = img.getWidth(); imgH = img.getHeight();
		chunksW = imgW/rows;
		chunksH = imgH/cols;

	}
	
	//裁切圖片
	public void cut() {
		pic_count();
		imgs = new BufferedImage[rows][cols];   //新增圖片切開後小圖個數
		for (int i = 0;i < rows; i++) {         
			for (int j = 0; j< cols; j++) {
				imgs[i][j] = new BufferedImage(chunksW, chunksH, BufferedImage.TYPE_INT_BGR);
				Graphics2D gr = imgs[i][j].createGraphics();
				gr.drawImage(img, 0, 0, chunksW, chunksH, chunksW*i, chunksH*j, chunksW*i+chunksW, chunksH*j+chunksH, null);
				gr.dispose();
				//匯出裁切圖片結果
//				try {
//					ImageIO.write(imgs[i][j],"jpg", new FileOutputStream("dir1/"+i+j+".jpg"));
//				} catch (IOException e1) {
//					System.out.println("XX");
//				}
			}
		}
		
	}
	
	public void rmd() {    //打亂拼圖
		list = new LinkedList<>();
        for(int i=0;i<chunks; i++) list.add(i);
        Collections.shuffle(list);
        
        int n = 0;
		for (Integer v : list) {
			int x = buttons[v].getX();   //先將打亂的按鈕原本位置xy軸，存放於變數x,y
			int y = buttons[v].getY();
			buttons[v].setLocation(buttons[n].getX(), buttons[n].getY()); //將原本打亂按鈕的原xy軸，改為新xy軸
			buttons[n].setLocation(x, y);   //然後將新按鈕位置改抓打亂的位置
			all.add(buttons[n]);   //逐一放已打亂的按鈕
			n++;
		}
	}
	
	//新增查看完整圖視窗
	public void look() {
		JFrame fimg = new JFrame();
		JPanel pimg = new JPanel();
		pic_count();   //抓取圖
		lookImg = new JLabel(new ImageIcon(img)); //新增label抓圖
		lookImg.setBounds(0, 0, imgW, imgH);  //設定label位置
		
		//視窗跟label排版
		fimg.setVisible(true);
		fimg.setLayout(null);
		fimg.setResizable(false);
		fimg.setSize(imgW+20, imgH+45);
		fimg.setContentPane(pimg);
		pimg.add(lookImg);
	}
	
	//判斷所有按鈕xy位置是否正確
	public boolean iswin() {
		for (int i = 0 ;i<chunks;i++) {    
			int x = buttons[i].getX();    //抓取各按鈕xy軸
			int y = buttons[i].getY();
			if (((x/(imgW/rows)*cols) + (y/(imgH/cols))) != i ) {    //用xy軸回推第幾個按鈕，再判斷是否與該按鈕流水號相符
				return false;
			}
		}
		return true;
	}
	public static void main(String args[]) {
		new Puzzle();
	}
}

