package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    // Getter & Setter
    public String getTenSanPham() { return tenSanPham; }
    public String getGia() { return gia; }
    public String getLoaiSanPham() { return loaiSanPham; }
    public String getDiemDanhGiaTrungBinh() { return diemDanhGiaTrungBinh; }
    public String getSoLuotDanhGia() { return soLuotDanhGia; }
    public String getMoTaSanPham() { return moTaSanPham; }
    public String getNguonDuLieu() { return nguonDuLieu; }
    public String getAnh() { return anh; }
}
