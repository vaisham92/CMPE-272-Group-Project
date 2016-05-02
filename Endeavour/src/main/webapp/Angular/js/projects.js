brahmos.controller('projectsController', function($scope, $http, $routeParams, $window) {
	$scope.formData = {};
	var response = $http.get('/BrahMos/portal/projects');
	response.success(function(data) {
		$scope.Projects = data;
	});

	$scope.formData.comments = "Default comment: new project";
	$scope.createNewProject = function() {
		$http({
			method : 'POST',
			url : '/BrahMos/portal/projects/new',
			data : $.param($scope.formData),
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