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
 *
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

        TextView tvTitle = view.findViewById(R.id.row_element_title);
        TextView tvSubText = view.findViewById(R.id.row_element_subtext);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.MobileStepElement,
                0, 0);
        try {
            String stepText = a.getString(R.styleable.MobileStepElement_stepTitle);
            String subText = a.getString(R.styleable.MobileStepElement_stepSubtext);

            MobileStepIcon mobileStepIcon = MobileStepIcon.valueOf(a.getInteger(R.styleable.MobileStepElement_stepIcon, 0));
            switch (mobileStepIcon) {
                case ACTIVE:
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty_circle, 0, 0, 0);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                    break;
                case INACTIVE:
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_inactive_circle, 0, 0, 0);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                    break;
                case EDIT:
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit_circle, 0, 0, 0);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                    break;
                case ERROR:
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_alert, 0, 0, 0);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                    break;
                case CHECKED:
                    tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_checkmark_circle_done, 0, 0, 0);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                    break;
            }
            tvTitle.setText(stepText);
            if (subText != null) {
                tvSubText.setVisibility(View.VISIBLE);
                tvSubText.setText(subText);
            }
        } finally {
            a.recycle();
        }
    }

    @Override
    public void onClick(View v) {
    }
}
