package org.bpeclipse.api.bpobjects;

import org.jdom.Element;

public class BPItem implements IBPObject {
    
    private String id;
    private boolean completed;
    private String text;

    public void loadFromXml(Element node) throws Exception {
        
        if (!node.getName().equals("item")) {
            throw new Exception("BPItem node not 'item'");
        }
        
        this.id = node.getAttribute("id").getValue();
        this.completed = Boolean.valueOf(node.getAttribute("completed").getValue()).booleanValue();
        this.text = node.getText();

    }

    public boolean isCompleted() {
        return completed;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

}
