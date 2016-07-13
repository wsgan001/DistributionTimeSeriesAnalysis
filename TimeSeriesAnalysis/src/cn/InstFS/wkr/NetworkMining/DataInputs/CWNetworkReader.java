package cn.InstFS.wkr.NetworkMining.DataInputs;



import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cn.InstFS.wkr.NetworkMining.TaskConfigure.TaskElement;

import java.util.Comparator;

import com.sun.jna.platform.unix.X11.XClientMessageEvent.Data;





/**
 * 
 * @author chenwei
 *
 */
class NodeandHops implements Comparable
{
	int Node;
	int Hops;
	public NodeandHops(int Node,int Hops)
	{
		this.Node	=	Node;
		this.Hops 	=	Hops;
	}
	public int compareTo(Object o) 
	{
		NodeandHops tmp=(NodeandHops) o;
	    int result=(Hops>tmp.Hops)?1:(Hops==tmp.Hops?0:-1);
        return result;
	}
}
class Link implements Comparable
{
	String start;
	String end;
	int time;
	public Link(String start,String end,int time)
	{
		this.start = start;
		this.end   = end;
		this.time  =time;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Link tmp = (Link) o;
		int result = (time>tmp.time)?1:(time==tmp.time?0:-1);
		return result;
	}
}
class Pair implements Comparable<Pair>
{
	String first;
	String second;
	public Pair(String first,String second)
	{
		this.first = first;
		this.second = second;
	}
	@Override
	public int compareTo(Pair arg0) {
		// TODO Auto-generated method stub
		if(first.compareTo(arg0.first)==0)
			return second.compareTo(arg0.second);
		return first.compareTo(arg0.first);
		
	}
	@Override
	public boolean equals(Object o)
	{
		Pair another =(Pair)o;
		
		return first.equals(another.first)&&second.equals(another.second);
	}
	@Override
	public  int hashCode()
	{
		return (first+second).hashCode();
		
	}
}

