


public abstract class State{
    
    
    // dice
    int[] dice;
    
    // locked mask
    boolean[] lockedMask;
    
    int round;
    
    private static final char lockedChar = 'x';
    private static final char unlockedChar = '_';
    
    @Override
    public int hashCode(){
        return (dice[0] + dice[1]) * 1  +
               (dice[2] + dice[3]) * 13 +
               (dice[4] + dice[5]) * 29 +
               (dice[6] + dice[7]) * 53;
    }
    
    @Override
    public String toString(){
        return String.format("[%d%c, %d%c, %d%c, %d%c, %d%c, %d%c, %2d%c, %2d%c]", 
            dice[0], lockedMask[0] ? lockedChar : unlockedChar,
            dice[1], lockedMask[1] ? lockedChar : unlockedChar,
            dice[2], lockedMask[2] ? lockedChar : unlockedChar,
            dice[3], lockedMask[3] ? lockedChar : unlockedChar,
            dice[4], lockedMask[4] ? lockedChar : unlockedChar,
            dice[5], lockedMask[5] ? lockedChar : unlockedChar,
            dice[6], lockedMask[6] ? lockedChar : unlockedChar,
            dice[7], lockedMask[7] ? lockedChar : unlockedChar
        );
    }
    
}