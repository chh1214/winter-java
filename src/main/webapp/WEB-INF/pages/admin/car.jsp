<%@ page language="java" contentType="text/html; charset=UTF-8"%> 
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Document</title>
	<link href="/static/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="/static/jquery-1.12.4/jquery.min.js"></script>
    <script src="/static/bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="/static/plugins/template.js"></script>
    <script src="/static/plugins/jquery.page.js"></script>
    <style>
        *{ margin:0; padding:0; list-style:none;}
        a{ text-decoration:none;}
        a:hover{ text-decoration:none;}
        .tcdPageCode{padding: 15px 20px;text-align: left;color: #ccc;text-align:center;}
        .tcdPageCode a{display: inline-block;color: #428bca;display: inline-block;height: 25px; line-height: 25px;  padding: 0 10px;border: 1px solid #ddd; margin: 0 2px;border-radius: 4px;vertical-align: middle;}
        .tcdPageCode a:hover{text-decoration: none;border: 1px solid #428bca;}
        .tcdPageCode span.current{display: inline-block;height: 25px;line-height: 25px;padding: 0 10px;margin: 0 2px;color: #fff;background-color: #428bca; border: 1px solid #428bca;border-radius: 4px;vertical-align: middle;}
        .tcdPageCode span.disabled{ display: inline-block;height: 25px;line-height: 25px;padding: 0 10px;margin: 0 2px; color: #bfbfbf;background: #f2f2f2;border: 1px solid #bfbfbf;border-radius: 4px;vertical-align: middle;}
    </style>
<body>
    <div class="container-fluid">
        <div class="row" style="margin:20px 0">
            <div class="col-md-8" style="padding-left:0">
                <div class="input-group" style="width: 36%;">
                  <input type="text" class="form-control" id="keyword" placeholder="Search for...">
                  <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="search">Search</button>
                  </span>
                </div>
            </div>
            <div class="col-md-4" style="padding-right:0">
                <button type="button" class="btn btn-success pull-right">新增</button>
            </div>
        </div>
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <td>编号</td>
                    <td>品牌</td>
                    <td>价格</td>
                    <td style="width: 10%">操作</td>
                </tr>
            </thead>
            <tbody id="tableList">
                
            </tbody>
        </table>
        <div class="tcdPageCode"></div>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">New a car</h4>
          </div>
          <div class="modal-body">
          <form id="addCarForm">
                <div class="form-group">
                    <label for="name">No</label>
                    <input type="text" class="form-control" id="no" placeholder="No" name="id">
                </div>
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" class="form-control" id="name" placeholder="Name" name="name">
                </div>
                <div class="form-group">
                    <label for="name">Price</label>
                    <input type="text" class="form-control" id="price" placeholder="Price" name="price">
                </div>
              </div>
          </form>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary btn-modal" id="saveCar">Save changes</button>
            <button type="button" class="btn btn-primary btn-modal" id="updateCar">Save changes</button>
          </div>
        </div>
      </div>
    </div>
    
    <script type="text/javascript">
    var carId;
    $(document.body)
        .on('click', '#search', function () {
            $.ajax({
             type: "POST",
             url: "/car/showList",
             data: {pageNum: 1, keyWord:$('#keyword').val()},
             dataType: "json",
             success: function(data){
                var html = template('carList', {list: data.list});
                document.getElementById('tableList').innerHTML = html;
                createPageS(data.count)
            }   
        })
        })
        .on('click', '.btn-success', function () {
            $('#myModal').modal('show')
            $('.btn-modal').hide()
            $('#saveCar').show()
        })
        .on('click', '#saveCar', function () {
            $.ajax({
             type: "POST",
             url: "/car/addCar",
             data: $('#addCarForm').serialize(),
             dataType: "json",
             success: function(data){
                window.location.reload()
                $('#myModal').modal('hide')
            }   
        })
        })
        .on('click', '.btn-danger', function () {
            $.ajax({
             type: "POST",
             url: "/car/deleteCar",
             data: {id:$(this).data('id')},
             dataType: "json",
             success: function(data){
                window.location.reload()
            }   
        })
        })
        .on('click', '.btn-edit', function () {
            carId = $(this).data('id')
            console.log(carId)
            $('#myModal').modal('show')
            $('.btn-modal').hide()
            $('#updateCar').show()
        })
        .on('click', '#updateCar', function () {
            console.log(carId)
            $.ajax({
             type: "POST",
             url: "/car/updateCar",
             data:{
                id:carId,
                name:$('#name').val(),
                price:$('#price').val()
             },
             dataType: "json",
             success: function(data){
                window.location.reload()       
            }   
        })
        })
    	$.ajax({
    		 type: "POST",
             url: "/car/showList",
             data: {pageNum: 1, keyWord:""},
             dataType: "json",
             success: function(data){
            	var html = template('carList', {list: data.list});
                document.getElementById('tableList').innerHTML = html;
                    createPageS(data.count)                    
            }	
    	})
    function createPageS(pageCount){
        if(pageCount<5){
            $(".tcdPageCode").hide()
            return false;
        }
        $(".tcdPageCode").show()
        $(".tcdPageCode").createPage({
            pageCount:Math.ceil(pageCount/5),
            current:1,
            backFn:function(index){
               $.ajax({
                     type: "POST",
                     url: "/car/showList",
                     data: {pageNum: index, keyWord:""},
                     dataType: "json",
                     success: function(data){
                        var html = template('carList', {list: data.list});
                        document.getElementById('tableList').innerHTML = html;         
                    }   
                }) 
            }
        })  
    }
    	
    </script>
    <script id="carList" type="text/html">
            {{each list value index}}
                <tr>
                    <td>{{value.id}}</td>
                    <td>{{value.name}}</td>
                    <td>{{value.price}}</td>
                    <td>
                        <button type="button" class="btn btn-primary btn-edit" data-id="{{value.id}}">编辑</button>
                        <button type="button" class="btn btn-danger" data-id="{{value.id}}">删除</button>
                    </td>
                </tr>
            {{/each}}
    </script>
    
</body>
</html>