package chess;

import chess.ReturnPiece.PieceFile;

public class WhiteKing extends WhitePiece {
    
    public WhiteKing(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to) {
        upDown = from[0]-to[0];
        leftRight = to[1]-from[1];
        if(Math.abs(upDown)==Math.abs(leftRight)&&Math.abs(leftRight)==1){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                Chess.currentGame.set(Chess.board[to[0]][to[1]].index,null);
                moveNumber++;
                return true;
            }else{
                moveNumber++;
                return true;
            }
        }else if (upDown==0&&(leftRight==1||leftRight==-1)){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                Chess.currentGame.set(Chess.board[to[0]][to[1]].index,null);
                moveNumber++;
                return true;
            }else{
                moveNumber++;
                return true;
            }
        }else if(leftRight==0&&(upDown==1||upDown==-1)){
            if(Chess.board[to[0]][to[1]] instanceof WhitePiece){
                return false;
            }else if(Chess.board[to[0]][to[1]] instanceof BlackPiece){
                Chess.currentGame.set(Chess.board[to[0]][to[1]].index,null);
                moveNumber++;
                return true;
            }else{
                moveNumber++;
                return true;
            }
        }else if(moveNumber==0&&(to[0]==7&&(to[1]==2||to[1]==6))){
            if(to[1]==6&&Chess.board[7][7] instanceof WhiteRook&&Chess.board[7][7].moveNumber==0&&Chess.board[7][6]==null&&Chess.board[7][5]==null){
                Chess.board[7][5]= Chess.board[7][7];
				Chess.board[7][7]=null;
				ReturnPiece changePiece = Chess.currentGame.get(Chess.board[7][5].index);
				changePiece.pieceFile = PieceFile.valueOf("f");
				changePiece.pieceRank = 1;
				Chess.currentGame.set(Chess.board[7][5].index, changePiece);
                moveNumber++;
                return true;
            }else if(to[1]==2&&Chess.board[7][0] instanceof WhiteRook&&Chess.board[7][0].moveNumber==0&&Chess.board[7][1]==null&&Chess.board[7][2]==null&&Chess.board[7][3]==null){
               Chess.board[7][3]= Chess.board[7][0];
				Chess.board[7][0]=null;
				ReturnPiece changePiece = Chess.currentGame.get(Chess.board[7][3].index);
				changePiece.pieceFile = PieceFile.valueOf("d");
				changePiece.pieceRank = 1;
				Chess.currentGame.set(Chess.board[7][3].index, changePiece);
                moveNumber++;
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
