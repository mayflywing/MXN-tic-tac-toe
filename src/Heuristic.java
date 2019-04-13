import java.util.Optional;

public class Heuristic {
	int m, n, target;
	
	Heuristic(int m, int n){
		this.m = m;
		this.n = n;
	}
	
	/*
	 * input: board[][], the number to check
	 * return: the number of cases where num cells are connected and unblocked
	 * */
	public int[] checkunblocked(int[][] board, int num) {//num =: the number of cells connected 
		int[] res = new int[2];
		boolean tof;
		for(int i=0;i<m;i++) {
			for(int j=0;j<m;j++) {
				if(i<=m-num&&j<=m-num) {
					tof=true;
					if(i!=0 && j!=0 && board[i-1][j-1]==0) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j+k])
								tof=false;
						if(tof && (j!=m-num && i!=m-num && board[i+num][j+num]==0)) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(i>=(num-1)&&j<=m-num) {
					tof=true;
					if(i!=m-1 && j!=0 && board[i+1][j-1]==0) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i-k][j+k])
								tof=false;
						if(tof && (j!=m-num && i!=num-1 && board[i-num][j+num]==0)) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(i<=m-num) {
					tof=true;
					if(i!=0 && board[i-1][j]==0) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j])
								tof=false;
						if(tof && i!=m-num && board[i+num][j]==0) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(j<=m-num) {
					tof=true;
					if(j!=0 && board[i][j-1]==0) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i][j+k])
								tof=false;
						if(tof && j!=m-num && board[i][j+num]==0) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
			}
		}
		return res;
	}
	
	/*
	 * input: board[][], the number to check
	 * return: the number of cases where num cells are connected and unblocked
	 * */
	public int[] checkblocked(int[][] board, int num) {//num =: the number of cells connected 
		int[] res = new int[2];
		boolean tof;
		for(int i=0;i<m;i++) {
			for(int j=0;j<m;j++) {
				if(i<=m-num&&j<=m-num) {
					tof=true;
					if((i==0 || j==0 || (board[i-1][j-1]!=0 && board[i-1][j-1]!=board[i][j])) || 
							(j==m-num || i==m-num || (board[i+num][j+num]!=0 && board[i+num][j+num]!=board[i][j]))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(i>=(num-1)&&j<=m-num) {
					tof=true;
					if((i==m-1 || j==0 || (board[i+1][j-1]!=0 && board[i+1][j-1]!=board[i][j])) ||
							(j==m-num || i==num-1 || (board[i-num][j+num]!=0 && board[i-num][j+num]!=board[i][j]))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i-k][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(i<=m-num) {
					tof=true;
					if((i==0 || (board[i-1][j]!=0 && board[i-1][j]!=board[i][j])) ||
							(i==m-num || (board[i+num][j]!=0 && board[i+num][j]!=board[i][j]))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(j<=m-num) {
					tof=true;
					if(j==0 || (board[i][j-1]!=0 && board[i][j-1]!=board[i][j]) ||
							(j==m-num || (board[i][j+num]!=0 && board[i][j+num]!=board[i][j]))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
			}
		}
		return res;
	}
	
	
	public int[] checkoneblocked(int[][] board, int num) {
		int[] res = new int[2];
		boolean tof;
		for(int i=0;i<m;i++) {
			for(int j=0;j<m;j++) {
				if(i<=m-num&&j<=m-num) {
					tof=true;
					if(((i==0 || j==0 || (board[i-1][j-1]!=0 && board[i-1][j-1]!=board[i][j])) && (j!=m-num && i!=m-num && board[i+num][j+num]==0)) ||
							((i!=0 && j!=0 && board[i-1][j-1]==0) && (j==m-num || i==m-num || (board[i+num][j+num]!=0 && board[i+num][j+num]!=board[i][j])))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(i>=(num-1)&&j<=m-num) {
					tof=true;
					if(((i==m-1 || j==0 || (board[i+1][j-1]!=0 && board[i+1][j-1]!=board[i][j])) && (j!=m-num && i!=num-1 && board[i-num][j+num]==0)) ||
							((i!=m-1 && j!=0 && board[i+1][j-1]==0) && (j==m-num || i==num-1 || (board[i-num][j+num]!=0 && board[i-num][j+num]!=board[i][j])))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i-k][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(i<=m-num) {
					tof=true;
					if(((i==0 || (board[i-1][j]!=0 && board[i-1][j]!=board[i][j])) && (i!=m-num && board[i+num][j]==0)) ||
							((i!=0 && board[i-1][j]==0) && (i==m-num || (board[i+num][j]!=0 && board[i+num][j]!=board[i][j])))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(j<=m-num) {
					tof=true;
					if(((j==0 || (board[i][j-1]!=0 && board[i][j-1]!=board[i][j])) && (j!=m-num && board[i][j+num]==0)) ||
							((j!=0 && board[i][j-1]==0) && (j==m-num || (board[i][j+num]!=0 && board[i][j+num]!=board[i][j])))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
			}
		}	
		return res;
	}
	
	public int[] checktwoblocked(int[][] board, int num) {
		int[] res = new int[2];
		boolean tof;
		for(int i=0;i<m;i++) {
			for(int j=0;j<m;j++) {
				if(i<=m-num&&j<=m-num) {
					tof=true;
					if(((i==0 || j==0 || (board[i-1][j-1]!=0 && board[i-1][j-1]!=board[i][j])) && 
							(j==m-num || i==m-num || (board[i+num][j+num]!=0 && board[i+num][j+num]!=board[i][j])))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(i>=(num-1)&&j<=m-num) {
					tof=true;
					if(((i==m-1 || j==0 || (board[i+1][j-1]!=0 && board[i+1][j-1]!=board[i][j])) && 
							(j==m-num || i==num-1 || (board[i-num][j+num]!=0 && board[i-num][j+num]!=board[i][j])))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i-k][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(i<=m-num) {
					tof=true;
					if(((i==0 || (board[i-1][j]!=0 && board[i-1][j]!=board[i][j])) && 
							(i==m-num || (board[i+num][j]!=0 && board[i+num][j]!=board[i][j])))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
				if(j<=m-num) {
					tof=true;
					if(((j==0 || (board[i][j-1]!=0 && board[i][j-1]!=board[i][j])) && 
							(j==m-num || (board[i][j+num]!=0 && board[i][j+num]!=board[i][j])))) {
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}
					}
				}
			}
		}	
		return res;
	}
	
	
	
	/* check if there is a winner
	 * input: board[][]
	 * return: the number of cases where num cells are connected or unblocked
	 * */
	public int[] check(int[][] board) {
		int num = n;
		int[] res = new int[2];
		boolean tof;
		for(int i=0;i<m;i++) {
			for(int j=0;j<m;j++) {
				if(board[i][j]>0) {
					if(i<=m-num&&j<=m-num) {
						tof=true;
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}							
					}
					if(i>=(num-1)&&j<=m-num) {
						tof=true;
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i-k][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}						
					}
					if(i<=m-num) {
						tof=true;
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i+k][j])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}						
					}
					if(j<=m-num) {
						tof=true;
						for(int k=1;k<num;k++)
							if(board[i][j]!=board[i][j+k])
								tof=false;
						if(tof) {
							if(board[i][j] == 2) 
								res[0] +=1;
							else if(board[i][j] == 1)
								res[1] +=1;
						}						
					}
				}
			}
		}
		return res;
	}

	/*
	check the must win case
	 */
//	public Optional<int[]> almostWin(int[][] board,int cur) {
//		int num = n-1;
//		int[] move = new int[2];
//		boolean checkmate;
//		for(int i=0;i<m;i++) {
//				for(int j=0;j<m;j++) {
//				if(board[i][j]==cur) {
//					if(i<=m-num&&j<=m-num) {
//						//对角
//						checkmate = true;
//						for (int k = 1; k < num; k++) {
//							if (board[i][j] != board[i + k][j + k]) {
//								//无连子
//								checkmate = false;
//							}
//						}
//						if (checkmate) {
//							if (i >= 1 && j >= 1) {
//								if (board[i - 1][j - 1] == 0) {
//									//左上角有空位
//									move[0] = i - 1;
//									move[1] = j - 1;
//									return Optional.of(move);
//								}
//							}
//							if (board[i + num][j + num] == 0) {
//								//右下角有空位
//								move[0] = i + num;
//								move[1] = j + num;
//								return Optional.of(move);
//							}
//						}
//					}
//					if(i>=(num-1)&&j<=m-num) {
//						//反对角
//						checkmate=true;
//						for(int k=1;k<num;k++) {
//							if (board[i][j] != board[i - k][j + k]) {
//								checkmate = false;
//							}
//						}
//
//						if(checkmate){
//							if(i<=m-1&&j>=1){
//								if(board[i+1][j-1]==0){
//									//左下角有空位
//									move[0] = i+1;
//									move[1] = j-1;
//									return Optional.of(move);
//								}
//							}
//							if(board[i-num][j+num]==0){
//								//右上角有空位
//								move[0]= i-num;
//								move[1]= j+num;
//								return Optional.of(move);
//							}
//						}
//					}
//					if(i<=m-num) {
//						//竖排
//						checkmate=true;
//						for(int k=1;k<num;k++) {
//							if (board[i][j] != board[i + k][j]) {
//								checkmate = false;
//							}
//						}
//
//						if(checkmate){
//							if(i>=1&&board[i-1][j]==0){
//								//上方
//								move[0] = i-1;
//								move[1] = j;
//								return Optional.of(move);
//							}
//							if(board[i+num][j]==0){
//								//下方
//								move[0] = i+num;
//								move[1] = j;
//								return Optional.of(move);
//							}
//						}
//					}
//					if(j<=m-num) {
//						//横排
//						checkmate=true;
//						for(int k=1;k<num;k++) {
//							if (board[i][j] != board[i][j + k]) {
//								checkmate = false;
//							}
//						}
//						if(checkmate){
//							if(j>=1&&board[i][j-1]==0){
//								//左边
//								move[0] = i;
//								move[1] = j-1;
//								return Optional.of(move);
//							}
//							if(board[i][j+num]==0){
//								//右边
//								move[0]=i;
//								move[1]=j+num;
//								return Optional.of(move);
//							}
//
//						}
//					}
//				}
//			}
//		}
//		return Optional.empty();
//	}
	
	public long evaluate(int[][] board) {
		long res=0;
		for(int i=0;i<m;i++) {//number of cells
			for(int j=0;j<m;j++) {
				if(board[i][j] == 2) res += 1;
				else if (board[i][j] == 1) res -= 1;
			}
		}
		for(int i = 2; i < n; i++) {//number of i connected cells
			int[] temp = checkunblocked(board, i);
			res += (Math.pow(10, (double)i)*temp[0] - Math.pow(10, (double)i)*temp[1]);
			//System.out.println("#2 unblocked "+i+":"+temp[0] + " #1 unblocked "+i+":"+temp[1]);
		}
		for(int i = 2; i < n; i++) {//number of i connected cells
			int[] temp = checkoneblocked(board, i);
			res += (Math.pow(5, (double)i)*temp[0] - Math.pow(5, (double)i)*temp[1]);
			//System.out.println("#2 oneblocked "+i+":"+temp[0] + " #1 oneblocked "+i+":"+temp[1]);
		}

		return res;
	}

	public long evaluate_winner(int[][] board) {
		long res=0;
		int[] temp = check(board);
		//System.out.println("#2  "+":"+temp[0] + " #1  "+":"+temp[1]);
		res += ((Integer.MAX_VALUE/2)*temp[0] - (Integer.MAX_VALUE/2)*temp[1]);
		return res;
	}

	public static void main(String[] args) {
		int[][] board = new int[6][6];
		Heuristic h1 = new Heuristic(6, 4);
		board[0] = new int[] {0,0,0,0,0,0};
		board[1] = new int[] {0,2,2,2,0,0};
		board[2] = new int[] {0,0,0,0,0,0};
		board[3] = new int[] {0,0,0,0,0,0};
		board[4] = new int[] {0,0,0,0,0,0};
		board[5] = new int[] {0,0,0,0,0,0};

//		if(h1.almostWin(board,2).isPresent()) {
//			System.out.println(h1.almostWin(board,2).get()[0]);
//			System.out.println(h1.almostWin(board,2).get()[1]);
//		}
	}

}
