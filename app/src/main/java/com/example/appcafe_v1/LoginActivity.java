package com.example.appcafe_v1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcafe_v1.Activity.BaseActivity;
import com.example.appcafe_v1.Activity.IntroActivity;
import com.example.appcafe_v1.Activity.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends BaseActivity {

    EditText login_email, login_password;
    Button login_Btn;
    TextView registerNow;
    ImageView back1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email =findViewById(R.id.Login_email);
        login_password =findViewById(R.id.Login_password);
        login_Btn = findViewById(R.id.Login_Btn);
        registerNow =findViewById(R.id.resNow);
        back1 = findViewById(R.id.backIconLogin);

        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUserName() | !validatePassword()){

                }else{
                    checkUser();
                }
            }
        });
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, IntroActivity.class);
                startActivity(intent);
            }
        });
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public Boolean validateUserName(){
        String val = login_email.getText().toString();
        if(val.isEmpty()){
            login_email.setError("Username cannot be empty");
            return false;
        }else{
            login_email.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = login_password.getText().toString();
        if(val.isEmpty()){
            login_password.setError("Password cannot be empty");
            return false;
        }else{
            login_password.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String userUsername = login_email.getText().toString();
        String userPassword = login_password.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDataBase = reference.orderByChild("name").equalTo(userUsername);

        checkUserDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    login_email.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userPassword)){
                        // Correct password, proceed to main activity
                        login_email.setError(null);

                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);

                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userKey", nameFromDB); // Lưu userKey
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        // Incorrect password
                        login_password.setError("Invalid Credentials");
                        login_password.requestFocus();
                    }
                } else {
                    // Username does not exist
                    login_email.setError("User does not exist");
                    login_email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}