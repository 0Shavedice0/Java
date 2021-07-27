import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class ex5 extends JFrame implements ActionListener{
public JButton B[][]=new JButton[3][3];
//public JButton G[]=new JButton[9]; 一維按鈕
public byte x=0,y=0;
public int T=-1,D=1,L=-1,R=1;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ex5 frame = new ex5();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ex5() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setTitle("交換數值類小遊戲");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3,3, 0, 0));
		//random start
		int []Number=new int[9];
		for(int i=0;i<=8;i++) {
			Number[i]=i;
		}
		Random RN=new Random();
		for(int count=10000;count>=0;count--) {
			int temp=0;
			int a =RN.nextInt(9);
			int b =RN.nextInt(9);
			temp=Number[a];
			Number[a]=Number[b];
			Number[b]=temp;
		}
		//random end 
		//按鈕二維 
		for(byte j=0,i=0;j<3;j++) {
			for(byte k=0;k<3;k++) {
				if(Number[i]!=0)B[j][k] = new JButton(""+Number[i]);
				else {
					B[j][k] = new JButton("");
					x=j;
					y=k;
					T=y-1;
					D=y+1;
					L=x-1;
					R=x+1;
				}
				contentPane.add(B[j][k]);
				B[j][k].addActionListener(this);
				i++;
			}
		}
		//按鈕一維
		/*for(byte i=0;i<9;i++) {
			if(Number[i]!=0)G[i] = new JButton(""+Number[i]);
			else G[i] = new JButton("");
			contentPane.add(G[i]);
			G[i].addActionListener(this);
		 }*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(!e.getActionCommand().equals("")) {
			/*if(!B[x][y].getText().equals("")) {
				x=0;
				y=0;
				while(!B[x][y].getText().equals("")) {
					y++;
					if(y==3) {
						x++;
						y=0;
					}
				}
				T=y-1;
				D=y+1;
				L=x-1;
				R=x+1;
			}*/
			byte a=0,b=0;
			while(!B[a][b].getText().equals(e.getActionCommand())) {
				b++;
				if(b==3) {
					a++;
					b=0;
				}
			}
			if(a==L&&b==y||a==R&&b==y||a==x&&b==T||a==x&&b==D) {
				B[x][y].setText(B[a][b].getText());
				B[a][b].setText("");
				x=a;
				y=b;
				T=y-1;
				D=y+1;
				L=x-1;
				R=x+1;
			}
		}
	}

}