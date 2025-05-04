package com.ecommerce.repository;

import com.ecommerce.model.Product;
import com.ecommerce.utils.FileUtil;

import java.util.List;

public class ProductRepository {
    private List<Product> productList;

    public ProductRepository() {
        this.productList = FileUtil.loadProductsFromJson();
    }

    public List<Product> getAllProducts() {
        return productList;
    }
}
