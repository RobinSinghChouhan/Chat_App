package com.example.database;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    FirebaseAuth auth;
    ProgressDialog pd;
  //  DatabaseReference databaseUsers;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.chat_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        login=findViewById(R.id.login_btn);
        auth=FirebaseAuth.getInstance();
     //   databaseUsers= FirebaseDatabase.getInstance().getReference("Users");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(LoginActivity.this);
                pd.setMessage("Logging In...");
                pd.setCancelable(false);
                pd.show();
                signIn();
            }
        });
    }
    public void signIn(){
        final String useremail=email.getText().toString();
        String pwd=password.getText().toString();
        if(useremail.isEmpty()){
            email.setError("Please enter email");
            email.requestFocus();
        }else if(pwd.isEmpty()){
            password.setError("Please enter password");
            password.requestFocus();
        }else{
            auth.signInWithEmailAndPassword(useremail,pwd).addOnCompleteListener(
                    LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login Error, Please" +
                                        "try again",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                                variables.currentuser=auth.getCurrentUser().getUid();
                                variables.currentEmail=auth.getCurrentUser().getEmail();
                             //   User user=new User(variables.currentuser,useremail);
                             //   databaseUsers.child(variables.currentuser).setValue(user);
                                Log.i("ID:",variables.currentuser);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }

                        }
                    }
            );
        }
    }
}
