brahmos.controller('perfSheetController', function($scope, $http) {
	$scope.ConsolidatedReports = [];
	var monthNames = ["January", "February", "March", "April", "May", "June",
	                  "July", "August", "September", "October", "November", "December"
	                ];
	var date = new Date();
	$http({
		url: '/BrahMos/portal/consolidatedReport',
		method: "GET",
		params: {year: date.getFullYear(), month: monthNames[date.getMonth()]}
	}).success(function(data) {
		$scope.ConsolidatedReports = data;
		$scope.monthAndYear = monthNames[date.getMonth()] + " - " + date.getFullYear();
	});

	$scope.getReportForCurrentYear = function() {
		$http({
			url: '/BrahMos/portal/consolidatedReport',
			method: "GET",
			params: {year: date.getFullYear(), month: monthNames[date.getMonth()]}
		}).success(function(data) {
			$scope.ConsolidatedReports = data;
			$scope.monthAndYear = monthNames[date.getMonth()] + " - " + date.getFullYear();
		});
	};

	$scope.getReportFor = function(year, month) {
		$http({
			url: '/BrahMos/portal/consolidatedReport',
			method: "GET",
			params: {year:year, month:month}
		}).success(function(data) {
			$scope.ConsolidatedReports = data;
			$scope.monthAndYear = month + " - " + year;
		});
	};

	var ReportResponse = $http.get('/BrahMos/portal/consolidatedReport/meta');
	ReportResponse.success(function(data) {
		$scope.ConsolidatedReportsMeta = data;
	});
	
});