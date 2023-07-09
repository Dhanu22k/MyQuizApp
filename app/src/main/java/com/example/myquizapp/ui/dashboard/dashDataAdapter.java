package com.example.myquizapp.ui.dashboard;



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
import com.example.myquizapp.ui.dashboard.dashQuiz;
import com.example.myquizapp.ui.home.quizPlay.quizStartPage;

import java.util.List;

public class dashDataAdapter extends RecyclerView.Adapter<com.example.myquizapp.ui.dashboard.dashDataAdapter.ViewHolder> {

    private List<dashQuiz> dataList;
    private Context context;
    public dashDataAdapter(List<dashQuiz> dataList, Context context) {
        this.dataList = dataList;
        this.context=context;
    }

    public void setFilter(List<dashQuiz> filteredDataList) {
        dataList = filteredDataList;
        notifyDataSetChanged();
    }

    @Override
    public com.example.myquizapp.ui.dashboard.dashDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item, parent, false);
        return new com.example.myquizapp.ui.dashboard.dashDataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.myquizapp.ui.dashboard.dashDataAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.quizname.setText(dataList.get(position).getQuizname());
        holder.totalquestion.setText(String.valueOf(dataList.get(position).getTotalquestions()));
        holder.date.setText(dataList.get(position).getDate());
        holder.score.setText(dataList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quizname,creater,totalquestion,date,score;
        ConstraintLayout quizListLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            quizname = itemView.findViewById(R.id.dashQuizName);
            totalquestion=itemView.findViewById(R.id.dashtotalQuestion);
            date=itemView.findViewById(R.id.dashquizDate);
            score=itemView.findViewById(R.id.dashScore);
            quizListLayout=itemView.findViewById(R.id.quizListLayout);
        }
    }
}
