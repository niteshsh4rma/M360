package com.nitesh.movie360;


import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class RequestContent extends AppCompatActivity {

    MaterialButton requestbutton;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    TextInputEditText contentname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_content);


        contentname = findViewById(R.id.requestinput);



        requestbutton = findViewById(R.id.requestbutton);


        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                ContentRequest request = new ContentRequest();
                request.setName(contentname.getText().toString());

                if (contentname.getText().toString().length() != 0){
                    databaseReference.child("Requests").push().setValue(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                            Snackbar snackbar = Snackbar.make(v, "Successfully Requested", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            snackbar.show();



                        }
                    });
                }

                else {
                    Snackbar.make(v, "Please Enter Content", Snackbar.LENGTH_LONG).show();
                }


            }
        });
    }
}
