package io.tammen.stepper.widget.mobile;

import io.tammen.stepper.R;

/**
 * Created by Tammen Bruccoleri on 1/5/2018.
 *
 */

public class StepElementDetail {
    private String stepTitle, stepSubText;
    private int stepNumber, stepIcon;
    private boolean stepOptional;

    private StepElementDetail(StepElementBuilder builder) {
        this.stepNumber = builder.stepNumber;
        this.stepIcon = builder.stepIcon;
        this.stepTitle = builder.stepTitle;
        this.stepSubText = builder.stepSubText;
        this.stepOptional = builder.stepOptional;
    }

    int getStepNumber() {
        return stepNumber;
    }

    int getStepIcon() {
        return stepIcon;
    }

    String getStepTitle() {
        return stepTitle;
    }

    String getStepSubText() {
        return stepSubText;
    }

    public static class StepElementBuilder {
        private int stepIcon = R.drawable.ic_default_circle; //Default drawable icon
        private int stepNumber = 0;
        private String stepTitle;
        private String stepSubText;
        private boolean stepOptional;


        public StepElementBuilder(int stepNumber) {
            this.stepNumber = stepNumber;
        }

        public StepElementBuilder stepIcon(int stepIcon) {
            this.stepIcon = stepIcon;
            return this;
        }

        public StepElementBuilder stepTitle(String stepTitle) {
            this.stepTitle = stepTitle;
            return this;
        }

        public StepElementBuilder stepSubText(String stepSubText) {
            this.stepSubText = stepSubText;
            return this;
        }

        public StepElementBuilder stepOptional(boolean stepOptional) {
            this.stepOptional = stepOptional;
            return this;
        }

        public StepElementDetail build() {
            return new StepElementDetail(this);
        }
    }
}
