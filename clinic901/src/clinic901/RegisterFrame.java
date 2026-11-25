
package clinic901;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;

public class RegisterFrame extends JFrame {

    JTextField txtFullName, txtUsername, txtPhone, txtEmail, txtAge;
    JPasswordField txtPassword, txtConfirmPassword;
    JComboBox<String> cmbGender, cmbBloodGroup;
    JTextArea txtAddress;

    public RegisterFrame() {

        setTitle("ClinicCare - Registration");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("USER REGISTRATION", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // Full Name
        addLabel(panel, gbc, "Full Name:", 1);
        txtFullName = new JTextField(20);
        addField(panel, gbc, txtFullName, 1);

        // Username
        addLabel(panel, gbc, "Username:", 2);
        txtUsername = new JTextField(20);
        addField(panel, gbc, txtUsername, 2);

        // Password
        addLabel(panel, gbc, "Password:", 3);
        txtPassword = new JPasswordField(20);
        addField(panel, gbc, txtPassword, 3);

        // Confirm Password
        addLabel(panel, gbc, "Confirm Password:", 4);
        txtConfirmPassword = new JPasswordField(20);
        addField(panel, gbc, txtConfirmPassword, 4);

        // Phone
        addLabel(panel, gbc, "Phone:", 5);
        txtPhone = new JTextField(20);
        addField(panel, gbc, txtPhone, 5);

        // Email
        addLabel(panel, gbc, "Email:", 6);
        txtEmail = new JTextField(20);
        addField(panel, gbc, txtEmail, 6);

        // Age
        addLabel(panel, gbc, "Age:", 7);
        txtAge = new JTextField(20);
        addField(panel, gbc, txtAge, 7);

        // Gender
        addLabel(panel, gbc, "Gender:", 8);
        cmbGender = new JComboBox<>(
                new String[]{"Select Gender", "Male", "Female", "Other"});
        addField(panel, gbc, cmbGender, 8);

        // Blood Group
        addLabel(panel, gbc, "Blood Group:", 9);
        cmbBloodGroup = new JComboBox<>(
                new String[]{"Select Blood Group", "A+", "A-", "B+", "B-",
                        "AB+", "AB-", "O+", "O-"});
        addField(panel, gbc, cmbBloodGroup, 9);

        // Address
        addLabel(panel, gbc, "Address:", 10);
        txtAddress = new JTextArea(3, 20);
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(txtAddress);
        addField(panel, gbc, scrollPane, 10);

        // Buttons
        JButton btnRegister = new JButton("Register");
        JButton btnClear = new JButton("Clear");
        JButton btnBack = new JButton("Back to Login");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnBack);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Register Button
        btnRegister.addActionListener(e -> registerUser());

        // Clear Button
        btnClear.addActionListener(e -> clearForm());

        // Back Button
        btnBack.addActionListener(e -> {
            dispose();
            // Open your LoginFrame here when available
            // new LoginFrame().setVisible(true);
        });

        add(panel);
        setVisible(true);
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc,
                         String text, int row) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        panel.add(new JLabel(text), gbc);
    }

    private void addField(JPanel panel, GridBagConstraints gbc,
                         Component field, int row) {

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        panel.add(field, gbc);
    }

    private void registerUser() {

        String fullName = txtFullName.getText().trim();
        String username = txtUsername.getText().trim();
        char[] password = txtPassword.getPassword();
        char[] confirmPassword = txtConfirmPassword.getPassword();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String ageText = txtAge.getText().trim();
        String gender = (String) cmbGender.getSelectedItem();
        String bloodGroup = (String) cmbBloodGroup.getSelectedItem();
        String address = txtAddress.getText().trim();

        try {

            // Empty field validation
            if (fullName.isEmpty() || username.isEmpty() ||
                    password.length == 0 || confirmPassword.length == 0 ||
                    phone.isEmpty() || email.isEmpty() ||
                    ageText.isEmpty() || address.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please fill all fields!");
                return;
            }

            // Password validation
            if (!Arrays.equals(password, confirmPassword)) {

                JOptionPane.showMessageDialog(this,
                        "Passwords do not match!");
                return;
            }

            // Gender validation
            if (gender.equals("Select Gender")) {

                JOptionPane.showMessageDialog(this,
                        "Please select gender!");
                return;
            }

            // Blood Group validation
            if (bloodGroup.equals("Select Blood Group")) {

                JOptionPane.showMessageDialog(this,
                        "Please select blood group!");
                return;
            }

            // Phone validation
            if (!phone.matches("[0-9]{10}")) {

                JOptionPane.showMessageDialog(this,
                        "Enter a valid 10-digit phone number!");
                return;
            }

            // Email validation
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                JOptionPane.showMessageDialog(this,
                        "Enter a valid email!");
                return;
            }

            // Age validation
            int age;

            try {
                age = Integer.parseInt(ageText);

                if (age < 1 || age > 120) {
                    JOptionPane.showMessageDialog(this,
                            "Enter a valid age!");
                    return;
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Age must be a number!");
                return;
            }

            // Database Connection
            try (Connection con = DatabaseConnection.getConnection()) {

                String sql = "INSERT INTO users "
                        + "(full_name, username, password, phone, email, "
                        + "age, gender, blood_group, address) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement pst = con.prepareStatement(sql)) {

                    pst.setString(1, fullName);
                    pst.setString(2, username);
                    pst.setString(3, new String(password));
                    pst.setString(4, phone);
                    pst.setString(5, email);
                    pst.setInt(6, age);
                    pst.setString(7, gender);
                    pst.setString(8, bloodGroup);
                    pst.setString(9, address);

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(this,
                            "Registration Successful!");

                    clearForm();
                }

            } catch (SQLException ex) {

                if ("23505".equals(ex.getSQLState())) {

                    JOptionPane.showMessageDialog(this,
                            "Username or Email already exists!");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Database Error: " + ex.getMessage());
                }
            }

        } finally {
            Arrays.fill(password, '\0');
            Arrays.fill(confirmPassword, '\0');
        }
    }

    private void clearForm() {

        txtFullName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAge.setText("");
        cmbGender.setSelectedIndex(0);
        cmbBloodGroup.setSelectedIndex(0);
        txtAddress.setText("");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new RegisterFrame());
    }
}