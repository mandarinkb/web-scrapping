
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QuestionRu {
    
  public void questionPage(String url) throws IOException{
      Document doc = Jsoup.connect(url).timeout(60 * 1000).get();
      
      Element ele = doc.getElementsByClass("odd").first();  // เลือก class odd แรกสุด   
      String question = ele.getElementsByIndexEquals(1).text();       // หัวข้อคำถาม
      String answer = ele.getElementsByIndexEquals(2).text();         // คำตอบ
      String groupQuestion = ele.getElementsByIndexEquals(3).text();  // หมวดหมู่คำถาม
      String countUserRead = ele.getElementsByIndexEquals(4).text();  // จำนวนผู้อ่าน
      String userQuestion = ele.getElementsByIndexEquals(5).text();   // ผู้ตั้งคำถาม
      String date = ele.getElementsByIndexEquals(6).text();           // วันที่ตั้งคำถาม
      
      /*
      //String question = ele.select("a").first().text();             // หัวข้อคำถาม  
      //String date = ele.getElementsByClass("span2").last().text();  // วันที่ตั้งคำถาม
      System.out.println(question);
      System.out.println(date);
      */
      System.out.println(question);
      System.out.println(answer);
      System.out.println(groupQuestion);
      System.out.println(countUserRead);
      System.out.println(userQuestion);
      System.out.println(date);

  }
  
    public void complaintPage(String url) throws IOException{
      Document doc = Jsoup.connect(url).timeout(60 * 1000).get();
      
      Element ele = doc.getElementsByClass("odd").first();  // เลือก class odd แรกสุด   
      String complaint = ele.getElementsByIndexEquals(1).text();      // ข้อร้องเรียน
      String countUserRead = ele.getElementsByIndexEquals(2).text();  // จำนวนผู้อ่าน
      String complaintUser = ele.getElementsByIndexEquals(3).text();  // ผู้ร้องเรียน
      String date = ele.getElementsByIndexEquals(4).text();           // วันที่ร้องเรียน
      
      System.out.println(complaint);
      System.out.println(countUserRead);
      System.out.println(complaintUser);
      System.out.println(date);
  }
    
    
    
    public static void main(String[] args) throws IOException{
        //String url = "http://www.question.ru.ac.th/regis/question/indexGroup.ru?gid=1";  //  การรับสมัครและแนะแนวการศึกษา
        //String url = "http://www.question.ru.ac.th/regis/question/indexGroup.ru?gid=2";  //  การลงทะเบียนเรียนและการจัดสอบ
        //String url = "http://www.question.ru.ac.th/regis/question/indexGroup.ru?gid=3";  //  ทะเบียนประวัตินักศึกษา
        //String url = "http://www.question.ru.ac.th/regis/question/indexGroup.ru?gid=4";  //  ประมวลผลการศึกษาและหนังสือสำคัญ
        //String url = "http://www.question.ru.ac.th/regis/question/indexGroup.ru?gid=6";  //  หลักสูตร
        //String url = "http://www.question.ru.ac.th/regis/question/indexGroup.ru?gid=5";  //  อื่น ๆ
        String urlComplaint = "http://www.question.ru.ac.th/regis/complaint/index.ru";     //  ข้อร้องเรียน
        
        QuestionRu q = new QuestionRu();
        //q.questionPage(url);
        q.complaintPage(urlComplaint);
        
    }
}
