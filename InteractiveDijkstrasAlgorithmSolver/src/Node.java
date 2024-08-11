import java.awt.Color;
import java.util.Random;

public class Node
{
	private static int m_numNodes = 0;
	private int x, y, id;
	private Color m_Color;
	
	public Node(int x, int y)
	{
		Random rand = new Random();
		id = m_numNodes;
		m_numNodes++;
		this.x = x;
		this.y = y;
		m_Color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
	}
	
	public static int getNumNodes() {return m_numNodes;}
	public int getID() {return id;}
	public int getX() {return x;}
	public int getY() {return y;}
	public Color getColor() {return m_Color;}
}
