package com.example.problem_selection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.job.R;
import com.example.problem.RecycleProblemView;

import java.util.List;

public class RecycleSelectProblemMenuView extends RecyclerView.Adapter<RecycleSelectProblemMenuView.MenuHolder> {
    private static RecycleProblemView.OnItemClickListener mListener=null;
    List<String> problem_nums;
    int problem_num;
    Context context;
    int len = 1;
    String problem_str="빈문제";
    int score_str=0;
    public RecycleSelectProblemMenuView(Context ct, List<String> Problem_nums, int _problem_num){
        context=ct;
        problem_nums=Problem_nums;
        problem_num=_problem_num;
        len = problem_nums.size();
    }
    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_porblem_list_menu,parent,false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleSelectProblemMenuView.MenuHolder holder, int position) {
        set_problem(position);
        String name;
        name=problem_nums.get(position);
        name=name.substring(6,name.length()-5);
        holder.tml_problem_num.setText(name);
        holder.tml_problem_score.setText(String.valueOf(score_str));
        holder.tml_problem_index.setText(problem_str);
    }

    @Override
    public int getItemCount() {
        return len;
    }

    public class MenuHolder extends RecyclerView.ViewHolder{
        TextView tml_problem_num;
        TextView tml_problem_score;
        TextView tml_problem_index;
        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            tml_problem_num = itemView.findViewById(R.id.problem_num);
            tml_problem_score = itemView.findViewById(R.id.problem_score);
            tml_problem_index = itemView.findViewById(R.id.problem_index);
        }

    }

    public void set_problem(int position){
        SelectProblemMenuActivity mainactivity = (SelectProblemMenuActivity) SelectProblemMenuActivity.mContext;
        score_str= mainactivity.problem_score(problem_nums.get(position));
        problem_str= mainactivity.problem_str(problem_nums.get(position));
        Log.d("score", String.valueOf(score_str)+String.valueOf(position));
    }

}