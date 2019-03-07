/**
 * 菜单信息
 */
common.pageName = "meeting";
common.openName = [ 'meeting' ];

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
		addMeeting : {
			projects:[]
		},
		modal1 : false,
		statusItems : [],
		users:[],
		meetingKind:[],
		project:{},
		meetingReminds:[
			{value:15,text:"会议开始前15分钟"},
			{value:30,text:"会议开始前30分钟"},
			{value:45,text:"会议开始前45分钟"},
			{value:60,text:"会议开始前60分钟"}
		],
		ruleValidate: {
			theme: [
                { required: true, message: '会议主题不能为空', trigger: 'blur' }
            ],
            kind: [
                { required: true, message: '会议类型不能为空', trigger: 'change' }
            ],
            place: [
                { required: true, message: '会议地点不能为空', trigger: 'change' }
            ],
            content: [
            	{ required: true, message: '会议内容不能为空', trigger: 'blur' }
            ],
            participantNo: [
                { required: true, type: 'array', min: 1, message: '请选择参会人员', trigger: 'change' }
            ]
            
        },
	},
	created : function() {
		if(document.getElementById("projectId").value){
			this.project.id = document.getElementById("projectId").value;
		}
		this.initDate();
		this.findProject();
		this.query(1);
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
		},
		kindFilter:function(value){
			for(var index in vue.meetingKind){
				if(value == vue.meetingKind[index].value){
					return vue.meetingKind[index].text;
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
			this.$http.get("/combo/meetingKind").then(function(response) {
				_self.meetingKind = response.data;
			}, function(error) {
				console.error(error);
			})
		},
		
		/**
		 * 查询项目
		 */
		findProject:function(){
			let _self = this;
			if(this.project.id){
				this.$http.get("/approval/"+this.project.id).then(function(response){
					_self.project = response.data;
					if(_self.project){
						_self.addMeeting.theme = _self.project.projectName+"立项会";
						_self.addMeeting.content = _self.project.projectName + "立项讨论会";
						_self.addMeeting.kind = "APPROVAL";
						_self.modal1 = true;
					}
				},function(error){
					console.error(error);
				})
			}
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
			let _self = this;
			this.$refs['entityDataForm'].validate((valid) => {
				if(!valid){
					this.$Message.error('校验失败,请完善登录信息!');
					return false;
				}else{
					if(_self.project.id){
						_self.addMeeting.projects.push(_self.project);
					}
					_self.addMeeting.beginTime = _self.addMeeting.meetingTime[0];
					_self.addMeeting.endTime = _self.addMeeting.meetingTime[1];
					if(_self.addMeeting.id==null||_self.addMeeting.id==''){
						_self.$http.post("/meeting",_self.addMeeting).then(function(response){
							if (response.data.success) {
								_self.$Message.info({
									content : "创建会议成功",
									onClose : function() {
										_self.query();
										_self.cancel();
									}
								});
							} else {
								_self.$Message.error(response.data.errorMessage);
							}
						},function(error){
							_self.$Message.error(error.data.message);
						})	
					}else{
						_self.$http.put("/meeting",_self.addMeeting).then(function(response){
							if (response.data.success) {
								_self.$Message.info({
									content : "更新会议成功",
									onClose : function() {
										_self.query();
										_self.cancel();
									}
								});
							} else {
								_self.$Message.error(response.data.errorMessage);
							}
						},function(error){
							_self.$Message.error(error.data.message);
						})	
					}
				}
			});
			
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
		},
		
		createMninutes:function(meeting){
			window.open("minutes?id="+meeting.id,"_blank");
		}
	}
});