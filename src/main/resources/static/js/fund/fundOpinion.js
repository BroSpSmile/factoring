/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = ['1'];

var vue = new Vue({
    el: '#fundOpinion',
    data: {
        project: {},
        target: {},
        opinion: {},
        validata: {
            opinion: [
                {required: true, message: '项目意见不能为空', trigger: 'blur'}
            ]
        }
    },
    created: function () {
        let id = document.getElementById("fundId").value;
        this.getProject(id);
    },
    methods: {
        /**
         *
         */
        getProject: function (id) {
            let _self = this;
            this.$http.get("/project/" + id).then(function (response) {
                _self.project = response.data;
                _self.getOpinion(_self.project.id);
                _self.getTarget(_self.project.projectId)
            }, function (error) {
                console.error(error);
            })
        },

        getTarget: function (id) {
            let _self = this;
            this.$http.get("/fund/" + id).then(function (response) {
                if (response.data) {
                    _self.target = response.data;
                }
            }, function (error) {
                console.error(error);
            })
        },

        getOpinion: function (id) {
            let _self = this;
            this.$http.get("/fundOpinion/" + id).then(function (response) {
                if (response.data) {
                    _self.opinion = response.data;
                    if(_self.opinion.transfer){
                        _self.opinion.transfer = "true";
                    }else{
                        _self.opinion.transfer = "false";
                    }
                }
            }, function (error) {
                console.error(error);
            })
        },

        save: function () {
            let _self = this;
            this.$refs['entityDataForm'].validate((valid) => {
                if (!valid) {
                    _self.$Message.error('校验失败,请完善信息!');
                    return false;
                } else {
                    _self.saved();
                }
            });
        },

        /**
         * 保存初步意见
         */
        saved: function () {
            this.$Spin.show();
            let self = this;
            this.opinion.project = this.project;
            this.$http.post("/fundOpinion", this.opinion).then(function (response) {
                this.$Spin.hide();
                if (response.data.success) {
                    self.$Message.info({
                        content: "保存初步意见成功"
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                this.$Spin.hide();
                self.$Message.error(error);
            })
        },

        /**
         * 提交保密协议
         */
        commit: function () {
            this.$Spin.show();
            let self = this;
            this.$http.put("/fundOpinion", this.project).then(function (response) {
                this.$Spin.hide();
                if (response.data.success) {
                    self.$Message.info({
                        content: "流转保密协议成功"
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function (error) {
                this.$Spin.hide();
                self.$Message.error(error);
            })
        },

    }
});


