package chess;

public abstract class Piece {
    int index;
    int upDown;
    int leftRight;
    int moveNumber;

    abstract boolean move(int[] from, int[] to, boolean checkSelfCheck);

    
}
