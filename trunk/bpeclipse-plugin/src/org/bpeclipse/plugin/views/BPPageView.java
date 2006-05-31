package org.bpeclipse.plugin.views;


import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.plugin.BPEclipseColorUtils;
import org.bpeclipse.plugin.core.BPPageMgr;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
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
    
    private BPPage page;

    private BPPageComposite comp;

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

	    // the page ID is the secondary part of the view ID
        if (page == null) {
            String pageID = getViewSite().getSecondaryId();
            page = BPPageMgr.getInstance().getPageByID(pageID);
	    }
        
        comp = new BPPageComposite(parent, SWT.NONE, page);
        
        ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL);
        sc.setBackground(BPEclipseColorUtils.COLOR_WHITE);
        sc.setContent(comp);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);
        sc.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        
        setPartName(page.getTitle());
	}

    public void setFocus() {
    }

}


	


