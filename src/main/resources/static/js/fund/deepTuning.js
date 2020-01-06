/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#deepTuning',
	data : {
		project:{},
		fileList:[],
		webItems:[]
	},
	created : function() {
		let id = document.getElementById("fundId").value;
		this.getProject(id);
	},
	methods : {
		/**
		 * 
		 */
		getProject:function(id){
			let _self = this;
			this.$http.get("/project/"+id).then(function(response){
				_self.project = response.data;
			},function(error){
				console.error(error);
			})
		},
		
		/**
		 * 提交深入尽调协议
		 */
		save:function(){
			if((this.fileList === undefined || this.fileList.length == 0)&&!this.webItems[0].itemValue){
				this.$Message.error("请上传尽调文件");
				return false;
			}
			let items = [];
			for(let index in this.fileList){
				let item={
						projectId:this.project.id,
						itemType:"DEEP_TUNING",
						itemName:this.fileList[index].name,
						itemValue:this.fileList[index].response.data.fileId
				}
				items.push(item);
			}
			for(let index in this.webItems){
				if(this.webItems[index].itemValue){
					this.webItems[index].projectId = this.project.id;
					items.push(this.webItems[index]);
				}
			}
			this.$Spin.show();
			let self = this;
			this.$http.post("/deepTuning",JSON.stringify(items)).then(function(response){
				this.$Spin.hide();
				if (response.data.success) {
					self.$Message.info({
						content : "上传深入尽调附件成功"
					});
					self.getProject(self.project.id);
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				this.$Spin.hide();
				self.$Message.error(error);
			})
		},

		commit:function(){
			this.$Spin.show();
			let self = this;
			this.$http.post("/fundstatus/PARTMENT_AUDIT",this.project).then(function(response){
				this.$Spin.hide();
				if (response.data.success) {
					self.$Message.info({
						content : "上传深入尽调附件成功"
					});
					self.getProject(self.project.id);
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				this.$Spin.hide();
				self.$Message.error(error);
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
		 * 下载文件
		 */
		downloadItem:function(item){
			window.open("/file?fileId="+item.itemValue+"&fileName="+item.itemName);
		},


		/**
		 * 打开文件
		 */
		openItem: function (item) {
			window.open(item.itemValue);
		},


		/**
		 * 删除附件
		 */
		deleteLoadItem:function(item,index){
			let _self = this;
			this.$http.delete("/project/items",item).then(function(response){
				if (response.data.success) {
					_self.project.items.splice(index, 1);
					_self.items.splice(index, 1);
					if(_self.items.length<1){
						_self.showUpdate = true;
					}
					_self.$Message.info("删除成功");
				} else {
					_self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				_self.$Message.error(error.data.errorMessage);
			})
		},


		/** 添加 */
		add:function(){
			this.webItems.push({
				itemKind:"WEB",
				itemType:"DEEP_TUNING",
				itemName:"",
				itemValue:""
			});
		},

		deleteFile: function (item) {
			let self = this;
			this.$http.delete("/attch", item).then(function (response) {
				if (response.data.success) {
					self.$Message.info("删除成功");
					self.getProject(self.project.id);
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			}, function (error) {
				self.$Message.error(error.data.errorMessage);
			})
		},

		/**
		 * 移除
		 */
		remove:function(index){
			this.webItems.splice(index,1);
		}
	}
});


