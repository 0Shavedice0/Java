import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;

public class Crawler extends JFrame {
	
	private Boolean SAVE = false;
	//Selenium
	private File Road = new File(".");
	public WebDriver driver;
	public String LocalPath,FileName[]= {"�t�m.txt","���q.txt"};
	//�W�誫��-���s,��J��,�U�Ԧ����-----------------------------------------------
	private JButton addSCB = new JButton("�s�W");
	private JButton removeSCB = new JButton("����");
	private JButton SearchPath = new JButton("�s�����|");
	private JCheckBox AllInOne = new JCheckBox("��X���@���ɮ�");
	private JTextField SavePath = new JTextField();
	private JTextField AC = new JTextField();
	private JPasswordField PW = new JPasswordField();
	private boolean overwrite=false;
	public JComboBox SelectComboBox = new JComboBox();
	public JComboBox CompanyComboBox = new JComboBox();
	public JComboBox WebComboBox = new JComboBox(new DefaultComboBoxModel(new String[] {"�п��","CMoney","�]����","anue�d���","���}��T�[����"}));
	//�U�����e-�Ŀﶵ��,�W�U�����s,Panel--------------------------
	private String DownloadAll="����";
	//�T���}�C����
	//[A][B][C]
	//A�N��Ω�PANEL�٬OCHECKBOX
	//B�N��PANEL�b�ĴX���MCHECKBOX�b�ĴX��PANEL
	//C�N����
	private String DynamicName[][][]=new String[2][][];
	private JPanel Middle = new JPanel(new GridLayout(2,6,20,0));
	private int AllPageCount,NowPageCount=1;
	private JPanel DynamicPanel[][];
	private JCheckBox DynamicCheckBox[][];
	private Boolean check=false;
	private JComboBox Page = new JComboBox();
	private JButton Up = new JButton("�W�@��");
	private JButton Down = new JButton("�U�@��");
	public boolean Download[][];
	
