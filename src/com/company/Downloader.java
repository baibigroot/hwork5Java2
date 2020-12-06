package com.company;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Downloader implements Runnable {
    private Uploader uploader;
    private CountDownLatch cdl;
    private Semaphore semaphore;
    private int user;

    public Downloader(Uploader uploader, CountDownLatch cdl, Semaphore semaphore, int user) {
        this.semaphore = semaphore;
        this.cdl = cdl;
        this.uploader = uploader;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(4000);
            cdl.countDown();
            System.out.println("Пользователь " + user + " ожидание загрузки ");
            cdl.await();
            Thread.sleep(4000);
            semaphore.acquire();
            System.out.println("Пользователь " + user + " началась загрузка в "
                    + uploader.getName());
            Thread.sleep(4000/*uploader.getFileSize()/(uploader.getLoadSpeed()*5)*/);
            System.out.println("Пользователь "+ user + " завершил загрузку "
                    + uploader.getName());
            semaphore.release();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
