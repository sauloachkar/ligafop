package com.appspot.ligafop.client;

import com.appspot.ligafop.client.ControlBar.Controls;
import com.appspot.ligafop.client.LigaFOP.Controller;
import com.appspot.ligafop.shared.Jogador;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class JogadorDetails extends Page {

	public interface Css extends CssResource {
		String back();

		String field();

		String fieldGroup();

		String label();

		String jogadorDetails();
	}

	public interface Resources extends ControlBar.Resources {
		@Source("resources/back.png")
		ImageResource back();

		@Source("resources/JogadorDetails.css")
		JogadorDetails.Css jogadorDetailsCss();
	}

	public static Controls createControls(final Controller controller, JogadorDetails.Resources resources) {
		JogadorDetails.Css css = resources.jogadorDetailsCss();

		Controls controls = new Controls(resources);
		controls.addControl(css.back(), new EventListener() {
			public void onBrowserEvent(Event event) {
				controller.goToJogoList();
			}
		});

		return controls;
	}

	private final Controller controller;
	private Jogador currentJogador;
	private final JogadorDetails.Resources resources;
	private final ButtonElement saveButton;
	private final InputElement emailField;
	private final InputElement nicknameField;

	protected JogadorDetails(PageTransitionPanel parent, Controls controls, Controller controller,
			JogadorDetails.Resources resources) {
		super(parent, controls, resources);
		this.resources = resources;
		this.controller = controller;
		Element contentElem = getContentContainer();
		contentElem.setClassName(resources.jogadorDetailsCss().jogadorDetails());
		Document doc = Document.get();

		emailField = doc.createElement("input").cast();
		emailField.setDisabled(true);
		contentElem.appendChild(createLabelledFieldGroup("Email:", emailField));

		nicknameField = doc.createElement("input").cast();
		nicknameField.setMaxLength(20);
		contentElem.appendChild(createLabelledFieldGroup("Apelido:", nicknameField));

		saveButton = doc.createPushButtonElement();
		saveButton.setInnerText("Salvar");
		saveButton.getStyle().setPropertyPx("marginLeft", 125);

		contentElem.appendChild(saveButton);

		hookEventListeners();
	}

	public void setFocus() {
		nicknameField.focus();
	}

	public void view(Jogador jogador) {
		currentJogador = jogador;
		populateFields();
	}

	private DivElement createLabelledFieldGroup(String labelText, Element field) {
		DivElement fieldGroup = Document.get().createDivElement();
		fieldGroup.setClassName(resources.jogadorDetailsCss().fieldGroup());

		DivElement label = Document.get().createDivElement();
		label.setInnerText(labelText);

		label.setClassName(resources.jogadorDetailsCss().label());
		field.setClassName(resources.jogadorDetailsCss().field());

		fieldGroup.appendChild(label);
		fieldGroup.appendChild(field);

		return fieldGroup;
	}

	private void hookEventListeners() {

		DomUtils.addEventListener("click", saveButton, new EventListener() {
			public void onBrowserEvent(Event event) {

				if (currentJogador != null) {
					currentJogador.setNickname(nicknameField.getValue());
					if (validateFields(currentJogador)) {
						controller.updateJogador(currentJogador);
					}
				}
			}
		});
	}

	private void populateFields() {
		emailField.setValue(currentJogador.getEmail());
		nicknameField.setValue(currentJogador.getNickname());

	}

	private boolean validateFields(Jogador jogador) {
		if (jogador.getNickname().equals("")) {
			DomUtils.getWindow().alert("Nickname inv�lido.");
			return false;
		} else {
			return true;
		}
	}

}
