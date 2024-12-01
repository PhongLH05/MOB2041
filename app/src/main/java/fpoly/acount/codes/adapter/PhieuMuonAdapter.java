package fpoly.acount.codes.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.acount.codes.DAO.PhieuMuonDAO;
import fpoly.acount.codes.R;
import fpoly.acount.codes.model.PhieuMuon;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{
    private Context context;
    private ArrayList<PhieuMuon> lst;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieumuon, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenTv.setText("Tên Thành Viên: " + lst.get(position).getTentv());
        holder.txtTentt.setText("Tên Thủ Thư: "  + lst.get(position).getTentt());
        holder.txtNgay.setText("Ngày Mượn: " + lst.get(position).getNgay());
        holder.txtTenSach.setText("Tên Sách: " +  lst.get(position).getTensach());
        holder.txtTienThue.setText("Tiền Thuê: " + lst.get(position).getTienthue());
        String status = "";
        if (lst.get(position).getTrasach() == 1){
            status = "Đã tra sách";
            holder.btnTraSach.setVisibility(View.GONE);
        }else {
            status = "Chưa trả saách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txtTraSach.setText("Trạng thai: " + status);

        holder.btnTraSach.setOnClickListener(view -> {
            PhieuMuonDAO dao = new PhieuMuonDAO(context);
            boolean check = dao.changeStatus(lst.get(holder.getAdapterPosition()).getMapm());
            if (check){
                lst.clear();
                lst = dao.getDSPhieuMuon();
                notifyDataSetChanged();
            }else {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenTv, txtTentt, txtTenSach, txtNgay, txtTienThue, txtTraSach;
        Button btnTraSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenTv = itemView.findViewById(R.id.txtTenTv);
            txtTentt = itemView.findViewById(R.id.txtTenTt);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTienThue = itemView.findViewById(R.id.txtTienThue);
            txtTraSach = itemView.findViewById(R.id.txtTraSach);

            btnTraSach = itemView.findViewById(R.id.btnTraSach);
        }
    }
}
