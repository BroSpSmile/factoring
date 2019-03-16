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
		modal1 : false,
		statusItems : [],
		models:[],
		steps:[],
		indexsteps:[]
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
			});
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
		toStepName:function(value){
			for(let index in this.steps){
				if(this.steps[index].value == value){
					return this.steps[index].text;
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
			window.open("factoring","_blank"); 
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
	    		style:{
	    			fontSize:'13px'
	    		}
	    	},[
	    		h('Row',{},[
	    			h('Col',{
	    				props:{span:24}
	    			},[
	    				h('span',{style:{color:'#2db7f5'}},'基础合同:'),
	    				h('span',{},params.row.detail.baseContract)
	    			])
	    		]),
	    		h('Divider',{props:{size:'small'}}),
	    		h('Row',{},[
	    			h('Col',{
	    				props:{span:6}
	    			},[
	    				h('span',{style:{color:'#2db7f5'}},'签署日期:'),
	    				h('span',{},moment(params.row.detail.signDate).format('YYYY-MM-DD'))
	    			]),
	    			h('Col',{
	    				props:{span:6}
	    			},[
	    				h('span',{style:{color:'#2db7f5'}},'合同回款日:'),
	    				h('span',{},moment(params.row.detail.remittanceDay).format('YYYY-MM-DD'))
	    			]),
	    			h('Col',{
	    				props:{span:6}
	    			},[
	    				h('span',{style:{color:'#2db7f5'}},'收益率:'),
	    				h('span',{},params.row.detail.returnRate+"%")
	    			])
	    		]),
	    		params.row.detail.loanInstallments.length < 1?
	    				h('div'):
	    				h('div',{},[
	    					h('Divider',{props:{size:'small'}}),
	    					h('Row',{},[
	    						h('Col',{},[
	    							h('span',{style:{color:'#2db7f5'}},'已投放金额:'),
	    							h('span',{},params.row.detail.dropAmount)
	    						])
	    					]),
	    					h('br'),
	    					h('Row',{},[
	    						h('Col',{props:{span:4}},[h('span',{style:{fontWeight:'bold'}},'已投放金额(万元)')]),
	    						h('Col',{props:{span:6}},[h('span',{style:{fontWeight:'bold'}},'放款日')]),
	    					]),
	    					params.row.detail.factoringInstallments.map(item=>{
	    						return h('Row',{},[
	    							h('Col',{props:{span:4}},[h('span',{},item.amount)]),
	    							h('Col',{props:{span:6}},[h('span',{},moment(item.installmentDate).format('YYYY-MM-DD'))])
	    						])
	    					})
	    				]),
				params.row.detail.returnInstallments.length < 1?
	    				h('div'):
	    				h('div',{},[
	    					h('Divider',{props:{size:'small'}}),
	    					h('Row',{},[
	    						h('Col',{props:{span:4}},[h('span',{style:{fontWeight:'bold'}},'回款金额(万元)')]),
	    						h('Col',{props:{span:4}},[h('span',{style:{fontWeight:'bold'}},'是否已回款')]),
	    						h('Col',{props:{span:6}},[h('span',{style:{fontWeight:'bold'}},'实际回款日')])
	    					]),
	    					params.row.detail.factoringInstallments.map(item=>{
	    						return h('Row',{},[
	    							h('Col',{props:{span:4}},[h('span',{},item.amount)]),
	    							h('Col',{props:{span:4}},[h('span',{},vue.toBoolean(item.paied))]),
	    							h('Col',{props:{span:6}},[h('span',{},moment(item.installmentDate).format('YYYY-MM-DD'))])
	    						])
	    					})
	    				]),
				params.row.detail.factoringInstallments.length < 1?
	    				h('div'):
	    				h('div',{},[
	    					h('Divider',{props:{size:'small'}}),
	    					h('Row',{},[
	    						h('Col',{},[
	    							h('span',{style:{color:'#2db7f5'}},'保理费合计:'),
	    							h('span',{},"￥"+params.row.detail.totalFactoringFee+"万元")
	    						])
	    					]),
	    					h('br'),
	    					h('Row',{},[
	    						h('Col',{props:{span:4}},[h('span',{style:{fontWeight:'bold'}},'保理费分期金额(万元)')]),
	    						h('Col',{props:{span:6}},[h('span',{style:{fontWeight:'bold'}},'保理费到账日(前)')]),
	    						h('Col',{props:{span:4}},[h('span',{style:{fontWeight:'bold'}},'已开发票')]),
	    						h('Col',{props:{span:4}},[h('span',{style:{fontWeight:'bold'}},'是否已支付')])
	    					]),
	    					params.row.detail.factoringInstallments.map(item=>{
	    						return h('Row',{},[
	    							h('Col',{props:{span:4}},[h('span',{},item.amount)]),
	    							h('Col',{props:{span:6}},[h('span',{},moment(item.installmentDate).format('YYYY-MM-DD'))]),
	    							h('Col',{props:{span:4}},[h('span',{},item.invoice==null?'否':item.invoice)]),
	    							h('Col',{props:{span:4}},[h('span',{},vue.toBoolean(item.paied))])
	    						])
	    					})
	    				]),
	    				h('Divider',{props:{size:'small'}}),
	    		h('Row',{},[
	    			h('Col',{
	    				props:{span:24}
	    			},[
	    				h('span',{style:{color:'#2db7f5'}},'备注:'),
	    				h('span',{},params.row.detail.remark)
	    			])
	    		]),
	    	])
	    }
	},{
        title: '项目编号',
        key: 'projectId',
        align: 'center',
        width:125
    },{
        title: '项目名称',
        key: 'projectName',
        width:125,
        tooltip:true,
        align: 'center'
    },{
        title: '让与人',
        key: 'creditor',
        width:150,
        tooltip:true,
        align: 'center'
    },{
        title: '债务人',
        key: 'debtor',
        width:150,
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
        title: '受让款(万元)',
        key: 'projectName',
        align: 'center',
        width:80,
        render:(h,param)=>{
        	return h('span',param.row.detail.assignee)
        }
    },{
        title: '应收账款(万元)',
        key: 'projectName',
        align: 'center',
        width:80,
        render:(h,param)=>{
        	return h('span',param.row.detail.receivable)
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
        render:(h,param)=>{
        	return h('span',param.row.user.username)
        }
    },{
        title: '进度',
        key: 'progress',
        align: 'center',
        render:(h,param)=>{
        	return h('span',vue.toIndexStepName(param.row.step));
        }
    },{
    	title: '操作',
    	align: 'center',
    	width:80,
        render:(h,param)=>{
        	return h('div', [
				h('Poptip',{props:{trigger:'click',placement:'right',width:'1400'}},[
					h('Button',{props:{size:'small',type:'info',ghost:true}},[
						'进度',
						h('Icon',{props:{type:'ios-information-circle'}})
					]),
					h('div',{props:{},slot:'content'},[
						h('Card',{},[
							h('p',{slot:'title'},param.row.projectName),
							h('p',{slot:'extra'},"项目编号:"+param.row.projectId),
							h('div',{},[
								h('Steps',{
									props:{
										current:param.row.step
									}
								},[
									param.row.records.map(item=>{
										return h('Step',{
											props:{
												title:vue.toStepName(item.step),
												content:item.modifyTime==null?'':moment(item.modifyTime).format('YYYY-MM-DD')
											}
										})
									})
								])
//								,
//								h('Row',[
//									h('Col',{props:{span:3}},[h('div',{style:'ivu-steps-content'},'已完结')]),
//									h('Col',{props:{span:3}},[h('div',{style:'ivu-steps-content'},'已完结')]),
//									h('Col',{props:{span:3}},[h('Button',{props:{type:'error',size:'small',ghost:true}},'后补')]),
//									h('Col',{props:{span:3}},[h('Button',{props:{type:'dashed',size:'small'}},'操作')])
//								])
							])
						])
					])
				]),
				h('Dropdown',{
					props:{},
					on:{
						'on-click':(value)=>{
							vue.toMenu(value,param.row.id);
						}
					}
				},[
					h('Button',{props:{size:'small',type:'warning',ghost:true}},[
						'操作',
						h('Icon',{props:{type:'ios-arrow-down'}})
					]),
					h('DropdownMenu',{slot:'list'},[
						h('DropdownItem',{props:{name:'factoring'}},'编辑'),
						param.row.step==-1||param.row.step==0?h('DropdownItem',{props:{name:'meeting'}},'立项会'):h('span'),
						param.row.step==1?h('DropdownItem',{props:{name:'apply'}},'尽调'):h('span'),
						param.row.step==3?h('DropdownItem',{props:{name:'past'}},'三重一大'):h('span'),
						param.row.step==4?h('DropdownItem',{props:{name:'contractInfo'}},'合同拟定'):h('span'),
						param.row.step==6?h('DropdownItem',{props:{name:'contractSign'}},'签署'):h('span'),
						param.row.step==7?h('DropdownItem',{props:{name:'loanApply'}},'放款'):h('span'),
						param.row.step==9?h('DropdownItem',{props:{name:'filingApply'}},'归档'):h('span')
					])
				])
			])
        }
    }
];


