package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import io.tammen.stepper.R;

/**
 * Created by Tammen Bruccoleri on 12/29/2017.
 *
 */

public class StepperForm extends RelativeLayout implements View.OnClickListener {

    public StepperForm(Context context) {
        this(context, null);
    }

    public StepperForm(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StepperForm(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public StepperForm(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        inflate(context, R.layout.mobile_stepper_form, this);
    }

    @Override
    public void onClick(View v) {

    }
}