class TextReader
{
	private String path="";
	private InputStreamReader fr= null;//new InputStreamReader(new FileInputStream(path+"/"+fileName),encoding);
	private BufferedReader bfr=null;//new BufferedReader(fr);
	private String curLine = null;
	private String encoding = "UTF-8";
	public TextReader(String path)
	{
		this.path = path;
		try
		{
			fr= new InputStreamReader(new FileInputStream(path),encoding);
			bfr = new BufferedReader(fr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public String readLine()
	{
		try
		{
			curLine=bfr.readLine();
			if(curLine==null)
				bfr.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		return curLine;
	}
}
public class CWNetworkReader  implements IReader{
   
    private ArrayList<Link> linkList = new ArrayList<Link> ();
    //private Set<String> nodesSet	= new HashSet<String>();
    private ArrayList<Set<Pair>> matrixList = new ArrayList<Set<Pair>> ();
    private TaskElement task = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private int start	=	0;
	private int end		=	365*24*60*60-1;	
	private int timeseg =   3*60*60; //锟斤拷锟斤拷锟斤拷锟斤拷
	private DataItems dataItems = new DataItems();
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		TaskElement task = new TaskElement();
		task.setSourcePath("E:\\javaproject\\NetworkMiningSystem\\NetworkMiningSystem\\mergeNode");
		
		task.setMiningObject("网络直径");
		CWNetworkReader reader = new CWNetworkReader(task);
		System.out.println("网络直径");
//		System.out.println(reader.readClusterByText().getData());
		System.out.println(reader.readInputByText().getData());
	
		task.setMiningObject("网络簇系数");
		reader = new CWNetworkReader(task);
		System.out.println("网络簇系数");
		System.out.println(reader.readInputByText().getData());
//		System.out.println(reader.readInputByText().getData());
		
	 }
	public CWNetworkReader(TaskElement task)
	{
		this.task=task;
		this.timeseg=task.getGranularity();
	}
	
	private Date second2Date(int second)
	{
		 Calendar cal = Calendar.getInstance();
		 cal.set(2015, 9, 1, 0, 0, 0);
		 cal.add(Calendar.SECOND,second);
		 Date date = cal.getTime();
		 return date;
	}
	private void getlinkList()
	{
		String path=task.getSourcePath();
		DataItems dataItems=new DataItems();

		File srcFiles=new File(path);
		try
		{
			for(int i=0;i<srcFiles.list().length;i++)
			{
				
				TextReader textReader = new TextReader(path+"/"+srcFiles.list()[i]);
				String curLine="";
				String header =textReader.readLine(); //读取文件
				while((curLine=textReader.readLine())!=null)
				{
					
					//List<Map.Entry<Integer,Double>> mappingList = new ArrayList<Map.Entry<Integer,Double>>(map.entrySet());
					
					String seg[]=curLine.split(",");
					/**
					 * 解析路径
					 */
					String preNode =null;
					int preHops =-1;
					for(int j=5;j<seg.length;j++)
					{
						String str[] = seg[j].split(":");
						String node = str[0].split("-")[0]; //获取路由器号
						
						int hops = Integer.valueOf(str[1]);               //获取跳数
						if(preNode!=null&&preHops==hops)
						{
							linkList.add(new Link(preNode,node,Integer.valueOf(seg[0])));
						}
						preNode=node;
						preHops=hops;
//	
					}
//					
//					
				}
			}
			Collections.sort(linkList);
			
//			for(int i=0;i<linkList.size();i++)
//			{
//				System.out.println(linkList.get(i).start+" "+linkList.get(i).end+" "+linkList.get(i).time);
//			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	private void getMatrixList()
	{
		String path=task.getSourcePath();
		File srcFiles=new File(path);
		try
		{
			for(int i=0;i<srcFiles.list().length;i++)
			{
				
				TextReader textReader = new TextReader(path+"/"+srcFiles.list()[i]);
				String curLine="";
				String header =textReader.readLine(); //读取文件
				System.out.println("读取文件"+srcFiles.list()[i]);
				while((curLine=textReader.readLine())!=null)
				{
					
					//List<Map.Entry<Integer,Double>> mappingList = new ArrayList<Map.Entry<Integer,Double>>(map.entrySet());
					
					String seg[]=curLine.split(",");
					/**
					 * 解析路径
					 */
					String preNode =null;
					int preHops =-1;
					for(int j=5;j<seg.length;j++)
					{
						String str[] = seg[j].split(":");
						String node = str[0].split("-")[0]; //获取路由器号
						
						int hops = Integer.valueOf(str[1]);               //获取跳数
						
						if(preNode!=null&&preHops==hops)
						{
							while(Integer.valueOf(seg[0])/task.getGranularity()>=matrixList.size())
							{
								matrixList.add(new HashSet<Pair>());
							}
							matrixList.get(Integer.valueOf(seg[0])/task.getGranularity()).add(new Pair(preNode,node));
							matrixList.get(Integer.valueOf(seg[0])/task.getGranularity()).add(new Pair(node,preNode));
						}
						preNode=node;
						preHops=hops;
//	
					}
//					
//					
				}
			}
//			Collections.sort(linkList);
			
//			for(int i=0;i<linkList.size();i++)
//			{
//				System.out.println(linkList.get(i).start+" "+linkList.get(i).end+" "+linkList.get(i).time);
//			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	/**
	 * 计算簇系数
	 * @return
	 */
	private double calCluster(Set<Pair> matrix)
	{
		double result=0.0;
		Map<String,Set<String>> neighbourMap= new HashMap<String,Set<String>> ();
		/**
		 * 得到邻居
		 */
//		System.out.println("start");
		for(Pair entry:matrix)
		{
			String st = entry.first;
			String ed = entry.second;
//			System.out.println(st+" " +ed);
			if(!neighbourMap.containsKey(st))
			{
				Set<String> set = new HashSet<String> ();
				neighbourMap.put(st, set);
			}
			if(!neighbourMap.containsKey(ed))
			{
				Set<String> set = new HashSet<String> ();
				neighbourMap.put(ed, set);
			}
			neighbourMap.get(st).add(ed);
			neighbourMap.get(ed).add(st);
		}
		/**
		 * 计算簇系数
		 */
		for(Map.Entry<String,Set<String>> entry:neighbourMap.entrySet())
		{
			int k = entry.getValue().size();
			int edgenum=0;
			Iterator<String> iter = entry.getValue().iterator();
			ArrayList<String> list = new ArrayList<String>();
			while(iter.hasNext())
			{
				list.add(iter.next());
			}
			for(int i = 0;i<list.size();i++)
			{
				for( int j = i+1;j<list.size();j++)
				{
					if(matrix.contains(new Pair(list.get(i),list.get(j)))||matrix.contains(new Pair(list.get(j),list.get(i))))
					{
						edgenum++;
					}
					
				}
			}
//			System.out.println("cu "+2.0*edgenum/(k*(k-1))+"k "+k);
			if(k<2)
				result+=0;
			else
				result += 2.0*edgenum/(k*(k-1));
		}
		result/=neighbourMap.size();   //平均簇系数
//		System.out.println("neighbourMap"+neighbourMap.size());
		return result;
		
	}
	/**
	 * 计算直径
	 * @param matrix
	 * @return
	 */
	private int calDiameter(Set<Pair> matrix)
	{
		// TODO Auto-generated method stub
		int result = 0;
		
		Set<String> nodesSet = new HashSet<String>();
		for(Pair entry:matrix)
		{
			nodesSet.add(entry.first);
			nodesSet.add(entry.second);
		}
		String [] p =nodesSet.toArray(new String [nodesSet.size()]);
		int [][]dis = new int[p.length][p.length];
		/**
		 * floyd计算最短路
		 */
		for(int i=0;i<p.length;i++)
			for(int j=0;j<p.length;j++)
			{
				if(!matrix.contains(new Pair(p[i],p[j])))
					dis[i][j]=Integer.MAX_VALUE;
				else if(i==j)
					dis[i][j]=0;
				else
					dis[i][j]=1;
//				System.out.println("i "+i+"j "+j+"dis "+dis[i][j]);
			}
		for(int k=0;k<p.length;k++)
			for(int i=0;i<p.length;i++)
				for(int j=0;j<p.length;j++)
				{
					if(dis[i][k]!=Integer.MAX_VALUE&&dis[k][j]!=Integer.MAX_VALUE)
						dis[i][j]=(dis[i][k]+dis[k][j])<dis[i][j]?(dis[i][k]+dis[k][j]):dis[i][j];
				}
		
		for(int i=0;i<p.length;i++)
			for(int j=0;j<p.length;j++)
				result = dis[i][j]>result?dis[i][j]:result;
		return result-1;
	}
	
	private DataItems readNodeFrequenceByText(){
		DataItems dataItems=new DataItems();
		dataItems.setIsAllDataDouble(-1);
		String path=task.getSourcePath();
		File srcFiles=new File(path);
		Map<Integer,Map<String,Integer>> timeMaps = new TreeMap<Integer,Map<String,Integer>>();
		int min=Integer.MAX_VALUE;
		int max =Integer.MIN_VALUE;
		try
		{
			for(int i=0;i<srcFiles.list().length;i++)
			{
				System.out.println("read file "+srcFiles.list()[i]);
				TextReader textReader = new TextReader(path+"/"+srcFiles.list()[i]);
				String curLine="";
				textReader.readLine();
				while((curLine=textReader.readLine())!=null)
				{
					Map<String,Integer>	map = new HashMap<String,Integer>();  
					String seg[]=curLine.split(",");
					int timeSegNum =(Integer.parseInt(seg[0])-start)/task.getGranularity();
					/**
					 * 解析文件
					 */
				
					if(!timeMaps.containsKey(timeSegNum))
					{
						timeMaps.put(timeSegNum,new HashMap<String,Integer>());
					}
					min=Math.min(min,timeSegNum);
					max=Math.max(max, timeSegNum);
					timeMaps.get(timeSegNum).put(seg[1], 1);
					timeMaps.get(timeSegNum).put(seg[2], 1);
				}
			}
//			System.out.println(min+"wwwww"+max);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		//对于没有通信的时间段补一个空map;
		int pre=-1;
		for(Map.Entry<Integer, Map<String,Integer>>entry:timeMaps.entrySet())
		{
			for(int i=pre+1;i<entry.getKey();i++)
			{
				dataItems.add1Data(second2Date(task.getGranularity()*i),new HashMap<String,Integer>());
			}
			
			dataItems.add1Data(second2Date(task.getGranularity()*entry.getKey()),entry.getValue());
			pre=entry.getKey();
		}
		return dataItems;
	}
	
	private DataItems readClusterByText()
	{
		
//		int presegnum=0;
//		int cursegnum;
//		Set<Pair> matrix =new HashSet<Pair>();

//		getlinkList();
		
		/**
		 * 计算簇系数
		 */
//		for(int i=0;i<linkList.size();i++)
//		{
//			 cursegnum = (linkList.get(i).time-start)/timeseg;
//			// System.out.println("cursegnum "+cursegnum);
//			 if(cursegnum>presegnum)
//			 {
//				// System.out.println("matrix "+matrix.size());
//				 Date date = second2Date(timeseg*presegnum);
//				 dataItems.add1Data(date, String.valueOf(calCluster(matrix)));
//				 for(int j=presegnum+1;j<cursegnum;j++)
//				 {
//					 date = second2Date(timeseg*j);
//					 dataItems.add1Data(date, "0");
//				 }
//				 matrix.clear();
//				 presegnum=cursegnum;
//			 }
//			 matrix.add(new Pair(linkList.get(i).start,linkList.get(i).end));
//			 matrix.add(new Pair(linkList.get(i).end,linkList.get(i).start));
//		}
		getMatrixList();
		for(int i=0;i<matrixList.size();i++)
		{
			Date date = second2Date(task.getGranularity()*i);
			dataItems.add1Data(date, String.valueOf(calCluster(matrixList.get(i))));
		}
		return dataItems;
	}
	private DataItems readDiameterByText()
	{
//		int presegnum=0;
//		int cursegnum;
//		Set<Pair> matrix =new HashSet<Pair>();
//		getlinkList();
		
		/**
		 * 计算直径
		 */
//		for(int i=0;i<linkList.size();i++)
//		{
//			 cursegnum = (linkList.get(i).time-start)/timeseg;
//			 if(cursegnum>presegnum)
//			 {
//				 Date date = second2Date(timeseg*presegnum);
//				 dataItems.add1Data(date, String.valueOf(calDiameter(matrix)));
////				 System.out.println("matrix "+matrix.size()); 
//				 for(int j=presegnum+1;j<cursegnum;j++)
//				 {
//					 date = second2Date(timeseg*j);
//					 dataItems.add1Data(date, "0");
//				 }
//				 presegnum=cursegnum;
//				 matrix.clear();
//			 }
//			 matrix.add(new Pair(linkList.get(i).start,linkList.get(i).end));
//			 matrix.add(new Pair(linkList.get(i).end,linkList.get(i).start));
//			
//		}
//		 
//		Date date = second2Date(timeseg*presegnum);
//		dataItems.add1Data(date, String.valueOf(calDiameter(matrix)));\
		getMatrixList();
		for(int i=0;i<matrixList.size();i++)
		{
			Date date = second2Date(task.getGranularity()*i);
			System.out.println(matrixList.get(i).size());
			dataItems.add1Data(date, String.valueOf(calDiameter(matrixList.get(i))));
		}
		return dataItems;
	}
	
	@Override
	public DataItems readInputBySql() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DataItems readInputBySql(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DataItems readInputByText()
	{
		switch(task.getMiningObject())
		{
		case "网络簇系数": return readClusterByText();
		case "网络直径":return readDiameterByText();
		default: return null;
		}
	}
	@Override
	public DataItems readInputByText(String[] condistions) {
		// TODO Auto-generated method stub
		
		switch(task.getMiningObject())
		{
		case "网络簇系数": return readClusterByText();
		case "网络直径":return readDiameterByText();
		default: return null;
		}
		
	}
	@Override
	public Map<String, DataItems> readAllRoute() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
