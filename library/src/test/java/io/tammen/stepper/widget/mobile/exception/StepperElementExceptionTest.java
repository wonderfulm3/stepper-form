package io.tammen.stepper.widget.mobile.exception;

import org.junit.Test;

/**
 * Created by Tammen Bruccoleri on 4/3/2018.
 */
public class StepperElementExceptionTest {

    @Test(expected = io.tammen.stepper.widget.mobile.exception.StepperElementException.class)
    public void StepperElementException() throws io.tammen.stepper.widget.mobile.exception.StepperElementException {
        throw new StepperElementException(new AnnotationErrorCode(AnnotationErrorCode.INVALID_ELEMENT_SIZE));
    }
}