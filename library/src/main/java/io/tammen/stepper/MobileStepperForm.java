package io.tammen.stepper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Tammen Bruccoleri on 12/29/2017.
 *
 */

public class MobileStepperForm extends RelativeLayout implements View.OnClickListener {

    public MobileStepperForm(Context context) {
        this(context, null);
    }

    public MobileStepperForm(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MobileStepperForm(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public MobileStepperForm(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        inflate(context, R.layout.mobile_stepper_form, this);
    }

    @Override
    public void onClick(View v) {

    }
}
