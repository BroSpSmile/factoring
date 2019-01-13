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
			pageSize : 5
		},
		pageInfo:{},
		modal1 : false,
		statusItems : []
	},
	created : function() {
		this.initDate();
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

		/**搜索 */
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
		 * 新增项目
		 */
		addProject : function() {
			this.modal1 = true;
		},

		/** 保存项目 */
		saveProject : function() {
			let self = this;

			this.$http.post("/approval", this.addForm).then(function(response) {
				if (response.data.success) {
					self.$Message.info({
						content : "保存成功",
						onClose : function() {
							self.cancel();
						}
					});
				} else {
					self.$Message.error(response.data.errorMessage);
				}
			}, function(error) {
				self.$Message.error(error.data.message);
			});
		},

		/**
		 * 取消保存
		 */
		cancel : function() {
			this.modal1 = false;
			this.$refs['entityDataForm'].resetFields();
		}
	}
});

vue.tableColumns=[
	{
        title: '项目编号',
        key: 'projectId',
        align: 'center'
    },{
        title: '项目名称',
        key: 'projectName',
        align: 'center'
    },{
        title: '项目负责人',
        key: 'person',
        align: 'center'
    },{
        title: '当前进度',
        key: 'progress',
        align: 'center'
    }
];