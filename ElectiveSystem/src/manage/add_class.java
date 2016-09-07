package manage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class add_class extends JFrame implements ActionListener{
	private String jdbc_driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/es";
    private String user="manage";
    private String password="1234";
    private Connection connection = null;
	private Statement state = null;
	private  ResultSet sql;
	private JButton sure=new JButton("确定");
	private JTextField class_no=new JTextField();
	private JTextField result=new JTextField();
	public add_class(){
		JPanel add_class_panel=new JPanel();
		setTitle("添加班级");
		JLabel text=new JLabel("班级号(2位数字):");
		text.setBounds(120,50,100,30);
		add(text);
		sure.setBounds(170,200,60,40);
		sure.addActionListener(this);
		add(sure);
		result.setBounds(150,100,100,50);
		result.setEditable(false);
		add(result);
		class_no.setBounds(220,50,60,30);
		add(class_no);
		setBounds(200,100,400,300);
		add(add_class_panel);
		setResizable(false);
		setVisible(true);
		try{
		Class.forName(jdbc_driver);
		connection=DriverManager.getConnection(url,user,password);
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
		result.setText(null);
		String no=class_no.getText();
		if(e.getSource()==sure){
		   if(no.length()==2){
			 try {
				state.execute("insert into cclass values('"+no+"','0');");
				result.setText("插入成功");
			} catch (SQLException e1) {
				e1.printStackTrace();
				result.setText("班号重复");
			} 
		   }
		   else result.setText("错误的班号");
		}
	}
	

}
