package inf;

import codes.DBconnect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

 import java.sql.PreparedStatement;  // video one
//import com.mysql.cj.jdbc.PreparedStatementWrapper; //default suggest one

import java.sql.Connection; // video one
//import com.sun.jdi.connect.spi.Connection; //default one

//import com.mysql.cj.protocol.Resultset; //default one
import java.sql.ResultSet;  // video one
import javax.swing.ButtonGroup;


import net.proteanit.sql.DbUtils; //default one

/*..........import parts are come automatically..............*/


public class mainframe extends javax.swing.JFrame {
    // create a connection variables
    Connection conn = null; // -->import java.sql.Connection; // video one
    
    //PreparedStatementWrapper pst = null; // default one
    PreparedStatement pst = null; //video one --> import java.sql.PreparedStatement;  // video one
    
    ResultSet rs = null; //--.import java.sql.ResultSet;  // video one
    
    public mainframe() {  //constructor
        initComponents();
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(MaleRadioButton);
        bg.add(FemaleRadioButton);
        FemaleRadioButton.setSelected(true);

        
        
        conn = (Connection) DBconnect.connect();  //cast --> not in video --> import codes.DBconnect;
        //call connect() method in DBconnect and link. afterthat assign it to conn variable
    
        // add the method in constructor
        tableload();
        resultCount();
    }
    
    
 //    table load  -->    load data to table from database     **************************************************************************
    public void tableload(){
        try {
            // get values from the table and assign to variables
            String sql= "select id 'STUDENT ID', sname NAME,sage AGE,sgender GENDER,email EMAIL, sgrade GRADE, address1 'ADDRESS L1' ,address2 'ADDRESS L2',address3 'ADDRESS L3', spno 'STUDENT CONTACT', gname 'GUARDIAN NAME', gpno 'GUARDIAN CONTACT' from student";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            //set varible vaalues to table
            table1.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
//    get table data and set to boxes **********************************************************************************    
    public void tableData(){
        int r = table1.getSelectedRow(); //get selected row and assign to r
        
        //assign values to column data
        String id = table1.getValueAt(r, 0).toString(); // (row, column)
        String name = table1.getValueAt(r, 1).toString();
        String age = table1.getValueAt(r, 2).toString();
        String gender = table1.getValueAt(r, 3).toString();
        String email = table1.getValueAt(r, 4).toString();
        String grade = table1.getValueAt(r, 5).toString();
        String address1 = table1.getValueAt(r, 6).toString();
        String address2 = table1.getValueAt(r, 7).toString();
        String address3 = table1.getValueAt(r, 8).toString();
        String spno = table1.getValueAt(r, 9).toString();
        String gname = table1.getValueAt(r, 10).toString();
        String gpno = table1.getValueAt(r, 11).toString();
        
        // set values to software boxes
        idbox.setText(id);
        namebox.setText(name);
        agebox.setText(age);
        

        
        emailbox.setText(email);
        gradebox.setSelectedItem(grade);
        address1box.setText(address1);
        address2box.setText(address2);
        address3box.setText(address3);
        studentnobox.setText(spno);
        guardiannamebox.setText(gname);
        guardiannobox.setText(gpno);
    }
    
    // print the results count
    public void resultCount(){
        int rowcount = table1.getRowCount();
        results_count.setText("No of " +rowcount + " Results");  
        
    }
    
    
//    search box **********************************************************************************
    public void search(){
        //create variable to assign searchbox value
        String srch = searchbox.getText();
        
        try {
            String sql = "select * from student where sname like '%"+srch+"%' or address1 like '%"+srch+"%' or address2 like '%"+srch+"%'or address3 like '%"+srch+"%'or email like '%"+srch+"%'or gname like '%"+srch+"%'  ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            //set data to table
            table1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // clear other search boxes data
        idsearchbox.setText("");
        agesearchbox.setText("");
        gradesearchbox.setText("");

    }
    
    //    search box  searchById **********************************************************************************
    public void searchById(){
        //create variable to assign searchbox value
        String srch = idsearchbox.getText();
        
        try {
            String sql = "select * from student where id like '%"+srch+"%'  ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            //set data to table
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // clear other search boxes data
        searchbox.setText("");
        agesearchbox.setText("");
        gradesearchbox.setText("");
    }
    
        //    search box  searchByAge **********************************************************************************
    public void searchByAge(){
        //create variable to assign searchbox value
        String srch = agesearchbox.getText();
        
        try {
            String sql = "select * from student where sage like '%"+srch+"%'  ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            //set data to table
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } 
        // clear other search boxes data
        idsearchbox.setText("");
        searchbox.setText("");
        gradesearchbox.setText("");
    }
    
        //    search box  searchByGrade **********************************************************************************
    public void searchByGrade(){
        //create variable to assign searchbox value
        String srch = gradesearchbox.getText();
        
        try {
            String sql = "select * from student where sgrade like '%"+srch+"%'  ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            //set data to table
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } 
        // clear other search boxes data
        idsearchbox.setText("");
        agesearchbox.setText("");
        searchbox.setText("");
    }
    
 //    Update button **********************************************************************************

        public void update(){
            int check = JOptionPane.showConfirmDialog(null, "Are you sure want to update");

            if(check==0){
                 // before update data we have to get that data.
                // afterthat save them in variables
                String id = idbox.getText();
                String name = namebox.getText();
                String age = agebox.getText();
                //String gender = agebox.getText();
                String email = emailbox.getText();
                String address1 = address1box.getText();
                String address2 = address2box.getText();
                String address3 = address3box.getText();
                String spno = studentnobox.getText();
                String gname = guardiannamebox.getText();
                String gpno = guardiannobox.getText();
                String grade = gradebox.getSelectedItem().toString();

                try {
                    String sql ="update student set sname ='"+name+"', sage= '"+age+"' , sgrade ='"+grade+"' , "
                            + "sage= '"+age+"' , email= '"+email+"' , address1= '"+address1+"' , address2= '"+address2+"' , "
                            + "address3= '"+address3+"' , spno= '"+spno+"' , gname= '"+gname+"', gpno= '"+gpno+"'  where id='"+id+"' ";
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Updated Successfully");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }    

    }

        /* ...............CLEAR BUTTON CODES...................*/  
     public void clear(){
         //clear searchbox
         searchbox.setText("");
         idsearchbox.setText("");
         agesearchbox.setText("");
         gradesearchbox.setText("");
         
         //set id value to id
         idbox.setText("ID");  
         
         //clear BOXES
         namebox.setText("");
         agebox.setText("");
//         male_radiobox.setSelected(false);
//         female_radiobox.setSelected(false);
//         genderradio.setSelected(null, false);
         emailbox.setText("");
         gradebox.setSelectedIndex(0);
         address1box.setText("");
         address2box.setText("");
         address3box.setText("");
         studentnobox.setText("");
         guardiannamebox.setText("");
         guardiannobox.setText("");
         
     }
     
     



    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        insertbtn = new javax.swing.JButton();
        updatebtn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        clearbtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        namebox = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        agebox = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        emailbox = new javax.swing.JTextField();
        address1box = new javax.swing.JTextField();
        address2box = new javax.swing.JTextField();
        address3box = new javax.swing.JTextField();
        studentnobox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        guardiannamebox = new javax.swing.JTextField();
        guardiannobox = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        gradebox = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        FemaleRadioButton = new javax.swing.JRadioButton();
        MaleRadioButton = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        idbox = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        searchbox = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        gradesearchbox = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        agesearchbox = new javax.swing.JTextField();
        idsearchbox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        exitbtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        results_count = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 0, 0));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(1280, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(0, 102, 51));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        insertbtn.setBackground(new java.awt.Color(153, 255, 153));
        insertbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        insertbtn.setText("INSERT");
        insertbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertbtnActionPerformed(evt);
            }
        });
        jPanel6.add(insertbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        updatebtn.setBackground(new java.awt.Color(204, 255, 255));
        updatebtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updatebtn.setText("UPDATE");
        updatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatebtnActionPerformed(evt);
            }
        });
        jPanel6.add(updatebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        deletebtn.setBackground(new java.awt.Color(255, 51, 51));
        deletebtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deletebtn.setText("DELETE");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });
        jPanel6.add(deletebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        clearbtn.setBackground(new java.awt.Color(255, 255, 102));
        clearbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clearbtn.setText("CLEAR");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });
        jPanel6.add(clearbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 80, -1));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 410, 50));

        jPanel5.setBackground(new java.awt.Color(0, 102, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        namebox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.add(namebox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 220, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Name");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Age");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        agebox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.add(agebox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 220, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Grade");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Email");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        emailbox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.add(emailbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 220, -1));

        address1box.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        address1box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                address1boxActionPerformed(evt);
            }
        });
        jPanel5.add(address1box, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 220, -1));

        address2box.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.add(address2box, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 220, -1));

        address3box.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.add(address3box, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 220, -1));

        studentnobox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.add(studentnobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, 220, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Student's contact no");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Address Line 3");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Address Line 2");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Address Line 1");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Guardian's name");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        guardiannamebox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.add(guardiannamebox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 220, -1));

        guardiannobox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel5.add(guardiannobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 420, 220, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Guardian's contact no");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, -1));

        gradebox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        gradebox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" }));
        gradebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeboxActionPerformed(evt);
            }
        });
        jPanel5.add(gradebox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 140, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Gender");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        FemaleRadioButton.setBackground(new java.awt.Color(0, 102, 51));
        FemaleRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        FemaleRadioButton.setForeground(new java.awt.Color(255, 255, 255));
        FemaleRadioButton.setText("Female");
        FemaleRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FemaleRadioButtonActionPerformed(evt);
            }
        });
        jPanel5.add(FemaleRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, -1));

        MaleRadioButton.setBackground(new java.awt.Color(0, 102, 51));
        MaleRadioButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MaleRadioButton.setForeground(new java.awt.Color(255, 255, 255));
        MaleRadioButton.setText("Male");
        MaleRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaleRadioButtonActionPerformed(evt);
            }
        });
        jPanel5.add(MaleRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, -1, -1));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 410, 470));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("POWERD BY SUMUDU KULATHUNGA");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, -1, 20));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 0));
        jLabel18.setText("Student ");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Management System");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ID :");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, -1, -1));

        idbox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        idbox.setForeground(new java.awt.Color(255, 255, 255));
        idbox.setText("ID Number");
        jPanel2.add(idbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 570, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 700));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 153));
        jLabel3.setText("Search ...");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, -1, -1));

        searchbox.setBackground(new java.awt.Color(255, 255, 204));
        searchbox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchboxActionPerformed(evt);
            }
        });
        searchbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchboxKeyReleased(evt);
            }
        });
        jPanel1.add(searchbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 310, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Search by Grade");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 50, -1, -1));

        gradesearchbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradesearchboxActionPerformed(evt);
            }
        });
        gradesearchbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                gradesearchboxKeyReleased(evt);
            }
        });
        jPanel1.add(gradesearchbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, 80, 30));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Search by Age");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 50, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Search by ID");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 50, -1, -1));

        agesearchbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agesearchboxActionPerformed(evt);
            }
        });
        agesearchbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                agesearchboxKeyReleased(evt);
            }
        });
        jPanel1.add(agesearchbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 80, 30));

        idsearchbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idsearchboxActionPerformed(evt);
            }
        });
        idsearchbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idsearchboxKeyReleased(evt);
            }
        });
        jPanel1.add(idsearchbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 80, 30));

        jButton1.setBackground(new java.awt.Color(0, 204, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 20, -1, 30));

        exitbtn.setBackground(new java.awt.Color(255, 102, 0));
        exitbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        exitbtn.setForeground(new java.awt.Color(255, 255, 255));
        exitbtn.setText("EXIT");
        exitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtnActionPerformed(evt);
            }
        });
        jPanel1.add(exitbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, 70, -1));

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table1.setRowHeight(30);
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        table1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, 800, 590));

        results_count.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        results_count.setForeground(new java.awt.Color(255, 255, 255));
        results_count.setText("0");
        jPanel1.add(results_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 700));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
        System.exit(0);
        
    }//GEN-LAST:event_exitbtnActionPerformed

    private void insertbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertbtnActionPerformed
        /* ...............INSERT BUTTON CODES...................*/
        
        //create variables for store entered values by the user
        String name;
        int age;
        char gender;
        String email;
        int grade;      
        String address1;
        String address2;
        String address3;
        int spno;
        String gname;
        int gpno;
        
        //assign values
        name = namebox.getText();
        age = Integer.parseInt(agebox.getText()); // convert AGE string to int
        //gender =ButtonGroup.getSelection().toString();
        email = emailbox.getText();
        grade = Integer.parseInt(gradebox.getSelectedItem().toString()); // get values from combo box
        address1 = address1box.getText();
        address2 = address2box.getText();
        address3 = address3box.getText();
        spno = Integer.parseInt(studentnobox.getText());
        gname = guardiannamebox.getText();
        gpno = Integer.parseInt(guardiannobox.getText());
        
        
        
        try {
            String sql = "insert into student(sname,sage, email,sgrade, address1,address2,address3,spno,gname,gpno)values ('"+name+"','"+age+"','"+email+"','"+grade+"','"+address1+"','"+address2+"','"+address3+"','"+spno+"','"+gname+"' ,'"+gpno+"')";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data inserted Successfully");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        tableload();


    }//GEN-LAST:event_insertbtnActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseClicked

    private void updatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebtnActionPerformed
        update();
        tableload();
        resultCount();
    }//GEN-LAST:event_updatebtnActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        int check = JOptionPane.showConfirmDialog(null, "Are you sure want to delete");
        /* checkbox
        yes-->0
        no--> 1
        cancel -->2 
        */
        if(check==0){
            // first need to get id to a variable. (delete rows using id)
            String id = idbox.getText();
            try {
                String sql ="delete from student where id= '"+id+"' ";
                pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Data Deleted Successfully");
                
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        tableload();
        clear(); // after delete clear data
        

    }//GEN-LAST:event_deletebtnActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        clear();
    }//GEN-LAST:event_clearbtnActionPerformed

    private void searchboxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchboxKeyReleased
        search();
        resultCount();
    }//GEN-LAST:event_searchboxKeyReleased

    private void searchboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchboxActionPerformed

    private void gradeboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gradeboxActionPerformed

    private void gradesearchboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradesearchboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gradesearchboxActionPerformed

    private void gradesearchboxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gradesearchboxKeyReleased
        searchByGrade();
        resultCount();

    }//GEN-LAST:event_gradesearchboxKeyReleased

    private void agesearchboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agesearchboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_agesearchboxActionPerformed

    private void agesearchboxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_agesearchboxKeyReleased
        searchByAge();
        resultCount();
    }//GEN-LAST:event_agesearchboxKeyReleased

    private void idsearchboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idsearchboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idsearchboxActionPerformed

    private void idsearchboxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idsearchboxKeyReleased
        searchById();
        resultCount();
    }//GEN-LAST:event_idsearchboxKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tableload();
        resultCount();
        // clear other search boxes data
        idsearchbox.setText("");
        agesearchbox.setText("");
        gradesearchbox.setText("");
        searchbox.setText("");
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        tableData();
        resultCount();
    }//GEN-LAST:event_table1MouseClicked

    private void table1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table1KeyReleased
        tableData();
        resultCount();
    }//GEN-LAST:event_table1KeyReleased

    private void address1boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_address1boxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_address1boxActionPerformed

    private void FemaleRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FemaleRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FemaleRadioButtonActionPerformed

    private void MaleRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaleRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaleRadioButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainframe().setVisible(true);                                                                                            
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup ButtonGroup;
    private javax.swing.JRadioButton FemaleRadioButton;
    private javax.swing.JRadioButton MaleRadioButton;
    private javax.swing.JTextField address1box;
    private javax.swing.JTextField address2box;
    private javax.swing.JTextField address3box;
    private javax.swing.JTextField agebox;
    private javax.swing.JTextField agesearchbox;
    private javax.swing.JButton clearbtn;
    private javax.swing.JButton deletebtn;
    private javax.swing.JTextField emailbox;
    private javax.swing.JButton exitbtn;
    private javax.swing.JComboBox<String> gradebox;
    private javax.swing.JTextField gradesearchbox;
    private javax.swing.JTextField guardiannamebox;
    private javax.swing.JTextField guardiannobox;
    private javax.swing.JLabel idbox;
    private javax.swing.JTextField idsearchbox;
    private javax.swing.JButton insertbtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField namebox;
    private javax.swing.JLabel results_count;
    private javax.swing.JTextField searchbox;
    private javax.swing.JTextField studentnobox;
    private javax.swing.JTable table1;
    private javax.swing.JButton updatebtn;
    // End of variables declaration//GEN-END:variables
}
