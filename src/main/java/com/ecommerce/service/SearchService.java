package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private final List<Product> products;

    public SearchService(ProductRepository repository) {
        this.products = repository.getAllProducts();
    }

    public List<Product> searchProducts(String query) {
        String[] keywords = query.toLowerCase().split("\\s+");
        List<Product> results = new ArrayList<>();

        for (Product product : products) {
            String combined = (product.getTenSanPham() + " " + product.getMoTaSanPham()).toLowerCase();
            boolean match = true;
            for (String keyword : keywords) {
                if (!combined.contains(keyword)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                results.add(product);
            }
        }
        return results;
    }
}
