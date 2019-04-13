import java.util.*;

public class DataUnit {
	//if there is only one AI, AI always plays 1(O) and human player always plays 2(X)
	public int m=3;
	public int n=3;
	public int board[][];
	public int cur=1;
	public int winner=0;
	public int count;
	public boolean draw;
	public boolean isEmpty;
	public boolean isAI[]=new boolean[3];
	public Heuristic heuristic;
	public HTTP http;
	public int MinScopeX,MinScopeY,MaxScopeX,MaxScopeY;
	public GUI gui;
	public long startTime;
	public long endTime;
	public long timmer;

	public DataUnit(){
		this.http = new HTTP();
	}
	public void init(int num) {
		board=new int[m][m];
		if(m>=12) maxdepth = 3;
		else if(m<12 && m >8) maxdepth = 4;
		else if(m<=8 && m>6) maxdepth = 5;
		else if(m==6) maxdepth = 6;
		else if(m==5) maxdepth = 7;
		else maxdepth = 8;
		cur=1;
		winner=0;
		count = 0;//how many steps we had played
		isEmpty = true;
		this.heuristic = new Heuristic(m, n);
		//http.createGame(http.teamId,http.opponent_teamId,m,n);//create game via api
		for(int i=1;i<3;i++)
			if(num<i)
				isAI[i]=false;
			else
				isAI[i]=true;
		check();
	}
	
	public void check() {
		//check
		boolean isend=false;
		isend = finish();
		if(isend) {
			winner = cur;
			try {
				Thread.sleep(1000);
			}catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			gui.display();
			return;
		}

		draw = true;
		for(int i=0;i<m;i++) {
			for(int j=0;j<m;j++) {
				if(board[i][j]==0)
					draw = false;
				}
		}
		if (draw) {
			System.out.println("draw");
			try {
				Thread.sleep(1000);
			}catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			gui.display();
			return;}
		//shift current player

		if(cur==1)cur=2;
		else cur=1;
		if(isAI[cur]==true)
			playAI();

	}
	
	public boolean finish() {//check if the game is finished
		boolean finish=false;
		boolean tof;
		//check
		for(int i=0;i<m;i++) {
			for(int j=0;j<m;j++) {
				if(board[i][j]>0) {
					if(i<=m-n&&j<=m-n) {
						tof=true;
						for(int k=1;k<n;k++)
							if(board[i][j]!=board[i+k][j+k])
								tof=false;
						if(tof) 
							return true;
					}
					if(i>=(n-1)&&j<=m-n) {
						tof=true;
						for(int k=1;k<n;k++)
							if(board[i][j]!=board[i-k][j+k])
								tof=false;
						if(tof) 
							return true;
					}
					if(i<=m-n) {
						tof=true;
						for(int k=1;k<n;k++)
							if(board[i][j]!=board[i+k][j])
								tof=false;
						if(tof) 
							return true;
					}
					if(j<=m-n) {
						tof=true;
						for(int k=1;k<n;k++)
							if(board[i][j]!=board[i][j+k])
								tof=false;
						if(tof) 
							return true;
					}
				}
			}
		}
		return finish;
	}

	public static int maxdepth;

	public long[] minimax(int depth, int player, long alpha, long beta) {
		endTime = System.currentTimeMillis();
		timmer = endTime - startTime;
		int cur_temp;
		if((player+depth)%2 == 0) cur_temp = 2;
		else cur_temp = 1;
		long res[]= {0,-1,-1};
		long temp;
		//generate possible nextmove
		findScope();
		//use heap to keep a queue sorted by distance from the existing pieces
		PriorityQueue<int[]> nextMoves = new PriorityQueue<int[]>(10,new Comparator()
		{
			public int compare(Object obj1,Object obj2){
				int[] n1=(int[]) obj1;
				int[] n2=(int[]) obj2;
				int abs1 = Math.min(Math.abs(n1[0]-MinScopeX),Math.abs(n1[0]-MaxScopeX))+Math.min(Math.abs(n1[1]-MinScopeY),Math.abs(n1[1]-MaxScopeY));
				int abs2 = Math.min(Math.abs(n2[0]-MinScopeX),Math.abs(n2[0]-MaxScopeX))+Math.min(Math.abs(n2[1]-MinScopeY),Math.abs(n2[1]-MaxScopeY));

				if(abs1>abs2)
					return 1;
				else if(abs1<abs2)
					return -1;
				else
					return 0;
			}

		});
//		List<int[]> nextMoves = new ArrayList<int[]>();
		for(int i=0;i<m;i++) {
			loop:for(int j=0;j<m;j++) {
				if(board[i][j]==0) {
					//if((i>=3 || ))
					for(int x=-2; x<3; x++) {
						for(int y=-2; y<3; y++) {
							if((i+x>=0) && (i+x<m) && (j+y>=0) && (j+y<m)) {
								if(board[i+x][j+y]!=0) {
									nextMoves.add(new int[] {i,j});
									continue loop;
								}
							}
						}
					}
				}
			}

		}
		if(finish()) {
			res[0] = heuristic.evaluate_winner(board) + depth;
		}

		else if(nextMoves.isEmpty() || depth>=maxdepth || timmer>=1700000) {
			//if(timmer>=1700000) System.out.print("Time Out!");
			res[0]=heuristic.evaluate(board) + depth;
		}

		else {
			if(cur_temp == 2) {
				//maximizing player
				res[0] = Integer.MIN_VALUE;
				while (!nextMoves.isEmpty()){
					int[] move = nextMoves.poll();
//				for(int[] move : nextMoves) {
					board[move[0]][move[1]] = cur_temp;
					temp = minimax(depth + 1, player, alpha, beta)[0];
					if (depth == 0) {
						//System.out.println("move " + move[0] + "," + move[1] + " heuristic:" + temp);
					}
					if (res[0] < temp) {
						res[0] = temp;
						res[1] = move[0];
						res[2] = move[1];
						alpha = res[0];
					}

					board[move[0]][move[1]] = 0;
					if (beta<=alpha) {
						break;
					}
				}
			}
			else {
				//minimizing player
				res[0] = Integer.MAX_VALUE;
				while (!nextMoves.isEmpty()){
					int[] move = nextMoves.poll();
//				for(int[] move : nextMoves) {
					board[move[0]][move[1]]=cur_temp;
					temp= minimax(depth+1,player,alpha,beta)[0];
					if(depth == 0) {
						//System.out.println("move "+move[0]+","+move[1]+" heuristic:"+temp);
						}
					if(res[0]>temp) {
						res[0]=temp;
						res[1]=move[0];
						res[2]=move[1];
						beta = res[0];
					}

					board[move[0]][move[1]]=0;
					if(beta<=alpha) {
						break;
					}
				}
			}
		}
		return res;
	}
	
