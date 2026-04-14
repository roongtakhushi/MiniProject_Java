package java_mini_project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SnackDealerManagement extends JFrame {

    JTextField snackField, priceField, categoryField, stockField;
    JTable table;
    DefaultTableModel model;

    public SnackDealerManagement() {

        setTitle("Snack Dealer Management System");
        setSize(950, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 140, 0));
        JLabel title = new JLabel("🍿 Snack Dealer Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title);
        add(header, BorderLayout.NORTH);

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Snack Item"));
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 250, 240));
        inputPanel.setPreferredSize(new Dimension(280, 0));

        inputPanel.add(new JLabel("  Snack Name:"));
        snackField = new JTextField();
        inputPanel.add(snackField);

        inputPanel.add(new JLabel("  Price (₹):"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("  Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);

        inputPanel.add(new JLabel("  Stock Quantity:"));
        stockField = new JTextField();
        inputPanel.add(stockField);

        JButton addBtn = new JButton("➕ Add Snack");
        JButton deleteBtn = new JButton("🗑 Delete Snack");

        addBtn.setBackground(new Color(46, 204, 113));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        deleteBtn.setBackground(new Color(231, 76, 60));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        inputPanel.add(addBtn);
        inputPanel.add(deleteBtn);

        add(inputPanel, BorderLayout.WEST);

        // ===== TABLE =====
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Snack Name");
        model.addColumn("Price (₹)");
        model.addColumn("Category");
        model.addColumn("Stock Quantity");

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(255, 140, 0));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(255, 220, 150));

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ===== BOTTOM PANEL =====
        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(255, 250, 240));

        JButton refreshBtn = new JButton("🔄 Refresh Stock");
        JButton updateStockBtn = new JButton("📦 Update Stock");
        JLabel totalLabel = new JLabel("  Total Items: 0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        refreshBtn.setBackground(new Color(52, 152, 219));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        updateStockBtn.setBackground(new Color(155, 89, 182));
        updateStockBtn.setForeground(Color.WHITE);
        updateStockBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        bottom.add(refreshBtn);
        bottom.add(updateStockBtn);
        bottom.add(totalLabel);

        add(bottom, BorderLayout.SOUTH);

        // ===== BUTTON ACTIONS =====
        addBtn.addActionListener(e -> addSnack(totalLabel));
        deleteBtn.addActionListener(e -> deleteSnack(totalLabel));
        refreshBtn.addActionListener(e -> loadSnacks(totalLabel));
        updateStockBtn.addActionListener(e -> updateStock(totalLabel));

        loadSnacks(totalLabel);

        setVisible(true);
    }

    // ===== ADD SNACK =====
    public void addSnack(JLabel totalLabel) {
        if (snackField.getText().isEmpty() || priceField.getText().isEmpty()
                || categoryField.getText().isEmpty() || stockField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = connect.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "insert into snacks(snack_name, price, category, stock_quantity) values(?,?,?,?)");

            ps.setString(1, snackField.getText().trim());
            ps.setInt(2, Integer.parseInt(priceField.getText().trim()));
            ps.setString(3, categoryField.getText().trim());
            ps.setInt(4, Integer.parseInt(stockField.getText().trim()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Snack Added Successfully! 🎉");

            snackField.setText("");
            priceField.setText("");
            categoryField.setText("");
            stockField.setText("");

            loadSnacks(totalLabel);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price and Stock must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== DELETE SNACK =====
    public void deleteSnack(JLabel totalLabel) {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a snack to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String snackName = (String) model.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete: " + snackName + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) model.getValueAt(row, 0);

        try {
            Connection con = connect.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from snacks where id=?");
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Snack Deleted Successfully!");
            loadSnacks(totalLabel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== UPDATE STOCK =====
    public void updateStock(JLabel totalLabel) {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a snack to update stock!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String input = JOptionPane.showInputDialog(this, "Enter new stock quantity:");
        if (input == null || input.trim().isEmpty()) return;

        int id = (int) model.getValueAt(row, 0);

        try {
            int newStock = Integer.parseInt(input.trim());
            Connection con = connect.getConnection();
            PreparedStatement ps = con.prepareStatement("update snacks set stock_quantity=? where id=?");
            ps.setInt(1, newStock);
            ps.setInt(2, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Stock Updated Successfully! 📦");
            loadSnacks(totalLabel);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stock must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== LOAD SNACKS =====
    public void loadSnacks(JLabel totalLabel) {
        try {
            model.setRowCount(0);

            Connection con = connect.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from snacks");

            int count = 0;
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("snack_name"),
                        rs.getInt("price"),
                        rs.getString("category"),
                        rs.getInt("stock_quantity")
                });
                count++;
            }

            totalLabel.setText("  Total Items: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SnackDealerManagement();
    }
}
