package com.company;

import java.util.concurrent.CountDownLatch;

public class Uploader implements Runnable {
    private CountDownLatch cdl;
    private String name;
    private int fileSize;
    private int loadSpeed;
    private Downloader downloader;

    public Uploader(CountDownLatch cdl, String name, int fileSize, int loadSpeed) {
        this.cdl = cdl;
        this.name = name;
        this.fileSize = fileSize;
        this.loadSpeed = loadSpeed;
    }

    public String getName() {
        return name;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getLoadSpeed() {
        return loadSpeed;
    }

    @Override
    public void run() {
        try {
            cdl.countDown();
            System.out.println(name + " загрузка займет приблизительное время " +
                    (fileSize / loadSpeed) + " c");
            cdl.await();
            System.out.println(" ⌛");
            Thread.sleep(fileSize / loadSpeed * 50);
            System.out.println(" ⌛⌛");
            Thread.sleep(fileSize / loadSpeed * 50);
            System.out.println(" ⌛⌛⌛");
            Thread.sleep(fileSize / loadSpeed * 50);
            System.out.println(name + " был загружен в облако");

            if (cdl.getCount() == 0) {
                Thread.sleep(20500);
                System.out.println(name + " был удален с облака");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }


}
