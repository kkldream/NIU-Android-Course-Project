package com.demo.opencv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG_MainActivity";
    static { System.loadLibrary("native-lib");
             System.loadLibrary("opencv_header"); }
    native void getLight(Object bitmap, int var);
    native void getEdge(Object bitmap, int var);
    native void getBlurry(Object bitmap, int var);
    native void getEmbossing(Object bitmap, int var);
    native void getSharpness(Object bitmap, int var);
    native void getKern(Object bitmap, float[] var);

    MyViewModel model;
    Camera camera;
    Bitmap original_bitmap, conversion_bitmap;
    float[] customKern = new float[25];
    int[] conversionProgress = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new MyViewModel(this);
        Log.i("TAG","-------------------onCreate-------------------");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        original_bitmap = Header.imageViewToBitmap(model.imageView);
        conversion_bitmap = original_bitmap.copy(original_bitmap.getConfig(), original_bitmap.isMutable());
        camera = new Camera(this);
        camera.setSurfaceView(model.surfaceView);
        model.setOnClickListener(clickListener);
        model.setOnSeekBarChangeListener(seekBarChangeListener);
        model.setOnCheckedChangeListener(checkedChangeListener);
    }

    private void conversion() {
        conversion_bitmap = original_bitmap.copy(Bitmap.Config.ARGB_8888,true);
        if (conversionProgress[0] > 0) getLight(conversion_bitmap, conversionProgress[0]);
        if (conversionProgress[1] > 0) getSharpness(conversion_bitmap, conversionProgress[1]);
        if (conversionProgress[2] > 0) getBlurry(conversion_bitmap, conversionProgress[2]);
        if (conversionProgress[3] > 0) getEmbossing(conversion_bitmap, conversionProgress[3]);
        if (conversionProgress[4] > 0) getEdge(conversion_bitmap, conversionProgress[4]);
        if (model.toggleButtonKern.isChecked()) {
            getKern(conversion_bitmap, customKern);
            Log.i(TAG, Arrays.toString(customKern));
        }
        if (model.switchConversion.isChecked()) model.imageView.setImageBitmap(conversion_bitmap);
    }

    private OnClickListener clickListener = new OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_camera:
                    camera.open(CameraCharacteristics.LENS_FACING_FRONT);
                    model.linearLayout1.setVisibility(View.GONE);
                    model.linearLayout2.setVisibility(View.VISIBLE);
                    model.imageView.setVisibility(View.INVISIBLE);
                    model.surfaceView.setVisibility(View.VISIBLE);
                    break;
                case R.id.button_save:
                    String fileName;
                    if (model.switchConversion.isChecked()) fileName = Header.saveBitmapToStorage(conversion_bitmap);
                    else fileName = Header.saveBitmapToStorage(original_bitmap);
                    Toast.makeText(MainActivity.this,"上圖已保存至 Pictures 資料夾\n檔案名稱：" + fileName, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_take_pictures:
                    camera.takePicture(obitmap -> {
                        Bitmap bitmap = (Bitmap) obitmap;
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        int c = Math.min(bitmap.getWidth(), bitmap.getHeight());
                        original_bitmap = Bitmap.createBitmap(bitmap,(w-c)/2,(h-c)/2, c, c);
                        model.imageView.setImageBitmap(original_bitmap);
                        model.imageView.setVisibility(View.VISIBLE);
                        model.surfaceView.setVisibility(View.INVISIBLE);
                        camera.close();
                        model.linearLayout1.setVisibility(View.VISIBLE);
                        model.linearLayout2.setVisibility(View.GONE);
                        model.imageView.setVisibility(View.VISIBLE);
                        model.surfaceView.setVisibility(View.INVISIBLE);
                        conversion();
                        return null;
                    });
                    break;
                case R.id.button_flip:
                    camera.flip();
                    break;
                case R.id.button_back:
                    camera.close();
                    model.linearLayout1.setVisibility(View.VISIBLE);
                    model.linearLayout2.setVisibility(View.GONE);
                    model.imageView.setVisibility(View.VISIBLE);
                    model.surfaceView.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };


    private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.switch_conversion:
                    conversion();
                    if (b) model.imageView.setImageBitmap(conversion_bitmap);
                    else model.imageView.setImageBitmap(original_bitmap);
                    break;
                case R.id.toggleButton_light:
                    if (b) camera.setAeMode(Camera.AE_MODE_ON_AUTO_FLASH);
                    else camera.setAeMode(Camera.AE_MODE_OFF);
                    break;
                case R.id.toggleButton_kern:
                    if (b) new KernDialog(MainActivity.this, input -> {
                            float[][] floats = (float[][]) input;
                            for (int i = 0; i < 25; i++)
                                customKern[i] = floats[(int) (i / 5)][i % 5];
                            conversion();
                            return null;
                        });
                    else conversion();
                    break;
            }
        }
    };

    private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            switch (seekBar.getId()) {
                case R.id.seekBar_light:
                    conversionProgress[0] = i;
                    break;
                case R.id.seekBar_sharpen:
                    conversionProgress[1] = i;
                    break;
                case R.id.seekBar_blurry:
                    conversionProgress[2] = i;
                    break;
                case R.id.seekBar_relief:
                    conversionProgress[3] = i;
                    break;
                case R.id.seekBar_edge:
                    conversionProgress[4] = i;
                    break;
            }
            conversion();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}