<html>
<head>
    <title>Welcome!</title>
    <style>
        table {
            width:100%;
        }
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px;
            text-align: left;
        }
        table.t01 tr:nth-child(even) {
            background-color: #eee;
        }
        table.t01 tr:nth-child(odd) {
            background-color:#fff;
        }
        table.t01 th {
            background-color: black;
            color: white;
        }
    </style>
</head>
<body>

<p style="color: red">※</p>
<p>親愛的 ${companyName} 您好: <br>


<h2>統計</h2>
<table class="t01" >
    <tr>
        <th>日期
        <th>張數
        <th>金額
        <th>總計
    </tr>
<#list List as Information>
    <tr><td>${eInformation.Date}
        <td>${Information.nCount}
        <td>${Information.Amount}
        <td>${Information.AllCount}
    </tr>
</#list>
</table>

</table>
<p style="color: red">※本信件是系統自動產生與發送，請勿直接回覆※</p>
</body>
</html>
<#function add x y z>
    <#return x + y + z>
</#function>
<#function not x >
    <#switch x>
        <#case 0>
            <#return "未處理">
            <#break>
        <#case 1>
            <#return "已處理">
            <#break>
    </#switch>
</#function>