package io.tammen.stepper.widget.mobile;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tammen Bruccoleri on 12/28/2017.
 */

public class StepperType {
    public static final int STEP_TEXT = 0;
    public static final int STEP_DOTS = 1;
    public static final int STEP_PROGRESS = 2;

    public final int stepperType;

    public StepperType(@StepperTypeInterface int stepperType) {
        this.stepperType = stepperType;
    }

    @IntDef({STEP_TEXT, STEP_DOTS, STEP_PROGRESS})
    @Retention(RetentionPolicy.SOURCE)
    @interface StepperTypeInterface {
    }
}
