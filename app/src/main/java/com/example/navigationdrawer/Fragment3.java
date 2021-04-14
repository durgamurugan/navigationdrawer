package com.example.navigationdrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.baoyachi.stepview.VerticalStepView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {
    EditText e1, e2, e3;
    Button b1, b2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment2,container,false);
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

