package com.example.appcafe_v1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcafe_v1.Activity.BaseActivity;
import com.example.appcafe_v1.Activity.IntroActivity;
import com.example.appcafe_v1.Activity.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends BaseActivity {
    TextView txtName, txtEmail, txtAddress, txtPhone;
    TextView titleName, titleEmail;
    Button editProfile;
    ImageView back1,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.profileName);
        txtEmail = findViewById(R.id.profileEmail);
        txtAddress = findViewById(R.id.profileAddress);
        txtPhone = findViewById(R.id.profilePhone);
        titleName = findViewById(R.id.titleName);
        titleEmail = findViewById(R.id.titleEmail);
        editProfile = findViewById(R.id.editProfile);
        back1 = findViewById(R.id.backIcon1);
        home = findViewById(R.id.imageHome);

        ShowUserData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

public void ShowUserData() {

    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    String userKey = sharedPreferences.getString("userKey", "default_value"); // Lấy userKey
    // Tham chiếu đến Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("users");

    // Lấy dữ liệu người dùng từ Firebase theo key
    reference.child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                // Lấy dữ liệu từ snapshot
                String nameUser = snapshot.child("name").getValue(String.class);
                String emailUser = snapshot.child("email").getValue(String.class);
                String addressUser = snapshot.child("address").getValue(String.class);
                String phoneUser = snapshot.child("phone").getValue(String.class);

                // Hiển thị dữ liệu trong các TextView
                txtName.setText(nameUser);
                txtEmail.setText(emailUser);
                txtAddress.setText(addressUser);
                txtPhone.setText(phoneUser);
                titleName.setText(nameUser);
                titleEmail.setText(emailUser);
            } else {
                Toast.makeText(ProfileActivity.this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            // Xử lý lỗi nếu có
            Toast.makeText(ProfileActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}
    public void passUserData(){
        String userUsername = txtName.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                    String addressFromDB = snapshot.child(userUsername).child("address").getValue(String.class);
                    String phoneFromDB = snapshot.child(userUsername).child("phone").getValue(String.class);


                    Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);

                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("address", addressFromDB);
                    intent.putExtra("phone", phoneFromDB);


                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}