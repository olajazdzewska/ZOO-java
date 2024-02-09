# ZOO

## Description:
 The program makes a simulation of a nature. It prints out grid which represents world. It cointains Animals, Plants and Human, which all inherit after Organism class. Each organism makes a move each turn. The order of moves is based on initiative attribute, excpet for Human as it is controlled with arrows. If organism moves to an accoupied gird cell then fight or breed will hapen. If there are two different species on one grid, then one must kill the other and the winner stays on the grid. The strength attribute and overriden colision function decide on who wins. If on the grid are the same species, then they will breed. The Organism that made a move goes back to previous cell and the child is put on the closest cell to its parent. Plants don't move or breed but the sow. Human also has a special ability which can be used every couple turns. It increases strength of human to 10 and is decreased each turn by 1 until it goes back to previous value. The state of grid can be saved (in txt file) and loaded.

## Stack:
Application is based on Java with libraries:
- swing for GUI
- awt for keyboard input
- util for operations on ArrayList

## Instruction:
 Go to `main.java` file in src folder and run the program. Then click on the menu in the left top corner and choose new game or load game. Then click 't' to make a turn is simulation. All necessary instructions will be then printed out in the program.