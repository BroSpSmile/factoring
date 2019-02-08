/**
 * 菜单信息
 */
common.pageName = "auditDetail";
common.openName = ['5'];

var vue = new Vue({
    el: '#auditDetail',
    data: {
        filingInfo: {
            applyType:'归档申请',
            applicant:'陈杰',
            applyTime:'2018-10-23 17:44:00',
            projectId: 0,
            filingList: [1,2,3,4,5,8],
            items:[]
        },
    },
    created: function () {
        this.filingInfo.project.id = document.getElementById("projectId").value;
    },
    methods: {
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
        },

        /**
         * 审核驳回
         */
        auditReject: function () {
        },

        /**
         * 归档完成
         */
        filingComplete: function () {
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
        save: function () {

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
            t
        }
    }
});
