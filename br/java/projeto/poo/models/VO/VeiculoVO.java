package br.java.projeto.poo.models.VO;

import br.java.projeto.poo.exceptions.InvalidCorException;
import br.java.projeto.poo.exceptions.InvalidIdException;
import br.java.projeto.poo.exceptions.InvalidModeloException;
import br.java.projeto.poo.exceptions.InvalidVeiculoException;

public class VeiculoVO {
    private long id;
    private String placa, cor, modelo, cpfDono, tipo, ano;
    private double km;

    public VeiculoVO(long id, String placa, String cor, String modelo, String cpfDono, String tipo, String ano, double km) throws Exception{
        setId(id);
        setPlaca(placa);
        setCor(cor);
        setModelo(modelo);
        setCpfDono(cpfDono);
        setTipo(tipo);
        setAno(ano);
        setKm(km);
    }

    public VeiculoVO(){};

    public long getId() {
        return this.id;
    }

    public void setId(long id) throws InvalidIdException {
        this.id = id;
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) throws InvalidCorException {
        this.cor = cor;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) throws InvalidModeloException {
        this.modelo = modelo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) throws InvalidVeiculoException {
        this.tipo = tipo;
    }

    public String getCpfDono() {
        return this.cpfDono;
    }

    public void setCpfDono(String cpfDono) {
        this.cpfDono = cpfDono;
    }

    public String getAno() {
        return this.ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public double getKm() {
        return this.km;
    }

    public void setKm(double km) {
        this.km = km;
    }


}
