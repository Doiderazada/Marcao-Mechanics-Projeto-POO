package br.java.projeto.poo.controller.Funcionarios;

import br.java.projeto.poo.controller.ModalsController;
import br.java.projeto.poo.models.BO.FuncionarioBO;
import br.java.projeto.poo.models.VO.FuncionarioVO;
import br.java.projeto.poo.models.VO.TelefoneVO;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditarFuncionariosController {
    private FuncionarioBO funcionarioBO = new FuncionarioBO();
    private ModalsController modalsController = new ModalsController();
    private FuncionarioVO funcEditar;
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";
    
    @FXML private Button cadastrar;
    @FXML private Button fechar;
    @FXML private Label mensagemDeErro;
    @FXML private TextField cpf;
    @FXML private TextField endereco;
    @FXML private TextField nivel;
    @FXML private TextField nome;
    @FXML private TextField salario;
    @FXML private TextField telefone;






    
    public void initialize(FuncionarioVO funcionario) {
        
        setInvisibleEdit();
        this.funcEditar = funcionario;
        acaoCompTela();
        preencherCampos(funcEditar);
        
    }



    private void acaoCompTela(){
        cadastrar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                editarFuncionario();
            }
            
        });

        fechar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                fecharModal();
            }
            
        });

        nome.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                nome.setStyle(null);
            }
            
        });
        
        cpf.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                cpf.setStyle(null);
            }
            
        });
        cpf.setOnKeyReleased(new EventHandler<KeyEvent>() {

            

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        
        endereco.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                endereco.setStyle(null);
            }
            
        });
        
        nivel.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                nivel.setStyle(null);
            }
            
        });
        
        telefone.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                telefone.setStyle(null);
            }
            
        });
        telefone.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                autoComplete();
            }
            
        });
        
        salario.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleEdit();
                salario.setStyle(null);
            }
            
        });
    }








    private void autoComplete(){
        // auto-complete cpf
        if(cpf.getText().length() == 3){
            cpf.setText(cpf.getText() + ".");
            cpf.end();
        };

        if(cpf.getText().length() == 7){
            cpf.setText(cpf.getText() + ".");
            cpf.end();
        };
        
        if(cpf.getText().length() == 11){
            cpf.setText(cpf.getText() + "-");
            cpf.end();
        };
        
        
        
        // auto-complete telefone
        if(telefone.getText().length() == 2){
            telefone.setText(telefone.getText() + " ");
            telefone.end();
        };
        
        if(telefone.getText().length() == 8){
            telefone.setText(telefone.getText() + "-");
            telefone.end();
        };

    }








    
    private void editarFuncionario() {
        try {
            if (validarCampos()) {
    
                TelefoneVO telefoneVO = funcEditar.getTelefone(); 
                
                telefoneVO.setNumero(telefone.getText());
                
                funcEditar.setCpf(cpf.getText());
                funcEditar.setEndereco((funcEditar.getEndereco().pegarValoresComoString(endereco.getText())));
                funcEditar.setNivel(Integer.parseInt(nivel.getText()));
                funcEditar.setNome(nome.getText());
                funcEditar.setSalario(Double.parseDouble(salario.getText()));
                funcEditar.setTelefone(telefoneVO);

                funcionarioBO.atualizar(funcEditar);
                fecharModal();
                modalsController.abrirModalSucesso("Funcionário editado com sucesso");;
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            this.mensagemDeErro.setText(e.getMessage());
            this.mensagemDeErro.setVisible(true);
        } 
    }


    private void fecharModal() {
        Stage stage = (Stage) this.fechar.getScene().getWindow();
        stage.close();
    }







    public void preencherCampos(FuncionarioVO vo) {
       try {
            
            this.cpf.setText(vo.getCpf());
            this.nome.setText(vo.getNome());
            this.salario.setText(String.valueOf(vo.getSalario()));
            this.nivel.setText(String.valueOf(vo.getNivel()));
            this.endereco.setText(vo.getEndereco().toString());
            this.telefone.setText(vo.getTelefone().getNumero());
       } catch (Exception e) {
            System.out.println(e.getMessage());
            modalsController.abrirModalFalha(e.getMessage());
       }
    }








    private boolean validarCampos() {
        if (nome.getText().isEmpty()) {
            mensagemDeErro.setText("O nome não pode ser vazio");
            mensagemDeErro.setVisible(true);
            nome.setStyle(textFieldStyle);
            new animatefx.animation.Shake(nome).play();
            return false;
        } else {
            if (nome.getText().matches("^[a-zA-Z^\s.áéíóúâêôãõ]{1,50}$")) {
                System.out.println("Campo nome válido");
            } else {
                mensagemDeErro.setText("Formato de nome inválido");
                mensagemDeErro.setVisible(true);
                nome.setStyle(textFieldStyle);
                new animatefx.animation.Shake(nome).play();
                return false;
            }
        }


        if (cpf.getText().isEmpty()) {
            mensagemDeErro.setText("O CPF não pode ser vazio");
            mensagemDeErro.setVisible(true);
            cpf.setStyle(textFieldStyle);
            new animatefx.animation.Shake(cpf).play();
            return false;
        } else {
            if (cpf.getText().matches("^[\\d]{3}[.][\\d]{3}[.][\\d]{3}[-][\\d]{2}$")) {
                System.out.println("Campo CPF válido");
            } else {
                mensagemDeErro.setText("Formato de CPF inválido");
                mensagemDeErro.setVisible(true);
                cpf.setStyle(textFieldStyle);
                new animatefx.animation.Shake(cpf).play();
                return false;
            }
        }
        

        if (endereco.getText().isEmpty()) {
            mensagemDeErro.setText("O endereço não pode ser vazio");
            mensagemDeErro.setVisible(true);
            endereco.setStyle(textFieldStyle);
            new animatefx.animation.Shake(endereco).play();
            return false;
        } else {
            if (endereco.getText().matches("^[a-zA-Z\\s^áãàéêèíóõú.,]{1,50}[\\d^,]{1,5}[a-zA-Z\\s^áãàéêèíóõú,]{1,50}")) {
                System.out.println("Campo endereço válido");
            } else {
                mensagemDeErro.setText("Formato de endereço inválido");
                mensagemDeErro.setVisible(true);
                endereco.setStyle(textFieldStyle);
                new animatefx.animation.Shake(endereco).play();
                return false;
            }
        }


        if (nivel.getText().isEmpty()) {
            mensagemDeErro.setText("O nível não pode ser vazio");
            mensagemDeErro.setVisible(true);
            nivel.setStyle(textFieldStyle);
            new animatefx.animation.Shake(nivel).play();
            return false;
        } else {
            if (nivel.getText().matches("^[1-3]{1}")) {
                System.out.println("Campo nível válido");
            } else {
                mensagemDeErro.setText("Formato de nível inválido");
                mensagemDeErro.setVisible(true);
                nivel.setStyle(textFieldStyle);
                new animatefx.animation.Shake(nivel).play();
                return false;
            }
        }
        
        
        if (telefone.getText().isEmpty()) {
            mensagemDeErro.setText("O telefone não pode ser vazio");
            mensagemDeErro.setVisible(true);
            telefone.setStyle(textFieldStyle);
            new animatefx.animation.Shake(telefone).play();
            return false;
        } else {
            if (telefone.getText().matches("[\\d]{2}[\\s][\\d]{5}[\\-][\\d]{4}")) {
                System.out.println("Campo telefone válido");
            } else {
                mensagemDeErro.setText("Formato de telefone inválido");
                mensagemDeErro.setVisible(true);
                telefone.setStyle(textFieldStyle);
                new animatefx.animation.Shake(telefone).play();
                return false;
            }
        }


        if (salario.getText().isEmpty()) {
            mensagemDeErro.setText("O salário não pode ser vazio");
            mensagemDeErro.setVisible(true);
            salario.setStyle(textFieldStyle);
            new animatefx.animation.Shake(salario).play();
            return false;
        } else {
            if (salario.getText().matches("^[1-9^.]{1}[0-9^.]{1,8}$")) {
                System.out.println("Campo salário válido");
            } else {
                mensagemDeErro.setText("Formato de salário inválido");
                mensagemDeErro.setVisible(true);
                salario.setStyle(textFieldStyle);
                new animatefx.animation.Shake(salario).play();
                return false;
            }
        }


        return true;
    }



    public FuncionarioVO getFuncionarioEditar(){
        return this.funcEditar;
    }


    private void setInvisibleEdit(){
        this.mensagemDeErro.setVisible(false);
    }
}