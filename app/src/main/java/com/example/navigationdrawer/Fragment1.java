package com.example.navigationdrawer;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    EditText e1,e2,e3;
    Button b1,b2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment1, null);
      //  mydb = new DatabaseHelper(getActivity());

        e1 = root.findViewById(R.id.editText);
        e2 = root.findViewById(R.id.editText2);
        e3 = root.findViewById(R.id.editText3);

        b1 =  root.findViewById(R.id.button);
        b2 =  root.findViewById(R.id.button2);



        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(getActivity());
                myDB.addLoc(e3.getText().toString().trim(),
                        e2.getText().toString().trim());
            }
        });

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                e1.setText("");
                e2.setText("");
                e3.setText("");
            }
        });


        return root;
    }

}



