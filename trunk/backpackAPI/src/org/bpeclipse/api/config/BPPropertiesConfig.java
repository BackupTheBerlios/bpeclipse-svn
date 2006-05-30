package org.bpeclipse.api.config;

import java.util.Properties;

public class BPPropertiesConfig implements IBPConfig {
    
    private Properties props;

    public BPPropertiesConfig(Properties props) {
        this.props = props;
    }

    public String getUserName() {
        return props.getProperty("username");
    }

    public String getToken() {
        return props.getProperty("token");
    }

}
