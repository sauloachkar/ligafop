package com.appspot.ligafop.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.appspot.ligafop.shared.BoardGame;
import com.appspot.ligafop.shared.HistoricoPartida;
import com.appspot.ligafop.shared.HistoricoPartidaDetalhe;
import com.appspot.ligafop.shared.Jogador;
import com.appspot.ligafop.shared.Partida;
import com.appspot.ligafop.shared.PartidaDetalhe;

public class PartidasUtils {

	@SuppressWarnings("unchecked")
	public static List<Jogador> getJogadores() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Jogador.class);
		query.setOrdering("nickname ASC");
		List<Jogador> jogadores = (List<Jogador>) query.execute();
		pm.detachCopyAll(jogadores);

		Collections.sort(jogadores, new Comparator<Jogador>() {
			public int compare(Jogador o1, Jogador o2) {
				return o1.getNickname().compareToIgnoreCase(o2.getNickname());
			}
		});
		return jogadores;
	}
	
	@SuppressWarnings("unchecked")
	public static List<BoardGame> getBoards() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(BoardGame.class);
		query.setOrdering("name ASC");
		List<BoardGame> boards = (List<BoardGame>) query.execute();
		pm.detachCopyAll(boards);
		return boards;
	}

	@SuppressWarnings("unchecked")
	public static List<HistoricoPartida> getPartidas(String filtroJogador, String filtroBoard) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Partida.class);

		query.setOrdering("data DESC");
		List<Partida> partidas = (List<Partida>) query.execute();
		pm.detachCopyAll(partidas);

		query = pm.newQuery(BoardGame.class);
		List<BoardGame> boards = (List<BoardGame>) query.execute();
		pm.detachCopyAll(boards);

		query = pm.newQuery(Jogador.class);
		List<Jogador> jogadores = (List<Jogador>) query.execute();
		pm.detachCopyAll(jogadores);

		ArrayList<HistoricoPartida> listaHistorico = new ArrayList<HistoricoPartida>();
		HistoricoPartida historicoPartida = null;

		for (Partida partida : partidas) {
			if (filtroJogador != null) {
				boolean tem = false;
				for (PartidaDetalhe pd : partida.getDetalhes()) {
					if (filtroJogador.equals(pd.getEmail())) {
						tem = true;
						break;
					}
				}
				if (!tem) continue;
			}
			Integer codigoBGG = Integer.valueOf(partida.getCodigoBGG());
			if (codigoBGG < 0) {
				historicoPartida = new HistoricoPartida(partida.getCodigoBGG());
				historicoPartida.setDetalhes(new ArrayList<HistoricoPartidaDetalhe>());
				Calendar c = Calendar.getInstance();
				c.setTime(partida.getData());
				c.add(Calendar.HOUR, -3);
				historicoPartida.setData(c.getTime());
				if (codigoBGG == -1) {
					historicoPartida.setLiga("Party");
				} else if (codigoBGG == -2) {
					historicoPartida.setLiga("Soft");
				} else if (codigoBGG == -3) {
					historicoPartida.setLiga("Hard");
				}
				for (PartidaDetalhe pd : partida.getDetalhes()) {
					HistoricoPartidaDetalhe hpd = new HistoricoPartidaDetalhe();
					hpd.setEmail(pd.getEmail());
					hpd.setPontuacao(pd.getPontuacao());
					hpd.setPosicao(pd.getPosicao());
					hpd.setRatingAntesLiga(pd.getRatingAntes());
					hpd.setRatingDepoisLiga(pd.getRatingDepois());
					historicoPartida.getDetalhes().add(hpd);
				}
			} else {
				for (BoardGame boardGame : boards) {
					if (boardGame.getCodigoBGG().equals(partida.getCodigoBGG())) {
						historicoPartida.setBoardGame(boardGame);
						break;
					}
				}
				for (HistoricoPartidaDetalhe hpd : historicoPartida.getDetalhes()) {
					for (Jogador jogador : jogadores) {
						if (jogador.getEmail().equals(hpd.getEmail())) {
							hpd.setNickname(jogador.getNickname());
							break;
						}
					}
					for (PartidaDetalhe pd : partida.getDetalhes()) {
						if (pd.getEmail().equals(hpd.getEmail())) {
							hpd.setRatingAntes(pd.getRatingAntes());
							hpd.setRatingDepois(pd.getRatingDepois());
							break;
						}
					}
				}
				if (filtroBoard == null || filtroBoard.equals(partida.getCodigoBGG())) {
					listaHistorico.add(historicoPartida);
				}
			}

		}
		return (List<HistoricoPartida>) listaHistorico;
	}
}
