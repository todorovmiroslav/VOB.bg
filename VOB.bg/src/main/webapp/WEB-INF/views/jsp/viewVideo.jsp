<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div id="top">  
		<%@ include file="logo.jsp" %>
	</div>
	<div id="menu">  
		<%@ include file="menu.jsp" %>
	</div>
	
	<h1 >${video.name}</h1>
	<div>
		<video width="220" height="200" controls>
			<source src= "/VOB.bg/videos/${video.path}" type="video/mp4">
		</video>
	</div>
	User: ${user.username} <br>
	Likes:${likes}
	Dislikes:${dislikes} <br>
	Views: ${views}<br>

	<div class="post" postid="10">
   	 <input type="button" value="Like" /> </input>
    	<input type="button" value="Dislike"> </input>
	</div>

