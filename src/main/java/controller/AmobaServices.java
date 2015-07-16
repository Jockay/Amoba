package controller;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Coordinate;
import model.Game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Game services class.
 * 
 * @author Jockay
 *
 */
public class AmobaServices {

	/**
	 * Saves game state in xml file.
	 * 
	 * @return True if method succeed at xml writing, else returns false;
	 */
	public boolean SaveToXML(Game g) {
		DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();           
		try {
			DocumentBuilder builder = dBF.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element properties = doc.createElement("GameData");
			doc.appendChild(properties);
			Element player1 = doc.createElement("Player1");
			Element player2 = doc.createElement("Player2");
			Element isTurn1 = doc.createElement("isActual");
			Element isTurn2 = doc.createElement("isActual");
			Element name1 = doc.createElement("Name");
			Element name2 = doc.createElement("Name");
			Element coordinates1 = doc.createElement("Coordinates");
			Element coordinates2 = doc.createElement("Coordinates");

			// create the hierarchy
			// doc.appendChild(properties);
			properties.appendChild(player1);
			player1.appendChild(name1);
			name1.appendChild(doc.createTextNode(g.getP1().getName()));
			player1.appendChild(isTurn1);
			isTurn1.appendChild(doc.createTextNode(g.getP1().isTurn() ? "true" : "false"));
			player1.appendChild(coordinates1);
			for (Coordinate temp : g.getP1().getCoords()) {
				Element x = doc.createElement("X");
				// xT = sb.toString();

				x.appendChild(doc.createTextNode("" + temp.x));
				coordinates1.appendChild(x);

				Element y = doc.createElement("Y");
				// yT = sb.toString();
				y.appendChild(doc.createTextNode("" + temp.y));
				coordinates1.appendChild(y);
			}

			/**/

			properties.appendChild(player2);
			player2.appendChild(name2);
			name2.appendChild(doc.createTextNode(g.getP2().getName()));
			player2.appendChild(isTurn2);
			isTurn2.appendChild(doc.createTextNode(g.getP2().isTurn() ? "true" : "false"));
			player2.appendChild(coordinates2);
			
			for (Coordinate coordinate : g.getP2().getCoords()) {
				Element x = doc.createElement("X");
				// xT = sb.toString();
				x.appendChild(doc.createTextNode("" + coordinate.x));
				coordinates2.appendChild(x);

				Element y = doc.createElement("Y");
				// yT = sb.toString();
				y.appendChild(doc.createTextNode("" + coordinate.y));
				coordinates2.appendChild(y);
			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();

			Source src = new DOMSource(doc);
			// String path_fixed = path.substring(0, 2) + path.substring(2);
			Result dest = new StreamResult(new File("saved.xml"));
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(src, dest);
			return true;
		} catch (ParserConfigurationException e) {
			return false;
		} catch (TransformerConfigurationException e) {
			return false;
		} catch (TransformerException e) {
			return false;
		}
	}
}
