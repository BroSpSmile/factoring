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
            status : 8
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
        modal1:false
    },
    created : function() {
        this.query();
    },
    methods : {
        /**
         * 查询
         */
        query : function(page){
            this.showResult=true;
            this.queryParam.pageNum = page;
            var _self = this;
            _self.queryParam.condition = _self.formInline;
            this.$http.post("/contractSign/list", _self.queryParam).then(
                function(response) {
                    _self.pageInfo = response.data;
                }, function(error) {
                    _self.$Message.error(error.data.message);
                })
        },
        /**
         * 签署
         */
        sign : function(serialNo) {
            console.log(serialNo)
            var self = this;
            self.addForm.serialNo = serialNo;
            this.$http.get("/contractInfo/signList/" + serialNo).then(function(response){
                if (response.data.success) {
                    self.addForm.signList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                console.error(error);
            });
            this.modal1 = true;
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
                            self.query();
                            self.cancel();
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
        signFinish : function() {
            let self = this;

            //校验所有必须项是否已完成
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
                            self.query();
                            self.cancel();
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function(error) {
                self.$Message.error(error.data.message);
            });
            this.addForm.finished = true;
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
        /** 分页 */
        pageChange : function(page){
            this.query();
        },
        /**
         * 项目模式转换
         * @param value
         */
        getProjectModeDesc : function(value) {
            if(value === 1) {
                return "有追索权模式";
            } else if(value === 2) {
                return "无追索权模式";
            }
            return "";
        },
        /**
         * 状态转换
         * @param value
         */
        getStatusDesc : function(value) {
            if(value === 1) {
                return "提出申请";
            } else if(value === 2) {
                return "部门初审";
            } else if(value === 3) {
                return "法务风控审核";
            } else if(value === 4) {
                return "集团副总审核";
            } else if(value === 5) {
                return "集团正总审核";
            } else if(value === 6) {
                return "完成";
            } else if(value === 7) {
                return "通知办公室";
            } else if(value === 8) {
                return "签署";
            } else if(value === 9) {
                return "签署完成";
            }
            return "";
        },
        remove: function (array, val) {
            let index = array.indexOf(val);
            if (index > -1) {
                array.splice(index, 1);
            }
        }
    }
});

vue.tableColumns=[
    {
        title: '合同编号',
        key: 'contractCode',
        align: 'left'
    },{
        title: '项目名称',
        key: 'projectName',
        align: 'left'
    },{
        title: '项目模式',
        key: 'projectMode',
        align: 'left',
        render:(h,param)=> {
        return h('span', vue.getProjectModeDesc(param.row.projectMode));
    }
    },{
        title: '状态',
            key: 'status',
            align: 'left',
            render:(h,param)=> {
            return h('span', vue.getStatusDesc(param.row.status));
        }
    },{
        title: '操作',
        align: 'center',
        render:(h,param)=>{
            return h('div', [
                h('span'),
                    h('Button', {
                        props: {
                            size: "small",
                            type: "warning"
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                            vue.sign(param.row.serialNo);
                        }
                    }
                }, '签署')
            ])
        }
    }
];