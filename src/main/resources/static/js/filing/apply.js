/**
 * 菜单信息
 */
common.pageName = "filingApply";
common.openName = ['5'];

var vue = new Vue({
    el: '#filingApply',
    data: {
        formInline: {
            type: []
        },
        filingInfo: {
            applyType: '归档申请',
            applicant: '陈杰',
            applyTime: null,
            projectId: 0,
            filingList: [],
            items: []
        },
        fileList: []
    },
    created: function () {
        this.filingInfo.projectId = document.getElementById("projectId").value;
        this.initDate();
    },
    methods: {
        /**
         * 初始化数据
         */
        initDate: function () {
            var _self = this;
            this.$http.get("/filingApply/"+ this.filingInfo.projectId).then(function (response) {
                if (null != response.data && 'null' != response.data) {
                    _self.filingInfo = response.data;
                }
            }, function (error) {
                console.error(error);
            })
        },

        /**
         * 文件上传成功
         */
        uploadSuccess: function (response, file, fileList) {
            this.fileList = fileList;
            console.log(response);
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
        removeAllFile: function () {
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
        save: function () {
            this.genFilingInfo();
            let self = this;
            this.$http.post("/filingApply/save", this.filingInfo).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档申请保存成功",
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
        commit: function () {
            this.genFilingInfo();
            let self = this;
            this.$http.post("/filingApply/commit", this.filingInfo).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档申请提交成功",
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
        genFilingInfo: function () {
            if (this.fileList === undefined || this.fileList.length == 0) {
                this.$Message.error("请上传尽调文件");
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
        cancel: function () {
            this.removeAllFile();
            window.open("filingProject", "_self");
        },
    }
});
