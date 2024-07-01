/* Lucas Yukio Fukuda Matsumoto - 2516977 */

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

class Consumidor extends Ator {
    private Caixa frutaComprada = null;
    
    public Consumidor(Deposito dep, Label counter, Caixa fruta, ImageView imageView) {
        super(dep, counter, fruta, imageView);
    }

    @Override
    public void run() {
        while (isRunning) {
            synchronized (this) {
                while (isPaused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            if(imageView.getX() + imageView.getFitWidth()/2 >= SCENE_WIDTH/2){
                System.out.println("Consumidor chegou ao depósito.");
                frutaComprada = dep.retirar();
                if(frutaComprada != null){
                    totalComprado++;
                    Platform.runLater(() -> counter.setText("Caixas compradas: " + totalComprado));
                    System.out.println("Consumidor comprou caixa.");
                    moveLeft();
                }
                toRight = false;
            }
            else if(toRight){
                System.out.println("Consumidor indo ao depósito.");
                // Move da esquerda para o meio (deposito)
                moveRight();
            }
            else{
                if(frutaComprada != null){
                    System.out.println("Consumidor voltando para casa.");
                    moveLeft();
                    Platform.runLater(() -> fruta.getImageView().setX(imageView.getX()));
                }
            }
            
            if(imageView.getX() <= 0){
                toRight = true;
                frutaComprada = null;
                Platform.runLater(() -> fruta.getImageView().setX(-fruta.getImageView().getFitWidth()));
                System.out.println("Consumidor consumindo.");
            }
            
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
