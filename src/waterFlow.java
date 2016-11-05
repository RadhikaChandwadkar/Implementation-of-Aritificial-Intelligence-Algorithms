import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class pipe
{
	int length;
	int[][] offtime;
	int offPeriods;
	String start;
	String end;
	boolean flag;
	
}

class Info
{
	int dist;
	int marked;
	String prev;
	public Info(int marked, int dist, String prev) {
		super();
		this.dist = dist;
		this.marked = marked;
		this.prev = prev;
	}
	@Override
	public String toString() {
		return "Info [dist=" + dist + ", marked=" + marked + ", prev=" + prev
				+ "]";
	}
}
public class waterFlow {

	int INFINITY=Integer.MAX_VALUE;
	pipe[] pipeTree=new pipe[1000];
	int testCases;
	String method;
	String startNode;
	String destNodes[];
	String midNodes[];
	int pipes;
	int vertices;
	int startTime;
	
	void Process(String fileName)
	{
		BufferedReader in=null;
		BufferedWriter out=null;
		try {
			in=new BufferedReader(new FileReader(fileName));
			out=new BufferedWriter(new FileWriter("output.txt"));
			String data;
			data=in.readLine();
			testCases=Integer.parseInt(data);
		
			while(testCases!=0)
			{
		
				method=in.readLine();
		
				if(method.equals("BFS"))
				{
					Map<String,Info> cost=new HashMap<String,Info>();
					startNode=in.readLine();
					vertices = 1;
					cost.put(startNode,new Info(0,0,null));
					destNodes=in.readLine().split(" ");
					for(String d:destNodes)
					{
						cost.put(d,new Info(0,INFINITY,null));
					}
					vertices=vertices+destNodes.length;
					midNodes=in.readLine().split(" ");
					for(String d:midNodes)
					{
						cost.put(d,new Info(0,INFINITY,null));
					}
					vertices=vertices+midNodes.length;
					pipes=Integer.parseInt(in.readLine());
				
					int i=0;
					while(i<pipes)
					{
						data=in.readLine();
					
						String[] pipeInfo=data.split(" ");
						pipeTree[i]=new pipe();
						pipeTree[i].start=pipeInfo[0];
						pipeTree[i].end=pipeInfo[1];
						pipeTree[i].length=1;
						pipeTree[i].flag=false;
						i++;	
					}
					startTime=Integer.parseInt(in.readLine());
					String answer=BFS(pipeTree,startNode,startTime,destNodes,midNodes,vertices,cost);
				
					out.write(answer);
					out.write("\r\n");
				}
				else if(method.equals("DFS"))
				{
					
					Map<String,Info> cost=new HashMap<String,Info>();
					startNode=in.readLine();
					vertices = 1;
					cost.put(startNode,new Info(0,0,null));
					destNodes=in.readLine().split(" ");
					for(String d:destNodes)
					{
						cost.put(d,new Info(0,INFINITY,null));
					}
					vertices=vertices+destNodes.length;
					midNodes=in.readLine().split(" ");
					for(String d:midNodes)
					{
						cost.put(d,new Info(0,INFINITY,null));
					}
					vertices=vertices+midNodes.length;
					pipes=Integer.parseInt(in.readLine());		
					int i=0;
					while(i<pipes)
					{
						String pipeInfo[]=in.readLine().split(" ");
						pipeTree[i]=new pipe();
						pipeTree[i].start=pipeInfo[0];
						pipeTree[i].end=pipeInfo[1];
						pipeTree[i].length=1;
						pipeTree[i].flag=false;
						i++;
					}
					startTime=Integer.parseInt(in.readLine());
					String answer=DFS(pipeTree,startNode,startTime,destNodes,midNodes,vertices,cost);
				
					out.write(answer+"\r\n");
				}
				else if(method.equals("UCS"))
				{
					Map<String,Info> cost=new HashMap<String,Info>();
					startNode=in.readLine();
					vertices = 1;
					cost.put(startNode,new Info(0,0,null));
					destNodes=in.readLine().split(" ");
					for(String d:destNodes)
					{
						cost.put(d,new Info(0,INFINITY,null));
					}
					vertices=vertices+destNodes.length;
					midNodes=in.readLine().split(" ");
					for(String d:midNodes)
					{
						cost.put(d,new Info(0,INFINITY,null));
					}
					vertices=vertices+midNodes.length;
					pipes=Integer.parseInt(in.readLine());
					int i=0;
					while(i<pipes)
					{
						String pipeInfo[]=in.readLine().split(" ");
						pipeTree[i]=new pipe();
						pipeTree[i].start=pipeInfo[0];
						pipeTree[i].end=pipeInfo[1];
						pipeTree[i].length=Integer.parseInt(pipeInfo[2]);
						pipeTree[i].offPeriods=Integer.parseInt(pipeInfo[3]);
						pipeTree[i].offtime=new int[pipeTree[i].offPeriods][2]; 
						for(int j=0,k=4;j<pipeTree[i].offPeriods;j++,k++)
						{
							String[] sf=pipeInfo[k].split("-");
							
							pipeTree[i].offtime[j][0]= Integer.parseInt(sf[0]);
							pipeTree[i].offtime[j][1]=Integer.parseInt(sf[1]);
				
						}
						pipeTree[i].flag=false;
						i++;
					}
					startTime=Integer.parseInt(in.readLine());
			
					String answer=UCS(pipeTree,startNode,startTime,destNodes,midNodes,vertices,cost);
				
					out.write(answer+"\r\n");
				}
				in.readLine();
				testCases--;
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
		try{
				
				if(in!=null)
				{
					in.close();
				}
				if(out!=null)
				{
					out.close();
				}
			}
			catch(IOException e)
			{
		 		e.printStackTrace();
			}
		}

	}
	
	private String UCS(pipe[] pipeTree2, String startNode2, int startTime2,
			String[] destNodes2, String[] midNodes2, int vertices2,
			Map<String, Info> cost) {
	
		LinkedList<Node> openQ=new LinkedList<Node>();
		LinkedList<Node> closedQ=new LinkedList<Node>();		
		
		openQ.add(new Node(startNode2,startTime2));
		Info info=cost.get(startNode2);
		info.dist=startTime2;
		cost.put(startNode2, info);

		
		
		while(!openQ.isEmpty())
		{
			Node node=openQ.remove();
			
			for (String d:destNodes2)
			{
				
				if((node.name).equals(d))
				{
					return d + " " +(node.length%24);
				}
			}
			for(int i=0;i<pipes;i++)
			{
				pipe currentPipe=pipeTree[i];
				if((currentPipe.start).equals(node.name))
				{
					Info info1=cost.get(currentPipe.start);
					Info info2=cost.get(currentPipe.end);
					Node current=hasNode(openQ,currentPipe.end);
					if(current!=null)
					{
						
						if(current.length>info2.dist)
						{
							current.length=info2.dist;
							continue;
						}
					}
					current=hasNode(closedQ,currentPipe.end);
					if(current!=null)
					{
						if(current.length>info2.dist)
						{
							closedQ.remove(current);
							current.length=info2.dist;
							openQ.add(current);
							continue;
						}	
					}
					else
					{
						if(info2.dist > (info1.dist+currentPipe.length) && inOffTime(currentPipe.offPeriods,currentPipe.offtime,info1.dist))
						{
						
							info2.dist=(info1.dist+currentPipe.length);
							cost.put(currentPipe.end, info2);
					
							openQ.add(new Node(currentPipe.end,info2.dist));
						}
					}
				}					
			}
			Collections.sort(openQ,new CompareNodesAlphabetically());
			closedQ.add(node);
		}
		return "None";
	}

	

	private boolean inOffTime(int offPeriods, int[][] offtime, int dist) {
		for(int i=0;i<offPeriods;i++)
		{
			
				if((dist%24)>=offtime[i][0] && (dist%24)<=offtime[i][1])
								return false;		
		}
		return true;
	}

	private Node hasNode(Queue<Node> queue, String name) {
	
		
		for(Node node:queue)
		{
			if(node.name==name)
			{
				return node;
			}
		}
		
		return null;
	}

	private String DFS(pipe[] pipeTree2, String startNode2, int startTime2,
			String[] destNodes2, String[] midNodes2, int vertices2,
			Map<String, Info> cost) {
		
		Stack<pipe> stack=new Stack<pipe>();
		Info info=cost.get(startNode2);
		info.dist=startTime2;
		
		cost.put(startNode2, info);
	
		int length = 0;
		pipe ends[]=new pipe[100];
		for(int i=0;i<pipes;i++)
		{
			pipe p=pipeTree[i];
			if((p.start).equals(startNode2))
			{
		
						ends[length]=new pipe();
						ends[length++]=p;
			}
		}
		if(length>1)
			ends=arranePipes(ends, length);
		for(int i=length-1;i>=0;i--)
		{
			pipe p=ends[i];
			stack.push(p);
		}
		
		while(!stack.isEmpty())
		{
			pipe p=stack.pop();
			info=cost.get(p.start);
			info.marked=1;	
			cost.put(p.start,info);
			for (String d:destNodes2)
			{
				
				if(p.end.equals(d))
				{
					Info info1=cost.get(p.start);
					Info info2=cost.get(p.end);

					//if(info2.dist > ((info1.dist+1)%24))
					{
						
						info2.dist=(info1.dist+1);
						cost.put(p.end, info2);

					}
					return p.end + " " +(info2.dist)%24;
				}
			}
			length=0;
			for(int i=0;i<pipes;i++)
			{
				pipe currentPipe=pipeTree[i];
				if((currentPipe.start).equals(p.end))
				{
		
					info=cost.get(currentPipe.end);
					if(info.marked!=1)
					{
						ends[length]=new pipe();
						ends[length++]=currentPipe;
						Info info1=cost.get(p.start);
						Info info2=cost.get(p.end);	
					//	if(info2.dist > ((info1.dist+1)%24))
						{
							info2.dist=(info1.dist+1);
							cost.put(p.end, info2);

						}
						/*info1.marked=1;
						cost.put(p.start,info1);*/
					}
					
				}
			}
			if(length>1)
				ends=arranePipes(ends, length);
			for(int i=length-1;i>=0;i--)
			{
		
					pipe p1=ends[i];				
					stack.push(p1);
			}
		}
		return "None";
	}

	public String BFS(pipe[] pipeTree2, String startNode2, int startTime2,
			String[] destNodes2, String[] midNodes2,int vertices,Map<String,Info> cost) {
		
		Queue<pipe> queue = new LinkedList<pipe>();

		Info info=cost.get(startNode2);
		info.dist=startTime2;
		info.marked=1;
		cost.put(startNode2, info);
	
		int length = 0;
		pipe ends[]=new pipe[100];
		for(int i=0;i<pipes;i++)
		{
			pipe p=pipeTree[i];
			if((pipeTree[i].start).equals(startNode2))
			{
						ends[length]=new pipe();
						ends[length++]=p;
			}
		}
		if(length>1)
			ends=arranePipes(ends, length);
		for(int i=0;i<length;i++)
		{
			pipe p=ends[i];
			
			info=cost.get(p.end);
			
			info.marked=1;
			cost.put(p.end, info);
			queue.add(p);
		}	
		
		while(!queue.isEmpty())
		{
			pipe p=queue.remove();
			Info info1;
			/*Info info1=cost.get(p.start);
			info1.marked=1;
			cost.put(p.start, info1);
			*/
			for (String d:destNodes2)
			{
				if(p.end.equals(d))
				{
					info1=cost.get(p.start);
					Info info2=cost.get(p.end);
					if(info2.dist > ((info1.dist+1)))
					{
						info2.dist=(info1.dist+1);
						cost.put(p.end, info2);
					}
					return p.end + " " +(info2.dist%24);
				}
			}
			length=0;
			for(int i=0;i<pipes;i++)
			{
				pipe p1=pipeTree[i];
				if((p1.start).equals(p.end))
				{
					info=cost.get(p1.end);
					if(info.marked!=1)
					{
						ends[length]=new pipe();
						ends[length++]=p1;
					}
				}
			}
			if(length>1)
				ends=arranePipes(ends, length);
			for(int i=0;i<length;i++)
			{
				pipe p1=ends[i];
			
				info=cost.get(p1.end);
				
				info.marked=1;
				cost.put(p1.end, info);
				queue.add(p1);
				info1=cost.get(p.start);
				Info info2=cost.get(p.end);
				if(info2.dist > ((info1.dist+1)))
				{
					info2.dist=(info1.dist+1);
					cost.put(p.end, info2);
				}
				/*info1.marked=1;
				cost.put(p.start,info1);*/

			}
		}
		return "None";
	}

	
	public pipe[] arranePipes(pipe pipes[],int length)
	{
		for(int i=0;i<length-1;i++)
		{
			for(int j=1;j<length-i;j++)
			{
				if((pipes[i].end).compareTo(pipes[j].end)>0)
				{
				
					pipe temp=pipes[i];
					pipes[i]=pipes[j];
					pipes[j]=temp;
				}
					
			}
		}
		return pipes;
	}
	

class Node
{
	public Node(String name, int length) {
		this.name=name;
		this.length=length;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String name;
	public int length;
}



class CompareNodesAlphabetically implements Comparator<Node>
{

	@Override
	public int compare(Node o1, Node o2) 
	{
		if(o1.length==o2.length)
		return o1.getName().compareTo(o2.getName());
		else
			return o1.length-o2.length;
	}
	
}	
	public static void main(String[] args) {
		long mytime=System.currentTimeMillis();
		waterFlow wf=new waterFlow();
		wf.Process(args[1]);
		long endTime=System.currentTimeMillis();
		System.out.println(endTime-mytime);
	}

}
