/**
 * 菜单信息
 */
common.pageName = "loanApply";
common.openName = [ '4' ];

var vue = new Vue({
	el : '#apply',
	data : {
		loan:{
			type:"ONLINE",
			chineseAmount:"",
			subscriptionAmount:0.00,
			payments:0.00,
			accumulativeyments:0.00,
			unpaid:0.00,
			createTime:new Date(),
			department:"",
			user:"",
			project:{
				id:0,
				items:[]
			},
			groups:[{
				
			}]
		},
		changeFlag:false,
		projects:[],
		fileList:[]
	},
	created : function() {
		this.initData();
		this.getLoan();
	},
	methods : {
		/**
		 * 初始化项目
		 */
		initData:function(){
			let _self = this;
			this.$http.get("/past/project").then(function(response){
				_self.projects = response.data;
				if(document.getElementById("projectId").value){
					_self.loan.project.id  = parseInt(document.getElementById("projectId").value);
					_self.changeFlag = true;
				}
			},function(error){
				console.error(error);
			});
			this.$http.get("/login/user").then(function(response){
				_self.loan.user = response.data.username;
			},function(error){
				_self.$Message.error(error);
			})
		},
		
		/**
		 * 获取项目信息
		 */
		getLoan:function(){
			let id = document.getElementById("projectId").value;
			let _self = this;
			if(id){
				this.$http.get("/loanApply/"+id).then(function(response){
					if(response.data){
						_self.loan = response.data;
						if(!_self.loan.groups||_self.loan.groups.length==0){
							_self.loan.groups = [];
							_self.loan.groups.push({
								payments:0
							})
						}
					}
				},function(error){
					console.error(error);
				})
			}
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
			console.log(error);
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
		 * 转换大写金额
		 */
		toChinese:function(value){
			if(null==value){
				value=0;
			}
			let _self = this;
			this.$http.get("/common/chinese/"+value).then(function(response){
				_self.loan.chineseAmount = response.data;
			},function(error){
				_self.$Message.error(error.data.errorMessage);
			});
		},
		
		/**
		 * formatter
		 */
		formatter:function(value){
			value = value+'';
			var intSum = value.replace( /\B(?=(?:\d{3})+$)/g, ',' );
			return '￥'+intSum;
		},
		
		parser:function(value){
			value = ""+value;
			return value.replace(/￥s?|(,*)/g, '')
		},
		
		save:function(){
			if(this.loan.type=='OFFLINE'){
				if(this.fileList === undefined || this.fileList.length == 0){
					this.$Message.error("请上传尽调文件");
					return false;
				}
				for(let index in this.fileList){
					let item={
							projectId:this.loan.project.id,
							itemType:"PENDINGLOAN",
							itemName:this.fileList[index].name,
							itemValue:this.fileList[index].response.data.fileId
					}
					this.loan.project.items.push(item);
				}
			}
			let _self = this;
			this.$http.post("/loanApply",this.loan).then(function(response){
				_self.$Message.info({
					content : "保存成功",
					onClose : function() {
					}
				});
			},function(error){
				_self.$Message.error(error);
			})
		},
		
		add:function(){
			this.loan.groups.push({
				payments:0
			});
		},
		
		remove:function(index){
			this.loan.groups.splice(index,1);
		},
		
		commit:function(){
			if(this.loan.type=='OFFLINE'){
				if(this.fileList === undefined || this.fileList.length == 0){
					this.$Message.error("请上传尽调文件");
					return false;
				}
				for(let index in this.fileList){
					let item={
							projectId:this.loan.project.id,
							itemType:"LOAN",
							itemName:this.fileList[index].name,
							itemValue:this.fileList[index].response.data.fileId
					}
					this.loan.project.items.push(item);
				}
			}
			let _self = this;
			this.$Spin.show();
			this.$http.post("/loanApply/commit",this.loan).then(function(response){
				this.$Spin.hide();
				_self.$Message.info({
					content : "申请成功",
					onClose : function() {
						window.close();
					}
				});
			},function(error){
				this.$Spin.hide();
				_self.$Message.error(error);
			})
		}
	}
});
