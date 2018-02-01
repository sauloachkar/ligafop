<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.appspot.ligafop.server.GerentesUtils"%>
<%@page import="com.appspot.ligafop.shared.Jogador"%>
<%@page import="java.util.List"%>

<html>
<body>
	<a href="admin.jsp">< Voltar</a><br>
	<%
		String msg = request.getParameter("mensagem");
	%>
	<div class="msg"><%=msg != null ? msg : ""%></div>
	<h2>Lista de Gerentes</h2>
	<%
		List<Jogador> gerentes = GerentesUtils.getGerentes();
		List<Jogador> naoGerentes = GerentesUtils.getNaoGerentes();
		Jogador usuario = GerentesUtils.getUser();
		if (usuario.isAdmin()) {
	%>
	<form action="/gerentes" method="post">
		<select name="adicionarEmail">
			<%
				for (Jogador ng : naoGerentes) {
			%>
			<option value="<%=ng.getEmail()%>"><%=ng.getNickname()%></option>
			<%
				}
			%>
		</select> <input type="submit" value="Incluir Gerente">
	</form>
	<%
		}
		if (gerentes.size() == 0) {
	%>
	Nenhum gerente encontrado.<%
		} else {
	%>
	<table border="1" style="border: thin;">
		<tr>
			<th>Gerente</th><th>Remover</th>
		</tr>
		<%
			for (Jogador gerente : gerentes) {
		%><tr>
			<td><%=gerente.getNickname()%></td>
			<% if (usuario.isAdmin()) { %>
			<td><form action="/gerentes" method="post">
				<input type="submit" value="Remover Gerente"><input type="hidden" name="removerEmail" value="<%=gerente.getEmail()%>">
				</form>
			</td>
			<% } else { %>
			<td>Apenas Admins</td>
			<% } %>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>
</body>
</html>
