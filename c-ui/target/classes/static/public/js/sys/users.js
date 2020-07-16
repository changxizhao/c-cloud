$(function(){
    Tree.initTree('DeptTreeVew',0);

    window.operateEvents = {
        'click #editUser': function (e, value, row, index) { // 编辑部门
            // editDept(row);
            layer.alert("编辑用户 ：" + row.username);
        },

        'click #deleteUser': function (e, value, row, index) { // 删除部门
            layer.alert("删除用户 ：" + row.username);
            //$("#upload").modal('show');
        }
    };

    initUserTable();
});

function initUserTable() {
    var opt = getUsersTableOption();
    $('#user-detail-table').bootstrapTable(opt);
}

var getUsersTableOption = function (deptId) {

    var option = {
        classes:'table table-hover table-no-bordered',
        url: '/sys/user/table',
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
                pageSize : this.pageSize,
                deptId: deptId
            };
            return param;
        },
        columns: [
            {checkbox:true},
            { title: '用户ID', field: 'id',align: 'center'},
            { title: '登录名', field: 'username',align: 'center'},
            { title:'姓名', field:'nickname',align: 'center'},
            { title: '性别', field: 'sex',align: 'center'},
            { title: '电话', field: 'telephone', align: 'center'},
            { title: '邮箱', field: 'mail',align: 'center'},
            { title: '所属部门', field: 'deptId',align: 'center'},
            { title: '状态', field: 'status',align: 'center'},
            { title: '操作', field: '',align: 'center',events: operateEvents,formatter: operateFormatter}
        ]
    };

    return option;
}

function operateFormatter(value, row, index) {
    return [
        '<button class="btn btn-info" type="button" id="editUser"><i class="fa fa-edit"> 编辑</i></button>&nbsp;&nbsp;&nbsp;',
        '<button class="btn btn-danger ml-3" type="button" id="deleteUser"><i class="fa fa fa-remove"> 删除</i></button>'
    ].join('');

}