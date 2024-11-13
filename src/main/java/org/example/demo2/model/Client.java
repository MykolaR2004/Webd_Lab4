package org.example.demo2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client{
        private int Id;
        private String Name;
        private String Surname;
        private int Age;
        private String Sex;
        private Date Birthdate;
        private String Hobby;

    public Client(){}
    public Client(
            int Id,
            String Name,
            String Surname,
            int Age,
            String Sex,
            Date Birthdate
    ){
        this.Id = Id;
        this.Name = Name;
        this.Surname = Surname;
        this.Age = Age;
        this.Sex = Sex;
        this.Birthdate = Birthdate;
    }



    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public int getAge() {
        return Age;
    }

    public String getSex() {
        return Sex;
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public void setBirthdate(Date birthdate) {
        Birthdate = birthdate;
    }

    private final List<DatingTable> dates = new ArrayList<DatingTable>();

    public List<DatingTable> getDates() {
        return dates;
    }
}
