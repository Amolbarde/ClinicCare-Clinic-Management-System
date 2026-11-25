package clinic901;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ClinicManagementSystem extends JFrame {

    private JTextField usernameField, nameField, specialtyField, contactField, emailField, roomField;
    private JTextField patientNameField, patientContactField, patientEmailField, patientAddressField;
    private JTextField appointmentDateField, appointmentTimeField;
    private JComboBox<Doctor> doctorComboBox, availabilityDoctorComboBox;
    private JComboBox<Patient> patientComboBox;
    private JButton loginButton, addDoctorButton, showDoctorsButton, addPatientButton, showPatientsButton, bookAppointmentButton, setAvailabilityButton, showAllDataButton;
    private JPasswordField passwordField;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public ClinicManagementSystem() {
        setTitle("Clinic Management System");
        setLayout(new BorderLayout());

        // Main Panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel, BorderLayout.CENTER);

        // Create and add panels
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createClinicInfoPanel(), "Clinic Info");  // ADDED
        mainPanel.add(createDoctorPanel(), "Doctor Management");
        mainPanel.add(createPatientPanel(), "Patient Management");
        mainPanel.add(createAppointmentPanel1(), "Appointment Management");
        mainPanel.add(createBillPanel(), "Bill Generation");
        //  mainPanel.add(createAllDataPanel(), "All Data");

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private JPanel createClinicInfoPanel() {
        JPanel clinicInfoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("hospi/green.jpg"); // Background Image
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        clinicInfoPanel.setLayout(new BorderLayout());
        clinicInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); // Colored Border

        // Title Panel
        JLabel titleLabel = new JLabel("Welcome to Zara Clinic!");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clinicInfoPanel.add(titleLabel, BorderLayout.NORTH);

        // Content Panel (Text & Image)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        // Clinic Info Text
        JTextArea infoTextArea = new JTextArea(
                "\u2022 Expert Doctors\n"
                + "\u2022 Modern Facilities\n"
                + "\u2022 Personalized Care\n"
                + "\u2022 24/7 Emergency Services\n"
                + "\u2022 Affordable Healthcare\n\n"
                + "Visit us today for a healthier tomorrow!");
        infoTextArea.setFont(new Font("Arial", Font.BOLD, 18));
        infoTextArea.setForeground(Color.red);
        infoTextArea.setOpaque(false);
        infoTextArea.setEditable(false);

        // Text Panel
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setOpaque(false);
        textPanel.add(infoTextArea);
        contentPanel.add(textPanel, BorderLayout.CENTER);

        // Right-side Image
        ImageIcon clinicImage = new ImageIcon("hospi/doctors.png");
        JLabel imageLabel = new JLabel(new ImageIcon(clinicImage.getImage().getScaledInstance(250, 180, Image.SCALE_SMOOTH)));
        JPanel imagePanel = new JPanel();
        imagePanel.setOpaque(false);
        imagePanel.add(imageLabel);
        contentPanel.add(imagePanel, BorderLayout.EAST);

        clinicInfoPanel.add(contentPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        JButton proceedButton = new JButton("Proceed to Management System");
        proceedButton.setFont(new Font("Arial", Font.BOLD, 16));
        proceedButton.setBackground(new Color(0, 102, 204));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setPreferredSize(new Dimension(280, 40));
        proceedButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        proceedButton.addActionListener(e -> cardLayout.show(mainPanel, "Doctor Management"));
        buttonPanel.add(proceedButton);

        clinicInfoPanel.add(buttonPanel, BorderLayout.SOUTH);

        return clinicInfoPanel;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("hospi/back90.jpg"); // Background image path
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Clinic Management System - JAVA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.yellow);
        loginPanel.add(titleLabel, gbc);

        // Logo
        gbc.gridy = 1;
        ImageIcon logo = new ImageIcon("hospi/loginLOGO.png"); // Logo image path
        Image logoImage = logo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        loginPanel.add(logoLabel, gbc);

        // Subtitle
        gbc.gridy = 2;
        JLabel subtitleLabel = new JLabel("Please enter your login credentials");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);
        loginPanel.add(subtitleLabel, gbc);

        // Input Panel with Border
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 2), "Login", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        GridBagConstraints inputGbc = new GridBagConstraints();
        inputGbc.insets = new Insets(5, 5, 5, 5);
        inputGbc.anchor = GridBagConstraints.CENTER;

        // Username Label and Field
        inputGbc.gridx = 0;
        inputGbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.YELLOW);
        inputPanel.add(usernameLabel, inputGbc);

        inputGbc.gridx = 1;
        usernameField = new JTextField(15);
        inputPanel.add(usernameField, inputGbc);

        // Password Label and Field
        inputGbc.gridx = 0;
        inputGbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.YELLOW);
        inputPanel.add(passwordLabel, inputGbc);

        inputGbc.gridx = 1;
        passwordField = new JPasswordField(15);
        inputPanel.add(passwordField, inputGbc);

        // Forgot Password Link
        inputGbc.gridx = 1;
        inputGbc.gridy = 2;
        JButton forgotPasswordButton = new JButton("<HTML><U color='red' >Forgot Password?</U></HTML>");
        forgotPasswordButton.setFont(new Font("Arial", Font.ITALIC, 12));
        forgotPasswordButton.setForeground(Color.pink);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordButton.addActionListener(e -> showForgotPasswordDialog());
        inputPanel.add(forgotPasswordButton, inputGbc);

        gbc.gridy = 3;
        loginPanel.add(inputPanel, gbc);

        // Login Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        loginButton = new JButton("Sign In");
        loginButton.setBackground(Color.BLUE);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> loginUser());
        loginPanel.add(loginButton, gbc);

        return loginPanel;
    }

    // Function to handle login
    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinic", "postgres", "Amol@123")) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                cardLayout.show(mainPanel, "Clinic Info"); // Show clinic info panel
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Function to handle forgot password
    private void showForgotPasswordDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        Object[] message = {
                "Enter your Username:", usernameField,
                "Enter New Password:", newPasswordField,
                "Confirm New Password:", confirmPasswordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Reset Password", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update password in database
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinic", "postgres", "Amol@123")) {
                String checkUserSQL = "SELECT * FROM users WHERE username=?";
                PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSQL);
                checkUserStmt.setString(1, username);
                ResultSet userResult = checkUserStmt.executeQuery();

                if (!userResult.next()) {
                    JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String updateSQL = "UPDATE users SET password=? WHERE username=?";
                PreparedStatement stmt = conn.prepareStatement(updateSQL);
                stmt.setString(1, newPassword);
                stmt.setString(2, username);
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Custom JPanel to set background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon("hospi/green.jpg").getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JPanel createDoctorPanel() {
        JPanel doctorPanel = new BackgroundPanel("hospi/green.jpg"); // Set background image
        doctorPanel.setLayout(new BorderLayout());

        JPanel bigPart = new JPanel();
        bigPart.setLayout(new BorderLayout());
        bigPart.setOpaque(false); // Ensure transparency

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        titlePanel.setBackground(new Color(0, 102, 204, 180)); // Semi-transparent
        JLabel titleLabel = new JLabel("Doctor Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        bigPart.add(titlePanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(220, 220, 220, 200)); // Semi-transparent background
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = createStyledTextField(12);
        inputPanel.add(nameField, gbc);

        // Specialty
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel specialtyLabel = new JLabel("Specialty:");
        specialtyLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(specialtyLabel, gbc);

        gbc.gridx = 1;
        specialtyField = createStyledTextField(12);
        inputPanel.add(specialtyField, gbc);

        // Contact
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(contactLabel, gbc);

        gbc.gridx = 1;
        contactField = createStyledTextField(12);
        inputPanel.add(contactField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = createStyledTextField(12);
        inputPanel.add(emailField, gbc);

        // Room
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel roomLabel = new JLabel("Room:");
        roomLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(roomLabel, gbc);

        gbc.gridx = 1;
        roomField = createStyledTextField(12);
        inputPanel.add(roomField, gbc);

        bigPart.add(inputPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel smallPart = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        smallPart.setOpaque(false);

        addDoctorButton = createStyledButton("Add", null, 80, 30);
        showDoctorsButton = createStyledButton("Show", null, 80, 30);
        JButton clearButton = createStyledButton("Clear", null, 80, 30);
        JButton nextButton = createStyledButton("Next", null, 80, 30);
        JButton backButton = createStyledButton("Back", null, 80, 30);

        addDoctorButton.addActionListener(e -> {
            if (validateInput()) {
                try {
                    Doctor doctor = new Doctor();
                    doctor.setName(nameField.getText());
                    doctor.setSpecialty(specialtyField.getText());
                    doctor.setContactNumber(contactField.getText());
                    doctor.setEmail(emailField.getText());
                    doctor.setRoomNumber(roomField.getText());

                    DoctorDAO dao = new DoctorDAO();
                    dao.addDoctor(doctor);
                    JOptionPane.showMessageDialog(null, "Doctor added successfully!");
                    clearFields();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
            populateComboBoxes();
        });

        showDoctorsButton.addActionListener(e -> displayDoctors());
        clearButton.addActionListener(e -> clearFields());
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "Patient Management"));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Clinic Info")); // back to Clinic info

        smallPart.add(addDoctorButton);
        smallPart.add(showDoctorsButton);
        smallPart.add(clearButton);
        smallPart.add(nextButton);
        smallPart.add(backButton);

        doctorPanel.add(bigPart, BorderLayout.CENTER);
        doctorPanel.add(smallPart, BorderLayout.NORTH);

        // Image Panel at Bottom
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        imagePanel.setOpaque(false);


        ImageIcon doctorImage = new ImageIcon(new ImageIcon("hospi/clogo.png").getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        imagePanel.add(new JLabel(doctorImage));

        ImageIcon image1 = new ImageIcon(new ImageIcon("hospi/logo1.png").getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        ImageIcon image2 = new ImageIcon(new ImageIcon("hospi/logo2.1.png").getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        ImageIcon image3 = new ImageIcon(new ImageIcon("hospi/logo2.3.png").getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));

        imagePanel.add(new JLabel(image1));
        imagePanel.add(new JLabel(image2));
        imagePanel.add(new JLabel(image3));

        doctorPanel.add(imagePanel, BorderLayout.SOUTH);

        return doctorPanel;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(173, 216, 230), 2),
                BorderFactory.createEmptyBorder(3, 3, 3, 3))); // Smaller padding
        textField.setBackground(new Color(240, 248, 255));
        textField.setForeground(Color.DARK_GRAY);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font
        return textField;
    }

    private JButton createStyledButton(String text, String iconPath, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (iconPath != null) {
            button.setIcon(new ImageIcon(iconPath));
        }

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 128, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 102, 204));
            }
        });

        return button;
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty() ||
                specialtyField.getText().trim().isEmpty() ||
                contactField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                roomField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out.");
            return false;
        }
        // Additional validation can be added here (e.g., email format, contact number format)
        return true;
    }

    private void clearFields() {
        nameField.setText("");
        specialtyField.setText("");
        contactField.setText("");
        emailField.setText("");
        roomField.setText("");
    }

    private JPanel createPatientPanel() {
        JPanel patientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("hospi/back1.jpg"); // Add your image path here
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        patientPanel.setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(0, 102, 204, 200)); // Transparent effect
        JLabel titleLabel = new JLabel("Patient Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        patientPanel.add(titlePanel, BorderLayout.NORTH);

        // Doctor's Photo Section
        JPanel doctorPanel = new JPanel();
        doctorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        doctorPanel.setOpaque(false);
        JLabel doctorImageLabel = new JLabel();
        ImageIcon doctorImage = new ImageIcon("hospi/logo2.3.png"); // Add path to doctor's photo here
        doctorImageLabel.setIcon(new ImageIcon(doctorImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        patientPanel.add(doctorPanel, BorderLayout.WEST); // Place doctor photo on the left side

        // Input Fields Panel with better spacing
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setOpaque(false); // Make it transparent
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Increased spacing for better visuals
        gbc.anchor = GridBagConstraints.WEST;

        // Patient Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Patient Name:");
        nameLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        patientNameField = createStyledTextField(20);
        inputPanel.add(patientNameField, gbc);

        // Contact Number
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(contactLabel, gbc);
        gbc.gridx = 1;
        patientContactField = createStyledTextField(20);
        inputPanel.add(patientContactField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        patientEmailField = createStyledTextField(20);
        inputPanel.add(patientEmailField, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(new Color(0, 128, 0));
        inputPanel.add(addressLabel, gbc);
        gbc.gridx = 1;
        patientAddressField = createStyledTextField(20);
        inputPanel.add(patientAddressField, gbc);

        patientPanel.add(inputPanel, BorderLayout.CENTER);

        // Buttons Panel with improved styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        addPatientButton = createStyledButton("Add Patient", null, 150, 40);
        showPatientsButton = createStyledButton("Show All Patients", null, 150, 40);
        JButton nextButton = createStyledButton("Next", null, 150, 40);
        JButton backButton = createStyledButton("Back", null, 150, 40);

        buttonPanel.add(addPatientButton);
        buttonPanel.add(showPatientsButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(backButton);

        // Button Actions
        addPatientButton.addActionListener(e -> {
            try {
                Patient patient = new Patient();
                patient.setName(patientNameField.getText());
                patient.setContactNumber(patientContactField.getText());
                patient.setEmail(patientEmailField.getText());
                patient.setAddress(patientAddressField.getText());

                PatientDAO dao = new PatientDAO();
                dao.addPatient(patient);
                JOptionPane.showMessageDialog(null, "Patient added successfully!");
                clearFields1();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
            populateComboBoxes();
        });

        showPatientsButton.addActionListener(e -> displayPatients());
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "Appointment Management"));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Doctor Management")); // back to doctor managment

        patientPanel.add(buttonPanel, BorderLayout.SOUTH);

        return patientPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private void clearFields1() {
        patientNameField.setText("");
        patientContactField.setText("");
        patientEmailField.setText("");
        patientAddressField.setText("");
    }

    private JPanel createAppointmentPanel1() {
        JPanel appointmentPanel = new JPanel(new BorderLayout());
        appointmentPanel.setBackground(new Color(220, 235, 250));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(0, 72, 204));
        JLabel titleLabel = new JLabel("Appointment Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        appointmentPanel.add(titlePanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 72, 204), 2), "Appointment Details",
                        TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16), new Color(0, 72, 204)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        inputPanel.add(createLabel("Select Doctor:"));
        doctorComboBox = new JComboBox<>();
        inputPanel.add(doctorComboBox);

        inputPanel.add(createLabel("Select Patient:"));
        patientComboBox = new JComboBox<>();
        inputPanel.add(patientComboBox);

        populateComboBoxes();

        inputPanel.add(createLabel("Appointment Date:"));
        appointmentDateField = createStyledTextField(20);
        inputPanel.add(appointmentDateField);

        inputPanel.add(createLabel("Appointment Time:"));
        appointmentTimeField = createStyledTextField(20);
        inputPanel.add(appointmentTimeField);

        appointmentPanel.add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(230, 230, 230));

        bookAppointmentButton = createStyledButton("Book Appointment", "", 220, 50);
        bookAppointmentButton.setBackground(new Color(0, 153, 76));
        bookAppointmentButton.setForeground(Color.WHITE);
        bookAppointmentButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bookAppointmentButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        bookAppointmentButton.setToolTipText("Click to book the appointment");
        bookAppointmentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(bookAppointmentButton);

        JButton nextButton = createStyledButton("Next", null, 150, 40);
        buttonPanel.add(nextButton);

        JButton backButton = createStyledButton("Back", "", 140, 50);
        backButton.setBackground(new Color(204, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.setToolTipText("Click to go back");
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(backButton);

        appointmentPanel.add(buttonPanel, BorderLayout.SOUTH);


        // Button Actions
        bookAppointmentButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (doctorComboBox.getSelectedItem() == null || patientComboBox.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, "Please select a doctor and a patient.");
                        return;
                    }

                    Appointment appointment = new Appointment();
                    appointment.setDoctorId(((Doctor) doctorComboBox.getSelectedItem()).getDoctorId());
                    appointment.setPatientId(((Patient) patientComboBox.getSelectedItem()).getPatientId());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    java.util.Date appointmentDate = dateFormat.parse(appointmentDateField.getText());
                    SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    appointment.setAppointmentDate(dbDateFormat.format(appointmentDate));
                    appointment.setAppointmentTime(appointmentTimeField.getText());

                    AppointmentDAO dao = new AppointmentDAO();
                    dao.bookAppointment(appointment);
                    JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
                    clearFieldsAppointment();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Invalid date format. Please use yyyy/MM/dd.");
                    e1.printStackTrace();
                }
            }
        });


        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "Bill Generation"));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Patient Management"));


        return appointmentPanel;
    }

    private void clearFieldsAppointment() {
        appointmentDateField.setText("");
        appointmentTimeField.setText("");
    }

    private void displayDoctors() {
        try {
            DoctorDAO dao = new DoctorDAO();
            List<Doctor> doctors = dao.getAllDoctors();
            StringBuilder doctorList = new StringBuilder();

            if (doctors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No doctors found.");
                return;
            }

            for (Doctor doctor : doctors) {
                doctorList.append("ID: ").append(doctor.getDoctorId()).append(" | ")
                        .append("Name: ").append(doctor.getName()).append(" | ")
                        .append("Specialty: ").append(doctor.getSpecialty()).append(" | ")
                        .append("Contact: ").append(doctor.getContactNumber()).append(" | ")
                        .append("Email: ").append(doctor.getEmail()).append(" | ")
                        .append("Room: ").append(doctor.getRoomNumber()).append("\n");
            }
            JOptionPane.showMessageDialog(null, doctorList.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void populateComboBoxes() {
        try {
            doctorComboBox.removeAllItems();
            patientComboBox.removeAllItems();

            DoctorDAO doctorDAO = new DoctorDAO();
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            for (Doctor doctor : doctors) {
                doctorComboBox.addItem(doctor);
            }

            PatientDAO patientDAO = new PatientDAO();
            List<Patient> patients = patientDAO.getAllPatients();
            for (Patient patient : patients) {
                patientComboBox.addItem(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayPatients() {
        try {
            PatientDAO dao = new PatientDAO();
            List<Patient> patients = dao.getAllPatients();
            StringBuilder patientList = new StringBuilder();
            for (Patient patient : patients) {
                patientList.append("patient_id : ").append(patient.getPatientId()).append(" | ")
                        .append(patient.getName()).append(" | ")
                        .append(patient.getContactNumber()).append("\n");
            }
            JOptionPane.showMessageDialog(null, patientList.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }

    private JPanel createBillPanel() {
        JPanel billPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("hospi/back1.jpg"); // Use specified image path
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        billPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel clinicLabel = new JLabel("SAI CLINIC");
        clinicLabel.setFont(new Font("Arial", Font.BOLD, 36));
        clinicLabel.setForeground(Color.orange);
        clinicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(clinicLabel, BorderLayout.NORTH);

        ImageIcon hospitalIcon = new ImageIcon("hospi/logo2.1.png");
        Image image = hospitalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledHospitalIcon = new ImageIcon(image);
        JLabel hospitalImageLabel = new JLabel(scaledHospitalIcon);
        hospitalImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(hospitalImageLabel, BorderLayout.CENTER);
        billPanel.add(topPanel, BorderLayout.NORTH);

        JTextArea billTextArea = new JTextArea();
        billTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(billTextArea);
        billPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        JButton generateBillButton = createStyledButton("Generate Bill", null, 150, 40);
        JButton printButton = createStyledButton("Print Bill", null, 150, 40);  // Added print button
        JButton backButton = createStyledButton("Back", null, 150, 40);
        JButton logoutButton = createStyledButton("Logout", null, 150, 40); // Add logout button

        buttonPanel.add(generateBillButton);
        buttonPanel.add(printButton);  // Added print button
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);   // Add logout button

        billPanel.add(buttonPanel, BorderLayout.SOUTH);

        generateBillButton.addActionListener(e -> {
            try {
                AppointmentDAO appointmentDAO = new AppointmentDAO();
                List<Appointment> appointments = appointmentDAO.getAllAppointments();

                StringBuilder bill = new StringBuilder();
                bill.append("Bill Generation\n");
                bill.append("-----------------\n");

                for (Appointment appointment : appointments) {
                    // Retrieve doctor and patient information for *each* appointment
                    DoctorDAO doctorDAO = new DoctorDAO();
                    Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());

                    PatientDAO patientDAO = new PatientDAO();
                    Patient patient = patientDAO.getPatientById(appointment.getPatientId());

                    // Ensure doctor and patient are not null before accessing their names
                    if (doctor != null && patient != null) {
                        bill.append("Appointment ID: ").append(appointment.getAppointmentId()).append("\n");
                        bill.append("Doctor Name: ").append(doctor.getName()).append("\n");
                        bill.append("Patient Name: ").append(patient.getName()).append("\n");
                        bill.append("Appointment Date: ").append(appointment.getAppointmentDate()).append("\n");
                        bill.append("Appointment Time: ").append(appointment.getAppointmentTime()).append("\n");
                        bill.append("Fee: 100.00 Rs\n");
                        bill.append("-----------------\n");
                    } else {
                        bill.append("Error: Could not retrieve doctor or patient information for Appointment ID: ")
                                .append(appointment.getAppointmentId()).append("\n");
                        bill.append("-----------------\n");
                    }
                }
                billTextArea.setText(bill.toString());

            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        printButton.addActionListener(e -> {
            try {
                String billText = billTextArea.getText();
                if (!billText.isEmpty()) {
                    printBill(billText);  // Call printBill method
                } else {
                    JOptionPane.showMessageDialog(null, "No bill to print.");
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Printing error: " + ex.getMessage());
                ex.printStackTrace(); // Log the exception for debugging
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Appointment Management"));
        //Implement logout functionality
        logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login")); // Navigate to Login panel

        return billPanel;
    }

    private void printBill(String billText) throws PrinterException {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) { // We have only one page
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // Use a monospaced font for the bill
            Font billFont = new Font("Monospaced", Font.PLAIN, 12);
            g2d.setFont(billFont);

            // Split the bill text into lines
            String[] lines = billText.split("\n");
            int y = 0; // Y position for the current line

            // Calculate the starting Y position
            FontMetrics fontMetrics = g2d.getFontMetrics(billFont); // Use billFont here
            int lineHeight = fontMetrics.getHeight(); // Height of a line
            y = fontMetrics.getAscent(); // Start from the top of the font

            // Print each line of the bill
            for (String line : lines) {
                g2d.drawString(line, 0, y);
                y += lineHeight;
            }

            return Printable.PAGE_EXISTS;
        });

        boolean doPrint = printerJob.printDialog();
        if (doPrint) {
            printerJob.print();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClinicManagementSystem::new);
    }
}