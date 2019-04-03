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
         * 用印完成确认
         */
        sealFinishWarn : function(projectId){
            console.log(projectId)
            this.$Modal.confirm({
                title: '用印完成提示',
                content: '<p>当前项目确定已盖章完成？</p>',
                onOk: () => {
                    this.sealFinish(projectId);
                },
                onCancel: () => {}
            })
        },
        sealFinish : function(projectId) {
            let self = this;
            this.$http.post("/seal/sealFinish/" + projectId).then(function(response) {
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
        title: '合同信息',
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
                            // vue.signFinishWarn(param.row.serialNo);
                        }
                    }
                }, '查看审批信息')
            ])
        }
    },{
        title: '操作',
            align: 'center',
            render:(h,param)=>{
            return h('div', [
                h('span'),
                param.row.sealStatus === 0 ? h('Button', {
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
                            vue.sealFinishWarn(param.row.projectId);
                        }
                    }
                }, '用印完成') : h('span')
            ])
        }
    }
];