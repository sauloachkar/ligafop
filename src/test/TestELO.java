package test;

import java.util.ArrayList;

import com.appspot.ligafop.server.MultiELO;
import com.appspot.ligafop.shared.BoardGame;
import com.appspot.ligafop.shared.Partida;
import com.appspot.ligafop.shared.PartidaDetalhe;

import junit.framework.TestCase;

public class TestELO extends TestCase {

	Partida p;
	ArrayList<PartidaDetalhe> d;

	protected void setUp() throws Exception {
		super.setUp();
		p = new Partida();
		d = new ArrayList<PartidaDetalhe>();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		p.setDetalhes(d);
		MultiELO.calculaRatings(p);
		System.out.println("Jogo: " + p.getBoardGame().getName() + " | Time: "
				+ p.getBoardGame().getPlayingTime() + " | Weight: "
				+ p.getBoardGame().getAverageWeight());
		for (PartidaDetalhe pd : p.getDetalhes()) {
			System.out.println("Posição: " + pd.getPosicao()
					+ " | Rating Antes: " + pd.getRatingAntes() + " | Depois: "
					+ pd.getRatingDepois());
		}
		System.out.println();
	}

	public void testELOQwixxEm2() {
		p.setBoardGame(jogo(1));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
	}
	
	public void testELOQwixxEm2Vantagem() {
		p.setBoardGame(jogo(1));
		d.add(jogador("1", 1, 2000));
		d.add(jogador("2", 2, 1500));
	}
	
	public void testELOQwixxEm2Desvantagem() {
		p.setBoardGame(jogo(1));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 2000));
	}
	
	public void testELOQwixxEm3() {
		p.setBoardGame(jogo(1));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
		d.add(jogador("3", 3, 1500));
	}
	
	public void testELOQwixxEm4() {
		p.setBoardGame(jogo(1));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
		d.add(jogador("3", 3, 1500));
		d.add(jogador("4", 4, 1500));
	}
	
	public void testELOCatanEm2() {
		p.setBoardGame(jogo(2));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
	}
	
	public void testELOCatanEm3() {
		p.setBoardGame(jogo(2));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
		d.add(jogador("3", 3, 1500));
	}
	
	public void testELOCatanEm4() {
		p.setBoardGame(jogo(2));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
		d.add(jogador("3", 3, 1500));
		d.add(jogador("4", 4, 1500));
	}
	
	public void testELOBurgundyEm2() {
		p.setBoardGame(jogo(3));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
	}
	
	public void testELOBurgundyEm2Vantagem() {
		p.setBoardGame(jogo(3));
		d.add(jogador("1", 1, 2000));
		d.add(jogador("2", 2, 1500));
	}
	
	public void testELOBurgundyEm2Desvantagem() {
		p.setBoardGame(jogo(3));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 2000));
	}
	
	public void testELOBurgundyEm3() {
		p.setBoardGame(jogo(3));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
		d.add(jogador("3", 3, 1500));
	}
	
	public void testELOBurgundyEm4() {
		p.setBoardGame(jogo(3));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
		d.add(jogador("3", 2, 1500));
		d.add(jogador("4", 3, 1500));
	}
	
	public void testELOResistanceEm5Vermelho() {
		p.setBoardGame(jogo(4));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 1, 1500));
		d.add(jogador("3", 2, 1500));
		d.add(jogador("4", 2, 1500));
		d.add(jogador("5", 2, 1500));
	}
	
	public void testELOResistanceEm5Azul() {
		p.setBoardGame(jogo(4));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 1, 1500));
		d.add(jogador("3", 1, 1500));
		d.add(jogador("4", 2, 1500));
		d.add(jogador("5", 2, 1500));
	}
	
	public void testELODixit() {
		p.setBoardGame(jogo(4));
		d.add(jogador("1", 1, 1500));
		d.add(jogador("2", 2, 1500));
		d.add(jogador("3", 2, 1500));
		d.add(jogador("4", 3, 1500));
		d.add(jogador("5", 3, 1500));
		d.add(jogador("6", 4, 1500));
		d.add(jogador("7", 5, 1500));
		d.add(jogador("8", 6, 1500));
	}

	private BoardGame jogo(int i) {
		switch (i) {
		case 1:
			return new BoardGame("131260", "Qwixx", "", 15, 1.1884);
		case 2:
			return new BoardGame("13", "Catan", "", 90, 2.3839);
		case 3:
			return new BoardGame("84876", "Burgundy", "", 90, 3.0629);
		default:
			return new BoardGame("41114", "Resistance", "", 30, 1.6043);
		}

	}

	private PartidaDetalhe jogador(String email, int posicao, int ratingAntes) {
		PartidaDetalhe j = new PartidaDetalhe();
		j.setEmail(email);
		j.setPosicao(posicao);
		j.setRatingAntes(ratingAntes);
		return j;
	}

}
