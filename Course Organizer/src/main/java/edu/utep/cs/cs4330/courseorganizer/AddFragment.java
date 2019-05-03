package edu.utep.cs.cs4330.courseorganizer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddFragment extends Fragment {
    Button addButton;
    EditText editName;
    NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        navigationView = getActivity().findViewById(R.id.nav_view);

        addButton = view.findViewById(R.id.buttonAdd);
        editName = view.findViewById(R.id.editName);

        addButton.setOnClickListener(this::addClicked);
        return view;
    }

    public void addClicked(View view){

        final Menu menu = navigationView.getMenu();
        menu.clear();
        MenuItem runtime_item = menu.add(0,0,0,"CS 4330");
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,1,0,"CS 2301");
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,2,0,"CS 101");
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,3,0,"PHYS 2421");
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,4,0,editName.getText().toString());
        runtime_item.setIcon(R.drawable.ic_school);

        runtime_item = menu.add(0,5,0,"Add Course");
        runtime_item.setIcon(R.drawable.ic_add);
    }
}