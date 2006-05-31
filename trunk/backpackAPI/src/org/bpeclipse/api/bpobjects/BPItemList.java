package org.bpeclipse.api.bpobjects;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

public class BPItemList implements IBPObject {
    
    private Map itemMap = new HashMap();

    public void loadFromXml(Element node) throws Exception {
        
        if (!node.getName().equals("items")) {
            throw new Exception("BPItemList element is not 'items'");
        }
        
        // TODO: there is another flavour of XML for item lists, when we query for item lists alone
        List items = node.getChildren("item");
        
        for (Iterator it = items.iterator(); it.hasNext(); ) {
            Element itemElement = (Element)it.next();
            
            String id = itemElement.getAttribute("id").getValue();
            BPItem item = new BPItem();
            item.loadFromXml(itemElement);
            
            itemMap.put(id, item);
        }
    }
    
    public BPItem getItem(String id) {
        return (BPItem)itemMap.get(id);
    }
    
    public Collection getItemIDs() {
        return itemMap.keySet();
    }

}
