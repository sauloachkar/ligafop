package com.appspot.ligafop.client;

import com.appspot.ligafop.shared.BoardGame;
import com.appspot.ligafop.shared.Jogador;
import com.appspot.ligafop.shared.Jogo;
import com.appspot.ligafop.shared.Liga;
import com.appspot.ligafop.shared.Partida;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous version of Jogos API.
 */
public interface JogosApiAsync {
	void deleteJogos(String[] jogosToDelete, AsyncCallback<String> callBack);

	void getLoginUrl(AsyncCallback<String> callBack);

	void getJogoList(AsyncCallback<Jogo[]> callBack);

	void persistJogo(Liga liga, AsyncCallback<String> callBack);

	void getJogadorList(AsyncCallback<Jogador[]> asyncCallback);

	void persistPartida(Partida partida, AsyncCallback<Liga[]> asyncCallback);

	void getLigaList(AsyncCallback<Liga[]> asyncCallback);

	void getBoardGames(AsyncCallback<BoardGame[]> asyncCallback);

	void persistJogo(BoardGame boardGame, AsyncCallback<String> callBack);

	void getBoardGame(BoardGame potentialJogo, AsyncCallback<BoardGame[]> asyncCallback);

	void persistJogador(Jogador jogador, AsyncCallback<String> asyncCallback);
}
