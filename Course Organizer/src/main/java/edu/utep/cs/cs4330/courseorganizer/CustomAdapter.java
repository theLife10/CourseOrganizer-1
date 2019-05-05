package edu.utep.cs.cs4330.courseorganizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<Task> taskList;
    Context mContext;

    public CustomAdapter(Context mContext, ArrayList<Task> taskList){
        this.mContext = mContext;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item,parent,false);
        }

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxTask);
        TextView textTaskDueDate = (TextView)convertView.findViewById(R.id.textTaskDueDate);

        checkBox.setText(taskList.get(position).getTask());
        textTaskDueDate.setText("Due Date: " + taskList.get(position).getDueDate());
        return convertView;
    }
}
