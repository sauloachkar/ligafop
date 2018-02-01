package com.appspot.ligafop.client;

import com.appspot.ligafop.client.JogoList.JogoRow;
import com.appspot.ligafop.shared.BoardGame;
import com.appspot.ligafop.shared.Jogador;
import com.appspot.ligafop.shared.Liga;
import com.appspot.ligafop.shared.Partida;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * 'jogo Engine' is a mobile sample App that demonstrates using GWT at a lower
 * level and closer to the underlying DOM.
 * 
 * If your Application is simple enough, you may be able to get by without
 * leveraging GWT's built in widgets and just use the core DOM API. For smaller
 * Apps, this may buy you some savings in code size. In the end however, there
 * is a trade off with regards to code complexity and managability. The more
 * complex the App is, the more you may find yourself re-inventing stuff that
 * GWT already has built in. Applications like those more often than not will
 * benefit all around (size and development effort) from using GWT's built in
 * Widgets.
 */
public class LigaFOP implements EntryPoint {
	/**
	 * This class provides Application level controls like page switching, and
	 * exposes the RPC service. We pass an instance of this object around so
	 * that the various components of our App can have access to the
	 * functionality.
	 */
	public class Controller {

		public void addNewJogo(BoardGame currentJogo) {
			Liga liga = new Liga(currentJogo);
			persistJogo(jogoList.addJogoToUi(liga, null));
			partidaDetails.setBoardGame(currentJogo);
			goToJogoList();
		}

		public void addNewPartida(Partida partida) {
			persistPartida(partida);
			goToJogoList();
		}

		public void goToJogoDetails() {
			uiPages.doPageTransition(jogoDetails.getPageIndex());
		}

		public void goToLigaDetails() {
			uiPages.doPageTransition(ligaDetails.getPageIndex());
		}

		public void goToPartidaDetails() {
			partidaDetails.setJogador(jogoList.getJogador());
			uiPages.doPageTransition(partidaDetails.getPageIndex());
		}

		public void goToJogadorDetails() {
			uiPages.doPageTransition(jogadorDetails.getPageIndex());
		}

		public void goToJogoList() {
			uiPages.doPageTransition(jogoList.getPageIndex());
		}

		public void loadJogo(Liga data) {
			jogoDetails.view(data == null ? null : data.getBoardGame());
		}

		public void loadPartida(Partida partida) {
			partidaDetails.view(partida);
		}

		public void loadLiga(Liga liga) {
			ligaDetails.view(liga);
		}

		public void loadJogador(Jogador jogador) {
			jogadorDetails.view(jogador);
		}

		public void persistJogo(final JogoRow row) {
			apiJogo.persistJogo(row.getJogoData().getBoardGame(), new AsyncCallback<String>() {

				public void onFailure(Throwable caught) {
					DomUtils.getWindow().alert(
							"Falhou ao tentar salvar Board Game. Tente novamente.");
				}

				public void onSuccess(String result) {
					if (result == null) {
						onFailure(null);
					} else {
						row.setRowAsPersisted(result);
					}
				}

			});
		}

		public void persistPartida(Partida partida) {
			apiJogo.persistPartida(partida, new AsyncCallback<Liga[]>() {

				public void onFailure(Throwable caught) {
					DomUtils.getWindow().alert(
							"Falhou ao tentar salvar a partida. Erro: " + caught.getMessage());
				}

				public void onSuccess(Liga[] ligas) {
					if (ligas == null) {
						onFailure(null);
					} else {
						// Atualiza liga board game
						jogoList.updateLiga(ligas[0]);

						// Atualiza liga party/soft/hard
						jogoList.updateLiga(ligas[1]);
					}
				}

			});
		}

		/**
		 * Updates the jogo information on the server and transitions back to
		 * the {@link JogoList}.
		 * 
		 * @param currentJogo
		 *            the {@link jogo} for the updated jogo
		 * @param oldPriority
		 *            the old value for the priority of the jogo
		 */
		public void updateJogo(BoardGame currentJogo) {
			// Liga liga = new Liga(currentJogo);
			// persistJogo(jogoList.updateJogo(liga));
			goToJogoList();
		}

		public void getJogos() {

			apiJogo.getBoardGames(new AsyncCallback<BoardGame[]>() {

				public void onFailure(Throwable caught) {
				}

				public void onSuccess(BoardGame[] boards) {
					if (boards == null) {
						onFailure(null);
					} else {
						for (BoardGame boardGame : boards) {
							partidaDetails.setBoardGame(boardGame);
						}
					}

				}

			});
		}

		public void getJogadores() {
			apiJogo.getJogadorList(new AsyncCallback<Jogador[]>() {

				public void onFailure(Throwable caught) {
				}

				public void onSuccess(Jogador[] jogadores) {
					if (jogadores == null) {
						onFailure(null);
					} else {
						partidaDetails.setJogadores(jogadores);
					}
				}

			});

		}

