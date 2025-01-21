package com.sist.client;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
public class Login extends JFrame implements FocusListener{
	JLabel la1,la2;
    JTextField tf;
    JPasswordField pf;
    JButton b1,b2;
    JLabel msg;
    public Login(){
    	la1=new JLabel("<html><body><p style=padding-top:10px;>아이디</p></body></html>");
		la1.setPreferredSize(new Dimension(280, 30));
		la2=new JLabel("비밀번호");
		la2.setPreferredSize(new Dimension(280, 16));
		
		tf=new JTextField();
		tf.setPreferredSize(new Dimension(280, 45));
		pf=new JPasswordField();
		pf.setPreferredSize(new Dimension(280, 45));
		msg=new JLabel("");
		msg.setFont(new Font("msg",Font.PLAIN,12));
		msg.setForeground(Color.red);
		msg.setPreferredSize(new Dimension(280,20));
		
		b1=new JButton("로그인");
		b1.setPreferredSize(new Dimension(280, 45));
		b2=new JButton("종료");
		b2.setPreferredSize(new Dimension(280, 45));
		
		Font btnfont = new Font("btnfont", Font.BOLD, 16);
		b1.setFont(btnfont);
		b2.setFont(btnfont);
		b1.setBackground(new Color(55,161,54));
		b2.setBackground(new Color(173,173,173));
		b1.setForeground(Color.WHITE);
		b2.setForeground(Color.WHITE);
		JPanel btnPanel=new JPanel();
		btnPanel.setLayout(new GridLayout(2,1,0,5));
		btnPanel.add(b1);
		btnPanel.add(b2);
		
		setLayout(new FlowLayout());
		add(la1); add(tf);
		add(la2); add(pf);
		
		add(msg);
		add(btnPanel);
		
		setTitle("영화 차트 로그인");
		setSize(320, 340);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		tf.addFocusListener(this);
		pf.addFocusListener(this);
    }
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==tf) {
			tf.setBorder(new LineBorder(new Color(55,161,54),2));
		}else if(e.getSource()==pf) {
			pf.setBorder(new LineBorder(new Color(55,161,54),2));
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource()==tf) {
			tf.setBorder(new LineBorder(new Color(150,150,150),1));
		}else if(e.getSource()==pf) {
			pf.setBorder(new LineBorder(new Color(150,150,150),1));
		}
	}
}
