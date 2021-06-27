//客户端
package Client;
import javax.swing.*;
 
public class TyWr_GameForm extends JFrame {
	private final TyWr_GamePanel gp;
	private final String username;
	public TyWr_GameForm(String username,String ip,int port)
	{   this.username=username;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		String nickName=JOptionPane.showInputDialog("输入昵称");
		this.setTitle("玩家:"+username);
		gp=new TyWr_GamePanel(username,ip,port);
		this.add(gp);
		gp.setFocusable(true);
		this.setSize(gp.getWidth(), gp.getHeight());
		this.setResizable(true);
		this.setVisible(true);
	}
	
//	public static void main(String[] args) {
//		new TyWrGameForm();
//	}
// 
}