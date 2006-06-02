package org.bpeclipse.plugin.views;


import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.plugin.BPEclipseColorUtils;
import org.bpeclipse.plugin.BPEclipsePlugin;
import org.bpeclipse.plugin.core.BPPageMgr;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
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
    
    private BPPage page;

    private BPPageComposite comp;

    private Label waitLabel;

    private StackLayout stackLayout;
    
    private Composite parent;

    private ScrolledComposite sc;

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
        
        this.parent = parent;
        
//        stackLayout = new StackLayout();
//        parent.setLayout(stackLayout);

	    // the page ID is the secondary part of the view ID
        if (page == null) {
            
            final String pageID = getViewSite().getSecondaryId();
            String pageName = BPPageMgr.getInstance().getPageTitle(pageID);

            Job j = new Job("Loading page " + pageName) {

                protected IStatus run(IProgressMonitor monitor) {
                    page = BPPageMgr.getInstance().getPageByID(pageID);
                    return Status.OK_STATUS;
                }
            };
            
            j.schedule();
            
            j.addJobChangeListener(new JobChangeAdapter() {

                public void done(IJobChangeEvent event) {
                    BPEclipsePlugin.getDefault().getDisplay().asyncExec(
                    new Runnable() {
                        public void run() {
                            BPPageView.this.pageDone();
                        }
                    });
                }
                
            });

            waitLabel = new Label(parent, SWT.BORDER);
            waitLabel.setText("Please wait...");
            waitLabel.setBackground(BPEclipseColorUtils.COLOR_WHITE);
            
	    }
        
        comp = new BPPageComposite(parent, SWT.BORDER);
        
        sc = new ScrolledComposite(parent, SWT.V_SCROLL);
        sc.setBackground(BPEclipseColorUtils.COLOR_WHITE);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);
        sc.setAlwaysShowScrollBars(true);
        
        sc.setVisible(false);
        sc.moveBelow(null);
        
        setPartName("Loading...");
        
//        stackLayout.topControl = waitLabel;
//        parent.layout();
        
	}

    private void pageDone() {
        
        if (page != null) {
            
            waitLabel.setVisible(false);
            waitLabel.moveBelow(null);
            sc.setVisible(true);
            
            setPartName(page.getTitle());
            comp.setPage(page);
            
//            stackLayout.topControl = sc;
//            parent.layout();

            sc.setContent(comp);
            sc.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
            comp.layout();
            
        } else {
            // TODO: handle error
        }
        
    }

    public void setFocus() {
    }

}


	


