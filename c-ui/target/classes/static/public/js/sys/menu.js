Menu = $(function () {
    Menu.init = function () {
        $.getJSON("/api/sys/menu/treegrid?_" + $.now(), function (r) {
            initTable(r.data);
        });
    };
    Menu.init();

});


var $table = $('#table');
var initTable = function (data) {
    $table.bootstrapTable({
        classes:'table table-hover table-no-bordered',
        // url: '/api/sys/menu/treegrid',
        data: data,
        idField: 'id',
        dataType:'jsonp',
        columns: [
            //{field: 'id', title: '编号', sortable: true, align: 'center'},
            { field: 'name',  title: '名称' },
            // {field: 'pid', title: '所属上级'},
            { field: 'code', title: '权限标识', align: 'center'  },
            { field: 'type', title: '类型', align: 'center'  },
            { field: 'seq', title: '显示顺序', align: 'center', sortable: true },
            { field: 'status',  title: '状态', align: 'center', formatter: 'statusFormatter'  },
            { field: 'operate', title: '操作', align: 'center', events : operateEvents, formatter: 'operateFormatter' },
        ],

        // bootstrap-table-treegrid.js 插件配置 -- start

        //在哪一列展开树形
        treeShowField: 'name',
        //指定父id列
        parentIdField: 'parentId',

        onResetView: function(data) {
            $table.treegrid({
                initialState: 'collapsed',// 所有节点都折叠
                treeColumn: 0,
                onChange: function() {
                    $table.bootstrapTable('resetWidth');
                }
            });

            //只展开树形的第一级节点
            //$table.treegrid('getRootNodes').treegrid('expand');

        }
    });
};

// 格式化按钮
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfadd btn btn-success" style="margin-right:15px;"><i class="fa fa-plus" ></i>&nbsp;新增</button>',
        '<button type="button" class="RoleOfedit btn btn-info" style="margin-right:15px;"><i class="fa fa-pencil-square-o" ></i>&nbsp;修改</button>',
        '<button type="button" class="RoleOfdelete btn btn-danger" style="margin-right:15px;"><i class="fa fa-trash-o" ></i>&nbsp;删除</button>'
    ].join('');

}
// 格式化类型
function typeFormatter(value, row, index) {
    if (value === 'menu') {  return '菜单';  }
    if (value === 'button') {  return '按钮'; }
    if (value === 'api') {  return '接口'; }
    return '-';
}
// 格式化状态
function statusFormatter(value, row, index) {
    if (value === 1) {
        return '正常';
    } else {
        return '停用';
    }
}

//初始化操作按钮的方法
window.operateEvents = {
    'click .RoleOfadd': function (e, value, row, index) {
        add(row.id, row.name);
    },
    'click .RoleOfdelete': function (e, value, row, index) {
        del(row.id, row.name);
    },
    'click .RoleOfedit': function (e, value, row, index) {
        update(row);
    }
};

/**
 * 选中父项时，同时选中子项
 * @param datas 所有的数据
 * @param row 当前数据
 * @param id id 字段名
 * @param pid 父id字段名
 */
function selectChilds(datas,row,id,pid,checked) {
    for(var i in datas){
        if(datas[i][pid] == row[id]){
            datas[i].check=checked;
            selectChilds(datas,datas[i],id,pid,checked);
        };
    }
}

function selectParentChecked(datas,row,id,pid){
    for(var i in datas){
        if(datas[i][id] == row[pid]){
            datas[i].check=true;
            selectParentChecked(datas,datas[i],id,pid);
        };
    }
}

function test() {
    var selRows = $table.bootstrapTable("getSelections");
    if(selRows.length == 0){
        alert("请至少选择一行");
        return;
    }

    var postData = "";
    $.each(selRows,function(i) {
        postData +=  this.id;
        if (i < selRows.length - 1) {
            postData += "， ";
        }
    });
    alert("你选中行的 id 为："+postData);

}

function add(parentId, parentName) {

    $("#parentId").val(parentId);
    $("#parentName").val(parentName);

    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "新增菜单",
        area: ['550px', '720px'],
        shadeClose: false,
        content: jQuery("#add-menu"),
        btn: ['确定','取消'],
        btn1: function (index) {
            $.post("/api/sys/menu/add",$("#add-menu-form").serialize(),function (data) {
                if(data.code == 200){
                    layer.close(index);
                    $('#add-menu-form')[0].reset();
                    reloadTable();
                }else {
                    layer.alert(data.msg);
                }
            },'json');
        }
    });
}

function del(id, name) {
    layer.confirm("确认删除菜单/权限【" + name + "】吗？", {btn: ['确定', '取消'], title: "提示"}, function (i) {
        $.post("/api/sys/menu/delete",{id: id},function (data) {
            if(data.code == 200){
                layer.close(i);
                reloadTable();
            }else {
                layer.alert(data.msg);
            }
        },'json');
    });
}
function update(row) {

    var datas = $table.bootstrapTable('getData');
    var parentName = getParentName(datas,row,"id","parentId")

    $("#parentId").val(row.parentId);
    $("#parentName").val(parentName);

    $("#id").val(row.id);
    $("#name").val(row.name);
    $("#code").val(row.code);
    $("#url").val(row.url);
    $("#seq").val(row.seq);
    $("#remark").val(row.remark);

    $("input[name='status'][value="+row.status+"]").attr("checked",true);
    $("input[name='type'][value="+row.type+"]").attr("checked",true);
    $("input[name='visible'][value="+row.visible+"]").attr("checked",true);

    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "新增菜单",
        area: ['550px', '720px'],
        shadeClose: false,
        content: jQuery("#add-menu"),
        btn: ['确定','取消'],
        btn1: function (index) {
            $.post("/api/sys/menu/update",$("#add-menu-form").serialize(),function (data) {
                if(data.code == 200){
                    layer.close(index);
                    reloadTable();
                }else {
                    layer.alert(data.msg);
                }
            },'json');
        },
        end: function () {
            $('#add-menu-form')[0].reset();
            $("input[name='status']").attr("checked",false);
            $("input[name='type']").attr("checked",false);
            $("input[name='visible']").attr("checked",false);
        }
    });
}

function getParentName(datas,row,id,pid){
    for(var i in datas){
        if(datas[i][id] == row[pid]){
            return datas[i].name;
        }
    }
    return "";
}

function reloadTable() {
    $table.bootstrapTable('destroy');
    Menu.init();
}
