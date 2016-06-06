<!DOCTYPE html>
<!--<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>-->
<html>

<head>
    <meta charset="UTF-8">
    <title>元玉数据</title>
    <meta content="telephone=no" name="format-detection">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="../../resources/src/common/style/main.css">
    <script src="../../resources/library/requirejs/require.min.js"></script>
    <script src="../../resources/requirejs-config.js"></script>
    <script>
        require(['app']);
    </script>
</head>

<body>
    <div id="page-wrap" class="page-wrap ui-view-container">
        <div class="page animate-box" ui-view>

        </div>
        <div id="progress-bar" class="progress-bar">
        	<div></div>
        </div>
    </div>
    <div id="popUp-wrap" class="modal-wrap">
    <div class="popUp-box" cancel-bubble>
        <div>
            <div>
                <div class="popUp-content"></div>
            </div>
        </div>
    </div>
</div>
    <div id="alert" class="modal-wrap">
        <div class="modal">
            <div class="container-fluid area-wrap">
                <p class="text-center"></p>
            </div>
            <div class="container-fluid area-wrap border-top">
                <button class="btn btn-block" type="submit">确认</button>
            </div>
        </div>
    </div>
</body>

</html>
