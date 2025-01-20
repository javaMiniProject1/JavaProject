package com.sist.client;
import java.awt.Font;

import javax.swing.*;
import java.awt.event.*;
import com.sist.dao.*;
import com.sist.vo.*;
// 사용자 입력 => 오라클에 저장 => 화면 이동
public class BoardInsert extends JPanel implements ActionListener{
    JLabel titleLa,nameLa,subLa,contLa,pwdLa;
    JTextField nameTf,subTf;
    JPasswordField pwdPf;
    JTextArea ta;
    JButton b1,b2;
    ControlPanel cp;
    public BoardInsert(ControlPanel cp)
    {
    	this.cp=cp;
    	titleLa=new JLabel("글쓰기",JLabel.CENTER);// <table>
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); //<h3></h3>
    	setLayout(null);
    	titleLa.setBounds(10, 15, 830, 50);
    	add(titleLa);
    	
    	nameLa=new JLabel("이름",JLabel.CENTER);
    	nameTf=new JTextField();
    	nameLa.setBounds(50, 70, 70, 30);
    	nameTf.setBounds(125, 70, 150, 30);
    	add(nameLa);add(nameTf);
    	
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
    	//             Top  Right Bottom Left ==> CSS
    	pwdLa.setBounds(50, 475, 70, 30);
    	//             x  y width heigth
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
		if(e.getSource()==b2)
		{
			cp.card.show(cp, "BLIST");// 목록으로 이동
		}
		else if(e.getSource()==b1)
		{
			String name=nameTf.getText();
			// 데이터베이스에 NOT NULL을 설정한 경우 => 반드시 입력 유도
			if(name.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this,"이름을 입력하세요");
				nameTf.requestFocus();
				return;
			}
			String subject=subTf.getText();
			if(subject.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this,"제목을 입력하세요");
				subTf.requestFocus();
				return;
			}
			String content=ta.getText();
			if(content.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this,"내용을 입력하세요");
				ta.requestFocus();
				return;
			}
			String pwd=String.valueOf(pwdPf);
			if(pwd.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this,"비밀번호를 입력하세요");
				pwdPf.requestFocus();
				return;
			}
			ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
			ReplyBoardVO vo=new ReplyBoardVO();
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(content);
			
			dao.boardInsert(vo);
			cp.bList.curpage=1;
			cp.card.show(cp, "BLIST");
			cp.bList.print();
		}
		
	}
}
