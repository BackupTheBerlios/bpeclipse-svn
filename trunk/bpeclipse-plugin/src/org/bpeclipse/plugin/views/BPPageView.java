package org.bpeclipse.plugin.views;


import org.bpeclipse.api.bpobjects.BPPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class BPPageView extends ViewPart {
    
    public static final String VIEW_ID = "org.bpeclipse.plugin.views.BPPageView";
    private Label label;

	/**
	 * The constructor.
	 */
	public BPPageView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
        
        label = new Label(parent, SWT.LEFT);
        label.setText("It's a page!");
        
	}

    public void setFocus() {
        // TODO Auto-generated method stub
        
    }

    public void setPage(BPPage selectedPage) {
        
        label.setText("I opened a page: " + selectedPage.getTitle());
        setPartName(selectedPage.getTitle());
        setContentDescription(selectedPage.getDescription());
        
    }

}


	


