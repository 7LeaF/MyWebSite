package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BbsDao;
import dto.BbsDto;

public class BoardWriteActionCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session= request.getSession();
		
		//userID�� �α��� �Ǿ��� ��� �����Ǵ� ���� �Ӽ� ��
		String userID= null;
		if(session.getAttribute("userID")!= null){
			userID= (String) session.getAttribute("userID");
		}
		
		//�α����� �Ǿ����� ���� ��� �� ���� �Ұ�
		if(userID== null){
			request.setAttribute("errorType", "isNotLogin");
			return "/error/boardWriteError.jsp";
			
		}else{
			//�α����� �Ǿ� �ִ� ���
			BbsDto bbsDto= new BbsDto();
			bbsDto.setBbsTitle(request.getParameter("bbsTitle"));
			bbsDto.setBbsContent(request.getParameter("bbsContent"));
			
			//����, ���� �� �� �ϳ��� ����ִ��� üũ
			if(bbsDto.getBbsTitle()== null || bbsDto.getBbsContent()== null){
				request.setAttribute("errorType", "isNull");
				return "/error/boardWriteError.jsp";
				
			}else{
				//����, ���� ��� �ۼ��Ǿ��ٸ� �����ͺ��̽��� �� ���
				BbsDao bbsDao= new BbsDao();
				int result= bbsDao.write(bbsDto.getBbsTitle(), userID, bbsDto.getBbsContent());
				
				if (result == -1){
					//�����ͺ��̽��� ������ �Է� ���� ���� ���� �߻��� ó��
					request.setAttribute("errorType", "writeError");
					return "/error/boardWriteError.jsp";
					
				}else {
					//�� ���� ���
					return "bbs.do";
					
				}
			}	
		}
	} //execute method end
} //Class end
