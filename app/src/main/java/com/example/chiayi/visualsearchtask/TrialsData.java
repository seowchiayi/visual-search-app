package com.example.chiayi.visualsearchtask;

/**
 * Created by Chia Yi on 11-May-17.
 */

public class TrialsData {
    private String player_name="";
    private int trial_no=0;
    private String task="";
    private int dist_no=0;
    private String target_status="";
    private String user_ans="No Answer";
    private String real_answer="Wrong";
    private long response_time=-1;
    private long score_per_trial=0;

    public TrialsData(){

    }
    public String getPlayer_name(){return player_name;}
    public int getTrial_no(){return trial_no;}
    public String getTask(){return task;}
    public int getDist_no(){return dist_no;}
    public String getTarget_status(){return target_status;}
    public String getUser_ans(){return user_ans;}
    public String getReal_answer(){return real_answer;}
    public long getResponse_time(){return response_time;}
    public long getScore_per_trial(){return score_per_trial;}

    public void setPlayer_name(String player_name){this.player_name=player_name;}
    public void setTrial_no(int trial_no){this.trial_no=trial_no;}
    public void setTask(String task){this.task=task;}
    public void setDist_no(int dist_no){this.dist_no=dist_no;}
    public void setTarget_status(String target_status){this.target_status=target_status;}
    public void setUser_ans(String user_ans){this.user_ans=user_ans;}
    public void setReal_answer(String real_answer){this.real_answer=real_answer;}
    public void setResponse_time(long response_time){this.response_time=response_time;}
    public void setScore_per_trial(long score_per_trial){this.score_per_trial=score_per_trial;}
}
