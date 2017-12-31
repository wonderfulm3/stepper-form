package io.tammen.stepper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.tammen.stepper.R;

/**
 * Created by Tammen Bruccoleri on 12/30/2017.
 */

public class MobileStepElement extends RelativeLayout implements View.OnClickListener {

    public MobileStepElement(Context context) {
        this(context, null);
    }

    public MobileStepElement(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MobileStepElement(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public MobileStepElement(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        View view = inflate(context, R.layout.mobile_step_element, this);

        TextView textView = view.findViewById(R.id.row_element_title);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.MobileStepElement,
                0, 0);
        try {
            String stepText = a.getString(R.styleable.MobileStepElement_stepTitle);
            textView.setText(stepText);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void onClick(View v) {
    }
}
