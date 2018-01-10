<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Starter</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.min.css" />">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/AdminLTE.min.css" />">
  <link rel="stylesheet" href="<c:url value="/lte/dist/css/skins/skin-blue.min.css" />">
  <%-- <c:url value="/lte/" /> --%>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

  		<%@ include file="general/header.jsp" %>
  		<%@ include file="general/sidebar-left.jsp" %>

  		<div class="content-wrapper">
    		<section class="content-header">
      			<h1>
        			Page Header
        			<small>Optional description</small>
      			</h1>
      			<ol class="breadcrumb">
        			<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        			<li class="active">Here</li>
      			</ol>
    		</section>

    		<section class="content">
      			<!-- Your Page Content Here -->
    		</section>
  		</div>

  		<%@ include file="general/footer.jsp" %>
  
	</div>

<%-- <c:url value="/lte/" /> --%>
<script src="<c:url value="/lte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" /> "></script>
<script src="<c:url value="/lte/dist/js/app.min.js" />"></script>

<!-- REQUIRED JS SCRIPTS -->



</body>
</html>
