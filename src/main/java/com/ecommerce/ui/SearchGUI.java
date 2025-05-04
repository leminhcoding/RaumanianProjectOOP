package com.ecommerce.ui;

import com.ecommerce.controller.SearchController;
import com.ecommerce.model.Product;
import com.ecommerce.service.SearchService;
import com.ecommerce.utils.FileUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class SearchGUI extends JFrame {
    private JTextField searchField;
    private JPanel resultsPanel;
    private JButton prevButton, nextButton;
    private JLabel pageLabel;

    private List<Product> allProducts;
    private List<Product> currentResults;
    private int currentPage = 1;
    private final int itemsPerPage = 10;

    public SearchGUI() {
        setTitle("Tìm kiếm sản phẩm");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load product list
        allProducts = FileUtil.loadProductsFromJson();
        SearchService searchService = new SearchService(allProducts);
        SearchController searchController = new SearchController(searchService);

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Tìm kiếm");

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            currentResults = searchController.search(query);
            currentPage = 1;
            displayResults();
        });

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Results panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Pagination controls
        JPanel paginationPanel = new JPanel();
        prevButton = new JButton("Trang trước");
        nextButton = new JButton("Trang sau");
        pageLabel = new JLabel("Trang 1");

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                displayResults();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentPage * itemsPerPage < currentResults.size()) {
                currentPage++;
                displayResults();
            }
        });

        paginationPanel.add(prevButton);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextButton);
        add(paginationPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void displayResults() {
        resultsPanel.removeAll();

        if (currentResults == null || currentResults.isEmpty()) {
            resultsPanel.add(new JLabel("Không tìm thấy sản phẩm nào."));
        } else {
            int start = (currentPage - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, currentResults.size());

            for (int i = start; i < end; i++) {
                Product product = currentResults.get(i);
                JPanel productPanel = new JPanel(new BorderLayout(10, 10));
                productPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
                productPanel.setBackground(Color.WHITE);

                // Load image
                try {
                    URL url = new URL(product.getAnh());
                    InputStream in = url.openStream();
                    Image img = ImageIO.read(in);
                    if (img != null) {
                        Image scaled = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(scaled));
                        productPanel.add(imageLabel, BorderLayout.WEST);
                    }
                } catch (Exception ignored) {}

                // Text info
                JPanel textPanel = new JPanel(new GridLayout(3, 1));
                textPanel.add(new JLabel("Tên: " + product.getTenSanPham()));
                textPanel.add(new JLabel("Giá: " + product.getGia()));
                JButton detailButton = new JButton("Chi tiết");
                detailButton.addActionListener((ActionEvent e) -> showDetailDialog(product));
                textPanel.add(detailButton);

                productPanel.add(textPanel, BorderLayout.CENTER);
                resultsPanel.add(productPanel);
            }

            pageLabel.setText("Trang " + currentPage + " / " + ((currentResults.size() - 1) / itemsPerPage + 1));
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void showDetailDialog(Product product) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText(
                "Tên sản phẩm: " + product.getTenSanPham() + "\n" +
                        "Giá: " + product.getGia() + "\n" +
                        "Loại sản phẩm: " + product.getLoaiSanPham() + "\n" +
                        "Điểm đánh giá trung bình: " + (product.getDiemDanhGiaTrungBinh() != null ? product.getDiemDanhGiaTrungBinh() : "Đang cập nhật") + "\n" +
                        "Số lượt đánh giá: " + (product.getSoLuotDanhGia() != null ? product.getSoLuotDanhGia() : "Đang cập nhật") + "\n" +
                        "Mô tả sản phẩm: " + product.getMoTaSanPham() + "\n" +
                        "Nguồn dữ liệu: " + product.getNguonDuLieu()
        );

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(this, scrollPane, "Chi tiết sản phẩm", JOptionPane.INFORMATION_MESSAGE);
    }
}
