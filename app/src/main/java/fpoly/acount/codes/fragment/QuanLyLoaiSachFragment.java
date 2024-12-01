package fpoly.acount.codes.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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

import fpoly.acount.codes.DAO.LoaiSachDAO;
import fpoly.acount.codes.R;
import fpoly.acount.codes.adapter.LoaiSachAdapter;
import fpoly.acount.codes.model.LoaiSach;


public class QuanLyLoaiSachFragment extends Fragment {
    RecyclerView rcvType;
    FloatingActionButton flbAdd;
    private LoaiSachDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_loai_sach, container, false);
        rcvType = view.findViewById(R.id.rcvType);
        flbAdd = view.findViewById(R.id.flbAdd);


        flbAdd.setOnClickListener(view1 -> {
            showDialog();
        });

        dao = new LoaiSachDAO(getContext());
        loadData();


        return view;

    }

    private void loadData(){
        ArrayList<LoaiSach> lst = dao.getDSLoaiSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvType.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter adapter = new LoaiSachAdapter(getContext(), lst, dao);
        rcvType.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_type, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();


        EditText edtTenLoai = view.findViewById(R.id.edtTenLoaiSach);
        Button btnLuu = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnLuu.setOnClickListener(view1 -> {
            String tenLoai = edtTenLoai.getText().toString();

            if (tenLoai.equals("")){
                Toast.makeText(getContext(), "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean check = dao.themLoaiSach(tenLoai);
            if (check){
                Toast.makeText(getContext(), "Them Thanh Cong", Toast.LENGTH_SHORT).show();
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
