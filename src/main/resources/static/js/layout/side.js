var side = new Vue({
	el : '#side',
	data : {
		activeName : "",
		openName : [],
		user : {},
		menus:[]
	},
	created : function() {
		this.activeName = common.pageName;
		this.openName = common.openName;
		this.getUser();
	},
	methods : {
		getUser : function() {
			this.$http.get("/login/user?"+new Date()).then(function(response) {
				this.user = response.data;
				this.menus = this.user.permissionList;
				console.log(this.menus);
			}, function(error) {

			})
		}

	}
});