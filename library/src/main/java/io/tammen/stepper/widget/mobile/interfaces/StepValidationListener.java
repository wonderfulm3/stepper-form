package io.tammen.stepper.widget.mobile.interfaces;

/**
 * Created by Tammen Bruccoleri on 8/18/2018.
 */
public interface StepValidationListener {
    //Informs the library that the step is currently being validated
    void isStepInValidationState(boolean validating);

    //Informs the library that the step is valid/not valid If the step was in a state of validation,
    //the library will handle putting the step in a state of false for a validation state
    void isStepValid(boolean valid);

    //Cancels the current step
    void cancelStep();
}
