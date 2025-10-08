package chess;

import chess.ReturnPiece.PieceFile;

public class WhiteKing extends WhitePiece {
    
    public WhiteKing(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean checkSelfCheck) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        // King move one step in any direction 
        if(Math.abs(upDown)==Math.abs(leftRight)&&Math.abs(leftRight)==1){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else{
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
               
                Chess.whiteKing[0] = to[0];
                Chess.whiteKing[1] = to[1];
                return true;
            }
        }else if (upDown==0&&(leftRight==1||leftRight==-1)){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else{
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                
                Chess.whiteKing[0] = to[0];
                Chess.whiteKing[1] = to[1];
                return true;
            }
        }else if(leftRight==0&&(upDown==1||upDown==-1)){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else{
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                
                Chess.whiteKing[0] = to[0];
                Chess.whiteKing[1] = to[1];
                return true;
            }
        // Castling
        }else if(moveNumber==0&&(to[0]==7&&(to[1]==2||to[1]==6))){
            if(to[1]==6&&Chess.board[7][7] instanceof WhiteRook&&Chess.board[7][7].moveNumber==0&&Chess.board[7][6]==null&&Chess.board[7][5]==null){
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                Chess.board[7][5]= Chess.board[7][7];
				Chess.board[7][7]=null;
				ReturnPiece changePiece = Chess.currentGame.get(Chess.board[7][5].index);
				changePiece.pieceFile = PieceFile.valueOf("f");
				changePiece.pieceRank = 1;
				Chess.currentGame.set(Chess.board[7][5].index, changePiece);
                
                Chess.whiteKing[0] = to[0];
                Chess.whiteKing[1] = to[1];
                return true;
            }else if(to[1]==2&&Chess.board[7][0] instanceof WhiteRook&&Chess.board[7][0].moveNumber==0&&Chess.board[7][1]==null&&Chess.board[7][2]==null&&Chess.board[7][3]==null){
                if(checkSelfCheck){
                    if(!SelfCheck(from, to)){
                        return false;
                    }
                }
                Chess.board[7][3]= Chess.board[7][0];
				Chess.board[7][0]=null;
				ReturnPiece changePiece = Chess.currentGame.get(Chess.board[7][3].index);
				changePiece.pieceFile = PieceFile.valueOf("d");
				changePiece.pieceRank = 1;
				Chess.currentGame.set(Chess.board[7][3].index, changePiece);
                Chess.whiteKing[0] = to[0];
                Chess.whiteKing[1] = to[1];
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    boolean SelfCheck(int[] from, int[] to) {
        Piece[] temp = new Piece[1];
        Chess.whiteKing[0] = to[0];
        Chess.whiteKing[1] = to[1];
        if (Chess.board[to[0]][to[1]] != null) {
            temp[0] = Chess.board[to[0]][to[1]];
        }
        Chess.board[to[0]][to[1]] = Chess.board[from[0]][from[1]];
        Chess.board[from[0]][from[1]] = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8;j++) {
                int[] from2 = { i, j };
                if (Chess.board[i][j] instanceof BlackPiece && Chess.board[i][j].move(from2, Chess.whiteKing,false)) {
                    Chess.board[from[0]][from[1]] = Chess.board[to[0]][to[1]];
                    Chess.board[to[0]][to[1]] = temp[0];
                    Chess.whiteKing[0] = from[0];
                    Chess.whiteKing[1] = from[1];
                    return false;
                }
            }
        }
        Chess.board[from[0]][from[1]] = Chess.board[to[0]][to[1]];
        Chess.board[to[0]][to[1]] = temp[0];
        Chess.whiteKing[0] = from[0];
        Chess.whiteKing[1] = from[1];
        return true;
    }

}
