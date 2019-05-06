function showIframeModel(id){
 $("#NoPermissioniframe").attr("src", BASEPATH+"/page/authorization/editmenu");
 $('#NoPermissionModal').modal({ show: true, backdrop: 'static' });
}
function addMenuIframeModel(){
 $("#NoPermissioniframe").attr("src", BASEPATH+"/page/authorization/addmenu");
 $('#NoPermissionModal').modal({ show: true, backdrop: 'static' });
}