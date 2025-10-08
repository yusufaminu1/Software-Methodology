package chess;

import chess.ReturnPiece.PieceFile;

public class BlackKing extends BlackPiece{
    
    public BlackKing(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean checkSelfCheck) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        // King move one step any direction 
        if(Math.abs(upDown)==Math.abs(leftRight)&&Math.abs(leftRight)==1){
            if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                return false;
            }else{
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                
                Chess.blackKing[0] = to[0];
                Chess.blackKing[1] = to[1];
                return true;
            }
        }else if (upDown==0&&(leftRight==1||leftRight==-1)){
            if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                return false;
            }else{
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                
                Chess.blackKing[0] = to[0];
                Chess.blackKing[1] = to[1];
                return true;
            }
        }else if(leftRight==0&&(upDown==1||upDown==-1)){
            if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                return false;
            }else{
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                
                Chess.blackKing[0] = to[0];
                Chess.blackKing[1] = to[1];
                return true;
            }
        // Castling
        }else if(moveNumber==0&&(to[0]==7&&(to[1]==2||to[1]==6))){
            if(to[1]==6&&Chess.board[0][7] instanceof BlackRook&&Chess.board[0][7].moveNumber==0&&Chess.board[0][6]==null&&Chess.board[0][5]==null){
                if(checkSelfCheck){
                        if(!SelfCheck(from, to)){
                            return false;
                        }
                    }
                Chess.board[0][5]= Chess.board[0][7];
				Chess.board[0][7]=null;
				ReturnPiece changePiece = Chess.currentGame.get(Chess.board[0][5].index);
				changePiece.pieceFile = PieceFile.valueOf("f");
				changePiece.pieceRank = 8;
				Chess.currentGame.set(Chess.board[0][5].index, changePiece);
                Chess.blackKing[0] = to[0];
                Chess.blackKing[1] = to[1];
                return true;
            }else if(to[1]==2&&Chess.board[0][0] instanceof BlackRook&&Chess.board[0][0].moveNumber==0&&Chess.board[0][1]==null&&Chess.board[0][2]==null&&Chess.board[0][3]==null){
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                Chess.board[0][3]= Chess.board[0][0];
				Chess.board[0][0]=null;
				ReturnPiece changePiece = Chess.currentGame.get(Chess.board[0][3].index);
				changePiece.pieceFile = PieceFile.valueOf("d");
				changePiece.pieceRank = 8;
				Chess.currentGame.set(Chess.board[0][3].index, changePiece);
                moveNumber++;
                Chess.blackKing[0] = to[0];
                Chess.blackKing[1] = to[1];
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    // Check if the move will put own king in check
    @Override
    boolean SelfCheck(int[] from, int[] to) {
        Piece[] temp = new Piece[1];
        Chess.blackKing[0] = to[0];
        Chess.blackKing[1] = to[1];
        if (Chess.board[to[0]][to[1]] != null) {
            temp[0] = Chess.board[to[0]][to[1]];
        }
        Chess.board[to[0]][to[1]] = Chess.board[from[0]][from[1]];
        Chess.board[from[0]][from[1]] = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8;j++) {
                int[] from2 = { i, j };
                if (Chess.board[i][j] instanceof WhitePiece && Chess.board[i][j].move(from2, Chess.blackKing,false)) {
                    Chess.board[from[0]][from[1]] = Chess.board[to[0]][to[1]];
                    Chess.board[to[0]][to[1]] = temp[0];
                    Chess.blackKing[0] = from[0];
                    Chess.blackKing[1] = from[1];
                    return false;
                }
            }
        }
        Chess.board[from[0]][from[1]] = Chess.board[to[0]][to[1]];
        Chess.board[to[0]][to[1]] = temp[0];
        Chess.blackKing[0] = from[0];
        Chess.blackKing[1] = from[1];
        return true;
    }
}
