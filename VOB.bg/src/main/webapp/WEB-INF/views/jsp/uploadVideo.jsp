<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload video</title>
</head>
<body>
	<form action="upload" method="post" enctype="multipart/form-data">
		<input type="file"  name="videoFile" accept="video/*"/>  <!-- Only videos are accepted -->
		<input type="submit"/>
		
	</form>
</body>
</html>