package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.tammen.stepper.R;

/**
 * Created by Tammen Bruccoleri on 12/30/2017.
 *
 */

public class StepElement extends RelativeLayout implements View.OnClickListener {

    public StepElement(Context context) {
        this(context, null);
    }

    public StepElement(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StepElement(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public StepElement(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        View view = inflate(context, R.layout.mobile_step_element, this);

        RelativeLayout rlRowElement = view.findViewById(R.id.rl_row_element);
        rlRowElement.setOnClickListener(this);

        TextView tvIcon = view.findViewById(R.id.row_element_icon);
        TextView tvTitle = view.findViewById(R.id.row_element_title);
        TextView tvSubText = view.findViewById(R.id.row_element_subtext);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.StepElement,
                0, 0);
        try {
            String stepText = a.getString(R.styleable.StepElement_stepTitle);
            String stepSubText = a.getString(R.styleable.StepElement_stepSubtext);
            String stepNumber = a.getString(R.styleable.StepElement_stepNumber);
            boolean stepOptional = a.getBoolean(R.styleable.StepElement_stepOptional, false);

            StepIcon stepIcon = StepIcon.valueOf(a.getInteger(R.styleable.StepElement_stepIcon, 0));
            switch (stepIcon) {
                case EDIT:
                    tvIcon.setBackgroundResource(R.drawable.ic_edit_circle);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                    break;
                case ACTIVE:
                    tvIcon.setBackgroundResource(R.drawable.ic_default_circle);
                    tvIcon.setText(stepNumber);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                    break;
                case INACTIVE:
                    tvIcon.setBackgroundResource(R.drawable.ic_inactive_circle);
                    tvIcon.setText(stepNumber);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                    break;
                case ERROR_ACTIVE:
                    tvIcon.setBackgroundResource(R.drawable.ic_alert);
                    stepSubText = getResources().getString(R.string.io_ta_mobile_subtext_alert_message);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_error_step);
                    tvSubText.setTextAppearance(R.style.io_ta_stepper_form_style_error_subtext);
                    break;
                case ERROR:
                    tvIcon.setBackgroundResource(R.drawable.ic_alert);
                    stepSubText = getResources().getString(R.string.io_ta_mobile_subtext_alert_message);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_error_step);
                    tvSubText.setTextAppearance(R.style.io_ta_stepper_form_style_error_subtext);
                    break;
                case CHECKED:
                    tvIcon.setBackgroundResource(R.drawable.ic_checkmark_circle_done);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                    break;
                default:
                    tvIcon.setBackgroundResource(R.drawable.ic_inactive_circle);
                    tvIcon.setText(stepNumber);
                    tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                    break;
            }
            tvTitle.setText(stepText);
            if (stepSubText != null || stepOptional) {
                addOrRemoveProperty(tvTitle, RelativeLayout.CENTER_IN_PARENT, false);
                tvSubText.setVisibility(View.VISIBLE);

                //If the step is Optional && no step subtext is provided
                // default of "Optional" subtext will be provided (at no additional cost)
                if (stepOptional && stepSubText == null) {
                    tvSubText.setText(R.string.io_ta_optional_step);
                } else {
                    tvSubText.setText(stepSubText);
                }
            }
        } finally {
            a.recycle();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_row_element) {
            TextView stepTitle = v.findViewById(R.id.row_element_title);
            Toast.makeText(v.getContext(), "Tapped on: " + stepTitle.getText(), Toast.LENGTH_LONG).show();
        }
    }

    private void addOrRemoveProperty(View view, int property, boolean flag) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (flag) {
            layoutParams.addRule(property);
        } else {
            layoutParams.removeRule(property);
        }
        view.setLayoutParams(layoutParams);
    }
}
