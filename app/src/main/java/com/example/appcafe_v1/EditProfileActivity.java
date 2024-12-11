package com.example.appcafe_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcafe_v1.Activity.BaseActivity;
import com.example.appcafe_v1.Activity.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends BaseActivity {
    EditText editName, editEmail,editAddress, editPhone;
    Button saveButton;
    ImageView back2;
    String nameUser, emailUser, addressUser, phoneUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        reference = FirebaseDatabase.getInstance().getReference("users");

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        back2 = findViewById(R.id.backIcon2);

        saveButton = findViewById(R.id.saveButton);
        showData();
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name", nameUser);
                intent.putExtra("email", emailUser);
                intent.putExtra("address", addressUser);
                intent.putExtra("phone", phoneUser);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged() || isEmailChanged() || isAddressChanged() || isPhoneChanged()) {
                    Toast.makeText(EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    intent.putExtra("name", nameUser);
                    intent.putExtra("email", emailUser);
                    intent.putExtra("address", addressUser);
                    intent.putExtra("phone", phoneUser);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditProfileActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean isNameChanged(){
        if (!nameUser.equals(editName.getText().toString())){
            reference.child(nameUser).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isEmailChanged(){
        if (!emailUser.equals(editEmail.getText().toString())){
            reference.child(nameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else{
            return false;
        }
    }
    public boolean isAddressChanged(){
        if (!addressUser.equals(editAddress.getText().toString())){
            reference.child(nameUser).child("address").setValue(editAddress.getText().toString());
            addressUser = editAddress.getText().toString();
            return true;
        } else{
            return false;
        }
    }
    public boolean isPhoneChanged(){
        if (!phoneUser.equals(editPhone.getText().toString())){
            reference.child(nameUser).child("phone").setValue(editPhone.getText().toString());
            phoneUser = editPhone.getText().toString();
            return true;
        } else{
            return false;
        }
    }
    public void showData(){
        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        addressUser = intent.getStringExtra("address");
        phoneUser = intent.getStringExtra("phone");


        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editAddress.setText(addressUser);
        editPhone.setText(phoneUser);

    }
}