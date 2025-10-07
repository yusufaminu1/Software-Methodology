package chess;

public abstract class BlackPiece extends Piece{

    boolean SelfCheck(int[] from, int[] to) {
        Piece[] temp = new Piece[1];
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
                    return false;
                }
            }
        }
        Chess.board[from[0]][from[1]] = Chess.board[to[0]][to[1]];
        Chess.board[to[0]][to[1]] = temp[0];
        return true;
    }
}
