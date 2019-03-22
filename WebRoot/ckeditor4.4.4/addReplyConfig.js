/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	config.toolbar = 'Full';
	 
	config.toolbar_Full =
	[
	    //{ name: 'document', items : [ 'Source','-','NewPage','DocProps','Preview','Print' ] },
	   // { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','-','Undo','Redo' ] },
	    //{ name: 'editing', items : [ 'Find','Replace','-','SelectAll'] },
	   // { name: 'colors', items : [ 'TextColor','BGColor' ] },
	    //{ name: 'links', items : [ 'Link','Unlink'] },
	    { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript'] },
	    { name: 'paragraph', items : ['Blockquote','CreateDiv'] },
	    { name: 'insert', items : ['Smiley'] },
	    { name: 'styles', items : ['FontSize' ] },
	    //{ name: 'tools', items : [ 'Maximize'] }
	];
	//取消右下角的拖拽
	config.resize_enabled=false;
	config.height=130;
	config.enterMode = CKEDITOR.ENTER_BR;
	config.shiftEnterMode = CKEDITOR.ENTER_P;
	
	//开启超链接和文件上传功能
	//开启工具栏“图像”中文件上传功能，后面的url为待会要上传action要指向的的action或servlet  
	//config.filebrowserImageUploadUrl= "${pageContext.request.contextPath}/blog/blogManageAction!toUpload.action";  
	//开启插入\编辑超链接中文件上传功能，后面的url为待会要上传action要指向的的action或servlet                                                                                                         
	//config.filebrowserUploadUrl ='/rdcms/ckeditorFileUpload_fileUpload.action';   
};
