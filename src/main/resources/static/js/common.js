/*document.addEventListener('plusready', function() {
    var webview = plus.webview.currentWebview();
    plus.key.addEventListener('backbutton', function() {
        webview.canBack(function(e) {
            if(e.canBack) {
                webview.back();
            } else {
                webview.close(); //hide,quit
                //plus.runtime.quit();
            }
        });
    });
});*/


//window.ajaxURL = 'http://www.sxqchr.com:3334';//正式环境 
window.ajaxURL = 'http://localhost:8089';//测试环境

function getData(url, data, type, callback, failureBack) {
    //console.log("get"+JSON.stringify(data));
    console.log(JSON.stringify(window.ajaxURL + url));
    $.ajax({
        url: window.ajaxURL + url,
        type: type,
        data: data || {},
        dataType: "json",
        xhrFields: {
            withCredentials: true
        },
        success: function (res) {
            callback(res);
        },
        error: function (e) {
            if (failureBack != null) {
                failureBack(e);
            } else {
                //alert("请求失败，请联系管理员"+url);
                console.log(JSON.stringify(e) + url);
                return;
            }
        }
    });
}

function downloadFile(url, data,bodyData, type, filename) {
    // var confirmDialog = layer.confirm('确认信息正确开始下载？', {
    //     area: ['60%', '28%'],
    //     btn: ['确认', '取消'] //按钮
    // }, function () {
    //   layer.close(confirmDialog);
        var queryString = Object.keys(data).map(key => key + '=' + encodeURIComponent(data[key])).join('&');
        var urlWithParams = url + '?' + queryString;

        var ajaxRequest = new XMLHttpRequest();
        ajaxRequest.open(type, urlWithParams, true);
        ajaxRequest.setRequestHeader("Content-type", "application/json");
        ajaxRequest.responseType = "blob";

        ajaxRequest.onload = function () {
            if (ajaxRequest.status === 200) {
                var blob = ajaxRequest.response;
                var link = document.createElement("a");
                link.href = window.URL.createObjectURL(blob);
                link.download = filename;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
                // 文件下载完成后关闭弹窗

            } else {
                console.error('Failed to download file. Status: ' + ajaxRequest.status);
            }
        };
        ajaxRequest.onerror = function () {
            console.error('Network error occurred while trying to download the file.');
        };
        if (bodyData != null) {
            ajaxRequest.send(bodyData);
        } else {
            ajaxRequest.send();
        }
    // }, function () {
    //     // 取消操作，不执行任何操作
    // });
}





