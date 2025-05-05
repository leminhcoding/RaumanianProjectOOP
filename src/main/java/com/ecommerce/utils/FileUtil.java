package com.ecommerce.utils;

import com.ecommerce.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtil {
    public static List<Product> loadProductsFromJson() {
        try {
            String relativePath = "src/main/java/resources/product_texts.json";
            File jsonFile = new File(relativePath);
            if (!jsonFile.exists()) {
                throw new RuntimeException("❌ Không tìm thấy file JSON: " + jsonFile.getAbsolutePath());
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonFile, new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            throw new RuntimeException("❌ Lỗi đọc file JSON: " + e.getMessage(), e);
        }
    }

    public static List<Product> loadSemanticResults(String path) {
        try {
            File jsonFile = new File(path);
            if (!jsonFile.exists()) {
                throw new RuntimeException("❌ Không tìm thấy file kết quả semantic: " + jsonFile.getAbsolutePath());
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonFile, new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            throw new RuntimeException("❌ Lỗi đọc file semantic JSON: " + e.getMessage(), e);
        }
    }
}
