package org.bpeclipse.api.config;

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
    
    private BPConfigMgr() {
    }

    public String getToken() {
        if (config == null) return null;
        
        return config.getToken();
    }
    
    public String getUserName() {
        if (config == null) return null;
        
        return config.getUserName();
    }
    
    public void setConfig(IBPConfig config) {
        this.config = config;
    }

}
