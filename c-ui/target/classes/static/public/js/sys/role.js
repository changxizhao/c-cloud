$(function () {

    window.operateEvents = {
        'click #editRole': function (e, value, row, index) { // 编辑角色
            doEdit(row);
        },

        'click #toAuthoriz': function (e, value, row, index) { // 角色授权
            openMenuTree(row.id);
        },

        'click #addUsers': function (e, value, row, index) { // 添加用户
            openRoleUser(row.id);
        },

        'click #deleteRole': function (e, value, row, index) { // 删除角色
            delRole(row.id, row.name);

        }
    };


    var opt = getTableOption($("#select-role-name").val());
    $('#role-table').bootstrapTable(opt);



});

var $checkableTree;

var initMenuTreeData = function (tree) {

    $('#menu-tree li').each(function(){
        var nodeId = $(this).attr('data-nodeid');
        var node = tree.treeview('getNode', nodeId);
        if(node.checked) {
            tree.treeview('checkNode', [ node, { silent: true}]);
        }
    })

};

var initMenuTree = function(data) {
    return $('#menu-tree').treeview({
        data: data,
        showIcon: false,
        showBorder: false,
        showCheckbox: true/*,
        onNodeChecked: function (event, node) {
            alert(node.nodeId + "," + node.tags);
        },
        onNodeUnchecked: function (event, node) {
            alert("unchecked");
        }*/
    });
}

var getTableOption = function (name) {

    var option = {
        classes:'table table-hover table-no-bordered',
        url: '/api/sys/role/table',
        pagination: true,	//显示分页条
        datatype: 'json',
        sidePagination: 'server',//服务器端分页
        //showRefresh: true,  //显示刷新按钮
        search: false,
        //toolbar: '#dept-table-toolbar',
        striped : true,     //设置为true会有隔行变色效果
        //idField: 'menuId',
        queryParams: function () {
            var param = {
                pageNumber : this.pageNumber,
                pageSize : this.pageSize,
                name: name
            };
            return param;
        },
        columns: [
            {checkbox:true},
            { title: '角色ID', field: 'id',align: 'center'},
            { title:'角色名称', field:'name',align: 'center'},
            { title: '角色类型', field: 'type',align: 'center', formatter: typeFormatter},
            { title: '角色状态', field: 'status', align: 'center', formatter: statusFormatter},
            { title: '操作', field: '',align: 'center',events: operateEvents,formatter: operateFormatter}
        ]
    };

    return option;
};

function operateFormatter(value, row, index) {
    return [
        '<button class="btn btn-info" type="button" id="editRole" style="margin-right:15px;"><i class="fa fa-edit"> 编辑</i></button>',
        '<button class="btn btn-primary" type="button" id="toAuthoriz" style="margin-right:15px;"><i class="fa fa-edit"> 授权</i></button>',
        '<button class="btn btn-warning" type="button" id="addUsers" style="margin-right:15px;"><i class="fa fa-edit"> 添加用户</i></button>',
        '<button class="btn btn-danger" type="button" id="deleteRole" style="margin-right:15px;"><i class="fa fa fa-remove"> 删除</i></button>'
    ].join('');

}

function reloadRoleTable() {
    $("#role-table").bootstrapTable('destroy');
    var opt = getTableOption($("#select-role-name").val());
    $('#role-table').bootstrapTable(opt);
}

function openAddRole() {
    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "新增角色",
        area: ['550px', '400px'],
        shadeClose: false,
        content: jQuery("#add-role"),
        btn: ['确定','取消'],
        btn1: function (index) {
            $.post("/api/sys/role/add",$("#add-role-form").serialize(),function (data) {
                if(data.code == 200){
                    // Tree.initTree('treeview5',1);
                    layer.close(index);
                    $('#role-table').bootstrapTable('refresh');
                }else {
                    layer.alert(data.msg);
                }
            },'json');
        },
        end: function () {
            $('#add-role-form')[0].reset();
        }
    });
};

var typeFormatter = function (value, row, index) {
    if(value == 1) {
        return "管理员";
    }else {
        return "其他";
    }
};

function statusFormatter(value, row, index) {
    if(value == 1) {
        return "正常";
    }else {
        return "停用";
    }
}

function doEdit(row) {

    $("#id").val(row.id);
    $("#name").val(row.name);
    $("input[name='type'][value="+row.type+"]").attr("checked",true);
    $("input[name='status'][value="+row.status+"]").attr("checked",true);
    $("#remark").val(row.remark);

    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "修改角色",
        area: ['550px', '450px'],
        shadeClose: false,
        content: jQuery("#add-role"),
        btn: ['确定','取消'],
        btn1: function (index) {
            $.post("/api/sys/role/update",$("#add-role-form").serialize(),function (data) {
                if(data.code == 200){
                    layer.close(index);
                    $('#role-table').bootstrapTable('refresh');
                }else {
                    layer.alert(data.msg);
                }
            },'json');
        },
        end: function () {
            $('#add-role-form')[0].reset();
        }
    });
}

