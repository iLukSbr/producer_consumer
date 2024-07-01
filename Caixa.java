import javafx.scene.image.ImageView;
import javafx.application.Platform;

public class Caixa {
    protected static final int PASSO = 10;
    private final String nome;
    private final ImageView imageView;

    public Caixa(String nome, ImageView imageView) {
        this.nome = nome;
        this.imageView = imageView;
    }

    public String getNome() {
        return nome;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void moveLeft() {
        Platform.runLater(() -> {imageView.setX(imageView.getX() - PASSO);});
    }
    
    public void moveRight() {
        Platform.runLater(() -> {imageView.setX(imageView.getX() + PASSO);});
    }

    @Override
    public String toString() {
        return nome;
    }
}
