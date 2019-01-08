/**
 * 菜单信息
 */
common.pageName = "approval";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#approval',
	data : {
		formInline : {
			projectId : "",
			projectName : "",
			projectOwner : "",
			status : ""
		},
		statusItems : [],
	},
	created : function() {
		this.initDate();
	},
	methods : {
		initDate : function() {
			var _self = this;
			this.$http.get("/combo/progress").then(function(response) {
				console.log(response.data);
				_self.statusItems = response.data;
			}, function(error) {
				console.error(error);
			})
		}
	}
});