define(function(require) {
    return function(app) {
		app.service('cache',function(){
			var obj={};
			this.set=function(key,value){
				obj[key]=value;
			};
			this.get=function(key){
				return obj[key];
			}
		})
    }
})
