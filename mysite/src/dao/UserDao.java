package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.UserDto;

public class UserDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDao(){
		try{
			String dbURL= "jdbc:oracle:thin:@127.0.0.1:1521:xe";
			String dbID= "leafbox";
			String dbPassword= "leafbox";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn= DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword){
		
		String SQL= "SELECT userPassword FROM users WHERE userId= ?";
		
		try{
			pstmt= conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				if(rs.getString(1).equals(userPassword)){
					return 1; //�α��� ����
				}else{
					return 0; //��й�ȣ ����ġ
				}
			}
			return -1; //���̵� ����
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return -2; //�����ͺ��̽� ����
	}
	
	public int join(UserDto userDto){
		String SQL= "INSERT INTO USERS VALUES (?, ?, ?, ?, ?)";
		try{
			pstmt= conn.prepareStatement(SQL);
			pstmt.setString(1, userDto.getUserID());
			pstmt.setString(2, userDto.getUserPassword());
			pstmt.setString(3, userDto.getUserName());
			pstmt.setString(4, userDto.getUserGender());
			pstmt.setString(5, userDto.getUserEmail());
			return pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
}

// oracle.jdbc.driver.OracleDriver
// jdbc:oracle:thin:@127.0.0.1:1521:xe