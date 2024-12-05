package controller.user;

import com.google.gson.Gson;
import dao.HomeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SearchByPrice
 */
@WebServlet("/searchByPrice")
public class SearchByPrice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchByPrice() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String value = request.getParameter("value");
		
		PrintWriter printWriter = response.getWriter();
		Gson gson = new Gson();
		HomeDAO homeDAO = new HomeDAO();
		if(value.equals("1")) {
			printWriter.print(gson.toJson(homeDAO.getDuoi2Tr()));
		} else if(value.equals("2")) {
			printWriter.print(gson.toJson(homeDAO.get2Trden4Tr()));
		} else if(value.equals("3")) {
			printWriter.print(gson.toJson(homeDAO.get4Trden7Tr()));
		} else if(value.equals("4")) {
			printWriter.print(gson.toJson(homeDAO.get7Trden13Tr()));
		} else if(value.equals("5")) {
			printWriter.print(gson.toJson(homeDAO.getTren13Tr()));
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
