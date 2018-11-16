package com.example.kabali.coldlaunchsplashscreen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private Context mCtx;
    private List<Person> taskList;

    public PersonAdapter(Context mCtx, List<Person> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_adapter, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Person t = taskList.get(position);
        holder.textViewTask.setText(t.getName());
        holder.textViewDesc.setText(t.getEmail());
        holder.textViewFinishBy.setText(t.getMobileNo());

        if (t.isFinished())
            holder.textViewStatus.setText("Completed");
        else
            holder.textViewStatus.setText("Non Completed");
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;

        public PersonViewHolder(View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Person task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdatePersonActivity.class);
            intent.putExtra("person", task);

            mCtx.startActivity(intent);
        }
    }
}
