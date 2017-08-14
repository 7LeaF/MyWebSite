package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BbsDao;
import dto.BbsDto;

public class BoardContentViewCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session= request.getSession();
		
		String userID= null;
		if(session.getAttribute("userID")!= null){
			userID= (String) session.getAttribute("userID");
		}
		
		int bbsID= 0;
		if(request.getParameter("bbsID")!= null){
			bbsID= Integer.parseInt(request.getParameter("bbsID"));
		}
		
		BbsDao bbsDao= new BbsDao();
		BbsDto content= new BbsDto();
		
		//�Խñ� ��ȣ �Ķ���� ���� �������� ���� �����ͺ��̽� ���� ����
		if(bbsID <= 0 || bbsDao.getNext() <= bbsID){
			request.setAttribute("errorType", "invalidContent");
			return "/error/boardViewError.jsp";
		}
		
		
		//�Խñ� ���� request ��ü�� ����
		content= bbsDao.getBbs(bbsID);
		request.setAttribute("content", content);
		
		
		// �α����� ����ڰ� �۾������� Ȯ�� (����/���� ��ư enable/disable)
		boolean isWriter= false;
		if(userID != null && userID.equals(content.getUserID())){
			isWriter= true;
		}
		System.out.println(userID +" / "+ content.getUserID() +" / "+ isWriter);;
		
		request.setAttribute("isWriter", isWriter);
		
		
		return "/board/view.jsp";
	
	} //execute method end
} //Class end
