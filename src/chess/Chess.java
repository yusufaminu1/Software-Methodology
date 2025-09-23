package chess;

import java.util.ArrayList;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;


public class Chess {

		static ArrayList<ReturnPiece> currentGame = new ArrayList<>();
		static Piece[][] board = new Piece[8][8];
		static int moveNumber = 1;
        enum Player { white, black }
    
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

        ReturnPlay returnPlay = new ReturnPlay();
		returnPlay.piecesOnBoard = currentGame;
		returnPlay.message=null;
		
		int[] moveFrom = {9-Character.getNumericValue(move.charAt(1))-1,move.charAt(0)-'a'};
		int[] moveTo = {9-Character.getNumericValue(move.charAt(4))-1,move.charAt(3)-'a'};
		
		if(board[moveFrom[0]][moveFrom[1]]==null){
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;

		}else if(moveNumber%2==1&&!(board[moveFrom[0]][moveFrom[1]] instanceof WhitePiece)){
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
		}else if(moveNumber%2==0&&!(board[moveFrom[0]][moveFrom[1]] instanceof BlackPiece)){
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
		}
		if(returnPlay.message==null){
			if(board[moveFrom[0]][moveFrom[1]].move(moveFrom,moveTo)){
				board[moveTo[0]][moveTo[1]]= board[moveFrom[0]][moveFrom[1]];
				board[moveFrom[0]][moveFrom[1]]=null;
				ReturnPiece changePiece = returnPlay.piecesOnBoard.get(board[moveTo[0]][moveTo[1]].index);
				changePiece.pieceFile = PieceFile.valueOf(""+(char)(moveTo[1]+'a'));
				changePiece.pieceRank = 9 - moveTo[0]-1;
				returnPlay.piecesOnBoard.set(board[moveTo[0]][moveTo[1]].index, changePiece);
			}else{
				returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
			}
		}
		moveNumber++;
		/* FILL IN THIS METHOD */
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return returnPlay;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		int index = 0;
		currentGame.clear();
		for(int i = 0;i<2;i++){
            for(int j = 0;j<8;j++){
				ReturnPiece tempPiece = new ReturnPiece();
				Piece tempPieceType;
                if(i==1){
                    tempPiece.pieceType = PieceType.BP;
					tempPieceType = new BlackPawn(index);
                }else{
					if(j==0||j==7){
                    	tempPiece.pieceType = PieceType.BR;
						tempPieceType = new BlackRook(index);
					}else if(j==1||j==6){
                    	tempPiece.pieceType = PieceType.BN;
						tempPieceType = new BlackKnight(index);
					}else if(j==2||j==5){
                    	tempPiece.pieceType = PieceType.BB;
						tempPieceType = new BlackBishop(index);
					}else if(j == 3){
                    	tempPiece.pieceType = PieceType.BQ;
						tempPieceType = new BlackQueen(index);
					}else {
                    	tempPiece.pieceType = PieceType.BK;
						tempPieceType = new BlackKing(index);
					}
                }
				index++;
				tempPiece.pieceFile = PieceFile.valueOf(""+(char)(j+'a'));
				tempPiece.pieceRank = 9-i-1;
				board[i][j]=tempPieceType;
				currentGame.add(tempPiece);
            }
        }
        for(int i = 7;i>5;i--){
            for(int j = 0;j<8;j++){
				ReturnPiece tempPiece = new ReturnPiece();
				Piece tempPieceType;
                if(i==6){
                    tempPiece.pieceType = PieceType.WP;
					tempPieceType = new WhitePawn(index);
                }else{
					if(j==0||j==7){
                    	tempPiece.pieceType = PieceType.WR;
						tempPieceType = new WhiteRook(index);
					}else if(j==1||j==6){
                    	tempPiece.pieceType = PieceType.WN;
						tempPieceType = new WhiteKnight(index);
					}else if(j==2||j==5){
                    	tempPiece.pieceType = PieceType.WB;
						tempPieceType = new WhiteBishop(index);
					}else if(j == 3){
                    	tempPiece.pieceType = PieceType.WQ;
						tempPieceType = new WhiteQueen(index);
					}else {
                    	tempPiece.pieceType = PieceType.WK;
						tempPieceType = new WhiteKing(index);
					}
                }
				index++;
				tempPiece.pieceFile = PieceFile.valueOf(""+(char)(j+'a'));
				tempPiece.pieceRank = 9-i-1;
				board[i][j]=tempPieceType;
				currentGame.add(tempPiece);

            }
			moveNumber=1;
        }
		/* FILL IN THIS METHOD */
	}
}
