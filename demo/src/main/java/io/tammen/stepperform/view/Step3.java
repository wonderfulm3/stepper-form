package io.tammen.stepperform.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import io.tammen.stepper.widget.mobile.interfaces.StepButtonListener;
import io.tammen.stepper.widget.mobile.interfaces.StepValidationListener;
import io.tammen.stepperform.R;

/**
 * Created by Tammen Bruccoleri on 8/11/2018.
 */
public class Step3 extends RelativeLayout implements StepButtonListener {
    private final RadioButton option1, option2, option3;
    public StepValidationListener stepValidationListener;

    public Step3(Context context) {
        this(context, null);
    }

    public Step3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Step3(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    private Step3(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View view = inflate(context, R.layout.step3_layout, this);
        option1 = view.findViewById(R.id.rb_step3_option_a);
        option2 = view.findViewById(R.id.rb_step3_option_b);
        option3 = view.findViewById(R.id.rb_step3_option_c);
    }

    private void clearSelections() {
        option1.setChecked(false);
        option2.setChecked(false);
        option3.setChecked(false);
    }

    @Override
    public void onStepContinueClicked(int stepClicked) {
        //Since this step is marked as optional we don't mock any validation nor do we indicate
        //that the step is in a validation state.
        if (option1.isChecked() || option2.isChecked() || option3.isChecked()) {
            stepValidationListener.isStepValid(true);
        } else {
            stepValidationListener.isStepValid(false);
        }
    }

    @Override
    public void onStepCancelClicked(int stepClicked) {
        stepValidationListener.cancelStep();
        clearSelections();
    }
}
