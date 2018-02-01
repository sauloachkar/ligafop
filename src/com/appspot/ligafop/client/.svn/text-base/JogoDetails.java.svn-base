package com.appspot.ligafop.client;

import com.appspot.ligafop.client.ControlBar.Controls;
import com.appspot.ligafop.client.LigaFOP.Controller;
import com.appspot.ligafop.shared.BoardGame;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

/**
 * This is UI for viewing the details of a jogo or adding a new jogo.
 */
public class JogoDetails extends Page {

	/**
	 * Styles for JogoDetails.
	 */
	public interface Css extends CssResource {
		String back();

		String field();

		String fieldGroup();

		String label();

		String jogoDetails();
	}

	/**
	 * Resources for JogoDetails.
	 */
	public interface Resources extends ControlBar.Resources {
		@Source("resources/back.png")
		ImageResource back();

		@Source("resources/JogoDetails.css")
		JogoDetails.Css jogoDetailsCss();
	}

	/**
	 * Creates the controls to be added to a JogoDetails.
	 */
	public static Controls createControls(final Controller controller,
			JogoDetails.Resources resources) {
		JogoDetails.Css css = resources.jogoDetailsCss();

		Controls controls = new Controls(resources);
		controls.addControl(css.back(), new EventListener() {
			public void onBrowserEvent(Event event) {
				controller.goToJogoList();
			}
		});

		return controls;
	}

	private final Controller controller;
	private BoardGame currentJogo;
	private final JogoDetails.Resources resources;
	private final ButtonElement procuraButton;
	private final ButtonElement incluiButton;
	private final InputElement inputField;
	private final InputElement nomeField;
	private final SelectElement boardSelect;

	protected JogoDetails(PageTransitionPanel parent, Controls controls, Controller controller,
			JogoDetails.Resources resources) {
		super(parent, controls, resources);
		this.resources = resources;
		this.controller = controller;
		Element contentElem = getContentContainer();
		contentElem.setClassName(resources.jogoDetailsCss().jogoDetails());
		Document doc = Document.get();

		inputField = doc.createElement("input").cast();
		contentElem.appendChild(createLabelledFieldGroup("Código BGG:", inputField));

		nomeField = doc.createElement("input").cast();
		contentElem.appendChild(createLabelledFieldGroup("Nome:", nomeField));

		boardSelect = doc.createSelectElement().cast();
		boardSelect.getStyle().setVisibility(Visibility.HIDDEN);
		contentElem.appendChild(createLabelledFieldGroup("", boardSelect));

		procuraButton = doc.createPushButtonElement();
		procuraButton.getStyle().setPropertyPx("marginLeft", 80);
		procuraButton.setInnerText("Procurar Jogo");

		incluiButton = doc.createPushButtonElement();
		incluiButton.setDisabled(true);
		incluiButton.getStyle().setPropertyPx("marginLeft", 40);
		incluiButton.setInnerText("Incluir Jogo");

		contentElem.appendChild(procuraButton);
		contentElem.appendChild(incluiButton);

		hookEventListeners();
	}

	public void setBoardGames(BoardGame[] boardGames) {
		limparBoardSelect();
		boardSelect.getStyle().setVisibility(Visibility.VISIBLE);
		OptionElement option = Document.get().createOptionElement();
		if (boardGames.length > 0) {
			option.setText("[ESCOLHA UM JOGO]");
		} else {
			option.setText("NENHUM JOGO ENCONTRADO");
		}
		boardSelect.add(option, null);
		for (BoardGame bg : boardGames) {
			option = Document.get().createOptionElement();
			option.setText(bg.getName());
			option.setValue(bg.getCodigoBGG());
			if (nomeField.getValue().equalsIgnoreCase(bg.getName())) {
				option.setSelected(true);
				controller.getBoardGame(bg);
			}
			boardSelect.add(option, null);
		}
	}

	private void limparBoardSelect() {
		for (int i = boardSelect.getChildCount(); i >= 0; i--) {
			boardSelect.remove(i);
		}
		boardSelect.getStyle().setVisibility(Visibility.HIDDEN);
	}

	public void setBoardGame(BoardGame boardGame) {
		currentJogo = boardGame;
		incluiButton.setDisabled(false);
		inputField.setValue(currentJogo.getCodigoBGG());
		nomeField.setValue(currentJogo.getName());
	}

	public void setFocus() {
		inputField.focus();
	}

	public void view(BoardGame data) {
		currentJogo = data;
		limparBoardSelect();
		incluiButton.setDisabled(true);
		if (data != null) {
			populateFields();
		} else {
			resetFields();
		}
	}

	private DivElement createLabelledFieldGroup(String labelText, Element field) {
		DivElement fieldGroup = Document.get().createDivElement();
		fieldGroup.setClassName(resources.jogoDetailsCss().fieldGroup());

		DivElement label = Document.get().createDivElement();
		label.setInnerText(labelText);

		label.setClassName(resources.jogoDetailsCss().label());
		field.setClassName(resources.jogoDetailsCss().field());

		fieldGroup.appendChild(label);
		fieldGroup.appendChild(field);

		return fieldGroup;
	}

	private void hookEventListeners() {

		DomUtils.addEventListener("click", procuraButton, new EventListener() {
			public void onBrowserEvent(Event event) {
				incluiButton.setDisabled(true);
				boardSelect.getStyle().setVisibility(Visibility.HIDDEN);
				currentJogo = new BoardGame(inputField.getValue());
				currentJogo.setName(nomeField.getValue());
				if (!validateFields(currentJogo)) {
					currentJogo = null;
					incluiButton.setDisabled(true);
				}
			}
		});

		DomUtils.addEventListener("click", incluiButton, new EventListener() {
			public void onBrowserEvent(Event event) {
				controller.addNewJogo(currentJogo);
			}
		});

		DomUtils.addEventListener("change", boardSelect, new EventListener() {
			public void onBrowserEvent(Event event) {
				incluiButton.setDisabled(true);
				controller.getBoardGame(new BoardGame(boardSelect.getOptions()
						.getItem(boardSelect.getSelectedIndex()).getValue()));
			}
		});

		DomUtils.addEventListener("click", nomeField, new EventListener() {
			public void onBrowserEvent(Event event) {
				inputField.setValue("");
			}
		});
	}

	private void populateFields() {
		inputField.setValue(currentJogo.getCodigoBGG());
	}

	private void resetFields() {
		inputField.setValue("");
		nomeField.setValue("");
	}

	private boolean validateFields(BoardGame potentialJogo) {
		String codigoBGG = potentialJogo.getCodigoBGG();
		String nome = potentialJogo.getName();
		if (codigoBGG.equals("") && nome.equals("")) {
			DomUtils.getWindow().alert(
					"Insira um código BoardGameGeek ou nome do jogo para procurar.");
			return false;
		} else if (nome.equals("") && numeroInvalido(codigoBGG)) {
			DomUtils.getWindow().alert("Código BGG válido - deve ser numérico.");
			return false;
		} else {
			controller.getBoardGame(potentialJogo);
			return true;
		}
	}

	private boolean numeroInvalido(String codigoBGG) {
		try {
			int i = Integer.parseInt(codigoBGG);
			return i <= 0;
		} catch (NumberFormatException nfe) {
			return true;
		}
	}

}
