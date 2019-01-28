/**
 * 菜单信息
 */
common.pageName = "meeting";
common.openName = [ '6' ];

var vue = new Vue({
	el : '#meeting',
	data : {
		formInline : {
			type : []
		},
		queryParam : {
			condition : {},
			pageNum : 1,
			pageSize : 10
		},
		pageInfo : {},
		addMeeting : {},
		modal1 : false,
		statusItems : [],
		users:[]
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
			this.$http.get("/combo/users").then(function(response) {
				_self.users = response.data;
			}, function(error) {
				console.error(error);
			})
		},

		/**
		 * 查询
		 */
		query : function(page) {
			this.queryParam.pageNum = page;
			let self = this;
			self.queryParam.condition = self.formInline;
			this.$http.post("/meeting/query", self.queryParam).then(
					function(response) {
						self.pageInfo = response.data;
					}, function(error) {
						self.$Message.error(error.data.message);
					})
		},

		/**
		 * 新增会议
		 */
		add : function() {
			this.addMeeting = {}
			this.modal1 = true;
		},

		/**
		 * 
		 */
		save : function() {
			let self = this;
			this.$http.post("/meeting",this.addMeeting).then(function(response){
				if (response.data.success) {
					self.$Message.info({
						content : "更新成功",
						onClose : function() {
							self.query();
							self.cancel();
						}
					});
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				self.$Message.error(error.data.message);
			})
		},

		/**
		 * 取消保存
		 */
		cancel : function() {
			this.modal1 = false;
			this.$refs['entityDataForm'].resetFields();
		}
	}
});