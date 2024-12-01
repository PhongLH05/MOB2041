package fpoly.acount.codes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import fpoly.acount.codes.DAO.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        edtPass = findViewById(R.id.edtPass);
        edtUser = findViewById(R.id.edtUser);
        btnLogin = findViewById(R.id.btnLogin);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);


        btnLogin.setOnClickListener(view -> {
            String user = edtUser.getText().toString();
            String pass = edtPass.getText().toString();

            if (thuThuDAO.checkDangNhap(user, pass)){

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else {
                Toast.makeText(this, "Mat khau khong dung", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
