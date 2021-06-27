package Client;

import java.net.Socket;
import java.util.Stack;

public class Gobang_ClientMG {
	private Gobang_SocketThread th;
	private Socket socket;
	private static final Gobang_ClientMG clientmg =new Gobang_ClientMG();
	private Gobang_ClientMG() {}
	public static Gobang_ClientMG getClientMG() {
		return clientmg;
	}
	
	private Gobang_Game_Client gs;
	public void  setGame_Client(Gobang_Game_Client gs) {
		this.gs =  gs;
	}
	 public void setopponent(String op) {
	    	gs.setopponent(op);
	    }
	 public String getopponent(){
		 return gs.opponent;
	 }
	public void setTextArea(String txt) {
		gs.textArea.append(txt+"\n");
	}
	
	public boolean connect(String IP,int port,String user) {
		try {
			socket = new Socket(IP,port);
			th = new Gobang_SocketThread(socket, user);
			th.start();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public String getName() {
		if(th != null) {
			return th.getName();
		}else {
			return "";
		}
	}
	
	public void sendMSG(String msg) {
		if(th != null) {
			th.sendMSG(msg);
		}
	}
	
	public void setUser(String B, String W) {
		gs.label.setText(B);
		gs.label_1.setText(W);
	}
	
	public void xiugaiqipan(int i,int j,int num) {
		gs.setChess(i, j, num);
	}
	
	public void setType(int n) {
		gs.setClessType(n);
	}
	
	public void reStart() {
		th.reStart();
	}
	
	public void setFlag(int num) {
		gs.setFlag(num);
	}
	
	public void reChess() {
		gs.reChess();
	}
	
	public void setF(int num) {
		gs.setF(num);
	}
	
	Stack<String> stackC = new Stack<>();
	public void PushC(int x,int y) {
		String s = x +"|"+ y;
		stackC.push(s);
	}
	
	public String PopC() {
		if(!stackC.isEmpty())
			return stackC.pop();
		return "";
	}
	
	public void ClearC() {
		stackC.clear();
	}
	
	Stack<String> stackS = new Stack<>();
	public void PushS(int x,int y) {
		String s = x +"|"+ y;
		stackS.push(s);
	}
	
	public String PopS() {
		if(!stackS.isEmpty())
			return stackS.pop();
		return "";
	}
	
	public void ClearS() {
		stackS.clear();
	}
	
	public void setCnt() {
		gs.setCnt();
	}
	public void RefreshLi(String userss) {
		String[] users=userss.split("_");
		gs.Refreashlist(users);
	}
}
