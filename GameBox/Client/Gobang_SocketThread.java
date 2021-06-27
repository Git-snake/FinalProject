package Client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Gobang_SocketThread extends Thread {
	BufferedReader br = null;
	PrintWriter pw = null;
	Socket socket = null;
	private final int[][] chess = new int[15][15];
	private final int iw = 20 + 15;
	private final int ih = 20 + 25;
	private int chessType;
	private int f = -1;
	public String fColor = null;

	public Gobang_SocketThread(Socket socket, String name) {
		// TODO Auto-generated constructor stub
		super(name);
		this.socket = socket;
		for (int i = 0; i < chess.length; i++) {
			for (int j = 0; j < chess[i].length; j++) {
				chess[i][j] = 0;
			}
		}
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)));

			Gobang_Game_Client gs = new Gobang_Game_Client(getName());
			Gobang_ClientMG.getClientMG().setGame_Client(gs);
			gs.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					if (JOptionPane.showConfirmDialog(null, "您确认退出游戏吗？", "退出游戏",
							JOptionPane.ERROR_MESSAGE) == JOptionPane.OK_OPTION) {
						Gobang_ClientMG.getClientMG().sendMSG("GOBANG|EXIT|" + getName());

					} else {
						gs.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
				}
			});

			String string = "LOGIN|GOBANG|" + getName();
			Gobang_ClientMG.getClientMG().sendMSG(string);
