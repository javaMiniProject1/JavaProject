package com.sist.client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChatPenal extends JPanel {
    JTextArea ta;
    JTextField tf;
    JTable table;
    DefaultTableModel model;
    JButton b1, b2, inputChatBtn;
    JScrollBar bar;
    ControlPanel cp;

    public ChatPenal(ControlPanel cp) {
        this.cp = cp;

        // 채팅창
        ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        ta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JScrollPane js = new JScrollPane(ta);
        bar = js.getVerticalScrollBar();

        // 입력 필드와 버튼
        tf = new JTextField();
        tf.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        tf.setBorder(new LineBorder(new Color(150, 150, 150), 1));
        inputChatBtn = new JButton("입력");
        inputChatBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        inputChatBtn.setBackground(new Color(63, 81, 181)); 
        inputChatBtn.setForeground(Color.WHITE); 
        inputChatBtn.setFocusPainted(false); 

        // 사용자 정보 테이블
        String[] col = { "아이디", "이름", "성별" };
        String[][] row = new String[0][3];
        model = new DefaultTableModel(row, col);
        table = new JTable(model);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        table.setRowHeight(25); 
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230)); 
        JScrollPane js1 = new JScrollPane(table);

        
        b1 = new JButton("쪽지보내기");
        b2 = new JButton("정보보기");

        // 버튼 디자인 개선
        JButton[] buttons = { b1, b2 };
        for (JButton button : buttons) {
            button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            button.setBackground(new Color(76, 175, 80)); 
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
        }

        setLayout(null);

        // 채팅창 배치
        js.setBounds(100, 20, 400, 450);
        add(js);

        // 입력 패널: 텍스트 필드 + 입력 버튼
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(5, 0)); 
        inputPanel.setBounds(100, 475, 465, 40);

        tf.setBounds(0, 0, 380, 30); 
        inputChatBtn.setBounds(380, 0, 80, 30); 
        inputPanel.add(tf, BorderLayout.CENTER);
        inputPanel.add(inputChatBtn, BorderLayout.EAST); 
        add(inputPanel);

        // 사용자 테이블 배치
        js1.setBounds(510, 20, 280, 300);
        add(js1);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.setBounds(510, 330, 280, 35);
        add(buttonPanel);
    }
}
/*
public class ChatPenal extends JPanel {
    JTextArea ta;
    JTextField tf;
    JTable table;
    DefaultTableModel model;
    JButton b1, b2, inputChat;

    ControlPanel cp;

    public ChatPenal(ControlPanel cp) {
        this.cp = cp;

        // 채팅창
        ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        ta.setBorder(BorderFactory.createCompoundBorder(
                			new LineBorder(new Color(200, 200, 200), 1),
                				BorderFactory.createEmptyBorder(5, 5, 5, 5))); // 테두리와 여백
        JScrollPane js = new JScrollPane(ta);

        // 입력 필드와 버튼
        tf = new JTextField();
        tf.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        tf.setBorder(new LineBorder(new Color(150, 150, 150), 1));
        inputChat = new JButton("입력");
        inputChat.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        inputChat.setBackground(new Color(63, 81, 181)); // 버튼 배경색 (파란색 계열)
        inputChat.setForeground(Color.WHITE); // 버튼 글자색 (흰색)
        inputChat.setFocusPainted(false); // 버튼 선택 테두리 제거

        // 사용자 정보 테이블
        String[] col = { "아이디", "이름", "성별" };
        String[][] row = new String[0][3];
        model = new DefaultTableModel(row, col);
        table = new JTable(model);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        table.setRowHeight(25); // 행 높이 조정
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230)); // 헤더 배경색
        JScrollPane js1 = new JScrollPane(table);

        // 부가 버튼
        b1 = new JButton("쪽지보내기");
        b2 = new JButton("정보보기");

        // 버튼 디자인 개선
        JButton[] buttons = { b1, b2 };
        for (JButton button : buttons) {
            button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            button.setBackground(new Color(76, 175, 80)); // 녹색 계열 배경색
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
        }

        setLayout(null);

        // 채팅창 배치
        js.setBounds(100, 20, 400, 450);
        add(js);

        // 입력 패널: 텍스트 필드 + 입력 버튼
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBounds(100, 475, 465, 40);

        tf.setBounds(0, 0, 375, 30); // 텍스트 필드 크기
        inputChat.setBounds(380, 0, 80, 30); // 입력 버튼 크기
        inputPanel.add(tf);
        inputPanel.add(inputChat);
        add(inputPanel);

        
        js1.setBounds(510, 20, 280, 300);
        add(js1);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.setBounds(510, 330, 280, 35);
        add(buttonPanel);
    }
}
*/