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

    Guardian mGuardian = new Guardian();
    private TextView fnTextView;
    private TextView lnTextView;
    private EditText fnEditText;
    private EditText lnEditText;
    private TextView occupation;
    private Button mSubmitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle){
        super.onCreateView(inflater, view, bundle);

        View rootView = inflater.inflate(R.layout.fragment_guardian, view, false);

        fnTextView = (TextView)rootView.findViewById(R.id.guardian1);
        lnTextView = (TextView)rootView.findViewById(R.id.guardian2);
        fnEditText = (EditText)rootView.findViewById(R.id.fPlainText1);
        lnEditText = (EditText)rootView.findViewById(R.id.fPlainText2);
        occupation = (TextView)rootView.findViewById(R.id.occupation);
        mSubmitButton = (Button)rootView.findViewById(R.id.fSubmitButton);

        fnTextView.setText(mGuardian.getFirstName());
        lnTextView.setText(mGuardian.getLastName());

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnTextView.setText(fnEditText.getText());
                lnTextView.setText(lnEditText.getText());
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    @Override
    public void onStart(){
        int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX, -1);
        if (index != -1){
            mGuardian = (Guardian)Family.getFamily().getFamilyList().get(index);
            fnTextView.setText(mGuardian.getFirstName());
            lnTextView.setText(mGuardian.getLastName());
            occupation.setText(mGuardian.getOccupation());
        }

        super.onStart();
    }



}
