/**
 * 菜单信息
 */
common.pageName = "project";
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
		flowInfo:{},
		modal1 : false,
		modal2:false,
		statusItems : [],
		models:[],
		steps:[],
		indexsteps:[]
	},
	created : function() {
		this.initDate();
		this.query();
	},
	filters:{
		timeFilter:function(value){
			if(!value){
				return "";
			}
			return moment(value).format('YYYY-MM-DD HH:mm');
		},
		/**
		 * 翻译
		 */
		toStepName:function(value){
			for(let index in vue.steps){
				if(vue.steps[index].value == value){
					return vue.steps[index].text;
				}
			}
			return "";
		}
	},
	methods : {
		/**
		 * 初始化数据
		 */
		initDate : function() {
			var _self = this;
			this.$http.get("/combo/projectModel").then(function(response) {
				_self.models = response.data;
			}, function(error) {
				console.error(error);
			});
			this.$http.get("/combo/steps").then(function(response) {
				_self.steps = response.data;
			}, function(error) {
				console.error(error);
			});
			this.$http.get("/combo/indexsteps").then(function(response) {
				_self.indexsteps = response.data;
			}, function(error) {
				console.error(error);
			});
		},
		
		/**
		 * 翻译
		 */
		toModelName:function(value){
			for(let index in this.models){
				if(this.models[index].value == value){
					return this.models[index].text;
				}
			}
			return "";
		},
		
		
		/**
		 * 翻译
		 */
		toIndexStepName:function(value){
			for(let index in this.indexsteps){
				if(this.indexsteps[index].value == value+""){
					return this.indexsteps[index].text;
				}
			}
			if(value==-1){
				return "项目创建";
			}

			return "";
		},
		
		toBoolean:function(value){
			if(value){
				return "是";
			}
			return "否";
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
			this.$http.post("/project/query", self.queryParam).then(
					function(response) {
						let data = response.data;
						for(let index in data.list){
							try{
								data.list[index].creditor = data.list[index].detail.creditor;
								data.list[index].debtor = data.list[index].detail.debtor;
							}catch(e){}
						}
						self.pageInfo = data;
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
			parent.window.menu.createNew({
				name:"新建",
				url:"factoring",
				id:"factoring"
			});
		},

		/** 保存项目 */
		saveProject : function() {
			let self = this;
			if(this.addForm.id==null||this.addForm.id==""){
				this.$http.post("/project", this.addForm).then(function(response) {
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
			this.$http.delete("/project/"+id).then(function(response){
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
		toMenu:function(menu,menuName,projectId){
			if(projectId){
				parent.window.menu.createNew({
					name:menuName,
					url:menu+"?id="+projectId,
					id:menu+"?id="+projectId
				});
			}else{
				parent.window.menu.createNew({
					name:menuName,
					url:menu,
					id:menu
				});
			}
		},
		
		showFlows:function(project){
			this.$http.get("/project/steps/"+project.id).then(function(response) {
				project.records = response.data;
				for(let index in project.records){
					let step = parseInt(index) ;
					if(step == project.step){
						project.records[index]['clazz'] = 'ivu-steps-status-process';
					}else if(step < project.step){
						if(project.records[index].status=='LATER'){
							project.records[index]['clazz'] = 'ivu-steps-status-error';
							var now = new Date();
							var days = daysBetween(new Date(),new Date(project.records[index].createTime));
							project.records[index]['days'] = days;
						}else if(project.records[index].status=='COMPLETED'){
							project.records[index]['clazz'] = 'ivu-steps-status-finish';
						}else{
							project.records[index]['clazz'] = 'ivu-steps-status-process';
						}
					}
				}
			}, function(error) {
				console.error(error);
			});
			this.flowInfo = project;
			this.modal2=true;
		},
		
		toAudit:function(record){
			console.log(record);
			parent.window.menu.createNew({
				name:"审核历史",
				url:"/audit?id="+record.audit.id,
				id:"/audit?id="+record.audit.id
			});
		},
		
		
		/**
		 * 后补
		 */
		make:function(item){
			var step = item.step
			var projectId = item.project.id
			if(step=='APPROVAL'){
				this.toMenu('meeting','立项申请',projectId);
			}else if(step == 'TUNEUP'){
				this.toMenu('apply','尽调申请',projectId);
			}else if(step == 'MEETING'){
				this.toMenu('past','三重一大',projectId);
			}else if(step == 'BACK'){
				this.toMenu('financeManage','财务管理');
			}
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
	    width: 40,
	    fixed:'left',
	    render: (h, params) => {
	    	return h('factoring-detail',{
	    		props:{
	    			row:params.row
	    		}
	    	});
	    }
	},{
        title: '项目编号',
        key: 'projectId',
        align: 'center',
        fixed:'left',
        width:125
    },{
        title: '项目名称',
        key: 'projectName',
        fixed:'left',
        minWidth:125,
        tooltip:true,
        align: 'center'
    },{
        title: '让与人',
        key: 'creditor',
        minWidth:150,
        tooltip:true,
        align: 'center'
    },{
        title: '债务人',
        key: 'debtor',
        minWidth:150,
        tooltip:true,
        align: 'center'
    },{
        title: '追索权',
        key: 'projectName',
        align: 'center',
        width:60,
        render:(h,param)=>{
        	return h('span',vue.toModelName(param.row.model))
        }
    },{
        title: '受让款(元)',
        key: 'projectName',
        align: 'center',
        width:130,
        render:(h,param)=>{
        	return h('span',common.money.formatter(param.row.detail.assignee))
        }
    },{
        title: '投放金额(元)',
        key: 'projectName',
        align: 'center',
        width:130,
        render:(h,param)=>{
        	return h('span',common.money.formatter(param.row.detail.receivable))
        }
    },{
        title: '年限',
        key: 'projectName',
        align: 'center',
        width:60,
        render:(h,param)=>{
        	return h('span',param.row.detail.duration)
        }
    },{
        title: '负责人',
        key: 'user',
        align: 'center',
        width:100,
        render:(h,param)=>{
        	return h('span',param.row.user.username)
        }
    },{
        title: '进度',
        key: 'progress',
        align: 'center',
        width:100,
        render:(h,param)=>{
        	return h('a',{
        		props:{},
        		on:{
        			'click':(value)=>{
        				vue.showFlows(param.row);
        			}
        		}
        	},vue.toIndexStepName(param.row.step));
        }
    },{
    	title: '操作',
    	align: 'center',
    	width:80,
        render:(h,param)=>{
        	return h('div', [
				h('Dropdown',{
					props:{},
					on:{
						'on-click':(value)=>{
							var menus = value.split("-");
							vue.toMenu(menus[0],menus[1],param.row.id);
						}
					}
				},[
					h('Button',{props:{size:'small',type:'warning',ghost:true}},[
						'操作',
						h('Icon',{props:{type:'ios-arrow-down'}})
					]),
					h('DropdownMenu',{slot:'list'},[
						h('DropdownItem',{props:{name:'factoring-编辑'}},'编辑'),
						param.row.step==-1||param.row.step==0?h('DropdownItem',{props:{name:'meeting-立项申请'}},'立项申请'):h('span'),
						param.row.step==1?h('DropdownItem',{props:{name:'apply-尽调申请'}},'尽调'):h('span'),
						param.row.step==3?h('DropdownItem',{props:{name:'past-三重一大'}},'三重一大'):h('span'),
						param.row.step==4?h('DropdownItem',{props:{name:'contractInfo-合同拟定'}},'合同拟定'):h('span'),
						param.row.step==6?h('DropdownItem',{props:{name:'contractSign-签署'}},'签署'):h('span'),
						param.row.step==7?h('DropdownItem',{props:{name:'loanApply-放款'}},'放款'):h('span'),
						param.row.step==10?h('DropdownItem',{props:{name:'filingApply-归档'}},'归档'):h('span')
					])
				])
			])
        }
    }
];


