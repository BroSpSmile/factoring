/**
 * 菜单信息
 */
common.pageName = "financeManage";
common.openName = ['financeManage'];

var vue = new Vue({
    el: '#financeOperation',
    data: {
        projectUrl: 'financeManage',
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

    },
    created: function () {
        this.formInline.id = document.getElementById("projectId").value;
        this.queryParam.condition = this.formInline;
        this.initDate();
        this.queryProject();
    },
    methods: {
        addInstallment: function (type) {
            if (type == 'return') {
                this.project.detail.returnInstallments.push({
                    amount: 0,
                    installmentDate: '',
                    item: null
                });
            } else if (type == 'loan') {
                this.project.detail.loanInstallments.push({
                    amount: 0,
                    installmentDate: '',
                    item: null
                });
            } else if (type == 'factoring') {
                this.installmentId--;
                this.indexView++;
                this.factoringInstallmentsPayment.push({
                    indexView: this.indexView,
                    id: this.installmentId,
                    amount: 0,
                    installmentDate: '',
                    type: 'PAYMENT',
                    bankInfoId: '',
                    detailAmount: 0,
                    detailDate: '',
                    item: null,
                });
                this.factoringInstallmentsInvoice.push({
                    indexView: this.indexView,
                    id: this.installmentId,
                    amount: 0,
                    installmentDate: '',
                    type: 'INVOICE',
                    bankInfoId: '',
                    detailAmount: 0,
                    detailDate: '',
                    item: null,
                });
            }
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
                if (type == 'return') {
                    if (null != this.project.detail.returnInstallments[index].item) {
                        this.deleteFile(this.project.detail.returnInstallments[index].item.itemValue, index, type);
                    }
                    this.project.detail.returnInstallments.splice(index, 1);
                } else if (type == 'loan') {
                    if (null != this.project.detail.loanInstallments[index].item) {
                        this.deleteFile(this.project.detail.loanInstallments[index].item.itemValue, index, type);
                    }
                    this.project.detail.loanInstallments.splice(index, 1);
                } else if (type == 'factoring') {
                    if ('PAYMENT' == installmentDetailType) {
                        if (installment.amount != -1) {
                            this.deleteSyn(installment);
                        } else {
                            if (null != this.factoringInstallmentsPayment[index].item) {
                                this.deleteFile(this.factoringInstallmentsPayment[index].item.itemValue, index, type);
                            }
                            this.factoringInstallmentsPayment.splice(index, 1);
                        }
                    } else if ('INVOICE' == installmentDetailType) {
                        if (installment.amount != -1) {
                            this.deleteSyn(installment);
                        } else {
                            if (null != this.factoringInstallmentsInvoice[index].item) {
                                this.deleteFile(this.factoringInstallmentsInvoice[index].item.itemValue, index, type);
                            }

                            this.factoringInstallmentsInvoice.splice(index, 1);
                        }
                    }
                }
            }
        },
        deleteSyn: function (installment) {
            for (let i = this.factoringInstallmentsInvoice.length - 1; i >= 0; i--) {
                let curInstallment = this.factoringInstallmentsInvoice[i];
                if (curInstallment.id == installment.id) {
                    if (null != this.factoringInstallmentsInvoice[i].item) {
                        this.deleteFile(this.factoringInstallmentsInvoice[i].item.itemValue, index, type);
                    }
                    this.factoringInstallmentsInvoice.splice(i, 1);
                }
            }
            for (let i = this.factoringInstallmentsPayment.length - 1; i >= 0; i--) {
                let curInstallment = this.factoringInstallmentsPayment[i];
                if (curInstallment.id == installment.id) {
                    if (null != this.factoringInstallmentsPayment[i].item) {
                        this.deleteFile(this.factoringInstallmentsPayment[i].item.itemValue, index, type);
                    }
                    this.factoringInstallmentsPayment.splice(i, 1);
                }
            }
        },
        uploadSuccessLoan: function (response, file, fileList) {
            this.loanInstallmentFileList.push(file);
            let id = response.data.id;
            for (let index in fileList) {
                let loanInstallment = this.project.detail.loanInstallments[id];
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
        uploadSuccessReturn: function (response, file, fileList) {
            this.returnInstallmentFileList.push(file);
            let id = response.data.id;
            for (let index in fileList) {
                let returnInstallment = this.project.detail.returnInstallments[id];
                let item = {
                    installmentId: returnInstallment.id,
                    //itemType 未使用
                    itemType: "",
                    itemName: fileList[index].name,
                    itemValue: fileList[index].response.data.fileId
                };
                returnInstallment.item = item;
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
        deleteFile: function (fileId, index, type, installmentDetailType) {
            let self = this;
            if (null == fileId) {
                return;
            }
            if (type == 'return') {
                this.$http.delete("/file/" + fileId).then(function (response) {
                    if (response.data.success) {
                        self.$Message.info("删除成功");
                        let returnInstallment = this.project.detail.returnInstallments[index];
                        returnInstallment.item = null;
                        for (let index in this.returnInstallmentFileList) {
                            if (fileId == this.returnInstallmentFileList[index].response.data.fileId) {
                                if (index > -1) {
                                    this.returnInstallmentFileList.splice(index, 1);
                                }
                            }
                        }
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    self.$Message.error(error.data.errorMessage);
                })
            } else if (type == 'loan') {
                this.$http.delete("/file/" + fileId).then(function (response) {
                    if (response.data.success) {
                        self.$Message.info("删除成功");
                        let loanInstallment = this.project.detail.loanInstallments[index];
                        loanInstallment.item = null;
                        for (let index in this.loanInstallmentFileList) {
                            if (fileId == this.loanInstallmentFileList[index].response.data.fileId) {
                                if (index > -1) {
                                    this.loanInstallmentFileList.splice(index, 1);
                                }
                            }
                        }
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    self.$Message.error(error.data.errorMessage);
                })
            } else if (type == 'factoring') {
                if ('PAYMENT' == installmentDetailType) {
                    this.$http.delete("/file/" + fileId).then(function (response) {
                        if (response.data.success) {
                            self.$Message.info("删除成功");
                            let factoringInstallmentPayment = this.factoringInstallmentsPayment[index];
                            factoringInstallmentPayment.item = null;
                            for (let index in this.factoringInstallmentFileListPayment) {
                                if (fileId == this.factoringInstallmentFileListPayment[index].response.data.fileId) {
                                    if (index > -1) {
                                        this.factoringInstallmentFileListPayment.splice(index, 1);
                                    }
                                }
                            }
                        } else {
                            self.$Message.error(response.data.errorMessage);
                        }
                    }, function (error) {
                        self.$Message.error(error.data.errorMessage);
                    })
                } else if ('INVOICE' == installmentDetailType) {
                    this.$http.delete("/file/" + fileId).then(function (response) {
                        if (response.data.success) {
                            self.$Message.info("删除成功");
                            let factoringInstallmentInvoice = this.factoringInstallmentsInvoice[index];
                            factoringInstallmentInvoice.item = null;
                            for (let index in this.factoringInstallmentFileListInvoice) {
                                if (fileId == this.factoringInstallmentFileListInvoice[index].response.data.fileId) {
                                    if (index > -1) {
                                        this.factoringInstallmentFileListPayment.splice(index, 1);
                                    }
                                }
                            }
                        } else {
                            self.$Message.error(response.data.errorMessage);
                        }
                    }, function (error) {
                        self.$Message.error(error.data.errorMessage);
                    })
                }
            }
        },
        download: function (fileId, fileName) {
            //TODO
            console.debug(fileId);
            window.open("/file?fileId=" + fileId + "&fileName=" + fileName);
        },
        saveInstallment: function (type) {
            let self = this;
            if (type == 'return') {
                this.removeAllFile(this.factoringInstallmentFileListPayment);
                this.removeAllFile(this.factoringInstallmentFileListInvoice);
                this.removeAllFile(this.loanInstallmentFileList);
                let url = "/financeOperation/saveReturnInstallment";
                this.doPostSave(url);
            } else if (type == 'loan') {
                this.removeAllFile(this.factoringInstallmentFileListPayment);
                this.removeAllFile(this.factoringInstallmentFileListInvoice);
                this.removeAllFile(this.returnInstallmentFileList);
                let url = "/financeOperation/saveLoanInstallment";
                this.doPostSave(url);
            } else if (type == 'factoring') {
                this.removeAllFile(this.loanInstallmentFileList);
                this.removeAllFile(this.returnInstallmentFileList);
                this.project.detail.factoringInstallments = [];
                for (let index in this.factoringInstallmentsPayment) {
                    let factoringInstallmentForView = this.factoringInstallmentsPayment[index];
                    if (factoringInstallmentForView.amount != -1) {
                        this.project.detail.factoringInstallments.push({
                            id: factoringInstallmentForView.id,
                            amount: factoringInstallmentForView.amount,
                            installmentDate: factoringInstallmentForView.installmentDate,
                            detailList: [
                                {
                                    id: factoringInstallmentForView.detailId,
                                    installmentId: factoringInstallmentForView.id,
                                    type: factoringInstallmentForView.type,
                                    bankInfoId: factoringInstallmentForView.bankInfoId,
                                    detailAmount: factoringInstallmentForView.detailAmount,
                                    detailDate: factoringInstallmentForView.detailDate,
                                    item: factoringInstallmentForView.item,
                                }
                            ]
                        });
                    } else {
                        for (let index2 in this.project.detail.factoringInstallments) {
                            if (this.project.detail.factoringInstallments[index2].id == factoringInstallmentForView.id) {
                                this.project.detail.factoringInstallments[index2].detailList.push({
                                    id: factoringInstallmentForView.detailId,
                                    installmentId: factoringInstallmentForView.id,
                                    type: factoringInstallmentForView.type,
                                    bankInfoId: factoringInstallmentForView.bankInfoId,
                                    detailAmount: factoringInstallmentForView.detailAmount,
                                    detailDate: factoringInstallmentForView.detailDate,
                                    item: factoringInstallmentForView.item,
                                });
                                break;
                            }
                        }
                    }
                }
                for (let index in this.factoringInstallmentsInvoice) {
                    let factoringInstallmentForView = this.factoringInstallmentsInvoice[index];

                    //已经存在
                    for (let index2 in this.project.detail.factoringInstallments) {
                        if (this.project.detail.factoringInstallments[index2].id == factoringInstallmentForView.id) {
                            this.project.detail.factoringInstallments[index2].detailList.push({
                                id: factoringInstallmentForView.detailId,
                                type: factoringInstallmentForView.type,
                                bankInfoId: factoringInstallmentForView.bankInfoId,
                                detailAmount: factoringInstallmentForView.detailAmount,
                                detailDate: factoringInstallmentForView.detailDate,
                                item: factoringInstallmentForView.item,
                            });
                            break;
                        }

                    }
                }
                let url = "/financeOperation/saveFactoringInstallment";
                this.doPostSave(url);
            }
        },
        doPostSave: function (url) {
            let self = this;
            this.$http.post(url, this.project).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "保存成功",
                        onClose: function () {
                            window.close();
                        },
                    });
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
            self.queryParam.condition = self.formInline;
            this.$http.post("/project/query", self.queryParam).then(
                function (response) {
                    let data = response.data;
                    self.project = data.list[0];
                    self.project.detail.loanInstallments.forEach(cur => {
                        self.totalLoanAmount += cur.amount;
                    });

                    self.factoringInstallmentsPayment = [];
                    self.factoringInstallmentsInvoice = [];
                    self.indexView = self.project.detail.factoringInstallments.length;
                    for (let index in self.project.detail.factoringInstallments) {
                        let factoringInstallment = self.project.detail.factoringInstallments[index];
                        let detailList = factoringInstallment.detailList;
                        let detailListPayment = [];
                        let detailListInvoice = [];
                        if (null != detailList) {
                            for (let i in detailList) {
                                if (detailList[i].type == 'PAYMENT') {
                                    detailListPayment.push(detailList[i]);
                                } else if (detailList[i].type == 'INVOICE') {
                                    detailListInvoice.push(detailList[i])
                                }
                            }
                        }
                        let indexView = parseInt(index) + 1;
                        if (detailListPayment.length > 0) {
                            for (let j in detailListPayment) {
                                this.factoringInstallmentsPayment.push({
                                    indexView: j == 0 ? indexView : null,
                                    id: factoringInstallment.id,
                                    amount: j == 0 ? factoringInstallment.amount : -1,
                                    installmentDate: factoringInstallment.installmentDate,
                                    type: 'PAYMENT',
                                    bankInfoId: detailListPayment[j].bankInfoId,
                                    detailAmount: detailListPayment[j].detailAmount,
                                    detailDate: detailListPayment[j].detailDate,
                                    item: detailListPayment[j].item,
                                    detailId: detailListPayment[j].id
                                });
                            }
                        } else {
                            this.factoringInstallmentsPayment.push({
                                indexView: indexView,
                                id: factoringInstallment.id,
                                amount: factoringInstallment.amount,
                                installmentDate: factoringInstallment.installmentDate,
                                type: 'PAYMENT',
                                bankInfoId: null,
                                detailAmount: 0,
                                detailDate: '',
                                item: null
                            });
                        }
                        if (detailListInvoice.length > 0) {
                            for (let k in detailListInvoice) {
                                this.factoringInstallmentsInvoice.push({
                                    indexView: k == 0 ? indexView : null,
                                    id: factoringInstallment.id,
                                    amount: k == 0 ? factoringInstallment.amount : -1,
                                    installmentDate: factoringInstallment.installmentDate,
                                    type: 'INVOICE',
                                    bankInfoId: detailListInvoice[k].bankInfoId,
                                    detailAmount: detailListInvoice[k].detailAmount,
                                    detailDate: detailListInvoice[k].detailDate,
                                    item: detailListInvoice[k].item,
                                    detailId: detailListInvoice[k].id
                                });
                            }
                        } else {
                            this.factoringInstallmentsInvoice.push({
                                indexView: indexView,
                                id: factoringInstallment.id,
                                amount: factoringInstallment.amount,
                                installmentDate: factoringInstallment.installmentDate,
                                type: 'INVOICE',
                                bankInfoId: null,
                                detailAmount: 0,
                                detailDate: '',
                                item: null
                            });
                        }

                    }
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
                    self.$Message.info("删除成功");

                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error.data.errorMessage);
            })
        },

        /**
         * 公用方法
         */
        cancel: function () {
            this.removeAllFile(this.loanInstallmentFileList);
            this.removeAllFile(this.factoringInstallmentFileList);
            this.removeAllFile(this.returnInstallmentFileList);
            window.open(this.projectUrl, "_self");
        }
    }
});
