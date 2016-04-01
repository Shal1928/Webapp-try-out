package ru.lb.fntestdojo.server;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Factory;
import com.filenet.api.util.UserContext;
import org.eclipse.jetty.jaas.JAASUserPrincipal;

import javax.security.auth.Subject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.security.PrivilegedExceptionAction;

/**
 * Created by mihard on 18.03.2016.
 */
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private static LoginJAAS.ULCredentials getUserPrincipal(Principal principal) {
        if (principal != null) {
            LoginJAAS.ULCredentials result = null;
                for (Object obj : ((JAASUserPrincipal) principal).getSubject().getPrivateCredentials()) {
                    if (obj instanceof LoginJAAS.ULCredentials) {
                        result = (LoginJAAS.ULCredentials) obj;
                        break;
                    }
                }
            return result;
        } else {
            return null;
        }
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {
            if (servletRequest instanceof HttpServletRequest) {
                LoginJAAS.ULCredentials ulCredentials = getUserPrincipal(((HttpServletRequest) servletRequest).getUserPrincipal());
                Connection connection = Factory.Connection.getConnection(FilenetProperties.URI);
                Subject subject = UserContext.createSubject(connection, ulCredentials.getUser(), ulCredentials.getPassword(), "FileNetP8WSI");
                UserContext.doAs(subject, new PrivilegedExceptionAction<Object>() {
                    @Override
                    public Object run() throws Exception {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return null;
                    }
                });

            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

    }

    @Override
    public void destroy() {
    }
}
