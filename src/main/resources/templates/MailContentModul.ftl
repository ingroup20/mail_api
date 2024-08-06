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

<p style="color: red">※本信件是由關網雲端電子發票服務平台系統自動產生與發送，請勿直接回覆※</p>
<p>親愛的 ${companyName} 您好: <br>

    附件為貴公司在 ${today} 經由關網雲端電子發票整合服務平台系統產生的電子發票統計表。<br>
    在開立電子發票的四十八小時後，可於<a href='https://www.einvoice.nat.gov.tw/'>財政部電子發票平台</a>進行相關查詢發票狀態。<br>
    <a href='http://www.gateweb.com.tw/'>關網資訊股份有限公司</a>敬上<br>
    客服時間: 一般上班日 09:30-12:00 及 13:30-18:00<br>
    客服專線: (02)-7750-5070</p>


<h2>發票開立統計</h2>
<table class="t01" border="1">
    <tr>
        <th>發票日期
        <th>開立張數
        <th>開立金額
        <th>作廢張數
        <th>作廢金額
        <th>註銷張數
        <th>註銷金額
        <th>總計張數
    </tr>
<#list invoiceInformationList as InvoiceInformation>
    <tr><td>${InvoiceInformation.invoiceDate}
        <td>${InvoiceInformation.openCount}
        <td>${InvoiceInformation.openTotalAmount}
        <td>${InvoiceInformation.cancelCount}
        <td>${InvoiceInformation.cancelTotalAmount}
        <td>${InvoiceInformation.voidCount}
        <td>${InvoiceInformation.voidTotalAmount}
        <td>${add(InvoiceInformation.openCount,InvoiceInformation.cancelCount,InvoiceInformation.voidCount)}
    </tr>
</#list>
</table>
<h2>漏號列表</h2>
<table class="t01" border="1">
    <tr>
        <th>字軌起號</th>
        <th>漏號區間</th>
    </tr>
<#list invoiceJumpResultList as InvoiceJumpResult>
    <tr>
        <td>${InvoiceJumpResult.assign}
        <td>${InvoiceJumpResult.start} ~ ${InvoiceJumpResult.end}
    </tr>
</#list>
</table>
<h2>財政部上傳錯誤</h2>
<table class="t01" border="1">
    <tr>
        <th>上傳日期</th>
        <th>發票號碼</th>
        <th>動作</th>
        <th>錯誤訊息</th>
<#--        <th>錯誤狀態</th>-->
    </tr>
<#list invoiceUploadInformationList as InvoiceUploadInformation>
    <tr>
        <td>${InvoiceUploadInformation.actionDate!"null"}
        <td>${InvoiceUploadInformation.invoiceNumber}
        <td>${InvoiceUploadInformation.uploadType}
        <td>${InvoiceUploadInformation.errorDetail}
<#--        <td>${not(InvoiceUploadInformation.errorStatus)}-->
    </tr>
</#list>
</table>
<p style="color: red">※本信件是由關網雲端電子發票服務平台系統自動產生與發送，請勿直接回覆※</p>
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