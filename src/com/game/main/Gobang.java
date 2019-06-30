package com.game.main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.game.ui.GameFrame;

/**
 * 五子棋游戏
 * 
 * @author ordinary-student
 *
 */
public class Gobang
{
	public static void main(String[] args)
	{
		try
		{
			// 设置本地系统默认的窗口风格样式
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "加载本地系统窗口样式失败！");
		} finally
		{
			new GameFrame().setVisible(true);
		}
	}

}
