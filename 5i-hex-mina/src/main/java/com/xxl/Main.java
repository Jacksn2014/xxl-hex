package com.xxl;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.xxl.server.MsgServer;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {
	
	public JButton startBtn;
	public JButton stopBtn;
	public JButton exitBtn;
	
	public Main() {
		
		// 界面元素
		startBtn = new JButton("启动");
		startBtn.addActionListener(this);
		stopBtn = new JButton("停止");
		stopBtn.addActionListener(this);
		exitBtn = new JButton("退出");
		exitBtn.addActionListener(this);
		
		this.setLayout(new FlowLayout());
		this.add(startBtn);
		this.add(stopBtn);
		this.add(exitBtn);
		
		// 主界面
		this.setTitle("服务器");
		this.setSize(250, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startBtn) {
			MsgServer.getInstance().start();
			this.getContentPane().setBackground(Color.GREEN);
		} else if (e.getSource() == stopBtn) {
			MsgServer.getInstance().stop();
			this.getContentPane().setBackground(Color.GRAY);
		} else if (e.getSource() == exitBtn) {
			System.exit(0);
		}
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
