package fpoly.acount.codes.model;

public class Sach {
    private int maSach;
    private String tenSach;
    private String tacGia;
    private int giaBan;
    private int maLoai;
    private int soLuongdaMuon;

    public Sach(int maSach, String tenSach, String tacGia, int giaBan, int maLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.giaBan = giaBan;
        this.maLoai = maLoai;
    }

    public Sach( int maSach,String tenSach,  int soLuongdaMuon) {
        this.tenSach = tenSach;
        this.maSach = maSach;
        this.soLuongdaMuon = soLuongdaMuon;
    }

    public Sach() {
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getSoLuongdaMuon() {
        return soLuongdaMuon;
    }

    public void setSoLuongdaMuon(int soLuongdaMuon) {
        this.soLuongdaMuon = soLuongdaMuon;
    }
}
