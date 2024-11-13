package org.example.demo2.model;

import java.sql.Date;

public class DatingTable {
    private int id;
    private String clientOne;
    private int userOneId;
    private String clientTwo;
    private int userTwoId;
    private Date date;

    public DatingTable(
            int id,
            String clientOne,
            int clientOneId,
            String clientTwo,
            int clientTwoId,
            Date date
    ){
        this.id = id;
        this.clientOne = clientOne;
        this.userOneId = clientOneId;
        this.clientTwo = clientTwo;
        this.userTwoId = clientTwoId;
        this.date = date;
    }

    public DatingTable(){}

    public int getId() {
        return id;
    }

    public int getUserOneId() {
        return userOneId;
    }

    public int getUserTwoId() {
        return userTwoId;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserOneId(int userOneId) {
        this.userOneId = userOneId;
    }

    public void setUserTwoId(int userTwoId) {
        this.userTwoId = userTwoId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClientOne() {
        return clientOne;
    }

    public void setClientOne(String clientOne) {
        this.clientOne = clientOne;
    }

    public String getClientTwo() {
        return clientTwo;
    }

    public void setClientTwo(String clientTwo) {
        this.clientTwo = clientTwo;
    }
}
