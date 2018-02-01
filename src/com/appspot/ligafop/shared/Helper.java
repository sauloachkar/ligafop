package com.appspot.ligafop.shared;


public class Helper {

	public static BoardGame createBoardGameLiga(String codigoLiga) {

		if (codigoLiga.equals("-1")) {
			return new BoardGame(codigoLiga, "Liga Party", "", 0, 1);
		}
		if (codigoLiga.equals("-2")) {
			return new BoardGame(codigoLiga, "Liga Soft", "", 0, 2);
		}
		if (codigoLiga.equals("-3")) {
			return new BoardGame(codigoLiga, "Liga Hard", "", 0, 3);
		}
		return null;
	}

}
