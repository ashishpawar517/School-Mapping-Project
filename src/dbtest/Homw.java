/* * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.*/
package dbtest;
//import stmts
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pawar!
 */
public class Homw extends javax.swing.JFrame {
    /**
     * Creates new form Home
     */
    Connection con =null;
    public Homw() {
        try {
            this.setTitle("Centralised Database System");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
            con.setAutoCommit(false);
            
            initComponents();
        } catch (SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    void loadData(String table)
    {
        try {
         //   Class.forName("mysql-connector-java-5.1.23-bin");
            //DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
            //con.setAutoCommit(false);
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("select * from "+table)) {
               
                ResultSetMetaData rsmd = rs.getMetaData();
 //String name = rsmd.getColumnName(1)
                
                while(jTable1.getRowCount() > 0)
                {
                    ((DefaultTableModel)jTable1.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                
                //set column names
               // DefaultTableModel model = new DefaultTableModel();
               
                String[] Cols = new String[col]; 
                for(int i=0;i<col;i++)
                {
                    Cols[i] = new String();
                    Cols[i] = rsmd.getColumnName(i+1);
                }
                  //  ((DefaultTableModel)jTable1.getModel()).setColumnIdentifier(Cols);
                  DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                  model.setColumnIdentifiers(Cols);
                
                //        = rs.GetColumnsNameByQuery(query);
                //model.setColumnIdentifiers(ColumnNamesCollections);
                
                
                
                
                
                while(rs.next())
                {
                    Object[] rows =new Object[col];
                    for(int i=1;i<=col;i++)
                    {
                        rows[i-1]= rs.getObject(i);
                    }
                    ((DefaultTableModel)jTable1.getModel()).insertRow(rs.getRow()-1,rows);
                }
                
            }
            
        } catch ( SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(jPanel1, "Exception occured "+ex.getMessage());
        }
        
    }
 
    void executeSql(String command)
    {
            try {
         //   Class.forName("mysql-connector-java-5.1.23-bin");
        //     con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
            
                try (Statement st = con.createStatement()) {
                    ResultSet rs = st.executeQuery(command);
                    
                    while(jTable1.getRowCount() > 0)
                    {
                        ((DefaultTableModel)jTable1.getModel()).removeRow(0);
                    }
                    int col = rs.getMetaData().getColumnCount();
                    while(rs.next())
                    {
                        Object[] rows =new Object[col];
                        for(int i=1;i<=col;i++)
                        {
                            rows[i-1]= rs.getObject(i);
                        }
                        ((DefaultTableModel)jTable1.getModel()).insertRow(rs.getRow()-1,rows);
                    }
                    
                    rs.close();
                }
            
        } catch ( SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(jPanel1, "Exception occured "+ex.getMessage());
        }
    
    }
   
    void insertRecord()     
    {
       
    }
    
    void updateRecord(String table,String col ,String newValue,String parameter,int id)
    {
         try {
         //   Class.forName("mysql-connector-java-5.1.23-bin");
          //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
            
            String query ="update $table set "+col+"='"+newValue+"' where "+parameter+"=?";
             query =query.replace("$table",table);
             //query =query.replace("$par",newValue);
             
             try (PreparedStatement ps = con.prepareStatement(query)) {
                // ps.setString(1, tabl
                               
                                    
                
                
               // ps.setString(1,col);              
               // ps.setString(2,newValue);              
              //  ps.setString(2,parameter);              
                //ps.setString(1,col);              
                ps.setInt(1,id);
                 
                 ps.executeUpdate();
                 JOptionPane.showMessageDialog(jPanel1, "Record Updated ") ;
       
             }
            
            //st.close();
            
        } catch ( SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(jPanel1, "Exception occured "+ex.getMessage());
        }   
    }
    
    void deleteRecord(int id,String table)
    {
        try {
            //delete record
           // Connection con=null;
            // Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
            
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery("select * from "+table);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            String name = rsmd.getColumnName(1);
            //int col = rs.getMetaData().getColumnCount();
            
            
            
            
            String query ="delete from "+table+" where "+name+"=?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                // ps.setString(1, table);
                ps.setInt(1,id);
                ps.execute();
                JOptionPane.showMessageDialog(null,"Record Deleted");
                
            } catch (SQLException ex) {
                Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            loadData(table);
            
        } catch (SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
        }
           // Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
             
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1350, 600));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(39, 34, 98));

        jPanel4.setBackground(new java.awt.Color(34, 38, 68));
        jPanel4.setMinimumSize(new java.awt.Dimension(139, 39));
        jPanel4.setPreferredSize(new java.awt.Dimension(139, 44));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Teacher");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 11, -1, -1));

        jPanel5.setBackground(new java.awt.Color(39, 34, 68));
        jPanel5.setPreferredSize(new java.awt.Dimension(137, 44));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Student");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(39, 34, 68));
        jPanel6.setPreferredSize(new java.awt.Dimension(137, 44));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6MouseExited(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Inspection Data");
        jLabel3.setPreferredSize(new java.awt.Dimension(139, 34));
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 13, 125, 20));

