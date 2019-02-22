/**
 * 菜单信息
 */
common.pageName = "meeting";
common.openName = [ '6' ];

var vue = new Vue({
	el : '#minutes',
	data : {
		meeting:{
			id:0,
			originator:{
				username:""
			}
		},
		phone:"",
		statusItems : [],
		projects:[]
	},
	created : function() {
		this.initDate();
		this.getMeeting();
	},
	filters:{
		timeFilter:function(value){
			return moment(value).format('YYYY-MM-DD HH:mm');
		}
	},
	computed:{
		statusShow:function(){
			if(this.meeting.status){
				for(var index in this.statusItems){
					if(this.meeting.status == this.statusItems[index].value){
						return this.statusItems[index].text;
					}
				}
				return "";
			}else{
				return "";
			}
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
			this.$http.get("/minutes/project").then(function(response){
				_self.projects = response.data;
			},function(error){
				console.error(error);
			})
		},
		
		getMeeting:function(){
			let _self = this;
			this.$http.get("/minutes/meeting/"+document.getElementById("meetingId").value).then(function(response) {
				_self.meeting = response.data;
				console.log(_self.meeting);
			}, function(error) {
				console.error(error);
			})
		},
		
		/**
		 * 保存
		 */
		save:function(){
			this.meeting.minutes = CKEDITOR.instances.myedit.getData();
			let meeting = JSON.parse(JSON.stringify(this.meeting));
			let projects = [];
			for(let index in meeting.projects){
				projects.push({id:meeting.projects[index]});
			}
			meeting.projects = projects;
			console.log(meeting);
			let self = this;
			this.$http.post("/minutes",meeting).then(function(response){
				if (response.data.success) {
					self.$Message.info({
						content : "创建会议成功",
						onClose : function() {
							
						}
					});
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				self.$Message.error(error.data.message);
			})	
		}
	}
});