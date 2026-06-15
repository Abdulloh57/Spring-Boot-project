package library.uz.springbootwithjpa.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import library.uz.springbootwithjpa.model.Admin;


import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        String path = request.getRequestURI();
        System.out.println(path);

        if (path.equals("admin/login") || path.equals("/")
        || path.equals("/admin/register")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        if (admin == null){
            request.getRequestDispatcher("/admin/login").forward(servletRequest,servletResponse);
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
