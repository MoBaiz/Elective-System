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

public class delete_teacher extends JFrame implements ActionListener{
	private String jdbc_driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/es";
    private String user="manage";
    private String password="1234";
    private Connection connection = null;
	private Statement state = null;
	private  ResultSet sql;
	private JButton sure=new JButton("Delete");
	private JTextField s_no=new JTextField();
	private JTextField result=new JTextField();
	public delete_teacher(){
		JPanel add_class_panel=new JPanel();
		setTitle("delete teacher");
		JLabel text=new JLabel("tno:");
		text.setBounds(140,50,100,30);
		add(text);
		sure.setBounds(170,200,80,40);
		sure.addActionListener(this);
		add(sure);
		result.setBounds(150,100,100,50);
		result.setEditable(false);
		add(result);
		s_no.setBounds(200,50,60,30);
		add(s_no);
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
		String no=s_no.getText().replace(" ","");
		if(e.getSource()==sure){
		   if(no.length()==8){
			 try {
				state.execute("delete from tc where(tno="+no+")");
				state.execute("delete from sc where(tno="+no+")");
				state.execute("delete from teacher where(tno="+no+")");
				result.setText("delete success");
			} catch (SQLException e1) {
				e1.printStackTrace();
				result.setText("tno isn's exist");
			} 
		   }
		   else result.setText("tno error");
		}
	}
	


}
