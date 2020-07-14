Dept = $(function(){
    Tree.initTree('treeview5',1);

    window.operateEvents = {
        'click #editDept': function (e, value, row, index) { // 编辑部门
            editDept(row);
            //$("#upload").modal('show');
        },

        'click #deleteDept': function (e, value, row, index) {
            alert("删除部门 ：" + row.name);
            //$("#upload").modal('show');
        }
    };

    initDeptTable('0');
});

// 初始化部门列表
function initDeptTable(level) {
    var opt = getTableOption(level);
    $('#dept-detail-table').bootstrapTable(opt);
    $('#dept-detail-table').bootstrapTable('hideColumn', 'parentId');
}

// 刷新部门列表
function reloadDeptTable(level) {
    // alert("level = " + level);
    var queryParams = {"pageNumber":1, "pageSize":10, "level": level.toString()};
    console.log("query = " + level.toString());
    $.get("/sys/dept/table",queryParams,function (data) {
        $("#dept-detail-table").bootstrapTable('load',data);
    })
}

// 获取部门列表参数
var getTableOption = function (level) {

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
                pageSize : this.pageSize,
                level : level
            };
            return param;
        },
        columns: [
            {checkbox:true},
            { title: '部门ID', field: 'id',align: 'center'},
            { title:'部门名称', field:'name',align: 'center'},
            { title: '上级部门', field: 'parentName',align: 'center'},
            { title: '上级部门id', field: 'parentId', align: 'center'},
            { title: '显示顺序', field: 'seq',align: 'center'},
            { title: '备注', field: 'remark',align: 'center'},
            { title: '操作', field: '',align: 'center',events: operateEvents,formatter: operateFormatter}
        ]
    };

    return option;
}

function operateFormatter(value, row, index) {
    return [
        '<button class="btn btn-info" type="button" id="editDept"><i class="fa fa-edit"> 编辑</i></button>&nbsp;&nbsp;&nbsp;',
        '<button class="btn btn-danger ml-3" type="button" id="deleteDept"><i class="fa fa fa-remove"> 删除</i></button>'
    ].join('');

}

// 新增部门
function saveDept() {
    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "新增部门",
        area: ['550px', '350px'],
        shadeClose: false,
        content: jQuery("#add-dept"),
        btn: ['确定','取消'],
        btn1: function (index) {
            $.post("/sys/dept/add",$("#add-dept-form").serialize(),function (data) {
                if(data.code == 200){
                    //$('#add-dept')[0].reset();
                    Tree.initTree('treeview5',1);
                    layer.close(index);
                    $('#dept-detail-table').bootstrapTable('refresh');
                }else {
                    alert(data.msg);
                }
            },'json');
        }
    });
}

function editDept(row) {
    //alert(row.id + "-" + row.parentId);

    $("#id").val(row.id);
    $("#name").val(row.name);
    $("#parentId").val(row.parentId);
    $("#parentName").val(row.parentName);
    $("#seq").val(row.seq);
    $("#remark").val(row.remark);

    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "新增部门",
        area: ['550px', '350px'],
        shadeClose: false,
        content: jQuery("#add-dept"),
        btn: ['确定','取消'],
        btn1: function (index) {
            $.post("/sys/dept/update",$("#add-dept-form").serialize(),function (data) {
                if(data.code == 200){
                    //$('#add-dept')[0].reset();
                    Tree.initTree('treeview5',1);
                    layer.close(index);
                    $('#dept-detail-table').bootstrapTable('refresh');
                }else {
                    alert(data.msg);
                }
            },'json');
        }
    });
}
