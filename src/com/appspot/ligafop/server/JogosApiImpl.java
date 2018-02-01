package com.appspot.ligafop.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.appspot.ligafop.client.JogosApi;
import com.appspot.ligafop.shared.BoardGame;
import com.appspot.ligafop.shared.Helper;
import com.appspot.ligafop.shared.Jogador;
import com.appspot.ligafop.shared.JogadorLiga;
import com.appspot.ligafop.shared.Jogo;
import com.appspot.ligafop.shared.Liga;
import com.appspot.ligafop.shared.Partida;
import com.appspot.ligafop.shared.PartidaDetalhe;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Implementation of Jogos API.
 */
@SuppressWarnings("serial")
public class JogosApiImpl extends RemoteServiceServlet implements JogosApi {
	private static final int RATING_INICIAL = 1500;

	// private final PersistenceManagerFactory pmf =
	// JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public String deleteJogos(String[] jogosToDelete) {
		String lastJogoDeletedId = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
		List<Jogo> jogos = getPersistedJogos(pm);

		HashMap<String, Jogo> managedJogoHash = new HashMap<String, Jogo>();
		for (int i = 0; i < jogos.size(); i++) {
			Jogo jogo = jogos.get(i);
			managedJogoHash.put(jogo.getId(), jogo);
		}

		for (int i = 0; i < jogosToDelete.length; i++) {
			String idOfJogoToDelete = jogosToDelete[i];
			Object toDelete = managedJogoHash.get(idOfJogoToDelete);
			if (toDelete != null) {
				pm.deletePersistent(toDelete);
				lastJogoDeletedId = idOfJogoToDelete;
			}
		}

