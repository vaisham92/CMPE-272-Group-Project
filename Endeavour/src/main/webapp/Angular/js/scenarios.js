brahmos.controller('scenariosController',function($scope, $http, $routeParams) {
	$scope.projectId = $routeParams.projectId;
	$scope.formData = {};
	var response = $http.get('/BrahMos/portal/projects/'
						+ $routeParams.projectId +
						"/scenarios");
	response.success(function(data) {
		$scope.Scenarios = data;
		projectId = $routeParams.projectId;
		var projectNameResponse = $http.get('/BrahMos/portal/projects/'
										+ $routeParams.projectId);
		projectNameResponse.success(function(project) {
			projectName = project.name;
			});
		});

	$scope.formData.comments = "Default comment: new scenario";
	$scope.createNewScenario = function() {
		$http({
				method : 'POST',
				url : '/BrahMos/portal/projects/' + $routeParams.projectId + '/scenarios/new',
				data : $.param($scope.formData),
				headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
				}
			})
			.success(function(data) {
				console.log(data);
				$('#myModal').modal('hide');
				var response = $http.get('/BrahMos/portal/projects/'+ $routeParams.projectId+ "/scenarios");
				response.success(function(data) {
					$scope.Scenarios = data;
					projectId = $routeParams.projectId;
					var projectNameResponse = $http.get('/BrahMos/portal/projects/'+ $routeParams.projectId);
					projectNameResponse.success(function(project) {
									projectName = project.name;
								});
				});
			if (!data.success) {
				$scope.errorName = data.name;
			} 
			else {
				$scope.message = data.message;
			}
		});
	};

	$scope.deleteScenario = function(scenarioId) {
		console.log($routeParams.projectId);
		$http({
			method : 'DELETE',
			url : '/BrahMos/portal/projects/' + $routeParams.projectId + '/scenarios/' + scenarioId,
		}).success(function(data) {
			console.log(data);
			$('#myModal').modal('hide');
			var response = $http.get('/BrahMos/portal/projects/' + $routeParams.projectId + '/scenarios');
			response.success(function(data) {
				$scope.Scenarios = data;
			});
			if (!data.success) {
				$scope.errorName = data.name;
			} else {
				$scope.message = data.message;
			}
		});
	};
});