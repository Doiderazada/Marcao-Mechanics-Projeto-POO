package br.java.projeto.poo.models.BO;

import java.util.ArrayList;

public interface BaseInterfaceBO<VO> {
    public ArrayList<VO> listar() throws Exception; 
    public Object buscarPorId(int id) throws Exception;
    public boolean inserir(VO ob) throws Exception;
    public boolean atualizar(VO ob) throws Exception;
    public Boolean deletar(VO ob) throws Exception;
}
