package io.tammen.stepper.widget.mobile.exception;

/**
 * Created by Tammen Bruccoleri on 3/26/2018.
 */
public class StepperElementException extends RuntimeException {

    private final AnnotationErrorCode annotationErrorCode;

    public StepperElementException(AnnotationErrorCode annotationErrorCode) {
        super();
        this.annotationErrorCode = annotationErrorCode;
    }

    public StepperElementException(String message, AnnotationErrorCode annotationErrorCode) {
        super(message);
        this.annotationErrorCode = annotationErrorCode;
    }

    public StepperElementException(String message, Throwable throwable, AnnotationErrorCode annotationErrorCode) {
        super(message, throwable);
        this.annotationErrorCode = annotationErrorCode;
    }
}
