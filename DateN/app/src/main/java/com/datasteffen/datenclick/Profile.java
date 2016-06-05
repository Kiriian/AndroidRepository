package com.datasteffen.datenclick;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by steffen on 23-05-2016.
 */
public class Profile implements Serializable {

    private String _id;
    private String email;
    private String password;
    private String name;
    private int age;
    private String gender;
    private Boolean searchmale;
    private Boolean searchfemale;
    private int searchfromage;
    private int searchtoage;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public Profile() {
    }

    public Profile(String _id, String name, int age, String gender, Boolean searchmale, Boolean searchfemale, int searchfromage, int searchtoage) {
        this._id = _id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.searchmale = searchmale;
        this.searchfemale = searchfemale;
        this.searchfromage = searchfromage;
        this.searchtoage = searchtoage;
    }

    public Profile(String _id, String email, String name, int age, String gender, Boolean searchmale, Boolean searchfemale, int searchfromage, int searchtoage) {
        this._id = _id;
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.searchmale = searchmale;
        this.searchfemale = searchfemale;
        this.searchfromage = searchfromage;
        this.searchtoage = searchtoage;
    }

    public Profile(String _id, String email, String password, String name, int age, String gender, Boolean searchmale, Boolean searchfemale, int searchfromage, int searchtoage) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.searchmale = searchmale;
        this.searchfemale = searchfemale;
        this.searchfromage = searchfromage;
        this.searchtoage = searchtoage;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getSearchmale() {
        return searchmale;
    }

    public void setSearchmale(Boolean searchmale) {
        this.searchmale = searchmale;
    }

    public Boolean getSearchfemale() {
        return searchfemale;
    }

    public void setSearchfemale(Boolean searchfemale) {
        this.searchfemale = searchfemale;
    }

    public int getSearchfromage() {
        return searchfromage;
    }

    public void setSearchfromage(int searchfromage) {
        this.searchfromage = searchfromage;
    }

    public int getSearchtoage() {
        return searchtoage;
    }

    public void setSearchtoage(int searchtoage) {
        this.searchtoage = searchtoage;
    }

    @Override
    public String toString() {
        return "{" +
                "\"_id\":'" + _id + '\'' +
                ", \"email\":'" + email + '\'' +
                ", \"password\":'" + password + '\'' +
                ", \"name\":'" + name + '\'' +
                ", \"age\":" + age +
                ", \"gender\":'" + gender + '\'' +
                ", \"searchmale\":" + searchmale +
                ", \"searchfemale\":" + searchfemale +
                ", \"searchfromage\":" + searchfromage +
                ", \"searchtoage\":" + searchtoage +
                '}';
    }
    public String toJson(){
        JSONObject job = new JSONObject();

        try {
            job.put("_id",getId());
            job.put("email",getEmail());
            job.put("password",getPassword());
            job.put("name",getName());
            job.put("age",getAge());
            job.put("gender",getGender());
            job.put("searchmale",getSearchmale());
            job.put("searchfemale",getSearchfemale());
            job.put("searchfromage",getSearchfromage());
            job.put("searchtoage",getSearchtoage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return job.toString();
    }
}