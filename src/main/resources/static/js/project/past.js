/**
 * 菜单信息
 */
common.pageName = "past";
common.openName = [ '2' ];

var vue = new Vue({
	el:"#past",
	data:{
		projectMeeting:{
			projectModel:"RECOURSE_RIGHT"
		},
		modelFlag:"",
		projects:[],
		meetings:[]
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
			},function(error){
				console.error(error);
			});
			this.$http.get("/past/meeting").then(function(response){
				_self.meetings = response.data;
			},function(error){
				console.error(error);
			})
		},
		
		/**
		 * 提交三重一大
		 */
		past:function(){
			console.log(this.projectMeeting);
			this.$http.post("/past", this.projectMeeting).then(function(response) {
				if (response.data.success) {
					self.$Message.info({
						content : "保存成功",
						onClose : function() {
							
						}
					});
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			}, function(error) {
				self.$Message.error(error.data.message);
			});
		},
		
		/**
		 * 选择项目
		 */
		changeProject:function(projectId){
			for(let index in this.projects){
				if(this.projects[index].id==projectId){
					this.projectMeeting.projectModel = this.projects[index].model;
					this.modelFlag = this.projects[index].model;
				}
			}
		}
	}
});