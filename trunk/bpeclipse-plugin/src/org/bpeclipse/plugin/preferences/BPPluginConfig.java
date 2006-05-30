package org.bpeclipse.plugin.preferences;

import org.bpeclipse.api.config.IBPConfig;
import org.bpeclipse.plugin.BPEclipsePlugin;
import org.eclipse.jface.preference.IPreferenceStore;

public class BPPluginConfig implements IBPConfig {
    
    private IPreferenceStore store;
    
    private static BPPluginConfig instance;
    
    public static BPPluginConfig getInstance() {
        if (instance == null) {
            instance = new BPPluginConfig();
        }
        
        return instance;
    }

    private BPPluginConfig() {
        store = BPEclipsePlugin.getDefault().getPreferenceStore();
    }

    public String getUserName() {
        return store.getString(IBPConfig.CFG_USERNAME);
    }

    public String getToken() {
        return store.getString(IBPConfig.CFG_TOKEN);
    }

}
