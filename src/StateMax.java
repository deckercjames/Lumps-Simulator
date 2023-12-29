// package default_packages;

import java.util.HashSet;
import java.util.Set;
import java.lang.Math;

public class StateMax extends State{
    
    public StateMax(int[] dice, boolean[] lockedMask, int round){
        this.dice = dice;
        this.lockedMask = lockedMask;
        this.round = round;
    }
    
    public static int printBestMoveForRound = -1;
    public static int printAllMovesForRound = -1;
    
    public int calcScore(){
        
        int score = 0;
        
        //create a histogram of the dice
        int[] histogram = new int[11]; //histogram[i] = # of dice with a face of i
        for(int i = 0; i < 8; i++){
            histogram[dice[i]]++;
        }
        
        //basic scoring
        for(int i = 1; i < histogram.length; i++){
            score += i * Math.max(0, histogram[i]-1);
        }
        
        //all even/odd bonus
        if(
            (histogram[1] + histogram[3] + histogram[5] + histogram[7] + histogram[9]  == 8) ||
            (histogram[2] + histogram[4] + histogram[6] + histogram[8] + histogram[10] == 8)
        ){
            score += 6;
        }
        
        //score 6/7/8 in a row
        int runCounter = 0;
        int maxRun = 0;
        for(int i = 1; i < histogram.length; i++){
            if(histogram[i] == 0){
                runCounter = 0;
            }else{
                runCounter++;
            }
            maxRun = Math.max(maxRun, runCounter);
        }
        score += Math.max(0, maxRun-5) * 10;
        
        //score 6/7/8 of a kind
        for(int i = 1; i < histogram.length; i++){
            if(histogram[i] >= 6){
                score += ( histogram[i] - 5 ) * 10;
                break;
            }
        }
        
        return score;
    }
    
    public double getValue(){
        
        if(this.round == 2){
            return calcScore();
        }
        
        int diceLockedAfterRound = 0;
        switch(round){
            case 0:
                diceLockedAfterRound = 4;
                break;
            case 1:
                diceLockedAfterRound = 6;
                break;
        }
        
        Set<StateExpect> usedChildren = new HashSet<StateExpect>();
        double max = -1;
        StateExpect maxChild = null;
        
        
        for(int i = 0; i < 256; i++){
            
            // check the number of dice that will be locked after this round
            int lockedCount = 0;
            for(int j = 0; j < 8; j++){
                if((i >> j & 1) == 1){
                    lockedCount += 1;
                }
            }
            
            if(lockedCount < diceLockedAfterRound) continue;
            
            //create an exprect state
            int[] dice = new int[8];
            boolean[] lockedMask = new boolean[8];
            
            for(int j = 0; j < 8; j++){
                lockedMask[j] = this.lockedMask[j];
                if((i >> j & 1) == 1 || this.lockedMask[j]){
                    dice[j] = this.dice[j];
                    lockedMask[j] = true;
                }else{
                    dice[j] = 0;
                }
            }
            
            StateExpect child = new StateExpect(dice, lockedMask, round+1);
            
            if(!usedChildren.contains(child)){
                double childValue = child.getValue();
                if(round == printAllMovesForRound) System.out.println("Option: "+child+" = "+childValue);
                if(childValue > max){
                    max = childValue;
                    maxChild = child;
                }
                usedChildren.add(child);
            }
            
        }
        
        StateExpect sePrint = new StateExpect(this.dice, maxChild.lockedMask, this.round); //make one just to print all the dice
        if(round == printBestMoveForRound) System.out.println("Best Move: "+sePrint+" = "+max);
        return max;
        
    }
    
    
    
    @Override
    public boolean equals(Object o){
        StateMax other = (StateMax) o;
        
        return Lumps.statesEqual(this.dice, this.lockedMask, this.round, other.dice, other.lockedMask, other.round);
    }
    
    
}