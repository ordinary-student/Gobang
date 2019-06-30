package com.game.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.game.thread.PlaySoundThread;
import com.game.utils.WindowUtil;

/**
 * 五子棋小游戏
 * 
 * @author ordinary-student
 *
 */
public class GameFrame extends KFrame
{
	private static final long serialVersionUID = -6201183662522133782L;
	private JMenuItem newGameItem;
	private JMenuItem exitItem;
	private JMenuItem backItem;
	private JCheckBoxMenuItem soundItem;

	// 所有棋子位置信息
	private Vector<String> allChessPositionInfo = new Vector<String>();
	// 白棋位置信息
	private Vector<String> whiteChessPositionInfo = new Vector<String>();
	// 黑棋位置信息
	private Vector<String> blackChessPositionInfo = new Vector<String>();

	// 计算悔棋步数
	private int whiteBackStepCount, blackBackStepCount;
	// 每个格子的边长
	private final static int GRID_WIDTH = 25;
	// 格子数量
	private final static int GRID_NUM = 16;
	// 棋盘的大小
	private int px = 100, py = 100;

	private int pxw = px + GRID_WIDTH, pyw = py + GRID_WIDTH;
	private int width = GRID_WIDTH * GRID_NUM, height = GRID_WIDTH * GRID_NUM;
	// 垂直线的长度
	private int vline = width + px;
	// 水平线的长度
	private int hline = height + py;
	// 音效标志
	private boolean isSound = true;

	/*
	 * 构造方法
	 */
	public GameFrame()
	{
		// 初始化界面
		initUI();
	}

	/**
	 * 初始化界面
	 */
	public void initUI()
	{
		// 设置标题
		setTitle("单机版五子棋");
		// 设置大小
		setSize(600, 600);
		// 不可改变大小
		setResizable(false);
		// 设置居中
		WindowUtil.center(this);
		// 设置布局
		getContentPane().setLayout(new BorderLayout());
		// 设置背景颜色
		setBackground(Color.orange);
		// 设置关闭方式
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 创建菜单栏
		JMenuBar menuBar = new JMenuBar();

		// 创建菜单
		JMenu startMenu = new JMenu("开始");
		JMenu helpMenu = new JMenu("帮助");

		// 创建菜单项
		newGameItem = new JMenuItem("新游戏");
		newGameItem.addActionListener(this);
		startMenu.add(newGameItem);

		exitItem = new JMenuItem("退出游戏");
		exitItem.addActionListener(this);
		startMenu.add(exitItem);

		backItem = new JMenuItem("悔棋");
		backItem.addActionListener(this);
		helpMenu.add(backItem);

		helpMenu.addSeparator();

		// 静音-菜单项
		soundItem = new JCheckBoxMenuItem("静音");
		soundItem.setMnemonic('Q');
		soundItem.setState(false);
		soundItem.addActionListener(this);
		helpMenu.add(soundItem);

		menuBar.add(startMenu);
		menuBar.add(helpMenu);

		// 设置菜单栏
		setJMenuBar(menuBar);

		validate();
		// 显示界面
		setVisible(true);
	}

