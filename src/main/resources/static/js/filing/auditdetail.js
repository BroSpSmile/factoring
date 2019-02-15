/**
 * 菜单信息
 */
common.pageName = "auditDetail";
common.openName = ['5'];

var vue = new Vue({
    el: '#auditDetail',
    data: {
        filingInfo: {
            applyType: '归档申请',
            applicant: '陈杰',
            applyTime: '2018-10-23 17:44:00',
            projectId: 0,
            filingList: [1, 2, 3, 4, 5, 8],
            progress: '',
            items: []
        },
        rejectModal: false,
        rejectForm: {
            progress: '',
            reason: '',
        },
        progresses: [
            {
                value: 'FILEAUDIT',
                label: '法务风控'
            },
            {
                value: 'TOBEFILED',
                label: '归档申请人'
            }
        ],
        isLastAuditStep: false,
        isView: false,
        step: 1,
        model11: ''
    },
    created: function () {
        this.filingInfo.projectId = document.getElementById("projectId").value;
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
                        if (_self.filingInfo.progress == 'FILE') {
                            this.progresses = [
                                {
                                    value: 'TOBEFILED',
                                    label: '归档申请人'
                                }
                            ];
                            this.step = 1;
                        }
                        else if (_self.filingInfo.progress == 'FILEAUDIT') {
                            this.step = 2;
                            this.isLastAuditStep = true;
                        }
                        else if (_self.filingInfo.progress == 'FILECOMPLETE') {
                            this.step = 3;
                        }
                    }
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                console.error(error);
            })
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
                    window.open("filingProject", "_self");
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
                    window.open("filingProject", "_self");
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
                    window.open("filingProject", "_self");
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                self.$Message.error(error);
            })
        },

        cancel: function () {
            window.open("filingProject", "_self");
        },
        commit: function () {
            if (this.fileList === undefined || this.fileList.length == 0) {
                this.$Message.error("请上传尽调文件");
                return false;
            }
            for (let index in this.fileList) {
                let item = {
                    projectId: this.project.id,
                    itemType: "DUE_DILIGENCE",
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
                    itemType: "DUE_DILIGENCE",
                    itemName: this.fileList[index].name,
                    itemValue: this.fileList[index].response.data.fileId
                }
                this.filingInfo.items.push(item);
            }
            console.log(this.filingInfo);
        },
    }
});
