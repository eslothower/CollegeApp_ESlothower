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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.family_memeber_title);
        FamilyMemberAdapter adapter = new FamilyMemberAdapter(mFamily.getFamilyList());
        setListAdapter(adapter);
        setHasOptionsMenu(true);

    }

    private class FamilyMemberAdapter extends ArrayAdapter<FamilyMember> {
        public FamilyMemberAdapter(ArrayList<FamilyMember> family) {
            super(getActivity(), 0, family);
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FamilyMemberAdapter adapter = (FamilyMemberAdapter)getListAdapter();
        FamilyMember newFM;
        /*switch (item.getItemId()) {
            case R.id.menu_item_new_guardian:
               newFM = new Guardian();
            case R.id.menu_item_new_sibling:
              newFM = new Sibling();
            default:
                newFM = null;
        }*/

        if (item.getItemId() == R.id.menu_item_new_guardian){
            newFM = new Guardian();
        } else if (item.getItemId() == R.id.menu_item_new_sibling){
            newFM = new Sibling();
        } else{
            newFM = null;
        }
        try{
        for (FamilyMember f: Family.getFamily().getFamilyList()){
             if (newFM.getClass().isInstance(f)){
                if (f.equals(newFM)) {
                    Log.i(TAG, "MATCH!!!");
                    return true;
                }
            }
        }}
        catch (Exception e){
            Log.i(TAG, e.getMessage());
        }
        Family.getFamily().addFamilyMember(newFM);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.d(TAG, "Creating Context Menu.");
        getActivity().getMenuInflater().inflate(R.menu.family_list_item_context,
                menu);
    }

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
            /*case R.id.menu_item_save_family_member:
                ArrayList<FamilyMember> familyArray = mFamily.getFamily();
                for (FamilyMember fm:familyArray){
                    Backendless.Persistence.save(fm, new AsyncCallback<FamilyMember>() {
                        @Override
                        public void handleResponse(FamilyMember familyMember) {
                            Log.i(TAG, "Saved " + familyMember.toString());
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Log.i(TAG, backendlessFault.toString());
                        }
                    });
                }*/
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        FamilyMemberAdapter adapter = (FamilyMemberAdapter) getListAdapter();
        adapter.notifyDataSetChanged();
    }


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


