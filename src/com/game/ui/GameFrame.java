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

	// 所有的每步走棋信息
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

	// 重写窗体
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

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

		} else if (e.getSource() == newGameItem)
		{
		}
	}

	/**
	 * 新游戏
	 */
	private void newGame()
	{
		//
		v.clear();
		//
		black.clear();
		white.clear();
		// 重绘
		repaint();
		//
		whiteCount = 0;
		blackCount = 0;
	}

	private void exit()
	{
		// TODO 自动生成的方法存根

	}

	private void back()
	{
		// TODO 自动生成的方法存根

	}

}
