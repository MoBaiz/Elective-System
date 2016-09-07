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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class add_student extends JFrame implements ActionListener{
	private String jdbc_driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/es";
    private String user="manage";
    private String password="1234";
    private Connection connection = null;
	private Statement state = null;
	private  ResultSet sql;
	private JButton sure=new JButton("确定");
	private JTextField sno=new JTextField();
	private JTextField sname=new JTextField();
	private JTextField sex=new JTextField();
	private JTextField age=new JTextField();
	private JTextField class_no=new JTextField();
	private JTextArea result=new JTextArea();
	private JScrollPane jsp=new JScrollPane(result); 
	public add_student(){
		JPanel add_class_panel=new JPanel();
		setTitle("添加学生");
		JLabel text1=new JLabel("学号:");
		text1.setBounds(50,0,60,30);
		add(text1);
		sno.setBounds(80,0,60,30);
		add(sno);
		JLabel text2=new JLabel("姓名:");
		text2.setBounds(150,0,60,30);
		add(text2);
		sname.setBounds(180,0,60,30);
		add(sname);
		JLabel text3=new JLabel("性别:");
		text3.setBounds(250,0,60,30);
		add(text3);
		sex.setBounds(280,0,60,30);
		add(sex);
		JLabel text4=new JLabel("年龄:");
		text4.setBounds(90,50,60,30);
		add(text4);
		age.setBounds(130,50,60,30);
		add(age);
		JLabel text5=new JLabel("班级号:");
		text5.setBounds(190,50,60,30);
		add(text5);
		class_no.setBounds(230,50,60,30);
		add(class_no);
		sure.setBounds(170,200,60,40);
		sure.addActionListener(this);
		add(sure);
		jsp.setBounds(100,100,200,100);
		result.setEditable(false);
		result.setLineWrap(true);
		add(jsp);
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
		String student_no=sno.getText();
		String student_name="'"+sname.getText()+"'";
		String student_age=age.getText();
		String student_sex="'"+sex.getText()+"'";
		String Class_no=class_no.getText();
		if(e.getSource()==sure){
		   if(student_no.length()==8&&Class_no.length()==2){
			 try {
				state.execute("insert into student values("+student_no+","+student_name
						+","+student_sex+","+student_age+","+"'"+Class_no+"'"+")");
				result.setText("插入成功");
			} catch (SQLException e1) {
				e1.printStackTrace();
				result.setText(e1.toString());
			} 
		   }
		   else result.setText("输入错误");
		}
	}
	

}
