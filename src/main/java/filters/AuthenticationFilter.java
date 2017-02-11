package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        HttpSession session = request.getSession(false);

        if (session != null) {
            if (session.getAttribute("user") != null && (uri.endsWith("signIn.jsp") || uri.endsWith("signUp.jsp")))
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}