package org.bpeclipse.api.bpobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class BPPageList implements IBPObject {
    
    private List pageIDList = new ArrayList();

    public void loadFromXml(Element node) throws Exception {
        if (!node.getName().equals("pages")) {
            throw new Exception("BPItemList element is not 'items'");
        }
        
        List pages = node.getChildren("page");
        
        for (Iterator it = pages.iterator(); it.hasNext(); ) {
            Element pageElement = (Element)it.next();
            
            String id = pageElement.getAttribute("id").getValue();
            pageIDList.add(id);
            
        }
    }

    public List getPageIDList() {
        return Collections.unmodifiableList(pageIDList);
    }

}
