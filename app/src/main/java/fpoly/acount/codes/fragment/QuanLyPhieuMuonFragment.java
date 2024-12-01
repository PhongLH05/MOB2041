package fpoly.acount.codes.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import fpoly.acount.codes.DAO.PhieuMuonDAO;
import fpoly.acount.codes.DAO.SachDao;
import fpoly.acount.codes.DAO.ThanhVienDAO;
import fpoly.acount.codes.R;
import fpoly.acount.codes.adapter.PhieuMuonAdapter;
import fpoly.acount.codes.model.PhieuMuon;
import fpoly.acount.codes.model.Sach;
import fpoly.acount.codes.model.ThanhVien;


public class QuanLyPhieuMuonFragment extends Fragment {

    RecyclerView rcvQLPM;
    FloatingActionButton flbADD;
    PhieuMuonDAO dao;
    ArrayList<PhieuMuon> lst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_phieu_muon, container, false);
        rcvQLPM = view.findViewById(R.id.rcvQLPM);
        flbADD = view.findViewById(R.id.flbADD);


       loadData();

        flbADD.setOnClickListener(view1 -> {
            showDialog();
        });
        return view;
    }

    private void loadData(){
        dao = new PhieuMuonDAO(getContext());
        lst = dao.getDSPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvQLPM.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(getContext(), lst);
        rcvQLPM.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_phieu_muon, null);
        builder.setView(view);


        Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        getDataTv(spnThanhVien);
        getDataSach(spnSach);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        btnThem.setOnClickListener(view1 -> {
            HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
            int maTV =(int)  hsTV.get("matv");

            HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
            int maSach = (int) hsSach.get("masach");

            int Tien =(int)  hsSach.get("giaban");
            themPhieuMuon(maTV, maTV, Tien);
            alertDialog.dismiss();
        });

        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });




    }

    private void getDataTv(Spinner spnThanhVien){
        ThanhVienDAO dao1 = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> lst = dao1.getDSThanhVien();

        ArrayList<HashMap<String, Object>> lstHM = new ArrayList<>();
        for (ThanhVien tv : lst){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getHoten());
            lstHM.add(hs);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(), lstHM, android.R.layout.simple_list_item_1, new String[]{"hoten"}, new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(adapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDao dao2 = new SachDao(getContext());
        ArrayList<Sach> lst = dao2.getDSSach();

        ArrayList<HashMap<String, Object>> lstHM = new ArrayList<>();
        for (Sach sc : lst){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sc.getMaSach());
            hs.put("tensach", sc.getTenSach());
            hs.put("giaban", sc.getGiaBan());
            lstHM.add(hs);
        }

        SimpleAdapter adapter = new SimpleAdapter(getContext(), lstHM, android.R.layout.simple_list_item_1, new String[]{"tensach"}, new int[]{android.R.id.text1});
        spnSach.setAdapter(adapter);
    }

    private void themPhieuMuon(int matv, int masach, int tien){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt", "");

        Date currentTine = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = simpleDateFormat.format(currentTine);

        PhieuMuon phieuMuon = new PhieuMuon(matv, matt, masach, date, 0, tien);
        boolean check = dao.ThemPhieuMuon(phieuMuon);
        if (check){
            Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
            loadData();
        }else {
            Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
        }
    }
}
