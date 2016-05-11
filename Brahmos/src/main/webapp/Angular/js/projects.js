brahmos.controller('projectsController', function($scope, $http, $routeParams, $window) {
	$scope.formData = {};
	var response = $http.get('/BrahMos/portal/projects');
	response.success(function(data) {
		$scope.Projects = data;
	});

	$scope.editProject = function(id) {
		$http({
			method : 'POST',
			url : '/BrahMos/portal/projects/' + id + '/update',
			data : $.param($scope.formData2),
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data) {
			console.log(data);
			$('#myModal').modal('hide');
			var response = $http.get('/BrahMos/portal/projects');
			response.success(function(data) {
				$scope.Projects = data;
			});
			if (!data.success) {
				$scope.errorName = data.name;
			} else {
				$scope.message = data.message;
			}
		});
	};

	$scope.selectedFile = [];
	$scope.onFileSelect = function($files) {
		$scope.selectedFile = $files[0];
	};

	$scope.formData.comments = "Default comment: new project";
	$scope.createNewProject = function() {
		var reqData = new FormData();
		reqData.append("name", $scope.formData.name);
		reqData.append("description", $scope.formData.description);
		reqData.append("file", $scope.selectedFile);
		reqData.append("location", $scope.formData.location);
		reqData.append("users", $scope.formData.users);
		reqData.append("loadNo", $scope.formData.load);
		reqData.append("iterationsNo", $scope.formData.iterations);
		reqData.append("comments", $scope.formData.comments);
		$http({
			method : 'POST',
			url : '/BrahMos/portal/projects/new',
			data : reqData,
			headers : {
				'Content-Type' : undefined
			}
		}).success(function(data) {
			console.log(data);
			$('#myModal').modal('hide');
			var response = $http.get('/BrahMos/portal/projects');
			response.success(function(data) {
				$scope.Projects = data;
			});
			if (!data.success) {
				$scope.errorName = data.name;
			} else {
				$scope.message = data.message;
			}
		});
	};
	$scope.deleteProject = function(projectId) {
		$http({
			method : 'DELETE',
			url : '/BrahMos/portal/projects/' + projectId,
		}).success(function(data) {
			console.log(data);
			$('#myModal').modal('hide');
			var response = $http.get('/BrahMos/portal/projects');
			response.success(function(data) {
				$scope.Projects = data;
			});
			if (!data.success) {
				$scope.errorName = data.name;
			} else {
				$scope.message = data.message;
			}
		});
	};
});