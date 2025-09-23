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
        if(Chess.board[to[0]][to[1]]!=null){
                    return false;
                }
        if(leftRight==0){
            if(upDown>2||upDown<1){
                return false;
            }else if(upDown==1){
                return true;
            }else{

            }
        }else{

        }
        
        firstMove=false;
        return true;
       
    }

}
