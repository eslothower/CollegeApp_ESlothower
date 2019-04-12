package com.example.eslothower.collegeapp_eslothower;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.backendless.persistence.DataQueryBuilder;


import java.util.List;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {


    Profile mProfile;
    String email;




    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle){
        super.onCreateView(inflater, view, bundle);

        View rootView = inflater.inflate(R.layout.fragment_profile, view, false);

        final TextView pFirstName = (TextView)rootView.findViewById(R.id.pFirstName);
        final TextView pLastName = (TextView)rootView.findViewById(R.id.pLastName);
        final EditText pfEditText = (EditText)rootView.findViewById(R.id.pPlainText1);
        final EditText plEditText = (EditText)rootView.findViewById(R.id.pPlainText2);
        Button pSubmitButton = (Button)rootView.findViewById(R.id.pSubmitButton);
        Button datePickerButton = (Button)rootView.findViewById(R.id.datePickerButton);
        mProfile = new Profile();

        pFirstName.setText(mProfile.getFirstName());
        pLastName.setText(mProfile.getLastName());

        pSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pfEditText.getText().toString()!=null){
                    mProfile.mFirstName = pfEditText.getText().toString();
                }
                if (plEditText.getText().toString()!=null){
                    mProfile.mLastName = plEditText.getText().toString();
                }
                saveToBackendless();
            }
        });


        pSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pFirstName.setText(pfEditText.getText());
                pLastName.setText(plEditText.getText());
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mProfile.dateOfBirth);
                dialog.show(fm, "DialogDateOfBirth");
                Log.i("ProfileFragment", mProfile.dateOfBirth.toString());
            }
        });
        return rootView;

    }

    @Override
    public void onPause(){
        super.onPause();

        if (email == null){
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            String email = sharedPreferences.getString(ApplicantActivity.EMAIL_PREF, null);
            if (mProfile.getEmail() == null) {
                mProfile.setEmail(email);
            }
        }

        String whereClause = "email = '" + email + "'";
        DataQueryBuilder query = DataQueryBuilder.create();
        query.setWhereClause(whereClause);
        Backendless.Data.of(Profile.class).find(query, new AsyncCallback<List<Profile>>() {
            @Override
            public void handleResponse(List<Profile> profile) {
                if (!profile.isEmpty()) {
                    String profileId = profile.get(0).getObjectID();
                    Log.d(TAG, "Object ID: " + profileId);
                    mProfile.setObjectID(profileId);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG, "Failed to find profile: " + fault.getMessage());
            }
        });
        Backendless.Data.of(Profile.class).save(mProfile, new AsyncCallback<Profile>() {
            @Override
            public void handleResponse(Profile response) {
                Log.i(TAG, "Saved profile to Backendless");
            }

            public void handleFault(BackendlessFault fault) {
                Log.i(TAG, "Failed to save profile!" + fault.getMessage());
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (email == null) {
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            String email = sharedPreferences.getString(ApplicantActivity.EMAIL_PREF, null);
            if (mProfile.getEmail() == null) {
                mProfile.setEmail(email);
            }
        }

        String whereClause = "email = '" + email + "'";
        DataQueryBuilder query = DataQueryBuilder.create();
        query.setWhereClause(whereClause);
        Backendless.Data.of(Profile.class).find(query, new AsyncCallback<List<Profile>>() {
            @Override
            public void handleResponse(List<Profile> profile) {
                if (!profile.isEmpty()) {
                    //String profileId = profile.get(0).getObjectID();
                    //Log.d(TAG, "Object ID: " + profileId);
                    //mProfile.setObjectID(profileId);
                    mProfile = profile.get(0);
                    mProfile.setFirstName(email);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG, "Failed to find profile: " + fault.getMessage());
            }
        });
        Backendless.Data.of(Profile.class).save(mProfile, new AsyncCallback<Profile>() {
            @Override
            public void handleResponse(Profile response) {
                Log.i(TAG, "Saved profile to Backendless");
            }

            public void handleFault(BackendlessFault fault) {
                Log.i(TAG, "Failed to save profile!" + fault.getMessage());
            }
        });
    }


    private void saveToBackendless(){
        String whereClause = "email = 'jlinburg@doversd.org'";
        DataQueryBuilder query = DataQueryBuilder.create();
        query.setWhereClause(whereClause);
        Backendless.Data.of(Profile.class).find(query, new AsyncCallback<List<Profile>>() {
            @Override
            public void handleResponse(List<Profile> response) {
                if (!response.isEmpty()) {
                    String profileId = response.get(0).getObjectID();
                    Log.d("Profile Fragment", "Object ID: " + profileId);
                    mProfile.setObjectID(profileId);
                    Backendless.Data.of(Profile.class).save(mProfile, new AsyncCallback<Profile>() {
                        @Override
                        public void handleResponse(Profile response) {
                            Log.i("success", response.getFirstName() + " has been saved");
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("Error", fault.getMessage());
                        }
                    });
                }
                else{
                    Backendless.Data.of(Profile.class).save(mProfile, new AsyncCallback<Profile>() {
                        @Override
                        public void handleResponse(Profile response) {
                            Log.i("success", response.getFirstName() + " has been saved");
                            mProfile.objectID = response.objectID;
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("Error", fault.getMessage());
                        }
                    });
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("Profile Fragment", "Failed to find profile: " + fault.getMessage());
            }
        });
    }


}
