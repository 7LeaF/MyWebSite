package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BbsDao;
import dto.BbsDto;
import util.StrUtil;

public class BoardUpdateActionCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session= request.getSession();
		
		String userID= null;
		int bbsID= 0;
		BbsDao bbsDao= new BbsDao();
		
		if(session.getAttribute("userID")!= null){
			userID= (String) session.getAttribute("userID");
		}
		
		if(request.getParameter("bbsID")!= null){
			bbsID= Integer.parseInt(request.getParameter("bbsID"));
		}
		
			
		//�α��� üũ
		if(userID== null){
			request.setAttribute("errorType", "isNotLogin");
			return "/error/boardUpdateError.jsp";	
		}
		
		//�Խñ� ��ȣ �Ķ���� ���� �������� ���� �����ͺ��̽� ���� ����
		if(bbsID <= 0 || bbsDao.getNext() <= bbsID){
			request.setAttribute("errorType", "invalidContent");
			return "/error/boardUpdateError.jsp";
		}
		
		
		//�� �ۼ��ڰ� �ƴ� ��� �ۼ��� ����
		BbsDto content= bbsDao.getBbs(bbsID);
		
		if(!userID.equals(content.getUserID())){
			request.setAttribute("errorType", "notAuth");
			return "/error/boardUpdateError.jsp";
		}
		
		//�Է� �� �� ����ִ� ���� �ִ� ��� üũ
		if(request.getParameter("bbsTitle")== null || request.getParameter("bbsContent")== null
				|| request.getParameter("bbsTitle").equals("") || request.getParameter("bbsContent").equals("")){
			request.setAttribute("errorType", "isNull");
			return "/error/boardUpdateError.jsp";
			
		}
		
		
		//XSS ��ũ��Ʈ ����
		StrUtil strUtil= new StrUtil();
		
		String bbsTitle= strUtil.cleanXSS(request.getParameter("bbsTitle"));
		String bbsContent= strUtil.cleanXSS(request.getParameter("bbsContent"));
		
		//�� ������Ʈ
		int result= bbsDao.update(bbsID, bbsTitle, bbsContent);
		
		
		if(result== -1){
			request.setAttribute("errorType", "updateFail");
			return "/error/boardUpdateError.jsp";
			
		}else{
			return "bbs.do";	
		
		}
		
	} //execute method end
} //Class end
