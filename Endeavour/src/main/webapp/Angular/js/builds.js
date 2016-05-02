brahmos.controller('buildController', function($scope, $routeParams, $http) {
	$scope.projectId = $routeParams.projectId;
	$scope.scenarioId = $routeParams.scenarioId;
	$scope.formData = {};
	$scope.formData.scenarioId = $routeParams.scenarioId;
	var response = $http.get('/BrahMos/portal/projects/'
			+ $routeParams.projectId + "/scenarios/" + $routeParams.scenarioId
			+ "/builds");
	response.success(function(data) {
		$scope.Builds = data;
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
							+ '/scenarios/' + $routeParams.scenarioId
							+ '/builds/new',
					data : reqData,
					headers : {
						'Content-Type' : undefined
					},
					transformRequest : angular.identity
				}).success(
				function(data) {
					// console.log(data);
					// alert(data);
					// console.log(data.buildInfo);
					// console.log(pack(data.buildInfo));
					$('#myModal').modal('hide');
					var response = $http.get('/BrahMos/portal/projects/'
							+ $routeParams.projectId + "/scenarios/"
							+ $routeParams.scenarioId + "/builds");
					response.success(function(data) {
						$scope.Builds = data;
					});
				});
	};

	$scope.crData = {};
	$scope.publishResults = function() {
		$scope.crData.projectId = $routeParams.projectId;
		$scope.crData.scenarioId = $routeParams.scenarioId;
		$http({
			method : 'POST',
			url : '/BrahMos/portal/consolidatedReport',
			data : $.param($scope.crData),
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).success(function(data) {
			console.log(data);
		});
	};
});