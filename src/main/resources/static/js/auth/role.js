/**
 * 角色信息
 */
common.pageName = "role";
common.openName = [ '8' ];

var vue = new Vue({
	el : '#role',
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
			roleCode : "",
			roleName : "",
			roleDesc : ""
		},
        ruleValidate: {
            roleCode: [
                { required: true, message: '角色编号不能为空', trigger: 'blur' }
            ],
            roleName: [
                { required: true, message: '角色名称不能为空', trigger: 'blur' }
            ]
        },
        permissionList : [],
		pageInfo:{},
		tableColumns:[],
		showResult:false,
		modal1:false,
        modal2:false,
        permissionForm : {
            checkedPermissionList:[],
            roleSerialNo:""
        }
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
			this.$http.post("/role/list", _self.queryParam).then(
					function(response) {
                        _self.pageInfo = response.data;
					}, function(error) {
                    	_self.$Message.error(error.data.message);
					})
		},
		/**
		 * 新增角色
		 */
		addRole : function() {
			this.modal1 = true;
            this.addForm = {
            };
		},
        /**
		 * 保存角色信息
         */
		saveRole : function() {
            let self = this;
            this.$refs.addForm.validate((valid) => {
                if(valid) {
                    if (this.addForm.id === undefined || this.addForm.id === null || this.addForm.id === "") {
                        this.$http.post("/role", this.addForm).then(function (response) {
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
                        this.$http.put("/role", this.addForm).then(function (response) {
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
                content: '<p>确认是否删除当前角色</p>',
                onOk: () => {
                    this.deleteRole(id);
                },
                onCancel: () => {}
            })
        },
        /** 删除角色 */
        deleteRole:function(id){
            let self = this;
            this.$http.delete("/role/" + id).then(function(response){
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
        updateRole : function(user){
            this.addForm = user;
            this.modal1 = true;
        },
        /**
         * 权限配置
         */
        settingPermission : function (serialNo) {
            this.permissionForm.roleSerialNo = serialNo;
            let self = this;
            this.$http.get("/permission/tree/" + serialNo).then(function(response){
                if (response.data.success) {
                    self.permissionList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
            this.modal2 = true;
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
        /**
         * 取消权限设置页面
         */
        cancelPermission : function() {
            this.modal2 = false;
        },
        /**
         * 保存权限信息
         */
        savePermission : function() {
            let self = this;
            this.$http.post("/role/permission", this.permissionForm).then(function(response){
                if (response.data.success) {
                    self.$Message.info({
                        content : "保存成功",
                        onClose : function() {
                            self.query();
                            self.cancelPermission();
                        }
                    });
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })
        },
        choiceAll:function(data){
            this.permissionForm.checkedPermissionList = [];
            for(var i = 0; i < data.length; i++) {
                this.permissionForm.checkedPermissionList.push(data[i].serialNo)
            }
            //let choicesAll=this.$refs.permissionTree.getCheckedNodes; //方法的运用 getSelectedNodes也是如此用法
            //console.log(choicesAll);
        },
        /** 分页 */
        pageChange : function(page){
            this.query();
        }
	}
});

vue.tableColumns=[
    {
        title: '角色编号',
        key: 'roleCode',
        align: 'left'
    },{
        title: '角色名称',
        key: 'roleName',
        align: 'center'
    },{
        title: '描述',
        key: 'roleDesc',
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
                            vue.updateRole(param.row);
                        }
                    }
                }, '编辑'),
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
                            vue.settingPermission(param.row.serialNo);
                        }
                    }
                }, '权限设置'),
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