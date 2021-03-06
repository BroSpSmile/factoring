/**
 * 银行信息
 */
common.pageName = "bankInfo";
common.openName = [ 'finance' ];

var vue = new Vue({
    el : '#bankInfo',
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
            bankFullName : "",
            bankShortName : "",
            bankAccount : "",
            organizationalSerialNo : ""
        },
        ruleValidate: {
            organizationalSerialNo: [
                { required: true, message: '所属组织不能为空', trigger: 'blur' }
            ],
            bankFullName: [
                { required: true, message: '银行全称不能为空', trigger: 'blur' }
            ],
            bankShortName: [
                { required: true, message: '银行简称不能为空', trigger: 'blur'}
            ],
            bankAccount: [
                { required: true, message: '银行账号不能为空', trigger: 'blur'}
            ]
        },
        pageInfo:{},
        tableColumns:[],
        showResult:false,
        modal1:false,
        isDisable : false,
        organizationalList:[]
    },
    created : function() {
        this.initData();
        this.query();
    },
    methods : {
        initData : function() {
            let self = this;
            this.$http.get("/organizational/list").then(function(response) {
                if (response.data.success) {
                    self.organizationalList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            }, function(error) {
                self.$Message.error(error.data.message);
            })
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
            this.showResult = true;
            var _self = this;
            this.queryParam.condition = this.formInline;
            this.$http.post("/bankInfo/list", this.queryParam).then(
                function(response) {
                    _self.pageInfo = response.data;
                }, function(error) {
                    _self.$Message.error(error.data.message);
                })
        },
        /**
         * 新增银行信息
         */
        addBank : function() {
            this.modal1 = true;
            this.isDisable = false;
            this.addForm = {
                bankFullName : "",
                bankShortName : "",
                bankAccount : "",
                organizationalSerialNo : "",
                amount:0.00
            };
        },
        /**
         * 保存银行信息
         */
        saveBank : function() {
            let self = this;
            this.isDisable = true;
            this.$refs.addForm.validate((valid) => {
                if(valid) {
                    if (this.addForm.id === undefined || this.addForm.id === null || this.addForm.id === "") {
                        this.$http.post("/bankInfo", this.addForm).then(function (response) {
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
                        this.$http.put("/bankInfo", this.addForm).then(function (response) {
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
                } else {
                    this.isDisable = false;
                }
            });
        },
        /**
         * 删除警告
         */
        deleteWarn:function(id){
            this.$Modal.confirm({
                title: '删除提示',
                content: '<p>确认是否删除当前银行</p>',
                onOk: () => {
                    this.deleteBank(id);
                },
                onCancel: () => {}
            })
        },
        /** 删除流程配置 */
        deleteBank:function(id){
            let self = this;
            this.$http.delete("/bankInfo/" + id).then(function(response){
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
         * 更新银行信息
         */
        updateBank : function(id){
            let self = this;
            this.$http.get("/bankInfo/" + id).then(function(response){
                if (response.data.success) {
                    self.addForm = response.data.data;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
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
         * 重置
         */
        reset: function () {
            this.$refs['searchForm'].resetFields();
        }
    }
});

vue.tableColumns=[
    {
        title: '银行全称',
        key: 'bankFullName',
        align: 'left'
    },{
        title: '银行简称',
        key: 'bankShortName',
        align: 'left'
    },{
        title: '银行账号',
        key: 'bankAccount',
        align: 'left'
    },{
        title: '银行余额',
        key: 'amount',
        align: 'right'
    },{
        title: '所属组织',
        key: 'organizationalName',
        align: 'left'
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
                        vue.updateBank(param.row.id);
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