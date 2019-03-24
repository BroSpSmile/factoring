/**
 * 菜单信息
 */
common.pageName = "past";
common.openName = [ 'past' ];

var vue = new Vue({
	el:"#past",
	data:{
		projectMeeting:{
			projectModel:"RECOURSE_RIGHT"
		},
		modelFlag:"",
		showMeeting:false,
		project:{},
		pms:[],
		projects:[],
		changeFlag:false,
		meetings:[],
		ruleValidate: {
			meetingIds: [
                { required: true, type: 'array', min: 1, message: '请选择三重一大会议', trigger: 'change' }
            ]
        }
	},
	created:function(){
		this.initData();
	},
	methods:{
		/**
		 * 初始化数据
		 */
		initData:function(){
			let _self = this;
			this.$http.get("/past/project").then(function(response){
				_self.projects = response.data;
				if(document.getElementById("projectId").value){
					_self.projectMeeting.projectId  = parseInt(document.getElementById("projectId").value);
					_self.changeFlag = true;
				}
			},function(error){
				console.error(error);
			});
			this.$http.get("/past/meeting").then(function(response){
				_self.meetings = response.data;
			},function(error){
				console.error(error);
			});
			if(document.getElementById("projectId").value){
				this.getProject(document.getElementById("projectId").value);
				this.getMeetings(document.getElementById("projectId").value);
			}
		},
		
		getProject:function(projectId){
			let _self = this;
			this.$http.get("/project/"+projectId).then(function(response){
				_self.project = response.data;
			},function(error){
				console.error(error);
			})
		},
		
		getMeetings:function(projectId){
			let _self = this;
			this.$http.get("/past/project/"+projectId+"/meetings").then(function(response){
				_self.pms = response.data;
				_self.showMeeting = _self.pms.length>0;
			},function(error){
				console.error(error);
			})
		},
		
		/**
		 * 提交三重一大
		 */
		past:function(){
			let self = this;
			this.$refs['entityDataForm'].validate((valid) => {
				if(!valid){
					this.$Message.error('校验失败,请完善信息!');
					return false;
				}else{
					self.$http.post("/past", self.projectMeeting).then(function(response) {
						if (response.data.success) {
							self.$Message.info({
								content : "保存成功",
								onClose : function() {
									self.initData();
								}
							});
						} else {
							self.$Message.error(response.data.errorMessage);
						}
					}, function(error) {
						self.$Message.error(error.data.message);
					});
				}
			});
		},
		
		skip:function(){
			let _self = this;
			this.$http.post("/past/"+_self.projectMeeting.projectId).then(function(response){
				if (response.data.success) {
					_self.$Message.info({
						content : "保存成功",
						onClose : function() {
							window.close();
						}
					});
				} else {
					_self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				_self.$Message.error(error.data.message);
			})
		},
		
		/**
		 * 选择项目
		 */
		changeProject:function(projectId){
			for(let index in this.projects){
				if(this.projects[index].id==projectId){
					this.projectMeeting.projectModel = this.projects[index].model;
					this.modelFlag = this.projects[index].model;
					this.getMeetings(projectId);
				}
			}
		}
	}
});