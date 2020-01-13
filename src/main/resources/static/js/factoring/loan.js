/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = ['1'];

var vue = new Vue({
    el: '#factoring',
    data: {
        detail: {
            project: {
                projectName: "",
                model: "RECOURSE_RIGHT"

            },
            assignee: 0,
            receivable: 0,
            dropAmount: 0,
            totalFactoringFee: 0,
            moneyBack: "false",
            invoicing: "false",
            paied: "false"
        },
        showLater: false,
        items: [],
        itemTypes: [],
        validata: {
            'project.projectId': [
                {required: true, message: '项目编号不能为空', trigger: 'blur'}
            ],
            'project.projectName': [
                {required: true, message: '项目名称不能为空', trigger: 'blur'}
            ],
            creditor: [
                {required: true, message: '让与人不能为空', trigger: 'blur'}
            ],
            debtor: [
                {required: true, message: '债务人不能为空', trigger: 'blur'}
            ],
            baseContract: [
                {required: true, message: '基础合同不能为空', trigger: 'blur'}
            ],
            assignee: [
                {required: true, message: '应收账款受让款不能为空'}
            ],
            receivable: [
                {required: true, message: '应收账款不能为空'}
            ],
            totalFactoringFee: [
                {required: true, type: 'number', message: '保理费不能为空'}
            ]
        }
    },
    created: function () {
        this.getProject();
        this.initItemTypes();
    },
    filters: {
        timeFilter: function (value) {
            if (!value) {
                return "";
            }
            return moment(value).format('YYYY-MM-DD HH:mm');
        },

        /**
         * 翻译
         */
        toTypeName: function (value) {
            for (let index in vue.itemTypes) {
                if (vue.itemTypes[index].value == value) {
                    return vue.itemTypes[index].text;
                }
            }
            return "";
        }
    },
    methods: {
        getProject: function () {
            let _self = this;
            let id = document.getElementById("projectId").value;
            this.items = [];
            if (id) {
                this.$http.get("/factoring/" + id).then(function (response) {
                    _self.detail = response.data;
                    let items = _self.detail.project.items;
                    for (let index in items) {
                        let has = false;
                        for (let j in _self.items) {
                            if (_self.items[j].key == items[index].itemType) {
                                _self.items[j].value.push(items[index]);
                                has = true;
                                break;
                            }
                        }
                        if (!has) {
                            let item = {key: items[index].itemType, value: []};
                            item.value.push(items[index]);
                            _self.items.push(item);
                        }
                    }
                    if (_self.detail.returnInstallments.length == 0) {
                        _self.detail.returnInstallments.push({
                            amount: 0,
                            type: "RETURN",
                            kind: "FACTORING"
                        });
                    }
                    console.log(_self.detail);
                }, function (error) {
                    console.error(error);
                })
            }
        },

        /**
         * 初始化附件类型
         */
        initItemTypes: function () {
            let _self = this;
            this.$http.get("/combo/itemTypes").then(function (response) {
                _self.itemTypes = response.data;
            }, function (error) {
                console.error(error);
            });
        },

        /** 添加保理费分期 */
        add: function () {
            this.detail.returnInstallments.push({
                amount: 0,
                type: "RETURN",
                kind: "FACTORING"
            });
        },

        /**
         * 移除
         */
        remove: function (index) {
            this.detail.returnInstallments.splice(index, 1);
        },


        /** 计算保理费分期 */
        computeFee: function (installment, index) {
            if (index == 0) {
                let day = this.difDay(installment.installmentDate, this.detail.loanDay)
                console.log(day);
                let rate = this.detail.dropAmount * ((day + 1) / 360) * this.detail.yearRate;
                console.log(rate);
                installment.amount = rate.toFixed(2);
            } else {
                let day = this.difDay(installment.installmentDate, this.detail.returnInstallments[index - 1].installmentDate);
                console.log(day);
                let rate = this.detail.dropAmount * (day / 360) * this.detail.yearRate;
                console.log(rate);
                installment.amount = rate.toFixed(2);
            }
        },

        difDay: function (date1, date2) {
            var diff = Math.abs(date1.getTime() - date2.getTime())
            var result = parseInt(diff / (1000 * 60 * 60 * 24));
            return result;
        },

        /**  */
        submit: function () {
            let _self = this;
            this.$refs['entityDataForm'].validate((valid) => {
                if (!valid) {
                    _self.$Message.error('校验失败,请完善信息!');
                    return false;
                } else {
                    _self.commit();
                }
            });
        },

        /**
         * 提交
         */
        commit: function () {
            let self = this;
            this.detail.project.items = [];
            for (let index in this.fileList) {
                let item = {
                    itemType: "LOAN",
                    itemName: this.fileList[index].name,
                    itemValue: this.fileList[index].response.data.fileId
                }
                this.detail.project.items.push(item);
            }
            this.$Spin.show();
            if (!this.detail.id) {
                this.$http.post("/factoring", this.detail).then(function (response) {
                    this.$Spin.hide();
                    if (response.data.success) {
                        self.$Message.info({
                            content: "保存成功",
                            onClose: function () {
                                self.getProject();
                            }
                        });
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    this.$Spin.hide();
                    self.$Message.error(error.data.message);
                });
            } else {
                this.$http.put("/factoring", this.detail).then(function (response) {
                    this.$Spin.hide();
                    if (response.data.success) {
                        self.$Message.info({
                            content: "保存成功",
                            onClose: function () {
                                self.getProject();
                            }
                        });
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function (error) {
                    this.$Spin.hide();
                    self.$Message.error(error.data.message);
                });
            }

        },

        /**
         * 提交申请
         */
        apply: function (step) {
            let self = this;
            this.$Spin.show();
            this.detail.project.step = step;
            this.$http.post("/factoringStatus", this.detail.project).then(function (response) {
                this.$Spin.hide();
                if (response.data.success) {
                    self.$Message.info({
                        content: "提交成功",
                        onClose: function () {
                            self.close();
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                this.$Spin.hide();
                self.$Message.error(error.data.message);
            });
        },

        /**
         * 转回
         */
        returnBack: function () {
            let self = this;
            this.$Spin.show();
            this.$http.post("/factoringStatus/back", this.detail.project).then(function (response) {
                this.$Spin.hide();
                if (response.data.success) {
                    self.$Message.info({
                        content: "提交成功",
                        onClose: function () {
                            self.close();
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                this.$Spin.hide();
                self.$Message.error(error.data.message);
            });
        },

        comRate: function () {
            this.detail.returnRate = ((this.detail.dropAmount / this.detail.assignee) * 100).toFixed(2);
        },

        /**
         * formatter
         */
        formatter: function (value) {
            value = value + '';
            //处理小数部分
            if (value.indexOf(".") != -1) {
                let values = value.split(".");
                let intSum = values[0].replace(/\B(?=(?:\d{3})+$)/g, ',');
                let floatSum = values[1];
                return '￥' + intSum +"."+ floatSum;
            } else {
                var intSum = value.replace(/\B(?=(?:\d{3})+$)/g, ',');
                return '￥' + intSum;
            }
        },

        /**
         * parser
         */
        parser: function (value) {
            value = "" + value;
            return value.replace(/￥s?|(,*)/g, '');
        },

        close: function () {
            //	console.log($(window.parent.document).contents().find("iframe:first").html));
            //$('[data-id="/project"]', parent.document).parent().attr('src', $('[data-id="/project"]').attr('src'));
        },

        /**
         * 下载文件
         */
        downloadItem: function (item) {
            window.open("/file?fileId=" + item.itemValue + "&fileName=" + encodeURI(item.itemName));
        },

        /**
         * 打开文件
         */
        openItem: function (item) {
            window.open(item.itemValue);
        },

        back: function () {
            window.location.href = "project?step=" + this.detail.project.step;
        },

        /**
         * 文件上传成功
         */
        uploadSuccess: function (response, file, fileList) {
            this.fileList = fileList;
        },

        /**
         * 文件上传失败
         */
        uploadError: function (error, file, fileList) {
            this.fileList = fileList;
        },

        removeFile: function (file, fileList) {
            this.fileList = fileList;
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

        deleteFile: function (item) {
            let self = this;
            this.$http.delete("/attch", item).then(function (response) {
                if (response.data.success) {
                    self.$Message.info("删除成功");
                    self.getProject();
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error.data.errorMessage);
            })
        }
    }
});
