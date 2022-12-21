package com.example.my_project.entity;

public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String password;
    private String sex;
    private Integer level;
    private String gmtCreate;

    public User (){
    }
    public User(Integer id, String name, String accountId, String password, String sex, Integer level, String gmtCreate) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
        this.password = password;
        this.sex = sex;
        this.level = level;
        this.gmtCreate = gmtCreate;
    }

    //User的实体类
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId='" + accountId + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", level=" + level +
                ", gmtCreate='" + gmtCreate + '\'' +
                '}';
    }

}
