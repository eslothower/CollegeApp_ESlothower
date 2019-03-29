package com.example.eslothower.collegeapp_eslothower;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.TextView;


public class FamilyMemberFragment extends Fragment {

    FamilyMember fm = new FamilyMember();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle bundle){
        super.onCreateView(inflater, view, bundle);

        View rootView = inflater.inflate(R.layout.fragment_family_member, view, false);

        TextView fnTextView = (TextView)rootView.findViewById(R.id.familyMember1);
        TextView lnTextView = (TextView)rootView.findViewById(R.id.familyMember2);
        EditText fnEditText = (EditText)rootView.findViewById(R.id.plainText1);
        EditText lnEditText = (EditText)rootView.findViewById(R.id.plainText2);
        fnTextView.setText(fm.getFirstName());
        lnTextView.setText(fm.getLastName());
        return rootView;

    }
}
