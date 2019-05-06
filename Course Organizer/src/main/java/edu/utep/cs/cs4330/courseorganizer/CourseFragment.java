package edu.utep.cs.cs4330.courseorganizer;

import android.content.DialogInterface;
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
    Course course;
    TextView textProfessorName;
    TextView textProfessorPhone;
    TextView textProfessorEmail;
    TextView textProfessorOffice;
    TextView textProfessorOfficeHours;
    TextView textCourseLocation;
    TextView textCourseDays;
    TextView textCourseTime;
    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_course, container, false);
        dbHelper = new DBHelper(getContext());
        course = dbHelper.getCourse(getArguments().getString("courseTitle"));

        setHasOptionsMenu(true);

        textProfessorName = view.findViewById(R.id.textProfessorName);
        textProfessorPhone = view.findViewById(R.id.textProfessorPhone);
        textProfessorEmail = view.findViewById(R.id.textProfessorEmail);
        textProfessorOffice = view.findViewById(R.id.textProfessorOffice);
        textProfessorOfficeHours = view.findViewById(R.id.textProfessorOfficeHours);
        textCourseLocation = view.findViewById(R.id.textCourseLocation);
        textCourseDays = view.findViewById(R.id.textCourseDays);
        textCourseTime = view.findViewById(R.id.textCourseTime);

        updateTextViews();

        view.findViewById(R.id.titleInstructor).setBackgroundColor(Color.rgb(0, 142, 180));
        view.findViewById(R.id.titleLocation).setBackgroundColor(Color.rgb(0, 142, 180));
        view.findViewById(R.id.titleTasks).setBackgroundColor(Color.rgb(0, 142, 180));


        taskList = dbHelper.getCourseTasks(getArguments().getString("courseTitle"));
        CustomAdapter listAdapter = new CustomAdapter(getContext(), taskList);
        listView = view.findViewById(R.id.listViewTasks);
        listView.setAdapter(listAdapter);

        return view;
    }

    public void updateTextViews(){
        textProfessorName.setText(course.getProfessorName());
        textProfessorPhone.setText("Phone: " + course.getProfessorPhone());
        textProfessorEmail.setText("Email: " + course.getProfessorEmail());
        textProfessorOffice.setText("Office: " + course.getProfessorOfficeLocation());
        textProfessorOfficeHours.setText("Office Hours: " + course.getProfessorOfficeHours());
        textCourseLocation.setText("Location: " + course.getLocation());
        textCourseDays.setText("Days: " + course.getDays());
        textCourseTime.setText("Time: " + course.getTime());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.course_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.courseMenuButtonAdd:
                addTaskDialog();
                break;
            case R.id.courseMenuButtonInstructor:
                editInstructorDialog();
                break;
            case R.id.courseMenuButtonLocation:
                editLocationDialog();
                break;
            case R.id.courseMenuButtonDelete:
                deleteCourseDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTaskDialog(){
        //Attaches the calling activity to the dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        //Retrieve and prepare the UI for the dialog box
        View view = getLayoutInflater().inflate(R.layout.add_task_dialog, null);

        //Set textViews

        //Assigns the UI to the dialog box and sets the title and behavior of positive
        //and negative buttons
        builder.setView(view)
                .setTitle("Add Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    /**
                     * Not implemented closes dialog by default.
                     * @param dialog The associated dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    /**
                     * Determines behavior of apply button on click, passes the string used to pass
                     * string back to DetailActivity
                     * @param dialog The associated dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Apply changes
                        Task t = new Task(((TextView)view.findViewById(R.id.addTask)).getText().toString(),
                                course.getCourseTitle(),
                                ((TextView)view.findViewById(R.id.addTask)).getText().toString());

                        taskList.add(t);
                        listView.setAdapter(new CustomAdapter(getContext(), taskList));

                        dbHelper.addTasks(t.getTask(), t.getCourse(), t.getDueDate());
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editInstructorDialog(){
        //Attaches the calling activity to the dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        //Retrieve and prepare the UI for the dialog box
        View view = getLayoutInflater().inflate(R.layout.edit_instructor_dialog, null);

        //Set textViews
        TextView instructorName = view.findViewById(R.id.editInstructorName);
        TextView instructorPhone = view.findViewById(R.id.editInstructorPhone);
        TextView instructorEmail = view.findViewById(R.id.editInstructorEmail);
        TextView instructorOffice = view.findViewById(R.id.editInstructorOffice);
        TextView instructorOfficeHours = view.findViewById(R.id.editInstructorOfficeHours);

        instructorName.setText(course.getProfessorName());
        instructorPhone.setText(course.getProfessorPhone());
        instructorEmail.setText(course.getProfessorEmail());
        instructorOffice.setText(course.getProfessorOfficeLocation());
        instructorOfficeHours.setText(course.getProfessorOfficeLocation());

        //Assigns the UI to the dialog box and sets the title and behavior of positive
        //and negative buttons
        builder.setView(view)
                .setTitle("Edit Instructor")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    /**
                     * Not implemented closes dialog by default.
                     * @param dialog The associated dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    /**
                     * Determines behavior of apply button on click, passes the string used to pass
                     * string back to DetailActivity
                     * @param dialog The associated dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Apply changes
                        course.setProfessorName(instructorName.getText().toString());
                        course.setProfessorPhone(instructorPhone.getText().toString());
                        course.setProfessorEmail(instructorEmail.getText().toString());
                        course.setProfessorOfficeLocation(instructorOffice.getText().toString());
                        course.setProfessorOfficeHours(instructorOfficeHours.getText().toString());

                        updateTextViews();

                        dbHelper.updateCourse(course);

                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editLocationDialog(){
        //Attaches the calling activity to the dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        //Retrieve and prepare the UI for the dialog box
        View view = getLayoutInflater().inflate(R.layout.edit_location_dialog, null);

        TextView courseLocation = view.findViewById(R.id.editLocation);
        TextView courseDays = view.findViewById(R.id.editCourseDays);
        TextView courseTime = view.findViewById(R.id.editCourseTime);

        courseLocation.setText(course.getLocation());
        courseDays.setText(course.getDays());
        courseTime.setText(course.getTime());


        //Assigns the UI to the dialog box and sets the title and behavior of positive
        //and negative buttons
        builder.setView(view)
                .setTitle("Edit Location and Time")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    /**
                     * Not implemented closes dialog by default.
                     * @param dialog The associated dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    /**
                     * Determines behavior of apply button on click, passes the string used to pass
                     * string back to DetailActivity
                     * @param dialog The associated dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        course.setLocation(courseLocation.getText().toString());
                        course.setDays(courseDays.getText().toString());
                        course.setTime(courseTime.getText().toString());

                        updateTextViews();

                        dbHelper.updateCourse(course);
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteCourseDialog(){
        //Attaches the calling activity to the dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        //Retrieve and prepare the UI for the dialog box
        View view = getLayoutInflater().inflate(R.layout.delete_dialog, null);

        //Set textViews

        //Assigns the UI to the dialog box and sets the title and behavior of positive
        //and negative buttons
        builder.setView(view)
                .setTitle("Delete Course")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    /**
                     * Not implemented closes dialog by default.
                     * @param dialog The associated dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    /**
                     * Determines behavior of apply button on click, passes the string used to pass
                     * string back to DetailActivity
                     * @param dialog The associated dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Apply changes
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

}