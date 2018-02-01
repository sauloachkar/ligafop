package com.appspot.ligafop.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.appspot.ligafop.client.ControlBar.Controls;
import com.appspot.ligafop.client.DomUtils.EventRemover;
import com.appspot.ligafop.client.LigaFOP.Controller;
import com.appspot.ligafop.shared.Jogador;
import com.appspot.ligafop.shared.JogadorLiga;
import com.appspot.ligafop.shared.Jogo;
import com.appspot.ligafop.shared.Liga;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

/**
 * The main UI for our Application. This is the list of jogos.
 */
public class JogoList extends Page {

	/**
	 * Styles for this Widget. CssResource styles are compiled, minified and injected into the
	 * compiled output for this application. Fewer round trips since everything is included in the
	 * JavaScript :)!
	 */
	public interface Css extends CssResource {
		String checkBoxContainer();

		String checked();

		String plusPartida();

		String plusJogo();

		String jogoRow();

		String ligaParty();

		String ligaSoft();

		String ligaHard();

		String title();

		String unChecked();

		String user();

		String rank();

		String configJogador();
	}

	/**
	 * Resources for this Widget.
	 * 
	 */
	public interface Resources extends ControlBar.Resources {
		@Source("resources/checkBox.png")
		ImageResource checkBox();

		@Source("resources/check.png")
		ImageResource checkMark();

		@Source("resources/plusJogo.png")
		ImageResource plusJogo();

		@Source("resources/plusPartida.png")
		ImageResource plusPartida();

		@Source("resources/configJogador.png")
		ImageResource configJogador();

		@Source("resources/JogoList.css")
		JogoList.Css jogoListCss();
		
		@Source("resources/ligaParty1.png")
		ImageResource ligaParty();
		
		@Source("resources/ligaSoft1.png")
		ImageResource ligaSoft();
		
		@Source("resources/ligaHard1.png")
		ImageResource ligaHard();

	}

	/**
	 * This class wraps JogoData and its associated DOM elements. This class controls the rendering
	 * of Jogo Data to the UI.
	 */
	public class JogoRow extends Widget {
		private final DivElement checkMark;
		private final Liga data;
		private List<EventRemover> removers = new ArrayList<EventRemover>();

		private final DivElement titleElem;
		private final DivElement rankElem;

		public JogoRow(Element parentElem, Liga liga, Element removed) {
			super(parentElem, removed);
			this.data = liga;
			Element myElem = getElement();
			JogoList.Css css = resources.jogoListCss();
			myElem.setClassName(css.jogoRow());
			titleElem = Document.get().createDivElement();
			titleElem.setClassName(css.title());

			DivElement rightMask = Document.get().createDivElement();
			rightMask.setClassName(css.checkBoxContainer());
			checkMark = Document.get().createDivElement();
			rightMask.appendChild(checkMark);

			rankElem = Document.get().createDivElement();
			rankElem.setClassName(css.rank());

			myElem.appendChild(titleElem);
			myElem.appendChild(rankElem);
			// myElem.appendChild(rightMask);

			renderJogo();

			hookEventListeners();
		}

		public Liga getJogoData() {
			return data;
		}

		public void removeFromList() {
			for (int i = 0, n = removers.size(); i < n; i++) {
				removers.get(i).remove();
			}
			removers.clear();

			jogoRowMap.remove(data.getBoardGame().getCodigoBGG());

		}

