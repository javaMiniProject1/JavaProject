package com.sist.client;
import java.awt.*;
import javax.swing.*;
public class Login extends JFrame{
	JLabel la1,la2;
    JTextField tf;
    JPasswordField pf;
    JButton b1,b2;
    public Login()
    {
    	la1=new JLabel("아이디");
		la1.setPreferredSize(new Dimension(280, 16));
		la2=new JLabel("비밀번호");
		la2.setPreferredSize(new Dimension(280, 16));
		
		tf=new JTextField();
		tf.setPreferredSize(new Dimension(280, 35));
		pf=new JPasswordField();
		pf.setPreferredSize(new Dimension(280, 35));
		
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
		
		setLayout(new FlowLayout());
		add(la1); add(tf);
		add(la2); add(pf);
		
		add(b1); add(b2);
		
		setSize(320, 280);
		setVisible(true);
		setLocationRelativeTo(null);
    }
}
