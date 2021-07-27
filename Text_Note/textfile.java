import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class textfile extends JFrame implements ActionListener {
	
	public JTextArea TA = new JTextArea();
	public boolean SAVE = false;
	public String road;
	private JPanel contentPane,Page1,Page2;
	private PaintPanel Paint;
	
	private JFrame SAR = new JFrame();
	private JFrame PF = new JFrame();
	private JTextField ST,RT;
	private JLabel label1,label2;
	private JButton ReplaceAll,Replace,Cancel,Next,PageS,PageR;
	private int SNtemp;
	private String SStemp;
	boolean OK=false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					textfile frame = new textfile();
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
	public textfile() {		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		this.setTitle("��r�s�边");
		TA.setLineWrap(true);
		TA.setWrapStyleWord(true);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu FM = new JMenu("�ɮ�");
		menuBar.add(FM);
		
		JMenu Edit = new JMenu("�s��");
		menuBar.add(Edit);
		
		JMenu Search = new JMenu("�j�M");
		menuBar.add(Search);
				
		JMenuItem NOPEN = new JMenuItem("�}�s�ɮ�");
		NOPEN.addActionListener(this);
		FM.add(NOPEN);
		
		JMenuItem OPEN = new JMenuItem("�}������");
		OPEN.addActionListener(this);
		FM.add(OPEN);
		
		JMenuItem SAVE = new JMenuItem("�x�s");
		SAVE.addActionListener(this);
		FM.add(SAVE);
		
		JMenuItem NSAVE = new JMenuItem("�t�s�s��");
		NSAVE.addActionListener(this);
		FM.add(NSAVE);
		
		JMenuItem SA = new JMenuItem("����");
		SA.addActionListener(this);
		Edit.add(SA);
		
		JMenuItem COPY = new JMenuItem("�ƻs");
		COPY.addActionListener(this);
		Edit.add(COPY);
		
		JMenuItem PAST = new JMenuItem("�K�W");
		PAST.addActionListener(this);
		Edit.add(PAST);
		
		JMenuItem Find = new JMenuItem("�j�M");
		Find.addActionListener(this);
		Search.add(Find);
		
		JMenuItem cover = new JMenuItem("���N");
		cover.addActionListener(this);
		Search.add(cover);
		
		JSeparator separator = new JSeparator();
		FM.add(separator);
		
		JMenuItem EXIT = new JMenuItem("����");
		EXIT.addActionListener(this);
		FM.add(EXIT);
		
		JMenu DESCRIPTION = new JMenu("����");
		menuBar.add(DESCRIPTION);
		
		JMenuItem ABOUT = new JMenuItem("����");
		ABOUT.addActionListener(this);
		DESCRIPTION.add(ABOUT);
		
		JMenu DrawPanel = new JMenu("ø�ϪO");
		menuBar.add(DrawPanel);
		
		JMenuItem Line = new JMenuItem("�e���u");
		Line.addActionListener(this);
		DrawPanel.add(Line);
		
		JMenuItem RECT = new JMenuItem("�e���");
		RECT.addActionListener(this);
		DrawPanel.add(RECT);
		
		JMenuItem OVAL = new JMenuItem("�e��");
		OVAL.addActionListener(this);
		DrawPanel.add(OVAL);
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(new JScrollPane(TA), BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("�}�s�ɮ�")) {
			//TA.setText("");
			//SAVE=false;
			//setTitle("");
			new PaintPanel();
		}
		else if (e.getActionCommand().equals("�}������")){
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				try{
					//System.out.print(chooser.getSelectedFile().getAbsolutePath());
					road=chooser.getSelectedFile().getAbsolutePath();
					FileReader fr = new FileReader(road);
					int a;
					String b = "";
					while((a=fr.read())!=-1) {
						System.out.print((char)a);
						b+=(char)a;
					}
					fr.close();
					TA.setText(b);
					this.setTitle(road);
					SAVE=true;
					System.out.println();
				}
				catch(FileNotFoundException yo){
					yo.printStackTrace();
				}
				catch(IOException yo){
					yo.printStackTrace();
				}
			}
		}
		
		else if(e.getActionCommand().equals("�t�s�s��")||e.getActionCommand().equals("�x�s")){
			JFileChooser saver = new JFileChooser();
			if(SAVE==false || e.getActionCommand().equals("�t�s�s��")) {
				String []TYPE = new String [8];
				TYPE[0]="�����(*.txt)";
				TYPE[1]="Word���(*.doc)";
				TYPE[2]="Word���(*.docx)";
				TYPE[3]="PDF(*.pdf)";
				TYPE[4]="txt";
				TYPE[5]="doc";
				TYPE[6]="docx";
				TYPE[7]="pdf";
				FileFilter []filter=new FileFilter[TYPE.length/2];
				for(int i =0,j=(int)TYPE.length/2;i<filter.length;i++,j++) {
					filter[i] = new FileNameExtensionFilter(TYPE[i], TYPE[j]);	
					saver.addChoosableFileFilter(filter[i]);
				}
				 saver.setFileFilter(filter[0]);
				 saver.setSelectedFile(new File("default name"));
				int returnVal = saver.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					try{
						String extensions;
						road=saver.getSelectedFile().getAbsolutePath();
						extensions=saver.getFileFilter().getDescription();
						extensions=extensions.substring(extensions.lastIndexOf("."),extensions.length()-1);
						road+=extensions;
						FileWriter fw = new FileWriter(road);
						fw.write(TA.getText().replaceAll("\n", "\r\n"));
						fw.flush();
						fw.close();
						this.setTitle(road);
						SAVE=true;
					}
					catch(IOException yo){
						yo.printStackTrace();
					}
				}
			}
			else {
				try {
					FileWriter fw = new FileWriter(road);
					fw.write(TA.getText().replaceAll("\n", "\r\n"));
					fw.flush();
					fw.close();
				}
				catch(IOException yo){
					yo.printStackTrace();
				}
			}
		}
		else if(e.getActionCommand().equals("����")) {
			PF.dispose();
			SAR.dispose();
			dispose();
		}
		else if(e.getActionCommand().equals("����"))TA.selectAll();
		else if(e.getActionCommand().equals("�ƻs"))TA.copy();
		else if(e.getActionCommand().equals("�K�W"))TA.paste();
		else if(e.getActionCommand().equals("����"))JOptionPane.showMessageDialog(null,"���§A���`�N","�`�N",JOptionPane.INFORMATION_MESSAGE);		
		else if(e.getActionCommand().equals("�j�M"))search(1);
		else if(e.getActionCommand().equals("���N")){
			String Comparison = e.getSource().getClass().getSimpleName();
			if(Comparison.equals("JMenuItem"))search(2);
			else if(Comparison.equals("JButton")) {
				String STA=TA.getText(),SRT=RT.getText(),SST=ST.getText();
				if(STA.indexOf(SST)==-1)JOptionPane.showMessageDialog(null,"�䤣��ҭn�M�䪺����","����",JOptionPane.INFORMATION_MESSAGE);
				else if(!SST.equals("")) {
					if(TA.getSelectedText()==null||!TA.getSelectedText().equals(SST)) TA.select(STA.indexOf(SST),STA.indexOf(SST)+SST.length());
					else if(!SST.equals("")&&TA.getSelectedText().equals(SST)){
						TA.replaceSelection(SRT);
						TA.select(STA.indexOf(SST),STA.indexOf(SST)+SST.length());
					}
				}
				else if(ST.getText().equals("")&&RT.getText().equals(""))JOptionPane.showMessageDialog(null,"�п�J���j�M�Ψ��N����r","����",JOptionPane.INFORMATION_MESSAGE);
				else if(ST.getText().equals(""))JOptionPane.showMessageDialog(null,"�j�M���i����","����",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("�������N")){
			if(TA.getText().indexOf(ST.getText())==-1)JOptionPane.showMessageDialog(null,"�䤣��ҭn�M�䪺����","����",JOptionPane.INFORMATION_MESSAGE);
			else if(ST.getText().equals(""))JOptionPane.showMessageDialog(null,"�j�M���i����","����",JOptionPane.INFORMATION_MESSAGE);
			else if(!ST.getText().equals("")){
				int count=0;
				int i =TA.getText().indexOf(ST.getText());
				while(TA.getText().indexOf(ST.getText(),i)!=-1) {
					if(TA.getText().indexOf(ST.getText(),i)>i) i=TA.getText().indexOf(ST.getText(),i)+1;
					else i++;
					count++;
				}
				TA.setText(TA.getText().replaceAll(ST.getText(),RT.getText()));
				JOptionPane.showMessageDialog(null,"���������C�@�@���N"+count+"�����","����",JOptionPane.INFORMATION_MESSAGE);	
			}
		}
		else if(e.getActionCommand().equals("�M��U�@��")){
			if(!ST.getText().equals("")) {
				if(!ST.getText().equals(SStemp))SNtemp=0;
				int a=TA.getText().indexOf(ST.getText(),SNtemp);
				int b=TA.getText().indexOf(ST.getText(),SNtemp)+ST.getText().length();
				TA.select(a, b);
				SStemp=ST.getText();
				if(a!=-1) SNtemp=a+1;
				else if(a==-1&&SNtemp==0) JOptionPane.showMessageDialog(null,"�䤣��ҭn�M�䪺����","����",JOptionPane.INFORMATION_MESSAGE);
				else if (a==-1&&SNtemp!=0) {
					JOptionPane.showMessageDialog(null,"�w�����j�M�A�N�q�Y�}�l","����",JOptionPane.INFORMATION_MESSAGE);
					SNtemp=0;
				}
			}
			else JOptionPane.showMessageDialog(null,"�j�M���i����","����",JOptionPane.INFORMATION_MESSAGE);	
		}
		else if(e.getActionCommand().equals("����"))SAR.dispose();
		else if(e.getActionCommand().equals("�j�M����")){
			SAR.remove(Page2);
			Page1.add(Next);
			Page1.add(label1);
			Page1.add(ST);
			Page1.add(Cancel);
			Page1.add(PageS);
			Page1.add(PageR);
			PageS.setBackground(Color.orange);
			PageR.setBackground(Color.WHITE);
			SAR.setTitle("�j�M");
			SAR.setContentPane(Page1);
			SAR.validate();
		}
		else if(e.getActionCommand().equals("���N����")){
			SAR.remove(Page1);
			Page2.add(Next);
			Page2.add(label1);
			Page2.add(ST);
			Page2.add(label2);
			Page2.add(RT);
			Page2.add(ReplaceAll);
			Page2.add(Replace);
			Page2.add(PageS);
			Page2.add(PageR);
			Page2.add(Cancel);
			PageS.setBackground(Color.WHITE);
			PageR.setBackground(Color.orange);
			SAR.setTitle("���N");
			SAR.setContentPane(Page2);
			SAR.validate();
		}
		else if (e.getActionCommand().equals("�e���u")) {
			PF();
			PF.setVisible(true);
			Paint.setDrawType(PaintPanel.DRAW_LINE);
			
		}
		else if (e.getActionCommand().equals("�e���")) {
			PF();
			PF.setVisible(true);
			Paint.setDrawType(PaintPanel.DRAW_RECT);
			
			
		}
		else if (e.getActionCommand().equals("�e��")) {
			PF();
			PF.setVisible(true);
			Paint.setDrawType(PaintPanel.DRAW_OVAL);
		}
	}
	
	private void search (int a) {
		SAR.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		int x=getBounds().x,y=getBounds().y,w=getBounds().width;
		SAR.setBounds(x+w, y, 570, 270);
		
		Page1 = new JPanel();
		Page2 = new JPanel();
		Page1.setBorder(new EmptyBorder(5, 5, 5, 5));
		Page2.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		Page1.setLayout(null);
		Page2.setLayout(null);
		
		Next = new JButton("�M��U�@��");
		Next.setBounds(360, 205, 109, 23);
		Next.addActionListener(this);
		
		label1 = new JLabel("�j�M�ؼ�");
		label1.setBounds(30, 77, 58, 15);
		
		ST = new JTextField();
		ST.setBounds(98, 74, 446, 21);
		ST.setColumns(10);		
		
		label2 = new JLabel("���N��");
		label2.setBounds(30, 136, 42, 15);
		
		RT = new JTextField();
		RT.setColumns(10);
		RT.setBounds(98, 130, 446, 21);
		RT.addActionListener(this);
		
		Cancel = new JButton("����");
		Cancel.setBounds(479, 205, 65, 23);
		Cancel.addActionListener(this);
		
		ReplaceAll = new JButton("�������N");
		ReplaceAll.setBounds(263, 205, 87, 23);
		ReplaceAll.addActionListener(this);
		
		Replace = new JButton("���N");
		Replace.setBounds(188, 205, 65, 23);
		Replace.addActionListener(this);
		
		PageS = new JButton("�j�M����");
		PageS.setBounds(1, 0, 87, 23);
		PageS.addActionListener(this);
		
		PageR = new JButton("���N����");
		PageR.setBounds(88, 0, 87, 23);
		PageR.addActionListener(this);
		
		PageS.setBackground(Color.orange);
		PageR.setBackground(Color.WHITE);
		SAR.setVisible(true);
		if(a==1) {
			Page1.add(Next);
			Page1.add(label1);
			Page1.add(ST);
			Page1.add(Cancel);
			Page1.add(PageS);
			Page1.add(PageR);
			PageS.setBackground(Color.orange);
			PageR.setBackground(Color.WHITE);
			SAR.setTitle("�j�M");
			SAR.setContentPane(Page1);
		}
		else if(a==2) {
			Page2.add(Next);
			Page2.add(label1);
			Page2.add(ST);
			Page2.add(label2);
			Page2.add(RT);
			Page2.add(ReplaceAll);
			Page2.add(Replace);
			Page2.add(PageS);
			Page2.add(PageR);
			Page2.add(Cancel);
			PageS.setBackground(Color.WHITE);
			PageR.setBackground(Color.orange);
			SAR.setTitle("���N");
			SAR.setContentPane(Page2);
		}
	}
	
	private void PF() {
		PF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		int x=getX(),y=getY(),h=getBounds().height;
		PF.setTitle("ø�ϪO");
		PF.setBounds(x, y+h, 570, 270);
		Paint = new PaintPanel();
		Paint.setBorder(new EmptyBorder(5, 5, 5, 5));
		Paint.setLayout(new BorderLayout(0, 0));
		PF.setContentPane(Paint);
	}
	
}



	

