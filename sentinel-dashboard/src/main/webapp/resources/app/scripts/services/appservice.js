
var app = angular.module('sentinelDashboardApp');

app.service('AppService', ['$http', function ($http) {
  this.getApps = function () {
    return $http({
      // url: 'app/mock_infos',
      url: '/sentinel/app/briefinfos.json',
      method: 'GET'
    });
  };
}]);
