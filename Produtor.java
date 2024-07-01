/* Lucas Yukio Fukuda Matsumoto - 2516977 */

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

class Produtor extends Ator {
    public Produtor(Deposito dep, Label counter, Caixa fruta, ImageView imageView) {
        super(dep, counter, fruta, imageView);
        toRight = false;
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

            if(imageView.getX() <= SCENE_WIDTH/2 && !toRight){
                dep.colocar(fruta);
                totalProduzido++;
                Platform.runLater(() -> counter.setText("Caixas produzidas: " + totalProduzido));
                toRight = true;
                moveRight();
                System.out.println("Produtor chegou ao depósito.");
                Platform.runLater(() -> fruta.getImageView().setX(SCENE_WIDTH));
            }
            else if(!toRight){
                moveLeft();
                Platform.runLater(() -> fruta.getImageView().setX(imageView.getX()));
                System.out.println("Produtor levando caixa ao depósito.");
            }
            else{
                // Move do meio (deposito) para a direita
                moveRight();
                System.out.println("Produtor voltando à fábrica.");
            }
            
            if(imageView.getX() + imageView.getFitWidth() >= SCENE_WIDTH){
                toRight = false;
                Platform.runLater(() -> fruta.getImageView().setX(imageView.getX()));
                System.out.println("Produtor embalando.");
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
