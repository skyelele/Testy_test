package com.bawp.nodo.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawp.nodo.R;
import com.bawp.nodo.model.NoDo;

import java.util.List;

public class NoDoListAdapter extends RecyclerView.Adapter<NoDoListAdapter.NoDoViewHolder> {


    private final LayoutInflater noDoInflater;
    private List<NoDo> noDoList; //cached copy of nodo items


    public NoDoListAdapter(Context context) {
        noDoInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = noDoInflater.inflate(R.layout.recyclerview_item, viewGroup, false);

        return new NoDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoDoViewHolder noDoViewHolder, int position) {

        if (noDoList != null) {
            NoDo current = noDoList.get(position);
            noDoViewHolder.noDoTextView.setText(current.getNoDo());
        } else {
            noDoViewHolder.noDoTextView.setText(R.string.no_notodo);
        }
    }

    public void setNoDos(List<NoDo> noDos) {
        noDoList = noDos;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (noDoList != null)
            return noDoList.size();
        else return 0;
    }

    public class NoDoViewHolder extends ViewHolder {
        public TextView noDoTextView;

        public NoDoViewHolder(@NonNull View itemView) {
            super(itemView);
            noDoTextView = itemView.findViewById(R.id.textView);
        }
    }
}
