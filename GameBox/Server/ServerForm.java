package Server;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerForm extends JFrame {

	private final JPanel contentPane;
	public JPanel panel;
	public JLabel label;
	public JTextField textPort;
	public JButton btnStart;
	public JButton btnEnd;
	public JScrollPane scrollPane;
	public JTextArea textLog;
public static serverListener sliListener=null;
	static ServerSocket Server = null;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm frame = new ServerForm();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							super.windowClosed(e);
							try {
								ServerMG.getSmg().setTextlog("服务器即将关闭！！！");
								for(String userinfo:ServerMG.getSmg().getinfomap().keySet()) {
									try {
										ServerMG.getSmg().getinfomap().get(userinfo).close();
									} catch (IOException ee) {
										// TODO Auto-generated catch block
										ee.printStackTrace();
									} 
							  }	  
								ServerMG.getSmg().getinfomap().clear();
								sliListener.setflag();
								Server.close();
							} catch (IOException eee) {
								// TODO Auto-generated catch block
								eee.printStackTrace();
							}
						}
					});
					ServerMG.getSmg().setwin(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerForm() {
		setTitle("游戏盒子服务器界面");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "\u914D\u7F6E\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(0, 0, 351, 69);
		contentPane.add(panel);
		panel.setLayout(null);
		
		label = new JLabel("\u7AEF\u53E3\uFF1A");
		label.setBounds(10, 34, 54, 15);
		panel.add(label);
		
		textPort = new JTextField();
		textPort.setText("1314");
		textPort.setBounds(60, 31, 66, 21);
		panel.add(textPort);
		textPort.setColumns(10);
		
		btnStart = new JButton("\u5F00\u542F\u670D\u52A1");
		btnStart.setBackground(Color.LIGHT_GRAY);
		btnStart.addActionListener(new BtnStartActionListener());
		btnStart.setBounds(136, 30, 93, 23);
		panel.add(btnStart);
		
		btnEnd = new JButton("\u5173\u95ED\u670D\u52A1");
		btnEnd.setBackground(Color.LIGHT_GRAY);
		btnEnd.addActionListener(new BtnEndActionListener());
		btnEnd.setBounds(239, 30, 93, 23);
		panel.add(btnEnd);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "\u65E5\u5FD7\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrollPane.setBounds(0, 79, 351, 148);
		contentPane.add(scrollPane);
		
		textLog = new JTextArea();
		textLog.setEditable(false);
		textLog.setLineWrap(true);
		scrollPane.setViewportView(textLog);
	}
	
	private class BtnStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
		//开启服务
			try {
				 Server=new ServerSocket(Integer.parseInt(textPort.getText()));
				ServerMG.getSmg().setTextlog("服务器创建成功！！");
				   sliListener= new serverListener(Server);
				       sliListener.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class BtnEndActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//关闭服务
			try {
				ServerMG.getSmg().setTextlog("服务器即将关闭！！！");
				for(String userinfo:ServerMG.getSmg().getinfomap().keySet()) {
					try {
						ServerMG.getSmg().getinfomap().get(userinfo).close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			  }	  
				ServerMG.getSmg().getinfomap().clear();
				sliListener.setflag();
				Server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
