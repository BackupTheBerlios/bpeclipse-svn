package org.bpeclipse.plugin;

import org.bpeclipse.api.config.BPConfigMgr;
import org.bpeclipse.plugin.core.BPPageMgr;
import org.bpeclipse.plugin.preferences.BPPluginConfig;
import org.eclipse.ui.plugin.*;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class BPEclipsePlugin extends AbstractUIPlugin {

	//The shared instance.
	private static BPEclipsePlugin plugin;
	
	/**
	 * The constructor.
	 */
	public BPEclipsePlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
        
        // initialize the configuration of the API library
        BPConfigMgr.getInstance().setConfig(BPPluginConfig.getInstance());
        
        // first, we initialize the pages in memory
        try {
            BPPageMgr.getInstance().initialize();
        } catch (BPPluginException e) {
            
            // TODO: logging
            BPEclipsePlugin.getDefault().getLog().log(
                    new Status(IStatus.ERROR, "bpeclipse", IStatus.OK, "Could not initialize Page manager", e));
        }
        
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static BPEclipsePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("bpeclipse_plugin", path);
	}
}
