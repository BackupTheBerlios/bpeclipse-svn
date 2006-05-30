package org.bpeclipse.api.messages;


import org.bpeclipse.api.BPException;
import org.bpeclipse.api.bpobjects.BPPageList;
import org.bpeclipse.api.bpobjects.IBPObject;
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

    protected void parseObject(Element root) throws BPException {
        if (!root.getName().equals("pages")) {
            logger.error("Wrong type of response for ListAllPagesMessage: " + root.getName());
        }
        
        // TODO: use a factory?
        pageList = new BPPageList();
        try {
            pageList.loadFromXml(root);
        } catch (Exception e) {
            String errMsg = "Error parsing page list: ";
            logger.error(errMsg, e);
            throw new BPException(errMsg, e);        
        }
        
    }

}
