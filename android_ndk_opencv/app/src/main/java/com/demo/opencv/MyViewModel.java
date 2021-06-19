package com.demo.opencv;

import android.app.AlertDialog;
import android.content.Context;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MyViewModel {
    AppCompatActivity activity;
    ImageView imageView;
    SurfaceView surfaceView;
    LinearLayout linearLayout1, linearLayout2;
    Switch switchConversion;
    Button buttonCamera, buttonSave, buttonTakePictures, buttonFlip, buttonBack;
    ToggleButton toggleButtonLight, toggleButtonKern;
    SeekBar seekBarLight, seekBarEdge, seekBarBlurry, seekBarRelief, seekBarSharpen;

    MyViewModel(Context context) {
        activity = (AppCompatActivity) context;
        imageView = activity.findViewById(R.id.imageView);
        surfaceView = activity.findViewById(R.id.surfaceView);
        linearLayout1 = activity.findViewById(R.id.linearLayout1);
        linearLayout2 = activity.findViewById(R.id.linearLayout2);
        switchConversion = activity.findViewById(R.id.switch_conversion);
        buttonCamera = activity.findViewById(R.id.button_camera);
        buttonSave = activity.findViewById(R.id.button_save);
        buttonTakePictures = activity.findViewById(R.id.button_take_pictures);
        buttonFlip = activity.findViewById(R.id.button_flip);
        buttonBack = activity.findViewById(R.id.button_back);
        toggleButtonLight = activity.findViewById(R.id.toggleButton_light);
        seekBarLight = activity.findViewById(R.id.seekBar_light);
        seekBarEdge = activity.findViewById(R.id.seekBar_edge);
        seekBarBlurry = activity.findViewById(R.id.seekBar_blurry);
        seekBarRelief = activity.findViewById(R.id.seekBar_relief);
        seekBarSharpen = activity.findViewById(R.id.seekBar_sharpen);
        toggleButtonKern = activity.findViewById(R.id.toggleButton_kern);
    }

    void setOnClickListener(OnClickListener onClickListener) {
        buttonCamera.setOnClickListener(onClickListener);
        buttonSave.setOnClickListener(onClickListener);
        buttonTakePictures.setOnClickListener(onClickListener);
        buttonFlip.setOnClickListener(onClickListener);
        buttonBack.setOnClickListener(onClickListener);
    }

    void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        switchConversion.setOnCheckedChangeListener(onCheckedChangeListener);
        toggleButtonLight.setOnCheckedChangeListener(onCheckedChangeListener);
        toggleButtonKern.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        seekBarLight.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarEdge.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarBlurry.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarRelief.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarSharpen.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}
