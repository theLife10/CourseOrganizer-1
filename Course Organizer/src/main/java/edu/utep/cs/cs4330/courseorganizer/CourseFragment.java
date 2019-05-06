package edu.utep.cs.cs4330.courseorganizer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CourseFragment extends Fragment {
    ListView listView;
    ArrayList<Task> taskList;
    CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_course, container, false);
        DBHelper dbHelper = new DBHelper(getContext());
        Course course = dbHelper.getCourse(getArguments().getString("courseTitle"));

        setHasOptionsMenu(true);

        TextView textProfessorName = view.findViewById(R.id.textProfessorName);
        TextView textProfessorPhone = view.findViewById(R.id.textProfessorPhone);
        TextView textProfessorEmail = view.findViewById(R.id.textProfessorEmail);
        TextView textProfessorOfficeHours = view.findViewById(R.id.textProfessorOfficeHours);
        TextView textCourseLocation = view.findViewById(R.id.textCourseLocation);
        TextView textCourseDays = view.findViewById(R.id.textCourseDays);
        TextView textCourseTime = view.findViewById(R.id.textCourseTime);

        textProfessorName.setText(course.getProfessorName());
        textProfessorPhone.setText("Phone: " + course.getProfessorPhone());
        textProfessorEmail.setText("Email: " + course.getProfessorEmail());
        textProfessorOfficeHours.setText("Office Hours: " + course.getProfessorOfficeHours());
        textCourseLocation.setText("Location: " + course.getLocation());
        textCourseDays.setText("Days: " + course.getDays());
        textCourseTime.setText("Time: " + course.getTime());

        view.findViewById(R.id.titleInstructor).setBackgroundColor(Color.rgb(0, 142, 180));
        view.findViewById(R.id.titleLocation).setBackgroundColor(Color.rgb(0, 142, 180));
        view.findViewById(R.id.titleTasks).setBackgroundColor(Color.rgb(0, 142, 180));


        taskList = dbHelper.getCourseTasks(getArguments().getString("courseTitle"));
        CustomAdapter listAdapter = new CustomAdapter(getContext(), taskList);
        listView = view.findViewById(R.id.listViewTasks);
        listView.setAdapter(listAdapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ListView Del Listener", "Click heard");
                taskList.remove(position);
                listView.setAdapter(new CustomAdapter(getContext(), taskList));
            }
        });*/

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.course_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

}