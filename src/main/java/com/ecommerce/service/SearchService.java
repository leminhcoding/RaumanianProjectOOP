package com.ecommerce.service;

import com.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private List<Product> products;

    public SearchService(List<Product> products) {
        this.products = products;
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> result = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (Product p : products) {
            if (p.getTenSanPham().toLowerCase().contains(keyword) ||
                    p.getLoaiSanPham().toLowerCase().contains(keyword) ||
                    p.getMoTaSanPham().toLowerCase().contains(keyword)) {
                result.add(p);
            }
        }
        return result;
    }
}
