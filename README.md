# JSudoku

####Project Description

  This is a sudoku game created by Jason Yang.  There are two main components to this app - the algorithm to generate new Sudoku game
  boards, and the game itself.  The purpose of this project was to introduce myself to the Swing library and GUI programming, as well 
  as practicing the concepts of recursion and backtracking to solve problems.
  
####Game board generation

  In order to provide players with random and new game boards each session, a recursive backtracking algorithm is run everytime the 
  player starts a new game.  This generates a filled and completely valid Sudoku board.  Based on the difficulty setting, a certain
  number of tiles are erased in a random order, after checking after each removal that the puzzle still contains a single unique solution.
  
####Game Interface

  The GUI is made with Java Swing components, which includes an "enter" button, a "reset" button, a menubar with difficulty settings,
  and the grid itself.  As of now, only 3X3 grids are available, but upon further work, 12X12 grids and above may be available.  Pressing
  each of the difficulty modes, easy, medium, or hard will wipe away current progress and start a new game with that difficulty setting.
  
####Future Work
  - Different playable grid types, e.g. 12X12, 16X16, etc.
  - A better algorithm to remove tiles from a filled board to generate a puzzle (currently, the algorithm checks for solution uniqueness, but less naive algorithms are able to craft puzzles which are hard for humans to solve, not necessarily computational time.
  - Better GUI implementation and refining the look of the app
  - Saving game states in external files for users to load afterwards
