package com.joe.accountingapp.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joe.accountingapp.R;

import java.util.ArrayList;

/**
 * author: Joe Cheng
 */
public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder>{
    Context context;
    ArrayList<String> functions;
    public SettingAdapter(Context context, ArrayList<String> functions) {
        this.context = context;
        this.functions = functions;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.settingitem, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        holder.btnFunc.setText(functions.get(position));
    }

    @Override
    public int getItemCount() {
        return functions.size();
    }

    public class SettingViewHolder extends RecyclerView.ViewHolder
    {
        Button btnFunc;
        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btnFunc = itemView.findViewById(R.id.btn_func);
        }
    }
}
