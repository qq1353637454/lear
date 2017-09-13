<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function diaryDelete(diaryId){
		if(confirm("你确定删除这条日记信息吗？")){
			window.location="diary?action=delete&diaryId="+diaryId
		}
	}
</script>
<div class="data_list">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/diary_show_icon.png"/>
		日记信息</div>
		<div>
			<div class="diary_title">${diaryShow.title }</div>
			<div class="diary_info">
				发布时间：『<fmt:formatDate value="${diaryShow.releaseDate }" type="date" pattern="yyyy-MM-dd"/>』&nbsp;&nbsp;日志类别：${diaryShow.typeName }
			</div>
			<div class="diary_content">
				${diaryShow.content }
			</div>
			<div>
				<button class="btn btn-small " type="button" onclick="">修改信息</button>
				<button class="btn btn-small " type="button" onclick="javascript:history.go(-1)">返回界面</button>
				<button class="btn btn-small " type="button" onclick="diaryDelete(${diaryShow.diaryId })">删除信息</button>
			</div>
		</div>
		
</div>
