/**
 * 合同审核
 */
common.pageName = "contractAudit";
common.openName = [ '3' ];

var vue = new Vue({
	el : '#contractAudit',
	data : {
		formInline:{
            type : []
		},
		queryParam : {
			condition : {},
			pageNum : 1,
			pageSize : 10
		},
        contractInfo : {
            baseInfo : {},
            contractExtendInfo : {},
            contractReceivableAgreement : {},
            contractReceivableConfirmation : {},
            signList : [],
            attachList : [],
            project : {}
		},
        contractAudit : {

        },
        auditRecordList : [],
		pageInfo:{},
		tableColumns:[],
        auditRecordColumns : [],
        statusList:[],
        fileList : [],
		showResult:false,
		modal1 : false,
        modal2 : false
	},
	created : function() {
	    this.initData();
	},
	methods : {
        /**
         * 初始化数据
         */
        initData : function() {
            let self = this;
            this.$http.get("/flowConfig/status/1").then(function(response){
                if (response.data.success) {
                    self.statusList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
        },
		/**
		 * 查询
		 */
		query : function(page){
			this.showResult=true;
			this.queryParam.pageNum = page;
			var _self = this;
            _self.queryParam.condition = _self.formInline;
			this.$http.post("/contractAudit/list", _self.queryParam).then(
					function(response) {
                        _self.pageInfo = response.data;
					}, function(error) {
                    	_self.$Message.error(error.data.message);
					})
		},
        /**
         * 提交审核警告
         */
        submitAuditWarn:function(id){
            this.$Modal.confirm({
                title: '审核提示',
                content: '<p>确认要提交审核</p>',
                onOk: () => {
                    this.submitAudit(id);
                },
                onCancel: () => {}
            })
        },
        /**
         * 审核通过
         * @param id
         */
        auditPass : function() {
            let self = this;
            this.contractAudit.operationType = 1;
            this.contractAudit.contractSerialNo = this.contractInfo.baseInfo.serialNo;
            this.$http.post("/contractAudit/audit", this.contractAudit).then(function(response) {
                if (response.data.success) {
                    self.$Message.info({
                        content : "审核成功",
                        onClose : function() {
                            self.query();
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
        /**
         * 审核驳回
         * @param id
         */
        saveAuditReject : function() {
            let self = this;
            this.contractAudit.operationType = 2;
            this.contractAudit.contractSerialNo = this.contractInfo.baseInfo.serialNo;
            this.$http.post("/contractAudit/audit", this.contractAudit).then(function(response) {
                if (response.data.success) {
                    self.$Message.info({
                        content : "审核驳回成功",
                        onClose : function() {
                            self.query();
                            self.cancelReject();
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
        /**
         * 审核驳回
         * @param id
         */
        auditReject : function() {
            this.modal2 = true;
        },
        /**
         * 审核详情
         */
        auditDetail : function(id, serialNo){
            let self = this;
            this.$http.get("/contractInfo/" + id).then(function(response){
                if (response.data.success) {
                    self.contractInfo = response.data.data;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })

            this.$http.get("/contractAudit/auditRecordList/" + serialNo).then(function(response){
                if (response.data.success) {
                    self.auditRecordList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
            this.modal1 = true;
        },
        /**
         * 下载附件
         * @param fileId
         */
        download: function (fileId) {
            //TODO
            console.debug(fileId);
        },
		/**
		 * 取消保存
		 */
		cancel : function() {
			this.modal1 = false;
            if(this.addForm.baseInfo.id === '') {
                this.$refs['entityDataForm'].resetFields();
            }
		},
        cancelReject : function() {
            this.modal2 = false;
            this.$refs['rejectForm'].resetFields();
        },
        /** 分页 */
        pageChange : function(page){
            this.query();
        },
        /**
         * 项目模式转换
         * @param value
         */
        getProjectModeDesc : function(value) {
            if(value === 1) {
                return "有追索权模式";
            } else if(value === 2) {
                return "无追索权模式";
            }
            return "";
        },
        /**
         * 操作类型转换
         * @param value
         */
        getOperationTypeDesc : function(value) {
            if(value === 1) {
                return "通过";
            } else if(value === 2) {
                return "驳回";
            }
            return "";
        },
        /**
         * 状态转换
         * @param value
         */
        getStatusDesc : function(value) {
            if(value === 1) {
                return "提出申请";
            } else if(value === 2) {
                return "部门初审";
            } else if(value === 3) {
                return "法务风控审核";
            } else if(value === 4) {
                return "集团副总审核";
            } else if(value === 5) {
                return "集团正总审核";
            } else if(value === 6) {
                return "完成";
            } else if(value === 7) {
                return "通知办公室";
            } else if(value === 8) {
                return "签署";
            } else if(value === 9) {
                return "签署完成";
            }
            return "";
        }
	}
});

vue.auditRecordColumns=[
    {
        title: '操作状态',
        key: 'operationStatus',
        align: 'left'
    },{
        title: '操作人',
        key: 'operationUser',
        align: 'left'
    },{
        title: '操作类型',
        key: 'operationType',
        align: 'left',
        render:(h,param)=> {
            return h('span', vue.getOperationTypeDesc(param.row.operationType));
        }
    },{
        title: '操作时间',
        key: 'operationTime',
        align: 'left'
    },{
        title: '备注',
        key: 'remark',
        align: 'left'
    }
];

vue.tableColumns=[
    {
        title: '合同编号',
        key: 'contractCode',
        align: 'left'
    },{
        title: '合同名称',
        key: 'contractName',
        align: 'left'
    },{
        title: '项目模式',
        key: 'projectMode',
        align: 'left',
        render:(h,param)=> {
            return h('span', vue.getProjectModeDesc(param.row.projectMode));
        }
    },{
        title: '状态',
        key: 'status',
        align: 'left',
        render:(h,param)=> {
            return h('span', vue.getStatusDesc(param.row.status));
        }
    },{
        title: '操作',
        align: 'center',
        render:(h,param)=>{
        return h('div', [
            h('span'),
                h('Button', {
                    props: {
                        size: "small",
                        type: "warning"
                    },
                    style: {
                        marginRight: '5px'
                    },
                    on: {
                        click: () => {
                            vue.auditDetail(param.row.id, param.row.serialNo);
                        }
                    }
                }, '审核详情')
                ]
            )
        }
    }
];