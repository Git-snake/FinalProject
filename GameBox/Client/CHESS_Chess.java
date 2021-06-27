package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class CHESS_Chess {

    public static final short WHITEPLAYER = 1;
    public static final short BLACKPLAYER = 0;
	public short player;
    public String typeName;
    public int x, y;// 网格地图对应的二维数组的下标
    int icH = 60, icW = 60;
    private Image chessImage;// 棋子图案

    public CHESS_Chess(short player, String typeName, int x, int y) {
        this.player = player;
        this.typeName = typeName;
        this.x = x;
        this.y = y;
		if (player == BLACKPLAYER) {
			switch (typeName) {
				case "黑色国王":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackKing.png").getScaledInstance(icH, icW, 2);
					break;
				case "黑色皇后":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackQueen.png").getScaledInstance(icH, icW,
							2);
					break;
				case "黑色战车":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackRook.png").getScaledInstance(icH, icW,
							2);
					break;
				case "黑色主教":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackBishop.png").getScaledInstance(icH, icW,
							2);
					break;
				case "黑色骑士":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackKnight.png").getScaledInstance(icH, icW,
							2);
					break;
				case "黑色禁卫军":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackPawn.png").getScaledInstance(icH, icW, 2);
					break;
			}
		} else {
			switch (typeName) {
				case "白色国王":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteKing.png").getScaledInstance(icH, icW, 2);
					break;
				case "白色皇后":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteQueen.png").getScaledInstance(icH, icW,
							2);
					break;
				case "白色战车":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteRook.png").getScaledInstance(icH, icW,
							2);
					break;
				case "白色主教":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteBishop.png").getScaledInstance(icH, icW,
							2);
					break;
				case "白色骑士":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteKnight.png").getScaledInstance(icH, icW,
							2);
					break;
				case "白色禁卫军":
					chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whitePawn.png").getScaledInstance(icH, icW, 2);
					break;
			}
		}
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void ReversePos() {
        x = 7 - x;
        y = 7 - y;
    }

    protected void paint(Graphics g, JPanel i) {
        g.drawImage(chessImage, x* icW, y * icH, 60, 60, i);
    }

    // 绘画选中框
    public void DrawSelectedChess(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; //首先把g转换成g2d
        g2d.setColor(Color.RED);//设置颜色,可以省略
        g2d.setStroke(new BasicStroke(5.0f));//关键,设置画笔的宽度. 越大,边框越粗
        g2d.drawRect(x * 60, y * 60, 60, 60);//画矩形
    }

    public void changeType(String typeName) {
        this.typeName = typeName;
		switch (typeName) {
			case "黑色皇后":
				this.chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackKQueen.png").getScaledInstance(icH, icW,
						2);
				break;
			case "黑色战车":
				this.chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackKRook.png").getScaledInstance(icH, icW,
						2);
				break;
			case "黑色主教":
				this.chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackBishop.png").getScaledInstance(icH, icW,
						2);
				break;
			case "黑色骑士":
				this.chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\blackKnight.png").getScaledInstance(icH, icW,
						2);
				break;
			case "白色皇后":
				this.chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteKQueen.png").getScaledInstance(icH, icW,
						2);
				break;
			case "白色战车":
				this.chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteKRook.png").getScaledInstance(icH, icW,
						2);
				break;
			case "白色主教":
				this.chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteBishop.png").getScaledInstance(icH, icW,
						2);
				break;
			case "白色骑士":
				this.chessImage = Toolkit.getDefaultToolkit().getImage("imgs\\whiteKnight.png").getScaledInstance(icH, icW,
						2);
				break;
		}
    }
}
