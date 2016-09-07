package Teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;

import com.mysql.jdbc.CallableStatement;

public class teacher_main extends JFrame implements ActionListener{
	private String jdbc_driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/es";
    private String user="manage";
    private String password="1234";
    private Connection connection = null;
	private Statement state = null;
	private  ResultSet sql;
	private String teacher_id;
	private list snoarray;
	private JButton bt[]={
			new JButton("alloc course"),new JButton("query all courses"),
			new JButton("teach courses"),new JButton("student rouster "),new JButton("register score"),
			new JButton("up student"),new JButton("down student"),new JButton("save")
	};
	private JTextField jt[]={
			new JTextField(),new JTextField(),new JTextField(),new JTextField(),new JTextField()
	};
	private JTextArea jta[]={
			new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea()
	};
	private JLabel JL[]={
		new JLabel("cno"),new JLabel("cname"),new JLabel("creadit")
		,new JLabel("cno"),new JLabel("cno")
	};
	private JTextArea show_all=new JTextArea();
	private JScrollPane jsp=new JScrollPane(show_all);
	public teacher_main(String id,String name){
		teacher_id=id;
		JPanel student_main=new JPanel();
	/*
	 *  	bt[0].setBounds(50,20,170,30);
	 *  bt[1].setBounds(50,80,170,30);
		bt[2].setBounds(250,20,140,30);
		bt[3].setBounds(250,80,140,30);
		bt[4].setBounds(700,20,140,30);
		bt[5].setBounds(700,80,140,30);
	 */ JL[0].setBounds(50,20,60,30);
	    jt[0].setBounds(100,20,70,30);
	    JL[1].setBounds(270,20,60,30);
	    jt[1].setBounds(330,20,200,30);
	    JL[2].setBounds(560,20,60,30);
	    jt[2].setBounds(620,20,70,30);
	    bt[0].setBounds(800,20,130,30);
	    bt[1].setBounds(50,80,140,30);
	    bt[2].setBounds(220,80,140,30);
	    JL[3].setBounds(370,80,60,30);
	    jt[3].setBounds(400,80,70,30);
	    bt[3].setBounds(500,80,140,30);
	    JL[4].setBounds(680,80,60,30);
	    jt[4].setBounds(710,80,70,30);
	    bt[4].setBounds(820,80,140,30);
	    jta[0].setBounds(50,140,100,60);
	    jta[1].setBounds(250,140,100,30);
	    jta[5].setBounds(250,170,100,30);
	    jta[2].setBounds(450,140,100,20);
	    jta[4].setBounds(450,170,100,20);
	    jta[3].setBounds(600,140,100,60);
	    bt[7].setBounds(720,135,100,50);
	    bt[5].setBounds(850,125,120,30);
	    bt[6].setBounds(850,170,120,30);
	    jta[0].setEditable(false);
	    jta[1].setEditable(false);
	    jta[2].setEditable(false);
	    jta[3].setEditable(false);
	    jta[5].setEditable(false);
		for(int i=0;i<5;add(jt[i]),i++);
		for(int i=0;i<6;add(jta[i]),i++);
		for(int i=0;i<5;add(JL[i]),i++);
		for(int i=0;i<8;add(bt[i]),bt[i].addActionListener(this),i++);
		jsp.setBounds(50,220,924,498);
		add(jsp);
		show_all.setLineWrap(true);
		setBounds(200,100,1024,768);
		add(student_main);
		setResizable(false);
		setVisible(true);
		setTitle("                teacher main page     tno:"+id+"       name:"+name);
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
		if(e.getSource()==bt[0]){      //开课
		show_all.setText(null);
		String cno="\""+jt[0].getText()+"\"";
		String cname="\""+jt[1].getText()+"\"";
		String credit=jt[2].getText();
		System.out.println(teacher_id+cno+cname+credit);
		try {
			state.execute("call add_course("+teacher_id+","+cno+","+cname+","+credit+")");
			show_all.append("allocing course success");
		} catch (SQLException e1) {
			e1.printStackTrace();
		    show_all.append(e1.toString());
		}
		}
	  if(e.getSource()==bt[1]){     //查询所有课
		  show_all.setText(null);
		   try {
			show_all.append("\ttno\tcno\tcname\t\tcredit\n");
			sql=state.executeQuery("select * from course,tc where (course.cno=tc.cno)");
			while(sql.next()){
				show_all.append("\t"+sql.getString("tno")+"\t"+sql.getString("cno")
				+"\t"+sql.getString("cname")+"\t\t"+sql.getInt("ccredit")+"\n");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			show_all.append(e1.toString());
		}
	  }
	  if(e.getSource()==bt[2]){     //查开的课，&&人数
		  show_all.setText(null);
		  show_all.append("\ttno\tcno\tcname\t\tcredit\tsize\n");
		  try {
			sql=state.executeQuery(
					    "select course.cno,tc.tno,course.cname,course.ccredit,count(*) from tc,course,sc where(tc.cno=course.cno and tc.cno=sc.cno and tc.tno="+teacher_id+") group by tc.cno");
			while(sql.next()){
				show_all.append("\t"+sql.getString("tno")+"\t"+sql.getString("cno")+"\t"+sql.getString("cname")+"\t\t"+sql.getInt("ccredit")+"\t"+sql.getInt("count(*)")+"\n");	
			}
			sql=state.executeQuery(
				    "select course.cno,tc.tno,course.cname,course.ccredit from tc,course where (tc.cno=course.cno and tc.tno="+teacher_id+" and tc.cno not in(select cno from sc));");
		    while(sql.next()){
			show_all.append("\t"+sql.getString("tno")+"\t"+sql.getString("cno")+"\t"+sql.getString("cname")+"\t\t"+sql.getInt("ccredit")+"\t"+0+"\n");	
		}
		} catch (SQLException e1) {
			e1.printStackTrace();
			show_all.append(e1.toString());
		}
	  }
      if(e.getSource()==bt[3]){    //选课花名册
    	  show_all.setText(null);
    	  String cno="\""+jt[3].getText()+"\"";
    	  show_all.append("\tcname\t\tclass no\tsno\tsname\tscore\n");
    	  try {
			sql=state.executeQuery("select sc.sno,course.cname,sc.grade,student.class_no,student.sname from course,sc,student where (sc.cno="+cno+" and sc.sno=student.sno and sc.cno=course.cno)");
		       while(sql.next()){
		    	   show_all.append("\t"+sql.getString("cname")+"\t\t"+sql.getString("class_no")+"\t"+sql.getString("sno")+"\t"+sql.getString("sname")+"\t"+sql.getInt("grade")+"\n");  
		       }
    	  } catch (SQLException e1) {
			e1.printStackTrace();
			show_all.append(e1.toString());
		}
    	  
	  }
		if (e.getSource() == bt[4]) { // 登分
			String cno = jt[4].getText().replace(" ", "");
			try {
				sql = state.executeQuery("select tno from tc where (tno=" + teacher_id+" and cno="+cno+")");
				if (!sql.next())
					JOptionPane.showMessageDialog(this, "no permission to register score", "waring",
							JOptionPane.WARNING_MESSAGE);
				else {
					show_all.setText(null);
					for (int i = 0; i < 5; jta[i].setText(null), i++)
						;
					show_all.setText("begin register grade");
					try {
						/*
						 * java.sql.CallableStatement cs=
						 * connection.prepareCall("{?= call cno_check(1)}");
						 * cs.registerOutParameter("1", Types.VARCHAR);
						 * cs.setString(1,cno); cs.setString(2,teacher_id);
						 * cs.execute();
						 * System.out.println("wqerwqer"+cs.getString(1));
						 */
						sql = state.executeQuery("select * from sc where cno=" + cno);
						LinkedList<String> snolist = new LinkedList<String>();
						while (sql.next()) {
							snolist.add(sql.getString("sno"));
						}
						snoarray = new list(snolist);

					} catch (SQLException e1) {
						e1.printStackTrace();
						show_all.append(e1.toString());
					}
					if (snoarray.getlength() != 0) {
						System.out.println(snoarray.getfirst());
						 String sno="\""+snoarray.getfirst()+"\""; 
						sql=state.executeQuery("select * from sc where (sno="+sno+"and cno="+cno+")");
						sql.next();
						jta[0].append("  cno  \n");
						jta[0].append("  "+sql.getString("cno")+"  \n");
						jta[1].append("  sno  \n");
						jta[5].append("  "+sql.getString("sno")+"  \n");
						jta[2].append("  grade  \n");
						jta[4].append("  "+sql.getString("grade")+"  \n");
						jta[3].append("  i/total  \n");
						jta[3].append("  "+snoarray.getpos()+"/"+snoarray.getlength()+"  \n");
						show_all.append("begin regiser grade for student who select cno:" + cno
								+ ", choose up or down to select student\n");
					} else
						show_all.append("no student select");
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
      if(e.getSource()==bt[5]){    //上一个
    	  String grade="\""+jta[4].getText()+"\"";
    	  String cno="\""+jt[4].getText()+"\"";
    	  if(snoarray.hasup()){
    		  String sno="\""+snoarray.getup()+"\""; 
    		  try { 
    		    for(int i=0;i<6;jta[i].setText(null),i++);
				sql=state.executeQuery("select * from sc where (sno="+sno+"and cno="+cno+")");
				while(sql.next()){
					jta[0].append("  cno  \n");
					jta[0].append("  "+sql.getString("cno")+"  \n");
					jta[1].append("  sno  \n");
					jta[5].append("  "+sql.getString("sno")+"  \n");
					jta[2].append("  grade  \n");
					jta[4].append("  "+sql.getString("grade")+"  \n");
					jta[3].append("  i/total  \n");
					jta[3].append("  "+snoarray.getpos()+"/"+snoarray.getlength()+"  \n");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println(e1.toString());
			}
    	  }
	  } 
      if(e.getSource()==bt[6]){    //下一个
    	  String grade="\""+jta[4].getText()+"\"";
    	  String cno="\""+jt[4].getText()+"\"";
    	  if(snoarray.hasnext()){
    		  String sno="\""+snoarray.getnext()+"\""; 
    		  try { 
    		    for(int i=0;i<6;jta[i].setText(null),i++);
				sql=state.executeQuery("select * from sc where (sno="+sno+" and cno="+cno+")");
				while(sql.next()){
					jta[0].append("  cno  \n");
					jta[0].append("  "+sql.getString("cno")+"  ");
					jta[1].append("  sno  \n");
					jta[5].append("  "+sql.getString("sno")+"  ");
					jta[2].append("  grade  \n");
					jta[4].append("  "+sql.getString("grade")+"  ");
					jta[3].append("  i/total  \n");
					jta[3].append("  "+snoarray.getpos()+"/"+snoarray.getlength()+"  ");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println(e1.toString());
			}
    	  }
	  }
      if(e.getSource()==bt[7]){   //更新成绩
    	  String grade=jta[4].getText().replace(" ","");
    	  String sno=jta[5].getText().replace(" ","");
    	  String cno=jt[4].getText().replace(" ","");
    	  try {
    		int score=Integer.parseInt(grade);
    			if(score<0||score>100)
    				JOptionPane.showMessageDialog(this, "Illegal grade","waring",JOptionPane.WARNING_MESSAGE);
    			else {
    				int num=state.executeUpdate("update sc set grade="+grade+" where (sno="+sno+" and cno="+cno+")");
    				if(num!=0)
    				show_all.append("sno:"+sno+"grade has been updated\n");
    			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			show_all.append(e1.toString());
		} 
    	 
      }
	}
}
class list{
	private LinkedList<String> snolist;
	private int length;
	private int pos=0;
	public list(LinkedList<String> list){
		snolist=list;
		length=snolist.size();
	}
	public boolean hasnext(){
		if(pos<length-1)
			return true;
		else return false;
	}
	public boolean hasup(){
		if(pos>0)
			return true;
		else return false;
	}
	public String getnext(){
		return snolist.get(++pos);
	}
	public String getup(){
		return snolist.get(--pos);
	}
	public String getfirst(){
		return snolist.get(0);
	}
	public int getpos(){
		return pos+1;
	}
	public int getlength(){
		return length;
	}
}
	
