
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Fixtures {
    
    
    public void inputElasticsearch(String body) {
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/fixtures_thaipremierleague/text")
                    .header("Content-Type", "application/json")
                    .header("Cache-Control", "no-cache")
                    .body(body)
                    .asString();
        } catch (UnirestException ex) {
            Logger.getLogger(Fixtures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String lessThanDateElasticsearch() {
        String str = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/fixtures_thaipremierleague/_search")
                    .header("Content-Type", "application/json")
                    .header("Cache-Control", "no-cache")
                    .body("{\r\n    \"query\": {\r\n        \"range\" : {\r\n            \"date\" : {\r\n                \"lt\" :  \"now/d\"\r\n            }\r\n        }\r\n    }\r\n}")
                    //.body("{\r\n    \"query\": {\r\n        \"range\" : {\r\n            \"date\" : {\r\n                \"lt\" :  \"2019-05-06\"\r\n            }\r\n        }\r\n    }\r\n}")
                    .asString();
            str = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(Fixtures.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
    public void delElasticsearch(String id) {
        try {
            HttpResponse<String> response = Unirest.delete("http://localhost:9200/fixtures_thaipremierleague/text/"+id+"")
                    .header("Cache-Control", "no-cache")
                    .header("cache-control", "no-cache")
                    .asString();
        } catch (UnirestException ex) {
            Logger.getLogger(Fixtures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
     public String thaiDateFormat(String input) {
        String dayStr = input.substring(6, 8);
        String monthStr = input.substring(4, 6);
        String yearStr = input.substring(0, 4);

        int d = Integer.parseInt(dayStr);
        int month = Integer.parseInt(monthStr);
        int y = Integer.parseInt(yearStr) + 543;//แปลงเป็น พศ

        String dayString = Integer.toString(d);
        String monthString;
        String yearString = Integer.toString(y);

        switch (month) {
            case 1:
                monthString = "มกราคม";
                break;
            case 2:
                monthString = "กุมภาพันธ์";
                break;
            case 3:
                monthString = "มีนาคม";
                break;
            case 4:
                monthString = "เมษายน";
                break;
            case 5:
                monthString = "พฤษภาคม";
                break;
            case 6:
                monthString = "มิถุนายน";
                break;
            case 7:
                monthString = "กรกฎาคม";
                break;
            case 8:
                monthString = "สิงหาคม";
                break;
            case 9:
                monthString = "กันยายน";
                break;
            case 10:
                monthString = "ตุลาคม";
                break;
            case 11:
                monthString = "พฤศจิกายน";
                break;
            case 12:
                monthString = "ธันวาคม";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        String mix = "วันที่ " + dayString + " " + monthString + " " + yearString;
        return mix;
    }
    
    
    
    
    
    public String getInterDate(String inputDate){
        String[] splitStr = inputDate.split("\\s+");
        //วันที่ 
        String day = splitStr[1];
        if("1".equals(day)){
            day = "01";
        }else if("2".equals(day)){
            day = "02";
        }else if("3".equals(day)){
            day = "03";
        }else if("4".equals(day)){
            day = "04";
        }else if("5".equals(day)){
            day = "05";
        }else if("6".equals(day)){
            day = "06";
        }else if("7".equals(day)){
            day = "07";
        }else if("8".equals(day)){
            day = "08";
        }else if("9".equals(day)){
            day = "09";
        }
        //เดือน
        String month = splitStr[2];
        if("มกราคม".equals(month)){
            month = "01";
        }else if("กุมภาพันธ์".equals(month)){
            month = "02";
        }else if("มีนาคม".equals(month)){
            month = "03";
        }else if("เมษายน".equals(month)){
            month = "04";
        }else if("พฤษภาคม".equals(month)){
            month = "05";
        }else if("มิถุนายน".equals(month)){
            month = "06";
        }else if("กรกฎาคม".equals(month)){
            month = "07";
        }else if("สิงหาคม".equals(month)){
            month = "08";
        }else if("กันยายน".equals(month)){
            month = "09";
        }else if("ตุลาคม".equals(month)){
            month = "10";
        }else if("พฤศจิกายน".equals(month)){
            month = "11";
        }else if("ธันวาคม".equals(month)){
            month = "12";
        }
        //ปี
        int y = Integer.parseInt(splitStr[3]) - 543;//แปลงเป็น คศ
        String year = Integer.toString(y);
        return year+"-"+month+"-"+day;
    }
    
    public String getHomeAway(String home , String away){
        String mix ;
        return mix = home+" - "+away;
    }
    public String getNewLinkImage(String url) {
        return url = url.replace("../..", "");
    }   

    public void page(String url) throws IOException, InterruptedException {
        Document doc = Jsoup.connect(url).timeout(60 * 1000).get();
        Elements elements = doc.getElementsByClass("ui-tabs");
        List<String> list = new ArrayList<>();
        String strUrl;
        // เก็บ id component ของเดือน
        for (Element ele : elements) {
            Elements elesId = ele.getElementsByClass("ui-tabs-nav");
            for (Element eleGetMonth : elesId) {
                Elements a = eleGetMonth.select("a");
                for (Element href : a) {
                    strUrl = href.attr("href");
                    strUrl = strUrl.replace("#", ""); //ตัด # ออก
                    list.add(strUrl); // เก็บใส่ list ไว้ 
                }
            }
        }
        // จบเก็บ id component ของเดือน   
        JSONObject json;
        String daymatches = null;
        String home ;
        String time;
        String scoreHome = null;
        String scoreAway = null;
        String away;
        String homeAway;
        String daymatchesInter ;
        String homeImgUrl = null;
        String awayImgUrl = null;
        
        for (String idMonth : list) {
            Element ele = doc.getElementById(idMonth);
            Elements eles = ele.getElementsByClass("league-fixture");
            for (Element eleResult : eles) {
                Elements elesChildren = eleResult.select("*"); // select all child tags of the form
                for (Element eleChild : elesChildren) {
                    if (eleChild.hasClass("daymatches")) {
                        daymatches = eleChild.getElementsByClass("daymatches").text();
                    }
                    else if(eleChild.hasClass("matches")){
                        Elements elesMatches = eleChild.getElementsByClass("matches");
                        for (Element eleMatch : elesMatches) {
                            json = new JSONObject();
                            home = eleMatch.getElementsByClass("home").text();
                            time = eleMatch.getElementsByClass("time").text();
                            away = eleMatch.getElementsByClass("away").text();
    
                            if (!home.isEmpty()) {
                                //เก็บ logo เจ้าบ้าน 
                                Elements elesHome = eleMatch.getElementsByClass("home");
                                Element eleHomeImg = elesHome.select("img").first();
                                homeImgUrl = getNewLinkImage(eleHomeImg.attr("src"));

                                //เก็บ logo ทีมเยือน 
                                Elements elesAway = eleMatch.getElementsByClass("away");
                                Element eleAwayImg = elesAway.select("img").first();
                                awayImgUrl = getNewLinkImage(eleAwayImg.attr("src"));
                                    
                                homeAway = getHomeAway(home,away);
                                daymatchesInter = getInterDate(daymatches);
                                json.put("date", daymatchesInter);
                                json.put("date_thai", daymatches);
                                json.put("home", home);
                                json.put("time", time);
                                json.put("away", away);
                                json.put("home_away", homeAway);
                                json.put("home_img", homeImgUrl);
                                json.put("away_img", awayImgUrl);
                                System.out.println(json.toString());
                                //inputElasticsearch(json.toString());
                            }
                        }  
                    }
                }
            }
        }  
    }

  public int getValue(String inputJson){
      JSONObject json = new JSONObject(inputJson);
      JSONObject obj = json.getJSONObject("hits");
      JSONObject objTotal = obj.getJSONObject("total");
      int value = objTotal.getInt("value");
      System.out.println(value);
      return value;   
  }
  
  public List getId(String inputJson){
      List<String> list = new ArrayList<>();
      String id;
      JSONObject json = new JSONObject(inputJson);
      JSONObject obj = json.getJSONObject("hits");
      JSONArray arrHits = obj.getJSONArray("hits");
        for (int i = 0; i < arrHits.length(); i++) {
            id = arrHits.getJSONObject(i).getString("_id");
            list.add(id);
        }
      return list;   
  }
  
  public void delOld(){
      boolean check = true;
      while (check) {
          String lessThan = lessThanDateElasticsearch();
          List<String> list = getId(lessThan);
          if (!list.isEmpty()) {  //กรณีมีค่าใน list 
              for (String listId : list) {
                  delElasticsearch(listId);   // ลบรายการ
              }
          } else {  // กรณี list ว่าง
              check = false;
          }
      }
  }
    
    public static void main(String[] args) throws IOException, InterruptedException{
        String url = "http://www.livesoccer888.com/thaipremierleague/fixtures/index.php";
        //String url = "http://www.livesoccer888.com/premierleague/fixtures/index.php";
        
        Fixtures pl = new Fixtures();
        pl.page(url);
        
        
        //pl.delOld();
        //
       // pl.delElasticsearch("mh4v-WoBAfgKSk5ouB9f");
       //String str = pl.lessThanDateElasticsearch();
       //System.out.println(str);
       //
    }
    
}
