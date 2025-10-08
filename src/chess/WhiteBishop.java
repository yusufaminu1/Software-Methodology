package chess;

public class WhiteBishop extends WhitePiece{
    
    public WhiteBishop(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean checkSelfCheck) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        // Bishop moves in diagonal line
        if(Math.abs(upDown)!=Math.abs(leftRight)){
            return false;
        }else{
            for(int i = 1;i<Math.abs(upDown);i++){
                if(upDown>0&&leftRight>0&&Chess.board[from[0]-i][from[1]+i]!=null){
                    return false;
                }
                if(upDown<0&&leftRight>0&&Chess.board[from[0]+i][from[1]+i]!=null){
                    return false;
                }
                if(upDown>0&&leftRight<0&&Chess.board[from[0]-i][from[1]-i]!=null){
                    return false;
                }
                if(upDown<0&&leftRight<0&&Chess.board[from[0]+i][from[1]-i]!=null){
                    return false;
                }
            }
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else{
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                return true;
            }
        }
    }

}
