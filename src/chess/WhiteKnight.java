package chess;

public class WhiteKnight extends WhitePiece {
    
    public WhiteKnight(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        if(!(Math.abs(upDown)==1&&Math.abs(leftRight)==2)&&!(Math.abs(upDown)==2&&Math.abs(leftRight)==1)){
            return false;
        }else{
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else if (Chess.board[to[0]][to[1]] instanceof BlackPiece){
                    Chess.currentGame.set(Chess.board[to[0]][to[1]].index,null);
                    return true;
            }else{
                return true;
            }
        }
        
    }

}
