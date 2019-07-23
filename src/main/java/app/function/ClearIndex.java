
package app.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClearIndex {
    @Autowired
    private Elasticsearch els;   
    
    public void presentResultsIndex(){
        els.deleteIndex("present_results_thaipremierleague");
        els.deleteIndex("present_results_premierleague");
    }
    public void presentResultsThaipremierleague(){
        els.deleteIndex("present_results_thaipremierleague");
    }
    public void presentResultsPremierleague(){
        els.deleteIndex("present_results_premierleague");
    }
    
    
    public void allIndex() {  // ยกเว้น presentResultsIndex
        els.deleteIndex("stats_of_team_thaipremierleague");
        els.deleteIndex("stats_of_team_premierleague");
        els.deleteIndex("team_detail_thaipremierleague");
        els.deleteIndex("team_detail_premierleague");
        els.deleteIndex("player_profile_thaipremierleague");
        els.deleteIndex("player_profile_premierleague");
        els.deleteIndex("staff_thaipremierleague");
        els.deleteIndex("staff_premierleague");
        els.deleteIndex("results_thaipremierleague");
        els.deleteIndex("results_premierleague");
        els.deleteIndex("fixtures_thaipremierleague");
        els.deleteIndex("fixtures_premierleague");
        els.deleteIndex("league_table_thaipremierleague");
        els.deleteIndex("league_table_premierleague");
        els.deleteIndex("statistics_thaipremierleague");
        els.deleteIndex("statistics_premierleague");
    }
}
