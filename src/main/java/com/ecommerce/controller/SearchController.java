package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.SearchService;

import java.util.List;

public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    public void handleSearch(String query) {
        List<Product> results = searchService.searchProducts(query);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm phù hợp.");
            return;
        }

        for (Product product : results) {
            System.out.println("Tên sản phẩm: " + safeDisplay(product.getTenSanPham()));
            System.out.println("Giá: " + safeDisplay(product.getGia()));
            System.out.println("Loại sản phẩm: " + safeDisplay(product.getLoaiSanPham()));
            System.out.println("Điểm đánh giá trung bình: " + safeDisplay(product.getDiemDanhGiaTrungBinh()));
            System.out.println("Số lượt đánh giá: " + safeDisplay(product.getSoLuotDanhGia()));
            System.out.println("Mô tả sản phẩm: " + safeDisplay(product.getMoTaSanPham()));
            System.out.println("Nguồn dữ liệu: " + safeDisplay(product.getNguonDuLieu()));
            System.out.println("---------------------------");
        }
    }

    private String safeDisplay(String value) {
        return (value == null || value.isEmpty()) ? "Đang cập nhật" : value;
    }
    public List<Product> search(String query) {
        return searchService.searchProducts(query);
    }
}
