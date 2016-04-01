package ru.lb.fntestdojo.server;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.util.UserContext;
import org.eclipse.jetty.jaas.JAASRole;
import org.eclipse.jetty.jaas.callback.ObjectCallback;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import java.io.IOException;
import java.util.Map;

/**
 * Created by mihard on 18.03.2016.
 */
public class LoginJAAS implements javax.security.auth.spi.LoginModule {
    public static class ULCredentials{
        private String user;
        private String password;

        public ULCredentials(String name, String s) {
            this.user = name;
            this.password = s;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private Subject subject;
    private CallbackHandler handler;
    private Map<String, ?> sharedstate;
    private Map<String, ?> options;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.handler = callbackHandler;
        this.sharedstate = sharedState;
        this.options = options;
    }

    @Override
    public boolean login() throws IllegalArgumentException {
        Callback[] clb = new Callback[]{
                new NameCallback("Login"),
                new PasswordCallback("PWD", false),
                new ObjectCallback()
        };

        try {
            handler.handle(clb);
        } catch (IOException | UnsupportedCallbackException e) {
        }

        String login = ((NameCallback) clb[0]).getName();
        String password = ((ObjectCallback) clb[2]).getObject().toString();

        if (login == null || password == null) {
            throw new IllegalArgumentException("логин или пароль = null: login=" + login + " password=" + password);
        }
        if (login.equals("") || password.equals("")){
            throw new IllegalArgumentException("логин или пароль пустые: login=" + login + " password=" + password);
        }

        Connection connection = Factory.Connection.getConnection(FilenetProperties.URI);
        Subject fnSubject = UserContext.createSubject(connection, login, password, "FileNetP8WSI");
        UserContext userContext = UserContext.get();
        userContext.pushSubject(fnSubject);
        Domain domain = Factory.Domain.fetchInstance(connection, FilenetProperties.DOMAIN_NAME, null);
        if(domain == null){
            return false;
        }
        else {
            subject.getPrivateCredentials().add(new ULCredentials(login, password));
            return true;
        }
    }

    @Override
    public boolean commit() {
        if (subject != null) {
            subject.getPrincipals().add(new JAASRole("All"));
        }
        return true;
    }

    @Override
    public boolean abort(){
        return true;
    }

    @Override
    public boolean logout(){
        return true;
    }
}