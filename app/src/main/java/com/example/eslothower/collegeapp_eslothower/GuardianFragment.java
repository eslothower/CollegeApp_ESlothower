package com.example.eslothower.collegeapp_eslothower;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import static android.content.ContentValues.TAG;


public class GuardianFragment extends Fragment {

    //variables and Guardian object
    Guardian mGuardian = new Guardian();
    private TextView fnTextView;
    private TextView lnTextView;
    private EditText fnEditText;
    private EditText lnEditText;
    private TextView occupationTextView;
    private Button mSubmitButton;
    private EditText occupationEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle){

        //standard inflating and linking
        super.onCreateView(inflater, view, bundle);

        View rootView = inflater.inflate(R.layout.fragment_guardian, view, false);

        fnTextView = (TextView)rootView.findViewById(R.id.fnTextView);
        lnTextView = (TextView)rootView.findViewById(R.id.lnTextView);
        fnEditText = (EditText)rootView.findViewById(R.id.fnEditText);
        lnEditText = (EditText)rootView.findViewById(R.id.lnEditText);
        occupationTextView = (TextView)rootView.findViewById(R.id.occupationTextView);
        mSubmitButton = (Button)rootView.findViewById(R.id.fSubmitButton);
        occupationEditText = (EditText) rootView.findViewById(R.id.occupationEditText);

        fnTextView.setText(mGuardian.getFirstName());
        lnTextView.setText(mGuardian.getLastName());

        //When hitting the submit button, it displays the new name/occupation in the text views and updates the Guardian's name and occupation. Also saves to Backendless
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fnEditText.getText().length() > 0){
                    String firstName = fnEditText.getText().toString();
                    mGuardian.setFirstName(firstName);
                    fnTextView.setText(firstName);
                    fnEditText.setText("");
                }

                if(lnEditText.getText().length() > 0){
                    String lastName = lnEditText.getText().toString();
                    mGuardian.setLastName(lastName);
                    lnTextView.setText(lastName);
                    lnEditText.setText("");
                }

                if(occupationEditText.getText().length() > 0){
                    String occupation = occupationEditText.getText().toString();
                    mGuardian.setOccupation(occupation);
                    occupationTextView.setText(occupation);
                    occupationEditText.setText("");
                }
                Backendless.Persistence.save(mGuardian, new AsyncCallback<Guardian>() {
                    @Override
                    public void handleResponse(Guardian guardian) {
                        Log.i(TAG, "Saved" + guardian.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Log.i(TAG, backendlessFault.toString());
                    }
                });

            }
    });


        return rootView;

    }

    //displays the default names and occupations for each guardian in the list view if said info wasn't specifically modified.
    @Override
    public void onStart(){
        int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX, -1);
        if (index != -1){
            mGuardian = (Guardian)Family.getFamily().getFamilyList().get(index);
            fnTextView.setText(mGuardian.getFirstName());
            lnTextView.setText(mGuardian.getLastName());
            occupationTextView.setText(mGuardian.getOccupation());
        }

        super.onStart();
    }



}
