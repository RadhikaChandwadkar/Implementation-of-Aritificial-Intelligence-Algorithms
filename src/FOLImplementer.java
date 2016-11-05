import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;




class AtomicSentence
{
	String predicateName;
	List<String> arguments;
	
	public AtomicSentence(String predicateName, List<String> arguments) 
	{
		super();
		this.predicateName = predicateName;
		this.arguments = arguments;
	}
	public AtomicSentence() {
		// TODO Auto-generated constructor stub
	}
	public String getPredicateName() 
	{
		return predicateName;
	}
	public void setPredicateName(String predicateName)
	{
		this.predicateName = predicateName;
	}
	public List<String> getArguments() 
	{
		return arguments;
	}
	public void setArguments(List<String> arguments) 
	{
		this.arguments = arguments;
	}
	@Override
	public String toString() {
		return "AtomicSentence [predicateName=" + predicateName
				+ ", arguments=" + arguments + "]";
	}
	
	
}

public class FOLImplementer
{
	static Map<AtomicSentence, List<List<AtomicSentence>>> KB;
	
	public void addInList(AtomicSentence RHS,List<AtomicSentence> LHS)
	{
		if(KB.containsKey(RHS))
		{
			List<List<AtomicSentence>> list=KB.get(RHS);
			list.add(LHS);
			KB.put(RHS, list);
		}
		else
		{
			List<List<AtomicSentence>> list=new ArrayList<List<AtomicSentence>>();
			list.add(LHS);
			KB.put(RHS, list);
		}
	}
	
	public void printMap()
	{
		for(Map.Entry<AtomicSentence, List<List<AtomicSentence>>> entry : KB.entrySet()){
		    System.out.println("Key::"+entry.getKey()+" Values::"+entry.getValue());
		}

	}
	
	

	public static void main(String[] args)
	{
		FOLImplementer i=new FOLImplementer();
		BufferedReader in=null;
		BufferedWriter out=null;
		List<AtomicSentence> queryList=new ArrayList<AtomicSentence>();
		KB=new HashMap<>();
		try
		{
			in=new BufferedReader(new FileReader(args[1]));
			out=new BufferedWriter(new FileWriter("output.txt"));
			int noOfQueries;
			noOfQueries=Integer.parseInt(in.readLine());	// no of queries
			for(int no=0;no<noOfQueries;no++)
			{
				AtomicSentence query=i.parseAtomicSentence(in.readLine());
				queryList.add(query);
			}

			int KBlength;
			KBlength=Integer.parseInt(in.readLine());
			// no of queries
			for(int no=0;no<KBlength;no++)
			{
				i.parseSentence(in.readLine());
				
			}
			
			for(int no=0;no<noOfQueries;no++)
			{
				i.getResult(queryList.get(no));
				
			}
			
			
			
			i.printMap();
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			try
			{
				
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


	public AtomicSentence getKey(String predicate)
	{
		for(AtomicSentence k:KB.keySet())
		{
			if(k.predicateName.equals(predicate))
			{
				return k;
			}
		}
		return null;
	}


	private void getResult(AtomicSentence goal)
	{
		List<AtomicSentence> goals=new ArrayList<>();
		goals.add(goal);
		Map<String,String> substitution=backwardChaining(goals,new HashMap<String,String>());
		
	}

	
	
	private Map<String, String> backwardChaining(List<AtomicSentence> goals,Map<String,String> substitution) 
	{
		if(goals==null)
			return substitution;
		AtomicSentence subgoal=goals.get(0);
		
		AtomicSentence q=subst(subgoal,substitution);
		AtomicSentence q1=getKey(subgoal.predicateName);
			
		//unify goal q' with q 
		unify();
		
		
		//
		List<List<AtomicSentence>> LHS=KB.get(q1);
		
		
		
		
		
		return null;
	}

	private  void parseSentence(String clause)
	{
		AtomicSentence sentence=new AtomicSentence();
	//	clause=clause.substring(0, clause.length()-1);
		String list[]=clause.split(" => ");
		if(list.length==1)
		{
			sentence=parseAtomicSentence(clause);
			KB.put(sentence,null);
			return;
		}
		else
		{
			String RHS=list[1];
			AtomicSentence impliedSentence=parseAtomicSentence(RHS);
			List<AtomicSentence> LHS=new ArrayList<>();
			String lhs[]=(list[0]).split("\\^");
			for(int i=0;i<lhs.length;i++)
			{
				lhs[i]=lhs[i].trim();
		//		System.out.println("value for "+lhs[i]);
				AtomicSentence newSentence=parseAtomicSentence(lhs[i]);
				LHS.add(newSentence);
			}
			addInList(impliedSentence, LHS);
		}
	}




	private  AtomicSentence parseAtomicSentence(String clause) 
	{
		AtomicSentence sentence=new AtomicSentence();
		clause=clause.substring(0, clause.length()-1);
		String list[]=clause.split("\\(");
		sentence.predicateName=list[0];
		sentence.arguments=new ArrayList<String>();
		String args[]=list[1].split(",");
		for(int i=0;i<args.length;i++)
		{
			sentence.arguments.add(args[i]);
		}
		return sentence;
	}

}

