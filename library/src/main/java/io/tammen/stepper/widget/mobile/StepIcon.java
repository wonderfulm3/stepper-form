package io.tammen.stepper.widget.mobile;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tammen Bruccoleri on 12/31/2017.
 */

public class StepIcon {
    public static final int ACTIVE = 0;
    public static final int INACTIVE = 1;
    public static final int EDIT = 2;
    public static final int ERROR = 3;
    public static final int CHECKED = 4;
    public static final int ERROR_ACTIVE = 5;

    public final int stepIcon;

    public StepIcon(@StepIconInterface int stepIcon) {
        this.stepIcon = stepIcon;
    }

    @IntDef({ACTIVE, INACTIVE, EDIT, ERROR, CHECKED, ERROR_ACTIVE})
    @Retention(RetentionPolicy.SOURCE)
    @interface StepIconInterface {
    }
}
