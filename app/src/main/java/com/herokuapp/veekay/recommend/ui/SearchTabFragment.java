package com.herokuapp.veekay.recommend.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.herokuapp.veekay.recommend.R;
import com.herokuapp.veekay.recommend.models.User;
import com.herokuapp.veekay.recommend.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTabFragment extends Fragment implements View.OnClickListener {
    private ConstraintLayout searchResults;
    private TextView searchedUserUserName;
    private TextView searchedUserFirstName;
    private TextView searchedUserSecondName;
    private TextView addFriendButton;
    private User searchedUser;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Context mContext;


    public static SearchTabFragment newInstance() {
        return new SearchTabFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        // Instructs fragment to include menu options:
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_tab, container, false);
        mContext = getActivity().getApplicationContext();
        searchResults = view.findViewById(R.id.searchResults);
        searchResults.setVisibility(View.GONE);
        searchedUserUserName = view.findViewById(R.id.searchedUserUserName);
        searchedUserFirstName = view.findViewById(R.id.searchedUserFirstName);
        searchedUserSecondName = view.findViewById(R.id.searchedUserSecondName);
        addFriendButton = view.findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(this);

        return view;
    }
    @Override
    // Method is now void, menu inflater is now passed in as argument:
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Call super to inherit method from parent:
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.userSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                displaySuggestions(newText);
                return false;
            }
        });
    }
    public void getUsers(String query){
        searchResults.setVisibility(View.GONE);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Constants.USERS_DB_KEY);
        Query query1 = reference.orderByChild("userName").equalTo(query);
        query1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                searchedUser = dataSnapshot.getValue(User.class);
                searchedUserUserName.setText(searchedUser.getUserName());
                searchedUserFirstName.setText(searchedUser.getFirstName());
                searchedUserSecondName.setText(searchedUser.getSecondName());
                searchResults.setVisibility(View.VISIBLE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void displaySuggestions(String newText){
        Log.d("query", newText);
    }

    @Override
    public void onClick(View view) {
        if(view == addFriendButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null){
                CharSequence text = "You need to login first!!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(mContext, text, duration);
                toast.show();
            }else{
                Query getCurrentUserDetail = reference.orderByChild("userId").equalTo(user.getUid());
                getCurrentUserDetail.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        final User userDB = dataSnapshot.getValue(User.class);
                        userDB.addFriends(searchedUser.getPushId());
                        reference.child(userDB.getPushId()).child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("Pushid", userDB.getPushId());
                                reference.child(userDB.getPushId()).child("friends").setValue(userDB.getFriends()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            searchResults.setVisibility(View.GONE);
                                            CharSequence text = "Added successfully";
                                            int duration = Toast.LENGTH_SHORT;
                                            Toast toast = Toast.makeText(mContext, text, duration);
                                            toast.show();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
//                        reference.child(userDB.getPushId()).getRef().setValue(userDB).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()){
//                                    searchResults.setVisibility(View.GONE);
//                                    CharSequence text = "Added successfully";
//                                    int duration = Toast.LENGTH_SHORT;
//                                    Toast toast = Toast.makeText(mContext, text, duration);
//                                    toast.show();
//                                }
//                            }
//                        });

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Log.d("add Friend", "Will be added");
            }

        }
    }
}