		public void renderJogo() {
			if (data.getBoardGame() != null) {
				String thumb = data.getBoardGame().getThumb();
				int codigoBGG = Integer.parseInt(data.getBoardGame().getCodigoBGG());
				switch (codigoBGG) {
				case -1:
					thumb = resources.ligaParty().getSafeUri().asString();
					titleElem.getStyle().setFontWeight(FontWeight.BOLD);
					break;
				case -2:
					thumb = resources.ligaSoft().getSafeUri().asString();
					titleElem.getStyle().setFontWeight(FontWeight.BOLD);
					break;
				case -3:
					thumb = resources.ligaHard().getSafeUri().asString();
					titleElem.getStyle().setFontWeight(FontWeight.BOLD);
					break;
				default:
					titleElem.getStyle().setProperty("backgroundRepeat", "no-repeat");
					titleElem.getStyle().setProperty("backgroundSize", "100px");
					break;
				}
				titleElem.getStyle().setProperty("backgroundImage", "url(\"" + thumb + "\")");
			}
			titleElem.setInnerText(data.getBoardGame().getName());
			checkMark.setClassName(resources.jogoListCss().unChecked());

			String detalhesLiga = "";

			ArrayList<JogadorLiga> jogadores = data.getJogadores();
			if (jogadores == null || jogadores.size() == 0) {
				detalhesLiga = "Liga nova!";
			} else {
				int partidas = data.getPartidas();
				detalhesLiga = partidas + (partidas > 1 ? " partidas" : " partida");
				int size = data.getJogadores().size();
				detalhesLiga += "<br>" + size + (size > 1 ? " jogadores" : " jogador");
				if (Integer.parseInt(data.getBoardGame().getCodigoBGG()) > 0) {
					if (data.getPontuacaoMaxima() != null) {
						detalhesLiga += "<br>" + data.getPontuacaoMaxima().intValue() + " máx pts";
					}
				} else {
					if (data.getJogadores().size() > 0) {
						detalhesLiga += "<br>Top: " + data.getJogadores().get(0).getNickname();
					}
				}

			}

			rankElem.setInnerHTML(detalhesLiga);
		}

		public void setRowAsNotPersisted() {
			getElement().setClassName(resources.jogoListCss().jogoRow());
		}

		public void setRowAsPersisted(String id) {
			jogoRowMap.remove(data.getBoardGame().getCodigoBGG());
			data.getBoardGame().setCodigoBGG(id);
			jogoRowMap.put(id, JogoRow.this);
		}

		private void hookEventListeners() {
			DomUtils.addEventListener("click", titleElem.getParentElement(), new EventListener() {

				public void onBrowserEvent(Event event) {
					controller.loadLiga(data);
					controller.goToLigaDetails();
				}

			});

			DomUtils.addEventListener("click", checkMark, new EventListener() {

				public void onBrowserEvent(Event event) {
					renderJogo();
					controller.persistJogo(JogoRow.this);
					event.stopPropagation();
				}

			});
		}
	}

	/**
	 * Creates the controls to be added to a JogoList.
	 */
	public static Controls createControls(final Controller controller, JogoList.Resources resources) {
		JogoList.Css css = resources.jogoListCss();

		Controls controls = new Controls(resources);
		controls.addControl(css.plusJogo(), new EventListener() {

			public void onBrowserEvent(Event event) {
				controller.loadJogo(null);
				controller.goToJogoDetails();
			}

		});

		controls.addControl(css.plusPartida(), new EventListener() {

			public void onBrowserEvent(Event event) {
				controller.loadPartida(null);
				controller.goToPartidaDetails();
			}

		});

		controls.addControl(css.configJogador(), new EventListener() {

			public void onBrowserEvent(Event event) {
				controller.loadJogador(jogador);
				controller.goToJogadorDetails();
			}

		});

		return controls;
	}

	private final Controller controller;
	private boolean isLoggedIn = false;
	private boolean isAdmin = false;
	private boolean isGerente = false;
	private final AnchorElement logoutLink;
	private final DivElement ligaParty;
	private final DivElement ligaSoft;
	private final DivElement ligaHard;
	private final JogoList.Resources resources;
	private final HashMap<String, JogoRow> jogoRowMap = new HashMap<String, JogoRow>();
	private final List<JogoRow> jogosPendingDeleteConfirmation = new ArrayList<JogoRow>();

	private final DivElement userEmail;
	private static Jogador jogador;

