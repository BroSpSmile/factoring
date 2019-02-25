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
			department:{},
			user:{},
			project:{
				items:[]
			}
		},
		projects:[],
		fileList:[]
	},
	created : function() {
		this.initData();
	},
	methods : {
		/**
		 * 初始化项目
		 */
		initData:function(){
			let _self = this;
			this.$http.get("/past/project").then(function(response){
				_self.projects = response.data;
			},function(error){
				console.error(error);
			});
			this.$http.get("/login/user").then(function(response){
				_self.loan.user = response.data;
			},function(error){
				_self.$Message.error(error);
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
			console.log(value);
			value = ""+value;
			return value.replace(/￥s?|(,*)/g, '')
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
							itemType:"LOAN_FILE",
							itemName:this.fileList[index].name,
							itemValue:this.fileList[index].response.data.fileId
					}
					this.loan.project.items.push(item);
				}
			}
			console.log(this.loan);
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
		}
	}
});
