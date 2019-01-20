/**
 * 菜单信息
 */
common.pageName = "user";
common.openName = [ '8' ];

var vue = new Vue({
	el : '#user',
	data : {
		formInline:{
			type:[]
		},
		queryParam : {
			condition : {},
			pageNum : 1,
			pageSize : 10
		},
		pageInfo:{},
		modal1:false
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
			this.$http.get("/combo/meetingStatus").then(function(response) {
				_self.statusItems = response.data;
			}, function(error) {
				console.error(error);
			})
		},
		
		/**
		 * 查询
		 */
		query : function(page){
			this.queryParam.pageNum = page;
			var _self = this;
            _self.queryParam = _self.formInline;
			this.$http.post("/user/list", _self.queryParam).then(
					function(response) {
                        _self.pageInfo = response.data;
					}, function(error) {
                    	_self.$Message.error(error.data.message);
					})
		},
        /**
         * 状态翻译
         */
        getStatusDesc : function(value){
            if(value === 0) {
            	return "无效";
			} else if(value === 1) {
            	return "有效";
			}
            return "";
        },
		/**
		 * 新增用户
		 */
		add : function(){
			this.modal1=true;
		},
		
		/**
		 * 取消保存
		 */
		cancel : function() {
			this.modal1 = false;
			this.$refs['entityDataForm'].resetFields();
		},

        /** 分页 */
        pageChange : function(page){
            this.query();
        }
	}
});

vue.tableColumns=[
    {
        title: '用户名称',
        key: 'username',
        align: 'left'
    },{
        title: '手机号',
        key: 'mobile',
        align: 'center'
    },{
        title: '邮箱',
        key: 'email',
        align: 'left'
    },{
        title: '状态',
        key: 'status',
        align: 'center',
        render:(h,param)=> {
        	return h('span', vue.getStatusDesc(param.row.status));
		}
	}
];