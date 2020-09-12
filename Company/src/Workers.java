import java.sql.*;
import java.util.*;
public class Workers {

	public static void main(String[] args) {
		try {
			//String url="jdbc:mysql://localhost:3307/company?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST";
			//String uname="root";
			//String pass="root";
//			String url="jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7359045";
//			String uname="sql7359045";
//			String pass="UcrhuLIuHk";
			dbProperties dbp=new dbProperties();
			Connection con=DriverManager.getConnection(dbp.getUrl(),dbp.getUname(),dbp.getPass());
			Statement st1=con.createStatement();
			Statement st2=con.createStatement();
			Statement st3=con.createStatement();
			ResultSet rs1=st1.executeQuery("select max(id) from fullTimeWorkers");
			ResultSet rs2=st2.executeQuery("select max(id) from shiftWorkers");
			ResultSet rs3=st3.executeQuery("select max(id) from volunteers");
			rs1.next();
			rs2.next();
			rs3.next();
			int nextId=Math.max(rs1.getInt("max(id)"), Math.max(rs2.getInt("max(id)"), rs3.getInt("max(id)")))+1;
			boolean quit=false;
			Scanner s=new Scanner(System.in);
			
			String key,firstName,lastName,salary,hWorked,hWage;
			Statement st=con.createStatement();
			ResultSet rs;
			while(!quit)
			{
				System.out.println("-----------------------------------\nMENU\n-----------------------------------");
				System.out.println("Press 1 to print the info about all the workers information from the database");
				System.out.println("Press 2 to delete a worker from the database");
				System.out.println("Press 3 to add a worker to the database");
				System.out.println("Press 4 to change a worker's information in the database");
				System.out.println("Press 5 to print information about the company");
				System.out.println("Press 8 to quit");
				try
				{
					key=s.nextLine();
					if(key.length()!=1)
						throw new Exception();
					switch(key.charAt(0))
					{
					case '1':
						rs=st.executeQuery("select count(*) from fullTimeWorkers");
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from fullTimeWorkers");
							System.out.println("Full-time workers:\nID\tname\t\tsalary");
							while(rs.next()) {
								System.out.println(rs.getInt("id")+"\t"+rs.getString("firstName")+" "+rs.getString("lastName")+"\t$"+rs.getInt("salary"));
							}
						}
						rs=st.executeQuery("select count(*) from shiftWorkers");
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from shiftWorkers");
							System.out.println("Shift workers:\nID\tname\t\thours worked\thourly wage\tsalary");
							while(rs.next()) {
								System.out.println(rs.getInt("id")+"\t"+rs.getString("firstName")+" "+rs.getString("lastName")+"\t"+rs.getInt("hoursWorked")+"\t\t$"+rs.getInt("hourlyWage")+"\t\t$"+(rs.getInt("hoursWorked")*rs.getInt("hourlyWage")));
							}
						}
						rs=st.executeQuery("select count(*) from volunteers");
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from volunteers");
							System.out.println("Volunteers:\nID\tname");
							while(rs.next()) {
								System.out.println(rs.getInt("id")+"\t"+rs.getString("firstName")+" "+rs.getString("lastName"));
							}
						}
						break;
					case '2':
						System.out.print("Enter the ID of the worker you want to delete:");
						key=s.nextLine();
						rs=st.executeQuery("select count(*) from fullTimeWorkers where id="+key);
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from fullTimeWorkers where id="+key);
							rs.next();
							System.out.println(rs.getString("firstName")+" "+rs.getString("lastName")+" was  deleted successfully from the database.");
							st.executeUpdate("delete from fullTimeWorkers where id="+key);
							break;
						}
						rs=st.executeQuery("select count(*) from shiftWorkers where id="+key);
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from shiftWorkers where id="+key);
							rs.next();
							System.out.println(rs.getString("firstName")+" "+rs.getString("lastName")+" was  deleted successfully from the database.");
							st.executeUpdate("delete from shiftWorkers where id="+key);
							break;
						}
						rs=st.executeQuery("select count(*) from volunteers where id="+key);
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from volunteers where id="+key);
							rs.next();
							System.out.println(rs.getString("firstName")+" "+rs.getString("lastName")+" was  deleted successfully from the database.");
							st.executeUpdate("delete from volunteers where id="+key);
							break;
						}
						System.out.println("ID was not found.");
						break;
					case '3':
						System.out.println("Press 1 to add a full-time worker\nPress 2 to add a shiftworker\nPress 3 to add volunteer");
						key=s.nextLine();
						if(key.length()!=1)
							throw new Exception();
						switch(key.charAt(0))
						{
						case '1':
							System.out.println("Enter the worker's first name: ");
							firstName=s.nextLine();
							System.out.println("Enter the worker's last name: ");
							lastName=s.nextLine();
							System.out.println("Enter the worker's salary: ");
							salary=s.nextLine();
							if(Integer.parseInt(salary)<1)
								throw new Exception();
							st.executeUpdate("insert into fullTimeWorkers values ("+nextId+",'"+firstName+"','"+lastName+"',"+Integer.parseInt(salary)+")");
							nextId++;
							System.out.println(firstName+" "+lastName+" was added successfully.");
							break;
						case '2':
							System.out.println("Enter the worker's first name: ");
							firstName=s.nextLine();
							System.out.println("Enter the worker's last name: ");
							lastName=s.nextLine();
							System.out.println("Enter the hours the worker worked this month: ");
							hWorked=s.nextLine();
							if(Integer.parseInt(hWorked)<1)
								throw new Exception();
							System.out.println("Enter the worker's hourly wage: ");
							hWage=s.nextLine();
							if(Integer.parseInt(hWage)<1)
								throw new Exception();
							st.executeUpdate("insert into shiftWorkers values ("+nextId+",'"+firstName+"','"+lastName+"',"+Integer.parseInt(hWorked)+","+Integer.parseInt(hWage)+")");
							nextId++;
							System.out.println(firstName+" "+lastName+" was added successfully.");
							break;
						case '3':
							System.out.println("Enter the worker's first name: ");
							firstName=s.nextLine();
							System.out.println("Enter the worker's last name: ");
							lastName=s.nextLine();
							st.executeUpdate("insert into volunteers values ("+nextId+",'"+firstName+"','"+lastName+"')");
							nextId++;
							System.out.println(firstName+" "+lastName+" was added successfully.");
							break;
						default:
							throw new Exception();
						}
						break;
					case '4':
						System.out.print("Enter the ID of the worker you want to update:");
						key=s.nextLine();
						if(Integer.parseInt(key)<1)
							throw new Exception();
						rs=st.executeQuery("select count(*) from fullTimeWorkers where id="+key);
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from fullTimeWorkers where id="+key);
							rs.next();
							System.out.print("Enter the first name of the worker (enter -1 to keep it the same):");
							firstName=s.nextLine();
							if(!firstName.equals("-1"))
								st.executeUpdate("update fullTimeWorkers set firstName='"+firstName+"' where id="+key);
							System.out.print("Enter the last name of the worker (enter -1 to keep it the same):");
							lastName=s.nextLine();
							if(!lastName.equals("-1"))
								st.executeUpdate("update fullTimeWorkers set lastName='"+lastName+"' where id="+key);
							System.out.print("Enter the salary of the worker (enter -1 to keep it the same):");
							salary=s.nextLine();
							if(Integer.parseInt(salary)<-1)
								throw new Exception();
							else if(Integer.parseInt(salary)>-1)
								st.executeUpdate("update fullTimeWorkers set salary="+Integer.parseInt(salary)+" where id="+key);
							System.out.println("Update was completed successfully.");
							break;
						}
						rs=st.executeQuery("select count(*) from shiftWorkers where id="+key);
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from shiftWorkers where id="+key);
							rs.next();
							System.out.print("Enter the first name of the worker (enter -1 to keep it the same):");
							firstName=s.nextLine();
							if(!firstName.equals("-1"))
								st.executeUpdate("update shiftWorkers set firstName='"+firstName+"' where id="+key);
							System.out.print("Enter the last name of the worker (enter -1 to keep it the same):");
							lastName=s.nextLine();
							if(!lastName.equals("-1"))
								st.executeUpdate("update shiftWorkers set lastName='"+lastName+"' where id="+key);
							System.out.print("Enter the hours the worker worked this month (enter -1 to keep it the same):");
							hWorked=s.nextLine();
							if(Integer.parseInt(hWorked)<-1)
								throw new Exception();
							else if(Integer.parseInt(hWorked)>-1)
								st.executeUpdate("update shiftWorkers set hoursWorked="+Integer.parseInt(hWorked)+" where id="+key);
							System.out.print("Enter the hourly wage of the worker (enter -1 to keep it the same):");
							hWage=s.nextLine();
							if(Integer.parseInt(hWage)<-1)
								throw new Exception();
							else if(Integer.parseInt(hWage)>-1)
								st.executeUpdate("update shiftWorkers set hourlyWage="+Integer.parseInt(hWage)+" where id="+key);
							System.out.println("Update was completed successfully.");
							break;
						}
						rs=st.executeQuery("select count(*) from volunteers where id="+key);
						rs.next();
						if(rs.getInt("count(*)")>0)
						{
							rs=st.executeQuery("select * from volunteers where id="+key);
							rs.next();
							System.out.print("Enter the first name of the worker (enter -1 to keep it the same):");
							firstName=s.nextLine();
							if(!firstName.equals("-1"))
								st.executeUpdate("update volunteers set firstName='"+firstName+"' where id="+key);
							System.out.print("Enter the last name of the worker (enter -1 to keep it the same):");
							lastName=s.nextLine();
							if(!lastName.equals("-1"))
								st.executeUpdate("update volunteers set lastName='"+lastName+"' where id="+key);
							System.out.println("Update was completed successfully.");
							break;
						}
						System.out.println("ID was not found.");
						break;
					case '5':
						int ftw,sw,v,ftws,sws=0;
						rs=st.executeQuery("select count(*) from fullTimeWorkers");
						rs.next();
						ftw=rs.getInt("count(*)");
						System.out.println("Total full-time workers: "+ftw);
						rs=st.executeQuery("select count(*) from shiftWorkers");
						rs.next();
						sw=rs.getInt("count(*)");
						System.out.println("Total shift workers: "+sw);
						rs=st.executeQuery("select count(*) from volunteers");
						rs.next();
						v=rs.getInt("count(*)");
						System.out.println("Total volunteers: "+v);
						System.out.println("Total workers: "+(ftw+sw+v));
						rs=st.executeQuery("select sum(salary) from fullTimeWorkers");
						rs.next();
						ftws=rs.getInt("sum(salary)");
						rs=st.executeQuery("select * from shiftworkers");
						while(rs.next())
						{
							sws+=rs.getInt("hoursWorked")*rs.getInt("hourlyWage");
						}
						System.out.println("Total salary for this month: $"+(ftws+sws));
						System.out.println("Average salary for a worker for this month: $"+((ftws+sws)/(ftw+sw+v)));
						break;
					case '8':
						quit=true;
						break;
					default:
						//System.out.println("Illegal case");
						throw new Exception();
					}
				}
				catch(Exception e)
				{
					System.out.println("Illegal input");
				}
			}
			
			
			
			st1.close();
			st2.close();
			st3.close();
			con.close();
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}

	}

}
