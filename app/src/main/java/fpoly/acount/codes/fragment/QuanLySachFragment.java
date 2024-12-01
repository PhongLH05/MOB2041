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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import fpoly.acount.codes.DAO.LoaiSachDAO;
import fpoly.acount.codes.DAO.SachDao;
import fpoly.acount.codes.R;
import fpoly.acount.codes.adapter.SachAdapterr;
import fpoly.acount.codes.model.LoaiSach;
import fpoly.acount.codes.model.Sach;


public class QuanLySachFragment extends Fragment {

    private RecyclerView rcvSach;
    private SachDao dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quan_ly_sach, container, false);
        // Inflate the layout for this fragment
        rcvSach = view.findViewById(R.id.rcvSach);
        FloatingActionButton flbAdding = view.findViewById(R.id.floatAdding);
        dao = new SachDao(getContext());

        flbAdding.setOnClickListener(view1 -> {
            showDialog();
        });

        loadData();
        return view;
    }

    private void loadData(){
        ArrayList<Sach> lst = dao.getDSSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSach.setLayoutManager(linearLayoutManager);
        SachAdapterr adapter = new SachAdapterr(getContext(), lst, getDSLoaiSach());
        rcvSach.setAdapter(adapter);
    }


    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtGia);
        EditText edtTacGia = view.findViewById(R.id.edtTacGia);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),
                getDSLoaiSach(), android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});


        spnLoaiSach.setAdapter(simpleAdapter);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        btnThem.setOnClickListener(view1 -> {
            String tensach = edtTenSach.getText().toString();
            String tagia = edtTacGia.getText().toString();
            int tien = Integer.parseInt(edtTien.getText().toString());
            HashMap<String, Object> hs = (HashMap<String, Object>)  spnLoaiSach.getSelectedItem();
            int maloai = (int) hs.get("maloai") ;

            boolean check = dao.themSach(tensach,tagia, tien, maloai);

            if (check){
                Toast.makeText(getContext(), "Them sach thanh cong", Toast.LENGTH_SHORT).show();
                loadData();
                alertDialog.dismiss();
            }else {
                Toast.makeText(getContext(), "Them khong thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }

    private ArrayList<HashMap<String, Object>> getDSLoaiSach(){
        LoaiSachDAO dao1 = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> lst = dao1.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> lstHM = new ArrayList<>();

        for (LoaiSach loaiSach : lst){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", loaiSach.getMaloai());
            hs.put("tenloai", loaiSach.getTenloai());
            lstHM.add(hs);
        }

        return lstHM;
    }
}
