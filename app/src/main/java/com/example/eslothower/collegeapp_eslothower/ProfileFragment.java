package com.example.eslothower.collegeapp_eslothower;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    Profile profile = new Profile();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle){
        super.onCreateView(inflater, view, bundle);

        View rootView = inflater.inflate(R.layout.fragment_profile, view, false);
        TextView fmTextView = (TextView)rootView.findViewById(R.id.textView1);
        TextView lmTextView = (TextView)rootView.findViewById(R.id.textView2);

        fmTextView.setText(profile.getFirstName());
        lmTextView.setText(profile.getLastName());
        return rootView;

    }
}
