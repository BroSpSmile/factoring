/**
 * 菜单信息
 */
common.pageName = "financeManage";
common.openName = ['financeManage'];

var vue = new Vue({
    el: '#financeOperation',
    data: {
        projectUrl: 'fundFinanceManage',
        project: {
            detail: {
                returnInstallments: [],
                loanInstallments: [],
                factoringInstallments: []
            },
        },
        statusItems: [],
        steps: [],
        banks: [],
        models: [],
        queryParam: {
            condition: {},
            pageNum: 1,
            pageSize: 10
        },
        formInline: {
            id: -1,
            projectId: null,
            projectName: null,
            person: null,
            progress: null
        },
        totalLoanAmount: 0,
        loanInstallmentFileList: [],
        returnInstallmentFileList: [],
        factoringInstallmentFileListPayment: [],
        factoringInstallmentFileListInvoice: [],
        factoringInstallmentsPayment: [],
        factoringInstallmentsInvoice: [],
        installmentId: 0,
        indexView: 0,
        deleteFileList: [],

    },
    created: function () {
        this.formInline.id = document.getElementById("projectId").value;
        this.queryParam.condition = this.formInline;
        this.initDate();
        this.queryProject();
    },
    filters:{
    	toMoney:function(value){
    		return common.money.formatter(value);
    	}
    },
    methods: {
        addInstallment: function () {
            this.project.loanInstallments.push({
                amount: 0,
                installmentDate: new Date(),
                item: null
            });
        },
        addInstallmentDetail: function (index, id, installmentDetailType) {
            if ('PAYMENT' == installmentDetailType) {
                let curFactoringInstallmentPayment = this.factoringInstallmentsPayment[index];
                let insertIndex = 0;
                for (let index1 in this.factoringInstallmentsPayment) {
                    if (index1 < index) {
                        continue;
                    }
                    if (curFactoringInstallmentPayment.id != this.factoringInstallmentsPayment[index1].id) {
                        insertIndex = index1 - 1;
                        break;
                    }
                    if (index1 == this.factoringInstallmentsPayment.length - 1) {
                        insertIndex = this.factoringInstallmentsPayment.length - 1;
                    }
                }

                this.factoringInstallmentsPayment.splice(insertIndex + 1, 0, {
                    id: id,
                    amount: -1,
                    installmentDate: '',
                    type: installmentDetailType,
                    bankInfoId: '',
                    detailAmount: 0,
                    detailDate: '',
                    item: null,
                });
            } else if ('INVOICE' == installmentDetailType) {
                let curFactoringInstallmentInvoice = this.factoringInstallmentsInvoice[index];
                let insertIndex = 0;
                for (let index1 in this.factoringInstallmentsInvoice) {
                    if (index1 < index) {
                        continue;
                    }
                    if (curFactoringInstallmentInvoice.id != this.factoringInstallmentsInvoice[index1].id) {
                        insertIndex = index1 - 1;
                        break;
                    }
                    if (index1 == this.factoringInstallmentsInvoice.length - 1) {
                        insertIndex = this.factoringInstallmentsInvoice.length - 1;
                    }
                }
                this.factoringInstallmentsInvoice.splice(insertIndex + 1, 0, {
                    id: id,
                    amount: -1,
                    installmentDate: '',
                    type: installmentDetailType,
                    bankInfoId: '',
                    detailAmount: 0,
                    detailDate: '',
                    item: null,
                });
            }

        },
        deleteInstallment: function (type, index, installmentDetailType, installment) {
            if (index > -1) {
                    this.deleteFile(installment.item, index, type);
                    this.project.loanInstallments.splice(index, 1);
            }
        },
        deleteSyn: function (installment) {
            for (let i = this.factoringInstallmentsInvoice.length - 1; i >= 0; i--) {
                let curInstallment = this.factoringInstallmentsInvoice[i];
                if (curInstallment.id == installment.id) {
                    this.deleteFile(this.factoringInstallmentsInvoice[i].item, i, 'factoring', 'INVOICE');
                    this.factoringInstallmentsInvoice.splice(i, 1);
                }
            }
            for (let i = this.factoringInstallmentsPayment.length - 1; i >= 0; i--) {
                let curInstallment = this.factoringInstallmentsPayment[i];
                if (curInstallment.id == installment.id) {
                    this.deleteFile(this.factoringInstallmentsPayment[i].item, i, 'factoring', 'PAYMENT');
                    this.factoringInstallmentsPayment.splice(i, 1);
                }
            }
        },
        uploadSuccessLoan: function (response, file, fileList) {
            this.loanInstallmentFileList.push(file);
            let id = response.data.id;
            for (let index in fileList) {
                let loanInstallment = this.project.loanInstallments[id];
                let item = {
                    installmentId: loanInstallment.id,
                    //itemType 未使用
                    itemType: "",
                    itemName: fileList[index].name,
                    itemValue: fileList[index].response.data.fileId
                };
                loanInstallment.item = item;
            }
        },
        synInstallment: function (installment, type) {
            let payment = null;
            for (let i in this.factoringInstallmentsPayment) {
                if (installment.id == this.factoringInstallmentsPayment [i].id && this.factoringInstallmentsPayment [i].amount != -1) {
                    payment = this.factoringInstallmentsPayment [i];
                    break;
                }
            }
            let invoice = null;
            for (let i in this.factoringInstallmentsInvoice) {
                if (installment.id == this.factoringInstallmentsInvoice [i].id && this.factoringInstallmentsInvoice [i].amount != -1) {
                    invoice = this.factoringInstallmentsInvoice [i];
                    break;
                }
            }
            if ('PAYMENT' == type) {
                invoice.amount = payment.amount;
                invoice.installmentDate = payment.installmentDate;
            } else if ('INVOICE' == type) {
                payment.amount = invoice.amount;
                payment.installmentDate = invoice.installmentDate;
            }

        },
        uploadSuccessFactoringPayment: function (response, file, fileList) {
            this.factoringInstallmentFileListPayment.push(file);
            let id = response.data.id;
            for (let index in fileList) {
                let factoringInstallmentPayment = this.factoringInstallmentsPayment[id];
                let item = {
                    installmentId: factoringInstallmentPayment.id,
                    //itemType 未使用
                    itemType: "",
                    itemName: fileList[index].name,
                    itemValue: fileList[index].response.data.fileId
                };
                factoringInstallmentPayment.item = item;
            }
        },
        uploadSuccessFactoringInvoice: function (response, file, fileList) {
            this.factoringInstallmentFileListInvoice.push(file);
            let id = response.data.id;
            for (let index in fileList) {
                let factoringInstallmentInvoice = this.factoringInstallmentsInvoice[id];
                let item = {
                    installmentId: factoringInstallmentInvoice.id,
                    //itemType 未使用
                    itemType: "",
                    itemName: fileList[index].name,
                    itemValue: fileList[index].response.data.fileId
                };
                factoringInstallmentInvoice.item = item;
            }
        },

        /**
         * 删除警告
         */
        deleteFileWarn: function (item, index, type, installmentDetailType) {
            this.$Modal.confirm({
                title: '删除提示',
                content: '<p>确认删除已上传文件</p>',
                onOk: () => {
                    this.deleteFile(item, index, type, installmentDetailType);
                },
                onCancel: () => {
                }
            })
        },
        deleteFile: function (item, index, type, installmentDetailType) {
            let self = this;
            if (item == null || null == item.itemValue) {
                return;
            }
            let fileId = item.itemValue;
            if (null != item.id) {
                this.deleteFileList.push(fileId);
                this.deleteInstallmentFile(fileId, index, type, installmentDetailType);
            } else {
                this.$http.delete("/file/" + fileId).then(function (response) {
                    if (response.data.success) {
                        self.$Message.info("删除成功");
                        this.deleteInstallmentFile(fileId, index, type, installmentDetailType);
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    self.$Message.error(error.data.errorMessage);
                });
            }
        },
        deleteInstallmentFile: function (fileId, index, type, installmentDetailType) {
            this.project.loanInstallments[index].item = null;
            for (let i in this.loanInstallmentFileList) {
                if (fileId == this.loanInstallmentFileList[i].response.data.fileId) {
                    if (i > -1) {
                        this.loanInstallmentFileList.splice(i, 1);
                    }
                }
            }
        },

        download: function (fileId, fileName) {
            //console.debug(fileId);
            window.open("/file?fileId=" + fileId + "&fileName=" + fileName);
        },

        /**
         * 保存放款信息
         * @param type
         */
        saveInstallment: function (type) {
            let self = this;
            for (let i in this.deleteFileList) {
                this.$http.delete("/file/" + this.deleteFileList[i]).then(function (response) {
                    if (response.data.success) {
                        //self.$Message.info("删除成功");
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    self.$Message.error(error.data.errorMessage);
                });
            }
            this.removeAllFile(this.factoringInstallmentFileListPayment);
            this.removeAllFile(this.factoringInstallmentFileListInvoice);
            this.removeAllFile(this.returnInstallmentFileList);
            console.log(this.project);
            this.$http.post("/fundFinanceOperate/save", this.project).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "保存成功",
                        onClose: function () {
                            window.close();
                        },
                    });
                    //window.location.href = this.projectUrl;
                    window.open(this.projectUrl, "_self");
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error);
            })
        },

        /**
         * 初始化数据
         */
        initDate: function () {
            let _self = this;
            this.$http.get("/combo/progress").then(function (response) {
                _self.statusItems = response.data;
            }, function (error) {
                console.error(error);
            });
            this.$http.get("/combo/projectModel").then(function (response) {
                _self.models = response.data;
            }, function (error) {
                console.error(error);
            });
            this.$http.get("/combo/steps").then(function (response) {
                _self.steps = response.data;
            }, function (error) {
                console.error(error);
            });

            this.$http.get("/combo/banks").then(function (response) {
                _self.banks = response.data;
            }, function (error) {
                console.error(error);
            });
        },

        /** 分页查询 */
        queryProject: function () {
            let self = this;
            this.$http.get("/fund/"+this.formInline.id).then(
                function (response) {
                	let detail = response.data;
                	self.project = detail;
                },
                function (error) {
                    self.$Message.error(error.data.message);
                }
            )
        },

        /**
         * 翻译
         */
        toModelName: function (value) {
            for (let index in this.models) {
                if (this.models[index].value == value) {
                    return this.models[index].text;
                }
            }
            return "";
        },

        /**
         * 翻译
         */
        toStepName: function (value) {
            for (let index in this.steps) {
                if (this.steps[index].value == value) {
                    return this.steps[index].text;
                }
            }
            return "";
        },

        toBoolean: function (value) {
            if (value) {
                return "是";
            }
            return "否";
        },

        /**
         * 状态翻译
         */
        getProgress: function (value) {
            for (var index in this.statusItems) {
                if (value == this.statusItems[index].value) {
                    return this.statusItems[index].text;
                }
            }
            return "";
        },


        /**
         * --公用方法
         */
        removeAllFile: function (fileList) {
            let self = this;
            for (let index in this.fileList) {
                let fileId = this.fileList[index].response.data.fileId;
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

        /**
         * 文件上传失败--公用方法
         */
        uploadError: function (error, file, fileList) {
            console.log(error);
        },

        /**
         * 页面已隐藏，用不到--公用方法
         * @param file
         * @param fileList
         */
        removeFile: function (file, fileList) {
            console.log(file);
            let fileId = file.response.data.fileId;
            let self = this;
            this.$http.delete("/file/" + fileId).then(function (response) {
                if (response.data.success) {
                    //self.$Message.info("删除成功");
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error.data.errorMessage);
            })
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

        /**
         * 公用方法
         */
        cancel: function () {
            this.removeAllFile(this.loanInstallmentFileList);
            this.removeAllFile(this.factoringInstallmentFileListInvoice);
            this.removeAllFile(this.factoringInstallmentFileListPayment);
            this.removeAllFile(this.returnInstallmentFileList);
            window.open(this.projectUrl, "_self");
            //window.location.href = this.projectUrl;
        }
    }
});

