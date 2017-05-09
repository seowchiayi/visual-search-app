package com.example.chiayi.visualsearchtask;

/**
 * Created by Chia Yi on 08-May-17.
 */

public class Record {
    private String score;
    private double accurate;
    private String status;
    private double feature_avg_time_3;
    private double feature_avg_time_6;
    private double feature_avg_time_9;
    private double feature_avg_time_12;
    private double con_avg_time_3;
    private double con_avg_time_6;
    private double con_avg_time_9;
    private double con_avg_time_12;

    public Record(){

    }

    public String getScore(){return score;}
    public double getAccurate(){return accurate;}
    public String getStatus(){return status;}
    public double getFea_AvgTime3(){return feature_avg_time_3;}
    public double getFea_AvgTime6(){return feature_avg_time_6;}
    public double getFea_AvgTime9(){return feature_avg_time_9;}
    public double getFea_AvgTime12(){return feature_avg_time_12;}

    public double getCon_AvgTime3(){return con_avg_time_3;}
    public double getCon_AvgTime6(){return con_avg_time_6;}
    public double getCon_AvgTime9(){return con_avg_time_9;}
    public double getCon_AvgTime12(){return con_avg_time_12;}


    public void setScore(String score){this.score=score;}
    public void setAccurate(double accurate){this.accurate=accurate;}
    public void setStatus(String status){this.status=status;}
    public void setFea_AvgTime3(double feature_avg_time_3){this.feature_avg_time_3=feature_avg_time_3;}
    public void setFea_AvgTime6(double feature_avg_time_6){this.feature_avg_time_6=feature_avg_time_6;}
    public void setFea_AvgTime9(double feature_avg_time_9){this.feature_avg_time_9=feature_avg_time_9;}
    public void setFea_AvgTime12(double feature_avg_time_12){this.feature_avg_time_12=feature_avg_time_12;}

    public void setCon_AvgTime3(double con_avg_time_3){this.con_avg_time_3=con_avg_time_3;}
    public void setCon_AvgTime6(double con_avg_time_6){this.con_avg_time_6=con_avg_time_6;}
    public void setCon_AvgTime9(double con_avg_time_9){this.con_avg_time_9=con_avg_time_9;}
    public void setCon_AvgTime12(double con_avg_time_12){this.con_avg_time_12=con_avg_time_12;}
}
