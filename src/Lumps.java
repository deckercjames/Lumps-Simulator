// package default_packages;

import java.lang.System;
import java.util.Random;
import java.util.Scanner;

public class Lumps{
    
    public static void main(String[] args){
        
        if(args.length >= 1 && args[0].equals("sample")){
            randomSample();
        }
        
        //else
        Scanner sc = new Scanner(System.in);
        while(true){
            
            System.out.print(">> ");
            
            String line = sc.nextLine().trim();
            String[] parts0 = line.split("\\s+");
            
            if (line.length() == 0) {
                continue;
            } else if (line.equals("quit")) {
                break;
            }
            
            int round = Integer.parseInt(parts0[0]);
            
            System.out.println("d4 d4 d6 d6 d8 d8 d10 d10");
            
            line = sc.nextLine();
            String[] parts = line.trim().split("\\s+");
            
            int[] dice = new int[8];
            boolean[] mask = new boolean[8];
            
            for(int i = 0; i < 8; i++){
                dice[i] = Integer.parseInt(parts[i].substring(0, parts[i].length()-1));
                mask[i] = parts[i].charAt(parts[i].length()-1) == 'x';
            }
            
            StateMax sm = new StateMax(dice,mask,round);
            StateMax.printBestMoveForRound = round;
            StateMax.printAllMovesForRound = (parts0.length > 1 && parts0[1].equals("all")) ? round : -1;
            sm.getValue();
            
        }
        
    }
    
    
    
    private static void randomSample(){
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
                public void run() {
                    System.out.println();
                }   
            }
        ); 
        
        Random rd = new Random();
        
        StateExpect.printExpectedValueForRound = -1;
        StateMax.printBestMoveForRound = 0;
        
        double runningTotal = 0;
        int count = 0;
        
        StateMax se = null;
        int[] dice = new int[8];
        
        while(true){
            count ++;
            
            //build a set of dice
            for(int i = 0; i < 8; i++){
                dice[i] = rd.nextInt((i - (i%2))+4)+1;
            }
            
            se = new StateMax(dice, new boolean[8], 0);
            
            runningTotal += se.getValue();
            
            System.out.print(String.format("Expected Value (count %d) = %f\r", count, (runningTotal / count)));
            
        }
        
        
    }
    
    
    
    
    public static boolean sameSetOfTwo(int a0, int a1, int b0, int b1, boolean x0, boolean x1, boolean y0, boolean y1){
        return (a0 == b0 && a1 == b1 && x0 == y0 && x1 == y1) || 
               (a0 == b1 && a1 == b0 && x0 == y1 && x1 == y0); 
    }
    
    public static boolean statesEqual(int[] dice0, boolean[] mask0, int round0, int[] dice1, boolean[] mask1, int round1){
        
        boolean equals = true;
        
        for(int i = 0; i < 8; i+=2){
            equals = equals && Lumps.sameSetOfTwo(dice0[i], dice0[i+1], dice1[i], dice1[i+1],
            mask0[i], mask0[i+1], mask1[i], mask1[i+1]);
        }
        
        equals = equals && round0 == round1;
        
        return equals;
    }
    
}