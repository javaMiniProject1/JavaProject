package com.sist.client;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.event.*;
import com.sist.commons.ImageChange;
import com.sist.dao.MovieDAO;
import com.sist.vo.*;
import java.util.List;
import java.net.*;
public class HomePenal extends JPanel
implements MouseListener,ActionListener
{
	ControlPanel cp;
	JPanel pan=new JPanel();
	// 이미지 출력 
	JLabel la=new JLabel("0 page / 0 pages");
	JLabel[] imgs=new JLabel[12];
	
	JTable table;
	DefaultTableModel model;
	TableColumn column;
	
	int curpage=1;
	int totalpage=0;
	
	// 데이터베이스 연동 => MovieDAO 
	JLabel titleLa=new JLabel("무비 차트",JLabel.CENTER);
	MovieDAO dao=MovieDAO.newInstance();
    public HomePenal(ControlPanel cp)
    {
    	// JPenal => FlowLayout - - - 
//    	setLayout(new BorderLayout());
    	setLayout(null);
    	
    	this.cp=cp;
    	pan.setLayout(new GridLayout(3,4,3,3));
    	
//    	add("Center",pan);
    	
    	pan.setBounds(20,15,530,550);
    	add(pan);
    	
    	JPanel p=new JPanel();
    	p.add(la); 
    	// add => 코딩 순서로 배치
    	add("South",p);
//    	print();
    	
    	
    	String[] col={"순위","제목"};
    	Object[][] row=new Object[0][2];
    	
//    	model=new DefaultTableModel(row,col);
    	model=new DefaultTableModel(row,col){
    		@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return getValueAt(0, columnIndex).getClass();
			}
    	};
    	
    	table=new JTable(model);
    	table.setRowHeight(31);
    	JScrollPane js=new JScrollPane(table);
    	for(int i=0;i<col.length;i++)
    	{
    		column=table.getColumnModel().getColumn(i);
    		if(i==0)
    			column.setPreferredWidth(30);
    		else if(i==1)
    			column.setPreferredWidth(200);
    	}
    	// 20, 595, 550, 35
    	titleLa.setBounds(560, 15, 250, 45);
    	titleLa.setFont(new Font("맑은 고딕",Font.BOLD,20));
    	add(titleLa);
    	js.setBounds(560,70, 250, 494);
    	add(js);
    	
    	print();
    }
    // 초기화 
    public void init()
    {
    	for(int i=0;i<imgs.length;i++)
    	{
    		imgs[i]=new JLabel("");
    	}
    	pan.removeAll();// 전체 삭제
    	pan.validate();// 재배치
    }
    // 이미지 출력 
    public void print()
    {
    	// 총페이지 읽기 
    	totalpage=dao.MovieTotalPage();
    	List<MovieVO> list=dao.MovieListData(curpage);
    	for(int i=0;i<list.size();i++)
    	{
    		MovieVO vo=list.get(i);
    		try
    		{
    			URL url=new URL(vo.getM_post());
    			Image image=
    				ImageChange.getImage(new ImageIcon(url), 125, 160);
    			imgs[i]=new JLabel(new ImageIcon(image));
    			imgs[i].setToolTipText(vo.getM_title()+"^"+vo.getM_no());
    			pan.add(imgs[i]);
    			// 이벤트 등록 
    			imgs[i].addMouseListener(this);
    		}catch(Exception ex) {}
    	}
//    	la.setText(curpage+" page / "+totalpage+" pages");
    	

    	for(int i=model.getRowCount()-1;i>=0;i--)
    	{
    		model.removeRow(i);
    	}
    	
    	List<MovieVO> tList=dao.movieTop10();
    	for(MovieVO vo:tList)
    	{
    		try
    		{
    			Object[] data= {
    				String.valueOf(vo.getM_no()),
    				vo.getM_title()
    			};
    			model.addRow(data);
    		}catch(Exception ex) {}
    	}
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		for(int i=0;i<imgs.length;i++)
		{
			if(e.getSource()==imgs[i])
			{
				imgs[i].setBorder(new LineBorder(Color.red,3));
			}
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<imgs.length;i++)
		{
			if(e.getSource()==imgs[i])
			{
				imgs[i].setBorder(null);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}