package geekbrains;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable{

    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    // добавили класс на ожидание
    private CyclicBarrier cyclicBarrier;
    // добавили класс на счетчик
    private CountDownLatch countDownLatch;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(CountDownLatch countDownLatch,CyclicBarrier cyclicBarrier, Race race, int speed) {
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            // счетчик для вывода в консоль инфо Гонка началась после того как все готовы
            countDownLatch.countDown();
            System.out.println(this.name + " готов");
            // ожидание пока все потоки не будут готовы
            cyclicBarrier.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }
}


