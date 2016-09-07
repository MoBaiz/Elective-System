package login;

import java.awt.Button;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Student.student_main;
import Teacher.teacher_main;
import manage.Manage_main;

public class login extends JFrame implements ActionListener,WindowListener{
    private JLabel userid,userkind,password;  
    private Button enter=new Button("µÇÂ¼");
    private JComboBox comboBox=new JComboBox();
    private JPanel log =new JPanel();
    private JTextField id,pass;
    private int x=300;
    private int y=400;
    private String jdbc_driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/es";
    private String user="manage";
    private String db_password="1234";
    private Connection connection = null;
	private Statement state = null;
	private  ResultSet sql;
	public login(){
           log.setLayout(null);
		   this.setBounds(x,y,400,300);
		   userid=new JLabel("ÓÃ»§Ãû",Label.RIGHT);
           userid.setBounds(50,50,60,30);
           id=new JTextField();
           id.setBounds(100,50,200,30);
           password=new JLabel("ÃÜÂë",Label.RIGHT);
           password.setBounds(50,100,60,30);
           pass=new JTextField();
           pass.setBounds(100,100,200,30);
           userkind=new JLabel("identity");
           userkind.setBounds(50,150,60,30);
           comboBox.addItem("teacher");
           comboBox.addItem("student");
           comboBox.addItem("admin");
           comboBox.setBounds(100,150,90,30);
           enter.setBounds(180,200,40,30);
           enter.addActionListener(this);
           log.add(userid);
           log.add(id);
           log.add(password);
           log.add(pass);
           log.add(userkind);
           log.add(comboBox);
           log.add(enter);
           this.add(log);
           this.setTitle("Elective System login");
           this.setResizable(false);
		   this.setVisible(true);
		   try{
				Class.forName(jdbc_driver);
				connection=DriverManager.getConnection(url,user,db_password);
				state=connection.createStatement();
				}
				catch(SQLException e1){
					
				} 
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	if(e.getSource()==enter){
		String idin=id.getText();
		String passin=pass.getText();
		System.out.print("success"); 
	   if(comboBox.getSelectedItem().equals("admin")&&idin.equals("admin")&&passin.equals("admin")){
		  System.out.print(comboBox.getSelectedItem()); 
		  new Manage_main();
		  }
	   if(comboBox.getSelectedItem().equals("student")){
		   
		  try { 
			sql=state.executeQuery("select * from student where sno="+idin);
			if(!sql.next())
			JOptionPane.showMessageDialog(this, "wrong id or password","waring",JOptionPane.WARNING_MESSAGE);
			else{
				new student_main(sql.getString("sno"),sql.getString("sname"));
			}
		} catch (SQLException e1) {
			System.out.print(idin);
			JOptionPane.showMessageDialog(this, " id or password is wrong format","waring",JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
		}
	   }
	   if(comboBox.getSelectedItem().equals("teacher")){
		   try { 
				sql=state.executeQuery("select * from teacher where tno="+idin);
				if(!sql.next())
				JOptionPane.showMessageDialog(this, "wrong id or password","waring",JOptionPane.WARNING_MESSAGE);
				else{
					new teacher_main(sql.getString("tno"),sql.getString("tname"));
				}
			} catch (SQLException e1) {
				System.out.print(idin);
				JOptionPane.showMessageDialog(this, " id or password is wrong format","waring",JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			} 
	   }
	}
		
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

       
}
