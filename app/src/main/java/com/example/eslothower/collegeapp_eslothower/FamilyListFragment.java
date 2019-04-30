package com.example.eslothower.collegeapp_eslothower;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import java.util.ArrayList;


public class FamilyListFragment extends ListFragment {
    private final String TAG = FamilyListFragment.class.getName();

    Family mFamily;


    public FamilyListFragment(){
        mFamily = Family.getFamily();
    }


    //Set the activitie's title and show the list view of family members
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.family_memeber_title);
        FamilyMemberAdapter adapter = new FamilyMemberAdapter(mFamily.getFamilyList());
        setListAdapter(adapter);
        setHasOptionsMenu(true);

    }


    //Adapts the array list
    private class FamilyMemberAdapter extends ArrayAdapter<FamilyMember> {
        public FamilyMemberAdapter(ArrayList<FamilyMember> family) {
            super(getActivity(), 0, family);
        }


        //inflates the view and displays the names of the family members in each list in the list view
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_family_member, null);
            }

            FamilyMember f = getItem(position);
            Log.d(TAG, "The type of FamilyMember at position " + position + " is " + f.getClass().getName());

            TextView nameTextView =
                    (TextView) convertView
                            .findViewById(R.id.family_member_list_item_nameTextView);
            nameTextView.setText(f.toString());

            return convertView;
        }
    }

    //standard onCreate stuff, inflating the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);



        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_family_list, menu);
    }



    //adds new sibling or guardian to the list according to their respective button. If it's the same to what is already there, then it doesn't add it
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FamilyMemberAdapter adapter = (FamilyMemberAdapter)getListAdapter();
        FamilyMember newFM;

        if (item.getItemId() == R.id.menu_item_new_guardian){
            newFM = new Guardian();
            newFM.setEmail("codingstamps@gmail.com");
        } else if (item.getItemId() == R.id.menu_item_new_sibling){
            newFM = new Sibling();
        } else{
            return super.onOptionsItemSelected(item);
            //newFM = null;
        }
            for (FamilyMember f: Family.getFamily().getFamilyList()) {
                if (newFM.getClass().isInstance(f)) {
                    if (f.equals(newFM)) {
                        Log.i(TAG, "MATCH!!!");
                        return true;
                    }
                }
            }


        Family.getFamily().addFamilyMember(newFM);
        adapter.notifyDataSetChanged();
        return true;

    }







    //standard inflating
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.d(TAG, "Creating Context Menu.");
        getActivity().getMenuInflater().inflate(R.menu.family_list_item_context,
                menu);
    }


    //adds or deletes family member depending on what button you hit
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "Context item selected.");
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        FamilyMemberAdapter adapter = (FamilyMemberAdapter) getListAdapter();
        final FamilyMember familyMember = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_family_member:
                Family.getFamily().deleteFamilyMember(familyMember);
                adapter.notifyDataSetChanged();
                Backendless.Data.of(FamilyMember.class).remove(familyMember,new
                        AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                Log.i(TAG, familyMember.toString() + " deleted");
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.e(TAG, fault.getMessage());
                            }
                        });
            case R.id.menu_item_save_family_member:
                ArrayList<FamilyMember> familyArray = mFamily.getFamily().getFamilyList();
                for (FamilyMember mGuardian:familyArray){
                    Backendless.Persistence.save(mGuardian, new AsyncCallback<FamilyMember>() {
                        @Override
                        public void handleResponse(FamilyMember familyMember) {
                            Log.i(TAG, "Saved " + familyMember.toString());
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Log.i(TAG, backendlessFault.toString());
                        }
                    });
                }




                return true;
        }
        return super.onContextItemSelected(item);
    }


    //updates the list of family members when the activity resumes
    @Override
    public void onResume() {
        super.onResume();
        FamilyMemberAdapter adapter = (FamilyMemberAdapter) getListAdapter();
        adapter.notifyDataSetChanged();
    }

    //this brings up a screen showing more detail into each family member, allowing you to modify their information
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FamilyMember f = ((FamilyMemberAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, f.toString() + " was clicked." + FamilyMemberActivity.class);
        Intent i = new Intent(getActivity(), FamilyMemberActivity.class);
        i.putExtra(FamilyMember.EXTRA_RELATION, f.getClass().getName());
        i.putExtra(FamilyMember.EXTRA_INDEX, position);
        startActivity(i);
    }




}


