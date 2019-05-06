package edu.utep.cs.cs4330.courseorganizer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<Task> taskList;
    Context mContext;
    ListView listView;
    Handler handler;
    Animation animation;


    public CustomAdapter(Context mContext, ArrayList<Task> taskList){
        this.mContext = mContext;
        this.taskList = taskList;
        this.handler = new Handler(mContext.getMainLooper());
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

        final View row = convertView;

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxTask);
        TextView textTaskDueDate = (TextView)convertView.findViewById(R.id.textTaskDueDate);

        checkBox.setText(taskList.get(position).getTask());
        textTaskDueDate.setText("Due Date: " + taskList.get(position).getDueDate());

        checkBox.setTag(position);

        listView = parent.findViewById(R.id.listViewTasks);

        DBHelper dbHelper = new DBHelper(mContext);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setChecked(false);
                    Log.i("CheckBox Listener", "Check heard");
                    Log.i("Button Tag", String.valueOf(buttonView.getTag()));
                    Log.i("taskList length before", String.valueOf(taskList.size()));
                    dbHelper.deleteTask(taskList.get((int)buttonView.getTag()));
                    taskList.remove((int)buttonView.getTag());
                    Log.i("taskList length after", String.valueOf(taskList.size()));
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }
}
