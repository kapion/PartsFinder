<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">

<!-- ___ Part finder web app ___
@autor Aleksandr Kuznetsov (kapion)
https://github.com/kapion/Parts-finder.git

Finder perform outputs list of Parts in HTML format.
-       List of Parts is a table with the columns corresponding to Part fields.
-       Before the table on page there is a filter which allows to filter output Part records.
-       If none of the filter fields is specified then all Parts should be shown.
-       The table can be sorted by any of the columns by clicking on their header (one click - ASC order, next click - DESC order).
-       Sorting performed only by one column at a time.
-       When user uses sorting - filtering is still applied.
-       Output and input format for dates is “MMM dd, yyyy”
-       Parts are stored in database.
-       No interfaces to edit or add Part are needed.

Filter notes:
-       Text fields are filtered using “like” criteria.
-       Integer fields are filtered using “not less” criteria.
-       Date fields are filtered using range.

-->

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/jquery-ui.min.css" rel="stylesheet" />
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/jquery-ui.min.js"></script>

    <!-- Set custom dateformat on datepicker -->
    <script type="text/javascript">
        $(function() {
            $.datepicker.setDefaults({dateFormat:"M dd, yy"});
            $('#shp_from').datepicker();
            $('#shp_to').datepicker();
            $('#rcv_from').datepicker();
            $('#rcv_to').datepicker();
        });
    </script>

</head>

<body>
<div class="container">

    <h2>Parts finder</h2>

<form class="form-horizontal" action="doAction"  method="POST">
    <!-- PN group -->
    <div class="form-group">
        <label for="pn" class="col-xs-2 control-label">Part Number</label>
        <div class="col-xs-3">
            <input type="text" class="form-control input-sm" id="pn" value="${pn}" name="part_number"/>
        </div>
    </div>

    <!-- Part Name group -->
    <div class="form-group">
        <label for="pname" class="col-xs-2 control-label">Part Name</label>
        <div class="col-xs-4">
            <input type="text" class="form-control input-sm " value="${pname}" id="pname" name="part_name"/>
        </div>
    </div>

    <!-- Vendor group -->
    <div class="form-group">
        <label for="vndr" class="col-xs-2 control-label">Vendor</label>
        <div class="col-xs-4">
            <input type="text" class="form-control input-sm " value="${vendor}" id="vndr" name="vendor"/>
        </div>
    </div>

    <!-- Qty group -->
    <div class="form-group">
        <label for="qty" class="col-xs-2 control-label">Qty</label>
        <div class="col-xs-1">
            <input type="number" class="form-control input-sm" value="${qty}" id="qty" name="qty"/>
        </div>
    </div>

    <!-- Date Shipped group -->
    <div class="form-group row">
        <label for="shp_from" class="col-xs-2 control-label">Shipped</label>
        <div class="col-xs-4">
            <div class="input-group">
                <div class="input-group-addon">from:</div>
                <input class="form-control" value="${s_from}" id="shp_from" name="shipped_from"/>
            </div>
        </div>
        <div class="col-xs-4">
            <div class="input-group">
                <div class="input-group-addon">to:</div>
                <input class="form-control" value="${s_to}" id="shp_to" name="shipped_to"/>
            </div>
        </div>
    </div>

    <!-- Date Receive group -->
    <div class="form-group row">
        <label for="rcv_from" class="col-xs-2 control-label">Receive</label>
        <div class="col-xs-4">
            <div class="input-group">
                <div class="input-group-addon">from:</div>
                <input class="form-control" value="${r_from}" id="rcv_from" name="receive_from"/>
            </div>
        </div>
        <div class="col-xs-4">
            <div class="input-group">
                <div class="input-group-addon">to:</div>
                    <input class="form-control ui-widget" value="${r_to}"id="rcv_to" name="receive_to"/>

            </div>
        </div>
    </div>


    <div class="form-group text-center">
        <div class="row">
            <button type="submit" id="submit-btn" class="btn btn-primary"
                    title="Apply filter criteria">Filter</button>
        </div>
    </div>

</form>

<table id="main_table" class="table table-bordered table-condensed">
    <tr>
        <th><a href="/doAction?sortCellIndex=1" title="One click sorting in ASC order, next click - DESC order">PN</a></th>
        <th><a href="/doAction?sortCellIndex=2" title="One click sorting in ASC order, next click - DESC order">Part Name</a></th>
        <th><a href="/doAction?sortCellIndex=3" title="One click sorting in ASC order, next click - DESC order">Vendor</a></th>
        <th><a href="/doAction?sortCellIndex=4" title="One click sorting in ASC order, next click - DESC order">Qty</a></th>
        <th><a href="/doAction?sortCellIndex=5" title="One click sorting in ASC order, next click - DESC order">Shipped</a></th>
        <th><a href="/doAction?sortCellIndex=6" title="One click sorting in ASC order, next click - DESC order">Received</a></th>
    </tr>
    <c:forEach items="${parts}" var="part">
        <tr>
            <td>${part.number}</td>
            <td>${part.name}</td>
            <td>${part.vendor}</td>
            <td>${part.qty}</td>
            <td>${part.shipped}</td>
            <td>${part.receive}</td>
        </tr>
    </c:forEach>
</table>
</div>
</body>
</html>