        jPanel7.setBackground(new java.awt.Color(39, 34, 68));
        jPanel7.setPreferredSize(new java.awt.Dimension(137, 44));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Chief");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Address", "Gender", "Contact", "Role", "std_id", "subject_id", "long", "lattitude", "Salary"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setIcon(UIManager.getIcon("FileChooser.detailsViewIcon"));
        jMenu1.setText("File");

        jMenuItem4.setText("Refresh ");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator1);

        jMenuItem1.setText("Save");
        jMenuItem1.setToolTipText("Commit");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setText("Undo ");
        jMenuItem3.setToolTipText("Rollback to last commit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem2.setMnemonic(KeyEvent.VK_E);
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu4.setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
        jMenu4.setText("Insert");

        jMenuItem10.setText("Teacher");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Student");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuItem9.setText("Inspection-data");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem12.setText("Chief");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuBar1.add(jMenu4);

        jMenu2.setIcon(UIManager.getIcon("FileView.computerIcon"));
        jMenu2.setText("Modify");

        jMenuItem6.setText("Update");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem5.setText("Delete");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(UIManager.getIcon("FileView.directoryIcon"));
        jMenu3.setText("Query");
        jMenu3.setToolTipText("");

        jMenuItem8.setText("Select");
        jMenuItem8.setToolTipText("");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem7.setText("Sort By");
        jMenuItem7.setToolTipText("Order By ");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem18.setText("Search as Value");
        jMenuItem18.setToolTipText("where clause");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem18);

        jMenuItem19.setText("Search as Pattern");
        jMenuItem19.setToolTipText("Like Clause");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem19);
        jMenu3.add(jSeparator2);

        jMenuItem20.setText("SQL Query");
        jMenuItem20.setToolTipText("PRO Users Only");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem20);

        jMenuBar1.add(jMenu3);

        jMenu5.setIcon(UIManager.getIcon("FileView.fileIcon"));
        jMenu5.setText("Procedure");

        jMenuItem13.setText("SUM");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem13);

        jMenuItem14.setText("AVG");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem14);

        jMenuItem15.setText("MAX");
        jMenuItem15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem15MouseClicked(evt);
            }
        });
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jMenuItem16.setText("MIN");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        jMenuItem17.setText("Count");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem17);

        jMenuBar1.add(jMenu5);

        jMenu6.setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
        jMenu6.setText("GIS Features");

        jMenuItem22.setText("School");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem22);

        jMenuItem21.setText("Teacher");
        jMenuItem21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                none(evt);
            }
        });
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem21);

        jMenuItem23.setText("Student");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem23);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        loadData("teacher");
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        loadData("student");
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        // TODO add your handling code here:
        //entry
        //[100,100,152]
        jPanel5.setBackground(new Color(100,100,152));
    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        // TODO add your handling code here:
        //exit
        //[39,34,68]
        jPanel5.setBackground(new Color(39,34,68));
   
    }//GEN-LAST:event_jPanel5MouseExited

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
    //    JOptionPane.showMessageDialog(null,"hello");
    loadData("inspection");
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        // TODO add your handling code here:
                //entry
        //[100,100,152]
        jPanel4.setBackground(new Color(100,100,152));

    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        // TODO add your handling code here:
           //exit
        //[39,34,68]
        jPanel4.setBackground(new Color(39,34,68));
   
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        // TODO add your handling code here:
                      //entry
        //[100,100,152]
        jPanel6.setBackground(new Color(100,100,152));

    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
        // TODO add your handling code here:
                 //exit
        //[39,34,68]
        jPanel6.setBackground(new Color(39,34,68));
  
        
    }//GEN-LAST:event_jPanel6MouseExited

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here
        loadData("chief");
        //JOptionPane.showMessageDialog(jPanel1, "Thank YOu \n Group 1 \n DBMS PRoject \n Centralised Database system");
 
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        // TODO add your handling code here:
                        //entry
        //[100,100,152]
        jPanel7.setBackground(new Color(100,100,152));

    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        // TODO add your handling code here:
                     //exit
        //[39,34,68]
        jPanel7.setBackground(new Color(39,34,68));
  
    }//GEN-LAST:event_jPanel7MouseExited

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
            //con.setAutoCommit(false);
            con.rollback(sv);
            JOptionPane.showMessageDialog(jPanel1,"Rollback to last savepoint");
        } catch (SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
               JOptionPane.showMessageDialog(jPanel1,"Exception"+ex.getMessage());
        
        }
        
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    Savepoint sv=null;
    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
         
    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            //commit
           // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
            
           
          // con.setAutoCommit(false);
            //con.commit();
            sv= con.setSavepoint();
            JOptionPane.showMessageDialog(jPanel1,"Created Save Point");
        
            //conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(jPanel1,"Exception"+ex.getMessage());
        
        }
       
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        //update
          //update btn
              Object[] possibilities = {"Teacher", "Inspection", "school","standard","student","subject"};
        String s = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Table:\n",
                    "Update Operation",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    " ");
        
        
        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) 
        {
                
                  try {
                      //DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
                     // con.setAutoCommit(false);
                      Statement st = con.createStatement();
                      
                      ResultSet rs = st.executeQuery("select * from "+s);
                      ResultSetMetaData rsmd = rs.getMetaData();
                      //String name = rsmd.getColumnName(1)
                       int col = rs.getMetaData().getColumnCount();
                       String[] Cols = new String[col];
                      for(int i=0;i<col;i++)
                      {
                          Cols[i] = new String();
                          Cols[i] = rsmd.getColumnName(i+1);
                      } 
                      
                    String s1 = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Column:\n",
                    "Update Operation",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Cols,
                    " ");
                        
                       String newVal = JOptionPane.showInputDialog("Enter New value:");
                       String id =  JOptionPane.showInputDialog("Enter ID:");
                       
                       updateRecord(s,s1,newVal,Cols[0],Integer.parseInt(id));
                       loadData(s);
                      // JOptionPane.showMessageDialog(jPanel1,"Record Updated");
                    // int id_int = Integer.parseInt(id);
      
       
                  
                  
                  
                  } catch (SQLException ex) {
                      Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
                  }
              
            
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        //deletef
          //delete btn
        Object[] possibilities = {"Teacher", "Inspection", "school","standard","student","subject"};
        String s = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Table:\n",
                    "Delete Data",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    " ");
        
        
        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) 
        {
            // loadData(s);
            
            
            
            
            String id = JOptionPane.showInputDialog("Enter ID:");
            int id_int = Integer.parseInt(id);
            deleteRecord(id_int,s);
            //JO;
            
        }
      
       
      
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        //select 
        //select btn
        Object[] possibilities = {"Teacher", "Inspection", "Moderator","principal","school","standard","standard_teacher","student","subject","subject_teacher"};
        String s = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Table:\n",
                    "Tables",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    " ");
        
        
        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) 
        {
            loadData(s);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            // TODO add your handling code here:
            //order by
            Statement st = con.createStatement();
            Object[] possibilities = {"Teacher", "Inspection", "Moderator","principal","school","standard","standard_teacher","student","subject","subject_teacher"};
            String s = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Table:\n",
                    "Tables",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    " ");
            
            
            ResultSet rs = st.executeQuery("select * from "+s);
            ResultSetMetaData rsmd = rs.getMetaData();
            //String name = rsmd.getColumnName(1)
            int col = rs.getMetaData().getColumnCount();
            String[] Cols = new String[col];
            for(int i=0;i<col;i++)
            {
                Cols[i] = new String();
                Cols[i] = rsmd.getColumnName(i+1);
            }
            
            String s1 = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Column:\n",
                    "Order By Operation",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Cols,
                    " ");
            
            executeSql("select * from "+s+" order by "+s1);
        } catch (SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
        }
                        
                  
        
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
          // this.dispose();
           Inspection_data frame2 = new Inspection_data();
           frame2.setVisible(true);
             
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
       // this.dispose();
       Teacher t = new Teacher();
       t.setVisible(true);
       //new Teacher().setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        new Student().setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        new Chief().setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    void refreshLink()
    {
        try {
            // TODO add your handling code here:
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/information_system","root","");
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        refreshLink();   
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    void callProcedure(String proc_name)
    {
        try {
            String query = "{CALL "+proc_name+"()}";
            CallableStatement stmt = con.prepareCall(query);
            ResultSet rs = stmt.executeQuery();
              ResultSetMetaData rsmd = rs.getMetaData();
 //String name = rsmd.getColumnName(1)
                while(jTable1.getRowCount() > 0)
                {
                    ((DefaultTableModel)jTable1.getModel()).removeRow(0);
                }
                int col = rs.getMetaData().getColumnCount();
                
                //set column names
               // DefaultTableModel model = new DefaultTableModel();
               
                String[] Cols = new String[col]; 
                for(int i=0;i<col;i++)
                {
                    Cols[i] = new String();
                    Cols[i] = rsmd.getColumnName(i+1);
                }
                  //  ((DefaultTableModel)jTable1.getModel()).setColumnIdentifier(Cols);
                  DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                  model.setColumnIdentifiers(Cols);
                
                //        = rs.GetColumnsNameByQuery(query);
                //model.setColumnIdentifiers(ColumnNamesCollections);
                
                
                
                
                
                while(rs.next())
                {
                    Object[] rows =new Object[col];
                    for(int i=1;i<=col;i++)
                    {
                        rows[i-1]= rs.getObject(i);
                    }
                    ((DefaultTableModel)jTable1.getModel()).insertRow(rs.getRow()-1,rows);
                }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(jPanel1,ex.getMessage());
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        //Max salary
        callProcedure("sum1");
      //      JOptionPane.showMessageDialog(jPanel1,"clicked");
        
        
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem15MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jMenuItem15MouseClicked

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        callProcedure("max1");
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        callProcedure("min1");
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        callProcedure("count1");
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        callProcedure("avg1");
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        try {
            // TODO add your handling code here:
            Object[] possibilities = {"Teacher", "Inspection", "Moderator","principal","school","standard","standard_teacher","student","subject","subject_teacher"};
            String s = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Table:\n",
                    "Tables",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    " ");
            Statement st= con.createStatement();
            ResultSet rs = st.executeQuery("select * from "+s);
            ResultSetMetaData rsmd = rs.getMetaData();
            //String name = rsmd.getColumnName(1)
            int col = rs.getMetaData().getColumnCount();
            String[] Cols = new String[col];
            for(int i=0;i<col;i++)
            {
                Cols[i] = new String();
                Cols[i] = rsmd.getColumnName(i+1);
            }
            
            String s1 = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Column:\n",
                    "Order By Operation",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Cols,
                    " ");
            
           // executeSql("select * from "+s+" order by "+s1);
            
            String val = JOptionPane.showInputDialog("Enter Parameter to search :");
           
            executeSql("select * from "+s+" where "+s1+"="+val);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(jPanel1,"Exception"+ex.getMessage());
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
         try {
            // TODO add your handling code here:
            Object[] possibilities = {"Teacher", "Inspection", "Moderator","principal","school","standard","standard_teacher","student","subject","subject_teacher"};
            String s = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Table:\n",
                    "Tables",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    " ");
            Statement st= con.createStatement();
            ResultSet rs = st.executeQuery("select * from "+s);
            ResultSetMetaData rsmd = rs.getMetaData();
            //String name = rsmd.getColumnName(1)
            int col = rs.getMetaData().getColumnCount();
            String[] Cols = new String[col];
            for(int i=0;i<col;i++)
            {
                Cols[i] = new String();
                Cols[i] = rsmd.getColumnName(i+1);
            }
            
            String s1 = (String)JOptionPane.showInputDialog(
                    jPanel1,
                    "Select Column:\n",
                    "Order By Operation",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Cols,
                    " ");
            
           // executeSql("select * from "+s+" order by "+s1);
            
            String val = JOptionPane.showInputDialog("Enter Pattern :");
           
            executeSql("select * from "+s+" where "+s1+" like '"+val+"'");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(jPanel1,"Exception"+ex.getMessage());
            Logger.getLogger(Homw.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        String query = JOptionPane.showInputDialog("Enter Query ");
        if(query != null);
            executeSql(query);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void none(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_none
        // TODO add your handling code here:
        
        //retrieve data and write data to javascript api file 
        //open that file after writing 
        
        
        
        
        
    }//GEN-LAST:event_none

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        
        FileWriter fw =null;
        try
        {
             fw=new FileWriter("teacher.html");    
             fw.write("<html>");
             fw.write("<head>");
             fw.write("<meta name=\"viewport\" content=\"initial-scale=1.0, width=device-width\" />");
             fw.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://js.api.here.com/v3/3.0/mapsjs-ui.css?dp-version=1549984893\" />");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-core.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-service.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-ui.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-mapevents.js\"></script>");
             fw.write("</head>");
             fw.write("<body>");
             fw.write("<div id='map' style='width:100%; height:1900px; background: grey'/>" );
             fw.write("<script  type=\"text/javascript\" charset=\"UTF-8\" >");
             fw.write("function addMarkersToMap(map) {");
             
             
             Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select latitude,longitude from teacher");  
            int count =0;
            while(rs.next())  
            {
                //var parisMarker = new H.map.Marker({lat:48.8567, lng:2.3508});
                //map.addObject(parisMarker);
                fw.write("var marker"+count+" = new H.map.Marker({lat:"+rs.getString(1)+", lng:"+rs.getString(2)+"});");
                fw.write("map.addObject(marker"+count+");");
                count++;
            }
     
            fw.write("}");
            //Step 1: initialize communication with the platform  
            fw.write(" var platform = new H.service.Platform({");
            fw.write("app_id: 'devportal-demo-20180625',");    
            fw.write("app_code: '9v2BkviRwi9Ot26kp2IysQ',");   
            fw.write("useHTTPS: true");
            fw.write("});");
            fw.write("var pixelRatio = window.devicePixelRatio || 1;");
            fw.write("var defaultLayers = platform.createDefaultLayers({");
            fw.write("tileSize: pixelRatio === 1 ? 256 : 512,");
            fw.write("ppi: pixelRatio === 1 ? undefined : 320");
            fw.write("});");
            

            //Step 2: initialize a map - this map is centered over Europe
            fw.write("var map = new H.Map(document.getElementById('map'),");
            fw.write("defaultLayers.normal.map,{");
            fw.write("center: {lat:50, lng:5},");
            fw.write("zoom: 4,      ");
            fw.write("pixelRatio: pixelRatio");
            fw.write("});");

            //Step 3: make the map interactive
            // MapEvents enables the event system
            // Behavior implements default interactions for pan/zoom (also on mobile touch environments)
            fw.write("var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));");

            // Create the default UI components
            fw.write("var ui = H.ui.UI.createDefault(map, defaultLayers);");

            // Now use the map as required...
            fw.write("addMarkersToMap(map);");
            // moveMapToBerline(map)
            fw.write("</script>");
            fw.write("</body>");
            fw.write("</html>");
            //System.out.println(rs.getString(1)+"  "+rs.getString(2));  
            //con.close();  
            rs.close();
            fw.close();
            //JOptionPane.showMessageDialog(jPanel1,"done!");
            Runtime.getRuntime().exec(new String[] {"cmd ","/K","start teacher.html"}); 
       
            
        }
        catch(Exception e)
        {
            
        }
        
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        //school
        FileWriter fw =null;
        try
        {
             fw=new FileWriter("school.html");    
             fw.write("<html>");
             fw.write("<head>");
             fw.write("<meta name=\"viewport\" content=\"initial-scale=1.0, width=device-width\" />");
             fw.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://js.api.here.com/v3/3.0/mapsjs-ui.css?dp-version=1549984893\" />");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-core.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-service.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-ui.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-mapevents.js\"></script>");
             fw.write("</head>");
             fw.write("<body>");
             fw.write("<div id='map' style='width:100%; height:1900px; background: grey'/>" );
             fw.write("<script  type=\"text/javascript\" charset=\"UTF-8\" >");
             fw.write("function addMarkersToMap(map) {");
             
             
             Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select latitude,longitude from school");  
            int count =0;
            while(rs.next())  
            {
                //var parisMarker = new H.map.Marker({lat:48.8567, lng:2.3508});
                //map.addObject(parisMarker);
                fw.write("var marker"+count+" = new H.map.Marker({lat:"+rs.getString(1)+", lng:"+rs.getString(2)+"});");
                fw.write("map.addObject(marker"+count+");");
                count++;
            }
     
            fw.write("}");
            //Step 1: initialize communication with the platform  
            fw.write(" var platform = new H.service.Platform({");
            fw.write("app_id: 'devportal-demo-20180625',");    
            fw.write("app_code: '9v2BkviRwi9Ot26kp2IysQ',");   
            fw.write("useHTTPS: true");
            fw.write("});");
            fw.write("var pixelRatio = window.devicePixelRatio || 1;");
            fw.write("var defaultLayers = platform.createDefaultLayers({");
            fw.write("tileSize: pixelRatio === 1 ? 256 : 512,");
            fw.write("ppi: pixelRatio === 1 ? undefined : 320");
            fw.write("});");
            

            //Step 2: initialize a map - this map is centered over Europe
            fw.write("var map = new H.Map(document.getElementById('map'),");
            fw.write("defaultLayers.normal.map,{");
            fw.write("center: {lat:50, lng:5},");
            fw.write("zoom: 4,      ");
            fw.write("pixelRatio: pixelRatio");
            fw.write("});");

            //Step 3: make the map interactive
            // MapEvents enables the event system
            // Behavior implements default interactions for pan/zoom (also on mobile touch environments)
            fw.write("var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));");

            // Create the default UI components
            fw.write("var ui = H.ui.UI.createDefault(map, defaultLayers);");

            // Now use the map as required...
            fw.write("addMarkersToMap(map);");
            // moveMapToBerline(map)
            fw.write("</script>");
            fw.write("</body>");
            fw.write("</html>");
            //System.out.println(rs.getString(1)+"  "+rs.getString(2));  
            //con.close();  
            rs.close();
            fw.close();
            //JOptionPane.showMessageDialog(jPanel1,"done!");
            Runtime.getRuntime().exec(new String[] {"cmd ","/K","start school.html"}); 
       
            
        }
        catch(Exception e)
        {
            
        }
        
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
                //student
        FileWriter fw =null;
        try
        {
             fw=new FileWriter("student.html");    
             fw.write("<html>");
             fw.write("<head>");
             fw.write("<meta name=\"viewport\" content=\"initial-scale=1.0, width=device-width\" />");
             fw.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"https://js.api.here.com/v3/3.0/mapsjs-ui.css?dp-version=1549984893\" />");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-core.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-service.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-ui.js\"></script>");
             fw.write("<script type=\"text/javascript\" src=\"https://js.api.here.com/v3/3.0/mapsjs-mapevents.js\"></script>");
             fw.write("</head>");
             fw.write("<body>");
             fw.write("<div id='map' style='width:100%; height:1900px; background: grey'/>" );
             fw.write("<script  type=\"text/javascript\" charset=\"UTF-8\" >");
             fw.write("function addMarkersToMap(map) {");
             
             
             Statement stmt=con.createStatement();  
             ResultSet rs=stmt.executeQuery("select latitude,longitude from student");  
            int count =0;
            while(rs.next())  
            {
                //var parisMarker = new H.map.Marker({lat:48.8567, lng:2.3508});
                //map.addObject(parisMarker);
                fw.write("var marker"+count+" = new H.map.Marker({lat:"+rs.getString(1)+", lng:"+rs.getString(2)+"});");
                fw.write("map.addObject(marker"+count+");");
                count++;
            }
     
            fw.write("}");
            //Step 1: initialize communication with the platform  
            fw.write(" var platform = new H.service.Platform({");
            fw.write("app_id: 'devportal-demo-20180625',");    
            fw.write("app_code: '9v2BkviRwi9Ot26kp2IysQ',");   
            fw.write("useHTTPS: true");
            fw.write("});");
            fw.write("var pixelRatio = window.devicePixelRatio || 1;");
            fw.write("var defaultLayers = platform.createDefaultLayers({");
            fw.write("tileSize: pixelRatio === 1 ? 256 : 512,");
            fw.write("ppi: pixelRatio === 1 ? undefined : 320");
            fw.write("});");
            

            //Step 2: initialize a map - this map is centered over Europe
            fw.write("var map = new H.Map(document.getElementById('map'),");
            fw.write("defaultLayers.normal.map,{");
            fw.write("center: {lat:50, lng:5},");
            fw.write("zoom: 4,      ");
            fw.write("pixelRatio: pixelRatio");
            fw.write("});");

            //Step 3: make the map interactive
            // MapEvents enables the event system
            // Behavior implements default interactions for pan/zoom (also on mobile touch environments)
            fw.write("var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));");

            // Create the default UI components
            fw.write("var ui = H.ui.UI.createDefault(map, defaultLayers);");

            // Now use the map as required...
            fw.write("addMarkersToMap(map);");
            // moveMapToBerline(map)
            fw.write("</script>");
            fw.write("</body>");    
            fw.write("</html>");
            //System.out.println(rs.getString(1)+"  "+rs.getString(2));  
            //con.close();  
            rs.close();
            fw.close();
            //JOptionPane.showMessageDialog(jPanel1,"done!");
            Runtime.getRuntime().exec(new String[] {"cmd ","/K","start student.html"}); 
       
            
        }
        catch(IOException | SQLException e)
        {
            
        }

        
    }//GEN-LAST:event_jMenuItem23ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Homw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Homw().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
