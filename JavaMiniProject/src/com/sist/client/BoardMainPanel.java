package com.sist.client;

import javax.swing.*;

import com.sist.dao.*;
import com.sist.vo.*;
import java.util.Date;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class BoardMainPanel extends JPanel implements ActionListener,MouseListener 
{
	CardLayout card=new CardLayout();
    BoardList bList=new BoardList();
    BoardDetail bDetail=new BoardDetail();
    BoardInsert bInsert=new BoardInsert();
    BoardUpdate bUpdate=new BoardUpdate();
    BoardDelete bDelete=new BoardDelete();
    // 게시판 관리자 
    // 변수 설정 
    int curpage=1;
    int totalpage=0;
    ControlPanel cp;
    public BoardMainPanel(ControlPanel cp)
    {
    	this.cp=cp;
    	setLayout(card);
    	
    	
    	
    	// 카드추가 
    	add("LIST",bList);
    	add("DETAIL",bDetail);
    	add("INSERT",bInsert);
    	add("UPDATE",bUpdate);
    	add("DELETE",bDelete);
    	
    	listPrint();
    	setBounds(510,330,280, 35);
    	JPanel p = new JPanel();
    	p.setBounds(510,330,280, 35);
    	add(p);
    	// 이벤트 등록 
    	bList.inBtn.addActionListener(this);// 글쓰기 이동버튼
    	bInsert.b1.addActionListener(this);// 글쓰기
    	bInsert.b2.addActionListener(this);// 취소 
    	
    	// 이전
    	bList.prevBtn.addActionListener(this);
    	// 다음
    	bList.nextBtn.addActionListener(this);
    	// table
    	bList.table.addMouseListener(this);
    	
    	// 목록 
    	bDetail.b3.addActionListener(this);
    	// 수정 
    	bDetail.b1.addActionListener(this);
    	// 삭제
    	bDetail.b2.addActionListener(this);
    	
    	// 실제 삭제
    	bDelete.b1.addActionListener(this);// 삭제
    	bDelete.b2.addActionListener(this);// 취소
    	
    	//수정
    	bUpdate.b1.addActionListener(this);//수정
    	bUpdate.b2.addActionListener(this);//취소
    }
    public void listPrint()
    {
    	// 테이블 전체 데이터 지우기 
    	for(int i=bList.model.getRowCount()-1;i>=0;i--)
    	{
    		bList.model.removeRow(i);
    	}
    	// JLabel => init => JLabel 초기화 
    	FreeBoardDAO dao=FreeBoardDAO.newInstance();
    	List<FreeBoardVO> list=dao.boardListData(curpage);
    	
    	for(FreeBoardVO vo:list)
    	{
    		String[] data= {
    			String.valueOf(vo.getNo()),
    			vo.getSubject(),
    			vo.getName(),
    			vo.getRegdate().toString(),
    			String.valueOf(vo.getHit())
    		};
    		
    		bList.model.addRow(data);
    	}
    	// 목록 출력 
    	totalpage=dao.boardTotalPage();
    	bList.pageLa.setText(curpage+" page / "+totalpage+" pages");
    }
    public static void main(String[] args) {
	//	new BoardMainPanel("BOARD");
	}
    
    public void detailPrint(int no)
    {
    	FreeBoardDAO dao = FreeBoardDAO.newInstance();
		FreeBoardVO vo = dao.boardDetailData(no);
    	bDetail.no.setText(String.valueOf(vo.getNo()));
    	bDetail.name.setText(vo.getName());
    	bDetail.day.setText(vo.getRegdate().toString());//문자열로 받아야해
    	bDetail.hit.setText(String.valueOf(vo.getHit()));
    	bDetail.sub.setText(vo.getSubject());
    	bDetail.ta.setText(vo.getContent());
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// 이전
		if(e.getSource()==bList.prevBtn)
		{
			if(curpage>1) // 현재페이지 
			{
				curpage--;
				listPrint();
			}
		}
		// 다음
		else if(e.getSource()==bList.nextBtn)
		{
			if(curpage<totalpage)
			{
				curpage++;
				listPrint();
			}
		}
		// 새글 추가 창 
		else if(e.getSource()==bList.inBtn)
		{
			card.show(this, "INSERT");
			bInsert.nameTf.setText("");
			bInsert.subTf.setText("");
			bInsert.ta.setText("");
			bInsert.pwdPf.setText("");
			bInsert.nameTf.requestFocus();
			// card => 단점은 윈도우가 종료하지 않는다 
			// => 입력된 내용을 그대로 유지
		}
		// 글쓰기 등록
		else if(e.getSource()==bInsert.b1)
		{
			// 입력한 데이터 읽기 
			// 유효성 검사 => NOT NULL 속성을 가지고 있는 경우
			// 웹 => 자바스크립트 
			String name=bInsert.nameTf.getText();
			if(name.trim().length()<1)
			{
				// 이름이 입력이 안된 상태 
				JOptionPane.showMessageDialog(this, 
						"이름을 입력하세요");
				// alert()
				bInsert.nameTf.requestFocus();
				// name.focus()
				return;
			}
			
			String subject=bInsert.subTf.getText();
			if(subject.trim().length()<1)
			{
				// 이름이 입력이 안된 상태 
				JOptionPane.showMessageDialog(this, 
						"제목을 입력하세요");
				// alert()
				bInsert.subTf.requestFocus();
				// name.focus()
				return;
			}
			
			String content=bInsert.ta.getText();
			if(content.trim().length()<1)
			{
				// 이름이 입력이 안된 상태 
				JOptionPane.showMessageDialog(this, 
						"내용을 입력하세요");
				// alert()
				bInsert.ta.requestFocus();
				// name.focus()
				return;
			}
			
			String pwd=String.valueOf(bInsert.pwdPf.getPassword());
			if(pwd.trim().length()<1)
			{
				// 이름이 입력이 안된 상태 
				JOptionPane.showMessageDialog(this, 
						"비밀번호를 입력하세요");
				// alert()
				bInsert.pwdPf.requestFocus();
				// name.focus()
				return;
			}
			
			// => 유효성 : NOT NULL / CHECK / PRIMARY KEY(id)
			
			// 오라클 연동 
			FreeBoardDAO dao=FreeBoardDAO.newInstance();
			FreeBoardVO vo=new FreeBoardVO();
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);
			
			dao.boardInsert(vo);
			
			card.show(this, "LIST");
			listPrint();
			
		}
		// 글쓰기 취소
		else if(e.getSource()==bInsert.b2)
		{
			// javascript:history.back()
			card.show(this, "LIST");
			// 화면 이동 getContentPane():Panel관리자 
			// => 이동시에 card이름 부여 
		}
		//상세보기 -> 목록이동
		else if(e.getSource()==bDetail.b3)
		{
			card.show(this, "LIST");
			listPrint();
		}
		//상세보기->수정
		else if(e.getSource()==bDetail.b1)
		{
			//화면이동
			card.show(this, "UPDATE");	
			//처리 -> 수정하기를 누르면 이전 입력값을 가지고 들어와야해(비밀번호 제외)
			FreeBoardDAO dao= FreeBoardDAO.newInstance();
			String no=bDetail.no.getText();
			//윈도우나 웹은 정수개념이 없고 다 문자열로 가지고 오기 때문에
			//정수를 사용하려면 Integer로 바꿔줘야한디
			FreeBoardVO vo = dao.boaredUpdateData(Integer.parseInt(no));
			bUpdate.nameTf.setText(vo.getName());
			bUpdate.subTf.setText(vo.getSubject());
			bUpdate.ta.setText(vo.getContent());
			bUpdate.pwdPf.setText("");
			
		}
		else if(e.getSource()==bUpdate.b1)
		{
			//db처리
			String name=bUpdate.nameTf.getText();
			if(name.trim().length()<1)
			{
				// 이름이 입력이 안된 상태 
				JOptionPane.showMessageDialog(this, 
						"이름을 입력하세요");
				// alert()
				bUpdate.nameTf.requestFocus();
				// name.focus()
				return;
			}
			
			String subject=bUpdate.subTf.getText();
			if(subject.trim().length()<1)
			{
				// 이름이 입력이 안된 상태 
				JOptionPane.showMessageDialog(this, 
						"제목을 입력하세요");
				// alert()
				bUpdate.subTf.requestFocus();
				// name.focus()
				return;
			}
			
			String content=bUpdate.ta.getText();
			if(content.trim().length()<1)
			{
				// 이름이 입력이 안된 상태 
				JOptionPane.showMessageDialog(this, 
						"내용을 입력하세요");
				// alert()
				bUpdate.ta.requestFocus();
				// name.focus()
				return;
			}
			
			String pwd=String.valueOf(bUpdate.pwdPf.getPassword());
			if(pwd.trim().length()<1)
			{
				// 이름이 입력이 안된 상태 
				JOptionPane.showMessageDialog(this, 
						"비밀번호를 입력하세요");
				// alert()
				bUpdate.pwdPf.requestFocus();
				// name.focus()
				return;
			}
			
			String no=bDetail.no.getText();
			
			FreeBoardVO vo = new FreeBoardVO();
			vo.setNo(Integer.parseInt(no));
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);
			
			FreeBoardDAO dao = FreeBoardDAO.newInstance();
			boolean bCheck = dao.boardUpdate(vo);
			
			if(bCheck==true)
			{
				card.show(this, "DETAIL");
				detailPrint(Integer.parseInt(no));
			}
			else
			{
				JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다");
				bUpdate.pwdPf.setText("");
				bUpdate.pwdPf.requestFocus();
			}
			
		}
		else if(e.getSource()==bUpdate.b2)
		{
			card.show(this, "DETAIL");
		}
		
		//삭제
		else if(e.getSource()==bDetail.b2)
		{
			card.show(this, "DELETE");
			bDelete.pf.setText("");
			bDelete.pf.requestFocus();
		}
		else if(e.getSource()==bDelete.b1)
		{
			String no=bDetail.no.getText();
			String pwd=String.valueOf(bDelete.pf.getPassword());
			FreeBoardDAO dao = FreeBoardDAO.newInstance();
			boolean bCheck = dao.boardDelete(Integer.parseInt(no), pwd);
			if(bCheck==true)
			{
				card.show(this, "LIST");
				listPrint();
			}
			else
			{
				JOptionPane.showMessageDialog(this, "비밀번호가 틀립니다");
				bDelete.pf.setText("");
				bDelete.pf.requestFocus();
			}
		}
		else if(e.getSource()==bDelete.b2)
		{
			card.show(this, "bDetail");
		}
	}
	// onMouseDown
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==bList.table)
		{
			if(e.getClickCount()==2)
			{
				int row=bList.table.getSelectedRow();
				String no=bList.model.getValueAt(row, 0).toString();
				//게시물 번호를 읽어온다
				card.show(this, "DETAIL");
				detailPrint(Integer.parseInt(no));
				
			}
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	// onMouseUp
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	// onMouseOver
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	// onMouseOut
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}