	//��������-��r��,���s,�i�ױ�----------------------------------------
	public JTextArea Detail = new JTextArea();
	private JButton OpenFile = new JButton("�}�Ҧs�ɦ�}");
	private JButton DF = new JButton("��^��t�]�w");//default
	private JButton SelectAll = new JButton("�Ŀﶵ�إ���");
	private JButton clear = new JButton("�M�ŤĿﶵ��");
	private JButton save = new JButton("�x�s�{���t�m");
	private JButton execution = new JButton("����");
	private JButton close = new JButton("����");
	public JProgressBar PB = new JProgressBar();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Crawler frame = new Crawler();
					frame.setVisible(true);
					frame.setTitle("��������");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Crawler() {
		//339�H�e�����O�����]�w
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension Screen = Toolkit.getDefaultToolkit().getScreenSize();
		int W = 1200,H=700;
		setBounds((int)Math.round(Screen.width/2-(W/2)), (int)Math.round(Screen.height/2-(H/2)), W,H);
		setResizable(false);
		
		JPanel TopAndMiddle = new JPanel();
		JPanel MiddleBoard = new JPanel(new BorderLayout());
		
		JPanel ControlPage =new JPanel(new FlowLayout());
		ControlPage.add(Up);
		ControlPage.add(Page);
		ControlPage.add(Down);
		
		MiddleBoard.add(Middle,BorderLayout.CENTER);
		MiddleBoard.add(ControlPage,BorderLayout.SOUTH);
		
		JPanel Tool = new JPanel();
		
		TopAndMiddle.setLayout(new BorderLayout(0, 0));
		TopAndMiddle.setBorder(new EmptyBorder(0, 0, 0, 0));
		TopAndMiddle.add(Tool,BorderLayout.NORTH);
		TopAndMiddle.add(MiddleBoard,BorderLayout.CENTER);
		
		//�W�誫��-�U�Ԧ����
		//�W�誫��-�¤�r
		JLabel select = new JLabel("�t�m���");
		JLabel company = new JLabel("��ܤ��q");
		JLabel web = new JLabel("��ܺ���");
		JLabel path = new JLabel("�s�ɸ��|");
		JLabel account = new JLabel("�b��(�i��J�N��ݭn�Ψ�)");
		JLabel password = new JLabel("�K�X");
		JLabel savetype = new JLabel("�s�ɮ榡");
		//�W�誫��-�]�w�榡
		Tool.setPreferredSize(new Dimension(0,100));
		select.setBounds(6, 17, 61, 38);
		company.setBounds(202, 17, 191, 38);
		web.setBounds(398, 17, 191, 38);
		path.setBounds(594, 17, 87, 38);
		account.setBounds(790, 17, 191, 38);
		password.setBounds(986, 17, 191, 38);
		AC.setBounds(790, 55, 191, 38);
		SavePath.setBounds(594, 55, 191, 38);
		PW.setBounds(986, 55, 191, 38);
		addSCB.setBounds(71, 25, 61, 23);
		removeSCB.setBounds(136, 25, 61, 23);
		SearchPath.setBounds(698, 25, 87, 23);
		SelectComboBox.setBounds(6, 55, 191, 38);
		CompanyComboBox.setBounds(202, 55, 191, 38);
		WebComboBox.setBounds(398, 55, 191, 38);
		select.setHorizontalAlignment(SwingConstants.CENTER);
		company.setHorizontalAlignment(SwingConstants.CENTER);
		web.setHorizontalAlignment(SwingConstants.CENTER);
		path.setHorizontalAlignment(SwingConstants.CENTER);
		account.setHorizontalAlignment(SwingConstants.CENTER);
		password.setHorizontalAlignment(SwingConstants.CENTER);
		AC.setColumns(10);
		SelectComboBox.setEditable(true);
		Tool.setBorder(BorderFactory.createTitledBorder("�򥻳]�w"));
		AC.setEditable(false);
		PW.setEditable(false);
		
		//�W��[�J
		Tool.setLayout(null);
		Tool.add(select);
		Tool.add(company);
		Tool.add(web);
		Tool.add(path);
		Tool.add(account);
		Tool.add(password);
		Tool.add(SelectComboBox);
		Tool.add(CompanyComboBox);
		Tool.add(WebComboBox);
		Tool.add(SavePath);//textfield
		Tool.add(AC);
		Tool.add(PW);
		Tool.add(addSCB);//button
		Tool.add(removeSCB);
		Tool.add(SearchPath);
		//�i�U�����e-����
		
		//�i�U�����e-�]�w
		Page.setPreferredSize(new Dimension(80, 28));
		Up.setEnabled(false);
		Down.setEnabled(false);
		MiddleBoard.setBorder(BorderFactory.createTitledBorder("�U������"));
		
		//�i�U�����e-�[�J
		
		//��������
		JPanel BottomBorder = new JPanel(new BorderLayout());
		JPanel Bottom = new JPanel(new GridLayout(1,1,0,0));
		
		//��������-�¤�r
		JScrollPane DetailJs = new JScrollPane(Detail,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		String vs = "1.0";
		JLabel version = new JLabel("�������G"+vs);
		//��������-�]�w�榡
		BottomBorder.setPreferredSize(new Dimension(0,230));
		Detail.setFont(new Font("�L�n������", Font.PLAIN, 15));
		Detail.setLineWrap(true);
		Detail.setWrapStyleWord(true);
		Detail.setEditable(false);
		DetailJs.setPreferredSize(new Dimension(0,150));
		PB.setFont(new Font("�L�n������", Font.PLAIN, 18));
		PB.setForeground(new Color(51, 204, 102));
		PB.setStringPainted(true);
		//Detail.setBorder(BorderFactory.createTitledBorder("���檬�A"));
		DetailJs.setBorder(BorderFactory.createTitledBorder("���檬�A"));
		DetailJs.setBackground(Color.WHITE);
		//��������-�[�J
		BottomBorder.add(DetailJs,BorderLayout.NORTH);
		BottomBorder.add(PB,BorderLayout.CENTER);
		BottomBorder.add(Bottom,BorderLayout.SOUTH);
		Bottom.add(version);
		Bottom.add(OpenFile);
		Bottom.add(DF);
		Bottom.add(SelectAll);
		Bottom.add(clear);
		Bottom.add(save);
		Bottom.add(execution);
		Bottom.add(close);
		//���O��J
		getContentPane().add(TopAndMiddle,BorderLayout.CENTER);
		getContentPane().add(BottomBorder,BorderLayout.SOUTH);
		//��l�]�w
		
		//�W�[��ť�� �W��
		addSCB.addActionListener(new Listener());
		removeSCB.addActionListener(new Listener());
		SearchPath.addActionListener(new Listener());
		SelectComboBox.getEditor().getEditorComponent().addKeyListener(new Listener());
		SelectComboBox.addItemListener(new Listener());
		CompanyComboBox.addItemListener(new Listener());
		WebComboBox.addItemListener(new Listener());
		//�U�����e
		Up.addActionListener(new Listener());
		Page.addActionListener(new Listener());
		Page.addItemListener(new Listener());
		Down.addActionListener(new Listener());
		//����
		AC.addKeyListener(new Listener());
		OpenFile.addActionListener(new Listener());
		DF.addActionListener(new Listener());
		SelectAll.addActionListener(new Listener());
		clear.addActionListener(new Listener());
		save.addActionListener(new Listener());
		execution.addActionListener(new Listener());
		close.addActionListener(new Listener());
		
		//�ˬd�ɮ׬O�_�s�b
		CheckFile();
	}
	public class Listener implements ActionListener, KeyListener,ItemListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//�s�W�t�m
				if (e.getActionCommand().equals(addSCB.getActionCommand())) {
					Boolean OK=false;
				//�Ѧs�ɵo�X�T�� �۰ʱq�t�m��J�طs�W�t�m 
				if(overwrite) {
					overwrite=false;
					return;
				}
				if (SelectComboBox.getEditor().getItem().equals("")) {
					JOptionPane.showMessageDialog(null,"�W�٤��ର�ťաA�Э��s�R�W�C","���~",JOptionPane.WARNING_MESSAGE);
					OK=false;
					return;
					}
				for(int i =0;i<SelectComboBox.getItemCount();i++) {
					if(SelectComboBox.getEditor().getItem().equals(SelectComboBox.getItemAt(i))) {
					JOptionPane.showMessageDialog(null,"�t�m�W�٤w���ơA�Э��s�R�W�C","���~",JOptionPane.WARNING_MESSAGE);
					OK=false;
					break;
					}
					else OK=true;
				}
				if(OK||SelectComboBox.getItemCount()==0)SelectComboBox.addItem(SelectComboBox.getEditor().getItem());
			}
			//�����t�m
			else if (e.getActionCommand().equals(removeSCB.getActionCommand())) {
				int count=0;
				for(int i =0;i<SelectComboBox.getItemCount();i++) {
					if(!SelectComboBox.getEditor().getItem().equals(SelectComboBox.getItemAt(i)))count++;
				}
				if(SelectComboBox.getItemCount()==0) {
					JOptionPane.showMessageDialog(null,"�w�g�S���F��i�H�R���o�I","����",JOptionPane.INFORMATION_MESSAGE);
					//return;
				}
				else if (SelectComboBox.getEditor().getItem().equals("")&&SelectComboBox.getItemCount()>0) {
					JOptionPane.showMessageDialog(null,"�п�ܭn�R�������ءI","���~",JOptionPane.WARNING_MESSAGE);
					//return;
				}
				else if(count==SelectComboBox.getItemCount()) {
					JOptionPane.showMessageDialog(null,"�S�����w���t�m�ﶵ����R���I","���~",JOptionPane.WARNING_MESSAGE);
					return;
				}
				else if(JOptionPane.showConfirmDialog(null, "�T�{�O�_�n����?","�w������",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					SelectComboBox.removeItem(SelectComboBox.getSelectedItem());
					SelectComboBox.getEditor().setItem("");
				}
			}
			//�s���s�ɸ��|
			else if (e.getActionCommand().equals(SearchPath.getActionCommand())){
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = chooser.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						if(SavePath.getText()!=chooser.getSelectedFile().getAbsolutePath())SAVE=false;
						SavePath.setText(chooser.getSelectedFile().getAbsolutePath());
					}
				}
			//�W�@��
			else if (e.getActionCommand().equals(Up.getActionCommand())) {
				NowPageCount--;
				Page.setSelectedIndex(Page.getSelectedIndex()-1);
			}
			//�U�@��
			else if (e.getActionCommand().equals(Down.getActionCommand())) {
				NowPageCount++;
				Page.setSelectedIndex(Page.getSelectedIndex()+1);
			}
			//�}�Ҧs�ɦ�m
			else if (e.getActionCommand().equals(OpenFile.getActionCommand())) {
				
					try {
						File local = new File(SavePath.getText());
						if(local.isDirectory()) {
							if(Desktop.isDesktopSupported())Desktop.getDesktop().open(local);	
						}
						else {
							JOptionPane.showMessageDialog(null,"�п�ܦ��Ī��s�ɸ��|�I\n�ѤW�誺�s�����|�άO��J�ذ����","���~",JOptionPane.WARNING_MESSAGE);
							return;
						}
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,"�п�ܥ��T�����|�I\n�ѤW�誺�s�����|�άO��J�ذ����","���~",JOptionPane.WARNING_MESSAGE);
						return;
					}
			}
			//��^��t�]�w
			else if(e.getActionCommand().equals(DF.getActionCommand())) {
				if(JOptionPane.showConfirmDialog(null, "�o�N�O���i�f�����G�A�T�{�O�_�n��^��t�]�w?","�w������",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					//������s
					SelectComboBox.removeAllItems();
					CompanyComboBox.removeAllItems();
					WebComboBox.setSelectedIndex(0);
					SavePath.setText("");
					AC.setText("");
					PW.setText("");
					Detail.setText("");
					PB.setValue(0);
					//�����÷s�W�ɮ�
					for(int i = 0;i<FileName.length;i++) {
						File FileCheck = new File(LocalPath+FileName[i]);
						FileCheck.delete();
					}
					CheckFile();
					JOptionPane.showMessageDialog(null,"�t�Τw��^��t�]�w!\n���q��ƽп�ܬ۹����������A�ê�������K�|�o��Ҧ��i�d�ߤ��q�C","����",JOptionPane.INFORMATION_MESSAGE);
					SAVE=true;
				}
			}
			//�Ŀﶵ�إ���
			else if(e.getActionCommand().equals(SelectAll.getActionCommand())) {
				if(WebComboBox.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null,"�п�ܥ���@�Ӻ���(���]�t\"�п��\")�A�_�h�L�k�ϥΡC","���~",JOptionPane.WARNING_MESSAGE);
				}
				else if(WebComboBox.getSelectedIndex()!=0) {
					for(int i = 0;i<DynamicCheckBox.length;i++) {
						for(int j = 0;j<DynamicCheckBox[i].length;j++) {
							DynamicCheckBox[i][j].setSelected(true);
							Download[i][j]=true;
						}	
					}
				}
				SAVE=false;
			}
			//�M�ŤĿﶵ��
			else if(e.getActionCommand().equals(clear.getActionCommand())) {
				if(WebComboBox.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null,"�п�ܥ���@�Ӻ���(���]�t\"�п��\")�A�_�h�L�k�ϥΡC","���~",JOptionPane.WARNING_MESSAGE);
				}
				if(WebComboBox.getSelectedIndex()!=0) {
					for(int i = 0;i<DynamicCheckBox.length;i++) {
						for(int j = 0;j<DynamicCheckBox[i].length;j++) {
							DynamicCheckBox[i][j].setSelected(false);
							Download[i][j]=false;
						}	
					}	
				}
				SAVE=false;
			}
			//�x�s�{���t�m	
			else if(e.getActionCommand().equals(save.getActionCommand())) {
					try {
						//�n�s�������
						if(SelectComboBox.getSelectedItem().equals("�п��")) {
							JOptionPane.showMessageDialog(null,"\"�п��\"�ä��O�@�Ӧ��Ī��t�m���ءA�д��@���x�s�C","����",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						overwrite=true;
						addSCB.doClick();
						LocalPath=Road.getCanonicalPath()+File.separator;
						String SelectSave="�t�m�W��:"+SelectComboBox.getSelectedItem().toString();
						String CompanySave="��ܤ��q�W��:"+CompanyComboBox.getSelectedItem().toString();
						String WebSave="��ܺ����W��:"+WebComboBox.getSelectedItem().toString();
						String CheckBoxSave="�Ŀﶵ��:\r\n";
						String SP = "�s�ɸ��|:"+SavePath.getText();
						String account="�b��:";//+AC.getText();
						String password="�K�X:";//+PW.hashCode();
						for(int i = 0;i<DynamicCheckBox.length;i++) {
							for(int j = 0;j<DynamicCheckBox[i].length;j++) {
								CheckBoxSave+=DynamicCheckBox[i][j].isSelected();
								if(j!=DynamicCheckBox[i].length-1)CheckBoxSave+=",";
							}
							if(i!=DynamicCheckBox.length-1)CheckBoxSave+="\r\n";
						}
						String FinalOutput=SelectSave+"\r\n"+CompanySave+"\r\n"+WebSave+"\r\n"+SP+"\r\n"+account+"\r\n"+password+"\r\n"+CheckBoxSave;
						//Ū��
						FileReader fr = new FileReader(LocalPath+FileName[0]);
						String temp="";
						int j;
						while((j=fr.read())!=-1)temp+=(char)j;
						fr.close();
						//�g��
						FileWriter fw = new FileWriter(LocalPath+FileName[0]);
						if(temp.indexOf(SelectSave)==-1&&temp!="")FinalOutput=temp+"\r\n"+FinalOutput;
						else {
							int star=temp.indexOf(SelectSave);
							int end=temp.indexOf("-",star)-1;
							temp=temp.replace(temp.substring(star,end), FinalOutput);
							FinalOutput=temp;
						}
						fw.write(FinalOutput+"\r\n-------------------------------------------------");
						fw.flush();
						fw.close();
						JOptionPane.showMessageDialog(null,"�t�m����\""+SelectComboBox.getSelectedItem()+"\"�w�g�x�s����!","����",JOptionPane.INFORMATION_MESSAGE);
						SAVE=true;
						}
						catch (FileNotFoundException X) {
						// TODO Auto-generated catch block
						X.printStackTrace();
						}
						catch (IOException X) {
						// TODO Auto-generated catch block
						X.printStackTrace();
						}
			}
			//����
			else if(e.getActionCommand().equals(execution.getActionCommand())) {
				int count=0;
				String wrongtxt="";
				boolean CHOSE=false;
				File FileCheck = new File(SavePath.getText());
				if(CompanyComboBox.getSelectedIndex()==0) {
					count++;
					wrongtxt+="\n��ܤ��q";
				}
				if(WebComboBox.getSelectedIndex()==0) {
					count++;
					wrongtxt+="\n��ܺ���";
				}
				else {
					for(int i=0;i<DynamicCheckBox.length;i++) {
						for(int j=0;j<DynamicCheckBox[i].length;j++) {
							if(DynamicCheckBox[i][j].isSelected()) {
								CHOSE=true;
								break;
							}
						}
						if(CHOSE)break;
					}
				}
				if(!FileCheck.isDirectory()) {
					count++;
					wrongtxt+="\n�ϥΦ��Ī��s�ɸ��|";
				}
				
						
				if(!CHOSE)wrongtxt+="\n�ܤ֤Ŀ�@�ӤU������";
				
				if(count==0 && CHOSE||CompanyComboBox.getItemCount()==1) {
					if(WebComboBox.getSelectedIndex()==0) {
						JOptionPane.showMessageDialog(null,"�Y�n������q��ơA�аO�o����۹�������!","���~",JOptionPane.WARNING_MESSAGE);
					}
					else {
						Detail.setText("���Ū�����A�еy��C\n");
						PB.setValue(0);
						execution.setEnabled(false);
						Thread CPU2=new Counting();
						CPU2.start();	
					}	
				}
				else {
					JOptionPane.showMessageDialog(null,"�п���H�U���Ϊ����ؤ~������檦��!"+wrongtxt,"���~",JOptionPane.WARNING_MESSAGE);
				}
			}
			
			//����
			else if (e.getActionCommand().equals(close.getActionCommand())) {
				if(!execution.isEnabled())driver.quit();
				System.exit(0);
			}
			//�]�w�W�U���O�_�i��
			if(Page.getItemCount()==0||AllPageCount==1){
				Up.setEnabled(false);
				Down.setEnabled(false);
			}
			else if(NowPageCount==1&&AllPageCount>1) {
				Up.setEnabled(false);
				Down.setEnabled(true);
				
			}
			else if(NowPageCount==AllPageCount&&AllPageCount>1) {
				Up.setEnabled(true);
				Down.setEnabled(false);
			}
			else {
				Up.setEnabled(true);
				Down.setEnabled(true);
			}
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			SelectComboBox.showPopup();
			
			
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			byte AFTER_CHANGE=1;
			if(e.getSource().equals(CompanyComboBox)&&e.getStateChange()==AFTER_CHANGE)SAVE=false;
			if(e.getSource().equals(SelectComboBox)&&e.getStateChange()==AFTER_CHANGE){
					try {
						//Ū��
						String temp="";
						for(int i=0;i<FileName.length;i++) {
							FileReader fr = new FileReader(LocalPath+FileName[i]);
							int j;
							while((j=fr.read())!=-1)temp+=(char)j;
							fr.close();
						}
						//�����۹����W�٪���ư϶�
						int star;
						int end;
						star=temp.indexOf(SelectComboBox.getSelectedItem().toString());
						if(star==-1)return;
						end=temp.indexOf("-------------------------------------------------",star)-1;
						String location="";
						location=temp.substring(star,end);
						
						//Ū���t�m���e
						String scout[]= {"��ܤ��q�W��:","��ܺ����W��:","�s�ɸ��|:","�b��:","�K�X:"};
						for(int i=0;i<scout.length;i++) {
							star=location.indexOf(scout[i])+scout[i].length();
							end=location.indexOf("\r\n",star);
							if(i==0)CompanyComboBox.setSelectedItem(location.substring(star,end));
							else if(i==1)WebComboBox.setSelectedItem(location.substring(star,end));
							else if(i==2)SavePath.setText(location.substring(star,end));
							else if(i==3)AC.setText(location.substring(star,end));
							else if(i==4)PW.setText(location.substring(star,end));
							
						}
						//�p�G�����O��"�п��"�N���������F
						if(WebComboBox.getSelectedIndex()==0)return;
						//���t�Ŀﶵ�ت�true/false���r��
						star=location.indexOf("�Ŀﶵ��:");
						location=location.replace("�Ŀﶵ��:\r\n", "");
						String tempA[]=location.substring(star).split("\r\n");
						String DownloadCheck[][]=new String[tempA.length][];
						for(int i =0;i<tempA.length;i++) {
							DownloadCheck[i]=new String[tempA[i].split(",").length];
							for(int j =0;j<tempA[i].length();j++) {
								DownloadCheck[i]=tempA[i].split(",");
							}
						}
						//�z�L�r�ꪺtrue/false�h���U�����ذ��Ŀ�
						for(int i=0;i<DynamicCheckBox.length;i++) {
							for(int j=0;j<DynamicCheckBox[i].length;j++) {
								if(DownloadCheck[i][j].equals("true"))DynamicCheckBox[i][j].setSelected(true);
								else DynamicCheckBox[i][j].setSelected(false);
							}
						}
					}
					catch (IOException x) {
						// TODO Auto-generated catch block
						x.printStackTrace();
					}
				
			}
			//��ܺ���
			else if(e.getSource().equals(WebComboBox)&&e.getStateChange()==AFTER_CHANGE) {
				if(WebComboBox.getSelectedIndex()==2) {
					AC.setEditable(true);
					PW.setEditable(true);
				}
				else {
					AC.setEditable(false);
					PW.setEditable(false);	
				}
				switch(WebComboBox.getSelectedIndex()) {
				case 1:
					//�۰ʱN�C���i�U�����ح��6�Ӱ϶�
					String CMData[]={"�򥻤��R","�w�X���R","�]�Ȥ��R"};
					String CMoneyPanel[][] = null;
					if(CMData.length%6==0) CMoneyPanel=new String [(int)Math.ceil(CMData.length/6)][];
					else if(CMData.length%6!=0)CMoneyPanel=new String [(int)Math.ceil(CMData.length/6+1)][];
					for(int i=0,temp=0,temp2=CMData.length;i<CMoneyPanel.length;i++,temp2-=6) {
						if(temp2>=6) {
							CMoneyPanel[i]=new String[6];
							for(int j=0;j<6;j++,temp++)CMoneyPanel[i][j]=CMData[temp];
						}
						else if (temp2<6) {
							CMoneyPanel[i]=new String[temp2];
							for(int j=0;j<temp2;j++,temp++)CMoneyPanel[i][j]=CMData[temp];
						}
					}
					String CMoneyCheckBox[][]={{"����","�򥻸��","�ѧQ�F��","�禬�l�B","����","���q��","�b�Ȥ�"},
											{"����","�w�XK�u","�ӽ�����","�T�j�k�H","�ĸ�Ĩ�","���O����","�D�O���"},
											{"����","�겣�t�Ū�","�l�q��","�{���y�q��","�]�Ȥ�v","�����O���"}};
					DynamicName[0]=new String[CMoneyPanel.length][];
					DynamicName[1]=new String[CMoneyCheckBox.length][];
					//Panel�W��
					for(int i =0;i<CMoneyPanel.length;i++) {
						DynamicName[0][i]=new String[CMoneyPanel[i].length];
						for(int j=0;j<CMoneyPanel[i].length;j++) {
							DynamicName[0][i][j]=CMoneyPanel[i][j];
						}
					}
					//CheckBox�W��
					for(int i =0;i<CMoneyCheckBox.length;i++) {
						DynamicName[1][i]=new String[CMoneyCheckBox[i].length];
						for(int j=0;j<CMoneyCheckBox[i].length;j++) {
							DynamicName[1][i][j]=CMoneyCheckBox[i][j];
						}
					}
					break;
				case 2:
					String DogData[]={"�̷s�ʺA(�s�D�B�}���峹�B���i)","�Ѳ����E","�]�ȳ���","��Q��O","�w���ʤ��R","�����O���R","���ȵ���","���ʻP�w�X"};
					String DogPanel[][] = null;
					if(DogData.length%6==0) DogPanel=new String [(int)Math.ceil(DogData.length/6)][];
					else if(DogData.length%6!=0)DogPanel=new String [(int)Math.ceil(DogData.length/6+1)][];
					for(int i=0,temp=0,temp2=DogData.length;i<DogPanel.length;i++,temp2-=6) {
						if(temp2>=6) {
							DogPanel[i]=new String[6];
							for(int j=0;j<6;j++,temp++)DogPanel[i][j]=DogData[temp];
						}
						else if (temp2<6) {
							DogPanel[i]=new String[temp2];
							for(int j=0;j<temp2;j++,temp++)DogPanel[i][j]=DogData[temp];
						}
					}
					String DogCheckBox[][]={{"����","2020�~","2019�~","2018�~","2017�~","2016�~","2015�~","2014�~","2013�~"},
											{"����","�ư��a�p�Ѱ��E","�w�s�Ѱ��E","�����Ѱ��E","�K�y�Ѱ��E"},
											{"����","�C���禬","�C�Ѭվl","�C�Ѳb��","�l�q��","�`�겣","�t�ũM�ѪF�v�q","�{���y�q��","�ѧQ�F��","�q�l��"},
											{"����","�Q���v","��~�O�Ω�Ѳv","�~�~���|�e�b�Q���","ROE/ROA","�������R","�g��P���O","��B�g��Ѽ�","��~�{���y��b�Q��","�{���ѧQ�o��v"},
											{"����","�]�ȵ��c��v","�y�t�ʤ�v","�Q���O�٭���","�{���y�q���R","��~�{���y��b�Q��","�վl�A����v"},
											{"����","���禬�����v","�禬�����v","��Q�����v","��~�Q�q�����v","�|��b�Q�����v","�C�Ѭվl�����v"},
											{"����","���q�����","���q��e�y��","�ѻ��b�Ȥ����","�ѻ��b�Ȥ�e�y��","�{���ѧQ�ާQ�v","�����{���ѧQ�ާQ�v","�����{���ѧQ�e�y��"},
											{"����","���I�w�X�ʦV","���ʫ��Ѥ��","���ʫ��ѽ����","���ʹS���[��"}};
					DynamicName[0]=new String[DogPanel.length][];
					DynamicName[1]=new String[DogCheckBox.length][];
					//Panel�W��
					for(int i =0;i<DogPanel.length;i++) {
						DynamicName[0][i]=new String[DogPanel[i].length];
						for(int j=0;j<DogPanel[i].length;j++) {
							DynamicName[0][i][j]=DogPanel[i][j];
						}
					}
					//CheckBox�W��
					for(int i =0;i<DogCheckBox.length;i++) {
						DynamicName[1][i]=new String[DogCheckBox[i].length];
						for(int j=0;j<DogCheckBox[i].length;j++) {
							DynamicName[1][i][j]=DogCheckBox[i][j];
						}
					}
					break;
				case 3:
					String AnueData[]={"1","2","3","4","5","6","7","8","9"};
					String AnuePanel[][] = null;
					if(AnueData.length%6==0) AnuePanel=new String [(int)Math.ceil(AnueData.length/6)][];
					else if(AnueData.length%6!=0)AnuePanel=new String [(int)Math.ceil(AnueData.length/6+1)][];
					for(int i=0,temp=0,temp2=AnueData.length;i<AnuePanel.length;i++,temp2-=6) {
						if(temp2>=6) {
							AnuePanel[i]=new String[6];
							for(int j=0;j<6;j++,temp++)AnuePanel[i][j]=AnueData[temp];
						}
						else if (temp2<6) {
							AnuePanel[i]=new String[temp2];
							for(int j=0;j<temp2;j++,temp++)AnuePanel[i][j]=AnueData[temp];
						}
					}
					String AnueCheckBox[][]={{"����","�򥻸��","�ѧQ�F��","�禬�l�B","����","���q��","�b�Ȥ�"},
											{"����","�w�XK�u","�ӽ�����","�T�j�k�H","�ĸ�Ĩ�","���O����","�D�O���"},
											{"����","�겣�t�Ū�","�l�q��","�{���y�q��","�]�Ȥ�v","�����O���"}};
					DynamicName[0]=new String[AnuePanel.length][];
					DynamicName[1]=new String[AnueCheckBox.length][];
					//Panel�W��
					for(int i =0;i<AnuePanel.length;i++) {
						DynamicName[0][i]=new String[AnuePanel[i].length];
						for(int j=0;j<AnuePanel[i].length;j++) {
							DynamicName[0][i][j]=AnuePanel[i][j];
						}
					}
					//CheckBox�W��
					for(int i =0;i<AnueCheckBox.length;i++) {
						DynamicName[1][i]=new String[AnueCheckBox[i].length];
						for(int j=0;j<AnueCheckBox[i].length;j++) {
							DynamicName[1][i][j]=AnueCheckBox[i][j];
						}
					}
					break;
				case 4:
					String OpenData[]={"1","2","3","4","5","6","7","8","9"};
					String OpenDataPanel[][] = null;
					if(OpenData.length%6==0) OpenDataPanel=new String [(int)Math.ceil(OpenData.length/6)][];
					else if(OpenData.length%6!=0)OpenDataPanel=new String [(int)Math.ceil(OpenData.length/6+1)][];
					for(int i=0,temp=0,temp2=OpenData.length;i<OpenDataPanel.length;i++,temp2-=6) {
						if(temp2>=6) {
							OpenDataPanel[i]=new String[6];
							for(int j=0;j<6;j++,temp++)OpenDataPanel[i][j]=OpenData[temp];
						}
						else if (temp2<6) {
							OpenDataPanel[i]=new String[temp2];
							for(int j=0;j<temp2;j++,temp++)OpenDataPanel[i][j]=OpenData[temp];
						}
					}
					String OpenDataCheckBox[][]={{"����","�򥻸��","�ѧQ�F��","�禬�l�B","����","���q��","�b�Ȥ�"},
											{"����","�w�XK�u","�ӽ�����","�T�j�k�H","�ĸ�Ĩ�","���O����","�D�O���"},
											{"����","�겣�t�Ū�","�l�q��","�{���y�q��","�]�Ȥ�v","�����O���"}};
					DynamicName[0]=new String[OpenDataPanel.length][];
					DynamicName[1]=new String[OpenDataCheckBox.length][];
					//Panel�W��
					for(int i =0;i<OpenDataPanel.length;i++) {
						DynamicName[0][i]=new String[OpenDataPanel[i].length];
						for(int j=0;j<OpenDataPanel[i].length;j++) {
							DynamicName[0][i][j]=OpenDataPanel[i][j];
						}
					}
					//CheckBox�W��
					for(int i =0;i<OpenDataCheckBox.length;i++) {
						DynamicName[1][i]=new String[OpenDataCheckBox[i].length];
						for(int j=0;j<OpenDataCheckBox[i].length;j++) {
							DynamicName[1][i][j]=OpenDataCheckBox[i][j];
						}
					}
					break;
				}
				
				Middle.removeAll();
				DynamicPanel=new JPanel[DynamicName[0].length][];
				DynamicCheckBox = new JCheckBox[DynamicName[1].length][];
				Download=new boolean[DynamicCheckBox.length][];
				for(int i=0;i<DynamicName[0].length;i++) DynamicPanel[i]=new JPanel[DynamicName[0][i].length];
				for(int i=0;i<DynamicName[1].length;i++) {
					DynamicCheckBox[i]=new JCheckBox[DynamicName[1][i].length];
					Download[i]=new boolean[DynamicCheckBox[i].length];
				}
				for(int x=0;x<DynamicName[1].length;x++) {
					for(int y=0;y<DynamicName[1][x].length;y++) {
						DynamicCheckBox[x][y] = new JCheckBox();
						DynamicCheckBox[x][y].setText(DynamicName[1][x][y]);
						DynamicCheckBox[x][y].addItemListener(new Listener());
					}	
				}
			for(int i=0;i<DynamicName[0].length;i++) {
				for(int j=0;j<DynamicName[0][i].length;j++) {
					DynamicPanel[i][j]=new JPanel();
					DynamicPanel[i][j].setLayout(new GridLayout(5,0,0,0));
					DynamicPanel[i][j].setBorder(BorderFactory.createTitledBorder(DynamicName[0][i][j]));
				}
			}
			for(int k=0,i=0,j=0;k<DynamicCheckBox.length;k++,j++) {
				if(j==6) {
					i++;
					j=0;
				}
				for(int x=0;x<DynamicCheckBox[k].length;x++) {
					DynamicPanel[i][j].add(DynamicCheckBox[k][x]);
				}
			}
			Middle.revalidate();
			Middle.repaint();
			AllPageCount=DynamicPanel.length;
			Page.removeAllItems();
			for(int i =1;i<=AllPageCount;i++)Page.addItem("��"+i+"��");
			check=true;
			SAVE=false;
			}
			//����
			else if(e.getSource().equals(Page)&&e.getStateChange()==AFTER_CHANGE) {
				Middle.removeAll();
				NowPageCount =Page.getSelectedIndex()+1;
				if(WebComboBox.getSelectedIndex()==0)Page.removeAllItems();
				else {
					for(int i =0;i<6;i++) {
						Middle.add(DynamicPanel[Page.getSelectedIndex()][i]);
						if(DynamicPanel[Page.getSelectedIndex()].length==i+1)break;
					}
				}
				Middle.revalidate();
				Middle.repaint();
			}
			else if(check){
				//count�p�⦳�X�ӳQ���
				int count[]=new int[DynamicCheckBox.length];
				for(int i=0;i<DynamicCheckBox.length;i++) {
					count[i]=0;
					for(int j=0;j<DynamicCheckBox[i].length;j++) {
						if(DynamicCheckBox[i][j].isSelected())count[i]++;
					}
				}
				//����P�۰ʤĿ�ŦX����
				for(int i=0;i<DynamicCheckBox.length;i++) {
					for(int j=0;j<DynamicCheckBox[i].length;j++) {
						//�������ǭn�U��
						Download[i][j]=DynamicCheckBox[i][j].isSelected();
						//����P��������ʧ@ �H�δ���L���ذ��Ŀ�����ʧ@
						if(e.getSource().equals(DynamicCheckBox[i][0])) {
							if(DynamicCheckBox[i][0].isSelected()&&j!=0) {
								check=false;
								DynamicCheckBox[i][j].setSelected(true);
								Download[i][j]=DynamicCheckBox[i][j].isSelected();
								
							}
							else if(!DynamicCheckBox[i][0].isSelected()&&j!=0) {
								check=false;
								DynamicCheckBox[i][j].setSelected(false);
								Download[i][j]=DynamicCheckBox[i][j].isSelected();
							}
						}
						//�۰��˴��Ҧ����جO�_�Q��� �Y���N����Ψ�������
						else if(!e.getSource().equals(DynamicCheckBox[i][0])) {
							if(count[i]==DynamicCheckBox[i].length-1&&!DynamicCheckBox[i][0].isSelected()) {
								check=false;
								DynamicCheckBox[i][0].setSelected(true);
								break;
							}
							else if (count[i]==DynamicCheckBox[i].length-1&&DynamicCheckBox[i][0].isSelected()) {
								check=false;
								DynamicCheckBox[i][0].setSelected(false);
								break;
							}
						}	
					}
				}
				check=true;
			}
		}
	}
	
	public class Counting extends Thread{
		@Override
		public void run() {
			try {
				//�U���ùL�o���Ƥ��q���
				if(CompanyComboBox.getItemCount()==1) {
					PB.setValue(0);
					Detail.setText(Detail.getText()+"���q�t�m�I�����C\n");
					CompanyFilter();
					Detail.setText(Detail.getText()+"���q�t�m�I�������I");
					execution.setEnabled(true);
				}
				//�U�����w���
				else {
					System.setProperty("webdriver.chrome.driver",LocalPath+"chromedriver.exe");
					String chose=CompanyComboBox.getSelectedItem().toString();
					chose=chose.substring(0,chose.indexOf("-"));
					String url="";
					//�������y�B������ܤ��O��CMoney�B�]�����Banue�d����B���}�]���[����
					switch(WebComboBox.getSelectedIndex()) {
					case 1:
						url="https://www.cmoney.tw/finance/f00040.aspx?s="+chose;
						break;
					case 2:
						JOptionPane.showMessageDialog(null,"�o�ӳ����|�b���դ��A�Ȥ��}��ϥΡA�ٽШ��̡C","�Ȥ��}��",JOptionPane.WARNING_MESSAGE);
						execution.setEnabled(true);
						break;
					case 3:
						execution.setEnabled(true);
						break;
					case 4:
						execution.setEnabled(true);
						break;
					}
					if(url=="")return;
					ChromeOptions options = new ChromeOptions();
					options.addArguments("window-size=1400,800");
					driver = new ChromeDriver(options);
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					driver.get(url);
					Thread.sleep(1000);
					int count=0;
					for(int i =0;i<Download.length;i++) {
						for(int j =0;j<Download[i].length;j++) {
							if(j==0)continue;
							if(Download[i][j])count++;
						}	
					}
					String temp ="";
					/*
					for(int i =1;i<=5;i++) {
						driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li["+i+"]/a")).click();
						Thread.sleep(1500);//*[@id="PageListTab"]/a[2]
						temp+=driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li["+i+"]/a")).getText()+"\n";
						/*
						if(i==1)temp+="�겣�t�Ū�\n";
						else if(i==2)temp+="�l�q��\n";
						else if(i==3)temp+="�{���y�q��\n";
						else if(i==4)temp+="�]�Ȥ�v\n";
						else if(i==5)temp+="�����O���\n";
						*/
					/*
							temp+=driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[1]")).getText()+"\n";
							temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";
							driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).click();
							temp+=driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).getText()+"\n";
							temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";	
						if(i==2||i==3) {
							driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[3]")).click();
							temp+=driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[3]")).getText()+"\n";
							temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";
						}
						else if(i==4) {
							
							//*[@id="MainContent"]/ul/li[2]/article/div/div/div/table
						}
						
						////*[@id="MainContent"]/ul/li/article/div[2]/div/table/
						//*[@id="MainContent"]/ul/li[2]/article/div/div/div/table
					}*/
					
						
					
					
					//�겣�t�Ū�
					
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[1]/a")).click();
					Thread.sleep(1500);
					temp+=driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[1]/a")).getText()+"\n";
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[1]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[4]/article/div[2]/ul/li/article/div[2]/div/table")).getText()+"\n";
					Thread.sleep(1500);
					driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).click();
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[4]/article/div[2]/ul/li/article/div[2]/div/table")).getText()+"\n";
					temp+="----------------------------------------------------------------------------------------------------------\n";
					PB.setValue(PB.getValue()+20);
					Detail.setText(Detail.getText()+"�겣�t�Ū�U�������I\n");
					Thread.sleep(1500);
					
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[2]/a")).click();					
					temp+=driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[2]/a")).getText()+"\n";
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[1]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";
					Thread.sleep(1500);
					driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).click();
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";
					Thread.sleep(1500);
					driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[3]")).click();
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[3]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";
					temp+="----------------------------------------------------------------------------------------------------------\n";
					PB.setValue(PB.getValue()+20);
					Detail.setText(Detail.getText()+"�l�q��U�������I\n");
					Thread.sleep(1500);
					
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[3]/a")).click();					
					temp+=driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[3]/a")).getText()+"\n";
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[1]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";
					Thread.sleep(1500);
					driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).click();
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";
					Thread.sleep(1500);
					driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[3]")).click();
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[3]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table")).getText()+"\n";
					temp+="----------------------------------------------------------------------------------------------------------\n";
					PB.setValue(PB.getValue()+20);
					Detail.setText(Detail.getText()+"�{���y�q��U�������I\n");
					Thread.sleep(1500);
					
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[4]/a")).click();
					temp+=driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[4]/a")).getText()+"\n";
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[1]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[2]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[3]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[4]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[5]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[6]/article/div/div/div/table")).getText()+"\n";
					Thread.sleep(1500);
					driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).click();
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[2]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[3]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[4]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[5]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[6]/article/div/div/div/table")).getText()+"\n";
					temp+="----------------------------------------------------------------------------------------------------------\n";
					PB.setValue(PB.getValue()+20);
					Detail.setText(Detail.getText()+"�]�Ȥ�v�U�������I\n");
					Thread.sleep(1500);
					
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[5]/a")).click();
					temp+=driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[5]/a")).getText()+"\n";
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[1]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/div/table")).getText()+"\n";
					Thread.sleep(1500);
					driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).click();
					temp+="\n"+driver.findElement(By.xpath("//*[@id=\"PageListTab\"]/a[2]")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[2]/article/div/div/div/table")).getText()+"\n";
					temp+=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li[3]/article/div/div/div/table")).getText()+"\n";
					PB.setValue(PB.getValue()+20);
					Detail.setText(Detail.getText()+"�����O��ƤU�������I\n");
					Detail.setText(Detail.getText()+"�Ҧ���ƬҥH�U�������I�I���}�Ҧs�ɦ�}�Y�i�ݨ��ơC");
					
					
					
					String type[]= {".txt",".csv"};
					int position=CompanyComboBox.getSelectedItem().toString().indexOf("-")+1;
					String CompanyName = CompanyComboBox.getSelectedItem().toString().substring(position)+"-";
					for(int i =0;i<2;i++) {
						FileWriter fw = new FileWriter(SavePath.getText()+File.separator+CompanyName+DynamicName[0][0][2]+type[i]);
						if(i==0)temp=temp.replaceAll("\n", "\r\n");
						if(i==1) {
							temp=temp.replaceAll("----------------------------------------------------------------------------------------------------------", "");
							temp=temp.replace(",", "");
							temp=temp.replace(" ", ",");
						}
						fw.write(temp);
						fw.flush();
						fw.close();	
					}
					
					
/*
					//�򥻸��
					if(!driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div/div")).getText().equals("�d�L���")) {
						driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[2]/a")).click();
						temp=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div/div/div/table")).getText();
						Thread.sleep(1500);	
					}
					
					//�ѧQ�F��
					if(!driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div/div")).getText().equals("�d�L���")) {
						
					}
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[3]/a")).click();
					Thread.sleep(1500);
					//�禬�l�B
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[5]/a")).click();
					Thread.sleep(1500);
					//����
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[6]/a")).click();
					Thread.sleep(1500);
					//���q��
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[7]/a")).click();
					Thread.sleep(1500);
					//�b�Ȥ�
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[8]/a")).click();
					Thread.sleep(1500);
					
					//�w�XK�u
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[1]/a")).click();
					Thread.sleep(1500);
					//�ӽ�����
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[3]/a")).click();
					Thread.sleep(1500);
					//�T�j�k�H
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[4]/a")).click();
					Thread.sleep(1500);
					//�ĸ�Ĩ�
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[5]/a")).click();
					Thread.sleep(1500);
					//���O����
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[6]/a")).click();
					Thread.sleep(1500);
					//�D�O���
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[7]/a")).click();
					Thread.sleep(1500);
					
					//�겣�t�Ū�
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[1]/a")).click();
					Thread.sleep(1500);
					//�l�q��
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[2]/a")).click();
					Thread.sleep(1500);
					//�{���y�q��
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[3]/a")).click();
					Thread.sleep(1500);
					//�]�Ȥ�v
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[4]/a")).click();
					Thread.sleep(1500);
					//�����O���
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[5]/a")).click();
					Thread.sleep(1500);
					//����
					*/
					driver.quit();
					execution.setEnabled(true);
					
					//�g��
					/*
					for(int i = 0;i<FileName.length;i++) {
						//�ˬd�O�_�����ɮ� �Y�L���إߦA�}�ҵ������ε{��
						File FileCheck = new File(LocalPath+FileName[i]);
						if(!FileCheck.exists()) {
							FileWriter fw = new FileWriter(LocalPath+FileName[i]);
							if(i==0)fw.write("�t�m�W��:�п��\r\n" + 
									"��ܤ��q�W��:�п��\r\n" + 
									"��ܺ����W��:�п��\r\n" + 
									"�s�ɸ��|:"+LocalPath+"\r\n" + 
									"�b��:\r\n" + 
									"�K�X:\r\n" + 
									"�Ŀﶵ��:\r\n" + 
									"-------------------------------------------------");
							else if(i==1)fw.write("�п��\r\n");
							fw.flush();
							fw.close();
						}
					}
					*/
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				
				
				
				//�Ȯɦ��ǰ��D�n�� �Q�I�s�観��s�ƭ� ������s�e��
				/*
				System.out.println(Download.length);
				if(WebComboBox.getSelectedIndex()!=0)new CrawlerSelenium(WebComboBox.getSelectedIndex());
				else JOptionPane.showMessageDialog(null,"�п�ܭn�j�M������!�I","���~",JOptionPane.WARNING_MESSAGE);
				*/
				//�����]�w
				//options.addArguments("headless");
				
				
				
				
				
				//�H�U���槹���Ϋ�~�|��

				//�Ĥ@���q
				
				
				

				
				
								  
				  
					
					
					
					
					
					/*
					PB.setValue(PB.getValue()+PBAverage);
				   A = driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody")).getText();
				   driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[2]/a")).click();
				   System.out.println("PB����"+PB.getValue());
				   System.out.println(A+"\n");
				   //Detail.setText(Detail.getText()+"\n"+A+"\n");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PB.setValue(PB.getValue()+PBAverage);
				   A = driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody")).getText();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   System.out.println(A+"\n");
				   
				   driver.findElement(By.xpath(" //*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[3]/a")).click();
				   System.out.println("\n\n");
				   PB.setValue(PB.getValue()+PBAverage);
				   List<?>  col = driver.findElements(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody/tr/th"));
			        System.out.println("Total No of columns are : " +col.size());
			        List<WebElement>  rows = driver.findElements(By.xpath ("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody/tr"));
			        for(WebElement i :rows) {
			        	System.out.println("Total No of rows are : " + rows.size());	
			        }
			        A="\""+A+"\"";
			        A=A.replaceAll(" ","\",\"");
			        System.out.println(A.replaceAll("\r\n", "\"\r\n")+"\n");
			        System.out.println(A);
			        Detail.setText(Detail.getText()+"\n"+A+"\n");
				  driver.quit(); 
				  PB.setValue(PB.getValue()+PBAverage);
				  JOptionPane.showMessageDialog(null,"��Ƥw�g�^�������I","�t�ΰT��",JOptionPane.INFORMATION_MESSAGE);
				  Detail.setText(Detail.getText()+"\n��Ƥw�g�^�������I");
				  PB.setValue(100);
				  execution.setEnabled(true);
				  */
			
		}	
	}
	public void CheckFile() {
		try {
			String temp[]=new String[2];
			LocalPath=Road.getCanonicalPath()+File.separator;
			Boolean check=false,check2=false;
			String WrongFile="";
			String setting="";
			for(int i = 0;i<FileName.length;i++) {
				//�ˬd�O�_�����ɮ� �Y�L���إߦA�}�ҵ������ε{��
				File FileCheck = new File(LocalPath+FileName[i]);
				if(!FileCheck.exists()) {
					FileWriter fw = new FileWriter(LocalPath+FileName[i]);
					if(i==0)fw.write("�t�m�W��:�п��\r\n" + 
							"��ܤ��q�W��:�п��\r\n" + 
							"��ܺ����W��:�п��\r\n" + 
							"�s�ɸ��|:"+LocalPath+"\r\n" + 
							"�b��:\r\n" + 
							"�K�X:\r\n" + 
							"�Ŀﶵ��:\r\n" + 
							"-------------------------------------------------");
					else if(i==1)fw.write("�п��\r\n");
					fw.flush();
					fw.close();
					WrongFile+=FileName[i]+"\n";
					check=true;
				}
				FileReader fr = new FileReader(LocalPath+FileName[i]);
				temp[i]="";
				int j;
				while((j=fr.read())!=-1)temp[i]+=(char)j;
				fr.close();
			}
			//���t�m�z���r
			for(int a=0;a!=-1;){
				a=temp[0].indexOf("�t�m�W��:",a);
				if(a==-1)break;
				int b=temp[0].indexOf("\r\n",a);
				a=a+5;
				setting+=temp[0].substring(a,b)+",";
				check2=true;
			}
			//���J���q�B�t�m�ﶵ
			if(check)JOptionPane.showMessageDialog(null,"�ѩ�䤣��H�U�ɮסA�N�Ѩt�Φۦ�ͦ��C\n"+WrongFile,"����",JOptionPane.INFORMATION_MESSAGE);
			if(check2) {
				setting=setting.substring(0,setting.length()-1);
				SelectComboBox.setModel(new DefaultComboBoxModel(setting.split(",|\n|\r\n")));
			}
			else SelectComboBox.setModel(new DefaultComboBoxModel(temp[0].split("-|\n|\r\n")));
			
			CompanyComboBox.setModel(new DefaultComboBoxModel(temp[1].split("\r\n")));
			
			CompanyComboBox.setSelectedIndex(0);
			WebComboBox.setSelectedIndex(0);
			}
			catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	public void CompanyFilter() {
		try {
			//selenium
			System.setProperty("webdriver.chrome.driver",LocalPath+"chromedriver.exe");
			String url="https://www.cmoney.tw/finance/f00025.aspx?s=1101";
			ChromeOptions options = new ChromeOptions();
			options.addArguments("window-size=1400,800");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(url);
			String A="";
			int temp=9;
			for(int i =0;i<=temp;i++) {				
				driver.findElement(By.xpath("//*[@id=\"qStockId\"]")).clear();
				driver.findElement(By.xpath("//*[@id=\"qStockId\"]")).sendKeys(String.valueOf(i));
				A += driver.findElement(By.xpath("//*[@id=\"ui-id-1\"]")).getText();
				Thread.sleep(2500);
				PB.setValue(PB.getValue()+(int)Math.ceil(100/temp));
			}
			
			driver.quit();
			//�g��
			FileWriter fw;
			fw = new FileWriter(LocalPath+FileName[1]);
			A="�п��\r\n"+A.replaceAll("\n", "\r\n");
			fw.write(A);
			fw.flush();
			fw.close();
			CompanyComboBox.setModel(new DefaultComboBoxModel(A.split("\r\n")));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
