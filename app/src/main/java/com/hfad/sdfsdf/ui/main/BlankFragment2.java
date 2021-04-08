package com.hfad.sdfsdf.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.sdfsdf.MainAdapter;
import com.hfad.sdfsdf.R;
import com.hfad.sdfsdf.SecondAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlankFragment2 extends Fragment {
    RecyclerView recyclerView;
    ImageButton imgBtnRefresh;

    List<String> arrayNamesLile;
    List<String> files1;
    Context context;
    View view;

    SecondAdapter adapter;
    public BlankFragment2(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        arrayNamesLile = search();
        recyclerView = view.findViewById(R.id.recycler_view1);
        adapter = new SecondAdapter(context,arrayNamesLile,view);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(adapter);
        imgBtnRefresh = view.findViewById(R.id.imageViewrefresh);
         super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout_2, container, false);
    return view;}

  //Ищем наши любимые картинки
    public List<String> search(){
       // String path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        File directory = new File(String.valueOf(context.getFilesDir()));
      List<String> files = new ArrayList<String>() {};
       try {
           files1 = Arrays.asList(directory.list(new FilenameFilter() {
               public boolean accept(File directory, String fileName) {
                   return fileName.endsWith(".jpg");
               }
           }));
       }catch (Exception e){e.printStackTrace();}

        //Это не правильно, но по другому пока не знаю как сделать,
        // т.е. при первом запуске когда папка с "Like" images пустая, я не имею права передавать null array в адаптер
        // это происходит в момент запуска когда user еще не дал разрешения, но отобразить данные хочется
        if(files1 == null || files1.size()==0) {
            List<String> f = new ArrayList<String>() {};
            f.add(0,"1");
            return f;
        }else {}
        return files1;
    }
}
