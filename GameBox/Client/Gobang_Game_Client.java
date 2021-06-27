package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gobang_Game_Client extends JFrame {

	private final JPanel contentPane;
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label;
	public JLabel label_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JScrollPane scrollPane;
	public JTextArea textArea;
	public JPanel panel_4;
	public JTextField textField;
	public JButton button;
	private final int [][] chess = new int[15][15];
	private final int iw = 20+15;
	private final int ih = 20+25;
	private int chessType;
	private int flag = 0;
	private int f = -1;
	private int cnt = 3;
	public JPanel panel_5;
	public JButton button_1;
	public JButton button_2;
	public JButton button_3;
	public JPanel panel_6;
	public JScrollPane scrollPane_1;
	public JList list;
	public JButton button_4;
    public String opponent=null;
    public String username=null;
    public void setopponent(String op) {
    	this.opponent=op;
    }
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Game_Client frame = new Game_Client();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Gobang_Game_Client(String username) {
		this.username=username;
		setBackground(new Color(240, 255, 240));
		setTitle(Gobang_ClientMG.getClientMG().getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 895, 588);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(222, 184, 135));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 222, 173));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 222, 173)), "\u9ED1\u65B9", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(14, 13, 201, 267);
		contentPane.add(panel);
		panel.setLayout(null);
		
		label = new JLabel("");
		label.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(28, 79, 152, 113);
		panel.add(label);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 222, 173));
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 228, 181)), "\u767D\u65B9", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(14, 290, 201, 238);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		label_1 = new JLabel("");
		label_1.setBorder(null);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 20));
		label_1.setBounds(22, 60, 153, 113);
		panel_1.add(label_1);
		
		panel_2 = new Gobang_MyPanel_Client(chess);
		panel_2.setBackground(new Color(255, 222, 173));                
		panel_2.addMouseListener(new Panel_2MouseListener());
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 228, 181)));
		panel_2.setBounds(229, 13, 344, 361);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 222, 173));
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 228, 181)));
		panel_3.setBounds(587, 290, 283, 177);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 228, 181));
		scrollPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 228, 181), new Color(255, 228, 181)), "\u804A\u5929\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrollPane.setBounds(14, 23, 255, 137);
		panel_3.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 228, 181)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		textArea.setBackground(new Color(255, 228, 181));
		scrollPane.setViewportView(textArea);
		
		panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 222, 173));
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 228, 181)));
		panel_4.setBounds(587, 467, 283, 61);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		textField = new JTextField();
		textField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 215, 0), new Color(255, 215, 0)));
		textField.setBackground(new Color(255, 228, 181));
		textField.setFont(new Font("宋体", Font.PLAIN, 17));
		textField.setBounds(10, 15, 199, 36);
		panel_4.add(textField);
		textField.setColumns(10);
		
		button = new JButton("\u53D1\u9001");
		button.setBorder(null);
		button.setBackground(new Color(255, 215, 0));
		button.addActionListener(new ButtonActionListener());
		button.setBounds(206, 16, 67, 36);
		panel_4.add(button);
		
		panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 222, 173));
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 228, 181)));
		panel_5.setBounds(229, 382, 344, 152);
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		button_1 = new JButton("\u91CD\u65B0\u5F00\u59CB");
		button_1.setBorder(null);
		button_1.setBackground(new Color(255, 215, 0));
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(14, 29, 316, 27);
		panel_5.add(button_1);
		
		button_2 = new JButton("\u6094\u68CB");
		button_2.setBorder(null);
		button_2.setBackground(new Color(255, 215, 0));
		button_2.addActionListener(new Button_2ActionListener());
		button_2.setBounds(14, 69, 316, 27);
		panel_5.add(button_2);
		
		button_3 = new JButton("\u8BA4\u8F93");
		button_3.setBorder(null);
		button_3.setBackground(new Color(255, 215, 0));
		button_3.addActionListener(new Button_3ActionListener());
		button_3.setBounds(14, 109, 316, 27);
		panel_5.add(button_3);
		
		panel_6 = new JPanel();
		panel_6.setBackground(new Color(255, 222, 173));
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 228, 181)));
		panel_6.setBounds(587, 13, 283, 267);
		contentPane.add(panel_6);
		panel_6.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(10, 23, 263, 190);
		panel_6.add(scrollPane_1);
		
		list = new JList();
		list.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 181), new Color(255, 228, 181)), "\u73A9\u5BB6\u5217\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		list.setBackground(new Color(255, 228, 181));
		scrollPane_1.setViewportView(list);
		
		button_4 = new JButton("玩家对战");
		button_4.addActionListener(new Button_4ActionListener());
		button_4.setBorder(null);
		button_4.setBackground(new Color(255, 215, 0));
		button_4.setBounds(10, 223, 263, 27);
		panel_6.add(button_4);
		
		this.setVisible(true);
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String sName = Gobang_ClientMG.getClientMG().getName();
			String text = textField.getText().trim();
			String res = "GOBANG|MSG@"+sName+"@"+text+"|"+Gobang_ClientMG.getClientMG().getopponent();
			Gobang_ClientMG.getClientMG().sendMSG(res);
			textField.setText("");
			Gobang_ClientMG.getClientMG().setTextArea("[我]："+text);
		}
	}
	private class Panel_2MouseListener extends MouseAdapter {
		@Override
		//下棋
		public void mouseReleased(MouseEvent m) {
			if(opponent!=null) {
			int x = m.getX();
			int y = m.getY();
			if(f == 1 && flag == 0) {
				int i = Math.round((x-iw)/20f);
				int j = Math.round((y-ih)/20f);
				if(i>=0 && i<15 && j>=0 && j<15 && chess[i][j]==0) {
					chess[i][j] = chessType;
					panel_2.repaint();
					Gobang_ClientMG.getClientMG().PushC(i, j);
					String str = "GOBANG|POS|"+ i +"@"+ j +"|"+opponent;
					Gobang_ClientMG.getClientMG().sendMSG(str);
					
					if (won(i, j, chessType)) {
						String msg = "GOBANG|WON|"+opponent;
						Gobang_ClientMG.getClientMG().sendMSG(msg);
						JOptionPane.showMessageDialog(null, chessType==1 ? "黑子赢了！" : "白子赢了！");
						flag = 1;
					}else {
						Gobang_ClientMG.getClientMG().setF(0);
					}
				}
			}else if(flag == 1) {
				JOptionPane.showMessageDialog(null, "棋局已结束，请重新开始！");
			}else  if(f != 1){
				JOptionPane.showMessageDialog(null, "请等对方落子！");
			}
		}else{
			JOptionPane.showMessageDialog(null, "请先选择您的对手！");
		}
	}
	}
	private class Button_1ActionListener implements ActionListener {
		//重新开始
		
		public void actionPerformed(ActionEvent e) {
			if(opponent!=null) {
				String msg = "GOBANG|RESTART|"+opponent;
				Gobang_ClientMG.getClientMG().sendMSG(msg);
			}else {
				JOptionPane.showMessageDialog(null, "请先选择您的对手！");
			}
		}
			
		}
	private class Button_3ActionListener implements ActionListener {
		//认输
		public void actionPerformed(ActionEvent e) {
			if(opponent!=null) {
			Gobang_ClientMG.getClientMG().sendMSG("GOBANG|LOST|"+opponent);
			Gobang_ClientMG.getClientMG().setFlag(1);
			}else {
				JOptionPane.showMessageDialog(null, "请先选择您的对手！");
			}
		}
	}
	private class Button_2ActionListener implements ActionListener {
		//悔棋
		public void actionPerformed(ActionEvent e) {
			if(opponent!=null) {
			if(cnt > 0) {
				if(f != 1) {
					int userOption = JOptionPane.showConfirmDialog(null, "您还有"+ cnt +"次机会，是否确定？", "提示",JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(userOption == JOptionPane.OK_OPTION) {
						Gobang_ClientMG.getClientMG().sendMSG("GOBANG|BACK|"+opponent);
					}
				}else
					JOptionPane.showMessageDialog(null, "对方已落子，无法悔棋！");
			}else {
				JOptionPane.showMessageDialog(null, "您的机会已用完！");
			}
			}else {
				JOptionPane.showMessageDialog(null, "请先选择您的对手！");
			}
		}
	}
	//玩家对战，向玩家发出邀请
	private class Button_4ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			opponent=list.getSelectedValue().toString().trim();
			if(list.getSelectedIndex()>=0) {
				String mess="GOBANG|INVITE@"+username+"|"+opponent;
				Gobang_ClientMG.getClientMG().sendMSG(mess);
				textArea.append("[我]:对战邀请"+"->"+opponent+"\n");
				}
			  
			
		}
	}
	private boolean won(int x,int y,int chessType) {
		
		int [][] chessTmp = chess;
		// 水平
		int c1 = 0, c2 = 0;
		for (int j = x; j >= 0; j--) {
			if (chessTmp[j][y] == chessType)
				c1++;
			else
				break;
		}
		for (int j = x; j < 15; j++) {
			if (chessTmp[j][y] == chessType)
				c2++;
			else
				break;
		}
		if (c1 + c2 - 1 >= 5) {
			return true;
		}
		// 上下
		c1 = 0;
		c2 = 0;
		for (int j = y; j >= 0; j--) {
			if (chessTmp[x][j] == chessType)
				c1++;
			else
				break;
		}
		for (int j = y; j < 15; j++) {
			if (chessTmp[x][j] == chessType)
				c2++;
			else
				break;
		}
		if (c1 + c2 - 1 >= 5) {
			return true;
		}

		// 左上方到右下
		c1 = 0;
		c2 = 0;
		for (int j = x, k = y; (j >= 0) && (k >= 0); j--, k--) {
			if (chessTmp[j][k] == chessType)
				c1++;
			else
				break;
		}
		for (int j = x, k = y; (j < 15) && (k < 15); j++, k++) {
			if (chessTmp[j][k] == chessType)
				c2++;
			else
				break;
		}
		if (c1 + c2 - 1 >= 5) {
			return true;
		}

		// 右上方到左下
		c1 = 0;
		c2 = 0;
		for (int j = x, k = y; (j >= 0) && (k >= 0); j++, k--) {
			if (chessTmp[j][k] == chessType)
				c1++;
			else
				break;
		}
		for (int j = x, k = y; (j < 15) && (k < 15); j--, k++) {
			if (chessTmp[j][k] == chessType)
				c2++;
			else
				break;
		}
		return c1 + c2 - 1 >= 5;
	}
	public void setChess(int i,int j,int type) {
		this.chess[i][j] = type;
		this.panel_2.repaint();
	}
	
	public void setClessType(int n) {
		this.chessType = n;
	}
	
	public void setFlag(int num) {
		flag = num;
	}

	public void reChess() {
		f = -1;
		flag = 0;
		for(int i=0;i<chess.length;i++) {
			for(int j=0;j<chess[i].length;j++) {
				chess[i][j]=0;
			}
		}
		panel_2.repaint();
	}
	
	public void setF(int num) {
		f = num;
	}
	
	public void setCnt() {
		cnt-=1;
	}
	public  void Refreashlist(String [] users) {
		DefaultListModel<String> item=new DefaultListModel();	
		for(String user:users) {
	   item.addElement(user);
		}
		list.setModel(item);
	}
}
