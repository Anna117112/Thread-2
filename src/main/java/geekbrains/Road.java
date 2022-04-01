package geekbrains;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Road extends Stage {
    // класс счетчик для подсчета окончания маршрута
    private CountDownLatch countDownLatch1;
    // замок для вывода инфо об 1 победителе
    private Lock lock;


    public Road(CountDownLatch countDownLatch1, int length, Lock lock) {
        this.countDownLatch1 = countDownLatch1;
        this.length = length;
        this.lock = lock;
        this.description = "Дорога " + length + " метров";
    }


    @Override
    public void go(Car c) {
        try {

            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);


            System.out.println(c.getName() + " закончил этап: " + description);
            countDownLatch1.countDown();
            try {
                 // вторая дорга равна 60 . Если ее проехали то получили победителя
                if (length == 60) {
// если ресурс занят ждем по времени. и выполняем задачу. Если ресурс не освободился за это время идем дальше
                    if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                        try {
                            //  if (length == 60) {
                            System.err.println(c.getName() + " - WIN");
                            // }
                            try {
                                Thread.sleep(6000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } finally {
                            lock.unlock();



                        }


                    } else {
                        System.out.print("");

                    }
                }
                } catch(InterruptedException e){
                    e.printStackTrace();
                }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



