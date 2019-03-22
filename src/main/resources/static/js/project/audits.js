/**
 * 菜单信息
 */
common.pageName = "audits";
common.openName = [ 'audits' ];

var vue = new Vue({
	el : '#audits',
	data : {
		auditType:[],
		auditResult:[],
		formInline:{},
		queryParam : {
			condition : {},
			pageNum : 1,
			pageSize : 10
		},
		pageInfo:{},
		tableColumns:[]
	},
	created : function() {
		this.initData();
		this.search();
	},
	methods : {
		
		/**
		 * 初始化
		 */
		initData:function(){
			let _self = this;
			this.$http.get("/combo/auditType").then(function(response) {
				_self.auditType = response.data;
			}, function(error) {
				console.error(error);
			});
			this.$http.get("/combo/auditResult").then(function(response) {
				_self.auditResult = response.data;
			}, function(error) {
				console.error(error);
			})
		},
		
		search:function(){
			this.queryParam.pageNum = 1;
			this.query();
		},
		
		/** 分页 */
		pageChange:function(page){
			this.queryParam.pageNum = page;
			this.query();
		},
		
		query:function(){
			let _self = this;
			_self.queryParam.condition = _self.formInline;
			this.$http.post("/audits/query",this.queryParam).then(function(response){
				_self.pageInfo = response.data;
			},function(error){
				console.error(error);
			})
		},
		
		reset:function(){
			
		},
		
		toAudit:function(auditId){
			window.open("audit?id="+auditId);
		},
		
		/**
		 * 转义
		 */
		auditTypeText:function(value){
			for(let index in this.auditType){
				if(value==this.auditType[index].value){
					return this.auditType[index].text;
				}
			}
			return "";
		}
	}
});

vue.tableColumns=[
	{
        title: '项目编号',
        key: 'project',
        align: 'center',
        render:(h,param)=>{
        	return h('span',param.row.project.projectId)
        }
    },{
        title: '项目名称',
        key: 'center',
        align: 'center',
        render:(h,param)=>{
        	return h('span',param.row.project.projectName)
        }
    },{
        title: '申请类型',
        key: 'type',
        align: 'center',
        render:(h,param)=>{
        	return h('span',vue.auditTypeText(param.row.auditType))
        }
    },{
        title: '申请人',
        key: 'applicant',
        align: 'center',
        render:(h,param)=>{
        	return h('span',param.row.applicant.username)
        }
    },{
        title: '申请时间',
        key: 'createTime',
        align: 'center',
        render:(h,param)=>{
        	return h('span',moment(param.row.createTime).format('YYYY-MM-DD HH:mm:ss'))
        }
    },{
    	title: '操作',
    	align: 'center',
    	render:(h,param)=>{
        	return h('div', [
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
							 vue.toAudit(param.row.id);
						}
					}
				}, '审核')
			])
        }
    }
];