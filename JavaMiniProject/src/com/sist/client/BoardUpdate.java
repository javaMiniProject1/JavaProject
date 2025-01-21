package com.sist.client;
import java.awt.Font;

import javax.swing.*;
import java.awt.event.*;
import com.sist.vo.*;
import com.sist.dao.*;
/*
 * 
 */
public class BoardUpdate extends JPanel
implements ActionListener
{
    JLabel titleLa,nameLa,subLa,contLa,pwdLa,noLa;
    JTextField nameTf,subTf;
    JPasswordField pwdPf;
    JTextArea ta;
    JButton b1,b2;
    ControlPanel cp;
    public BoardUpdate(ControlPanel cp)
    {
    	this.cp=cp;
    	titleLa=new JLabel("수정하기",JLabel.CENTER);
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); 
    	setLayout(null);
    	titleLa.setBounds(10, 15, 830, 50);
    	add(titleLa);
    	
    	noLa=new JLabel();
    	noLa.setBounds(340, 70, 20, 30);
    	add(noLa);
    	noLa.setVisible(false);
    	
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
    	pwdLa.setBounds(50, 475, 70, 30);
    	pwdPf.setBounds(125, 475, 150, 30);
    	add(pwdLa);add(pwdPf);
    	
    	b1=new JButton("수정");
    	b2=new JButton("취소");
    	
    	JPanel p=new JPanel();
    	p.add(b1);p.add(b2);
    	p.setBounds(120, 510, 545, 35);
    	add(p);
    	
    	b1.addActionListener(this);
    	b2.addActionListener(this);
    	
    }
    public void print(ReplyBoardVO vo)
    {
    	nameTf.setText(vo.getName());
    	subTf.setText(vo.getSubject());
    	ta.setText(vo.getContent());
    	noLa.setText(String.valueOf(vo.getNo()));
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b2)// 취소
		{
			cp.card.show(cp,"BDETAIL");
		}
		else if(e.getSource()==b1)
		{
			String name=nameTf.getText();
			if(name.trim().length()<1)
			{
				nameTf.requestFocus();
				return;
			}
			String subject=subTf.getText();
			if(subject.trim().length()<1)
			{
				subTf.requestFocus();
				return;
			}
			
			String content=ta.getText();
			if(content.trim().length()<1)
			{
				ta.requestFocus();
				return;
			}
			
			String pwd=String.valueOf(pwdPf.getPassword());
			if(pwd.trim().length()<1)
			{
				pwdPf.requestFocus();
				return;
			}
			
			String no=noLa.getText();
			
			// no를 전송 
			ReplyBoardVO vo=new ReplyBoardVO();
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);
			vo.setNo(Integer.parseInt(no));
			
			// 오라클 연결 
			ReplyBoardDAO dao=
					ReplyBoardDAO.newInstance();
			boolean bCheck=dao.boardUpdate(vo);
		
			if(bCheck==true)
			{
				cp.card.show(cp, "BDETAIL");
				cp.bDetail.print(2,vo.getNo());
			}
			else
			{
				JOptionPane.showMessageDialog(this, 
						"비밀번호가 틀립니다\n다시 입력하세요");
				pwdPf.setText("");
				pwdPf.requestFocus();
			}
		}
	}
}