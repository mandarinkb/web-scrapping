
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


public class DataFixtures {
    public String interDate() {  //คศ 
        LocalDate localDate = LocalDate.now();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
    }
    public String searchFixtures(String date, String index) {
        String str = null;
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:9200/"+index+"/_search")
                    .header("Content-Type", "application/json")
                    .header("Cache-Control", "no-cache")
                    .body("{\"query\": {\"match_phrase\": {\"date\": \""+date+"\"}}}")
                    .asString();
            
            str = response.getBody();
        } catch (UnirestException ex) {
            Logger.getLogger(DataFixtures.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
    public boolean haveFixturesData(String inputJsonStr){
        boolean data ;
        JSONObject json = new JSONObject(inputJsonStr);
        try {
            JSONObject jsonHits = json.getJSONObject("hits");
            JSONObject jsonTotal = jsonHits.getJSONObject("total");
            int value = jsonTotal.getInt("value");
            System.out.println(value);
            if(value == 0){  //กรณีที่ไม่มีข้อมูลใน json
               data = false; 
            }else{           //กรณีที่มีข้อมูลใน json
               data = true;  
            }
        } catch (JSONException e) {  //กรณีไม่มี index หรือกรณีอื่นๆ
            data = false; 
            System.out.println(e.getMessage());
        }
        return data;
    }
    public static void main(String[] argv) {
        DataFixtures data = new DataFixtures();
        //String body = "{\"query\": {\"match_phrase\": {\"date\": \""+data.interDate()+"\"}}}";
        
        //data.searchFixtures(body, "fixtures_thaipremierleague");
        //System.out.println(data.searchFixtures(body, "fixtures_thaipremierleague"));
        boolean check = data.haveFixturesData(data.searchFixtures("2019-07-06", "fixtures_thaipremierleague")); //data.interDate()
        boolean check2 = data.haveFixturesData(data.searchFixtures("2019-07-06", "fixtures_premierleague")); //data.interDate()
        
        if (check || check2 ) {
            int max = 31;
            for (int i = 0; i < max; i++) {
                System.out.println(data.interDate() + " : run working");
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {

                }
            }
        }
        
    }
}
