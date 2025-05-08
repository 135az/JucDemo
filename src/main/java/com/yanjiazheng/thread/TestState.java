package com.yanjiazheng.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j(topic = "c.TestState")
public class TestState {
    /**
     * 主函数，用于演示不同线程的状态
     * 创建多个线程，每个线程执行不同的任务，以展示它们在不同状态下的行为
     */
    public static void main(String[] args) throws IOException {
        // 创建线程t1，它在运行时会输出一条日志信息
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        // 创建线程t2，它会陷入一个无限循环，展示一个始终处于运行状态的线程
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                while (true) { // runnable

                }
            }
        };
        t2.start();

        // 创建并启动线程t3，与t1类似，展示一个简单的运行状态线程
        Thread t3 = new Thread("t3") {
            @Override
            public void run() {
                log.debug("running...");
            }
        };
        t3.start();

        // 创建并启动线程t4，它会获取一个类级别的同步块，然后进入长时间的睡眠状态，展示一个处于计时等待状态的线程
        Thread t4 = new Thread("t4") {
            @Override
            public void run() {
                synchronized (TestState.class) {
                    try {
                        Thread.sleep(1000000); // timed_waiting
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t4.start();

        // 创建并启动线程t5，它会等待线程t2的完成，展示一个处于等待状态的线程
        Thread t5 = new Thread("t5") {
            @Override
            public void run() {
                try {
                    t2.join(); // waiting
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t5.start();

        // 创建并启动线程t6，它会尝试获取一个类级别的同步块，但由于t4已经持有该锁，因此t6会处于阻塞状态
        Thread t6 = new Thread("t6") {
            @Override
            public void run() {
                synchronized (TestState.class) { // blocked
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t6.start();

        // 主线程短暂睡眠后，输出各个线程的状态
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t1 state {}", t1.getState());
        log.debug("t2 state {}", t2.getState());
        log.debug("t3 state {}", t3.getState());
        log.debug("t4 state {}", t4.getState());
        log.debug("t5 state {}", t5.getState());
        log.debug("t6 state {}", t6.getState());

        // 阻塞主线程，等待用户输入，以保持程序运行并观察线程状态
        System.in.read();
    }
}
