import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class StartPanel extends JPanel
{
	private JButton startButton = new JButton("Start...");
	
	StartPanel()
	{
		this.setLayout(null);
		this.setSize(new Dimension(750, 750));
		
		startButton.setBounds(750 / 2 - 100, 750 / 2 - 50, 200, 50);
		startButton.setBackground(Color.MAGENTA);
		startButton.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent e)
			{
				startButton.setVisible(false);
				remove(startButton);
				Window.placeNodes();
			}
				});
		this.add(startButton);
	}
}

class DrawPanel extends JPanel
{
	private JButton stopButton = new JButton("Stop...");
	private Random rand = new Random();
	private ArrayList<Node> nodes;
	private ArrayList<WeightedLine> lines;
	private MouseAdapter mouseListener = new MouseAdapter()
			{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(e.getSource() != stopButton)
			{
				Node n = new Node(e.getX(), e.getY());
				Window.m_Dijkstras.addNode(n);
				if(n.getID() > 0)
				{
					Window.m_Dijkstras.addWeightedLine(Window.m_Dijkstras.getNode(n.getID() - 1), n);
					Window.m_Dijkstras.addWeightedLine(n, Window.m_Dijkstras.getNode(n.getID() - 1));
				}
				if((rand.nextInt(2) == 1) && n.getID()  > 1)
				{
					int nextID = -1;
					while(nextID == -1)
					{
						nextID = rand.nextInt(n.getID());
						if(nextID == n.getID() - 1) nextID = -1;
					}
					Window.m_Dijkstras.addWeightedLine(n, Window.m_Dijkstras.getNode(nextID));
					Window.m_Dijkstras.addWeightedLine(Window.m_Dijkstras.getNode(nextID), n);
				}
				
				repaint();
			}
		}
			};
	
	DrawPanel()
	{
		this.setLayout(null);
		this.setSize(new Dimension(750, 750));
		
		stopButton.setBounds(650, 750 / 2 - 15, 100, 30);
		stopButton.setBackground(Color.RED);
		stopButton.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent e)
			{
				stopButton.setVisible(false);
				remove(stopButton);
				removeMouseListener(mouseListener);
				Window.solve();
			}
				});
		
		this.add(stopButton);
		this.addMouseListener(mouseListener);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		nodes = Window.m_Dijkstras.getNodeList();
		lines = Window.m_Dijkstras.getLineList();
		
		if(nodes.size() == 0)
		{
			g2d.drawString("Click to place nodes...", 750 / 2 - 50, 750 / 2);
		}
		
		for(int i = 0; i < nodes.size(); i++)
		{
			g2d.setColor(nodes.get(i).getColor());
			g2d.fillRect(nodes.get(i).getX(), nodes.get(i).getY(), 20, 20);
			g2d.drawString(Integer.toString(nodes.get(i).getID() + 1), nodes.get(i).getX(), nodes.get(i).getY());
		}
		for(int i = 0; i < lines.size(); i++)
		{
			g2d.setColor(Color.BLACK);
			g2d.drawLine(lines.get(i).getX1(), lines.get(i).getY1(), lines.get(i).getX2(), lines.get(i).getY2());
			g2d.drawString(Integer.toString(lines.get(i).getWeight()), lines.get(i).getmX(), lines.get(i).getmY());
		}
	}
	
}

class SolvePanel extends JPanel
{
	private JTextArea m_fInput, m_sInput;
	private JButton m_button;
	private int firstNode = 0, secondNode = 0;
	private int shortestDistance = 0;
	
	public SolvePanel()
	{
		this.setLayout(null);
		this.setSize(new Dimension(750, 750));
		
		m_button = new JButton("Submit");
		m_button.setBounds(335, 80, 80, 50);
		m_button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		m_button.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent e)
			{
				firstNode = checkInteger(m_fInput.getText());
				secondNode = checkInteger(m_sInput.getText());
				
				if((firstNode == -1) || (secondNode == -1))
				{
					firstNode = secondNode = -1;
				}
				else
				{
					shortestDistance = Window.m_Dijkstras.solve_dijkstras(firstNode - 1, secondNode - 1);
					repaint();
				}
			}
				});
		
		m_fInput = new JTextArea();
		m_fInput.setBounds(335, 130, 80, 17);
		m_fInput.setBackground(Color.MAGENTA);
		m_fInput.setCaretColor(Color.BLACK);
		m_sInput = new JTextArea();
		m_sInput.setBounds(335, 148, 80, 17);
		m_sInput.setBackground(Color.MAGENTA);
		m_sInput.setCaretColor(Color.BLACK);
		this.add(m_fInput);
		this.add(m_sInput);
		this.add(m_button);
	}
	
	private int checkInteger(String text)
	{
		if(text.length() == 0) return -1;
		
		for(int i = 0; i < text.length(); i++)
		{
			if(!Character.isDigit(text.charAt(i)))
			{
				return -1;
			}
		}
		if(Integer.parseInt(text) > Window.m_Dijkstras.getNodeListSize() || Integer.parseInt(text) < 1) return -1;
		
		return Integer.parseInt(text);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		ArrayList<Node> nodes = Window.m_Dijkstras.getNodeList();
		ArrayList<WeightedLine> lines = Window.m_Dijkstras.getLineList();
		
		for(int i = 0; i < nodes.size(); i++)
		{
			g2d.setColor(nodes.get(i).getColor());
			g2d.fillRect(nodes.get(i).getX(), nodes.get(i).getY(), 20, 20);
			g2d.drawString(Integer.toString(nodes.get(i).getID() + 1), nodes.get(i).getX(), nodes.get(i).getY());
		}
		for(int i = 0; i < lines.size(); i++)
		{
			g2d.setColor(Color.BLACK);
			g2d.drawLine(lines.get(i).getX1(), lines.get(i).getY1(), lines.get(i).getX2(), lines.get(i).getY2());
			g2d.drawString(Integer.toString(lines.get(i).getWeight()), lines.get(i).getmX(), lines.get(i).getmY());
		}
		if(shortestDistance != 0)
		{
			g2d.setColor(Color.MAGENTA);
			g2d.fillRect(0, 0, 200, 18);
			g2d.setColor(Color.BLACK);
			g2d.drawString(("Shortest Distance(" + firstNode + "->" + secondNode + "): " + shortestDistance + " units"), 2, 12);
		}
	}
}