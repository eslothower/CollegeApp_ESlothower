package com.example.eslothower.collegeapp_eslothower;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SiblingFragment extends Fragment {

    FamilyMember fm = new FamilyMember();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle){
        super.onCreateView(inflater, view, bundle);

        View rootView = inflater.inflate(R.layout.fragment_sibling, view, false);

        final TextView fnTextView = (TextView)rootView.findViewById(R.id.sibling1);
        final TextView lnTextView = (TextView)rootView.findViewById(R.id.sibling2);
        final EditText fnEditText = (EditText)rootView.findViewById(R.id.fPlainText1);
        final EditText lnEditText = (EditText)rootView.findViewById(R.id.fPlainText2);
        Button mSubmitButton = (Button)rootView.findViewById(R.id.fSubmitButton);

        fnTextView.setText(fm.getFirstName());
        lnTextView.setText(fm.getLastName());

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnTextView.setText(fnEditText.getText());
                lnTextView.setText(lnEditText.getText());
            }
        });
        return rootView;

    }
}
