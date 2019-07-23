package app.controller;

import app.service.ServiceWebStart;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping({"/api"})
public class RestApiController {
    /*
    @Autowired
    private ServiceWebStart serviceWebStart;
    
    
    @GetMapping(path = {"/web"}, headers = "Accept=application/json;charset=UTF-8")
    public String webStatus() {
        return serviceWebStart.getWeb();
    }
    
    @PutMapping(path = {"/web/{id}"}, headers = "Accept=application/json;charset=UTF-8")
    public String updateWebStatus(@RequestBody String strForm, @PathVariable("id") int id ) {
        JSONObject obj = new JSONObject(strForm);
        String webStatus = obj.getString("web_status");
        return serviceWebStart.updateWebStatus(id, webStatus);
    }  
    
    @GetMapping(path = {"/schedule"}, headers = "Accept=application/json;charset=UTF-8")
    public String webSchedule() {
        return serviceWebStart.getSchedule();
    }
    
    @GetMapping(path = {"/schedule/{id}"}, headers = "Accept=application/json;charset=UTF-8")
    public String webScheduleId(@PathVariable("id") int id) {
        return serviceWebStart.findScheduleById(id);
    } 
    @PutMapping(path = {"/schedule/{id}"}, headers = "Accept=application/json;charset=UTF-8")
    public String updateSchedule(@RequestBody String strForm, @PathVariable("id") int id ) {
        JSONObject obj = new JSONObject(strForm);
        String schedule_name = obj.getString("schedule_name");
        String cron_expression = obj.getString("cron_expression");
        return serviceWebStart.updateSchedule(id, schedule_name, cron_expression);
    }
    
    @PostMapping(path = {"/login"}, headers = "Accept=application/json;charset=UTF-8")
    public String loginUser(@RequestBody String formStr) {
        JSONObject obj = new JSONObject(formStr);
        return null;
    } 

    @PutMapping(path = {"/folder/{id}"}, headers = "Accept=application/json;charset=UTF-8")
    public String updateFolder(@RequestBody String strForm, @PathVariable("id") int id ) {
        JSONObject obj = new JSONObject(strForm);
        return null;
    }
   
    @DeleteMapping(path = {"/folder/{id}"}, headers = "Accept=application/json;charset=UTF-8")
    public String changeStatusFolder(@PathVariable("id") int id) {
        return null;
    }    
 */    
    
}
