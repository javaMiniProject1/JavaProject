package com.sist.client;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

import com.sist.dao.ReplyBoardDAO;
import com.sist.vo.ReplyBoardVO;
import java.awt.event.*;
public class BoardDetail extends JPanel
implements ActionListener
{
     JLabel titleLa,nameLa,noLa,subLa,dayLa,hitLa;
     JLabel name,no,sub,day,hit;
     JTextArea ta;
     JButton b1,b2,b3,b4; // 수정 / 삭제 / 목록
     ControlPanel cp;
     public  BoardDetail(ControlPanel cp)
     {
    	 this.cp=cp;
    	 titleLa=new JLabel("내용보기",JLabel.CENTER);
     	 titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30));
     	 
     	 setLayout(null);
    	 titleLa.setBounds(10, 15, 830, 50);
    	 add(titleLa);
    	 
    	 noLa=new JLabel("번호",JLabel.CENTER);
    	 noLa.setBounds(50, 75 , 110, 30);
    	 noLa.setBackground(Color.GREEN);
     	 noLa.setOpaque(true);
    	 no=new JLabel("",JLabel.CENTER);
    	 no.setBounds(165, 75, 250, 30);
    	 add(noLa);add(no);
    	 
    	 dayLa=new JLabel("작성일",JLabel.CENTER);
    	 dayLa.setBounds(420, 75 , 110, 30);
    	 dayLa.setBackground(Color.GREEN);
     	 dayLa.setOpaque(true);
    	 day=new JLabel("",JLabel.CENTER);
    	 day.setBounds(535, 75, 300, 30);
    	 add(dayLa);add(day);
    	 
    	 nameLa=new JLabel("이름",JLabel.CENTER);
    	 nameLa.setBounds(50, 110, 110, 30);
    	 nameLa.setBackground(Color.GREEN);
     	 nameLa.setOpaque(true);
    	 name=new JLabel("",JLabel.CENTER);
    	 name.setBounds(165, 110, 250, 30);
    	 add(nameLa);add(name);
    	 
    	 hitLa=new JLabel("조회수",JLabel.CENTER);
    	 hitLa.setBounds(420, 110, 110, 30);
    	 hitLa.setBackground(Color.GREEN);
     	 hitLa.setOpaque(true);
    	 hit=new JLabel("",JLabel.CENTER);
    	 hit.setBounds(535, 110, 300, 30);
    	 add(hitLa);add(hit);
    	 
    	 subLa=new JLabel("제목",JLabel.CENTER);
    	 subLa.setBounds(50, 145 , 110, 30);
    	 subLa.setBackground(Color.GREEN);
     	 subLa.setOpaque(true);
    	 sub=new JLabel("");
    	 sub.setBounds(165, 145, 580, 30);
    	 add(subLa);add(sub);
    	 
    	 ta=new JTextArea();
    	 ta.setEditable(false);
    	 ta.setBounds(50, 180, 705, 320);
    	 add(ta);
    	 
    	 JPanel p=new JPanel();
    	 b1=new JButton("수정");
    	 b2=new JButton("삭제");
    	 b3=new JButton("목록");
    	 b4=new JButton("답변");
    	 p.add(b4);p.add(b1);p.add(b2);p.add(b3);
    	 p.setBounds(170, 510, 485, 35);
    	 add(p);
    	 
    	 b1.addActionListener(this);
    	 b2.addActionListener(this);
    	 b3.addActionListener(this);
    	 b4.addActionListener(this);
     }
     public void print(int type,int n)
     {
    	 //데이터베이스 연동
    	 ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
    	 ReplyBoardVO vo=dao.boardDetailData(type,n);
    	 name.setText(vo.getName());
    	 no.setText(String.valueOf(vo.getNo()));
    	 day.setText(vo.getDbday());
    	 hit.setText(String.valueOf(vo.getHit()));
    	 sub.setText(vo.getSubject());
    	 ta.setText(vo.getContent());
    	 
     }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b3)
		{
			cp.card.show(cp,"BLIST");
			cp.bList.print();
		}
		else if(e.getSource()==b1)// 수정
		{
			cp.bUpdate.pwdPf.setText("");
			String strNo=no.getText();
			ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
			ReplyBoardVO vo=dao.boardUpdateData(Integer.parseInt(strNo));
			cp.card.show(cp,"BUPDATE");
			cp.bUpdate.print(vo);
		}
		else if(e.getSource()==b2)// 삭제
		{
			String strNo=no.getText();
			cp.bDelete.pf.setText("");
			cp.card.show(cp, "BDELETE");
			cp.bDelete.noLa.setText(strNo);
			cp.bDelete.pf.requestFocus();
		}
		else if(e.getSource()==b4)// 답변
		{
			String strNo=no.getText();
			cp.bReply.nameTf.setText("");
			cp.bReply.subTf.setText("");
			cp.bReply.ta.setText("");
			cp.bReply.pwdPf.setText("");
			cp.card.show(cp, "BREPLY");
			cp.bReply.noLa.setText(strNo);
		}
	}
}