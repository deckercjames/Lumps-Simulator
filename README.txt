Lumps Analysis

This is a expectimax algorithm to play the dice game Lumps

COMPILING
cd src/
javac *.java

RUNNNIG
cd src/
java Lumps ["sample"]
  Running with "sample" will make the program continuesusly sample initial dice rolls to get their best expected score. These are averaged to get an expected value for any turn.
  Running without "sample" will drop into a cli to evaluate a given position and give the best move

CLI
>> ROUND_NUMBER ["all"]
  ROUND_NUMBER is 0 for fist selection (lock at least 4); 1 is for second selection (lock at least 6)
  "all" means it will print all possible actions and the exected value from that postion
This will give a header prompt for the user to enter the dice: "d4 d4 d6 d6 d8 d8 d10 d10"
Enter the number showing on the die and a character to show if it is already locked ('x' for locked, '_' for unlocked). Note for round 0, all dice should be marked as unlocked

E.G.
>> 1 all
d4 d4 d6 d6 d8 d8 d10 d10
1_ 4x 4x 5x 6x 3_ 9x  8_
