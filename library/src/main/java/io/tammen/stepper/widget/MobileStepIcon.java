package io.tammen.stepper.widget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tammen Bruccoleri on 12/31/2017.
 *
 */

public enum MobileStepIcon {
    ACTIVE(0),
    INACTIVE(1),
    EDIT(2),
    ERROR(3),
    CHECKED(4),
    ERROR_ACTIVE(5);

    private static Map map = new HashMap<>();

    static {
        for (MobileStepIcon mobileStepIcon : MobileStepIcon.values()) {
            map.put(mobileStepIcon.value, mobileStepIcon);
        }
    }

    private int value;

    MobileStepIcon(int value) {
        this.value = value;
    }

    public static MobileStepIcon valueOf(int mobileStepIcon) {
        return (MobileStepIcon) map.get(mobileStepIcon);
    }

    public int getValue() {
        return value;
    }
}
