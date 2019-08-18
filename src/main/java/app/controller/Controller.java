package app.controller;

import app.dao.Redis;
import app.function.ClearIndex;
import app.function.DateTimes;
import app.function.Elasticsearch;
import app.function.OtherFunc;
import app.service.ServiceWebStart;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import app.service.ServicePremierLeagueAndThaiPremierLeague;
import java.util.concurrent.TimeUnit;

@Component
public class Controller {

    @Autowired
    private ClearIndex clearIndex;

    @Autowired
    private DateTimes dateTimes;

    @Autowired
    private ServiceWebStart serviceWebStart;

    @Autowired
    private Redis rd;

    @Autowired
    private ServicePremierLeagueAndThaiPremierLeague servicePreAndThai;
    
    @Autowired
    private Elasticsearch elasticsearch;
    
    @Autowired
    private OtherFunc func;

       
    //@Scheduled(cron = "${cron_every_1_min}")  //  ทุกๆ 1 นาที
    @Scheduled(cron = "#{@cronExpression_1}") 
    public void runTask_1() {
        System.out.println(dateTimes.interDateTime() + " : web scrapping runTask_1 start");
        /*  System.out.println("search date => " + dateTimes.newInterDate());
        String dataFixturesTh = elasticsearch.searchFixtures(dateTimes.newInterDate(), "fixtures_thaipremierleague");  //query โดยระบุวันที่วันนี้ ลงในตารางผลบอลไทยพรีเมียลีก 
        String dataFixturesPre = elasticsearch.searchFixtures(dateTimes.newInterDate(), "fixtures_premierleague");     //query โดยระบุวันที่วันนี้ ลงในตารางผลบอลพรีเมียลีก

        boolean valueFixturesTh = func.haveFixturesData(dataFixturesTh);  // ตรวจเช็คว่ามีการเเข่งขันไทยพรีเมียลีกหรือไม่ (กรมี จะเป็น true)
        boolean valueFixturesPre = func.haveFixturesData(dataFixturesPre); // ตรวจเช็คว่ามีการเเข่งขันพรีเมียลีกหรือไม่ (กรมี จะเป็น true)
        
        if(valueFixturesTh){
            System.out.println("   find fixtures_thaipremierleague");
        }else{
            System.out.println("   didn't find fixtures_thaipremierleague");
        }
        
        if(valueFixturesPre){ 
            System.out.println("   find fixtures_premierleague");
        }else{
            System.out.println("   didn't find fixtures_premierleague");
        }
     */      
        //if (valueFixturesTh || valueFixturesPre) {  // กรณีมีการแข่งขันไทยพรีเมียลีก หรือ พรีเมีย ให้ทำงาน
 /*           serviceWebStart.startPresentResults();
            Jedis redis = rd.connect();
            String url;
            JSONObject json;
            String type;

            boolean check = true;
            while (check) {
                url = redis.rpop("link_queue");
                if (url != null) {
                    json = new JSONObject(url);
                    type = json.getString("type");
                    if ("results_thaipremierleague".equals(type)) {             //ผลการแข่งขันไทยลีก (ปัจจุบัน)
                        clearIndex.presentResultsThaipremierleague();           //ลบ index present_results_thaipremierleague เพื่อ update ใหม่
                        servicePreAndThai.getPages(url);
                    }
                    if ("results_premierleague".equals(type)) {                 //ผลการแข่งขันพรีเมียร์ลีก อังกฤษ (ปัจจุบัน)
                        clearIndex.presentResultsPremierleague();               //ลบ index present_results_premierleague เพื่อ update ใหม่
                        servicePreAndThai.getPages(url);
                    }
                } else {
                    check = false;
                    //System.out.println(dateTimes.interDateTime() + " : web scrapping runTask_1 stop");
                }
            } */      
        //} 
        System.out.println(dateTimes.interDateTime() + " : web scrapping runTask_1 stop");
    }

