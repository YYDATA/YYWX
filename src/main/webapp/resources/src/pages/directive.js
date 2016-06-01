define(function(require) {
    var IScroll = require('iScroll');
    var Hammer = require('hammer');
    var angular = require('angular');
    var baseUrl = require('../common/js/baseUrl/baseUrl');
    return function(app) {
        app.directive('tap', function() {
            return function($scope, element, attrs, ctrl) {
                var hm = new Hammer(element[0]);
                hm.on('tap', function() {
                    $scope.$apply(attrs.tap);
                    if (element.attr('href') && element.attr('href') != 'javascript:;') {
                        location.href = attrs.href;
                    }
                })
            }
        });
        app.directive('formValidated', function() {
            return {
                restrict: 'A',
                require: '^?form',
                link: function($scope, element, attrs, formCtrl) {
                    var ele = element[0];
                    element.on('submit', function() {
                        var isApply = false;
                        for (var i = 0; i < ele.length; i++) {
                            var name = ele[i].name;
                            if (name && formCtrl[name]) {
                                if (formCtrl[name].$invalid) {
                                    isApply = true;
                                    formCtrl[name].$pristine = false;
                                }
                            }
                        }
                        if (isApply) {
                            $scope.$apply();
                        } else {
                            $scope.$apply(attrs.formValidated);
                        }
                        return false;
                    })
                }
            }
        });
        app.directive('doValidate', function() {
            return {
                restrict: 'A',
                require: '^?form',
                link: function($scope, element, attrs, formCtrl) {
                    var hm = new Hammer(element[0]);
                    hm.on('tap', function() {
                        var isApply = false;
                        var name = attrs.doValidate;
                        if (name && formCtrl[name]) {
                            if (formCtrl[name].$invalid) {
                                isApply = true;
                                formCtrl[name].$pristine = false;
                                element.attr('is-disabled', 'true');
                            } else {
                                element.attr('is-disabled', 'false');
                            }
                        }
                        if (isApply) {
                            $scope.$apply();
                            return false;
                        }
                    })
                }
            }
        })
        app.directive("repeatKey", function() {
            return {
                require: "ngModel",
                link: function(scope, elem, attrs, ctrl) {
                    var otherInput = elem.inheritedData("$formController")[attrs.repeatKey];

                    ctrl.$parsers.push(function(value) {
                        if (value === otherInput.$viewValue) {
                            ctrl.$setValidity("repeatKey", true);
                            return value;
                        }
                        ctrl.$setValidity("repeatKey", false);
                    });

                    otherInput.$parsers.push(function(value) {
                        ctrl.$setValidity("repeatKey", value === ctrl.$viewValue);
                        return value;
                    });
                }
            };
        })

        app.directive('iscroll', ['$parse',
            function($parse) {
                return {
                    link: function($scope, element, attrs) {
                        var iscroll = new IScroll(element[0], {
                            probeType: 2,
                            scrollbars: true,
                            fadeScrollbars: true,
                            shrinkScrollbars: 'clip'
                        });
                        element[0].children[0].style.minHeight = element[0].offsetHeight + 1 + 'px';
                        var isPagination = true;

                        var currentPage = 1;
                        var pages = 0;
                        var scrollDirection = null;

                        function updateFn() {
                            var getDataFn = $parse(attrs.pagination)($scope);
                            if (typeof getDataFn === 'function') {
                                getDataFn.call($scope, currentPage);
                            }
                        };
                        if (attrs.pagination) {
                            var eleUp = angular.element('<div class="update-prompt update-prompt-up"></div>');
                            var eleDown = angular.element('<div class="update-prompt update-prompt-down"></div>');
                            $scope.$watch(attrs.currentPage, function(newCurrentPage) {
                                if (newCurrentPage === undefined) {
                                    eleUp.addClass('hidden');
                                    eleDown.addClass('hidden');
                                    return;
                                } else {
                                    eleUp.removeClass('hidden');
                                    eleDown.removeClass('hidden');
                                    eleUp.removeClass('update-loading');
                                    eleDown.removeClass('update-loading');
                                }
                                currentPage = newCurrentPage;
                                pages = $parse(attrs.pages)($scope);
                                if (pages > 1 && isPagination) {
                                    isPagination = false;
                                    element.children().eq(0).addClass('pagination-content');
                                    element.append(eleUp);
                                    element.append(eleDown);
                                }
                            })

                            $scope.$watch(attrs.pages, function(n) {
                                pages = n || 0;
                            })
                            iscroll.on('scroll', function() {
                                if (currentPage === 1) {
                                    eleUp.addClass('hidden');
                                } else {
                                    eleUp.removeClass('hidden');
                                }

                                if (currentPage === pages) {
                                    eleDown.addClass('hidden');
                                } else {
                                    eleDown.removeClass('hidden');
                                }
                                if (this.y > 40) {
                                    scrollDirection = 'down';
                                    eleUp.text('正在刷新');
                                    eleUp.addClass('update-loading');
                                } else if (this.y - this.maxScrollY < -40) {
                                    scrollDirection = 'up';
                                    eleDown.text('正在加载');
                                    eleDown.addClass('update-loading');
                                } else {
                                    scrollDirection = null;
                                }
                            });

                            iscroll.on('scrollEnd', function() {
                                if (scrollDirection) {
                                    if (scrollDirection == 'up') {
                                        if (currentPage == pages) return;
                                        eleDown.text('');
                                        eleUp.text('');
                                        eleDown.removeClass('update-loading');
                                        currentPage++;
                                    }
                                    if (scrollDirection == 'down') {
                                        if (currentPage == 1) return;
                                        eleUp.text('');
                                        eleDown.text('');
                                        eleUp.removeClass('update-loading');
                                        currentPage--;
                                    }
                                    updateFn();
                                }
                                scrollDirection = null;
                            });
                        }
                        attrs.iscroll.split(';').forEach(function(item) {
                            $scope.$watch(item, function() {
                                setTimeout(function() {
                                    iscroll.refresh();
                                    iscroll.scrollTo(0, 0);
                                }, 200)
                            });
                        })
                    }
                }
            }
        ]);
        app.directive('modal', function() {
            return {
                restrict: 'A',
                link: function($scope, element, attrs) {
                    var isHide = false;
                    element.on('webkitTransitionEnd transitionend', function() {
                        !isHide && element.css('display', 'none');
                    })
                    $scope.$watch(attrs.modal, function(val) {
                        isHide = val;
                        if (val) {
                            element.css('display', 'block');
                            setTimeout(function() {
                                element.addClass('modal-show')
                            }, 0)
                        } else {
                            element.removeClass('modal-show');
                        }
                    })
                }
            }
        });
        app.directive('testCode', ['$http', '$state', '$parse', 'baseUrl',
            function($http, $state, $parser, baseUrl) {
                var isGoPage = true;
                return {
                    restrict: 'A',
                    link: function($scope, element, attrs) {
                        isGoPage = true;
                        var isWatch = false;
                        var hm = new Hammer(element[0]);
                        hm.on('tap', function() {
                            var isChecked = $parser(attrs.verifyCodeState)($scope);
                            if (isChecked) {
                                $state.go(attrs.targetPage, $parser(attrs.stateParams)($scope));
                            } else {
                                $scope.$apply(attrs.testCode);
                                if (isWatch) return;
                                isWatch = true;
                                var watch = $scope.$watch(attrs.verifyCodeState, function(val) {
                                    if (val && isGoPage) {
                                        watch();
                                        isGoPage = false;
                                        $state.go(attrs.targetPage, $parser(attrs.stateParams)($scope));
                                    }
                                })
                            }
                            return false;
                        })
                    }
                }
            }
        ])
        app.directive('cancelBubble', function() {
            return {
                restrict: 'A',
                link: function($scope, element, attrs) {
                    var hm = new Hammer(element[0]);
                    hm.on('tap', function(ev) {
                        ev.srcEvent.stopPropagation();
                        ev.cancelBubble = true;
                    })
                }
            }
        });
        app.directive('backPage', ['$state', '$stateParams',
            function($state, $stateParams) {
                return {
                    restrict: 'A',
                    link: function($scope, element, attrs) {
                        var hm = new Hammer(element[0]);
                        hm.on('tap', function() {
                            if (history.length > 1) {
                                history.back();
                            } else {
                                $state.go(attrs.backPage, $stateParams);
                            }
                        });
                    }
                }
            }
        ]);

    }
})
