package com.game.ui;

import java.awt.Graphics;

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
