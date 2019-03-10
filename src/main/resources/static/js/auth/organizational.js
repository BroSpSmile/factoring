/**
 * 组织架构信息
 */
common.pageName = "organizational";
common.openName = [ '8' ];

var vue = new Vue({
    el : '#organizational',
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
            organizationalName : "",
            remark : ""
        },
        ruleValidate: {
            organizationalName: [
                { required: true, message: '组织架构名称不能为空', trigger: 'blur' }
            ]
        },
        pageInfo:{},
        tableColumns:[],
        showResult:false,
        modal1:false,
        organizationalList:[],
        model11: '',
        isDisable : false
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
            this.$http.post("/organizational/list", _self.queryParam).then(
                function(response) {
                    _self.pageInfo = response.data;
                }, function(error) {
                    _self.$Message.error(error.data.message);
                })
        },
        /**
         * 新增组织架构
         */
        addOrganizational : function() {
            this.modal1 = true;
            this.addForm = {
            };
            this.getOrganizationalList();
        },
        /**
         * 获取组织列表
         */
        getOrganizationalList() {
            let self = this;
            this.$http.get("/organizational/list").then(function(response) {
                    if (response.data.success) {
                        self.organizationalList = response.data.values;
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function(error) {
                    self.$Message.error(error.data.message);
                }
            )
        },
        /**
         * 保存组织架构
         */
        saveOrganizational : function() {
            let self = this;
            this.isDisable = true;
            this.$refs.addForm.validate((valid) => {
                if(valid) {
                    if (this.addForm.id === undefined || this.addForm.id === null || this.addForm.id === "") {
                        this.$http.post("/organizational", this.addForm).then(function (response) {
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
                        this.$http.put("/organizational", this.addForm).then(function (response) {
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
                    self.isDisable = false;
                }
            });
        },
        /**
         * 删除警告
         */
        deleteWarn:function(id){
            this.$Modal.confirm({
                title: '删除提示',
                content: '<p>确认是否删除当前组织架构</p>',
                onOk: () => {
                this.deleteOrganizational(id);
        },
            onCancel: () => {}
        })
        },
        /** 删除组织架构 */
        deleteOrganizational:function(id){
            let self = this;
            this.$http.delete("/organizational/" + id).then(function(response){
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
         * 更新角色
         */
        updateOrganizational : function(organizational){
            this.addForm = organizational;
            this.modal1 = true;
            this.getOrganizationalList();
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
        },
        /** 分页 */
        pageChange : function(page){
            this.query();
        }
    }
});

vue.tableColumns=[
    {
        title: '组织名称',
        key: 'organizationalName',
        align: 'left'
    },{
        title: '描述',
        key: 'remark',
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
                    vue.updateOrganizational(param.row);
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