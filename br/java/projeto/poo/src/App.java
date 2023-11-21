package br.java.projeto.poo.src;

import br.java.projeto.poo.models.VO.FuncionarioVO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
 
public class App extends Application {
    private static Stage janela;
    private static GerenciadorDeTelas gr = new GerenciadorDeTelas();
    public static FuncionarioVO usuarioLogado = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            janela = primaryStage;
            janela.setTitle("Marção Mechanics");
            Image logo = new Image(getClass().getResourceAsStream("../public/MarcãoIcon566x566.png"));
            janela.getIcons().add(logo);
            janela.setResizable(true);
            janela.setMinHeight(800);
            janela.setMinWidth(1024);
            Scene telaInicial = usuarioLogado != null ? gr.carregarNovaTela("orcamentos") : gr.carregarNovaTela("login");
            janela.setScene(telaInicial);
            janela.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * <p> Swithes up screens based on the existing screens in {@link br.java.projeto.poo.src.TelasDisponiveis#TelasDisponiveis() TelasDisponiveis()}.
     * @param nomeTela name of the screen to switch to.
     */
    public static void navegarEntreTelas(String nomeTela) {
        double newW = janela.getScene().getWindow().getWidth();
        double newH = janela.getScene().getWindow().getHeight();
        janela.setY(janela.getScene().getWindow().getY());
        janela.setScene(gr.carregarNovaTela(nomeTela));
        janela.setWidth(newW);
        janela.setHeight(newH);
    }
}