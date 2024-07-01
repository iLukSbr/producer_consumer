/* Lucas Yukio Fukuda Matsumoto - 2516977 */

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

abstract class Ator implements Runnable {
    protected static final int PASSO = 5;
    protected static final int SCENE_WIDTH = 600;
    protected static final int SCENE_HEIGHT = 300;
    protected final Deposito dep;
    protected final ImageView imageView;
    protected final Label counter;
    protected Caixa fruta;
    protected int delay = 1000;
    protected int totalProduzido = 0;
    protected int totalComprado = 0;
    protected volatile boolean isRunning = true;
    protected volatile boolean isPaused = false;
    protected boolean toRight = false;

    public Ator(Deposito dep, Label counter, Caixa fruta, ImageView imageView) {
        this.dep = dep;
        this.imageView = imageView;
        this.counter = counter;
        this.fruta = fruta;
        this.delay = 0;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        synchronized (this) {
            isPaused = false;
            notify();
        }
    }

    public void stop() {
        isRunning = false;
        resume();
    }

    public void moveLeft() {
        Platform.runLater(() -> {imageView.setX(imageView.getX() - PASSO);});
    }
    
    public void moveRight() {
        Platform.runLater(() -> {imageView.setX(imageView.getX() + PASSO);});
    }
    
    public ImageView getImageView(){
        return imageView;
    }
}
