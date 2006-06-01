package org.bpeclipse.plugin.preferences;

import org.bpeclipse.api.config.IBPConfig;
import org.bpeclipse.plugin.BPEclipsePlugin;
import org.bpeclipse.plugin.BPPluginException;
import org.bpeclipse.plugin.core.BPPageMgr;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class BPEclipsePreferences
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {


    public BPEclipsePreferences() {
		super(GRID);
		setPreferenceStore(BPEclipsePlugin.getDefault().getPreferenceStore());
		setDescription("Please enter your Backpack user name, and your password token, " +
                "which can be obtained at the Backpack website");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
        
		StringFieldEditor userNameField = 
            new StringFieldEditor(IBPConfig.CFG_USERNAME, "User name", getFieldEditorParent());
        
        StringFieldEditor tokenField = 
            new StringFieldEditor(IBPConfig.CFG_TOKEN, "Token", getFieldEditorParent());
        
        tokenField.getTextControl(getFieldEditorParent()).setEchoChar('*');
        
        addField(userNameField);
        addField(tokenField);
	}
    
	public boolean performOk() {
	    boolean rc = super.performOk();
        
        // try reloading the pages
        
        try {
            BPPageMgr.getInstance().initialize();
        } catch (BPPluginException e) {
            // TODO: handle this
            e.printStackTrace();
        }
        
        return rc;
	}

    public void init(IWorkbench workbench) {
        
    }
	
}