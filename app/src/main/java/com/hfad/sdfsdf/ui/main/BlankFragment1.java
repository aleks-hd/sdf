package com.hfad.sdfsdf.ui.main;

import android.content.Context;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import com.hfad.sdfsdf.MainAdapter;
import com.hfad.sdfsdf.MainData;
import com.hfad.sdfsdf.MainInterface;
import com.hfad.sdfsdf.R;


public class BlankFragment1 extends Fragment {
    RecyclerView recyclerView;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;

    Context context;
    View view;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    int page = 1, limit = 200;
    public BlankFragment1() {
    }

    public BlankFragment1(Context context) {
        this.context = context;
    }


    @Override
    public void onStart() {
        super.onStart();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new MainAdapter(context,dataArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setAdapter(adapter);
        nestedScrollView = view.findViewById(R.id.sclorl_view);
        progressBar = view.findViewById(R.id.progress_bar);

        getData(page,limit);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight()- v.getMeasuredHeight()){
                    page++;
                   // Toast.makeText(context,"Page " +page, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page,limit);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           view = inflater.inflate(R.layout.fragment_layout_1, container, false);
        return view;
    }
    private void getData(int page, int limit) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MainInterface mainInterface = retrofit.create(MainInterface.class);

        Call<String> stringCall = mainInterface.STRING_CALL(page,limit);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null)
                progressBar.setVisibility(View.GONE);
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        parseArray1(jsonArray);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }


    private void parseArray1(JSONArray jsonArray) {
        dataArrayList.clear();
        for(int i=0; i<jsonArray.length(); i++ ){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MainData data = new MainData();
                data.setImage(jsonObject.getString("download_url"));
                data.setName(jsonObject.getString("author"));
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new MainAdapter(context,dataArrayList);
            recyclerView.setAdapter(adapter);
        }

    }


}

