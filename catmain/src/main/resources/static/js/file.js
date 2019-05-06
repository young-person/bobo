
(function(w,$, undefined){
    'use strict';
    $('#filePanel').contextmenu({
        target: '#context-menu',
        before: function (e) {
            e.preventDefault();
            if (e.target.tagName == 'SPAN') {
                e.preventDefault();
                this.closemenu();
                return false;
            }
            return true;
        }
    });

    var version = {
        folder:"<div class='folder-container'><span class='folder-del'></span><div class='folder-icon'></div><input title='%s' type='text' value='' class='folder-input'/></div>",
        a:"<a>%s</a>",
        li:"<li class='active'></li>",
        container:"folder-container",
        dataType:"json",
        post:"POST",
        put:"PUT",
        firstName:"文件首页",
        update_url:"file/sys/put"
    };

    var paper = function(config) {
        var cfg = new Object();
        cfg.id = config['id'];
        cfg.nav = config['nav'];
        var obj = getPaperObj();
        obj['config'] = cfg;
        return obj;
    };

    var doload = function (url,upid) {
        paper['selectUrl'] = url;
        var options = this.options;
        var config = this.config;
        realToLoad(url,upid,options,config);
    };

    var getInOptions = function(){
        var options = new Object();
        options.linked = new Array();
        options.cache = {};
        return options;
    };

    var getPaperObj = function(){
        var obj = new Object();
        obj['create'] = create;
        obj['update'] = update;
        obj['doNext'] = doNext;
        obj['doHead'] = doHead;
        obj['cutoff'] = cutoff;
        obj['doload'] = doload;
        obj['options'] = getInOptions();
        return obj;
    };

    var freshenContain = function(data,config,options){
        var array = [];
        for(var index = 0; index < data.length;index ++){
            var o = getFolder(data[index]);
            array.push(o.content);
        }
        $("#"+config.id).html(array.join(""));
        bindForInput(config,options);
    };

    var changeActiveLi = function(options,config,data){
        var len  = options.linked.length,bean, $a;
        bean = options.linked[len-1];
        if (len == 0){
            throw new Error('没有初始化根节点');
        } else if (len == 1) {
            $("#"+config.nav).find("li.active").html(bean.filename);
        }else{
            var last = options.linked[len-2];
            $a = sprintf(version.a,last.filename);
            var $li = $(version.li).html(bean.filename);

            $("#"+config.nav).find("li.active").html('').append($a).removeClass('active');
            $("#"+config.nav).append($li.prop("outerHTML"));
        }
        doHead(options,config,bean);
    };

    var getSubLinked = function(options,id){
        var subIndex = 0;
        for(var index = 0; index < options.linked.length; index ++){
            if (options.linked[index].id == id) {
                subIndex = index;
                break;
            }
        }
        options.linked = options.linked.slice(0,subIndex);
    };

    var getFolder = function(bean,pid){
        var filename,uuid;
        if (pid)pid = pid.id;
        if (bean) {
            filename = bean.filename;
            uuid = bean.id;
        }else{
            var cName='新建文件夹',len = cName.length;
            var files = $("."+version.container);
            filename = getNewFileInfo(filename,cName,files);
            uuid = guid();
            if (!filename)filename=cName;
        }
        var folder = sprintf(version.folder,filename);
        var $folder = $(folder);
        $folder.attr('id',uuid);
        $folder.find("input").attr('value',filename);

        var result = new Object();
        result.content = $folder[0].outerHTML;
        result.bean = {'id':uuid,'pid':(pid || ''),'filename':filename};
        return result;
    };

    var getNewFileInfo = function(name,cName,files){
        if(typeof(files)!='undefined' && files.length > 0){
            var same = [];
            for(var i = 0; i < files.length; i++){
                var $_f = $(files[i]);
                var input = $_f.find("input").eq(0);
                var old = input.val();
                if(old.indexOf(cName)>-1){
                    same.push(input);
                }
            }
            if(same.length>0){
                var temp = 0;
                var len = same.length;
                do{
                    if(len-1==0){
                        name = cName+'1';
                    }else{
                        var pre = same[temp];
                        var lst = same[temp+1];
                        if(typeof(lst)=='undefined'){
                            name = cName+(len);
                            break;
                        }else{
                            var start = pre.val().indexOf(cName)+len;
                            var end = pre.val().length;
                            var n1 = pre.val().substr(start, end)==''?0:pre.val().substr(start,end);
                            var n2 = lst.val().substr(lst.val().indexOf(cName)+len, lst.val().length);

                            if((parseInt(n2)-parseInt(n1))!=1){
                                name = cName+(parseInt(n1)+1);
                            }
                        }
                    }
                    temp++;
                }while(temp<len);
            }
        }
        return name;
    };
    var realToLoad = function (url,upid,options,config,filename) {
        options.linked.push({'id':upid,'filename':(filename || version.firstName)});
        doAjaxRestfulRequest(url+upid,null,function (data) {
            options.cache[upid] = data;
            freshenContain(data,config,options);
            changeActiveLi(options,config,data);
            clickForFolder(url,options,config,realToLoad);
        });
    };
    var clickForFolder = function(url,options,config,fun){
        $(".folder-icon").off("dblclick").on('dblclick',function(){
            var pid = $(this).parent().attr("id");
            var filename = $(this).next().val();
            // 当失败 删除前面一个元素
            fun(url,pid,options,config,filename);
        });
    };
    //创建
    var create = function(url,callback){
        var last = this.options.linked[this.options.linked.length - 1];
        var f = getFolder(null,last);
        var bean = f.bean;
        $("#"+this.config.id).append(f.content);
        clickForFolder(paper.selectUrl,this.options,this.config,realToLoad);
        doAjaxRequest(url,JSON.stringify(bean),callback,version.post);
    };

    //更新文件信息
    var update = function(options,config,bean,url){
        doAjaxRequest(url,JSON.stringify(bean),function (data) {
            if (data.success){
                var list = options.cache[options.linked[options.linked.length-1]['id']];
                $(list).each(function (i,e) {
                    if(e.pid == bean.id){
                        e.filename = bean.filename;
                        return;
                    }
                });
            }else{
                alert("修改失败");
            }
        },version.post);
    };
    //点击头部
    var doHead = function(options,config,bean){
        $("#"+config.nav).find("li.active").attr('uid',bean.id);
        $("#"+config.nav).find("li.active").siblings().off("click").on('click',function(param){
            $(this).nextAll().remove();
            $(this).html($(this).text());
            $(this).addClass('active');
            var cacheID = $(this).attr('uid');
            getSubLinked(options,cacheID);
            realToLoad(paper.selectUrl,cacheID,options,config);
            //使用缓存策略
/*            var list = options.cache[cacheID];
            freshenContain(list,config);
            clickForFolder(paper.selectUrl,options,config,realToLoad);
            getSubLinked(options,cacheID);*/
        });
    };
    //删除
    var cutoff = function(options,config,bean,url){

    };
    var doNext = function(options,config){

    };
    
    function bindForInput(config,options) {
        var oldValue;
        $('.folder-input').off("focus").on("focus",function(e){
            oldValue = $(this).val();
        });

        $('.folder-input').off("blur").on("blur",function(e){
            if(oldValue!==$(this).val()){
                var id = $(this).parent().attr('id');
                var name = $(this).val();
                var list = options.cache[options.linked[options.linked.length-1]['id']];
                var flag = true;
                for(var index = 0; index < list.length;index ++){
                    var element = list[index];
                    if (element.filename == name){
                        alert("文件名不能重复");
                        $(this).val(oldValue);
                        flag = false;
                        break;
                    }
                }
                if (flag)update(options,config,{'id':id,'filename':name},version.update_url);
            }
        });
    }

    var doAjaxRequest = function(url,bean,callback,type) {
        $.ajax({
            type: (type || 'GET'),
            url: url,
            data: bean,
            dataType: version.dataType,
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (typeof callback === 'function') {
                    callback(data);
                }
            },
            error: function (e) {
                if (typeof callback === 'function') {
                    callback(e);
                }
            }
        });
    };

    var doAjaxRestfulRequest = function(url,auth,callback,type) {
        var param = {type: (type || 'GET'),url: url,dataType: version.dataType,
            success: function (data) {
                if (typeof callback === 'function') {
                    callback(data);
                }
            },
            error: function (e) {
                if (typeof callback === 'function') {
                    callback(e);
                }
            }};
        if (auth)
            param['data'] = auth;
        $.ajax(param);
    };

    var sprintf = function (str) {
        var args = arguments,
            flag = true,
            i = 1;

        str = str.replace(/%s/g, function () {
            var arg = args[i++];

            if (typeof arg === 'undefined') {
                flag = false;
                return '';
            }
            return arg;
        });
        return flag ? str : '';
    };
    function guid() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        }).replace(/-/g,'');
    }
    w.paper = paper;
})(window,$);

var p = paper({'id':'filePanel','nav':'filelist'});

p.doload("file/sys/",upid);

$("#createfo").click(function(){
    //p['hander']()['create']();
    p.create('/file/sys/');
});