		pm.close();
		return lastJogoDeletedId;
	}

	public String getLoginUrl() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL(getAppUrl());
	}

	public Liga[] getLigaList() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String email = null;
		if (user != null) {
			email = user.getEmail();
			String nickname = user.getNickname();
			PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
			Jogador j = getPersistedJogador(email, nickname, pm);
			j.setLogoutURL(userService.createLoginURL(getAppUrl()));

			// Marcar Adminitrador/Gerente
			if (j.getEmail().equals("saulo.achkar@gmail.com")) {
				j.setAdmin(true);
				j.setGerente(true);
			}

			List<Liga> ligas = new ArrayList<Liga>();

			// Obter lista de BoardGames
			BoardGame[] boards = getBoardGames();

			// Obter lista de JogadorLiga
			for (BoardGame board : boards) {
				Liga liga = obterLiga(pm, board);
				ligas.add(liga);
			}

			ligas.add(obterLiga(pm, Helper.createBoardGameLiga("-1")));
			ligas.add(obterLiga(pm, Helper.createBoardGameLiga("-2")));
			ligas.add(obterLiga(pm, Helper.createBoardGameLiga("-3")));

			orderLigaByPartidas(ligas);

			// Inclui Liga Com Detalhes Jogador
			ligas.add(0, new Liga(new BoardGame()));
			ligas.get(0).setJogador(j);

			pm.close();

			return ligas.toArray(new Liga[0]); // detachedJogos.toArray(new
												// Jogo[0]);
		} else {
			return null;
		}
	}

	private Liga obterLiga(PersistenceManager pm, BoardGame board) {
		Liga liga = new Liga(board);
		List<JogadorLiga> jogadoresLiga = (List<JogadorLiga>) getPersistedJogadorLigaByBoarGame(
				board.getCodigoBGG(), pm);
		orderJogadorLigaByRating(jogadoresLiga);
		ArrayList<JogadorLiga> listaJogadores = new ArrayList<JogadorLiga>();
		for (JogadorLiga jogadorLiga : jogadoresLiga) {
			listaJogadores.add(jogadorLiga);
			Double pontuacaoMaximaLiga = liga.getPontuacaoMaxima();
			Double pontuacaoMaximaJogador = jogadorLiga.getPontuacaoMaxima();
			if ((pontuacaoMaximaLiga == null ? 0 : pontuacaoMaximaLiga) < (pontuacaoMaximaJogador == null ? 0
					: pontuacaoMaximaJogador)) {
				liga.setPontuacaoMaxima(jogadorLiga.getPontuacaoMaxima());
			}
		}
		liga.setJogadores(listaJogadores);
		liga.setPartidas(getQuantidadePartidasByBoardGame(board.getCodigoBGG(), pm));
		return liga;
	}

	private void orderJogadorLigaByRating(List<JogadorLiga> jogadoresLiga) {
		Collections.sort(jogadoresLiga, new Comparator<JogadorLiga>() {
			public int compare(JogadorLiga o1, JogadorLiga o2) {
				return o1.getRating() > o2.getRating() ? -1 : 1;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private int getQuantidadePartidasByBoardGame(String codigoBGG, PersistenceManager pm) {
		Query query = pm.newQuery(Partida.class);
		query.setFilter("codigoBGG == codigoBGGParam");
		query.declareParameters("String codigoBGGParam");
		return ((List<Partida>) query.execute(codigoBGG)).size();
	}

	private void orderLigaByPartidas(List<Liga> ligas) {
		Collections.sort(ligas, new Comparator<Liga>() {
			public int compare(Liga l1, Liga l2) {
				if (l1.getBoardGame().getCodigoBGG().equals("-1"))
					return -1;
				if (l2.getBoardGame().getCodigoBGG().equals("-1"))
					return 1;
				if (l1.getBoardGame().getCodigoBGG().equals("-2"))
					return -1;
				if (l2.getBoardGame().getCodigoBGG().equals("-2"))
					return 1;
				if (l1.getBoardGame().getCodigoBGG().equals("-3"))
					return -1;
				if (l2.getBoardGame().getCodigoBGG().equals("-3"))
					return 1;
				return l1.getPartidas() > l2.getPartidas() ? -1 : l1.getPartidas() < l2
						.getPartidas() ? 1
						: l1.getJogadores().size() > l2.getJogadores().size() ? -1 : l1
								.getJogadores().size() < l2.getJogadores().size() ? 1 : l1
								.getBoardGame().getName().compareTo(l2.getBoardGame().getName());
			}
		});
	}

	public Jogador[] getJogadorList() {
		PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
		List<Jogador> detachedJogadores = null;

		List<Jogador> persistedJogadores = getPersistedJogadores(pm);

		detachedJogadores = (List<Jogador>) pm.detachCopyAll(persistedJogadores);
		pm.close();

		orderByNickname(detachedJogadores);

		return detachedJogadores.toArray(new Jogador[0]);
	}

	private void orderByNickname(List<Jogador> jogadores) {
		Collections.sort(jogadores, new Comparator<Jogador>() {
			public int compare(Jogador j1, Jogador j2) {
				return j1.getNickname().compareToIgnoreCase(j2.getNickname());
			}
		});
	}

	public Jogo[] getJogoList() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String email = null;
		if (user != null) {
			email = user.getEmail();
			String nickname = user.getNickname();

			PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
			List<Jogo> detachedJogos = null;

			List<Jogo> persistedJogos = getPersistedJogos(pm);
			Jogador j = getPersistedJogador(email, nickname, pm);

			detachedJogos = (List<Jogo>) pm.detachCopyAll(persistedJogos);
			pm.close();

			Jogo metaJogo = new Jogo();
			metaJogo.setEmail(j.getEmail());
			metaJogo.setNickname(j.getNickname());
			metaJogo.setLogoutURL(userService.createLogoutURL(userService
					.createLoginURL(getAppUrl())));
			metaJogo.setRating(j.getRating());
			detachedJogos.add(0, metaJogo);
			return detachedJogos.toArray(new Jogo[0]);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public BoardGame[] getBoardGames() {
		PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
		Query query = pm.newQuery(BoardGame.class);
		query.setOrdering("name asc");
		return ((List<BoardGame>) pm.detachCopyAll((List<BoardGame>) query.execute()))
				.toArray(new BoardGame[0]);
	}

	public String persistJogo(Jogo jogo) {
		if (jogo.getId() == null) {
			return persistNewJogo(jogo);
		} else {
			return updateExistingJogo(jogo);
		}
	}

	private String getAppUrl() {
		String servletUrl = getThreadLocalRequest().getRequestURL().toString();
		String resourcePath = getThreadLocalRequest().getRequestURI();
		return servletUrl.replace(resourcePath, "");
	}

	@SuppressWarnings("unchecked")
	private List<JogadorLiga> getPersistedJogadorLigaByBoarGame(String codigoBGG,
			PersistenceManager pm) {
		Query query = pm.newQuery(JogadorLiga.class);
		query.setFilter("codigoBGG == codigoBGGParam");
		query.declareParameters("String codigoBGGParam");
		List<JogadorLiga> listaJogadorLiga = (List<JogadorLiga>) query.execute(codigoBGG);
		for (JogadorLiga jogadorLiga : listaJogadorLiga) {
			Jogador persistedJogador = pm.getObjectById(Jogador.class, jogadorLiga.getEmail());
			jogadorLiga.setNickname(persistedJogador.getNickname());
		}
		return listaJogadorLiga;
	}

	@SuppressWarnings("unchecked")
	private List<Jogo> getPersistedJogos(PersistenceManager pm) {
		Query query = pm.newQuery(Jogo.class);
		query.setOrdering("nome desc");
		return (List<Jogo>) query.execute();
	}

	private String persistNewJogo(Jogo newJogo) {
		PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
		pm.makePersistent(newJogo);
		pm.close();
		return newJogo.getId();
	}

	@Override
	public String persistJogo(BoardGame boardGame) {
		PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
		pm.makePersistent(boardGame);
		pm.close();
		return boardGame.getCodigoBGG();
	}

	public Liga[] persistPartida(Partida partida) {
		Liga[] ligas = new Liga[2];
		BoardGame bg = persistPartidaLiga(partida);

		ligas[0] = obterLiga(PMF.get().getPersistenceManager(), bg);

		String codigoLiga = null;
		if (bg.getAverageWeight() < 2) {
			codigoLiga = "-1";
		} else if (bg.getAverageWeight() < 3) {
			codigoLiga = "-2";
		} else {
			codigoLiga = "-3";
		}
		partida.setCodigoBGG(codigoLiga);
		partida.setId(null);
		for (PartidaDetalhe d : partida.getDetalhes()) {
			d.setId(null);
		}
		persistPartidaLiga(partida);

		ligas[1] = obterLiga(PMF.get().getPersistenceManager(),
				Helper.createBoardGameLiga(codigoLiga));

		return ligas;
	}

	private BoardGame persistPartidaLiga(Partida partida) {
		PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
		String codigoBGG = partida.getBoardGame().getCodigoBGG();
		String codigoLiga = partida.getCodigoBGG();

		BoardGame bg = getPersistedBoardGame(codigoBGG, pm);

		partida.setBoardGame(pm.detachCopy(bg));
		partida.setData(new Date());

		// Obtém jogadores da liga correspondente
		for (PartidaDetalhe detalhe : partida.getDetalhes()) {
			int rating = RATING_INICIAL;
			if (!detalhe.getEmail().equals("")) {
				JogadorLiga persistedLiga = getPersistedJogadorLiga(detalhe.getEmail(), codigoLiga,
						pm);
				if (persistedLiga != null)
					rating = persistedLiga.getRating();
			}
			detalhe.setRatingAntes(rating);
		}

		// Calcula rating da liga
		MultiELO.calculaRatings(partida);

		// Persiste a partida (liga)
		pm.makePersistent(partida);

		// Persiste (cria/atualiza) os dados da liga
		for (PartidaDetalhe detalhe : partida.getDetalhes()) {
			if (!detalhe.getEmail().equals("")) {
				JogadorLiga persistedLiga = getPersistedJogadorLiga(detalhe.getEmail(), codigoLiga,
						pm);
				if (persistedLiga == null) {
					persistedLiga = new JogadorLiga(detalhe.getRatingDepois(), detalhe.getEmail(),
							codigoLiga, detalhe.getPosicao() == 1 ? 1 : 0,
							detalhe.getPontuacao() == null ? 0 : detalhe.getPontuacao());
					pm.makePersistent(persistedLiga);
				} else {
					// Conta vitórias
					persistedLiga.setRating(detalhe.getRatingDepois());
					persistedLiga.setPartidas(persistedLiga.getPartidas() + 1);
					if (detalhe.getPosicao() == 1) {
						persistedLiga.setVitorias(persistedLiga.getVitorias() + 1);
					}

					// Decide pontuação máxima
					Double pontuacaoMaximaPersited = persistedLiga.getPontuacaoMaxima();
					Double pontuacao = detalhe.getPontuacao();
					if ((pontuacaoMaximaPersited == null ? 0 : pontuacaoMaximaPersited) < (pontuacao == null ? 0
							: pontuacao)) {
						persistedLiga.setPontuacaoMaxima(pontuacao);
					}
				}
			}
		}

		pm.close();
		return bg;
	}

	@SuppressWarnings("unchecked")
	private JogadorLiga getPersistedJogadorLiga(String email, String codigoBGG,
			PersistenceManager pm) {
		JogadorLiga result = null;
		Query query = pm.newQuery(JogadorLiga.class);
		query.setFilter("email == emailParam");
		query.declareParameters("String emailParam");
		List<JogadorLiga> results = (List<JogadorLiga>) query.execute(email);
		for (JogadorLiga liga : results) {
			if (liga.getCodigoBGG().equals(codigoBGG)) {
				result = liga;
				break;
			}
			result = null;
		}
		return result;
	}

	private Jogador getPersistedJogador(String email, String nickname, PersistenceManager pm) {
		Jogador j = null;
		try {
			j = (Jogador) pm.getObjectById(Jogador.class, email);
		} catch (JDOObjectNotFoundException e) {
			if (j == null) {
				j = new Jogador(email, nickname, RATING_INICIAL);
				pm.makePersistent(j);
				j.setPrimeiroAcesso(true);
			}
		}
		return j;

	}

	private BoardGame getPersistedBoardGame(String codigoBGG, PersistenceManager pm) {
		return (BoardGame) pm.getObjectById(BoardGame.class, codigoBGG);

	}

	private String updateExistingJogo(Jogo existingJogo) {
		String jogoId = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Jogo managedJogo = (Jogo) pm.getObjectById(Jogo.class, existingJogo.getId());
			if (managedJogo != null) {
				managedJogo.setNome(existingJogo.getNome());
				managedJogo.setKFactor(existingJogo.getKFactor());
				managedJogo.setDFactor(existingJogo.getDFactor());
				jogoId = managedJogo.getId();
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			pm.close();
		}
		return jogoId;
	}

	public String persistJogador(Jogador jogador) {
		String email = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();// pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Jogador managedJogador = (Jogador) pm.getObjectById(Jogador.class, jogador.getEmail());
			if (managedJogador != null) {
				managedJogador.setNickname(jogador.getNickname());
				email = managedJogador.getEmail();
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			pm.close();
		}
		return email;
	}

	@SuppressWarnings("unchecked")
	private List<Jogador> getPersistedJogadores(PersistenceManager pm) {
		return (List<Jogador>) pm.newQuery(Jogador.class).execute();
	}

	@Override
	public String persistJogo(Liga liga) {
		// TODO Auto-generated method stub
		return null;
	}

	public BoardGame[] getBoardGame(BoardGame boardGame) {
		BoardGame[] retorno = null;
		if (!boardGame.getCodigoBGG().equals("")) {
			BGGXML.obterDadosBGG(boardGame);
			retorno = new BoardGame[] { boardGame.getName() == null ? null : boardGame };
		} else {
			ArrayList<BoardGame> listaJogos = BGGXML.obterListaJogos(boardGame.getName());
			if (listaJogos != null) {
				retorno = listaJogos.toArray(new BoardGame[0]);
			}
		}
		return retorno;
	}

}
