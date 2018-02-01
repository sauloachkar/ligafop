package com.appspot.ligafop.client;

import com.appspot.ligafop.client.ControlBar.Controls;
import com.appspot.ligafop.client.LigaFOP.Controller;
import com.appspot.ligafop.shared.JogadorLiga;
import com.appspot.ligafop.shared.Liga;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class LigaDetails extends Page {

	public interface Css extends CssResource {
		String back();

		String field();

		String fieldGroup();

		String label();

		String jogoDetails();

		String jogadorLigaRow();

		String jogadorFoto();

		String jogadorRank();

		String jogadorApelido();

		String jogadorPartidas();

		String ligaRow();

		String ligaName();

		String imagemLiga();

		String cabecalho();

		String cabecalhoRank();

		String cabecalhoApelido();

		String cabecalhoPartidas();
		
	}

	public interface Resources extends ControlBar.Resources {
		@Source("resources/back.png")
		ImageResource back();

		@Source("resources/LigaDetails.css")
		LigaDetails.Css ligaDetailsCss();

		@Source("resources/no_photo.jpg")
		ImageResource noPhoto();

		@Source("resources/ligaParty1.png")
		@ImageOptions(preventInlining = true)
		ImageResource ligaParty();

		@Source("resources/ligaSoft1.png")
		@ImageOptions(preventInlining = true)
		ImageResource ligaSoft();

		@Source("resources/ligaHard1.png")
		@ImageOptions(preventInlining = true)
		ImageResource ligaHard();

	}

	public class JogadorLigaRow extends Widget {
		private final JogadorLiga data;
		private final Element divJogador;
		private final DivElement divJogadorFoto;
		private final DivElement divJogadorRank;
		private final DivElement divJogadorApelido;
		private final DivElement divJogadorPartidas;

		public JogadorLigaRow(Element parentElem, JogadorLiga jogadorLiga) {
			super(parentElem);
			this.data = jogadorLiga;
			LigaDetails.Css css = resources.ligaDetailsCss();// jogoListCss();
			Document doc = Document.get();
			divJogador = getElement();

			divJogador.setClassName(css.jogadorLigaRow());

			divJogadorFoto = doc.createDivElement();
			divJogadorFoto.setClassName(css.jogadorFoto());
			divJogadorFoto.getStyle().setProperty("backgroundImage",
					"url(\"" + resources.noPhoto().getSafeUri().asString() + "\")");
			divJogadorFoto.getStyle().setProperty("backgroundRepeat", "no-repeat");
			divJogadorFoto.getStyle().setProperty("backgroundSize", "50px");
			divJogador.appendChild(divJogadorFoto);

			divJogadorRank = doc.createDivElement();
			divJogadorRank.setClassName(css.jogadorRank());
			divJogadorRank.setInnerHTML("#" + parentElem.getChildCount() + "<br>" + data.getRating());
			divJogador.appendChild(divJogadorRank);

			divJogadorApelido = doc.createDivElement();
			divJogadorApelido.setClassName(css.jogadorApelido());
			divJogadorApelido.setInnerHTML(data.getNickname() == null || data.getNickname().equals("") ? data
					.getEmail() : data.getNickname());
			divJogador.appendChild(divJogadorApelido);

			divJogadorPartidas = doc.createDivElement();
			divJogadorPartidas.setClassName(css.jogadorPartidas());
			divJogadorPartidas.setInnerHTML(data.getVitorias() + "<br>" + data.getPartidas());
			divJogador.appendChild(divJogadorPartidas);

			hookEventListeners();
		}

		public JogadorLiga getJogoData() {
			return data;
		}

		private void hookEventListeners() {
			DomUtils.addEventListener("click", divJogador, new EventListener() {
				public void onBrowserEvent(Event event) {
					// controller.loadJogo(data);
					// controller.goToJogoDetails();
					// TODO: Load player details
				}
			});
		}
	}

	public static Controls createControls(final Controller controller, LigaDetails.Resources resources) {
		LigaDetails.Css css = resources.ligaDetailsCss();

		Controls controls = new Controls(resources);
		controls.addControl(css.back(), new EventListener() {
			public void onBrowserEvent(Event event) {
				controller.goToJogoList();
			}
		});

		return controls;
	}

	@SuppressWarnings("unused")
	private final Controller controller;
	private Liga currentLiga;
	private final LigaDetails.Resources resources;
	private final DivElement divLiga;
	private final DivElement divJogadores;
	private final SpanElement divLigaName;
	private final DivElement divCabecalho;
	private final DivElement divCabecalhoRank;
	private final DivElement divCabecalhoApelido;
	private final DivElement divCabecalhoPartidas;
	private final ImageElement imagemLiga;

	protected LigaDetails(PageTransitionPanel parent, Controls controls, Controller controller,
			LigaDetails.Resources resources) {
		super(parent, controls, resources);
		this.resources = resources;
		this.controller = controller;
		Element contentElem = getContentContainer();
		contentElem.setClassName(resources.ligaDetailsCss().jogoDetails());
		Document doc = Document.get();

		divLiga = doc.createDivElement();
		divLiga.setClassName(resources.ligaDetailsCss().ligaRow());

		divLigaName = doc.createSpanElement();
		divLigaName.setClassName(resources.ligaDetailsCss().ligaName());

		imagemLiga = doc.createImageElement();
		imagemLiga.setClassName(resources.ligaDetailsCss().imagemLiga());

		divLiga.appendChild(imagemLiga);
		divLiga.appendChild(divLigaName);
		contentElem.appendChild(divLiga);

		// Cabeçalho
		divCabecalho = doc.createDivElement();
		divCabecalho.setClassName(resources.ligaDetailsCss().cabecalho());

		divCabecalhoRank = doc.createDivElement();
		divCabecalhoRank.setInnerHTML("Rank<br>Rating");
		divCabecalhoRank.setClassName(resources.ligaDetailsCss().cabecalhoRank());
		divCabecalho.appendChild(divCabecalhoRank);

		divCabecalhoApelido = doc.createDivElement();
		divCabecalhoApelido.setInnerHTML("Apelido");
		divCabecalhoApelido.setClassName(resources.ligaDetailsCss().cabecalhoApelido());
		divCabecalho.appendChild(divCabecalhoApelido);

		divCabecalhoPartidas = doc.createDivElement();
		divCabecalhoPartidas.setInnerHTML("Vitórias<br>Partidas");
		divCabecalhoPartidas.setClassName(resources.ligaDetailsCss().cabecalhoPartidas());
		divCabecalho.appendChild(divCabecalhoPartidas);

		contentElem.appendChild(divCabecalho);
		// Fim Cabeçalho

		divJogadores = doc.createDivElement();
		contentElem.appendChild(divJogadores);

	}

	public void setLiga(Liga liga) {
		if (liga == null) {
			currentLiga = null;
		} else {
			currentLiga = liga;
		}
	}

	public void view(Liga liga) {
		currentLiga = liga;
		if (liga != null) {
			NodeList<Node> nodes = divJogadores.getChildNodes();
			int length = nodes.getLength();
			for (int i = 0; i < length; i++) {
				Node node = nodes.getItem(i);
				divJogadores.removeChild(node);
			}
			int codigoBGG = Integer.parseInt(currentLiga.getBoardGame().getCodigoBGG());
			if (codigoBGG > 0) {
				imagemLiga.setSrc(currentLiga.getBoardGame().getThumb());
			} else {
				ImageResource ligaImage = null;
				if (codigoBGG == -1) {
					ligaImage = resources.ligaParty();
				} else if (codigoBGG == -2) {
					ligaImage = resources.ligaSoft();
				} else {
					ligaImage = resources.ligaHard();
				}
				imagemLiga.setAttribute("src", ligaImage.getSafeUri().asString());
			}
			divLigaName.setInnerText(currentLiga.getBoardGame().getName());
			if (liga.getJogadores() != null) {
				for (JogadorLiga jogador : liga.getJogadores()) {
					new JogadorLigaRow(divJogadores, jogador);
				}
			}
		}
	}

	public Liga getLiga(Liga liga) {
		return currentLiga;
	}

}
