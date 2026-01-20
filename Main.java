import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ContactManager {

    static DefaultListModel<String> listModel = new DefaultListModel<>();
    static JList<String> contactList = new JList<>(listModel);
    static File file = new File("contacts.txt");

    public static void main(String[] args) {

        JFrame frame = new JFrame("Contact Management System");
        frame.setSize(450, 350);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Contact Manager");
        title.setBounds(150, 10, 200, 30);
        frame.add(title);

        JScrollPane scrollPane = new JScrollPane(contactList);
        scrollPane.setBounds(20, 50, 250, 200);
        frame.add(scrollPane);

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        addBtn.setBounds(300, 70, 100, 30);
        editBtn.setBounds(300, 120, 100, 30);
        deleteBtn.setBounds(300, 170, 100, 30);

        frame.add(addBtn);
        frame.add(editBtn);
        frame.add(deleteBtn);

        loadContacts();

        // Add Contact
        addBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Name:");
            String phone = JOptionPane.showInputDialog("Enter Phone:");
            String email = JOptionPane.showInputDialog("Enter Email:");

            if (name != null && phone != null && email != null) {
                String contact = name + " | " + phone + " | " + email;
                listModel.addElement(contact);
                saveContacts();
            }
        });

        // Edit Contact
        editBtn.addActionListener(e -> {
            int index = contactList.getSelectedIndex();
            if (index != -1) {
                String selected = listModel.get(index);
                String[] parts = selected.split(" \\| ");

                String name = JOptionPane.showInputDialog("Edit Name:", parts[0]);
                String phone = JOptionPane.showInputDialog("Edit Phone:", parts[1]);
                String email = JOptionPane.showInputDialog("Edit Email:", parts[2]);

                if (name != null && phone != null && email != null) {
                    listModel.set(index, name + " | " + phone + " | " + email);
                    saveContacts();
                }
            }
        });

        // Delete Contact
        deleteBtn.addActionListener(e -> {
            int index = contactList.getSelectedIndex();
            if (index != -1) {
                listModel.remove(index);
                saveContacts();
            }
        });

        frame.setVisible(true);
    }

    // Load contacts from file
    static void loadContacts() {
        try {
            if (!file.exists()) return;
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                listModel.addElement(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save contacts to file
    static void saveContacts() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < listModel.size(); i++) {
                bw.write(listModel.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
