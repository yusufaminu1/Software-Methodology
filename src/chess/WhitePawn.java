package chess;

public class WhitePawn extends WhitePiece{
    boolean firstMove=true;
    public WhitePawn(int index) {
        
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean ifSelfCheck) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        
        if(leftRight==0){
            if(Chess.board[to[0]][to[1]]!=null){
                    return false;
            }
            if(upDown>2||upDown<1){
                return false;
            }else if(upDown==1){
                if(ifSelfCheck){
                    if(!checkSelfCheck(from, to)){
                        return false;
                    }
                }
                return true;
            }else if(upDown==2&&moveNumber==0){
                if(ifSelfCheck){
                    if(!checkSelfCheck(from, to)){
                        return false;
                    }
                }
                return true;
            }
        }else if(leftRight==-1||leftRight==1){
            if(upDown!=1){
                return false;
            }else{
                if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                    if(ifSelfCheck){
                        if(!checkSelfCheck(from, to)){
                            return false;
                        }
                    }
                    return true;
                }else if(Chess.board[to[0]+1][to[1]] instanceof BlackPawn&&to[0]+1==3&&Chess.board[to[0]+1][to[1]].moveNumber==1){
                    if(ifSelfCheck){
                        if(!checkSelfCheck(from, to)){
                            return false;
                        }
                    }
                    Chess.currentGame.set(Chess.board[to[0]+1][to[1]].index,null);
                    Chess.board[to[0]+1][to[1]]=null;
                    return true;
                }
                
            }
        }
        return false;
       
    }
   

}
