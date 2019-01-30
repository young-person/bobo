var filetable = function(){

    var bean = function(){
        return {id:'',userid:'',filename:'',level:0,ishidden:false,parentid:'',description:'',urlname:'',createtime:''};
    }
    var _caches = [];
    var _cache = new Object();
    _cache['id'] = bean.id;
    _cache[bean.id] = bean;
    _cache[children] = [];
    _caches.push(_cache);
    /*
     * 创建文件夹
     */
    function createFile(){
        $("#hotleinfo").bind('contextmenu',function(e){
            e.preventDefault();
            $('#createPanle').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        });
    }

    /*
     * 绑定事件
     */
    function bindEvent(){
        $(".fileDiv,.ui-widget-content").on('mouseover',showDel);
        $(".fileDiv,.ui-widget-content").on('mouseout',hideDel);
        $('.fileinput').on("focus",function(e){
            //debugger
            inputValue = $(this).val();
        });
        $('.fileinput').on("blur",function(e){
            if(inputValue!==$(this).val()){
                //编辑文件名保存
                editSave($(this).attr('uuid'),$(this).val());
            }
        });
    }
    /*
     * 编辑文件名称
     */
    function editSave(id,name){
        if($('.fileinput').length>0){
            for(var ind = 0; ind < $('.fileinput').length;ind ++){
                if($($('.fileinput')[ind]).val() == name){
                    $($('.fileinput')[ind]).val(inputValue);
                    return;
                }
            }
        }
        $.ajax({type: "POST",url: CONTEXTPATH+"/savename.do",data: {'id':id,'name':name},cache:false,async:false,dataType:"json",
             success: function (data,textStatus,jqXHR)
             {

             },
            error:function (XMLHttpRequest,textStatus,errorThrown) {
            }
        });
    }

    function showDel(){
        $(this).find("span.gzv8Pv").css("visibility","visible");
    }
    function hideDel(){
        $(this).find("span.gzv8Pv").css("visibility","hidden");
    }
    /*
     * 删除文件
     */
    function delThisFile(){
        var parent = $(this).parent();
        var _func = parent.attr('ondblclick');
        var param = _func.match(/\(([^)]*)\)/);
        if(param){
            $(this).parent().parent().remove();
            var relDo = _func.replace('initFile','delFile');
            eval(relDo);
        }
    }
    /*
     * 后台删除文件
     */
    function delFile(userid,level,parentid){
        $.ajax({type: "POST",url: CONTEXTPATH+"/delfiles.do",data: {'parentid':parentid,'userid':userid,'level':level},cache: false,async : false,dataType: "json",
             success: function (data ,textStatus, jqXHR)
             {

             },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    var initFiles = function(bean){
        var element = event.target||event.srcElement;
        if(element.localName=='span'&&element.className=='gzv8Pv'){
            return;
        }
        $.ajax({type: "POST",url: CONTEXTPATH+"/queryfiles.do",data: {bean},cache: false,async : false,dataType: "json",
            success: function (data ,textStatus, jqXHR)
            {
                if(data||data.length>0){
                    $(".portlet-body").html("");
                    var fileDiv = "";
                    $(data['list']).each(function(index,e){
                        fileDiv += "<div class='filecontainer'>"+
                            "<div class='fileDiv ui-widget-content' ondblclick=initFile('"+e+"')>" +
                            "<span class='gzv8Pv'></span><img src='resources/images/file.gif'/></div>"+
                            "<input uuid='"+e['id']+"' title='"+e['filename']+"' type='text' value='"+e['filename']+"' name='"+e['filename']+"' class='fileinput'/>"+
                            "</div>";
                    });
                    $(".portlet-body").append(fileDiv);
                    bindEvent();
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }

    /**
    文件夹 表结构设置
    id 文件id
    userid  文件对应用户 id
    filename 文件名称
    level  文件夹等级
    ishidden  是否隐藏
    parentid  父文件的id
    description 文件描述
    subName 拼接上层文件夹名称
    */
    function editHandle(){
        var bean = bean();
        bean.id = getUuid().replace(/-/g,"");
        var parentid = _UUID;
        var level = _LEVEL;
        var fileIndex = 1;
        var files = $(".filecontainer");
        var fileDiv = "<div index='"+fileIndex+"' class='filecontainer'>"+
        "<div class='fileDiv ui-widget-content' ondblclick=initFile('"+userid+"','"+level+"','"+uuid+"')>" +
        "<span class='gzv8Pv'></span><img src='resources/images/file.gif'/></div>"+
        "<input uuid='"+uuid+"' type='text' title='新建文件夹' value='新建文件夹' name='新建文件夹' class='fileinput'/>"+
        "</div>";
        if(typeof(files)=='undefined'&&files.length==0){
            //直接进行保存
        }else{
            var same = [];
            for(var i = 0; i < files.length; i++){
                var $_f = $(files[i]);
                 var input = $_f.find("input").eq(0);
                 var name = input.val();
                 if(name.indexOf('新建文件夹')>-1){
                     same.push(input);
                 }
            }
            if(same.length>0){
                var temp = 0;
                var len = same.length;
                do{
                    if(len-1==0){
                         var name = '新建文件夹1';
                         fileDiv = "<div class='filecontainer'>"+
                            "<div class='fileDiv ui-widget-content' ondblclick=initFile('"+userid+"','"+level+"','"+uuid+"')>" +
                            "<span class='gzv8Pv'></span><img src='resources/images/file.gif' /></div>"+
                            "<input uuid='"+uuid+"' title='"+name+"' type='text' value='"+name+"' name='"+name+"' class='fileinput'/>"+
                            "</div>";
                    }else{
                        var pre = same[temp];
                        var lst = same[temp+1];
                        if(typeof(lst)=='undefined'){
                             var name = '新建文件夹'+(len);
                             fileDiv = "<div class='filecontainer'>"+
                                "<div class='fileDiv ui-widget-content' ondblclick=initFile('"+userid+"','"+level+"','"+uuid+"')>" +
                                "<span class='gzv8Pv'></span><img src='resources/images/file.gif'/></div>"+
                                "<input uuid='"+uuid+"' title='"+name+"' type='text' value='"+name+"' name='"+name+"' class='fileinput'/>"+
                                "</div>";
                                break;
                        }else{
                            var n1 = pre.val().substring(pre.val().indexOf('新建文件夹')+5, pre.val().length)==''?0:pre.val().substring(pre.val().indexOf('新建文件夹')+5, pre.val().length);
                            var n2 = lst.val().substring(lst.val().indexOf('新建文件夹')+5, lst.val().length);
                            if((parseInt(n2)-parseInt(n1))!=1){
                                 var name = '新建文件夹'+(parseInt(n1)+1);
                                 fileDiv = "<div class='filecontainer'>"+
                                    "<div class='fileDiv ui-widget-content' ondblclick=initFile('"+userid+"','"+level+"','"+uuid+"')>" +
                                    "<span class='gzv8Pv'></span><img src='resources/images/file.gif' /></div>"+
                                    "<input uuid='"+uuid+"' title='"+name+"' type='text' value='"+name+"' name='"+name+"' class='fileinput'/>"+
                                    "</div>";
                            }
                        }
                    }
                    temp++;
                }while(temp<len);
            }
        }

        $(".portlet-body").append(fileDiv);
        var $currentFile = $(fileDiv);
        var filename = $currentFile.find(".fileinput").val();
        $currentFile.attr('uuid',uuid);
        $currentFile.attr('userid',userid);
        $currentFile.attr('parentid',parentid);
        $currentFile.attr('level',level);
        fileInfo.uuid = uuid;
        fileInfo.filename = filename;
        fileInfo.userid = userid;
        fileInfo.parentid = parentid;
        fileInfo.level = level;
        $.ajax({type: "POST",url: CONTEXTPATH+"/filecreate.do",data: {'uuid':uuid,'userid':userid,'filename':filename,'parentid':parentid,'level':level},cache: false,async : false,dataType: "json",
            success: function (data ,textStatus, jqXHR)
            {
                bindEvent();
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
            }
         });
    }

    return {'bean':bean,'caches':_caches,'cache':_cache};
}