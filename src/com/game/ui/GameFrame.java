package com.game.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;

import com.game.utils.WindowUtil;

/**
 * 五子棋小游戏
 * 
 * @author ordinary-student
 *
 */
public class GameFrame extends KFrame
{
	private static final long serialVersionUID = -3578214145718737571L;

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
		setSize(400, 500);
		// 不可改变大小
		setResizable(false);
		// 设置居中
		WindowUtil.center(this);
		// 设置布局
		setLayout(null);
		// 设置关闭方式
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 创建菜单栏
		MenuBar mb = new MenuBar();
		mb.setFont(new Font("粗体", Font.CENTER_BASELINE, 14));
		// 创建菜单
		Menu m1 = new Menu("开始");
		Menu m2 = new Menu("帮助");
		// m1.setFont(new Font("仿宋", Font.CENTER_BASELINE, 14));
		// m2.setFont(new Font("仿宋", Font.CENTER_BASELINE, 14));
		// 创建菜单项
		final MenuItem mi1 = new MenuItem("新游戏");
		final MenuItem mi2 = new MenuItem("退出游戏");
		final MenuItem mi3 = new MenuItem("悔棋");
		final MenuItem mi4 = new MenuItem("静音");

		m1.add(mi1);
		m1.add(mi2);
		m2.add(mi3);
		m2.add(mi4);
		mb.add(m1);
		mb.add(m2);
		// 设置菜单栏
		this.setMenuBar(mb);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭按钮
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

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

}
