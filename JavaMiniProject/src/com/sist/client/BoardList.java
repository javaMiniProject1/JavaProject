package com.sist.client;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import com.sist.dao.*;
import com.sist.vo.*;
import java.util.*;
import java.util.List;
public class BoardList extends JPanel implements ActionListener,MouseListener{
    JButton inBtn,prevBtn,nextBtn;
    JLabel pageLa,titleLa;
    JTable table;
    DefaultTableModel model;
    TableColumn column;
    ControlPanel cp;
    ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
    int curpage=1;// 현재 페이지(보여지는 페이지)
    int totalpage=0;// 총페이지
       public BoardList(ControlPanel cp)
    {
        this.cp=cp;// 화면 자체 이동
    	inBtn=new JButton("새글");//<input type=button value="새글">
    	prevBtn=new JButton("이전");
    	nextBtn=new JButton("다음");
    	pageLa=new JLabel("0 page / 0 pages"); //<label>0 page / 0 pages</label>
    	titleLa=new JLabel("게시판",JLabel.CENTER);// <table>
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); //<h3></h3>
    	
    	String[] col={"번호","제목","이름","작성일","조회수"};//<tr><th></th>....</tr>
    	String[][] row=new String[0][5];
    	// 한줄에 5개 데이터를 첨부 
    	model=new DefaultTableModel(row,col) // 데이터 관리
    	{

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    		 // 익명의 클래스 => 포함 클래스 => 상속없이 오버라이딩 => 클릭 => 편집기 => 편집방지 
    		 
    	};
    	table=new JTable(model); // 테이블 모양 관리 
    	JScrollPane js=new JScrollPane(table);
    	for(int i=0;i<col.length;i++)
    	{
    		column=table.getColumnModel().getColumn(i);
    		if(i==0)
    		{
    			column.setPreferredWidth(50);
    		}
    		else if(i==1)
    		{
    			column.setPreferredWidth(350);
    		}
    		else if(i==2)
    		{
    			column.setPreferredWidth(100);
    		}
    		else if(i==3)
    		{
    			column.setPreferredWidth(150);
    		}
    		else if(i==4)
    		{
    			column.setPreferredWidth(50);
    		}
    	}
    	table.getTableHeader().setReorderingAllowed(false);
    	table.setShowVerticalLines(false);
    	table.setRowHeight(30);
    	table.getTableHeader().setBackground(Color.cyan);
    	
    	// 배치 
    	setLayout(null);
    	titleLa.setBounds(10, 15, 830, 50);
    	add(titleLa);
    	inBtn.setBounds(100, 70, 100, 30);
    	add(inBtn);
    	js.setBounds(100, 110, 600, 330);
    	add(js);
    	
    	JPanel p=new JPanel();
    	p.add(prevBtn);
    	p.add(pageLa);
    	p.add(nextBtn);
    	
    	p.setBounds(100, 450, 600, 35);
    	add(p);
    	
    	print();
    	inBtn.addActionListener(this);// 새글
    	prevBtn.addActionListener(this);// 이전
    	nextBtn.addActionListener(this);// 다음
    	
    	// 상세보기
    	table.addMouseListener(this);
    }
    public void print()
    {
    	// 테이블은 반드시 한 번 리셋해야 함 => 안 그러면 아래에 계속 붙여짐
    	for(int i=model.getRowCount()-1;i>=0;i--)
    	{
    		model.removeRow(i);
    	}
    	// 데이터 받기
        List<ReplyBoardVO> list=dao.boardListData(curpage);
        totalpage=dao.boardTotalPage();
        
        // 출력 => 테이블
        for(ReplyBoardVO vo:list)
        {
        	if(vo.getGroup_tab()>0)// 답변이라면
        	{
        		String s="";
        		for(int i=0;i<vo.getGroup_tab();i++)
        		{
        			s+="&nbsp;&nbsp;";
        			//&nbsp; => 공백처리 == " "
        		}
        		String subject="<html><body>"+s+"<font color=red>☞</font>"+vo.getSubject()+"</body></html>";
        		String[] data= {
        			String.valueOf(vo.getNo()),
        			subject,
        			vo.getName(),
        			vo.getDbday(),
        			String.valueOf(vo.getHit())
        		};
        		model.addRow(data);
        	}
        	else// 답변이 아니라면 => 새글
        	{
        		String[] data= {
            			String.valueOf(vo.getNo()),
            			vo.getSubject(),
            			vo.getName(),
            			vo.getDbday(),
            			String.valueOf(vo.getHit())
        		};
        		model.addRow(data);
        	}
        }
        pageLa.setText(curpage+" page / "+totalpage+" pages");
        
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==inBtn)
		{
			cp.bInsert.nameTf.setText("");
			cp.bInsert.subTf.setText("");
			cp.bInsert.ta.setText("");
			cp.bInsert.pwdPf.setText("");
			cp.card.show(cp, "BINSERT");
			cp.bInsert.nameTf.requestFocus();
		}
		else if(e.getSource()==prevBtn)
		{
			if(curpage>1)
			{
				curpage--;
				print();
			}
		}
		else if(e.getSource()==nextBtn)
		{
			if(curpage<totalpage)
			{
				curpage++;
				print();
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table)
		{
			if(e.getClickCount()==2)
			{
				// 클릭 위치 확인 => 게시물 번호 읽기
				int row=table.getSelectedRow();
				String no=model.getValueAt(row, 0).toString();
				
				cp.card.show(cp, "BDETAIL");
				cp.bDetail.print(1,Integer.parseInt(no));// 조회수 증가 o => type1
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
