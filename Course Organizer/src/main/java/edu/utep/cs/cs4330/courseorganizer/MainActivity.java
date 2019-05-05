package edu.utep.cs.cs4330.courseorganizer;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private ArrayList<Course> extractedCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set MainActivity layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configure Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Course Organizer");


        //Configure Navigation Drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_message);
        }*/



        /* **************************************For testing purposes************************************************ */
        ArrayList<Course> insertedCourseList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        insertedCourseList.add(new Course("CS 1401", "MWF", "12:00PM - 1:00PM",
                "CCSB 1.01", "Dr.Professor", "(111)111-1111",
                "prof@uni.edu", "CCSB 1.02", "1:00PM - 2:00PM"));

        dbHelper.addTasks("Create Skynet", insertedCourseList.get(0).getCourseTitle(), "1/1/1");

        insertedCourseList.add(new Course("HIST 2020", "W", "2:00PM - 3:00PM",
                "LAC 320", "Dr. Pepper", "(222)222-2222",
                "pepp@uni.edu", "CCSB 1.03", "1:00PM - 2:00PM"));

        dbHelper.addTasks("Write paper on Napoleonic Wars", insertedCourseList.get(1).getCourseTitle(), "2/2/2");

        insertedCourseList.add(new Course("ENG 101", "TR", "7:00AM - 8:00AM",
                "UGLC 016", "Dr. Love", "(333)333-3333",
                "lov@uni.edu", "CCSB 1.04", "1:00PM - 2:00PM"));

        dbHelper.addTasks("Read Gravity's Rainbow", insertedCourseList.get(2).getCourseTitle(), "3/3/3");

        insertedCourseList.add(new Course("MATH 3141", "T", "11:00AM - 12:00PM",
                "BSN 240", "Dr. Oc", "(444)444-4444",
                "oc@uni.edu", "CCSB 1.05", "1:00PM - 2:00PM"));

        dbHelper.addTasks("Solve Riemman Hypothesis", insertedCourseList.get(3).getCourseTitle(), "4/4/4");

        dbHelper.addCourseList(insertedCourseList);

        dbHelper = new DBHelper(this);
        extractedCourseList = dbHelper.getAllCourses();

        Log.i("1", String.valueOf(extractedCourseList.size()));

        final Menu menu = navigationView.getMenu();
        MenuItem runtime_item = menu.add(0,0,0, extractedCourseList.get(0).getCourseTitle());
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,1,0,extractedCourseList.get(1).getCourseTitle());
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,2,0,extractedCourseList.get(2).getCourseTitle());
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,3,0,extractedCourseList.get(3).getCourseTitle());
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,4,0,"Add Course");
        runtime_item.setIcon(R.drawable.ic_add);
        /* ************************************************************************************************************* */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = new CourseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("courseTitle", extractedCourseList.get(item.getItemId()).getCourseTitle());
        fragment.setArguments(bundle);

        getSupportActionBar().setTitle("Course Organizer " + extractedCourseList.get(item.getItemId()).getCourseTitle());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}