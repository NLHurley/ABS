package com.cis4150.ABS;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;


/*
 * The GUI class for Advanced backing strategies, project for Software Engineering 2016
 * @author Nicole Hurley
 */
public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField outputFolder, customFiles;
    static GUI frame;
    static RestoreGUI restoreFrame = new RestoreGUI();
    JComboBox<String> backupChoiceBox = new JComboBox<>();
    JButton fullButton, restoreButton, customButton, chooseFolder, customFolderChooser;
    static JButton cancelButton;
    static JLabel header;
    static JLabel logo;
    JRadioButton encryptOnButton = new JRadioButton("Select if you would like your data encrypted.");

    Image icon, background;
    static final Color RED = new Color(255, 65, 65);
    static final Color BLUE = new Color(30, 50, 125);
    static final Color GREEN = new Color(39, 174, 96);

    public static void main(String[] args) {

        // Set the UI to our liking.
        UIManager.put("Button.border", BorderFactory.createLineBorder(Color.BLACK));
        UIManager.put("Button.select", Color.DARK_GRAY);
        UIManager.put("ComboBox.selectionBackground", Color.DARK_GRAY);
        UIManager.put("ComboBox.selectionForeground", Color.LIGHT_GRAY);
        UIManager.put("ComboBox.disabledForeground", Color.DARK_GRAY);
        UIManager.put("ComboBox.disabledBackground", Color.LIGHT_GRAY);


        // Start up the GUI and make it visible.
        try {
            frame = new GUI();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This will take care of the main GUI.
    public GUI() {
        setResizable(false);
        // Set the icon.
        icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ABSLogo.png"));
        setIconImage(icon);

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
        header = new JLabel("Advanced Backing Strategies");
        header.setBackground(Color.LIGHT_GRAY);
        header.setBorder(new LineBorder(new Color(0, 0, 0)));
        header.setVerticalAlignment(SwingConstants.TOP);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setForeground(Color.DARK_GRAY);
        header.setBounds(90, 11, 324, 21);
        contentPane.add(header);

        // drive label
        JLabel lblChooseADrive = new JLabel("Choose a drive to backup:");
        lblChooseADrive.setBounds(34, 50, 256, 21);
        contentPane.add(lblChooseADrive);


        // pick a drive
        File[] backupChoiceDrive = File.listRoots();
        String[] backupChoices = new String[backupChoiceDrive.length];
        for (int i = 0; i < backupChoiceDrive.length; i++) {
            String volumeLabel = FileSystemView.getFileSystemView().getSystemDisplayName(backupChoiceDrive[i]);
            backupChoices[i] = volumeLabel;
        }

        backupChoices = removeBlanks(backupChoices, "");

        // Our hard drive choices.
        backupChoiceBox.setBorder(new LineBorder(Color.BLACK));
        backupChoiceBox.setFocusable(false);
        backupChoiceBox.setEditable(false);
        backupChoiceBox.setFont(new Font("Arial", Font.PLAIN, 11));
        backupChoiceBox.setBackground(Color.LIGHT_GRAY);
        // Add the choices to the box
        for (String str : backupChoices) {
            backupChoiceBox.addItem(str);
        }
        backupChoiceBox.setBounds(34, 68, 324, 21);
        contentPane.add(backupChoiceBox);


        // Picking custom files/directories to back up.
        JLabel chooseCustomFiles = new JLabel("OR Choose custom files:");
        chooseCustomFiles.setBounds(34, 100, 324, 14);
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
        customFiles.setBounds(34, 125, 212, 23);
        contentPane.add(customFiles);
        customFiles.setColumns(10);

        // Choose your files to backup
        customFolderChooser = new JButton("Choose folder");
        customFolderChooser.setFocusable(false);
        customFolderChooser.setBackground(Color.LIGHT_GRAY);
        customFolderChooser.setBorder(new LineBorder(new Color(0, 0, 0)));
        customFolderChooser.setForeground(Color.DARK_GRAY);
        customFolderChooser.setFont(new Font("Arial", Font.PLAIN, 10));
        customFolderChooser.setBounds(258, 125, 100, 23);
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
        JLabel lblWhereDoYou = new JLabel("Where do you want to backup your data?");
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


        // Add the choices to the box
        for (String str : backupChoices) {
            backupChoiceBox.addItem(str);
        }
        backupChoiceBox.setBounds(34, 68, 324, 21);
        contentPane.add(backupChoiceBox);

        // The button that will start a custom backup.
        customButton = new JButton("Custom");
        customButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        customButton.setFont(new Font("Arial", Font.PLAIN, 18));
        customButton.setForeground(Color.DARK_GRAY);
        customButton.setBackground(Color.LIGHT_GRAY);
        customButton.setBounds(34, 225, 100, 30);
        customButton.setFocusable(false);
        customButton.addActionListener(e -> {
            if (!outputFolder.getText().isEmpty()) {
                try {
                    startCustomBackup();
                } catch (IllegalBlockSizeException e1) {
                    e1.printStackTrace();
                } catch (NoSuchPaddingException e1) {
                    e1.printStackTrace();
                } catch (BadPaddingException e1) {
                    e1.printStackTrace();
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
                } catch (InvalidKeyException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(customButton);

        // Radio button for encrypting the files when backing up.
        encryptOnButton.setSelected(false);
        encryptOnButton.setBounds(30, 200, 500, 23);

        contentPane.add(encryptOnButton);


        // The button that will start a full backup.
        fullButton = new JButton("Full");
        fullButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        fullButton.setFont(new Font("Arial", Font.PLAIN, 18));
        fullButton.setBackground(Color.LIGHT_GRAY);
        fullButton.setForeground(Color.DARK_GRAY);
        fullButton.setBounds(146, 225, 100, 30);
        fullButton.setFocusable(false);
        fullButton.addActionListener(e -> {
            if (!outputFolder.getText().isEmpty()) {
                startFullBackup();
            }
        });
        contentPane.add(fullButton);

        // The button that will start a Restore.
        restoreButton = new JButton("Restore");
        restoreButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        restoreButton.setFont(new Font("Arial", Font.PLAIN, 18));
        restoreButton.setBackground(Color.LIGHT_GRAY);
        restoreButton.setForeground(Color.DARK_GRAY);
        restoreButton.setBounds(258, 225, 100, 30);
        restoreButton.setFocusable(false);
        restoreButton.addActionListener(e -> {
            restoreFrame.setVisible(true);
        });
        contentPane.add(restoreButton);

        // Cancel button!
        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.setBorder(new LineBorder(new Color(0, 0, 0)));
        cancelButton.setForeground(Color.DARK_GRAY);
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 18));
        cancelButton.setBounds(362, 225, 100, 30);
        cancelButton.addActionListener(e -> {
            System.exit(0);
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
        fullButton.setEnabled(false);
        customButton.setEnabled(false);
        cancelButton.setBackground(RED);
        chooseFolder.setEnabled(false);
        setBounds(300, 300, 500, 300);
        setLocationRelativeTo(null);
    }

    public static String[] removeBlanks(String[] input, String deleteMe) {
        List<String> result = new LinkedList<>();

        for (String item : input)
            if (!deleteMe.equals(item)) result.add(item);

        String[] returnThis = new String[result.size()];

        for (int i = 0; i < result.size(); i++) {
            returnThis[i] = result.get(i);
        }
        return returnThis;
    }

    // This function starts a custom backup.
    public void startCustomBackup() throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String choice = customFiles.getText().toString();
        fixButtons();
        Backup backup = new Backup();
        File file1 = new File(choice);
        File file2 = new File(outputFolder.getText());
        File encryptedFile = new File(choice + ".enc");
        try {
            if (encryptOnButton.isSelected()) {
                Encrypt.encryptFile("1234567890123456", file1, encryptedFile);

                backup.copyFileForBackUp(encryptedFile, file2);
                header.setText("Success!");
            } else {
                backup.copyFileForBackUp(encryptedFile, file2);
                header.setText("Success!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This function starts a full backup.
    public void startFullBackup() {
        String choice = backupChoiceBox.getSelectedItem().toString();
        fixButtons();
        header.setText("Performing full backup...");
//      run full backup

    }


}