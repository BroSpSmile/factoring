
var vue = new Vue({
	el : '#content',
	data : {
		factoringTotal:{
			total:0,
			investment:0.0,
			profit:0.0
		},
		factorings : []
	},
	created : function() {
		this.getFactorings();
	},
	methods : {
		getFactorings : function() {
			this.$http.get("factoring/infos").then(function(response) {
				this.factorings = response.data;
				for(var index in this.factorings){
					this.factoringTotal.total += this.factorings[index].total;
					this.factoringTotal.investment += this.factorings[index].investment;
					this.factoringTotal.profit += this.factorings[index].profit;
				}
			}, function(error) {

			});
		},
		openFactoring:function(){
			parent.window.menu.createNew({
				name:"保理业务",
				url:"/project",
				id:"/project"
			});
		}
	}
});