	// 重写棋盘
	@Override
	public void paint(Graphics g)
	{
		// 清除棋盘
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		// 绘制网格颜色
		g.setColor(Color.BLACK);
		// 绘制网格大边界
		g.drawRect(px, py, width, height);
		// 画提示信息
		g.drawString("单机版五子棋小游戏，右击可以悔棋，欢迎来玩！", 180, 70);
		g.drawString(allChessPositionInfo.size() + "步棋", 250, 550);

		// 绘制每条横线和竖线
		for (int i = 0; i < 15; i++)
		{
			g.drawLine(pxw + i * GRID_WIDTH, py, pxw + i * GRID_WIDTH, hline);
			g.drawLine(px, pyw + i * GRID_WIDTH, vline, pyw + i * GRID_WIDTH);
		}

		// 绘制棋子
		for (int x = 0; x < allChessPositionInfo.size(); x++)
		{
			String str = (String) allChessPositionInfo.get(x);
			String tmp[] = str.split("-");

			int a = Integer.parseInt(tmp[0]);
			int b = Integer.parseInt(tmp[1]);

			a = a * GRID_WIDTH + px;
			b = b * GRID_WIDTH + py;

			if (x % 2 == 0)
			{
				g.setColor(Color.WHITE);
			} else
			{
				g.setColor(Color.BLACK);
			}

			// 绘制棋子
			g.fillArc(a - GRID_WIDTH / 2, b - GRID_WIDTH / 2, GRID_WIDTH, GRID_WIDTH, 0, 360);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// 判断来源
		if (e.getSource() == newGameItem)
		{
			// 新游戏
			newGame();

		} else if (e.getSource() == exitItem)
		{
			// 退出
			exit();

		} else if (e.getSource() == backItem)
		{
			// 悔棋
			back();

		} else if (e.getSource() == soundItem)
		{
			// 音效
			isSound = !soundItem.isSelected();
		}
	}

	/**
	 * 新游戏
	 */
	private void newGame()
	{
		// 清空所有走棋信息
		allChessPositionInfo.clear();
		blackChessPositionInfo.clear();
		whiteChessPositionInfo.clear();
		// 重绘
		repaint();
		// 悔棋次数清零
		whiteBackStepCount = 0;
		blackBackStepCount = 0;
	}

	/**
	 * 退出游戏
	 */
	private void exit()
	{
		System.exit(0);
	}

	/**
	 * 悔棋
	 */
	private void back()
	{
		// 没有走棋信息
		if (allChessPositionInfo.isEmpty())
		{
			// 播放音效
			if (isSound)
			{
				new PlaySoundThread("warning.wav").start();
			}

			JOptionPane.showMessageDialog(this, "没有棋可悔！");

		} else
		{
			// 有走棋信息
			// 判断是白棋悔棋，还是黑棋悔棋
			if (allChessPositionInfo.size() % 2 == 0)
			{
				// 黑棋悔棋
				blackBackStepCount++;
				// 最多悔3步棋
				if (blackBackStepCount > 3)
				{
					if (isSound)
					{
						new PlaySoundThread("warning.wav").start();
					}

					JOptionPane.showMessageDialog(this, "黑棋已经悔了3步啦！");

				} else
				{
					// 悔棋
					if (isSound)
					{
						new PlaySoundThread("move.wav").start();
					}

					// 移除最后一步走棋信息
					allChessPositionInfo.remove(allChessPositionInfo.lastElement());
					// 重绘
					repaint();
				}
			} else
			{
				// 白棋悔棋
				whiteBackStepCount++;
				// 最多悔3步棋
				if (whiteBackStepCount > 3)
				{
					if (isSound == true)
					{
						new PlaySoundThread("warning.wav").start();
					}

					JOptionPane.showMessageDialog(null, "白棋已经悔了3步");

				} else
				{
					// 悔棋
					if (isSound == true)
					{
						new PlaySoundThread("move.wav").start();
					}

					// 移除最后一步走棋信息
					allChessPositionInfo.remove(allChessPositionInfo.lastElement());
					// 重绘
					repaint();
				}
			}
		}

	}

	/**
	 * 判断胜利的方法
	 * 
	 * @param x
	 * @param y
	 * @param chessPositionInfo
	 */
	private void checkVictory(int x, int y, Vector chessPositionInfo)
	{
		// 垂直方向棋子数量
		int verticalCount = 0;
		// 水平方向棋子数量
		int horizontalCount = 0;
		// 斜线方向棋子数量1
		int diagonalCount = 0;
		// 斜线方向棋子数量2
		int antiDiagonalCount = 0;

		// 计算水平方向棋子数量
		for (int i = 1; i < 5; i++)
		{
			if (chessPositionInfo.contains((x + i) + "-" + y))
			{
				horizontalCount++;
			} else
			{
				break;
			}
		}
		for (int i = 1; i < 5; i++)
		{
			if (chessPositionInfo.contains((x - i) + "-" + y))
			{
				horizontalCount++;
			} else
			{
				break;
			}
		}

		// 计算垂直方向棋子数量
		for (int i = 1; i < 5; i++)
		{
			if (chessPositionInfo.contains(x + "-" + (y + i)))
			{
				verticalCount++;
			} else
			{
				break;
			}
		}
		for (int i = 1; i < 5; i++)
		{
			if (chessPositionInfo.contains(x + "-" + (y - i)))
			{
				verticalCount++;
			} else
			{
				break;
			}
		}

		// 计算45°斜线方向棋子数量
		for (int i = 1; i < 5; i++)
		{
			if (chessPositionInfo.contains((x + i) + "-" + (y + i)))
			{
				diagonalCount++;
			} else
			{
				break;
			}
		}
		for (int i = 1; i < 5; i++)
		{
			if (chessPositionInfo.contains((x - i) + "-" + (y - i)))
			{
				diagonalCount++;
			} else
			{
				break;
			}
		}

		// 计算135°斜线方向棋子数量
		for (int i = 1; i < 5; i++)
		{
			if (chessPositionInfo.contains((x + i) + "-" + (y - i)))
			{
				antiDiagonalCount++;
			} else
			{
				break;
			}
		}
		for (int i = 1; i < 5; i++)
		{
			if (chessPositionInfo.contains((x - i) + "-" + (y + i)))
			{
				antiDiagonalCount++;
			} else
			{
				break;
			}
		}

		// 判断是否赢了
		if ((horizontalCount >= 4) || (verticalCount >= 4) || (diagonalCount >= 4) || (antiDiagonalCount >= 4))
		{
			if (isSound)
			{
				new PlaySoundThread("win.wav").start();
			}

			// 判断是黑棋赢，还是白棋赢
			if (allChessPositionInfo.size() % 2 == 0)
			{
				JOptionPane.showMessageDialog(this, "黑棋赢了");
			} else
			{
				JOptionPane.showMessageDialog(this, "白棋赢了");
			}

			// 新游戏
			newGame();
		}
	}

	// 鼠标点击事件
	@Override
	public void mouseClicked(MouseEvent e)
	{
		// 判断来源
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			// 左击下棋
			// 获取鼠标点击位置
			int x = e.getX();
			int y = e.getY();
			// 计算精确位置
			x = (x - x % GRID_WIDTH) + ((x % GRID_WIDTH) > (GRID_WIDTH / 2) ? GRID_WIDTH : 0);
			y = (y - y % GRID_WIDTH) + ((y % GRID_WIDTH > (GRID_WIDTH / 2) ? GRID_WIDTH : 0));

			x = (x - px) / GRID_WIDTH;
			y = (y - py) / GRID_WIDTH;

			// 判断棋子是否出界
			if ((x >= 0) && (y >= 0) && (x <= 16) && (y <= 16))
			{
				// 没有出界
				// 判断该位置是否已经有棋子
				if (allChessPositionInfo.contains(x + "-" + y))
				{
					// 该位置已经有棋
					if (isSound)
					{
						new PlaySoundThread("warning.wav").start();
					}

				} else
				{
					// 该位置可下
					if (isSound)
					{
						new PlaySoundThread("merge.wav").start();
					}

					// 添加走棋信息(棋子位置信息)
					allChessPositionInfo.add(x + "-" + y);
					// 重绘
					this.repaint();

					// 判断黑白棋
					if (allChessPositionInfo.size() % 2 == 0)
					{
						blackChessPositionInfo.add(x + "-" + y);
						checkVictory(x, y, blackChessPositionInfo);
					} else
					{
						whiteChessPositionInfo.add(x + "-" + y);
						checkVictory(x, y, whiteChessPositionInfo);
					}

				}
			} else
			{
				// 鼠标点击位置出界
				if (isSound)
				{
					new PlaySoundThread("warning.wav").start();
				}
			}
		} else if (e.getButton() == MouseEvent.BUTTON3)
		{
			// 右击悔棋
			back();
		}
	}

}
