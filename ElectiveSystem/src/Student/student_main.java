package Student;

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

public class student_main extends JFrame implements ActionListener{
	private String jdbc_driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/es";
    private String user="manage";
    private String password="1234";
    private Connection connection = null;
	private Statement state = null;
	private  ResultSet sql;
	private JButton bt[]={
			new JButton("query all course"),new JButton("query select course"),
			new JButton("selecte courses"),new JButton("drop course"),new JButton("sum credit"),
			new JButton("Avg score")
	};
	private JTextField select=new JTextField();
	private JTextField drop=new JTextField();
	private JTextArea show_all=new JTextArea();
	private JScrollPane jsp=new JScrollPane(show_all);
	private String student_id;
	public student_main(String id,String name){
		student_id="\""+id+"\"";
		JPanel student_main=new JPanel();
		bt[0].setBounds(50,20,170,30);
		bt[1].setBounds(50,80,170,30);
		bt[2].setBounds(250,20,140,30);
		bt[3].setBounds(250,80,140,30);
		bt[4].setBounds(700,20,140,30);
		bt[5].setBounds(700,80,140,30);
		for(int i=0;i<6;add(bt[i]),bt[i].addActionListener(this),i++);
		JLabel lable1=new JLabel("cno:");
		JLabel lable2=new JLabel("cno:");
		lable1.setBounds(400,20,50,30);
		add(lable1);
		lable2.setBounds(400,80,50,30);
		add(lable2);
		select.setBounds(450,20,100,30);
		drop.setBounds(450,80,100,30);
		jsp.setBounds(50,120,924,598);
		show_all.setLineWrap(true);
		add(jsp);
		add(select);
		add(drop);
		setBounds(200,100,1024,768);
		add(student_main);
		setResizable(false);
		setVisible(true);
		setTitle("                student main page     sno:"+id+"       name:"+name);
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
		if(e.getSource()==bt[0]){        //查询所有课程
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
        if(e.getSource()==bt[1]){        //查询已选课程
        	show_all.setText(null);
 		   try {
 			show_all.append("\ttno\tcno\tcname\t\tcredit\tgrade\n");
 			sql=state.executeQuery("select * from sc,tc,course where (sc.sno="+student_id+"and sc.cno=tc.cno and sc.cno=course.cno)");
 			while(sql.next()){
 				show_all.append("\t"+sql.getString("tno")+"\t"+sql.getString("cno")
 				+"\t"+sql.getString("cname")+"\t\t"+sql.getInt("ccredit")+"\t"+sql.getInt("grade")+"\n");
 			}
 		} catch (SQLException e1) {
 			e1.printStackTrace();
 			show_all.append(e1.toString());
 		}
		}
        if(e.getSource()==bt[2]){        //选课
        	show_all.setText(null);
        	String cno="\""+select.getText()+"\"";
        	try {
				state.execute("insert into sc values("+student_id+","+cno+","+0+")");
				show_all.append("you select cno"+select.getText());
			} catch (SQLException e1) {
				e1.printStackTrace();
	 			show_all.append(e1.toString());
			}
        }
        if(e.getSource()==bt[3]){        //退课
        	show_all.setText(null);
        	String cno="\""+drop.getText()+"\"";
        	try {
				state.execute("delete from sc where (sno="+student_id+"and cno="+cno+")");
				show_all.append("you delete cno"+drop.getText());
			} catch (SQLException e1) {
				e1.printStackTrace();
	 			show_all.append(e1.toString());
			}
        }
        if(e.getSource()==bt[4]){        //总学分
        	show_all.setText(null);
  		   try {
  			sql=state.executeQuery("select sum(ccredit) from sc,tc,course where (sc.sno="+student_id+"and sc.cno=tc.cno and sc.cno=course.cno)");
  			while(sql.next()){
  				show_all.append("you select course sum credits are "+sql.getInt("sum(ccredit)"));
  			}
  		} catch (SQLException e1) {
  			e1.printStackTrace();
  			show_all.append(e1.toString());
  		}
        }
        if(e.getSource()==bt[5]){        //平均分
         	show_all.setText(null);
   		   try {
   			sql=state.executeQuery("select avg(grade) from sc,tc,course where (sc.sno="+student_id+"and sc.cno=tc.cno and sc.cno=course.cno)");
   			while(sql.next()){
   				show_all.append("you select course avg grade are "+sql.getInt("avg(grade)"));
   			}
   		} catch (SQLException e1) {
   			e1.printStackTrace();
   			show_all.append(e1.toString());
   		}
        }
		
	}

}
