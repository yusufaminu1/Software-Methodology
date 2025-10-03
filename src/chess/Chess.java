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
		String sm ="";
		if(move.equals("resign")){
			if(moveNumber%2==1){
				returnPlay.message =ReturnPlay.Message.CHECKMATE_BLACK_WINS;
			}else{
				returnPlay.message =ReturnPlay.Message.CHECKMATE_WHITE_WINS;
			}
			return returnPlay;
		}
		int[] moveFrom = {9-Character.getNumericValue(move.charAt(1))-1,move.charAt(0)-'a'};
		int[] moveTo = {9-Character.getNumericValue(move.charAt(4))-1,move.charAt(3)-'a'};
		if(move.length()>5){
			 sm = move.substring(6);
		}
		
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
				moveNumber++;
				if(board[moveTo[0]][moveTo[1]] instanceof WhitePawn && moveTo[0]==0){
					int index = board[moveTo[0]][moveTo[1]].index;
					if(sm.equals("") || sm.equals("Q")){
						board[moveTo[0]][moveTo[1]] = new WhiteQueen(index);
						changePiece.pieceType = PieceType.WQ;
					}else if(sm.equals("N")){
						board[moveTo[0]][moveTo[1]] = new WhiteKnight(index);
						changePiece.pieceType = PieceType.WN;
					}else if (sm.equals("B")){
						board[moveTo[0]][moveTo[1]] = new WhiteBishop(index);
						changePiece.pieceType = PieceType.WB;
					}else if(sm.equals("R")){
						board[moveTo[0]][moveTo[1]] = new WhiteRook(index);
						changePiece.pieceType = PieceType.WR;
					}
					returnPlay.piecesOnBoard.set(board[moveTo[0]][moveTo[1]].index, changePiece);
				}else if(board[moveTo[0]][moveTo[1]] instanceof BlackPawn && moveTo[0]==7){
					int index = board[moveTo[0]][moveTo[1]].index;
					if(sm.equals("") || sm.equals("Q")){
						board[moveTo[0]][moveTo[1]] = new BlackQueen(index);
						changePiece.pieceType = PieceType.BQ;
					}else if(sm.equals("N")){
						board[moveTo[0]][moveTo[1]] = new BlackKnight(index);
						changePiece.pieceType = PieceType.BN;
					}else if (sm.equals("B")){
						board[moveTo[0]][moveTo[1]] = new BlackBishop(index);
						changePiece.pieceType = PieceType.BB;
					}else if(sm.equals("R")){
						board[moveTo[0]][moveTo[1]] = new BlackRook(index);
						changePiece.pieceType = PieceType.BR;
					}
					returnPlay.piecesOnBoard.set(board[moveTo[0]][moveTo[1]].index, changePiece);
				}
				if(sm.equals("draw?")){
					returnPlay.message = ReturnPlay.Message.DRAW;
				}
			}else{
				returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
			}
		}
		
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
		for(int i = 0;i<board.length;i++){
			for(int j = 0;j<board[1].length;j++){
				board[i][j]=null;
			}
		}
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
