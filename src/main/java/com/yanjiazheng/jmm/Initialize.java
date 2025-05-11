package com.yanjiazheng.jmm;

public class Initialize {

    private boolean initialized = false;

    public void init() {

        synchronized (this) {
            if (initialized) {
                return;
            }
            doInit();
            initialized = true;
        }
    }

    private void doInit() {

    }
}
