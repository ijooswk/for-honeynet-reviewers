/*
 This Java source file was generated by test-to-java.xsl
 and is a derived work from the source document.
 The source document contained the following notice:



 Copyright (c) 2001-2003 World Wide Web Consortium,
 (Massachusetts Institute of Technology, Institut National de
 Recherche en Informatique et en Automatique, Keio University).  All
 Rights Reserved.  This program is distributed under the W3C's Software
 Intellectual Property License.  This program is distributed in the
 hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 PURPOSE.

 See W3C License http://www.w3.org/Consortium/Legal/ for more details.


 */

package tests.org.w3c.dom;

import dalvik.annotation.TestTargets;
import dalvik.annotation.TestLevel;
import dalvik.annotation.TestTargetNew;
import dalvik.annotation.TestTargetClass;

import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;

/**
 * The "getAttributeNS(namespaceURI,localName)" method retrieves an attribute
 * value by local name and NamespaceURI.
 *
 * Retrieve the first "emp:address" element. The value returned by the
 * "getAttributeNS()" method should be the value "DISTRICT" since the attribute
 * has a default value.
 *
 * @author NIST
 * @author Mary Brady
 * @see <a
 *      href="http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAttrNS">http://www.w3.org/TR/DOM-Level-2-Core/core#ID-ElGetAttrNS</a>
 * @see <a
 *      href="http://www.w3.org/Bugs/Public/show_bug.cgi?id=238">http://www.w3.org/Bugs/Public/show_bug.cgi?id=238</a>
 */
@TestTargetClass(Element.class)
public final class GetAttributeNS extends DOMTestCase {

    DOMDocumentBuilderFactory factory;

    DocumentBuilder builder;

    protected void setUp() throws Exception {
        super.setUp();
        try {
            factory = new DOMDocumentBuilderFactory(DOMDocumentBuilderFactory
                    .getConfiguration2());
            builder = factory.getBuilder();
        } catch (Exception e) {
            fail("Unexpected exception" + e.getMessage());
        }
    }

    protected void tearDown() throws Exception {
        factory = null;
        builder = null;
        super.tearDown();
    }

    /**
     * Runs the test case.
     *
     * @throws Throwable
     *             Any uncaught exception causes test to fail
     */
// Assumes validation.
//    public void testGetAttributeNS1() throws Throwable {
//        String namespaceURI = "http://www.nist.gov";
//        String localName = "district";
//
//        Document doc;
//        NodeList elementList;
//        Element testAddr;
//        String attrValue;
//        doc = (Document) load("staffNS", builder);
//        elementList = doc.getElementsByTagName("emp:address");
//        testAddr = (Element) elementList.item(0);
//        attrValue = testAddr.getAttributeNS(namespaceURI, localName);
//        assertEquals("attrValue", "DISTRICT", attrValue);
//    }
    @TestTargetNew(
        level = TestLevel.PARTIAL,
        notes = "Doesn't verify DOMException.",
        method = "getAttributeNS",
        args = {java.lang.String.class, java.lang.String.class}
    )
    public void testGetAttributeNS2() throws Throwable {
        String namespaceURI = "http://www.nist.gov";
        String localName = "district";
        String qualifiedName = "emp:district";
        Document doc;
        Attr newAttribute;
        NodeList elementList;
        Element testAddr;

        String attrValue;
        doc = (Document) load("staffNS", builder);
        newAttribute = doc.createAttributeNS(namespaceURI, qualifiedName);
        elementList = doc.getElementsByTagName("emp:address");
        testAddr = (Element) elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        testAddr.setAttributeNodeNS(newAttribute);
        elementList = doc.getElementsByTagName("emp:address");
        testAddr = (Element) elementList.item(0);
        attrValue = testAddr.getAttributeNS(namespaceURI, localName);
        assertEquals("throw_Equals", "", attrValue);
    }
    @TestTargetNew(
        level = TestLevel.PARTIAL,
        notes = "Doesn't verify DOMException.",
        method = "getAttributeNS",
        args = {java.lang.String.class, java.lang.String.class}
    )
    public void testGetAttributeNS3() throws Throwable {
        String namespaceURI = "http://www.nist.gov";
        String localName = "domestic";
        Document doc;
        NodeList elementList;
        Element testAddr;
        String attrValue;
        doc = (Document) load("staffNS", builder);
        elementList = doc.getElementsByTagName("emp:address");
        testAddr = (Element) elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        testAddr.removeAttributeNS(namespaceURI, localName);
        attrValue = testAddr.getAttributeNS(namespaceURI, localName);
        assertEquals("throw_Equals", "", attrValue);
    }
    @TestTargetNew(
        level = TestLevel.PARTIAL,
        notes = "Doesn't verify DOMException.",
        method = "getAttributeNS",
        args = {java.lang.String.class, java.lang.String.class}
    )
    public void testGetAttributeNS4() throws Throwable {
        String namespaceURI = "http://www.nist.gov";
        String localName = "blank";
        String qualifiedName = "emp:blank";
        Document doc;

        NodeList elementList;
        Element testAddr;

        String attrValue;
        doc = (Document) load("staffNS", builder);
        doc.createAttributeNS(namespaceURI, qualifiedName);
        elementList = doc.getElementsByTagName("emp:address");
        testAddr = (Element) elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        testAddr.setAttributeNS(namespaceURI, qualifiedName, "NewValue");
        attrValue = testAddr.getAttributeNS(namespaceURI, localName);
        assertEquals("throw_Equals", "NewValue", attrValue);
    }
    @TestTargetNew(
        level = TestLevel.PARTIAL,
        notes = "Doesn't verify DOMException.",
        method = "getAttributeNS",
        args = {java.lang.String.class, java.lang.String.class}
    )
    public void testGetAttributeNS5() throws Throwable {
        Document doc;
        NodeList elementList;
        Element testAddr;
        String attrValue;
        doc = (Document) load("staffNS", builder);
        elementList = doc.getElementsByTagName("emp:address");
        testAddr = (Element) elementList.item(0);
        assertNotNull("empAddrNotNull", testAddr);
        attrValue = testAddr.getAttributeNS("http://www.nist.gov", "domestic");
        assertEquals("attrValue", "Yes", attrValue);
    }
}
