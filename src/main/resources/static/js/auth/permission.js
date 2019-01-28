/**
 * 权限信息
 */
common.pageName = "permission";
common.openName = [ '10' ];

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
            permissionType : "",
            url : "",
            remark : ""
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
			this.$http.post("/permission/list", _self.queryParam).then(
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
            this.addForm = {
            };
		},
        /**
		 * 保存权限信息
         */
		savePermission : function() {
            let self = this;
            if(this.addForm.id == null || this.addForm.id == ""){
                this.$http.post("/permission", this.addForm).then(function(response) {
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
                this.$http.put("/permission", this.addForm).then(function(response) {
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
        updatePermission : function(user){
            this.addForm = user;
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

        /** 分页 */
        pageChange : function(page){
            this.query();
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
        align: 'center'
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
                        type: "warning"
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