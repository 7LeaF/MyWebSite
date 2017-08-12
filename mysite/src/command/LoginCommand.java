package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;

public class LoginCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session= request.getSession();
		
		//�α��εǾ� �ִ��� üũ
		if(session.getAttribute("userID")!= null){
			
			//�̷̹α��� �Ǿ� �ִ� ��� ���� ���, �̹� �α��εǾ� �ִ°�� �α������� ������ �� �ִ� �޴��� �����.
			return "/error/accessError.jsp";	
		}
		
		String userID= request.getParameter("userID");
		String userPassword= request.getParameter("userPassword");
		
		UserDao userDao= new UserDao();
		int result= userDao.login(userID, userPassword);
		
		//�α��� ����
		if(result== 1){
			session.setAttribute("userID", userID);
			return "main.jsp";
		
		//�н����� ����ġ
		}else if(result== 0){
			request.setAttribute("errorType", "invalidPassword");
			return "/error/loginError.jsp";
		
		//���̵� ����
		}else if(result== -1){
			request.setAttribute("errorType", "invalidUserID");
			return "/error/loginError.jsp";
		
		//�����ͺ��̽� ����
		}else if(result== -2){
			request.setAttribute("errorType", "dbError");
			return "/error/loginError.jsp";
			
		}
		return null;
	} //execute method end
} //Class end
