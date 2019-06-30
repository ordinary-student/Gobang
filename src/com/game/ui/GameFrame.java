package com.game.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
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

	// 所有的每一步走棋的信息
	private Vector v = new Vector();
	// 白方走棋信息
	private Vector white = new Vector();
	// 黑方走棋信息
	private Vector black = new Vector();

	// 用来判断白棋还是黑棋
	private boolean b;
	// 计算悔棋步数
	private int whiteCount, blackCount;
	// 间距大小
	private int w = 25;
	// 棋盘的大小
	private int px = 100, py = 100;

	private int pxw = px + w, pyw = py + w;
	private int width = w * 16, height = w * 16;
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
		// 绘制网格
		g.drawRect(px, py, width, height);
		// 画提示信息
		g.drawString("单机版五子棋小游戏，右击可以悔棋，欢迎来玩！", 180, 70);
		g.drawString(v.size() + "步棋", 250, 550);

		// 绘制每条横线和竖线
		for (int i = 0; i < 15; i++)
		{
			g.drawLine(pxw + i * w, py, pxw + i * w, hline);
			g.drawLine(px, pyw + i * w, vline, pyw + i * w);
		}

		// 绘制棋子
		for (int x = 0; x < v.size(); x++)
		{
			String str = (String) v.get(x);
			String tmp[] = str.split("-");

			int a = Integer.parseInt(tmp[0]);
			int b = Integer.parseInt(tmp[1]);

			a = a * w + px;
			b = b * w + py;

			if (x % 2 == 0)
			{
				g.setColor(Color.WHITE);
			} else
			{
				g.setColor(Color.BLACK);
			}

			// 绘制棋子
			g.fillArc(a - w / 2, b - w / 2, w, w, 0, 360);
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
		v.clear();
		black.clear();
		white.clear();
		// 重绘
		repaint();
		// 悔棋次数清零
		whiteCount = 0;
		blackCount = 0;
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
		if (v.isEmpty())
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
			if (v.size() % 2 == 0)
			{
				// 黑棋悔棋
				blackCount++;
				// 最多悔3步棋
				if (blackCount > 3)
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
					v.remove(v.lastElement());
					// 重绘
					repaint();
				}
			} else
			{
				// 白棋悔棋
				whiteCount++;
				// 最多悔3步棋
				if (whiteCount > 3)
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
					v.remove(v.lastElement());
					// 重绘
					repaint();
				}
			}
		}

	}

}
