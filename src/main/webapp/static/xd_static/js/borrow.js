var mainModule=angular.module("mainModule",[]);
mainModule.controller("borrowController",function($scope,pageList,ajaxFail){
	$scope.borrow={
			userName:"",
			bookName:"",
			operatorName:"",
			status:0,
			pageSize:15,
			currentPage:1
	}
	$scope.page={};
	$scope.statusData=[
			{id:0,name:"=全部="},
			{id:1,name:"申请"},
			{id:2,name:"已借"},
			{id:3,name:"已还"}
		];
	$scope.page.pageSize=$scope.borrow.pageSize;
	function getBorrowList(){
		$.ajax({
			url:"../../borrow.it?action=getBorrowList",
			data:$scope.borrow
		}).done(function(data){
			$scope.page.totalCount=data.totalCount;
			$scope.page.currentPage=data.currentPage;
			$scope.page=pageList.page($scope.page);//调用服务,重置page页码属性
			$scope.borrowList=data.list;
			$scope.$apply();
		}).fail(ajaxFail.fail);
	}
	getBorrowList();
	$scope.serachList=function(){
		getBorrowList();
	}
	
	$scope.clickPageNum=function(currentPage){
		$scope.borrow.currentPage=currentPage;
		getBorrowList();
	}
	$scope.cancleBorrow=function(bookId,userId){
		$.ajax({
			url:"../../borrow.it?action=deleteApplication&bookId="+bookId+"&userId="+userId+"&status=3"
		}).done(function(data){
			alert(data.msg)
			if(data.status=="success"){
				getBorrowList();
			}			
		}).fail(ajaxFail.fail);
	}
	$scope.borrowBook=function(id,bookId){
		$.ajax({
			url:"../../borrow.it?action=updateStatus&id="+id+"&status=2&bookId="+bookId
		}).done(function(data){
			alert(data.msg)
			if(data.status=="success"){
				getBorrowList();
			}			
		}).fail(ajaxFail.fail);
	}
	$scope.backBook=function(id,bookId){
		$.ajax({
			url:"../../borrow.it?action=updateStatus&id="+id+"&status=3&bookId="+bookId
		}).done(function(data){
			alert(data.msg)
			if(data.status=="success"){
				getBorrowList();
			}			
		}).fail(ajaxFail.fail);
	}
	$scope.deleteData=function(id){
		$.ajax({
			url:"../../borrow.it?action=deleteBorrow&id="+id
		}).done(function(data){
			alert(data.msg)
			if(data.status=="success"){
				getBorrowList();
			}			
		}).fail(ajaxFail.fail);
	}
	
});