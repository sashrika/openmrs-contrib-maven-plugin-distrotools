/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.maven.plugins.distrotools.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * XML utility methods
 */
public class XmlUtils {

	/**
	 * Creates a new document builder
	 * @return the builder
	 */
	public static DocumentBuilder createBuilder() throws ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}

	/**
	 * Creates a new document transformer
	 * @return the transformer
	 */
	public static Transformer createTransformer() throws TransformerConfigurationException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		return transformer;
	}

	/**
	 * Converts an XML string to a DOM document
	 * @param xml the xml string
	 * @param documentBuilder the DOM document builder
	 * @return the DOM document
	 */
	public static Document stringToDocument(String xml, DocumentBuilder documentBuilder) throws SAXException, IOException {
		return documentBuilder.parse(new InputSource(new StringReader(xml)));
	}

	/**
	 * Converts a document object to an xml string
	 * @param document the document to convert
	 * @param documentTransformer the DOM document transformer
	 * @return the xml string
	 */
	public static String documentToString(Document document, Transformer documentTransformer) throws TransformerException {
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(document);
		documentTransformer.transform(source, result);
		return sw.toString();
	}

	/**
	 * Finds the first child of a node with the given name
	 * @param parent the parent node
	 * @param name the name
	 * @return the child node
	 */
	public static Node findFirstChild(Node parent, String name) {
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Node node = children.item(i);
			if (name.equals(node.getNodeName())) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Finds all children of a node with the given name
	 * @param parent the parent node
	 * @param name the name
	 * @return the child nodes
	 */
	public static List<Node> findAllChildren(Node parent, String name) {
		List<Node> found = new ArrayList<Node>();
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Node node = children.item(i);
			if (name.equals(node.getNodeName())) {
				found.add(node);
			}
		}
		return found;
	}
}