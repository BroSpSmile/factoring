/**
 * 菜单信息
 */
common.pageName = "meeting";
common.openName = [ '6' ];

var vue = new Vue({
	el : '#meeting',
	data : {
		formInline:{
			type:[]
		},
		queryParam : {
			condition : {},
			pageNum : 1,
			pageSize : 10
		},
		pageInfo:{},
		statusItems : []
	},
	created : function() {
		this.initDate();
	},
	methods : {
		/**
		 * 初始化数据
		 */
		initDate : function() {
			var _self = this;
			this.$http.get("/combo/meetingStatus").then(function(response) {
				_self.statusItems = response.data;
			}, function(error) {
				console.error(error);
			})
		},
		
		/**
		 * 查询
		 */
		query:function(page){
			this.queryParam.pageNum = page;
			let self = this;
			self.queryParam.condition = self.formInline;
			this.$http.post("/meeting/query", self.queryParam).then(
					function(response) {
						self.pageInfo = response.data;
					}, function(error) {
						self.$Message.error(error.data.message);
					})
		}
	}
});