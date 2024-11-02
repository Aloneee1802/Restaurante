<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test = "${sessionScope.error !=null}">
    <script>
         fnError('${sessionScope.error }');
    </script>
</c:if> 

<c:if test = "${sessionScope.success !=null}">
    <script>
        fnSuccess('${sessionScope.success }')
    </script>
</c:if>

<c:remove var="success" scope="session"/> 
<c:remove var="error" scope="session"/> 