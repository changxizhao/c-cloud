$(function(){
    Tree.initTree('DeptTreeVew',0);

    window.operateEvents = {
        'click #editUser': function (e, value, row, index) { // 编辑部门
            // editDept(row);
            editUser(row);
        },

        'click #deleteUser': function (e, value, row, index) { // 删除部门
            delUser(row.id, row.username);
            //$("#upload").modal('show');
        }
    };

    initUserTable('0');
});

function initUserTable(deptId) {
    var opt = getUsersTableOption(deptId);
    $('#user-detail-table').bootstrapTable(opt);
}

// 刷新部门列表
function reloadUserTable(deptId) {
    if(deptId == null || deptId == '' || deptId == undefined) {
        deptId = '0';
    }
    $("#user-detail-table").bootstrapTable('destroy');
    initUserTable(deptId.toString());
}

// 根据搜索条件查询部门
var reloadSelectUserTable = function (deptId) {
    if(deptId == null || deptId == '' || deptId == undefined) {
        deptId = '0';
    }
    $("#user-detail-table").bootstrapTable('destroy');
    var opt = getUsersTableOption(deptId, $("#select-user-name").val(), $("#select-user-nick-name").val());
    $('#user-detail-table').bootstrapTable(opt);
}

var getUsersTableOption = function (deptId, username, nickname) {

    var option = {
        classes:'table table-hover table-no-bordered',
        url: '/api/sys/user/table',
        pagination: true,	//显示分页条
        datatype: 'json',
        sidePagination: 'server',//服务器端分页
        //showRefresh: true,  //显示刷新按钮
        search: false,
        toolbar: '#dept-table-toolbar',
        striped : true,     //设置为true会有隔行变色效果
        //idField: 'menuId',
        queryParams: function () {
            var param = {
                pageNumber : this.pageNumber,
                pageSize : this.pageSize,
                deptId: deptId,
                username: username,
                nickname: nickname

            };
            return param;
        },
        columns: [
            {checkbox:true},
            { title: '用户ID', field: 'id',align: 'center'},
            { title: '登录名', field: 'username',align: 'center'},
            { title:'姓名', field:'nickname',align: 'center'},
            { title: '性别', field: 'sex',align: 'center', formatter: sexFormatter},
            { title: '电话', field: 'telephone', align: 'center'},
            { title: '邮箱', field: 'mail',align: 'center'},
            { title: '所属部门', field: 'deptName',align: 'center'},
            { title: '状态', field: 'status',align: 'center', formatter: statusFormatter},
            { title: '操作', field: '',align: 'center',events: operateEvents,formatter: operateFormatter}
        ]
    };

    return option;
}

var statusFormatter = function (value, row, index) {
    if(value == 1) {
        return "正常";
    }else if(value == 0) {
        return "停用";
    }
}

var sexFormatter = function (value, row, index) {
    if(value == 0) {
        return "女";
    }else if(value == 1) {
        return "男";
    }else {
        return "保密";
    }
}

function operateFormatter(value, row, index) {
    return [
        '<button class="btn btn-info" type="button" id="editUser" style="margin-right:15px;"><i class="fa fa-edit"> 编辑</i></button>',
        '<button class="btn btn-danger ml-3" type="button" id="deleteUser" style="margin-right:15px;"><i class="fa fa fa-remove"> 删除</i></button>'
    ].join('');

}

// 新增用户
var saveUser = function() {
    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "新增用户",
        area: ['550px', '700px'],
        shadeClose: false,
        content: jQuery("#add-user"),
        btn: ['确定','取消'],
        btn1: function (index) {
            $.post("/api/sys/user/add",$("#add-user-form").serialize(),function (data) {
                if(data.code == 200){
                    layer.close(index);
                    $('#user-detail-table').bootstrapTable('refresh');
                }else {
                    layer.alert(data.msg);
                }
            },'json');
        },
        end: function () {
            $('#add-user-form')[0].reset();
        }
    });
}

var editUser = function(row) {
    //alert(row.id + "-" + row.parentId);

    $("#id").val(row.id);
    $("#username").val(row.username);
    $("#nickname").val(row.nickname);

    $("#telephone").val(row.telephone);
    $("#mail").val(row.mail);
    $("#deptId").val(row.deptId);
    $("#deptName").val(row.deptName);

    $("input[name='sex'][value="+row.sex+"]").attr("checked",true);
    $("input[name='status'][value="+row.status+"]").attr("checked",true);

    $("#remark").val(row.remark);

    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "编辑用户",
        area: ['550px', '700px'],
        shadeClose: false,
        content: jQuery("#add-user"),
        btn: ['确定','取消'],
        btn1: function (index) {
            $.post("/api/sys/user/update",$("#add-user-form").serialize(),function (data) {
                if(data.code == 200){
                    Tree.initTree('treeview5',1);
                    layer.close(index);
                    $('#user-detail-table').bootstrapTable('refresh');
                }else {
                    layer.alert(data.msg);
                }
            },'json');
        },
        end: function () {
            $('#add-user-form')[0].reset();
        }
    });
}


var delUser = function(id, name) {
    layer.confirm("确认删除用户【" + name + "】吗？", {btn: ['确定', '取消'], title: "提示"}, function (i) {
        $.post("/api/sys/user/delete",{id: id},function (data) {
            if(data.code == 200){
                Tree.initTree('treeview5',1);
                layer.close(i);
                $('#user-detail-table').bootstrapTable('refresh');
            }else {
                layer.alert(data.msg);
            }
        },'json');
    });
}