package Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
public class ServerMG {
   private static final ServerMG Smg=new ServerMG();
   public static ServerMG getSmg(){
	   return Smg;
   }
	//存储客户端线程
   private final ArrayList<String> userlist=new ArrayList<>();
//	private  ArrayList<Socket> Socketlist=new ArrayList<>();
	private final HashMap<String, Socket> infomap=new HashMap<>();
	public HashMap<String, Socket> getinfomap(){
		return infomap;
	}
	public void putinfomap(String str,Socket socket) {
		infomap.put(str, socket);
	}
   private ServerForm win;
   public  ArrayList<String> getuserlist(){
	   return  userlist;
   }
//   public ArrayList<Socket> getSocketlist(){
//	   return Socketlist;
//   }
   public void setwin(ServerForm win) {
	   this.win =win;
   }
   public void setTextlog(String str) {
	   win.textLog.append(str+"\n");
   }
	//向Arraylist中用户
	public void AddUser(String str) {
		userlist.add(str);
	}
	//向Arraylist中删除客户端user
	public void DeleteUser(String str) {
		userlist.remove(str);
	}
	//向Arraylist中加入客户端线程
//	public void AddSocket(Socket socket) {
//		Socketlist.add(socket);
//	}
	//向Arraylist中删除客户端线程
//	public void DeleteSocket(Socket socket) {
//		Socketlist.remove(Socketlist.indexOf(socket));
//	}
}
