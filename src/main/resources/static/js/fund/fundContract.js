/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#fundContract',
	data : {
		project:{},
		fund:{},
		fileList:[],
		items:[],
		webItems:[]
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
				_self.getFundTarget(_self.project.projectId);
			},function(error){
				console.error(error);
			})
		},

		/**
		 *
		 */
		getFundTarget:function(id){
			let _self = this;
			this.$http.get("/fund/"+id).then(function(response){
				_self.fund = response.data;
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
			this.$http.post("/project/items/"+id+"/CONTRACT_SIGN").then(function(response){
				_self.items = response.data;
			},function(error){
				console.error(error);
			})
		},
		
		/**
		 * 提交合同
		 */
		commit:function(){
			if((this.fileList === undefined || this.fileList.length == 0)&&!this.webItems[0].itemValue){
				this.$Message.error("请上传尽调文件");
				return false;
			}
			let items = [];
			for(let index in this.fileList){
				let item={
						projectId:this.project.id,
						itemType:this.fund.projectStep,
						itemName:this.fileList[index].name,
						itemValue:this.fileList[index].response.data.fileId
				}
				items.push(item);
			}
			for(let index in this.webItems){
				this.webItems[index].projectId = this.project.id;
				items.push(this.webItems[index]);
			}
			this.$Spin.show();
			let self = this;
			console.log();
			if(this.fund.projectStep == 'CONTRACT_SIGN'){
				this.$http.post("/fundContract",JSON.stringify(items)).then(function(response){
					this.$Spin.hide();
					if (response.data.success) {
						self.$Message.info({
							content : "上传合同成功"
						});
						self.fileList = [];
						self.getItems(self.project.id);
					} else {
						self.$Message.error(response.data.errorMessage);
					}
				},function(error){
					this.$Spin.hide();
					self.$Message.error(error);
				})
			}else{
				this.$http.post("/fundContract/signed",JSON.stringify(items)).then(function(response){
					this.$Spin.hide();
					if (response.data.success) {
						self.$Message.info({
							content : "上传合同成功"
						});
						self.fileList = [];
						self.getItems(self.project.id);
					} else {
						self.$Message.error(response.data.errorMessage);
					}
				},function(error){
					this.$Spin.hide();
					self.$Message.error(error);
				})
			}

		},



		/**
		 * 提交审核
		 */
		pass:function(){
			var project = this.project;
			this.$Modal.confirm({
				title: "是否提交合同审核",
				onOk:function (event) {
					this.$Spin.show();
					let self = this;
					project.detail ={};
					project.items = [];
					this.$http.put("/fundContract",project).then(function(response){
						this.$Spin.hide();
						if (response.data.success) {
							self.$Message.info({
								content : "提交审核成功"
							});
						} else {
							self.$Message.error(response.data.errorMessage);
						}
					},function(error){
						this.$Spin.hide();
						self.$Message.error(error);
					})
				},
				onCancel:function (event) {

				}
			});

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
		},

		/** 添加 */
		add:function(){
			this.webItems.push({
				itemKind:"WEB",
				itemType:"CONTRACT_SIGN",
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


