package Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketThread extends Thread {
	String AllUsers = "";
	Socket client1 = null;
	PrintWriter ou = null;
	BufferedReader in = null;
	ObjectInputStream objectin = null;

	public SocketThread(Socket socket) {
		this.client1 = socket;
	}

	public static void SendSocketMess(Socket client, String Stri, String sendside) {
		PrintWriter ouu = null;
//		 BufferedReader inn=null;
		try {
			ouu = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8)));
//			in=new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
			ouu.println(sendside + ":" + Stri);
			ouu.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

	}

	// 发送给所有服务器消息
	public static void SendSocketMess(Socket client, String Stri) {
		PrintWriter ouu = null;
		try {
			ouu = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8)));
			ouu.println(Stri);
			ouu.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			in = new BufferedReader(new InputStreamReader(client1.getInputStream(), StandardCharsets.UTF_8));
			ou = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client1.getOutputStream(), StandardCharsets.UTF_8)));
			String read = null;
			if (!client1.isClosed()) {
				while ((read = in.readLine()) != null) {
					if (read.equals("END"))
						break;
					ServerMG.getSmg().setTextlog(read);
					String[] commands = read.trim().split("\\|");
					String protocol = commands[0];

					if (protocol.equals("MESS")) {
						String Message = commands[1];
						String sendside = commands[2];
						String acceptside = commands[3];
						// SendSocketMess(ServerMG.getSmg().getSocketlist().get(ServerMG.getSmg().getuserlist().indexOf(acceptside)),
						// Message,sendside);
					}
					// 游戏盒子玩家登陆
					else if (protocol.equals("LOGIN")) {
						ServerMG.getSmg().AddUser(commands[1] + "|" + commands[2]);
						ServerMG.getSmg().putinfomap(commands[1] + "|" + commands[2], client1);
						ServerMG.getSmg().setTextlog(commands[1] + "|" + commands[2] + "登录");

						if (commands[1].equals("GOBANG")) {
							String AllUsers = "";
							for (String n : ServerMG.getSmg().getuserlist()) {
								String[] name = n.split("\\|");
								if (name[0].equals("GOBANG"))
									AllUsers += name[1] + "_";
							}
							for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
								if ((userinfo.split("\\|"))[0].equals("GOBANG"))
									SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo),
											"REFREASH@" + AllUsers.trim());
							}
						}
						if (commands[1].equals("CHESS")) {
							String AllUsers = "";
							for (String n : ServerMG.getSmg().getuserlist()) {
								String[] name = n.split("\\|");
								if (name[0].equals("CHESS")&&!name[1].equals(commands[2]))
									AllUsers += name[1] + "_";
							}
							for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
								if ((userinfo.split("\\|"))[0].equals("CHESS")) {
									if((userinfo.split("\\|"))[1].equals(commands[2]))
										SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo),
											"USERLISTS|" + AllUsers.trim());
									else{
										SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo),
												"ADD|" +commands[2].trim());
									}
								}
							}
						}

					}
					// 打字游戏服务器中转
					else if (protocol.equals("TypeGame")) {
						for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
							if ((userinfo.split("\\|"))[0].equals("TypeGame"))
								SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo), commands[1].trim());
						}
					} // 结束
//				  else if(protocol.equals("EXIT")) {
//					  ServerMG.getSmg().getuserlist().remove((String)commands[1]);
//					for(Socket client:ServerMG.getSmg().getSocketlist()) {
//						 SendSocketMess(client, "REMOVE|"+commands[1]);
//					}
//					ServerMG.getSmg().getSocketlist().get(ServerMG.getSmg().getuserlist().indexOf(commands[1])).close();
//				  }
					// 五子棋的服务器中转协议
					else if (protocol.equals("GOBANG")) {

						if (commands[1].equals("INVITE")) {
							for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
								if ((userinfo.split("\\|"))[0].equals("GOBANG")) {
									if ((userinfo.split("\\|"))[1].equals(commands[2].trim()))
										SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo),
												"INVITE@" + commands[2].trim());
								}
							}
						} else if (commands[1].equals("POS")) {
							for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
								if ((userinfo.split("\\|"))[0].equals("GOBANG")) {
									if ((userinfo.split("\\|"))[1].equals(commands[3].trim()))
										SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo),
												"POS@" + commands[2].trim());
								}
							}

						} else if (commands[1].equals("NAME")) {
							for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
								if ((userinfo.split("\\|"))[0].equals("GOBANG")) {
									if ((userinfo.split("\\|"))[1].equals(commands[3].trim()))
										SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo),
												"NAME@" + commands[2].trim());
								}
							}

						} else if (commands[1].equals("EXIT")) {
							ServerMG.getSmg().getinfomap().get("GOBANG|" + commands[2]).close();
							ServerMG.getSmg().getuserlist().remove("GOBANG|" +commands[2]);
							ServerMG.getSmg().getinfomap().remove("GOBANG|" +commands[2]);
							String AllUsers = "";
							for (String n : ServerMG.getSmg().getuserlist()) {
								String[] name = n.split("\\|");
								if (name[0].equals("GOBANG"))
									AllUsers += name[1] + "_";
							}
							for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
								if ((userinfo.split("\\|"))[0].equals("GOBANG")) {
									SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo),
											"REFREASH@" + AllUsers.trim());
								}
							}
						} else {
							for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
								if ((userinfo.split("\\|"))[0].equals("GOBANG"))
									if ((userinfo.split("\\|"))[1].equals(commands[2].trim()))
										SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo), commands[1]);
							}
						}
					} // 五子棋中中转协议结束
					// 国际象棋的服务器中转协议
					else if(protocol.equals("CHESS"))
					{
						if (commands[1].equals("quit")){
							ServerMG.getSmg().getinfomap().get("CHESS|" + commands[2]).close();
							ServerMG.getSmg().getuserlist().remove("CHESS|" +commands[2]);
							ServerMG.getSmg().getinfomap().remove("CHESS|" +commands[2]);
							String AllUsers = "";
							for (String n : ServerMG.getSmg().getuserlist()) {
								String[] name = n.split("\\|");
								if (name[0].equals("CHESS"))
									AllUsers += name[1] + "_";
							}
							for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
								if ((userinfo.split("\\|"))[0].equals("CHESS")) {
									SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo),
											"REFREASH|" +commands[2]);
								}
							}
						}
						StringBuilder sends= new StringBuilder("CHESS");
						for(int i=1;i<commands.length;i++)
							sends.append("|").append(commands[i]);
						for (String userinfo : ServerMG.getSmg().getinfomap().keySet()) {
							if ((userinfo.split("\\|"))[0].equals("CHESS"))
								if ((userinfo.split("\\|"))[1].equals(commands[commands.length - 1].trim()))
									SendSocketMess(ServerMG.getSmg().getinfomap().get(userinfo), sends.toString());
						}
					}
				} // while结束
			} // if结束
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// run
}// 类结束
