<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html>
<head>
<title>图片上传</title>

<link href="${pageContext.request.contextPath }/static/lib/webuploader/webuploader.css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath }/static/lib/assets/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/lib/bootstrap/select/bootstrap-select.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/lib/layer/theme/default/layer.css">

<script type="text/javascript" src="${pageContext.request.contextPath }/static/lib/assets/js/jquery-2.1.4.min.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath }/static/lib/webuploader/webuploader.js"></script>
<script src="${pageContext.request.contextPath }/static/lib/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath }/static/lib/bootstrap/select/bootstrap-select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/lib/bootstrap/select/i18n/defaults-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/lib/layer/layer.js"></script>
</head>
<body>
<div class="container" style="margin-top: 20px">
    <div class="alert alert-info">可以一次上传多个大文件</div>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-4">		  
			<ul class="list-group">
				<li class="list-group-item">图片上传</li>
			 </ul>
		 </div>
		<div class="col-md-6">
			<div id="image-uploader" class="container">
				<div class="btns container">
		            <div id="image-picker" class="webuploader-container" style="float: left; margin-right: 10px">
		                <div>选择图片<input type="file" name="file" class="webuploader-element-invisible" multiple="multiple">
		                </div>
		            </div>
		            <div id="image-UploadBtn" class="webuploader-pick" style="float: left;">开始上传</div>
			    </div>
		    </div>
		</div>
		<div class="col-md-2">
			<div id="image-fileList" class="uploader-list"></div> 
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<ul class="list-group">
				<li class="list-group-item">文本文件上传</li>
			</ul>
		</div>
		<div class="col-md-6">
			 <div id="text-uploader" class="container">
		        <div class="btns container">
		            <div id="text-picker" class="webuploader-container" style="float: left; margin-right: 10px">
		                <div>
		                    选择文件
		                    <input type="file" name="file" class="webuploader-element-invisible" multiple="multiple">
		                </div>
		            </div>
		            <div id="text-UploadBtn" class="webuploader-pick" style="float: left">开始上传</div>
		        </div>
		    </div>
		</div>
		<div class="col-md-2">
			<div class="container">
	            <div id="text-fileList" class="uploader-list"></div>
	        </div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<ul class="list-group">
				<li class="list-group-item">大文件上传</li>
			</ul>
		</div>
		<div class="col-md-6">
		    <div id="big-uploader" class="container">
		        <div class="btns container">
		            <div id="big-picker" class="webuploader-container" style="float: left; margin-right: 10px">
		                <div>选择文件<input type="file" name="file" class="webuploader-element-invisible" multiple="multiple">
		                </div>
		            </div>
		
		            <div id="big-UploadBtn" class="webuploader-pick" style="float: left; margin-right: 10px">开始上传</div>
		            <div id="big-StopBtn" class="webuploader-pick" style="float: left; margin-right: 10px" status="suspend">暂停上传</div>
		        </div>
		    </div>
		</div>
		<div class="col-md-2">
			<div class="container">
	            <div id="big-fileList" class="uploader-list"></div> <!--存放文件的容器-->
	        </div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<ul class="list-group">
				<li class="list-group-item">多文件上传</li>
			</ul>
		</div>
		<div class="col-md-6">
			<div class="col-lg-12">
		        <div class="panel-body">
		            <div class="row">
		                <div class="container">
		                    <div class="picker webuploader-container" id="researchReport" style="float: left; margin-right: 10px">
		                        <div> 选择研究报告<input type="file" class="webuploader-element-invisible" multiple="multiple"></div>
		                    </div>
		                </div>
		            </div>
		            <div class="row">
		                <div class="container">
		                    <div class="picker webuploader-container" id="researchReportStuff"
		                         style="float: left; margin-right: 10px">
		                        <div>选择研究报告支撑材料(限PDF)<input type="file" class="webuploader-element-invisible"multiple="multiple"></div>
		                    </div>
		                </div>
		            </div>
		            <div class="row">
		                <div class="container">
		                    <div class="picker webuploader-container" id="applyReport"
		                         style="float: left; margin-right: 10px">
		                        <div> 选择应用报告<input type="file" class="webuploader-element-invisible" multiple="multiple"></div>
		                    </div>
		                </div>
		            </div>
		            <div class="row">
		                <div class="container">
		                    <div class="picker webuploader-container" id="applyReportStuff"
		                         style="float: left; margin-right: 10px">
		                        <div>选择应用报告支撑材料(限PDF)<input type="file" class="webuploader-element-invisible" multiple="multiple"></div>
		                    </div>
		                </div>
		            </div>
		            <div class="row">
		                <div class="container">
		                    <div id="multil-UploadBtn" class="webuploader-pick btn-success" style="float: left">开始上传所选文件</div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		<div class="col-md-2">
			<div class="container">
	            <div id="multil-fileList" class="uploader-list"></div>
	        </div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<ul class="list-group">
				<li class="list-group-item">Excel数据对比</li>
			</ul>
		</div>
		<div class="col-md-8">
			<form id="stafffrom" action="${pageContext.request.contextPath }/db/import"  enctype="multipart/form-data" method="POST">
				<div class="container">
					<div class="col-md-2">
						<input style="display: none;" type="file" name="file" id="file" class="inputfile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
						<label for="file" class='btn btn-success'>选择对比Excel</label>
					</div>
					<div class="col-md-10">
						<button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-floppy-save"></span> 点击提交</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<ul class="list-group">
				<li class="list-group-item">数据字典生成</li>
			</ul>
		</div>
		<div class="col-md-8">
		<form id="downDictionary" action="${pageContext.request.contextPath}/db/dictionary" method="post">
			<div class="col-md-8">
			    	<select name ="dbname" id="dbSelect" class="form-control selectpicker">
		        		<c:forEach items="${dbs}" var="e">
							<option value="${e.dbname}">${e.dbname}</option>
						</c:forEach>
			        </select>
			</div>
			<div class="col-md-4">
				<button type="submit" class="btn btn-primary">提交</button>
			</div>
 		</form>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<ul class="list-group">
				<li class="list-group-item">html页面转PDF</li>
			</ul>
		</div>
		<div class="col-md-8">
			<form id="downDictionary" action="${pageContext.request.contextPath}/db/pdf" method="post">
				<div class="col-md-8">
				    	<select name ="dbname" id="pageSelect" class="form-control selectpicker">
				        </select>
				</div>
				<div class="col-md-4">
					<button type="submit" class="btn btn-primary">提交</button>
				</div>
	 		</form>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function () {
	ImageuploadFiles();
	textUploadFiles();
	bigUploadFiles();
	multilUploadFiles();
    $("#stafffrom").ajaxForm(function(data){   
    	if(data.meta.success){
    		layer.open({
    			  type: 1,
    			  skin: 'layui-layer-rim', 
    			  content: data.data
    			});
    		var firstTr = $("#excelDataTable tr:eq(0)");
    		firstTr.find("td").each(function(i,v){
    			$(v).unbind();
    			$(v).bind("click",function(){
    				var This = $(this);
    				This.toggleClass("bg-primary");
    				var row = 1;
    				var col = 1;
    				var tmpIndex  = -1;
    				var trs = firstTr.siblings("tr");
    				for(var index = 0; index < trs.length ; index ++){
    					if(index < tmpIndex){
    						continue;
    					}
    					var tr = trs[index];
    					var tds = $(tr).find("td");
    					for(var k = 0; k < tds.length; k ++){
    						var td = tds[k];
    						row = $(td).attr("rowspan");
    						col = $(td).attr("colspan");
    						
    						if(row > 1){
    							tmpIndex = index + row;
    						}
							if(col > 1){
    							
    						}
        					if(i == k){
        						$(this).toggleClass("bg-primary");
        					}
    					}
    				}
    				
    			});
    		});
    	}
    });
});
</script>
<!-- 图片上传 -->
<script type="text/javascript">
	function ImageuploadFiles(){
        var $list = $('#image-fileList');
        var thumbnailHeight = 150;
        var thumbnailWidth = 150;
        var uploader = WebUploader.create({
            auto: false,
            swf: '${pageContext.request.contextPath }/resources/lib/webuploader/Uploader.swf',
            server: '${pageContext.request.contextPath }/file/imageup',
            pick: '#image-picker',
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });

        uploader.on('fileQueued', function (file) {
            var $li = $('<div id="' + file.id + '" class="item">' +
                            '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +
                            '<p class="state">等待上传...</p>' +
                            '<img>' + '</div>'),
            $img = $li.find('img');
            $list.append($li);
            //创建缩略图
            uploader.makeThumb(file, function (error, src) {
                if (error) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }
                $img.attr('src', src);
            }, thumbnailWidth, thumbnailHeight);
            $(".btn-delete").click(function () {
                uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));
                $(this).parent().parent().fadeOut();//视觉上消失了
                $(this).parent().parent().remove();//DOM上删除了
            });
        });

        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),$percent = $li.find('.progress .progress-bar');
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                        '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                        '</div>' +
                        '</div>').appendTo($li).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });

        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).find('p.state').text('已上传');
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
            $('#' + file.id).find(".info").find('.btn').fadeOut('slow');//上传完后删除"删除"按钮
        });

        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
            //上传出错后进度条爆红
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-danger");
            if ($('#' + file.id).find(".btn-retry").length < 1) {
                var btn = $('<button type="button" fileid="' + file.id + '" class="btn btn-success btn-retry"><span class="glyphicon glyphicon-refresh"></span></button>');
                $('#' + file.id).find(".info").append(btn);//.find(".btn-danger")
            }
            $(".btn-retry").click(function () {
                uploader.retry(uploader.getFile($(this).attr("fileId")));
            });
        });

        uploader.on('uploadComplete', function (file) {//上传完成后回调
            $('#' + file.id).find('.progress').fadeOut();//上传完删除进度条
            $('#' + file.id + 'btn').fadeOut('slow')//上传完后删除"删除"按钮
        });

        uploader.on('uploadFinished', function () {
            alert("所有文件上传完毕");
        });

        $("#image-UploadBtn").click(function () {
            uploader.upload();//上传
        });

        uploader.on('uploadAccept', function (file, response) {
            if (response._raw == '{"error":true}') {
                return false;
            }
        });
	}
