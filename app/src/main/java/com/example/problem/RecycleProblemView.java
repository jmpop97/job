package com.example.problem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.job.R;

import java.util.List;

public class RecycleProblemView extends RecyclerView.Adapter<RecycleProblemView.MenuHolder> {
    private static OnItemClickListener mListener=null;
    List<String> problem_nums;
    int problem_num;
    Context context;
    boolean open = false;

    int len=1;
    public RecycleProblemView(Context ct, List<String> Problem_nums, int Problem_num){
        context=ct;
        problem_nums=Problem_nums;
        problem_num=Problem_num;

    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.problem_menu,parent,false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, @SuppressLint("RecyclerView") int position) {
        String name;
        if (open){
            name=problem_nums.get(position);
        }
        else {
            name=problem_nums.get(problem_num);
        }
        name=name.substring(6,name.length()-5);
        holder.problem_view.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (open){
                    mListener.onItemClick(view,position,len);
                    len=1;
                    open=false;
                }
                else {
                    len = problem_nums.size();;
                    open = true;
                }
                notifyDataSetChanged();


            }
        });
    }

    @Override
    public int getItemCount() {
        return len;
    }

    public interface OnItemClickListener{void onItemClick(View v, int pos, int n);}
    public static void setOnItemClickListener(OnItemClickListener listener){mListener = listener;}

    public class MenuHolder extends RecyclerView.ViewHolder{
        TextView problem_view;
        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            problem_view=itemView.findViewById(R.id.menu_problem_num);
        }
    }

}
