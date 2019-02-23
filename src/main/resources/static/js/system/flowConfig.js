/**
 * 流程配置
 */
common.pageName = "flowConfig";
common.openName = [ '8' ];

var vue = new Vue({
    el : '#flowConfig',
    data : {
        formInline:{
            type:[]
        },
        queryParam : {
            condition : {},
            pageNum : 1,
            pageSize : 10
        },
        addForm : {
            flowName : "",
            statusList:[]
        },
        pageInfo:{},
        tableColumns:[],
        showResult:false,
        modal1:false
    },
    created : function() {
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
            this.$http.post("/flowConfig/list", _self.queryParam).then(
                function(response) {
                    _self.pageInfo = response.data;
                }, function(error) {
                    _self.$Message.error(error.data.message);
                })
        },
        /**
         * 新增流程配置
         */
        addFlow : function() {
            this.modal1 = true;
            this.addForm = {
            };
        },
        /**
         * 保存流程配置
         */
        saveFlow : function() {
            let self = this;
            if(this.addForm.id == null || this.addForm.id == ""){
                this.$http.post("/flowConfig", this.addForm).then(function(response) {
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
            }else{
                this.$http.put("/flowConfig", this.addForm).then(function(response) {
                    if (response.data.success) {
                        self.$Message.info({
                            content : "更新成功",
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
            }
        },
        /**
         * 获取状态列表
         */
        getStatusList:function(value) {
            let self = this;
            this.$http.get("/flowConfig/status/" + value).then(function(response){
                if (response.data.success) {
                    self.addForm.statusList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
        },
        /**
         * 删除警告
         */
        deleteWarn:function(id){
            this.$Modal.confirm({
                title: '删除提示',
                content: '<p>确认是否删除当前流程配置</p>',
                onOk: () => {
                this.deleteFlow(id);
        },
            onCancel: () => {}
        })
        },
        /** 删除流程配置 */
        deleteFlow:function(id){
            let self = this;
            this.$http.delete("/flowConfig/" + id).then(function(response){
                if (response.data.success) {
                    self.$Message.info({
                        content : "删除成功",
                        onClose : function() {
                            self.query();
                            self.cancel();
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
        },
        /**
         * 更新流程配置
         */
        updateFlow : function(id){
            let self = this;
            this.$http.get("/flowConfig/" + id).then(function(response){
                if (response.data.success) {
                    self.addForm = response.data.data;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
            this.modal1 = true;
        },
        /**
         * 取消保存
         */
        cancel : function() {
            this.modal1 = false;
            if(this.addForm.id == '') {
                this.$refs['entityDataForm'].resetFields();
            }
        },
        /**
         * 流程类型
         */
        getFlowTypeDesc : function(value){
            if(value === 1) {
                return "合同";
            } else if(value === 2) {
                return "项目";
            }
            return "";
        },
        /** 分页 */
        pageChange : function(page){
            this.query();
        }
    }
});

vue.tableColumns=[
    {
        title: '流程名称',
        key: 'flowName',
        align: 'left'
    },
    {
        title: '流程类型',
        key: 'projectMode',
        align: 'left',
        render:(h,param)=> {
            return h('span', vue.getFlowTypeDesc(param.row.flowType));
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
                        vue.updateFlow(param.row.id);
        }
        }
        }, '编辑'),
            h('Button', {
                props: {
                    size: "small",
                    type: "error"
                },
                style: {
                    marginRight: '5px'
                },
                on: {
                    click: () => {
                    vue.deleteWarn(param.row.id);
        }
    }
    }, '删除')
    ]
    )
    }
}
];