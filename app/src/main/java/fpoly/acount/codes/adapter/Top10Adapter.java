package fpoly.acount.codes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.acount.codes.R;
import fpoly.acount.codes.model.Sach;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder>{

    private Context context;
    private ArrayList<Sach> lst;

    public Top10Adapter(Context context, ArrayList<Sach> lst) {
        this.context = context;
        this.lst = lst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top_10, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText("Ma Sach: " + lst.get(position).getMaSach());
        holder.txtSach.setText("Ten Sach: " + lst.get(position).getTenSach());
        holder.txtSoLuongMuon.setText("So Luong Muon: " + lst.get(position).getSoLuongdaMuon());
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach, txtSach, txtSoLuongMuon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtSach = itemView.findViewById(R.id.txtSach);
            txtSoLuongMuon = itemView.findViewById(R.id.txtSoLuongMuon);
        }
    }
}
