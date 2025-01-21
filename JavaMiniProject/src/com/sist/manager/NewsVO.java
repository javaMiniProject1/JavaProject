package com.sist.manager;

import lombok.Data;

@Data
public class NewsVO {

	private String title;
	private String link;
	private String description; //기사내용
	private String pubDate; //작성시간
	
}
