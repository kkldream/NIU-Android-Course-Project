package com.demo.opencv;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

public class KernDialog {
    AppCompatActivity activity;
    Function function;
    Button buttonAdd, buttonSub, buttonConversion;
    SeekBar seekBarKern;
    ToggleButton[][] toggleButtons = new ToggleButton[5][5];
    TextView textViewNum;
    AlertDialog dialog;
    double seekBarValue = 0.04;

    KernDialog(Context context, Function result) {
        activity = (AppCompatActivity) context;
        function = result;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View root = activity.getLayoutInflater().inflate(R.layout.alertdialog_kern,null);
        findViewById(root);
        builder.setView(root);
        setClickListener();
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (i < 9) {
                seekBarValue = (double) i / 100 + 0.01;
                textViewNum.setText(String.format("每次 增加/減少 %.2f 單位", seekBarValue));
            }
            else if (i < 18) {
                seekBarValue = (double) (i - 10) / 10 + 0.2;
                textViewNum.setText(String.format("每次 增加/減少 %.1f 單位", seekBarValue));
            }
            else {
                seekBarValue = i - 17;
                textViewNum.setText(String.format("每次 增加/減少 %.0f 單位", seekBarValue));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    View.OnClickListener clickListener = view -> {
        switch (view.getId()) {
            case R.id.button_add:
            case R.id.button_sub:
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (toggleButtons[i][j].isChecked()) {
                            Log.d("TAG", String.format("i = %d, j = %d", i, j));
                            double var = Float.parseFloat(toggleButtons[i][j].getText().toString());
                            if (view.getId() == R.id.button_add) var += seekBarValue;
                            else var -= seekBarValue;
                            String str = String.format("%.2f", var);
                            if (str.equals("-0.00")) str = "0.00";
                            toggleButtons[i][j].setText(str);
                            toggleButtons[i][j].setTextOn(str);
                            toggleButtons[i][j].setTextOff(str);
                        }
                    }
                }
                break;
            case R.id.button_conversion:
                float[][] floats = new float[5][5];
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        float var = Float.parseFloat(toggleButtons[i][j].getText().toString());
                        floats[i][j] = var;
                    }
                }
                function.apply(floats);
                dialog.dismiss();
                break;
        }
    };

    private void findViewById(View view) {
        textViewNum = view.findViewById(R.id.textView_num);
        buttonAdd = view.findViewById(R.id.button_add);
        buttonSub = view.findViewById(R.id.button_sub);
        buttonConversion = view.findViewById(R.id.button_conversion);
        seekBarKern = view.findViewById(R.id.seekBar_kern);
        toggleButtons[0][0] = view.findViewById(R.id.button_1_1);
        toggleButtons[0][1] = view.findViewById(R.id.button_1_2);
        toggleButtons[0][2] = view.findViewById(R.id.button_1_3);
        toggleButtons[0][3] = view.findViewById(R.id.button_1_4);
        toggleButtons[0][4] = view.findViewById(R.id.button_1_5);
        toggleButtons[1][0] = view.findViewById(R.id.button_2_1);
        toggleButtons[1][1] = view.findViewById(R.id.button_2_2);
        toggleButtons[1][2] = view.findViewById(R.id.button_2_3);
        toggleButtons[1][3] = view.findViewById(R.id.button_2_4);
        toggleButtons[1][4] = view.findViewById(R.id.button_2_5);
        toggleButtons[2][0] = view.findViewById(R.id.button_3_1);
        toggleButtons[2][1] = view.findViewById(R.id.button_3_2);
        toggleButtons[2][2] = view.findViewById(R.id.button_3_3);
        toggleButtons[2][3] = view.findViewById(R.id.button_3_4);
        toggleButtons[2][4] = view.findViewById(R.id.button_3_5);
        toggleButtons[3][0] = view.findViewById(R.id.button_4_1);
        toggleButtons[3][1] = view.findViewById(R.id.button_4_2);
        toggleButtons[3][2] = view.findViewById(R.id.button_4_3);
        toggleButtons[3][3] = view.findViewById(R.id.button_4_4);
        toggleButtons[3][4] = view.findViewById(R.id.button_4_5);
        toggleButtons[4][0] = view.findViewById(R.id.button_5_1);
        toggleButtons[4][1] = view.findViewById(R.id.button_5_2);
        toggleButtons[4][2] = view.findViewById(R.id.button_5_3);
        toggleButtons[4][3] = view.findViewById(R.id.button_5_4);
        toggleButtons[4][4] = view.findViewById(R.id.button_5_5);
    }

    private void setClickListener() {
        buttonAdd.setOnClickListener(clickListener);
        buttonSub.setOnClickListener(clickListener);
        buttonConversion.setOnClickListener(clickListener);
        seekBarKern.setOnSeekBarChangeListener(seekBarChangeListener);
        toggleButtons[0][0].setOnClickListener(clickListener);
        toggleButtons[0][1].setOnClickListener(clickListener);
        toggleButtons[0][2].setOnClickListener(clickListener);
        toggleButtons[0][3].setOnClickListener(clickListener);
        toggleButtons[0][4].setOnClickListener(clickListener);
        toggleButtons[1][0].setOnClickListener(clickListener);
        toggleButtons[1][1].setOnClickListener(clickListener);
        toggleButtons[1][2].setOnClickListener(clickListener);
        toggleButtons[1][3].setOnClickListener(clickListener);
        toggleButtons[1][4].setOnClickListener(clickListener);
        toggleButtons[2][0].setOnClickListener(clickListener);
        toggleButtons[2][1].setOnClickListener(clickListener);
        toggleButtons[2][2].setOnClickListener(clickListener);
        toggleButtons[2][3].setOnClickListener(clickListener);
        toggleButtons[2][4].setOnClickListener(clickListener);
        toggleButtons[3][0].setOnClickListener(clickListener);
        toggleButtons[3][1].setOnClickListener(clickListener);
        toggleButtons[3][2].setOnClickListener(clickListener);
        toggleButtons[3][3].setOnClickListener(clickListener);
        toggleButtons[3][4].setOnClickListener(clickListener);
        toggleButtons[4][0].setOnClickListener(clickListener);
        toggleButtons[4][1].setOnClickListener(clickListener);
        toggleButtons[4][2].setOnClickListener(clickListener);
        toggleButtons[4][3].setOnClickListener(clickListener);
        toggleButtons[4][4].setOnClickListener(clickListener);
    }
}
