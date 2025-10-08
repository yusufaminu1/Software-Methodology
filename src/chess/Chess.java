package chess;

import java.util.ArrayList;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;


public class Chess {

		static ArrayList<ReturnPiece> currentGame = new ArrayList<>();
		static Piece[][] board = new Piece[8][8];
		static int moveNumber = 1;
		static int[] blackKing = new int[2];
		static int[] whiteKing = new int[2];
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
		// Check for resign
		if(move.equals("resign")){
			if(moveNumber%2==1){
				returnPlay.message =ReturnPlay.Message.RESIGN_BLACK_WINS;
			}else{
				returnPlay.message =ReturnPlay.Message.RESIGN_WHITE_WINS;
			}
			return returnPlay;
		}
		// converts the move into array indices
		int[] moveFrom = {9-Character.getNumericValue(move.charAt(1))-1,move.charAt(0)-'a'};
		int[] moveTo = {9-Character.getNumericValue(move.charAt(4))-1,move.charAt(3)-'a'};
		if(move.length()>5){
			 sm = move.substring(6);
		}
		// Check to see if the move is from the right player
		if(board[moveFrom[0]][moveFrom[1]]==null){
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
		}else if(moveNumber%2==1&&!(board[moveFrom[0]][moveFrom[1]] instanceof WhitePiece)){
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
		}else if(moveNumber%2==0&&!(board[moveFrom[0]][moveFrom[1]] instanceof BlackPiece)){
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
		}

		// Move the piece if able
		if(returnPlay.message==null){
			if(board[moveFrom[0]][moveFrom[1]].move(moveFrom,moveTo,true)){
				if((board[moveTo[0]][moveTo[1]] instanceof BlackPiece && moveNumber%2==1)||(board[moveTo[0]][moveTo[1]] instanceof WhitePiece && moveNumber%2==0)){
					Chess.currentGame.set(Chess.board[moveTo[0]][moveTo[1]].index,null);
				}
				board[moveTo[0]][moveTo[1]]= board[moveFrom[0]][moveFrom[1]];
				board[moveFrom[0]][moveFrom[1]]=null;
				ReturnPiece changePiece = returnPlay.piecesOnBoard.get(board[moveTo[0]][moveTo[1]].index);
				changePiece.pieceFile = PieceFile.valueOf(""+(char)(moveTo[1]+'a'));
				changePiece.pieceRank = 9 - moveTo[0]-1;
				returnPlay.piecesOnBoard.set(board[moveTo[0]][moveTo[1]].index, changePiece);
				moveNumber++;
				if(board[moveTo[0]][moveTo[1]] instanceof BlackKing){
						blackKing[0]=moveTo[0];
						blackKing[1]=moveTo[1];
				}
				if(board[moveTo[0]][moveTo[1]] instanceof WhiteKing){
						whiteKing[0]=moveTo[0];
						whiteKing[1]=moveTo[1];
				}
				// Pawn's Promotion	
				if(board[moveTo[0]][moveTo[1]] instanceof WhitePawn && moveTo[0]==0){
					int index = board[moveTo[0]][moveTo[1]].index;
                    switch (sm) {
                        case "", "Q" -> {
                            board[moveTo[0]][moveTo[1]] = new WhiteQueen(index);
                            changePiece.pieceType = PieceType.WQ;
                        }
                        case "N" -> {
                        	board[moveTo[0]][moveTo[1]] = new WhiteKnight(index);
                        	changePiece.pieceType = PieceType.WN;
                        }
                        case "B" -> {
                        	board[moveTo[0]][moveTo[1]] = new WhiteBishop(index);
                        	changePiece.pieceType = PieceType.WB;
                        }
                        case "R" -> {
                        	board[moveTo[0]][moveTo[1]] = new WhiteRook(index);
                        	changePiece.pieceType = PieceType.WR;
                        }
                        default -> {
                        }
                    }
					returnPlay.piecesOnBoard.set(board[moveTo[0]][moveTo[1]].index, changePiece);
				}else if(board[moveTo[0]][moveTo[1]] instanceof BlackPawn && moveTo[0]==7){
					int index = board[moveTo[0]][moveTo[1]].index;
                    switch (sm) {
                        case "", "Q" -> {
                            board[moveTo[0]][moveTo[1]] = new BlackQueen(index);
                            changePiece.pieceType = PieceType.BQ;
                        }
                        case "N" -> {
                            board[moveTo[0]][moveTo[1]] = new BlackKnight(index);
                            changePiece.pieceType = PieceType.BN;
                        }
                        case "B" -> {
                            board[moveTo[0]][moveTo[1]] = new BlackBishop(index);
                            changePiece.pieceType = PieceType.BB;
                    	}
                    	case "R" -> {
                            board[moveTo[0]][moveTo[1]] = new BlackRook(index);
                            changePiece.pieceType = PieceType.BR;
                        }
                    	default -> {
                    	}
                    }
					returnPlay.piecesOnBoard.set(board[moveTo[0]][moveTo[1]].index, changePiece);
				}
				// Check for draw
				if(sm.equals("draw?")){
					returnPlay.message = ReturnPlay.Message.DRAW;
					return returnPlay;
				}
				// Check for a check
				for (int i = 0; i < 8; i++) {
            		for (int j = 0; j < 8;j++) {
                		int[] from2 = { i, j };
                		if ((Chess.board[i][j] instanceof BlackPiece && Chess.board[i][j].move(from2, Chess.whiteKing,false))||(Chess.board[i][j] instanceof WhitePiece && Chess.board[i][j].move(from2, Chess.blackKing,false))) {
							returnPlay.message = ReturnPlay.Message.CHECK;
                		}
            		}
        		}
				board[moveTo[0]][moveTo[1]].moveNumber++;
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
                     switch (j) {
                        case 0, 7 -> {
                            tempPiece.pieceType = PieceType.BR;
                            tempPieceType = new BlackRook(index);
                        }
                        case 1, 6 -> {
                            tempPiece.pieceType = PieceType.BN;
                            tempPieceType = new BlackKnight(index);
                        }
                        case 2, 5 -> {
                            tempPiece.pieceType = PieceType.BB;
                            tempPieceType = new BlackBishop(index);
                        }
                        case 3 -> {
                            tempPiece.pieceType = PieceType.BQ;
                            tempPieceType = new BlackQueen(index);
                        }
                        default -> {
                            blackKing[0]=i;
                            blackKing[1]=j;
                            tempPiece.pieceType = PieceType.BK;
                            tempPieceType = new BlackKing(index);
                        }
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
					//continue;
					tempPiece.pieceType = PieceType.WP;
					tempPieceType = new WhitePawn(index);
                }else{
                    switch (j) {
                        case 0, 7 -> {
                            tempPiece.pieceType = PieceType.WR;
                            tempPieceType = new WhiteRook(index);
                		}
                		case 1, 6 -> {
                            //continue;
                            tempPiece.pieceType = PieceType.WN;
                            tempPieceType = new WhiteKnight(index);
                    	}
                 		case 2, 5 -> {
                            //continue;
                            tempPiece.pieceType = PieceType.WB;
                            tempPieceType = new WhiteBishop(index);
                    	}
                    	case 3 -> {
                            //continue;
                            tempPiece.pieceType = PieceType.WQ;
                            tempPieceType = new WhiteQueen(index);
                    	}
                        default -> {
                            whiteKing[0]=i;
                            whiteKing[1]=j;
                            tempPiece.pieceType = PieceType.WK;
                            tempPieceType = new WhiteKing(index);
                        }
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
