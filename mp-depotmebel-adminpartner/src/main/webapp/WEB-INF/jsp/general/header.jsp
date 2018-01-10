<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header class="main-header">

	<!-- Logo -->
	<a href="index2.html" class="logo"> 
		<!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><b>C</b>SM</span> 
		<!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>CENT</b>Smile</span>
	</a>

	<!-- Header Navbar -->
	<nav class="navbar navbar-static-top" role="navigation">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button"> 
			<span class="sr-only">Toggle navigation</span>
		</a>
		<!-- Navbar Right Menu -->
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				
				<!-- Tasks Menu -->
				<li class="dropdown tasks-menu">
		            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
		              <i class="fa fa-flag-o"></i>
		              <span class="label label-danger">${taskNum!=null?taskNum:0}</span>
		            </a>
		            <ul class="dropdown-menu">
		              <li class="header">You have ${taskNum!=null?taskNum:0} tasks</li>
		              <li>
		              	<ul class="menu">
		              	<c:forEach var="obj" items="${taskList}">
		              		<li><a href="<c:url value="${obj.link}"/>"><i class="fa ${obj.icon} text-red"></i> ${obj.num} ${obj.label}</a></li>
		              	</c:forEach>
		              	</ul>
		              </li>
		              <%-- <li class="footer">
		                <a href="<c:url value="/order/qclist"/>">View all tasks</a>
		              </li> --%>
		            </ul>
		          </li>
				
				<!-- User Account Menu -->
				<li class="dropdown user user-menu">
					<!-- Menu Toggle Button --> 
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
						<!-- The user image in the navbar--> 
						<img src="<c:url value="/lte/dist/img/user2-160x160.jpg" />"
							class="user-image" alt="User Image"> 
							<!-- hidden-xs hides the username on small devices so only the image appears. -->
							<span class="hidden-xs">${session_.user.name}</span>
					</a>
					<ul class="dropdown-menu">
						<!-- The user image in the menu -->
						<li class="user-header">
							<img src="<c:url value="/lte/dist/img/user2-160x160.jpg" />" 
								class="img-circle" alt="User Image">
							<p> ${session_.user.name} - Web Developer <small>Member since Nov. 2012</small></p>
						</li>
						
						<!-- Menu Body -->
						<li class="user-body">
							<div class="row">
								<div class="col-xs-4 text-center">
									<a href="#">Followers</a>
								</div>
								<div class="col-xs-4 text-center">
									<a href="#">Sales</a>
								</div>
								<div class="col-xs-4 text-center">
									<a href="#">Friends</a>
								</div>
							</div> <!-- /.row -->
						</li>
						
						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-left">
								<a href="#" class="btn btn-default btn-flat">Profile</a>
							</div>
							<div class="pull-right">
								<a href="<c:url value="/login/logout"/>" class="btn btn-default btn-flat">Sign out</a>
							</div>
						</li>
					</ul>
				</li>
				<!-- Control Sidebar Toggle Button -->
				<!-- <li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li> -->
			</ul>
		</div>
	</nav>
</header>