    //@Scheduled(cron = "${cron_everyday_5_AM}") // everday 5 AM
    @Scheduled(cron = "#{@cronExpression_2}") 
    public void runTask_2() {
        System.out.println(dateTimes.interDateTime() + " : web scrapping runTask_2 start");
        //clearIndex.allIndex();  // ลบ index ทั้งหมดยกเว้น ผลบอล

        serviceWebStart.start();
        Jedis redis = rd.connect();
        String url;
        JSONObject json;
        String type;

        boolean check = true;
        while (check) {
            url = redis.rpop("link_queue");
            if (url != null) {
                json = new JSONObject(url);
                type = json.getString("type");
                if ("results_thaipremierleague".equals(type)) {                 //ผลการแข่งขันไทยลีก (อดีต)
                    servicePreAndThai.getPages(url);
                }
                if ("results_premierleague".equals(type)) {                     //ผลการแข่งขันพรีเมียร์ลีก อังกฤษ (อดีต)
                    servicePreAndThai.getPages(url);
                }
                if ("fixtures_thaipremierleague".equals(type)) {                //ตารางแข่งขันฟุตบอลไทยลีก
                    servicePreAndThai.getPages(url);
                }
                if ("fixtures_premierleague".equals(type)) {                    //ตารางแข่งขันฟุตบอลพรีเมียร์ลีก อังกฤษ
                    servicePreAndThai.getPages(url);
                }
                if ("league_table_thaipremierleague".equals(type)) {            //ตารางคะแนนไทยลีก
                    servicePreAndThai.getPages(url);
                }
                if ("league_table_premierleague".equals(type)) {                //ตารางคะแนนพรีเมียร์ลีก อังกฤษ
                    servicePreAndThai.getPages(url);
                }
                if ("stats_thaipremierleague_players".equals(type)) {
                    servicePreAndThai.getStatsOfTeamPages(url, type);                         //สถิติต่างๆของสโมสรฟุตบอล
                    servicePreAndThai.getTeamDetailPages(url, type);                          //ผู้เล่นนักเตะทีม...
                }
                if ("stats_premierleague_players".equals(type)) {
                    servicePreAndThai.getStatsOfTeamPages(url, type);                         //สถิติต่างๆของสโมสรฟุตบอล
                    servicePreAndThai.getTeamDetailPages(url, type);                          //ผู้เล่นนักเตะทีม...
                }
                if ("stats".equals(type)) {
                    servicePreAndThai.getPlayersProfilePages(url);                            //รายละเอียดนักเตะ
                }
                if ("statistics_thaipremierleague".equals(type)) {              //รวมสถิติต่างๆไทยลีก
                    servicePreAndThai.getPages(url);
                }
                if ("statistics_premierleague".equals(type)) {                  //รวมสถิติต่างๆพรีเมียร์ลีก อังกฤษ
                    servicePreAndThai.getPages(url);
                }
                if ("staff_thaipremierleague".equals(type)) {                   //ข้อมูลผู้จัดการทีมไทยลีก
                    servicePreAndThai.getTeamPage(url, type);
                }
                if ("staff_premierleague".equals(type)) {                       //ข้อมูลผู้จัดการทีมพรีเมียร์ลีก อังกฤษ
                    servicePreAndThai.getTeamPage(url, type);
                }

                if ("staff".equals(type)) {                                     //ข้อมูลผู้จัดการทีม
                    String link = json.getString("link");
                    String typeDetail = json.getString("type_detail");

                    if ("link_team".equals(typeDetail)) {                       //กรณีเป็น link team
                        servicePreAndThai.getStaffTeamPage(link, url);
                    }
                }
                
                if ("teams_thaipremierleague".equals(type)) {                   //สโมสรฟุตบอล thaipremierleague 
                    servicePreAndThai.getPages(url);
                }
                if ("teams_premierleague".equals(type)) {                       //สโมสรฟุตบอล premierleague
                    servicePreAndThai.getPages(url);
                }
 
                if ("score_analyze_thaipremierleague".equals(type)) {           //วิเคราะห์บอลไทยลีก
                    servicePreAndThai.getPages(url);
                }
                if ("score_analyze_premierleague".equals(type)) {               //วิเคราะห์บอลพรีเมียร์ลีก อังกฤษ
                    servicePreAndThai.getPages(url);
                } 
                
            } else {
                check = false;
            }
        }
        System.out.println(dateTimes.interDateTime() + " : web scrapping runTask_2 stop");
    }

}
