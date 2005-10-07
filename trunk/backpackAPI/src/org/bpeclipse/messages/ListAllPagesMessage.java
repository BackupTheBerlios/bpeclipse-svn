package org.bpeclipse.messages;


import org.bpeclipse.bpobjects.BPPageList;
import org.bpeclipse.bpobjects.IBPObject;
import org.jdom.Element;

public class ListAllPagesMessage extends AbstractBPMessage {
    
    private IBPObject pageList;

    protected String getRequestURL() {
        return "/pages/all";
    }

    protected void createRequestBody(Element root) {
        // do nothing
        return;
    }

    public IBPObject getResponseObject() {
        return pageList;
    }

    protected boolean parseObject(Element root) {
        if (!root.getName().equals("pages")) {
            logger.error("Wrong type of response for ListAllPagesMessage: " + root.getName());
        }
        
        // TODO: use a factory?
        pageList = new BPPageList();
        try {
            pageList.loadFromXml(root);
        } catch (Exception e) {
            logger.error("Error parsing page list: ", e);
            return false;
        }
        
        return true;
    }

}
