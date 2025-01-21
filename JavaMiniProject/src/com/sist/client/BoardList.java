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
    int curpage=1;
    int totalpage=0;
       public BoardList(ControlPanel cp)
    {
        this.cp=cp;
    	inBtn=new JButton("새글");
    	prevBtn=new JButton("이전");
    	nextBtn=new JButton("다음");
    	pageLa=new JLabel("0 page / 0 pages"); 
    	titleLa=new JLabel("게시판",JLabel.CENTER);
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,30)); 
    	
    	String[] col={"번호","","제목","이름","작성일","조회수"};
    	String[][] row=new String[0][6];
    	model=new DefaultTableModel(row,col)
    	{

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    	};
    	table=new JTable(model);
    	JScrollPane js=new JScrollPane(table);
    	for(int i=0;i<col.length;i++)
    	{
    		column=table.getColumnModel().getColumn(i);
    		if(i==0)
    		{
    			column.setPreferredWidth(50);
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
    	table.getColumnModel().removeColumn(table.getColumnModel().getColumn(1));
    	table.getTableHeader().setReorderingAllowed(false);
    	table.setShowVerticalLines(false);
    	table.setRowHeight(30);
    	table.getTableHeader().setBackground(Color.cyan);
    	
    	// 배치 
    	setLayout(null);
    	titleLa.setBounds(5, 15, 830, 50);
    	add(titleLa);
    	inBtn.setBounds(40, 70, 100, 30);
    	add(inBtn);
    	js.setBounds(40, 110, 750, 380);
    	add(js);
    	
    	JPanel p=new JPanel();
    	p.add(prevBtn);
    	p.add(pageLa);
    	p.add(nextBtn);
    	
    	p.setBounds(100, 500, 600, 35);
    	add(p);
    	
    	print();
    	inBtn.addActionListener(this);
    	prevBtn.addActionListener(this);
    	nextBtn.addActionListener(this);
    	
    	table.addMouseListener(this);
    }
       public void print()
       {
       	for(int i=model.getRowCount()-1;i>=0;i--)
       	{
       		model.removeRow(i);
       	}
           List<ReplyBoardVO> list=dao.boardListData(curpage);
           int count=dao.boardRowCount();
           totalpage=(int)(Math.ceil(count/10.0));
           count=count-((curpage*10)-10);
           for(ReplyBoardVO vo:list)
           {
           	if(vo.getGroup_tab()>0)
           	{
           		String s="";
           		for(int i=0;i<vo.getGroup_tab();i++)
           		{
           			s+="&nbsp;&nbsp;";
           		}
           		String subject="<html><body>"+s+"<font color=red>☞</font>"+vo.getSubject()+"</body></html>";
           		String[] data= {
           			String.valueOf(count),
           			String.valueOf(vo.getNo()),
           			subject,
           			vo.getName(),
           			vo.getDbday(),
           			String.valueOf(vo.getHit())
           		};
           		model.addRow(data);
           	}
           	else
           	{
           		String[] data= {
               			String.valueOf(count),
               			String.valueOf(vo.getNo()),
               			vo.getSubject(),
               			vo.getName(),
               			vo.getDbday(),
               			String.valueOf(vo.getHit())
           		};
           		model.addRow(data);
           	}
           	count--;
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
				int row=table.getSelectedRow();
				String no=model.getValueAt(row, 1).toString();
				
				cp.card.show(cp, "BDETAIL");
				cp.bDetail.print(1,Integer.parseInt(no));
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
