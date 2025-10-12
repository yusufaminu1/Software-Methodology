package chess;

public class BlackPawn extends BlackPiece {
    public BlackPawn(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean checkSelfCheck) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        // Pawn move
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
        // Capture move
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
                // En Passant
                else if(Chess.board[to[0]-1][to[1]] instanceof WhitePawn&&to[0]-1==4&&Chess.board[to[0]-1][to[1]].moveNumber==1){
                    if(checkSelfCheck){
                        if(!SelfCheck(from, to)){
                            return false;
                        }
                    }
                    
                    int indexFrom = Chess.board[to[0]-1][to[1]].index;
                    Chess.currentGame.remove(Chess.board[to[0]-1][to[1]].index);
                    for(int i =0;i<8;i++){
                        for(int j = 0;j<8;j++){
                            if(Chess.board[i][j]!=null&&Chess.board[i][j].index >indexFrom){
                                Chess.board[i][j].index--;
                            }
                        }
                    }
                    Chess.board[to[0]-1][to[1]]=null;
                    return true;
                }
                
        }
    }
        return false;
       
    }

}
