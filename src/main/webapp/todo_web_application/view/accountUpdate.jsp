<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/todo_web_application/common/header.jspf" %>
<%@include file="/todo_web_application/common/nav_logout.jspf" %> 

<style>

.container{
    margin-top:20px;
}
.image-preview-input {
    position: relative;
    overflow: hidden;
    margin: 0px;    
    color: #333;
    background-color: #fff;
    border-color: #ccc;    
}
.image-preview-input input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	padding: 0;
	font-size: 20px;
	cursor: pointer;
	opacity: 0;
	filter: alpha(opacity=0);
}
.image-preview-input-title {
    margin-left:2px;
}

</style>



 
 <div class="container">
 
 <H1>Account Details </H1>
 
 
      
      

<div class="row">  



		<div class="col-md-6">
            
            <div class="row">
                   <img  src="${fileName}" alt="..." class="img-responsive center-block" width="200px" height="200px">  
            </div>
            <div class="row">
            <form action="upload.do" method="post" enctype="multipart/form-data" >
         
            <!-- image-preview-filename input [CUT FROM HERE]-->
            <font color="red">${error}</font>
      		<font color="green">${message}</font>
            <div class="input-group image-preview">
            
                <input type="text" class="form-control image-preview-filename" disabled="disabled"> 
                
                <span class="input-group-btn">
                    <!-- image-preview-clear button -->
                    <button type="button" class="btn btn-default image-preview-clear" style="display:none;">
                        <span class="glyphicon glyphicon-remove"></span> Clear
                    </button>
                    <!-- image-preview-input -->
                    <div class="btn btn-default image-preview-input">
                        <span class="glyphicon glyphicon-folder-open"></span>
                        <span class="image-preview-input-title">Browse</span>
                        <input type="file" accept="image/png, image/jpeg, image/gif" name="input-file-preview"/>
                    </div>
                </span>
            </div><!-- /input-group image-preview [TO HERE]--> 
            <button id="submit" class="btn btn-lg btn-primary btn-block" type="submit">Upload Image/Avatar</button> 
             </form>
            </div>
            
            
             
        </div>
        <div class="col-md-6">
            <c:forEach items="${user}" var="u">
 	<form action="account-update.do" method="post" class="form-signin">
		<p>
			<font color="red">${error}</font>
			<font color="green">${success}</font>
		</p>
			<input type="text" class="form-control" name="name" placeholder="Name" required="" autofocus="" value="${u.username}" />
			<input type="text" class="form-control" name="fullname" placeholder="Full Name" required="" autofocus="" value="${u.fullname}" />
			<input type="text" class="form-control" name="email" placeholder="Email ID" required="" autofocus="" value="${u.email}" />
      	<input id="token" class="form-control" name="token" type="hidden" value="${sessionScope.csrfToken}" />
      <button id="submit" class="btn btn-lg btn-primary btn-block" type="submit">Update Account</button> 
			    	
	</form>
</c:forEach>	
        </div>

    </div>
  

 </div>
 
 
 
 
 
 
 
 
 
 
 
 
 <script type="text/javascript">
 $(document).on('click', '#close-preview', function(){ 
	    $('.image-preview').popover('hide');
	    // Hover befor close the preview
	    $('.image-preview').hover(
	        function () {
	           $('.image-preview').popover('show');
	        }, 
	         function () {
	           $('.image-preview').popover('hide');
	        }
	    );    
	});

	$(function() {
	    // Create the close button
	    var closebtn = $('<button/>', {
	        type:"button",
	        text: 'x',
	        id: 'close-preview',
	        style: 'font-size: initial;',
	    });
	    closebtn.attr("class","close pull-right");
	    // Set the popover default content
	    $('.image-preview').popover({
	        trigger:'manual',
	        html:true,
	        title: "<strong>Preview</strong>"+$(closebtn)[0].outerHTML,
	        content: "There's no image",
	        placement:'bottom'
	    });
	    // Clear event
	    $('.image-preview-clear').click(function(){
	        $('.image-preview').attr("data-content","").popover('hide');
	        $('.image-preview-filename').val("");
	        $('.image-preview-clear').hide();
	        $('.image-preview-input input:file').val("");
	        $(".image-preview-input-title").text("Browse"); 
	    }); 
	    // Create the preview image
	    $(".image-preview-input input:file").change(function (){     
	        var img = $('<img/>', {
	            id: 'dynamic',
	            width:250,
	            height:200
	        });      
	        var file = this.files[0];
	        var reader = new FileReader();
	        // Set preview image into the popover data-content
	        reader.onload = function (e) {
	            $(".image-preview-input-title").text("Change");
	            $(".image-preview-clear").show();
	            $(".image-preview-filename").val(file.name);            
	            img.attr('src', e.target.result);
	            $(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
	        }        
	        reader.readAsDataURL(file);
	    });  
	});
</script>
 
 
 <%@include file="/todo_web_application/common/footer.jspf" %>
 