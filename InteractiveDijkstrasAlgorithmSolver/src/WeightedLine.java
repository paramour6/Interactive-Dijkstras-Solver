import java.lang.Math;

public class WeightedLine
{
	private Node m_fNode, m_sNode;
	private int m_weight;
	private int m_midpointX, m_midpointY;
	
	public WeightedLine(Node fNode, Node sNode)
	{
		m_fNode = fNode;
		m_sNode = sNode;
		m_weight = (int)Math.round(Math.sqrt((sNode.getX() - fNode.getX())*(sNode.getX() - fNode.getX()) + (sNode.getY()- fNode.getY())*(sNode.getY()- fNode.getY())) / 3);
		m_midpointX = (m_fNode.getX() + m_sNode.getX()) / 2;
		m_midpointY = (m_fNode.getY() + m_sNode.getY()) / 2;
	}
	
	public int getX1() {return m_fNode.getX();}
	public int getX2() {return m_sNode.getX();}
	public int getY1() {return m_fNode.getY();}
	public int getY2() {return m_sNode.getY();}
	public int getmX() {return m_midpointX;}
	public int getmY() {return m_midpointY;}
	public Node getFNode() {return m_fNode;}
	public Node getSNode() {return m_sNode;}
	public int getWeight() {return m_weight;}
}
