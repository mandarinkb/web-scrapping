import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.Jedis;


public class SmmSportPage {
    public static String url = "http://livescore.smmsport.com";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");//yyyy/MM/dd HH:mm:ss
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  //yyyy/MM/dd HH:mm:ss
    
    public String interDateTime() {  //คศ + เวลา
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    
    public String thaiDateTime(){
       Date date = new Date();  //พศ + เวลา
       return df.format(date);
   }
    
    public String interDate(){  //คศ 
       LocalDate localDate = LocalDate.now();
       return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(localDate);
   }
    
    public String timeNow(){
       Date time = new java.util.Date(System.currentTimeMillis());
       return new SimpleDateFormat("HH:mm:ss").format(time);
       //return java.time.LocalTime.now().toString(); //13:20:14.905
   }
    
   /* public Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }*/

    public String getThaiYesterdayDate() { // พศ
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);      
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        return dateFormat.format(cal.getTime());
    }
    
    public String getInterYesterdayDate(){ // คศ
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(yesterday);
    }
    
    
    public String getPrevPage(String inputUrl) {
        String getUrl = null;
        try {
            Document doc = Jsoup.connect(inputUrl).timeout(60 * 1000).get();
            Elements elements = doc.getElementsByClass("ButtonPrev");
            Element eleUrl = elements.select("a").first();
            String urlPrev = eleUrl.attr("href");

            System.out.println(urlPrev);
            return url + urlPrev;

        } catch (IOException e) {
            //e.getMessage();
            return getUrl;
        }
    }
    
    public void test(String page) {  
        Jedis redis = new Jedis("127.0.0.1");
        try {
            boolean flag = true;
            while (flag) {
                if (page != null) {
                    Document doc = Jsoup.connect(page).timeout(60 * 1000).get();
                    Elements elements = doc.getElementsByClass("ButtonPrev");
                    Element eleUrl = elements.select("a").first();
                    String urlPrev = eleUrl.attr("href");
                    String newPage = url + urlPrev;
                    page = newPage;
                    
                    String pageJson = "{page:\""+page+"\"}";
                    redis.rpush("SmmSport", pageJson);
                    
                    //System.out.println(pageJson);
                    System.out.println("new page : " +newPage);
                    
                } else {
                    flag = false;
                }
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }
    
    public void pop(){
        Jedis redis = new Jedis("127.0.0.1");
        int count = 0;
        boolean flag = true;
        while (flag) {
            String str = redis.rpop("SmmSport");
            if (str == null) {
                flag = false;
                return;
            }
            JSONObject obj = new JSONObject(str);
            String strObj = obj.getString("page");
            System.out.println(++count + " : " + strObj);
            //System.out.println(++count +" : "+ str);

        }
    }
    @Scheduled(fixedDelay = 2*1000) //   3*60*1000  คือ 3นาที
    public void scheduleTaskWithFixedRate() {
        Date time = new java.util.Date(System.currentTimeMillis());
        String strTime = new SimpleDateFormat("HH:mm:ss").format(time);
        System.out.println("hello world. : " + strTime);
    }

    
    public static void main(String[] args){
        SmmSportPage smm = new SmmSportPage();
        //String newPage = smm.getPrevPage(url);
        //smm.test(newPage);
        //smm.pop();
        /*System.out.println(smm.interDateTime());
        System.out.println(smm.thaiDateTime());
        System.out.println(smm.timeNow());
        System.out.println(smm.interDate());
        System.out.println(smm.getInterYesterdayDate());
        System.out.println(smm.getThaiYesterdayDate());
        
*/
        smm.scheduleTaskWithFixedRate();
    }
     
}