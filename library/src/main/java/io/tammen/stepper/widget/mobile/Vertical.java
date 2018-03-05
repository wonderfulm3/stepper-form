package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import io.tammen.stepper.R;

/**
 * Created by Tammen Bruccoleri on 12/28/2017.
 *
 */

public class Vertical extends RelativeLayout implements View.OnClickListener {
    private ArrayList<StepElementDetail> stepElementDetails = null;

    public Vertical(Context context) {
        this(context, null);
    }

    public Vertical(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Vertical(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public Vertical(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        inflate(context, R.layout.mobile_vertical_stepper, this);
    }

    @Override
    public void onClick(View v) {

    }

    public void setStepElementDetail(ArrayList<StepElementDetail> stepElementDetail) {
        if (stepElementDetail != null && stepElementDetail.size() > 0) {
            //TODO need to build out the layout dynamically off here...
            this.stepElementDetails = stepElementDetail;
        } else {
            //TODO throw an exception or bake a cake
        }
    }
}
