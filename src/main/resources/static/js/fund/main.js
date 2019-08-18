/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#fund',
	data : {
		fundStatus:[],
		modal1:false,
		users:[],
		queryParam : {
			condition : {},
			pageNum : 1,
			pageSize : 10
		},
		addForm:{
			id:0,
			investment:'',
			preVal:'',
			memberA:{},
			memberB:{},
			postVal:'',
			registeredCapital:'',
			shareHodingRate:''
		},
		formInline:{},
		tableColumns:[],
		pageInfo:{},
		validata:{
			projectName: [
            	{ required: true,  message: '项目名称不能为空',trigger:'blur'}
            ],
            'memberA.id': [
            	{ required: true,  type:'number', message: '项目成员A角不能为空',trigger: 'change'}
            ],
            'memberB.id': [
            	{ required: true,  type:'number', message: '项目成员B角不能为空',trigger: 'change'}
            ],
            companySortName: [
            	{ required: true,  message: '公司简称不能为空',trigger:'blur'}
            ],
            companyFullName: [
            	{ required: true,  message: '公司全称不能为空',trigger:'blur'}
            ],
            controllerOwner: [
            	{ required: true,  message: '实际控制人不能为空',trigger:'blur'}
            ],
            registeredCapital: [
            	{ required: true, type:'number', message: '注册资本不能为空',trigger:'blur'}
            ],
            chairman: [
	        	{ required: true,  message: '董事长不能为空',trigger:'blur'}
	        ]
        }
	},
	created : function() {
		this.initTable();
		this.initDate();
		this.search();
	},
	filters:{
		timeFilter:function(value){
			if(!value){
				return "";
			}
			return moment(value).format('YYYY-MM-DD HH:mm');
		}
	},
	methods : {
		/**
		 * 初始化表格数据
		 */
		initTable:function(){
			this.tableColumns=[{
		        title: '项目编号',
		        key: 'projectId',
		        align: 'center',
		        fixed:'left',
		        width:125
		    },{
		        title: '项目名称',
		        key: 'projectName',
		        minWidth:120,
		        tooltip:true,
		        align: 'center'
		    },{
		        title: '项目进度',
		        key: 'projectStep',
		        align: 'center',
		        minWidth:120,
		        render:(h,param)=>{
		        	return h('span',this.fliterStep(param.row.projectStep));
		        }
		    },{
		        title: '所在地',
		        key: 'location',
		        tooltip:true,
		        minWidth:100,
		        align: 'center'
		    },{
		        title: '所属行业',
		        key: 'industry',
		        tooltip:true,
		        minWidth:100,
		        align: 'center'
		    },{
		        title: '主营业务',
		        key: 'mainBusiness',
		        minWidth:100,
		        tooltip:true,
		        align: 'center'
		    },{
		        title: '项目成员',
		        key: 'member',
		        minWidth:100,
		        align: 'center',
		        render:(h,param)=>{
		        	let name = "";
		        	console.log();
		        	if(param.row.memberA!=null && param.row.memberA.username!=null){
		        		name = param.row.memberA.username;
		        	}
		        	if(param.row.memberB!=null &&param.row.memberB.username!=null){
		        		name += ","+param.row.memberB.username;
		        	}
		        	return h('span',name);
		        }
		    },{
		        title: '投资金额',
		        key: 'investment',
		        minWidth:100,
		        align: 'center',
		        render:(h,param)=>{
		        	return h('span',common.money.formatter(param.row.investment))
		        }
		    },{
		        title: '投资主体',
		        key: 'investmentPart',
		        minWidth:100,
		        align: 'center'
		    },{
		        title: '投后持股比例',
		        key: 'shareHodingRate',
		        width:80,
		        align: 'center'
		    },{
		        title: '投前估值',
		        key: 'preVal',
		        width:100,
		        align: 'center',
		        render:(h,param)=>{
		        	return h('span',common.money.formatter(param.row.preVal))
		        }
		    },{
		        title: '投后估值',
		        key: 'postVal',
		        width:100,
		        align: 'center',
		        render:(h,param)=>{
		        	return h('span',common.money.formatter(param.row.postVal))
		        }
		    },{
		        title: '出资时间',
		        key: 'investemntTime',
		        align: 'center',
		        width:100,
		        render:(h,param)=>{
		        	return h('span',moment(param.row.investemntTime).format('YYYY-MM-DD'))
		        }
		    },{
		        title: '项目明细',
		        key: 'edit',
		        align: 'center',
		        width:100,
		        render:(h,param)=>{
		        	return h('div', [
    	 				h('Dropdown',{
    	 					props:{},
    	 					on:{
    	 						'on-click':(value)=>{
    	 							this.toMenu(param.row);
    	 						}
    	 					}
    	 				},[
    	 					h('Button',{props:{size:'small',type:'warning',ghost:true}},[
    	 						'操作',
    	 						h('Icon',{props:{type:'ios-arrow-down'}})
    	 					]),
    	 					h('DropdownMenu',{slot:'list'},[
    	 						h('DropdownItem',{props:{name:'factoring-编辑'}},'编辑')
    	 					])
    	 				])
    	 			])
		        }
		    }]
		},
		
		/**
		 * 跳转菜单
		 */
		toMenu:function(project){
			this.modal1 = true;
			this.addForm = JSON.parse(JSON.stringify(project));
			if(null == this.addForm.memberA){
				this.addForm.memberA ={};
			}
			if(null == this.addForm.memberB){
				this.addForm.memberB ={};
			}
		},
		
		fliterStep:function(step){
			for(let index in this.fundStatus){
				if(this.fundStatus[index].value==step){
					return this.fundStatus[index].text;
				}
			}
			return "";
		},
		
		/**
		 * 初始化数据
		 */
		initDate:function(){
			var _self = this;
			this.$http.get("/combo/fundStatus").then(function(response) {
				_self.fundStatus = response.data;
			}, function(error) {
				console.error(error);
			});
			this.$http.get("/combo/users").then(function(response) {
				_self.users = response.data;
			}, function(error) {
				console.error(error);
			});
		},
		
		/**
		 * 搜索
		 */
		search:function(){
			this.queryParam.pageNum = 1;
			this.query();
		},
		
		/** 分页 */
		pageChange:function(page){
			this.queryParam.pageNum = page;
			this.query();
		},
		
		/** 分页查询 */
		query : function() {
			let self = this;
			self.queryParam.condition = self.formInline;
			if(self.queryParam.condition.stateDate){
				self.queryParam.condition.endDate = self.queryParam.condition.stateDate[1];
				self.queryParam.condition.stateDate = self.queryParam.condition.stateDate[0];
			}
			this.$http.post("/fund/query", self.queryParam).then(
					function(response) {
						let data = response.data;
						this.pageInfo = data;
					}, function(error) {
						self.$Message.error(error.data.message);
					})
		},
		
		
		
		/**
		 * 
		 */
		open:function(){
			this.modal1=true;
		},
		
		/**
		 * 重置
		 */
		reset:function(){
			this.formInline = {};
			this.$refs['searchForm'].resetFields();
		},
		
		save:function(){
			let _self = this;
			this.$refs['entityDataForm'].validate((valid) => {
				if(!valid){
					_self.$Message.error('校验失败,请完善信息!');
					return false;
				}else{
					_self.commit();
				}
			});
		},
		
		/**
		 * 
		 */
		commit:function(){
			let self = this;
			console.log(this.addForm);
			if(!this.addForm.projectId){
				this.$http.post("/fund", this.addForm).then(function(response) {
					this.$Spin.hide();
					if (response.data.success) {
						self.$Message.info({
							content : "保存成功",
							onClose : function() {
								self.modal=false;
								self.search();
							}
						});
					} else {
						self.$Message.error(response.data.errorMessage);
					}
				}, function(error) {
					this.$Spin.hide();
					self.$Message.error(error.data.message);
				});
			}else{
				this.$http.put("/fund", this.addForm).then(function(response) {
					this.$Spin.hide();
					if (response.data.success) {
						self.$Message.info({
							content : "保存成功",
							onClose : function() {
								self.modal=false;
								self.search();
							}
						});
					} else {
						self.$Message.error(response.data.errorMessage);
					}
				}, function(error) {
					this.$Spin.hide();
					self.$Message.error(error.data.message);
				});
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
		},

		/**
		 * 文件上传成功
		 */
		uploadSuccess : function(response, file, fileList) {
			this.fileList=fileList;
		},
		
		/**
		 * 文件上传失败
		 */
		uploadError:function(error, file, fileList){
			this.fileList=fileList;
		},
		
		removeFile:function(file, fileList){
			this.fileList=fileList;
			let fileId = file.response.data.fileId;
			let self =this;
			this.$http.delete("/file/"+fileId).then(function(response){
				if (response.data.success) {
					self.$Message.info("删除成功");
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			},function(error){
				self.$Message.error(error.data.errorMessage);
			})
			
		},
		
		/**
		 * formatter
		 */
		formatter:function(value){
			value = value+'';
			var intSum = value.replace( /\B(?=(?:\d{3})+$)/g, ',' );
			return '￥'+intSum;
		},
		
		parser:function(value){
			value = ""+value;
			return value.replace(/￥s?|(,*)/g, '')
		}
	}
});


