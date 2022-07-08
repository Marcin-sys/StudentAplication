import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class reg extends JFrame {
    private JTextField txtName;
    private JTextField txtMobile;
    private JTextField txtCourse;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTable table1;
    private JPanel rootPanel;

    public reg() {
        createTable();
        tableUpdate();
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String mobile = txtMobile.getText();
                String course = txtCourse.getText();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3307/linda"
                            ,"Mircin","Mircin27");

                    String sql = "insert into record(name,mobile,course)values(?,?,?)";
                    insert = con1.prepareStatement(sql);
                    insert.setString(1, name);
                    insert.setString(2, mobile);
                    insert.setString(3, course);
                    insert.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Record Added");
                    tableUpdate();

                    txtName.setText("");
                    txtMobile.setText("");
                    txtCourse.setText("");
                    txtName.requestFocus();

                }catch (ClassNotFoundException | SQLException ex){
                System.out.println(ex);
                }
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel defaultTableModel = (DefaultTableModel)table1.getModel();
                int selectedIndex = table1.getSelectedRow();

                txtName.setText(defaultTableModel.getValueAt(selectedIndex,1).toString());
                txtMobile.setText(defaultTableModel.getValueAt(selectedIndex,2).toString());
                txtCourse.setText(defaultTableModel.getValueAt(selectedIndex,3).toString());

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel defaultTableModel = (DefaultTableModel)table1.getModel();
                int selectedIndex = table1.getSelectedRow();

                try {
                    int id = Integer.parseInt(defaultTableModel.getValueAt(selectedIndex,0).toString());
                    String name = txtName.getText();
                    String mobile = txtMobile.getText();
                    String course = txtCourse.getText();

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con1 = DriverManager.getConnection("jdbc:mysql://localhost:3307/linda"
                            ,"Mircin","Mircin27");

                    String sql = ("update record set name= ?,mobile=?,course=? where id=?");
                    insert = con1.prepareStatement(sql);
                    insert.setString(1, name);
                    insert.setString(2, mobile);
                    insert.setString(3, course);
                    insert.setInt(4, id);

                    insert.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Record Updated");
                    tableUpdate();

                    txtName.setText("");
                    txtMobile.setText("");
                    txtCourse.setText("");
                    txtName.requestFocus();

                }catch (ClassNotFoundException | SQLException ex){
                    System.out.println(ex);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel defaultTableModel = (DefaultTableModel)table1.getModel();
                int selectedIndex = table1.getSelectedRow();

                try {
                    int id = Integer.parseInt(defaultTableModel.getValueAt(selectedIndex,0).toString());

                    int dialogMessage =JOptionPane.showConfirmDialog(null,
                            "Do you want to delete?","Warning",JOptionPane.YES_NO_OPTION);

                    if (dialogMessage == JOptionPane.YES_OPTION){
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3307/linda"
                                ,"Mircin","Mircin27");

                        String sql = ("delete from record where id=?");
                        insert = con1.prepareStatement(sql);
                        insert.setInt(1, id);

                        insert.executeUpdate();

                        JOptionPane.showMessageDialog(null,"Record Deleted");
                        tableUpdate();

                        txtName.setText("");
                        txtMobile.setText("");
                        txtCourse.setText("");
                        txtName.requestFocus();
                    }
                }catch (ClassNotFoundException | SQLException ex){
                    System.out.println(ex);
                }
            }
        });
    }
    public JPanel getRootPanel() {
        return rootPanel;
    }
    Connection con1;
    PreparedStatement insert;

    private void tableUpdate(){
        int c;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3307/linda"
                    ,"Mircin","Mircin27");

            String sql = "select * from record";
            insert = con1.prepareStatement(sql);
            ResultSet resultSet = insert.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            c = resultSetMetaData.getColumnCount();

            DefaultTableModel defaultTableModel = (DefaultTableModel)table1.getModel();
            defaultTableModel.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> v2 = new Vector<>();

                for (int a=1; a <=c; a ++){
                    v2.add(resultSet.getString("id"));
                    v2.add(resultSet.getString("name"));
                    v2.add(resultSet.getString("mobile"));
                    v2.add(resultSet.getString("course"));
                }
                defaultTableModel.addRow(v2);
            }


        }catch (ClassNotFoundException | SQLException ex){
            System.out.println(ex);
        }
    }

    private void createTable() {
        table1.setModel(new DefaultTableModel(
                null,
                new String[]{"Id", "Name", "Mobile", "Course"}
        ));
    }
}
