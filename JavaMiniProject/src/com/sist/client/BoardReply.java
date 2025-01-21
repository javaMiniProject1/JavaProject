package com.sist.client;
import java.awt.Font;

import javax.swing.*;
import java.awt.event.*;
import com.sist.dao.*;
import com.sist.vo.*;
// 사용자 입력 => 오라클에 저장 => 화면 이동
public class BoardReply extends JPanel implements ActionListener{
    JLabel titleLa,nameLa,subLa,contLa,pwdLa,noLa;
    JTextField nameTf,subTf;
    JPasswordField pwdPf;
    JTextArea ta;
    JButton b1,b2;
    ControlPanel cp;
    public BoardReply(ControlPanel cp)
    {
    	this.cp=cp;
    	titleLa=new JLabel("답변하기",JLabel.CENTER);
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30));
    	setLayout(null);
    	titleLa.setBounds(10, 15, 830, 50);
    	add(titleLa);
    	
    	nameLa=new JLabel("이름",JLabel.CENTER);
    	nameTf=new JTextField();
    	nameLa.setBounds(50, 70, 70, 30);
    	nameTf.setBounds(125, 70, 150, 30);
    	add(nameLa);add(nameTf);
    	
    	noLa = new JLabel();
		noLa.setBounds(340, 70, 50, 30);
		add(noLa);
		noLa.setVisible(false);
		
    	subLa=new JLabel("제목",JLabel.CENTER);
    	subTf=new JTextField();
    	subLa.setBounds(50, 105, 70, 30);
    	subTf.setBounds(125, 105, 450, 30);
    	add(subLa);add(subTf);
    	
    	
    	contLa=new JLabel("내용",JLabel.CENTER);
    	ta=new JTextArea();
    	JScrollPane js=new JScrollPane(ta);
    	contLa.setBounds(50, 140, 70, 30);
    	js.setBounds(125, 140, 600, 330);
    	add(contLa);add(js);
 
    	pwdLa=new JLabel("비밀번호",JLabel.CENTER);
    	pwdPf=new JPasswordField();
    	pwdLa.setBounds(50, 475, 70, 30);
    	pwdPf.setBounds(125, 475, 150, 30);
    	add(pwdLa);add(pwdPf);
    	
    	b1=new JButton("글쓰기");
    	b2=new JButton("취소");
    	
    	JPanel p=new JPanel();
    	p.add(b1);p.add(b2);
    	p.setBounds(120, 510, 545, 35);
    	add(p);
    	
    	b1.addActionListener(this);
    	b2.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == b1) {
			String name = nameTf.getText();
			if (name.trim().length() < 1) 
			{
				nameTf.requestFocus();
				return;
			}
			String subject = subTf.getText();
			if (subject.trim().length() < 1) 
			{
				subTf.requestFocus();
				return;
			}

			String content = ta.getText();
			if (content.trim().length() < 1) 
			{
				ta.requestFocus();
				return;
			}

			String pwd = String.valueOf(pwdPf.getPassword());
			if (pwd.trim().length() < 1) 
			{
				pwdPf.requestFocus();
				return;
			}

			String no = noLa.getText();

			ReplyBoardVO vo = new ReplyBoardVO();
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);

			ReplyBoardDAO dao = ReplyBoardDAO.newInstance();
			dao.replyInsert(Integer.parseInt(no), vo);
			cp.card.show(cp, "BLIST");
			cp.bList.print();
		} else if (e.getSource()==b2) 
		{
			cp.card.show(cp, "BDETAIL");
		}
	
	}
}