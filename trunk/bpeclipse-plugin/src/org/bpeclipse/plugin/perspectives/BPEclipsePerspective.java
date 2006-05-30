package org.bpeclipse.plugin.perspectives;

import org.bpeclipse.plugin.views.BPPageListView;
import org.bpeclipse.plugin.views.BPPageView;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class BPEclipsePerspective implements IPerspectiveFactory {

    public void createInitialLayout(IPageLayout layout) {
        
        String editorArea = layout.getEditorArea();
        
        layout.addView(BPPageListView.VIEW_ID, IPageLayout.LEFT, 0.25f, editorArea);

        IFolderLayout folder = 
            layout.createFolder("pages", IPageLayout.TOP, 0.75f, editorArea);
        
        folder.addPlaceholder(BPPageView.VIEW_ID + ":*");
        
    }

}
