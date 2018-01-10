<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar user panel (optional) -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="<c:url value="/lte/dist/img/user2-160x160.jpg" />" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${sessionVendor_.user.name}</p>
          <!-- Status -->
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>

      <!-- search form (Optional) -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form>
      <!-- /.search form -->

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu">
        <li class="header">MENUS</li>
        <li class="${mnActiveDashboard}"><a href="<c:url value="/home/" />"><i class="fa fa-dashboard"></i> <span>Dashboard</span></a></li>
        <li class="${mnActiveProduct}"><a href="<c:url value="/product/" />"><i class="fa fa-link"></i> <span>Product</span></a></li>
        <li class="${mnActiveOrderVdr}"><a href="<c:url value="/order/" />"><i class="fa fa-link"></i> <span>Order</span></a></li>
        <li class="header">SETTINGS</li>
        <li class="${mnActiveEditProf}"><a href="#"><i class="fa fa-link"></i> <span>Edit Profile</span></a></li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>