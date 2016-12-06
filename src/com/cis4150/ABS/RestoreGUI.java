package com.cis4150.ABS;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/*
 * The  Restore GUI class for Advanced backing strategies, project for Software Engineering 2016
 * @author Nicole Hurley
 */
public class RestoreGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField outputFolder, customFiles;
    JComboBox<String> backupChoiceBox = new JComboBox<>();
    JComboBox<String> restoreChoiceBox = new JComboBox<>();
    JButton fullButton, restoreButton, customButton, chooseFolder, customFolderChooser;
    static JButton cancelButton;
    static JLabel header;
    static JLabel logo;

    Image icon, background;
    static final Color RED = new Color(255, 65, 65);
    static final Color BLUE = new Color(30, 50, 125);
    static final Color GREEN = new Color(39, 174, 96);



    // This will take care of the main GUI.
    public RestoreGUI() {
        setResizable(false);
        // Set the icon.
        icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ABSLogo.png"));
        setIconImage(icon);

        // Boring stuff
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 300, 500, 300);
        setLocationRelativeTo(null);
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;

            // Override the JPanel's paintComponent to set the background image
            @Override
            protected void paintComponent(Graphics g) {
                background = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ABSBackground.jpg"));
                super.paintComponent(g);
                int iw = background.getWidth(this);
                int ih = background.getHeight(this);
                if (iw > 0 && ih > 0) {
                    for (int x = 0; x < getWidth(); x += iw) {
                        for (int y = 0; y < getHeight(); y += ih) {
                            g.drawImage(background, x, y, iw, ih, this);
                        }
                    }
                }
            }
        };
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Header label
        header = new JLabel("Advanced Backing Strategies - Restore");
        header.setBackground(Color.LIGHT_GRAY);
        header.setBorder(new LineBorder(new Color(0, 0, 0)));
        header.setVerticalAlignment(SwingConstants.TOP);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setForeground(Color.DARK_GRAY);
        header.setBounds(90, 11, 324, 21);
        contentPane.add(header);


        // Picking custom files/directories to back up.
        JLabel chooseCustomFiles = new JLabel("Please select the files to restore");
        chooseCustomFiles.setBounds(34, 50, 324, 14);
        contentPane.add(chooseCustomFiles);


        // This text field will hold the chosen backup files
        customFiles = new JTextField();
        customFiles.setMargin(new Insets(5, 5, 5, 5));
        customFiles.setSelectionColor(Color.DARK_GRAY);
        customFiles.setFocusable(false);
        customFiles.setEditable(false);
        customFiles.setBorder(new LineBorder(Color.DARK_GRAY));
        customFiles.setBackground(Color.LIGHT_GRAY);
        customFiles.setFont(new Font("Arial", Font.PLAIN, 11));
        customFiles.setForeground(Color.DARK_GRAY);
        customFiles.setBounds(34, 75, 212, 23);
        contentPane.add(customFiles);
        customFiles.setColumns(10);

        // Choose your files to backup
        customFolderChooser = new JButton("Choose folder");
        customFolderChooser.setFocusable(false);
        customFolderChooser.setBackground(Color.LIGHT_GRAY);
        customFolderChooser.setBorder(new LineBorder(new Color(0, 0, 0)));
        customFolderChooser.setForeground(Color.DARK_GRAY);
        customFolderChooser.setFont(new Font("Arial", Font.PLAIN, 10));
        customFolderChooser.setBounds(258, 75, 100, 23);
        // When the user hits this button, create a file chooser.
        customFolderChooser.addActionListener(e -> {
            // Set the UI of the file chooser to the system default
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Let the user choose the backup destination folder.
            JFileChooser backupChooser = new JFileChooser();
            backupChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            backupChooser.setMultiSelectionEnabled(true);
            backupChooser.setApproveButtonText("Choose files/directories");
            int returnVal = backupChooser.showOpenDialog(getParent());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                customFiles.setText(backupChooser.getSelectedFile().getPath());
            }
            // Set the UI
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(customFolderChooser);

        // Destination label
        JLabel lblWhereDoYou = new JLabel("Where do you want to restore your data?");
        lblWhereDoYou.setBounds(34, 150, 325, 14);
        contentPane.add(lblWhereDoYou);

        // This text field will hold the chosen backup destination
        outputFolder = new JTextField();
        outputFolder.setMargin(new Insets(5, 5, 5, 5));
        outputFolder.setSelectionColor(Color.DARK_GRAY);
        outputFolder.setFocusable(false);
        outputFolder.setEditable(false);
        outputFolder.setBorder(new LineBorder(Color.DARK_GRAY));
        outputFolder.setBackground(Color.LIGHT_GRAY);
        outputFolder.setFont(new Font("Arial", Font.PLAIN, 11));
        outputFolder.setForeground(Color.DARK_GRAY);
        outputFolder.setBounds(34, 175, 212, 23);
        contentPane.add(outputFolder);
        outputFolder.setColumns(10);

        // Choose your folder
        chooseFolder = new JButton("Choose folder");
        chooseFolder.setFocusable(false);
        chooseFolder.setBackground(Color.LIGHT_GRAY);
        chooseFolder.setBorder(new LineBorder(new Color(0, 0, 0)));
        chooseFolder.setForeground(Color.DARK_GRAY);
        chooseFolder.setFont(new Font("Arial", Font.PLAIN, 10));
        chooseFolder.setBounds(258, 175, 100, 23);
        // create the file chooser
        chooseFolder.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // pick backup destination folder
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setApproveButtonText("Choose folder");
            int returnVal = chooser.showOpenDialog(getParent());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                outputFolder.setText(chooser.getSelectedFile().getPath());
            }

            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(chooseFolder);



        // The button that will start a Restore.
        restoreButton = new JButton("Restore");
        restoreButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        restoreButton.setFont(new Font("Arial", Font.PLAIN, 18));
        restoreButton.setBackground(Color.LIGHT_GRAY);
        restoreButton.setForeground(Color.DARK_GRAY);
        restoreButton.setBounds(300, 225, 100, 30);
        restoreButton.setFocusable(false);
        restoreButton.addActionListener(e -> {
            if (!outputFolder.getText().isEmpty()) {
                try {
                    restoreBackup();
                } catch (IllegalBlockSizeException e1) {
                    e1.printStackTrace();
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (BadPaddingException e1) {
                    e1.printStackTrace();
                } catch (NoSuchPaddingException e1) {
                    e1.printStackTrace();
                } catch (InvalidKeyException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(restoreButton);

        // Cancel button!
        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        cancelButton.setForeground(Color.DARK_GRAY);
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18));
        cancelButton.setBounds(175, 225, 100, 30);
        cancelButton.addActionListener(e -> {
            this.setVisible(false);
        });
        contentPane.add(cancelButton);

        logo = new JLabel("");
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("ABSLogo.png")));
        logo.setHorizontalAlignment(SwingConstants.RIGHT);
        logo.setVerticalAlignment(SwingConstants.TOP);
        logo.setBounds(362, 45, 125, 125);
        logo.setVisible(true);
        contentPane.add(logo);
    }


    // We want to disable the buttons and shit when a backup is in progress.
    public void fixButtons() {
        backupChoiceBox.setEnabled(false);
        cancelButton.setBackground(RED);
        chooseFolder.setEnabled(false);
        setBounds(300, 300, 500, 300);
        setLocationRelativeTo(null);
    }


    // This function starts a custom backup.
    public void restoreBackup() throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        String choice = customFiles.getText().toString();
        fixButtons();
        Restore restore = new Restore();
        File file1 = new File(choice);
        File file2 = new File(outputFolder.getText());
        restore.restore(file1, file2);
        header.setText("Restore was a success!");

     }



}