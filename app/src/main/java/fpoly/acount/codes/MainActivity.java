package fpoly.acount.codes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import fpoly.acount.codes.DAO.ThuThuDAO;
import fpoly.acount.codes.fragment.DoanhThuFragment;
import fpoly.acount.codes.fragment.QuanLyLoaiSachFragment;
import fpoly.acount.codes.fragment.QuanLyPhieuMuonFragment;
import fpoly.acount.codes.fragment.QuanLySachFragment;
import fpoly.acount.codes.fragment.QuanLyThanhVienFragment;
import fpoly.acount.codes.fragment.Top10Fragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View decoreView = getWindow().getDecorView();
        decoreView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsets onApplyWindowInsets(@NonNull View view, @NonNull WindowInsets windowInsets) {
                int left = windowInsets.getSystemWindowInsetLeft();
                int top = windowInsets.getSystemWindowInsetTop();
                int bottom = windowInsets.getSystemWindowInsetBottom();
                int right = windowInsets.getSystemWindowInsetRight();
                view.setPadding(left, top, right, bottom);
                return windowInsets.consumeSystemWindowInsets();
            }
        });

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);

        View headerLayout = navigationView.getHeaderView(0);
        TextView txtTem = headerLayout.findViewById(R.id.txtTen);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.linerlayout, new QuanLyLoaiSachFragment()).commit();

        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        String loaiTk = sharedPreferences.getString("loaitaikhoan", "");
        if (!loaiTk.equals("admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mDoanhThu).setVisible(false);
            menu.findItem(R.id.mTop10).setVisible(false);
        }

        String hoten = sharedPreferences.getString("hoten", "");
        txtTem.setText("Welcome, "+ hoten);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if(item.getItemId() == R.id.mQLS){
                    fragment = new QuanLySachFragment();
                }else if (item.getItemId() == R.id.mQLPM){
                  fragment = new QuanLyPhieuMuonFragment();
                } else if (item.getItemId() == R.id.mQLLS){
                    fragment = new QuanLyLoaiSachFragment();
                }else if (item.getItemId() == R.id.mQLTV){
                    fragment = new QuanLyThanhVienFragment();
                } else if (item.getItemId() == R.id.mTop10) {
                    fragment = new Top10Fragment();
                } else if (item.getItemId() == R.id.mDoanhThu) {
                    fragment = new DoanhThuFragment();
                } else if (item.getItemId() == R.id.mDoiMatKhau) {
                    showDialogDoiMatKhau();
                } else if (item.getItemId() == R.id.mDangXuat) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    fragment = new QuanLySachFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.linerlayout, fragment).commit();
                getSupportActionBar().setTitle(item.getTitle());
                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if (item.getItemId() == android.R.id.home){
           drawerLayout.openDrawer(GravityCompat.START);
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
             super.onBackPressed();
    }

    public void showDialogDoiMatKhau(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Cap nhat", null)
                .setPositiveButton("Huy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doi_mat_khau, null);
        EditText edtOldPass = view.findViewById(R.id.edtOldPass);
        EditText edtNewPass = view.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = view.findViewById(R.id.edtReNewPass);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
            String oldPass = edtOldPass.getText().toString();
            String NewPass = edtNewPass.getText().toString();
            String ReNewPass = edtReNewPass.getText().toString();
            SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
            String matt = sharedPreferences.getString("matt", "");
            if (NewPass.equals(ReNewPass)){
                ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                boolean check = thuThuDAO.UpdatePass(matt, oldPass, NewPass);
                if (check){
                    Toast.makeText(MainActivity.this, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Cap nhat that bai", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(MainActivity.this, "Mat khau khong trung voi nhau", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
