package org.bpeclipse.bpobjects;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

public class BPNoteList implements IBPObject {
    
    private Map noteMap = new HashMap();

    public void loadFromXml(Element node) throws Exception {
        if (!node.getName().equals("notes")) {
            throw new Exception("BPItemList element is not 'items'");
        }
        
        List notes = node.getChildren("note");
        
        for (Iterator it = notes.iterator(); it.hasNext(); ) {
            Element noteElement = (Element)it.next();
            
            String id = noteElement.getAttribute("id").getValue();
            BPNote note = new BPNote();
            note.loadFromXml(noteElement);
            
            noteMap.put(id, note);
        }

    }

}
