package io.tammen.stepperform.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import io.tammen.stepper.widget.mobile.interfaces.StepButtonListener;
import io.tammen.stepper.widget.mobile.interfaces.StepValidationListener;
import io.tammen.stepperform.R;

/**
 * Created by Tammen Bruccoleri on 8/11/2018.
 */
public class Step1 extends RelativeLayout implements StepButtonListener {
    public StepValidationListener stepValidationListener;
    private CheckBox option1, option2;

    public Step1(Context context) {
        this(context, null);
    }

    public Step1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Step1(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Step1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View view = inflate(context, R.layout.step1_layout, this);
        option1 = view.findViewById(R.id.cb_option_a);
        option2 = view.findViewById(R.id.cb_option_b);
    }

    @Override
    public void onStepContinueClicked(int stepClicked) {
        if (option1.isChecked() || option2.isChecked()) {
            Toast.makeText(super.getContext(), "Proceed!!!", Toast.LENGTH_LONG).show();
            this.stepValidationListener.isStepValid(true);
        } else {
            Toast.makeText(super.getContext(), "You need a topping!!!", Toast.LENGTH_LONG).show();
            this.stepValidationListener.isStepValid(false);
        }
    }

    @Override
    public void onStepCancelClicked(int stepClicked) {
        Toast.makeText(super.getContext(), "Why you Cancel!? Toppings scare you?", Toast.LENGTH_LONG).show();
    }
}
