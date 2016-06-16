define(["require","iScroll","hammer","angular","../common/js/baseUrl/baseUrl"],function(e){var t=e("iScroll"),n=e("hammer"),r=e("angular"),i=e("../common/js/baseUrl/baseUrl");return function(e){e.directive("tap",function(){return function(e,t,r,i){var s=new n(t[0]);s.on("tap",function(){e.$apply(r.tap),t.attr("href")&&t.attr("href")!="javascript:;"&&(location.href=r.href)})}}),e.directive("formValidated",function(){return{restrict:"A",require:"^?form",link:function(e,t,n,r){var i=t[0];t.on("submit",function(){var t=!1;for(var s=0;s<i.length;s++){var o=i[s].name;o&&r[o]&&r[o].$invalid&&(t=!0,r[o].$pristine=!1)}return t?e.$apply():e.$apply(n.formValidated),!1})}}}),e.directive("doValidate",function(){return{restrict:"A",require:"^?form",link:function(e,t,r,i){var s=new n(t[0]);s.on("tap",function(){var n=!1;e.isDisabled=!1;var s=r.doValidate;s&&i[s]&&(i[s].$invalid?(n=!0,i[s].$pristine=!1,t.attr("is-disabled","true")):t.attr("is-disabled","false"));if(n)return e.$apply(),!1})}}}),e.directive("bankCard",["validatePattern",function(e){return{restrict:"A",scope:!0,require:"ngModel",link:function(t,n,r,i){i.$parsers.unshift(function(t){t=t.replace(/\D/g,"");var r="";for(var s=0;s<t.length;s++)s>3&&s%4==0&&(r+=" "),r+=t.charAt(s);return n.val(r),e.bankCard(t)?(i.$setValidity("bankCard",!0),t):(i.$setValidity("bankCard",!1),undefined)})}}}]),e.directive("idcard",["validatePattern",function(e){return{restrict:"A",scope:!0,require:"ngModel",link:function(t,n,r,i){i.$parsers.unshift(function(t){t=t.replace(/\D/g,"");var r="";for(var s=0;s<t.length;s++)s>3&&(s+2)%4==0&&(r+=" "),r+=t.charAt(s);return n.val(r),e.idcard(t)?(i.$setValidity("idcard",!0),t):(i.$setValidity("idcard",!1),undefined)})}}}]),e.directive("phone",function(){return{restrict:"A",scope:!0,require:"ngModel",link:function(e,t,n,r){r.$parsers.unshift(function(e){e=e.replace(/\D/g,"");var n="";for(var i=0;i<e.length;i++)i>0&&(i+1)%4==0&&(n+=" "),n+=e.charAt(i);return t.val(n),/^1[345678]\d{9}$/.test(e)?(r.$setValidity("phone",!0),e):(r.$setValidity("phone",!1),undefined)})}}}),e.directive("checkVerifyCode",["cache",function(e){var t=60;return e.set("timer",t),{restrict:"A",scope:!0,require:"^?form",link:function(r,i,s,o){function u(){i.html(f+"秒");var n=setInterval(function(){f--;if(f<0){f=t,e.set("timer",f),i.html(a),clearInterval(n),n=null;return}i.html(f+"秒"),e.set("timer",f)},1e3)}var a=i.html(),f=e.get("timer");f!==t&&u();var l=new n(i[0]);l.on("tap",function(){if(f!==t)return;var e=s.doValidate;e&&o[e]?o[e].$invalid||(r.$apply(s.checkVerifyCode),u()):(r.$apply(s.checkVerifyCode),u())})}}}]),e.directive("repeatKey",function(){return{require:"ngModel",link:function(e,t,n,r){var i=t.inheritedData("$formController")[n.repeatKey];r.$parsers.push(function(e){if(e===i.$viewValue)return r.$setValidity("repeatKey",!0),e;r.$setValidity("repeatKey",!1)}),i.$parsers.push(function(e){return r.$setValidity("repeatKey",e===r.$viewValue),e})}}}),e.directive("iscroll",["$parse",function(e){return{link:function(n,i,s){function o(){var t=e(s.pagination)(n);typeof t=="function"&&t.call(n,f)}var u=new t(i[0],{probeType:2,scrollbars:!0,fadeScrollbars:!0,shrinkScrollbars:"clip"});i[0].children[0].style.minHeight=i[0].offsetHeight+1+"px";var a=!0,f=1,l=0,c=null;if(s.pagination){var h=r.element('<div class="update-prompt update-prompt-up"></div>'),p=r.element('<div class="update-prompt update-prompt-down"></div>');n.$watch(s.currentPage,function(t){if(t===undefined){h.addClass("hidden"),p.addClass("hidden");return}h.removeClass("hidden"),p.removeClass("hidden"),h.removeClass("update-loading"),p.removeClass("update-loading"),f=t,l=e(s.pages)(n),l>1&&a&&(a=!1,i.children().eq(0).addClass("pagination-content"),i.append(h),i.append(p))}),n.$watch(s.pages,function(e){l=e||0}),u.on("scroll",function(){f===1?h.addClass("hidden"):h.removeClass("hidden"),f===l?p.addClass("hidden"):p.removeClass("hidden"),this.y>40?(c="down",h.text("正在刷新"),h.addClass("update-loading")):this.y-this.maxScrollY<-40?(c="up",p.text("正在加载"),p.addClass("update-loading")):c=null}),u.on("scrollEnd",function(){if(c){if(c=="up"){if(f==l)return;p.text(""),h.text(""),p.removeClass("update-loading"),f++}if(c=="down"){if(f==1)return;h.text(""),p.text(""),h.removeClass("update-loading"),f--}o()}c=null})}s.iscroll.split(";").forEach(function(e){n.$watch(e,function(){setTimeout(function(){u.refresh(),u.scrollTo(0,0)},200)})})}}}]),e.directive("modal",function(){return{restrict:"A",link:function(e,t,n){var r=!1;t.on("webkitTransitionEnd transitionend",function(){!r&&t.css("display","none")}),e.$watch(n.modal,function(e){r=e,e?(t.css("display","block"),setTimeout(function(){t.addClass("modal-show")},0)):t.removeClass("modal-show")})}}}),e.directive("testCode",["$http","$state","$parse","baseUrl",function(e,t,r,i){var s=!0;return{restrict:"A",link:function(e,i,o){s=!0;var u=!1,a=new n(i[0]);a.on("tap",function(){var n=r(o.verifyCodeState)(e);if(n)t.go(o.targetPage,r(o.stateParams)(e));else{e.$apply(o.testCode);if(u)return;u=!0;var i=e.$watch(o.verifyCodeState,function(n){n&&s&&(i(),s=!1,t.go(o.targetPage,r(o.stateParams)(e)))})}return!1})}}}]),e.directive("cancelBubble",function(){return{restrict:"A",link:function(e,t,r){var i=new n(t[0]);i.on("tap",function(e){e.srcEvent.stopPropagation(),e.cancelBubble=!0})}}}),e.directive("backPage",["$state","$stateParams",function(e,t){return{restrict:"A",link:function(r,i,s){var o=new n(i[0]);o.on("tap",function(){history.length>1?history.back():e.go(s.backPage,t)})}}}])}});