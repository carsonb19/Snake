package Snake;
import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class board extends JPanel implements KeyListener
{
	//a[41][41]
	private int a[][] = new int[41][41];
	Timer timer;
	private int curi = 20;
	private int curj = 20;
	private int diri = 0;
	private int dirj = 0;
	private int length = 1;
	private int count = 0;
	boolean direction = true;
	private int curi1 = 0;
	private int curj1 = 0;
	private int curi2 = 0;
	private int appleI = 50;
	private int appleJ = 50;
	Random r = new Random();
	public board()
	{
		setLayout(null);
        addKeyListener(this);
        setFocusable(true);
		SpawnBoard();
		timer = new Timer (80, new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						a[appleI][appleJ] = -1;
						count++;
						try
						{
							if (a[curi+diri][curj+dirj] == 0)
							{
								a[curi+diri][curj+dirj] = length+1;
								curi = curi + diri;
								curj = curj + dirj;
								for (int i = 0; i < 41; i++)
								{
									for (int j = 0; j < 41; j++)
									{
										if (a[i][j] > 0)
										{
											a[i][j]--;
										}
									}
								}
							}
							else if (a[curi+diri][curj+dirj] == -1)
							{
								length = length + 4;
								a[curi+diri][curj+dirj] = length;
								curi = curi + diri;
								curj = curj + dirj;
								for (int i = 0; i < 41; i++)
								{
									for (int j = 0; j < 41; j++)
									{
										if (a[i][j] > 0 && a[i][j] != a[curi][curj])
										{
											a[i][j] = a[i][j] + 4;
										}
									}
								}
								SpawnApple();
							}
							else
							{
								fail();
							}
						}
						catch (Exception ex)
						{
							fail();
						}
						
						if (count % 2 == 0)
						{
							for (int i = curi1; i < curi1 + 6; i++)
							{
								if (direction == true)
								{
									try
									{
										a[i][curj1+1] = -2;
										a[i][curj1] = 0;
										a[i+25][curj1+1] = -2;
										a[i+25][curj1] = 0;
									}
									catch (Exception E)
									{
										a[i][curj1-1] = -2;
										a[i][curj1] = 0;
										a[i+25][curj1-1] = -2;
										a[i+25][curj1] = 0;
										direction = false;
										//i = curi1;
										//timer.stop();
									}
								}
								else if (direction == false)
								{
									try
									{
										a[i][curj1-1] = -2;
										a[i][curj1] = 0;	
										a[i+25][curj1-1] = -2;
										a[i+25][curj1] = 0;
									}
									catch (Exception E)
									{
										a[i][curj1+1] = -2;
										a[i][curj1] = 0;
										a[i+25][curj1+1] = -2;
										a[i+25][curj1] = 0;
										direction = true;
									}
								}
							}
							try
							{
								if (direction == true)
								{
									curj1++;
								}
								else if (direction == false)
								{
									curj1--;
								}
							}
							catch (Exception E)
							{
								
							}
						}
						repaint();
					}
				}
				
			);
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT)
        {
        	diri = 0;
        	dirj = -1;
        	if (timer.isRunning() == false)
        	{
        		timer.restart();
        		SpawnApple();
        	}
        }
        else if (key == KeyEvent.VK_RIGHT)
        {
        	diri = 0;
        	dirj = 1;
        	if (timer.isRunning() == false)
        	{
        		timer.restart();
        		SpawnApple();
        	}
        }
        else if (key == KeyEvent.VK_DOWN)
        {
        	diri = 1;
        	dirj = 0;
        	if (timer.isRunning() == false)
        	{
        		timer.restart();
        		SpawnApple();
        	}
        }
        else if (key == KeyEvent.VK_UP)
        {
        	diri = -1;
        	dirj = 0;
        	if (timer.isRunning() == false)
        	{
        		timer.restart();
        		SpawnApple();
        	}
        }
	}
	public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
	
    public void SpawnApple()
    {
    	boolean flag = false;
    	while (flag == false)
    	{
    		int i = r.nextInt(41);
    		int j = r.nextInt(41);
    		if(a[i][j] == 0)
    		{
    			a[i][j] = -1;
    			appleI = i;
    			appleJ = j;
    			flag = true;
    		}
    		else if (a[j][i] == 0)
    		{
    			a[j][i] = -1;
    			appleI = j;
    			appleJ = i;
    			flag = true;
    		}
    	}
    }
    public void SpawnBoard()
    {
    	for (int i = 0; i < 41; i++)
		{
			for (int j = 0; j < 41; j++)
			{
				a[i][j] = 0;
			}
		}
		a[20][20] = 1;
		for (int i = 6; i < 12; i++)
		{
			a[i][6] = -2;
			a[i+25][6] = -2;
		}
		curi1 = 6;
		curi2 = 31;
		curj1 = 6;
    }
    
    public void fail()
    {
    	JOptionPane.showMessageDialog (this, "Congatulations, you reached a length of " + length + "! Try again?");
    	SpawnBoard();
		length = 1;
		curi = 20;
		curj = 20;
		diri = 0;
		dirj = 0;
		curi1 = 6;
		curj1 = 6;
		direction = false;
		timer.stop();
    }
    
    public void paint(Graphics g)
    {
    	super.paint(g);
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, 615, 615);
    	for (int i = 0; i < 41; i++)
    	{
    		for (int j = 0; j < 41; j++)
    		{
    			if (a[i][j] > 0)
    			{
    				g.setColor(Color.GREEN);
    		    	g.fillRect(j*15, i*15, 15, 15);
    			}
    			else if (a[i][j] == -1)
    			{
    				g.setColor(Color.RED);
    				g.fillRect(j*15, i*15, 15, 15);
    			}
    			else if (a[i][j] == -2)
    			{
    				g.setColor(Color.WHITE);
    				g.fillRect(j*15, i*15, 15, 15);
    			}
    		}
    	}
    	g.setColor(Color.RED);
    	g.fillRect(appleJ*15, appleI*15, 15, 15);
    	g.setColor(Color.GRAY);
    	for (int i = 0; i < 605; i = i + 15)
    	{
    		g.drawLine(i, 0, i, 615);
    		g.drawLine(0, i, 615, i);
    	}
    }
    
	public static void main(String args[])
	{
		JFrame frame = new JFrame("Snake");
		board b = new board();
		frame.add(b);
		frame.setSize(628, 650);
		frame.setResizable(true);  
        frame.setVisible(true);
	}
}
