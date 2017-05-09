package com.example.chiayi.visualsearchtask;

/**
 * Created by Chia Yi on 04-May-17.
 */

class User {
    private String name;
    private String age;
    private String gender;
    private String matric;

    public User(){

    }

    public String getName(){
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getGender(){
        return gender;
    }
    public String getMatric(){return matric;}

    public void setName(String name){
        this.name=name;
    }
    public void setAge(String age){
        this.age=age;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    public void setMatric(String matric){this.matric=matric;}

}
