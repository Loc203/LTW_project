package controller.admin;

import com.google.gson.Gson;
import dao.ContactsDAO;
import dao.HomeDAO;
import model.Contacts;
import org.jdbi.v3.core.Jdbi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@WebServlet(urlPatterns = {"/admin/contacts/manage_contacts"})
public class ManagerContact extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            doGet_List(req, resp);
        }else if (action.equalsIgnoreCase("delete")) {
            doGet_Remove(req, resp);
        }else if (action.equalsIgnoreCase("update")){
            doGet_Update1(req, resp);
        }
    }

    protected void doGet_List(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ContactsDAO contactsDAO = new ContactsDAO();
        List<Contacts> contactsList = contactsDAO.selectContacts();
        req.setAttribute("listContact", contactsList);
        req.getRequestDispatcher("/viewAdmin/manage_contact.jsp").forward(req, resp);
    }
    protected void doGet_Remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String value = request.getParameter("value");
        int id = Integer.parseInt(value);
        PrintWriter printWriter = response.getWriter();
        Gson gson = new Gson();
        ContactsDAO contactsDAO = new ContactsDAO();
        printWriter.print(gson.toJson(contactsDAO.removeContacts(id)));
    }
    protected void doGet_Update1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String value = request.getParameter("value");
        int id = Integer.parseInt(value);
        PrintWriter printWriter = response.getWriter();
        Gson gson = new Gson();
        ContactsDAO contactsDAO = new ContactsDAO();
        printWriter.print(gson.toJson(contactsDAO.updateAction(id)));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
