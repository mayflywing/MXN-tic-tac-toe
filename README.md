# MXN-tic-tac-toe
board size M, wincon N, tic tac toe

1.	AI Move Strategy
1)	On First move, if the entire board is empty, then the AI would always make the first move by putting its chess piece into the middle spot of the board; but if there is already one chess piece exists on the board, the first move will be placing the chess piece to the diagonal position of the currently existing piece.
2)	The following move would be based on the algorithm we used. The algorithm used in this project is the minimax algorithm with alpha-beta pruning. We have also made serval update on this algorithm as well.

PriorityQueue<int[]> nextMoves = new PriorityQueue<int[]>(10,new Comparator(){
		public int compare(Object obj1,Object obj2){
			int[] n1=(int[]) obj1;
			int[] n2=(int[]) obj2;
			int abs1 = Math.min(Math.abs(n1[0] -MinScopeX), Math.abs(n1[0] - MaxScopeX)) + Math.min(Math.abs(n1[1] - MinScopeY), Math.abs(n1[1] - MaxScopeY));
			int abs2 = Math.min(Math.abs(n2[0] - MinScopeX), Math.abs(n2[0] - MaxScopeX)) + Math.min(Math.abs(n2[1] - MinScopeY), Math.abs(n2[1] - MaxScopeY));
			if(abs1>abs2)
				return 1;
			else if(abs1<abs2)
				return -1;
			else
				return 0;
			}
		});

First, we set the depth of the minimax tree to x, which depends on the width of the board. Then we used the heap to maintain the empty board, based on the distance between the spot and the current existing chess piece. After testing, we found that using heap rather than array would help the algorithm to prune more branches and make the algorithm run faster. Finally, we set a drop range that is the radius 2 range of the current move, if there is no opponent’s piece exists within the drop range, then prune this branch.
3)	Then check if there exists n connected chess pieces at this point. If there is, determine which player win. If not, check whether the board is full. If the board is full, it is a draw, otherwise continue to the next step.
2.	Heuristics
First, we designed two functions. 
The first function is designed to check the number of cases where num chess pieces are connected and unblocked. num is the number of chess pieces connected. Then return the number, where int [0] is the number for player 1, int[1] is the number of player 2.
 
The second function is to check the number of cases where num chess pieces are connected and one end of it has been blocked. Then return the number.
 
Then, for a given board, calculate utility function.
 
   Where we use the functions we just mentioned to check for these two types of cases where k chess pieces are connected, 1<k<n, for cases where num chess pieces are connected and unblocked, they have a weight of 10^k in the utility function, and for cases where num chess pieces are connected and one end of it has been blocked, they have a weight of 5^k in the utility function. For k = n, we just set its weight to Integer.MAX_VALUE/2.
 
Finally, we check if there exists n connected chess pieces, which is the winning case.
3.	How to run this program
In this project, we used GUI as our user interface. 
 
Local Play:
Before start playing the game, we need to set some parameters. 
The board size m, determines how big the board is, which is the size of the m*m board. 
Wincon n is the number of chess pieces need to be connected to win the game. 
After entering m, n, click the button “Set m/n” would change the game setting, then click the start button to display the board.
Number of AI is the number of AI used to play this game. Set to one means the game would be one person verse one AI, set to two means the game would be AI playing against AI. In the case of one person verse one AI, the person would always use X as their chess piece, and the AI would always use O. 
Does AI moves first determines which player gets to move first. In the case of one person verses one AI, 0 means the player gets to move first, 1 means the AI gets to move first.
After changing these parameters, click start to start the game.
In the cases where there is one person playing with one AI, Next Move is the move this person make. Next Move X and Next Move Y determines the position where this player placing its next chess piece. Next Move X is the number of the row, and Next Move Y is the number of the column. 
After entering Next Move X and Next Move Y, click the button “ManualMove” to make the next move.

API:
	New Game: 
	Enter the opponent’s teamId into “Opponent TeamId”, then click “SetOpponent”. After changing the game parameters, click “CreateGame” to start a new game. The gameId would be shown in the “gameId” text frame.
	Continue Game:
	If the game is created by the opponent, then enter gameId into the “gameId” text frame, click “SetGame”to join the game.  To get the opponent’s next move, click “AutoMove”to show their move on the GUI.
