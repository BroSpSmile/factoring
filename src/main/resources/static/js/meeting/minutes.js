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
		meetingItems:[],
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
		},
		canUpload:function(){
			if(this.fileList&& this.fileList.length>3){
				return true;
			}else{
				return false;
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
		
		/** 获取会议信息 */
		getMeeting:function(){
			let _self = this;
			this.$http.get("/minutes/meeting/"+document.getElementById("meetingId").value).then(function(response) {
				_self.meeting = response.data;
					let projects = [];
					for (let index in _self.meeting.projects){
						projects.push(_self.meeting.projects[index].id);
					}
					_self.meeting.projects = projects;
				this.getItems();
			}, function(error) {
				console.error(error);
			})
		},
		
		/** 获取会议附件 */
		getItems:function(){
			this.meetingItems  = [];
			if(this.meeting.minutes){
				let items = this.meeting.minutes.split("&");
				for(let index in items){
					let item = items[index].split("|");
					let att={
							itemValue:item[0],
							itemName:item[1]
					}
					this.meetingItems.push(att);
				}
			}
		},
		
		/**
		 * 保存
		 */
		save:function(progress){
			let meeting = JSON.parse(JSON.stringify(this.meeting));
				if(this.fileList === undefined || this.fileList.length == 0){
					this.$Message.error("请上传会议纪要文件");
					return false;
				}
				let projects = [];
				for(let index in meeting.projects){
					projects.push({id:meeting.projects[index]});
				}
				meeting.projects = projects;
				for(let index in this.fileList){
					if(meeting.kind=='APPROVAL'){
						let item={
								projectId:meeting.projects[0].id,
								itemType:"INITIATE",
								itemName:this.fileList[index].name,
								itemValue:this.fileList[index].response.data.fileId
						}
						meeting.projects[0].items = new Array();
						meeting.projects[0].items.push(item);
						meeting.projects[0].progress = progress;
					}
					meeting.minutesKind = 'CUSTOM';
					if(index==0){
						meeting.minutes = this.fileList[index].response.data.fileId+"|"+this.fileList[index].name ;
					}else{
						meeting.minutes += "&"+this.fileList[index].response.data.fileId+"|"+this.fileList[index].name ;
					}
				}
			let self = this;
			this.$Spin.show();
			this.$http.post("/minutes",meeting).then(function(response){
				this.$Spin.hide();
				if (response.data.success) {
					self.$Message.info({
						content : "创建会议成功",
						onClose : function() {
							self.getMeeting();
						}
					});
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				this.$Spin.hide();
				self.$Message.error(error.data.message);
			})	
		},
		
		/**
		 * 文件上传成功
		 */
		uploadSuccess : function(response, file, fileList) {
			this.fileList=fileList;
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
		},
		
		/**
		 * 下载文件
		 */
		downloadItem:function(item){
			window.open("/file?fileId="+item.itemValue+"&fileName="+encodeURI(item.itemName));
		}
	}
});