		public void getBoardGame(BoardGame potentialJogo) {
			apiJogo.getBoardGame(potentialJogo, new AsyncCallback<BoardGame[]>() {

				public void onFailure(Throwable caught) {
					jogoDetails.setBoardGame(null);
				}

				public void onSuccess(BoardGame[] boardGames) {
					if (boardGames == null) {
						onFailure(null);
					} else {
						if (boardGames.length == 1 && boardGames[0].getThumb() != null
								&& !boardGames[0].getThumb().equals("")) {
							jogoDetails.setBoardGame(boardGames[0]);
						} else {
							jogoDetails.setBoardGames(boardGames);
						}

					}
				}
			});

		}

		public void updateJogador(Jogador jogador) {
			apiJogo.persistJogador(jogador, new AsyncCallback<String>() {

				public void onFailure(Throwable caught) {
					DomUtils.getWindow().alert(
							"Falhou ao tentar salvar os dados do Jogador. Favor tentar novamente.");
				}

				public void onSuccess(String result) {
					if (result == null) {
						onFailure(null);
					} else {
						goToJogoList();
					}
				}

			});

		}
	}

	/**
	 * Our resources used in the sample.
	 * 
	 * {@link ControlBar.Resources} is an {@link ImmutableResourceBundle} (IRB).
	 * IRB allows us to have a programmatic interface with static resources used
	 * in this sample, like CSS styles and Images. Images specified here (or in
	 * the inheritance chain) are automatically combined into a single sprite,
	 * with corresponding CSS automatically generated to display each individual
	 * image piece through cropping.
	 * 
	 */
	public interface Resources extends JogoList.Resources, JogoDetails.Resources,
			PartidaDetails.Resources, LigaDetails.Resources, JogadorDetails.Resources {
	}

	private final JogosApiAsync apiJogo = GWT.create(JogosApi.class);;
	private final Resources resources = GWT.create(Resources.class);
	private JogoDetails jogoDetails;
	private JogadorDetails jogadorDetails;
	private PartidaDetails partidaDetails;
	private LigaDetails ligaDetails;
	private JogoList jogoList;
	private PageTransitionPanel uiPages;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		StyleInjector.injectAtEnd(resources.jogoDetailsCss().getText()
				+ resources.jogoListCss().getText() + resources.controlBarCss().getText()
				+ resources.partidaDetailsCss().getText() + resources.ligaDetailsCss().getText()
				+ resources.jogadorDetailsCss().getText());

		uiPages = new PageTransitionPanel(Document.get().getBody());
		Controller controller = new Controller();

		ControlBar.Controls jogoListControls = JogoList.createControls(controller, resources);
		jogoList = new JogoList(uiPages, jogoListControls, controller, resources);

		ControlBar.Controls ligaDetailsControls = LigaDetails.createControls(controller, resources);
		ligaDetails = new LigaDetails(uiPages, ligaDetailsControls, controller, resources);

		ControlBar.Controls partidaDetailsControls = PartidaDetails.createControls(controller,
				resources);
		partidaDetails = new PartidaDetails(uiPages, partidaDetailsControls, controller, resources);

		ControlBar.Controls jogadorDetailsControls = JogadorDetails.createControls(controller,
				resources);
		jogadorDetails = new JogadorDetails(uiPages, jogadorDetailsControls, controller, resources);

		ControlBar.Controls jogoDetailsControls = JogoDetails.createControls(controller, resources);
		jogoDetails = new JogoDetails(uiPages, jogoDetailsControls, controller, resources);

		carregaJogador();

		uiPages.doResize();

		DeferredCommand.defer(new DeferredCommand() {
			@Override
			public void onExecute() {
				uiPages.doResize();
			}
		}, 100);

	}

	/**
	 * Carrega Jogador e Partidas
	 */
	private void carregaJogador() {
		DeferredCommand.defer(new DeferredCommand() {
			@Override
			public void onExecute() {
				if (!jogoList.isLoggedIn()) {
					jogoList.notifyNotLoggedIn(null);
				}
			}
		}, 5000);

		// apiJogo.getJogoList(new AsyncCallback<Jogo[]>() {
		apiJogo.getLigaList(new AsyncCallback<Liga[]>() {

			public void onFailure(Throwable caught) {
				if (caught == null) {
					apiJogo.getLoginUrl(new AsyncCallback<String>() {

						public void onFailure(Throwable caught) {
							jogoList.notifyNotLoggedIn(null);
						}

						public void onSuccess(String loginUrl) {
							jogoList.notifyNotLoggedIn(loginUrl);
						}

					});
				} else {
					jogoList.notifyNotLoggedIn(null);
				}
			}

			public void onSuccess(Liga[] ligas) {
				if (ligas == null) {
					onFailure(null);
				} else {
					Jogador jogador = ligas[0].getJogador();
					jogoList.setJogador(jogador);
					jogoList.setUserLoggedIn();

					for (int i = 1, n = ligas.length; i < n; i++) {
						Liga liga = ligas[i];
						jogoList.addJogoToUi(liga, null).setRowAsPersisted(
								liga.getBoardGame().getCodigoBGG());
					}
				}
			}
		});
	}
}
