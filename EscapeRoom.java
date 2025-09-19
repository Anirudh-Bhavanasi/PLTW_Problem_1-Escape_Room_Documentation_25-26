/*
* Problem 1: Escape Room
* 
* V1.0
* 10/10/2019
* Copyright(c) 2019 PLTW to present. All rights reserved
*/

/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
public class EscapeRoom
{
  /* TO-DO: Process game commands from user input:
      right, left, up, down: move player size of move, m, if player try to go off grid or bump into wall, score decreases
      jump over 1 space: player cannot jump over walls
      pick up prize: score increases, if there is no prize, penalty
      help: display all possible commands
      end: reach the far right wall, score increase, game ends, if game ends without reaching far right wall, penalty
      replay: shows number of player steps and resets the board, player or another player can play the same board
        
      if player land on a trap, spring a trap to increase score: the program must first check if there is a trap, if none exists, penalty
      Note that you must adjust the score with any method that returns a score
      Optional: create a custom image for player - use the file player.png on disk
    */

  public static void main(String[] args) 
  {      
    // welcome message
    System.out.println("Welcome to EscapeRoom!");
    System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
    System.out.println("pick up all the prizes.\n");
    
    GameGUI game = new GameGUI();
    game.createBoard();

    // size of move
    int m = 60; 
    int score = 0;
    String[] validCommands = { "right", "left", "up", "down", "r", "l", "u", "d",
    "jump", "jr", "jumpleft", "jl", "jumpup", "ju", "jumpdown", "jd",
    "pickup", "p", "springtrap", "st", "detect", "dt", "quit", "q", "replay", "end", "help", "?"};
    
    String[] jumpDirections = { "right", "left", "up", "down", "r", "l", "u", "d"};
  
    // set up game
    boolean play = true;
    while (play)
    {

      // get user command and validate
      System.out.print("Enter command:");
      String input = UserInput.getValidInput(validCommands);

	    /* process user commands*/
      if (input.equals("right") || input.equals("r")) {
        score += game.movePlayer(m, 0);
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("left") || input.equals("l")) {
        score += game.movePlayer(-m, 0);
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("up") || input.equals("u")) {
        score += game.movePlayer(0, -m);
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("down") || input.equals("d")) {
        score += game.movePlayer(0, m);
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("jump")) {
        // Prompt user for jump direction
        System.out.print("Jump in which direction? (right/left/up/down or r/l/u/d): ");
        String jumpDir = UserInput.getValidInput(jumpDirections);
        
        // Execute jump in specified direction
        if (jumpDir.equals("right") || jumpDir.equals("r")) {
          score += game.movePlayer(2*m, 0);
        }
        else if (jumpDir.equals("left") || jumpDir.equals("l")) {
          score += game.movePlayer(-2*m, 0);
        }
        else if (jumpDir.equals("up") || jumpDir.equals("u")) {
          score += game.movePlayer(0, -2*m);
        }
        else if (jumpDir.equals("down") || jumpDir.equals("d")) {
          score += game.movePlayer(0, 2*m);
        }
        
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("jr")) {
        // Jump right - move 2 spaces right (shortcut)
        score += game.movePlayer(2*m, 0);
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("jumpleft") || input.equals("jl")) {
        // Jump left - move 2 spaces left
        score += game.movePlayer(-2*m, 0);
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("jumpup") || input.equals("ju")) {
        // Jump up - move 2 spaces up
        score += game.movePlayer(0, -2*m);
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("jumpdown") || input.equals("jd")) {
        // Jump down - move 2 spaces down
        score += game.movePlayer(0, 2*m);
        // Check for trap only if player lands on trap tile
        if (game.isTrap(0, 0)) {
          System.out.println("You landed on a trap! You lose points!");
          score += game.stepOnTrap();
        }
      }
      else if (input.equals("pickup") || input.equals("p")) {
        score += game.pickupPrize();
      }
      else if (input.equals("springtrap") || input.equals("st")) {
        // Spring trap at current location
        score += game.springTrap(0, 0);
      }
      else if (input.equals("detect") || input.equals("dt")) {
        // Detect traps in adjacent tiles
        score += game.detectTraps();
      }
      else if (input.equals("help") || input.equals("?")) {
        System.out.println("\n=== ESCAPE ROOM COMMANDS ===");
        System.out.println("Movement:");
        System.out.println("  right, r     - Move right one space");
        System.out.println("  left, l      - Move left one space");
        System.out.println("  up, u        - Move up one space");
        System.out.println("  down, d      - Move down one space");
        System.out.println("Jumping:");
        System.out.println("  jump         - Jump in a direction (you'll be prompted for direction)");
        System.out.println("  jr           - Jump right (skip one space)");
        System.out.println("  jumpleft, jl - Jump left (skip one space)");
        System.out.println("  jumpup, ju   - Jump up (skip one space)");
        System.out.println("  jumpdown, jd - Jump down (skip one space)");
        System.out.println("Actions:");
        System.out.println("  pickup, p    - Pick up prize at current location");
        System.out.println("  springtrap, st - Spring traps in adjacent tiles (disarms nearby traps)");
        System.out.println("  detect, dt   - Detect traps in adjacent tiles (bonus if found, penalty if none)");
        System.out.println("Game Control:");
        System.out.println("  end          - End game and check if you reached the far right");
        System.out.println("  replay       - Reset board and show step count");
        System.out.println("  quit, q      - Quit the game");
        System.out.println("  help, ?      - Show this help message");
        System.out.println("===============================\n");
      }
      else if (input.equals("end")) {
        score += game.endGame();
        play = false;
      }
      else if (input.equals("replay")) {
        System.out.println("Steps taken: " + game.getSteps());
        score += game.replay();
      }
      else if (input.equals("quit") || input.equals("q")) {
        play = false;
      }
      
      // Display current score
      System.out.println("Current score: " + score);
      System.out.println("Steps taken: " + game.getSteps());
      System.out.println();
    }

    score += game.endGame();

    System.out.println("score=" + score);
    System.out.println("steps=" + game.getSteps());
  }
}

        