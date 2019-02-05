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
	filters:{
		timeFilter:function(value){
			return moment(value).format('YYYY-MM-DD HH:mm');
		},
		statusFilter:function(value){
			for(var index in vue.statusItems){
				if(value == vue.statusItems[index].value){
					return vue.statusItems[index].text;
				}
			}
			return "";
		}
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
		
		edit:function(meeting){
			this.addMeeting = JSON.parse(JSON.stringify(meeting));
			this.addMeeting.participantNo=[];
			for(let index in this.addMeeting.participant){
				this.addMeeting.participantNo.push(''+this.addMeeting.participant[index].id);
			}
			console.log(this.addMeeting);
			this.modal1 = true;
		},

		/**
		 * 
		 */
		save : function() {
			let self = this;
			if(this.addMeeting.id==null||this.addMeeting.id==''){
				this.$http.post("/meeting",this.addMeeting).then(function(response){
					if (response.data.success) {
						self.$Message.info({
							content : "创建会议成功",
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
			}else{
				this.$http.put("/meeting",this.addMeeting).then(function(response){
					if (response.data.success) {
						self.$Message.info({
							content : "更新会议成功",
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
			}
			
		},
		
		cancelMeeting:function(meeting){
			let self = this;
			meeting.status='CANCELLED';
			this.$http.put("/meeting",meeting).then(function(response){
				if (response.data.success) {
					self.$Message.info({
						content : "取消会议成功",
						onClose : function() {
							//self.query();
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