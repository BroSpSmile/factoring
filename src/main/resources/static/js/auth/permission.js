/**
 * 权限信息
 */
common.pageName = "permission";
common.openName = [ '8' ];

var vue = new Vue({
    el : '#permission',
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
            permissionCode : "",
            permissionName : "",
            parentSerialNo : "",
            url : "",
            remark : ""
        },
        ruleValidate: {
            permissionCode: [
                { required: true, message: '权限编号不能为空', trigger: 'blur' }
            ],
            permissionName: [
                { required: true, message: '权限名称不能为空', trigger: 'blur' }
            ],
            permissionType: [
                { required: true, message: '权限类型不能为空', trigger: 'change', type:'number'}
            ]
        },
        pageInfo:{},
        tableColumns:[],
        showResult:false,
        modal1:false,
        permissionList:[],
        model11: '',
        isDisable : false
    },
    created : function() {
        this.query();
    },
    methods : {
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
            this.$http.post("/permission/list", this.queryParam).then(
                function(response) {
                    _self.pageInfo = response.data;
                }, function(error) {
                    _self.$Message.error(error.data.message);
                })
        },
        /**
         * 类型翻译
         */
        getTypeDesc : function(value){
            if(value === 1) {
                return "菜单";
            } else if(value === 2) {
                return "按钮";
            }
            return "";
        },
        /**
         * 新增权限
         */
        addPermission : function() {
            this.modal1 = true;
            this.isDisable = false;
            this.addForm = {
            };
            this.getMenuList();
        },
        /**
         * 获取权限列表
         */
        getMenuList() {
            let self = this;
            this.$http.get("/permission/list/1").then(function(response) {
                    if (response.data.success) {
                        self.permissionList = response.data.values;
                    } else {
                        self.$Message.error(response.data.errorMessage);
                    }
                }, function(error) {
                    self.$Message.error(error.data.message);
                }
            )
        },
        /**
         * 保存权限信息
         */
        savePermission : function() {
            let self = this;
            this.isDisable = true;
            this.$refs.addForm.validate((valid) => {
                if(valid) {
                    if (this.addForm.id === undefined || this.addForm.id === null || this.addForm.id === "") {
                        this.$http.post("/permission", this.addForm).then(function (response) {
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
                        this.$http.put("/permission", this.addForm).then(function (response) {
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
                content: '<p>确认是否删除当前权限</p>',
                onOk: () => {
                this.deletePermission(id);
        },
            onCancel: () => {}
        })
        },
        /** 删除权限 */
        deletePermission:function(id){
            let self = this;
            this.$http.delete("/permission/" + id).then(function(response){
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
         * 更新权限
         */
        updatePermission : function(permission){
            this.addForm = JSON.parse(JSON.stringify(permission));
            this.modal1 = true;
            this.isDisable = false;
            this.getMenuList();
        },
        changePermissionType : function(value) {
            if(value === 1) {
                this.ruleValidate.url[0].required = true;
            } else {
                this.ruleValidate.url[0].required = false;
            }
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
        title: '权限编号',
        key: 'permissionCode',
        align: 'left'
    },{
        title: '权限名称',
        key: 'permissionName',
        align: 'left'
    },{
        title: '权限类型',
        key: 'permissionType',
        align: 'left',
        render:(h,param)=> {
        return h('span', vue.getTypeDesc(param.row.permissionType));
}
},{
    title: '菜单路径',
        key: 'url',
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
                    vue.updatePermission(param.row);
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