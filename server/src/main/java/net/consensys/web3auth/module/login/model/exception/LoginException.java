package net.consensys.web3auth.module.login.model.exception;

public class LoginException extends Exception {

    private static final long serialVersionUID = 6532373444945616657L;

    private final String appId;
    private final String clientId;
    private final String redirectUri;
    
    public LoginException(String appId, String clientId, String redirectUri, String message) {
        super(message);
        this.appId = appId;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }
    
    public LoginException(String appId, String clientId, String redirectUri, Throwable t) {
        super(t);
        this.appId = appId;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public String getAppId() {
        return appId;
    }
    public String getClientId() {
        return clientId;
    }
    public String getRedirectUri() {
        return redirectUri;
    }
}
