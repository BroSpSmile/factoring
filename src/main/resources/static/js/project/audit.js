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
		modal2:false,
		fileList:[],
		record:{
			items:[]
		},
        contractAudit : {
        },
		showAuditButton:false,
		nowStep:0,
		tableColumns:[],
		isLastFileStep: false,
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
					_self.audit = response.data.data;
					_self.nowStep = _self.audit.step;
					_self.showAuditButton = response.data.success;
				},function(error){
					console.log(error);
				})
			}
		},
		
		/**
		 * 
		 */
		openPass:function(){
			this.audit.remark = "";
			this.audit.step = this.nowStep;
			this.modal1 = true;
		},
		
		/**
		 * 
		 */
		openReject:function(){
			this.audit.remark = "";
			this.audit.step = this.nowStep;
			this.modal2 = true;
		},
		/**
		 * 审核成功
		 */
		pass:function(){
			let _self = this;
			this.record.audit = this.audit;
			if(this.audit.auditType=='TUNEUP'&& (this.audit.step==2||this.audit.step==3)){
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
							_self.getAudit(document.getElementById("auditId").value);
						}
					});
				} else {
					_self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				_self.$Message.error(response.data.errorMessage);
			})
		},
		
		/**
		 * 审核驳回
		 */
		reject:function(){
			let _self = this;
			//非合同审核驳回
			if(this.audit.auditType !== 'CONTRACT') {
                this.record.audit = this.audit;
                this.$http.put("/audit", this.record).then(function (response) {
                    if (response.data.success) {
                        _self.$Message.info({
                            content: "驳回成功",
                            onClose: function () {
                                _self.cancel();
                                _self.getAudit(document.getElementById("auditId").value);
                            }
                        });
                    } else {
                        _self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    _self.$Message.error(response.data.errorMessage);
                })
            } else {
                this.contractAudit.operationType = 2;
                this.contractAudit.projectId = this.audit.project.id;
                this.contractAudit.auditId = this.audit.id;
                this.contractAudit.remark = this.record.remark;
                this.contractAudit.rejectStatus = this.audit.step;
                this.$http.post("/contractAudit/audit", this.contractAudit).then(function(response) {
                    if (response.data.success) {
                        _self.$Message.info({
                            content : "驳回成功",
                            onClose : function() {
                                _self.cancel();
                            }
                        });
                    } else {
                        _self.$Message.error(response.data.errorMessage);
                    }
                }, function(error) {
                    _self.$Message.error(error.data.message);
                });
			}
		},
        /**
         * 合同审核通过
         * @param id
         */
        contractPass : function() {
            let self = this;
            this.contractAudit.operationType = 1;
            this.contractAudit.projectId = this.audit.project.id;
            this.contractAudit.auditId = this.audit.id;
            this.$http.post("/contractAudit/audit", this.contractAudit).then(function(response) {
                if (response.data.success) {
                    self.$Message.info({
                        content : "审核成功",
                        onClose : function() {
                            self.cancel();
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function(error) {
                self.$Message.error(error.data.message);
            });
        },
		cancel:function(){
			this.modal1 = false;
			this.modal2 = false;
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