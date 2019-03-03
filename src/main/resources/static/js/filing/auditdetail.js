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
            projectId: 0,
            filingList: [],
            progress: '',
            items: []
        },
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
    },
    created: function () {
        this.filingInfo.projectId = document.getElementById("projectId").value;
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
            var _self = this;
            this.$http.get("/filingApply/" + this.filingInfo.projectId).then(function (response) {
                if (response.data.success) {
                    if (null != response.data.data && 'null' != response.data.data) {
                        _self.filingInfo = response.data.data;
                        _self.filingInfo.applyType = '归档申请';
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
            this.$http.get("/combo/filingProgress").then(function (response) {
                _self.allProgresses = response.data;
                for (let i = 0; i < this.allProgresses.length; i++) {
                    let stepObj = new Object();
                    if (i == 0) {
                        stepObj.content = _self.filingInfo.applicant + ' ' + _self.filingInfo.applyTime;
                    } else {
                        stepObj.content = '';
                    }
                    stepObj.title = this.allProgresses[i].text;
                    _self.steps.push(stepObj);
                }
                _self.setProgresses();
                if (_self.filingInfo.progress == 'FILE') {
                    this.step = 1;
                    _self.steps[1].content = '高育良';
                } else if (_self.filingInfo.progress == 'FILEAUDIT') {
                    this.step = 2;
                    _self.steps[1].content = '高育良';
                    _self.steps[2].content = '丁义珍';
                    this.isLastAuditStep = true;
                } else if (_self.filingInfo.progress == 'FILECOMPLETE') {
                    _self.steps[1].content = '高育良';
                    _self.steps[2].content = '丁义珍';
                    this.step = 3;
                }
            }, function (error) {
                console.error(error);
            })
        },

        /**
         * 设置步骤
         */
        setProgresses: function () {
            let index = 0;
            //this.progresses.concat(this.allProgresses);
            for (let k in this.allProgresses) {
                this.progresses.push(this.allProgresses[k]);
            }
            for (let j in this.progresses) {
                if (this.progresses[j].value == this.filingInfo.progress) {
                    index = j;
                    break;
                }
            }
            let length = this.progresses.length
            for (let i = length; i > index; i--) {
                this.progresses.pop();
            }
            this.progresses.reverse();
        },

        download: function (fileId) {
            //TODO
            console.debug(fileId);
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
            let self = this;
            this.$http.post("/filingApply/audit", this.filingInfo).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "提交成功",
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
         * 审核驳回弹框
         */
        auditRejectModel: function () {
            this.rejectModal = true;
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
            let self = this;
            this.filingInfo.progress = this.rejectForm.progress;
            this.$http.post("/filingApply/reject", this.filingInfo).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档申请驳回成功",
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
         * 归档完成
         */
        filingComplete: function () {
            let self = this;
            this.$http.post("/filingApply/complete", this.filingInfo).then(function (response) {
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
        commit: function () {
            if (this.fileList === undefined || this.fileList.length == 0) {
                this.$Message.error("请上传尽调文件");
                return false;
            }
            for (let index in this.fileList) {
                let item = {
                    projectId: this.project.id,
                    itemType: "TUNEUP",
                    itemName: this.fileList[index].name,
                    itemValue: this.fileList[index].response.data.fileId
                }
                this.project.items.push(item);
            }
            console.log(this.project);
            let self = this;
            this.$http.post("/filingApply", this.project).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "立项申请成功",
                        onClose: function () {
                            window.close();
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error);
            })
        },
        genFilingInfo: function () {
            if (this.fileList === undefined || this.fileList.length == 0) {
                this.$Message.error("请上传归档文件");
                return false;
            }
            for (let index in this.fileList) {
                let item = {
                    projectId: this.filingInfo.projectId,
                    itemType: "TUNEUP",
                    itemName: this.fileList[index].name,
                    itemValue: this.fileList[index].response.data.fileId
                }
                this.filingInfo.items.push(item);
            }
            console.log(this.filingInfo);
        },
    }
});
