package com.example.zhuzzzzzzx.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zhuzzzzzzx on 2017/3/19.
 */

public class Adapter extends RecyclerView.Adapter {
    private ImageView imageView;
    private TextView textView;
    private List<String> list;
    public Adapter(List<String> list){
        this.list=list;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.img);
            textView = (TextView)view.findViewById(R.id.tx);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Getimg getimg = new Getimg();
        getimg.load(list.get(position));
        getimg.into(imageView);
        textView.setText("123456");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
