<%--
  Created by IntelliJ IDEA.
  User: caballeto
  Date: 15.12.19
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="language" value="${pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<html lang="${language}">
<head>
    <title>Project Versioning</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <style>
        .card {
            margin-top: 10px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<fmt:message key="view.placeholder.program" var="programNameHolder"/>
<fmt:message key="view.placeholder.version" var="programVersionHolder"/>
<fmt:message key="view.placeholder.file" var="fileNameHolder"/>
<fmt:message key="view.placeholder.initversion" var="initVersionHolder"/>

<div class="container" style="margin-top: 25px;">
    <c:if test="${not empty error}">
        <div class="row">
            <div class="col-md-12">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </div>
        </div>
    </c:if>

    <div class="row">
        <div class="col-3">
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#v-pills-home" role="tab" aria-controls="v-pills-home" aria-selected="true">
                    <fmt:message key="view.program"/>
                </a>
                <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#v-pills-profile" role="tab" aria-controls="v-pills-profile" aria-selected="false">
                    <fmt:message key="view.versions"/>
                </a>
                <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#v-pills-messages" role="tab" aria-controls="v-pills-messages" aria-selected="false">
                    <fmt:message key="view.files"/>
                </a>
                <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#v-pills-settings" role="tab" aria-controls="v-pills-settings" aria-selected="false">
                    <fmt:message key="view.usages"/>
                </a>
            </div>
        </div>
        <div class="col-9">
            <div class="tab-content" id="v-pills-tabContent">
                <div class="tab-pane fade show active" id="v-pills-home" role="tabpanel" aria-labelledby="v-pills-home-tab">
                    <div class="row">
                        <div class="col-md-6">
                            <form method="post">
                                <div class="form-group">
                                    <label for="insertProgramProgramName"><fmt:message key="view.enter"/> <fmt:message key="view.program.name"/></label>
                                    <input name="programName" type="text" class="form-control" id="insertProgramProgramName"
                                           placeholder="${programNameHolder}" required>
                                </div>

                                <div class="form-group">
                                    <input type="hidden" name="type" value="insertProgram">
                                </div>

                                <button type="submit" class="btn btn-primary"><fmt:message key="view.insert" /></button>
                            </form>
                        </div>
                        <div class="col-md-6">
                            <form method="post">
                                <div class="form-group">
                                    <label for="deleteProgramProgramName"><fmt:message key="view.enter"/> <fmt:message key="view.program.name"/></label>
                                    <input name="programName" required type="text" class="form-control" id="deleteProgramProgramName" placeholder="${programNameHolder}">
                                </div>

                                <input type="hidden" name="type" value="deleteProgram">

                                <button type="submit" class="btn btn-danger"><fmt:message key="view.delete" /></button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">
                    <div class="row">
                        <div class="col-md-6">
                            <form method="post">
                                <div class="form-group">
                                    <label for="insertPVProgramName"><fmt:message key="view.enter"/> <fmt:message key="view.program.name"/></label>
                                    <input name="programName" type="text" class="form-control" id="insertPVProgramName"
                                           placeholder="${programNameHolder}" required>
                                </div>

                                <div class="form-group">
                                    <label for="insertPVProgramVersion"><fmt:message key="view.enter"/> <fmt:message key="view.program.version"/></label>
                                    <input type="text" class="form-control" id="insertPVProgramVersion"
                                           name="programVersion" placeholder="${programVersionHolder}" required>
                                </div>

                                <div class="form-group">
                                    <input type="hidden" name="type" id="insertPV" value="insertPV">
                                </div>

                                <button type="submit" class="btn btn-primary"><fmt:message key="view.insert" /></button>
                            </form>
                        </div>
                        <div class="col-md-6">
                            <form method="post">
                                <div class="form-group">
                                    <label for="deletePVProgramName"><fmt:message key="view.enter"/> <fmt:message key="view.program.name"/></label>
                                    <input name="programName" required type="text" class="form-control" id="deletePVProgramName" placeholder="${programNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="deletePVProgramVersion"><fmt:message key="view.enter"/> <fmt:message key="view.program.version"/></label>
                                    <input name="programVersion" required type="text" class="form-control" id="deletePVProgramVersion"
                                           placeholder="${programVersionHolder}">
                                </div>

                                <input type="hidden" name="type" value="deletePV">

                                <button type="submit" class="btn btn-danger"><fmt:message key="view.delete" /></button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="v-pills-messages" role="tabpanel" aria-labelledby="v-pills-messages-tab">
                    <div class="row">
                        <div class="col-md-6">
                            <form method="post">
                                <div class="form-group">
                                    <label for="insertFileProgramName"><fmt:message key="view.enter"/> <fmt:message key="view.program.name"/></label>
                                    <input required name="programName" type="text" class="form-control" id="insertFileProgramName" placeholder="${programNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="insertFileProgramVersion"><fmt:message key="view.enter"/> <fmt:message key="view.program.version"/></label>
                                    <input required type="text" class="form-control" id="insertFileProgramVersion"
                                           name="programVersion" placeholder="${programVersionHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="insertFileFileName"><fmt:message key="view.enter"/> <fmt:message key="view.file.name"/></label>
                                    <input required name="fileName" type="text" class="form-control" id="insertFileFileName" placeholder="${fileNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="insertFileInitialVersion"><fmt:message key="view.enter"/> <fmt:message key="view.file.version"/></label>
                                    <input required name="initVersion" type="text" class="form-control" id="insertFileInitialVersion" placeholder="${initVersionHolder}">
                                </div>
                                <div class="form-group">
                                    <input type="hidden" name="type" id="inserFile" value="insertFile">
                                </div>

                                <button type="submit" class="btn btn-primary"><fmt:message key="view.insert" /></button>
                            </form>
                        </div>
                        <div class="col-md-6">
                            <form method="post">
                                <div class="form-group">
                                    <label for="deleteFileProgramName"><fmt:message key="view.enter"/> <fmt:message key="view.program.name"/></label>
                                    <input name="programName" required type="text" class="form-control" id="deleteFileProgramName" placeholder="${programNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="deleteFileProgramVersion"><fmt:message key="view.enter"/> <fmt:message key="view.program.version"/></label>
                                    <input name="programVersion" required type="text" class="form-control" id="deleteFileProgramVersion"
                                           placeholder="${programVersionHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="deleteFileFileName"><fmt:message key="view.enter"/> <fmt:message key="view.file.name"/></label>
                                    <input name="fileName" required type="text" class="form-control" id="deleteFileFileName" placeholder="${fileNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="deleteFileInitialVersion"><fmt:message key="view.enter"/> <fmt:message key="view.file.version"/></label>
                                    <input name="initVersion" required type="text" class="form-control" id="deleteFileInitialVersion" placeholder="${initVersionHolder}">
                                </div>

                                <input type="hidden" name="type" value="deleteFile">

                                <button type="submit" id="deleteFile" class="btn btn-danger"><fmt:message key="view.delete" /></button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
                    <div class="row">
                        <div class="col-md-6">
                            <form method="post">
                                <div class="form-group">
                                    <label for="insertFUProgramName"><fmt:message key="view.enter"/> <fmt:message key="view.program.name"/></label>
                                    <input required name="programName" type="text" class="form-control" id="insertFUProgramName" placeholder="${programNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="insertFUProgramVersion"><fmt:message key="view.enter"/> <fmt:message key="view.program.version"/></label>
                                    <input required type="text" class="form-control" id="insertFUProgramVersion"
                                           name="programVersion" placeholder="${programVersionHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="insertFUFileName1"><fmt:message key="view.enter"/> <fmt:message key="view.file.name"/> 1</label>
                                    <input required name="fileName1" type="text" class="form-control" id="insertFUFileName1" placeholder="${fileNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="insertFUFileName2"><fmt:message key="view.enter"/> <fmt:message key="view.file.name"/> 2</label>
                                    <input required name="fileName2" type="text" class="form-control" id="insertFUFileName2" placeholder="${fileNameHolder}">
                                </div>

                                <div class="form-group">
                                    <input type="hidden" name="type" id="insertFU" value="insertFU">
                                </div>

                                <button type="submit" class="btn btn-primary"><fmt:message key="view.insert" /></button>
                            </form>
                        </div>
                        <div class="col-md-6">
                            <form method="post">
                                <div class="form-group">
                                    <label for="deleteFUProgramName"><fmt:message key="view.enter"/> <fmt:message key="view.program.name"/></label>
                                    <input name="programName" required type="text" class="form-control" id="deleteFUProgramName" placeholder="${programNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="deleteFUProgramVersion"><fmt:message key="view.enter"/> <fmt:message key="view.program.version"/></label>
                                    <input name="programVersion" required type="text" class="form-control" id="deleteFUProgramVersion"
                                           placeholder="${programVersionHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="deleteFUFileName1"><fmt:message key="view.enter"/> <fmt:message key="view.file.name"/> 1</label>
                                    <input name="fileName1" required type="text" class="form-control" id="deleteFUFileName1" placeholder="${fileNameHolder}">
                                </div>

                                <div class="form-group">
                                    <label for="deleteFUFileName2"><fmt:message key="view.enter"/> <fmt:message key="view.file.name"/> 2</label>
                                    <input name="fileName2" required type="text" class="form-control" id="deleteFUFileName2" placeholder="${fileNameHolder}">
                                </div>

                                <input type="hidden" name="type" value="deleteFU">

                                <button type="submit" id="deleteFU" class="btn btn-danger"><fmt:message key="view.delete" /></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div id="accordion">
                <c:set var="outc" value="1" scope="page" />
                <c:forEach items="${programs}" var="program">
                    <div class="card">
                        <div class="card-header" id="heading${outc}">
                            <h5 class="mb-0">
                                <button class="btn btn-link" data-toggle="collapse" data-target="#collapse${outc}" aria-expanded="true" aria-controls="collapse${outc}">
                                    ${program.name}
                                </button>
                            </h5>
                        </div>

                        <div id="collapse${outc}" class="collapse show" aria-labelledby="heading${outc}" data-parent="#accordion">
                            <div class="card-body">
                                <div id="accordion${outc}">
                                    <c:set var="inc" value="1" scope="page" />
                                    <c:forEach items="${program.versions}" var="version">
                                        <div class="card">
                                            <div class="card-header" id="heading${inc}">
                                                <h5 class="mb-0">
                                                    <button class="btn btn-link" data-toggle="collapse" data-target="#collapse${outc*1000 + inc}" aria-expanded="true" aria-controls="collapse${outc*1000 + inc}">
                                                        ${programVersionHolder} ${version.version}
                                                    </button>
                                                </h5>
                                            </div>

                                            <div id="collapse${outc*1000 + inc}" class="collapse show" aria-labelledby="heading${outc*1000 + inc}" data-parent="#accordion${outc}">
                                                <div class="card-body">
                                                    <ul class="list-group">
                                                        <c:forEach items="${version.files}" var="file">
                                                            <li class="list-group-item">${file.name} ${file.variant}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <c:set var="inc" value="${inc + 1}" scope="page" />
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>

                    <c:set var="outc" value="${outc + 1}" scope="page" />
                </c:forEach>
            </div>
        </div>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
