package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.SearchService;
import com.ecommerce.utils.FileUtil;

import java.io.IOException;
import java.util.List;

public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    // ✅ Dùng cho giao diện console (in kết quả ra màn hình)
    public void handleSearch(String query) {
        System.out.println("🔍 Tìm kiếm với từ khóa: " + query);

        List<Product> results = handleSearchAndReturn(query);

        if (results.isEmpty()) {
            System.out.println("❌ Không tìm thấy kết quả từ cả hai mô hình.");
            return;
        }

        System.out.println("📌 Kết quả tìm được:");
        for (Product product : results) {
            inRaSanPham(product);
        }
    }

    // ✅ Dùng cho giao diện GUI – Trả về danh sách kết quả để hiển thị
    public List<Product> handleSearchAndReturn(String query) {
        List<Product> ragResults = null;

        try {
            Process process = Runtime.getRuntime().exec("python src/main/java/resources/semantic_search.py \"" + query + "\"");
            process.waitFor();

            ragResults = FileUtil.loadSemanticResults("src/main/java/resources/top_results.json");
            if (ragResults != null && !ragResults.isEmpty()) {
                return ragResults;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("❌ Lỗi khi gọi semantic search: " + e.getMessage());
        }

        // Fallback sang tìm kiếm từ khóa
        return searchService.searchProducts(query);
    }

    private void inRaSanPham(Product product) {
        System.out.println("Tên sản phẩm: " + safeDisplay(product.getTenSanPham()));
        System.out.println("Giá: " + safeDisplay(product.getGia()));
        System.out.println("Loại sản phẩm: " + safeDisplay(product.getLoaiSanPham()));
        System.out.println("Điểm đánh giá trung bình: " + safeDisplay(product.getDiemDanhGiaTrungBinh()));
        System.out.println("Số lượt đánh giá: " + safeDisplay(product.getSoLuotDanhGia()));
        System.out.println("Mô tả sản phẩm: " + safeDisplay(product.getMoTaSanPham()));
        System.out.println("Nguồn dữ liệu: " + safeDisplay(product.getNguonDuLieu()));
        System.out.println("---------------------------");
    }

    private String safeDisplay(String value) {
        return (value == null || value.isEmpty()) ? "Đang cập nhật" : value;
    }

    // Dùng cho tìm kiếm từ khóa đơn thuần nếu cần
    public List<Product> search(String query) {
        return searchService.searchProducts(query);
    }
}
