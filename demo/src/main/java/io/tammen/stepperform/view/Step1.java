package io.tammen.stepperform.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import io.tammen.stepper.widget.mobile.interfaces.StepButtonListener;
import io.tammen.stepper.widget.mobile.interfaces.StepValidationListener;
import io.tammen.stepperform.R;

/**
 * Created by Tammen Bruccoleri on 8/11/2018.
 */
public class Step1 extends RelativeLayout implements StepButtonListener {
    public StepValidationListener stepValidationListener;
    private final CheckBox option1, option2;
    private final Handler handler = new Handler();

    public Step1(Context context) {
        this(context, null);
    }

    public Step1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Step1(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    private Step1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View view = inflate(context, R.layout.step1_layout, this);
        option1 = view.findViewById(R.id.cb_option_a);
        option2 = view.findViewById(R.id.cb_option_b);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //mocking server side validation this could be a server side response,
            //some task that takes time, delayed network responses, etc)
            stepValidationListener.isStepValid(true);
        }
    };

    private void clearSelections() {
        option1.setChecked(false);
        option2.setChecked(false);
    }

    @Override
    public void onStepCancelClicked(int stepClicked) {
        this.stepValidationListener.cancelStep();
        clearSelections();
    }

    @Override
    public void onStepContinueClicked(int stepClicked) {
        //Must select 1 or more toppings for this step to be considered valid and meet validation
        if (option1.isChecked() || option2.isChecked()) {
            this.stepValidationListener.isStepInValidationState(true);
            handler.postDelayed(runnable, 5000);
        } else {
            this.stepValidationListener.isStepValid(false);
        }
    }
}
