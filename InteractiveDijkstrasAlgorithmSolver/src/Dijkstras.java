import java.util.ArrayList;
import java.util.Random;

public class Dijkstras
{
	private ArrayList<Node> m_nodeList;
	private ArrayList<WeightedLine> m_lineList;
	public ArrayList<Integer> distances;
	public ArrayList<Integer> visited_ids;
	private Random random = new Random();
	
	public Dijkstras()
	{
		m_nodeList = new ArrayList<Node>();
		m_lineList = new ArrayList<WeightedLine>();
	}
	
	public ArrayList<Node> getNodeList()
	{
		return m_nodeList;
	}
	
	public ArrayList<WeightedLine> getLineList()
	{
		return m_lineList;
	}
	
	public int getNodeListSize()
	{
		return m_nodeList.size();
	}
	
	private int getWeightBetweenNodes(Node fNode, Node sNode)
	{
		for(int i = 0; i < m_lineList.size(); i++)
		{
			if(m_lineList.get(i).getFNode() == fNode && m_lineList.get(i).getSNode() == sNode)
			{
				return m_lineList.get(i).getWeight();
			}
		}
		return 0;
	}
	
	public void addNode(int x, int y)
	{
		m_nodeList.add(new Node(x, y));
	}
	
	public void addNode(Node n)
	{
		m_nodeList.add(n);
	}
	
	public void addWeightedLine(Node fNode, Node sNode)
	{
		m_lineList.add(new WeightedLine(fNode, sNode));
	}
	
	public Node getNode(int index)
	{
		if(index >= m_nodeList.size()) return null;
		
		return m_nodeList.get(index);
	}
	
	private int minimumVertexID(int[] distances, boolean[] in)
	{
		int minID = -1, minVal = Integer.MAX_VALUE;
		for(int i = 0; i < m_nodeList.size(); i++)
		{
			if(in[i] == false && distances[i] <= minVal)
			{
				minVal = distances[i];
				minID = i;
			}
		}
		return minID;
	}

	public int solve_dijkstras(int sourceNode, int destNode)
	{
		int[][] weight_map = new int[m_nodeList.size()][m_nodeList.size()];
		int[] DIST_set = new int[m_nodeList.size()];
		boolean[] IN_set = new boolean[m_nodeList.size()];
		
		for(int y = 0; y < m_nodeList.size(); y++)
		{
			IN_set[y] = false;
			DIST_set[y] = Integer.MAX_VALUE;
			for(int x = 0; x < m_nodeList.size(); x++)
			{
				weight_map[y][x] = getWeightBetweenNodes(m_nodeList.get(y), m_nodeList.get(x));
			}
		}
		
		DIST_set[sourceNode] = 0;
		
		for(int i = 0; i < m_nodeList.size(); i++)
		{
			int u = minimumVertexID(DIST_set, IN_set);
			IN_set[u] = true;
			
			for(int v = 0; v < m_nodeList.size(); v++)
			{
				if(!IN_set[v] && weight_map[u][v] != 0 && DIST_set[u] != Integer.MAX_VALUE && DIST_set[u] + weight_map[u][v] < DIST_set[v])
				{
					DIST_set[v] = DIST_set[u] + weight_map[u][v];
				}
			}
		}
		return DIST_set[destNode];
	}
}
