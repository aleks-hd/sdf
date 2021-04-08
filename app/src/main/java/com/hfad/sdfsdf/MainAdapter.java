package com.hfad.sdfsdf;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hfad.sdfsdf.savehelper.SaveHelper;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Animation animTranslate;
    private ArrayList<MainData> dataArrayList;
    private Context context;

    public MainAdapter(Context context, ArrayList<MainData> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainData data = dataArrayList.get(position);

        Glide.with(context).load(data.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.textView.setText(data.getName());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            String link = data.getImage();

            @Override
            public void onClick(View view) {
                SaveHelper saveHelper = new SaveHelper();
                saveHelper.loadImage(context, link, position);
                holder.imageButton.startAnimation(animTranslate);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
            imageButton = itemView.findViewById(R.id.likeBtn);
            imageButton.setImageResource(R.drawable.like);
            animTranslate = AnimationUtils.loadAnimation(context, R.anim.scale);
        }

    }
}

