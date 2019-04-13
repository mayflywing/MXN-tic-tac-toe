import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.*;
import javax.swing.WindowConstants;


public class GUI {
	DataUnit du = new DataUnit();
	JFrame console = new JFrame("Console");
	JPanel jp = new JPanel(null);
	JFrame button = new JFrame();

	//Control Button
	public static final int con_num = 7;
	JButton con_b[] = new JButton[con_num];
	String constr[] = {"Set m/n", "Start", "ManualMove", "AutoMove", "SetOpponent", "SetGame", "CreateGame"};

	//Control Button Listener
	class ConListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String target = e.getActionCommand();
			if (constr[0].equals(target)) {
				//set M/N
				du.setmn(Integer.valueOf(tfield[1].getText()), Integer.valueOf(tfield[2].getText()));
				String mess = String.format("M=%d, N=%d",Integer.valueOf(tfield[1].getText()), Integer.valueOf(tfield[2].getText()));
				tfield[9].setText(mess);
			} else if (constr[1].equals(target)) {
				//start
				new Thread(new Runnable() {
					@Override
					public void run() {
						du.gui = GUI.this;
						du.init(Integer.valueOf(tfield[3].getText()));
						if (Integer.valueOf(tfield[4].getText()) == 1) {
							du.check();//if AI moves first, after init(), AI plays once
						}
						display();

					}
				}).start();


			}else if (constr[2].equals(target)) {
				//Manual Move
				new Thread(new Runnable() {
					@Override
					public void run() {
						int x = Integer.valueOf(tfield[5].getText());
						int y = Integer.valueOf(tfield[6].getText());
						if (x < 0 || x >= du.m || y < 0 || y >= du.m || du.board[x][y] > 0)
							tfield[6].setText("Incorrect parameter");
						else {
							du.play(x, y);
						}
					}
				}).start();
			} else if (constr[3].equals(target)) {
				//auto move
				new Thread(new Runnable() {
					@Override
					public void run() {
						if(du.http.gameId!=null) {
							du.autoget();
						}
					}
				}).start();
			} else if (constr[4].equals(target)) {
				//set Opponent team
				//System.out.println(tfield[7].getText());
				du.http.setOpponent_teamId(tfield[7].getText());
				tfield[9].setText("Opponent Set");
			} else if (constr[5].equals(target)) {
				//set game
				du.http.ManualSetGameId(tfield[8].getText());
				tfield[9].setText("Game Set");
			} else if (constr[6].equals(target)) {
				//create game
				if (Integer.valueOf(tfield[4].getText()) == 1) {
					//we play firstly
					du.http.createGame(du.http.teamId, du.http.opponent_teamId, du.m, du.n);
				} else {
					//the opponent plays firstly
					du.http.createGame(du.http.opponent_teamId, du.http.teamId, du.m, du.n);

				}
				tfield[8].setText(du.http.gameId);
				tfield[9].setText("Game Created");

			}
		}
	}

	ConListener conlis = new ConListener();

	public static final int text_num = 10;
	JLabel label[] = new JLabel[text_num];
	String labelstr[] = {"Board", "Board size m", "Wincon n", "Number of AI"
			, "Does AI move first?", "next move x", "next move Y", "Opponent TeamId", "gameId", "Help"};
	JTextArea tfield[] = new JTextArea[text_num];
	String tfieldstr[] = {"Not Start", "3", "3", "1", "0", "0", "0", "", "", "See User Guide"};


	void display() {
//		System.out.println(fm.stringWidth("o"));
//		System.out.println(fm.stringWidth(" "));
//		System.out.println(fm.stringWidth("X"));
//		System.out.println(fm.stringWidth("_"));

		String str = "";
		str+="        ";
		for (int j = 0; j < du.m; j++) {
			str+=String.format("%02d",j)+"         ";
		}
		str+="\r\n";
		for (int i = 0; i < du.m; i++) {
			str+=String.format("%02d",i)+"  ";
			for (int j = 0; j < du.m; j++) {
				if (du.board[i][j] == 0) {
					str += String.format("%4s","_");
				}
				if (du.board[i][j] == 1) {
					str += String.format("%4s","o");
				}
				if (du.board[i][j] == 2) {
					str += String.format("%4s","X");
				}
				str+="        ";
			}
			str += "\r\n";
		}
		if (du.winner > 0)
			str += "player " + du.winner + " win!";
		if (du.draw) {
			str += "draw";
		}
		tfield[0].setText(str);
	}



	
	GUI(){
		console.setSize(1200, 800);
		console.setLocationRelativeTo(null);
		console.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//init button
		for(int i=0;i<con_num;i++){
			con_b[i]=new JButton(constr[i]);
			con_b[i].setBounds(20+130*i, 600, 120, 30);
			con_b[i].addActionListener(conlis);
			jp.add(con_b[i]);
		}
		
		
		//init Label and TextField
		for(int i=0;i<text_num;i++){
			label[i]=new JLabel(labelstr[i]);
			label[i].setBounds(30, 20+45*i, 120, 40);
			jp.add(label[i]);
			
			tfield[i]=new JTextArea(tfieldstr[i]);
			tfield[i].setBounds(160, 20+45*i, 120, 40);
			jp.add(tfield[i]);
		}
		tfield[0].setBounds(400, 20, 700, 400);
		label[0].setBounds(580, 450, 120, 40);
		

		console.setContentPane(jp);
		console.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
