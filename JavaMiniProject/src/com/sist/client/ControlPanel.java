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
    BoardMainPanel bp;
    MoviePenal mp;
    MovieFindPenal mfp;
    MovieDetailPenal mdp;
    CardLayout card=new CardLayout();
    public ControlPanel()
    {
    	setLayout(card);
    	hp=new HomePenal(this);
    	add("HOME",hp);
    	cp=new ChatPenal(this);
    	add("CHAT",cp);
    	bp=new BoardMainPanel(this);
    	add("BOARD",bp);
    	mp=new MoviePenal(this);
    	add("MOVIE",mp);
    	mfp=new MovieFindPenal(this);
    	add("MFP",mfp);
    	mdp=new MovieDetailPenal(this);
    	add("DETAIL",mdp);
    }
    
}