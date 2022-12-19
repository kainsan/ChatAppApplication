package com.example.chatappapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.chatappapplication.messages.MessagesAdapter;
import com.example.chatappapplication.messages.MessagesList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private  String mobile;
    private  String email;
    private  String name;
     private RecyclerView messagesRecyclerView;
     private MessagesAdapter messagesAdapter;
    private  List<MessagesList> messagesList = new ArrayList<>();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatappapplication-582b0-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        final CircleImageView userProfilePic = findViewById(R.id.userProfilePic);

        messagesRecyclerView =  findViewById(R.id.messagesRecyclerView);
        messagesAdapter = new MessagesAdapter(messagesList ,this);

             //get intent data from Register.class activity
            mobile = getIntent().getStringExtra("mobile");
            email = getIntent().getStringExtra("email");
            name = getIntent().getStringExtra("name");

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setAdapter(messagesAdapter);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        //get profile pic from firebase database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final String profilePicUrl = snapshot.child("users").child(mobile).child("profile_pic").getValue(String.class);

                if(!profilePicUrl.isEmpty())
                {
                    //set profile image to circle image view
                    Picasso.get().load(profilePicUrl).into(userProfilePic);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
           }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesList.clear();

                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()){

                    final  String getMobile = dataSnapshot.getKey();

                    if(!getMobile.equals(mobile))
                    {
                       final String getName= dataSnapshot.child("name").getValue(String.class);
                       final String getProfilePic = dataSnapshot.child("profile_pic").getValue(String.class);

                       MessagesList messagesList = new MessagesList(getName,getMobile,"",getProfilePic,0);
                       messagesList.add(messagesList);
                    }
                }

                messagesRecyclerView.setAdapter( new MessagesAdapter(messagesList,MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}