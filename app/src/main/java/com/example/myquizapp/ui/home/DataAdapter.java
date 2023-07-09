package com.example.myquizapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.R;
import com.example.myquizapp.ui.home.quizPlay.quizStartPage;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<quiz> dataList;
    private Context context;
    public DataAdapter(List<quiz> dataList, Context context) {
        this.dataList = dataList;
        this.context=context;
    }

    public void setFilter(List<quiz> filteredDataList) {
        dataList = filteredDataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.quizname.setText(dataList.get(position).getQuizname());
        holder.creater.setText(dataList.get(position).getCreater());
        holder.totalquestion.setText(String.valueOf(dataList.get(position).getTotalquestion()));
        holder.date.setText(dataList.get(position).getDate());
        holder.quizListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("quizname",dataList.get(position).getQuizname() );
                bundle.putString("totalquestion",dataList.get(position).getTotalquestion());
                bundle.putString("userid",dataList.get(position).getUserid());
                bundle.putString("quizid",dataList.get(position).getQuizid());
                Intent intent=new Intent(context, quizStartPage.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quizname,creater,totalquestion,date;
        ConstraintLayout quizListLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            quizname = itemView.findViewById(R.id.homeQuizName);
            creater=itemView.findViewById(R.id.homeAuthorName);
            totalquestion=itemView.findViewById(R.id.hometotalQuestion);
            date=itemView.findViewById(R.id.homequizDate);
            quizListLayout=itemView.findViewById(R.id.quizListLayout);
        }
    }
}
