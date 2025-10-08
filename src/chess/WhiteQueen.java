package chess;

public class WhiteQueen extends WhitePiece{
    
    public WhiteQueen(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean checkSelfCheck) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        // Queen move diagonal
        if(Math.abs(upDown)==Math.abs(leftRight)){
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
        // Queen move horizontally
        }else if (upDown==0&&(leftRight>0||leftRight<0)){
            for(int i = 1;i<Math.abs(leftRight);i++){
                if(leftRight>0&&Chess.board[from[0]][from[1]+i]!=null){
                    return false;
                }
                if(leftRight<0&&Chess.board[from[0]][from[1]-i]!=null){
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
        // Queen move vertically
        }else if(leftRight==0&&(upDown>0||upDown<0)){
           for(int i = 1;i<Math.abs(upDown);i++){
                if(upDown>0&&Chess.board[from[0]-i][from[1]]!=null){
                    return false;
                }
                if(upDown<0&&Chess.board[from[0]+i][from[1]]!=null){
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
        }else{
            return false;
        }
    }

}
