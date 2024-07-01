/* Lucas Yukio Fukuda Matsumoto - 2516977 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deposito {
    private final Queue<Caixa> frutas = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final int capacidadeMaxima;

    public Deposito(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public void colocar(Caixa fruta) {
        lock.lock();
        try {
            while (frutas.size() == capacidadeMaxima) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrompida durante a colocação", e);
                }
            }
            frutas.add(fruta);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Caixa retirar() {
        lock.lock();
        try {
            while (frutas.isEmpty()) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrompida durante a retirada", e);
                }
            }
            Caixa fruta = frutas.poll();
            notFull.signal();
            return fruta;
        } finally {
            lock.unlock();
        }
    }
}
