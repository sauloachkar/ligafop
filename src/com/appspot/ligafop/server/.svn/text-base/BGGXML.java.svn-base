package com.appspot.ligafop.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.appspot.ligafop.shared.BoardGame;

public class BGGXML {

	public static void obterDadosBGG(BoardGame result) {
		String codigoBGG = result.getCodigoBGG();
		Document doc;
		try {
			String preURL = "http://www.boardgamegeek.com/xmlapi/boardgame/";
			String posURL = "?stats=1";
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new URL(preURL + codigoBGG + posURL).openStream());

			String name = null;
			NodeList listaNomes = doc.getElementsByTagName("name");
			for (int i = 0; i < listaNomes.getLength(); i++) {
				if (listaNomes.item(i).getAttributes().getNamedItem("primary") != null) {
					name = listaNomes.item(i).getTextContent();
					break;
				}
			}

			if (name == null || name.equals("")) {
				return;
			}
			result.setName(name);

			String minPlayers = doc.getElementsByTagName("minplayers").item(0).getTextContent();
			try {
				result.setMinPlayers(Integer.valueOf(minPlayers));
			} catch (Exception e) {
			}

			String playingTime = doc.getElementsByTagName("playingtime").item(0).getTextContent();
			try {
				result.setPlayingTime(Integer.valueOf(playingTime));
			} catch (Exception e) {
			}

			result.setThumb(doc.getElementsByTagName("thumbnail").item(0).getTextContent());

			String averageWeight = doc.getElementsByTagName("averageweight").item(0)
					.getTextContent();
			try {
				result.setAverageWeight(Double.valueOf(averageWeight));
			} catch (Exception e) {
			}

		} catch (SAXException | IOException | ParserConfigurationException e) {
			//
		}

	}

	public static ArrayList<BoardGame> obterListaJogos(String consulta) {
		ArrayList<BoardGame> retorno = new ArrayList<BoardGame>();
		Document doc;
		try {
			consulta = consulta.replaceAll(" ", "%20");
			String url = "http://boardgamegeek.com/xmlapi/search?search=" + consulta;
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new URL(url).openStream());

			NodeList listaBoards = doc.getElementsByTagName("boardgame");
			for (int i = 0; i < listaBoards.getLength(); i++) {
				BoardGame bg = new BoardGame();
				Node node = listaBoards.item(i);

				String codigoBGG = node.getAttributes().getNamedItem("objectid").getNodeValue();
				bg.setCodigoBGG(codigoBGG);

				String name = node.getChildNodes().item(1).getTextContent();
				bg.setName(name);
				retorno.add(bg);
			}

		} catch (SAXException | IOException | ParserConfigurationException e) {
			//
		}

		return retorno;

	}

}
