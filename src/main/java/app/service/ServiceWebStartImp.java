package app.service;

import app.dao.Database;
import app.dao.Redis;
import app.function.ApiResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class ServiceWebStartImp implements ServiceWebStart {

    @Autowired
    private Database db;

    @Autowired
    private Redis rd;
    
    @Autowired
    private ApiResponse apiResponse;    

    @Override
    public void start() {
        JSONObject json = new JSONObject();
        Jedis redis = rd.connect();
        String sql = "select * from web where web_status = 1";
        //String sql = "select * from web where web_status = 1 AND (detail != 'present_results' OR detail IS NULL)";
        try {
            Connection conn = db.connectDatase();
            ResultSet result = db.getResultSet(conn, sql);
            while (result.next()) {
                json.put("web_id", result.getInt("web_id"));
                json.put("web_name", result.getString("web_name"));
                json.put("link", result.getString("link"));
                json.put("type", result.getString("type"));
                json.put("detail", result.getString("detail"));
                json.put("web_status", result.getString("web_status"));
                json.put("season", result.getString("season"));
                json.put("base_logo_link", result.getString("base_logo_link"));
                redis.rpush("link_queue", json.toString());
            }
            conn.close();
            redis.close();
        } catch (SQLException | JSONException e) {
            e.getMessage();
        }
    }
    
    @Override
    public void startPresentResults() {
        JSONObject json = new JSONObject();
        Jedis redis = rd.connect();
        String sql = "select * from web where web_status = 1 AND detail = 'present_results'";
        try {
            Connection conn = db.connectDatase();
            ResultSet result = db.getResultSet(conn, sql);
            while (result.next()) {
                json.put("web_id", result.getInt("web_id"));
                json.put("web_name", result.getString("web_name"));
                json.put("link", result.getString("link"));
                json.put("type", result.getString("type"));
                json.put("detail", result.getString("detail"));
                json.put("web_status", result.getString("web_status"));
                json.put("season", result.getString("season"));
                json.put("base_logo_link", result.getString("base_logo_link"));
                redis.rpush("link_queue", json.toString());
            }
            conn.close();
            redis.close();
        } catch (SQLException | JSONException e) {
            e.getMessage();
        }
    }
    
    @Override
    public String getWeb() {
        String sql = "select * from web";
        List<String> listVarchar = new ArrayList<String>();
        List<String> listChar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        List<JSONObject> list = new ArrayList<>();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INT") {
                        listInt.add(columns.getColumnName(i));
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i));
                    } else {
                        listVarchar.add(columns.getColumnName(i));
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(listChar.get(i), true);
                        } else {
                            obj.put(listChar.get(i), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
    }

    @Override
    public String updateWebStatus(int id ,String webStatus) {
        String sql = "UPDATE web SET web_status = '"+webStatus+"' WHERE web_id ="+id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);   
        return apiResponse.status(200, "อัพเดทสถานะเรียบร้อยแล้ว");
    }

    @Override
    public String getSchedule() {
        String sql = "select * from schedule";
        List<String> listVarchar = new ArrayList<String>();
        List<String> listChar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        List<JSONObject> list = new ArrayList<>();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INT") {
                        listInt.add(columns.getColumnName(i));
                    } else if (columns.getColumnTypeName(i) == "CHAR") {
                        listChar.add(columns.getColumnName(i));
                    } else {
                        listVarchar.add(columns.getColumnName(i));
                    }
                }
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
                    }
                    for (i = 0; i < listChar.size(); i++) {
                        String value = rs.getString(listChar.get(i));
                        if ("1".equals(value)) {
                            obj.put(listChar.get(i), true);
                        } else {
                            obj.put(listChar.get(i), false);
                        }
                    }

                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return list.toString();
    }

    @Override
    public String findScheduleById(int id) {
        String sql = "select * FROM schedule WHERE schedule_id =" + id;
        List<String> listVarchar = new ArrayList<String>();
        List<String> listInt = new ArrayList<String>();
        JSONObject obj = new JSONObject();
        try {
            ResultSet rs = db.getResultSet(db.connectDatase(), sql);
            if (rs != null) {
                ResultSetMetaData columns = rs.getMetaData();
                int i = 0;
                while (i < columns.getColumnCount()) {
                    i++;
                    if (columns.getColumnTypeName(i) == "INT") {
                        listInt.add(columns.getColumnName(i));
                    } else {
                        listVarchar.add(columns.getColumnName(i));
                    }
                }
                while (rs.next()) {
                    obj = new JSONObject();
                    for (i = 0; i < listInt.size(); i++) {
                        obj.put(listInt.get(i), rs.getInt(listInt.get(i)));
                    }
                    for (i = 0; i < listVarchar.size(); i++) {
                        obj.put(listVarchar.get(i), rs.getString(listVarchar.get(i)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnect(db.connectDatase());
        }
        return obj.toString();
    }

    @Override
    public String updateSchedule(int id, String scheduleName, String cronExpression) {
        String sql = "UPDATE schedule SET schedule_name = '"+scheduleName+"' , cron_expression = '"+cronExpression+"' WHERE schedule_id ="+id;
        Connection conn = db.connectDatase();
        db.executeQuery(conn, sql);
        db.closeConnect(conn);   
        return apiResponse.status(200, "อัพเดทข้อมูลเรียบร้อยแล้ว");
    }

}
