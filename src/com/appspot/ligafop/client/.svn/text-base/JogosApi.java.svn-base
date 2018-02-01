package com.appspot.ligafop.client;

import com.appspot.ligafop.shared.BoardGame;
import com.appspot.ligafop.shared.Jogador;
import com.appspot.ligafop.shared.Jogo;
import com.appspot.ligafop.shared.Liga;
import com.appspot.ligafop.shared.Partida;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * API for interacting with our jogos backend.
 */
@RemoteServiceRelativePath("api")
public interface JogosApi extends RemoteService {
	/**
	 * Deletes records for the specified jogos.
	 * 
	 * @param jogosToDelete
	 *            the IDs of jogos to delete
	 * @return the key of the last jogo deleted
	 */
	String deleteJogos(String[] jogosToDelete);

	/**
	 * Fetches login URL for users who are not logged in.
	 * 
	 * @return the login URL
	 */
	String getLoginUrl();

	/**
	 * Gets all jogos that have been persisted.
	 * 
	 * @return
	 */
	Jogo[] getJogoList();

	String persistJogo(Liga liga);

	Jogador[] getJogadorList();

	Liga[] persistPartida(Partida partida);

	Liga[] getLigaList();

	BoardGame[] getBoardGames();

	String persistJogo(BoardGame boardGame);

	BoardGame[] getBoardGame(BoardGame potentialJogo);

	String persistJogador(Jogador jogador);
}
