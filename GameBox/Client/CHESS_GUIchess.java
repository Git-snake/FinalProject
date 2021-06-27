package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CHESS_GUIchess extends JFrame {

    public static final short WHITEPLAYER = 1;
    public static final short BLACKPLAYER = 0;
    public static JButton btn_regret;
    public static JTextArea LogArea;
    public static JButton btn_start;
    public static JButton btn_again;
    static CHESS_BoardPane Borad = new CHESS_BoardPane();
    /**
     * Launch the application.
     */

    static DefaultListModel<String> itemUsers;
    static String sRecName = "";
    public JPanel online;
    public static JList onlinelist;
    public JPanel panel;
    public static JButton btn_concede;
    public static JButton btn_draw;
    public JScrollPane Logscroll;
    public JScrollPane sendscroll;
    public JButton btn_send;
    public JTextField textField;
    private final JPanel contentPane;

    /**
     * Create the frame.
     *
     * @throws Exception
     */
    public CHESS_GUIchess(String ip, int port, String username) throws Exception {
        setTitle("\u56FD\u9645\u8C61\u68CB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 703, 598);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(210, 180, 140));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        Borad.setBounds(10, 10, 480, 480);
        contentPane.add(Borad);
        Borad.setLayout(null);

        online = new JPanel();
        online.setBounds(500, 10, 167, 275);
        contentPane.add(online);
        online.setLayout(null);

        itemUsers = new DefaultListModel<String>();
        onlinelist = new JList(itemUsers);
        onlinelist.setBackground(new Color(245, 222, 179));
        onlinelist.setBorder(
                new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(245, 222, 179), new Color(245, 222, 179)), "\u5728\u7EBF\u7528\u6237", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        onlinelist.setBounds(0, 0, 167, 275);
        online.add(onlinelist);

        panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(245, 222, 179), new Color(245, 222, 179)));
        panel.setBackground(new Color(245, 222, 179));
        panel.setBounds(500, 295, 167, 255);
        contentPane.add(panel);
        panel.setLayout(null);

        Logscroll = new JScrollPane();
        Logscroll.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(245, 222, 179), new Color(245, 222, 179)));
        Logscroll.setBounds(0, 0, 167, 161);
        panel.add(Logscroll);

        LogArea = new JTextArea();
        LogArea.setBackground(new Color(245, 222, 179));
        LogArea.setBorder(
                new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(245, 222, 179), new Color(245, 222, 179)), "\u804A\u5929\u8BB0\u5F55", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        LogArea.setEditable(false);
        Logscroll.setViewportView(LogArea);

        sendscroll = new JScrollPane();
        sendscroll.setBackground(new Color(245, 222, 179));
        sendscroll.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(245, 222, 179), new Color(245, 222, 179)));
        sendscroll.setBounds(0, 171, 167, 62);
        panel.add(sendscroll);

        textField = new JTextField();
        textField.setBackground(new Color(245, 222, 179));
        textField.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(245, 222, 179), new Color(222, 184, 135)), "\u53D1\u9001\u6846", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        sendscroll.setViewportView(textField);
        textField.setColumns(10);

        btn_send = new JButton("\u53D1\u9001");
        btn_send.setBackground(new Color(255, 215, 0));
        btn_send.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 215, 0), new Color(255, 215, 0)));
        btn_send.addActionListener(new Btn_sendActionListener());
        btn_send.setBounds(74, 232, 93, 23);
        panel.add(btn_send);

        btn_regret = new JButton("\u6094\u68CB");
        btn_regret.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 196), new Color(255, 228, 196)));
        btn_regret.setBackground(new Color(255, 228, 196));
        btn_regret.setVisible(false);
        btn_regret.addActionListener(new Btn_regretActionListener());
        btn_regret.setBounds(10, 527, 93, 23);
        contentPane.add(btn_regret);

        btn_concede = new JButton("\u8BA4\u8F93");
        btn_concede.setBackground(new Color(255, 228, 196));
        btn_concede.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 196), new Color(255, 228, 196)));
        btn_concede.addActionListener(new Btn_concedeActionListener());
        btn_concede.setVisible(false);
        btn_concede.setBounds(210, 527, 93, 23);
        contentPane.add(btn_concede);

        btn_draw = new JButton("\u6C42\u548C");
        btn_draw.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 228, 196), new Color(255, 228, 196)));
        btn_draw.setBackground(new Color(255, 228, 196));
        btn_draw.addActionListener(new Btn_drawActionListener());
        btn_draw.setVisible(false);
        btn_draw.setBounds(397, 527, 93, 23);
        contentPane.add(btn_draw);

        btn_start = new JButton("\u5F00\u59CB\u6E38\u620F");
        btn_start.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(240, 230, 140), new Color(240, 230, 140)));
        btn_start.setBackground(new Color(240, 230, 140));
        btn_start.addActionListener(new Btn_startActionListener());
        btn_start.setBounds(210, 500, 93, 23);
        contentPane.add(btn_start);

        btn_again = new JButton("\u518D\u6765\u4E00\u5C40");
        btn_again.addActionListener(new Btn_againActionListener());
        btn_again.setVisible(false);
        btn_again.setBounds(210, 500, 93, 23);
        contentPane.add(btn_again);
        Borad.startJoin(ip, port, username);
        
        itemUsers.addElement(username+"(我)");
        
        this.addWindowListener(new WindowAdapter() {//窗口关闭事件
            public void windowClosing(WindowEvent e){
                try{
                    Borad.send("quit");
                    Borad.pw.close();
                    Borad.br.close();
                    Borad.socket.close();
                    System.exit(0);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public void btn_change(boolean b) {
		btn_start.setVisible(b);
		btn_regret.setVisible(!b);
		btn_concede.setVisible(!b);
		btn_draw.setVisible(!b);
    }

    private class Btn_startActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (onlinelist.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(null, "请在右侧选择对手", "尚未选择对手", JOptionPane.ERROR_MESSAGE);
            } else {
                sRecName = onlinelist.getSelectedValue().toString().trim();
                Borad.send("GAMEREQUEST");
                btn_change(false);
            }
        }
    }

    private class Btn_regretActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (Borad.list.size() == 0) {
                JOptionPane.showMessageDialog(null, "不能悔棋");
                return;
            }
            if (Borad.list.size() == 1) {
                int flag = Borad.LocalPlayer == WHITEPLAYER ? WHITEPLAYER : BLACKPLAYER;
                if (flag == WHITEPLAYER) {//如果我是白方，判断上一步是不是对方下的，如果是，不能悔棋
                    if (Borad.list.get(0).index < 16) {
                        JOptionPane.showMessageDialog(null, "不能悔棋");
                        return;
                    }
                } else {
                    if (Borad.list.get(0).index >= 16) {
                        JOptionPane.showMessageDialog(null, "不能悔棋");
                        return;
                    }
                }
            }
            Borad.send("ask");// 发送请求悔棋请求
        }
    }
    
    private class Btn_sendActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		String sendmgs=textField.getText();
    		textField.setText(null);
    		LogArea.append("[我]："+sendmgs+"\n");
    		Borad.send("MSG|"+sendmgs);
    	}
    }
    //认输
    private class Btn_concedeActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    	    btn_again.setVisible(true);
    		Borad.send("concede");
    	}
    }
    //求和
    private class Btn_drawActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		Borad.send("draw");
    	}
    }
    private class Btn_againActionListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		btn_again.setVisible(false);
    		btn_change(true);
    		LogArea.removeAll();
    	}
    }
}
