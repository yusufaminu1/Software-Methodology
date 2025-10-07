package chess;

public class BlackPawn extends BlackPiece {
    boolean firstMove=true;
    public BlackPawn(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean checkSelfCheck) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        
        if(leftRight==0){
            if(Chess.board[to[0]][to[1]]!=null){
                    return false;
            }
            if(upDown<-2||upDown>-1){
                return false;
            }else if(upDown==-1){
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                return true;
            }else if(upDown==-2&&moveNumber==0){
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                return true;
            }
        }else if(leftRight==-1||leftRight==1){
            if(upDown!=-1){
                return false;
            }else{
                if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                    if(checkSelfCheck){
                        if(!SelfCheck(from, to)){
                            return false;
                        }
                    }
                    return true;
                }
                else if(Chess.board[to[0]-1][to[1]] instanceof WhitePawn&&to[0]-1==4&&Chess.board[to[0]-1][to[1]].moveNumber==1){
                    if(checkSelfCheck){
                        if(!SelfCheck(from, to)){
                            return false;
                        }
                    }
                    Chess.currentGame.set(Chess.board[to[0]-1][to[1]].index,null);
                    Chess.board[to[0]-1][to[1]]=null;
                    return true;
                }
                
        }
    }
        return false;
       
    }

}
