package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gobang_GameRule extends JFrame {

    public JPanel panel;
    public JButton btnNewButton;
    public JButton btnNewButton_1;
    public JButton btnNewButton_2;
    public JScrollPane scrollPane;
    public JTextArea textArea;
    private final JPanel contentPane;

    /**
     * Launch the application.
     */
//	public static void main(String[] args) {
//		
//	}

    /**
     * Create the frame.
     */
    public Gobang_GameRule() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 514, 526);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(248, 248, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(248, 248, 255), new Color(248, 248, 255)));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBounds(10, 10, 480, 39);
        contentPane.add(panel);
        panel.setLayout(null);

        btnNewButton = new JButton("打字游戏");
        btnNewButton.addActionListener(new BtnNewButtonActionListener());
        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 20));
        btnNewButton.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(173, 216, 230), new Color(173, 216, 230)));
        btnNewButton.setBackground(new Color(135, 206, 235));
        btnNewButton.setBounds(0, 0, 160, 39);
        panel.add(btnNewButton);

        btnNewButton_1 = new JButton("五子棋");
        btnNewButton_1.addActionListener(new BtnNewButton_1ActionListener());
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 20));
        btnNewButton_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(176, 224, 230)));
        btnNewButton_1.setBackground(new Color(176, 224, 230));
        btnNewButton_1.setBounds(160, 0, 160, 39);
        panel.add(btnNewButton_1);

        btnNewButton_2 = new JButton("国际象棋");
        btnNewButton_2.addActionListener(new BtnNewButton_2ActionListener());
        btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 20));
        btnNewButton_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(176, 224, 230)));
        btnNewButton_2.setBackground(new Color(176, 224, 230));
        btnNewButton_2.setBounds(320, 0, 160, 39);
        panel.add(btnNewButton_2);

        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(248, 248, 255));
        scrollPane.setBounds(10, 62, 478, 415);
        contentPane.add(scrollPane);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        textArea.setBackground(new Color(248, 248, 255));
        scrollPane.setViewportView(textArea);
        textArea.setText("规则：\r\n"
                + "1.输入用户名开始游戏.\r\n"
                + "2.开始游戏后会随机落下字母，在字母沉底前敲击键盘上\n对应字母.\r\n"
                + "3.敲击正确个人血量加二，其他玩家减一.\r\n"
                + "4.不正确或者未敲击则扣除个人血量一点.\r\n"
                + "5.每位玩家初始血量为10，扣完出局.");
//		textArea.
    }

    private class BtnNewButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //打字游戏
            //其他按钮变暗
            btnNewButton_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(176, 224, 230)));
            btnNewButton_1.setBackground(new Color(176, 224, 230));
            btnNewButton_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(176, 224, 230)));
            btnNewButton_2.setBackground(new Color(176, 224, 230));
            //加亮
            btnNewButton.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(173, 216, 230), new Color(173, 216, 230)));
            btnNewButton.setBackground(new Color(135, 206, 235));
            textArea.setText("规则：\r\n"
                    + "1.输入用户名开始游戏.\r\n"
                    + "2.开始游戏后会随机落下字母，在字母沉底前敲击键盘上\n对应字母.\r\n"
                    + "3.敲击正确个人血量加二，其他玩家减一.\r\n"
                    + "4.不正确或者未敲击则扣除个人血量一点.\r\n"
                    + "5.每位玩家初始血量为10，扣完出局.");
        }
    }

    private class BtnNewButton_1ActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //五子棋
            //其他按钮变暗
            btnNewButton.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(176, 224, 230)));
            btnNewButton.setBackground(new Color(176, 224, 230));
            btnNewButton_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(176, 224, 230)));
            btnNewButton_2.setBackground(new Color(176, 224, 230));
            //加亮
            btnNewButton_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(173, 216, 230), new Color(173, 216, 230)));
            btnNewButton_1.setBackground(new Color(135, 206, 235));
            textArea.setText("规则：\r\n"
                    + "1.输入用户名开始游戏。\r\n"
                    + "2.首先选中玩家，点击玩家对战邀请其他玩家开始对战\r\n"
                    + "   其他玩家同意后对战开始，系统自动分配黑白方。\r\n"
                    + "3.点击重新开始以重开游戏。\r\n"
                    + "4.点击悔棋待对手同意后返回上一步。\r\n"
                    + "5.点击认输 直接认输。\r\n"
                    + "6.可在输入框输入信息，点击发送与对手交流。");
        }
    }

    private class BtnNewButton_2ActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //国际象棋
            //其他按钮变暗
            btnNewButton_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(176, 224, 230)));
            btnNewButton_1.setBackground(new Color(176, 224, 230));
            btnNewButton.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(176, 224, 230)));
            btnNewButton.setBackground(new Color(176, 224, 230));
            //加亮
            btnNewButton_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(173, 216, 230), new Color(173, 216, 230)));
            btnNewButton_2.setBackground(new Color(135, 206, 235));
            textArea.setText("规则：\r\n"
					+ "一：国际象棋是由两方对下，以把对方的王‘将死’为取胜。国际象棋由白方先走，而后双方轮流走棋，直到对局结束为止。\r\n"
//					+ "国际象棋的棋盘是一个正方形，等分为64个方格，因此又被称为“64格”。这些方格有深浅两种颜色，交替排列。深色的方格称为黑格，浅色的方格称为白格，棋子就放在这些格子中移动。摆放棋盘时，须使每方的右下角是白格。\r\n"
					+ "二：\r\n\t1、 王：横、直、斜都可以走，可进可退，但每步仅限走1格。\r\n"
					+ "\t2、 后：横、直、斜都可以走，可进可退，格数不限，但不能越子。\r\n"
					+ "\t3、 车：横、直都可以走，可进可退，格数不限，但不能斜走，也不能越子。\r\n"
					+ "\t4、 象：只能斜走，可进可退，格数不限，但不能越子。\r\n"
					+ "\t5、 马：走法有点特别，先横走或直走1格，再沿离开原在格子的方向斜走1格，合起来为一步棋。可以越子，可进可退。\r\n"
					+ "\t6、 兵：只能向前直走，不能后退，而且每步只能走1格。但在初始位置的兵，第一步可以选走1格或2格，\r\n以后每步只能走1格。兵的吃子方法与其走法不同，它只能向前斜进1格吃掉对方的棋子，所以它是直进斜吃。\r\n"
					+ "三：点击重新开始以重开游戏。\r\n"
					+ "四：点击悔棋待对手同意后返回上一步。\r\n"
					+ "五：点击认输 直接认输。\r\n"
					+ "六：可在输入框输入信息，点击发送与对手交流。"
			);

        }
    }
}
