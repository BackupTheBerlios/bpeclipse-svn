package org.bpeclipse.plugin.views;

import java.util.ArrayList;
import java.util.Collection;

import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.plugin.core.BPPageMgr;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class BPPageListView extends ViewPart {
    
    private class BPPageListLabelProvider extends LabelProvider implements IBaseLabelProvider {

        public String getText(Object element) {
            return BPPageMgr.getInstance().getPageTitle((String)element);
        }

    }

    private class BPPageListContentProvider implements IStructuredContentProvider {

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        public Object[] getElements(Object inputElement) {
            return new ArrayList((Collection)inputElement).toArray(new String[0]);
        }

    }

    public static final String VIEW_ID = "org.bpeclipse.plugin.views.BPPageListView";
    
    private ListViewer viewer;

    public void createPartControl(Composite parent) {
        
        viewer = new ListViewer(parent, SWT.SINGLE);
        
        viewer.setContentProvider(new BPPageListContentProvider());
        viewer.setLabelProvider(new BPPageListLabelProvider());
        viewer.setInput(BPPageMgr.getInstance().getPageIDs());
        viewer.addDoubleClickListener(new IDoubleClickListener() {

            public void doubleClick(DoubleClickEvent event) {
                
                IStructuredSelection selection 
                    = (IStructuredSelection) event.getSelection();
                
                String selectedPageID = (String)selection.getFirstElement();
                BPPage selectedPage = BPPageMgr.getInstance().getPageByID(selectedPageID);
                
                try {
                    
                    IViewPart view = 
                        PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow()
                        .getActivePage()
                        .showView(BPPageView.VIEW_ID, selectedPageID, IWorkbenchPage.VIEW_ACTIVATE);
                    
                } catch (PartInitException e) {
                    // TODO: logging
                    e.printStackTrace();
                }
                
            }
        });
    }

    public void setFocus() {

    }

}
