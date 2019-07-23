
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

public class SmmSport {
//public static String date = "12/05/2019";
    
    public String ddmmyyyyToyyyymmdd(String inputDate) {
        String day = inputDate.substring(0, 2);
        String month = inputDate.substring(3, 5);
        String year = inputDate.substring(6, 10);
        String mix = year + "-" + month + "-" + day;
        return mix;
    } 
    
    public String getInterYesterdayDate(){ // คศ
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(yesterday);
    }
    
    

    public String page(String url) throws IOException, InterruptedException { 
        String date = null;
        String country;
        String league;
        String time;
        String home;
        String homeScore;
        String away;
        String awayScore;
        String home_away;
                
        if("http://livescore.smmsport.com/result.php".equals(url)){
            date = getInterYesterdayDate();
        }else{
           date = url.replace("http://livescore.smmsport.com/result_program.php?date=", ""); 
           date = ddmmyyyyToyyyymmdd(date);
        }
        JSONObject json = null ;
        
        Document doc = Jsoup.connect(url).timeout(60 * 1000).get();
        Elements elements = doc.getElementsByClass("match_zone");
        for (Element ele : elements) {
            country = ele.getElementsByClass("col1").text();
            league = ele.getElementsByClass("col3").text();
            
            Elements elesDetails = ele.getElementsByClass("match_program");
            for (Element eleDetail : elesDetails) {
                json = new JSONObject();
                time = eleDetail.getElementsByClass("kickon").text();
                home = eleDetail.getElementsByClass("match_item_home").text();
                homeScore = eleDetail.getElementsByClass("home_score").text();
                away = eleDetail.getElementsByClass("match_item_away").text();
                awayScore = eleDetail.getElementsByClass("away_score").text();
                home_away = home +" - "+away;
                
                json.put("date", date);
                json.put("link", url);
                json.put("country", country);  //country ประเทศ
                json.put("league", league);      // ลีก
                json.put("kick_on", time);
                json.put("home", home);
                json.put("home_score", Integer.parseInt(homeScore));
                json.put("away", away);
                json.put("away_score", Integer.parseInt(awayScore));
                json.put("home_away", home_away);
                System.out.println(json.toString());
                elasticsearch(json.toString());
            }
        }
        return json.toString();
    }
    
    public void elasticsearch(String body){
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/smmsport/text")
                    .header("Content-Type", "application/json")
                    .header("Cache-Control", "no-cache")
                    //.header("Postman-Token", "1d20e8f1-0b7a-424c-812d-f769f2a38776,cd0befcb-e82c-4fc5-a341-f718fbd4afed")
                    .body(body)
                    .asString();
          System.out.println("ข้อมูลลง database เรียบร้อยแล้ว");  
        } catch (UnirestException ex) {
            Logger.getLogger(SmmSport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        //String url = "http://livescore.smmsport.com/result_program.php?date=" + date;
        SmmSport smm = new SmmSport(); 
        Jedis redis = new Jedis("127.0.0.1");
        JSONObject json ;
        String url = "";
        boolean flag = true;
        while (flag) {
            url = redis.rpop("pages");
            if(url == null){
               return;
            }
            json = new JSONObject(url);
            String body = smm.page(json.getString("link"));
            //System.out.println(body);
            //smm.elasticsearch(body);
        }

       /*
       String date = "12/05/2019";
       SmmSport smm = new SmmSport(); 
       String newDate = smm.ddmmyyyyToyyyymmdd(date);
       System.out.println(newDate);
       System.out.println(smm.getInterYesterdayDate());
       
       String day = date.substring(0, 2);
       String month = date.substring(3, 5);
       String year = date.substring(6, 10);
       System.out.println(day);
       System.out.println(month);
       System.out.println(year);
        */
    }
}
