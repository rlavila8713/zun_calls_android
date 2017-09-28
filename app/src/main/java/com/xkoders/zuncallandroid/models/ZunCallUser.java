package com.xkoders.zuncallandroid.models;


public class ZunCallUser {
    private int id;
    private int id_seco;
    private String email;
    private String password;
    private String username;
    private String avatar;
    private String name;

    public ZunCallUser() {
    }


    public ZunCallUser(int id, String name, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.name = name;
    }

    public ZunCallUser(int id, int id_seco, String name, String email, String username, String password) {
        this.id_seco = id_seco;
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.name = name;
    }

    public ZunCallUser(int id, int id_seco, String email, String password, String username, String avatar, String name) {
        this.id = id;
        this.id_seco = id_seco;
        this.email = email;
        this.password = password;
        this.username = username;
        this.avatar = avatar;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_seco() {
        return id_seco;
    }

    public void setId_seco(int id_seco) {
        this.id_seco = id_seco;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
