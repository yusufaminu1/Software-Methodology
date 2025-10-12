package chess;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;
import java.util.ArrayList;

// Yusuf Aminu Jaiveer Singh
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

         move = move.trim();
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
                    int indexFrom = Chess.board[moveTo[0]][moveTo[1]].index;
                    Chess.currentGame.remove(Chess.board[moveTo[0]][moveTo[1]].index);
                    for(int i =0;i<8;i++){
                        for(int j = 0;j<8;j++){
                            if(board[i][j]!=null&&board[i][j].index >indexFrom){
                                board[i][j].index--;
                            }
                        }
                    }
					
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
                //Checks for draw, check, checkmate
				if (returnPlay.message == null) {
                    // Check for draw
                    if (sm.equals("draw?")) {
                        returnPlay.message = ReturnPlay.Message.DRAW;
                    } else {
                        // Finds the opponent for check/checkmate
                        Player opp = (moveNumber % 2 == 1) ? Player.white : Player.black;

                        //Check for check, if the opponnent has no legal move, then checkmate
                        if (isInCheck(opp)) {
                            if (!hasAnyLegalMove(opp)) {
                                returnPlay.message =
                                        (opp == Player.white)
                                                ? ReturnPlay.Message.CHECKMATE_BLACK_WINS
                                                : ReturnPlay.Message.CHECKMATE_WHITE_WINS;
                            } else {
                                returnPlay.message = ReturnPlay.Message.CHECK;
                            }
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
                    continue;
                    //tempPiece.pieceType = PieceType.BP;
					//tempPieceType = new BlackPawn(index);
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
                    continue;
					//tempPiece.pieceType = PieceType.WP;
					//tempPieceType = new WhitePawn(index);
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
                            continue;
                            //tempPiece.pieceType = PieceType.WB;
                            //tempPieceType = new WhiteBishop(index);
                    	}
                    	case 3 -> {
                            continue;
                            //tempPiece.pieceType = PieceType.WQ;
                            //tempPieceType = new WhiteQueen(index);
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

    //Boolean for check
    static boolean isInCheck(Player side) {
        int kr = (side == Player.white) ? whiteKing[0] : blackKing[0];
        int kc = (side == Player.white) ? whiteKing[1] : blackKing[1];
        int[] kingSq = new int[] { kr, kc };
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p == null) continue;
                if (side == Player.white && p instanceof BlackPiece) {
                    Snapshot s = snapshot();
                    boolean atk = p.move(new int[] { r, c }, kingSq, false);
                    restore(s);
                    if (atk) return true;
                } else if (side == Player.black && p instanceof WhitePiece) {
                    Snapshot s = snapshot();
                    boolean atk = p.move(new int[] { r, c }, kingSq, false);
                    restore(s);
                    if (atk) return true;
                }
            }
        }
        return false;
    }

    //Boolean to check for legal moves
    static boolean hasAnyLegalMove(Player side) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p == null) continue;
                if (side == Player.white && !(p instanceof WhitePiece)) continue;
                if (side == Player.black && !(p instanceof BlackPiece)) continue;
                for (int rr = 0; rr < 8; rr++) {
                    for (int cc = 0; cc < 8; cc++) {
                        if (rr == r && cc == c) continue;
                        Snapshot s = snapshot();
                        boolean ok = p.move(new int[] { r, c }, new int[] { rr, cc }, true);
                        restore(s);
                        if (ok) return true;
                    }
                }
            }
        }
        return false;
    }

    //Snapshot class helps with check and legal move checking by storing the current state of the game
    static class Snapshot {
        Piece[][] b = new Piece[8][8];
        ArrayList<ReturnPiece> g = new ArrayList<>();
        int[] wk = new int[2];
        int[] bk = new int[2];
    }

    //Take a snapshot of the current game
    static Snapshot snapshot() {
        Snapshot s = new Snapshot();
        for (int i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, s.b[i], 0, 8);
        }
        for (ReturnPiece rp : currentGame) {
            if (rp == null) {
                s.g.add(null);
            } else {
                ReturnPiece x = new ReturnPiece();
                x.pieceType = rp.pieceType;
                x.pieceFile = rp.pieceFile;
                x.pieceRank = rp.pieceRank;
                s.g.add(x);
            }
        }
        s.wk[0] = whiteKing[0]; s.wk[1] = whiteKing[1];
        s.bk[0] = blackKing[0]; s.bk[1] = blackKing[1];
        return s;
    }

    //Bring the game back to the snapshot
    static void restore(Snapshot s) {
        for (int i = 0; i < 8; i++) {
            System.arraycopy(s.b[i], 0, board[i], 0, 8);
        }
        currentGame = s.g;
        whiteKing[0] = s.wk[0]; whiteKing[1] = s.wk[1];
        blackKing[0] = s.bk[0]; blackKing[1] = s.bk[1];
    }

}
