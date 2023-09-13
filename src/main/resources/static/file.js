document.getElementById("pageListForm").addEventListener("submit", function(event) {
    event.preventDefault(); // 阻止表单默认的提交行为

    var pageNum = document.getElementById("pageNum").value;
    var pageSize = document.getElementById("pageSize").value;
    var sortField = document.getElementById("sortField").value;
    var sortOrder = document.getElementById("sortOrder").value;
    var filters = document.getElementById("filters").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/user/pageList", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            // 在这里处理返回的分页列表数据并更新页面
            document.getElementById("result").innerHTML = "Total: " + response.total + ", Data: " + JSON.stringify(response.data);
        }
    };
    xhr.send(JSON.stringify({
        pageNum: pageNum,
        pageSize: pageSize,
        sortField: sortField,
        sortOrder: sortOrder,
        filters: filters
    }));
});