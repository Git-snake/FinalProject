package Client;

public class CHESS_Node {
	int index;//移动的棋子下标
	int x,y;//棋子移动后位于(x,y)
	int oldX,oldY;//棋子移动前位于(oldX,oldY)
	int eatChessIndex;//被吃掉的棋子下标
	
	//如果棋子移动过程没有吃子，eatChessIndex = -1;
	public CHESS_Node(int index,int x,int y,int oldX,int oldY,int eatChessIndex){
		this.index = index;
		this.x = x;
		this.y = y;
		this.oldX = oldX;
		this.oldY = oldY;
		this.eatChessIndex = eatChessIndex;
	}
}