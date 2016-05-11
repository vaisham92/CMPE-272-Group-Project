brahmos.controller('buildController', function($scope, $routeParams, $http) {

	$scope.projectId = $routeParams.projectId;

	$scope.formData = {};
	$scope.formData.projectId = $scope.projectId
	var response = $http.get('/BrahMos/portal/projects/'
			+ $routeParams.projectId + "/builds");
	response.success(function(data) {
		$scope.Builds = data;
	});

	var projectresponse = $http.get('/BrahMos/portal/projects/' + $scope.projectId);
	projectresponse.success(function(data) {
		console.log(data);
		$scope.currentProject = data;
	});

	$scope.projectName = projectName;
	$scope.selectedFile = [];
	$scope.onFileSelect = function($files) {
		$scope.selectedFile = $files[0];
	};

	$scope.formData.comments = "Default comment: new build";
	$scope.addNewBuild = function() {
		var reqData = new FormData();
		reqData.append("file", $scope.selectedFile);
		reqData.append("id", $scope.formData.id);
		reqData.append("description", $scope.formData.description);
		reqData.append("comments", $scope.formData.comments);
		$http(
				{
					method : 'POST',
					url : '/BrahMos/portal/projects/' + $routeParams.projectId
							+ '/builds/new',
					data : reqData,
					headers : {
						'Content-Type' : undefined
					},
					transformRequest : angular.identity
				}).success(
				function(data) {
					$('#myModal').modal('hide');
					var response = $http.get('/BrahMos/portal/projects/'
							+ $routeParams.projectId + "/builds");
					response.success(function(data) {
						$scope.Builds = data;
					});
				});
	};

	var intervalID;
	$scope.triggerBuild = function() {
		$http.get('http://52.37.97.94/start?count=' + $scope.currentProject.loadNo);
		$("#runningModal").modal();
		var index = 0;
		$scope.status = "Running .";
		intervalID = setInterval(function() {
			if(index == 0) {
				$scope.status = "Running .";
				console.log($scope.status);
				index++;
				index %= 3;
			}
			else if(index == 1) {
				$scope.status = "Running ..";
				console.log($scope.status);
				index++;
				index %= 3;
			}
			else if(index == 2) {
				$scope.status = "Running ...";
				console.log($scope.status);
				index++;
				index %= 3;
			}
		}, 20000);
	};
});