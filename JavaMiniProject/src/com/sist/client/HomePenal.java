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
	JLabel[] imgs=new JLabel[12];
	JPanel[] td=new JPanel[12];
	
	JTable table;
	DefaultTableModel model;
	TableColumn column;
	
	
	// 데이터베이스 연동 => MovieDAO 
	JLabel titleLa=new JLabel("무비 차트",JLabel.CENTER);
	MovieDAO dao=MovieDAO.newInstance();
    public HomePenal(ControlPanel cp)
    {
    	// JPenal => FlowLayout - - - 
    	setLayout(null);
    	
    	this.cp=cp;
    	pan.setLayout(new GridLayout(3,4,3,3));
    	
    	pan.setBounds(20,15,530,550);
    	add(pan);
    	
    	
    	String[] col={"순위","제목"};
    	Object[][] row=new Object[0][2];
    	
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
    	table.getTableHeader().setReorderingAllowed(false);
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
    public void print(){
    	List<MovieVO> list=dao.MovieListData(1);
    	for(int i=0;i<list.size();i++){
    		MovieVO vo=list.get(i);
    		try{
    			URL url=new URL(vo.getM_post());
    			Image image=ImageChange.getImage(new ImageIcon(url), 125, 160);
    			imgs[i]=new JLabel(new ImageIcon(image));
    			imgs[i].setToolTipText(vo.getM_title()+" |"+vo.getM_no());
    			JLabel label=new JLabel(String.valueOf(i+1), JLabel.CENTER);
    			td[i]=new JPanel(null);
    			imgs[i].setBounds(td[i].getX(), td[i].getY(), 125, 175);
    			label.setOpaque(true);
    			
    			if(i<3) {
    				label.setBounds(td[i].getX(), td[i].getY()+145, 30, 30);
    				label.setBackground(Color.red);
        			label.setFont(new Font("Ariel",Font.BOLD,16));
        			label.setForeground(Color.white);
    			}else {
    				label.setBounds(td[i].getX(), td[i].getY()+149, 26, 26);
    				label.setBackground(new Color(45,45,45));
        			label.setFont(new Font("Ariel",Font.PLAIN,14));
        			label.setForeground(Color.white);
    			}
    			
//    			관람가
    			String grade=vo.getGrade();
    			JLabel g=new JLabel("", JLabel.CENTER);
    			g.setOpaque(true);
    			g.setBounds(td[i].getX()+100, td[i].getY()+5, 20, 20);
    			g.setFont(new Font("Ariel",Font.BOLD,10));
    			g.setForeground(Color.white);
    			
    			if(grade.contains("전체")) {
    				grade="ALL";
    				g.setBackground(new Color(33,159,40));
    			}else if(grade.contains("12")) {
    				grade="12";
    				g.setBackground(new Color(252,207,7));
    			}else if(grade.contains("15")) {
    				grade="15";
    				g.setBackground(new Color(242,122,2));
    			}else if(grade.contains("청소년")) {
    				grade="19";
    				g.setBackground(new Color(223,1,1));
    			}

				g.setText(grade);
    			
    			td[i].add(label);
    			td[i].add(g);
    			td[i].add(imgs[i]);
    			pan.add(td[i]);
    			// 이벤트 등록 
    			imgs[i].addMouseListener(this);
    			td[i].addMouseListener(this);
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
		for(int i=0;i<imgs.length;i++)
		{
			if(e.getSource()==imgs[i])
			{
				if(e.getClickCount()==2)
				{
					String mno=imgs[i].getToolTipText();
					mno=mno.substring(mno.lastIndexOf("|")+1);
					MovieVO vo=dao.MovieDetailData(Integer.parseInt(mno));
					cp.mdp.detailPrint(1, vo);
					cp.card.show(cp, "DETAIL");
				}
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