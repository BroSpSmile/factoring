/**
 * 签署清单信息
 */
common.pageName = "signListTemplate";
common.openName = [ '8' ];

var vue = new Vue({
    el : '#signListTemplate',
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
            signListName : "",
            sort : ""
        },
        ruleValidate: {
            signListName: [
                { required: true, message: '签署清单名称不能为空', trigger: 'blur' }
            ],
            sort: [
                { required: true, message: '排序值不能为空', trigger: 'blur', type:'number' }
            ],
            category: [
                { required: true, message: '清单类别不能为空', trigger: 'change', type:'number' }
            ]
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
            this.$http.post("/signListTemplate/list", _self.queryParam).then(
                function(response) {
                    _self.pageInfo = response.data;
                }, function(error) {
                    _self.$Message.error(error.data.message);
                })
        },
        /**
         * 新增签署清单
         */
        addSign : function() {
            this.modal1 = true;
            this.isDisable = false;
            this.addForm = {
                projectMode : 1,
                isRequired : 1
            };
        },
        /**
         * 保存签署清单
         */
        saveSign : function() {
            let self = this;
            this.$refs.addForm.validate((valid) => {
                if(valid) {
                    if (this.addForm.id === undefined || this.addForm.id === null || this.addForm.id === "") {
                        this.$http.post("/signListTemplate", this.addForm).then(function (response) {
                            if (response.data.success) {
                                self.$Message.info({
                                    content: "保存成功",
                                    onClose: function () {
                                        self.query();
                                        self.cancel();
                                    }
                                });
                            } else {
                                self.$Message.error(response.data.errorMessage);
                            }
                        }, function (error) {
                            self.$Message.error(error.data.message);
                        });
                    } else {
                        this.$http.put("/signListTemplate", this.addForm).then(function (response) {
                            if (response.data.success) {
                                self.$Message.info({
                                    content: "更新成功",
                                    onClose: function () {
                                        self.query();
                                        self.cancel();
                                    }
                                });
                            } else {
                                self.$Message.error(response.data.errorMessage);
                            }
                        }, function (error) {
                            self.$Message.error(error.data.message);
                        });
                    }
                }
            });
        },
        /**
         * 删除警告
         */
        deleteWarn:function(id){
            this.$Modal.confirm({
                title: '删除提示',
                content: '<p>确认是否删除当前签署清单</p>',
                onOk: () => {
                this.deleteSign(id);
        },
            onCancel: () => {}
        })
        },
        /** 删除签署清单 */
        deleteSign:function(id){
            let self = this;
            this.$http.delete("/signListTemplate/" + id).then(function(response){
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
         * 更新签署清单
         */
        updateSign : function(sign){
            this.addForm = JSON.parse(JSON.stringify(sign));
            this.modal1 = true;
            this.isDisable = false;
        },
        /**
         * 取消保存
         */
        cancel : function() {
            this.modal1 = false;
            if(this.addForm.id === undefined || this.addForm.id === null || this.addForm.id === "") {
                this.$refs.addForm.resetFields();
            }
        },
        /**
         * 项目模式
         */
        getProjectModeDesc : function(value){
            if(value === 1) {
                return "有追索权模式";
            } else if(value === 2) {
                return "无追索权模式";
            }
            return "";
        },
        /**
         * 是否必须
         */
        getIsRequiredDesc : function(value){
            if(value === 1) {
                return "必须";
            } else if(value === 2) {
                return "非必须";
            }
            return "";
        },
        /**
         * 清单类别
         */
        getCategoryDesc : function(value){
            if(value === 1) {
                return "债权人";
            } else if(value === 2) {
                return "债务人";
            } else if(value === 3) {
                return "内部决策";
            } else if(value === 4) {
                return "签署文件";
            } else if(value === 5) {
                return "出款依据";
            } else if(value === 6) {
                return "其他";
            }
            return "";
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
        }
    }
});

vue.tableColumns=[
    {
        title: '签署清单名称',
        key: 'signListName',
        align: 'left'
    },{
        title: '项目模式',
        key: 'projectMode',
        align: 'left',
        render:(h,param)=> {
            return h('span', vue.getProjectModeDesc(param.row.projectMode));
        }
    },{
        title: '清单类别',
            key: 'category',
            align: 'left',
            render:(h,param)=> {
            return h('span', vue.getCategoryDesc(param.row.category));
        }
    },{
        title: '排序值',
            key: 'sort',
            align: 'right'
    },{
        title: '是否必须',
        key: 'isRequired',
        align: 'left',
        render:(h,param)=> {
            return h('span', vue.getIsRequiredDesc(param.row.isRequired));
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
                        vue.updateSign(param.row);
        }
        }
        }, '编辑'),
            h('Button', {
                props: {
                    size: "small",
                    type: "error",
					ghost:true
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