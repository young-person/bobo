$(function () {

    var data = [];
    var myTable =
    $("#dynamic-table").dataTable({
        data: data,
        lengthMenu: [ [10, 25, 50, -1], [10, 25, 50, "所有"] ],
        paging: true,
        ordering: false,
        searching: false,
        language: {
            paginate: {
                previous: "上一页",
                next: "下一页",
                first: "第一页",
                last: "最后"
            },
            zeroRecords: "没有内容",
            info: "总共_PAGES_ 页，显示第_START_ 到第 _END_ ，筛选之后得到 _TOTAL_ 条，初始_MAX_ 条 ",
            infoEmpty: "0条记录",
            infoFiltered: ""
        },
        paging: true,
        pagingType: "full_numbers",
    });
});