</script>
<!-- 文件上传 -->
<script type="text/javascript">
    function textUploadFiles(){
    	var $list = $('#text-fileList');
        var uploader = WebUploader.create({
            auto: false,
            swf: '${pageContext.request.contextPath }/resources/lib/webuploader/Uploader.swf',
            server: '${pageContext.request.contextPath }/file/load',
            pick: '#text-picker',
            accept: {
                title: 'Images',
                extensions: 'txt,xml,pdf,doc,docx',
                mimeTypes: 'text/plain,text/xml,application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document'
            }
        });
        uploader.on('fileQueued', function (file) {
            $list.append('<div id="' + file.id + '" class="item">' +
                    '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +
                    '<p class="state">等待上传...</p>' +
                    '</div>');
            $(".btn-delete").click(function () {
                uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));
                $(this).parent().parent().fadeOut();
                $(this).parent().parent().remove();
            });
        });

        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),$percent = $li.find('.progress .progress-bar');
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active"><div class="progress-bar" role="progressbar" style="width: 0%"></div></div>').appendTo($li).find('.progress-bar');
            }
            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });

        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).find('p.state').text('已上传');
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
            $('#' + file.id).find(".info").find('.btn').fadeOut('slow');
        });

        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-danger");
            if ($('#' + file.id).find(".btn-retry").length < 1) {
                var btn = $('<button type="button" fileid="' + file.id + '" class="btn btn-success btn-retry"><span class="glyphicon glyphicon-refresh"></span></button>');
                $('#' + file.id).find(".info").append(btn);//.find(".btn-danger")
            }

            $(".btn-retry").click(function () {
                uploader.retry(uploader.getFile($(this).attr("fileId")));
            });
        });

        uploader.on('uploadComplete', function (file) {//上传完成后回调
            $('#' + file.id).find('.progress').fadeOut();//上传完删除进度条
            $('#' + file.id + 'btn').fadeOut('slow')//上传完后删除"删除"按钮
        });

        uploader.on('uploadFinished', function () {
            alert("所有文件上传完毕");
        });

        $("#text-UploadBtn").click(function () {
            uploader.upload();
        });

        uploader.on('uploadAccept', function (file, response) {
            if (response._raw == '{"error":true}') {
                return false;
            }
        });
    }
