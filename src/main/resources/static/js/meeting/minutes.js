/**
 * 菜单信息
 */
common.pageName = "meeting";
common.openName = [ 'meeting' ];

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
		projects:[],
		fileList:[]
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
				if(_self.meeting.kind=='DIRECTORATE'){
					let projects = [];
					for (let index in _self.meeting.projects){
						projects.push(_self.meeting.projects[index].id);
					}
					_self.meeting.projects = projects;
					CKEDITOR.replace( 'myedit');
					CKEDITOR.instances.myedit.setData(_self.meeting.minutes);
				}
				console.log(_self.meeting);
			}, function(error) {
				console.error(error);
			})
		},
		
		/**
		 * 保存
		 */
		save:function(progress){
			let meeting = JSON.parse(JSON.stringify(this.meeting));
			if(meeting.kind=='DIRECTORATE'){
				meeting.minutes = CKEDITOR.instances.myedit.getData();
				let projects = [];
				for(let index in meeting.projects){
					projects.push({id:meeting.projects[index]});
				}
				meeting.projects = projects;
			}else{
				for(let index in this.fileList){
					let item={
							projectId:this.meeting.projects[0].id,
							itemType:"INITIATE",
							itemName:this.fileList[index].name,
							itemValue:this.fileList[index].response.data.fileId
					}
					meeting.projects[0].items = new Array();
					meeting.projects[0].items.push(item);
				}
				meeting.projects[0].progress = progress;
			}
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
		},
		
		/**
		 * 文件上传成功
		 */
		uploadSuccess : function(response, file, fileList) {
			this.fileList=fileList;
			console.log(response);
		},
		
		/**
		 * 文件上传失败
		 */
		uploadError:function(error, file, fileList){
			this.fileList=fileList;
		},
		
		removeFile:function(file, fileList){
			this.fileList=fileList;
			console.log(file);
			let fileId = file.response.data.fileId;
			let self =this;
			this.$http.delete("/file/"+fileId).then(function(response){
				if (response.data.success) {
					self.$Message.info("删除成功");
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				self.$Message.error(error.data.errorMessage);
			})
			
		},
		
		/**
		 * 下载会议纪要
		 */
		download:function(){
			window.open("/minutes/download/"+this.meeting.id);
		}
	}
});