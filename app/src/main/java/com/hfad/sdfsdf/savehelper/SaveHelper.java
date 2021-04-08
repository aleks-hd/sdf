package com.hfad.sdfsdf.savehelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;


import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class SaveHelper {

    Context context;
    public void loadImage(Context context1, String url, int namePosition) {
        String fileName = String.valueOf(namePosition);
        context= context1;
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        saveImage(resource, fileName);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        Log.d("DEBUG", "onLoadCleared");
                    }
                });
    }

    //Сохраниние в локальный репозиторий
    public void saveImage(Bitmap imageToSave, String fileName) {
        //Рабочий вариант:
       // File folder1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File folder1 = context.getFilesDir();
        File folder = new File(folder1.toString());
        if(!folder.exists()){folder.mkdir();}
        File file = new File(folder, fileName+".jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 95, fos);
            fos.flush();
            fos.close();
            Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Нет разрешения", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}