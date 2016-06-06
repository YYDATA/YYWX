define(function(require) {
    var baseUrl = require('../../common/js/baseUrl/baseUrl');
    return function(app) {
        app.controller('homeControl', ['$scope', '$http', '$state',
            function($scope, $http, $state) {
                $scope.params = {
                    isAccept: true
                };
                $scope.submit = function() {
                    $http({
                        url: baseUrl + 'customer/saveCustomerLoan',
                        method: 'post',
                        data: $scope.params
                    }).success(function(data) {
                        if (data.success) {
                            $state.go('register');
                        } else {
                            alert(data.msg);
                        }
                    })
                }
                $scope.getVerifyCode = function() {
                    $http({
                        url: baseUrl + 'sms/sendVerificationCode',
                        method: 'post',
                        data: {
                            phone: $scope.params.phone
                        }
                    }).success(function(data) {
                        alert(data.msg);
                    })
                }
            }
        ])
    }
})
