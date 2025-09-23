package chess;

public abstract class Piece {
    int index;
    int upDown;
    int leftRight;

    abstract boolean move(int[] from, int[] to);
    
}
