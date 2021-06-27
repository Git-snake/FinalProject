package Client;

import javax.swing.*;
import java.awt.*;

public class Gobang_MyPanel_Client extends JPanel {

	/**
	 * Create the panel.
	 */
	Image imagePan = new ImageIcon("imgs\\ChessBoard.jpg").getImage();
	Image imageB = new ImageIcon("imgs\\black.png").getImage();
	Image imageW = new ImageIcon("imgs\\white.png").getImage();
	int [][] chess;
	int iw = 20+15;
	int ih = 20+25;
	int ow = 10;
	int oh = 10;
	public Gobang_MyPanel_Client(int [][] c) {
		chess = c;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
//		System.out.println("Client");
		g.drawImage(imagePan, 15, 25, null);
		for (int i=0; i<chess.length; i++) {
			for (int j=0;j<chess[i].length; j++) {
				int x = i*20+iw-ow;
				int y = j*20+ih-oh;
				switch(chess[i][j]) {
					case 1:
						g.drawImage(imageB, x, y, null);
						break;
					case 2:
						g.drawImage(imageW, x, y, null);
						break;
				}
			}
		}
	}
}
