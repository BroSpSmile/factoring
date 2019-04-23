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
            contractExtendInfo : {
                receivableRecoveryMoneyUpper : null,
                interestRate : null,
                receivableAssigneeMoney : null,
                obligorEnjoyMoney : null,
                receivableMoney : null
            },
            contractReceivableAgreement : {},
            contractReceivableConfirmation : {
                invoiceMoney : null,
                receivableAssigneeMoney : null
            },
            contractShareholderMeeting : {
                meetingNumber : null,
                shareholderNumber : null
            },
            contractFasa : {
                expiryDateMonth : null,
                advisoryServiceMoney : null
            },
            signList : [],
            attachList : [],
            projectMode : 1
        },
        ruleValidate: {
            'contractReceivableConfirmation.contractReceivable' : [
                { type: 'number', message: '请输入数字', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.assignorAbligorReceivable' : [
                { type: 'number', message: '请输入数字', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.receivableAssigneeMoneyPaid' : [
                { type: 'number', message: '请输入数字', trigger: 'blur' }
            ],
            'baseInfo.contractTemplate' : [
                { required : true, type: 'number', message: '请选择合同模板', trigger: 'change' }
            ],
            'contractShareholderMeeting.spCompanyName' : [
                { required : true, message: '请输入乙方公司名称', trigger: 'blur' }
            ],
            'contractFasa.fpCompanyName' : [
                { required : true, message: '请输入甲方公司名称', trigger: 'blur' }
            ],
            'contractFasa.fpResidence' : [
                { required : true, message: '请输入甲方住所', trigger: 'blur' }
            ],
            'contractFasa.fpLegalPerson' : [
                { required : true, message: '请输入甲方法定代表人', trigger: 'blur' }
            ],
            'contractFasa.signAddress' : [
                { required : true, message: '请输入协议签署地', trigger: 'blur' }
            ],
            'contractFasa.signDate' : [
                { required : true, type: 'date', message: '请输入签署日期', trigger: 'blur' }
            ],
            'contractFasa.advisoryServiceMoney' : [
                { required : true, type: 'number', message: '请输入财务顾问费', trigger: 'blur' }
            ],
            'contractFasa.advisoryServiceMoneyUpper' : [
                { required : true, message: '请输入财务顾问费大写', trigger: 'blur' }
            ],
            'contractFasa.advisoryServiceMoneyAppointment' : [
                { required : true, message: '请输入财务顾问费约定', trigger: 'blur' }
            ],
            'contractFasa.spBankName' : [
                { required : true, message: '请输入乙方银行名称', trigger: 'blur' }
            ],
            'contractFasa.spAccount' : [
                { required : true, message: '请输入乙方银行账户', trigger: 'blur' }
            ],
            'contractFasa.expiryDateMonth' : [
                { required : true, type: 'number', message: '请输入协议有效期月数', trigger: 'blur' }
            ],

            'contractExtendInfo.spCompanyName' : [
                { required : true, message: '请输入乙方公司名称', trigger: 'blur' }
            ],
            'contractExtendInfo.spResidence' : [
                { required : true, message: '请输入乙方住所', trigger: 'blur' }
            ],
            'contractExtendInfo.spLegalPerson' : [
                { required : true, message: '请输入乙方法定代表人', trigger: 'blur' }
            ],
            'contractExtendInfo.signDate' : [
                { required : true, type: 'date', message: '请输入保理合同签署日期', trigger: 'blur' }
            ],
            'contractExtendInfo.baseSignDate' : [
                { required : true, type: 'date', message: '请输入基础合同签署日期', trigger: 'blur' }
            ],
            'contractExtendInfo.obligor' : [
                { required : true, message: '请输入债务人', trigger: 'blur' }
            ],
            'contractExtendInfo.contractName' : [
                { required : true, message: '请输入合同名称', trigger: 'blur' }
            ],
            'contractExtendInfo.receivableMoney' : [
                { required : true, type: 'number', message: '请输入应收账款', trigger: 'blur' }
            ],
            'contractExtendInfo.receivableMoneyUpper' : [
                { required : true, message: '请输入应收账款大写', trigger: 'blur' }
            ],
            'contractExtendInfo.obligorEnjoyMoney' : [
                { required : true, type: 'number', message: '请输入债务人享有金额', trigger: 'blur' }
            ],
            'contractExtendInfo.obligorEnjoyMoneyUpper' : [
                { required : true, message: '请输入债务人享有金额大写', trigger: 'blur' }
            ],
            'contractExtendInfo.receivableAssigneeMoney' : [
                { required : true, type: 'number', message: '请输入应收账款受让款', trigger: 'blur' }
            ],
            'contractExtendInfo.receivableAssigneeMoneyUpper' : [
                { required : true, message: '请输入应收账款受让款大写', trigger: 'blur' }
            ],
            'contractExtendInfo.receivableRecoveryMoney' : [
                { required : true, type: 'number', message: '请输入应收账款回收款', trigger: 'blur' }
            ],
            'contractExtendInfo.receivableRecoveryMoneyUpper' : [
                { required : true, message: '请输入应收账款回收款大写', trigger: 'blur' }
            ],
            'contractExtendInfo.receivableRecoveryMoneyPaytime' : [
                { required : true, type: 'date', message: '请输入应收账款支付日期', trigger: 'blur' }
            ],
            'contractExtendInfo.fpAccountName' : [
                { required : true, message: '请输入甲方户名', trigger: 'blur' }
            ],
            'contractExtendInfo.fpBankName' : [
                { required : true, message: '请输入甲方银行名称', trigger: 'blur' }
            ],
            'contractExtendInfo.fpAccount' : [
                { required : true, message: '请输入甲方银行账户', trigger: 'blur' }
            ],
            'contractExtendInfo.spAccountName' : [
                { required : true, message: '请输入乙方户名', trigger: 'blur' }
            ],
            'contractExtendInfo.spBankName' : [
                { required : true, message: '请输入乙方银行名称', trigger: 'blur' }
            ],
            'contractExtendInfo.spAccount' : [
                { required : true, message: '请输入乙方银行账户', trigger: 'blur' }
            ],
            'contractExtendInfo.compulsoryRescissionDate' : [
                { required : true, type: 'date', message: '请输入合同强制解除日期', trigger: 'blur' }
            ],

            'contractReceivableConfirmation.signDate' : [
                { required : true, type: 'date', message: '请输入签署日期', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.assignor' : [
                { required : true, message: '请输入让与人', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.obligor' : [
                { required : true, message: '请输入债务人', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.businessContractName' : [
                { required : true, message: '请输入商务合同名称', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.receivableAssigneeMoney' : [
                { required : true, type: 'number', message: '请输入应收账款受让款', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.receivableAssigneeMoneyUpper' : [
                { required : true, message: '请输入应收账款受让款大写', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.receivableExpiryDate' : [
                { required : true, type: 'date', message: '请输入应收账款回收款截止日期', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.assignorCommitDate' : [
                { required : true, type: 'date', message: '请输入让与人提交资料日期', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.assigneeAccountName' : [
                { required : true, message: '请输入受让人户名', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.assigneeBankName' : [
                { required : true, message: '请输入受让人开户银行', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.assigneeAccount' : [
                { required : true, message: '请输入受让人账户', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.assignorCompanyName' : [
                { required : true, message: '请输入让与人公司名称', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.obligorCompanyName' : [
                { required : true, message: '请输入债务人公司名称', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.nameOfSubject' : [
                { required : true, message: '请输入商务合同标的物名称', trigger: 'blur' }
            ],
            'contractReceivableConfirmation.invoiceMoney' : [
                { required : true, type: 'number', message: '请输入发票/收据所载金额（元）', trigger: 'blur' }
            ],

            'contractReceivableAgreement.signDate' : [
                { required : true, type: 'date', message: '请输入签署日期', trigger: 'blur' }
            ],
            'contractReceivableAgreement.spName' : [
                { required : true, message: '请输入乙方名称', trigger: 'blur' }
            ],
            'contractReceivableAgreement.spResidence' : [
                { required : true, message: '请输入乙方住所', trigger: 'blur' }
            ],
            'contractReceivableAgreement.spLegalPerson' : [
                { required : true, message: '请输入乙方法定代表人', trigger: 'blur' }
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
                            self.addForm.contractShareholderMeeting = {
                                meetingNumber : null,
                                shareholderNumber : null
                            };
                        }
                        if(response.data.data.contractFasa === null) {
                            self.addForm.contractFasa = {
                                expiryDateMonth : null,
                                advisoryServiceMoney : null
                            };
                        }
                        if(response.data.data.contractExtendInfo === null) {
                            self.addForm.contractExtendInfo = {
                                receivableRecoveryMoneyUpper : null,
                                interestRate : null,
                                receivableAssigneeMoney : null,
                                obligorEnjoyMoney : null,
                                receivableMoney : null
                            };
                        }
                        if(response.data.data.contractReceivableAgreement === null) {
                            self.addForm.contractReceivableAgreement = {};
                        }
                        if(response.data.data.contractReceivableConfirmation === null) {
                            self.addForm.contractReceivableConfirmation = {
                                invoiceMoney : null,
                                receivableAssigneeMoney : null
                            };
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
                    //清单验证
                    for (let index in self.addForm.signList) {
                        if (self.addForm.signList[index].type === 'add' && self.addForm.signList[index].signListName === '') {
                            self.$Message.error('签署清单名称必须输入');
                            self.isDisable = false;
                            return;
                        }
                    }
                    if (this.addForm.baseInfo.id === undefined || this.addForm.baseInfo.id === null || this.addForm.baseInfo.id === "") {
                        this.$http.post("/contractInfo", this.addForm).then(function (response) {
                            if (response.data.success) {
                                console.log("id=" + response.data.data)
                                self.addForm.baseInfo.id = response.data.data;
                                self.addForm.baseInfo.status = 0;
                                self.isDisable = false;
                                self.$Message.info({
                                    content: "保存成功",
                                    onClose: function () {
                                        self.getAttachList();
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
                                        self.getAttachList();
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
         * 获取附件列表
         */
        getAttachList : function() {
            let self = this;
            this.$http.get("/contractInfo/attach/" + this.project.id).then(function(response){
                if (response.data.success) {
                    self.addForm.attachList = response.data.values;
                } else {
                    console.log(response);
                }
            });
        },
        /**
         * 提交审核警告
         */
        submitAuditWarn : function(id){
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
         * 转换大写金额
         */
        toChinese : function(value, upper){
            console.log(value)
            console.log(upper)
            if(null == value){
                value = 0;
            }
            let _self = this;
            this.$http.get("/common/chinese/" + value).then(function(response){
                // upper = response.data;
            },function(error){
                _self.$Message.error(error.data.errorMessage);
            });
        },
        /**
         * 小写转大写
         * @param n
         * @returns {string}
         */
        smallToBig : function (n) {
            var fraction = ['角', '分'];
            var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
            var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];
            var head = n < 0 ? '欠': '';
            n = Math.abs(n);

            var s = '';

            for (var i = 0; i < fraction.length; i++) {
                s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
            }
            s = s || '整';
            n = Math.floor(n);

            for (var i = 0; i < unit[0].length && n > 0; i++) {
                var p = '';
                for (var j = 0; j < unit[1].length && n > 0; j++) {
                    p = digit[n % 10] + unit[1][j] + p;
                    n = Math.floor(n / 10);
                }
                s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;
            }
            return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
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
                type : 'add',
                category : 6
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
            console.log(this.fileList)
            for (let index in this.fileList) {
                let item = {
                    attachName : this.fileList[index].name,
                    fileId : this.fileList[index].response.data.fileId,
                    attachType : 2
                };
                this.addForm.attachList.push(item);
            }
            console.log(this.addForm.attachList)
            return true;
        },
        remove: function (array, val) {
            let index = array.indexOf(val);
            if (index > -1) {
                array.splice(index, 1);
            }
        },
        /**
         * 下载文件
         */
        downloadItem:function(item){
            window.open("/file?fileId=" + item.fileId+"&fileName="+item.attachName);
        }
    }
});