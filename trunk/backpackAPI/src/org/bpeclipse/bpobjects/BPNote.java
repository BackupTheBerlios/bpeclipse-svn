package org.bpeclipse.bpobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Element;

public class BPNote implements IBPObject {
    
    private String id;
    private String title;
    private Date createdDate;
    private String text;

    public void loadFromXml(Element node) throws Exception {

        if (!node.getName().equals("note")) {
            throw new Exception("BPItem node not 'note'");
        }
        
        this.id = node.getAttribute("id").getValue();
        this.title = node.getAttribute("title").getValue();
        
        String dateString = node.getAttribute("created_at").getValue();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        
        this.createdDate = df.parse(dateString);
        
        this.text = node.getText();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

}
