/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#payment',
	data : {
		project:{},
		fileList:[],
		items:[]
	},
	created : function() {
		let id = document.getElementById("fundId").value;
		this.getProject(id);
		this.getItems(id);
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
		 * 获取项目附件
		 * @param id
		 */
		getItems:function(id){
			let _self = this;
			this.$http.post("/project/items/"+id+"/PAYMENT").then(function(response){
				_self.items = response.data;
			},function(error){
				console.error(error);
			})
		},
		
		/**
		 * 提交保密协议
		 */
		commit:function(){
			if(this.fileList === undefined || this.fileList.length == 0){
				this.$Message.error("请上传尽调文件");
				return false;
			}
			let items = [];
			for(let index in this.fileList){
				let item={
						projectId:this.project.id,
						itemType:"PAYMENT",
						itemName:this.fileList[index].name,
						itemValue:this.fileList[index].response.data.fileId
				}
				items.push(item);
			}
			this.$Spin.show();
			let self = this;
			this.$http.post("/payment/PAYMENT_AUDIT",JSON.stringify(items)).then(function(response){
				this.$Spin.hide();
				if (response.data.success) {
					self.$Message.info({
						content : "上传付款通知书成功"
					});
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

		/**
		 * 下载文件
		 */
		downloadItem:function(item){
			window.open("/file?fileId="+item.itemValue+"&fileName="+encodeURI(item.itemName));
		}
	}
});


