package chess;

public class WhiteKing extends WhitePiece {
    
    public WhiteKing(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        if(Math.abs(upDown)==Math.abs(leftRight)&&Math.abs(leftRight)==1){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                Chess.currentGame.set(Chess.board[to[0]][to[1]].index,null);
                moveNumber++;
                return true;
            }else{
                moveNumber++;
                return true;
            }
        }else if (upDown==0&&(leftRight==1||leftRight==-1)){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                Chess.currentGame.set(Chess.board[to[0]][to[1]].index,null);
                moveNumber++;
                return true;
            }else{
                moveNumber++;
                return true;
            }
        }else if(leftRight==0&&(upDown==1||upDown==-1)){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                Chess.currentGame.set(Chess.board[to[0]][to[1]].index,null);
                moveNumber++;
                return true;
            }else{
                moveNumber++;
                return true;
            }
        }else if(true){
            return false;
        }else{
            return false;
        }
    }

}
