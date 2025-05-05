package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // ✅ Bỏ qua các field không xác định (vd: score)
public class Product {
    @JsonProperty("Tên sản phẩm")
    private String tenSanPham;

    @JsonProperty("Giá")
    private String gia;

    @JsonProperty("Loại sản phẩm")
    private String loaiSanPham;

    @JsonProperty("Điểm đánh giá trung bình")
    private String diemDanhGiaTrungBinh;

    @JsonProperty("Số lượt đánh giá")
    private String soLuotDanhGia;

    @JsonProperty("Mô tả sản phẩm")
    private String moTaSanPham;

    @JsonProperty("Nguồn dữ liệu")
    private String nguonDuLieu;

    @JsonProperty("Ảnh")
    private String anh;

    // ✅ Bổ sung nếu muốn sử dụng score để hiển thị kết quả semantic
    private Double score;

    // Getters & Setters
    public String getTenSanPham() { return tenSanPham; }
    public String getGia() { return gia; }
    public String getLoaiSanPham() { return loaiSanPham; }
    public String getDiemDanhGiaTrungBinh() { return diemDanhGiaTrungBinh; }
    public String getSoLuotDanhGia() { return soLuotDanhGia; }
    public String getMoTaSanPham() { return moTaSanPham; }
    public String getNguonDuLieu() { return nguonDuLieu; }
    public String getAnh() { return anh; }
    public Double getScore() { return score; }

    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public void setGia(String gia) { this.gia = gia; }
    public void setLoaiSanPham(String loaiSanPham) { this.loaiSanPham = loaiSanPham; }
    public void setDiemDanhGiaTrungBinh(String diemDanhGiaTrungBinh) { this.diemDanhGiaTrungBinh = diemDanhGiaTrungBinh; }
    public void setSoLuotDanhGia(String soLuotDanhGia) { this.soLuotDanhGia = soLuotDanhGia; }
    public void setMoTaSanPham(String moTaSanPham) { this.moTaSanPham = moTaSanPham; }
    public void setNguonDuLieu(String nguonDuLieu) { this.nguonDuLieu = nguonDuLieu; }
    public void setAnh(String anh) { this.anh = anh; }
    public void setScore(Double score) { this.score = score; }
}
