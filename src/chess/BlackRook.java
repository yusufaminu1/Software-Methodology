package chess;

public class BlackRook extends BlackPiece{
    
    public BlackRook(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean ifSelfCheck) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        if (upDown==0&&(leftRight>0||leftRight<0)){
            for(int i = 1;i<Math.abs(leftRight);i++){
                if(leftRight>0&&Chess.board[from[0]][from[1]+i]!=null){
                    return false;
                }
                if(leftRight<0&&Chess.board[from[0]][from[1]-i]!=null){
                    return false;
                }
            }
            if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                return false;
            }else{
                if(ifSelfCheck){
                    if(!checkSelfCheck(from, to)){
                        return false;
                    }
                }
                return true;
            }
        }else if(leftRight==0&&(upDown>0||upDown<0)){
           for(int i = 1;i<Math.abs(upDown);i++){
                if(upDown>0&&Chess.board[from[0]-i][from[1]]!=null){
                    return false;
                }
                if(upDown<0&&Chess.board[from[0]+i][from[1]]!=null){
                    return false;
                }
            }
            if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                return false;
            }else{
                if(ifSelfCheck){
                    if(!checkSelfCheck(from, to)){
                        return false;
                    }
                }
                return true;
            }

        }else{
            return false;
        }
    }

}
