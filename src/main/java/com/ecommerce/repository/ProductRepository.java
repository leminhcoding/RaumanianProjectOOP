package com.ecommerce.repository;

import com.ecommerce.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class ProductRepository {
    private List<Product> productList;

    public ProductRepository() {
        this.productList = loadProductsFromJson();
    }

    private List<Product> loadProductsFromJson() {
        try {
            // Đường dẫn tuyệt đối tới JSON file (chạy được từ IDE)
            String path = "src/main/java/resources/all_products.json";
            FileInputStream fis = new FileInputStream(Paths.get(path).toFile());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(fis, new TypeReference<List<Product>>() {});
        } catch (Exception e) {
            throw new RuntimeException("❌ Lỗi đọc file JSON: " + e.getMessage(), e);
        }
    }

    public List<Product> getAllProducts() {
        return productList != null ? productList : Collections.emptyList();
    }
}
