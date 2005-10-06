package org.bpeclipse.bpobjects;


import org.jdom.Element;

public interface IBPObject {
    
    public void loadFromXml(Element node) throws Exception;

}
