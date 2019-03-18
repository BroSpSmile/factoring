/**
 * 合同签署
 */
common.pageName = "contractSign";
common.openName = [ '3' ];

var vue = new Vue({
    el : '#contractSign',
    data : {
        formInline:{
            type : [],
            status : 6
        },
        queryParam : {
            condition : {},
            pageNum : 1,
            pageSize : 10
        },
        addForm : {
            signList : []
        },
        pageInfo:{},
        tableColumns:[],
        showResult:false,
        modal1:false,
        projectList : []
    },
    created : function() {
        this.initData();
    },
    methods : {
        /**
         * 初始化数据
         */
        initData : function() {
            let projectId = document.getElementById("projectId").value;
            this.addForm.projectId = projectId;
            let self = this;
            this.$http.get("/contractSign/signList/" + projectId).then(function(response){
                if (response.data.success) {
                    self.addForm.signList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                console.error(error);
            });
        },
        /**
         * 保存签署信息
         */
        saveSign : function() {
            let self = this;

            //检查是否有选中
            let checked = false;
            for(index in self.addForm.signList) {
                let sign = self.addForm.signList[index];
                if(sign.status) {
                    checked = true;
                    break;
                }
            }
            if(!checked) {
                self.$Message.error('至少选择一项才能签署');
                return;
            }

            this.$http.post("/contractSign/save", this.addForm).then(function(response) {
                if (response.data.success) {
                    self.$Message.info({
                        content : "保存成功",
                        onClose : function() {
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
         * 签署完成
         */
        signFinish : function(serialNo) {
            let self = this;
            this.addForm.finished = true;

            // 校验所有必须项是否已完成
            for(index in self.addForm.signList) {
                let sign = self.addForm.signList[index];
                if(sign.isRequired === 1 && !sign.status) {
                    self.$Message.error('带红星项全部完成才可以签署完成');
                    return;
                }
            }

            this.$http.post("/contractSign/save", this.addForm).then(function(response) {
                if (response.data.success) {
                    self.$Message.info({
                        content : "保存成功",
                        onClose : function() {
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
         * 取消保存
         */
        cancel : function() {
            this.modal1 = false;
        },
        /**
         * 重置
         */
        reset: function () {
            this.$refs['searchForm'].resetFields();
        },
        remove: function (array, val) {
            let index = array.indexOf(val);
            if (index > -1) {
                array.splice(index, 1);
            }
        }
    }
});