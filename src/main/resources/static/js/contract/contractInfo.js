/**
 * 合同基本信息
 */
common.pageName = "contractInfo";
common.openName = [ '3' ];

var vue = new Vue({
    el : '#contractInfo',
    data : {
        formInline:{
            type : []
        },
        queryParam : {
            condition : {},
            pageNum : 1,
            pageSize : 10
        },
        addForm : {
            baseInfo : {},
            contractExtendInfo : {},
            contractReceivableAgreement : {},
            contractReceivableConfirmation : {},
            signList : [],
            attachList : [],
            projectMode : 1
        },
        pageInfo:{},
        tableColumns:[],
        statusList:[],
        fileList : [],
        showResult:false,
        modal1:false,
        panelOpen : "0"
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
            this.$http.post("/contractInfo/list", _self.queryParam).then(
                function(response) {
                    _self.pageInfo = response.data;
                }, function(error) {
                    _self.$Message.error(error.data.message);
                })
        },
        /**
         * 新增合同
         */
        addContract : function() {
            this.modal1 = true;
            this.addForm = {
                baseInfo : {},
                contractExtendInfo : {},
                contractReceivableAgreement : {},
                contractReceivableConfirmation : {},
                signList : [],
                attachList : [],
                projectMode : 1
            };
            this.addForm.contractExtendInfo.fpAccountName = '苏州市相城融金商业保理有限公司';
            this.addForm.contractReceivableConfirmation.assigneeAccountName = '苏州市相城融金商业保理有限公司';
        },
        /**
         * 保存合同
         */
        saveContract : function() {
            let self = this;
            this.genFileInfo();
            if(this.addForm.baseInfo.id == null || this.addForm.baseInfo.id === ""){
                this.$http.post("/contractInfo", this.addForm).then(function(response) {
                    if (response.data.success) {
                        self.$Message.info({
                            content : "保存成功",
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
            }else{
                this.$http.put("/contractInfo", this.addForm).then(function(response) {
                    if (response.data.success) {
                        self.$Message.info({
                            content : "更新成功",
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
            }
        },
        /**
         * 删除警告
         */
        deleteWarn:function(id){
            this.$Modal.confirm({
                title: '删除提示',
                content: '<p>确认是否删除当前签署清单</p>',
                onOk: () => {
                this.deleteSign(id);
        },
            onCancel: () => {}
        })
        },
        /** 删除签署清单 */
        deleteSign:function(id){
            let self = this;
            this.$http.delete("/contractInfo/" + id).then(function(response){
                if (response.data.success) {
                    self.$Message.info({
                        content : "删除成功",
                        onClose : function() {
                            self.query();
                            self.cancel();
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
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
         * 状态流转
         * @param id
         */
        submitAudit : function(id) {
            let self = this;
            this.$http.put("/contractInfo/submitAudit/" + id).then(function(response) {
                if (response.data.success) {
                    self.$Message.info({
                        content : "提交审核成功",
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
         * 更新合同
         */
        updateContract : function(id){
            let self = this;
            this.$http.get("/contractInfo/" + id).then(function(response){
                if (response.data.success) {
                    self.addForm = response.data.data;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
            this.modal1 = true;
            console.log(this.addForm)
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
        /** 分页 */
        pageChange : function(page){
            this.query();
        },
        /**
         * 获取签署清单
         * @param projectMode
         */
        getSignList : function (projectMode) {
            let self = this;
            this.$http.get("/signListTemplate/list/" + projectMode).then(function(response){
                if (response.data.success) {
                    self.addForm.signList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
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
        },
        handleRemove : function(index) {
           // this.addForm.signList[index].status = 1;
            this.addForm.signList.splice(index,1);
        },
        /**
         * 文件上传成功
         */
        uploadSuccess: function (response, file, fileList) {
            this.fileList = fileList;
            console.log(fileList);
        },
        /**
         * 文件上传失败
         */
        uploadError: function (error, file, fileList) {
            this.fileList = fileList;
            console.log(error);
        },
        removeFile: function (file, fileList) {
            this.fileList = fileList;
            console.log(file);
            let fileId = file.response.data.fileId;
            let self = this;
            this.$http.delete("/file/" + fileId).then(function (response) {
                if (response.data.success) {
                    self.$Message.info("删除成功");

                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error.data.errorMessage);
            })
        },
        deleteFile: function (fileId) {
            let self = this;
            this.$http.delete("/file/" + fileId).then(function (response) {
                if (response.data.success) {
                    self.$Message.info("删除成功");
                    for (let index in self.addForm.attachList) {
                        if (self.addForm.attachList[index].fileId === fileId) {
                            let item = self.addForm.attachList[index];
                            this.remove(self.addForm.attachList, item);
                        }
                    }
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error.data.errorMessage);
            })
        },
        removeAllFile: function () {
            for (let index in this.addForm.fileList) {
                let fileId = this.addForm.fileList[index].response.data.fileId;
                this.$http.delete("/file/" + fileId).then(function (response) {
                    if (response.data.success) {
                        //self.$Message.info("删除成功");
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    self.$Message.error(error.data.errorMessage);
                })
            }
        },
        genFileInfo: function () {
            for (let index in this.fileList) {
                let item = {
                    attachName: this.fileList[index].name,
                    fileId: this.fileList[index].response.data.fileId
                };
                this.addForm.attachList.push(item);
            }
            return true;
        },
        remove: function (array, val) {
            let index = array.indexOf(val);
            if (index > -1) {
                array.splice(index, 1);
            }
        }
    }
});

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
                    vue.updateContract(param.row.id);
    }
    }
    }, '编辑'),
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
                vue.submitAuditWarn(param.row.id);
    }
    }
    }, '提交审核'),
        h('Button', {
            props: {
                size: "small",
                type: "error"
            },
            style: {
                marginRight: '5px'
            },
            on: {
                click: () => {
                vue.deleteWarn(param.row.id);
    }
    }
    }, '删除')
    ]
    )
    }
}
];