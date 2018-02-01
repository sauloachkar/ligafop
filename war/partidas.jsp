<%@page import="com.appspot.ligafop.shared.BoardGame"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.appspot.ligafop.shared.HistoricoPartida"%>
<%@ page import="com.appspot.ligafop.shared.Jogador"%>
<%@ page import="com.appspot.ligafop.shared.BoardGame"%>
<%@ page import="com.appspot.ligafop.shared.HistoricoPartidaDetalhe"%>
<%@ page import="com.appspot.ligafop.server.PartidasUtils"%>
<%@ page import="java.util.List"%>

<html>
<body>
	<a href="admin.jsp">< Voltar</a><br>
	<h2>Histórico de Partidas</h2>
	<%
		String filtroJogador = request.getParameter("Jogador");
		if (filtroJogador == "")
			filtroJogador = null;
		String filtroBoard = request.getParameter("Board");
		if (filtroBoard == "")
			filtroBoard = null;
		List<Jogador> jogadores = PartidasUtils.getJogadores();
		List<BoardGame> boards = PartidasUtils.getBoards();
		List<HistoricoPartida> partidas = PartidasUtils.getPartidas(filtroJogador, filtroBoard);
	%>

	<form action="/partidas.jsp">
		Jogador: <select name="Jogador">
			<option value="">[TODOS]</option>
			<%
				for (Jogador jogador : jogadores) {
			%>
			<option value="<%=jogador.getEmail()%>"
				<%=jogador.getEmail().equals(filtroJogador) ? "selected" : ""%>><%=jogador.getNickname()%></option>
			<%
				}
			%>
		</select><br> Board Game: <select name="Board">
			<option value="">[TODOS]</option>
			<%
				for (BoardGame board : boards) {
			%>
			<option value="<%=board.getCodigoBGG()%>"
				<%=board.getCodigoBGG().equals(filtroBoard) ? "selected" : ""%>><%=board.getName()%></option>
			<%
				}
			%>
		</select><br> <input value="Filtrar" type="submit" />
	</form>

	<%
		if (partidas.isEmpty()) {
	%>
	Nenhuma partida encontrada.
	<%
		} else {
			for (HistoricoPartida partida : partidas) {
	%><table border="1" style="border: 1">
		<tr style="background-color: lightgray;">
			<th>Liga</th>
			<th>Data</th>
			<th>Jogo</th>
			<th>Aver. Weight</th>
			<th>Play. Time</th>
		</tr>
		<tr>
			<td><%=partida.getLiga()%></td>
			<td><%=new SimpleDateFormat("HH:mm:ss dd/MM/yyy").format(partida.getData())%></td>
			<td><%=partida.getBoardGame().getName()%></td>
			<td><%=partida.getBoardGame().getAverageWeight()%></td>
			<td><%=partida.getBoardGame().getPlayingTime()%>
		</tr>
	</table>
	<table border="1" style="border: 1">
		<tr style="background-color: lightgray;">
			<th>#</th>
			<th>Jogador</th>
			<th>Rating Liga</th>
			<th>Rating Jogo</th>
			<th>Pontuação</th>
		</tr>
		<%
			for (HistoricoPartidaDetalhe pd : partida.getDetalhes()) {
		%><tr
			<%=pd.getEmail().equals(filtroJogador) ? "style=\"background-color: yellow;\""
								: ""%>>
			<td><%=pd.getPosicao()%></td>
			<td><%=pd.getNickname() == null ? "[Não Cadastrado]" : pd.getNickname()%></td>
			<td><%=pd.getRatingAntesLiga()%> -> <%=pd.getRatingDepoisLiga()%></td>
			<td><%=pd.getRatingAntes()%> -> <%=pd.getRatingDepois()%></td>
			<td><%=pd.getPontuacao() == null ? "" : pd.getPontuacao()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<br>
	<%
		}
		}
	%>
</body>
</html>
