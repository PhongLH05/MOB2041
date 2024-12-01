package fpoly.acount.codes.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.acount.codes.DAO.ThanhVienDAO;
import fpoly.acount.codes.R;
import fpoly.acount.codes.adapter.ThanhVienAdapter;
import fpoly.acount.codes.model.ThanhVien;


public class QuanLyThanhVienFragment extends Fragment {

    ThanhVienDAO dao;
    ArrayList<ThanhVien> lst;
    RecyclerView rcvQLTV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quan_ly_thanh_vien, container, false);

        rcvQLTV = view.findViewById(R.id.rcvQLTV);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAd);


        dao = new ThanhVienDAO(getContext());


        loadData();

        floatAdd.setOnClickListener(view1 -> {
            showDDialog();
        });

        return view;
    }

    private void loadData(){
        lst = dao.getDSThanhVien();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvQLTV.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(getContext(), lst);
        rcvQLTV.setAdapter(adapter);
    }

    private void showDDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_thanh_vien, null);
        builder.setView(view);

        EditText edthoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        AlertDialog alertDialog =builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        btnThem.setOnClickListener(view1 -> {
            String hoten = edthoTen.getText().toString();
            String namsinh = edtNamSinh.getText().toString();

            boolean check = dao.themThanhVien(hoten, namsinh);
            if(check){
                Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                loadData();
                alertDialog.dismiss();
            }else {
                Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
            }
        });


        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }

}
