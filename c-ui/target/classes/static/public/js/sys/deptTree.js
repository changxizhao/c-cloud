Tree = $(function () {

    var $selectableTree;

    Tree.deptTreeData;

    Tree.initTree = function(treeId,mark) {
        $.getJSON("/sys/dept/tree?_" + $.now(), function (r) {
            //setDeptTreeView(r.data);
            Tree.deptTreeData = r.data;
            $selectableTree = initSelectableTree(treeId, r.data, mark);
            $selectableTree.treeview('collapseAll', {silent: $('#chk-expand-silent').is(':checked')});
        });
    }

    // 初始化部门列表
    var initSelectableTree = function(treeId,defaultData,mark) {
    //function setDeptTreeView(defaultData) {
        return $('#'+treeId).treeview({
            color: "#000000",
            expandIcon: 'glyphicon glyphicon-chevron-right',
            collapseIcon: 'glyphicon glyphicon-chevron-down',
            nodeIcon: 'glyphicon glyphicon-bookmark',
            showBorder: false,
            data: defaultData,
            onNodeSelected: function(event, node) {
                if(mark == 1){ // 如果mark=1，则为部门管理
                    reloadDeptTable(node.tags);
                }else if(mark == 0) { // 用户管理
                    alert("用户管理：" + node.tags);
                }

            }
        });
    }

    var findSelectableNodes = function() {
        return $selectableTree.treeview('search', [ $('#input-select-node').val(), { ignoreCase: false, exactMatch: false } ]);
    };

    $('#input-select-node').on('keydown', function (e) {
        if (e.keyCode == "13") {
            selectableNodes = findSelectableNodes();
            $selectableTree.treeview('selectNode', [selectableNodes, {silent: $('#chk-select-silent').is(':checked')}]);
        }
    });
});

function chooseDept() {
    initParentTree('select-parent-dept', 1);
    layer.open({
        type: 1,
        skin: 'layui-layer-lan',
        title: "选择上级部门",
        area: ['300px', '550px'],
        shadeClose: false,
        content: jQuery("#select-parent-dept-tree"),
        btn: ['取消'],
        btn1: function (index) {
            layer.close(index);
        }
    });
}

var initParentTree = function(domId, mark) {
    $('#' + domId).treeview({
        color: "#000000",
        expandIcon: 'glyphicon glyphicon-chevron-right',
        collapseIcon: 'glyphicon glyphicon-chevron-down',
        nodeIcon: 'glyphicon glyphicon-bookmark',
        showBorder: false,
        data: Tree.deptTreeData,
        onNodeSelected: function (event, node) {
            var deptName = node.text;
            var deptId = node.tags;
            if(mark == 1) { // 部门管理
                $("#parentName").val(deptName);
                $("#parentId").val(deptId.toString());
            }else if(mark == 0) { // 用户管理

            }
            layer.close(layer.index);
        }
    });
}

// $('#region').click(function () {
//     $('#tv').show();
// })