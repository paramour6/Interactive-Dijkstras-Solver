import javax.swing.JFrame;

public class Window
{
	private static JFrame m_Frame;
	private static StartPanel m_startPanel;
	private static DrawPanel m_drawPanel;
	private static SolvePanel m_solvePanel;
	public static Dijkstras m_Dijkstras;
	
	public Window()
	{
		m_Frame = new JFrame("Interactive Dijkstras Algorithm Solver");
		m_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_Frame.setResizable(false);
		m_Frame.setSize(750, 750);
		
		m_Dijkstras = new Dijkstras();
		m_startPanel = new StartPanel();
		m_drawPanel = new DrawPanel();
		m_solvePanel = new SolvePanel();
	}
	
	public void start()
	{
		m_Frame.add(m_startPanel);
		m_Frame.setVisible(true);
	}
	
	public static void placeNodes()
	{
		m_Frame.remove(m_startPanel);
		m_Frame.add(m_drawPanel);
	}
	
	public static void solve()
	{
		m_Frame.add(m_solvePanel);
		m_Frame.remove(m_drawPanel);
	}
}