package com.example.w190227.objetos;

public class Vendedor {

    private int id;
    private String nome;
    private double meta;
    private double vlrAtual;

    public Vendedor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getMeta() {
        return meta;
    }

    public void setMeta(double meta) {
        this.meta = meta;
    }

    public double getVlrAtual() {
        return vlrAtual;
    }

    public void setVlrAtual(double vlrAtual) {
        this.vlrAtual = vlrAtual;
    }
}