</script>
<!-- 大文件上传 -->
<script type="text/javascript">
    function bigUploadFiles(){
    	var $list = $('#big-fileList');
        var uploader = WebUploader.create({
            //设置选完文件后是否自动上传
            auto: false,
            //swf文件路径
            swf: '${pageContext.request.contextPath }/resources/lib/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: '${pageContext.request.contextPath }/file/bigfileup',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#big-picker',
            chunked: true, //开启分块上传
            chunkSize: 10 * 1024 * 1024,
            chunkRetry: 3,//网络问题上传失败后重试次数
            threads: 5, //上传并发数
            //formData: { guid: WebUploader.guid() },  //一个文件有一个guid，在服务器端合并成一个文件  这里有个问题，多个文件或者上传一个文件成功后不刷新直接添加文件上传生成的guid不变！！！   暂时只能传一个大文件（已解决）
            fileNumLimit :1,
            fileSizeLimit: 2000 * 1024 * 1024,//最大2GB
            fileSingleSizeLimit: 2000 * 1024 * 1024,
            resize: false//不压缩
        });

        // 当有文件被添加进队列的时候
        uploader.on('fileQueued', function (file) {debugger
            $list.append('<div id="' + file.id + '" class="item">' +
                    '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +
                    '<p class="state">正在计算文件MD5...请等待计算完毕后再点击上传！</p>' +
                    '</div>');
            //删除要上传的文件
            //每次添加文件都给btn-delete绑定删除方法
            $(".btn-delete").click(function () {
                uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));
                $(this).parent().parent().fadeOut();//视觉上消失了
                $(this).parent().parent().remove();//DOM上删除了
            });

            uploader.options.formData.guid = WebUploader.guid();//每个文件都附带一个guid，以在服务端确定哪些文件块本来是一个

            uploader.md5File(file).then(function (fileMd5) {
                        //var end = +new Date();
                        // console.log("before-send-file  preupload: file.size="+file.size+" file.md5="+fileMd5);
                        //insertLog("<br>" + moment().format("YYYY-MM-DD HH:mm:ss") + " before-send-file  preupload:计算文件(" + file.name + ")MD5完成. 耗时  " + (end - start) + '毫秒  fileMd5: ' + fileMd5);
                        file.wholeMd5 = fileMd5;//获取到了md5
                        uploader.options.formData.md5value = file.wholeMd5;//每个文件都附带一个md5，便于实现秒传
                        $('#' + file.id).find('p.state').text('MD5计算完毕，可以点击上传了');
                        $.ajax({//向服务端发送请求
                            cache: false,
                            type: "post",
                            //dataType: "json",
                            url: "${pageContext.request.contextPath }/file/IsMD5Exist",//baseUrl +
                            data: {
                                fileMd5: fileMd5,
                                fileName: file.name,
                                fileID: file.id,
                                //isShared: $("#isShared").val()
                            },
                            success: function (result) {
                                if (result == "this file is exist") {
                                	//已有相同文件进行秒传
                                    uploader.removeFile(file, true);
                                    $('#' + file.id).find('p.state').text('已上传');
                                    $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
                                    $('#' + file.id).find(".info").find('.btn').fadeOut('slow');//上传完后删除"删除"按钮
                                    $("#big-StopBtn").fadeOut('slow');
                                } else {
                                	//没有相同文件
                                }
                            }
                        });
                    });
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),$percent = $li.find('.progress .progress-bar');
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active"><div class="progress-bar" role="progressbar" style="width: 0%"></div></div>').appendTo($li).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });

        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).find('p.state').text('已上传');
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
            $('#' + file.id).find(".info").find('.btn').fadeOut('slow');//上传完后删除"删除"按钮
            $('#StopBtn').fadeOut('slow');
        });

        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
            //上传出错后进度条爆红
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-danger");
            //添加重试按钮
            if ($('#' + file.id).find(".btn-retry").length < 1) {
                var btn = $('<button type="button" fileid="' + file.id + '" class="btn btn-success btn-retry"><span class="glyphicon glyphicon-refresh"></span></button>');
                $('#' + file.id).find(".info").append(btn);
            }

            $(".btn-retry").click(function () {
                uploader.retry(uploader.getFile($(this).attr("fileId")));

            });
        });

        uploader.on('uploadComplete', function (file) {//上传完成后回调
            $('#' + file.id).find('.progress').fadeOut();//上传完删除进度条
            $('#' + file.id + 'btn').fadeOut('slow')//上传完后删除"删除"按钮
        });

        uploader.on('uploadFinished', function () {
            alert("所有文件上传完毕");
        });

        $("#big-UploadBtn").click(function () {
            uploader.upload();//上传
        });

        $("#StopBtn").click(function () {
            var status = $('#StopBtn').attr("status");
            if (status == "suspend") {
                $("#StopBtn").html("继续上传");
                $("#StopBtn").attr("status", "continuous");
                uploader.stop(true);
            } else {
                $("#StopBtn").html("暂停上传");
                $("#StopBtn").attr("status", "suspend");
                uploader.upload(uploader.getFiles("interrupt"));
            }
        });

        uploader.on('uploadAccept', function (file, response) {
            if (response._raw === '{"error":true}') {
                return false;
            }
        });
    }
