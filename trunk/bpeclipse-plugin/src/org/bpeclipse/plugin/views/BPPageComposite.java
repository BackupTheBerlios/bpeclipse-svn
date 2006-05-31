package org.bpeclipse.plugin.views;

import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.plugin.BPEclipsePlugin;
import org.bpeclipse.plugin.ColorUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class BPPageComposite extends Composite {

    private BPPage page;
    private Text bodyText;

    public BPPageComposite(Composite parent, int style) {
        super(parent, style);
        
        setBackground(ColorUtils.COLOR_WHITE);

        GridLayout layout = new GridLayout();
        setLayout(layout);
        
        createHeadingLabel("Body");
        
        bodyText = new Text(this, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        
        GridData gridData = new GridData(500, 300);
        bodyText.setLayoutData(gridData);
        
        createHeadingLabel("Lists");
        
    }

        

    private void createHeadingLabel(String labelText) {
        
        Label label = new Label(this, SWT.SINGLE | SWT.BORDER);
        label.setText(labelText);
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.LEFT;
        label.setLayoutData(gridData);
        
        Font font = label.getFont();
        FontData fd = font.getFontData()[0];
        
        fd.setStyle(SWT.BOLD);
        Font newFont = new Font(BPEclipsePlugin.getDefault().getDisplay(), fd);
        label.setFont(newFont);
        label.setBackground(ColorUtils.COLOR_WHITE);
        
    }

    public void setPage(BPPage page) {
        this.page = page;
        
        bodyText.setText(page.getDescription());
        
    }


}
