package io.tammen.stepperform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import io.tammen.stepper.widget.mobile.StepElementDetail;
import io.tammen.stepper.widget.mobile.Vertical;

/**
 * Created by Tammen Bruccoleri on 12/28/2017.
 *
 */

public class MainActivity extends AppCompatActivity {
    private final ArrayList<StepElementDetail> stepElementDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Vertical vertical = findViewById(R.id.vertical_example);

        StepElementDetail step1 = new StepElementDetail.StepElementBuilder(1)
                .stepIcon(R.drawable.ic_default_circle)
                .stepTitle("This is a first step")
                .build();

        StepElementDetail step2 = new StepElementDetail.StepElementBuilder(2)
                .stepTitle("This is a second step")
                .build();

        stepElementDetails.add(step1);
        stepElementDetails.add(step2);
        vertical.setStepElementDetail(stepElementDetails);
    }
}
