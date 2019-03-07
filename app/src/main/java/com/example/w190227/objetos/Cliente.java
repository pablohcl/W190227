package com.example.w190227.objetos;

public class Cliente {

    private int id;
    private String razao;
    private String fantasia;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String ultimaDataDia;
    private String ultimaDataMes;
    private String ultimaDataAno;
    private String proximaDataDia;
    private String proximaDataMes;
    private String proximaDataAno;
    private String frequencia;
    private String obs;
    private String vendedor;

    public Cliente(){};

    public Cliente(int id, String razao, String fantasia, String cidade, String bairro, String rua, String numero, String ultimaDataDia, String ultimaDataMes, String ultimaDataAno, String proximaDataDia, String proximaDataMes, String proximaDataAno, String frequencia, String obs, String vendedor) {
        this.id = id;
        this.razao = razao;
        this.fantasia = fantasia;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.ultimaDataDia = ultimaDataDia;
        this.ultimaDataMes = ultimaDataMes;
        this.ultimaDataAno = ultimaDataAno;
        this.proximaDataDia = proximaDataDia;
        this.proximaDataMes = proximaDataMes;
        this.proximaDataAno = proximaDataAno;
        this.frequencia = frequencia;
        this.obs = obs;
        this.vendedor = vendedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUltimaDataDia() {
        return ultimaDataDia;
    }

    public void setUltimaDataDia(String ultimaDataDia) {
        this.ultimaDataDia = ultimaDataDia;
    }

    public String getUltimaDataMes() {
        return ultimaDataMes;
    }

    public void setUltimaDataMes(String ultimaDataMes) {
        this.ultimaDataMes = ultimaDataMes;
    }

    public String getUltimaDataAno() {
        return ultimaDataAno;
    }

    public void setUltimaDataAno(String ultimaDataAno) {
        this.ultimaDataAno = ultimaDataAno;
    }

    public String getProximaDataDia() {
        return proximaDataDia;
    }

    public void setProximaDataDia(String proximaDataDia) {
        this.proximaDataDia = proximaDataDia;
    }

    public String getProximaDataMes() {
        return proximaDataMes;
    }

    public void setProximaDataMes(String proximaDataMes) {
        this.proximaDataMes = proximaDataMes;
    }

    public String getProximaDataAno() {
        return proximaDataAno;
    }

    public void setProximaDataAno(String proximaDataAno) {
        this.proximaDataAno = proximaDataAno;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }
}
