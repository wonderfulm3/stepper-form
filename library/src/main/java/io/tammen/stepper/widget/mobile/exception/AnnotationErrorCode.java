package io.tammen.stepper.widget.mobile.exception;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Tammen Bruccoleri on 3/31/2018.
 */
public class AnnotationErrorCode {
    public static final int INVALID_ELEMENT_SIZE = 0;
    public static final int INVALID_BUILDER_OPTIONS = 1;

    public AnnotationErrorCode(@ErrorCode int errorCode) {
    }

    @IntDef({INVALID_ELEMENT_SIZE, INVALID_BUILDER_OPTIONS})
    @Retention(RetentionPolicy.SOURCE)
    @interface ErrorCode {
    }
}
