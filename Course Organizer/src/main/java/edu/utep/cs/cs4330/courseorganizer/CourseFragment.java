package edu.utep.cs.cs4330.courseorganizer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CourseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_course, container, false);
        DBHelper dbHelper = new DBHelper(getContext());
        Course course = dbHelper.getCourse(getArguments().getString("courseTitle"));

        TextView textProfessorName = view.findViewById(R.id.textProfessorName);
        TextView textProfessorPhone = view.findViewById(R.id.textProfessorPhone);
        TextView textProfessorEmail = view.findViewById(R.id.textProfessorEmail);
        TextView textProfessorOfficeHours = view.findViewById(R.id.textProfessorOfficeHours);
        TextView textCourseLocation = view.findViewById(R.id.textCourseLocation);
        TextView textCourseDays = view.findViewById(R.id.textCourseDays);
        TextView textCourseTime = view.findViewById(R.id.textCourseTime);

        textProfessorName.setText(course.getProfessorName());
        textProfessorPhone.setText(course.getProfessorPhone());
        textProfessorEmail.setText(course.getProfessorEmail());
        textProfessorOfficeHours.setText(course.getProfessorOfficeHours());
        textCourseLocation.setText(course.getLocation());
        textCourseDays.setText(course.getDays());
        textCourseTime.setText(course.getTime());

        return view;
    }
}