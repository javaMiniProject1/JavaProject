package com.sist.client;
// 상세 보기 
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.*;

import com.sist.commons.ImageChange;
import com.sist.dao.*;
import com.sist.vo.*;

public class MovieDetailPenal extends JPanel
implements ActionListener
{
	 JLabel poster;
     JLabel titleLa,nationLa,genreLa,runtimeLa,reg_dateLa,total_auditLa,dirLa,actLa,raitingLa,gradeLa;
     JLabel title,nation,genre,runtime,reg_date,total_audit,dir,act,raiting,grade;
     JLabel[] images=new JLabel[6];
     JTextPane storyTa;
     JButton b1,b2,b3;
     int mode=0;
     ControlPanel cp;
     String[] link={"","HOME","MOVIE","MFP"};
     public MovieDetailPenal(ControlPanel cp)
     {
    	 this.cp=cp;
    	 setLayout(null);
    	 
    	 poster=new JLabel("");
    	 poster.setBounds(20, 20, 300, 500);
    	 add(poster);
    	 
    	 titleLa=new JLabel("영화 제목");
    	 title=new JLabel();
    	 
    	 titleLa.setBounds(330, 20, 80, 30);
    	 title.setBounds(415, 20, 250, 30);
    	 add(titleLa);add(title);
    	 
    	 nationLa=new JLabel("나라");
    	 nation=new JLabel();
    	 nationLa.setBounds(330, 55, 80, 30);
    	 nation.setBounds(415, 55, 250, 30);
    	 add(nationLa);add(nation);
    	 
    	 runtimeLa=new JLabel("상영 시간");
    	 runtime=new JLabel();
    	 
    	 runtimeLa.setBounds(330, 90, 80, 30);
    	 runtime.setBounds(415, 90, 350, 30);
    	 add(runtimeLa);add(runtime);
    	 
    	 genreLa=new JLabel("장르");
    	 genre=new JLabel();
    	 
    	 genreLa.setBounds(330, 125, 80, 30);
    	 genre.setBounds(415, 125, 250, 30);
    	 add(genreLa);add(genre);
    	 
    	 reg_dateLa=new JLabel("등록일");
    	 reg_date=new JLabel();
    	 
    	 reg_dateLa.setBounds(330, 160, 80, 30);
    	 reg_date.setBounds(415, 160, 250, 30);
    	 add(reg_dateLa);add(reg_date);
    	 
    	 
    	 total_auditLa=new JLabel("누적 관객수");
    	 total_audit=new JLabel();
    	 
    	 total_auditLa.setBounds(330, 195, 80, 30);
    	 total_audit.setBounds(415, 195, 250, 30);
    	 add(total_auditLa);add(total_audit);
    	 
    	 dirLa=new JLabel("감독");
    	 dir=new JLabel();
    	 
    	 dirLa.setBounds(330, 230, 80, 30);
    	 dir.setBounds(415, 230, 250, 30);
    	 add(dirLa);add(dir);
    	 
    	 
    	 actLa=new JLabel("배우");
    	 act=new JLabel();
    	 
    	 actLa.setBounds(330, 265, 80, 30);
    	 act.setBounds(415, 265, 250, 30);
    	 add(actLa);add(act);
    	 
    	 raitingLa=new JLabel("별점");
    	 raiting=new JLabel();
    	 
    	 raitingLa.setBounds(330, 300, 80, 30);
    	 raiting.setBounds(415, 300, 250, 30);
    	 add(raitingLa);add(raiting);
    	 
    	 gradeLa=new JLabel("등급");
    	 grade=new JLabel();
    	 
    	 gradeLa.setBounds(330, 335, 80, 30);
    	 grade.setBounds(415, 335, 250, 30);
    	 add(gradeLa);add(grade);
    	 
    	 storyTa=new JTextPane();
    	 JScrollPane js=new JScrollPane(storyTa);
    	 js.setBounds(330, 400, 435, 120);
    	 add(js);
    	 storyTa.setEnabled(false);
    	 
    	 b1=new JButton("찜하기");
    	 b2=new JButton("예약하기");
    	 b3=new JButton("목록");
    	 
    	 JPanel p=new JPanel();
    	 p.add(b1);p.add(b2);p.add(b3);
    	 p.setBounds(330, 525, 435, 35);
    	 add(p);
    	 
    	 b3.addActionListener(this);
    	 
     }
     public void detailPrint(int mode,MovieVO vo)
     {
    	 this.mode=mode;
    	 try
    	 {
    		 URL url=new URL(vo.getM_post());
    		 Image img=
    			ImageChange.getImage(new ImageIcon(url), 350, 500);
    		 poster.setIcon(new ImageIcon(img));
    		 
    		 title.setText(vo.getM_title()+" ("+vo.getM_eng_title()+")");
    		 nation.setText(vo.getNation());
    		 runtime.setText(vo.getRuntime());
    		 genre.setText(vo.getGenre());
    		 reg_date.setText(vo.getReg_date().toString());
    		 DecimalFormat df = new DecimalFormat("#,###,###");
    		 total_audit.setText(df.format(vo.getTotal_audi()));
    		 dir.setText(vo.getDir());
    		 act.setText(vo.getAct());
    		 raiting.setText(vo.getRaiting().toString());
    		 grade.setText(vo.getGrade());
    		 storyTa.setText(vo.getStory());
    	 }catch(Exception ex) {}
    	 
     }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b3)
		{
			cp.card.show(cp, link[mode]);
		}
	}
     
     
}