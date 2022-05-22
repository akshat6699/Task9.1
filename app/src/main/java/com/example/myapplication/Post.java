package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Post implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "location")
    public String location;

    public Post(int id, String type, String name, String phone, String description, Date date, String location) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }

//    public int getId() {
//        return id;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
    public String getLocation() {
        if (location.contains("@:"))
            return location.split("@:")[0];
        else
            return location;
    }

    public String getLatLang() {
        if (location.contains("@:"))
            return location.split("@:")[1];
        else
            return "";
    }
//
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
}
