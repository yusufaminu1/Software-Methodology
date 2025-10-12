package chess;

public class BlackKnight extends BlackPiece {
    
    public BlackKnight(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to,boolean checkSelfCheck) {

        int row1 = from[0], col1 = from[1];
        int row2 = to[0],   col2 = to[1];

        if (!inBounds(row1, col1) || !inBounds(row2, col2)) return false;

        if (row1 == row2 && col1 == col2){
            return false;
        } 

        int upDown = Math.abs(row2 - row1);
        int leftRight = Math.abs(col2 - col1);
        boolean isKnightL = (upDown == 2 && leftRight == 1) || (upDown == 1 && leftRight == 2);

        if (!isKnightL){
            return false;
        }

        Piece target = Chess.board[row2][col2];
        if (target instanceof BlackPiece){
            return false;
        }

        else{
            if(checkSelfCheck){
                if(!SelfCheck(from, to)){
                    return false;
                }
            }
            return true;
        }
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

}