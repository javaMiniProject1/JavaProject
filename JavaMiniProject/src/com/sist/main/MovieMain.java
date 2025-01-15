//package com.sist.main;
//
//
//
//
//import java.util.Date;
//import java.text.SimpleDateFormat;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//public class MovieMain {
//	public static void main(String[] args) {
//		String[] movieNum = {};
//		MovieVO movie = null;
//		for(int j = 0;j<movieNum.length;j++) {
//			
//			
//		try {
//			// 크롤링할 URL
//			String url = "http://www.cine21.com/movie/info/?movie_id="+movieNum[j];
//			// HTML 문서 가져오기
//			Document doc = Jsoup.connect(url).get();
//			// Movie 객체 생성
//			movie = new MovieVO();
//			// 영화 제목
//			String title = doc.select(".mov_info .tit").text();
//			movie.setM_title(title);
//			String eng_title = doc.select(".mov_info .tit_eng").text();
//			movie.setM_eng_title(eng_title);
//			// 포스터 이미지 URL
//			String poster = doc.select(".mov_poster img").attr("src");
//			movie.setM_post(poster);
//			// 나라, 장르, 상영시간
//			Elements subInfo = doc.select(".sub_info span");
//			String country = subInfo.get(1).text();
//			String genre = subInfo.get(3).text();
//			String runtime = subInfo.get(4).text().replace("상영시간 :", "").trim();
//			movie.setNation(country);
//			movie.setGenre(genre);
//			movie.setRuntime(runtime);
//			// 개봉일
//			String releaseDateStr = doc.select(".sub_info").get(2).select("span").get(0).text().replace("개봉일 :", "")
//					.trim();
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			movie.setReg_date(dateFormat.parse(releaseDateStr));
//			//누적관객
//			int total_audi = Integer.parseInt(doc.select(".sub_info").get(2).select("span").get(1).text().replace("누적관객 :", "").replace("명", "").replace(",", "").trim());
//			movie.setTotal_audi(total_audi);
//			Elements personElements = doc.select(".sub_info a[href*='/person/info']");
//			String director = personElements.get(0).text();
//			movie.setDir(director);
//			// 배우
//			StringBuilder actors = new StringBuilder();
//			for (int i = 1; i < personElements.size(); i++) {
//				Element actor = personElements.get(i);
//				if (actor.text().equals("more"))
//					break;
//				actors.append(actor.text()).append(" ");
//			}
//			movie.setAct(actors.toString().replaceAll(", $", "")); // 마지막 쉼표 제거
//			// 씨네21 평점 없는것도 있어서 try catch로 잡음
//			String cine21RatingStr = null;
//			try {
//			cine21RatingStr = doc.select(".mov_rating li").first().text().replace("씨네21", "").trim();
//			double cine21Rating = Double.parseDouble(cine21RatingStr);
//			movie.setRaiting(cine21Rating);
//			}catch (Exception e) {
//			}
//			// 줄거리
//			
//			String story = doc.select(".story_area .story").text();
//			movie.setStory(story);
//			// 시청 등급
//			String gradeClass = doc.select(".sub_info .grade001, .grade002, .grade003, .grade004").attr("class");
//			String grade = "";
//			if (gradeClass.contains("grade004")) {
//				grade = "청소년 관람불가";
//			} else if (gradeClass.contains("grade003")) {
//				grade = "15세 이상 관람가";
//			} else if (gradeClass.contains("grade002")) {
//				grade = "12세 이상 관람가";
//
//			} else if (gradeClass.contains("grade001")) {
//				grade = "전체 관람가";
//			}
//			movie.setGrade(grade);
//			// Movie 정보 출력
//			System.out.println(movie);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//		}
//
//	}
//	}
//}
//
