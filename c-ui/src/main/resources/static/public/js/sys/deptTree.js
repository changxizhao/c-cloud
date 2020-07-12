$(function () {

    var $selectableTree;

    $.getJSON("/sys/dept/tree?_"+$.now(), function(r){
        //setDeptTreeView(r.data);
        $selectableTree = initSelectableTree(r.data);
        $selectableTree.treeview('collapseAll', { silent: $('#chk-expand-silent').is(':checked') });
    });


    var initSelectableTree = function(defaultData) {
    //function setDeptTreeView(defaultData) {
        return $('#treeview5').treeview({
            color: "#000000",
            expandIcon: 'glyphicon glyphicon-chevron-right',
            collapseIcon: 'glyphicon glyphicon-chevron-down',
            nodeIcon: 'glyphicon glyphicon-bookmark',
            showBorder: false,
            data: defaultData,
            onNodeSelected: function(event, node) {
                alert(node.tags);
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