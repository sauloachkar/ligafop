package com.appspot.ligafop.client;

import java.util.ArrayList;

import com.appspot.ligafop.client.ControlBar.Controls;
import com.appspot.ligafop.client.LigaFOP.Controller;
import com.appspot.ligafop.shared.BoardGame;
import com.appspot.ligafop.shared.Jogador;
import com.appspot.ligafop.shared.Partida;
import com.appspot.ligafop.shared.PartidaDetalhe;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class PartidaDetails extends Page {

	public class JogadorSelect extends Widget {

		private DivElement titleElem;
		private DivElement dadosDiv;
		private SelectElement jogador;
		private SelectElement posicao;
		private InputElement pontuacao;
		private DivElement remover;

		public JogadorSelect(DivElement parent, boolean permiteRemover, int pos) {
			super(parent);
			Element myElem = getElement();
			Document doc = Document.get();

			titleElem = doc.createDivElement();
			titleElem.setClassName(resources.partidaDetailsCss().label());
			titleElem.setInnerText("Jogador:");

			dadosDiv = doc.createDivElement();
			dadosDiv.setClassName(resources.partidaDetailsCss().field());
			jogador = doc.createSelectElement().cast();
			if (jogadores != null) {
				setJogadores(jogadores);
			}
			posicao = doc.createSelectElement().cast();
			for (int i = 1; i <= pos; i++) {
				OptionElement option = doc.createOptionElement();
				option.setText(String.valueOf(i) + "º");
				option.setValue(String.valueOf(i));
				option.setSelected(true);
				posicao.add(option, null);
			}
			pontuacao = doc.createElement("input").cast();
			pontuacao.setClassName(resources.partidaDetailsCss().pontuacaoField());

			dadosDiv.appendChild(titleElem);
			dadosDiv.appendChild(jogador);
			dadosDiv.appendChild(posicao);
			dadosDiv.appendChild(pontuacao);

			/*
			if (permiteRemover) {
				remover = doc.createDivElement();
				remover.setClassName(resources.partidaDetailsCss().remover());
				adicionaEventoRemover();
				dadosDiv.appendChild(remover);
			}*/

			myElem.appendChild(titleElem);
			myElem.appendChild(dadosDiv);

		}

		private void adicionaEventoRemover() {
			DomUtils.addEventListener("click", remover, new EventListener() {
				public void onBrowserEvent(Event event) {
					getElement().removeFromParent();
					listaJogadorSelect.remove(JogadorSelect.this);
				}
			});
		}

		private void setOptionJogador(Jogador jogador) {
			OptionElement option = Document.get().createOptionElement();
			option.setText(jogador.getNickname());
			option.setValue(jogador.getEmail());
			this.jogador.add(option, null);
		}

		public void setJogadores(Jogador[] jogadores) {
			setOptionJogador(new Jogador("", "[Não cadastrado]", 0));
			for (Jogador j : jogadores) {
				setOptionJogador(j);
			}
		}

	}

	/**
	 * Styles for PartidaDetails.
	 */
	public interface Css extends CssResource {
		String back();

		String field();

		String pontuacaoField();

		String remover();

		String fieldGroup();

		String label();

		String partidaDetails();
		
		String adicionar();
		String botoesJogador();
	}

	/**
	 * Resources for PartidaDetails.
	 */
	public interface Resources extends ControlBar.Resources {
		@Source("resources/back.png")
		ImageResource back();
		
		@Source("resources/add.png")
		ImageResource add();
		
		@Source("resources/remove.png")
		ImageResource remove();

		@Source("resources/PartidaDetails.css")
		PartidaDetails.Css partidaDetailsCss();
	}

	/**
	 * Creates the controls to be added to a PartidaDetails.
	 */
	public static Controls createControls(final Controller controller,
			PartidaDetails.Resources resources) {
		PartidaDetails.Css css = resources.partidaDetailsCss();

		Controls controls = new Controls(resources);
		controls.addControl(css.back(), new EventListener() {
			public void onBrowserEvent(Event event) {
				controller.goToJogoList();
			}
		});

		return controls;
	}

	private final Controller controller;
	private Partida currentPartida;
	private final PartidaDetails.Resources resources;
	private final ButtonElement saveButton;
	private final SelectElement jogoSelect;

	private final DivElement listaJogadores;
	private Jogador[] jogadores;
	private ArrayList<JogadorSelect> listaJogadorSelect = new ArrayList<JogadorSelect>();
	private final DivElement adicionarJogador;
	private final DivElement removerJogador;
	private final DivElement botoesJogador;
	private static Jogador jogador;

	protected PartidaDetails(PageTransitionPanel parent, Controls controls, Controller controller,
			PartidaDetails.Resources resources) {
		super(parent, controls, resources);
		this.resources = resources;
		this.controller = controller;
		Element contentElem = getContentContainer();
		contentElem.setClassName(resources.partidaDetailsCss().partidaDetails());
		Document doc = Document.get();

		jogoSelect = doc.createSelectElement().cast();
		OptionElement primeiraOpcao = doc.createOptionElement();
		primeiraOpcao.setText("<Escolha um jogo>");
		primeiraOpcao.setValue("-1");
		jogoSelect.add(primeiraOpcao, null);
		controller.getJogos();
		contentElem.appendChild(createLabelledFieldGroup("Jogo:", jogoSelect));

		listaJogadores = doc.createDivElement();
		listaJogadores.setClassName(resources.partidaDetailsCss().fieldGroup());

		adicionarJogadorSelect(new JogadorSelect(listaJogadores, false, 1));
		adicionarJogadorSelect(new JogadorSelect(listaJogadores, false, 2));

		controller.getJogadores();

		contentElem.appendChild(listaJogadores);
		
		botoesJogador = doc.createDivElement();
		botoesJogador.setClassName(resources.partidaDetailsCss().botoesJogador());

		adicionarJogador = doc.createDivElement();
		adicionarJogador.setClassName(resources.partidaDetailsCss().adicionar());
		botoesJogador.appendChild(adicionarJogador);
		
		removerJogador = doc.createDivElement();
		removerJogador.setClassName(resources.partidaDetailsCss().remover());
		removerJogador.getStyle().setDisplay(Display.NONE);
		botoesJogador.appendChild(removerJogador);
		
		contentElem.appendChild(botoesJogador);

		saveButton = doc.createPushButtonElement();
		saveButton.getStyle().setPropertyPx("marginLeft", 125);
		contentElem.appendChild(saveButton);

		hookEventListeners();
	}

	private void adicionarJogadorSelect(JogadorSelect jogadorSelect) {
		this.listaJogadorSelect.add(jogadorSelect);

	}

	public void setBoardGame(BoardGame boardGame) {
		OptionElement option = Document.get().createOptionElement();
		option.setText(boardGame.getName());
		option.setValue(boardGame.getCodigoBGG());
		option.setAttribute("minplayers", String.valueOf(boardGame.getMinPlayers()));
		jogoSelect.add(option, null);

	}

	public void setFocus() {
		jogoSelect.focus();
	}

	public void view(Partida partida) {
		currentPartida = partida;
		if (partida != null) {
			saveButton.setInnerText("Salvar Partida");
		} else {
			saveButton.setInnerText("Adicionar Partida");
			resetFields();
		}
	}

	private DivElement createLabelledFieldGroup(String labelText, Element field) {
		DivElement fieldGroup = Document.get().createDivElement();
		fieldGroup.setClassName(resources.partidaDetailsCss().fieldGroup());

		DivElement label = Document.get().createDivElement();
		label.setInnerText(labelText);

		label.setClassName(resources.partidaDetailsCss().label());
		field.setClassName(resources.partidaDetailsCss().field());

		fieldGroup.appendChild(label);
		fieldGroup.appendChild(field);

		return fieldGroup;
	}

	private void hookEventListeners() {

		DomUtils.addEventListener("click", saveButton, new EventListener() {
			public void onBrowserEvent(Event event) {
				currentPartida = new Partida(jogoSelect.getValue());
				for (JogadorSelect js : listaJogadorSelect) {
					String email = js.jogador.getValue();
					String posicao = js.posicao.getValue();
					String pontuacao = js.pontuacao.getValue();
					currentPartida.adicionarJogador(email, Integer.valueOf(posicao),
							pontuacao.equals("") ? null : Double.valueOf(pontuacao));
				}
				if (validateFields(currentPartida)) {
					controller.addNewPartida(currentPartida);
				} else {
					currentPartida = null;
				}

			}
		});

		DomUtils.addEventListener("click", adicionarJogador, new EventListener() {
			public void onBrowserEvent(Event event) {
				adicionarJogadorSelect(new JogadorSelect(listaJogadores, true, listaJogadorSelect
						.size() + 1));
				removerJogador.getStyle().setDisplay(Display.BLOCK);
			}
		});
		
		DomUtils.addEventListener("click", removerJogador, new EventListener() {
			public void onBrowserEvent(Event event) {
				listaJogadores.removeChild(listaJogadores.getLastChild());
				listaJogadorSelect.remove(listaJogadorSelect.size() - 1);
				if (listaJogadorSelect.size() == 2) {
					removerJogador.getStyle().setDisplay(Display.NONE);
				}
			}
		});
			}

	private void resetFields() {
		// partidaField.setValue("");
		jogoSelect.setValue("-1");

	}

	private boolean validateFields(Partida potentialPartida) {
		String codigoBGG = potentialPartida.getBoardGame().getCodigoBGG();
		if (codigoBGG.equals("-1")) {
			DomUtils.getWindow().alert("Jogo deve ser escolhido.");
			return false;
		} else if (possuiNenhumJogadorCadastrado(potentialPartida)) {
			DomUtils.getWindow().alert("Deve ter pelo menos um jogador cadastrado.");
			return false;
		} else if (possuiJogadoresIguais(potentialPartida)) {
			DomUtils.getWindow().alert("Não pode haver jogador repetido.");
			return false;
		} else if (conferePontuacao(potentialPartida)) {
			DomUtils.getWindow().alert("Há algo errado com as posições x pontuações.");
			return false;
		} else {
			for (int i = 0; i < jogoSelect.getChildCount(); i++) {
				OptionElement jogo = (OptionElement) jogoSelect.getChild(i);
				if (jogo.getValue().equals(codigoBGG)) {
					int min = Integer.valueOf(jogo.getAttribute("minplayers"));
					if (min > potentialPartida.getDetalhes().size()) {
						DomUtils.getWindow().alert("Número mínimo de jogadores: " + min);
						return false;
					} else {
						break;
					}
				}
			}
			return true;
		}
	}

	private boolean confereJogadorIncluindo(Partida potentialPartida) {
		for (PartidaDetalhe d : potentialPartida.getDetalhes()) {
			if (d.getEmail().equals(jogador.getEmail())) {
				if (d.getPosicao() == 1)
					return true;
				else
					return false;
			}
		}
		return true;
	}

	private boolean possuiNenhumJogadorCadastrado(Partida potentialPartida) {
		int count = 0;
		for (PartidaDetalhe d : potentialPartida.getDetalhes()) {
			if (!d.getEmail().equals(""))
				count++;
			if (count > 0)
				return false;
		}
		return true;
	}

	private boolean conferePontuacao(Partida potentialPartida) {
		for (PartidaDetalhe d : potentialPartida.getDetalhes()) {
			for (PartidaDetalhe d2 : potentialPartida.getDetalhes()) {
				if (d.equals(d2))
					continue;
				double pontuacaoD = d.getPontuacao() == null ? 0 : d.getPontuacao();
				double pontuacaoD2 = d2.getPontuacao() == null ? 0 : d2.getPontuacao();
				if (d.getPosicao() < d2.getPosicao() && pontuacaoD < pontuacaoD2)
					return true;
			}
		}
		int maxPosicao = 0;
		for (PartidaDetalhe pd : potentialPartida.getDetalhes()) {
			if (pd.getPosicao() > maxPosicao) {
				maxPosicao = pd.getPosicao();
			}
		}
		return naoExistePosicao(potentialPartida.getDetalhes(), maxPosicao - 1);
	}

	private boolean naoExistePosicao(ArrayList<PartidaDetalhe> detalhes, int posicao) {
		for (PartidaDetalhe partidaDetalhe : detalhes) {
			if (partidaDetalhe.getPosicao() == posicao) {
				if (posicao == 1) {
					return false;
				} else {
					return naoExistePosicao(detalhes, posicao - 1);
				}
			}
		}
		return true;
	}

	private boolean possuiJogadoresIguais(Partida potentialPartida) {
		ArrayList<String> jogadores = new ArrayList<String>();
		for (PartidaDetalhe d : potentialPartida.getDetalhes()) {
			if (d.getEmail().equals(""))
				continue;
			if (jogadores.contains(d.getEmail()))
				return true;
			else
				jogadores.add(d.getEmail());
		}
		return false;
	}

	public void setJogadores(Jogador[] jogadores) {
		this.jogadores = jogadores;
		for (JogadorSelect js : listaJogadorSelect) {
			js.setJogadores(jogadores);
		}

	}

	public void setJogador(Jogador jogador) {
		PartidaDetails.jogador = jogador;
	}

}
