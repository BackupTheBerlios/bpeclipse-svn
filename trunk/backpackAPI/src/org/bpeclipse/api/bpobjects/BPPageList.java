package org.bpeclipse.api.bpobjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

public class BPPageList implements IBPObject {
    
    private Map pageMap = new HashMap();

    public void loadFromXml(Element node) throws Exception {
        if (!node.getName().equals("pages")) {
            throw new Exception("BPPageList element is not 'pages'");
        }
        
        List pages = node.getChildren("page");
        
        for (Iterator it = pages.iterator(); it.hasNext(); ) {
            Element pageElement = (Element)it.next();
            
            String id = pageElement.getAttribute("id").getValue();
            String title = pageElement.getAttribute("title").getValue();
            pageMap.put(id, title);
            
        }
    }

    public Map getPageMap() {
        return Collections.unmodifiableMap(pageMap);
    }

}
