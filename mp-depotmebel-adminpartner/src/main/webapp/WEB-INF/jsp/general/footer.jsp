<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Main Footer -->
<footer class="main-footer">
	
	<!-- <label class="text-red">Anything you want</label> -->
	<c:choose>
		<c:when test="${not empty footermsg}">
			<strong style="color: red;">${footermsg}</strong>
		</c:when>
		<c:otherwise>
			<strong>-</strong>
		</c:otherwise>
	</c:choose>
	
    
    <div class="pull-right hidden-xs">
    	<strong>Copyright &copy; 2016 <a href="#">Company</a>.</strong> All rights reserved.
    </div>
</footer>