/**
 * 签署清单信息
 */
common.pageName = "role";
common.openName = [ '3' ];

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
            this.addForm = {
            };
		},
        /**
		 * 保存签署清单
         */
        saveSign : function() {
            let self = this;
            if(this.addForm.id == null || this.addForm.id == ""){
                this.$http.post("/signListTemplate", this.addForm).then(function(response) {
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
                this.$http.put("/signListTemplate", this.addForm).then(function(response) {
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
         * 更新角色
         */
        updateSign : function(user){
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
        title: '签署清单名称',
        key: 'signListName',
        align: 'left'
    },{
        title: '排序值',
        key: 'sort',
        align: 'right'
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
                            vue.updateSign(param.row);
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