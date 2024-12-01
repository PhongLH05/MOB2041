package fpoly.acount.codes.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import fpoly.acount.codes.DAO.SachDao;
import fpoly.acount.codes.R;
import fpoly.acount.codes.model.Sach;

public class SachAdapterr extends RecyclerView.Adapter<SachAdapterr.ViewHolder>{

    private Context context;
    private ArrayList<Sach> lst;
    private ArrayList<HashMap<String, Object>> lstHM;

    public SachAdapterr(Context context, ArrayList<Sach> lst, ArrayList<HashMap<String, Object>> lstHM) {
        this.context = context;
        this.lst = lst;
        this.lstHM = lstHM;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_book, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSach.setText("ID: " + lst.get(position).getMaSach());
        holder.txtTenSach.setText(lst.get(position).getTenSach());
        holder.txtGia.setText(String.valueOf(lst.get(position).getGiaBan()));
        holder.txtTacgia.setText(lst.get(position).getTacGia());

        holder.imgEdit.setOnClickListener(view -> {
            showDialogEdit(lst.get(holder.getAdapterPosition()));
        });

        holder.imgDel.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtSach, txtTenSach, txtTacgia, txtGia;
        ImageView imgEdit, imgDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSach = itemView.findViewById(R.id.txtSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTacgia = itemView.findViewById(R.id.txtTacGia);
            txtGia = itemView.findViewById(R.id.txtGia);

            imgEdit = itemView.findViewById(R.id.imvEdit);
            imgDel = itemView.findViewById(R.id.imvDelete);
        }
    }

    private void showDialogEdit(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_sach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTacGia = view.findViewById(R.id.edtTacGia);
        EditText edtTien = view.findViewById(R.id.edtGia);

        TextView txtMaSach = view.findViewById(R.id.txtMaSach);

        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        Button btnSua = view.findViewById(R.id.btnSua);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        txtMaSach.setText("Ma Sach; " + sach.getMaSach());
        edtTenSach.setText(sach.getTenSach());
        edtTacGia.setText(sach.getTacGia());
        edtTien.setText(String.valueOf(sach.getGiaBan()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, lstHM, android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});

        spnLoaiSach.setAdapter(simpleAdapter);

        int i = 0;
        int position = -1;
        for (HashMap<String, Object> item : lstHM){
            if ((int) item.get("maloai") == sach.getMaLoai()){
                i = position;
            }
            i++;
        }


        spnLoaiSach.setSelection(position);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnSua.setOnClickListener(view1 -> {
            String tensach = edtTenSach.getText().toString();
            String tagia = edtTacGia.getText().toString();
            int tien = Integer.parseInt(edtTien.getText().toString());
            HashMap<String, Object> hs = (HashMap<String, Object>)  spnLoaiSach.getSelectedItem();
            int maloai = (int) hs.get("maloai") ;
            SachDao dao = new SachDao(context);
            boolean check = dao.updateSach(sach.getMaSach() ,tensach,tagia, tien, maloai);
            if (check){
                Toast.makeText(context, "Cap nhat sach thanh cong", Toast.LENGTH_SHORT).show();
                loadData();
                alertDialog.dismiss();
            }else {
                Toast.makeText(context , "Cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void showDialogDel(){

    }

    private void loadData(){
        SachDao dao = new SachDao(context);
        lst.clear();
        lst = dao.getDSSach();
        notifyDataSetChanged();
    }
}
