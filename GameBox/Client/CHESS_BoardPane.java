package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CHESS_BoardPane extends JPanel implements Runnable {

	public static final short WHITEPLAYER = 1;
	public static final short BLACKPLAYER = 0;
	public CHESS_Chess[] chess = new CHESS_Chess[32];// 棋子数组
	public int[][] map = new int[8][8];// 存储棋盘布局信息数组10行9列
	public short LocalPlayer = WHITEPLAYER;// 记录当前执子方
	public String username;
	public ArrayList<CHESS_Node> list = new ArrayList<>();// 存储棋谱
	Socket socket = null;
	BufferedReader br = null;
	PrintWriter pw = null;
	private CHESS_Chess firstChess = null;
	private CHESS_Chess secondChess = null;
	private boolean isFirstClick = true;// 标记是否第一次点击
	private int x1, y1, x2, y2;
	private int tempX, tempY;
	private boolean isMyTurn = true;// 标记是否自己执子
	private String message = "";// 提示信息

	public CHESS_BoardPane() {
		initMap();
		message = "程序处于等待联机状态!";

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!isMyTurn) {
					message = "现在该对方走棋";
					repaint();
					return;
				}
				selectedChess(e);
				repaint();
			}

			private void selectedChess(MouseEvent e) {
				int index1, index2;// 保存第一次和第二次被单击的棋子对应数组下标
				if (isFirstClick) {// 第一次点击
					firstChess = analyse(e.getX(), e.getY());
					x1 = tempX;
					y1 = tempY;
					if (firstChess != null) {
						if (firstChess.player != LocalPlayer) {
							message = "点击成对方棋子了";
							return;
						}
						isFirstClick = false;
					}
				} else {
					secondChess = analyse(e.getX(), e.getY());
					x2 = tempX;
					y2 = tempY;
					if (secondChess != null) { // 如果第二次点击选中了棋子
						if (secondChess.player == LocalPlayer) { // 如果第二次点击的棋子是自己的棋子，则对第一次选中的棋子进行更换
							firstChess = secondChess;
							x1 = tempX;
							y1 = tempY;
							secondChess = null;
							return;
						}
					}

					if (secondChess == null) {// 如果目标处没有棋子,判断是否可以走棋
						if (IsAbleToMove(firstChess, x2, y2)) {
//							System.out.println("判断后:" + x2 + ":" + y2);
							index1 = map[x1][y1];
							map[x1][y1] = -1;
							map[x2][y2] = index1;
							chess[index1].setPos(x2, y2);
							// send
							send("move" + "|" + index1 + "|" + (7 - x2) + "|" + (7 - y2) + "|" + (7 - x1) + "|"
									+ (7 - y1) + "|" + "-1");
							list.add(new CHESS_Node(index1, x2, y2, x1, y1, -1));// 存储我方下棋信息
							// 置第一次选中标记量为空
							isFirstClick = true;
							repaint();
							SetMyTurn(false);// 该对方了
						} else {
							message = "不符合走棋规则";
						}
						return;
					}

					if (secondChess != null && IsAbleToMove(firstChess, x2, y2)) {// 可以吃子
						isFirstClick = true;
						index1 = map[x1][y1];
						index2 = map[x2][y2];
						map[x1][y1] = -1;
						map[x2][y2] = index1;
						chess[index1].setPos(x2, y2);
						chess[index2] = null;
						repaint();
						send("move|" + index1 + "|" + (7 - x2) + "|" + (7 - y2) + "|" + (7 - x1) + "|" + (7 - y1) + "|"
								+ index2);
						list.add(new CHESS_Node(index1, x2, y2, x1, y1, index2));// 记录我方下棋信息
						if (index2 == 0) {// 被吃掉的是黑色国王
							message = "白方赢了";
							JOptionPane.showConfirmDialog(null, "白方赢了", "提示", JOptionPane.DEFAULT_OPTION);
							// send
							send("succ|白方赢了");
							CHESS_GUIchess.btn_again.setVisible(true);
							return;
						}
						if (index2 == 16) {// 被吃掉的是白色国王
							message = "黑方赢了";
							JOptionPane.showConfirmDialog(null, "黑方赢了", "提示", JOptionPane.DEFAULT_OPTION);
							// send
							send("succ|黑方赢了");
							CHESS_GUIchess.btn_again.setVisible(true);
							return;
						}
						SetMyTurn(false);// 该对方了
					} else {// 不能吃子
						message = "不能吃子";
					}
				}
			}

			private CHESS_Chess analyse(int x, int y) {
				int index_x = -1, index_y = -1;// 记录点击处是第几行第几列
				int i = (int) Math.floor(x / 60f);
				int j = (int) Math.floor(y / 60f);
//                System.out.println(i + " " + j);
				if (i >= 0 && i < 8 && j >= 0 && j < 8) {
					index_x = i;
					index_y = j;
				}
				tempX = index_x;
				tempY = index_y;

				if (index_x == -1 && index_y == -1) {// 没有点击到任何棋盘可点击处
					return null;
				}

				if (map[index_x][index_y] == -1) {
					return null;
				} else {
					return chess[map[index_x][index_y]];
				}

			}
		});
	}

	/**
	 * Create the panel.
	 */

	private void initMap() {
		int i, j;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				map[i][j] = -1;
			}
		}
	}

	private boolean isMyChess(int index) {
		if (index >= 0 && index <= 15 && LocalPlayer == BLACKPLAYER) {
			return true;
		}

		return index >= 16 && index <= 31 && LocalPlayer == WHITEPLAYER;
	}

	private void SetMyTurn(boolean b) {

		isMyTurn = b;
		if (b) {
			message = "请您开始走棋";
		} else {
			message = "对方正在思考";
		}
	}

	public void startNewGame(short player) {
		initMap();
		initChess();

		if (player == BLACKPLAYER) {
			reverseBoard();
		}
		repaint();
	}

	// 将棋子回退到上一步的位置，并把棋子未回退前的棋盘位置信息清空
	private void rebackChess(int index, int x, int y, int oldX, int oldY) {
		chess[index].setPos(oldX, oldY);
		map[oldX][oldY] = index;// 棋子放回到(oldX,oldY)
		map[x][y] = -1;// 棋盘里原有棋子位置信息清除
	}

	public void startJoin(String ip, int Port, String username) throws Exception {
		// 端口
		this.username = username;
		this.socket = new Socket(ip, Port);
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		this.pw = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)));
		Thread th = new Thread(this);
		th.start();
	}

	// 将一个被吃了的子重新放回到棋盘，传入参数说明：index棋子数组下标，第x行，第y列
	private void resetChess(int index, int x, int y) {
		short temp = index < 16 ? BLACKPLAYER : WHITEPLAYER;// 存储是哪方的棋子
		String name = null;// 存储棋子上的字
		switch (index) {// 根据棋子索引，得到棋子上面的字
		case 0:
			name = "黑色国王";
			break;
		case 1:
			name = "黑色皇后";
			break;
		case 2:
		case 3:
			name = "黑色主教";
			break;
		case 4:
		case 5:
			name = "黑色骑士";
			break;
		case 6:
		case 7:
			name = "黑色战车";
			break;
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
			name = "黑色禁卫军";
			break;
		case 16:
			name = "白色国王";
			break;
		case 17:
			name = "白色皇后";
			break;
		case 18:
		case 19:
			name = "白色主教";
			break;
		case 20:
		case 21:
			name = "白色骑士";
			break;
		case 22:
		case 23:
			name = "白色战车";
			break;
		case 24:
		case 25:
		case 26:
		case 27:
		case 28:
		case 29:
		case 30:
		case 31:
			name = "白色禁卫军";
			break;
		}

		chess[index] = new CHESS_Chess(temp, name, x, y);
		map[x][y] = index;// 将棋子放回到棋盘
	}

	// 初始化棋子布局
	private void initChess() {
		// 布置黑方棋子
		chess[0] = new CHESS_Chess(BLACKPLAYER, "黑色国王", 4, 0);// 第0行第4列
		map[4][0] = 0;
		chess[1] = new CHESS_Chess(BLACKPLAYER, "黑色皇后", 3, 0);// 第0行第3列
		map[3][0] = 1;
		chess[2] = new CHESS_Chess(BLACKPLAYER, "黑色主教", 5, 0);// 第0行第5列
		map[5][0] = 2;
		chess[3] = new CHESS_Chess(BLACKPLAYER, "黑色主教", 2, 0);// 第0行第2列
		map[2][0] = 3;
		chess[4] = new CHESS_Chess(BLACKPLAYER, "黑色骑士", 6, 0);// 第0行第6列
		map[6][0] = 4;
		chess[5] = new CHESS_Chess(BLACKPLAYER, "黑色骑士", 1, 0);// 第0行第1列
		map[1][0] = 5;
		chess[6] = new CHESS_Chess(BLACKPLAYER, "黑色战车", 7, 0);// 第0行第7列
		map[7][0] = 6;
		chess[7] = new CHESS_Chess(BLACKPLAYER, "黑色战车", 0, 0);// 第0行第0列
		map[0][0] = 7;
		for (int i = 0; i < 8; i++) {// 8个黑方禁卫军
			chess[8 + i] = new CHESS_Chess(BLACKPLAYER, "黑色禁卫军", i, 1);
			map[i][1] = 8 + i;
		}

		// 布置红方棋子
		chess[16] = new CHESS_Chess(WHITEPLAYER, "白色国王", 4, 7);// 第7行第4列
		map[4][7] = 16;
		chess[17] = new CHESS_Chess(WHITEPLAYER, "白色皇后", 3, 7);// 第7行第3列
		map[3][7] = 17;
		chess[18] = new CHESS_Chess(WHITEPLAYER, "白色主教", 5, 7);// 第7行第5列
		map[5][7] = 18;
		chess[19] = new CHESS_Chess(WHITEPLAYER, "白色主教", 2, 7);// 第7行第2列
		map[2][7] = 19;
		chess[20] = new CHESS_Chess(WHITEPLAYER, "白色骑士", 6, 7);// 第7行第6列
		map[6][7] = 20;
		chess[21] = new CHESS_Chess(WHITEPLAYER, "白色骑士", 1, 7);// 第7行第1列
		map[1][7] = 21;
		chess[22] = new CHESS_Chess(WHITEPLAYER, "白色战车", 7, 7);// 第7行第7列
		map[7][7] = 22;
		chess[23] = new CHESS_Chess(WHITEPLAYER, "白色战车", 0, 7);// 第7行第0列
		map[0][7] = 23;

		for (int i = 0; i < 8; i++) {// 8个白色禁卫军
			chess[24 + i] = new CHESS_Chess(WHITEPLAYER, "白色禁卫军", i, 6);
			map[i][6] = 24 + i;
		}

	}

	// 倒置棋盘
	private void reverseBoard() {
		// 对棋子的位置进行互换
		for (int i = 0; i < 32; i++) {
			if (chess[i] != null) {
				chess[i].ReversePos();
			}
		}

		// 对两方的棋盘信息进行倒置互换
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 2; j++) {
				int temp = map[i][j];
				map[i][j] = map[7 - i][7 - j];
				map[7 - i][7 - j] = temp;
			}
		}

	}

	public void paint(Graphics g) {
		// 绘制棋盘
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0)
					g.setColor(new Color(235, 213, 176));
				else
					g.setColor(new Color(167, 86, 20));
				g.fillRect(i * 60, j * 60, 60, 60);
			}

		for (int i = 0; i < 32; i++) {
			if (chess[i] != null) {
				chess[i].paint(g, this);
			}
		}
		if (firstChess != null) {
			firstChess.DrawSelectedChess(g);
		}
		if (secondChess != null) {
			secondChess.DrawSelectedChess(g);
		}
