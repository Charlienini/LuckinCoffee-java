<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Conter-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="bootstrap-3.4.1/css/bootstrap.min.css"/>
<title>添加咖啡</title>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">瑞幸咖啡管理系统'添加咖啡菜单'</a>
        </div>

        
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">

                <li><a href="main.jsp">咖啡menu <span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span></a></li>
           
                
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="managlogin.jsp">切换管理员<span class="glyphicon glyphicon-send" aria-hidden="true"></a></span></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


	<div class="container">
		
		<div class="panel-body">
			<form action="CoffeeServlet?action=add" method="post">
				<div class="form-group">
					<label>咖啡名称</label>
                    <input type="text" class="form-control" placeholder="咖啡名称" name="coffeename"/>
                </div>
                <div class="form-group">
                	<label>杯型</label>
                    <select class="form-control" name="size">
                    	<option value="超大杯">超大杯</option>
                    	<option value="大杯">大杯</option>
                    	<option value="中杯">中杯</option>
                	</select>
                </div>
                
                <div class="form-group">
                	<label>温度</label>
                    <select class="form-control" name="temp">
                    	<option value="冰">冰</option>
                    	<option value="去冰">去冰</option>
                    	<option value="热">热</option>
                	</select>
                </div>
                
                <div class="form-group">
                	<label>糖度</label>
                    <select class="form-control" name="sweet">
                    	<option value="不另外加糖">不另外加糖</option>
                    	<option value="半糖">半糖</option>
                    	<option value="标准糖">标准糖</option>
                	</select>
                </div>
   
                <div class="form-group">
					<label>价格</label>
                    <input type="text" class="form-control" placeholder="咖啡价格" name="price"/>
                </div>
			
				<button type="submit" class="btn btn-primary">提 交</button>
			</form>
		</div>
</body>
</html>