/* Lucas Yukio Fukuda Matsumoto - 2516977 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 300;
    private static final int CAPACITY = 100;
    private Deposito dep;
    private Produtor produtor;
    private Consumidor consumidor;
    private Caixa frutaC, frutaP;
    private Thread threadProdutor;
    private Thread threadConsumidor;
    private Label produtorCounter = new Label("Caixas produzidas: 0");
    private Label consumidorCounter = new Label("Caixas compradas: 0");
    private ImageView frutaPView;
    private ImageView frutaCView;
    private ImageView produtorView;
    private ImageView consumidorView;
    private ImageView depositoView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Criar as visualizações de imagem para a fruta, o produtor, o consumidor e o depósito
        frutaCView = new ImageView(new Image("file:resources/caixa.png"));
        frutaPView = new ImageView(new Image("file:resources/caixa.png"));
        produtorView = new ImageView(new Image("file:resources/produtor.png"));
        consumidorView = new ImageView(new Image("file:resources/consumidor.png"));
        depositoView = new ImageView(new Image("file:resources/deposito.png"));

        // Ajustar o tamanho das imagens para serem proporcionais à janela, mantendo a proporção        
        produtorView.setFitWidth(SCENE_WIDTH / 9);
        produtorView.setPreserveRatio(true);
        produtorView.setX(SCENE_WIDTH - produtorView.getFitWidth());
        produtorView.setY(120);
        
        frutaCView.setFitWidth(SCENE_WIDTH / 20);
        frutaCView.setPreserveRatio(true);
        frutaCView.setX(produtorView.getX());
        frutaCView.setY(produtorView.getY() + 10);
        
        frutaPView.setFitWidth(SCENE_WIDTH / 20);
        frutaPView.setPreserveRatio(true);
        frutaPView.setX(produtorView.getX());
        frutaPView.setY(produtorView.getY() + 10);
        
        consumidorView.setFitWidth(SCENE_WIDTH / 7);
        consumidorView.setPreserveRatio(true);
        consumidorView.setX(0);
        consumidorView.setY(110);
        
        depositoView.setFitWidth(SCENE_WIDTH / 3);
        depositoView.setPreserveRatio(true);
        depositoView.setX(SCENE_WIDTH/2 - depositoView.getFitWidth()/2);
        depositoView.setY(50);
        
        consumidorCounter.setLayoutX(0);
        produtorCounter.setLayoutX(SCENE_WIDTH - 150);

        // Criar a fruta com a respectiva imagem
        frutaP = new Caixa("Maçã", frutaPView);
        frutaC = new Caixa("Maçã", frutaCView);

        // Criar o depósito com a respectiva imagem
        dep = new Deposito(CAPACITY);

        // Criar os itens de menu
        MenuItem startItem = new MenuItem("Iniciar");
        MenuItem stopItem = new MenuItem("Parar");

        // Adicionar manipuladores de eventos aos itens de menu
        startItem.setOnAction(event -> startThreads());
        stopItem.setOnAction(event -> stopThreads());

        // Criar o menu e a barra de menu
        Menu menu = new Menu("Controle");
        menu.getItems().addAll(startItem, stopItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        // Ajustar a margem da barra de menu
        menuBar.setPadding(new Insets(10, 10, 10, 10));

        // Create a BorderPane and set it as the root of the scene
        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        // Create a Pane for the counters and images
        Pane pane = new Pane();
        pane.getChildren().addAll(produtorCounter, consumidorCounter, frutaCView, frutaPView, consumidorView, produtorView, depositoView);
        root.setCenter(pane);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startThreads() {
        if (threadProdutor == null || threadConsumidor == null) {
            produtor = new Produtor(dep, produtorCounter, frutaP, produtorView);
            consumidor = new Consumidor(dep, consumidorCounter, frutaC, consumidorView);
            produtor.setDelay(50);
            consumidor.setDelay(50);
            threadProdutor = new Thread(produtor);
            threadConsumidor = new Thread(consumidor);
            threadProdutor.start();
            threadConsumidor.start();
        }
    }

    private void stopThreads() {
        if (threadProdutor != null) {
            produtor.stop();
            threadProdutor = null;
        }
        if (threadConsumidor != null) {
            consumidor.stop();
            threadConsumidor = null;
        }
    }
}
