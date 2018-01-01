package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import io.tammen.stepper.R;

/**
 * Created by Tammen Bruccoleri on 12/28/2017.
 *
 */

public class VerticalForm extends RelativeLayout implements View.OnClickListener {

    public VerticalForm(Context context) {
        this(context, null);
    }

    public VerticalForm(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VerticalForm(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public VerticalForm(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        inflate(context, R.layout.vertical_stepper_form, this);
    }

    @Override
    public void onClick(View v) {

    }
}
