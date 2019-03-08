/**
 * 菜单信息
 */
common.pageName = "approval";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#approval',
	data : {
		formInline : {
			projectId : null,
			projectName : null,
			person : null,
			progress : null
		},
		addForm : {
			projectId : "",
			projectName : "",
			person : ""
		},
		queryParam : {
			condition : {},
			pageNum : 1,
			pageSize : 10
		},
		pageInfo:{},
		modal1 : false,
		statusItems : []
	},
	created : function() {
		this.initDate();
		this.query();
	},
	methods : {
		/**
		 * 初始化数据
		 */
		initDate : function() {
			var _self = this;
			this.$http.get("/combo/progress").then(function(response) {
				_self.statusItems = response.data;
			}, function(error) {
				console.error(error);
			})
		},
		
		/**
		 * 状态翻译
		 */
		getProgress:function(value){
			for(var index in this.statusItems){
				if(value==this.statusItems[index].value){
					return this.statusItems[index].text;
				}
			}
			return "";
		},

		/** 分页查询 */
		query : function() {
			let self = this;
			self.queryParam.condition = self.formInline;
			this.$http.post("/approval/query", self.queryParam).then(
					function(response) {
						self.pageInfo = response.data;
					}, function(error) {
						self.$Message.error(error.data.message);
					})
		},

		/** 搜索 */
		search:function(){
			this.queryParam.pageNum = 1;
			this.query();
		},
		
		/** 分页 */
		pageChange:function(page){
			this.queryParam.pageNum = page;
			this.query();
		},
		
		/**
		 * 重置
		 */
		reset:function(){
			this.$refs['searchForm'].resetFields();
		},
		
		/**
		 * 新增项目
		 */
		addProject : function() {
			window.open("factoring","_blank"); 
		},

		/** 保存项目 */
		saveProject : function() {
			let self = this;
			if(this.addForm.id==null||this.addForm.id==""){
				this.$http.post("/approval", this.addForm).then(function(response) {
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
				this.$http.put("/approval", this.addForm).then(function(response) {
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
		 * 更新项目
		 */
		updateProject:function(project){
			this.addForm = project;
			this.modal1 = true;
		},
		
		/**
		 * 删除警告
		 */
		deleteWarn:function(id){
			this.$Modal.confirm({
				title: '删除提示',
				content: '<p>确认是否删除当前项目</p>',
				onOk: () => {
					this.deleteProject(id);
				},
				onCancel: () => {
				}
			})
		},
		
		/** 删除项目 */
		deleteProject:function(id){
			let self = this;
			this.$http.delete("/approval/"+id).then(function(response){
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
				self.$Message.error(error.data.errorMessage);
			})
		},
		
		/**
		 * 跳转菜单
		 */
		toMenu:function(menu,projectId){
			if(projectId){
				window.open(menu+"?id="+projectId);
			}else{
				window.open(menu);
			}
		},
		
		/**
		 * 尽调审核
		 */
		tuneup:function(projectId){
			window.open("apply?id="+projectId,"_blank"); 
		},

		/**
		 * 取消保存
		 */
		cancel : function() {
			this.modal1 = false;
			if(this.addForm.projectId==''){
				this.$refs['entityDataForm'].resetFields();
			}
		}
	}
});

vue.tableColumns=[{
		type: 'expand',
	    width: 50,
	    render: (h, params) => {
	    	return h('div', {
                props: {
                    row: params.row
                },
                domProps:{
                	innerHTML:'<span>让与人:'+params.row.detail.creditor+'</span>&nbsp;&nbsp;<span>债务人:'+params.row.detail.debtor+
                	'</span>&nbsp;&nbsp;<span>基础合同:'+params.row.detail.baseContract+'</span>&nbsp;&nbsp;<br><span>签署日期:'+moment(params.row.detail.signDate).format('YYYY-MM-DD HH:mm')+
                	'</span>&nbsp;&nbsp;<span>应收账款受让款:'+params.row.detail.assignee+'</span>&nbsp;&nbsp;<span>应收账款:'+params.row.detail.receivable+'</span>'
                }
            })
	    }
	},{
        title: '项目编号',
        key: 'projectId',
        align: 'center'
    },{
        title: '项目名称',
        key: 'projectName',
        align: 'center'
    },{
        title: '项目负责人',
        key: 'user',
        align: 'center',
        render:(h,param)=>{
        	return h('span',param.row.user.username)
        }
    },{
        title: '当前进度',
        key: 'progress',
        align: 'center',
        render:(h,param)=>{
        	return h('span',vue.getProgress(param.row.progress));
        }
    },{
    	title: '操作',
    	align: 'center',
        render:(h,param)=>{
        	return h('div', [
        		param.row.progress=='INIT'?
						h('Button', {
							props: {
								size: "small",
								type: "info",
								ghost:true
							},
							style: {
								marginRight: '5px'
							},
							on: {
								click: () => {
									vue.toMenu("meeting",param.row.id);
								}
							}
						}, '发起立项会'):
						h('span'),
				param.row.progress=='APPROVAL'?
						h('Button', {
							props: {
								size: "small",
								type: "info",
								ghost:true
							},
							style: {
								marginRight: '5px'
							},
							on: {
								click: () => {
									vue.toMenu("apply",param.row.id);
								}
							}
						}, '项目尽调'):
						h('span'),
				param.row.progress=='INVESTIGATION'?
						h('Button', {
							props: {
								size: "small",
								type: "info",
								ghost:true
							},
							style: {
								marginRight: '5px'
							},
							on: {
								click: () => {
									vue.toMenu("past",param.row.id);
								}
							}
						}, '发起会议'):
						h('span'),
				param.row.progress=='PASTMEETING'?
						h('Button', {
							props: {
								size: "small",
								type: "info",
								ghost:true
							},
							style: {
								marginRight: '5px'
							},
							on: {
								click: () => {
									vue.toMenu("contractInfo",param.row.id);
								}
							}
						}, '合同草拟'):
						h('span'),
				param.row.progress=='DRAWUP'?
						h('Button', {
							props: {
								size: "small",
								type: "info",
								ghost:true
							},
							style: {
								marginRight: '5px'
							},
							on: {
								click: () => {
									vue.toMenu("contractSign",param.row.id);
								}
							}
						}, '合同签署'):
						h('span'),
				param.row.progress=='SIGN'?
						h('Button', {
							props: {
								size: "small",
								type: "info",
								ghost:true
							},
							style: {
								marginRight: '5px'
							},
							on: {
								click: () => {
									 vue.toMenu("loanApply",param.row.id);
								}
							}
						}, '放款'):
						h('span'),
				param.row.progress=='LOAN'?
						h('Button', {
							props: {
								size: "small",
								type: "info",
								ghost:true
							},
							style: {
								marginRight: '5px'
							},
							on: {
								click: () => {
									vue.toMenu("filingApply",param.row.id);
								}
							}
						}, '归档'):
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
							 vue.updateProject(param.row);
						}
					}
				}, '编辑'),
				param.row.progress=='INIT'?
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
						}, '删除'):
						h('span')
				
			])
        }
    }
];


