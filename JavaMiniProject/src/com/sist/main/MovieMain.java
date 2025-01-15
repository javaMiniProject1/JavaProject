package com.sist.main;




import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MovieMain {
	public static void main(String[] args) {
		String[] movieNum = {"61175","56448","61932","61853","61738"
				,"57542","61854","61487","61244","61558"
				,"61957","61579","2676","61912","61119",
				"61884","59945","61989","61860","61950",
				"61206","21799","58814","8724","61981",
				"61863","5496","61934","61760","61900",
				"61943","61523","61905","58356","61931",
				"61757","61951","61572","59284","61401",
				"61788", "61936","28917","61961",
				"61131","61960","61790","15806","61327"
				,"61811", "61048","61048","60801","61797","46314","61859","2929","61753","61818","54227","54227","61850","1267","61831","962","60886","61915", "61941","61928","48621","61044","48895","61862","61866","61771","54608","61417","58527","61661","48079","47818","52898","5825",  "60741","38803", "24827","41182","41182","39810","29025",  "61106","312","61648","61916","60970","61110","61065",  "672","61147","61733","399","61969","59566","61448","44529","61855","61930","61759","61149","61929","60611","61804","7610","61405","24540","61742","14","61620","61846","61836","41064","61824","58537","61522","61975","61793",   "57339","61315","8696","2949", "3250","60211","61664", "61810","61525"};
		Movie movie = null;
		for(int j = 0;j<movieNum.length;j++) {
			
			
		try {
			System.out.println(movieNum[j]);
			// 크롤링할 URL
			String url = "http://www.cine21.com/movie/info/?movie_id="+movieNum[j];
			// HTML 문서 가져오기
			Document doc = Jsoup.connect(url).get();
			// Movie 객체 생성
			movie = new Movie();
			// 영화 제목
			String title = doc.select(".mov_info .tit").text();
			movie.setM_title(title);
			String eng_title = doc.select(".mov_info .tit_eng").text();
			movie.setM_eng_title(eng_title);
			// 포스터 이미지 URL
			String poster = doc.select(".mov_poster img").attr("src");
			movie.setM_post(poster);
			// 나라, 장르, 상영시간
			Elements subInfo = doc.select(".sub_info span");
			String country = subInfo.get(1).text();
			String genre = subInfo.get(3).text();
			String runtime = subInfo.get(4).text().replace("상영시간 :", "").trim();
			movie.setNation(country);
			movie.setGenre(genre);
			movie.setRuntime(runtime);
			// 개봉일
				
			String releaseDateStr = doc.select(".sub_info").get(2).select("span").get(0).text().replace("개봉일 :", "")
					.trim();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			movie.setReg_date(dateFormat.parse(releaseDateStr));
			//누적관객
			
			int total_audi = Integer.parseInt(doc.select(".sub_info").get(2).select("span").get(1).text().replace("누적관객 :", "").replace("명", "").replace(",", "").trim());
			movie.setTotal_audi(total_audi);
			Elements personElements = doc.select(".sub_info a[href*='/person/info']");
			String director = personElements.get(0).text();
			movie.setDir(director);
			// 배우
			StringBuilder actors = new StringBuilder();
			for (int i = 1; i < personElements.size(); i++) {
				Element actor = personElements.get(i);
				if (actor.text().equals("more"))
					break;
				actors.append(actor.text()).append(" ");
			}
			movie.setAct(actors.toString().replaceAll(", $", "")); // 마지막 쉼표 제거
			// 씨네21 평점 없는것도 있어서 try catch로 잡음
			String cine21RatingStr = null;
			try {
			cine21RatingStr = doc.select(".mov_rating li").first().text().replace("씨네21", "").trim();
			double cine21Rating = Double.parseDouble(cine21RatingStr);
			movie.setRaiting(cine21Rating);
			}catch (Exception e) {
			}
			// 줄거리
			
			String story = doc.select(".story_area .story").text();
			movie.setStory(story);
			// 시청 등급
			String gradeClass = doc.select(".sub_info .grade001, .grade002, .grade003, .grade004").attr("class");
			String grade = "";
			if (gradeClass.contains("grade004")) {
				grade = "청소년 관람불가";
			} else if (gradeClass.contains("grade003")) {
				grade = "15세 이상 관람가";
			} else if (gradeClass.contains("grade002")) {
				grade = "12세 이상 관람가";

			} else if (gradeClass.contains("grade001")) {
				grade = "전체 관람가";
			}
			movie.setGrade(grade);
			// Movie 정보 출력
			System.out.println(movie);
		
		} catch (Exception e) {
			
			e.printStackTrace();

		}

	}
	}
}

