import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;





 class  Pit implements Cloneable
 {
	 String name;
	 int stones;
	@Override
	
	public String toString() {
		return name+":"+stones+" ";
	}
	
	
	
	public Pit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Pit(String name, int stones) {
		super();
		this.name = name;
		this.stones = stones;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Pit p=(Pit) super.clone();
		p.name=name;
		p.stones=stones;
		return p;
		
	}
 }
class board implements Cloneable
{
	int OtherMancala;
	int MyMancala;
	Pit Pits[];
	int size;
	int playerPits;
	int myChance;
	
	public board(board b)
	{
		this.OtherMancala=b.OtherMancala;
		this.MyMancala=b.MyMancala;
		this.Pits=b.Pits.clone();
		this.size=b.size;
		this.playerPits=b.playerPits;
		this.myChance=b.myChance;
	}
	
	public boolean ifAllMyPitsEmpty()
	{
		if(myChance==1)
		{
			for(int i=2;i<=(playerPits+1);i++)
			{
				if(Pits[i].stones!=0)
					return false;
			}
			return true;
		}
		else
		if(myChance==2)
		{
			for(int i=(playerPits+3);i<=size;i++)
			{
				if(Pits[i].stones!=0)
					return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean ifAllHisPitsEmpty()
	{
		if(myChance==1)
		{
			for(int i=(playerPits+3);i<=size;i++)
			{
				if(Pits[i].stones!=0)
					return false;
			}
			return true;
		}
		else
		if(myChance==2)
		{
			for(int i=2;i<=(playerPits+1);i++)
			{ 
				if(Pits[i].stones!=0)
					return false;
			}
		}
		return true;
	}
	public boolean IsEndGame()
	{
		boolean flag=false;
		if(myChance==1)
		{
			if(ifAllMyPitsEmpty())
			{
				flag=true;
				for(int i=(playerPits+3);i<=size;i++)
				{
					if(Pits[i].stones!=0)
					{
						Pits[OtherMancala].stones+=Pits[i].stones;
						Pits[i].stones=0;
					}
				}
			}
			else
			if(ifAllHisPitsEmpty())
			{
				flag=true;
				for(int i=2;i<=(playerPits+1);i++)
				{
					if(Pits[i].stones!=0)
					{
						Pits[MyMancala].stones+=Pits[i].stones;
						Pits[i].stones=0;
					}
				}

			}
			return flag;

		}
		else
		if(myChance==2)
		{
			if(ifAllMyPitsEmpty())
			{
				flag=true;
				for(int i=2;i<=playerPits+1;i++)
				{
					if(Pits[i].stones!=0)
					{
						Pits[OtherMancala].stones+=Pits[i].stones;
						Pits[i].stones=0;
					}
				}
			}
			else
			if(ifAllHisPitsEmpty())
			{
				flag=true;
				for(int i=playerPits+3;i<=size;i++)
				{
					if(Pits[i].stones!=0)
					{
						Pits[MyMancala].stones+=Pits[i].stones;
						Pits[i].stones=0;
					}
				}
			}
			return flag;
		}
		return flag;
	}
	
	
	public board( Pit[] Pits, int size,
			int playerPits,int player) {
		super();
		myChance=player;
		if(player==1)
		{
			MyMancala = (playerPits+2);
			OtherMancala = 1;
		}
		else
		if(player==2)
		{
			OtherMancala = (playerPits+2);
			MyMancala = 1;
		}
		this.Pits = Pits;
		this.size = size;
		this.playerPits = playerPits;
	}
	
/*	public void setMyChance(int myChance) {
		this.myChance = myChance;
		
		
		int temp=MyMancala;
			MyMancala=OtherMancala ; 
			OtherMancala = temp;
		}
		
	*/
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		board b1 = null;
	    b1 = (board) super.clone();
	    b1.Pits=new Pit[size+1];
	    for(int i=1;i<=size;i++)
	    {
	    	b1.Pits[i]=(Pit) Pits[i].clone();
	    }
	    this.Pits.clone();
	    return b1;
	}
	
	public boolean isMyMancala(int num)
	{
		if(MyMancala==num)
			return true;
		return false;
	}
	
	public boolean isOtherMancala(int num)
	{
		if(OtherMancala==num)
			return true;
		return false;
	}
	
	
	@Override
	public String toString() {
		return Arrays.toString(Pits);
	}

	public int evalfunction()
	{
			return Pits[MyMancala].stones-Pits[OtherMancala].stones;
	}
	
	
	public int myStartPit()
	{
		if(myChance==1)
			return 2; 
		else
			return (OtherMancala+1);
	}
	public boolean isMyPit(int pitNumber)
	{
		if(myChance==1)
		{
			if(pitNumber>=2 && pitNumber<=playerPits+1)
				return true;
		}
		else
		if(myChance==2)
		{
			if(pitNumber>=playerPits+3 && pitNumber<=size)
				return true;
		}	
		return false;
	}
	
	public boolean isEmpty(int pitNumber)
	{
		if(Pits[pitNumber].stones==1)
		{
			return true;
		}
		return false;
	}
	
	public void moveStonesToMyMancala(int pitNum)
	{
		String myPit=Pits[pitNum].name;
		String s=new String();
		if(myPit.charAt(0)=='A')
		{
			s=myPit.replace('A', 'B');
		}
		else
			s=myPit.replace('B', 'A');
	//	System.out.println("Match:"+s);
		for(Pit p: Pits)
		{
		
			if(p!=null)
			{
			//	System.out.println("Outside if:"+p.name);
				if(p.name.equals(s))
				{
				//	System.out.println("Inside if:"+p.name);
					Pits[MyMancala].stones+=Pits[pitNum].stones;
					Pits[MyMancala].stones+=p.stones;
					p.stones=0;
					Pits[pitNum].stones=0;
					IsEndGame();
					return;
				}
			}
		}	
	}
	public boolean removeStones(int pit)
	{
		
		int stones=Pits[pit].stones;
		int pitNum=pit;
		Pits[pit].stones=0;
		while(stones!=0)
		{
			if((pitNum+1)>size)
			{
				pitNum=0;
			}
			if(isOtherMancala(pitNum+1))
				++pitNum;
			Pits[++pitNum].stones+=1;
			stones--;
		}
		
		if(isMyMancala(pitNum))
		{
			IsEndGame();
			return true;
		}
		else
			if(IsEndGame())
			{
				return false;
			}
			else
		if(isMyPit(pitNum) && isEmpty(pitNum))
		{
		
			moveStonesToMyMancala(pitNum);
			if(IsEndGame())
			{
				return false;
			}
			return false;
		}
		if(IsEndGame())
		{
			return false;
		}
		return false;
	}
	public int otherStartPit() {
		// TODO Auto-generated method stub
		if(myChance==1)
			return (MyMancala+1); 
		else
		
			return 2;
		
	}
	public boolean removeOtherStones(int pit) {
		// TODO Auto-generated method stub
		int stones=Pits[pit].stones;
		int pitNum=pit;
		Pits[pit].stones=0;
		while(stones!=0)
		{
			//System.out.println(pitNum);
			if((pitNum+1)>size)
			{
				pitNum=0;
			}
			
			if(isMyMancala(pitNum+1))
				++pitNum;
			Pits[++pitNum].stones+=1;
			stones--;
		}
		
		if(isOtherMancala(pitNum))
		{
			IsEndGame();
			return true;
		}
			else
			if(IsEndGame())
			{
				return false;
			}
			else
		if(!isMyPit(pitNum) && !isMyMancala(pitNum) && isEmpty(pitNum))
		{
			moveStonesToOtherMancala(pitNum);
			if(IsEndGame())
			{
				return false;
			}
			return false;
		}
		if(IsEndGame())
		{
			return false;
		}
		return false;
	}
	
	private void moveStonesToOtherMancala(int pitNum) {
		String myPit=Pits[pitNum].name;
		String s=new String();
		if(myPit.charAt(0)=='A')
		{
			s=myPit.replace('A', 'B');
		}
		else
			s=myPit.replace('B', 'A');
		//System.out.println("Match:"+s);
		for(Pit p: Pits)
		{
		
			if(p!=null)
			{
		//		System.out.println("Outside if:"+p.name);
				if(p.name.equals(s))
				{
			//		System.out.println("Inside if:"+p.name);
					Pits[OtherMancala].stones+=Pits[pitNum].stones;
					Pits[OtherMancala].stones+=p.stones;
					p.stones=0;
					Pits[pitNum].stones=0;
				}
			}
		}
		
	}
}


class State implements Comparable<State>
{
	String name;
	int val;
	int alpha;
	int beta;
	board b;
	State parent;
	String type;
	int depth;
	
	@Override
	public int compareTo(State o) {
		// TODO Auto-generated method stub
		if(this.val==o.val)
		{
			return name.compareTo(o.name);
		}
		else
			return o.val-val;
	}

	@Override
	public String toString() {
		return "State [name=" + name + ", val=" + val + ", "+" b=" + b + ", "
				+ ", type=" + type + ", depth=" + depth + "]";
	}
	
	public String print()
	{
		if(val==Integer.MAX_VALUE)
		{
			return "Infinity";
		}
		if(val==Integer.MIN_VALUE)
			return "-Infinity";
		return val+"";
	}
}



public class mancala {
	
	BufferedWriter traverse,states;
	static int cutOffDepth;
	static int myChance;
	public static void main(String args[])
	{
		BufferedReader in=null;
		BufferedWriter out=null;
		try 
		{
			
			in=new BufferedReader(new FileReader(args[1]));
	
		
			Pit pits[];
			int data=Integer.parseInt(in.readLine());
			myChance=Integer.parseInt(in.readLine());
			cutOffDepth=Integer.parseInt(in.readLine());
		//	System.out.println(cutOffDepth);
			String P2state=in.readLine();
			String stones[]=P2state.split(" ");
			int noOfPits=stones.length*2+2+1;
			pits=new Pit[noOfPits];
			int playerPits=stones.length;
			
			int i,j;
			j=noOfPits-1;
			int k=2;
			for(i=0;i<stones.length;j--,i++)
			{
				pits[j]=new Pit();
				pits[j].stones=Integer.parseInt(stones[i]);
				pits[j].name="A"+k;
				k++;
			}
			String P1state=in.readLine();
			stones=P1state.split(" ");
			
			for(i=0,j=2;i<stones.length;j++,i++)
			{
				pits[j]=new Pit();
				pits[j].stones=Integer.parseInt(stones[i]);
				pits[j].name="B"+j;
			}
			pits[1]=new Pit();
			pits[1].stones=Integer.parseInt(in.readLine());
			pits[1].name="A1";
			pits[playerPits+2]=new Pit();
			pits[playerPits+2].stones=Integer.parseInt(in.readLine());
			pits[playerPits+2].name="B"+(playerPits+2);
			
			board Board=new board(pits, noOfPits-1, playerPits, myChance);
	
			//System.out.println(Board);
			mancala m=new mancala();
			m.states=new BufferedWriter(new FileWriter("states.txt"));
			if(data==1)
			{
				m.greedy(Board);
			}
			else
			if(data==2)
			{
				State s=new State();
				s.b=Board;		
				s.parent=null;
				s.val=Integer.MIN_VALUE;
				s.name="root";
				s.depth=0;
				s.type="MAX";
				s.parent=null;
				m.traverse=new BufferedWriter(new FileWriter("traverse_log.txt"));
				try {
					//	System.out.println("MIN: "+s.name+","+s.depth+","+s.val+"\r\n");
						m.traverse.write("Node,Depth,Value\r\n");
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				m.generateMinimaxTree(s);
			}
			else
			if(data==3)
			{
				State s=new State();
				s.b=Board;		
				s.parent=null;
				s.val=Integer.MIN_VALUE;
				s.name="root";
				s.depth=0;
				s.type="MAX";
				s.parent=null;
				s.alpha=Integer.MIN_VALUE;
				s.beta=Integer.MAX_VALUE;
				m.traverse=new BufferedWriter(new FileWriter("traverse_log.txt"));
				try {
					//	System.out.println("MIN: "+s.name+","+s.depth+","+s.val+"\r\n");
					
						m.traverse.write("Node,Depth,Value,Alpha,Beta\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				m.generateAlphaBetaTree(s);
		
			//	m.AlphaBetaPruning(s);
				
				
				
				//	alphabeta(Board);
			}
		}
		catch(IOException e)
		{
			System.out.println("Exception: "+e);
		}
	}

	
	
	public void greedy(board B)
	{
		
		State s=generateChildren(B);
		
		printNextMove(s.b);
	}
	
		
	public boolean testGoalState(State state)
	{
		if(state.b.ifAllHisPitsEmpty() && state.b.ifAllMyPitsEmpty())
			return true;
		return false;
	}
	public State generateChildren(board b)
	{
		
		int i,j;
		State s = null;
		List<State> children=new ArrayList<State>();
		//i=b.myStartPit();
		
		for(i=b.myStartPit();i<=b.OtherMancala+b.playerPits;i++)
		{
			
			if(b.Pits[i].stones==0)
				continue;
			s=new State();
			board child= null;
			try 
			{
					child = (board)b.clone();
				//	System.out.println(child);
					if(child.removeStones(i))
					{			
						s=generateChildren(child);
						
					}
					else
					{	
						int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
						s.val=eval;	
					}
					children.add(s);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Collections.sort(children);
		return children.get(0);
		//return s;
	}
	
	List<State> Tree;
	public void generateMinimaxTree(State s)
	{
		Tree=new ArrayList<State>();
		int v=generateMaxValue(s);
	//	System.out.println(v);
		
		State s1=finalMove(s, v);
		printNextMove(s1.b);
	
	}
	
	public int generateMaxValue(State s1)
	{
		List<State> children=new ArrayList<State>();
		board b=s1.b;
		State s;
		int v=Integer.MIN_VALUE;
		if(myChance==1)
		{
			if(testGoalState(s1))
			{			
				if(s1.depth!=cutOffDepth || (s1.depth==cutOffDepth && s1.type.equals(s1.parent.type)))
				{
					try {
						traverse.write(s1.name+","+s1.depth+","+print(v)+"\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				s1.val=s1.b.evalfunction();
				try {
					traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(s1.depth==1)
					Tree.add(s1);
				return s1.val;
			}

			
			s1.val=v;
			try 
			{
				//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
				traverse.write(s1.name+","+s1.depth+","+s1.print()+"\r\n");
			} 
			catch (IOException e) 
			{
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//	System.out.println("Start:::"+b.myStartPit()+" End:::"+(b.OtherMancala+b.playerPits));
			for(int i=b.myStartPit(); i<=b.OtherMancala+b.playerPits ;i++)
			{
				
				if(b.Pits[i].stones==0)
				{
					continue;
				}
				s=new State();
				board child= null;
				try 
				{
						child = (board)b.clone();		
						boolean flag=child.removeStones(i);
			//			System.out.println("Child:"+s.name+child);
			//			System.out.println("child:"+b.Pits[i]);
				//		int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
				//		s.val=eval;
		//				System.out.println("Child:"+s.name+child);
						s.parent=s1;
						if(s1.type.equals("MAX"))
							s.depth=s1.depth+1;
						else
							s.depth=s1.depth;
						s.type="MIN";	
					//	printState(s);
					//	Tree.add(s);
						if(flag)
						{	
							v=Math.max(v,generateMaxValue(s));
							s1.val=v;	
							try 
							{
								//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
								traverse.write(s1.name+","+s1.depth+","+s1.print()+"\r\n");
							} 
							catch (IOException e) 
							{
							// TODO Auto-generated catch block
								e.printStackTrace();
							}		
						}
						else
						{
							if(s.depth==cutOffDepth)
							{
									s.val=s.b.evalfunction();
									try {
										traverse.write(s.name+","+s.depth+","+print(s.val)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									v=Math.max(v, s.val);
									s1.val=v;

									if(s.depth==1)
										Tree.add(s);
									try {
										traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
							}
							else
							{
								v=Math.max(v,generateMinValue(s));
								s1.val=v;
								try 
								{
								//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
									traverse.write(s1.name+","+s1.depth+","+s1.print()+"\r\n");
								} 
								catch (IOException e) 
								{
							// TODO Auto-generated catch block
								e.printStackTrace();
								}
							}
						}	
				} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s1.depth==1)
				Tree.add(s1);
			return v;

		}
		else
		{
			if(testGoalState(s1))
			{
				
				
				if(s1.depth!=cutOffDepth || (s1.depth==cutOffDepth && s1.type.equals(s1.parent.type)))
				{
					try {
						traverse.write(s1.name+","+s1.depth+","+print(v)+"\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				s1.val=s1.b.evalfunction();
				try {
					traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(s1.depth==1)
					Tree.add(s1);
				return s1.val;
			}

			
			s1.val=v;
			try 
			{
				//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
				traverse.write(s1.name+","+s1.depth+","+s1.print()+"\r\n");
			} 
			catch (IOException e) 
			{
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//	System.out.println("Start:::"+b.myStartPit()+" End:::"+(b.OtherMancala+b.playerPits));
			for(int i=(s1.b.size); i>s1.b.OtherMancala;i--)
			{
				
				if(b.Pits[i].stones==0)
				{
					continue;
				}
				s=new State();
				board child= null;
				try 
				{
						child = (board)b.clone();
						
						boolean flag=child.removeStones(i);
			//			System.out.println("Child:"+s.name+child);
			//			System.out.println("child:"+b.Pits[i]);
				//		int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
				//		s.val=eval;
			//			System.out.println("Child:"+s.name+child);
						s.parent=s1;
						if(s1.type.equals("MAX"))
							s.depth=s1.depth+1;
						else
							s.depth=s1.depth;
						s.type="MIN";	
					//	printState(s);
					//	Tree.add(s);
						if(flag)
						{	
							v=Math.max(v,generateMaxValue(s));
							s1.val=v;	
							try 
							{
								//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
								traverse.write(s1.name+","+s1.depth+","+s1.print()+"\r\n");
							} 
							catch (IOException e) 
							{
							// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						}
						else
						{
							if(s.depth==cutOffDepth)
							{
									s.val=s.b.evalfunction();
									try {
										traverse.write(s.name+","+s.depth+","+print(s.val)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									v=Math.max(v, s.val);
									s1.val=v;
									if(s.depth==1)
										Tree.add(s);
									
									try {
										traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
							}
							else
							{
								v=Math.max(v,generateMinValue(s));
								s1.val=v;
								try 
								{
								//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
									traverse.write(s1.name+","+s1.depth+","+s1.print()+"\r\n");
								} 
								catch (IOException e) 
								{
							// TODO Auto-generated catch block
								e.printStackTrace();
								}
							}
						}	
				} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s1.depth==1)
				Tree.add(s1);
			return v;

		}
		
	}
	
	
	public void printState(State s)
	{
		try {
			//	System.out.println("MIN: "+s.name+","+s.depth+","+s.val+"\r\n");
			if(s.parent==null)
			{
				states.write(s.name+","+s.b+","+s.depth+","+s.val+"\r\n");
			}
			else
				states.write(s.name+","+s.b+","+s.depth+","+s.val+",parent:"+s.parent.name+","+s.parent.depth+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}

	public int generateMinValue(State s1)
	{
		List<State> children=new ArrayList<State>();
		board b=s1.b;
		State s;
		int v=Integer.MAX_VALUE;
		if(myChance==1)
		{
			if(testGoalState(s1))
			{
				
				
				if(s1.depth!=cutOffDepth || (s1.depth==cutOffDepth && s1.type.equals(s1.parent.type)))
				{
					try {
						traverse.write(s1.name+","+s1.depth+","+print(v)+"\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				s1.val=s1.b.evalfunction();
				try {
					traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(s1.depth==1)
					Tree.add(s1);
				return s1.val;
			}

		
			//
			s1.val=v;
			try {
				traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println("Start:::"+b.myStartPit()+" End:::"+(b.OtherMancala+b.playerPits));
			for(int i=b.size;i>b.MyMancala ;i--)
			{
				if(b.Pits[i].stones==0)
				{
					continue;
				}
				s=new State();
				board child= null;
				try 
				{
						child = (board)b.clone();
						boolean flag=child.removeOtherStones(i);
						
//						int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
			//			s.val=eval;
						
				//		System.out.println("Child:"+s.name+child);
				//		System.out.println("child:"+b.Pits[i]);
						s.parent=s1;
						if(s1.type.equals("MIN"))
							s.depth=s1.depth+1;
						else
							s.depth=s1.depth;
						s.type="MAX";
						printState(s);
			
						if(flag)
						{	
							v=Math.min(v,generateMinValue(s));
							s1.val=v;
							try {
								traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
						{
							if(s.depth==cutOffDepth)
							{
								s.val=s.b.evalfunction();
								try {
									traverse.write(s.name+","+s.depth+","+print(s.val)+"\r\n");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								v=Math.min(v, s.val);	
								
								s1.val=v;
								if(s.depth==1)
									Tree.add(s);
									try {
										traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
															
							}
							else
							{	
								v=Math.min(v,generateMaxValue(s));
								s1.val=v;
								try {
									traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
								}
								catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								}
							}
						}		
				} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s1.depth==1)
				Tree.add(s1);
			return v;
	
		}
		else
		{
			if(testGoalState(s1))
			{
				if(s1.depth!=cutOffDepth || (s1.depth==cutOffDepth && s1.type.equals(s1.parent.type)))
				{
					try {
						traverse.write(s1.name+","+s1.depth+","+print(v)+"\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				
				
				
				s1.val=s1.b.evalfunction();
				try {
					traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(s1.depth==1)
					Tree.add(s1);
				return s1.val;
			}

		
		//	int v=Integer.MAX_VALUE;
			s1.val=v;
			try {
				traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println("Start:::"+b.myStartPit()+" End:::"+(b.OtherMancala+b.playerPits));
			for(int i=b.otherStartPit();i<=b.MyMancala+b.playerPits ;i++)
			{
				if(b.Pits[i].stones==0)
				{
					continue;
				}
				s=new State();
				board child= null;
				try 
				{
						child = (board)b.clone();
						boolean flag=child.removeOtherStones(i);
						
//						int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
			//			s.val=eval;
						
				//		System.out.println("Child:"+s.name+child);
				//		System.out.println("child:"+b.Pits[i]);
						s.parent=s1;
						if(s1.type.equals("MIN"))
							s.depth=s1.depth+1;
						else
							s.depth=s1.depth;
						s.type="MAX";
						printState(s);
			
						if(flag)
						{	
							
							v=Math.min(v,generateMinValue(s));
							s1.val=v;
							try {
								traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
						{
							if(s.depth==cutOffDepth)
							{
								s.val=s.b.evalfunction();
								try {
									traverse.write(s.name+","+s.depth+","+print(s.val)+"\r\n");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								v=Math.min(v, s.val);	
								 
								s1.val=v;
								if(s.depth==1)
									Tree.add(s);
									try {
										traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}								
							}
							else
							{	
								v=Math.min(v,generateMaxValue(s));
								s1.val=v;
								try {
									traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
								}
								catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								}
							}
						}		
				} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s1.depth==1)
				Tree.add(s1);
			return v;
	
		}
	}
	
	
	
	
	
	public void printNextMove(board b)
	{
		BufferedWriter out=null;
		try 
		{
			out=new BufferedWriter(new FileWriter("next_state.txt"));
			Pit[] Pits=b.Pits;
			for(int i=b.size;i>b.size-b.playerPits;i--)
				out.write(Pits[i].stones+" ");
			out.write("\r\n");
			for(int i=2;i<=b.playerPits+1;i++)
				out.write(Pits[i].stones+" ");
			out.write("\r\n"+Pits[1].stones);
			int x=b.playerPits+2;
			
			out.write("\r\n"+Pits[x].stones+"\r\n");
		}
		catch(IOException e)
		{
			
		}
		finally
		{
		try{
				if(out!=null)
				{
					out.close();
				}
				if(traverse!=null)
				{
					traverse.close();
				}
			}
			catch(IOException e)
			{
		 		e.printStackTrace();
			}
		}
	}
	
	
	
	
	public State finalMove(State s,int value)
	{
		State f=null;
		List<State> successors=Successors(s);
		if(successors!=null)
		{
			if(successors.get(0).type.equals("MIN"))
			{
				for(State state: successors)
				{
					if(state.val==value)
					{
			
						f=finalMove(state, value);
						return f;
					}
				}
				
			}
		}
		else
		if((s.val)==value)
		{
		//	System.out.println("Inside this");
			return s;
		}
		return s;
	}

	public State finalMoveAB(State s,int value)
	{
		State f=null;
		List<State> successors=Successors(s);
		if(successors!=null)
		{
			if(successors.get(0).type.equals("MIN"))
			{
				for(State state: successors)
				{
					if(state.val==value)
					{
						f=finalMove(state, value);
						return f;
					}
				}
			}
		}
		else
		if((s.val)==value)
		{
	//		System.out.println("Inside this");
			return s;
		}
		return s;
	}
	
	
	class CompareAlpabetically implements Comparator<State>
	{
		@Override
		public int compare(State o1, State o2) {
			// TODO Auto-generated method stub
			return (o1.name).compareTo(o2.name);
		}
	}
	
	public boolean cutOffTest(int depth)
	{
		if(depth==cutOffDepth)
		{
			return true;
		}
		return false;
	}

	public List<State> Successors(State s)
	{
		List<State> children=new ArrayList<State>();
		boolean flag=false;
		//System.out.println("Children of "+s.name+","+s.depth);
		for(State child:Tree)
		{
			if(child.parent!=null && child.parent==s)
			{
	//			System.out.println(child.name+","+child.depth);
				children.add(child);
				flag=true;
			}
		}
		if(flag==true)
		{
			Collections.sort(children, new CompareAlpabetically());
			return children;
		}
		return null;
	}

	
	public void generateAlphaBetaTree(State s)
	{
		Tree=new ArrayList<State>();
		int value=generateMaxValueAB(s,Integer.MIN_VALUE,Integer.MAX_VALUE);
		//System.out.println(value);
		State s1=finalMove(s,value);
		//System.out.println(s1);
		printNextMove(s1.b);
	}
	
	public int generateMaxValueAB(State s1,int alpha,int beta)
	{
		List<State> children=new ArrayList<State>();
		board b=s1.b;
		State s;
		int v=Integer.MIN_VALUE;
		if(myChance==1)
		{
			if(testGoalState(s1))
			{
				if(s1.depth!=cutOffDepth ||   (s1.depth==cutOffDepth && s1.type.equals(s1.parent.type)))
				{
					try {
						traverse.write(s1.name+","+s1.depth+","+print(v)+","+print(alpha)+","+print(beta)+"\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				s1.val=s1.b.evalfunction();
				try {
					traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(s1.depth==1)
					Tree.add(s1);
				return s1.val;
			}

			
			s1.val=v;
			try 
			{
				//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
				traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
			} 
			catch (IOException e) 
			{
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//	System.out.println("Start:::"+b.myStartPit()+" End:::"+(b.OtherMancala+b.playerPits));
			for(int i=b.myStartPit(); i<=b.OtherMancala+b.playerPits ;i++)
			{
				
				if(b.Pits[i].stones==0)
				{
					continue;
				}
				s=new State();
				board child= null;
				try 
				{
						child = (board)b.clone();		
						boolean flag=child.removeStones(i);
			//			System.out.println("Child:"+s.name+child);
			//			System.out.println("child:"+b.Pits[i]);
				//		int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
				//		s.val=eval;
			//			System.out.println("Child:"+s.name+child);
						s.parent=s1;
						if(s1.type.equals("MAX"))
							s.depth=s1.depth+1;
						else
							s.depth=s1.depth;
						s.type="MIN";	
					//	printState(s);
					//	Tree.add(s);
						if(flag)
						{	
							v=Math.max(v,generateMaxValueAB(s,alpha,beta));
							s1.val=v;	
						/*	try 
							{
								//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
								traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
							} 
							catch (IOException e) 
							{
							// TODO Auto-generated catch block
								e.printStackTrace();
							}*/		
						}
						else
						{
							if(s.depth==cutOffDepth)
							{
									s.val=s.b.evalfunction();
									try {
										traverse.write(s.name+","+s.depth+","+print(s.val)+","+print(alpha)+","+print(beta)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									v=Math.max(v, s.val);
									s1.val=v;
									if(s.depth==1)
										Tree.add(s);
									/*try {
										traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}*/
									
							}
							else
							{
								v=Math.max(v,generateMinValueAB(s,alpha,beta));
								s1.val=v;
							/*	try 
								{
								//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
									traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
								} 
								catch (IOException e) 
								{
							// TODO Auto-generated catch block
								e.printStackTrace();
								}*/
							}
						}
						
						if(v>=beta)
						{
							
							try 
							{
							//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
								traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
							} 
							catch (IOException e) 
							{
						// TODO Auto-generated catch block
							e.printStackTrace();
							}
	
							
							return v;
						}
						alpha=Math.max(alpha,v);
						
						
						try 
						{
						//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
							traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
						} 
						catch (IOException e) 
						{
					// TODO Auto-generated catch block
						e.printStackTrace();
						}
				} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s1.depth==1)
				Tree.add(s1);
			return v;

		}
		else
		{
			if(testGoalState(s1))
			{
				if(s1.depth!=cutOffDepth || (s1.depth==cutOffDepth && s1.type.equals(s1.parent.type)))
				{
					try {
						traverse.write(s1.name+","+s1.depth+","+print(v)+","+print(alpha)+","+print(beta)+"\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				s1.val=s1.b.evalfunction();
				try {
					traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(s1.depth==1)
					Tree.add(s1);
				return s1.val;
			}

			
			s1.val=v;
			try 
			{
				//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
				traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
			} 
			catch (IOException e) 
			{
		
				e.printStackTrace();
			}
			
			//	System.out.println("Start:::"+b.myStartPit()+" End:::"+(b.OtherMancala+b.playerPits));
			for(int i=(s1.b.size); i>s1.b.OtherMancala;i--)
			{
				
				if(b.Pits[i].stones==0)
				{
					continue;
				}
				s=new State();
				board child= null;
				try 
				{
						child = (board)b.clone();
						
						boolean flag=child.removeStones(i);
			//			System.out.println("Child:"+s.name+child);
			//			System.out.println("child:"+b.Pits[i]);
				//		int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
				//		s.val=eval;
				//		System.out.println("Child:"+s.name+child);
						s.parent=s1;
						if(s1.type.equals("MAX"))
							s.depth=s1.depth+1;
						else
							s.depth=s1.depth;
						s.type="MIN";	
					//	printState(s);
					//	Tree.add(s);
						if(flag)
						{	
							v=Math.max(v,generateMaxValueAB(s,alpha,beta));
							s1.val=v;	
						/*	try 
							{
								//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
								traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
							} 
							catch (IOException e) 
							{
							// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
						
						}
						else
						{
							if(s.depth==cutOffDepth)
							{
									s.val=s.b.evalfunction();
									try {
										traverse.write(s.name+","+s.depth+","+print(s.val)+","+print(alpha)+","+print(beta)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									v=Math.max(v, s.val);
									s1.val=v;
									if(s.depth==1)
										Tree.add(s);
									
								/*	try {
										traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}*/
									
							}
							else
							{
								v=Math.max(v,generateMinValueAB(s,alpha,beta));
								s1.val=v;
								/*try 
								{
								//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
									traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
								} 
								catch (IOException e) 
								{
							// TODO Auto-generated catch block
								e.printStackTrace();
								}*/
							}
							
							
						}	
						if(v>=beta)
						{
							
							try 
							{
							//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
								traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
							} 
							catch (IOException e) 
							{
						// TODO Auto-generated catch block
							e.printStackTrace();
							}
							
							return v;
						}
						alpha=Math.max(alpha,v);
						
						try 
						{
						//System.out.println("MAX: "+s.name+","+s.depth+","+s.print()+"\r\n");
							traverse.write(s1.name+","+s1.depth+","+s1.print()+","+print(alpha)+","+print(beta)+"\r\n");
						} 
						catch (IOException e) 
						{
					// TODO Auto-generated catch block
						e.printStackTrace();
						}
				} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s1.depth==1)
				Tree.add(s1);
			return v;

		}
		
	}
	
	
	
	public int generateMinValueAB(State s1,int alpha,int beta)
	{
		List<State> children=new ArrayList<State>();
		board b=s1.b;
		State s;
		int v=Integer.MAX_VALUE;
		if(myChance==1)
		{
			if(testGoalState(s1))
			{
				if(s1.depth!=cutOffDepth || (s1.depth==cutOffDepth && s1.type.equals(s1.parent.type)))
				{
					try {
						traverse.write(s1.name+","+s1.depth+","+print(v)+","+print(alpha)+","+print(beta)+"\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
			
				s1.val=s1.b.evalfunction();
				try {
					traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(s1.depth==1)
					Tree.add(s1);
				return s1.val;
			}

		
			
			s1.val=v;
			try {
				traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println("Start:::"+b.myStartPit()+" End:::"+(b.OtherMancala+b.playerPits));
			for(int i=b.size;i>b.MyMancala ;i--)
			{
				if(b.Pits[i].stones==0)
				{
					continue;
				}
				s=new State();
				board child= null;
				try 
				{
						child = (board)b.clone();
						boolean flag=child.removeOtherStones(i);
						
//						int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
			//			s.val=eval;
						
				//		System.out.println("Child:"+s.name+child);
				//		System.out.println("child:"+b.Pits[i]);
						s.parent=s1;
						if(s1.type.equals("MIN"))
							s.depth=s1.depth+1;
						else
							s.depth=s1.depth;
						s.type="MAX";
						printState(s);
			
						if(flag)
						{	
							v=Math.min(v,generateMinValueAB(s,alpha,beta));
							s1.val=v;
							/*try {
								traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
						}
						else
						{
							if(s.depth==cutOffDepth)
							{
								s.val=s.b.evalfunction();
								try {
									traverse.write(s.name+","+s.depth+","+print(s.val)+","+print(alpha)+","+print(beta)+"\r\n");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								v=Math.min(v, s.val);	
								
								s1.val=v;
								if(s.depth==1)
									Tree.add(s);
									/*try {
										traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}*/
									
															
							}
							else
							{	
								v=Math.min(v,generateMaxValueAB(s,alpha,beta));
								s1.val=v;
							/*	try {
									traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
								}
								catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								}*/
							}	
						}	
						
						if(v<=alpha)
						{
							try {
								traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
							}
							catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							}
							return v;
						}
						
						beta=Math.min(beta, v);
						try {
							traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
						}
						catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						
				} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s1.depth==1)
				Tree.add(s1);
			return v;
	
		}
		else
		{
			if(testGoalState(s1))
			{
				if(s1.depth!=cutOffDepth || (s1.depth==cutOffDepth && s1.type.equals(s1.parent.type)))
				{
					try {
						traverse.write(s1.name+","+s1.depth+","+print(v)+","+print(alpha)+","+print(beta)+"\r\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
			
		
						
				
				s1.val=s1.b.evalfunction();
				try {
					traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(s1.depth==1)
					Tree.add(s1);
				return s1.val;
			}

		
			
			s1.val=v;
			try {
				traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println("Start:::"+b.myStartPit()+" End:::"+(b.OtherMancala+b.playerPits));
			for(int i=b.otherStartPit();i<=b.MyMancala+b.playerPits ;i++)
			{
				if(b.Pits[i].stones==0)
				{
					continue;
				}
				s=new State();
				board child= null;
				try 
				{
						child = (board)b.clone();
						boolean flag=child.removeOtherStones(i);
						
//						int eval=child.evalfunction();
						s.b=child;
						s.name=b.Pits[i].name;
			//			s.val=eval;
						
				//		System.out.println("Child:"+s.name+child);
				//		System.out.println("child:"+b.Pits[i]);
						s.parent=s1;
						if(s1.type.equals("MIN"))
							s.depth=s1.depth+1;
						else
							s.depth=s1.depth;
						s.type="MAX";
						printState(s);
			
						if(flag)
						{	
							
							v=Math.min(v,generateMinValueAB(s,alpha,beta));
							s1.val=v;
							/*try {
								traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
						}
						else
						{
							if(s.depth==cutOffDepth)
							{
								s.val=s.b.evalfunction();
								try {
									traverse.write(s.name+","+s.depth+","+print(s.val)+","+print(alpha)+","+print(beta)+"\r\n");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								v=Math.min(v, s.val);	
								 
								s1.val=v;
								if(s.depth==1)
									Tree.add(s);
									/*try {
										traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}	*/							
							}
							else
							{	
								v=Math.min(v,generateMaxValueAB(s,alpha,beta));
								s1.val=v;
								/*try {
									traverse.write(s1.name+","+s1.depth+","+print(s1.val)+"\r\n");
								}
								catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								}*/
							}
						}	
						
						if(v<=alpha)
						{
							try {
								traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
							}
							catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							}
							return v;
						}
						
						beta=Math.min(beta, v);
						try {
							traverse.write(s1.name+","+s1.depth+","+print(s1.val)+","+print(alpha)+","+print(beta)+"\r\n");
						}
						catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						
						
				} 
				catch (CloneNotSupportedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s1.depth==1)
				Tree.add(s1);
			return v;
	
		}
	}
	

	
	
	public String print(int val)
	{
		if(val==Integer.MAX_VALUE)
		{
			return "Infinity";
		}
		if(val==Integer.MIN_VALUE)
			return "-Infinity";
		return val+"";	
	}
}




