package com.example.appcafe_v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcafe_v1.Activity.BaseActivity;
import com.example.appcafe_v1.Activity.IntroActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends BaseActivity {

    EditText editTextName, editTextEmail, editTextPassword,editTextAddress, editTextPhone;
    Button signupBtn;
    TextView loginNow;
    ImageView back;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.signup_name);
        editTextEmail = findViewById(R.id.signup_email);
        editTextPassword = findViewById(R.id.signup_password);
        editTextAddress = findViewById(R.id.signup_address);
        editTextPhone = findViewById(R.id.signup_phone);
        back = findViewById(R.id.backIconRes);
        loginNow = findViewById(R.id.loginNow);
        signupBtn = findViewById(R.id.signup_Btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, IntroActivity.class);
                startActivity(intent);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                // Ràng buộc cho name
                if (name.isEmpty()) {
                    editTextName.setError("Tên không được để trống");
                    return;
                }
                if (name.length() < 3) {
                    editTextName.setError("Tên phải có ít nhất 3 ký tự");
                    return;
                }

                // Ràng buộc cho email
                if (email.isEmpty()) {
                    editTextEmail.setError("Email không được để trống");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Email không hợp lệ");
                    return;
                }
                if (phone.length() != 10) {
                    editTextPassword.setError("Số điện thoại chưa đúng định dạng");
                    return;
                }
                // Ràng buộc cho password
                if (password.isEmpty()) {
                    editTextPassword.setError("Mật khẩu không được để trống");
                    return;
                }
                if (password.length() < 6) {
                    editTextPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
                    return;
                }

                HelperClass helperClass = new HelperClass(name, email, password, address, phone);
                reference.child(name).setValue(helperClass);

                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}