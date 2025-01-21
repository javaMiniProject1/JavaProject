package com.sist.client;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
// 화면 변경 
public class ControlPanel extends JPanel{
    HomePenal hp;
    ChatPenal cp;
    MoviePenal mp;
    MovieFindPenal mfp;
    MovieDetailPenal mdp;
	BoardList bList;
	BoardInsert bInsert;
	BoardDetail bDetail;
	BoardUpdate bUpdate;
	BoardReply bReply;
	BoardDelete bDelete;
    CardLayout card=new CardLayout();
    public ControlPanel()
    {
    	setLayout(card);
    	hp=new HomePenal(this);
    	add("HOME",hp);
    	cp=new ChatPenal(this);
    	add("CHAT",cp);
    	mp=new MoviePenal(this);
    	add("MOVIE",mp);
    	mfp=new MovieFindPenal(this);
    	add("MFP",mfp);
    	mdp=new MovieDetailPenal(this);
    	add("DETAIL",mdp);
    	// 게시판
    	bList=new BoardList(this);
    	add("BLIST",bList);
    	bInsert=new BoardInsert(this);
    	add("BINSERT",bInsert);
    	bDetail=new BoardDetail(this);
    	add("BDETAIL",bDetail);
    	bUpdate=new BoardUpdate(this);
    	add("BUPDATE",bUpdate);
		bReply=new BoardReply(this);
		add("BREPLY",bReply);
		bDelete=new BoardDelete(this);
		add("BDELETE",bDelete);
    }
    
}