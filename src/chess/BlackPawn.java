package chess;

public class BlackPawn extends BlackPiece {
    
    public BlackPawn(int index) {
        this.index = index;
    }
    @Override
    public boolean move(int[] from, int[] to) {
        return true;
    }

}
