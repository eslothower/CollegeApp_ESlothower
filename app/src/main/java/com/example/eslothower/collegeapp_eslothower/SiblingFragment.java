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

public class SiblingFragment extends Fragment {

    //variables
    Sibling mSibling = new Sibling();
    private TextView fnTextView;
    private TextView lnTextView;
    private EditText fnEditText;
    private EditText lnEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle){

       //standard inflating and linking up
        super.onCreateView(inflater, view, bundle);

        View rootView = inflater.inflate(R.layout.fragment_sibling, view, false);

        fnTextView = (TextView)rootView.findViewById(R.id.sibling1);
        lnTextView = (TextView)rootView.findViewById(R.id.sibling2);
        fnEditText = (EditText)rootView.findViewById(R.id.fnEditText);
        lnEditText = (EditText)rootView.findViewById(R.id.lnEditText);
        Button mSubmitButton = (Button)rootView.findViewById(R.id.fSubmitButton);

        fnTextView.setText(mSibling.getFirstName());
        lnTextView.setText(mSibling.getLastName());


        //when you hit the submit button, it sets the first and last names to the text views and Object, and saves to backendless
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fnEditText.getText().length() > 0){
                    String firstName = fnEditText.getText().toString();
                    mSibling.setFirstName(firstName);
                    fnTextView.setText(firstName);
                    fnEditText.setText("");
                }

                if(lnEditText.getText().length() > 0){
                    String lastName = lnEditText.getText().toString();
                    mSibling.setLastName(lastName);
                    lnTextView.setText(lastName);
                    lnEditText.setText("");
                }
                Backendless.Persistence.save(mSibling, new AsyncCallback<Sibling>() {
                    @Override
                    public void handleResponse(Sibling sibling) {
                        Log.i(TAG, "Saved" + sibling.toString());
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


    //Displays the default first and last names for the siblings in the list views if the names havent already been specifically modified
    @Override
    public void onStart(){
        int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX, -1);
        if (index != -1){
            mSibling = (Sibling) Family.getFamily().getFamilyList().get(index);
            fnTextView.setText(mSibling.getFirstName());
            lnTextView.setText(mSibling.getLastName());
        }

        super.onStart();
    }
}
