package io.tammen.stepper.widget.mobile;

import android.util.SparseArray;

/**
 * Created by Tammen Bruccoleri on 12/31/2017.
 *
 */

public enum StepIcon {
    ACTIVE(0),
    INACTIVE(1),
    EDIT(2),
    ERROR(3),
    CHECKED(4),
    ERROR_ACTIVE(5);

    private static SparseArray<StepIcon> map = new SparseArray<>();

    static {
        for (StepIcon stepIcon : StepIcon.values()) {
            map.put(stepIcon.value, stepIcon);
        }
    }

    private int value;

    StepIcon(int value) {
        this.value = value;
    }

    public static StepIcon valueOf(int mobileStepIcon) {
        return map.get(mobileStepIcon);
    }

    public int getValue() {
        return value;
    }
}
