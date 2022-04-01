package geekbrains;


import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    // вводи класс для контроля допуска. Чтобы не допускать всех сразу в тонель
    private Semaphore semaphore;

        public Tunnel(Semaphore semaphore) {
            this.semaphore =semaphore;
            this.length = 80;
            this.description = "Тоннель " + length + " метров";


        }
        @Override
        public void go(Car c) {
            try {
                try {
                    System.out.println(c.getName() + " готовится к этапу(ждет): " +
                            description);
                    // даем заехать 2-м машинам
                    semaphore.acquire();
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(c.getName() + " закончил этап: " +
                            description);
                    // разрешаем заехать в тонель остальным по мере освобождения тонеля но не более 2 машин в тонели 
                    semaphore.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

