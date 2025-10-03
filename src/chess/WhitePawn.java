package chess;

public class WhitePawn extends WhitePiece{
    boolean firstMove=true;
    public WhitePawn(int index) {
        
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        
        if(leftRight==0){
            if(Chess.board[to[0]][to[1]]!=null){
                    return false;
            }
            if(upDown>2||upDown<1){
                return false;
            }else if(upDown==1){
                firstMove=false;
                moveNumber++;
                
                return true;
            }else if(upDown==2&&firstMove==true){
                firstMove=false;
                moveNumber++;
                return true;
            }
        }else if(leftRight==-1||leftRight==1){
            if(upDown!=1){
                return false;
            }else{
                if(Chess.board[to[0]+1][to[1]] instanceof BlackPawn&&to[0]+1==3&&Chess.board[to[0]+1][to[1]].moveNumber==1){
                    Chess.currentGame.set(Chess.board[to[0]+1][to[1]].index,null);
                    Chess.board[to[0]+1][to[1]]=null;
                    moveNumber++;
                    return true;
                }
                if(Chess.board[to[0]][to[1]]==null||Chess.board[to[0]][to[1]] instanceof WhitePiece){
                    return false;
                }else{
                    Chess.currentGame.set(Chess.board[to[0]][to[1]].index,null);
                    firstMove=false;
                    moveNumber++;
                    return true;
                }
            }
        }
        
        firstMove=false;
        return false;
       
    }
   

}
