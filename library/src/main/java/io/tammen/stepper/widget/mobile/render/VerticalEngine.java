package io.tammen.stepper.widget.mobile.render;

/**
 * Created by Tammen Bruccoleri on 6/16/2018.
 */
public class VerticalEngine {
    private static volatile VerticalEngine instance = null;

    private VerticalEngine() {
    }

    public static VerticalEngine getInstance() {
        if (instance == null) {
            synchronized (VerticalEngine.class) {
                if (instance == null) {
                    instance = new VerticalEngine();
                }
            }
        }
        return instance;
    }
}
