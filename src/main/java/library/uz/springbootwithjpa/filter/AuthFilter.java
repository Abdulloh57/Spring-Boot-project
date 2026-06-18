package library.uz.springbootwithjpa.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import library.uz.springbootwithjpa.model.Admin;


import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String path = request.getRequestURI();

        if (path.equals("/")
                || path.equals("/admin/login")
                || path.equals("/admin/register")) {

            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession(false);

        Admin admin = null;

        if (session != null) {
            admin = (Admin) session.getAttribute("admin");
        }

        if (admin == null) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Please login first");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
