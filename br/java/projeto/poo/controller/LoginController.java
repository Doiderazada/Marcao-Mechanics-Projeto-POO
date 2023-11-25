package br.java.projeto.poo.controller;
import br.java.projeto.poo.exceptions.ErroDeAuthenticacaoException;
import br.java.projeto.poo.models.BO.FuncionarioBO;
import br.java.projeto.poo.models.VO.FuncionarioVO;
import br.java.projeto.poo.src.App;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginController {

    private FuncionarioBO funcBO = new FuncionarioBO();
    private String textFieldStyle = "-fx-border-color: red; -fx-border-radius: 3px;";

    @FXML private Button logar;
    @FXML private Label erro;
    @FXML private TextField nomeUsuario;
    @FXML private PasswordField password;



    @FXML
    public void initialize() {

        acaoCompTela();
        setInvisibleErro();
    }

    private void acaoCompTela() {
        logar.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                logarUsuario();
            }
            
        });
        nomeUsuario.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleErro();
                nomeUsuario.setStyle(null);
            }
            
        });
        password.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                setInvisibleErro();
                password.setStyle(null);
            }
            
        });
        password.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                if (arg0.getCode() == KeyCode.ENTER) {
                    logarUsuario();
                }
            }
            
        });;
    }

    
    private void logarUsuario() {
        try {
            if(validarCampos()) {    
                FuncionarioVO usuario = new FuncionarioVO();
                usuario.setCpf(nomeUsuario.getText());
                usuario.setSenha(password.getText());

                App.usuarioLogado = funcBO.authenticar(usuario);
                App.navegarEntreTelas("orcamentos");
            }
        } catch (ErroDeAuthenticacaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            erro.setText(e.getMessage());
            erro.setVisible(true);
        }
    }

    private void setInvisibleErro() {
        this.erro.setVisible(false);
    }



    private boolean validarCampos() {
        if (nomeUsuario.getText().isEmpty()) {
            erro.setText("O nome de usuário não pode ser vazio.");
            erro.setVisible(true);
            nomeUsuario.setStyle(textFieldStyle);
            new animatefx.animation.Shake(nomeUsuario).play();
            return false;
        } else {
            if (nomeUsuario.getText().matches("^\\d{3}[.]\\d{3}[.]\\d{3}[-]\\d{2}$")) {
                System.out.println("Nome de usuário válido.");
            } else {
                erro.setText("Nome de usuário inválido.");
                erro.setVisible(true);
                nomeUsuario.setStyle(textFieldStyle);
                new animatefx.animation.Shake(nomeUsuario).play();
                return false;
            }
        }

        if (password.getText().isEmpty()) {
            erro.setText("A senha não pode ser vazia.");
            erro.setVisible(true);
            password.setStyle(textFieldStyle);
            new animatefx.animation.Shake(password).play();
            return false;
        }

        return true;
    }

}
