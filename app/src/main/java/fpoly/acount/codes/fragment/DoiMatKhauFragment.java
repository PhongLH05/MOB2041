package fpoly.acount.codes.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.acount.codes.DAO.ThuThuDAO;
import fpoly.acount.codes.LoginActivity;
import fpoly.acount.codes.MainActivity;
import fpoly.acount.codes.R;


public class DoiMatKhauFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        EditText edtOldPass = view.findViewById(R.id.edtOldPass);
        EditText edtNewPass = view.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = view.findViewById(R.id.edtReNewPass);

        Button btnDoimatKhau = view.findViewById(R.id.btnDoimatKhau);


        btnDoimatKhau.setOnClickListener(view1 -> {
            String oldPass = edtOldPass.getText().toString();
            String NewPass = edtNewPass.getText().toString();
            String ReNewPass = edtReNewPass.getText().toString();
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("info", MODE_PRIVATE);
            String matt = sharedPreferences.getString("matt", "");
            if (NewPass.equals(ReNewPass)){
                ThuThuDAO thuThuDAO = new ThuThuDAO(getContext());
                boolean check = thuThuDAO.UpdatePass(matt, oldPass, NewPass);
                if (check){
                    Toast.makeText(getContext(), "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "Cap nhat that bai", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getContext(), "Mat khau khong trung voi nhau", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