//        g.setColor(Color.BLUE);
//        g.drawString(message, 10, 500);
	}

	private boolean IsAbleToMove(CHESS_Chess firstChess, int x, int y) {

		int oldX, oldY;
		oldX = firstChess.x;
		oldY = firstChess.y;
		String chessName = firstChess.typeName;
		if (chessName.equals("黑色国王") || chessName.equals("白色国王")) {
			return Math.abs(x - oldX) <= 1 && Math.abs(y - oldY) <= 1;
		}

		if (chessName.equals("黑色皇后") || chessName.equals("白色皇后")) {
			if (x == oldX || y == oldY || Math.abs(x - oldX) == (Math.abs(y - oldY))) {
				if (x < oldX && y < oldY) { // 左上
					for (int i = 1; i < (oldX - x); i++)
						if (map[x + i][y + i] != -1)
							return false;
				} else if (x == oldX && y < oldY) { // 正上
					for (int i = 1; i < (oldY - y); i++)
						if (map[x][y + i] != -1)
							return false;
				} else if (x < oldX && y > oldY) { // 左下
					for (int i = 1; i < (oldX - x); i++)
						if (map[x + i][y - i] != -1)
							return false;
				} else if (x == oldX && y > oldY) { // 正下
					for (int i = 1; i < (y - oldY); i++)
						if (map[x][y - i] != -1)
							return false;
				} else if (x > oldX && y < oldY) { // 右上
					for (int i = 1; i < (x - oldX); i++)
						if (map[x - i][y + i] != -1)
							return false;
				} else if (x > oldX && y == oldY) { // 正右
					for (int i = 1; i < (x - oldX); i++)
						if (map[x - i][y] != -1)
							return false;
				} else if (x > oldX && y > oldY) { // 右下
					for (int i = 1; i < (x - oldX); i++)
						if (map[x - i][y - i] != -1)
							return false;
				}
				return true;
			}
			return false;
		}
		if (chessName.equals("白色战车") || chessName.equals("黑色战车")) {
			if ((x - oldX) * (y - oldY) != 0) {// 如果横向位移量和纵向位移量同时都不为0，说明车在斜走，故return false
				return false;
			}
			if (x != oldX) {// 如果车纵向移动
				if (oldX > x) {// 将判断过程简化为纵向从上往下查找中间是否有其他子
					int t = x;
					x = oldX;
					oldX = t;
				}
				for (int i = oldX + 1; i < x; i++) {
					if (map[i][oldY] != -1) {// 如果中间有其他子
						return false;
					}
				}
			}
			if (y != oldY) {// 如果车横向移动
				if (oldY > y) {// 将判断过程简化为横向从左到右查找中间是否有其他子
					int t = y;
					y = oldY;
					oldY = t;
				}
				for (int i = oldY + 1; i < y; i++) {
					if (map[oldX][i] != -1) {// 如果中间有其他子
						return false;
					}
				}
			}
			return true;
		}

		if (chessName.equals("黑色主教") || chessName.equals("白色主教")) {
			if (Math.abs(x - oldX) != Math.abs(y - oldY))
				return false;
			if (x < oldX && y < oldY) {
				for (int i = 1; i < (oldX - x); i++)
					if (map[x + i][y + i] != -1)
						return false;
			} else if (x < oldX && y > oldY) {
				for (int i = 1; i < (oldX - x); i++)
					if (map[x - i][y + i] != -1)
						return false;
			} else if (x > oldX && y < oldY) {
				for (int i = 1; i < (x - oldX); i++)
					if (map[x - i][y + i] != -1)
						return false;
			} else if (x > oldX && y > oldY) {
				for (int i = 1; i < (x - oldX); i++)
					if (map[x - i][y - i] != -1)
						return false;
			}
			return true;
		}

		if (chessName.equals("黑色骑士") || chessName.equals("白色骑士")) {
			// 如果横向位移量乘以竖向位移量不等于2,即如果马不是走日字
			return Math.abs(x - oldX) * Math.abs(y - oldY) == 2;
		}

		if (chessName.equals("黑色禁卫军") || chessName.equals("白色禁卫军")) {
			if (oldY == 6 && oldY - y == 2) { // 棋子第一步可以选择走两格
				if (map[x][y + 1] == -1)
					return true;
			}
			if ((x == oldX && oldY - y == 1) || ((Math.abs(oldX - x) == 1 && oldY - y == 1 && map[x][y] != -1))) { // 棋子正常只能走一格
				if (y == 0) { // 到达对面底部，棋子生变
					String[] options = { "皇后", "战车", "主教", "骑士" };
					int n = JOptionPane.showOptionDialog(null, "请选择你的选项：", "提示", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]); // 选择对话框*/
					firstChess.changeType(chessName.substring(0, 2) + options[n]);
				}
				return true;
			}
			return false;
		}

		return false;
	}

	public void send(String str) {
		try {
			pw.println("CHESS|" + str + "|" + username + "|" + CHESS_GUIchess.sRecName);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_change(boolean b) {
		CHESS_GUIchess.btn_start.setVisible(b);
		CHESS_GUIchess.btn_regret.setVisible(!b);
		CHESS_GUIchess.btn_concede.setVisible(!b);
		CHESS_GUIchess.btn_draw.setVisible(!b);
	}

	@Override
	public void run() {
		try {
			String str;
//	        发送登录信息：GAME|The name of the game|Username
			String sUser = username;
			String sSend = "LOGIN|CHESS|" + sUser; // 登录信息
			pw.println(sSend);
			pw.flush();

			while ((str = br.readLine()) != null) { // 循环监听客户端的数据
				String[] commands = str.split("\\|");
				if (commands[0].equals("USERLISTS")) {
//				处理USERLISTS命令：所有在线用户的用户名 ,格式：USERLISTS|user1_user2_user3
					try {
						String sUsers = commands[1]; // 所有用户信息
						String[] users = sUsers.split("_");
						// 添加到客户端的List控件中。
						for (String user : users) {
							CHESS_GUIchess.itemUsers.addElement(user);
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						// TODO: handle exception
					}

				} else if (commands[0].equals("ADD")) {
//					处理添加用户的信息，格式：ADD|Username
					String user = commands[1];
					CHESS_GUIchess.itemUsers.addElement(user);
				} else if (commands[0].equals("REFREASH")) {
					CHESS_GUIchess.itemUsers.removeElement(commands[1]);
				} else if (commands[1].equals("GAMEREQUEST")) {// 对局被加入，我是黑方
					int userOption = JOptionPane.showConfirmDialog(null, "是否接受" + commands[2] + "的游戏请求？", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); // 确认对话框
//					//如果用户选择的是OK
					if (userOption == JOptionPane.OK_OPTION) {
						CHESS_GUIchess.sRecName = commands[2];
						LocalPlayer = BLACKPLAYER;
						startNewGame(LocalPlayer);
						btn_change(false);
						CHESS_GUIchess.onlinelist.setSelectedValue(commands[2], isFirstClick);
						SetMyTurn(LocalPlayer == BLACKPLAYER);
						// 发送联机成功信息
						send("conn");
					} else {
						send("GAMENO");
					}
				} else if (commands[1].equals("GAMENO")) {
					JOptionPane.showConfirmDialog(null, "对方拒绝游戏", "ERROR", JOptionPane.DEFAULT_OPTION);
					btn_change(true);
				} else if (commands[1].equals("conn")) {// 我成功加入别人的对局，联机成功。我是白方
					btn_change(false);
					LocalPlayer = WHITEPLAYER;
					startNewGame(LocalPlayer);
					SetMyTurn(LocalPlayer == WHITEPLAYER);

				} else if (commands[1].equals("succ")) {
					if (commands[2].equals("黑方赢了")) {
						if (LocalPlayer == WHITEPLAYER)
							JOptionPane.showConfirmDialog(null, "黑方赢了，你可以重新开始", "你输了", JOptionPane.DEFAULT_OPTION);
						else
							JOptionPane.showConfirmDialog(null, "黑方赢了，你可以重新开始", "你赢了", JOptionPane.DEFAULT_OPTION);
					}
					if (commands[2].equals("白方赢了")) {
						if (LocalPlayer == WHITEPLAYER)
							JOptionPane.showConfirmDialog(null, "白方赢了，你可以重新开始", "你赢了", JOptionPane.DEFAULT_OPTION);
						else
							JOptionPane.showConfirmDialog(null, "白方赢了，你可以重新开始", "你输了", JOptionPane.DEFAULT_OPTION);
					}
					message = "你可以重新开局";
					CHESS_GUIchess.btn_again.setVisible(true);
				} else if (commands[1].equals("concede")) {
					JOptionPane.showConfirmDialog(null, "对方已认输，你可以重新开始", "你赢了", JOptionPane.DEFAULT_OPTION);
					CHESS_GUIchess.btn_again.setVisible(true);
				} else if (commands[1].equals("draw")) {
					int userOption = JOptionPane.showConfirmDialog(null, commands[2] + "请求和棋，是否同意", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); // 确认对话框
//					//如果用户选择的是OK
					if (userOption == JOptionPane.OK_OPTION) {
						JOptionPane.showConfirmDialog(null, "和棋，你可以重新开始", "平局", JOptionPane.DEFAULT_OPTION);
						send("drawYES");
						CHESS_GUIchess.btn_again.setVisible(true);
					} else {
						send("drawNO");
					}
				} else if (commands[1].equals("drawYES")) {
					CHESS_GUIchess.btn_again.setVisible(true);
					JOptionPane.showConfirmDialog(null, "和棋，你可以重新开始", "平局", JOptionPane.DEFAULT_OPTION);
				} else if (commands[1].equals("drawNO")) {
					JOptionPane.showConfirmDialog(null, "对方拒绝和棋，请继续游戏", "ERROR", JOptionPane.DEFAULT_OPTION);
				} else if (commands[1].equals("move")) {
					// 对方的走棋信息，move|棋子索引号|x|y|oldX|oldY|被吃掉的棋子的索引号|
					int index = Short.parseShort(commands[2]);
					x2 = Short.parseShort(commands[3]); // 棋子移动后所在列数
					y2 = Short.parseShort(commands[4]); // 棋子移动后所在行数
					int oldX = Short.parseShort(commands[5]);// 棋子移动前所在列数
					int oldY = Short.parseShort(commands[6]);// 棋子移动前所在行数
					int eatChessIndex = Short.parseShort(commands[7]);// 被吃掉的棋子索引
					list.add(new CHESS_Node(index, x2, y2, oldX, oldY, eatChessIndex));// 记录下棋信息
					message = "对方将棋子\"" + chess[index].typeName + "\"移动到了(" + x2 + "," + y2 + ")\n现在该你走棋";
					CHESS_Chess c = chess[index];
					x1 = c.x;
					y1 = c.y;

					index = map[x1][y1];
					int index2 = map[x2][y2];
					map[x1][y1] = -1;
					map[x2][y2] = index;
					chess[index].setPos(x2, y2);

					if (index2 != -1) {// 如果吃了子，则取下被吃掉的棋子
						chess[index2] = null;
					}
					repaint();
					isMyTurn = true;

				} else if (commands[1].equals("quit")) {
					JOptionPane.showConfirmDialog(null, "对方退出了，游戏结束！", "提示", JOptionPane.DEFAULT_OPTION);
					message = "对方退出了，游戏结束！";
					CHESS_GUIchess.btn_again.setVisible(true);// 可以点击再来一局按钮了
				} else if (commands[0].equals("lose")) {
					JOptionPane.showConfirmDialog(null, "恭喜你，对方认输了！", "你赢了", JOptionPane.DEFAULT_OPTION);
					SetMyTurn(false);
					CHESS_GUIchess.btn_again.setVisible(true);// 可以点击再来一局按钮了
				} else if (commands[1].equals("ask")) {// 对方请求悔棋

					String msg = "对方请求悔棋，是否同意？";
					int type = JOptionPane.YES_NO_OPTION;
					String title = "请求悔棋";
					int choice = 0;
					choice = JOptionPane.showConfirmDialog(null, msg, title, type);
					if (choice == 1) {// 否,拒绝悔棋
						send("refuse");
					} else if (choice == 0) {// 是,同意悔棋
						send("agree");
						message = "同意了对方的悔棋，对方正在思考";
						SetMyTurn(false);// 对方下棋

						CHESS_Node temp = list.get(list.size() - 1);// 获取棋谱最后一步棋的信息
						list.remove(list.size() - 1);// 移除
						if (LocalPlayer == WHITEPLAYER) {// 假如我是白方

							if (temp.index >= 16) {// 上一步是我下的，需要回退两步
								rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
								if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
									resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
								}
								temp = list.get(list.size() - 1);
								list.remove(list.size() - 1);

								rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
								if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
									resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
								}
							} else {// 上一步是对方下的，需要回退一步

								rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
								if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
									resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
								}

							}

						} else {// 假如我是黑方

							if (temp.index < 16) {// 上一步是我下的，需要回退两步

								rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
								if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
									resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
								}
								temp = list.get(list.size() - 1);
								list.remove(list.size() - 1);
								rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
								if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
									resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
								}
							} else {// 上一步是对方下的，需要回退一步
								rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
								if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
									resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
								}
							}
						}
						repaint();
					}

				} else if (commands[1].equals("agree")) {// 对方同意悔棋

					JOptionPane.showMessageDialog(null, "对方同意了你的悔棋请求");
					CHESS_Node temp = list.get(list.size() - 1);// 获取棋谱最后一步棋的信息
					list.remove(list.size() - 1);// 移除
					if (LocalPlayer == WHITEPLAYER) {// 假如我是白方

						if (temp.index >= 16) {// 上一步是我下的，回退一步即可

							rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
							if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
								resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
							}
						} else {// 上一步是对方下的，需要回退两步
							// 第一次回退，此时回退到的状态是我刚下完棋轮到对方下棋的状态
							rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
							if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
								resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
							}
							temp = list.get(list.size() - 1);
							list.remove(list.size() - 1);
							// 第二次回退，此时回退到的状态是我上一次刚轮到我下棋的状态
							rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
							if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
								resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
							}
						}
					} else {// 假如我是黑方
						if (temp.index < 16) {// 上一步是我下的，回退一步即可
							rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
							if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
								resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
							}
						} else {// 上一步是对方下的，需要回退两步
							// 第一次回退，此时回退到的状态是我刚下完棋轮到对方下棋的状态
							rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
							if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
								resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
							}
							temp = list.get(list.size() - 1);
							list.remove(list.size() - 1);
							// 第二次回退，此时回退到的状态是我上一次刚轮到我下棋的状态
							rebackChess(temp.index, temp.x, temp.y, temp.oldX, temp.oldY);// 回退棋子
							if (temp.eatChessIndex != -1) {// 如果上一步吃了子，将被吃的子重新放回到棋盘
								resetChess(temp.eatChessIndex, temp.x, temp.y);// 将被吃的棋子放回棋盘
							}
						}
					}
					SetMyTurn(true);
					repaint();
				} else if (commands[1].equals("refuse")) {// 对方拒绝悔棋
					JOptionPane.showMessageDialog(null, "对方拒绝了你的悔棋请求");
				} else if (commands[1].equals("MSG")) {// 处理对方发来的信息
					System.out.println();
					CHESS_GUIchess.LogArea.append("[" + commands[3] + "]：" + commands[2] + "\n");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