function openMenuTree(roleId) {

    $.getJSON("/api/sys/menu/tree?roleId=" + roleId +"&_" + $.now(), function (r) {
        $checkableTree = initMenuTree(r.data);
        $checkableTree.treeview('expandAll', {silent: true});
        initMenuTreeData($checkableTree);
    });

    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "角色授权",
        area: ['300px', '450px'],
        shadeClose: false,
        content: jQuery("#open-menu-tree"),
        btn: ['确定','取消'],
        btn1: function (index) {
            var nodes = $checkableTree.treeview("getChecked");
            var aclIdList = new Array();
            for(var i = 0; i < nodes.length; i++){
                var node = nodes[i];
                aclIdList.push(node.tags.toString());
            }

            var json = {};
            json.roleId = roleId;
            json.aclIdList = aclIdList;

            $.ajax({
                type: 'post',
                url:'/api/sys/role/acl/change',
                contentType:'application/json',
                data:JSON.stringify(json),
                dataType:'json',
                success:function (data) {
                    if(data.code == 200){
                        layer.close(index);
                    }else {
                        layer.alert(data.msg);
                    }
                }
            })
        },
        end: function () {

        }
    });
};

var getUsersTableOption = function (deptId, username, nickname) {

    var option = {
        classes:'table table-hover table-no-bordered',
        url: '/api/sys/user/table',
        pagination: true,	//显示分页条
        datatype: 'json',
        sidePagination: 'server',//服务器端分页
        //showRefresh: true,  //显示刷新按钮
        search: false,
        pageSize: 5,
        pageList: [5, 10, 20, 50],
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
        onDblClickRow: function (item, $element) {
            selectUser(item.nickname, item.id);
        },
        columns: [
            {title: '账户名', field: 'username', align: "center"},
            { title:'姓名', field:'nickname', align: "center"},
            { title: '所属部门', field: 'deptName',align: 'center'},
            { title: '状态', field: 'status',align: 'center', formatter: statusFormatter}
        ]
    };

    return option;
}




function openRoleUser(roleId) {

    var opt = getUsersTableOption('0');
    $('#user-table').bootstrapTable(opt);

    getSelectedUsers(roleId);

    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "添加用户",
        area: ['800px', '500px'],
        shadeClose: false,
        content: jQuery("#add-user-layer"),
        btn: ['确定','取消'],
        btn1: function (index) {
            var userIdList = new Array();
            $('#role-users span').each(function(){
                userIdList.push($(this).children('input').val());
            });

            var json = {};
            json.roleId = roleId;
            json.userIdList = userIdList;
            //alert(JSON.stringify(json));

            $.ajax({
                type: 'post',
                url:'/api/sys/role/user/change',
                contentType:'application/json',
                data:JSON.stringify(json),
                dataType:'json',
                success:function (data) {
                    if(data.code == 200){
                        layer.close(index);
                    }else {
                        layer.alert(data.msg);
                    }
                }
            });
        },
        end: function () {

        }
    });
}

function getSelectedUsers(roleId) {
    $.getJSON("/api/sys/role/user/roleUser/" + roleId, function (r) {
        initSelectedUser(r.data);
    });
}

function initSelectedUser(users) {
    $("#role-users").html('');
    $.each(users, function(index, user) {
        var html = "<span class='user-label' id='role-user-" + user.id + "'>" + user.nickname + "<i class='remove fa fa-fw fa-remove'></i><input value='" + user.id + "' style='display: none;'/></span>";
        $("#role-users").append(html);
    });
}

function selectUser(text, value) {

    var hasItem = false;
    $('#role-users span').each(function(){
        var itemText = $(this).text();
        var itemValue = $(this).children('input').val();

        if(itemText == text && itemValue == value) {
            hasItem = true;
            return;
        }
    })
    if(hasItem) {
        layer.alert("请勿重复添加人员");
        return;
    }

    var html = "<span class='user-label' id='role-user-" + value + "'>" + text + "<i class='remove fa fa-fw fa-remove'></i><input value='" + value + "' style='display: none;'/></span>";
    $("#role-users").append(html);
}

$("#role-users").on('click', 'i', function () {
    var itemValue = $(this).parent().children('input').val();
    $("#role-user-" + itemValue).remove();
})


function delRole(id, name) {
    layer.confirm("确认删除角色【" + name + "】吗？", {btn: ['确定', '取消'], title: "提示"}, function (i) {
        $.post("/api/sys/role/delete",{id: id},function (data) {
            if(data.code == 200){
                layer.close(i);
                $('#role-table').bootstrapTable('refresh');
            }else {
                layer.alert(data.msg);
            }
        },'json');
    });
}
