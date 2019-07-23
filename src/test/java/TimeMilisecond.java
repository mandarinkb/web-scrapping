
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class TimeMilisecond {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");//yyyy/MM/dd HH:mm:ss
    public String interDate() {  //คศ 
        LocalDate localDate = LocalDate.now();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
    }
    public String interDateTime() {  //คศ + เวลา
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public String timeNow() {
        java.util.Date time = new java.util.Date(System.currentTimeMillis());
        return new SimpleDateFormat("HH:mm").format(time);
    }
    public static void main(String[] argv) throws ParseException, InterruptedException {
        List<String> listDate = new ArrayList<String>();
        TimeMilisecond t = new TimeMilisecond();
        //System.out.println("Time now : " + t.interDateTime());
        
        String myDate = "2019-07-05 21:40";                        //เวลาและวันที่  2019-07-05 16:45
        String inputModified = myDate.replace( " " , "T" );        //จัดรูปใหม่     2019-07-05T16:45 
        LocalDateTime ldt = LocalDateTime.parse( inputModified );
        LocalDateTime ldtLater = ldt.plusMinutes( 5 );           //เพิ่มเวลา 100 นาที
        String dateModified = ldt.toString().replace("T" ," ");    //แปลงกลับตัด T ออก
        String afterPlusMin = ldtLater.toString().replace("T" ," ");
        System.out.println("Time : " + dateModified);
        System.out.println("Time after : " + afterPlusMin);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date beginDate = sdf.parse(dateModified);
        long beginMillis = beginDate.getTime();
        
        Date endDate = sdf.parse(afterPlusMin);
        long endMillis = endDate.getTime();      //แปลงหน่วยเป็นวินาทีเพื่อใช้เปรียบเทียบ
        listDate.add("2019-07-06 17:45");
        listDate.add("2019-07-06 18:00");
        listDate.add("2019-07-06 19:00");
        listDate.add("2019-07-06 20:00");
        int sizeListDate = listDate.size();       // นำจำนวน index ทั้งหมด
        //System.out.println("List Date : " + sizeListDate );   
        
        System.out.println(t.interDate());   // วันปัจจุบัน
        System.out.println(t.timeNow());     // เวลาปัจจุบัน
        System.out.println("");
        long maxDate = 0 ;
        int count = 0;
        String maxStr = null;
        for(String str : listDate){
            Date strDate = sdf.parse(str);
            long strMillis = strDate.getTime();
            if(count < 1){ // รอบแรกให้เก็บค่าเริ่มต้น
                maxDate = strMillis;
                maxStr = str;
            }

            if(strMillis < maxDate){   // ติดลบน้อยคือค่าเวลาที่มาก
                maxDate = strMillis;
                maxStr = str;
            }
            System.out.println(str); 
            String[] arrOfStr = str.split(" "); 
            String dateSplit = arrOfStr[0];
            String timeSplit = arrOfStr[1];
            System.out.println("dateSplit : " + dateSplit);
            System.out.println("timeSplit : " + timeSplit);
            System.out.println("Millis : " + strMillis);
            System.out.println("");
        }
        System.out.println("maxDate : "+maxStr);
        System.out.println(maxDate);
        
/*        while (true) {
            Date date3 = sdf.parse(t.interDateTime());
            long now = date3.getTime();         //เวลาปัจจุบัน
            
            if (now < endMillis) {
                System.out.println("Time now : " + t.interDateTime() + " ยังไม่ถึงเวลาสิ้นสุด");
            } else {
                System.out.println("Time now : " + t.interDateTime() + " เลยเวลาสิ้นสุด");
                return;
            }
            TimeUnit.SECONDS.sleep(2);
        }
*/
    }

}
