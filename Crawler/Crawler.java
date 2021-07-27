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
	public String LocalPath,FileName[]= {"配置.txt","公司.txt"};
	//上方物件-按鈕,輸入框,下拉式選單-----------------------------------------------
	private JButton addSCB = new JButton("新增");
	private JButton removeSCB = new JButton("移除");
	private JButton SearchPath = new JButton("瀏覽路徑");
	private JCheckBox AllInOne = new JCheckBox("整合成一個檔案");
	private JTextField SavePath = new JTextField();
	private JTextField AC = new JTextField();
	private JPasswordField PW = new JPasswordField();
	private boolean overwrite=false;
	public JComboBox SelectComboBox = new JComboBox();
	public JComboBox CompanyComboBox = new JComboBox();
	public JComboBox WebComboBox = new JComboBox(new DefaultComboBoxModel(new String[] {"請選擇","CMoney","財報狗","anue鉅亨網","公開資訊觀測站"}));
	//下載內容-勾選項目,上下頁按鈕,Panel--------------------------
	private String DownloadAll="全選";
	//三維陣列解釋
	//[A][B][C]
	//A代表用於PANEL還是CHECKBOX
	//B代表PANEL在第幾頁和CHECKBOX在第幾個PANEL
	//C代表資料
	private String DynamicName[][][]=new String[2][][];
	private JPanel Middle = new JPanel(new GridLayout(2,6,20,0));
	private int AllPageCount,NowPageCount=1;
	private JPanel DynamicPanel[][];
	private JCheckBox DynamicCheckBox[][];
	private Boolean check=false;
	private JComboBox Page = new JComboBox();
	private JButton Up = new JButton("上一頁");
	private JButton Down = new JButton("下一頁");
	public boolean Download[][];
	
	//底部物件-文字框,按鈕,進度條----------------------------------------
	public JTextArea Detail = new JTextArea();
	private JButton OpenFile = new JButton("開啟存檔位址");
	private JButton DF = new JButton("返回原廠設定");//default
	private JButton SelectAll = new JButton("勾選項目全選");
	private JButton clear = new JButton("清空勾選項目");
	private JButton save = new JButton("儲存現有配置");
	private JButton execution = new JButton("執行");
	private JButton close = new JButton("關閉");
	public JProgressBar PB = new JProgressBar();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Crawler frame = new Crawler();
					frame.setVisible(true);
					frame.setTitle("網路爬蟲");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Crawler() {
		//339以前全都是版面設定
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
		
		//上方物件-下拉式選單
		//上方物件-純文字
		JLabel select = new JLabel("配置選擇");
		JLabel company = new JLabel("選擇公司");
		JLabel web = new JLabel("選擇網站");
		JLabel path = new JLabel("存檔路徑");
		JLabel account = new JLabel("帳號(可輸入代表需要用到)");
		JLabel password = new JLabel("密碼");
		JLabel savetype = new JLabel("存檔格式");
		//上方物件-設定格式
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
		Tool.setBorder(BorderFactory.createTitledBorder("基本設定"));
		AC.setEditable(false);
		PW.setEditable(false);
		
		//上方加入
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
		//可下載內容-物件
		
		//可下載內容-設定
		Page.setPreferredSize(new Dimension(80, 28));
		Up.setEnabled(false);
		Down.setEnabled(false);
		MiddleBoard.setBorder(BorderFactory.createTitledBorder("下載項目"));
		
		//可下載內容-加入
		
		//底部物件
		JPanel BottomBorder = new JPanel(new BorderLayout());
		JPanel Bottom = new JPanel(new GridLayout(1,1,0,0));
		
		//底部物件-純文字
		JScrollPane DetailJs = new JScrollPane(Detail,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		String vs = "1.0";
		JLabel version = new JLabel("版本號："+vs);
		//底部物件-設定格式
		BottomBorder.setPreferredSize(new Dimension(0,230));
		Detail.setFont(new Font("微軟正黑體", Font.PLAIN, 15));
		Detail.setLineWrap(true);
		Detail.setWrapStyleWord(true);
		Detail.setEditable(false);
		DetailJs.setPreferredSize(new Dimension(0,150));
		PB.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		PB.setForeground(new Color(51, 204, 102));
		PB.setStringPainted(true);
		//Detail.setBorder(BorderFactory.createTitledBorder("執行狀態"));
		DetailJs.setBorder(BorderFactory.createTitledBorder("執行狀態"));
		DetailJs.setBackground(Color.WHITE);
		//底部物件-加入
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
		//面板放入
		getContentPane().add(TopAndMiddle,BorderLayout.CENTER);
		getContentPane().add(BottomBorder,BorderLayout.SOUTH);
		//初始設定
		
		//增加監聽器 上方
		addSCB.addActionListener(new Listener());
		removeSCB.addActionListener(new Listener());
		SearchPath.addActionListener(new Listener());
		SelectComboBox.getEditor().getEditorComponent().addKeyListener(new Listener());
		SelectComboBox.addItemListener(new Listener());
		CompanyComboBox.addItemListener(new Listener());
		WebComboBox.addItemListener(new Listener());
		//下載內容
		Up.addActionListener(new Listener());
		Page.addActionListener(new Listener());
		Page.addItemListener(new Listener());
		Down.addActionListener(new Listener());
		//底部
		AC.addKeyListener(new Listener());
		OpenFile.addActionListener(new Listener());
		DF.addActionListener(new Listener());
		SelectAll.addActionListener(new Listener());
		clear.addActionListener(new Listener());
		save.addActionListener(new Listener());
		execution.addActionListener(new Listener());
		close.addActionListener(new Listener());
		
		//檢查檔案是否存在
		CheckFile();
	}
	public class Listener implements ActionListener, KeyListener,ItemListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//新增配置
				if (e.getActionCommand().equals(addSCB.getActionCommand())) {
					Boolean OK=false;
				//由存檔發出訊息 自動從配置輸入框新增配置 
				if(overwrite) {
					overwrite=false;
					return;
				}
				if (SelectComboBox.getEditor().getItem().equals("")) {
					JOptionPane.showMessageDialog(null,"名稱不能為空白，請重新命名。","錯誤",JOptionPane.WARNING_MESSAGE);
					OK=false;
					return;
					}
				for(int i =0;i<SelectComboBox.getItemCount();i++) {
					if(SelectComboBox.getEditor().getItem().equals(SelectComboBox.getItemAt(i))) {
					JOptionPane.showMessageDialog(null,"配置名稱已重複，請重新命名。","錯誤",JOptionPane.WARNING_MESSAGE);
					OK=false;
					break;
					}
					else OK=true;
				}
				if(OK||SelectComboBox.getItemCount()==0)SelectComboBox.addItem(SelectComboBox.getEditor().getItem());
			}
			//移除配置
			else if (e.getActionCommand().equals(removeSCB.getActionCommand())) {
				int count=0;
				for(int i =0;i<SelectComboBox.getItemCount();i++) {
					if(!SelectComboBox.getEditor().getItem().equals(SelectComboBox.getItemAt(i)))count++;
				}
				if(SelectComboBox.getItemCount()==0) {
					JOptionPane.showMessageDialog(null,"已經沒有東西可以刪除囉！","提示",JOptionPane.INFORMATION_MESSAGE);
					//return;
				}
				else if (SelectComboBox.getEditor().getItem().equals("")&&SelectComboBox.getItemCount()>0) {
					JOptionPane.showMessageDialog(null,"請選擇要刪除的項目！","錯誤",JOptionPane.WARNING_MESSAGE);
					//return;
				}
				else if(count==SelectComboBox.getItemCount()) {
					JOptionPane.showMessageDialog(null,"沒有指定的配置選項能夠刪除！","錯誤",JOptionPane.WARNING_MESSAGE);
					return;
				}
				else if(JOptionPane.showConfirmDialog(null, "確認是否要移除?","安全機制",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					SelectComboBox.removeItem(SelectComboBox.getSelectedItem());
					SelectComboBox.getEditor().setItem("");
				}
			}
			//瀏覽存檔路徑
			else if (e.getActionCommand().equals(SearchPath.getActionCommand())){
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = chooser.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						if(SavePath.getText()!=chooser.getSelectedFile().getAbsolutePath())SAVE=false;
						SavePath.setText(chooser.getSelectedFile().getAbsolutePath());
					}
				}
			//上一頁
			else if (e.getActionCommand().equals(Up.getActionCommand())) {
				NowPageCount--;
				Page.setSelectedIndex(Page.getSelectedIndex()-1);
			}
			//下一頁
			else if (e.getActionCommand().equals(Down.getActionCommand())) {
				NowPageCount++;
				Page.setSelectedIndex(Page.getSelectedIndex()+1);
			}
			//開啟存檔位置
			else if (e.getActionCommand().equals(OpenFile.getActionCommand())) {
				
					try {
						File local = new File(SavePath.getText());
						if(local.isDirectory()) {
							if(Desktop.isDesktopSupported())Desktop.getDesktop().open(local);	
						}
						else {
							JOptionPane.showMessageDialog(null,"請選擇有效的存檔路徑！\n由上方的瀏覽路徑或是輸入框做更改","錯誤",JOptionPane.WARNING_MESSAGE);
							return;
						}
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,"請選擇正確的路徑！\n由上方的瀏覽路徑或是輸入框做更改","錯誤",JOptionPane.WARNING_MESSAGE);
						return;
					}
			}
			//返回原廠設定
			else if(e.getActionCommand().equals(DF.getActionCommand())) {
				if(JOptionPane.showConfirmDialog(null, "這將是不可逆的結果，確認是否要返回原廠設定?","安全機制",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					//介面更新
					SelectComboBox.removeAllItems();
					CompanyComboBox.removeAllItems();
					WebComboBox.setSelectedIndex(0);
					SavePath.setText("");
					AC.setText("");
					PW.setText("");
					Detail.setText("");
					PB.setValue(0);
					//移除並新增檔案
					for(int i = 0;i<FileName.length;i++) {
						File FileCheck = new File(LocalPath+FileName[i]);
						FileCheck.delete();
					}
					CheckFile();
					JOptionPane.showMessageDialog(null,"系統已返回原廠設定!\n公司資料請選擇相對應的網站，並直接執行便會得到所有可查詢公司。","提示",JOptionPane.INFORMATION_MESSAGE);
					SAVE=true;
				}
			}
			//勾選項目全選
			else if(e.getActionCommand().equals(SelectAll.getActionCommand())) {
				if(WebComboBox.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null,"請選擇任何一個網站(不包含\"請選擇\")，否則無法使用。","錯誤",JOptionPane.WARNING_MESSAGE);
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
			//清空勾選項目
			else if(e.getActionCommand().equals(clear.getActionCommand())) {
				if(WebComboBox.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null,"請選擇任何一個網站(不包含\"請選擇\")，否則無法使用。","錯誤",JOptionPane.WARNING_MESSAGE);
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
			//儲存現有配置	
			else if(e.getActionCommand().equals(save.getActionCommand())) {
					try {
						//要存取的資料
						if(SelectComboBox.getSelectedItem().equals("請選擇")) {
							JOptionPane.showMessageDialog(null,"\"請選擇\"並不是一個有效的配置項目，請換一個儲存。","提示",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						overwrite=true;
						addSCB.doClick();
						LocalPath=Road.getCanonicalPath()+File.separator;
						String SelectSave="配置名稱:"+SelectComboBox.getSelectedItem().toString();
						String CompanySave="選擇公司名稱:"+CompanyComboBox.getSelectedItem().toString();
						String WebSave="選擇網站名稱:"+WebComboBox.getSelectedItem().toString();
						String CheckBoxSave="勾選項目:\r\n";
						String SP = "存檔路徑:"+SavePath.getText();
						String account="帳號:";//+AC.getText();
						String password="密碼:";//+PW.hashCode();
						for(int i = 0;i<DynamicCheckBox.length;i++) {
							for(int j = 0;j<DynamicCheckBox[i].length;j++) {
								CheckBoxSave+=DynamicCheckBox[i][j].isSelected();
								if(j!=DynamicCheckBox[i].length-1)CheckBoxSave+=",";
							}
							if(i!=DynamicCheckBox.length-1)CheckBoxSave+="\r\n";
						}
						String FinalOutput=SelectSave+"\r\n"+CompanySave+"\r\n"+WebSave+"\r\n"+SP+"\r\n"+account+"\r\n"+password+"\r\n"+CheckBoxSave;
						//讀檔
						FileReader fr = new FileReader(LocalPath+FileName[0]);
						String temp="";
						int j;
						while((j=fr.read())!=-1)temp+=(char)j;
						fr.close();
						//寫檔
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
						JOptionPane.showMessageDialog(null,"配置項目\""+SelectComboBox.getSelectedItem()+"\"已經儲存完畢!","提示",JOptionPane.INFORMATION_MESSAGE);
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
			//執行
			else if(e.getActionCommand().equals(execution.getActionCommand())) {
				int count=0;
				String wrongtxt="";
				boolean CHOSE=false;
				File FileCheck = new File(SavePath.getText());
				if(CompanyComboBox.getSelectedIndex()==0) {
					count++;
					wrongtxt+="\n選擇公司";
				}
				if(WebComboBox.getSelectedIndex()==0) {
					count++;
					wrongtxt+="\n選擇網站";
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
					wrongtxt+="\n使用有效的存檔路徑";
				}
				
						
				if(!CHOSE)wrongtxt+="\n至少勾選一個下載項目";
				
				if(count==0 && CHOSE||CompanyComboBox.getItemCount()==1) {
					if(WebComboBox.getSelectedIndex()==0) {
						JOptionPane.showMessageDialog(null,"若要獲取公司資料，請記得選取相對應網站!","錯誤",JOptionPane.WARNING_MESSAGE);
					}
					else {
						Detail.setText("資料讀取中，請稍後。\n");
						PB.setValue(0);
						execution.setEnabled(false);
						Thread CPU2=new Counting();
						CPU2.start();	
					}	
				}
				else {
					JOptionPane.showMessageDialog(null,"請選取以下提及的項目才能夠執行爬蟲!"+wrongtxt,"錯誤",JOptionPane.WARNING_MESSAGE);
				}
			}
			
			//關閉
			else if (e.getActionCommand().equals(close.getActionCommand())) {
				if(!execution.isEnabled())driver.quit();
				System.exit(0);
			}
			//設定上下頁是否可按
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
						//讀檔
						String temp="";
						for(int i=0;i<FileName.length;i++) {
							FileReader fr = new FileReader(LocalPath+FileName[i]);
							int j;
							while((j=fr.read())!=-1)temp+=(char)j;
							fr.close();
						}
						//提取相對應名稱的資料區塊
						int star;
						int end;
						star=temp.indexOf(SelectComboBox.getSelectedItem().toString());
						if(star==-1)return;
						end=temp.indexOf("-------------------------------------------------",star)-1;
						String location="";
						location=temp.substring(star,end);
						
						//讀取配置內容
						String scout[]= {"選擇公司名稱:","選擇網站名稱:","存檔路徑:","帳號:","密碼:"};
						for(int i=0;i<scout.length;i++) {
							star=location.indexOf(scout[i])+scout[i].length();
							end=location.indexOf("\r\n",star);
							if(i==0)CompanyComboBox.setSelectedItem(location.substring(star,end));
							else if(i==1)WebComboBox.setSelectedItem(location.substring(star,end));
							else if(i==2)SavePath.setText(location.substring(star,end));
							else if(i==3)AC.setText(location.substring(star,end));
							else if(i==4)PW.setText(location.substring(star,end));
							
						}
						//如果網站是選"請選擇"就直接不做了
						if(WebComboBox.getSelectedIndex()==0)return;
						//分配勾選項目的true/false給字串
						star=location.indexOf("勾選項目:");
						location=location.replace("勾選項目:\r\n", "");
						String tempA[]=location.substring(star).split("\r\n");
						String DownloadCheck[][]=new String[tempA.length][];
						for(int i =0;i<tempA.length;i++) {
							DownloadCheck[i]=new String[tempA[i].split(",").length];
							for(int j =0;j<tempA[i].length();j++) {
								DownloadCheck[i]=tempA[i].split(",");
							}
						}
						//透過字串的true/false去幫下載項目做勾選
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
			//選擇網站
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
					//自動將每頁可下載項目限制成6個區塊
					String CMData[]={"基本分析","籌碼分析","財務分析"};
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
					String CMoneyCheckBox[][]={{"全選","基本資料","股利政策","營收餘額","轉投資","本益比","淨值比"},
											{"全選","籌碼K線","申請轉讓","三大法人","融資融券","集保分布","主力券商"},
											{"全選","資產負債表","損益表","現金流量表","財務比率","部門別資料"}};
					DynamicName[0]=new String[CMoneyPanel.length][];
					DynamicName[1]=new String[CMoneyCheckBox.length][];
					//Panel名稱
					for(int i =0;i<CMoneyPanel.length;i++) {
						DynamicName[0][i]=new String[CMoneyPanel[i].length];
						for(int j=0;j<CMoneyPanel[i].length;j++) {
							DynamicName[0][i][j]=CMoneyPanel[i][j];
						}
					}
					//CheckBox名稱
					for(int i =0;i<CMoneyCheckBox.length;i++) {
						DynamicName[1][i]=new String[CMoneyCheckBox[i].length];
						for(int j=0;j<CMoneyCheckBox[i].length;j++) {
							DynamicName[1][i][j]=CMoneyCheckBox[i][j];
						}
					}
					break;
				case 2:
					String DogData[]={"最新動態(新聞、洞見文章、公告)","股票健診","財務報表","獲利能力","安全性分析","成長力分析","價值評估","董監與籌碼"};
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
					String DogCheckBox[][]={{"全選","2020年","2019年","2018年","2017年","2016年","2015年","2014年","2013年"},
											{"全選","排除地雷股健診","定存股健診","成長股健診","便宜股健診"},
											{"全選","每月營收","每股盈餘","每股淨值","損益表","總資產","負債和股東權益","現金流量表","股利政策","電子書"},
											{"全選","利潤比率","營業費用拆解率","業外佔稅前淨利比例","ROE/ROA","杜邦分析","經營周轉能力","營運週轉天數","營業現金流對淨利比","現金股利發放率"},
											{"全選","財務結構比率","流速動比率","利息保障倍數","現金流量分析","營業現金流對淨利比","盈餘再投資比率"},
											{"全選","月營收成長率","營收成長率","毛利成長率","營業利益成長率","稅後淨利成長率","每股盈餘成長率"},
											{"全選","本益比評價","本益比河流圖","股價淨值比評價","股價淨值比河流圖","現金股利殖利率","平均現金股利殖利率","平均現金股利河流圖"},
											{"全選","分點籌碼動向","董監持股比例","董監持股質押比例","董監酬金觀察"}};
					DynamicName[0]=new String[DogPanel.length][];
					DynamicName[1]=new String[DogCheckBox.length][];
					//Panel名稱
					for(int i =0;i<DogPanel.length;i++) {
						DynamicName[0][i]=new String[DogPanel[i].length];
						for(int j=0;j<DogPanel[i].length;j++) {
							DynamicName[0][i][j]=DogPanel[i][j];
						}
					}
					//CheckBox名稱
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
					String AnueCheckBox[][]={{"全選","基本資料","股利政策","營收餘額","轉投資","本益比","淨值比"},
											{"全選","籌碼K線","申請轉讓","三大法人","融資融券","集保分布","主力券商"},
											{"全選","資產負債表","損益表","現金流量表","財務比率","部門別資料"}};
					DynamicName[0]=new String[AnuePanel.length][];
					DynamicName[1]=new String[AnueCheckBox.length][];
					//Panel名稱
					for(int i =0;i<AnuePanel.length;i++) {
						DynamicName[0][i]=new String[AnuePanel[i].length];
						for(int j=0;j<AnuePanel[i].length;j++) {
							DynamicName[0][i][j]=AnuePanel[i][j];
						}
					}
					//CheckBox名稱
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
					String OpenDataCheckBox[][]={{"全選","基本資料","股利政策","營收餘額","轉投資","本益比","淨值比"},
											{"全選","籌碼K線","申請轉讓","三大法人","融資融券","集保分布","主力券商"},
											{"全選","資產負債表","損益表","現金流量表","財務比率","部門別資料"}};
					DynamicName[0]=new String[OpenDataPanel.length][];
					DynamicName[1]=new String[OpenDataCheckBox.length][];
					//Panel名稱
					for(int i =0;i<OpenDataPanel.length;i++) {
						DynamicName[0][i]=new String[OpenDataPanel[i].length];
						for(int j=0;j<OpenDataPanel[i].length;j++) {
							DynamicName[0][i][j]=OpenDataPanel[i][j];
						}
					}
					//CheckBox名稱
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
			for(int i =1;i<=AllPageCount;i++)Page.addItem("第"+i+"頁");
			check=true;
			SAVE=false;
			}
			//換頁
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
				//count計算有幾個被選取
				int count[]=new int[DynamicCheckBox.length];
				for(int i=0;i<DynamicCheckBox.length;i++) {
					count[i]=0;
					for(int j=0;j<DynamicCheckBox[i].length;j++) {
						if(DynamicCheckBox[i][j].isSelected())count[i]++;
					}
				}
				//全選與自動勾選符合條件
				for(int i=0;i<DynamicCheckBox.length;i++) {
					for(int j=0;j<DynamicCheckBox[i].length;j++) {
						//紀錄哪些要下載
						Download[i][j]=DynamicCheckBox[i][j].isSelected();
						//全選與取消全選動作 以及替其他項目做勾選取消動作
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
						//自動檢測所有項目是否被選取 若有就選取或取消全選
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
				//下載並過濾重複公司資料
				if(CompanyComboBox.getItemCount()==1) {
					PB.setValue(0);
					Detail.setText(Detail.getText()+"公司配置截取中。\n");
					CompanyFilter();
					Detail.setText(Detail.getText()+"公司配置截取完成！");
					execution.setEnabled(true);
				}
				//下載指定資料
				else {
					System.setProperty("webdriver.chrome.driver",LocalPath+"chromedriver.exe");
					String chose=CompanyComboBox.getSelectedItem().toString();
					chose=chose.substring(0,chose.indexOf("-"));
					String url="";
					//網站分流、網站選擇分別為CMoney、財報狗、anue鉅亨網、公開財務觀測站
					switch(WebComboBox.getSelectedIndex()) {
					case 1:
						url="https://www.cmoney.tw/finance/f00040.aspx?s="+chose;
						break;
					case 2:
						JOptionPane.showMessageDialog(null,"這個部份尚在測試中，暫不開放使用，還請見諒。","暫不開放",JOptionPane.WARNING_MESSAGE);
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
						if(i==1)temp+="資產負債表\n";
						else if(i==2)temp+="損益表\n";
						else if(i==3)temp+="現金流量表\n";
						else if(i==4)temp+="財務比率\n";
						else if(i==5)temp+="部門別資料\n";
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
					
						
					
					
					//資產負債表
					
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
					Detail.setText(Detail.getText()+"資產負債表下載完成！\n");
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
					Detail.setText(Detail.getText()+"損益表下載完成！\n");
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
					Detail.setText(Detail.getText()+"現金流量表下載完成！\n");
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
					Detail.setText(Detail.getText()+"財務比率下載完成！\n");
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
					Detail.setText(Detail.getText()+"部門別資料下載完成！\n");
					Detail.setText(Detail.getText()+"所有資料皆以下載完成！點擊開啟存檔位址即可看到資料。");
					
					
					
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
					//基本資料
					if(!driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div/div")).getText().equals("查無資料")) {
						driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[2]/a")).click();
						temp=driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div/div/div/table")).getText();
						Thread.sleep(1500);	
					}
					
					//股利政策
					if(!driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div/div")).getText().equals("查無資料")) {
						
					}
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[3]/a")).click();
					Thread.sleep(1500);
					//營收餘額
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[5]/a")).click();
					Thread.sleep(1500);
					//轉投資
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[6]/a")).click();
					Thread.sleep(1500);
					//本益比
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[7]/a")).click();
					Thread.sleep(1500);
					//淨值比
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[3]/ul/li[8]/a")).click();
					Thread.sleep(1500);
					
					//籌碼K線
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[1]/a")).click();
					Thread.sleep(1500);
					//申請轉讓
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[3]/a")).click();
					Thread.sleep(1500);
					//三大法人
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[4]/a")).click();
					Thread.sleep(1500);
					//融資融券
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[5]/a")).click();
					Thread.sleep(1500);
					//集保分布
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[6]/a")).click();
					Thread.sleep(1500);
					//主力券商
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[4]/ul/li[7]/a")).click();
					Thread.sleep(1500);
					
					//資產負債表
					driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[1]/a")).click();
					Thread.sleep(1500);
					//損益表
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[2]/a")).click();
					Thread.sleep(1500);
					//現金流量表
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[3]/a")).click();
					Thread.sleep(1500);
					//財務比率
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[4]/a")).click();
					Thread.sleep(1500);
					//部門別資料
					driver.findElement(By.xpath("//*[@id=\\\"CMoneyBrowser\\\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[5]/a")).click();
					Thread.sleep(1500);
					//結束
					*/
					driver.quit();
					execution.setEnabled(true);
					
					//寫檔
					/*
					for(int i = 0;i<FileName.length;i++) {
						//檢查是否有該檔案 若無先建立再開啟視窗應用程式
						File FileCheck = new File(LocalPath+FileName[i]);
						if(!FileCheck.exists()) {
							FileWriter fw = new FileWriter(LocalPath+FileName[i]);
							if(i==0)fw.write("配置名稱:請選擇\r\n" + 
									"選擇公司名稱:請選擇\r\n" + 
									"選擇網站名稱:請選擇\r\n" + 
									"存檔路徑:"+LocalPath+"\r\n" + 
									"帳號:\r\n" + 
									"密碼:\r\n" + 
									"勾選項目:\r\n" + 
									"-------------------------------------------------");
							else if(i==1)fw.write("請選擇\r\n");
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
			
			
				
				
				
				//暫時有些問題要修 被呼叫方有更新數值 但不更新畫面
				/*
				System.out.println(Download.length);
				if(WebComboBox.getSelectedIndex()!=0)new CrawlerSelenium(WebComboBox.getSelectedIndex());
				else JOptionPane.showMessageDialog(null,"請選擇要搜尋的網站!！","錯誤",JOptionPane.WARNING_MESSAGE);
				*/
				//部分設定
				//options.addArguments("headless");
				
				
				
				
				
				//以下執行完爬蟲後才會做

				//第一階段
				
				
				

				
				
								  
				  
					
					
					
					
					
					/*
					PB.setValue(PB.getValue()+PBAverage);
				   A = driver.findElement(By.xpath("//*[@id=\"MainContent\"]/ul/li/article/div[2]/div/table/tbody")).getText();
				   driver.findElement(By.xpath("//*[@id=\"CMoneyBrowser\"]/body/div[1]/div[2]/div[4]/nav/ul/li[5]/ul/li[2]/a")).click();
				   System.out.println("PB的值"+PB.getValue());
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
				  JOptionPane.showMessageDialog(null,"資料已經擷取完成！","系統訊息",JOptionPane.INFORMATION_MESSAGE);
				  Detail.setText(Detail.getText()+"\n資料已經擷取完成！");
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
				//檢查是否有該檔案 若無先建立再開啟視窗應用程式
				File FileCheck = new File(LocalPath+FileName[i]);
				if(!FileCheck.exists()) {
					FileWriter fw = new FileWriter(LocalPath+FileName[i]);
					if(i==0)fw.write("配置名稱:請選擇\r\n" + 
							"選擇公司名稱:請選擇\r\n" + 
							"選擇網站名稱:請選擇\r\n" + 
							"存檔路徑:"+LocalPath+"\r\n" + 
							"帳號:\r\n" + 
							"密碼:\r\n" + 
							"勾選項目:\r\n" + 
							"-------------------------------------------------");
					else if(i==1)fw.write("請選擇\r\n");
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
			//替配置篩選文字
			for(int a=0;a!=-1;){
				a=temp[0].indexOf("配置名稱:",a);
				if(a==-1)break;
				int b=temp[0].indexOf("\r\n",a);
				a=a+5;
				setting+=temp[0].substring(a,b)+",";
				check2=true;
			}
			//載入公司、配置選項
			if(check)JOptionPane.showMessageDialog(null,"由於找不到以下檔案，將由系統自行生成。\n"+WrongFile,"提示",JOptionPane.INFORMATION_MESSAGE);
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
			//寫檔
			FileWriter fw;
			fw = new FileWriter(LocalPath+FileName[1]);
			A="請選擇\r\n"+A.replaceAll("\n", "\r\n");
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
