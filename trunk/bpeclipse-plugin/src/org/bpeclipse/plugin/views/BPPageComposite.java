package org.bpeclipse.plugin.views;

import java.util.Iterator;

import org.bpeclipse.api.bpobjects.BPItem;
import org.bpeclipse.api.bpobjects.BPItemList;
import org.bpeclipse.api.bpobjects.BPPage;
import org.bpeclipse.plugin.BPEclipsePlugin;
import org.bpeclipse.plugin.BPEclipseColorUtils;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class BPPageComposite extends Composite {

    private BPPage page;
    private Text bodyText;
    private Table todoList;

    public BPPageComposite(Composite parent, int style) {
        super(parent, style);
        
        setBackground(BPEclipseColorUtils.COLOR_WHITE);

        GridLayout layout = new GridLayout();
        layout.verticalSpacing = 15;
        setLayout(layout);
        
        createHeadingLabel("Body");
        
        bodyText = new Text(this, SWT.MULTI);
        
        
        createHeadingLabel("Lists");
        
        todoList = new Table(this, SWT.CHECK | SWT.SINGLE);

        CheckboxTableViewer todoListViewer = new CheckboxTableViewer(todoList);
        
    }

    private void createHeadingLabel(String labelText) {
        
        Label label = new Label(this, SWT.SINGLE);
        label.setText(labelText);
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.LEFT;
        label.setLayoutData(gridData);
        
        Font font = label.getFont();
        FontData fd = font.getFontData()[0];
        
        fd.setStyle(SWT.BOLD);
        Font newFont = new Font(BPEclipsePlugin.getDefault().getDisplay(), fd);
        label.setFont(newFont);
        label.setBackground(BPEclipseColorUtils.COLOR_WHITE);
        
    }

    public void setPage(BPPage page) {
        this.page = page;
        
        bodyText.setText(page.getDescription());
        
        Point p = bodyText.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        bodyText.setLayoutData(new GridData(p.x, p.y));
        
        BPItemList items = page.getListItems();
        for (Iterator it = items.getItemIDs().iterator(); it.hasNext(); ) {
            String itemID = (String)it.next();
            BPItem item = items.getItem(itemID);
            
            TableItem tableItem = new TableItem(todoList, SWT.NONE);
            tableItem.setChecked(item.isCompleted());
            tableItem.setText(item.getText());
        }
        
        p = todoList.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        todoList.setLayoutData(new GridData(p.x, p.y));
        
    }


}
