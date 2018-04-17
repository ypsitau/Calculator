package io.github.ypsitau.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    protected abstract static class Operator {
        abstract public void calc(MainActivity activity);
    }
    protected static class Operator_plus extends Operator {
        @Override
        public void calc(MainActivity activity) {
            activity.updateNumAccum(activity.getNumAccum() + activity.getNumEntered());
        }
    }
    protected static class Operator_minus extends Operator {
        @Override
        public void calc(MainActivity activity) {
            activity.updateNumAccum(activity.getNumAccum() - activity.getNumEntered());
        }
    }
    protected static class Operator_multiply extends Operator {
        @Override
        public void calc(MainActivity activity) {
            activity.updateNumAccum(activity.getNumAccum() * activity.getNumEntered());
        }
    }
    protected static class Operator_divide extends Operator {
        @Override
        public void calc(MainActivity activity) {
            activity.updateNumAccum(activity.getNumAccum() / activity.getNumEntered());
        }
    }
    //-------------------------------------------------------------------------
    Double mNumAccum;
    Double mNumEntered;
    String mStrDisplay;
    Operator mOpPrev;
    Map<Integer, Operator> mOpMap = new HashMap<Integer, Operator>();
    //-------------------------------------------------------------------------
    public Double getNumAccum() {
        return mNumAccum;
    }
    public Double getNumEntered() {
        return mNumEntered;
    }
    public void doCalc() {
        if (mOpPrev == null) {
            updateNumAccum(mNumEntered);
        } else {
            mOpPrev.calc(this);
        }
    }
    protected void updateDisplay() {
        TextView textView = findViewById(R.id.textview_display);
        textView.setText(mStrDisplay);
    }

    protected void updateNumAccum(Double numAccum) {
        mNumAccum = numAccum;
        TextView textView = findViewById(R.id.textview_display);
        textView.setText(String.format("%g", mNumAccum));
        mStrDisplay = "";
    }
    //-------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumAccum = 0.;
        mStrDisplay = "0";
        mOpPrev = null;
        mOpMap.put(R.id.button_plus, new Operator_plus());
        mOpMap.put(R.id.button_minus, new Operator_minus());
        mOpMap.put(R.id.button_multiply, new Operator_multiply());
        mOpMap.put(R.id.button_divide, new Operator_divide());
        updateDisplay();
    }
    public void onClick_digit(View view) {
        Button button = (Button) view;
        Log.i("Calculator", String.format("%s", button.getText()));
        if (mStrDisplay == "0") {
            mStrDisplay = "";
        }
        mStrDisplay += button.getText();
        updateDisplay();
    }
    public void onClick_operator(View view) {
        if (mStrDisplay != "") {
            mNumEntered = Double.parseDouble(mStrDisplay);
            doCalc();
        }
        mOpPrev = mOpMap.get(view.getId());
    }
    public void onClick_equal(View view) {
        doCalc();
    }
}
