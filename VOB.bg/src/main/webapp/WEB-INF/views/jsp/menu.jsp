<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	
	<table>
		<tr>
			<form action="main" method="get">
				<button>Home</button>
			</form>
			<form>
				<button>Videos</button>
			</form>
			<form action="profile" method="get">
				<button>Profile</button>
			</form>
			<form>
				<button>Playlists</button>
			</form>
			<form action="logout" method="post">
				<button type="submit">Logout</button>
			</form>
		</tr>
	</table>