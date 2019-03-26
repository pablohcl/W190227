package com.example.w190227.objetos;

public class Visita {

    private int id;
    private int cliente;
    private String dataDaVisita;
    private String obs;
    private int positivado;
    private double latitude;
    private double longitude;

    public Visita() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getDataDaVisita() {
        return dataDaVisita;
    }

    public void setDataDaVisita(String dataDaVisita) {
        this.dataDaVisita = dataDaVisita;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getPositivado() {
        return positivado;
    }

    public void setPositivado(int positivado) {
        this.positivado = positivado;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
