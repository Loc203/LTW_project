package controller.admin;

import model.Capacity;
import model.Manufacturer;
import service.CapacityService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/manage_capacity"})
public class ManageCapacityController extends HttpServlet {
    @Inject
    private CapacityService capacityService;

    String action;


    int limit = 1000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("capacities", capacityService.getAllCapacities());
        request.getRequestDispatcher("/viewAdmin/manage_capacity.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action = request.getParameter("action");
        boolean isSuccessful;
        if (action != null) {
            switch (action) {
                case "search":
                    String keyword = request.getParameter("keyword");
                    List<Capacity> results = capacityService.findCapacityWithKeyWord(keyword);
                    if (results.isEmpty()) {
                        request.getSession().setAttribute("status", false);
                        request.getSession().setAttribute("message", "Không tìm thấy kết quả !");
                    }
                    request.setAttribute("capacities", results);
                    request.getRequestDispatcher("/viewAdmin/manage_capacity.jsp").forward(request, response);
                    break;
                case "add":
                    String name = request.getParameter("name_capacity");
                    int row = capacityService.findCapacityByName(name);
                    if (row == 1) {
                        request.getSession().setAttribute("status", false);
                        request.getSession().setAttribute("message", "Tên dung lượng đã tồn tại !");
                        response.sendRedirect(request.getContextPath() + "/admin/manage_capacity");
                        break;
                    }
                    isSuccessful = capacityService.insertCapacity(name);
                    if (isSuccessful) {
                        request.getSession().setAttribute("status", true);
                        request.getSession().setAttribute("message", "Thêm thông tin thành công !");
                    } else {
                        request.getSession().setAttribute("status", false);
                        request.getSession().setAttribute("message", "Thêm thông tin thất bại !");
                    }
                    response.sendRedirect(request.getContextPath() + "/admin/manage_capacity");
                    break;
                case "showEdit":
                    int idCapacityEdit = Integer.parseInt(request.getParameter("id"));
                    Capacity capacity = capacityService.findCapacityById(idCapacityEdit);
                    request.setAttribute("capacity", capacity);
                    request.getRequestDispatcher("/viewAdmin/manage_capacity.jsp").forward(request, response);
                    break;
                case "edit":
                    int idCapacity = Integer.parseInt(request.getParameter("id"));
                    String nameCapacity = request.getParameter("name");
                    isSuccessful = capacityService.alertCapacity(idCapacity, nameCapacity);
                    if (isSuccessful) {
                        request.getSession().setAttribute("status", true);
                        request.getSession().setAttribute("message", "Sửa thành công !");
                    } else {
                        request.getSession().setAttribute("status", false);
                        request.getSession().setAttribute("message", "Sửa thất bại !");
                    }
                    response.sendRedirect(request.getContextPath() + "/admin/manage_capacity");
                    break;
                case "delete":
                    int id = Integer.parseInt(request.getParameter("id"));
                    try {
                        isSuccessful = capacityService.deleteCapacity(id);
                    } catch (Exception e) {
                        isSuccessful = false;
                    }
                    if (isSuccessful) {
                        request.getSession().setAttribute("status", true);
                        request.getSession().setAttribute("message", "Xóa thành công!");
                    } else {
                        request.getSession().setAttribute("status", false);
                        request.getSession().setAttribute("message", "Bạn cần xóa các thông tin có sử dụng tới dung lượng này !");
                    }
                    response.sendRedirect(request.getContextPath() + "/admin/manage_capacity");
                    break;
            }
        } else {
            request.getRequestDispatcher("/viewAdmin/manage_capacity.jsp").forward(request, response);
        }
    }
}
