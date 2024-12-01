package fpoly.acount.codes.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import fpoly.acount.codes.DAO.LoaiSachDAO;
import fpoly.acount.codes.R;
import fpoly.acount.codes.model.LoaiSach;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

   private Context context;
   private ArrayList<LoaiSach> lst;
   private LoaiSachDAO dao;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> lst, LoaiSachDAO dao) {
        this.context = context;
        this.lst = lst;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaLoai.setText("ID: " + lst.get(position).getMaloai());
        holder.txtLoai.setText(lst.get(position).getTenloai());

        holder.imvDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Thong bao");
            builder.setIcon(R.drawable.baseline_warning_24);
            builder.setMessage("Ban co chac chan muon xoa " + lst.get(holder.getAdapterPosition()).getTenloai() + " khong ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int check = dao.xoaLoaiSach(lst.get(holder.getAdapterPosition()).getMaloai());
                    switch (check){
                        case -1:
                            Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                            break;
                        case 0:
                            Toast.makeText(context, "Ban can xoa nhung cuon sach trong the loai nay truoc", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                            lst.clear();
                            lst =dao.getDSLoaiSach();
                            notifyDataSetChanged();
                            break;
                    }
                }
            });
        builder.setNegativeButton("no", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        });

        holder.imvEdit.setOnClickListener(view -> {
            showDialog(lst.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtMaLoai, txtLoai;
        private ImageView imvEdit, imvDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtLoai = itemView.findViewById(R.id.txtLoai);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            imvEdit = itemView.findViewById(R.id.imvEdit);
            imvDelete = itemView.findViewById(R.id.imvDelete);
        }
    }

    private void showDialog(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_type, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);

        TextView txtTitle = view.findViewById(R.id.txtTitle);
        EditText edtTenLoai = view.findViewById(R.id.edtTenLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        edtTenLoai.setText(loaiSach.getTenloai());


        txtTitle.setText("Cap nhat thong tin");
        btnThem.setText("Cap nhat");

        btnThem.setOnClickListener(view1 -> {
            String tenLoai = edtTenLoai.getText().toString();
            LoaiSach loaiSach1 = new LoaiSach(loaiSach.getMaloai(), tenLoai);
            boolean check = dao.suaLoaiSach(loaiSach1);

            if (check){
                Toast.makeText(context, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                lst.clear();
                lst =dao.getDSLoaiSach();
                notifyDataSetChanged();
                alertDialog.dismiss();
            }else Toast.makeText(context, "Cap nhat that bai", Toast.LENGTH_SHORT).show();
        });

        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
    }
}