//			work();         //向对方发去自己的颜色
			String str = "";
			while ((str = br.readLine()) != null) {
//				System.out.println(str);

				String[] res = str.split("@");
				if (res[0].equals("MSG")) {
					Gobang_ClientMG.getClientMG().setTextArea("[" + res[1] + "]:" + res[2]);
				} else if (res[0].equals("POS")) {
					int i = Integer.parseInt(res[1]);
					int j = Integer.parseInt(res[2]);
					chess[i][j] = chessType == 1 ? 2 : 1;
					Gobang_ClientMG.getClientMG().xiugaiqipan(i, j, chess[i][j]);
					Gobang_ClientMG.getClientMG().setF(1);
					Gobang_ClientMG.getClientMG().PushS(i, j);
				} else if (res[0].equals("COLOR")) {
					if (res[1].equals("White")) {
						int k = 1;
//						Gobang_ClientMG.getClientMG().setF(f);
						if (k == f) {
							work();
						}
					} else {
						String Bname = res[1].equals("Black") ? getName() : res[2];
						String Wname = res[1].equals("Black") ? res[2] : getName();
						if (Bname.equals(getName())) {
							chessType = 1;
							f = 1;
						} else {
							chessType = 2;
							f = 0;
						}
						Gobang_ClientMG.getClientMG().setF(f);
						Gobang_ClientMG.getClientMG().setType(chessType);
						Gobang_ClientMG.getClientMG().setUser(Bname, Wname);
						String strname = "GOBANG|NAME@" + getName() + "|" + Gobang_ClientMG.getClientMG().getopponent();
						Gobang_ClientMG.getClientMG().sendMSG(strname);
						System.out.print(strname);
					}
				} else if (res[0].equals("NAME")) {
					String Bname = fColor.equals("Black") ? res[1] : getName();
					String Wname = fColor.equals("Black") ? getName() : res[1];
					if (Bname.equals(getName())) {
						chessType = 1;
					} else {
						chessType = 2;
					}
					Gobang_ClientMG.getClientMG().setType(chessType);
					Gobang_ClientMG.getClientMG().setUser(Bname, Wname);
				} else if (res[0].equals("WON")) {
					JOptionPane.showMessageDialog(null, chessType == 1 ? "白子赢了！" : "黑子赢了！");
					Gobang_ClientMG.getClientMG().setFlag(1);
				} else if (res[0].equals("RESTART")) {
					int userOption = JOptionPane.showConfirmDialog(null, "对方请求重新开始，是否同意？", "提示", JOptionPane.OK_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (userOption == JOptionPane.OK_OPTION) {
						Gobang_ClientMG.getClientMG()
								.sendMSG("GOBANG|AGREE|" + Gobang_ClientMG.getClientMG().getopponent());
						Gobang_ClientMG.getClientMG().reStart();
						Gobang_ClientMG.getClientMG().reChess();
						Gobang_ClientMG.getClientMG().ClearC();
						work();
					} else {
						Gobang_ClientMG.getClientMG()
								.sendMSG("GOBANG|REFUSE|" + Gobang_ClientMG.getClientMG().getopponent());
					}
				} else if (res[0].equals("AGREE")) {
					Gobang_ClientMG.getClientMG().reStart();
					Gobang_ClientMG.getClientMG().reChess();
					Gobang_ClientMG.getClientMG().ClearC();
				} else if (res[0].equals("REFUSE")) {
					JOptionPane.showMessageDialog(null, "对方拒绝重新开始！");
				} else if (res[0].equals("LOST")) {
					JOptionPane.showMessageDialog(null, "对方认输，恭喜你获胜！");
					Gobang_ClientMG.getClientMG().setFlag(1);
				} else if (res[0].equals("BACK")) {
					int userOption = JOptionPane.showConfirmDialog(null, "对方请求悔棋，是否同意？", "提示", JOptionPane.OK_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (userOption == JOptionPane.OK_OPTION) {
						Gobang_ClientMG.getClientMG()
								.sendMSG("GOBANG|OK|" + Gobang_ClientMG.getClientMG().getopponent());
						Gobang_ClientMG.getClientMG().setF(0);
						String[] ss = Gobang_ClientMG.getClientMG().PopS().split("\\|");
						int i = Integer.parseInt(ss[0]);
						int j = Integer.parseInt(ss[1]);
						chess[i][j] = 0;
						Gobang_ClientMG.getClientMG().xiugaiqipan(i, j, chess[i][j]);
					} else {
						Gobang_ClientMG.getClientMG()
								.sendMSG("GOBANG|NO|" + Gobang_ClientMG.getClientMG().getopponent());
					}
				} else if (res[0].equals("OK")) {
					Gobang_ClientMG.getClientMG().setF(1);
					String[] ss = Gobang_ClientMG.getClientMG().PopC().split("\\|");
					int x = Integer.parseInt(ss[0]);
					int y = Integer.parseInt(ss[1]);
					chess[x][y] = 0;
					Gobang_ClientMG.getClientMG().xiugaiqipan(x, y, chess[x][y]);
					Gobang_ClientMG.getClientMG().setCnt();
				} else if (res[0].equals("NO")) {
					JOptionPane.showMessageDialog(null, "对方拒绝!");
				} else if (res[0].equals("REFREASH")) {
					Gobang_ClientMG.getClientMG().RefreshLi(res[1]);
				} else if (res[0].equals("INVITE")) {
					if (JOptionPane.showConfirmDialog(gs, res[1] + "向你发来对战邀请，是否接受？") == JOptionPane.YES_OPTION) {
						Gobang_ClientMG.getClientMG().setopponent(res[1]);
						work();
					} else {
						Gobang_ClientMG.getClientMG()
								.sendMSG("GOBANG|NO|" + Gobang_ClientMG.getClientMG().getopponent());
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (pw != null)
					pw.close();
				if (br != null)
					br.close();
				if (socket != null)
					socket.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

	public void sendMSG(String str) {
		pw.println(str);
		pw.flush();
	}

	// 随机生成颜色
	public String First() {
		double m = Math.random();
		double v = Math.random();
		if (m > v) {
			return "Black";
		} else {
			return "White";
		}
	}

	public void work() {
		fColor = First();
		String string = "GOBANG|COLOR@" + fColor + "@" + getName() + "|" + Gobang_ClientMG.getClientMG().getopponent();
		Gobang_ClientMG.getClientMG().sendMSG(string);
		if (fColor.equals("White")) {
			f = 1;
			Gobang_ClientMG.getClientMG().setF(f);
		} else {
			f = 0;
			Gobang_ClientMG.getClientMG().setF(f);
		}
	}

	public void Close() {
		try {
			if (pw != null)
				pw.close();
			if (br != null)
				br.close();
			if (socket != null)
				socket.close();
//			System.out.println("客户端关闭");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void reStart() {
		f = -1;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				chess[i][j] = 0;
			}
		}
	}
}
