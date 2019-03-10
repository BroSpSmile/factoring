/**
 * 菜单信息
 */
common.pageName = ("0" == document.getElementById("type").value ? 'filingProject' : "filingProjectAudit");
common.openName = ['5'];

var vue = new Vue({
    el: '#auditDetail',
    data: {
        filingInfo: {
            applyType: '归档申请',
            applicant: '',
            applyTime: '',
            project: 0,
            filingList: [],
            progress: '',
            items: [],
            record: {
                result: 'PASS',
                remark: '',
            },
        },
        user: {},
        rejectModal: false,
        rejectForm: {
            progress: '',
            reason: '',
        },
        allProgresses: [],
        progresses: [],
        isLastAuditStep: false,
        isView: false,
        step: 1,
        model11: '',
        content: ['', '', '', ''],
        steps: [],
        projectUrl: 'filingProject',
        tableColumns: [],
        records: [],
        audit: {},
        auditResult: [],
    },
    created: function () {
        this.filingInfo.project = document.getElementById("projectId").value;
        this.projectUrl = this.projectUrl + '?type=' + document.getElementById("type").value;

        let isView = document.getElementById("isView").value;
        if (isView && isView == 'true') {
            this.isView = true;
        }
        this.initDate();

    },
    methods: {
        /**
         * 初始化数据
         */
        initDate: function () {
            let _self = this;
            this.$http.get("/filingAudit/" + this.filingInfo.project).then(function (response) {
                if (response.data.success) {
                    if (null != response.data.data && 'null' != response.data.data) {
                        _self.filingInfo = response.data.data;
                        _self.filingInfo.applyType = '归档申请';
                        _self.getCurUser();
                    }
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                console.error(error);
            });
            this.$http.get("/combo/auditResult").then(function (response) {
                _self.auditResult = response.data;
            }, function (error) {
                console.error(error);
            })
        },

        /**
         * 初始化数据
         */
        getCurUser: function () {
            var _self = this;
            this.$http.get("/filingAudit/user").then(function (response) {
                if (response.data.success) {
                    if (null != response.data.data && 'null' != response.data.data) {
                        _self.user = response.data.data;
                        _self.setAllProgresses();
                    }
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                console.error(error);
            })
        },

        /**
         * 设置步骤
         */
        setAllProgresses: function () {
            let _self = this;
            let progresses = [];
            this.$http.get("/combo/filingSubProgress").then(function (response) {
                _self.allProgresses = response.data;

                //审批信息
                _self.audit = _self.filingInfo.audit;
                this.step = _self.audit.step;

                //审核历史
                //let records = _self.audit.records;
                //_self.records = _self.audit.records;

                //审核流程状态
                let flows = _self.audit.flows;
                //_self.flows = _self.audit.flows;

                //去除第一个待归档状态
                _self.allProgresses.shift();
                if (this.step == -1) {
                    this.step = this.allProgresses.length
                }
                for (let i = 0; i < this.allProgresses.length; i++) {
                    let stepObj = new Object();
                    if (i == 0) {
                        stepObj.content = _self.filingInfo.applicant + ' ' + _self.filingInfo.applyTime;
                    } else if (this.step == i) {
                        stepObj.content = _self.user.username;
                    } else {
                        stepObj.content = '';
                    }
                    stepObj.title = this.allProgresses[i].text;
                    _self.steps.push(stepObj);
                }

                //总计的步骤数-2 为倒数第二个元素的index
                if (this.step == _self.allProgresses.length - 2) {
                    this.isLastAuditStep = true;
                }

                for (let i = 0; i < this.step; i++) {
                    let flow = flows[i];
                    if (flow && i != 0) {
                        _self.steps[i].content = flow.user == null ? flow.role.roleName : flow.user.username
                    }

                }


                _self.setProgresses();
            }, function (error) {
                console.error(error);
            })
        },

        /**
         * 设置步骤
         */
        setProgresses: function () {
            //this.progresses.concat(this.allProgresses);
            for (let k in this.allProgresses) {
                this.progresses.push(this.allProgresses[k]);
            }
            let length = this.progresses.length;
            for (let i = length; i > this.step; i--) {
                this.progresses.pop();
            }
            this.progresses.reverse();
        },
        auditResultText: function (value) {
            for (let index in this.auditResult) {
                if (value == this.auditResult[index].value) {
                    return this.auditResult[index].text;
                }
            }
            return "";
        },
        download: function (fileId, fileName) {
            //TODO
            console.debug(fileId);
            window.open("/file?fileId=" + fileId + "&fileName=" + fileName);
        },
        /**
         * 判断数组是否包含元素
         */
        isItemExist: function (item) {
            let _self = this;
            return _self.filingInfo.filingList.join(',').indexOf(item) != -1;
        },
        /**
         * 审核通过
         */
        auditPass: function () {
            let _self = this;

            this.$http.post("/filingAudit/audit", _self.filingInfo).then(function (response) {
                if (response.data.success) {
                    _self.$Message.info({
                        content: "提交成功",
                        onClose: function () {
                            window.close();
                        },
                    });
                    window.open(this.projectUrl, "_self");
                } else {
                    _self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                _self.$Message.error(error);
            })
        },

        /**
         * 审核驳回弹框
         */
        auditRejectModel: function () {
            let _self = this;
            _self.rejectModal = true;
            this.rejectForm = {
                progress: '',
                reason: '',
            };
        },
        /**
         * 审核驳回 取消按钮
         */
        auditRejectModelCancel: function () {
            this.rejectModal = false;
            this.rejectForm = {
                progress: '',
                reason: '',
            };
        },

        /**
         * 审核驳回
         */
        auditReject: function () {
            let _self = this;
            _self.filingInfo.progress = _self.rejectForm.progress;
            _self.filingInfo.record.remark = _self.rejectForm.reason;
            _self.filingInfo.record.result = 'REJECTED';

            _self.$http.post("/filingAudit/reject", _self.filingInfo).then(function (response) {
                if (response.data.success) {
                    _self.$Message.info({
                        content: "归档申请驳回成功",
                        onClose: function () {
                            window.close();
                        },
                    });
                    window.open(this.projectUrl, "_self");
                } else {
                    _self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                _self.$Message.error(error);
            })
        },
        /**
         * 归档完成
         */
        filingComplete: function () {
            let self = this;
            self.filingInfo.record.result = 'COMPLETE';
            this.$http.post("/filingAudit/complete", this.filingInfo).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档完成",
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

        cancel: function () {
            window.open(this.projectUrl, "_self");
        },

    }
});
vue.tableColumns = [
    {
        title: '操作类型',
        key: 'type',
        align: 'center'
    }, {
        title: '操作人',
        key: 'auditor.username',
        align: 'center',
        render: (h, param) => {
            return h('span', param.row.auditor.username)
        }
    }, {
        title: '执行状态',
        key: 'result',
        align: 'center',
        render: (h, param) => {
            return h('span', vue.auditResultText(param.row.result))
        }
    }, {
        title: '操作时间',
        key: 'auditTime',
        align: 'center',
        render: (h, param) => {
            return h('span', moment(param.row.auditTime).format('YYYY-MM-DD HH:mm:ss'))
        }
    }, {
        title: '备注',
        key: 'remark',
        align: 'center'
    }, {
        title: '执行结果',
        key: 'status',
        align: 'center'
    }
];