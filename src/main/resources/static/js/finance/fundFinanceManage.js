/**
 * 菜单信息
 */
common.pageName = "project";
common.openName = [ '1' ];

var vue = new Vue({
	el : '#fundFinance',
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
			detail:{
				investment:'',
				preVal:'',
				memberA:{},
				memberB:{},
				postVal:'',
				registeredCapital:'',
				shareHodingRate:''
			}
		},
		formInline:{
			detail:{
				projectStep:"FUND_LOAN"
			}
		},
		tableColumns:[],
		pageInfo:{}
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
		        key: 'detail.projectStep',
		        align: 'center',
		        minWidth:120,
		        render:(h,param)=>{
		        	return h('span',this.fliterStep(param.row.detail.projectStep))
		        }
		    },{
		        title: '所在地',
		        tooltip:true,
		        minWidth:100,
		        align: 'center',
                render:(h,param)=>{
                    return h('span',param.row.detail.location)
                }
		    },{
		        title: '所属行业',
		        key: 'detail.industry',
		        tooltip:true,
		        minWidth:100,
		        align: 'center',
                render:(h,param)=>{
                    return h('span',param.row.detail.industry)
                }
		    },{
		        title: '主营业务',
		        key: 'detail.mainBusiness',
		        minWidth:100,
		        tooltip:true,
		        align: 'center',
                render:(h,param)=>{
                    return h('span',param.row.detail.mainBusiness)
                }
		    },{
		        title: '项目成员',
		        key: 'member',
		        minWidth:100,
		        align: 'center',
		        render:(h,param)=>{
		        	let name = "";
		        	console.log();
		        	if(param.row.detail.memberA!=null && param.row.detail.memberA.username!=null){
		        		name = param.row.detail.memberA.username;
		        	}
		        	if(param.row.detail.memberB!=null &&param.row.detail.memberB.username!=null){
		        		name += ","+param.row.detail.memberB.username;
		        	}
		        	return h('span',name);
		        }
		    },{
		        title: '投资金额',
		        key: 'detail.investment',
		        minWidth:100,
		        align: 'center',
		        render:(h,param)=>{
		        	return h('span',common.money.formatter(param.row.detail.investment))
		        }
		    },{
		        title: '投资主体',
		        key: 'detail.investmentPart',
		        minWidth:100,
		        align: 'center',
                render:(h,param)=>{
                    return h('span',param.row.detail.investmentPart)
                }
		    },{
		        title: '投后持股比例',
		        key: 'detail.shareHodingRate',
		        width:80,
		        align: 'center',
                render:(h,param)=>{
                    return h('span',param.row.detail.shareHodingRate+"%")
                }
		    },{
		        title: '投前估值',
		        key: 'detail.preVal',
		        width:100,
		        align: 'center',
		        render:(h,param)=>{
		        	return h('span',common.money.formatter(param.row.detail.preVal))
		        }
		    },{
		        title: '投后估值',
		        key: 'detail.postVal',
		        width:100,
		        align: 'center',
		        render:(h,param)=>{
		        	return h('span',common.money.formatter(param.row.detail.postVal))
		        }
		    },{
		        title: '出资时间',
		        key: 'detail.investemntTime',
		        align: 'center',
		        width:100,
		        render:(h,param)=>{
		        	return h('span',param.row.detail.investemntTime?moment(param.row.detail.investemntTime).format('YYYY-MM-DD'):"")
		        }
		    },{
		        title: '操作',
				fixed:'right',
				align: 'center',
		        width:120,
		        render:(h,param)=>{
		        	return h('div', [
                        h('Button', {
                            props: {
                                size: "small",
                                type: "success",
                                ghost: true

                            },
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: () => {
									this.toOperate( param.row.projectId);
                                }
                            }
                        }, '放款'),
    	 			])
		        }
		    }]
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
			});
			this.$http.get("/combo/users").then(function(response) {
				_self.users = response.data;
			}, function(error) {
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
		 * 跳转操作页面
		 * @param id
		 */
		toOperate:function(id){
			window.location.href="fundFinanceOperate?id=" + id;
		},

		/**
		 * 重置
		 */
		reset:function(){
			this.formInline = {};
			this.$refs['searchForm'].resetFields();
		},
	}
});


