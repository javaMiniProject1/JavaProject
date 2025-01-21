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
    JButton b1, b2, inputChatBtn, addFriendBtn, listFriendBtn;
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
        ta.setEditable(false);
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
        model = new DefaultTableModel(row, col) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
        	
        };
        table = new JTable(model);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        table.setRowHeight(25); 
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230)); 
        JScrollPane js1 = new JScrollPane(table);

        
        b1 = new JButton("쪽지보내기");
        b2 = new JButton("정보보기");
        addFriendBtn = new JButton("친구추가");
        listFriendBtn = new JButton("친구목록");

        // 버튼 디자인 개선
        JButton[] buttons = { b1, b2, addFriendBtn, listFriendBtn };
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
        buttonPanel.add(addFriendBtn);
        buttonPanel.add(listFriendBtn);
        buttonPanel.setBounds(510, 330, 280, 60);
        add(buttonPanel);
    }
}
