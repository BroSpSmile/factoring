/**
 * 菜单信息
 */
common.pageName = "audit";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#audit',
	data : {
		audit:{},
		auditType:[],
		auditResult:[],
		modal1:false,
		fileList:[],
		record:{
			items:[]
		},
		tableColumns:[]
	},
	created : function() {
		this.initDate();
	},
	filters:{
		timeFilter:function(value){
			return moment(value).format('YYYY-MM-DD HH:mm');
		}
	},
	computed:{
		auditTypeShow:function(){
			if(this.audit.auditType){
				for(var index in this.auditType){
					if(this.audit.auditType == this.auditType[index].value){
						return this.auditType[index].text;
					}
				}
				return "";
			}else{
				return "";
			}
		},
		auditTitle:function(){
			if(this.audit.auditType){
				if(this.audit.auditType=='TUNEUP'){
					return "尽调审核报告";
				}else if(this.audit.auditType=='LOAN'){
					return "放款凭证"
				}else{
					return "上传附件";
				}
			}
		}
	},
	methods : {
		
		/**
		 * 初始化
		 */
		initDate:function(){
			let _self = this;
			this.$http.get("/combo/auditType").then(function(response) {
				_self.auditType = response.data;
				_self.getAudit(document.getElementById("auditId").value);
			}, function(error) {
				console.error(error);
			});
			this.$http.get("/combo/auditResult").then(function(response) {
				_self.auditResult = response.data;
			}, function(error) {
				console.error(error);
			})
		},
		
		auditResultText:function(value){
			for(let index in this.auditResult){
				if(value==this.auditResult[index].value){
					return this.auditResult[index].text;
				}
			}
			return "";
		},
		
		/**
		 * 获取审核信息
		 */
		getAudit:function(id){
			if(id){
				let _self = this;
				this.$http.get("/audit/"+id).then(function(response){
					_self.audit = response.data;
				},function(error){
					console.log(error);
				})
			}
		},
		
		/**
		 * 
		 */
		openPass:function(){
			this.modal1 = true;
		},
		
		/**
		 * 审核成功
		 */
		pass:function(){
			let _self = this;
			this.record.audit = this.audit;
			if(this.audit.auditType=='TUNEUP' || this.audit.auditType=='LOAN'){
				if(this.fileList === undefined || this.fileList.length == 0){
					this.$Message.error("请上传尽调文件");
					return false;
				}
				for(let index in this.fileList){
					let item={
							itemName:this.fileList[index].name,
							itemValue:this.fileList[index].response.data.fileId
					}
					this.record.items.push(item);
				}
			}
			this.$http.post("/audit",this.record).then(function(response){
				if (response.data.success) {
					_self.$Message.info({
						content : "审核成功",
						onClose : function() {
							_self.cancel();
							window.close();
						}
					});
				} else {
					_self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				_self.$Message.error(response.data.errorMessage);
			})
		},
		
		cancel:function(){
			this.modal1 = false;
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
		
		/**
		 * 删除文件
		 */
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
		}
	}
});

vue.tableColumns=[
	{
        title: '操作类型',
        key: 'type',
        align: 'center'
    },{
        title: '操作人',
        key: 'auditor.username',
        align: 'center',
        render:(h,param)=>{
        	return h('span',param.row.auditor.username)
        }
    },{
        title: '执行状态',
        key: 'result',
        align: 'center',
        render:(h,param)=>{
        	return h('span',vue.auditResultText(param.row.result))
        }
    },{
        title: '操作时间',
        key: 'auditTime',
        align: 'center',
        render:(h,param)=>{
        	return h('span',moment(param.row.auditTime).format('YYYY-MM-DD HH:mm:ss'))
        }
    },{
        title: '备注',
        key: 'remark',
        align: 'center'
    },{
        title: '执行结果',
        key: 'status',
        align: 'center'
    }
];