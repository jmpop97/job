package com.example.job;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuRecycleView extends RecyclerView.Adapter<MenuRecycleView.MenuHolder> {
    List<String> problem_nums;
    Context context;
    boolean open = false;

    int len=1;
    public MenuRecycleView(Context ct,List<String> problem_nums){
        context=ct;
        problem_nums=problem_nums;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.problem_menu,parent,false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        holder.problem_view.setText(Integer.toString(position));
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return len;
    }

    public interface OnItemClickListener{void onItem2Click(View v, int pos, int n);}

    public class MenuHolder extends RecyclerView.ViewHolder{
        TextView problem_view;
        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            problem_view=itemView.findViewById(R.id.menu_problem_num);
        }
    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (open){
                len=1;
                open=false;
            }
            else {
                len=2;
                open=true;
            }
            notifyDataSetChanged();


        }
    };
}
