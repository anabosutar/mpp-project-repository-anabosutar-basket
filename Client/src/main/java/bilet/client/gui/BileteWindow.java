package bilet.client.gui;

import jdbc.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Properties;

public class BileteWindow extends JFrame {
    private JList bileteList;
    private boolean status;
    private Properties jdbcProps;
    private JTextField message;
    JFrame frame1;
    static JTable table;
    private BiletClientCtrl ctrl;
    private JdbcUtils jdbc;
    Connection con;
    public BileteWindow(String title, BiletClientCtrl ctrl){
        super(title);
        this.ctrl=ctrl;
        JPanel panel=new JPanel(new BorderLayout());

//        panel.add(createSendMessage(), BorderLayout.SOUTH);
 //       panel.add(getAllTickets(), BorderLayout.WEST);
        panel.add(getTablePanel(), BorderLayout.CENTER);
        System.out.println();
        getContentPane().add(panel);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                close();
            }
        });
//        JFrame frame = new JFrame();
//        frame.setTitle(title);
//        frame.setSize(800, 500);
//
//        // Adding Table View
//        frame.add(getTablePanel());
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
    }

    private void close(){
        ctrl.logout();
    }


    private JPanel getTablePanel() {
        JPanel tableJPanel = new JPanel(new GridLayout(1, 1));
        tableJPanel.setLayout(new BorderLayout());
        //Column Header
        String[] columns = {"id", "loc", "rand", "pret"};
        // Getting Data for Table from Database
        Object[][] data = getTicketDetails();
        // Creating JTable object passing data and header
        JTable ticketsTable = new JTable(data, columns);
        tableJPanel.add(ticketsTable.getTableHeader(), BorderLayout.NORTH);
        tableJPanel.add(ticketsTable, BorderLayout.CENTER);
        tableJPanel.setBorder(BorderFactory.createTitledBorder("Available Tickets"));
        return tableJPanel;
    }
    private Object[][] getTicketDetails() {

        Object[][] data = null;

        String DRIVER_NAME="org.sqlite.JDBC";
        String CONNECTION_URL="jdbc:sqlite:C:/Users/Ana/IdeaProjects/mpp-project-repository-anabosutar-basket/BasketDB.db";

        try {

            // Loading the Driver
            Class.forName(DRIVER_NAME);

            // Getting Database Connection Object by Passing URL, Username and Password
            Connection con = DriverManager.getConnection(CONNECTION_URL);
            //Connection con = jdbc.getConnection();

            PreparedStatement statement = con.prepareStatement("Select id,nr_loc,nr_rand,pret from BILETE");
            //ResultSet rs = statement.executeQuery();
            ResultSet rs2 = statement.executeQuery();

            int rowCount = 13; // Row Count
            int columnCount = 4; // Column Count

            data = new Object[rowCount][columnCount];

            // Starting from First Row for Iteration
            //rs.beforeFirst();

            int i = 0;

            while (rs2.next()) {


                data[i][0] = rs2.getInt(1);
                data[i][1] = rs2.getInt(2);
                data[i][2] = rs2.getInt(3);
                data[i][3] = rs2.getInt(4);

                i++;
            }

            status = true;

            // Closing the Resources;
            statement.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return data;
    }
    private int getRowCount(ResultSet rs) {
        int i =0;
        try {

            while (rs.next()){
                i++;
            }
            return i;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    // Method to get Column Count from ResultSet Object
    private int getColumnCount(ResultSet rs) {

        try {

            if(rs != null)
                return rs.getMetaData().getColumnCount();

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
    public String toString() {

        return (status) ? "Data Listed Successfully" : "Application Error Occured";
    }

}

