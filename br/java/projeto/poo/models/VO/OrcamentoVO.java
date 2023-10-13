package br.java.projeto.poo.models.VO;

import java.sql.Date;
import java.util.ArrayList;

public class OrcamentoVO {
    long id;
    String placaVeiculo, cpfCliente;
    double valor;
    ArrayList<PecaVo> pecas;
    ArrayList<ServicoVO> Servicos;
    Date dataDeCriação, dataDeEncerramento;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlacaVeiculo() {
        return this.placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public ArrayList<PecaVo> getPecas() {
        return this.pecas;
    }

    public void setPecas(ArrayList<PecaVo> pecas) {
        this.pecas = pecas;
    }

    public ArrayList<ServicoVO> getServicos() {
        return this.Servicos;
    }

    public void setServicos(ArrayList<ServicoVO> Servicos) {
        this.Servicos = Servicos;
    }

    public Date getDataDeCriação() {
        return this.dataDeCriação;
    }

    public void setDataDeCriação(Date dataDeCriação) {
        this.dataDeCriação = dataDeCriação;
    }

    public Date getDataDeEncerramento() {
        return this.dataDeEncerramento;
    }

    public void setDataDeEncerramento(Date dataDeEncerramento) {
        this.dataDeEncerramento = dataDeEncerramento;
    }

    public String getCpfCliente() { // campo que não existe no banco usado para facilitar queries
        return this.cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

}
