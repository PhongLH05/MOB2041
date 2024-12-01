package fpoly.acount.codes.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.acount.codes.DAO.ThanhVienDAO;
import fpoly.acount.codes.R;
import fpoly.acount.codes.model.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ThanhVien> lst;
    ThanhVienDAO dao;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanh_vien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaTv.setText("ID: " + lst.get(position).getMatv());
        holder.txtHoten.setText("Ho ten: " + lst.get(position).getHoten());
        holder.txtNamSinh.setText("Nam sinh: " + lst.get(position).getNamsinh());

        holder.imvEdit.setOnClickListener(view -> {
            showDialog(lst.get(holder.getAdapterPosition()));
        });

        holder.imvDel.setOnClickListener(view -> {
            int check = dao.xoaThanhVien(lst.get(holder.getAdapterPosition()).getMatv());
            switch (check){
                case 1:
                    Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                    break;
                case 0:
                    Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(context, "Khong the xoa do ton tai phieu muon", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaTv, txtHoten, txtNamSinh;
        ImageView imvEdit, imvDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTv = itemView.findViewById(R.id.txtMaTv);
            txtHoten = itemView.findViewById(R.id.txtHoTen);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);
            imvEdit = itemView.findViewById(R.id.imvEdit);
            imvDel = itemView.findViewById(R.id.imvDel);
            dao = new ThanhVienDAO(context);
        }
    }


    private void showDialog(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_thanh_vien, null);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView txtMAtv = view.findViewById(R.id.txtMAtv);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        Button btnSua = view.findViewById(R.id.btnSua);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        txtMAtv.setText("Ma TV: " + thanhVien.getMatv());
        edtHoTen.setText(thanhVien.getHoten());
        edtNamSinh.setText(thanhVien.getNamsinh());


        btnSua.setOnClickListener(view1 -> {
            String hoten = edtHoTen.getText().toString();
            String namsinh = edtNamSinh.getText().toString();
            int id = thanhVien.getMatv();

            boolean check = dao.UpdateThanhVien(id, hoten, namsinh);
            if (check){
                Toast.makeText(context, "Cap nnhat thanh cong", Toast.LENGTH_SHORT).show();
                loadData();
                alertDialog.dismiss();
            }else {
                Toast.makeText(context, "Cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }

    private void loadData(){
        lst.clear();
        lst = dao.getDSThanhVien();
        notifyDataSetChanged();
    }
}
