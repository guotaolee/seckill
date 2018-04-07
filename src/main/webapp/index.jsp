<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <style>
        .wrap{
            position: relative;
            width: 200px;
            left: 50px;
            top: 50px;
        }
        ul{
            padding: 15px 0px;
            margin: 9px;
            list-style: none;
            background: #6c6669;
            color: #ffffff;
            border-right-width: 0px;
        }
        li{
            display: block;
            height: 30px;
            line-height: 30px;
            padding-left: 12px;
            cursor: pointer;
            font-size: 14px;
            position: relative;
        }
        li.active{
            background: #999395;
        }
        li span:hover {
            color: #c81623;
        }

        .none {
            display: none;
        }

        #sub {
            width: 600px;
            position: absolute;
            border: 1px solid #f7f7f7;
            background: #f7f7f7;
            box-shadow: 2px 0 5px rgba(0,0,0,.3);
            left: 200px;
            top: 0px;
            box-sizing: border-box;
            margin: 0px;
            padding: 10px;
        }

        .sub_content a {
            font-size: 12px;
            color: #666;
            text-decoration: none;
        }

        .sub_content dd a {
            border-left: 1px solid #e0e0e0;
            padding: 0 10px;
            margin: 4px 0;
        }

        .sub_content dl {
            overflow: hidden;
        }

        .sub_content dt {
            float: left;
            width: 70px;
            font-weight: bold;
            clear: left;
            position: relative;
        }

        .sub_content dd {
            float: left;
            margin-left: 5px;
            border-top: 1px solid #eee;
            margin-bottom: 5px;
        }

        .sub_content dd i {
            width: 4px;
            height: 14px;
            font: 400 9px/14px Consolas;
            position: absolute;
            right: 5px;
            top: 5px;
        }
    </style>
</head>
<body>
<div class="wrap" id="test">
<ul>
    <li data-id="a">
        <span>家用电器</span>
    </li>
    <li data-id="b">
        <span>手机</span>
    </li>
    <li data-id="c">
        <span>家用电器</span>
    </li>
    <li data-id="d">
        <span>男装</span>
    </li>
    <li data-id="">
        <span>女装</span>
    </li>
</ul>
    <div id="sub" class="">
        <div id="a" class="sub_content">
            <dl>
                <dt>
                    <a href="#">电视<i>&gt;</i></a>
                </dt>
                <dd>
                    <a href="#">合资品牌</a>
                    <a href="#">国产品牌</a>
                    <a href="#">互联网品牌</a>
                </dd>
            </dl>
            <dl>
                <dt>
                    <a href="#">电视<i>&gt;</i></a>
                </dt>
                <dd>
                    <a href="#">合资品牌</a>
                    <a href="#">国产品牌</a>
                    <a href="#">互联网品牌</a>
                </dd>
            </dl>
            <dl>
                <dt>
                    <a href="#">电视<i>&gt;</i></a>
                </dt>
                <dd>
                    <a href="#">合资品牌</a>
                    <a href="#">国产品牌</a>
                    <a href="#">互联网品牌</a>
                </dd>
            </dl>
            <dl>
                <dt>
                    <a href="#">电视<i>&gt;</i></a>
                </dt>
                <dd>
                    <a href="#">合资品牌</a>
                    <a href="#">国产品牌</a>
                    <a href="#">互联网品牌</a>
                </dd>
            </dl>
        </div>
    </div>
</div>
<script src="resources/script/jquery-3.3.1.min.js"></script>
<script src="resources/script/megadropdown.js"></script>
</body>
</html>
