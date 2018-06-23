package io.tammen.stepper.widget.mobile.render;

/**
 * Created by Tammen Bruccoleri on 6/16/2018.
 */
public class StepElementValidator {
    private static volatile StepElementValidator instance = null;

    private StepElementValidator() {
    }

    public static StepElementValidator getInstance() {
        if (instance == null) {
            synchronized (StepElementValidator.class) {
                if (instance == null) {
                    instance = new StepElementValidator();
                }
            }
        }
        return instance;
    }
}
