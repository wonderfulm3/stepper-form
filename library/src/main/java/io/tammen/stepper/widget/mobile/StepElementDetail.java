package io.tammen.stepper.widget.mobile;

import android.support.annotation.IntRange;
import android.support.annotation.RestrictTo;
import android.support.annotation.Size;
import android.util.Log;
import android.view.View;

import io.tammen.stepper.widget.mobile.exception.AnnotationErrorCode;
import io.tammen.stepper.widget.mobile.exception.StepperElementException;
import io.tammen.stepper.widget.mobile.interfaces.StepButtonListener;
import io.tammen.stepper.widget.mobile.interfaces.StepValidationListener;

/**
 * Created by Tammen Bruccoleri on 1/5/2018.
 */

public class StepElementDetail {
    StepButtonListener stepButtonListener;
    @Size(min = 1)
    public String stepTitle;
    @Size(min = 1)
    public String stepSubText;
    @IntRange(from = 1, to = 255)
    public int stepNumber;
    private boolean stepOptional;
    boolean isStepExpanded;
    boolean isStepDirty;
    @StepIcon.StepIconInterface
    private int stepIcon;
    private String TAG = this.getClass().getSimpleName();
    private boolean stepRequiresValidation;
    private boolean stepContinueOnValidationFailure;
    private View stepView;
    private boolean isStepValid;
    public StepValidationListener stepValidationListener = new StepValidationListener() {
        @Override
        public void isStepValid(boolean stepValid) {
            Log.d(TAG, "Step valid: " + stepValid);
            isStepValid = stepValid;
        }
    };

    StepElementDetail() {
    }

    private StepElementDetail(StepElementBuilder builder) {
        this.stepNumber = builder.stepNumber;
        this.stepIcon = builder.stepIcon;
        this.stepTitle = builder.stepTitle;
        this.stepSubText = builder.stepSubText;
        this.stepOptional = builder.stepOptional;
        this.stepRequiresValidation = builder.stepRequiresValidation;
        this.stepContinueOnValidationFailure = builder.stepContinueOnValidationFailure;
        this.stepView = builder.stepView;
        this.stepButtonListener = builder.stepButtonListener;
    }

    @StepIcon.StepIconInterface
    public int getStepIcon() {
        return stepIcon;
    }

    void setStepIcon(@StepIcon.StepIconInterface int stepIcon) {
        this.stepIcon = stepIcon;
    }

    public void setStepTitle(@Size(min = 1) String stepTitle) {
        this.stepTitle = stepTitle;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public boolean getStepRequiresValidation() {
        return this.stepRequiresValidation;
    }

    public boolean getStepContinueOnValidationFailure() {
        return this.stepContinueOnValidationFailure;
    }

    public View getStepView() {
        return this.stepView;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    void cancelStepElement() {
        this.setStepIcon(StepIcon.INACTIVE);
        this.isStepExpanded = false;
        this.isStepDirty = false;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public boolean isStepValid() {
        return !stepRequiresValidation || isStepValid;
    }

    public static class StepElementBuilder {
        @StepIcon.StepIconInterface
        private int stepIcon = StepIcon.INACTIVE; //Default inactive step if not defined
        private int stepNumber;
        private String stepTitle;
        private String stepSubText;
        private boolean stepOptional;
        private boolean stepRequiresValidation;
        private boolean stepContinueOnValidationFailure;
        private View stepView;
        private StepButtonListener stepButtonListener;

        public StepElementBuilder(@Size(min = 1) String stepTitle) {
            this.stepTitle = stepTitle;
        }

        public StepElementBuilder stepNumber(int stepNumber) {
            this.stepNumber = stepNumber;
            return this;
        }

        public StepElementBuilder stepIcon(@StepIcon.StepIconInterface int stepIcon) {
            this.stepIcon = stepIcon;
            return this;
        }

        public StepElementBuilder stepTitle(@Size(min = 1) String stepTitle) {
            this.stepTitle = stepTitle;
            return this;
        }

        public StepElementBuilder stepSubText(@Size(min = 1) String stepSubText) {
            this.stepSubText = stepSubText;
            return this;
        }

        public StepElementBuilder stepOptional(boolean stepOptional) {
            this.stepOptional = stepOptional;
            return this;
        }

        public StepElementBuilder stepRequiresValidation(boolean stepRequiresValidation) {
            this.stepRequiresValidation = stepRequiresValidation;
            return this;
        }

        public StepElementBuilder stepContinueOnValidationFailure(boolean stepContinueOnValidationFailure) {
            this.stepContinueOnValidationFailure = stepContinueOnValidationFailure;
            return this;
        }

        public StepElementBuilder stepView(View stepView) {
            this.stepView = stepView;
            return this;
        }

        public StepElementBuilder stepButtonListener(StepButtonListener stepButtonListener) {
            this.stepButtonListener = stepButtonListener;
            return this;
        }

        public StepElementDetail build() throws StepperElementException {
            //TODO if this gets too complex or duplication comes in, this will be abstracted out to it's own validation engine
            //Validate builder objects
            //1. Requesting build option of stepContinueOnValidationFailure without
            //setting the stepRequiresValidation will result in this exception being thrown.
            if (!stepRequiresValidation && stepContinueOnValidationFailure) {
                throw new StepperElementException("The options provided to the Builder are incompatible. " +
                        "Having stepContinueOnValidationFailure without setting stepRequiresValidation == true caused this exception.",
                        new AnnotationErrorCode(AnnotationErrorCode.INVALID_BUILDER_OPTIONS));
            }
            return new StepElementDetail(this);
        }
    }
}
