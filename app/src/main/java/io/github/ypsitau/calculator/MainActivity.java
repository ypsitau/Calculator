package io.github.ypsitau.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Main activity of Calculator application.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * This class provides calculation handler that is provoked by calculator buttons.
     */
    protected abstract static class Operator {
        abstract public double calc(double numLeft, double numRight);
    }
    protected static class Operator_plus extends Operator {
        @Override
        public double calc(double numLeft, double numRight) {
            return numLeft + numRight;
        }
    }
    protected static class Operator_minus extends Operator {
        @Override
        public double calc(double numLeft, double numRight) {
            return numLeft - numRight;
        }
    }
    protected static class Operator_multiply extends Operator {
        @Override
        public double calc(double numLeft, double numRight) {
            return numLeft * numRight;
        }
    }
    protected static class Operator_divide extends Operator {
        @Override
        public double calc(double numLeft, double numRight) {
            return numLeft / numRight;
        }
    }
    //-------------------------------------------------------------------------
    Double numAccum;
    Double numEntered;
    String strEntered;
    Operator opPrev;
    Map<Integer, Operator> opMap = new HashMap<Integer, Operator>();
    //-------------------------------------------------------------------------
    protected void doClearAll() {
        numAccum = 0.;
        numEntered = 0.;
        strEntered = "";
        opPrev = null;
        updateDisplay("0");
    }
    public void doCalc() {
        if (!strEntered.isEmpty()) {
            Log.i("Calculator", String.format("strEntered:%s", strEntered));
            numEntered = Double.parseDouble(strEntered);
            strEntered = "";
        }
        if (opPrev == null) {
            updateNumAccum(numEntered);
        } else {
            updateNumAccum(opPrev.calc(numAccum, numEntered));
        }
    }
    protected void updateDisplay(String str) {
        TextView textView = findViewById(R.id.textview_display);
        textView.setText(str);
    }

    protected void updateNumAccum(Double numAccum) {
        this.numAccum = numAccum;
        updateDisplay(String.format("%g", numAccum));
    }
    //-------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        opMap.put(R.id.button_plus, new Operator_plus());
        opMap.put(R.id.button_minus, new Operator_minus());
        opMap.put(R.id.button_multiply, new Operator_multiply());
        opMap.put(R.id.button_divide, new Operator_divide());
        doClearAll();
    }
    public void onClick_digit(View view) {
        Button button = (Button) view;
        String strKeyIn = button.getText().toString();
        if (!strKeyIn.equals(".")) {
            strEntered += strKeyIn;
        } else if (strEntered.isEmpty()) {
            strEntered = "0.";
        } else if (!strEntered.contains(".")) {
            strEntered += strKeyIn;
        }
        updateDisplay(strEntered);
    }
    public void onClick_operator(View view) {
        if (!strEntered.isEmpty()) doCalc();
        opPrev = opMap.get(view.getId());
    }
    public void onClick_equal(View view) {
        doCalc();
    }
    public void onClick_clear(View view) {
        doClearAll();
    }
}
