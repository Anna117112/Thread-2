package geekbrains;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {

    public static final int CARS_COUNT = 4;

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        // класс ожидания
        CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT);
        // класс счетчик для машин
        CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);
        // клас для поочередного выполнения (ограничиваем выполнение задачь одновременноно 2 мя потоками )
        Semaphore semaphore = new Semaphore(CARS_COUNT / 2);
        // синхронизация по замку на кусок кода в классе дорога
        Lock lock = new ReentrantLock();
        // счетчик на окнчание маршрута по дороге . Умножили на 2 так как каждая машина проезжает по 2м дорогам машин -4
        CountDownLatch countDownLatch2 = new CountDownLatch(CARS_COUNT*2);
        Race race = new Race(new Road(countDownLatch2, 40, lock), new Tunnel(semaphore), new Road(countDownLatch2, 60, lock));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(countDownLatch, cyclicBarrier, race, 20 + (int) (Math.random() * 10));

        }


        for (int i = 0; i < cars.length; i++) {

            new Thread((Runnable) cars[i]).start();
        }

// ждем по счетчику который к клссе Car
        countDownLatch.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

// ждем по счетчику который в классе дорога . Пока все закончат маршрут
        countDownLatch2.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

    }
}



