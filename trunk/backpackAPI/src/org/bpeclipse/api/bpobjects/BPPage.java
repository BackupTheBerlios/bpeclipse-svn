package org.bpeclipse.api.bpobjects;

import org.apache.log4j.Logger;
import org.bpeclipse.api.config.IBPConfig;
import org.jdom.Element;

public class BPPage implements IBPObject {
    
    private static Logger logger = Logger.getLogger(IBPConfig.BP_LOGGER);
    
    private String title;
    private String id;
    private String emailAddress;
    private String description;
    
    private BPItemList listItems;
    private BPNoteList notes;
    
    
    public void loadFromXml(Element node) throws Exception {
        
        if (!node.getName().equals("page")) {
            throw new Exception("BPPage XML root is not 'page'");
        }
        
        // we will throw a NullPointerException if any of this goes wrong
        this.title = node.getAttribute("title").getValue();
        this.id = node.getAttribute("id").getValue();
        this.emailAddress = node.getAttribute("email_address").getValue();
        
        this.description = node.getChild("description").getText();
        
        if (node.getChild("items") != null) {
            this.listItems = new BPItemList();
            this.listItems.loadFromXml(node.getChild("items"));
        }
        
        if (node.getChild("notes") != null) {
            this.notes = new BPNoteList();
            this.notes.loadFromXml(node.getChild("notes"));
        }
        
    }


    public String getDescription() {
        return description;
    }


    public String getEmailAddress() {
        return emailAddress;
    }


    public String getId() {
        return id;
    }


    public BPItemList getListItems() {
        return listItems;
    }


    public BPNoteList getNotes() {
        return notes;
    }


    public String getTitle() {
        return title;
    }

}
