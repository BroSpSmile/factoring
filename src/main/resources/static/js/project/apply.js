/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#apply',
	data : {
		project : {
			id : 0,
			items:[]
		},
		fileList:[]
	},
	created : function() {
		if(document.getElementById("applyId").value){
			this.project.id = document.getElementById("applyId").value;
			this.getProject(this.project.id);
		}
	},
	methods : {
		/**
		 * 获取项目
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

		commit : function() {
			if(this.fileList === undefined || this.fileList.length == 0){
				this.$Message.error("请上传尽调文件");
				return false;
			}
			for(let index in this.fileList){
				let item={
						projectId:this.project.id,
						itemType:"TUNEUP",
						itemName:this.fileList[index].name,
						itemValue:this.fileList[index].response.data.fileId
				}
				this.project.items.push(item);
			}
			let self = this;
			this.$http.post("/apply",this.project).then(function(response){
				if (response.data.success) {
					self.$Message.info({
						content : "尽调申请成功",
						onClose : function() {
							window.close();
						}
					});
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				self.$Message.error(error);
			})
			
		},
		
		/**
		 * 下载文件
		 */
		downloadItem:function(item){
			window.open("/file?fileId="+item.itemValue+"&fileName="+item.itemName);
		},
		
		deleteLoadItem:function(item,index){
			let _self = this;
			this.$http.delete("/project/items",item).then(function(response){
				if (response.data.success) {
					_self.project.items.splice(index, 1);
					_self.$Message.info("删除成功");
				} else {
					_self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				_self.$Message.error(error.data.errorMessage);
			})
		}
	}
});
