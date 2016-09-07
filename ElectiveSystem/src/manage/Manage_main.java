package manage;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.directory.SearchControls;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Manage_main extends JFrame implements ActionListener{
    private String jdbc_driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/es";
    private String user="manage";
    private String password="1234";
    private Connection connection = null;
	private Statement state = null;
	private  ResultSet sql;
	private JPanel Manage_main =new JPanel();
	private JButton bt[]={
		new JButton("添加学生"),new JButton("添加老师"),new JButton("添加班级"),
		new JButton("删除学生"),new JButton("删除老师"),new JButton("删除课程")
	};
	private JButton search_bt[]={
			new JButton("query all student"),new JButton("query all teacher"),new JButton("query all course"),new JButton("query all class"),
			new JButton("query student"),new JButton("query teacher"),new JButton("query course")
		};
	private JTextField sno_t=new JTextField();
	private JTextField tno_t=new JTextField();
	private JTextField cno_t=new JTextField();
    private	JTextArea show_all=new JTextArea();
    private JScrollPane jsp=new JScrollPane(show_all);
	public Manage_main(){
	Manage_main.setLayout(null);
	setTitle("管理员主界面");
	for(int i=0;i<6;i++){
		bt[i].addActionListener(this);
		bt[i].setBounds(50+150*i,50, 160,30);
		Manage_main.add(bt[i]);
	}
	for(int i=4;i<7;i++){
		search_bt[i].addActionListener(this);
		search_bt[i].setBounds(300*(i-3)-100,100,140,30);
		add(search_bt[i]);
	}
	    JLabel sno=new JLabel("学号:");
	    sno_t.setBounds(110,100,60,30);
	    sno.setBounds(70,100,60,30);
        add(sno);
        add(sno_t);
        JLabel tno=new JLabel("教工号:");
	    tno_t.setBounds(410,100,60,30);
	    tno.setBounds(370,100,60,30);
        add(tno);
        add(tno_t);
        JLabel cno=new JLabel("课程号:");
	    cno_t.setBounds(710,100,60,30);
	    cno.setBounds(670,100,60,30);
        add(cno);
        add(cno_t);
        show_all.setLineWrap(true);
        show_all.setEditable(false);
        jsp.setBounds(50,200, 924,518);
        add(jsp);
	for(int i=0;i<4;i++){
		search_bt[i].addActionListener(this);
		search_bt[i].setBounds(100+200*i,150,160, 30);
		add(search_bt[i]);
	}
	setBounds(200,100,1024,768);
	add(Manage_main);
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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==search_bt[0]){     //查询全部学生
			show_all.setText(null);
			try {
				sql=state.executeQuery("select * from student");
				show_all.append("\t学号\t姓名\t性别\t年龄\t班级\n");
                   while(sql.next()){
					show_all.append("\t"+sql.getString("sno")+"\t"+sql.getString("sname")
					+"\t"+sql.getString("ssex")+"\t"+sql.getInt("sage")+"\t"+sql.getString("class_no")+"\n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			    show_all.setText(e.toString());
			}
		}
		else if(e.getSource()==search_bt[1]){ //查询所有老师
			show_all.setText(null);
			try {
				sql=state.executeQuery("select * from teacher");
				show_all.append("\t教工\t姓名\t性别\t年龄\n");
                   while(sql.next()){
					show_all.append("\t"+sql.getString("tno")+"\t"+sql.getString("tname")
					+"\t"+sql.getString("tsex")+"\t"+sql.getInt("tage")+"\n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			    show_all.setText(e.toString());
			}
		}
	    else if(e.getSource()==search_bt[2]){ //查询所有课程
	    	show_all.setText(null);
			   try {
				show_all.append("\ttno\ttname\tcno\tcname\t\tcredit\n");
				sql=state.executeQuery("select * from course,tc,teacher where (course.cno=tc.cno and tc.tno=teacher.tno)");
				while(sql.next()){
					show_all.append("\t"+sql.getString("tno")+"\t"+sql.getString("tname")+"\t"+sql.getString("cno")
					+"\t"+sql.getString("cname")+"\t\t"+sql.getInt("ccredit")+"\n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				show_all.append(e1.toString());
			}	
		}
	    else if(e.getSource()==search_bt[3]){ //查询所有班级
	    	show_all.setText(null);
			try {
				sql=state.executeQuery("select * from cclass");
				show_all.append("\tclass_no\tclass size\n");
                   while(sql.next()){
					show_all.append("\t"+sql.getString("class_no")+"\t"+sql.getString("class_size")+"\n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			    show_all.setText(e.toString());
			}
		}
		else if(e.getSource()==search_bt[4]){ //查询指定学生
			show_all.setText(null);
	    	String s_no="\""+sno_t.getText()+"\"";
			try {
				sql=state.executeQuery("select * from student where("+"sno="+s_no+")");
				show_all.append("\t学号\t姓名\t性别\t年龄\t班级\n");
                   while(sql.next()){
					show_all.append("\t"+sql.getString("sno")+"\t"+sql.getString("sname")
					+"\t"+sql.getString("ssex")+"\t"+sql.getInt("sage")+"\t"+sql.getString("class_no")+"\n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			    show_all.setText(e1.toString());
			}
		}
	    else if(e.getSource()==search_bt[5]){ //查询指定老师
	    	show_all.setText(null);
			String t_no="\""+tno_t.getText()+"\"";
			try {
				sql=state.executeQuery("select * from teacher where("+"tno="+t_no+")");
				show_all.append("\t教工\t姓名\t性别\t年龄\n");
                   while(sql.next()){
					show_all.append("\t"+sql.getString("tno")+"\t"+sql.getString("tname")
					+"\t"+sql.getString("tsex")+"\t"+sql.getInt("tage")+"\n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			    show_all.setText(e.toString());
			}
		}
        else if(e.getSource()==search_bt[5]){ //查询指定课程
			
		}
	    else if(e.getSource()==bt[0]){//添加学生
	    	new add_student();
	    }
        else if(e.getSource()==bt[1]){//添加老师
	    	new add_teacher();
	    }
        else if(e.getSource()==bt[2]){//添加班级
	    	new add_class();
	    }
        else if(e.getSource()==bt[3]){//删除学生
	    	new delete_student();
	    }
        else if(e.getSource()==bt[4]){//删除老师
	    	new delete_teacher();
	    }
        else if(e.getSource()==bt[5]){//删除课程
	    	new delete_course();
	    }
	}

}
