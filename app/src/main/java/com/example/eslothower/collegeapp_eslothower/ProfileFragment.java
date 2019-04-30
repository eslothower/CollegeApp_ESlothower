package com.example.eslothower.collegeapp_eslothower;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {

    //variables
    Profile mProfile;
    public String email;

    public Button datePickerButton;

    public ImageButton mSelfieButton;
    public ImageView mSelfieView;
    public File mSelfieFile;
    private final int REQUEST_SELFIE = 1;
    public static final int REQUEST_DATE_OF_BIRTH = 0;
    private SimpleDateFormat formatter;



    @Override

    //standard inflating and linking up buttons, textViews, etc.
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle){
        super.onCreateView(inflater, view, bundle);

        View rootView = inflater.inflate(R.layout.fragment_profile, view, false);
        final TextView pFirstName = (TextView)rootView.findViewById(R.id.pFirstName);
        final TextView pLastName = (TextView)rootView.findViewById(R.id.pLastName);
        final EditText pfEditText = (EditText)rootView.findViewById(R.id.pPlainText1);
        final EditText plEditText = (EditText)rootView.findViewById(R.id.pPlainText2);
        Button pSubmitButton = (Button)rootView.findViewById(R.id.pSubmitButton);
        datePickerButton = (Button)rootView.findViewById(R.id.datePickerButton);
        mProfile = new Profile();
        mSelfieButton =  (ImageButton)rootView.findViewById(R.id.profile_camera);
        mSelfieView = (ImageView)rootView.findViewById(R.id.profile_pic);
        mSelfieFile = mProfile.getPhotoFile(getActivity());

        formatter = new SimpleDateFormat("MM/dd/yyyy");



        //allows for selfies to be taken
        final Intent captureSelfie = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakeSelfie = mSelfieFile != null &&
                captureSelfie.resolveActivity(getActivity().getPackageManager()) != null;
        mSelfieButton.setEnabled(canTakeSelfie);
        if (canTakeSelfie) {
            Uri uri = Uri.fromFile(mSelfieFile);
            captureSelfie.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mSelfieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureSelfie, REQUEST_SELFIE);
            }
        });



        pFirstName.setText(mProfile.getFirstName());
        pLastName.setText(mProfile.getLastName());


        //sets first and last names and saves to Backendless
        pSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pfEditText.getText().toString()!=null){
                    mProfile.mFirstName = pfEditText.getText().toString();
                    pFirstName.setText(pfEditText.getText());
                }
                if (plEditText.getText().toString()!=null){
                    mProfile.mLastName = plEditText.getText().toString();
                    pLastName.setText(plEditText.getText());
                }
                saveToBackendless();
            }
        });


        //functionality for the date picker button/widget
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mProfile.dateOfBirth);
                dialog.show(fm, "DialogDateOfBirth");
                Log.i("ProfileFragment", mProfile.dateOfBirth.toString());
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog;
                dialog = DatePickerFragment.newInstance(mProfile.getDateOfBirth());
                dialog.setTargetFragment(ProfileFragment.this, REQUEST_DATE_OF_BIRTH);
                dialog.show(fm, "DialogDateOfBirth");
            }
        });

        updateSelfieView();
        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
    }



    //sets the email and profile, and sends it to Backendless
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



    //this is the method you wrote that saves everything to Backendless
    public void saveToBackendless(){
        //Makes DataQueryBuilder
        String whereClause = "email = 'jlinburg@doversd.org'";
        DataQueryBuilder query = DataQueryBuilder.create();
        query.setWhereClause(whereClause);

        //saves to Backendless
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        //sets the date of birth

        if(requestCode == REQUEST_DATE_OF_BIRTH){
            Date date = (Date)(data.getSerializableExtra(DatePickerFragment.EXTRA_DATE_OF_BIRTH));
            mProfile.setDateOfBirth(date);
            datePickerButton.setText(formatter.format(mProfile.getDateOfBirth()));
        }

        //displays your image you took
        if(requestCode == REQUEST_SELFIE){
            updateSelfieView();
        }
    }


    //sets the bitmap to the image you took
    public void updateSelfieView(){
        if (mSelfieFile!=null && mSelfieFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(mSelfieFile.getPath());
            mSelfieView.setImageBitmap(bitmap);
        }
    }






}
