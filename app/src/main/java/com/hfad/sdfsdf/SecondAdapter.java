package com.hfad.sdfsdf;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondAdapter  extends RecyclerView.Adapter<SecondAdapter.MyViewHoder1> {

    Context context;
    List<String> nameFiles;
    private Animation animation;

    View view1;
    View view2;
    public SecondAdapter(){
     }

    public SecondAdapter(Context ct, List<String> array){
        this.context =ct;
        this.nameFiles = array;
    }

    public SecondAdapter(Context ct, List<String> array, View view){
        this.context =ct;
        this.nameFiles = array;
        this.view2= view;
    }

    @NonNull
    @Override
    public MyViewHoder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);
        return new MyViewHoder1(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder1 holder, int position) {
        File f = new File(context.getFilesDir() + "/"+ nameFiles.get(position));
        Glide.with(context).load(f).into(holder.imageView);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLikeImage(f);
                Toast.makeText(context,"Удалено", Toast.LENGTH_SHORT).show();
               notify2(position);
              }
        });
    }
    //Убираем позицию
    public void notify2(int pos){
        nameFiles = search1();
        notifyItemRemoved(pos);
       }
    // Полное обновление

    public void notify4(){
        nameFiles = search1();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return nameFiles.size();
    }

    public class MyViewHoder1 extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton imageButton,imgBtnRefresh;
        ConstraintLayout fragment2;
        public MyViewHoder1(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            imageButton = itemView.findViewById(R.id.likeBtn);
            imageButton.setImageResource(R.drawable.imgdelete);
            imgBtnRefresh = view2.findViewById(R.id.imageViewrefresh);
            fragment2 = view2.findViewById(R.id.fragment_id2);
            imgBtnRefresh.setImageResource(R.drawable.refresh2);
            imgBtnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgBtnRefresh.startAnimation(animation);
                    notify4();
                }
            });
            animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        }
    }

    //Удаляем любимые картинки
    private void deleteLikeImage(File path){
        File file = new File(path.toString());
        if (file.exists()) {
            file.delete();
        }
    }
    interface Communicator{
        public void notify4();
    }

    List<String> files1;
    //Ищем наши любимые картинки
    public List<String> search1(){
       // String path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File path1 = context.getFilesDir();
        File directory = new File(String.valueOf(path1));

        List<String> files = new ArrayList<String>() {};
       try{ files1 = Arrays.asList(directory.list(new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith(".jpg");
            }
        }));}
       catch (Exception e){e.printStackTrace();}

        if(files1 == null) {
            List<String> f = new ArrayList<String>() {};
            f.add(0,"1");
            return f;
        }else {}
        return files1;}

}
