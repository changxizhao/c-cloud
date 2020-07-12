$(function(){
    var option = {
        classes:'table table-hover table-no-bordered',
        url: '/sys/dept/table',
        pagination: true,	//显示分页条
        datatype: 'json',
        sidePagination: 'server',//服务器端分页
        showRefresh: true,  //显示刷新按钮
        search: false,
        toolbar: '#dept-table-toolbar',
        striped : true,     //设置为true会有隔行变色效果

        //idField: 'menuId',
        queryParams: function () {
            var param = {
                pageNumber : this.pageNumber,
                pageSize : this.pageSize
            };
            return param;
        },
        columns: [
            {checkbox:true},
            { title: '部门ID', field: 'id',align: 'center'},
            { title:'部门名称', field:'name',align: 'center'},
            { title: '上级部门', field: 'parentId',align: 'center'},
            { title: '显示顺序', field: 'seq',align: 'center'},
            { title: '操作', field: '',align: 'center',formatter: operateFormatter}
        ]
    };
    $('#dept-detail-table').bootstrapTable(option);

    function operateFormatter(value, row, index) {
        return [
            '<button class="btn btn-info" type="button" onclick="delDepts()">编辑</button>',
            '<button class="btn btn-danger" type="button" onclick="delDepts()">删除</button>'
        ].join('');

    }
});

function saveDept() {
    console.log("add one dept");
}

function delDepts() {
    console.log("delete more dept");
}