	public void playAI() {
		startTime=System.currentTimeMillis();
		if(winner>0)return;
		int x=0,y=0;
		if(isEmpty){
			x=m/2;
			y=m/2;
			//board[m/2][m/2]=cur;
			//Empty, put directly in the middle
		}
		else if(count==1){
			//if there is only one pawn, down at its opposite corner
			int previousX=0,previousY=0;
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < m; j++) {
					if(board[i][j]!=0){
						previousX = i;
						previousY = j;
						break;
					}
				}
				if(previousX<m/2){
					x=previousX+1;
				}
				else{
					x=previousX-1;
				}

				if(previousY<m/2){
					y=previousY+1;
				}
				else{
					y=previousY-1;
				}
			}
		}
		else {

			long next[] = minimax(0, cur, Integer.MIN_VALUE, Integer.MAX_VALUE);
			x = (int) next[1];
			y = (int) next[2];

			//System.out.println("cur:" + cur + " [" + next[1] + "," + next[2] + "]");
			//board[next[1]][next[2]] = cur;
		}
		play(x,y);
		if(http.gameId!=null) {
			http.makeMove(x,y);
			autoget();
		}
		//isEmpty = false;
		//check();
	}
	
	public void play(int x,int y) {
		if(winner>0)return;
		board[x][y]=cur;
		gui.display();
		count++;
		isEmpty = false;
		System.out.println("cur:"+cur+" ["+x+","+y+"]");
		gui.tfield[9].setText("cur:"+cur+" ["+x+","+y+"]");//show the information
		check();
	}

	public void play(Move move){
		if(winner>0)return;
		int x = Integer.valueOf(move.moveX);
		int y = Integer.valueOf(move.moveY);
		play(x,y);
	}
	
	public void setmn(int a,int b) {
		m=a;
		n=b;	
	}

	public void findScope() {
		int MinScopeX = Integer.MAX_VALUE,MaxScopeX =Integer.MIN_VALUE,MinScopeY=Integer.MAX_VALUE,MaxScopeY=Integer.MIN_VALUE;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if(board[i][j]!=0){
					MinScopeX = Math.min(MinScopeX,i);
					MinScopeY = Math.min(MinScopeY,j);
					MaxScopeX = Math.max(MaxScopeX,i);
					MaxScopeY = Math.max(MaxScopeY,j);
				}
			}
		}
		this.MinScopeX = MinScopeX;
		this.MaxScopeX = MaxScopeX;
		this.MinScopeY = MinScopeY;
		this.MaxScopeY = MaxScopeY;
	}

	void autoget(){
		//Auto Move
		long startTime = System.currentTimeMillis();
		long time = 0;
		while(winner==0) {
			try {
				long nowTime = System.currentTimeMillis();
				time = nowTime-startTime;
				if(time>=300*1000){
					System.out.println("Time Out");
					break;
				}
				Thread.sleep( 3*1000); //get opponent move per 3 seconds
				Optional<Move> move= http.getOpponentLatestMove(http.getMoves(1));//get the latest step
				if(move.isPresent()){
					System.out.println("Get Opponent Move");
					gui.tfield[7].setText(move.get().teamId);//set Opponent teamId
					//gui.tfield[9].setText(move.get().teamId+" "+move.get().move);//show the information
					play(move.get());
					break;
				}

			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}

	}
}
