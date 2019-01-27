/**
 * 角色信息
 */
common.pageName = "role";
common.openName = [ '9' ];

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
        permissionList : [],
		pageInfo:{},
		tableColumns:[],
		showResult:false,
		modal1:false,
        modal2:false
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
            if(this.addForm.id == null || this.addForm.id == ""){
                this.$http.post("/role", this.addForm).then(function(response) {
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
                this.$http.put("/role", this.addForm).then(function(response) {
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
        settingPermission : function () {
            let self = this;
            this.$http.get("/permission/tree").then(function(response){
                if (response.data.success) {
                    self.permissionList = response.data.values;
                } else {
                    self.$Message.error(response.data.errorMessage);
                }
            },function(error){
                self.$Message.error(error.data.message);
            })

            // this.permissionList = [{
            //     expand: true,
            //     title: 'parent 1',
            //     children: [{
            //         title: 'parent 1-0',
            //         expand: true,
            //         disabled: true,
            //         children: [{
            //             title: 'leaf',
            //             disableCheckbox: true
            //         }, {
            //             title: 'leaf',
            //         }]
            //     }, {
            //         title: 'parent 1-1',
            //         expand: true,
            //         checked: true,
            //         children: [{
            //             title: '<span style="color: red">leaf</span>',
            //         }]
            //     }]
            // }]

            this.modal2 = true;
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
         * 取消权限设置页面
         */
        cancelPermission : function() {
            this.modal2 = false;
        },
        /**
         * 保存权限信息
         */
        savePermission : function() {
            let checkedNodes = this.$refs.permissionTree.getSelectedNodes;
            console.log(checkedNodes.length)
            console.log(checkedNodes)
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
                        type: "warning"
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
                        type: "warning"
                    },
                    style: {
                        marginRight: '5px'
                    },
                    on: {
                        click: () => {
                            vue.settingPermission();
                        }
                    }
                }, '权限设置'),
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