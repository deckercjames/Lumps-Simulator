// package default_packages;

import java.util.HashMap;
import java.util.Map;

public class StateExpect extends State{
    
    public StateExpect(int[] dice, boolean[] lockedMask, int round){
        this.dice = dice;
        this.lockedMask = lockedMask;
        this.round = round;
    }
    
    public static int printExpectedValueForRound = -1;
    
    private double rollDice(int[] dice, Map<StateMax, Double> children){
        
        //scan for first unrolled die
        int i = 0;
        while(i < 8){
            
            if(dice[i] != 0){
                i++;
                continue;
            }
            
            //get the d# of this die
            int d = i - (i % 2) + 4;
            
            float expectedValue = 0;
            
            for(int j = 1; j <= d; j++){
                int[] newDice = new int[8];
                for(int k = 0; k < 8; k++){
                    newDice[k] = dice[k];
                }
                newDice[i] = j;
                expectedValue += rollDice(newDice, children);
            }
            
            return expectedValue / d;
        }
        
        //now we have a full set of dice to work with. Return the value of the child state
        StateMax child = new StateMax(dice, this.lockedMask, this.round);
        
        double childValue = 0;
        
        if(children.containsKey(child)){
            childValue = children.get(child);
        }else{
            childValue = child.getValue();
            children.put(child, childValue);
        }
        
        if(round == printExpectedValueForRound){
            System.out.println(String.format("[%d, %d, %d, %d, %d, %d, %2d, %2d] = %f", 
                dice[0],
                dice[1],
                dice[2],
                dice[3],
                dice[4],
                dice[5],
                dice[6],
                dice[7],
                childValue
            ));
        }
        
        return childValue;
        
    }
    
    
    public double getValue(){
        
        // Maps state to known value
        Map<StateMax, Double> children = new HashMap<StateMax, Double>();
        
        return rollDice(this.dice, children);
        
    }
    
    @Override
    public boolean equals(Object o){
        StateExpect other = (StateExpect) o;
        
        return Lumps.statesEqual(this.dice, this.lockedMask, this.round, other.dice, other.lockedMask, other.round);
    }
    
    
}