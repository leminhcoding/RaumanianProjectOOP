package com.ecommerce.ui;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.SearchService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchGUI extends JFrame {
    private final JTextField searchField;
    private final JButton searchButton;
    private final JPanel resultsPanel;
    private final SearchService searchService;

    public SearchGUI() {
        setTitle("Tìm kiếm sản phẩm");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ProductRepository repository = new ProductRepository();
        this.searchService = new SearchService(repository);

        // Search bar
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchButton = new JButton("Tìm kiếm");

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        // Results panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchAndDisplay());

        setVisible(true);
    }

    private void searchAndDisplay() {
        String query = searchField.getText();
        List<Product> results = searchService.searchProducts(query);

        resultsPanel.removeAll();

        if (results.isEmpty()) {
            resultsPanel.add(new JLabel("Không tìm thấy sản phẩm phù hợp."));
        } else {
            for (Product product : results) {
                JPanel itemPanel = new JPanel(new BorderLayout());
                JLabel nameLabel = new JLabel("<html><b>" + product.getTenSanPham() + "</b></html>");
                JLabel priceLabel = new JLabel("Giá: " + product.getGia());

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.add(nameLabel);
                textPanel.add(priceLabel);

                itemPanel.add(textPanel, BorderLayout.CENTER);

                if (product.getAnh() != null && !product.getAnh().isEmpty()) {
                    try {
                        ImageIcon icon = new ImageIcon(new java.net.URL(product.getAnh()));
                        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        itemPanel.add(new JLabel(new ImageIcon(scaledImage)), BorderLayout.WEST);
                    } catch (Exception ignored) {
                    }
                }

                JButton detailButton = new JButton("Chi tiết");
                detailButton.addActionListener(e -> showDetail(product));
                itemPanel.add(detailButton, BorderLayout.EAST);

                resultsPanel.add(itemPanel);
                resultsPanel.add(Box.createVerticalStrut(10));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void showDetail(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tên sản phẩm: ").append(product.getTenSanPham()).append("\n")
                .append("Giá: ").append(product.getGia()).append("\n")
                .append("Loại sản phẩm: ").append(product.getLoaiSanPham()).append("\n")
                .append("Điểm đánh giá trung bình: ").append(product.getDiemDanhGiaTrungBinh() != null ? product.getDiemDanhGiaTrungBinh() : "Đang cập nhật").append("\n")
                .append("Số lượt đánh giá: ").append(product.getSoLuotDanhGia() != null ? product.getSoLuotDanhGia() : "Đang cập nhật").append("\n")
                .append("Mô tả sản phẩm: ").append(product.getMoTaSanPham()).append("\n")
                .append("Nguồn dữ liệu: ").append(product.getNguonDuLieu());

        JOptionPane.showMessageDialog(this, sb.toString(), "Thông tin chi tiết", JOptionPane.INFORMATION_MESSAGE);
    }
}
