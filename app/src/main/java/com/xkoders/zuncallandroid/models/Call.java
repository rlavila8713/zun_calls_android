package com.xkoders.zuncallandroid.models;

import com.xkoders.zuncallandroid.R;

import java.util.Random;


public class Call {
    private int idCall;
    private int idImagen;
    private String digitos;
    private String name;
    private String fecha;
    private String cost;
    private String duration;


    public Call() {
    }

    public Call(String phoneNumber, String cost) {
        this.digitos = phoneNumber;
        this.cost = cost;
    }

    public Call(int idCall, String phoneNumber, String name, String cost) {
        this.idImagen = getRandomPictureDrawable();
        this.digitos = phoneNumber;
        this.name = name;
        this.cost = cost;
        this.idCall = idCall;
    }

    public Call(int idCall, String digitos, String name, String cost, String duration) {
        this.idImagen = getRandomPictureDrawable();
        this.digitos = digitos;
        this.name = "Desconocido";
        this.cost = cost;
        this.duration = String.valueOf(Integer.parseInt(duration)/60);
        this.idCall = idCall;
    }
    public Call(int idCall, String digitos, String name, String fecha, String cost, String duration) {
        this.idImagen = getRandomPictureDrawable();
        this.digitos = digitos;
        this.name = "Desconocido";
        this.fecha = fecha;
        this.cost = cost;
        this.duration = String.valueOf(Integer.parseInt(duration)/60);
        this.idCall = idCall;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdCall() {
        return idCall;
    }

    public void setIdCall(int idCall) {
        this.idCall = idCall;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public String getDigitos() {
        return digitos;
    }

    public void setDigitos(String digitos) {
        this.digitos = digitos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private int getRandomPictureDrawable() {
        Random rnd = new Random();
        switch (rnd.nextInt(8)) {
            default:
            case 0:
                return R.drawable.ic_account_circle_black_24dp;
            case 1:
                return R.drawable.ic_account_circle_black_24dp;
            case 2:
                return R.drawable.ic_account_circle_black_24dp;
            case 3:
                return R.drawable.ic_account_circle_black_24dp;
            case 4:
                return R.drawable.ic_account_circle_black_24dp;
            case 5:
                return R.drawable.ic_account_circle_black_24dp;
            case 6:
                return R.drawable.ic_account_circle_black_24dp;
            case 7:
                return R.drawable.ic_account_circle_black_24dp;
        }
    }
}
