package edu.utep.cs.cs4330.courseorganizer;

/**
 * @author Kenneth Ward
 * @since 5/8/2019
 * @version 1.0
 */

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

/**
 * Custom adapter class for populating a ListView with information from Task objects.
 * Each row contains a CheckBox with the description, and a TextView for the due date
 */
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

    //Creates the view for each ListView item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item,parent,false);
        }

        final View row = convertView;

        //Sets the text for the CheckBox and TextView
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxTask);
        TextView textTaskDueDate = (TextView)convertView.findViewById(R.id.textTaskDueDate);
        checkBox.setText(taskList.get(position).getTask());
        textTaskDueDate.setText("Due Date: " + taskList.get(position).getDueDate());

        //Very important!! Tag used to identify the position of the
        //CheckBox within the ListView
        checkBox.setTag(position);

        listView = parent.findViewById(R.id.listViewTasks);

        DBHelper dbHelper = new DBHelper(mContext);

        //Creates a listener for every CheckBox in the ListView
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setChecked(false);
                    Log.i("CheckBox Listener", "Check heard");
                    Log.i("Button Tag", String.valueOf(buttonView.getTag()));
                    Log.i("taskList length before", String.valueOf(taskList.size()));

                    //The ListView item is removed from the UI and DB when the
                    //checkbox is checked
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
