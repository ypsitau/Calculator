package io.github.ypsitau.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Double numAccum;
    String strDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numAccum = 0.;
        strDisplay = "0";
        updateDisplay();
    }
    protected void updateDisplay() {
        TextView textView = findViewById(R.id.textview_display);
        textView.setText(strDisplay);
    }
    public void onClick_digit(View view) {
        Button button = (Button)view;
        Log.i("Calculator", String.format("%s", button.getText()));
        if (strDisplay == "0") {
            strDisplay = "";
        }
        strDisplay += button.getText();
        updateDisplay();
    }
    public void onClick_plus(View view) {
        numAccum += Double.parseDouble(strDisplay);
        strDisplay = String.format("%f", numAccum);
        updateDisplay();
        strDisplay = "";
    }
    public void onClick_minus(View view) {

    }
    public void onClick_multiply(View view) {

    }
    public void onClick_divide(View view) {

    }
    public void onClick_equal(View view) {

    }
}
