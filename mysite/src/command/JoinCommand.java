package command;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import dto.UserDto;

public class JoinCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session= request.getSession();
	
		//�α��εǾ� �ִ��� üũ
		if(session.getAttribute("userID")!= null){

			//�̷̹α��� �Ǿ� �ִ� ��� ���� ���, �̹� �α��εǾ� �ִ°�� ȸ���������� ������ �� �ִ� �޴��� �����.
			return "/error/accessError.jsp";
		}
		
		//ȸ������ ������ �޾ƿ� �����͸� DTO Ŭ������ ����
		UserDto userDto= new UserDto();
		
		userDto.setUserID(request.getParameter("userID"));
		userDto.setUserPassword(request.getParameter("userPassword"));
		userDto.setUserName(request.getParameter("userName"));
		userDto.setUserGender(request.getParameter("userGender"));
		userDto.setUserEmail(request.getParameter("userEmail"));

		//�Է� ���� ���� ���� �ִ��� üũ
		if(userDto.getUserID().equals("") || userDto.getUserPassword().equals("") || userDto.getUserName().equals("") 
				|| userDto.getUserGender().equals("") || userDto.getUserEmail().equals("")){
			
			request.setAttribute("errorType", "isNull");
			return "/error/joinError.jsp";
		}else{
			UserDao userDao= new UserDao();
			int result= userDao.join(userDto);
			
			//���̵� �ߺ� üũ
			if(result== -1){
				request.setAttribute("errorType", "isMember");
				return "/error/joinError.jsp";
				
			}else{
				session.setAttribute("userID", userDto.getUserID());
				return "main.jsp";
				
			}
		}
	} //execute method end
} //Class end
