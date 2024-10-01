# Yahtzee
Developed by **Sam Farris**

This project aims to create a command line interface yahtzee game for 1 to 6 players.
## Usage
To start playing the game you'll need to run the `Yahtzee.java` file through a command line or install `vscjava.vscode-java-pack` with VSCode extensions.
## Prompts
### Player Info
- When the game starts it'll ask for how many players are playing the Yahtzee game then the names of the players one by one (any characters are valid including spaces).
- Based on the order of the player names that are entered, that'll be the order of the player turns.
### Player Turn
- During a player's turn it'll instantly roll the first roll and prompt which dice should be kept. If none wish to be kept then press `Enter` to reroll all.
- Then it'll roll the second roll. It'll again ask which dice should be kept, but after it'll also ask which indexes should not be kept anymore.
- Finally, the third roll will be the last roll and it'll ask which category will be used for the rolled dice. After the input it'll calculate the player's turn score and move on to the next player in turn.
- It's important to note that when inputting the indexes for the dice to keep. You don't have to re-enter the indexes that are already kept. If you don't want to keep the new rolled dice, but keep the old ones then re-enter just 1 of the previous indexes that have been kept to keep all the previous indexes. You can re-enter all of the indexes you want to keep each time and accomplish the same thing but this is just a shortcut.
### Player Scores
- The final scores are as expected from any yahtzee game. Including the possible 35 point bonus for exceeding 63 points on the top scores (ones, twos, etc). Although there is not yet an implementation for multiple yahtzees.
- If there is a tie in the final scores then it'll display multiple winners. Which are the ones tied with the highest score out of all of the players. Otherwise it'll display the winner who would be the one with the highest score.