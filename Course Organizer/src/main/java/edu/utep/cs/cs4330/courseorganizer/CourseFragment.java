package edu.utep.cs.cs4330.courseorganizer;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;


public class CourseFragment extends Fragment {
    ListView listView;
    ArrayList<Task> taskList;
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

        registerForContextMenu(view.findViewById(R.id.card_view));
        registerForContextMenu(view.findViewById(R.id.card_view2));

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.card_view:
                menu.setHeaderTitle("Edit Instructor");
                getActivity().getMenuInflater().inflate(R.menu.instructor_context_menu, menu);

                break;
            case R.id.card_view2:
                menu.setHeaderTitle("Edit Location and Time");
                getActivity().getMenuInflater().inflate(R.menu.location_context_menu, menu);

                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.contextMenuName :
                editTextDialog(R.id.contextMenuName);
                break;
            case R.id.contextMenuPhone :
                editPhoneDialog();
                break;
            case R.id.contextMenuEmail :
                editTextDialog(R.id.contextMenuEmail);
                break;
            case R.id.contextMenuOffice :
                editTextDialog(R.id.contextMenuOffice);
                break;
            case R.id.contextMenuOfficeHours :
                editTimeDialog(true, R.id.contextMenuOfficeHours);
                break;
            case R.id.contextMenuLocation :
                editTextDialog(R.id.contextMenuLocation);
                break;
            case R.id.contextMenuDays :
                editDayDialog();
                break;
            case R.id.contextMenuTime :
                editTimeDialog(true, R.id.contextMenuTime);
                break;
        }
        return super.onContextItemSelected(item);
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

            case R.id.courseMenuButtonDelete:
                deleteCourseDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editTextDialog(int id){
        //Attaches the calling activity to the dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        //Retrieve and prepare the UI for the dialog box
        View view = getLayoutInflater().inflate(R.layout.edit_one_dialog, null);
        EditText editText = view.findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        String title;
        switch (id){
            case R.id.contextMenuName :
                title ="Edit Instructor Name";
                editText.setHint("Enter Name");
                break;
            case R.id.contextMenuEmail :
                title = "Edit Email";
                editText.setHint("Enter Email");
                break;
            case R.id.contextMenuOffice :
                title = "Edit Office Location";
                editText.setHint("Enter Office Location");
                break;
            case R.id.contextMenuLocation :
                editText.setHint("Enter Course Location");
                title = "Edit Location";
                break;
            default:
                title = "Edit";
                break;
        }

        //Assigns the UI to the dialog box and sets the title and behavior of positive
        //and negative buttons
        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (id){
                            case R.id.contextMenuName :
                                course.setProfessorName(editText.getText().toString());
                                dbHelper.updateCourse(course);
                                break;
                            case R.id.contextMenuEmail :
                                course.setProfessorEmail(editText.getText().toString());
                                dbHelper.updateCourse(course);
                                break;
                            case R.id.contextMenuOffice :
                                course.setProfessorOfficeLocation(editText.getText().toString());
                                dbHelper.updateCourse(course);
                                break;
                            case R.id.contextMenuLocation :
                                course.setLocation(editText.getText().toString());
                                dbHelper.updateCourse(course);
                                break;
                            default:
                                break;
                        }
                        updateTextViews();
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editDateDialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.edit_one_dialog, null);
        EditText editText = view.findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);

        builder.setView(view)
                .setTitle("Add Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editTimeDialog(boolean isStart, int id){
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    Log.i("Time Picker", String.valueOf(hourOfDay));
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    final String time = String.valueOf(myCalender.get(Calendar.HOUR_OF_DAY)) +
                            ":" + String.valueOf(myCalender.get(Calendar.MINUTE));

                    switch (id){
                        case R.id.contextMenuOfficeHours:
                            if(isStart){course.setProfessorOfficeHours(time);}
                            else{
                                course.setProfessorOfficeHours(course.getProfessorOfficeHours() + " - " + time);
                                updateTextViews();
                                dbHelper.updateCourse(course);
                            }
                            break;
                        case R.id.contextMenuTime:
                            if(isStart){course.setTime(time);}
                            else{
                                course.setTime(course.getTime() + " - " + time);
                                updateTextViews();
                                dbHelper.updateCourse(course);
                            }
                            break;
                    }

                    if(isStart)editTimeDialog(false, id);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Material_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        if(isStart){timePickerDialog.setTitle("Choose start time:");}
        else {timePickerDialog.setTitle("Choose end time:");}
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public void editPhoneDialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.edit_one_dialog, null);
        EditText editText = view.findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_PHONE);

        builder.setView(view)
                .setTitle("Add Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        course.setProfessorPhone(textProfessorPhone.getText().toString());
                        updateTextViews();
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editDayDialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.edit_days_dialog, null);
        MaterialDayPicker dayPicker = view.findViewById(R.id.day_picker);

        builder.setView(view)
                .setTitle("Select Course Days")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<MaterialDayPicker.Weekday> daysSelected = dayPicker.getSelectedDays();
                        course.setDays(formatWeekDays(daysSelected));
                        dbHelper.updateCourse(course);
                        updateTextViews();
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String formatWeekDays(List<MaterialDayPicker.Weekday> weekdays){
        String formatted = "";
        for(MaterialDayPicker.Weekday w : weekdays){
            if(w.toString().equals("THURSDAY")){formatted += "R";}
            else{
                formatted += w.toString().charAt(0);
            }
        }
        return formatted;
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
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
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
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ArrayList<Course> courseList = dbHelper.getAllCourses();
                        for(Course c : courseList){
                            if(c.getCourseTitle().equals(course.getCourseTitle())){
                                courseList.remove(c);
                                break;
                            }
                        }

                        dbHelper.deleteCourse(course);
                        ((MainActivity)getActivity()).updateNavigationMenu(courseList);
                        ((MainActivity)getActivity()).deleteAndSwitchFragments();
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}