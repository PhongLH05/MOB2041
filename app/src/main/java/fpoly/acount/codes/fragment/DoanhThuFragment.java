package fpoly.acount.codes.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import fpoly.acount.codes.DAO.Top10DAO;
import fpoly.acount.codes.R;

public class DoanhThuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);

        EditText edtStart = view.findViewById(R.id.edtStart);
        EditText edtEnd = view.findViewById(R.id.edtEnd);
        Button btnThongKe = view.findViewById(R.id.btnThongke);
        TextView txtKetQua = view.findViewById(R.id.ttKetQua);

        Calendar calendar = Calendar.getInstance();

        edtStart.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String ngay ="";
                    String thang = "";
                    if (i2 < 10){
                        ngay = "0" + i2;
                    }else {
                        ngay = String.valueOf(i2);
                    }

                    if (i1 + 1 < 10){
                        thang = "0" + i1;
                    }else {
                        thang = String.valueOf(i1 + 1);
                    }
                    edtStart.setText(i + "/" + thang + "/" + ngay);
                }
            },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) );

            datePickerDialog.show();
        });

        edtEnd.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String ngay ="";
                    String thang = "";
                    if (i2 < 10){
                        ngay = "0" + i2;
                    }else {
                        ngay = String.valueOf(i2);
                    }

                    if (i1 + 1 < 10){
                        thang = "0" + i1;
                    }else {
                        thang = String.valueOf(i1 + 1);
                    }
                    edtEnd.setText(i + "/" + thang + "/" + ngay);
                }
            },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) );

            datePickerDialog.show();
        });



        btnThongKe.setOnClickListener(view1 -> {
            Top10DAO top10DAO = new Top10DAO(getContext());
            String st = edtStart.getText().toString();
            String e = edtEnd.getText().toString();
            int doanhthu= top10DAO.getDoanhThu(st, e);
            txtKetQua.setText(doanhthu + "VND");
        });

        return view;
    }
}
