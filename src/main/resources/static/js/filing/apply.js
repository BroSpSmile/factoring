/**
 * 菜单信息
 */
common.pageName = ("0" == document.getElementById("type").value ? 'filingProject' : "filingProjectAudit");
common.openName = ['5'];

var vue = new Vue({
    el: '#filingApply',
    data: {
        formInline: {
            type: []
        },
        filingInfo: {
            applyType: '归档申请',
            applicant: '',
            applyTime: '',
            project: 0,
            filingList: [],
            progress: 'FILE_TOBE_APPLY',
            items: []
        },
        fileList: [],
        isInitFileRow: false,
        projectUrl: 'filingProject?type=0'
    },
    created: function () {
        this.filingInfo.project = document.getElementById("projectId").value;
        this.initDate();
    },
    methods: {
        /**
         * 初始化数据
         */
        initDate: function () {
            var _self = this;
            this.$http.get("/filingAudit/" + this.filingInfo.project).then(function (response) {
                if (response.data.success) {
                    if (null != response.data.data && 'null' != response.data.data) {
                        _self.filingInfo = response.data.data;
                        if (_self.filingInfo.items.length > 0) {
                            _self.isInitFileRow = true;
                        }
                    }
                } else {
                    self.$Message.error(response.data.errorMessage);
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
        deleteFile: function (fileId) {
            let self = this;
            this.$http.delete("/file/" + fileId).then(function (response) {
                if (response.data.success) {
                    self.$Message.info("删除成功");
                    for (let index in self.filingInfo.items) {
                        if (self.filingInfo.items[index].itemValue == fileId) {
                            let item = self.filingInfo.items[index];
                            this.remove(self.filingInfo.items, item);
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
            let result = this.genFilingInfo();
            if (!result) {
                return false;
            }
            let self = this;
            this.$http.post("/filingApply/save", this.filingInfo).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档申请保存成功",
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
        commit: function () {
            let result = this.genFilingInfo();
            if (!result) {
                return false;
            }
            let self = this;
            this.$http.post("/filingApply/commit", this.filingInfo).then(function (response) {
                if (response.data.success) {
                    self.$Message.info({
                        content: "归档申请提交成功",
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
        genFilingInfo: function () {
            if (this.fileList === undefined || this.fileList.length == 0) {
                if (null != this.filingInfo.items && this.filingInfo.items.length > 0) {
                    return true;
                }
                this.$Message.error("请上传归档文件");
                return false;
            }
            for (let index in this.fileList) {
                let item = {
                    project: this.filingInfo.project,
                    itemType: "FILE",
                    itemName: this.fileList[index].name,
                    itemValue: this.fileList[index].response.data.fileId
                };
                this.filingInfo.items.push(item);
            }
            console.log(this.filingInfo);
            return true;
        },
        cancel: function () {
            this.removeAllFile();
            window.open(this.projectUrl, "_self");
        },
        indexOf: function (array, val) {
            for (let i = 0; i < array.length; i++) {
                if (array[i] == val) return i;
            }
            return -1;
        },
        remove: function (array, val) {
            let index = array.indexOf(val);
            if (index > -1) {
                array.splice(index, 1);
            }
        }
    }
});
