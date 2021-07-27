import java.util.*;

public class ex20190304 {
	public static void RDN (int number[]) {
		Random RN=new Random();
		for(int i=0;i<number.length;i++) {
			number[i]=RN.nextInt(100);
		}
	}
	public static void insert (int number[],int Ins[],int count[]) {
		count[0]=0;
		count[1]=0;
		for(int i=0;i<number.length;i++) {
			for(int j=0;j<=i;j++) {
				count[0]++;
				if(Ins[j]>=number[i]) {
					for(int k=i-1,p=0;i-j>p;k--,p++) {
						count[1]++;
						Ins[k+1]=Ins[k];
					}
					Ins[j]=number[i];
					break;
				}
				if(i==j) {
					Ins[j]=number[i];
				}
			}
		}
	}
	public static void BOB (int number[],int count[]) {
		int temp=0;
		count[0]=0;
		count[1]=0;
		for(int i=0;i<number.length;i++) {
			for(int j=0;j<number.length-i-1;j++) {
				count[0]++;
				if(number[j]>number[j+1]) {
					count[1]++;
					temp=number[j+1];
					number[j+1]=number[j];
					number[j]=temp;
				}
			}
		}
	}
	public static void Chose (int number[],int Cnumber[],int count[]) {
		count[0]=0;
		count[1]=0;
		for(int i=0,QQ=0,L=0;i<number.length;i++,QQ=0,L=0) {
			for(int j=0;j<number.length;j++) {
				if(i!=j)count[0]++;
				if(i!=j&number[i]>=number[j]) {
					QQ++;
					if(number[i]==number[j])L++;
				}
				if(j==number.length-1) {
					if(number[i]==Cnumber[QQ]) {
						int k=1;
						while(L>0) {
							Cnumber[QQ-k]=number[i];
							L--;
							k++;
						}
					}
					else Cnumber[QQ]=number[i];
				}
			}
		}
	}
	
	public static void output(int number[],int count[],String out,String Acount,String Bcount) {
		String output="";
		for(int i=0;i<number.length;i++)output+=number[i]+" ";
		if(count[0]!=0&&count[1]!=0) System.out.println(out+"\n"+output+"\n"+Acount+" "+count[0]+Bcount+" "+count[1]);
		else if (count[0]!=0&count[1]==0)System.out.println(out+"\n"+output+"\n"+Acount+" "+count[0]);
		else System.out.println(out+"\n"+output);	

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner X = new Scanner(System.in);
		//int a=X.nextInt();
		int a=20;
		int []number=new int[a];
		int []Bnumber=new int[a];
		int []Cnumber=new int[a];
		int []count=new int[2];
		RDN(number);
		output(number,count, "原始資料為", "", "");

		insert(number,Bnumber,count);
		output(Bnumber,count,"插入排序後為","比較次數為","位移次數為");		
		
		Chose(number,Cnumber,count);
		output(Cnumber,count,"選擇排序後為","比較次數為","");		

		BOB(number,count);
		output(number,count,"氣泡排序後為","比較次數為","交換次數為");
		X.close();
	}

}