/**
 * 合同信息
 */
common.pageName = "contractInfoAdd";
common.openName = [ '3' ];

var vue = new Vue({
    el : '#contractInfoAdd',
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
            contractShareholderMeeting : {},
            contractFasa : {},
            signList : [],
            attachList : [],
            projectMode : 1
        },
        ruleValidate: {
            'contractExtendInfo.receivableAssigneeMoney' : [
                { type: 'number', message: '请输入数字', trigger: 'blur' }
            ],
            'contractExtendInfo.receivableRecoveryMoney' : [
                { type: 'number', message: '请输入数字', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.contractReceivable' : [
                { type: 'number', message: '请输入数字', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.assignorAbligorReceivable' : [
                { type: 'number', message: '请输入数字', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.receivableAssigneeMoneyPaid' : [
                { type: 'number', message: '请输入数字', trigger: 'blur' }
            ]
        },
        pageInfo:{},
        tableColumns:[],
        project : {},
        fileList : [],
        showResult:false,
        modal1:false,
        panelOpen : "projectInfo",
        isDisable : false
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
            let projectId = document.getElementById("projectId").value;

            //获取项目信息
            self.$http.get("/project/" + projectId).then(function(response){
                self.project = response.data;

                //获取合同信息
                this.$http.get("/contractInfo/" + projectId).then(function(response){
                    console.log(response.data)
                    //合同信息已经存在
                    if (response.data.success && response.data.data !== null) {
                        self.addForm = response.data.data;
                        if(response.data.data.contractShareholderMeeting === null) {
                            self.addForm.contractShareholderMeeting = {};
                        }
                        if(response.data.data.contractFasa === null) {
                            self.addForm.contractFasa = {};
                        }
                        if(response.data.data.contractExtendInfo === null) {
                            self.addForm.contractExtendInfo = {};
                        }
                        if(response.data.data.contractReceivableAgreement === null) {
                            self.addForm.contractReceivableAgreement = {};
                        }
                        if(response.data.data.contractReceivableConfirmation === null) {
                            self.addForm.contractReceivableConfirmation = {};
                        }
                        if(response.data.data.attachList === null) {
                            self.addForm.attachList = [];
                        }
                    } else {
                        //没有合同信息，初始新增数据
                        self.addForm.baseInfo.projectId = projectId;
                        self.addForm.contractExtendInfo.fpAccountName = '苏州市相城融金商业保理有限公司';
                        self.addForm.contractReceivableConfirmation.assigneeAccountName = '苏州市相城融金商业保理有限公司';
                        //有追索权模式
                        if(self.project.model === 'RECOURSE_RIGHT') {
                            self.addForm.baseInfo.projectMode = 1;
                            self.getSignList(1);
                        } else {
                            self.addForm.baseInfo.projectMode = 2;
                            self.getSignList(2);
                        }
                    }
                },function(error){
                    self.$Message.error(error.data.message);
                });
            },function(error){
                self.$Message.error(error);
            })
        },
        /**
         * 保存合同
         */
        saveContract : function() {
            let self = this;
            this.genFileInfo();
            this.isDisable = true;
            this.$refs.addForm.validate((valid) => {
                if(valid) {
                    if (this.addForm.baseInfo.id === undefined || this.addForm.baseInfo.id === null || this.addForm.baseInfo.id === "") {
                        this.$http.post("/contractInfo", this.addForm).then(function (response) {
                            if (response.data.success) {
                                self.$Message.info({
                                    content: "保存成功",
                                    onClose: function () {
                                        self.addForm.baseInfo.status = 0;
                                        self.isDisable = false;
                                        self.cancel();
                                    }
                                });
                            } else {
                                self.isDisable = false;
                                self.$Message.error(response.data.errorMessage);
                            }
                        }, function (error) {
                            self.isDisable = false;
                            self.$Message.error(error.data.message);
                        });
                    } else {
                        this.$http.put("/contractInfo", this.addForm).then(function (response) {
                            if (response.data.success) {
                                self.$Message.info({
                                    content: "更新成功",
                                    onClose: function () {
                                        self.isDisable = false;
                                        self.cancel();
                                    }
                                });
                            } else {
                                self.isDisable = false;
                                self.$Message.error(response.data.errorMessage);
                            }
                        }, function (error) {
                            self.isDisable = false;
                            self.$Message.error(error.data.message);
                        });
                    }
                } else {
                    this.isDisable = false;
                }
            });
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
                            self.isDisable = true;
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
            } else {
                this.fileList = [];
                this.$refs.upload.clearFiles();
            }
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
            if(value === 0) {
                return "新建";
            } if(value === 1) {
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
            this.addForm.signList.splice(index,1);
        },
        handleAdd : function() {
            this.addForm.signList.push({
                status: 0,
                signListName : '',
                isRequired : 2,
                type : 'add'
            });
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