</script>
<!-- 多文件上传 -->
<script type="text/javascript">
    function multilUploadFiles(){
        var $list = $('#multil-fileList');
        var uploader = WebUploader.create({
            auto: false,
            swf: '${pageContext.request.contextPath }/resources/lib/webuploader/Uploader.swf',
            server: '${pageContext.request.contextPath }/file/entityUp',
            pick: '.picker',
            resize: false,
            accept: {
                title: '文件',
                extensions: 'doc,docx,pdf,zip,rar,7z'
            }
        });
        uploader.option('fileNumLimit', (4));
        uploader.option('threads', (1));

        // 当有文件被添加进队列的时候
        uploader.on('fileQueued', function (file) {
            file.type = file.source._refer[0].id;
            $list.append('<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>');
            $(".btn-delete").click(function () {
                uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));
                $(this).parent().parent().fadeOut();//视觉上消失了
                $(this).parent().parent().remove();//DOM上删除了
            });
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo($li).find('.progress-bar');
            }
            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });
        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).find('p.state').text('已上传');
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");
            $('#' + file.id).find(".info").find('.btn').fadeOut('slow');//上传完后删除"删除"按钮
        });
        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
            //上传出错后进度条爆红
            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-danger");
            //添加重试按钮
            //为了防止重复添加重试按钮，做一个判断
            if ($('#' + file.id).find(".btn-retry").length < 1) {
                var btn = $('<button type="button" fileid="' + file.id + '" class="btn btn-success btn-retry"><span class="glyphicon glyphicon-refresh"></span></button>');
                $('#' + file.id).find(".info").append(btn);//.find(".btn-danger")
            }

            $(".btn-retry").click(function () {
                uploader.retry(uploader.getFile($(this).attr("fileId")));
            });
        });
        uploader.on('uploadComplete', function (file) {//上传完成后回调
            $('#' + file.id).find('.progress').fadeOut();//上传完删除进度条
            $('#' + file.id + 'btn').fadeOut('slow')//上传完后删除"删除"按钮
        });
        uploader.on('uploadFinished', function () {
            alert("所有文件上传完毕");
        });

        $("#multil-UploadBtn").click(function () {
            uploader.upload();//上传
        });

        uploader.on('uploadAccept', function (file, response) {
            if (response._raw == '{"error":true}') {
                return false;
            }
        });
    }
</script>
<!-- Excel数据解析上传 -->
<script type="text/javascript">
	$(function(){
		
		
	})
</script>
</body>
</html>