	protected JogoList(PageTransitionPanel parent, Controls controls, Controller controller,
			JogoList.Resources resources) {
		super(parent, controls, resources);
		this.controller = controller;
		this.resources = resources;
		Element container = getContentContainer();

		ligaParty = Document.get().createDivElement();
		ligaParty.setClassName(resources.jogoListCss().ligaParty());
		container.appendChild(ligaParty);

		ligaSoft = Document.get().createDivElement();
		ligaSoft.setClassName(resources.jogoListCss().ligaSoft());
		container.appendChild(ligaSoft);

		ligaHard = Document.get().createDivElement();
		ligaHard.setClassName(resources.jogoListCss().ligaHard());
		container.appendChild(ligaHard);

		userEmail = Document.get().createDivElement();
		userEmail.getStyle().setProperty("display", "inline-block");
		userEmail.setInnerText("Carregando...");
		logoutLink = Document.get().createAnchorElement();
		DivElement userInfoContainer = Document.get().createDivElement();
		userInfoContainer.appendChild(userEmail);
		userInfoContainer.appendChild(logoutLink);
		userInfoContainer.setClassName(resources.jogoListCss().user());
		container.appendChild(userInfoContainer);
	}

	public JogoRow addJogoToUi(Liga liga, Element removed) {
		Element container = getTipoLiga(liga);

		JogoRow row = new JogoRow(container, liga, removed);

		return row;
	}

	private Element getTipoLiga(Liga liga) {
		if (liga.getBoardGame().getAverageWeight() < 2) {
			return ligaParty;
		} else if (liga.getBoardGame().getAverageWeight() < 3) {
			return ligaSoft;
		} else
			return ligaHard;
	}

	public void confirmDeletion() {
		for (int i = 0, n = jogosPendingDeleteConfirmation.size(); i < n; i++) {
			JogoRow row = jogosPendingDeleteConfirmation.get(i);
			row.removeFromList();
		}
		jogosPendingDeleteConfirmation.clear();
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void notifyNotLoggedIn(String loginUrl) {
		getControlBar().disableControls();
		if (loginUrl != null) {
			userEmail.setInnerText("Please ");
			logoutLink.setHref(loginUrl);
			logoutLink.setInnerText(" signin.");
		} else {
			userEmail.setInnerText("Carregando Liga. Aguarde...");
			//logoutLink.setHref("javascript:location.reload(true);");
			//logoutLink.setInnerText(" Try Refresh");
		}
	}

	public void setUserLoggedIn() {
		isLoggedIn = true;
		isAdmin = jogador.isAdmin();
		isGerente = jogador.isGerente();
		userEmail.setInnerText("Olá, " + jogador.getNickname() + "! (" + jogador.getEmail() + ") ");
		logoutLink.setHref(jogador.getLogoutURL());
		logoutLink.setInnerHTML("<br><br>Sair");

		getControlBar().enableControls();

		getControlBar().disableControls(isGerente);

		if (jogador.isPrimeiroAcesso()) {
			controller.loadJogador(jogador);
			controller.goToJogadorDetails();
		}

	}

	/**
	 * Updates the UI to reflect that the underlying jogo has been updated.
	 * 
	 * @param jogo
	 *            the {@link Jogo} that has been updated.
	 * @param oldPriority
	 *            the old priority level of the jogo.
	 * @return returns the updated {@link JogoRow}.
	 */
	public JogoRow updateLiga(Liga liga) {
		String codigoBGG = liga.getBoardGame().getCodigoBGG();
		JogoRow removed = jogoRowMap.remove(codigoBGG);
		JogoRow row = addJogoToUi(liga, removed.getElement());
		if (removed != null) {
			removed.getElement().removeFromParent();
		}
		row.setRowAsPersisted(codigoBGG);
		return row;
	}

	public void setJogador(Jogador jogador) {
		JogoList.jogador = jogador;

	}

	public Jogador getJogador() {
		return jogador;
	}

}
