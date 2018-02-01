package test;

import java.util.ArrayList;

import com.appspot.ligafop.server.BGGXML;
import com.appspot.ligafop.shared.BoardGame;

import junit.framework.TestCase;

public class TestGBGGXML extends TestCase {
	
	public void testObterListaJogos() {
		ArrayList<BoardGame> listaJogos = BGGXML.obterListaJogos("wonders");
		for (BoardGame boardGame : listaJogos) {
			System.out.println(boardGame.getCodigoBGG() + " " + boardGame.getName());
		}
	}
	

}
