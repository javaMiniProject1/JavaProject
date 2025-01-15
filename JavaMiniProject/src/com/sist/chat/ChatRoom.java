package com.sist.chat;

import javax.swing.*;

public class ChatRoom extends JPanel {
	
    public ChatRoom() {
        JFrame frame = new JFrame("제목 받아오기?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(null);

        // 채팅창 (왼쪽)
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBorder(BorderFactory.createTitledBorder("채팅창"));
        JScrollPane chatAreaScrollPane = new JScrollPane(chatArea);
        chatAreaScrollPane.setBounds(10, 10, 375, 470);
        frame.add(chatAreaScrollPane);

        // 접속자 목록 (오른쪽)
        // 서버 메모리에 접속 인원 저장 / users 배열 임시 인원
        // 서버에서 접속 인원 받아올 것임
        String[] users = {"User1", "User2", "User3"};
        JList<String> userList = new JList<>(users);
        userList.setBorder(BorderFactory.createTitledBorder("접속자 목록"));
        JScrollPane userListScrollPane = new JScrollPane(userList);
        userListScrollPane.setBounds(390, 10, 90, 470);
        frame.add(userListScrollPane);

        // 하단 패널: 채팅 입력창 + 전송 버튼
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBounds(10, 480, 480, 100);


        JTextField chatInput = new JTextField();
        chatInput.setBounds(0, 10, 375, 50);

        JButton sendButton = new JButton("입력");
        sendButton.setBounds(380, 10, 90, 50);

        inputPanel.add(chatInput);
        inputPanel.add(sendButton);

        frame.add(inputPanel);

        // 프레임 표시
        frame.setVisible(true);
    }
}
