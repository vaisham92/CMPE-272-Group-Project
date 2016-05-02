brahmos.controller('graphController',function($scope, $http, $routeParams) {
	$scope.projectId = $routeParams.projectId;
	$scope.scenarioId = $routeParams.scenarioId;
	
	if($scope.incrementSize != parseInt($scope.incrementSize, 10))
		$scope.incrementSize = 500;

	$scope.progressPercentage = 20;
	document.getElementById('widthProgressBar').setAttribute("style","width:" + $scope.progressPercentage +"%");
	$scope.WidthValue = document.getElementById("lineGraph").offsetWidth;
	
	$scope.increaseWidth = function() {
		if($scope.progressPercentage <= 90) {
			$scope.progressPercentage = $scope.progressPercentage + 10;
			$scope.WidthValue = $scope.WidthValue + parseInt($scope.incrementSize);
			document.getElementById('widthProgressBar').setAttribute("style","width:" + $scope.progressPercentage +"%");
			document.getElementById('lineGraph').setAttribute("style","width:" + $scope.WidthValue +"px");
		}
		setColorForProgressBar();
	};

	function setColorForProgressBar() {
		if($scope.progressPercentage < 70 && $scope.progressPercentage >= 40)
			document.getElementById('widthProgressBar').setAttribute("class","progress-bar progress-bar-warning");
		else if ($scope.progressPercentage >= 70)
			document.getElementById('widthProgressBar').setAttribute("class","progress-bar progress-bar-success");
		else
			document.getElementById('widthProgressBar').setAttribute("class","progress-bar progress-bar-danger");
	}

	$scope.decreaseWidth = function() {
		if($scope.progressPercentage > 10) {
			$scope.progressPercentage = $scope.progressPercentage - 10;
			$scope.WidthValue = $scope.WidthValue - parseInt($scope.incrementSize);
			document.getElementById('widthProgressBar').setAttribute("style","width:" + $scope.progressPercentage +"%");
			document.getElementById('lineGraph').setAttribute("style","width:" + $scope.WidthValue +"px");
		}
		setColorForProgressBar();
	};

	var response = $http.get('/BrahMos/portal/projects/'
			+ $routeParams.projectId + "/scenarios/" + $routeParams.scenarioId
			+ "/builds/" + $routeParams.buildNumber);
	response.success(function(data) {
		//console.log(data);
		//alert(JSON.stringify(data.buildInfo));
		//console.log(JSON.stringify(pack(data.buildInfo)));
		$scope.jsonBlob = JSON.parse(pack(data.buildInfo));
		//alert($scope.jsonBlob);
		//alert($scope.jsonBlob.graph_data);
		$scope.items = $scope.jsonBlob.graph_data;
		
		$scope.onPerfApiSelect = function(selectedApi) {
			var errordata = $scope.jsonBlob.error_data[selectedApi];
			var graphdata = $scope.jsonBlob.graph_data[selectedApi];
			
			var chartData = graphdata;
			//console.log(graphdata);
			//console.log(JSON.stringify(chartData[0]));
			//console.log(JSON.stringify(chartData));
			var lineChart = AmCharts.makeChart("lineGraph", {
			    "type": "serial",
			    "theme": "light",
			    "marginRight": 80,
			    "dataProvider": chartData,
			    "valueAxes": [{
			        "position": "left",
			        "title": "Response Time (milli seconds)"
			    }],
			    "graphs": [{
			        "id": "g1",
			        "fillAlphas": 0.4,
			        "valueField": "response",
			         "balloonText": "<div style='margin:5px; font-size:19px;'>Response Time:<b>[[value]]</b></div>"
			    }],
			    "chartScrollbar": {
			        "graph": "g1",
			        "scrollbarHeight": 80,
			        "backgroundAlpha": 0,
			        "selectedBackgroundAlpha": 0.1,
			        "selectedBackgroundColor": "#888888",
			        "graphFillAlpha": 0,
			        "graphLineAlpha": 0.5,
			        "selectedGraphFillAlpha": 0,
			        "selectedGraphLineAlpha": 1,
			        "autoGridCount": true,
			        "color": "#AAAAAA"
			    },
			    "chartCursor": {
			        "categoryBalloonDateFormat": "JJ:NN, DD MMMM",
			        "cursorPosition": "mouse"
			    },
			    "categoryField": "date",
			    "categoryAxis": {
			        "minPeriod": "mm",
			        "parseDates": true
			    },
			    "export": {
			        "enabled": true
			    }
			});

			lineChart.addListener("dataUpdated", zoomChart);
			// when we apply theme, the dataUpdated event is fired even before we add listener, so
			// we need to call zoomChart here
			zoomChart();
			// this method is called when chart is first inited as we listen for "dataUpdated" event
			function zoomChart() {
			    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
				lineChart.zoomToIndexes(chartData.length - 250, chartData.length - 100);
			}

			var pieChartData = [];
			for(error in errordata) {
				pieChartData.push({
					country: error,
					count: errordata[error].count
				});
			}
			var pieChart = AmCharts.makeChart("pieGraph", {
				  "type": "pie",
				  "startDuration": 0,
				   "theme": "light",
				  "addClassNames": true,
				  "legend":{
				   	"position":"right",
				    "marginRight":100,
				    "autoMargins":false
				  },
				  "innerRadius": "30%",
				  "defs": {
				    "filter": [{
				      "id": "shadow",
				      "width": "200%",
				      "height": "200%",
				      "feOffset": {
				        "result": "offOut",
				        "in": "SourceAlpha",
				        "dx": 0,
				        "dy": 0
				      },
				      "feGaussianBlur": {
				        "result": "blurOut",
				        "in": "offOut",
				        "stdDeviation": 5
				      },
				      "feBlend": {
				        "in": "SourceGraphic",
				        "in2": "blurOut",
				        "mode": "normal"
				      }
				    }]
				  },
				  "dataProvider": pieChartData,
				  "valueField": "count",
				  "titleField": "country",
				  "export": {
				    "enabled": true
				  }
				});

			pieChart.addListener("init", handleInit);

			pieChart.addListener("rollOverSlice", function(e) {
				  handleRollOver(e);
				});

				function handleInit(){
					pieChart.legend.addListener("rollOverItem", handleRollOver);
				}

				function handleRollOver(e){
				  var wedge = e.dataItem.wedge.node;
				  wedge.parentNode.appendChild(wedge);  
				}
		};
		
		//console.log($scope.jsonBlob.error_data);
		$scope.projectId = $routeParams.projectId;
		$scope.scenarioId = $routeParams.scenarioId;
		//var chartData = generateChartData();
	});
});