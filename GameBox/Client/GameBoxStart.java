package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoxStart extends JFrame {

	private final JPanel contentPane;
	public JPanel panel;
	public JLabel lblNewLabel;
	public JComboBox comboBox_server;
	public JLabel lblNewLabel_1;
	public JTextField textField_user;
	public JPanel panel_1;
	public JLabel lblNewLabel_2;
	public JComboBox comboBox_game;
	public JButton btnNewButton_startgame;
	public JButton btnNewButton_startgame_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameBoxStart frame = new GameBoxStart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameBoxStart() {
		setTitle("游戏盒子");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 381);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(240, 255, 255));
		panel.setBorder(new TitledBorder(null, "\u767B\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 598, 90);
		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("服务大区：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 25, 106, 40);
		panel.add(lblNewLabel);

		comboBox_server = new JComboBox();
		comboBox_server
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(224, 255, 255), new Color(224, 255, 255)));
		comboBox_server.setForeground(new Color(0, 0, 0));
		comboBox_server.setModel(new DefaultComboBoxModel(new String[] { "localhost:1314" }));
		comboBox_server.setBackground(new Color(224, 255, 255));
		comboBox_server.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox_server.setBounds(102, 30, 215, 30);
		panel.add(comboBox_server);

		lblNewLabel_1 = new JLabel("用户名：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(327, 25, 78, 40);
		panel.add(lblNewLabel_1);

		textField_user = new JTextField();
		textField_user.setBackground(new Color(245, 255, 250));
		textField_user
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(211, 211, 211), new Color(211, 211, 211)));
		textField_user.setFont(new Font("宋体", Font.PLAIN, 16));
		textField_user.setBounds(410, 31, 167, 30);
		panel.add(textField_user);
		textField_user.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 255, 255));
		panel_1.setBorder(
				new TitledBorder(null, "\u6E38\u620F\u670D\u52A1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 104, 598, 234);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		lblNewLabel_2 = new JLabel("选择游戏：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(81, 72, 106, 40);
		panel_1.add(lblNewLabel_2);

		comboBox_game = new JComboBox();
		comboBox_game
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(224, 255, 255), new Color(224, 255, 255)));
		comboBox_game.setModel(new DefaultComboBoxModel(new String[] { "打字游戏", "五子棋", "国际象棋" }));
		comboBox_game.setBackground(new Color(224, 255, 255));
		comboBox_game.setFont(new Font("宋体", Font.PLAIN, 18));
		comboBox_game.setBounds(181, 76, 257, 32);
		panel_1.add(comboBox_game);

		btnNewButton_startgame = new JButton("开始游戏");
		btnNewButton_startgame.setBorder(null);
		btnNewButton_startgame.addActionListener(new BtnNewButton_startgameActionListener());
		btnNewButton_startgame.setBackground(new Color(175, 238, 238));
		btnNewButton_startgame.setFont(new Font("宋体", Font.PLAIN, 16));
		btnNewButton_startgame.setBounds(306, 140, 132, 40);
		panel_1.add(btnNewButton_startgame);

		btnNewButton_startgame_1 = new JButton("游戏规则");
		btnNewButton_startgame_1.addActionListener(new BtnNewButton_startgame_1ActionListener());
		btnNewButton_startgame_1.setFont(new Font("宋体", Font.PLAIN, 16));
		btnNewButton_startgame_1.setBorder(null);
		btnNewButton_startgame_1.setBackground(new Color(175, 238, 238));
		btnNewButton_startgame_1.setBounds(181, 140, 126, 40);
		panel_1.add(btnNewButton_startgame_1);
	}

	private class BtnNewButton_startgameActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] serverinfo = comboBox_server.getSelectedItem().toString().trim().split(":");
			String ip = serverinfo[0];
			int port = Integer.parseInt(serverinfo[1].trim());
			String gameinfo = comboBox_game.getSelectedItem().toString().trim();
			String username = textField_user.getText().trim();
			if (username.equals("")) {
				JOptionPane.showMessageDialog(null, "用户名不可为空且不可重复！！");
			} else {
				if (gameinfo.equals("打字游戏")) {
//            	   System.out.print(username+ip+port);
					new TyWr_GameForm(username, ip, port);
				} else if (gameinfo.equals("五子棋")) {
					if (Gobang_ClientMG.getClientMG().connect(ip, port, username)) {
						System.out.println("连接成功！");
					} else {
						System.out.println("连接失败！");
					}
				}
				else if(gameinfo.equals("国际象棋")) {
					CHESS_GUIchess frame;
					try {
						frame = new CHESS_GUIchess(ip,port,username);
						frame.setVisible(true);
					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
						System.out.println("连接失败！");
					}
					
				}
			}
		}
	}

	private class BtnNewButton_startgame_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Gobang_GameRule frame = new Gobang_GameRule();
						frame.setVisible(true);
						frame.setLocation(400, 100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
