package org.bpeclipse.config;

/**
 * @author achoi
 *
 */
public class BPConfigMgr {
    
    private IBPConfig config;
    
    private static BPConfigMgr instance = null;
    
    public static BPConfigMgr getInstance() {
        if (instance == null) {
            instance = new BPConfigMgr();
        }
        
        return instance;
    }

    public String getToken() {
        return config.getToken();
    }
    
    public String getUserName() {
        return config.getUserName();
    }
    
    public void setConfig(IBPConfig config) {
        this.config = config;
    }

}
