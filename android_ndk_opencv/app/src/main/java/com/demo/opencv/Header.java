package com.demo.opencv;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Header {
    public static String saveBitmapToStorage(Bitmap bitmap) {
        File sdCard = Environment.getExternalStorageDirectory();
        File filePath = new File(sdCard,"Pictures");
        if (!filePath.exists()) filePath.mkdir();
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(filePath, fileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            Bitmap ratio_bitmap = bitmapRotate(bitmap,90);
            ratio_bitmap.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static Bitmap bitmapFlip(Bitmap bitmap, int mode) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        if (mode == 0) matrix.postScale(-1, 1); // 镜像水平翻转
        else matrix.postScale(1, -1); // 镜像垂直翻转
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }

    public static Bitmap bitmapRotate(Bitmap bitmap, float degrees) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }

    public static Bitmap imageViewToBitmap(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
