/**
 * 用印管理
 */
common.pageName = "seal";
common.openName = [ '3' ];

var vue = new Vue({
    el : '#seal',
    data : {
        formInline:{
            type : []
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
        this.initData();
        this.query();
    },
    methods : {
        /**
         * 初始化数据
         */
        initData : function() {
        },
        /** 分页 */
        pageChange : function(page){
            this.queryParam.pageNum = page;
            this.query();
        },
        /**
         * 查询
         */
        query : function(){
            this.showResult=true;
            var _self = this;
            this.queryParam.condition = this.formInline;
            this.$http.post("/seal/list", this.queryParam).then(
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
            var self = this;
            this.addForm.serialNo = serialNo;
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
         * 签署完成确认
         */
        signFinishWarn : function(serialNo){
            this.$Modal.confirm({
                title: '签署完成提示',
                content: '<p>当前项目确定已完成签署？</p>',
                onOk: () => {
                    this.signFinish(serialNo);
                },
                onCancel: () => {}
            })
        },
        /**
         * 签署完成
         */
        signFinish : function(serialNo) {
            let self = this;
            this.addForm.serialNo = serialNo;
            this.addForm.finished = true;
            //校验所有必须项是否已完成
            // for(index in self.addForm.signList) {
            //     let sign = self.addForm.signList[index];
            //     if(sign.isRequired === 1 && !sign.status) {
            //         self.$Message.error('带红星项全部完成才可以签署完成');
            //         return;
            //     }
            // }

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
            if(value === 0) {
                return "未用印";
            } else if(value === 1) {
                return "已用印";
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
        title: '项目编号',
        key: 'projectCode',
        align: 'left'
    },{
        title: '项目名称',
        key: 'projectName',
        align: 'left'
    },{
        title: '业务负责人',
        key: 'projectPerson',
        align: 'left'
    },{
        title: '合同通过时间',
        key: 'contractPassTime',
        align: 'left'
    },{
        title: '用印状态',
        key: 'sealStatus',
        align: 'left',
        render:(h,param)=> {
            return h('span', vue.getStatusDesc(param.row.sealStatus));
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
                            type: "warning",
							ghost:true
                        },
                        style: {
                            marginRight: '5px'
                        },
                        on: {
                            click: () => {
                            vue.signFinishWarn(param.row.serialNo);
                        }
                    }
                }, '用印完成')
            ])
        }
    }
];