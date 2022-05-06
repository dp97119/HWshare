package tw.optest.tutor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class RPS extends JFrame{
	private JButton rock, paper, scissors;  //宣告按鈕
	private JMenu mreset, mquit;   //宣告清單
	private JPanel all;    //宣告容器
	private JLabel score, luser, lcom,com_answer, user_answer, user_score, com_score, com_pic;  //宣告標籤
	private int comscr , userscr;   //宣告電腦及玩家分數數字
	private int ua, ca, result;     //宣告電腦及玩家猜拳回答代表數字，以及後續提示視窗回應
	private BufferedImage comImg;   //宣告暫存圖片
	
	public RPS() {
		super("猜拳遊戲");   //視窗標題
		JMenuBar mb = new JMenuBar();    //宣告並新增上方清單列物件
		all = new JPanel();    //新增容器物件
		setContentPane(all);   //設定內容版面
		Font font = new Font("微軟正黑體", Font.PLAIN, 16);   //宣告並新增文字樣式大小物件
		
		//新增上方清單項目
		mreset = new JMenu("重新開始");   
		mquit = new JMenu("離開");
		
		//新增各項目標籤並預設文字
		score = new JLabel("分數"); luser = new JLabel("玩家："); lcom = new JLabel("電腦：");
		com_score = new JLabel("0"); user_score = new JLabel("0");
		com_answer = new JLabel("電腦："); user_answer = new JLabel("玩家：");
		
		//讀取圖片
		try {
			comImg = ImageIO.read(new File("dir1/computer.gif"));
			
		} catch (IOException e1) {
			System.out.println(e1.toString());
		}
		//新增標籤並將圖片放入
		com_pic = new JLabel(new ImageIcon(comImg));

		//新增按鈕物件並設定顯示文字
		rock = new JButton("石頭");
		paper = new JButton("布");
		scissors = new JButton("剪刀");
		
		//將清單項目放入上方清單列
		mb.add(mreset); mb.add(mquit);
		
		//設定各物件位置以及大小
		user_answer.setBounds(350, 350, 80, 30);
		com_answer.setBounds(10, 10, 80, 30);
		
		com_pic.setBounds(20, 40, comImg.getWidth(), comImg.getHeight());
		
		score.setBounds(380, 5, 50, 30);
		
		lcom.setBounds(350, 30, 50, 30);
		luser.setBounds(350, 70, 50, 30);
		com_score.setBounds(400, 30, 50, 30);
		user_score.setBounds(400, 70, 50, 30);
		
		scissors.setBounds(50, 325, 80, 40);
		rock.setBounds(150, 325, 80, 40);
		paper.setBounds(250, 325, 80, 40);
		
		//設定清單點擊時的動作
		mreset.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				newGame();   //被點擊後重新開始	
			}
			//不選擇及取消不會用到但刪除會出錯(?)先留著
			@Override
			public void menuDeselected(MenuEvent e) {
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				
			}
		});
		
		mquit.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				System.exit(0);   //被點擊後關閉程式	
			}
			//不選擇及取消不會用到但刪除會出錯(?)先留著
			@Override
			public void menuDeselected(MenuEvent e) {}
			
			@Override
			public void menuCanceled(MenuEvent e) {}
		});
		
		//設定按鈕點擊時的動作，每點擊一次就會顯示玩家猜拳回答，並設定參數數值以利後續比較
		//同時觸發電腦亂數產生回答，以及分數計算
		rock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				user_answer.setText("玩家：石頭");
				ua = 0;
				comans();
				counts();
			}
		});
		
		paper.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				user_answer.setText("玩家：布");
				ua = 1;
				comans();
				counts();
			}
		});
		
		scissors.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				user_answer.setText("玩家：剪刀");
				ua = 2;
				comans();
				counts();
			}
		});
		
		setJMenuBar(mb);   //放入上方清單列
		setSize(480, 480); //設定視窗大小
		setVisible(true);  //允許顯示視窗
		setResizable(false);  //不允許調整視窗
		all.setLayout(null);  //JPanel不使用任何版面配置方式
		setDefaultCloseOperation(EXIT_ON_CLOSE);  //預設關閉的操作動作
		//JPanel放入所有標籤及按鈕
		all.add(com_answer); all.add(user_answer); 
		all.add(com_pic);
		all.add(score); all.add(lcom); all.add(luser); all.add(com_score); all.add(user_score); 
		all.add(scissors);all.add(paper);all.add(rock);
		//設定所有標籤及按鈕文字樣式及大小
		rock.setFont(font); paper.setFont(font); scissors.setFont(font);
		score.setFont(font); luser.setFont(font); lcom.setFont(font); com_answer.setFont(font);
		user_answer.setFont(font); user_score.setFont(font); com_score.setFont(font);
	
	}
	
	//設定亂數以呈現電腦猜拳回答，若0為石頭、1為布、2為剪刀
	private void comans() {
		ca = (int)(Math.random()*3);
		
		switch (ca) {
			case 0:  com_answer.setText("電腦：石頭"); break;
			case 1:  com_answer.setText("電腦：布"); break;
			case 2:  com_answer.setText("電腦：剪刀"); break;
		}
		
	}

	//以電腦回答角度，比對輸贏計算分數
	private void counts() {
		switch (ca) {
		case 0:
			if (ua == 1) {
				userscr++;    //玩家分數加分
				user_score.setText(String.valueOf(userscr));   //將分數顯示於標籤
				message();   //需判斷分數是否滿10分，再跳出提示視窗
			}else if (ua == 2) {
				comscr++;    //電腦分數加分
				com_score.setText(String.valueOf(comscr));   //將分數顯示於標籤
				message();   //需判斷分數是否滿10分，再跳出提示視窗
			}
			break;
		case 1:
			if (ua == 2) {
				userscr++;    //玩家分數加分
				user_score.setText(String.valueOf(userscr));   //將分數顯示於標籤
				message();   //需判斷分數是否滿10分，再跳出提示視窗
			}else if (ua == 0) {
				comscr++;    //電腦分數加分
				com_score.setText(String.valueOf(comscr));   //將分數顯示於標籤
				message();   //需判斷分數是否滿10分，再跳出提示視窗
			}
			break;
		case 2:
			if (ua == 0) {
				userscr++;    //玩家分數加分
				user_score.setText(String.valueOf(userscr));   //將分數顯示於標籤
				message();   //需判斷分數是否滿10分，再跳出提示視窗
			}else if (ua == 1) {
				comscr++;    //電腦分數加分
				com_score.setText(String.valueOf(comscr));   //並將分數顯示於標籤
				message();   //需判斷分數是否滿10分，再跳出提示視窗
			}
			break;
	
		default:
			System.out.println("程式有問題，請檢查");    //例外提示
			break;
		}
			
	}
	
	//任何一方贏10次顯示提示視窗
	public void message() {
		JFrame msg = new JFrame();   //新增視窗元件
		Object[] options = {"是","離開"};  //設定視窗選項物件
		if (userscr == 10 ) {
			//showOptionDialog(容器ex：JFrame,顯示訊息,視窗訊息標題,選項類型,訊息類型,icon，ex：圖片,按鈕選項,按鈕預設)
			result = JOptionPane.showOptionDialog(msg,"恭喜你贏了！！！是否重新開始？","訊息", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
			if (result == 0) {
				newGame();    //如果點選是，重新開始
			}else {
				System.exit(0);    //點選離開則關閉程式
			}
		}else if (comscr == 10) {
			result = JOptionPane.showOptionDialog(msg,"QQ輸了，是否重新開始？","訊息", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
			if(result == 0) {
				newGame();    //如果點選是，重新開始
			}else {
				System.exit(0);    //點選離開則關閉程式
			}
		}
		
	}
	//重新開始將分數歸零以及最後回答清空
	public void newGame() {
		userscr = 0;
		user_score.setText(String.valueOf(userscr));
		comscr = 0;
		com_score.setText(String.valueOf(comscr));
		
		user_answer.setText("玩家：");
		com_answer.setText("電腦：");
		
	}
	
	//程式進入點
	public static void main(String[] args) {
		new RPS();

	}

}