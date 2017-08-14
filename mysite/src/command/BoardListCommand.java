package command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;
import dto.BbsDto;

public class BoardListCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//���� ������ ����, ������ ù �������� ����
		int pageNumber= 1;
		if (request.getParameter("pageNumber") != null){
			pageNumber= Integer.parseInt(request.getParameter("pageNumber"));
		}
		request.setAttribute("pageNumber", pageNumber);
		
		//�����ͺ��̽����� �Խ��� �� ����Ʈ�� �޾ƿ�
		BbsDao bbsDao= new BbsDao();
		ArrayList<BbsDto> list= bbsDao.getList(pageNumber);
		request.setAttribute("list", list);
		
		//������ ���� ����� ���� ���� �������� �ִ��� Ȯ��
		boolean isNextPage= bbsDao.nextPage(pageNumber+1);
		request.setAttribute("isNextPage", isNextPage);
		
		
		return "/board/bbs.jsp";

	} //execute method end
} //Class End
