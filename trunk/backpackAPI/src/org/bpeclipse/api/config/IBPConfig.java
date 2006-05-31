package org.bpeclipse.api.config;

public interface IBPConfig {
    
    public static final String CFG_USERNAME = "bpconfig.username";
    public static final String CFG_TOKEN = "bpconfig.token";
    
    public String getUserName();
    
    public String getToken();

}
